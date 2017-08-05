package com.vertical.app.module.home;

import android.content.Context;

import javax.inject.Inject;

/**
 * Created by ls on 7/27/17.
 */

public class HomeWorkPresenter implements HomeWorkContract.Presenter {


    protected HomeWorkContract.View mView;

    protected HomeWorkContract.Model mModel;

    protected Context mContext;

    public HomeWorkPresenter(HomeWorkContract.Model model, HomeWorkContract.View view, Context context) {
        mView = view;
        mModel = model;
        mContext = context;
        //mView.setPresenter(this);


    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
