package com.yxkj.deliveryman.bean.response;

import java.util.List;

/*
 *  @项目名：  yxkj-operation-android 
 *  @包名：    com.yxkj.deliveryman.bean.response
 *  @文件名:   WaitSupGoodsListBean
 *  @创建者:   hhe
 *  @创建时间:  2017/10/24 15:31
 *  @描述：    待补商品清单
 */
public class WaitSupGoodsListBean {

    public List<GroupsBean> groups;

    public static class GroupsBean {
        /**
         * goodsSn : 008
         * goodsName : 张飞牛肉300g
         * goodsPic : jhg2s5fgsd5.jpg
         * waitSupplyCount : 8
         */

        public String goodsSn;
        public String goodsName;
        public String goodsPic;
        public int waitSupplyCount;

        @Override
        public String toString() {
            return "GroupsBean{" +
                    "goodsSn='" + goodsSn + '\'' +
                    ", goodsName='" + goodsName + '\'' +
                    ", goodsPic='" + goodsPic + '\'' +
                    ", waitSupplyCount=" + waitSupplyCount +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "WaitSupGoodsListBean{" +
                "groups=" + groups +
                '}';
    }
}
