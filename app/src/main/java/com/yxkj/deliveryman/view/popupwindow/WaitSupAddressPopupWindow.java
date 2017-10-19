package com.yxkj.deliveryman.view.popupwindow;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.adapter.WaitSupAddressAdapter;
import com.yxkj.deliveryman.util.RecyclerViewSetUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


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
    private WaitSupAddressAdapter addressAdapter;

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
        addressAdapter = new WaitSupAddressAdapter(mContext);
        RecyclerViewSetUtil.setRecyclerView(mContext, mLrv, addressAdapter);
        mLrv.setPullRefreshEnabled(false);
        mLrv.setLoadMoreEnabled(false);

    }

    public void setList(String[] list) {
        List<String> addressList = new ArrayList<>();
        Collections.addAll(addressList, list);
        addressAdapter.settList(addressList);
    }
}
