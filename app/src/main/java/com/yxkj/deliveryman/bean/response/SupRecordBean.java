package com.yxkj.deliveryman.bean.response;

import java.util.List;

/**
 * Created by hhe on 2017/10/29.
 * 补货记录
 */

public class SupRecordBean  {

    public List<GroupsBean> groups;

    public static class GroupsBean {
        /**
         * sceneSn : 001
         * sceneName : 香年广场T1
         * waitSuppTotalCount : 30
         * suppTotalCount : 10
         * lackCount : 20
         * suppEndTime : 2017/10/29 01:21:21
         */

        public String sceneSn;
        public String sceneName;
        public int waitSuppTotalCount;
        public int suppTotalCount;
        public int lackCount;
        public String suppEndTime;

        @Override
        public String toString() {
            return "GroupsBean{" +
                    "sceneSn='" + sceneSn + '\'' +
                    ", sceneName='" + sceneName + '\'' +
                    ", waitSuppTotalCount=" + waitSuppTotalCount +
                    ", suppTotalCount=" + suppTotalCount +
                    ", lackCount=" + lackCount +
                    ", suppEndTime='" + suppEndTime + '\'' +
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
