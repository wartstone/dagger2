package com.vertical.app.module.member;

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
 * Created by ls on 10/31/17.
 */

@AutoLayout(layout = R.layout.activity_createmember, title = "新增会员", title_right = "保存")
public class CreateMemberActivity extends BaseCatActivity {
    @BindView(R.id.name)
    CatEditText mName;
    @BindView(R.id.mobileNo)
    CatEditText mMobileNo;
    @BindView(R.id.memberNo)
    CatEditText mMemberNo;
    @BindView(R.id.sexType)
    CatTextView mSexType;
    @BindView(R.id.cardType)
    CatTextView mCardType;
    @BindView(R.id.birthday)
    CatTextView mBirthday;
    @BindView(R.id.recommendPerson)
    CatTextView mRecommendPerson;
    @BindView(R.id.remark)
    CatEditText mRemark;
    @BindView(R.id.area)
    CatTextView mArea;

    private final String[] mSexTypes = new String[] {"男", "女"};
    private final String[] mCardTypes = new String[]{"会员卡", "优惠卡"};

    @Override
    public void onNavigationRightClicked() {
        finish();
    }

    @OnClick(R.id.sexType)
    void clickSexType() {
        new ActionSheetDialog(this)
                .builder()
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .addSheetItems(mSexTypes, ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        mSexType.setCenterTextString(mSexTypes[which]);
                    }
                }).show();
    }

    @OnClick(R.id.cardType)
    void clickCardType() {
        new ActionSheetDialog(this)
                .builder()
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .addSheetItems(mCardTypes, ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        mCardType.setCenterTextString(mCardTypes[which]);
                    }
                }).show();
    }
}