package com.vertical.app.core;

import android.app.Application;

/**
 * Created by ls on 12/1/17.
 */

public class CatApp extends BaseAutoApp {
    private static Application mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }

    public static Application getAppContext() {
        return mInstance;
    }
}
