package com.yxkj.deliveryman.dao;

/*
 *  @项目名：  yxkj-operation-android 
 *  @包名：    com.yxkj.deliveryman.dao
 *  @文件名:   WaitSupGoods
 *  @创建者:   hhe
 *  @创建时间:  2017/10/31 15:00
 *  @描述：    补货商品的补货情况
 */

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class WaitSupGoods {
    /**
     * 组成格式：cntrId+goodsSn
     */
    @Id
    private String id;
    /**
     * 补货数量
     */
    private int supNum;
    public int getSupNum() {
        return this.supNum;
    }
    public void setSupNum(int supNum) {
        this.supNum = supNum;
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    @Generated(hash = 508826567)
    public WaitSupGoods(String id, int supNum) {
        this.id = id;
        this.supNum = supNum;
    }
    @Generated(hash = 27092595)
    public WaitSupGoods() {
    }

}
