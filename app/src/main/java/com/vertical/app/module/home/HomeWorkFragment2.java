package com.vertical.app.module.home;

import android.util.Log;

import com.vertical.app.R;
import com.vertical.app.base.BaseFragment;
import com.vertical.app.bean.BaseBean;
import com.vertical.app.bean.MessageBean;
import com.vertical.app.bean.UserBean;
import com.vertical.app.network.CatApis;
import com.vertical.app.network.HttpModule;

import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ls on 7/27/17.
 */

public class HomeWorkFragment2 extends BaseFragment {
    private final String TAG = getClass().getSimpleName();

    @Override
    protected int inflateContentView() {
        return R.layout.fragment_work2;
    }

    @Override
    protected void onInitializeView() {

    }

    @OnClick(R.id.tv_1)
    public void changeText() {
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

    @OnClick(R.id.tv_2)
    public void getUsers() {
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

    @OnClick(R.id.tv_3)
    public void addUser() {
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

    @OnClick(R.id.tv_4)
    public void getMembers() {
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
