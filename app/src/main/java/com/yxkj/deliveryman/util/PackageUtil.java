package com.yxkj.deliveryman.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.List;

/**
 * 用于判断某个程序是否安装
 */

public class PackageUtil {

    private static PackageUtil mInstance = null;

    private static Context mContext = null;

    private PackageUtil(){

    }

    public static PackageUtil getInstance(){
        if(mInstance == null){
            mInstance = new PackageUtil();
        }
        return mInstance;
    }

    public static void init(Context context){
        mInstance.mContext = context;
    }

    /**
     * 判断app是否安装
     * @param appPackage 程序包各
     * @return
     */
    public boolean isAppInstalled(String appPackage){
        if(mContext == null){
            throw new RuntimeException("Please initialize PackageUtil");
        }
        PackageManager pManager = mContext.getPackageManager();
        List<PackageInfo> packageInfoList = pManager.getInstalledPackages(0);//获取所有已安装程序包信息
        for(PackageInfo packageInfo : packageInfoList){
            if(packageInfo.packageName.equals(appPackage)){
                return true;
            }
        }
        return false;
    }

}
