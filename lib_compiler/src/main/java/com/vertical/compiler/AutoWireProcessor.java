package com.vertical.compiler;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.vertical.annotation.AutoWire;

import java.io.IOException;
import java.io.Writer;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.JavaFileObject;
import static com.google.auto.common.MoreElements.getPackage;

/**
 * Created by katedshan on 17/8/7.
 */

@AutoService(Processor.class)
public class AutoWireProcessor extends AbstractProcessor{
    private Types mTypeUtils;
    private Elements mElementUtils;
    private Filer mFiler;
    private Messager mMessager;
    private ProcessingEnvironment mProcessEnv;

    private ComponentGenerator mComponenetGenerator;
    private ModuleGenerator mModuleGenerator;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);

        //初始化我们需要的基础工具
        mTypeUtils = processingEnv.getTypeUtils();
        mElementUtils = processingEnv.getElementUtils();
        mFiler = processingEnv.getFiler();
        mMessager = processingEnv.getMessager();
        mComponenetGenerator = new ComponentGenerator(mFiler, mMessager);
        mModuleGenerator = new ModuleGenerator(mFiler, mMessager, mTypeUtils);
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        //支持的java版本
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        //支持的注解
        Set<String> annotations = new LinkedHashSet<>();
        annotations.add(AutoWire.class.getCanonicalName());
        return annotations;
    }

    @Override
    public boolean process(Set<? extends TypeElement> sets, RoundEnvironment roundEnvironment) {
        if (sets == null || sets.isEmpty()) {
            MessagerUtil.getInstance(mMessager).info(">>> set is null... <<<");
            return true;
        }

        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(AutoWire.class);

        mComponenetGenerator.generate(elements);
        mModuleGenerator.generate(elements);

        return true;
    }

    String packageName = "com.vertical.app.di";

    private void analyzeAnnotated(Element element) throws ClassNotFoundException {
        TypeElement enclosingElement = (TypeElement) element;

        String elementPackage = getPackage(enclosingElement).getQualifiedName().toString();
        String className = enclosingElement.getQualifiedName().toString().substring(
                elementPackage.length() + 1).replace('.', '$');

        ClassName bindingClassName = ClassName.get(elementPackage, className);

        AnnotationSpec annotationSpec = AnnotationSpec.builder(ClassName.get("dagger", "Component"))
                                                    .addMember("modules", "$L.class", ClassName.get("com.vertical.app.di", "CatDIModuleEx"))
                                                    .build();
        ParameterSpec parameterSpec = ParameterSpec.builder(bindingClassName, "activity").build();

        //genera interface
        TypeSpec injectClass = TypeSpec.interfaceBuilder("CatDIComponentEx")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(annotationSpec)
                .addMethod(MethodSpec.methodBuilder("inject")
                        .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                        .addParameter(parameterSpec)
                        .build())
                .build();

        try {
            JavaFile.builder(packageName, injectClass).build().writeTo(mFiler);
            //info(classElement, "[Lingshan] analyzeAnnotated injectClass = " + injectClass.toString());
        } catch (IOException e) {
            MessagerUtil.getInstance(mMessager).error(element, "[Lingshan] analyzeAnnotated exception : " + e);
        }
    }

    private void analysisAnnotated(Element classElement) {
            MethodSpec bindViewMethod = MethodSpec.methodBuilder("inject")
                                                    .addModifiers(new javax.lang.model.element.Modifier[]{javax.lang.model.element.Modifier.PUBLIC, javax.lang.model.element.Modifier.ABSTRACT})
                                                    .addParameter(classElement.getClass(), "activity")
                                                    .returns(TypeName.VOID)
                                                    .build();

//        TypeElement superClassName = mElementUtils.getTypeElement(name);
            String newClassName = "CatDIComponentEx";

            StringBuilder builder = new StringBuilder()
                    .append("package com.vertical.app.di;\n\n")
                    .append("import com.vertical.core.di.PerActivity;\n")
                    .append("import dagger.Component;\n")
                    .append("import com.vertical.app.module.member.MemberActivity;\n\n")
                    .append("@PerActivity\n")
                    .append("@Component(modules = CatDIModule.class)\n")
                    .append(String.format("public interface %s {\n", newClassName))
                    //.append("void inject(MemberActivity activity);\n}\n")
                    .append(bindViewMethod.toString())
                    .append("}\n");


            try { // write the file
                JavaFileObject source = mFiler.createSourceFile("com.vertical.app.di."+ newClassName);
                Writer writer = source.openWriter();
                writer.write(builder.toString());
                writer.flush();
                writer.close();
            } catch (IOException e) {
                // Note: calling e.printStackTrace() will print IO errors
                // that occur from the file already existing after its first run, this is normal
            }

            MessagerUtil.getInstance(mMessager).info(">>> analysisAnnotated is finish... <<<");
    }


}
