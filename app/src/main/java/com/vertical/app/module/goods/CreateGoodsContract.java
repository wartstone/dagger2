package com.vertical.app.module.goods;

import com.vertical.base.BasePresenter;
import com.vertical.base.BaseView;

/**
 * Created by ls on 10/12/17.
 */

public interface CreateGoodsContract {
    public interface View extends BaseView<Presenter> {
        void onGoodsCreation(boolean success);
    }

    public interface Presenter extends BasePresenter {
        void createGoods(long receiver_id, long goods_id, int payment, int goods_count, double order_bill, String comments);
    }
}
