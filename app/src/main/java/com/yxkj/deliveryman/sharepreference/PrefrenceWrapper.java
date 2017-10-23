package com.yxkj.deliveryman.sharepreference;

import android.content.Context;
import android.content.SharedPreferences;

/**
 *
 */

public class PrefrenceWrapper {
    private SharedPreferences sharedPreferences;

    private static final String SP_NAME = "share_operation_info";
    private SharedPreferences.Editor mEditor;

    protected PrefrenceWrapper(Context context) {
        sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);//Context.MODE_MULTI_PROCESS支持跨进程访问
        mEditor = sharedPreferences.edit();
    }

    public void setString(String key, String value) {
        mEditor.putString(key, value).apply();
    }

    public String getString(String key, String defaultVaule) {
        return sharedPreferences.getString(key, defaultVaule);
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, "");
    }

    public void setBoolean(String key, boolean vaule) {
        mEditor.putBoolean(key, vaule).apply();
    }

    public boolean getBoolean(String key, boolean defaultVaule) {
        return sharedPreferences.getBoolean(key, defaultVaule);
    }

    public void clear() {
        mEditor.clear();
    }
}
