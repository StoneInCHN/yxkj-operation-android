package com.yxkj.deliveryman.dao;

/*
 *  @项目名：  yxkj-operation-android 
 *  @包名：    com.yxkj.deliveryman.dao
 *  @文件名:   WaitSupGoods
 *  @创建者:   hhe
 *  @创建时间:  2017/10/31 15:00
 *  @描述：    补货商品的补货统计存储
 */

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class WaitSupGoods {

    @Id(autoincrement = true)
    private Long id;
    /**
     * 优享空间地址
     */
    private String sceneId;
    /**
     * 货柜编号
     */
    private String cntrId;
    /**
     * 商品编号
     */
    private String goodsSn;

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

    public String getGoodsSn() {
        return this.goodsSn;
    }

    public void setGoodsSn(String goodsSn) {
        this.goodsSn = goodsSn;
    }

    public String getCntrId() {
        return this.cntrId;
    }

    public void setCntrId(String cntrId) {
        this.cntrId = cntrId;
    }

    public String getSceneId() {
        return this.sceneId;
    }

    public void setSceneId(String sceneId) {
        this.sceneId = sceneId;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Generated(hash = 28831934)
    public WaitSupGoods(Long id, String sceneId, String cntrId, String goodsSn,
            int supNum) {
        this.id = id;
        this.sceneId = sceneId;
        this.cntrId = cntrId;
        this.goodsSn = goodsSn;
        this.supNum = supNum;
    }

    @Generated(hash = 27092595)
    public WaitSupGoods() {
    }


}
