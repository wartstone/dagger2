package com.vertical.app.module.home;

import com.vertical.app.R;

import org.w3c.dom.ProcessingInstruction;

/**
 * Created by ls on 10/27/17.
 */

public enum HomeMemberMenu {
    MEMBERLIST("会员列表", R.drawable.profile_icon_authorization),
    SALESBACK("销售回访", R.drawable.mine_money),
    OPERATION("生日祝福", R.drawable.operationanalysis),
    HOLIDAY("家庭节日", R.drawable.icon_mine_store),
    OPERATION2("节日祝福", R.drawable.icon_mine_service),
    SETTING("休眠激活", R.drawable.icon_mine_setting);

    private String mTitle;
    private int mDrawable;

    HomeMemberMenu(String title, int drawable) {
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
