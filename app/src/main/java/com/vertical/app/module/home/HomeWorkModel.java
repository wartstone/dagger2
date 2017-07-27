package com.vertical.app.module.home;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by ls on 7/27/17.
 */

public class HomeWorkModel implements HomeWorkContract.Model {

    @Inject
    public HomeWorkModel() {

    }

    @Override
    public Observable getData() {
        return null;
    }

    @Override
    public Observable saveData() {
        return null;
    }
}
