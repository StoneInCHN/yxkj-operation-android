package com.yxkj.deliveryman.activity;

import android.view.View;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.adapter.MessageDetailAdapter;
import com.yxkj.deliveryman.base.BaseActivity;
import com.yxkj.deliveryman.bean.response.MessageDetailBean;
import com.yxkj.deliveryman.constant.UserInfo;
import com.yxkj.deliveryman.http.BaseObserver;
import com.yxkj.deliveryman.http.HttpApi;
import com.yxkj.deliveryman.util.RecyclerViewSetUtil;
import com.yxkj.deliveryman.view.RichToolBar;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MessageDetailActivity extends BaseActivity {

    @BindView(R.id.tb_message_detail)
    RichToolBar mTb;
    @BindView(R.id.lrv_message_detail)
    LRecyclerView mLrv;
    /**
     * 消息类型，由前个activity传进来
     */
    private String type;
    private MessageDetailAdapter mAdapter;

    @Override
    public int getContentViewId() {
        return R.layout.activity_message_detail;
    }

    @Override
    public void initView() {
        type = getIntent().getExtras().getString("type");
    }

    private int mPageNum = 1;

    @Override
    public void initData() {
        mAdapter = new MessageDetailAdapter(mContext);
        RecyclerViewSetUtil.setRecyclerView(mContext, mLrv, mAdapter, true);
        mLrv.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPageNum = 1;
                getMessageDetail();
            }
        });
        mLrv.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPageNum++;
                getMessageDetail();
            }
        });
        getMessageDetail();
    }

    private void getMessageDetail() {
        HttpApi.getInstance().getMsgDetails(UserInfo.USER_ID, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<MessageDetailBean>() {
                    @Override
                    protected void onHandleSuccess(MessageDetailBean messageDetailBean) {
                        if (mPageNum == 1) {
                            mAdapter.mBeanList.clear();
                        }

                        mAdapter.mBeanList.addAll(messageDetailBean.groups);
                        mAdapter.notifyDataSetChanged();
                        mLrv.refreshComplete(10);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        mLrv.refreshComplete(10);
                    }
                });
    }

    @Override
    public void setEvent() {

    }

    @Override
    public void onClick(View v) {

    }

}
