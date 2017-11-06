package com.yxkj.deliveryman.application;

import android.app.Application;
import android.content.Context;

import cn.jpush.android.api.JPushInterface;

/**
 *
 * @author hhe
 */

public class MyApplication extends Application {
    private static MyApplication myApplication;

    public static MyApplication getMyApplication() {
        return myApplication;
    }

    public static Context getAppContext() {
        return getMyApplication().getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;

        initJpush();

    }

    private void initJpush() {
        //先后顺序不要变,避免出现部分日志没打印的情况
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

}
