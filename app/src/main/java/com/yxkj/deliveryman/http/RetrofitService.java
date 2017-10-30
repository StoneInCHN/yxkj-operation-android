package com.yxkj.deliveryman.http;


import com.yxkj.deliveryman.base.BaseEntity;
import com.yxkj.deliveryman.bean.response.GetCodeBean;
import com.yxkj.deliveryman.bean.response.GoodsCategoryBean;
import com.yxkj.deliveryman.bean.response.LoginBean;
import com.yxkj.deliveryman.bean.response.MessageBean;
import com.yxkj.deliveryman.bean.response.MessageDetailBean;
import com.yxkj.deliveryman.bean.response.NullBean;
import com.yxkj.deliveryman.bean.response.PublicKeyBean;
import com.yxkj.deliveryman.bean.response.SceneListBean;
import com.yxkj.deliveryman.bean.response.SupRecordBean;
import com.yxkj.deliveryman.bean.response.SupRecordDetail;
import com.yxkj.deliveryman.bean.response.WaitSupContainerGoodsBean;
import com.yxkj.deliveryman.bean.response.WaitSupGoodsDetailBean;
import com.yxkj.deliveryman.bean.response.WaitSupGoodsListBean;
import com.yxkj.deliveryman.bean.response.WaitSupStateBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

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

    /**
     * 获取验证码
     */
    @POST("keeper/getVerificationCode")
    Observable<BaseEntity<GetCodeBean>> getVerificationCode(@Body Map<String, String> reqest);

    /**
     * 根据验证码登录
     */
    @POST("keeper/loginByVft")
    Observable<BaseEntity<LoginBean>> loginByVft(@Body Map<String, String> reqest);

    /**
     * 忘记密码时，验证码身份验证
     */
    @POST("keeper/forgetPwd")
    Observable<BaseEntity<NullBean>> forgetPwdVft(@Body Map<String, String> reqest);

    /**
     * 重置密码
     */
    @POST("keeper/resetPwd")
    Observable<BaseEntity<NullBean>> resetPwd(@Body Map<String, String> reqest);

    /**
     * 修改密码
     */
    @POST("keeper/updatePwd")
    Observable<BaseEntity<NullBean>> updatePwd(@Body Map<String, String> reqest);

    /**
     * 获取货柜待补情况
     */
    @POST("keeper/getWaitSupplyState")
    Observable<BaseEntity<WaitSupStateBean>> getWaitSupplyState(@Body Map<String, String> request);

    /**
     * 获取待补优享空间
     */
    @POST("keeper/getWaitSupplySceneList")
    Observable<BaseEntity<SceneListBean>> getWaitSupplySceneList(@Body Map<String, String> request);

    /**
     * 获取待补商品类别列表
     */
    @POST("keeper/getWaitSupplyGoodsCategoryList")
    Observable<BaseEntity<GoodsCategoryBean>> getWaitSupplyGoodsCategoryList(@Body Map<String, String> request);

    /**
     * 获取待补商品清单
     */
    @POST("keeper/getWaitSupplyGoodsList")
    Observable<BaseEntity<WaitSupGoodsListBean>> getWaitSupplyGoodsList(@Body Map<String, String> request);

    /**
     * 获取待补商品详情
     */
    @POST("keeper/getWaitSupplyGoodsDetails")
    Observable<BaseEntity<WaitSupGoodsDetailBean>> getWaitSupplyGoodsDetails(@Body Map<String, String> request);

    /**
     * 获取货柜待补商品
     */
    @POST("keeper/getWaitSupplyContainerGoodsList")
    Observable<BaseEntity<WaitSupContainerGoodsBean>> getWaitSupplyContainerGoodsList(@Body Map<String, String> request);

    /**
     * 提交补货记录
     */
    @POST("keeper/commitSupplementRecord")
    Observable<BaseEntity<NullBean>> commitSupplementRecord(@Body Map<String, String> request);

    /**
     * 上传补货照片
     */
    @Multipart
    @POST("keeper/uploadSupplementPic")
    Observable<BaseEntity<NullBean>> uploadSupplementPic(@PartMap Map<String, String> request, @Part MultipartBody.Part file);

    /**
     * 完成补货
     */
    @POST("keeper/finishSupplyGoods")
    Observable<BaseEntity<SceneListBean.GroupsBean>> finishSupplyGoods(@Body Map<String, String> request);

    /**
     * 查看总补货记录
     */
    @POST("keeper/getSupplementSumRecord")
    Observable<BaseEntity<SupRecordBean>> getSupplementSumRecord(@Body Map<String, String> request);

    /**
     * 查看补货记录详情
     */
    @POST("keeper/getSupplementRecordDetails")
    Observable<BaseEntity<SupRecordDetail>> getSupplementRecordDetails(@Body Map<String, String> request);

    /**
     * 查看是否还有未补货完成的优享空间
     */
    @POST("keeper/startSupplyGoods")
    Observable<BaseEntity<SceneListBean.GroupsBean>> startSupplyGoods(@Body Map<String, String> request);

    /**
     * 查看消息
     */
    @POST("keeper/getMsg")
    Observable<BaseEntity<MessageBean>> getMsg(@Body Map<String, String> request);

    /**
     * 查看消息详情
     */
    @POST("keeper/getMsgDetails")
    Observable<BaseEntity<MessageDetailBean>> getMsgDetails(@Body Map<String, String> request);


}
