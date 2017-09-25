package com.yxkj.deliveryman.frament;


import android.view.View;
import android.widget.TextView;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.activity.SupRecordActivity;
import com.yxkj.deliveryman.activity.WaitSupplementActivity;
import com.yxkj.deliveryman.adapter.ReplenishAdapter;
import com.yxkj.deliveryman.base.BaseFragment;
import com.yxkj.deliveryman.callback.MainPageClickListener;
import com.yxkj.deliveryman.util.IntentUtil;
import com.yxkj.deliveryman.util.RecyclerViewSetUtil;
import com.yxkj.deliveryman.view.MainPagePopupWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页货柜页
 */
public class ContainerFragment extends BaseFragment implements MainPageClickListener {
    /*更多*/
    private TextView tv_more;
    /*补货列表*/
    private LRecyclerView recyclerView;
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
        tv_more = findViewByIdNoCast(R.id.tv_more);
        recyclerView = findViewByIdNoCast(R.id.recyclerView);
    }

    @Override
    protected void initData() {
        replenishAdapter = new ReplenishAdapter(getData(), getActivity());
        RecyclerViewSetUtil.setRecyclerView(getActivity(), recyclerView, replenishAdapter);
    }

    @Override
    protected void setEvent() {
        setOnClick(tv_more);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_more:
                MainPagePopupWindow pagePopupWindow = new MainPagePopupWindow(getActivity());
                pagePopupWindow.showAsDropDown(tv_more);
                pagePopupWindow.setClickListener(this);
                break;
        }
    }

    private List<String> getData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
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
