package com.vertical.compiler;

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

import static com.google.auto.common.MoreElements.getPackage;

/**
 * Created by ls on 8/9/17.
 */

public class ComponentGenerator {
    private TypeSpec mTypeSpec;
    private TypeSpec.Builder mTypeSpecBuilder;
    private Filer mFiler;
    private Messager mMessager;

    private String mPackageName = "com.vertical.app.di";
    private String mModuleName = "CatDIModuleEx";

    public ComponentGenerator(Filer filer, Messager messager) {
        mFiler = filer;
        mMessager = messager;
        mTypeSpecBuilder = TypeSpec.interfaceBuilder("CatDIComponentEx").addModifiers(Modifier.PUBLIC);
    }

    private void generateAnnotation() {
        AnnotationSpec annotationSpec = AnnotationSpec.builder(ClassName.get("dagger", "Component"))
                .addMember("modules", "$L.class", ClassName.get(mPackageName, mModuleName))
                .build();
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

        String elementPackage = getPackage(enclosingElement).getQualifiedName().toString();
        String className = enclosingElement.getQualifiedName().toString().substring(elementPackage.length() + 1).replace('.', '$');

        ClassName bindingClassName = ClassName.get(elementPackage, className);
        ParameterSpec parameterSpec = ParameterSpec.builder(bindingClassName, "activity").build();

        MethodSpec methodSpec = MethodSpec.methodBuilder("inject")
                                    .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                                    .addParameter(parameterSpec)
                                    .build();

        mTypeSpecBuilder.addMethod(methodSpec);
    }


    private void generateFile(TypeSpec typeSpec) {
        try {
            JavaFile.builder(mPackageName, typeSpec).build().writeTo(mFiler);
        } catch (IOException e) {
            MessagerUtil.getInstance(mMessager).error("[generateFile] exception : " + e);
        }
    }

    public boolean generate(Set<? extends Element> elements) {
        if (elements == null || elements.isEmpty()) {
            MessagerUtil.getInstance(mMessager).info(">>> elements is null... <<<");
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
}
