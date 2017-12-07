package com.vertical.app.module.mine;

import android.widget.EditText;
import android.widget.Toast;

import com.vertical.annotation.autolayout.AutoLayout;
import com.vertical.app.R;
import com.vertical.app.base.BaseCatActivity;
import com.vertical.app.common.Constant;
import com.vertical.app.common.util.InputUtil;
import com.vertical.app.common.util.PreferenceHelper;

import butterknife.BindView;

/**
 * Created by ls on 10/31/17.
 */

@AutoLayout(layout = R.layout.activity_signature, title = "短信模板", title_right = "保存")
public class SignatureActivity extends BaseCatActivity {
    @BindView(R.id.et_signature)
    EditText mSignature;

    @Override
    protected void initEventAndData() {
        String nickName = PreferenceHelper.getInstance(this).getString(Constant.KEY_ACCOUNT_SIGNATURE, "");

        mSignature.setText(nickName);
    }

    @Override
    public void onNavigationRightClicked() {
        String signature = mSignature.getText().toString();
        try {
            InputUtil.assertMulti("短信模板", signature, 150, InputUtil.AssertType.NOT_NULL, InputUtil.AssertType.MAXIMUM_COUNT);
        } catch (InputUtil.InvalidInputException e) {
            Toast.makeText(SignatureActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }

        PreferenceHelper.getInstance(this).saveString(Constant.KEY_ACCOUNT_SIGNATURE, signature);

        finish();
    }
}