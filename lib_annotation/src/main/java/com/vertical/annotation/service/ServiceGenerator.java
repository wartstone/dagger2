package com.vertical.annotation.service;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.vertical.annotation.MessagerUtil;

import java.io.IOException;
import java.util.Set;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Types;

import static com.google.auto.common.MoreElements.getPackage;
import static com.vertical.annotation.Configuration.ComponentName;
import static com.vertical.annotation.Configuration.CorePackageName;
import static com.vertical.annotation.Configuration.InjectorActivityName;
import static com.vertical.annotation.Configuration.ModuleName;
import static com.vertical.annotation.Configuration.PackageName;

/**
 * Created by ls on 8/10/17.
 */

public class ServiceGenerator {
    private TypeSpec mTypeSpec;
    private TypeSpec.Builder mTypeSpecBuilder;
    private MethodSpec.Builder mMethodSpecBuilder;
    private Filer mFiler;
    private Messager mMessager;
    private Types mTypeUtils;

    public ServiceGenerator(Filer filer, Messager messager, Types typeUtils) {
        mFiler = filer;
        mMessager = messager;
        mTypeUtils = typeUtils;
        mTypeSpecBuilder = TypeSpec.classBuilder(InjectorActivityName);
    }

    private void generateModifier() {
        mTypeSpecBuilder.addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                .superclass(ClassName.get(CorePackageName, "BaseRxActivity"));
    }

    private void generateMethodWrapper() {
        mMethodSpecBuilder = MethodSpec.methodBuilder("autoInject")
                                       .addAnnotation(Override.class)
                                       .addModifiers(Modifier.PROTECTED)
                                       .returns(TypeName.VOID);
    }

    private void generateCodeBlock(Element element) {
        TypeElement enclosingElement;

        if(element instanceof TypeElement) {
            enclosingElement = (TypeElement) element;
        } else if(element.getEnclosingElement() instanceof TypeElement) {
            enclosingElement = (TypeElement) element.getEnclosingElement();
        } else {
            return;
        }

        String elementPackage = getPackage(enclosingElement).getQualifiedName().toString();
        String className = enclosingElement.getQualifiedName().toString().substring(elementPackage.length() + 1).replace('.', '$');

        ClassName bindingClassName = ClassName.get(elementPackage, className);
        String moduleMethodName = String.format("%s%s", ModuleName.substring(0, 1).toLowerCase(), ModuleName.substring(1));
        String code = String.format("%s.builder().%s(new $L(getApplication(), this)).build().inject(($L)this)", "Dagger" + ComponentName, moduleMethodName);
        MessagerUtil.getInstance(mMessager).info("code is %s", code);
        CodeBlock codeBlock = CodeBlock.builder()
                .beginControlFlow("if(this instanceof $L)", bindingClassName)
                .addStatement(code, ClassName.get(PackageName, ModuleName), bindingClassName)
                .endControlFlow()
                .build();

        mMethodSpecBuilder.addCode(codeBlock);
    }

    private void generateFile(TypeSpec typeSpec) {
        try {
            JavaFile.builder(PackageName, typeSpec).build().writeTo(mFiler);
        } catch (IOException e) {
            MessagerUtil.getInstance(mMessager).error("[generateFile] exception : " + e);
        }
    }

    public boolean generate(Set<? extends Element> elements) {
        //1: generate modifier
        generateModifier();

        //2. generate method wrapper
        generateMethodWrapper();

        //3. generate method code blocks
        if (elements != null && !elements.isEmpty()) {
            for(Element element : elements) {
                if (element.getKind() != ElementKind.CLASS) {
                    MessagerUtil.getInstance(mMessager).error("Only classes can be annotated");
                    return false;
                }

                generateCodeBlock(element);
            }
        }

        //4. generate whole method
        mTypeSpecBuilder.addMethod(mMethodSpecBuilder.build());

        //5. generate whole file
        mTypeSpec = mTypeSpecBuilder.build();
        generateFile(mTypeSpec);

        return true;
    }
}
