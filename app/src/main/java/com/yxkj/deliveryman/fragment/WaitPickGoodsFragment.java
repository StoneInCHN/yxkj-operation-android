package com.yxkj.deliveryman.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.adapter.WaitPickListAdapter;
import com.yxkj.deliveryman.http.BaseObserver;
import com.yxkj.deliveryman.constant.UserInfo;
import com.yxkj.deliveryman.event.WaitSupAddressEvent;
import com.yxkj.deliveryman.http.HttpApi;
import com.yxkj.deliveryman.bean.response.WaitSupGoodsListBean;
import com.yxkj.deliveryman.util.RecyclerViewSetUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/*
 *  @项目名：  yxkj-operation-android 
 *  @包名：    com.yxkj.deliveryman.frament
 *  @文件名:   WaitPickGoodsFragment
 *  @创建者:   hhe
 *  @创建时间:  2017/10/26 10:12
 *  @描述：    待补清单fragment
 */
public class WaitPickGoodsFragment extends Fragment {

    @BindView(R.id.lrv_fragment_wait_sup_goods)
    LRecyclerView mLrv;
    /**
     * 商品类别Id
     */
    private String mCateId;
    /**
     * 商品地点
     */
    public String mSceneSn;
    private WaitPickListAdapter mWaitSupListAdapter;
    private Unbinder mUnbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wait_pick_list, container, false);
        mUnbinder = ButterKnife.bind(this, rootView);

        mCateId = getArguments().getString("cateId");
        mSceneSn = getArguments().getString("sceneSn");

        initRv();
        getWaitSupplyGoodsByCategory();
        return rootView;
    }

    /**
     * 由于切换优享空间，更新数据
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateData(WaitSupAddressEvent event) {
        mSceneSn = event.addressBean.sceneSn;
        mPageNum = 1;
        getWaitSupplyGoodsByCategory();
    }

    private void initRv() {
        mWaitSupListAdapter = new WaitPickListAdapter(getActivity(), mSceneSn);
        RecyclerViewSetUtil.setRecyclerView(getActivity(), mLrv, mWaitSupListAdapter, true);
        mLrv.setPullRefreshEnabled(false);
        mLrv.setLoadMoreEnabled(false);
        mLrv.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPageNum++;
                getWaitSupplyGoodsByCategory();
            }
        });
    }

    private int mPageNum = 1;

    /**
     * 获取待补商品列表
     */
    private void getWaitSupplyGoodsByCategory() {
        HttpApi.getInstance().
                getWaitSupplyGoodsByCategory(UserInfo.USER_ID, mSceneSn, mCateId, mPageNum + "", "10")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<WaitSupGoodsListBean>() {
                    @Override
                    protected void onHandleSuccess(WaitSupGoodsListBean waitSupGoodsListBean) {
                        if (mPageNum == 1) {
                            mWaitSupListAdapter.mBeanList.clear();
                        }
                        mWaitSupListAdapter.mBeanList.addAll(waitSupGoodsListBean.groups);
                        mWaitSupListAdapter.notifyDataSetChanged();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        EventBus.getDefault().unregister(this);
    }
}
