package com.vertical.app.module.mine;

import android.widget.Toast;

import com.vertical.annotation.autolayout.AutoLayout;
import com.vertical.app.R;
import com.vertical.app.base.BaseCatActivity;
import com.vertical.app.common.util.Constant;
import com.vertical.app.common.util.PreferenceHelper;
import com.vertical.app.common.widget.ActionSheetDialog;
import com.vertical.app.common.widget.CatEditText;
import com.vertical.app.common.widget.CatTextView;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by ls on 10/30/17.
 */

@AutoLayout(layout = R.layout.activity_account, title = "我的账户", title_right = "保存")
public class AccountActivity extends BaseCatActivity {
    @BindView(R.id.mine_nickname)
    CatEditText mNickName;
    @BindView(R.id.mine_sign)
    CatEditText mSign;
    @BindView(R.id.mine_operateKind)
    CatTextView mOperateKind;
    @BindView(R.id.mine_operateName)
    CatEditText mOperateName;
    @BindView(R.id.mine_operateTitle)
    CatTextView mOperateTitle;

    private final String[] mOperateKinds = new String[] {"男装", "女装", "童装", "母婴用品", "其他"};
    private final String[] mOperateTitles = new String[]{"导购", "店长", "督导", "区域经理", "老板"};

    @Override
    protected void initEventAndData() {
        String nickName = PreferenceHelper.getInstance(this).getString(Constant.KEY_ACCOUNT_NICKNAME, "");
        String sign = PreferenceHelper.getInstance(this).getString(Constant.KEY_ACCOUNT_SIGN, "");
        String operateKind = PreferenceHelper.getInstance(this).getString(Constant.KEY_ACCOUNT_OPERATEKIND, mOperateKinds[0]);
        String operateName = PreferenceHelper.getInstance(this).getString(Constant.KEY_ACCOUNT_OPERATENAME, "");
        String operateTitle = PreferenceHelper.getInstance(this).getString(Constant.KEY_ACCOUNT_OPERATETITLE, mOperateTitles[0]);

        mNickName.setCenterTextString(nickName);
        mSign.setCenterTextString(sign);
        mOperateKind.setCenterTextString(operateKind);
        mOperateName.setCenterTextString(operateName);
        mOperateTitle.setCenterTextString(operateTitle);
    }

    @Override
    public void onNavigationRightClicked() {
        PreferenceHelper.getInstance(this).saveString(Constant.KEY_ACCOUNT_NICKNAME, mNickName.getCenterTextString());
        PreferenceHelper.getInstance(this).saveString(Constant.KEY_ACCOUNT_SIGN, mSign.getCenterTextString());
        PreferenceHelper.getInstance(this).saveString(Constant.KEY_ACCOUNT_OPERATEKIND, mOperateKind.getCenterTextString());
        PreferenceHelper.getInstance(this).saveString(Constant.KEY_ACCOUNT_OPERATENAME, mOperateName.getCenterTextString());
        PreferenceHelper.getInstance(this).saveString(Constant.KEY_ACCOUNT_OPERATETITLE, mOperateTitle.getCenterTextString());

        finish();
    }

    @OnClick(R.id.mine_operateKind)
    void clickOperateKind() {
        new ActionSheetDialog(this)
                .builder()
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .addSheetItems(mOperateKinds, ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        mOperateKind.setCenterTextString(mOperateKinds[which]);
                    }
                }).show();
    }

    @OnClick(R.id.mine_operateTitle)
    void clickOperateTitle() {
        new ActionSheetDialog(this)
                .builder()
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .addSheetItems(mOperateTitles, ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        mOperateTitle.setCenterTextString(mOperateTitles[which]);
                    }
                }).show();
    }

}
