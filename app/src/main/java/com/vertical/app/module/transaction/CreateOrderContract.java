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
        void createOrder(long receiver_id, long goods_id, int payment, int goods_count, double order_bill, String comments);
    }
}
