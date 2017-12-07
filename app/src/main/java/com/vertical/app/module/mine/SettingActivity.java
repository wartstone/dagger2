package com.vertical.app.module.mine;

import android.text.TextUtils;
import android.widget.Toast;

import com.vertical.annotation.autolayout.AutoLayout;
import com.vertical.app.R;
import com.vertical.app.base.BaseCatActivity;
import com.vertical.app.bean.BaseBeanEx;
import com.vertical.app.bean.UserBean;
import com.vertical.app.common.util.PreferenceHelper;
import com.vertical.app.common.util.Trace;
import com.vertical.app.common.widget.CatTextView;
import com.vertical.app.module.home.HomeActivity;
import com.vertical.app.module.login.GuideActivity;
import com.vertical.app.network.CatApis;
import com.vertical.app.network.HttpModule;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.vertical.app.common.Constant.KEY_USERPHONE;
import static com.vertical.app.common.Constant.KEY_USERTOKEN;

/**
 * Created by ls on 10/31/17.
 */

@AutoLayout(layout = R.layout.activity_setting, title = "设置")
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

    @OnClick(R.id.btn_exit)
    void logout() {
        String phone = PreferenceHelper.getInstance(this).getString(KEY_USERPHONE, "");
        if(TextUtils.isEmpty(phone)) {
            Toast.makeText(SettingActivity.this, "用户手机号为空!", Toast.LENGTH_LONG).show();
            return;
        }

        UserBean userBean = new UserBean();
        userBean.setName("");
        userBean.setPhone(phone);

        HttpModule.getSharedInstance().createRetrofit(CatApis.class)
                .logout(userBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseBeanEx<String>>() {
                    @Override
                    public void onCompleted() {
                        Trace.d(TAG, "logout onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Trace.d(TAG, "logout onError : " + e);
                    }

                    @Override
                    public void onNext(BaseBeanEx<String> stringBaseBeanEx) {
                        if(!stringBaseBeanEx.isSuccess()) {
                            Toast.makeText(SettingActivity.this, stringBaseBeanEx.getData(), Toast.LENGTH_LONG).show();
                            return;
                        }

                        String token = stringBaseBeanEx.getData();
                        PreferenceHelper.getInstance(SettingActivity.this).saveString(KEY_USERTOKEN, token);
                        Trace.d(TAG, "token = " + token);

                        launchScreen(GuideActivity.class);
                    }
                });
    }

}