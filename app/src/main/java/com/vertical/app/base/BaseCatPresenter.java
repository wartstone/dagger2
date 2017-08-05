package com.vertical.app.base;

import com.vertical.base.BaseView;
import com.vertical.core.base.BaseRxPresenter;

/**
 * Created by katedshan on 17/8/5.
 */

public abstract class BaseCatPresenter<T extends BaseView> extends BaseRxPresenter {
    protected T mView;

    public BaseCatPresenter(T view) {
        mView = view;
    }
}
