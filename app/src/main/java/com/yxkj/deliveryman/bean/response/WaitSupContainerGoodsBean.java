package com.yxkj.deliveryman.bean.response;

import java.util.List;

/*
 *  @项目名：  yxkj-operation-android 
 *  @包名：    com.yxkj.deliveryman.bean.response
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

        @Override
        public String toString() {
            return "GroupsBean{" +
                    "channelSn='" + channelSn + '\'' +
                    ", goodsSn='" + goodsSn + '\'' +
                    ", goodsName='" + goodsName + '\'' +
                    ", goodsPic='" + goodsPic + '\'' +
                    ", waitSupplyCount=" + waitSupplyCount +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "WaitSupContainerGoodsBean{" +
                "groups=" + groups +
                '}';
    }
}
