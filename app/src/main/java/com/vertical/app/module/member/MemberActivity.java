package com.vertical.app.module.member;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.vertical.annotation.AutoWire;
import com.vertical.app.R;
import com.vertical.app.base.BaseCatActivity;

//import butterknife.BindView;

@AutoWire(presenter = MemberPresenter.class, contract = MemberContract.class)
public class MemberActivity extends BaseCatActivity<MemberContract.Presenter> implements MemberContract.View {
    private final String TAG = "MemberActivity";

//    @BindView(R.id.detail_content)
    TextView mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    protected void inject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_home;
    }

    @Override
    protected void onViewCreated() {
        mPresenter.getContent();
    }

    @Override
    public void showContent() {
        //mContent.setText("dependency injected");
        Log.d(TAG, "showconent");
    }
}
