package com.vertical.app.module.member;

import android.util.Log;

import com.vertical.app.base.BaseCatPresenter;
import com.vertical.app.bean.BaseBean;
import com.vertical.app.bean.MemberBean;
import com.vertical.app.network.CatApis;
import com.vertical.app.network.HttpModule;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ls on 10/12/17.
 */

public class CreateMemberPresenter extends BaseCatPresenter<CreateMemberContract.View> implements CreateMemberContract.Presenter {
    private final String TAG = "CreateOrderPresenter";

    public CreateMemberPresenter(CreateMemberContract.View view) {
        super(view);
    }

    @Override
    public void createMember(MemberBean memberBean) {
        HttpModule.getSharedInstance().createRetrofit(CatApis.class)
                .createMember(memberBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseBean>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "createOrder onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "createOrder onError : " + e);
                    }

                    @Override
                    public void onNext(BaseBean value) {
                        Log.d(TAG, "createOrder onNext" + value);
                        mView.onMemberCreated(true);
                    }
                });
    }
}
