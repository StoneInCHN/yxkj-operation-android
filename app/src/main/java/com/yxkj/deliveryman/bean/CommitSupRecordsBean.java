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
    public CommitSupRecordsBean(String sceneSn, String userId, List<SupplementRecordsBean> supplementRecords) {
        this.sceneSn = sceneSn;
        this.userId = userId;
        this.supplementRecords = supplementRecords;
    }

    /**
     * cntrId : 0
     * sceneSn : string
     * supplementRecords : [{"supplementId":0,"supplyCount":0}]
     * userId : 0
     */

    public String sceneSn;
    public String userId;
    public List<SupplementRecordsBean> supplementRecords;

    public static class SupplementRecordsBean {
        public SupplementRecordsBean(int supplementId, int supplyCount) {
            this.supplementId = supplementId;
            this.supplyCount = supplyCount;
        }

        /**
         * supplementId : 0
         * supplyCount : 0
         */



        public int supplementId;
        public int supplyCount;
    }
}
