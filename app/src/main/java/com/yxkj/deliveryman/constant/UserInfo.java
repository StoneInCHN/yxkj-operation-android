package com.yxkj.deliveryman.constant;

import com.yxkj.deliveryman.sharepreference.SharePrefreceHelper;
import com.yxkj.deliveryman.sharepreference.SharedKey;

/*
 *  @项目名：  yxkj-operation-android 
 *  @包名：    com.yxkj.deliveryman.constant
 *  @文件名:   UserInfo
 *  @创建者:   hhe
 *  @创建时间:  2017/10/30 12:52
 *  @描述：    存储个人信息
 */
public class UserInfo {

    public static String USER_ID = SharePrefreceHelper.getInstance().getString(SharedKey.USER_ID);

}
