package com.yxkj.deliveryman.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.base.BaseActivity;
import com.yxkj.deliveryman.base.BaseObserver;
import com.yxkj.deliveryman.bean.response.SupRecordDetail;
import com.yxkj.deliveryman.constant.UserInfo;
import com.yxkj.deliveryman.http.HttpApi;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 补货记录详情
 */
public class SupRecordDetailActivity extends BaseActivity {

    private String sceneSn;

    @Override
    public int getContentViewId() {
        return R.layout.activity_sup_detail;
    }

    @Override
    public void initView() {
        sceneSn = getIntent().getExtras().getString("sceneSn");
    }

    @Override
    public void initData() {
        getDataList();
    }

    private void getDataList() {
        HttpApi.getInstance()
                .getSupplementRecordDetails(UserInfo.USER_ID, sceneSn)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<SupRecordDetail>() {
                    @Override
                    protected void onHandleSuccess(SupRecordDetail supRecordDetail) {

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
