package com.vertical.app.module.work.routine;

import com.vertical.app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ls on 10/27/17.
 */

public enum RoutineManagementMenu {
    DAILYPUSH("日常跟进", R.drawable.icon_mine_service);

    private String mTitle;
    private int mDrawable;

    RoutineManagementMenu(String title, int drawable) {
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
