package com.yxkj.deliveryman.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;


/*
 *  @项目名：  yxkj-operation-android 
 *  @包名：    com.yxkj.deliveryman.adapter
 *  @文件名:   GoodsCategoryPagerAdapter
 *  @创建者:   hhe
 *  @创建时间:  2017/10/24 17:32
 *  @描述：    商品类别PagerAdapter
 */
public class GoodsCategoryPagerAdapter extends PagerAdapter {
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
