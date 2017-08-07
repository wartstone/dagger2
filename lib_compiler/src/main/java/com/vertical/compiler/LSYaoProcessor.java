package com.vertical.compiler;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.vertical.annotation.LSYao;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Modifier;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * Created by katedshan on 17/8/7.
 */

@AutoService(Processor.class)
public class LSYaoProcessor extends AbstractProcessor{
    private Types mTypeUtils;
    private Elements mElementUtils;
    private Filer mFiler;
    private Messager mMessager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);

        //初始化我们需要的基础工具
        mTypeUtils = processingEnv.getTypeUtils();
        mElementUtils = processingEnv.getElementUtils();
        mFiler = processingEnv.getFiler();
        mMessager = processingEnv.getMessager();
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
        annotations.add(LSYao.class.getCanonicalName());
        return annotations;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if (set == null || set.isEmpty())
        {
            info(">>> set is null... <<<");
            return true;
        }

        info(">>> Found field, start... <<<");

        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(LSYao.class);

        if (elements == null || elements.isEmpty())
        {
            info(">>> elements is null... <<<");
            return true;
        }

        // 遍历所有被注解了@Factory的元素
        for (Element annotatedElement : elements) {

            // 检查被注解为@Factory的元素是否是一个类
            if (annotatedElement.getKind() != ElementKind.CLASS) {
                error(annotatedElement, "Only classes can be annotated with @%s",
                        LSYao.class.getSimpleName());
                return true; // 退出处理
            }

            try {
                analyzeAnnotated(annotatedElement);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    private void analyzeAnnotated(Element classElement) throws ClassNotFoundException {
        MethodSpec.Builder bindViewMethod = MethodSpec.methodBuilder("inject")
                .addParameter(classElement.getClass(), "activity");

        AnnotationSpec spec = AnnotationSpec.builder(ClassName.get("dagger", "Component"))
                                            .addMember("modules", "$L", ClassName.get("com.vertical.app.di", "CatDIModule"))
                                            .build();

        //generaClass
        TypeSpec injectClass = TypeSpec.classBuilder(classElement.getSimpleName() + "_LS")
                .addAnnotation(spec)
                .addModifiers(new javax.lang.model.element.Modifier[Modifier.PUBLIC])
                .addMethod(bindViewMethod.build())
                .build();

        String packageName = "com.vertical.app.di";
        info(">>> analyzeAnnotated, start12... <<<");
        //info(">>> analyzeAnnotated, start12... %s<<<", injectClass.toString());
        if(injectClass == null) {
            info(">>> analyzeAnnotated, null  <<<");
        } else {
            info(">>> analyzeAnnotated, not null  <<<");
        }

        try {
            JavaFile.builder(packageName, injectClass).build().writeTo(mFiler);
            //info(classElement, "[Lingshan] analyzeAnnotated injectClass = " + injectClass.toString());
            //Logger.getGlobal().log(Level.ALL, "Lingshan");
        } catch (IOException e) {
            error(classElement, "[Lingshan] analyzeAnnotated exception : " + e);
        }
    }

    private void analysisAnnotated(Element classElement) {
            LSYao annotation = classElement.getAnnotation(LSYao.class);
            String name = annotation.name();
            String text = annotation.text();

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
                    .append("void inject(MemberActivity activity);\n}\n");


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

            info(">>> analysisAnnotated is finish... <<<");
    }

    private void error(Element e, String msg, Object... args) {
        mMessager.printMessage(
                Diagnostic.Kind.ERROR,
                String.format(msg, args),
                e);
    }

    private void info(String msg, Object... args) {
        mMessager.printMessage(
                Diagnostic.Kind.NOTE,
                String.format(msg, args));
    }

    private void info(Element e, String msg, Object... args) {
        mMessager.printMessage(
                Diagnostic.Kind.NOTE,
                String.format(msg, args),
                e);
    }
}
