package com.vertical.app.common.util;

import android.util.Log;

/**
 * Created by ls on 11/22/17.
 */

public class Trace {
    private static final String LOG_KEY = "Vertical";

    public static final boolean DEBUG_MODE = true;

    public static void v(String msg) {
        if (DEBUG_MODE)
            Log.v(LOG_KEY, msg);
    }

    public static void d(String msg) {
        if (DEBUG_MODE)
            Log.d(LOG_KEY, msg);
    }

    public static void i(String msg) {
        if (DEBUG_MODE)
            Log.i(LOG_KEY, msg);
    }

    public static void w(String msg) {
        if (DEBUG_MODE)
            Log.w(LOG_KEY, msg);
    }

    public static void e(String msg) {
        if (DEBUG_MODE)
            Log.e(LOG_KEY, msg);
    }

    public static void v(String tag, String msg) {
        if (DEBUG_MODE)
            Log.v(LOG_KEY+'['+tag+']', msg);
    }

    public static void d(String tag, String msg) {
        if (DEBUG_MODE)
            Log.d(LOG_KEY+'['+tag+']', msg);
    }

    public static void i(String tag, String msg) {
        if (DEBUG_MODE)
            Log.i(LOG_KEY+'['+tag+']', msg);
    }

    public static void w(String tag, String msg) {
        if (DEBUG_MODE)
            Log.w(LOG_KEY+'['+tag+']', msg);
    }

    public static void e(String tag, String msg) {
        if (DEBUG_MODE)
            Log.e(LOG_KEY+'['+tag+']', msg);
    }
}
