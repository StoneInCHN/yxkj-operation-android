package com.yxkj.deliveryman.bean;

import java.util.List;

/*
 *  @项目名：  yxkj-operation-android 
 *  @包名：    com.yxkj.deliveryman.bean
 *  @文件名:   CommitSupRecordsBean
 *  @创建者:   hhe
 *  @创建时间:  2017/10/27 14:50
 *  @描述：    提交给服务器的补货记录
 */
public class CommitSupRecordsBean {


    public List<SupplementRecordsBean> supplementRecords;

    public static class SupplementRecordsBean {
        /**
         * supplementId : 0
         * supplyCount : 0
         */

        public int supplementId;
        public int supplyCount;
    }
}
