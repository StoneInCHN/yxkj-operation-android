package com.yxkj.deliveryman.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.adapter.ContainerSupFragmentViewpagerAdapter;
import com.yxkj.deliveryman.base.BaseActivity;
import com.yxkj.deliveryman.base.BaseObserver;
import com.yxkj.deliveryman.callback.CommonDialogSureListener;
import com.yxkj.deliveryman.callback.OnCommon2Listener;
import com.yxkj.deliveryman.constant.UserInfo;
import com.yxkj.deliveryman.event.RestartTakePhotoEvent;
import com.yxkj.deliveryman.fragment.AllSupContainerManageFragment;
import com.yxkj.deliveryman.fragment.WaitSupContainerManageFragment;
import com.yxkj.deliveryman.http.HttpApi;
import com.yxkj.deliveryman.permission.Permission;
import com.yxkj.deliveryman.permission.RxPermissions;
import com.yxkj.deliveryman.bean.response.NullBean;
import com.yxkj.deliveryman.util.LogUtil;
import com.yxkj.deliveryman.util.ToastUtil;
import com.yxkj.deliveryman.util.UploadImageUtil;
import com.yxkj.deliveryman.view.RichToolBar;
import com.yxkj.deliveryman.view.popupwindow.BottomTakePhotoAndPicPopupWindow;
import com.yxkj.deliveryman.view.dialog.CommonYesOrNoDialog;
import com.yxkj.deliveryman.view.popupwindow.CompleteSupPopWindow;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 货柜管理
 */
public class ContainerManageActivity extends BaseActivity {
    private ViewPager mViewPager;
    private TabLayout mTablayout;
    private ContainerSupFragmentViewpagerAdapter mContainerAdapter;
    private RichToolBar mToolbar;
    private WaitSupContainerManageFragment mWaitSupFragment;

    @Override
    public int getContentViewId() {
        return R.layout.activity_container_manage;
    }

    public String sceneSn;
    public String cntrId;
    public String containerName;

    @Override
    public void beforeInitView() {
        sceneSn = getIntent().getExtras().getString("sceneSn");
        cntrId = getIntent().getExtras().getString("cntrId");
        containerName = getIntent().getExtras().getString("containerName");
    }

    @Override
    public void initView() {
        mViewPager = findViewByIdNoCast(R.id.vp_container_manage);
        mToolbar = findViewByIdNoCast(R.id.tb_container_manage);
        mTablayout = findViewByIdNoCast(R.id.tab_container_manage);

    }

    @Override
    public void initData() {
        initTablayout();
        mToolbar.setTitle(containerName + "管理");
        mToolbar.mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWaitSupFragment.getUploadRecordBean().size() > 0) {
                    showBackConfirmDialog();
                } else {
                    finish();
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mWaitSupFragment.getUploadRecordBean().size() > 0) {
            showBackConfirmDialog();
        } else {
            finish();
        }
    }

    private void showBackConfirmDialog() {
        CommonYesOrNoDialog commonYesOrNoDialog = new CommonYesOrNoDialog(mContext);
        commonYesOrNoDialog.setTv_content("您还未提交补货记录，是否提交");
        commonYesOrNoDialog.setBtn_cancle("取消");
        commonYesOrNoDialog.setBtn_sure("提交");
        commonYesOrNoDialog.setDialogSureListener(new CommonDialogSureListener() {
            @Override
            public void onSure() {
                mWaitSupFragment.uploadCompletedGoods(sceneSn);
            }
        });
        commonYesOrNoDialog.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        commonYesOrNoDialog.show();
    }

    @Override
    public void setEvent() {

    }

    private void initTablayout() {
        List<String> titles = new ArrayList<>();
        titles.add("待补商品");
        titles.add("全部商品");

        List<Fragment> fragmentList = new ArrayList<>();
        //等待补货
        mWaitSupFragment = new WaitSupContainerManageFragment();
        Bundle bundle = new Bundle();
        bundle.putString("sceneSn", sceneSn);
        bundle.putString("cntrId", cntrId);
        mWaitSupFragment.setArguments(bundle);
        //全部商品
        AllSupContainerManageFragment allGoodsFragment = new AllSupContainerManageFragment();

        fragmentList.add(mWaitSupFragment);
        fragmentList.add(allGoodsFragment);

        mContainerAdapter = new ContainerSupFragmentViewpagerAdapter(getSupportFragmentManager(), fragmentList, titles);
        mViewPager.setAdapter(mContainerAdapter);
        mTablayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onClick(View v) {

    }
}
