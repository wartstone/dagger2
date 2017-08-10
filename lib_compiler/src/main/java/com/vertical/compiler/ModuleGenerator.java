package com.vertical.compiler;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;
import com.vertical.annotation.AutoWire;

import java.io.IOException;
import java.util.Set;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

import static com.google.auto.common.MoreElements.getPackage;
import static com.vertical.compiler.Configuration.ModuleName;
import static com.vertical.compiler.Configuration.PackageName;

/**
 * Created by ls on 8/9/17.
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

    private void generateMethod(Element element) {
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

        // 2. generate view instance
        String elementPackage = getPackage(enclosingElement).getQualifiedName().toString();
        String className = enclosingElement.getQualifiedName().toString().substring(elementPackage.length() + 1).replace('.', '$');
        ClassName bindingClassName = ClassName.get(elementPackage, className);

        MethodSpec viewMethodSpec = MethodSpec.methodBuilder("provide" + className)
                .addAnnotation(annotationSpec)
                .addStatement("return new $L()", className)
                .returns(bindingClassName)
                .build();

        mTypeSpecBuilder.addMethod(viewMethodSpec);

        // 3. generate presenter instance
        AutoWire annotation = enclosingElement.getAnnotation(AutoWire.class);
        TypeMirror presenterMirror = getPresenterTypeMirror(annotation);
        TypeElement presenter = (TypeElement) mTypeUtils.asElement(presenterMirror);

        TypeMirror contractMirror = getContractTypeMirror(annotation);
        TypeElement contract = (TypeElement) mTypeUtils.asElement(contractMirror);

        ParameterSpec parameterSpec = ParameterSpec.builder(bindingClassName, "view").build();

        MethodSpec presenterMethodSpec = MethodSpec.methodBuilder("provide" + presenter.getSimpleName())
                .addAnnotation(annotationSpec)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(parameterSpec)
                .addStatement("return new $L(view)", presenter)
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


        //2. generate each method
        for(Element element : elements) {
            if (element.getKind() != ElementKind.CLASS) {
                MessagerUtil.getInstance(mMessager).error("Only classes can be annotated");
                return false;
            }

            generateMethod(element);
        }

        //3. generate whole file
        mTypeSpec = mTypeSpecBuilder.build();
        generateFile(mTypeSpec);

        return true;
    }

    private TypeMirror getPresenterTypeMirror(AutoWire annotation) {
        try {
            annotation.presenter();
        } catch(MirroredTypeException mte ) {
            return mte.getTypeMirror();
        }
        return null;
    }

    private TypeMirror getContractTypeMirror(AutoWire annotation) {
        try {
            annotation.contract();
        } catch(MirroredTypeException mte ) {
            return mte.getTypeMirror();
        }
        return null;
    }
}
