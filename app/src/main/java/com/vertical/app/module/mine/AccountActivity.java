package com.vertical.app.module.mine;

import com.vertical.annotation.autolayout.AutoLayout;
import com.vertical.app.R;
import com.vertical.app.base.BaseCatActivity;
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

    @Override
    public void onNavigationRightClicked() {

    }

    @OnClick(R.id.mine_operateKind)
    void clickOperateKind() {
        new ActionSheetDialog(this)
                .builder()
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .addSheetItem("男装", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {

                            }
                        })
                .addSheetItem("女装", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {

                            }
                        })
                .addSheetItem("童装", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {

                            }
                        })
                .addSheetItem("母婴用品", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {

                            }
                        })
                .addSheetItem("其他", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {

                            }
                        }).show();
    }

    @OnClick(R.id.mine_operateTitle)
    void clickOperateTitle() {
        new ActionSheetDialog(this)
                .builder()
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .addSheetItem("导购", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {

                            }
                        })
                .addSheetItem("店长", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {

                            }
                        })
                .addSheetItem("督导", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {

                            }
                        })
                .addSheetItem("区域经理", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {

                            }
                        })
                .addSheetItem("老板", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {

                            }
                        }).show();
    }

}
