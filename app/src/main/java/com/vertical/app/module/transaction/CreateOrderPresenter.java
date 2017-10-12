package com.vertical.app.module.transaction;

import com.vertical.app.base.BaseCatPresenter;
import com.vertical.app.bean.OrderBean;

/**
 * Created by ls on 10/12/17.
 */

public class CreateOrderPresenter extends BaseCatPresenter<CreateOrderContract.View> implements CreateOrderContract.Presenter {
    public CreateOrderPresenter(CreateOrderContract.View view) {
        super(view);
    }

    @Override
    public void createOrder(OrderBean orderBean) {

    }
}
