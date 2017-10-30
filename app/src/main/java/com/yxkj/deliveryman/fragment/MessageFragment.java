package com.yxkj.deliveryman.fragment;


import android.view.View;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.adapter.MessageAdapter;
import com.yxkj.deliveryman.base.BaseFragment;
import com.yxkj.deliveryman.base.BaseObserver;
import com.yxkj.deliveryman.bean.response.MessageBean;
import com.yxkj.deliveryman.bean.response.SupRecordDetail;
import com.yxkj.deliveryman.constant.UserInfo;
import com.yxkj.deliveryman.http.HttpApi;
import com.yxkj.deliveryman.sharepreference.SharePrefreceHelper;
import com.yxkj.deliveryman.sharepreference.SharedKey;
import com.yxkj.deliveryman.util.RecyclerViewSetUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 首页消息
 */
public class MessageFragment extends BaseFragment {
    /*消息列表*/
    private LRecyclerView mLrv;
    private MessageAdapter mMessageAdapter;

    @Override
    protected int getResource() {
        return R.layout.fragment_message;
    }

    @Override
    protected void beforeInitView() {

    }

    @Override
    protected void initView(View rootView) {
        mLrv = findViewByIdNoCast(R.id.lrv_fragment_message);
    }

    private int mPageNum = 1;

    @Override
    protected void initData() {
        mMessageAdapter = new MessageAdapter(getActivity());
        RecyclerViewSetUtil.setRecyclerView(getActivity(), mLrv, mMessageAdapter, true);
//        mLrv.setLoadMoreFooter(new LoadingFooter(getActivity()));
//        mLrv.setFooterViewHint("加载中", "没有更多了", "网络出错");
//        mLrv.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        //mLrv.setLoadMoreEnabled(false);

        mLrv.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPageNum = 1;
                getMsgList();
            }
        });

        mLrv.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPageNum++;
                getMsgList();
            }
        });
        getMsgList();
    }

    private void getMsgList() {
        HttpApi.getInstance()
                .getMsg(UserInfo.USER_ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<MessageBean>() {
                    @Override
                    protected void onHandleSuccess(MessageBean bean) {
                        mMessageAdapter.settList(bean.groups);
                        mLrv.refreshComplete(10);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        mLrv.refreshComplete(10);
                    }
                });
    }

    @Override
    protected void setEvent() {

    }

    @Override
    public void onClick(View view) {

    }

}
