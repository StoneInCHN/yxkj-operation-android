package com.yxkj.deliveryman.response;

import java.util.List;

/*
 *  @项目名：  yxkj-operation-android 
 *  @包名：    com.yxkj.deliveryman.response
 *  @文件名:   WaitSupContainerGoodsBean
 *  @创建者:   hhe
 *  @创建时间:  2017/10/24 15:34
 *  @描述：    货柜待补商品
 */
public class WaitSupContainerGoodsBean {

    public List<GroupsBean> groups;

    public static class GroupsBean {
        /**
         * channelSn : D
         * goodsSn : 005
         * goodsName : 可口可乐250ml
         * goodsPic : 65fgs2fgd.jpg
         * waitSupplyCount : 2
         */

        public String channelSn;
        public String goodsSn;
        public String goodsName;
        public String goodsPic;
        public int waitSupplyCount;
    }
}