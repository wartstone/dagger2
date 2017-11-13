package com.vertical.annotation;

import com.google.auto.service.AutoService;
import com.vertical.annotation.autoinject.InjectorActivityGenerator;
import com.vertical.annotation.autoinject.InjectorFragmentGenerator;
import com.vertical.annotation.autolayout.AutoLayout;
import com.vertical.annotation.autolayout.AutoLayoutActivityGenerator;
import com.vertical.annotation.autolayout.AutoLayoutFragmentGenerator;

import java.util.HashSet;
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

import static com.vertical.annotation.Utils.isSubtypeOfActivity;
import static com.vertical.annotation.Utils.isSubtypeOfFragment;


/**
 * Created by ls on 8/9/17.
 */

@AutoService(Processor.class)
public class AutoWireProcessor extends AbstractProcessor {
    private Types mTypeUtils;
    private Elements mElementUtils;
    private Filer mFiler;
    private Messager mMessager;

    private ComponentGenerator mComponenetGenerator;
    private ModuleGenerator mModuleGenerator;
    private InjectorActivityGenerator mInjectorActivityGenerator;
    private InjectorFragmentGenerator mInjectorFragmentGenerator;
    private AutoLayoutActivityGenerator mAutoLayoutActivityGenerator;
    private AutoLayoutFragmentGenerator mAutoLayoutFragmentGenerator;

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
        mInjectorActivityGenerator = new InjectorActivityGenerator(mFiler, mMessager, mTypeUtils);
        mInjectorFragmentGenerator = new InjectorFragmentGenerator(mFiler, mMessager, mTypeUtils);
        mAutoLayoutActivityGenerator =new AutoLayoutActivityGenerator(mFiler, mMessager, mTypeUtils);
        mAutoLayoutFragmentGenerator = new AutoLayoutFragmentGenerator(mFiler, mMessager, mTypeUtils);
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        //支持的java版本
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
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

        Set<Element> activityElements = new HashSet<>();
        Set<Element> fragmentElements = new HashSet<>();
        for(Element element : elements) {
            if(isSubtypeOfActivity(element.asType(), mMessager)) {
                activityElements.add(element);
            } else if(isSubtypeOfFragment(element.asType(), mMessager)) {
                fragmentElements.add(element);
            }
        }

        mComponenetGenerator.generate(elements);
        mModuleGenerator.generate(elements);
        mInjectorActivityGenerator.generate(activityElements);
        mInjectorFragmentGenerator.generate(fragmentElements);

        Set<? extends Element> autoLayoutelements = roundEnvironment.getElementsAnnotatedWith(AutoLayout.class);

        activityElements.clear();
        fragmentElements.clear();
        for(Element element : autoLayoutelements) {
            if(isSubtypeOfActivity(element.asType(), mMessager)) {
                activityElements.add(element);
            } else if(isSubtypeOfFragment(element.asType(), mMessager)) {
                fragmentElements.add(element);
            }
        }


        mAutoLayoutActivityGenerator.generateEx(activityElements);
        mAutoLayoutFragmentGenerator.generateEx(fragmentElements);

        return true;
    }
}
