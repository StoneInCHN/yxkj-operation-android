package com.yxkj.deliveryman.bean.response;

import java.util.List;

/**
 * Created by hhe on 2017/10/29.
 * 信息详情
 */

public class MessageDetailBean {

    public List<GroupsBean> groups;

    public static class GroupsBean {
        /**
         * sceneSn : 003
         * title : 香年广场T3
         * content : 急需补货15件
         * sendDate : 17/10/29 11:04:05
         */

        public String sceneSn;
        public String title;
        public String content;
        public String sendDate;
    }
}
