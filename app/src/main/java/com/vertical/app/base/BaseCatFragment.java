package com.vertical.app.base;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.vertical.android.di.BaseAutoFragment;
import com.vertical.android.di.BaseAutoInjectFragment;
import com.vertical.base.BasePresenter;
import com.vertical.base.BaseView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ls on 7/31/17.
 * Mvp arch with Rxjava support.
 */

public abstract class BaseCatFragment<T extends BasePresenter> extends BaseAutoFragment implements BaseView<T> {

    @Inject
    protected T mPresenter;
    private Unbinder mUnBinder;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (mPresenter != null) mPresenter.subscribe();
        super.onViewCreated(view, savedInstanceState);

        mUnBinder = ButterKnife.bind(this, mContentFrame);

        onViewCreated();

        initEventAndData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnBinder.unbind();
        if (mPresenter != null) mPresenter.unsubscribe();
    }

    protected void onViewCreated() {}
    protected void initEventAndData() {}

}
