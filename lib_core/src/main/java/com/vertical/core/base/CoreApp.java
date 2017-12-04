package com.vertical.core.base;

import android.app.Application;

/**
 * Created by ls on 12/1/17.
 */

public abstract class CoreApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        autoInject();
    }
    protected abstract void autoInject();
}
