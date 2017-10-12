package com.vertical.app.module.transaction;

import com.vertical.annotation.AutoWire;
import com.vertical.annotation.autolayout.AutoLayout;
import com.vertical.app.R;
import com.vertical.app.base.BaseCatActivity;

/**
 * Created by ls on 10/12/17.
 */

@AutoWire(presenter = CreateOrderPresenter.class)
@AutoLayout(layout = R.layout.activity_createorder, title = "销售开单")
public class CreateOrderActivity extends BaseCatActivity<CreateOrderContract.Presenter> implements CreateOrderContract.View {

    @Override
    public void onOrderCreation(boolean success) {
    }
}
