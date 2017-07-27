package com.vertical.app.module.home;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.vertical.app.R;

public class HomeActivity extends AppCompatActivity {
    private HomeWorkContract.Presenter mPresenter;
    private HomeWorkContract.View mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        HomeWorkFragment singleChatFragment = (HomeWorkFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (singleChatFragment == null) {
            singleChatFragment = HomeWorkFragment.newInstance();
            mView = singleChatFragment;

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.contentFrame, singleChatFragment);
            transaction.commitNow();
        }

        // Create the presenter
        mPresenter = new HomeWorkPresenter(new HomeWorkModel(), new HomeWorkFragment(), this);
    }
}
