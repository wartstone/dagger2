package com.vertical.app.module.mine;

import com.vertical.annotation.autolayout.AutoLayout;
import com.vertical.app.R;
import com.vertical.app.base.BaseCatActivity;
import com.vertical.app.common.widget.CatTextView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ls on 10/31/17.
 */

@AutoLayout(layout = R.layout.activity_setting, title = "设置", title_right = "保存")
public class SettingActivity extends BaseCatActivity {
    @BindView(R.id.setting_msgtemplate)
    CatTextView mMsgTemplate;
    @BindView(R.id.setting_cardType)
    CatTextView mCardType;
    @BindView(R.id.setting_salesBackReminder)
    CatTextView mReminder;

    @OnClick(R.id.setting_msgtemplate)
    void clickMsgTemplate() {
        launchScreen(SignatureActivity.class);
    }

    @OnClick(R.id.setting_cardType)
    void clickCardType() {

    }

    @OnClick(R.id.setting_salesBackReminder)
    void clickReminder() {

    }

}