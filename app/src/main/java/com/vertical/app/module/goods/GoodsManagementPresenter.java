package com.vertical.app.module.goods;

import android.util.Log;

import com.vertical.app.base.BaseCatPresenter;
import com.vertical.app.bean.BaseListBean;
import com.vertical.app.bean.OrderBean;
import com.vertical.app.module.transaction.CreateOrderContract;
import com.vertical.app.network.CatApis;
import com.vertical.app.network.HttpModule;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ls on 10/12/17.
 */

public class GoodsManagementPresenter extends BaseCatPresenter<GoodsManagementContract.View> implements GoodsManagementContract.Presenter {
    private final String TAG = "CreateOrderPresenter";

    public GoodsManagementPresenter(GoodsManagementContract.View view) {
        super(view);
    }


    @Override
    public void fetchGoods(Long userid) {
//        HttpModule.getSharedInstance().createRetrofit(CatApis.class)
//                .createOrder(receiver_id, goods_id, payment, goods_count, order_bill, comments)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<BaseListBean<OrderBean>>() {
//                    @Override
//                    public void onCompleted() {
//                        Log.d(TAG, "createOrder onCompleted");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d(TAG, "createOrder onError : " + e);
//                    }
//
//                    @Override
//                    public void onNext(BaseListBean<OrderBean> orderBeanBaseListBean) {
//                        Log.d(TAG, "createOrder onNext");
//                    }
//                });
    }
}
