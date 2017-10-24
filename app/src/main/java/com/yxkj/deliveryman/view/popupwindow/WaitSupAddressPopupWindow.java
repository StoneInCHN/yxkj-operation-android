package com.yxkj.deliveryman.view.popupwindow;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.adapter.WaitSupAddressAdapter;
import com.yxkj.deliveryman.base.BaseObserver;
import com.yxkj.deliveryman.http.HttpApi;
import com.yxkj.deliveryman.response.SceneListBean;
import com.yxkj.deliveryman.sharepreference.SharePrefreceHelper;
import com.yxkj.deliveryman.sharepreference.SharedKey;
import com.yxkj.deliveryman.util.RecyclerViewSetUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/*
 *  @项目名：  yxkj-operation-android 
 *  @包名：    com.yxkj.deliveryman.view.popupwindow
 *  @文件名:   WaitSupAddressPopupWindow
 *  @创建者:   hhe
 *  @创建时间:  2017/10/18 14:43
 *  @描述：    待补清单地址列表弹出框
 */
public class WaitSupAddressPopupWindow extends PopupWindow {

    private Context mContext;
    private LRecyclerView mLrv;
    private WaitSupAddressAdapter mAddressAdapter;

    public WaitSupAddressPopupWindow(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_wait_sup_address, null);
        setContentView(view);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);

        mLrv = view.findViewById(R.id.lrv_wait_sup);
        mAddressAdapter = new WaitSupAddressAdapter(mContext);
        RecyclerViewSetUtil.setRecyclerView(mContext, mLrv, mAddressAdapter);
        mLrv.setPullRefreshEnabled(false);
        mLrv.setLoadMoreEnabled(false);

    }


    public void getWaitSupList() {
        HttpApi.getInstance()
                .getWaitSupplySceneList(SharePrefreceHelper.getInstance().getString(SharedKey.USER_ID))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<SceneListBean>() {
                    @Override
                    protected void onHandleSuccess(SceneListBean sceneListBean) {
                        mAddressAdapter.settList(sceneListBean.groups);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });


    }


}
