package com.vertical.app.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.vertical.app.R;
//import com.vertical.app.di.CatDIComponent;
import com.vertical.app.di.CatDIComponentEx;
//import com.vertical.app.di.DaggerCatDIComponent;
import com.vertical.app.di.CatDIModuleEx;
import com.vertical.app.di.DaggerCatDIComponentEx;
import com.vertical.base.BasePresenter;
import com.vertical.base.BaseView;
import com.vertical.core.base.BaseRxActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by katedshan on 17/8/5.
 */

public abstract class BaseCatActivity<T extends BasePresenter> extends BaseRxActivity implements BaseView<T> {
    @Inject
    protected T mPresenter;
    private Unbinder mUnBinder;

    protected TextView mTitleText;

    @BindView(R.id.layout_content)
    protected FrameLayout mFrameLayout;

    protected FrameLayout mToolbarContainer;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (mPresenter != null) mPresenter.subscribe();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_base);

        inject();

        initParentViews();

        mUnBinder = ButterKnife.bind(this);

        onViewCreated();

        initEventAndData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnBinder.unbind();
        if (mPresenter != null) mPresenter.unsubscribe();
    }

    protected CatDIComponentEx getActivityComponent() {
        return DaggerCatDIComponentEx.builder()
                .catDIModuleEx(new CatDIModuleEx())
                .build();

//        return null;
    }

    private void initParentViews() {
        LayoutInflater inflater = LayoutInflater.from(BaseCatActivity.this);
        if (getLayout() > 0) {
            inflater.inflate(getLayout(), mFrameLayout);
        }
    }

    protected void onViewCreated() {}
    protected void initEventAndData() {}

    protected abstract void inject();
    protected abstract int getLayout();
}
