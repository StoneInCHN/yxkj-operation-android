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
import com.yxkj.deliveryman.activity.ContainerManageActivity;
import com.yxkj.deliveryman.adapter.SupGoodsAdapter;
import com.yxkj.deliveryman.base.BaseObserver;
import com.yxkj.deliveryman.http.HttpApi;
import com.yxkj.deliveryman.bean.response.WaitSupContainerGoodsBean;
import com.yxkj.deliveryman.sharepreference.SharePrefreceHelper;
import com.yxkj.deliveryman.sharepreference.SharedKey;
import com.yxkj.deliveryman.util.RecyclerViewSetUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/*
 *  @项目名：  yxkj-operation-android 
 *  @包名：    com.yxkj.deliveryman.fragment
 *  @文件名:   ContainerManageFragment
 *  @创建者:   hhe
 *  @创建时间:  2017/10/26 16:56
 *  @描述：    货柜管理页面中的商品列表fragment
 */
public class ContainerManageFragment extends Fragment {
    private LRecyclerView mLrv;
    private SupGoodsAdapter mSupGoodsAdapter;

    /**
     * 待补商品
     */
    public final String TYPE_WAIT_SUP = "wait_sup";
    /**
     * 所有商品
     */
    public final String TYPE_ALL_GOODS = "all_goods";

    /**
     * fragment类型
     */
    private String mFragmentType;
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_container_manage, container, false);
        mFragmentType = getArguments().getString("fragment_type");
        initRv(rootView);
        getWaitSupplyContainerGoodsList();

        return rootView;
    }

    private void initRv(View rootView) {
        mLrv = rootView.findViewById(R.id.lrv_fragment_container_manage);
        mSupGoodsAdapter = new SupGoodsAdapter(getActivity());
        RecyclerViewSetUtil.setRecyclerView(getActivity(), mLrv, mSupGoodsAdapter, true);
        mLrv.setPullRefreshEnabled(false);
        mLrv.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPageNum++;
                getWaitSupplyContainerGoodsList();
            }
        });
    }

    private int mPageNum = 1;

    private void getWaitSupplyContainerGoodsList() {
        String userId = SharePrefreceHelper.getInstance().getString(SharedKey.USER_ID);
        ContainerManageActivity activity = (ContainerManageActivity) getActivity();
        HttpApi.getInstance()
                .getWaitSupplyContainerGoodsList(userId, activity.cntrId, mPageNum + "", "10")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<WaitSupContainerGoodsBean>() {
                    @Override
                    protected void onHandleSuccess(WaitSupContainerGoodsBean waitSupContainerGoodsBean) {
                        mLrv.refreshComplete(10);
                        mSupGoodsAdapter.setGroupsBeanList(waitSupContainerGoodsBean.groups);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });

    }
}
