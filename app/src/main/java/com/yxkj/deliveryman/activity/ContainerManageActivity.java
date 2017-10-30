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
import com.yxkj.deliveryman.fragment.ContainerManageFragment;
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
    private Button btTakePhoto;
    private Button btPauseSup;
    private ContainerSupFragmentViewpagerAdapter mContainerAdapter;
    private CompleteSupPopWindow completeSupPopWindow;
    ;
    private RichToolBar mToolbar;
    private ContainerManageFragment mWaitSupFragment;

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
        btTakePhoto = findViewByIdNoCast(R.id.bt_take_photo_complete);
        btPauseSup = findViewByIdNoCast(R.id.bt_pause_sup_goods);

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

        EventBus.getDefault().register(this);
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
        setOnClick(btTakePhoto, btPauseSup);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_take_photo_complete:
                showBottomPopupWindow();
                break;
            case R.id.bt_pause_sup_goods://上传设置为已补货的商品
                mWaitSupFragment.uploadCompletedGoods(sceneSn);
                break;
        }
    }

    private void showBottomPopupWindow() {
        BottomTakePhotoAndPicPopupWindow picPopupWindow = new BottomTakePhotoAndPicPopupWindow(this);
        picPopupWindow.showAtLocation(btTakePhoto, Gravity.NO_GRAVITY, 0, 0);
        picPopupWindow.setOnTakePhotoListener(new BottomTakePhotoAndPicPopupWindow.OnTakePhotoListener() {
            @Override
            public void onTakePhto() {
                goTakePhoto();
                picPopupWindow.dismiss();
            }

            @Override
            public void onGetFromAlbum() {
                goAlbum();
                picPopupWindow.dismiss();
            }
        });
    }

    private void initTablayout() {
        List<String> titles = new ArrayList<>();
        titles.add("待补商品");
        titles.add("全部商品");

        List<Fragment> fragmentList = new ArrayList<>();
        //等待补货
        mWaitSupFragment = new ContainerManageFragment();
        Bundle bundle = new Bundle();
        bundle.putString("fragment_type", "wait_sup");
        mWaitSupFragment.setArguments(bundle);
        //全部商品
        ContainerManageFragment allGoodsFragment = new ContainerManageFragment();
        bundle.putString("fragment_type", "all_goods");
        allGoodsFragment.setArguments(bundle);

        fragmentList.add(mWaitSupFragment);
        fragmentList.add(allGoodsFragment);

        mContainerAdapter = new ContainerSupFragmentViewpagerAdapter(getSupportFragmentManager(), fragmentList, titles);
        mViewPager.setAdapter(mContainerAdapter);
        mTablayout.setupWithViewPager(mViewPager);
    }

    private void goTakePhoto() {
        RxPermissions rxPermissions = new RxPermissions(mActivity);
        rxPermissions.setLogging(true);
        Observable.just(rxPermissions).compose(rxPermissions.ensureEach(Manifest.permission.CAMERA)).subscribe(permission -> {
            if (permission.granted) {
                /*跳转相机拍照*/
                UploadImageUtil.doTakePhoto(ContainerManageActivity.this);
            } else if (permission.shouldShowRequestPermissionRationale) {

            } else {
                //如果用户选择了不再提醒，那么就会一直走这一步
                CommonYesOrNoDialog commonYesOrNoDialog = new CommonYesOrNoDialog(mContext);
                commonYesOrNoDialog.setTv_content("请允许系统使用您的相机以及存储权限");
                commonYesOrNoDialog.setBtn_sure("去授权");
                commonYesOrNoDialog.setDialogSureListener(() -> {
                    //引导用户至设置页手动授权
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", ContainerManageActivity.this.getApplicationContext().getPackageName(), null);
                    intent.setData(uri);
                    ContainerManageActivity.this.startActivity(intent);
                });
                commonYesOrNoDialog.show();
            }
        });


    }

    private void goAlbum() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.setLogging(true);
        Observable.just(rxPermissions).compose(rxPermissions.ensureEach(Manifest.permission.WRITE_EXTERNAL_STORAGE))
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            UploadImageUtil.doPickPhotoFromGallery((Activity) mContext);
                        } else if (permission.shouldShowRequestPermissionRationale) {

                        } else {
                            //如果用户选择了不再提醒，那么就会一直走这一步
                            CommonYesOrNoDialog commonYesOrNoDialog = new CommonYesOrNoDialog(mContext);
                            commonYesOrNoDialog.setTv_content("请允许存储权限");
                            commonYesOrNoDialog.setBtn_sure("去授权");
                            commonYesOrNoDialog.setDialogSureListener(() -> {
                                //引导用户至设置页手动授权
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", getApplicationContext().getPackageName(), null);
                                intent.setData(uri);
                                startActivity(intent);
                            });
                            commonYesOrNoDialog.show();
                        }
                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.e("时间判断1", "" + System.currentTimeMillis());
        File picResultFile = UploadImageUtil.dealWithUploadImageOnActivityResult(this, requestCode, resultCode, data,
                new UploadImageUtil.OnCompleteListener() {
                    @Override
                    public void onComplete(Bitmap bitmap) {
                        if (bitmap != null) {
                            ContainerManageActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    completeSupPopWindow = new CompleteSupPopWindow(mContext);
                                    LogUtil.e("时间判断5", "" + System.currentTimeMillis());
                                    completeSupPopWindow.showAtLocation(btTakePhoto, Gravity.CENTER, 0, 0);
                                    completeSupPopWindow.setBitmaps(bitmap);
                                    LogUtil.e("时间判断5.3", "" + System.currentTimeMillis());
                                    completeSupPopWindow.setCommon2Listener(new OnCommon2Listener<String, File>() {
                                        @Override
                                        public void onCommon1(String s) {
                                            //重新拍照
                                            goTakePhoto();
                                        }

                                        @Override
                                        public void onCommon2(File file) {
                                            //上传图片，完成补货
                                            completeSup(file);
                                        }
                                    });
                                    LogUtil.e("时间判断6", "" + System.currentTimeMillis());
                                }
                            });
                        }
                    }
                });
    }


    /**
     * 完成补货，上传图片
     *
     * @param file
     */
    private void completeSup(File file) {
        if (file == null) {
            return;
        }
        HttpApi.getInstance()
                .uploadSupplementPic(UserInfo.USER_ID, cntrId, file)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<NullBean>() {
                    @Override
                    protected void onHandleSuccess(NullBean bean) {
                        ToastUtil.showShort("上传成功,该货柜补货完成");
                        completeSupPopWindow.dismiss();
                        finish();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void restartTakePhoto(RestartTakePhotoEvent event) {
        goTakePhoto();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().removeStickyEvent(RestartTakePhotoEvent.class);
        EventBus.getDefault().unregister(this);
    }
}
