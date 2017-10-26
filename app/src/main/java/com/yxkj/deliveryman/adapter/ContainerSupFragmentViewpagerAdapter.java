package com.yxkj.deliveryman.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;


/*
 *  @项目名：  yxkj-operation-android 
 *  @包名：    com.yxkj.deliveryman.adapter
 *  @文件名:   ContainerSupFragmentViewpagerAdapter
 *  @创建者:   hhe
 *  @创建时间:  2017/10/26 16:51
 *  @描述：    货柜管理实际补货
 */
public class ContainerSupFragmentViewpagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList;
    private List<String> mTitles;

    public ContainerSupFragmentViewpagerAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> titles) {
        super(fm);
        mTitles = titles;
        mFragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
