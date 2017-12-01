package com.vertical.app.core;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ls on 11/23/17.
 */

@Singleton
@Component(modules = CatDIAppModule.class)
public interface CatDIAppComponent {
    void inject(BaseAutoApp app);
}