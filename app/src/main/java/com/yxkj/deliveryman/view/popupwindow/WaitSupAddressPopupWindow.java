package com.yxkj.deliveryman.view.popupwindow;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.event.WaitSupAddressEvent;
import com.yxkj.deliveryman.adapter.WaitSupAddressAdapter;
import com.yxkj.deliveryman.base.BaseRecyclerViewAdapter;
import com.yxkj.deliveryman.bean.response.SceneListBean;

import org.greenrobot.eventbus.EventBus;


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
    private RecyclerView mLrv;
    public WaitSupAddressAdapter mAddressAdapter;

    public WaitSupAddressPopupWindow(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_wait_sup_address, null);
        setContentView(view);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);

        mLrv = view.findViewById(R.id.lrv_wait_sup);
        mAddressAdapter = new WaitSupAddressAdapter(mContext);
//        RecyclerViewSetUtil.setRecyclerView(mContext, mLrv, mAddressAdapter);
//        mLrv.setPullRefreshEnabled(false);
//        mLrv.setLoadMoreEnabled(false);
        mLrv.setLayoutManager(new LinearLayoutManager(mContext));
        mLrv.setAdapter(mAddressAdapter);
        mAddressAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object data) {
                EventBus.getDefault().post(new WaitSupAddressEvent((SceneListBean.GroupsBean) data));
                dismiss();
            }
        });

    }
}
