package com.vertical.app.module.member;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.vertical.annotation.AutoWire;
import com.vertical.annotation.autolayout.AutoLayout;
import com.vertical.app.R;
import com.vertical.app.base.BaseCatActivity;
import com.vertical.app.bean.MemberBean;
import com.vertical.app.common.widget.ActionSheetDialog;
import com.vertical.app.common.widget.CatEditText;
import com.vertical.app.common.widget.CatTextView;
import com.vertical.app.module.transaction.CreateOrderContract;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ls on 10/31/17.
 */

@AutoWire(presenter = CreateMemberPresenter.class)
@AutoLayout(layout = R.layout.activity_createmember, title = "新增会员", title_right = "保存")
public class CreateMemberActivity extends BaseCatActivity<CreateMemberContract.Presenter> implements CreateMemberContract.View {
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


    public String avatarUrl;
    public int create_by;
    public String comments;
    public String create_date;

    @Override
    public void onNavigationRightClicked() {
        String name = mName.getCenterTextString();
        if(TextUtils.isEmpty(name)) {
            Toast.makeText(this, "会员名不应为空!", Toast.LENGTH_LONG).show();
            return;
        }

        MemberBean memberBean = new MemberBean();
        memberBean.user_id = 1;
        memberBean.name = name;
        memberBean.gender = 1;
        memberBean.birthday = "1989/10/01";
        memberBean.phone = "18201878233";
        memberBean.card_no = "00000000001";
        memberBean.card_type_id = 1;
        memberBean.recommend_by = 1;
        memberBean.avatar_picture_id = 1;
        memberBean.create_by = 1;
        memberBean.comments = "debug member";
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss.SSS");
        memberBean.create_date = format.format(new Timestamp(System.currentTimeMillis()));

        mPresenter.createMember(memberBean);
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

    @Override
    public void onMemberCreated(boolean result) {
        Toast.makeText(this, "创建会员成功!", Toast.LENGTH_LONG).show();
    }
}