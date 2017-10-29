package com.yxkj.deliveryman.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yxkj.deliveryman.bean.response.GoodsCategoryBean;

import java.util.List;

/*
 *  @项目名：  yxkj-operation-android 
 *  @包名：    com.yxkj.deliveryman.adapter
 *  @文件名:   GoodsCategoryFragmentAdapter
 *  @创建者:   hhe
 *  @创建时间:  2017/10/24 17:32
 *  @描述：    商品类别PagerAdapter
 */
public class GoodsCategoryFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList;

    private List<GoodsCategoryBean.GroupsBean> mTitles;

    public GoodsCategoryFragmentAdapter(FragmentManager fragmentManager, List<Fragment> fragmentList, List<GoodsCategoryBean.GroupsBean> titles) {
        super(fragmentManager);
        mFragmentList = fragmentList;
        mTitles = titles;
    }

    public void setTitlesAndFragmentList(List<GoodsCategoryBean.GroupsBean> titles, List<Fragment> fragmentList) {
        mFragmentList = fragmentList;
        mTitles = titles;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position).cateName;
    }
}
