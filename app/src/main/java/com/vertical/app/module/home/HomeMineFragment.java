package com.vertical.app.module.home;

import com.vertical.app.R;
import com.vertical.app.base.BaseFragment;
import com.vertical.app.module.mine.AccountActivity;
import com.vertical.app.module.mine.SettingActivity;
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

    @OnClick(R.id.layout_account)
    void clickAccount() {
        launchScreen(AccountActivity.class);
    }

    @OnClick(R.id.layout_settting)
    void clickSetting() {
        launchScreen(SettingActivity.class);
    }
}
