package com.vertical.api;

import android.app.Activity;
import android.view.View;

/**
 * Created by katedshan on 17/8/5.
 */

public class LSActivityViewFinder implements LSViewFinder {
    @Override
    public View findView(Object object, int id) {
        return ((Activity) object).findViewById(id);
    }
}
