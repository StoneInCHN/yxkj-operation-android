package com.yxkj.deliveryman.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/*
 *  @项目名：  yxkj-operation-android 
 *  @包名：    com.yxkj.deliveryman.dao
 *  @文件名:   FetchGoods
 *  @创建者:   hhe
 *  @创建时间:  2017/10/30 10:32
 *  @描述：    取货记录本地存储
 */
@Entity
public class FetchGoods {
    @Id
    public String id;

    public boolean isComplete;

    public boolean getIsComplete() {
        return this.isComplete;
    }

    public void setIsComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Generated(hash = 1026542380)
    public FetchGoods(String id, boolean isComplete) {
        this.id = id;
        this.isComplete = isComplete;
    }

    @Generated(hash = 295179174)
    public FetchGoods() {
    }

}
