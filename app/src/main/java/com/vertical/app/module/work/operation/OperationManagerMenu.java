package com.vertical.app.module.work.operation;

import com.vertical.app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ls on 10/27/17.
 */

public enum OperationManagerMenu {
    BIRTHDAYMGR("生日管理", R.drawable.icon_mine_service),
    HOLIDAYMGR("节日管理", R.drawable.icon_mine_setting),
    REVOKE("休眠激活", R.drawable.icon_mine_service);

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

    public static List<OperationManagerMenu> getDateManagementMenu() {
        List<OperationManagerMenu> list = new ArrayList<>();
        list.add(BIRTHDAYMGR);
        list.add(HOLIDAYMGR);
        return list;
    }

    public static List<OperationManagerMenu> getFuncManagementMenu() {
        List<OperationManagerMenu> list = new ArrayList<>();
        list.add(REVOKE);
        return list;
    }
}
