package com.vertical.app.module.login;

import android.app.Dialog;
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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.vertical.annotation.autolayout.AutoLayout;
import com.vertical.app.BuildConfig;
import com.vertical.app.R;
import com.vertical.app.base.BaseCatActivity;
import com.vertical.app.common.util.CountDownTimerTool;
import com.vertical.app.common.util.InputUtil;
import com.vertical.app.common.util.Utils;

/**
 * Created by ls on 12/5/17.
 */

@AutoLayout(layout = R.layout.activity_login, title = "登录")
public class LoginActivity extends BaseCatActivity implements View.OnClickListener {
    private Button login_btn;
    private EditText et_phone;
    private EditText et_register;
    private Button get_reguster_code_btn;
    private TextView tv_free_phone;
    private Intent intent;
    private String firstInPhone;
    private String nextInPhone;
    private Boolean flag = false;
    private Boolean flag2 = false;
    private String checkNum = "-0a0ac";
    private long startTimeMillions;

    @Override
    protected void onViewCreated() {
        initView();
        initListenerEvent();
        setTextEvent();
    }

    private void initView() {
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_register = (EditText) findViewById(R.id.et_register);
        et_phone.setTextColor(Color.BLACK);
        et_register.setTextColor(Color.BLACK);
        get_reguster_code_btn = (Button) findViewById(R.id.get_reguster_code_btn);
        tv_free_phone = (TextView) findViewById(R.id.tv_free_phone);
        login_btn = (Button) findViewById(R.id.login_btn);
    }

    private void initListenerEvent() {
        login_btn.setOnClickListener(this);
        get_reguster_code_btn.setOnClickListener(this);
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
                    if (flag2) {
                        login_btn.setBackgroundResource(R.drawable.bg_orange_radius_5);
                        login_btn.setEnabled(true);
                    }
                } else {
                    flag = false;
                    login_btn.setBackgroundResource(R.drawable.bg_white_radius_5);
                    login_btn.setEnabled(false);
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
                    if (flag) {
                        login_btn.setBackgroundResource(R.drawable.bg_orange_radius_5);
                        login_btn.setEnabled(true);
                    }
                } else {
                    flag2 = false;
                    login_btn.setEnabled(false);
                    login_btn.setBackgroundResource(R.drawable.bg_white_radius_5);
                }

            }
        });
    }

    private void setTextEvent() {
        String str = "未收到验证码，请拨打免费热线获取帮助";
        String clickTxt = "免费热线";
        tv_free_phone.setText(str);
        SpannableStringBuilder spannable = new SpannableStringBuilder(str);
        int startIndex = str.indexOf(clickTxt);
        int endIndex = startIndex + clickTxt.length();
        // 文字点击
        spannable.setSpan(new TextClick(), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 一定要记得设置，不然点击不生效
        tv_free_phone.setMovementMethod(LinkMovementMethod.getInstance());
        tv_free_phone.setText(spannable);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_reguster_code_btn:
                startTimeMillions = System.currentTimeMillis();
                if (InputUtil.isMobile(et_phone.getText().toString().trim())) {
                    firstInPhone = et_phone.getText().toString().trim();
                } else {
                    Toast.makeText(this, "请输入正确的手机号码后，获取验证码", Toast.LENGTH_SHORT).show();
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
            case R.id.login_btn:
                if (!checkNum.equals(et_register.getText().toString().trim())) {
                    Toast.makeText(getApplicationContext(), "您未获取验证码或验证码错误", Toast.LENGTH_LONG).show();
                    break;
                }
                if (System.currentTimeMillis() - startTimeMillions > 30 * 60 * 1000) {
                    Toast.makeText(getApplicationContext(), "验证码已失效，请重新获取", Toast.LENGTH_LONG).show();
                    et_register.setText("");
                    break;
                }
                if (InputUtil.isMobile(et_phone.getText().toString().trim())) {
                    nextInPhone = et_phone.getText().toString().trim();
                } else {
                    Toast.makeText(this, "手机号码格式不正确，请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!firstInPhone.equals(nextInPhone)) {
                    Toast.makeText(this, "您输入的手机号码已改变，请重新输入手机号或重新获取验证码", Toast.LENGTH_LONG).show();
                } else {
                    //requestQueryUser();
                    requestLoginToken(firstInPhone, Utils.getIMEIDeviceId(getApplicationContext()));
                }
                break;
            default:
                break;
        }
    }

    private void requestLoginToken(String mobile, String deviceId){

    }

    private void requestQueryUser(){
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
}
