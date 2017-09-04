package com.vertical.app.module.market;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.vertical.annotation.AutoWire;
import com.vertical.app.R;
import com.vertical.app.bean.MessageBean;
import com.vertical.app.module.member.MemberPresenter;
import com.vertical.app.network.CatApis;
import com.vertical.app.network.HttpModule;

import java.util.List;
import java.util.Observer;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class Market2Activity extends Activity {
    private final String TAG = "Market2Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market2);
    }

    public void changeText(View view) {
        ((TextView)view).setText("GENERATED");



        HttpModule.getSharedInstance().createRetrofit(CatApis.class)
                .fetchData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new rx.Observer<MessageBean>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError");
                    }

                    @Override
                    public void onNext(MessageBean messageBean) {
                        Log.d(TAG, "onNext");
                    }
                });
    }
}
