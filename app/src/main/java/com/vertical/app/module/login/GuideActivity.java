package com.vertical.app.module.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.vertical.annotation.autolayout.AutoLayout;
import com.vertical.app.BuildConfig;
import com.vertical.app.R;
import com.vertical.app.base.BaseCatActivity;
import com.vertical.app.common.util.Trace;
import com.vertical.app.module.devtool.DevConfigActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ls on 12/5/17.
 */

@AutoLayout(layout = R.layout.activity_guide)
public class GuideActivity extends BaseCatActivity {
    private final String TAG = getClass().getSimpleName();
    private long mCurrentTime = 0;

    @BindView(R.id.tv_register)
    Button mRegisterTv;
    @BindView(R.id.tv_login)
    Button mLoginTv;
    @BindView(R.id.dev_tool)
    View mDevToolView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Trace.i(TAG, "GuideActivity onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy(){
        Trace.i(TAG, "GuideActivity onDestroy");
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        long interval = System.currentTimeMillis() - mCurrentTime;
        if(interval < 2000) {
            finish();
            System.exit(0);
        } else {
            mCurrentTime = System.currentTimeMillis();
            Toast.makeText(this, "再按一次退出农丁掌柜", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onViewCreated() {
        if(BuildConfig.FLAVOR.equalsIgnoreCase("Product")) {
            mDevToolView.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.tv_register)
    void onRegister() {
        launchScreen(RegisterActivity.class);
    }

    @OnClick(R.id.tv_login)
    void onLogin() {
        launchScreen(LoginActivity.class);
    }

    @OnClick(R.id.dev_tool)
    void onDevTool() {
        launchScreen(DevConfigActivity.class);
    }
}
