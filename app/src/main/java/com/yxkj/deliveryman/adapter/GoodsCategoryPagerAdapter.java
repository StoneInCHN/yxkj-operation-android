package com.yxkj.deliveryman.adapter;

import android.content.Context;
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
 *  @文件名:   GoodsCategoryPagerAdapter
 *  @创建者:   hhe
 *  @创建时间:  2017/10/24 17:32
 *  @描述：    商品类别PagerAdapter
 */
public class GoodsCategoryPagerAdapter extends PagerAdapter {

    private List<GoodsCategoryBean.GroupsBean> mGroupsBeanList;

    private Context mContext;
    private WaitSupListAdapter waitSupListAdapter;

    public GoodsCategoryPagerAdapter(Context context) {
        mContext = context;
        mGroupsBeanList = new ArrayList<>();
    }

    public void setList(List<GoodsCategoryBean.GroupsBean> groupsBeanList) {
        mGroupsBeanList.clear();
        mGroupsBeanList.addAll(groupsBeanList);
        notifyDataSetChanged();
    }


    public String sceneSn;

    public void setSceneSn(String sceneSn) {
        this.sceneSn = sceneSn;
    }

    @Override
    public int getCount() {
        return mGroupsBeanList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mGroupsBeanList.get(position).cateName;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LRecyclerView lRecyclerView = new LRecyclerView(mContext);
        waitSupListAdapter = new WaitSupListAdapter(mContext);
        RecyclerViewSetUtil.setRecyclerView(mContext, lRecyclerView, waitSupListAdapter);
        getWaitSupplyGoodsByCategory(position);

        container.addView(lRecyclerView);
        return lRecyclerView;

    }

    private void getWaitSupplyGoodsByCategory(int position) {
        String userId = SharePrefreceHelper.getInstance().getString(SharedKey.USER_ID);
        HttpApi.getInstance().
                getWaitSupplyGoodsByCategory(userId, sceneSn, mGroupsBeanList.get(position).cateId + "", "1", "10")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<WaitSupGoodsListBean>() {
                    @Override
                    protected void onHandleSuccess(WaitSupGoodsListBean waitSupGoodsListBean) {
                        waitSupListAdapter.settList(waitSupGoodsListBean.groups);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
