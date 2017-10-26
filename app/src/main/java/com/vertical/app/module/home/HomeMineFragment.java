package com.vertical.app.module.home;

import com.vertical.app.R;
import com.vertical.app.base.BaseFragment;
import com.vertical.app.module.transaction.CreateOrderActivity;

import butterknife.OnClick;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by ls on 7/27/17.
 */

public class HomeMineFragment extends BaseFragment {

    @Override
    protected int inflateContentView() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void onInitializeView() {

    }

    @OnClick(R.id.tv_createOrder)
    public void onCreateOrder() {
        launchScreen(CreateOrderActivity.class);
    }
}
