package com.vertical.core.base;

import android.support.annotation.CallSuper;

import com.vertical.base.BasePresenter;

/**
 * Created by katedshan on 17/8/5.
 */

public abstract class BaseRxPresenter implements BasePresenter {
    @Override
    @CallSuper
    public void subscribe() {

    }

    @Override
    @CallSuper
    public void unsubscribe() {

    }
}
