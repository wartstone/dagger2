package com.vertical.annotation;

/**
 * Created by ls on 8/10/17.
 */

public class Configuration {
    public static String PackageName = "com.vertical.android.di";
    public static String CorePackageName = "com.vertical.core.base";
    public static String ResPackageName = "com.vertical.app";
    public static String LayoutPackageName="com.vertical.android.layout";
    public static String ComponentName = "HnDIComponent";
    public static String ModuleName = "HnDIModule";
    public static String InjectorActivityName = "BaseAutoInjectActivity";
    public static String InjectorFragmentName = "BaseAutoInjectFragment";
    public static String AutoLayoutActivityName = "BaseAutoActivity"; // 之后考虑将BaseAutoActivity与BaseAutoInjectActivity合并, 减少层级
    public static String AutoLayoutFragmentName = "BaseAutoFragment";
    public static String AutoApp = "BaseAutoApp";

    static final String VIEW_TYPE = "android.view.View";
    static final String ACTIVITY_TYPE = "android.app.Activity";
    static final String FRAGMENT_TYPE = "android.support.v4.app.Fragment";
}
