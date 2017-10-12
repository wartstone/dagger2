package com.vertical.app.module.transaction;

import android.content.Intent;

import com.vertical.app.bean.OrderBean;
import com.vertical.base.BasePresenter;
import com.vertical.base.BaseView;

import java.util.List;

/**
 * Created by ls on 10/12/17.
 */

public interface CreateOrderContract {
    public interface View extends BaseView<Presenter> {
        void onOrderCreation(boolean success);
    }

    public interface Presenter extends BasePresenter {
//        void submit(String farmerId, Retail retail, List<RetailDetail> retailDetail, List<RetailPrescription> prescription);
//
//        void getContent();
//
//        void result(int requestCode, int resultCode, Intent data);
//
//        void queryPrepaymentAmount(String id);
        void createOrder(OrderBean orderBean);
    }
}
