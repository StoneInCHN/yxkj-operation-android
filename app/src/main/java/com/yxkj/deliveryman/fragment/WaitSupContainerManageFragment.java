package com.yxkj.deliveryman.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.activity.ContainerManageActivity;
import com.yxkj.deliveryman.adapter.WaitSupGoodsAdapter;
import com.yxkj.deliveryman.application.MyApplication;
import com.yxkj.deliveryman.dao.DBManager;
import com.yxkj.deliveryman.dao.WaitSupGoods;
import com.yxkj.deliveryman.dao.gen.DaoMaster;
import com.yxkj.deliveryman.dao.gen.DaoSession;
import com.yxkj.deliveryman.dao.gen.WaitSupGoodsDao;
import com.yxkj.deliveryman.http.BaseObserver;
import com.yxkj.deliveryman.bean.CommitSupRecordsBean;
import com.yxkj.deliveryman.bean.response.NullBean;
import com.yxkj.deliveryman.bean.response.WaitSupContainerGoodsBean;
import com.yxkj.deliveryman.callback.OnCommon2Listener;
import com.yxkj.deliveryman.constant.UserInfo;
import com.yxkj.deliveryman.event.RestartTakePhotoEvent;
import com.yxkj.deliveryman.http.HttpApi;
import com.yxkj.deliveryman.permission.Permission;
import com.yxkj.deliveryman.permission.RxPermissions;
import com.yxkj.deliveryman.util.LogUtil;
import com.yxkj.deliveryman.util.RecyclerViewSetUtil;
import com.yxkj.deliveryman.util.ToastUtil;
import com.yxkj.deliveryman.util.UploadImageUtil;
import com.yxkj.deliveryman.view.dialog.CommonYesOrNoDialog;
import com.yxkj.deliveryman.view.popupwindow.BottomTakePhotoAndPicPopupWindow;
import com.yxkj.deliveryman.view.popupwindow.CompleteSupPopWindow;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/*
 *  @项目名：  yxkj-operation-android 
 *  @包名：    com.yxkj.deliveryman.fragment
 *  @文件名:   WaitSupContainerManageFragment
 *  @创建者:   hhe
 *  @创建时间:  2017/10/26 16:56
 *  @描述：    待补商品货柜管理
 */
public class WaitSupContainerManageFragment extends Fragment {
    private LRecyclerView mLrv;
    private WaitSupGoodsAdapter mWaitSupGoodsAdapter;

    private View rootView;
    private Activity mActivity;
    private Unbinder unbinder;
    private String sceneSn;
    private String cntrId;
    private Fragment mFragment = this;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_wait_sup_container_manage, container, false);
        sceneSn = getArguments().getString("sceneSn");
        cntrId = getArguments().getString("cntrId");
        unbinder = ButterKnife.bind(this, rootView);
        mActivity = getActivity();
        initRv(rootView);
        getWaitSupplyContainerGoodsList();
        EventBus.getDefault().register(this);
        return rootView;
    }

    private void initRv(View rootView) {
        mLrv = rootView.findViewById(R.id.lrv_fragment_container_manage);
        mWaitSupGoodsAdapter = new WaitSupGoodsAdapter(getActivity(), cntrId, sceneSn);
        RecyclerViewSetUtil.setRecyclerView(getActivity(), mLrv, mWaitSupGoodsAdapter, true);
        mLrv.setPullRefreshEnabled(false);
        mLrv.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPageNum++;
                getWaitSupplyContainerGoodsList();
            }
        });
    }

    private int mPageNum = 1;

    /**
     * 获取待补商品列表
     */
    private void getWaitSupplyContainerGoodsList() {
        ContainerManageActivity activity = (ContainerManageActivity) getActivity();
        HttpApi.getInstance()
                .getWaitSupplyContainerGoodsList(UserInfo.USER_ID, activity.cntrId, mPageNum + "", "10")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<WaitSupContainerGoodsBean>() {
                    @Override
                    protected void onHandleSuccess(WaitSupContainerGoodsBean waitSupContainerGoodsBean) {
                        if (mPageNum == 1) {
                            mWaitSupGoodsAdapter.mGroupsBeanList.clear();
                        }
                        mWaitSupGoodsAdapter.setGroupsBeanList(waitSupContainerGoodsBean.groups);
                        mLrv.refreshComplete(10);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    public List<CommitSupRecordsBean.SupplementRecordsBean> mSupRecordsBeanList = new ArrayList<>();

    /**
     * 上传已补货的商品
     */
    public void uploadCompletedGoods(String sceneSn) {
        List<CommitSupRecordsBean.SupplementRecordsBean> recordsBeanList = getUploadRecordBean();
        if (recordsBeanList.size() == 0) {
            ToastUtil.showShort("您还没有进行补货，请先补货后再上传");
            return;
        }
        CommitSupRecordsBean commitSupRecordsBean = new CommitSupRecordsBean(sceneSn, UserInfo.USER_ID, getUploadRecordBean());

        HttpApi.getInstance()
                .commitSupplementRecord(commitSupRecordsBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<NullBean>() {
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    protected void onHandleSuccess(NullBean nullBean) {
                        ToastUtil.showShort("提交补货记录成功");
                        //提交成功后，需要在本地数据库删除本货柜存储的商品
                        WaitSupGoodsDao dao = getDao();
                        List<WaitSupGoods> list = dao.queryBuilder().list();
                        for (WaitSupGoods goods : list) {
                            if (goods.getCntrId().equals(cntrId) && goods.getSceneId().equals(sceneSn)) {
                                dao.delete(goods);
                            }
                        }
                        getActivity().finish();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    private WaitSupGoodsDao getDao() {
        DaoMaster daoMaster = new DaoMaster(DBManager.getInstance(mActivity).getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        return daoSession.getWaitSupGoodsDao();
    }

    public List<CommitSupRecordsBean.SupplementRecordsBean> getUploadRecordBean() {
        mSupRecordsBeanList.clear();
        for (WaitSupContainerGoodsBean.GroupsBean bean : mWaitSupGoodsAdapter.mGroupsBeanList) {
            if (bean.isSupped) {
                mSupRecordsBeanList.add(new CommitSupRecordsBean.SupplementRecordsBean(Integer.parseInt(bean.id), bean.actualNum));
            }
        }

        return mSupRecordsBeanList;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().removeStickyEvent(RestartTakePhotoEvent.class);
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.bt_take_photo_complete, R.id.bt_pause_sup_goods})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_take_photo_complete:
                showBottomPopupWindow();
                break;
            case R.id.bt_pause_sup_goods:
                uploadCompletedGoods(sceneSn);
                break;
        }
    }

    private void showBottomPopupWindow() {
        BottomTakePhotoAndPicPopupWindow picPopupWindow = new BottomTakePhotoAndPicPopupWindow(getActivity());
        picPopupWindow.showAtLocation(rootView, Gravity.NO_GRAVITY, 0, 0);
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

    private void goTakePhoto() {
        RxPermissions rxPermissions = new RxPermissions(mActivity);
        rxPermissions.setLogging(true);
        Observable.just(rxPermissions).compose(rxPermissions.ensureEach(Manifest.permission.CAMERA)).subscribe(permission -> {
            if (permission.granted) {
                /*跳转相机拍照*/
                UploadImageUtil.doTakePhoto(mFragment);
            } else if (permission.shouldShowRequestPermissionRationale) {

            } else {
                //如果用户选择了不再提醒，那么就会一直走这一步
                CommonYesOrNoDialog commonYesOrNoDialog = new CommonYesOrNoDialog(mActivity);
                commonYesOrNoDialog.setTv_content("请允许系统使用您的相机以及存储权限");
                commonYesOrNoDialog.setBtn_sure("去授权");
                commonYesOrNoDialog.setDialogSureListener(() -> {
                    //引导用户至设置页手动授权
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", MyApplication.getAppContext().getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                });
                commonYesOrNoDialog.show();
            }
        });
    }

    private void goAlbum() {
        RxPermissions rxPermissions = new RxPermissions(mActivity);
        rxPermissions.setLogging(true);
        Observable.just(rxPermissions).compose(rxPermissions.ensureEach(Manifest.permission.WRITE_EXTERNAL_STORAGE))
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            UploadImageUtil.doPickPhotoFromGallery(mFragment);
                        } else if (permission.shouldShowRequestPermissionRationale) {

                        } else {
                            //如果用户选择了不再提醒，那么就会一直走这一步
                            CommonYesOrNoDialog commonYesOrNoDialog = new CommonYesOrNoDialog(mActivity);
                            commonYesOrNoDialog.setTv_content("请允许存储权限");
                            commonYesOrNoDialog.setBtn_sure("去授权");
                            commonYesOrNoDialog.setDialogSureListener(() -> {
                                //引导用户至设置页手动授权
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", MyApplication.getAppContext().getPackageName(), null);
                                intent.setData(uri);
                                startActivity(intent);
                            });
                            commonYesOrNoDialog.show();
                        }
                    }
                });
    }

    private CompleteSupPopWindow completeSupPopWindow;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.e("时间判断1", "" + System.currentTimeMillis());
        UploadImageUtil.dealWithUploadImageOnActivityResult(mActivity, requestCode, resultCode, data,
                new UploadImageUtil.OnCompleteListener() {
                    @Override
                    public void onComplete(Bitmap bitmap) {
                        if (bitmap != null) {
                            mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    completeSupPopWindow = new CompleteSupPopWindow(mActivity);
                                    LogUtil.e("时间判断5", "" + System.currentTimeMillis());
                                    completeSupPopWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);
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
                        mActivity.finish();
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

}
