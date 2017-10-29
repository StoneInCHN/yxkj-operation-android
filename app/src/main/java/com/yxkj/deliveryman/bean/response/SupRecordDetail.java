package com.yxkj.deliveryman.bean.response;

import java.util.List;

/**
 * Created by hhe on 2017/10/29.
 * 补货记录详情
 */

public class SupRecordDetail {


    public List<GroupsBean> groups;

    public static class GroupsBean {
        /**
         * cntrSn : A
         * cntrSupplementRecords : [{"channelSn":"X","goodsName":"美好火腿肠300g","goodsPic":"1dfasdfas51.jpg","waitSupplyCount":5,"supplyCount":5},{"channelSn":"A","goodsName":"乐事薯片300g","goodsPic":"65fgs2fgd.jpg","waitSupplyCount":15,"supplyCount":5},{"channelSn":"Y","goodsName":"美好火腿肠300g","goodsPic":"a5dda2gfs.jpg","waitSupplyCount":10,"supplyCount":5},{"channelSn":"A","goodsName":"乐事薯片300g","goodsPic":"65fgs2fgd.jpg","waitSupplyCount":10,"supplyCount":5}]
         */

        public String cntrSn;
        public List<CntrSupplementRecordsBean> cntrSupplementRecords;

        public static class CntrSupplementRecordsBean {
            /**
             * channelSn : X
             * goodsName : 美好火腿肠300g
             * goodsPic : 1dfasdfas51.jpg
             * waitSupplyCount : 5
             * supplyCount : 5
             */

            public String channelSn;
            public String goodsName;
            public String goodsPic;
            public int waitSupplyCount;
            public int supplyCount;
        }
    }
}
