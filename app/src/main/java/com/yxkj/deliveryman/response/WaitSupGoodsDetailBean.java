package com.yxkj.deliveryman.response;

import java.util.List;

/*
 *  @项目名：  yxkj-operation-android 
 *  @包名：    com.yxkj.deliveryman.response
 *  @文件名:   WaitSupGoodsDetailBean
 *  @创建者:   hhe
 *  @创建时间:  2017/10/24 15:32
 *  @描述：    待补商品详情
 */
public class WaitSupGoodsDetailBean {


    /**
     * goodsSn : 004
     * goodsName : 康师傅红烧牛肉面250ml
     * sceneCountList : [{"waitSupplyCount":4,"sceneName":"香年广场T1"},{"waitSupplyCount":6,"sceneName":"香年广场T2"},{"waitSupplyCount":4,"sceneName":"香年广场T2"},{"waitSupplyCount":6,"sceneName":"香年广场T3"}]
     * sumCount : 20
     */

    public String goodsSn;
    public String goodsName;
    public int sumCount;
    public List<SceneCountListBean> sceneCountList;

    public static class SceneCountListBean {
        /**
         * waitSupplyCount : 4
         * sceneName : 香年广场T1
         */

        public int waitSupplyCount;
        public String sceneName;
    }
}
