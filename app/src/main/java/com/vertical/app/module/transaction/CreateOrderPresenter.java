package com.vertical.app.module.transaction;

import android.util.Log;

import com.vertical.app.base.BaseCatPresenter;
import com.vertical.app.bean.BaseBean;
import com.vertical.app.bean.BaseListBean;
import com.vertical.app.bean.OrderBean;
import com.vertical.app.network.CatApis;
import com.vertical.app.network.HttpModule;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ls on 10/12/17.
 */

public class CreateOrderPresenter extends BaseCatPresenter<CreateOrderContract.View> implements CreateOrderContract.Presenter {
    private final String TAG = "CreateOrderPresenter";

    public CreateOrderPresenter(CreateOrderContract.View view) {
        super(view);
    }

    @Override
    public void createOrder(OrderBean orderBean) {
        HttpModule.getSharedInstance().createRetrofit(CatApis.class)
//                .createOrder(orderBean.id, orderBean.order_id)
                .createOrder2(orderBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseListBean<OrderBean>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "createOrder onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "createOrder onError : " + e);
                    }

                    @Override
                    public void onNext(BaseListBean<OrderBean> orderBeanBaseListBean) {
                        Log.d(TAG, "createOrder onNext");
                    }
                });
    }
}
