package com.vertical.annotation.autolayout;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.vertical.annotation.MessagerUtil;

import java.io.IOException;
import java.util.Set;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Types;

import static com.google.auto.common.MoreElements.getPackage;
import static com.vertical.annotation.Configuration.AutoLayoutFragmentName;
import static com.vertical.annotation.Configuration.InjectorFragmentName;
import static com.vertical.annotation.Configuration.PackageName;
import static com.vertical.annotation.Configuration.ResPackageName;

/**
 * Created by user on 2017/8/21.
 */

public class AutoLayoutFragmentGenerator {
    private static final String methodName = "bindLayout";
    private TypeSpec.Builder mTypeSpecBuilder;
    private MethodSpec.Builder mLayoutMethodSpecBuilder, mCreateMethodSpecBuilder;
    private MethodSpec.Builder mCreateViewMethodSpecBuilder, mViewCreatedMethodSpecBuilder;
    private MethodSpec.Builder onLeftClickedMethodBuidler, onRightClickedMethodBuidler;
    private MethodSpec.Builder mHandleArgumentMethodBuilder, mFindViewMethodBuilder;
    private TypeSpec mTypeSpec;
    private Types mTypeUtils;
    private Filer mFiler;
    private Messager mMessager;

    public AutoLayoutFragmentGenerator(Filer filer, Messager messager, Types typeUtils) {
        mFiler = filer;
        mMessager = messager;
        mTypeUtils = typeUtils;
        mTypeSpecBuilder = TypeSpec.classBuilder(AutoLayoutFragmentName).addModifiers(Modifier.PUBLIC);
    }

    private void generateModifier() {
        mTypeSpecBuilder.addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                .superclass(ClassName.get(PackageName, InjectorFragmentName))
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
                .addModifiers(Modifier.PUBLIC)
                .addStatement("super.onCreate(savedInstanceState)")
                .addStatement("Bundle arguments = getArguments()")
                .beginControlFlow("if(arguments != null)")
                .addStatement("onHandleArguments(arguments)")
                .endControlFlow()
                .addParameter(parameterSpec)
                .returns(TypeName.VOID);
    }

    private void generateCreateViewMethod() {
        ParameterSpec inflaterParam = ParameterSpec.builder(ClassName.get("android.view", "LayoutInflater"), "inflater").build();
        ParameterSpec containerParam = ParameterSpec.builder(ClassName.get("android.view", "ViewGroup"), "container").build();
        ParameterSpec instanceParam = ParameterSpec.builder(ClassName.get("android.os", "Bundle"), "savedInstanceState").build();

        mCreateViewMethodSpecBuilder = MethodSpec.methodBuilder("onCreateView")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addStatement("$T root = inflater.inflate(R.layout.fragment_base, container, false);", ClassName.get("android.view", "View"))
                .addStatement("return root")
                .addParameter(inflaterParam)
                .addParameter(containerParam)
                .addParameter(instanceParam)
                .returns(ClassName.get("android.view", "View"));
    }

    private void generateViewCreatedMethod() {
        ParameterSpec containerParam = ParameterSpec.builder(ClassName.get("android.view", "View"), "view").build();
        ParameterSpec instanceParam = ParameterSpec.builder(ClassName.get("android.os", "Bundle"), "savedInstanceState").build();

        mViewCreatedMethodSpecBuilder = MethodSpec.methodBuilder("onViewCreated")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addStatement("autoLayout()")
                .addParameter(containerParam)
                .addParameter(instanceParam)
                .returns(TypeName.VOID);
    }

    private void generateLayoutMethodWrapper() {
        mLayoutMethodSpecBuilder = MethodSpec.methodBuilder("autoLayout")
                .addModifiers(Modifier.PROTECTED)
                .addStatement("mTitleFrame = ($T) findViewById($T.id.layout_title)", ClassName.get("com.vertical.core.ui", "TitleBarLayout"), ClassName.get(ResPackageName, "R"))
                .addStatement("mContentFrame = ($T) findViewById($T.id.layout_content)", ClassName.get("android.widget", "FrameLayout"), ClassName.get(ResPackageName, "R"))
                .addStatement("mTitleFrame.setOnTitleBarClickListener(this)")
                .returns(TypeName.VOID);
    }

    private void generateTitlebarMethods() {
        onLeftClickedMethodBuidler = MethodSpec.methodBuilder("onNavigationLeftClicked")
                                                        .addAnnotation(Override.class)
                                                        .addModifiers(Modifier.PUBLIC)
                                                        .addStatement("getActivity().onBackPressed()");

        onRightClickedMethodBuidler = MethodSpec.methodBuilder("onNavigationRightClicked")
                                                        .addAnnotation(Override.class)
                                                        .addModifiers(Modifier.PUBLIC);
    }

    private void generateHandleArgumentMethod() {
        ParameterSpec parameterSpec = ParameterSpec.builder(ClassName.get("android.os", "Bundle"), "extras").addModifiers(Modifier.FINAL).build();

        mHandleArgumentMethodBuilder = MethodSpec.methodBuilder("onHandleArguments")
                                                        .addModifiers(Modifier.PROTECTED)
                                                        .addParameter(parameterSpec);
    }

    private void generateFindViewMethod() {
        ParameterSpec parameterSpec = ParameterSpec.builder(int.class, "res").build();

        mFindViewMethodBuilder = MethodSpec.methodBuilder("findViewById")
                .addModifiers(Modifier.PROTECTED)
                .beginControlFlow("if(getView() != null)")
                .addStatement("return getView().findViewById(res)")
                .endControlFlow()
                .addStatement("return null")
                .addParameter(parameterSpec)
                .returns(ClassName.get("android.view", "View"));
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
                .build();

        CodeBlock contentFrameCodeBlock;
        if(title.isEmpty()) {
            contentFrameCodeBlock = CodeBlock.builder()
                    .add("\n")
                    .beginControlFlow("if(this instanceof $T)", bindingClassName)
                    .addStatement("mTitleFrame.setVisibility($L);", ClassName.get("android.view.View", "GONE"))
                    .addStatement("$T.from(getActivity()).inflate($L, mContentFrame, true)", ClassName.get("android.view", "LayoutInflater"), layoutID)
                    .endControlFlow()
                    .build();
        } else {
            contentFrameCodeBlock = CodeBlock.builder()
                    .add("\n")
                    .beginControlFlow("if(this instanceof $T)", bindingClassName)
                    .addStatement("mTitleFrame.configTitleBar($L, $S, $S, $L, $S);", leftDrawable, leftTitle, title, rightDrawable, rightTitle)
                    .addStatement("$T.from(getActivity()).inflate($L, mContentFrame, true)", ClassName.get("android.view", "LayoutInflater"), layoutID)
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
        generateCreateViewMethod();
        generateViewCreatedMethod();
        generateLayoutMethodWrapper();
        generateTitlebarMethods();
        generateHandleArgumentMethod();
        generateFindViewMethod();

        for(Element element : elements) {
            MessagerUtil.getInstance(mMessager).info("======================================= LLLLLLLLLLLL: %s === ", element.getSimpleName());
            generateCodeblock(element);
        }

        //4. assemble methods
        mTypeSpecBuilder.addMethod(mCreateMethodSpecBuilder.build());
        mTypeSpecBuilder.addMethod(mCreateViewMethodSpecBuilder.build());
        mTypeSpecBuilder.addMethod(mViewCreatedMethodSpecBuilder.build());
        mTypeSpecBuilder.addMethod(mLayoutMethodSpecBuilder.build());
        mTypeSpecBuilder.addMethod(onLeftClickedMethodBuidler.build());
        mTypeSpecBuilder.addMethod(onRightClickedMethodBuidler.build());
        mTypeSpecBuilder.addMethod(mHandleArgumentMethodBuilder.build());
        mTypeSpecBuilder.addMethod(mFindViewMethodBuilder.build());

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
}
