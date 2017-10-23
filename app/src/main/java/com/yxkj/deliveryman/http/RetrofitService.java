package com.yxkj.deliveryman.http;


import com.yxkj.deliveryman.base.BaseEntity;
import com.yxkj.deliveryman.response.LoginBean;
import com.yxkj.deliveryman.response.PublicKeyBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * For Retrofit
 */
public interface RetrofitService {

    /**
     * 获取公钥
     */
    @GET("keeper/getPublicKey")
    Observable<BaseEntity<PublicKeyBean>> getPublicKey();

    /**
     * 用户密码登录
     */
    @POST("keeper/loginByPwd")
    Observable<BaseEntity<LoginBean>> loginByPwd(@Body Map<String, String> reqest);


}
