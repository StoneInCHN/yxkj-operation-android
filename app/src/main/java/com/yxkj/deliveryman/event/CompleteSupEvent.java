package com.yxkj.deliveryman.event;

/**
 *  @项目名：  yxkj-operation-android 
 *  @包名：    com.yxkj.deliveryman.event
 *  @文件名:   CompleteSupEvent
 *  @创建者:   hhe
 *  @创建时间:  2017/10/26 16:01
 *  @描述：    点击某商品完成取货
 */
public class CompleteSupEvent {
    /**
     * 位于列表中的位置
     */
    public int position;

    public CompleteSupEvent(int position) {
        this.position = position;
    }
}
