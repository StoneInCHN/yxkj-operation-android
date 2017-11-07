package com.yxkj.deliveryman.http;

import com.google.gson.Gson;
import com.yxkj.deliveryman.base.BaseEntity;
import com.yxkj.deliveryman.bean.CommitSupRecordsBean;
import com.yxkj.deliveryman.bean.response.AllSupContainerGoodsBean;
import com.yxkj.deliveryman.bean.response.ControllerVolume;
import com.yxkj.deliveryman.bean.response.GetCodeBean;
import com.yxkj.deliveryman.bean.response.GoodsCategoryBean;
import com.yxkj.deliveryman.bean.response.LoginBean;
import com.yxkj.deliveryman.bean.response.MessageBean;
import com.yxkj.deliveryman.bean.response.MessageDetailBean;
import com.yxkj.deliveryman.bean.response.NullBean;
import com.yxkj.deliveryman.bean.response.PublicKeyBean;
import com.yxkj.deliveryman.bean.response.SceneListBean;
import com.yxkj.deliveryman.bean.response.SupRecordBean;
import com.yxkj.deliveryman.bean.response.SupRecordDetailBean;
import com.yxkj.deliveryman.bean.response.WaitSupContainerGoodsBean;
import com.yxkj.deliveryman.bean.response.WaitSupGoodsDetailBean;
import com.yxkj.deliveryman.bean.response.WaitSupGoodsListBean;
import com.yxkj.deliveryman.bean.response.WaitSupStateBean;
import com.yxkj.deliveryman.sharepreference.SharePrefreceHelper;
import com.yxkj.deliveryman.sharepreference.SharedKey;
import com.yxkj.deliveryman.util.LogUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 *  项目名：  yxkj-controller-app
 *  包名：    com.yxkj.controller.http
 *  文件名:   HttpApi
 *  创建者:   hhe
 *  创建时间:  2017/10/11 9:53
 *  描述：    网络请求集合
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
     * 通用请求封装
     */
    public static <T> void toSubscribe(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 获取公钥
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

    /**
     * 获取验证码
     *
     * @param cellPhoneNum
     * @param type         验证码类型，login或resetPwd
     * @return
     */
    public Observable<BaseEntity<GetCodeBean>> getVerificationCode(String cellPhoneNum, String type) {
        Map<String, String> req = new HashMap<>();
        req.put("cellPhoneNum", cellPhoneNum);
        req.put("type", type);
        return RetrofitFactory.getInstance().getVerificationCode(req);
    }

    /**
     * 验证码登录
     *
     * @param cellPhoneNum
     * @param verificationCode
     * @return
     */
    public Observable<BaseEntity<LoginBean>> loginByVft(String cellPhoneNum, String verificationCode) {
        Map<String, String> req = new HashMap<>();
        req.put("cellPhoneNum", cellPhoneNum);
        req.put("verificationCode", verificationCode);
        return RetrofitFactory.getInstance().loginByVft(req);
    }

    /**
     * 忘记密码验证验证码
     *
     * @param cellPhoneNum
     * @param verificationCode
     * @return
     */
    public Observable<BaseEntity<NullBean>> forgetPwdVft(String cellPhoneNum, String verificationCode) {
        Map<String, String> req = new HashMap<>();
        req.put("cellPhoneNum", cellPhoneNum);
        req.put("verificationCode", verificationCode);
        return RetrofitFactory.getInstance().forgetPwdVft(req);
    }

    /**
     * 重置密码
     *
     * @param cellPhoneNum
     * @param newPwd
     * @return
     */
    public Observable<BaseEntity<LoginBean>> resetPwd(String cellPhoneNum, String newPwd) {
        Map<String, String> req = new HashMap<>();
        req.put("cellPhoneNum", cellPhoneNum);
        req.put("newPwd", newPwd);
        return RetrofitFactory.getInstance().resetPwd(req);
    }

    /**
     * 修改密码
     *
     * @param cellPhoneNum
     * @param oldPwd
     * @param newPwd
     * @return
     */
    public Observable<BaseEntity<NullBean>> updatePwd(String userId, String cellPhoneNum, String oldPwd, String newPwd) {
        Map<String, String> req = new HashMap<>();
        req.put("userId", userId);
        req.put("cellPhoneNum", cellPhoneNum);
        req.put("oldPwd", oldPwd);
        req.put("newPwd", newPwd);
        return RetrofitFactory.getInstance().updatePwd(req);
    }

    /**
     * 获取货柜待补情况
     *
     * @param userId
     * @param pageSize
     * @param pageSize
     * @return
     */
    public Observable<BaseEntity<WaitSupStateBean>> getWaitSupplyState(String userId, String pageNo, String pageSize) {
        Map<String, String> req = new HashMap<>();
        req.put("userId", userId);
        req.put("pageNo", pageNo);
        req.put("pageSize", pageSize);
        String token = SharePrefreceHelper.getInstance().getString(SharedKey.TOKEN);
        LogUtil.i("token", token);
        return RetrofitFactory.getInstance().getWaitSupplyState(req);
    }

    /**
     * 获取待补优享空间
     *
     * @param userId
     * @return
     */
    public Observable<BaseEntity<SceneListBean>> getWaitSupplySceneList(String userId) {
        Map<String, String> req = new HashMap<>();
        req.put("userId", userId);
        return RetrofitFactory.getInstance().getWaitSupplySceneList(req);
    }

    /**
     * 获取待补商品类别列表
     *
     * @param userId
     * @return
     */
    public Observable<BaseEntity<GoodsCategoryBean>> getWaitSupplyGoodsCategoryList(String userId) {
        Map<String, String> req = new HashMap<>();
        req.put("userId", userId);
        return RetrofitFactory.getInstance().getWaitSupplyGoodsCategoryList(req);
    }

    /**
     * 根据类别获取待补商品清单
     *
     * @param userId
     * @return
     */
    public Observable<BaseEntity<WaitSupGoodsListBean>> getWaitSupplyGoodsByCategory(String userId, String sceneSn, String cateId, String pageNo, String pageSize) {
        Map<String, String> req = new HashMap<>();
        req.put("userId", userId);
        req.put("sceneSn", sceneSn);
        req.put("cateId", cateId);
        req.put("pageNo", pageNo);
        req.put("pageSize", pageSize);
        return RetrofitFactory.getInstance().getWaitSupplyGoodsList(req);
    }

    /**
     * 获取待补商品详情
     *
     * @param userId
     * @return
     */
    public Observable<BaseEntity<WaitSupGoodsDetailBean>> getWaitSupplyGoodsDetails(String userId, String goodsSn) {
        Map<String, String> req = new HashMap<>();
        req.put("userId", userId);
        req.put("goodsSn", goodsSn);
        return RetrofitFactory.getInstance().getWaitSupplyGoodsDetails(req);
    }

    /**
     * 获取货柜待补商品
     *
     * @param userId
     * @return
     */
    public Observable<BaseEntity<WaitSupContainerGoodsBean>> getWaitSupplyContainerGoodsList(String userId, String cntrId, String pageNo, String pageSize) {
        Map<String, String> req = new HashMap<>();
        req.put("userId", userId);
        req.put("cntrId", cntrId);
        req.put("pageNo", pageNo);
        req.put("pageSize", pageSize);
        return RetrofitFactory.getInstance().getWaitSupplyContainerGoodsList(req);
    }

    /**
     * 获取货柜待补商品
     *
     * @param userId
     * @return
     */
    public Observable<BaseEntity<AllSupContainerGoodsBean>> getAllSupContainerGoodsList(String userId, String cntrId, String pageNo, String pageSize) {
        Map<String, String> req = new HashMap<>();
        req.put("userId", userId);
        req.put("cntrId", cntrId);
        req.put("pageNo", pageNo);
        req.put("pageSize", pageSize);
        return RetrofitFactory.getInstance().getContainerGoodsList(req);
    }

    /**
     * 提交补货记录
     *
     * @return
     */
    public Observable<BaseEntity<NullBean>> commitSupplementRecord(CommitSupRecordsBean bean) {
        Gson gson = new Gson();
        String beanString = gson.toJson(bean, CommitSupRecordsBean.class);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), beanString);
        return RetrofitFactory.getInstance().commitSupplementRecord(body);
    }

    /**
     * 上传补货照片
     *
     * @param userId
     * @param cntrId
     * @param file
     * @return
     */
    public Observable<BaseEntity<NullBean>> uploadSupplementPic(String userId, String cntrId, File file) {
        Map<String, String> req = new HashMap<>();
        req.put("userId", userId);
        req.put("cntrId", cntrId);

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("suppPic", file.getName(), requestFile);

        return RetrofitFactory.getInstance().uploadSupplementPic(req, filePart);
    }

    /**
     * 完成补货
     *
     * @param userId
     * @param sceneSn 优享空间编号
     * @return
     */
    public Observable<BaseEntity<SceneListBean.GroupsBean>> finishSupplyGoods(String userId, String sceneSn) {
        Map<String, String> req = new HashMap<>();
        req.put("userId", userId);
        req.put("sceneSn", sceneSn);
        return RetrofitFactory.getInstance().finishSupplyGoods(req);
    }

    /**
     * 查看总补货记录
     *
     * @param userId
     * @return
     */
    public Observable<BaseEntity<SupRecordBean>> getSupplementSumRecord(String userId, String pageNo, String pageSize) {
        Map<String, String> req = new HashMap<>();
        req.put("userId", userId);
        req.put("pageNo", pageNo);
        req.put("pageSize", pageSize);
        return RetrofitFactory.getInstance().getSupplementSumRecord(req);
    }

    /**
     * 查看补货记录详情
     *
     * @param userId
     * @return
     */
    public Observable<BaseEntity<SupRecordDetailBean>> getSupplementRecordDetails(String userId, String sceneSn) {
        Map<String, String> req = new HashMap<>();
        req.put("userId", userId);
        req.put("sceneSn", sceneSn);
        return RetrofitFactory.getInstance().getSupplementRecordDetails(req);
    }

    /**
     * 查看是否还有未补货完成的优享空间
     *
     * @param userId
     * @return
     */
    public Observable<BaseEntity<SceneListBean.GroupsBean>> startSupplyGoods(String userId, String sceneSn) {
        Map<String, String> req = new HashMap<>();
        req.put("userId", userId);
        req.put("sceneSn", sceneSn);
        return RetrofitFactory.getInstance().startSupplyGoods(req);
    }

    /**
     * 查看消息
     *
     * @param userId
     * @return
     */
    public Observable<BaseEntity<MessageBean>> getMsg(String userId) {
        Map<String, String> req = new HashMap<>();
        req.put("userId", userId);
        return RetrofitFactory.getInstance().getMsg(req);
    }

    /**
     * 查看消息详情
     *
     * @param userId
     * @return
     */
    public Observable<BaseEntity<MessageDetailBean>> getMsgDetails(String userId, String type) {
        Map<String, String> req = new HashMap<>();
        req.put("userId", userId);
        req.put("msgType", type);
        return RetrofitFactory.getInstance().getMsgDetails(req);
    }

    /**
     * 出货测试
     *
     * @return
     */
    public Observable<BaseEntity<NullBean>> testDeliver(String channelId) {
        Map<String, String> req = new HashMap<>();
        req.put("channelId", channelId);
        return RetrofitFactory.getInstance().testDeliver(req);
    }

    /**
     * 调节音量
     *
     * @return
     */
    public Observable<BaseEntity<NullBean>> updateAudioVolume(String deviceNo, String volume) {
        Map<String, String> req = new HashMap<>();
        req.put("deviceNo", deviceNo);
        req.put("volume", volume);
        return RetrofitFactory.getInstance().updateAudioVolume(req);
    }

    /**
     * 重启设备
     *
     * @return
     */
    public Observable<BaseEntity<NullBean>> rebootSystem(String deviceNo) {
        Map<String, String> req = new HashMap<>();
        req.put("deviceNo", deviceNo);
        return RetrofitFactory.getInstance().rebootSystem(req);
    }

    /**
     * 获取当前设备的音量
     *
     * @return
     */
    public Observable<BaseEntity<ControllerVolume>> getCurrentVolume(String deviceNo) {
        Map<String, String> req = new HashMap<>();
        req.put("deviceNo", deviceNo);
        return RetrofitFactory.getInstance().getCurrentVolume(req);
    }


}

