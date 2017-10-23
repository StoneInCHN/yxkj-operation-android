package com.yxkj.deliveryman.sharepreference;

import android.content.Context;

import com.yxkj.deliveryman.application.MyApplication;

/**
 *
 */

public class SharePrefreceHelper extends PrefrenceWrapper {

    private static SharePrefreceHelper sharePrefreceHelper;

    private SharePrefreceHelper(Context context) {
        super(context);
    }

    public static SharePrefreceHelper getInstance() {
        if (sharePrefreceHelper == null)
            sharePrefreceHelper = new SharePrefreceHelper(MyApplication.getAppContext());
        return sharePrefreceHelper;
    }

    /**
     * 是否是第一次启动
     *
     * @param value
     */
    public void setFirstBoolean(boolean value) {
        setBoolean(SharedKey.IS_FIRST_START, value);
    }

    public boolean getFirstBoolean(boolean defaultValue) {
        return getBoolean("is_first_start", defaultValue);
    }
}
