package com.yxkj.deliveryman.event;

import com.yxkj.deliveryman.response.SceneListBean;

/*
 *  @项目名：  yxkj-operation-android 
 *  @包名：    com.yxkj.deliveryman
 *  @文件名:   WaitSupAddressEvent
 *  @创建者:   hhe
 *  @创建时间:  2017/10/25 14:49
 *  @描述：    选择优享空间地址
 */
public class WaitSupAddressEvent {
    public SceneListBean.GroupsBean addressBean;

    public WaitSupAddressEvent(SceneListBean.GroupsBean addressBean) {
        this.addressBean = addressBean;
    }
}
