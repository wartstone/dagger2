package com.vertical.annotation;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;

/**
 * Created by ls on 8/9/17.
 */

public class MessagerUtil {
    private static MessagerUtil mInstance;
    private static Messager mMeesager;

    private MessagerUtil(Messager messager) {
        this.mMeesager = messager;
    }

    public static MessagerUtil getInstance(Messager messager) {
        if(mInstance == null) {
            mInstance = new MessagerUtil(messager);
        }

        return mInstance;
    }


    public void error(Element e, String msg, Object... args) {
        mMeesager.printMessage(
                Diagnostic.Kind.ERROR,
                String.format(msg, args),
                e);
    }

    public void error(String msg, Object... args) {
        mMeesager.printMessage(
                Diagnostic.Kind.ERROR,
                String.format(msg, args));
    }

    public void info(String msg, Object... args) {
        mMeesager.printMessage(
                Diagnostic.Kind.NOTE,
                String.format(msg, args));
    }

    public void info(Element e, String msg, Object... args) {
        mMeesager.printMessage(
                Diagnostic.Kind.NOTE,
                String.format(msg, args),
                e);
    }
}
