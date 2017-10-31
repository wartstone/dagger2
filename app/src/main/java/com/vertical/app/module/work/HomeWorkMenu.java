package com.vertical.app.module.work;

import com.vertical.app.R;

/**
 * Created by ls on 10/27/17.
 */

public enum HomeWorkMenu {
    TRADE("交易管理", R.drawable.transactionmanagement),
    NOTICE("公告管理", R.drawable.bulletinmanagement),
    GOODS("商品管理", R.drawable.goodsmanagement),
    OPERATION_MANAGEMENT("运营管理", R.drawable.operationmana),
    OPERATION_ANALYSIS("经营分析", R.drawable.operationanalysis),
    MEMBERMANAGEMENT("会员管理", R.drawable.transactionmanagement);

    private String mTitle;
    private int mDrawable;

    HomeWorkMenu(String title, int drawable) {
        this.mTitle = title;
        this.mDrawable = drawable;
    }

    public String getTitle() {
        return mTitle;
    }

    public int getDrawable() {
        return mDrawable;
    }
}
