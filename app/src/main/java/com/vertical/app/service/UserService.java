package com.vertical.app.service;

import android.content.Context;

import javax.inject.Inject;

/**
 * Created by ls on 10/13/17.
 */

public class UserService {
    private final String TAG = "UserService";
    private static UserService mInstance;

    @Inject
    Context mContext;

    private UserService() {
    }

    public static UserService getInstance() {
        synchronized (UserService.class) {
            if (mInstance == null) {
                mInstance = new UserService();
            }
        }
        return mInstance;
    }
}
