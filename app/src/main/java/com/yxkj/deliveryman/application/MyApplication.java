package com.yxkj.deliveryman.application;

import android.app.Application;
import android.content.Context;

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

    public static Context getAppContext() {
        return getMyApplication().getApplicationContext();
    }
}
