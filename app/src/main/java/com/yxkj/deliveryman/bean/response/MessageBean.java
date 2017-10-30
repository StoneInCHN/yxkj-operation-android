package com.yxkj.deliveryman.bean.response;

import java.util.List;

/**
 * Created by hhe on 2017/10/29.
 * 消息列表
 */

public class MessageBean {

    public List<GroupsBean> groups;

    public static class GroupsBean {
        /**
         * sceneSn : 003
         * type : LACK
         * noticeCount : 5
         * noticeTime : 17/10/29 11:04:05
         * content : 香年广场T3急需补货15件
         */

        public String sceneSn;
        public String type;
        public int noticeCount;
        public String noticeTime;
        public String content;

        @Override
        public String toString() {
            return "GroupsBean{" +
                    "sceneSn='" + sceneSn + '\'' +
                    ", type='" + type + '\'' +
                    ", noticeCount=" + noticeCount +
                    ", noticeTime='" + noticeTime + '\'' +
                    ", content='" + content + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MessageBean{" +
                "groups=" + groups +
                '}';
    }
}
