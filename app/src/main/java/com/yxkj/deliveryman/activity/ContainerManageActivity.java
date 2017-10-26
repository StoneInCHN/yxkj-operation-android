package com.yxkj.deliveryman.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.adapter.ContainerSupFragmentViewpagerAdapter;
import com.yxkj.deliveryman.adapter.WaitSupListAdapter;
import com.yxkj.deliveryman.base.BaseActivity;
import com.yxkj.deliveryman.callback.CommenDialogSureListener;
import com.yxkj.deliveryman.fragment.ContainerManageFragment;
import com.yxkj.deliveryman.permission.RxPermissions;
import com.yxkj.deliveryman.util.RecyclerViewSetUtil;
import com.yxkj.deliveryman.util.UploadImageUtil;
import com.yxkj.deliveryman.view.popupwindow.BotomPopWindow;
import com.yxkj.deliveryman.view.dialog.CommonYesOrNoDialog;
import com.yxkj.deliveryman.view.popupwindow.CompleteSupPopWindow;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * 货柜管理
 */
public class ContainerManageActivity extends BaseActivity implements CommenDialogSureListener {
    private LRecyclerView recyclerView;
    private TabLayout mTablayout;
    /**
     * 拍照
     */
    private Button btn_photograph;
    private WaitSupListAdapter adapter;
    private CompleteSupPopWindow completeSupPopWindow;
    private ViewPager mViewPager;
    private ContainerSupFragmentViewpagerAdapter mContainerAdapter;

    @Override
    public int getContentViewId() {
        return R.layout.activity_container_manage;
    }

    /**
     * 货柜ID
     */
    public String cntrId;

    @Override
    public void beforeInitView() {
        cntrId = getIntent().getExtras().getString("cntrId");
    }

    @Override
    public void initView() {
        mViewPager = findViewByIdNoCast(R.id.vp_container_manage);
        recyclerView = findViewByIdNoCast(R.id.recyclerView);
        mTablayout = findViewByIdNoCast(R.id.tab_container_manage);
        btn_photograph = findViewByIdNoCast(R.id.btn_photograph);
    }

    @Override
    public void initData() {
        initTablayout();

    }

    @Override
    public void setEvent() {
        setOnClick(btn_photograph);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_photograph:
                BotomPopWindow pupWIndow = new BotomPopWindow(this);
                pupWIndow.showAtLocation(btn_photograph, Gravity.NO_GRAVITY, 0, 0);
                pupWIndow.setCommenDialogSureListener(this);
                break;
        }
    }

    private List<Fragment> mFragmentList;
    private List<String> mTitles;

    private void initTablayout() {
        mTitles = new ArrayList<>();
        mTitles.add("待补商品");
        mTitles.add("全部商品");

        mFragmentList = new ArrayList<>();
        ContainerManageFragment waitSupFragment = new ContainerManageFragment();
        ContainerManageFragment allGoodsFragment = new ContainerManageFragment();
        mFragmentList.add(waitSupFragment);
        mFragmentList.add(allGoodsFragment);

        mContainerAdapter = new ContainerSupFragmentViewpagerAdapter(getSupportFragmentManager(), mFragmentList, mTitles);
        mViewPager.setAdapter(mContainerAdapter);
        mTablayout.setupWithViewPager(mViewPager);
    }

    private List<String> getData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            list.add(i + "");
        }
        return list;
    }

    /**
     * 点击了拍照
     */
    @Override
    public void onSure() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.setLogging(true);
        Observable.just(rxPermissions).compose(rxPermissions.ensureEach(Manifest.permission.CAMERA)).subscribe(permission -> {
            if (permission.granted) {
                /*跳转相机拍照*/
                UploadImageUtil.doTakePhoto(this);
            } else if (permission.shouldShowRequestPermissionRationale) {

            } else {
                //如果用户选择了不再提醒，那么就会一直走这一步
                CommonYesOrNoDialog commonYesOrNoDialog = new CommonYesOrNoDialog();
                commonYesOrNoDialog.setTv_content("请允许系统使用您的相机");
                commonYesOrNoDialog.setBtn_sure("去授权");
                commonYesOrNoDialog.setDialogSureListener(() -> {
                    //引导用户至设置页手动授权
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getApplicationContext().getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UploadImageUtil.dealWithUploadImageOnActivityResult(this, requestCode, resultCode, data,
                (final Bitmap bitmap) -> {
                    if (bitmap != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                completeSupPopWindow.setBitmaps(bitmap);
                                completeSupPopWindow.showAtLocation(btn_photograph, Gravity.CENTER, 0, 0);
                            }
                        });
                    }
                });
    }
}
