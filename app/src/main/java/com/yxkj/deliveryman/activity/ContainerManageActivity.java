package com.yxkj.deliveryman.activity;

import android.Manifest;
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
import com.yxkj.deliveryman.callback.OnCommon2Listener;
import com.yxkj.deliveryman.fragment.ContainerManageFragment;
import com.yxkj.deliveryman.http.HttpApi;
import com.yxkj.deliveryman.permission.RxPermissions;
import com.yxkj.deliveryman.response.NullBean;
import com.yxkj.deliveryman.sharepreference.SharePrefreceHelper;
import com.yxkj.deliveryman.sharepreference.SharedKey;
import com.yxkj.deliveryman.util.ToastUtil;
import com.yxkj.deliveryman.util.UploadImageUtil;
import com.yxkj.deliveryman.view.RichToolBar;
import com.yxkj.deliveryman.view.popupwindow.BottomTakePhotoAndPicPopupWindow;
import com.yxkj.deliveryman.view.dialog.CommonYesOrNoDialog;
import com.yxkj.deliveryman.view.popupwindow.CompleteSupPopWindow;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
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
    private RichToolBar mToolbar;

    @Override
    public int getContentViewId() {
        return R.layout.activity_container_manage;
    }

    /**
     * 货柜ID
     */
    public String cntrId;
    public String containerName;

    @Override
    public void beforeInitView() {
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
            case R.id.bt_pause_sup_goods:

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
        ContainerManageFragment waitSupFragment = new ContainerManageFragment();
        Bundle bundle = new Bundle();
        bundle.putString("fragment_type", "wait_sup");
        waitSupFragment.setArguments(bundle);
        //全部商品
        ContainerManageFragment allGoodsFragment = new ContainerManageFragment();
        bundle.putString("fragment_type", "all_goods");
        allGoodsFragment.setArguments(bundle);

        fragmentList.add(waitSupFragment);
        fragmentList.add(allGoodsFragment);

        mContainerAdapter = new ContainerSupFragmentViewpagerAdapter(getSupportFragmentManager(), fragmentList, titles);
        mViewPager.setAdapter(mContainerAdapter);
        mTablayout.setupWithViewPager(mViewPager);
    }

    private void goTakePhoto() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.setLogging(true);
        Observable.just(rxPermissions)
                .compose(rxPermissions.ensureEach(Manifest.permission.CAMERA))
                .subscribe(permission -> {
                    if (permission.granted) {
                        //跳转相机拍照
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

    private void goAlbum() {
        RxPermissions rxPermissions = new RxPermissions(this);
        Observable.just(rxPermissions)
                .compose(rxPermissions.ensureEach(Manifest.permission.READ_EXTERNAL_STORAGE))
                .subscribe(permission -> {
                    if (permission.granted) {
                        //跳转相机拍照
                        UploadImageUtil.doPickPhotoFromGallery(this);
                    } else if (permission.shouldShowRequestPermissionRationale) {

                    } else {
                        //如果用户选择了不再提醒，那么就会一直走这一步
                        CommonYesOrNoDialog commonYesOrNoDialog = new CommonYesOrNoDialog();
                        commonYesOrNoDialog.setTv_content("请允许读取相册");
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
        File picResultFile = UploadImageUtil.dealWithUploadImageOnActivityResult(this, requestCode, resultCode, data,
                (final Bitmap bitmap) -> {
                    if (bitmap != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                completeSupPopWindow = new CompleteSupPopWindow(mContext);
                                completeSupPopWindow.showAtLocation(btTakePhoto, Gravity.CENTER, 0, 0);
                                completeSupPopWindow.setBitmaps(bitmap);

                                completeSupPopWindow.setCommon2Listener(new OnCommon2Listener<String, File>() {
                                    @Override
                                    public void onCommon1(String s) {
                                        //重新拍照

                                    }

                                    @Override
                                    public void onCommon2(File file) {
                                        //上传图片，完成补货
                                        completeSup(file);
                                    }
                                });
                            }
                        });
                    }
                });
    }


    /**
     * 完成补货，上传图片
     *
     * @param file
     */
    private void completeSup(File file) {
        String userId = SharePrefreceHelper.getInstance().getString(SharedKey.USER_ID);
        HttpApi.getInstance()
                .uploadSupplementPic(userId, cntrId, file)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<NullBean>() {
                    @Override
                    protected void onHandleSuccess(NullBean bean) {
                        ToastUtil.showShort("上传成功,该货柜补货完成");
                        finish();

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }
}
