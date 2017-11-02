package com.yxkj.deliveryman.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.adapter.ContainerSupFragmentViewpagerAdapter;
import com.yxkj.deliveryman.base.BaseActivity;
import com.yxkj.deliveryman.callback.CommonDialogSureListener;
import com.yxkj.deliveryman.fragment.AllSupContainerManageFragment;
import com.yxkj.deliveryman.fragment.WaitSupContainerManageFragment;
import com.yxkj.deliveryman.view.RichToolBar;
import com.yxkj.deliveryman.view.dialog.CommonYesOrNoDialog;

import java.util.ArrayList;
import java.util.List;

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
        commonYesOrNoDialog.setTv_content("你尚未完成补货，是否要暂停补货？");
        commonYesOrNoDialog.setTv_title("提示");
        commonYesOrNoDialog.setBtn_cancle("取消");
        commonYesOrNoDialog.setBtn_sure("暂停");
        commonYesOrNoDialog.setDialogSureListener(new CommonDialogSureListener() {
            @Override
            public void onSure() {
                mWaitSupFragment.uploadCompletedGoods(sceneSn);
            }
        });
        commonYesOrNoDialog.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commonYesOrNoDialog.dismiss();
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
