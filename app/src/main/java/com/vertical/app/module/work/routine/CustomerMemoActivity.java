package com.vertical.app.module.work.routine;

import com.vertical.annotation.AutoWire;
import com.vertical.annotation.autolayout.AutoLayout;
import com.vertical.app.R;
import com.vertical.app.base.BaseCatActivity;
import com.vertical.app.bean.OrderBean;
import com.vertical.app.module.transaction.CreateOrderContract;
import com.vertical.app.module.transaction.CreateOrderPresenter;

import java.sql.Date;
import java.sql.Timestamp;

import butterknife.OnClick;

/**
 * Created by ls on 10/12/17.
 */

@AutoLayout(layout = R.layout.activity_dailypush, title = "跟进记录")
public class CustomerMemoActivity extends BaseCatActivity<CreateOrderContract.Presenter> implements CreateOrderContract.View {

    @Override
    public void onOrderCreation(boolean success) {
    }

    @OnClick(R.id.submit)
    protected void submit() {
        finish();
    }
}
