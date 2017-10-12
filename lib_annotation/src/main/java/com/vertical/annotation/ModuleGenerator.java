package com.vertical.annotation;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Set;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

import static com.google.auto.common.MoreElements.getPackage;
import static com.vertical.annotation.Configuration.ModuleName;
import static com.vertical.annotation.Configuration.PackageName;

/**
 * Created by ls on 8/9/17.
 * Note: HnDIModule对于activity的处理只需提供mActivity, 之后在各个provider中强行转换.
 * 原理: 每个provide方法都会生成一个类(HnDiModule_ProvideDetailPresenterFactory), 之后在DaggerHnDIComponent中对每个provider进行调用引用。
 *      但由于在注入时， BaseAutoInjectActivity的autoInject会根据当前instance做针对性的注入, 因此DaggerHnDIComponent中某些类型不匹配的provider不会被调用到.
 *      (如注入Detail2Activity时， 对于detailActivityMembersInjector的inject()方法不会被调用，因此在HnDIModule中每个provider中， mActivity被强行转化为不同的类不会出错)
 */

public class ModuleGenerator {
    private TypeSpec mTypeSpec;
    private TypeSpec.Builder mTypeSpecBuilder;
    private Filer mFiler;
    private Messager mMessager;
    private Types mTypeUtils;

    public ModuleGenerator(Filer filer, Messager messager, Types typeUtils) {
        mFiler = filer;
        mMessager = messager;
        mTypeUtils = typeUtils;
        mTypeSpecBuilder = TypeSpec.classBuilder(ModuleName).addModifiers(Modifier.PUBLIC);
    }

    private void generateAnnotation() {
        AnnotationSpec annotationSpec = AnnotationSpec.builder(ClassName.get("dagger", "Module")).build();
        mTypeSpecBuilder.addAnnotation(annotationSpec);
    }

    private void generateGeneralProvider() {
        mTypeSpecBuilder.addField(ClassName.get("android.app", "Application"), "mApplication", Modifier.PRIVATE);
        mTypeSpecBuilder.addField(ClassName.get("android.app", "Activity"), "mActivity", Modifier.PRIVATE);
        mTypeSpecBuilder.addField(ClassName.get("android.app", "Fragment"), "mFragment", Modifier.PRIVATE);

        ParameterSpec appparameterSpec = ParameterSpec.builder(ClassName.get("android.app", "Application"), "application").build();
        ParameterSpec activityparameterSpec = ParameterSpec.builder(ClassName.get("android.app", "Activity"), "activity").build();
        ParameterSpec fragmentparameterSpec = ParameterSpec.builder(ClassName.get("android.app", "Fragment"), "fragment").build();

        // 1: activity constructor type
        MethodSpec constructorMethod = MethodSpec.constructorBuilder()
                                            .addModifiers(Modifier.PUBLIC)
                                            .addParameter(appparameterSpec)
                                            .addParameter(activityparameterSpec)
                                            .addStatement("this.mApplication = application")
                                            .addStatement("this.mActivity = activity")
                                            .build();

        mTypeSpecBuilder.addMethod(constructorMethod);

        // 2. fragment constructor type
        MethodSpec constructorMethod2 = MethodSpec.constructorBuilder()
                                            .addModifiers(Modifier.PUBLIC)
                                            .addParameter(appparameterSpec)
                                            .addParameter(fragmentparameterSpec)
                                            .addStatement("this.mApplication = application")
                                            .addStatement("this.mFragment = fragment")
                                            .build();

        mTypeSpecBuilder.addMethod(constructorMethod2);
    }

    private void generatePresenterProvider(Element element) {
        TypeElement enclosingElement;

        if(element instanceof TypeElement) {
            enclosingElement = (TypeElement) element;
        } else if(element.getEnclosingElement() instanceof TypeElement) {
            enclosingElement = (TypeElement) element.getEnclosingElement();
        } else {
            return;
        }

        // 1. prepare annotation
        AnnotationSpec annotationSpec = AnnotationSpec.builder(ClassName.get("dagger", "Provides")).build();

        // 2. generate presenter instance
        String elementPackage = getPackage(enclosingElement).getQualifiedName().toString();
        String className = enclosingElement.getQualifiedName().toString().substring(elementPackage.length() + 1).replace('.', '$');
        ClassName bindingClassName = ClassName.get(elementPackage, className);

        AutoWire annotation = enclosingElement.getAnnotation(AutoWire.class);
        TypeMirror presenterMirror = Utils.getPresenterTypeMirror(annotation);
        TypeElement presenter = (TypeElement) mTypeUtils.asElement(presenterMirror);

        TypeMirror contractMirror = Utils.getMVPContractClass(element.asType(), mMessager);
        if(contractMirror == null) {
            contractMirror = Utils.getContractTypeMirror(annotation);
        }
        TypeElement contract = (TypeElement) mTypeUtils.asElement(contractMirror);

        MethodSpec presenterMethodSpec = MethodSpec.methodBuilder("provide" + presenter.getSimpleName())
                .addAnnotation(annotationSpec)
                .addModifiers(Modifier.PUBLIC)
                .addStatement("return new $T(($T)mActivity)", presenter, ClassName.get(getPackage(contract).getQualifiedName().toString() + '.' + contract.getSimpleName(), "View"))
                .returns(ClassName.get(getPackage(contract).getQualifiedName().toString() + '.' + contract.getSimpleName(), "Presenter"))
                .build();

        mTypeSpecBuilder.addMethod(presenterMethodSpec);
    }

    private void generateFile(TypeSpec typeSpec) {
        try {
            JavaFile.builder(PackageName, typeSpec).build().writeTo(mFiler);
        } catch (IOException e) {
            MessagerUtil.getInstance(mMessager).error("[generateFile] exception : " + e);
        }
    }

    public boolean generate(Set<? extends Element> elements) {
        if (elements == null || elements.isEmpty()) {
            MessagerUtil.getInstance(mMessager).info(">>>[ModuleGenerator] generate elements is null... <<<");
            return false;
        }

        //1: generate annotation
        generateAnnotation();


        //2. generate methods

        //2.1 generate unique activity provider method
        generateGeneralProvider();

        //2.2 generate each presenter provider method
        for(Element element : elements) {
            if (element.getKind() != ElementKind.CLASS) {
                MessagerUtil.getInstance(mMessager).error("Only classes can be annotated");
                return false;
            }

            generatePresenterProvider(element);
        }

        //3. generate whole file
        mTypeSpec = mTypeSpecBuilder.build();
        generateFile(mTypeSpec);

        return true;
    }
}
