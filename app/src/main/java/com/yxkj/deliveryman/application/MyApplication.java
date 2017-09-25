package com.yxkj.deliveryman.application;

import android.app.Application;

/**
 *
 */

public class MyApplication extends Application {
    private static MyApplication myApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
    }

    public static MyApplication getMyApplication() {
        return myApplication;
    }
}
