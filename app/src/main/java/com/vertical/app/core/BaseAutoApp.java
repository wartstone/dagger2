package com.vertical.app.core;

import android.content.Context;
import android.support.annotation.CallSuper;

import com.vertical.app.common.util.Trace;
import com.vertical.core.CoreApp;

import javax.inject.Inject;

/**
 * Created by ls on 12/1/17.
 */

public class BaseAutoApp extends CoreApp {

    @Inject
    Context mContext;

    @Override
    protected void autoInject() {
        DaggerCatServiceComponent.getInstance().mCatDIAppModule = new CatDIAppModule(this);
        DaggerCatDIAppComponent.builder().catDIAppModule(DaggerCatServiceComponent.getInstance().mCatDIAppModule).build().inject(this);
        DaggerCatServiceComponent.getInstance().initialize();
        Trace.i("--------   mContext= " + mContext);
    }
}
