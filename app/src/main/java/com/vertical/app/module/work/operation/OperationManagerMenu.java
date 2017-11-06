package com.vertical.app.module.work.operation;

import com.vertical.app.R;

/**
 * Created by ls on 10/27/17.
 */

public enum OperationManagerMenu {
    BIRTHDAYMGR("生日管理", R.drawable.icon_mine_service),
    HOLIDAYMGR("节日管理", R.drawable.icon_mine_setting);

    private String mTitle;
    private int mDrawable;

    OperationManagerMenu(String title, int drawable) {
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
