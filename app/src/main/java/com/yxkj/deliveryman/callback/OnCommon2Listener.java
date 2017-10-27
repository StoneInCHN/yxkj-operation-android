package com.yxkj.deliveryman.callback;

/*
 *  @项目名：  yxkj-operation-android 
 *  @包名：    com.yxkj.deliveryman.callback
 *  @文件名:   OnCommon2Listener
 *  @创建者:   hhe
 *  @创建时间:  2017/10/27 20:17
 *  @描述：    两个方法的通用接口
 */
public interface OnCommon2Listener<T1, T2> {
    void onCommon1(T1 t1);

    void onCommon2(T2 t2);
}
