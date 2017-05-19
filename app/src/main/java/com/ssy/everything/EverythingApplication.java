package com.ssy.everything;

import android.app.Application;

/**
 * Created by ssy on 2017/5/19.
 */

public class EverythingApplication extends Application {
    private static final String TAG = "EverythingApplication";
    private static EverythingApplication instance;
    public static boolean hasLogin;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static EverythingApplication getInstance() {
        return instance;
    }
}
