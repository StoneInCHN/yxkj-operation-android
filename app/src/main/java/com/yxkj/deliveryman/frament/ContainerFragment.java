package com.yxkj.deliveryman.frament;


import android.view.View;
import android.widget.ImageView;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.activity.SupRecordActivity;
import com.yxkj.deliveryman.activity.WaitSupplementActivity;
import com.yxkj.deliveryman.adapter.ReplenishAdapter;
import com.yxkj.deliveryman.base.BaseFragment;
import com.yxkj.deliveryman.callback.MainPageClickListener;
import com.yxkj.deliveryman.util.IntentUtil;
import com.yxkj.deliveryman.util.RecyclerViewSetUtil;
import com.yxkj.deliveryman.view.popupwindow.MainPagePopupWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 首页货柜页
 */
public class ContainerFragment extends BaseFragment implements MainPageClickListener {
    /**
     * 添加
     */
    @BindView(R.id.iv_add_fragment_container)
     ImageView mIvAdd;
    /**
     * 补货列表
     */
    @BindView(R.id.lrv_fragment_container)
     LRecyclerView mLrv;
    /*补货列表适配器*/
    private ReplenishAdapter replenishAdapter;

    @Override
    protected int getResource() {
        return R.layout.fragment_container;
    }

    @Override
    protected void beforeInitView() {

    }

    @Override
    protected void initView(View rootView) {

    }

    @Override
    protected void initData() {
        //按大楼分的list
        replenishAdapter = new ReplenishAdapter(getData(), getActivity());
        RecyclerViewSetUtil.setRecyclerView(getActivity(), mLrv, replenishAdapter);
    }

    @Override
    protected void setEvent() {
        setOnClick(mIvAdd);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_add_fragment_container:
                MainPagePopupWindow pagePopupWindow = new MainPagePopupWindow(getActivity());
                pagePopupWindow.showAsDropDown(mIvAdd);
                pagePopupWindow.setClickListener(this);
                break;
        }
    }

    private List<String> getData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            list.add(i + "");
        }
        return list;
    }

    @Override
    public void onTvreplenish() {
        IntentUtil.openActivity(getActivity(), WaitSupplementActivity.class);
    }

    @Override
    public void onTvreplenishRecord() {
        IntentUtil.openActivity(getActivity(), SupRecordActivity.class);
    }
}
