package com.vertical.app.core;

import android.app.Application;
import android.content.Context;

import com.vertical.app.service.UserService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 11/23/17.
 */

@Module
public class CatDIAppModule {
    private Application mApplication;

    public CatDIAppModule(Application app) {
        this.mApplication = app;
    }

    @Provides
    public Application provideApplication() {
        return mApplication;
    }

    @Provides
    public Context provideApplicationContext() {
        return mApplication;
    }

    @Provides
    public UserService provideUserService() {
        return UserService.getInstance();
    }

}
