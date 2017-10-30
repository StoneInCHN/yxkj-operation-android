package com.yxkj.deliveryman.activity;

import android.view.View;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.adapter.SupRecordDetailAdapter;
import com.yxkj.deliveryman.base.BaseActivity;
import com.yxkj.deliveryman.base.BaseObserver;
import com.yxkj.deliveryman.bean.response.SupRecordDetailBean;
import com.yxkj.deliveryman.constant.UserInfo;
import com.yxkj.deliveryman.http.HttpApi;
import com.yxkj.deliveryman.util.RecyclerViewSetUtil;
import com.yxkj.deliveryman.view.RichToolBar;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 补货记录详情
 */
public class SupRecordDetailActivity extends BaseActivity {
    @BindView(R.id.lrv_sup_detail)
    LRecyclerView mLrv;
    @BindView(R.id.tb_sup_detail)
    RichToolBar mRichToolBar;

    private String sceneSn;
    private SupRecordDetailAdapter mSupRecordDetailAdapter;
    private String sceneName;

    @Override
    public int getContentViewId() {
        return R.layout.activity_sup_detail;
    }

    @Override
    public void initView() {
        sceneSn = getIntent().getExtras().getString("sceneSn");
        sceneName = getIntent().getExtras().getString("sceneName");
        mRichToolBar.setTitle(sceneName);
    }


    private int mPageNum = 1;

    @Override
    public void initData() {
        mSupRecordDetailAdapter = new SupRecordDetailAdapter(mContext);
        RecyclerViewSetUtil.setRecyclerView(mContext, mLrv, mSupRecordDetailAdapter);

        mLrv.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPageNum++;
                getDataList();
            }
        });

        mLrv.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPageNum = 1;
                getDataList();
            }
        });

        getDataList();
    }

    private void getDataList() {
        HttpApi.getInstance()
                .getSupplementRecordDetails(UserInfo.USER_ID, sceneSn)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<SupRecordDetailBean>() {
                    @Override
                    protected void onHandleSuccess(SupRecordDetailBean supRecordDetailBean) {
                        if (mPageNum == 1) {
                            mSupRecordDetailAdapter.mGroupsBeanList.clear();
                        }
                        mSupRecordDetailAdapter.mGroupsBeanList.addAll(supRecordDetailBean.groups);
                        mSupRecordDetailAdapter.notifyDataSetChanged();
                        mLrv.refreshComplete(10);
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
    public void onClick(View v) {

    }
}
