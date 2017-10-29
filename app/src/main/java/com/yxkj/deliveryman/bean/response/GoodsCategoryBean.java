package com.yxkj.deliveryman.bean.response;

import java.util.List;

/*
 *  @项目名：  yxkj-operation-android 
 *  @包名：    com.yxkj.deliveryman.bean.response
 *  @文件名:   GoodsCategoryBean
 *  @创建者:   hhe
 *  @创建时间:  2017/10/24 15:21
 *  @描述：    商品类别
 */
public class GoodsCategoryBean {


    public List<GroupsBean> groups;

    public static class GroupsBean {
        /**
         * cateId : 1
         * cateName : 美味零食
         */

        public int cateId;
        public String cateName;
    }

}
