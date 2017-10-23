package com.yxkj.deliveryman.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.activity.ControllerManageActivity;
import com.yxkj.deliveryman.base.BaseRecyclerViewAdapter;
import com.yxkj.deliveryman.base.BaseViewHolder;
import com.yxkj.deliveryman.response.WaitSupStateBean;
import com.yxkj.deliveryman.util.IntentUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 货柜分组
 */

public class TeamAdapter extends BaseRecyclerViewAdapter<WaitSupStateBean.ScenesBean.VendingContainerGroupsBean> {
    private List<WaitSupStateBean.ScenesBean.VendingContainerGroupsBean> mVendingContainerGroupsBeanList = new ArrayList<>();
    private List<WaitSupStateBean.ScenesBean.VendingContainerGroupsBean.VendingContainersBean> mContainersBeanList = new ArrayList<>();

    public TeamAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_team;
    }

    @Override
    public void onCorvert(BaseViewHolder holder, int position, WaitSupStateBean.ScenesBean.VendingContainerGroupsBean bean) {
        //某组里面的机器list
        RecyclerView rvTeamMember = holder.getView(R.id.rv_team_member);
        rvTeamMember.setLayoutManager(new GridLayoutManager(context, 3));
        ContainerAdapter containerAdapter = new ContainerAdapter(context);
        rvTeamMember.setAdapter(containerAdapter);
        containerAdapter.settList(mContainersBeanList);
        containerAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object data) {
                /*ContainerSupPopWindow popWindow = new ContainerSupPopWindow(context);
                popWindow.showAtLocation(holder.itemView, Gravity.NO_GRAVITY, 0, 0);*/
                IntentUtil.openActivity(context, ControllerManageActivity.class);
            }
        });
    }
}
