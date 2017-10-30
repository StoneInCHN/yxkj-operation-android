package com.yxkj.deliveryman.activity;

import android.content.SharedPreferences;
import android.view.View;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.adapter.SupRecordAdapter;
import com.yxkj.deliveryman.base.BaseActivity;
import com.yxkj.deliveryman.base.BaseObserver;
import com.yxkj.deliveryman.bean.response.SupRecordBean;
import com.yxkj.deliveryman.constant.UserInfo;
import com.yxkj.deliveryman.http.HttpApi;
import com.yxkj.deliveryman.sharepreference.SharePrefreceHelper;
import com.yxkj.deliveryman.sharepreference.SharedKey;
import com.yxkj.deliveryman.util.RecyclerViewSetUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.HTTP;

/**
 * 补货记录
 */
public class SupRecordActivity extends BaseActivity {
    /**
     * 按时间记录的列表
     */
    private LRecyclerView mRlvSupRecord;
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
        mRlvSupRecord = findViewByIdNoCast(R.id.lrv_sup_record);
    }

    @Override
    public void initData() {
        adapter = new SupRecordAdapter(this);
        RecyclerViewSetUtil.setRecyclerView(this, mRlvSupRecord, adapter);

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
                    protected void onHandleSuccess(SupRecordBean supRecordBean) {
                        adapter.settList(supRecordBean.groups);
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
