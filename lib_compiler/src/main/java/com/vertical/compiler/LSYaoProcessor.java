package com.vertical.compiler;

import com.google.auto.service.AutoService;
import com.vertical.annotation.LSYao;

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
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
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
    public synchronized void init(ProcessingEnvironment processingEnvironment)
    {
        super.init(processingEnvironment);

        //初始化我们需要的基础工具
        mTypeUtils = processingEnv.getTypeUtils();
        mElementUtils = processingEnv.getElementUtils();
        mFiler = processingEnv.getFiler();
        mMessager = processingEnv.getMessager();
    }

    @Override
    public SourceVersion getSupportedSourceVersion()
    {
        //支持的java版本
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes()
    {
        //支持的注解
        Set<String> annotations = new LinkedHashSet<>();
        annotations.add(LSYao.class.getCanonicalName());
        return annotations;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment)
    {
        //这里开始处理我们的注解解析了，以及生成Java文件

        for (Element annotatedElement : roundEnvironment.getElementsAnnotatedWith(ZyaoAnnotation.class)) {
            // 检查被注解为@Factory的元素是否是一个类
            if (annotatedElement.getKind() != ElementKind.CLASS) {
                error(annotatedElement, "Only classes can be annotated with @%s",
                        ZyaoAnnotation.class.getSimpleName());
                return true; // 退出处理
            }

            //解析，并生成代码
            analysisAnnotated(annotatedElement);

        return false;
    }

         final String SUFFIX = "$$ZYAO";

        private void analysisAnnotated(Element classElement)
        {
            LSYao annotation = classElement.getAnnotation(LSYao.class);
            String name = annotation.name();
            String text = annotation.text();

//        TypeElement superClassName = mElementUtils.getTypeElement(name);
            String newClassName = name + SUFFIX;

            StringBuilder builder = new StringBuilder()
                    .append("package com.zyao89.demoprocessor.auto;\n\n")
                    .append("public class ")
                    .append(newClassName)
                    .append(" {\n\n") // open class
                    .append("\tpublic String getMessage() {\n") // open method
                    .append("\t\treturn \"");

            // this is appending to the return statement
            builder.append(text).append(name).append(" !\\n");


            builder.append("\";\n") // end return
                    .append("\t}\n") // close method
                    .append("}\n"); // close class


            try { // write the file
                JavaFileObject source = mFiler.createSourceFile("com.zyao89.demoprocessor.auto."+newClassName);
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
}
