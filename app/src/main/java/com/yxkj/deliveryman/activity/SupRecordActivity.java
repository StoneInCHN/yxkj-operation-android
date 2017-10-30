package com.yxkj.deliveryman.activity;

import android.view.View;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.adapter.SupRecordAdapter;
import com.yxkj.deliveryman.base.BaseActivity;
import com.yxkj.deliveryman.base.BaseObserver;
import com.yxkj.deliveryman.bean.response.SupRecordBean;
import com.yxkj.deliveryman.constant.UserInfo;
import com.yxkj.deliveryman.http.HttpApi;
import com.yxkj.deliveryman.util.RecyclerViewSetUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 补货记录
 */
public class SupRecordActivity extends BaseActivity {
    /**
     * 按时间记录的列表
     */
    private LRecyclerView mLrvSupRecord;
    private SupRecordAdapter adapter;

    @Override
    public int getContentViewId() {
        return R.layout.activity_sup_record;
    }

    @Override
    public void beforeInitView() {

    }

    @Override
    public void initView() {
        mLrvSupRecord = findViewByIdNoCast(R.id.lrv_sup_record);
    }

    @Override
    public void initData() {
        adapter = new SupRecordAdapter(this);
        RecyclerViewSetUtil.setRecyclerView(this, mLrvSupRecord, adapter);

        mLrvSupRecord.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPageNum = 1;
                getRecordsList();
            }
        });
        mLrvSupRecord.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPageNum++;
                getRecordsList();
            }
        });

        getRecordsList();
    }

    private int mPageNum = 1;

    /**
     * 获取补货记录
     */
    private void getRecordsList() {
        HttpApi.getInstance()
                .getSupplementSumRecord(UserInfo.USER_ID, mPageNum + "", "10")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<SupRecordBean>() {
                    @Override
                    public void onComplete() {
                        mLrvSupRecord.refreshComplete(10);
                    }

                    @Override
                    protected void onHandleSuccess(SupRecordBean supRecordBean) {
                        if (mPageNum == 1) {
                            adapter.mBeanList.clear();
                        }
                        adapter.mBeanList.addAll(supRecordBean.groups);
                        adapter.notifyDataSetChanged();
                        mLrvSupRecord.refreshComplete(10);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    @Override
    public void setEvent() {

    }

    @Override
    public void onClick(View view) {

    }

}
