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
import com.yxkj.deliveryman.adapter.AllSupGoodsAdapter;
import com.yxkj.deliveryman.http.BaseObserver;
import com.yxkj.deliveryman.bean.response.AllSupContainerGoodsBean;
import com.yxkj.deliveryman.constant.UserInfo;
import com.yxkj.deliveryman.http.HttpApi;
import com.yxkj.deliveryman.util.RecyclerViewSetUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/*
 *  @项目名：  yxkj-operation-android 
 *  @包名：    com.yxkj.deliveryman.fragment
 *  @文件名:   WaitSupContainerManageFragment
 *  @创建者:   hhe
 *  @创建时间:  2017/10/26 16:56
 *  @描述：    全部商品货柜管理
 */
public class AllSupContainerManageFragment extends Fragment {
    private LRecyclerView mLrv;
    private AllSupGoodsAdapter mAllSupGoodsAdapter;

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_all_sup_container_manage, container, false);
        initRv(rootView);
        getAllSupplyContainerGoodsList();

        return rootView;
    }

    private void initRv(View rootView) {
        mLrv = rootView.findViewById(R.id.lrv_fragment_all_sup_container_manage);
        mAllSupGoodsAdapter = new AllSupGoodsAdapter(getActivity());
        RecyclerViewSetUtil.setRecyclerView(getActivity(), mLrv, mAllSupGoodsAdapter, true);
        mLrv.setPullRefreshEnabled(false);
        mLrv.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPageNum++;
                getAllSupplyContainerGoodsList();
            }
        });
    }

    private int mPageNum = 1;

    private void getAllSupplyContainerGoodsList() {
        ContainerManageActivity activity = (ContainerManageActivity) getActivity();
        HttpApi.getInstance()
                .getAllSupContainerGoodsList(UserInfo.USER_ID, activity.cntrId, mPageNum + "", "10")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<AllSupContainerGoodsBean>() {
                    @Override
                    protected void onHandleSuccess(AllSupContainerGoodsBean allSupContainerGoodsBean) {
                        if (mPageNum==1){
                            mAllSupGoodsAdapter.mGroupsBeanList.clear();
                        }
                        mAllSupGoodsAdapter.setGroupsBeanList(allSupContainerGoodsBean.groups);
                        mLrv.refreshComplete(10);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        mLrv.refreshComplete(10);
                    }
                });
    }

}
