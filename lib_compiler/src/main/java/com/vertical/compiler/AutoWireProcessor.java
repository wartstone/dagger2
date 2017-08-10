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

    private ComponentGenerator mComponenetGenerator;
    private ModuleGenerator mModuleGenerator;
    private AutoInjectorGenerator mAutoInjectorGenerator;

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
        mAutoInjectorGenerator = new AutoInjectorGenerator(mFiler, mMessager, mTypeUtils);
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
        mAutoInjectorGenerator.generate(elements);

        return true;
    }
}
