package com.yxkj.deliveryman.bean.response;

import java.util.List;

/*
 *  @项目名：  yxkj-operation-android 
 *  @包名：    com.yxkj.deliveryman.bean.response
 *  @文件名:   WaitSupStateBean
 *  @创建者:   hhe
 *  @创建时间:  2017/10/23 16:51
 *  @描述：    货柜待补情况
 */
public class WaitSupStateBean {


    /**
     * waitSupplySumCount : 14
     * scenes : [{"sceneSn":"001","sceneName":"香年广场T3","vendingContainerGroups":[{"vendingContainers":[{"id":1,"vendingContainerName":null,"cntrSn":"01","central":true,"waitSupplyCount":0},{"id":2,"vendingContainerName":null,"cntrSn":"02","central":false,"waitSupplyCount":6},{"id":3,"vendingContainerName":null,"cntrSn":"03","central":false,"waitSupplyCount":0},{"id":4,"vendingContainerName":null,"cntrSn":"04","central":false,"waitSupplyCount":0},{"id":5,"vendingContainerName":null,"cntrSn":"05","central":false,"waitSupplyCount":0}]},{"vendingContainers":[{"id":6,"vendingContainerName":null,"cntrSn":"06","central":true,"waitSupplyCount":0},{"id":7,"vendingContainerName":null,"cntrSn":"07","central":false,"waitSupplyCount":8},{"id":8,"vendingContainerName":null,"cntrSn":"08","central":false,"waitSupplyCount":0}]}]},{"sceneSn":"002","sceneName":"香年广场T1","vendingContainerGroups":[{"vendingContainers":[{"id":9,"vendingContainerName":null,"cntrSn":"09","central":true,"waitSupplyCount":0},{"id":10,"vendingContainerName":null,"cntrSn":"10","central":false,"waitSupplyCount":0}]}]}]
     */

    public int waitSupplySumCount;
    public List<ScenesBean> scenes;


    public static class ScenesBean {
        /**
         * sceneSn : 001
         * sceneName : 香年广场T3
         * vendingContainerGroups : [{"vendingContainers":[{"id":1,"vendingContainerName":null,"cntrSn":"01","central":true,"waitSupplyCount":0},{"id":2,"vendingContainerName":null,"cntrSn":"02","central":false,"waitSupplyCount":6},{"id":3,"vendingContainerName":null,"cntrSn":"03","central":false,"waitSupplyCount":0},{"id":4,"vendingContainerName":null,"cntrSn":"04","central":false,"waitSupplyCount":0},{"id":5,"vendingContainerName":null,"cntrSn":"05","central":false,"waitSupplyCount":0}]},{"vendingContainers":[{"id":6,"vendingContainerName":null,"cntrSn":"06","central":true,"waitSupplyCount":0},{"id":7,"vendingContainerName":null,"cntrSn":"07","central":false,"waitSupplyCount":8},{"id":8,"vendingContainerName":null,"cntrSn":"08","central":false,"waitSupplyCount":0}]}]
         */

        public String sceneSn;
        public String sceneName;
        public List<VendingContainerGroupsBean> vendingContainerGroups;

        @Override
        public String toString() {
            return "ScenesBean{" +
                    "sceneSn='" + sceneSn + '\'' +
                    ", sceneName='" + sceneName + '\'' +
                    ", vendingContainerGroups=" + vendingContainerGroups +
                    '}';
        }

        public static class VendingContainerGroupsBean {
            public List<VendingContainersBean> vendingContainers;

            public static class VendingContainersBean {
                /**
                 * id : 1
                 * vendingContainerName : null
                 * cntrSn : 01
                 * central : true
                 * waitSupplyCount : 0
                 */

                public int id;
                public String vendingContainerName;
                public String cntrSn;
                public boolean central;
                public int waitSupplyCount;

                @Override
                public String toString() {
                    return "VendingContainersBean{" +
                            "id=" + id +
                            ", vendingContainerName=" + vendingContainerName +
                            ", cntrSn='" + cntrSn + '\'' +
                            ", central=" + central +
                            ", waitSupplyCount=" + waitSupplyCount +
                            '}';
                }
            }
        }

    }

    @Override
    public String toString() {
        return "WaitSupStateBean{" +
                "waitSupplySumCount=" + waitSupplySumCount +
                ", scenes=" + scenes +
                '}';
    }
}
