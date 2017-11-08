package com.vertical.app.module.work.bizanalysis;

import com.vertical.app.R;

/**
 * Created by ls on 10/27/17.
 */

public enum BizAnalysisMenu {
    SALESVISIT("销售回访", R.drawable.icon_mine_service),
    SALESQUERY("销售查询", R.drawable.icon_aboutus_info);

    private String mTitle;
    private int mDrawable;

    BizAnalysisMenu(String title, int drawable) {
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
