package com.yxkj.deliveryman.bean.response;

import java.util.List;

/**
 * Created by hhe on 2017/10/29.
 * 补货记录
 */

public class SupRecordBean {


    public List<GroupsBean> groups;

    public static class GroupsBean {
        /**
         * date : 2017.29
         * supplementList : [{"sceneSn":"001","sceneName":"香年广场T1","waitSupplyCount":30,"supplyCount":10,"lackCount":20,"supplyTime":"001"},{"sceneSn":"001","sceneName":"香年广场T1","waitSupplyCount":36,"supplyCount":0,"lackCount":36,"supplyTime":"001"}]
         * sumSupplyCount : 10
         * sumWaitSupplyCount : 56
         */

        public String date;
        public int sumSupplyCount;
        public int sumWaitSupplyCount;
        public List<SupplementListBean> supplementList;

        public static class SupplementListBean {
            /**
             * sceneSn : 001
             * sceneName : 香年广场T1
             * waitSupplyCount : 30
             * supplyCount : 10
             * lackCount : 20
             * supplyTime : 001
             */

            public String sceneSn;
            public String sceneName;
            public int waitSupplyCount;
            public int supplyCount;
            public int lackCount;
            public String supplyTime;

            @Override
            public String toString() {
                return "SupplementListBean{" +
                        "sceneSn='" + sceneSn + '\'' +
                        ", sceneName='" + sceneName + '\'' +
                        ", waitSupplyCount=" + waitSupplyCount +
                        ", supplyCount=" + supplyCount +
                        ", lackCount=" + lackCount +
                        ", supplyTime='" + supplyTime + '\'' +
                        '}';
            }
        }


        @Override
        public String toString() {
            return "GroupsBean{" +
                    "date='" + date + '\'' +
                    ", sumSupplyCount=" + sumSupplyCount +
                    ", sumWaitSupplyCount=" + sumWaitSupplyCount +
                    ", supplementList=" + supplementList +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "SupRecordBean{" +
                "groups=" + groups +
                '}';
    }
}
