package com.vertical.app.module.goods;

import android.util.Log;

import com.vertical.app.base.BaseCatPresenter;
import com.vertical.app.bean.BaseListBean;
import com.vertical.app.network.CatApis;
import com.vertical.app.network.HttpModule;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ls on 10/12/17.
 */

public class CreateGoodsPresenter extends BaseCatPresenter<CreateGoodsContract.View> implements CreateGoodsContract.Presenter {
    private final String TAG = "CreateGoodsPresenter";

    public CreateGoodsPresenter(CreateGoodsContract.View view) {
        super(view);
    }

    @Override
    public void createGoods(long receiver_id, long goods_id, int payment, int goods_count, double order_bill, String comments) {
//        HttpModule.getSharedInstance().createRetrofit(CatApis.class)
//                .createGoods(receiver_id, goods_id, payment, goods_count, order_bill, comments)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<BaseListBean<GoodsBean>>() {
//                    @Override
//                    public void onCompleted() {
//                        Log.d(TAG, "createGoods onCompleted");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d(TAG, "createGoods onError : " + e);
//                    }
//
//                    @Override
//                    public void onNext(BaseListBean<GoodsBean> orderBeanBaseListBean) {
//                        Log.d(TAG, "createGoods onNext");
//                    }
//                });
    }
}
