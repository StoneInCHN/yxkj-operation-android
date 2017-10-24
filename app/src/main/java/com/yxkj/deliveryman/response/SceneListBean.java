package com.yxkj.deliveryman.response;

import java.util.List;

/*
 *  @项目名：  yxkj-operation-android 
 *  @包名：    com.yxkj.deliveryman.response
 *  @文件名:   SceneListBean
 *  @创建者:   hhe
 *  @创建时间:  2017/10/24 14:16
 *  @描述：    待补优享空间
 */
public class SceneListBean {


    public List<GroupsBean> groups;

    public static class GroupsBean {
        /**
         * sceneName : 香年广场T3
         * sceneSn : 001
         */

        public String sceneName;
        public String sceneSn;

        @Override
        public String toString() {
            return "GroupsBean{" +
                    "sceneName='" + sceneName + '\'' +
                    ", sceneSn='" + sceneSn + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "SceneListBean{" +
                "groups=" + groups +
                '}';
    }
}
