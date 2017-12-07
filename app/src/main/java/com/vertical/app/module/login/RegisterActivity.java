package com.vertical.app.module.login;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vertical.annotation.autolayout.AutoLayout;
import com.vertical.app.BuildConfig;
import com.vertical.app.R;
import com.vertical.app.base.BaseCatActivity;
import com.vertical.app.bean.BaseBean;
import com.vertical.app.bean.BaseBeanEx;
import com.vertical.app.bean.UserBean;
import com.vertical.app.common.util.CountDownTimerTool;
import com.vertical.app.common.util.InputUtil;
import com.vertical.app.common.util.PreferenceHelper;
import com.vertical.app.common.util.Trace;
import com.vertical.app.common.util.Utils;
import com.vertical.app.network.CatApis;
import com.vertical.app.network.HttpModule;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ls on 12/5/17.
 */

@AutoLayout(layout = R.layout.activity_register, title = "注册")
public class RegisterActivity extends BaseCatActivity implements View.OnClickListener {
    private EditText et_phone;
    private EditText et_register;
    private Button get_reguster_code_btn;
    private ImageView iv_checkbox;
    private TextView r_free_phone;
    private Button agree_btn;
    private Button register_btn;
    private String firstInPhone;
    private String nextInPhone;
    private Boolean isCkeck = true;
    private Intent intent;
    private Boolean flag = false;
    private Boolean flag2 = false;
    private String register;
    private String registerPhoneNum;
    private String checkNum = "-----1";
    private SimpleDateFormat df;
    private Date curDate;
    private Date endDate;
    private long diff;

    public final static String KEY_USERTOKEN = "cat_usertoken";

    @Override
    protected void onViewCreated() {
        initView();
        initListenerEvent();
        setTextEvent();
    }

    private void initListenerEvent() {

        get_reguster_code_btn.setOnClickListener(this);
        register_btn.setOnClickListener(this);
        agree_btn.setOnClickListener(this);
        iv_checkbox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isCkeck) {
                    iv_checkbox.setBackgroundResource(R.drawable.radio_off);
                    isCkeck = !isCkeck;
                    register_btn.setBackgroundResource(R.drawable.bg_white_radius_5);
                    register_btn.setEnabled(false);
                } else {
                    iv_checkbox.setBackgroundResource(R.drawable.radio_on);
                    isCkeck = !isCkeck;
                    if (flag2 && flag) {
                        register_btn.setBackgroundResource(R.drawable.bg_orange_radius_5);
                        register_btn.setEnabled(true);
                    }

                }

            }
        });

        et_phone.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && s.length() > 0) {
                    flag = true;

                    if (flag2 && isCkeck) {
                        register_btn.setBackgroundResource(R.drawable.bg_orange_radius_5);
                        register_btn.setEnabled(true);
                    }
                } else {
                    flag = false;
                    register_btn.setBackgroundResource(R.drawable.bg_white_radius_5);
                    register_btn.setEnabled(false);
                }
            }
        });
        et_register.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && s.length() > 0) {
                    flag2 = true;

                    if (flag && isCkeck) {
                        register_btn.setBackgroundResource(R.drawable.bg_orange_radius_5);
                        register_btn.setEnabled(true);
                    }
                } else {
                    flag2 = false;
                    register_btn.setEnabled(false);
                    register_btn.setBackgroundResource(R.drawable.bg_white_radius_5);
                }

            }
        });

    }

    private void setTextEvent() {
        String str = "未收到验证码，请拨打免费热线获取帮助";
        String clickTxt = "免费热线";
        r_free_phone.setText(str);
        SpannableStringBuilder spannable = new SpannableStringBuilder(str);
        int startIndex = str.indexOf(clickTxt);
        int endIndex = startIndex + clickTxt.length();
        // 文字点击
        spannable.setSpan(new TextClick(), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 一定要记得设置，不然点击不生效
        r_free_phone.setMovementMethod(LinkMovementMethod.getInstance());
        r_free_phone.setText(spannable);
    }
    
    public void initView() {

        et_phone = (EditText) findViewById(R.id.et_phone);
        et_register = (EditText) findViewById(R.id.et_register);
        et_phone.setTextColor(Color.BLACK);
        et_register.setTextColor(Color.BLACK);
        get_reguster_code_btn = (Button) findViewById(R.id.get_reguster_code_btn);
        iv_checkbox = (ImageView) findViewById(R.id.iv_checkbox);
        iv_checkbox.setBackgroundResource(R.drawable.radio_on);
        r_free_phone = (TextView) findViewById(R.id.r_free_phone);
        agree_btn = (Button) findViewById(R.id.agree_btn);
        register_btn = (Button) findViewById(R.id.register_btn);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_reguster_code_btn:
                df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                curDate = new Date(System.currentTimeMillis());
                if (InputUtil.isMobile(et_phone.getText().toString().trim())) {
                    firstInPhone = et_phone.getText().toString().trim();
                } else {
                    Toast.makeText(this, "手机号码格式不正确，请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!Utils.isInternetConnected(getApplicationContext())) {
                    Toast.makeText(getApplicationContext(), "当前网络不可用，请检查网络连接", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    if ("Product".equalsIgnoreCase(BuildConfig.FLAVOR)) {
                        checkNum = Utils.getVerificationCode();
                    } else {
                        checkNum = "666666";
                    }

                    CountDownTimerTool mCountDownTimerUtils = new CountDownTimerTool(get_reguster_code_btn, 60000, 1000);
                    mCountDownTimerUtils.start();
                    break;
                }
            case R.id.agree_btn:
//                WebViewActivity.launchWebViewActivity(this, "/api/pages/integral_mall/serviceAgreement.html", "");
                break;
            case R.id.register_btn:

                if (!checkNum.equals(et_register.getText().toString().trim())) {
                    Toast.makeText(getApplicationContext(), "您未获取验证码或验证码错误", Toast.LENGTH_LONG).show();
                    break;
                }
                endDate = new Date(System.currentTimeMillis());
                diff = endDate.getTime() - curDate.getTime();
                if (diff > 30 * 60 * 1000) {
                    Toast.makeText(getApplicationContext(), "验证码已失效，请重新获取", Toast.LENGTH_LONG).show();
                    et_register.setText("");
                    break;
                }
                if (InputUtil.isMobile(et_phone.getText().toString().trim())) {
                    nextInPhone = et_phone.getText().toString().trim();
                } else {
                    Toast.makeText(this, "请输入正确的手机号码后，获取验证码", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (!Utils.isInternetConnected(getApplicationContext())) {
                    Toast.makeText(getApplicationContext(), "当前网络不可用，请检查网络连接", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!firstInPhone.equals(nextInPhone)) {
                    Toast.makeText(getApplicationContext(), "您输入的手机号码已改变，请重新输入手机号或重新获取验证码", Toast.LENGTH_LONG).show();
                    return;
                }

                registerUser();
                break;
            default:
                break;
        }
    }

    private class TextClick extends ClickableSpan {

        // 在此处理指定文字的点击事件
        @Override
        public void onClick(View v) {

            // 跳转拨打免费热线
            intent = new Intent().setAction(Intent.ACTION_CALL);
            Uri data = Uri.parse("tel: 4009009078");
            intent.setData(data);
            startActivity(intent);
        }

        // 设置“免费热线”的字体颜色
        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(Color.BLUE);
        }

    }

    private void registerUser() {
        UserBean userBean = new UserBean();
        userBean.setName("");
        userBean.setPhone(et_phone.getText().toString().trim());

        HttpModule.getSharedInstance().createRetrofit(CatApis.class)
                .register(userBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseBeanEx<String>>() {
                    @Override
                    public void onCompleted() {
                        Trace.d(TAG, "createUser onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Trace.d(TAG, "createUser onError : " + e);
                    }

                    @Override
                    public void onNext(BaseBeanEx<String> stringBaseBeanEx) {
                        if(!stringBaseBeanEx.isSuccess()) {
                            Toast.makeText(RegisterActivity.this, stringBaseBeanEx.getData(), Toast.LENGTH_LONG).show();
                            return;
                        }

                        String token = stringBaseBeanEx.getData();
                        PreferenceHelper.getInstance(RegisterActivity.this).saveString(KEY_USERTOKEN, token);
                        Trace.d(TAG, "token = " + token);
                    }
                });
    }
}