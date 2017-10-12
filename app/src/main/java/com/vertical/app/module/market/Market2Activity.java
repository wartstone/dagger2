package com.vertical.app.module.market;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.vertical.annotation.AutoWire;
import com.vertical.app.R;
import com.vertical.app.bean.BaseBean;
import com.vertical.app.bean.MessageBean;
import com.vertical.app.bean.UserBean;
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
                        Log.d(TAG, "greeting onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "greeting onError: " + e);
                    }

                    @Override
                    public void onNext(MessageBean messageBean) {
                        Log.d(TAG, "greeting  onNext: id = " + messageBean.getId() + ", content = " + messageBean.getContent());
                    }
                });
    }

    public void getUsers(View view) {
        HttpModule.getSharedInstance().createRetrofit(CatApis.class)
                .getUsrs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new rx.Observer<UserBean>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "getUsers onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "getUsers onError : " + e);
                    }

                    @Override
                    public void onNext(UserBean userBeanBaseBean) {
                        Log.d(TAG, "getUsers onNext : size = " + userBeanBaseBean.getResultSize());
                        for(int i = 0; i < userBeanBaseBean.getResultSize(); i++) {
                            Log.d(TAG, "getUsers onNext : name = " + userBeanBaseBean.getResult().get(i).name + ",  age = " + userBeanBaseBean.getResult().get(i).age);
                        }
                    }
                });


        HttpModule.getSharedInstance().createRetrofit(CatApis.class)
                .fetchUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new rx.Observer<UserBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError");
                    }

                    @Override
                    public void onNext(UserBean userBean) {
                        Log.d(TAG, "onNext");
                    }
                });
    }

    public void addUser(View view) {
        HttpModule.getSharedInstance().createRetrofit(CatApis.class)
                .addUser("sss", 12)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new rx.Observer<BaseBean>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "getUsers onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "getUsers onError : " + e);
                    }

                    @Override
                    public void onNext(BaseBean baseBean) {
                        Log.d(TAG, "getUsers onNext : success = " + baseBean.isSuccess() + ", message= " + baseBean.getMessage());
                    }
                });
    }

    public void getMembers(View view) {
        HttpModule.getSharedInstance().createRetrofit(CatApis.class)
                .addUser("sss", 12)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new rx.Observer<BaseBean>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "getUsers onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "getUsers onError : " + e);
                    }

                    @Override
                    public void onNext(BaseBean baseBean) {
                        Log.d(TAG, "getUsers onNext : success = " + baseBean.isSuccess() + ", message= " + baseBean.getMessage());
                    }
                });
    }
}
