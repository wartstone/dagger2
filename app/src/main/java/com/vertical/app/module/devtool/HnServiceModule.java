package com.vertical.app.module.devtool;


import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 11/22/17.
 */

@Module
public class HnServiceModule {

    public HnServiceModule() {
    }

    @Provides
    public CatDevService provideCatDevService() {
        return new CatDevService();
    }
}
