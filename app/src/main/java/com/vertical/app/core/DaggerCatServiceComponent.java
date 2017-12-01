package com.vertical.app.core;

import android.content.Context;

import com.vertical.app.service.UserService;
import com.vertical.app.service.UserService_MembersInjector;

import javax.inject.Provider;

import dagger.MembersInjector;

/**
 * Created by ls on 11/23/17.
 */

public class DaggerCatServiceComponent {
    public CatDIAppModule mCatDIAppModule;

    private Provider<Context> provideApplicationContextProvider;
    private MembersInjector<UserService> userServiceMembersInjector;

    private static DaggerCatServiceComponent mInstance;

    public static DaggerCatServiceComponent getInstance() {
        synchronized (DaggerCatServiceComponent.class) {
            if (mInstance == null) {
                mInstance = new DaggerCatServiceComponent();
            }
        }
        return mInstance;
    }

    public void initialize() {
        this.provideApplicationContextProvider = CatDIAppModule_ProvideApplicationContextFactory.create(mCatDIAppModule);
        this.userServiceMembersInjector = UserService_MembersInjector.create(provideApplicationContextProvider);
        this.userServiceMembersInjector.injectMembers(CatDIAppModule_ProvideUserServiceFactory.create(mCatDIAppModule).get());
    }
}
