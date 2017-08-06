package com.vertical.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by katedshan on 17/8/7.
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
public @interface LSYao {
    String name() default "undefined";

    String text() default "";
}
