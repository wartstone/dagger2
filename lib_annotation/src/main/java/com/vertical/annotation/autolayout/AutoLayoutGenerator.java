package com.vertical.annotation.autolayout;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.vertical.annotation.Configuration;
import com.vertical.annotation.MessagerUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Types;

import static com.google.auto.common.MoreElements.getPackage;
import static com.vertical.annotation.Configuration.AutoLayoutActivityName;
import static com.vertical.annotation.Configuration.InjectorActivityName;
import static com.vertical.annotation.Configuration.PackageName;
import static com.vertical.annotation.Configuration.ResPackageName;

/**
 * Created by user on 2017/8/21.
 */

public class AutoLayoutGenerator {
    private static final String methodName = "bindLayout";
    private TypeSpec.Builder mTypeSpecBuilder;
    private MethodSpec.Builder mLayoutMethodSpecBuilder, mCreateMethodSpecBuilder;
    private MethodSpec.Builder onLeftClickedMethodBuidler, onRightClickedMethodBuidler;
    private TypeSpec mTypeSpec;
    private Types mTypeUtils;
    private Filer mFiler;
    private Messager mMessager;

    public AutoLayoutGenerator(Filer filer, Messager messager, Types typeUtils) {
        mFiler = filer;
        mMessager = messager;
        mTypeUtils = typeUtils;
        mTypeSpecBuilder = TypeSpec.classBuilder(AutoLayoutActivityName).addModifiers(Modifier.PUBLIC);
    }

    private void generateModifier() {
        mTypeSpecBuilder.addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                .superclass(ClassName.get(PackageName, InjectorActivityName))
                .addSuperinterface(ClassName.get("com.vertical.core.ui", "TitleBarLayout.OnTitleBarClickListener"));
    }

    private void generateFields() {
        mTypeSpecBuilder.addField(ClassName.get("com.vertical.core.ui", "TitleBarLayout"), "mTitleFrame", Modifier.PROTECTED);
        mTypeSpecBuilder.addField(ClassName.get("android.widget", "FrameLayout"), "mContentFrame", Modifier.PROTECTED);
    }

    private void generateCreateMethod() {
        ParameterSpec parameterSpec = ParameterSpec.builder(ClassName.get("android.os", "Bundle"), "savedInstanceState").build();

        mCreateMethodSpecBuilder = MethodSpec.methodBuilder("onCreate")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PROTECTED)
                .addStatement("super.onCreate(savedInstanceState)")
                .addStatement("setContentView($T.layout.activity_base)", ClassName.get(ResPackageName, "R"))
                .addStatement("autoLayout()")
                .addParameter(parameterSpec)
                .returns(TypeName.VOID);
    }

    private void generateLayoutMethodWrapper() {
        mLayoutMethodSpecBuilder = MethodSpec.methodBuilder("autoLayout")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PROTECTED)
                .addStatement("mTitleFrame = ($T) findViewById($T.id.layout_title)", ClassName.get("com.vertical.core.ui", "TitleBarLayout"), ClassName.get(ResPackageName, "R"))
                .addStatement("mContentFrame = ($T) findViewById($T.id.layout_content)", ClassName.get("android.widget", "FrameLayout"), ClassName.get(ResPackageName, "R"))
                .returns(TypeName.VOID);
    }

    private void generateTitlebarMethods() {
        onLeftClickedMethodBuidler = MethodSpec.methodBuilder("onNavigationLeftClicked")
                                                        .addAnnotation(Override.class)
                                                        .addModifiers(Modifier.PUBLIC)
                                                        .addStatement("super.onBackPressed()");

        onRightClickedMethodBuidler = MethodSpec.methodBuilder("onNavigationRightClicked")
                                                        .addAnnotation(Override.class)
                                                        .addModifiers(Modifier.PUBLIC);
    }

    private void generateCodeblock(Element element) {
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

        AutoLayout annotation = element.getAnnotation(AutoLayout.class);
        String title = annotation.title();
        String leftTitle = annotation.title_left();
        String rightTitle = annotation.title_right();
        int leftDrawable = annotation.title_left_drawable();
        int rightDrawable = annotation.title_right_drawable();
        int layoutID = annotation.layout();

        CodeBlock titlebarCodeBlock = CodeBlock.builder()
                                               .add("\n")
                                               .addStatement("mTitleFrame.setOnTitleBarClickListener(this)")
                                               .build();

        CodeBlock contentFrameCodeBlock;
        if(title.isEmpty()) {
            contentFrameCodeBlock = CodeBlock.builder()
                    .add("\n")
                    .beginControlFlow("if(this instanceof $T)", bindingClassName)
                    .addStatement("mTitleFrame.setVisibility($L);", ClassName.get("android.view.View", "GONE"))
                    .addStatement("getLayoutInflater().inflate($L, mContentFrame, true)", layoutID)
                    .endControlFlow()
                    .build();
        } else {
            contentFrameCodeBlock = CodeBlock.builder()
                    .add("\n")
                    .beginControlFlow("if(this instanceof $T)", bindingClassName)
                    .addStatement("mTitleFrame.configTitleBar($L, $S, $S, $L, $S);", leftDrawable, leftTitle, title, rightDrawable, rightTitle)
                    .addStatement("getLayoutInflater().inflate($L, mContentFrame, true)", layoutID)
                    .endControlFlow()
                    .build();
        }



        mLayoutMethodSpecBuilder.addCode(titlebarCodeBlock);
        mLayoutMethodSpecBuilder.addCode(contentFrameCodeBlock);
    }

    public boolean generateEx(Set<? extends Element> elements) {
        //1: generate modifier
        generateModifier();

        //2. generate fields
        generateFields();

        //3. generate methods
        generateCreateMethod();
        generateLayoutMethodWrapper();
        generateTitlebarMethods();

        for(Element element : elements) {
            generateCodeblock(element);
        }

        //4. assemble methods
        mTypeSpecBuilder.addMethod(mCreateMethodSpecBuilder.build());
        mTypeSpecBuilder.addMethod(mLayoutMethodSpecBuilder.build());
        mTypeSpecBuilder.addMethod(onLeftClickedMethodBuidler.build());
        mTypeSpecBuilder.addMethod(onRightClickedMethodBuidler.build());

        //5. generate whole file
        mTypeSpec = mTypeSpecBuilder.build();
        generateFile(mTypeSpec);

        return true;
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
        MethodSpec.Builder method = createMethod();
        method.addCode("int resId = 0;\n");
        method.addCode("int visible = 0;\n");

        int postion = 0;

        for (Element element : elements) {
            generateCodeBlock(method, element, postion);
            postion++;
        }

        ClassName viewClass = ClassName.get("android.view", "View");

        method.addCode("\n activity.setContentView(resId);\n");
        method.addCode("if(visible == 0){\n");
        method.addCode("    barLayout.setVisibility($L.GONE);\n", viewClass);
        method.addCode("}else{\n");
        method.addCode("    barLayout.setVisibility($L.VISIBLE);\n",viewClass);
        method.addCode("}\n");

        mTypeSpecBuilder.addMethod(method.build());

        try {
            JavaFile.builder(Configuration.LayoutPackageName, mTypeSpecBuilder.build()).build().writeTo(mFiler);
        } catch (IOException e) {
            MessagerUtil.getInstance(mMessager).error("[generateFile] exception : " + e);
        }

        return true;
    }

    private MethodSpec.Builder createMethod() {

        MethodSpec.Builder mMethodSpecBuilder = MethodSpec.methodBuilder(methodName).addModifiers(Modifier.PUBLIC, Modifier.STATIC);
        ArrayList<ParameterSpec> list = new ArrayList<>();

        ParameterSpec parameterSpec = ParameterSpec.builder(ClassName.get("android.app", "Activity"), "activity").build();
        ParameterSpec barLayoutSpec = ParameterSpec.builder(ClassName.get("com.henong.android.core", "TitleBarLayout"), "barLayout").build();

        list.add(parameterSpec);
        list.add(barLayoutSpec);
        mMethodSpecBuilder.addParameters(list);

        return mMethodSpecBuilder;
    }

    /**
     * 生成代码块
     *
     * @param builder
     * @param element
     * @param postion
     */
    private void generateCodeBlock(MethodSpec.Builder builder, Element element, int postion) {
        AutoLayout layout = element.getAnnotation(AutoLayout.class);
        int resId = layout.layout();
        AutoLayoutEnum visible = layout.visible();

        StringBuffer buffer = new StringBuffer();

        if (postion == 0) {
            buffer.append(String.format("if(activity instanceof %s){\n", getClassName(element)));
        } else {
            buffer.append(String.format("else if(activity instanceof %s){\n", getClassName(element)));
        }

        buffer.append(String.format("   resId = %s;\n", resId));
        buffer.append(String.format("   visible = %s;\n", visible.getValue()));
        buffer.append("}");

        builder.addCode(buffer.toString());
    }

    /**
     * 获取当前类信息
     *
     * @param element
     * @return
     */
    private ClassName getClassName(Element element) {

        TypeElement enclosingElement;

        if (element instanceof TypeElement) {
            enclosingElement = (TypeElement) element;
        } else if (element.getEnclosingElement() instanceof TypeElement) {
            enclosingElement = (TypeElement) element.getEnclosingElement();
        } else {
            return null;
        }

        String elementPackage = getPackage(enclosingElement).getQualifiedName().toString();
        String className = enclosingElement.getQualifiedName().toString().substring(elementPackage.length() + 1).replace('.', '$');
        ClassName bindingClassName = ClassName.get(elementPackage, className);

        return bindingClassName;
    }

}
