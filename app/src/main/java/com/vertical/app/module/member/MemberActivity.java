package com.vertical.app.module.member;

import android.util.Log;
import com.vertical.annotation.AutoWire;
import com.vertical.annotation.autolayout.AutoLayout;
import com.vertical.app.R;
import com.vertical.app.base.BaseCatActivity;

@AutoWire(presenter = MemberPresenter.class, contract = MemberContract.class)
@AutoLayout(layout = R.layout.activity_member)
public class MemberActivity extends BaseCatActivity<MemberContract.Presenter> implements MemberContract.View {
    private final String TAG = "MemberActivity";

    @Override
    protected void onViewCreated() {
        mPresenter.getContent();
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    public void showContent() {
        Log.d(TAG, "showconent");
    }
}
