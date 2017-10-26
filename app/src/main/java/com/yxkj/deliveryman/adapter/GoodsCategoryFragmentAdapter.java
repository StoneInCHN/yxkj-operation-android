package com.yxkj.deliveryman.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.util.RecyclerViewUtils;
import com.yxkj.deliveryman.base.BaseObserver;
import com.yxkj.deliveryman.http.HttpApi;
import com.yxkj.deliveryman.response.GoodsCategoryBean;
import com.yxkj.deliveryman.response.WaitSupGoodsListBean;
import com.yxkj.deliveryman.sharepreference.SharePrefreceHelper;
import com.yxkj.deliveryman.sharepreference.SharedKey;
import com.yxkj.deliveryman.util.RecyclerViewSetUtil;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.interfaces.PBEKey;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
