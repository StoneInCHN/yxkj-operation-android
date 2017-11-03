package com.yxkj.deliveryman.constant;

import android.os.Environment;

import java.io.File;

/**
 * 常量类
 */

public class Constants {
    //public static final String BASE_URL = "http://192.167.1.242:8080/";
    public static final String BASE_URL = "http://106.14.7.117:8080/";
    /* 图片保存路径 */
    public static final String PHOTO_URI = "/Deliveryman/Image";
    /*拍照的照片存储位置*/
    public static final File PHOTO_DIR = new File(Environment.getExternalStorageDirectory() + Constants.PHOTO_URI);
    /* 用来标识请求照相功能的activity*/
    public static final int CAMERA_WITH_DATA = 3021;
    /*用来标识请求相册的activity*/
    public static final int PHOTO_PICKED_WITH_DATA = 3022;
    /* 用跳转剪切*/
    public static final int CROP_BIG_PICTURE = 3024;

    /**
     * 登录等操作错误提示时间
     */
    public static final int ERROR_TIP_TIME = 1500;
}
