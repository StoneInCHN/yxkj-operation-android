package com.yxkj.deliveryman.http;

import com.yxkj.deliveryman.base.BaseEntity;
import com.yxkj.deliveryman.response.LoginBean;
import com.yxkj.deliveryman.response.PublicKeyBean;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/*
 *  @项目名：  yxkj-controller-app 
 *  @包名：    com.yxkj.controller.http
 *  @文件名:   HttpApi
 *  @创建者:   hhe
 *  @创建时间:  2017/10/11 9:53
 *  @描述：    网络请求集合
 */
public class HttpApi {
    private static HttpApi instance = new HttpApi();

    public static HttpApi getInstance() {
        return instance;
    }

    /**
     * 将数组转换成map，须保持两个数组个数一致
     *
     * @param keys
     * @param values
     * @return
     */
    private Map<String, String> generateMap(String[] keys, String[] values) {
        if (keys.length != values.length) {
            return null;
        }
        Map<String, String> map = new HashMap<>(keys.length);
        for (int i = 0; i < keys.length; i++) {
            map.put(keys[i], values[i]);
        }

        return map;
    }

    /**
     * 获取公钥
     *
     * @return
     */
    public Observable<BaseEntity<PublicKeyBean>> getPublicKey() {
        return RetrofitFactory.getInstance().getPublicKey();
    }

    /**
     * 用户密码登录
     *
     * @param cellPhoneNum
     * @param password
     * @return
     */
    public Observable<BaseEntity<LoginBean>> loginByPwd(String cellPhoneNum, String password) {
        Map<String, String> req = new HashMap<>();
        req.put("cellPhoneNum", cellPhoneNum);
        req.put("password", password);
        return RetrofitFactory.getInstance().loginByPwd(req);
    }


}

