package com.vertical.app.module.member;

import com.vertical.annotation.AutoWire;
import com.vertical.annotation.autolayout.AutoLayout;
import com.vertical.app.R;
import com.vertical.app.base.BaseCatActivity;
import com.vertical.app.bean.MemberBean;
import com.vertical.app.common.widget.ActionSheetDialog;
import com.vertical.app.common.widget.CatEditText;
import com.vertical.app.common.widget.CatTextView;
import com.vertical.app.module.transaction.CreateOrderContract;

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
        MemberBean memberBean = new MemberBean();
        memberBean.user_id = 1;
        memberBean.name = "one";
        memberBean.gender = true;
        memberBean.birthdate = "1989/10/01";
        memberBean.phone = "18201878233";
        memberBean.card_no = "00000000001";
        memberBean.card_type_id = 1;
        memberBean.recommend_by = 1;
        memberBean.avatarUrl = "";
        memberBean.create_by = 1;
        memberBean.comments = "第一个会员";
        memberBean.create_date = "2017/11/08";

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
        finish();
    }
}