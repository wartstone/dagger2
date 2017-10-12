package com.vertical.annotation.autolayout;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by user on 2017/8/16.
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface AutoLayout {
     int layout();
     String title() default "";
     String title_left() default "返回";
     int title_left_drawable() default 0;
     String title_right() default "";
     int title_right_drawable() default 0;
}
