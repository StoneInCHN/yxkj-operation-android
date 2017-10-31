package com.yxkj.deliveryman.bean.response;

import java.util.List;

/*
 *  @项目名：  yxkj-operation-android 
 *  @包名：    com.yxkj.deliveryman.bean.response
 *  @文件名:   AllSupContainerGoodsBean
 *  @创建者:   hhe
 *  @创建时间:  2017/10/31 9:34
 *  @描述：    货柜全部商品
 */
public class AllSupContainerGoodsBean {


    public List<GroupsBean> groups;

    public static class GroupsBean {
        /**
         * id : 1
         * channelSn : A
         * goodsSn : 001
         * goodsName : 美好火腿肠300g
         * goodsPic : 1dfasdfas51.jpg
         * waitSupplyCount : 2
         * remainCount : 8
         */

        public int id;
        public String channelSn;
        public String goodsSn;
        public String goodsName;
        public String goodsPic;
        public int waitSupplyCount;
        public int remainCount;

        @Override
        public String toString() {
            return "GroupsBean{" +
                    "id=" + id +
                    ", channelSn='" + channelSn + '\'' +
                    ", goodsSn='" + goodsSn + '\'' +
                    ", goodsName='" + goodsName + '\'' +
                    ", goodsPic='" + goodsPic + '\'' +
                    ", waitSupplyCount=" + waitSupplyCount +
                    ", remainCount=" + remainCount +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "AllSupContainerGoodsBean{" +
                "groups=" + groups +
                '}';
    }
}
