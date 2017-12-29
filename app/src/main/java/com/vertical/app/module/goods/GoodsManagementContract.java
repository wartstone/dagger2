package com.vertical.app.module.goods;

import com.vertical.base.BasePresenter;
import com.vertical.base.BaseView;

import java.util.List;

/**
 * Created by ls on 10/12/17.
 */

public interface GoodsManagementContract {
    public interface View extends BaseView<Presenter> {
        void onShowGoods(List<GoodsBean> goodsBeanList);
    }

    public interface Presenter extends BasePresenter {
        void fetchGoods(Long userid);
    }
}
