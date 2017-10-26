package com.vertical.app.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.vertical.android.di.BaseAutoActivity;
import com.vertical.app.R;
//import com.vertical.app.di.CatDIComponent;
//import com.vertical.app.di.DaggerCatDIComponent;
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

public abstract class BaseCatActivity<T extends BasePresenter> extends BaseAutoActivity implements BaseView<T> {
    protected final String TAG = getClass().getSimpleName();

    @Inject
    protected T mPresenter;
    private Unbinder mUnBinder;

    @BindView(R.id.layout_content)
    protected FrameLayout mFrameLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (mPresenter != null) mPresenter.subscribe();
        super.onCreate(savedInstanceState);

        mUnBinder = ButterKnife.bind(this);

        onViewCreated();

        initEventAndData();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnBinder.unbind();
        if (mPresenter != null) mPresenter.unsubscribe();
    }

    public void launchScreen(Class<? extends Activity> target, Bundle... bd) {
        Intent intent = new Intent();
        intent.setClass(this, target);
        if (bd != null && bd.length > 0) {
            intent.putExtras(bd[0]);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(intent);
    }

    public void launchScreenForResult(Class<?> target, int requestCode, Bundle... bd) {
        Intent intent = new Intent();
        intent.setClass(this, target);
        if (bd != null && bd.length > 0) {
            intent.putExtras(bd[0]);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent, requestCode);
    }

    protected void onViewCreated() {}
    protected void initEventAndData() {}
}
