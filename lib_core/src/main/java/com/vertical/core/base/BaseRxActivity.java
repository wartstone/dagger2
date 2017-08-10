package com.vertical.core.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.SupportActivity;

/**
 * Created by katedshan on 17/8/5.
 */

public abstract class BaseRxActivity extends SupportActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        autoInject();
    }

    protected abstract void autoInject();
}
