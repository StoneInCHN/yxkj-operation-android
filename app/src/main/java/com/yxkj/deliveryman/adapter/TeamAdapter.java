package com.yxkj.deliveryman.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.activity.ContainerManageActivity;
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
        TextView tvTeam = holder.getView(R.id.tv_teamnum_item_team);
        tvTeam.setText((position + 1) + "组");
        RecyclerView rvTeamMember = holder.getView(R.id.rv_team_member);
        rvTeamMember.setLayoutManager(new GridLayoutManager(context, 3));
        ContainerMemberAdapter containerAdapter = new ContainerMemberAdapter(context);
        rvTeamMember.setAdapter(containerAdapter);
        containerAdapter.settList(bean.vendingContainers);
        containerAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object data) {
                if (bean.vendingContainers.get(position).central) {//中控台
                    IntentUtil.openActivity(context, ControllerManageActivity.class);
                } else {//其他货柜
                    IntentUtil.openActivity(context, ContainerManageActivity.class);
                }

            }
        });
    }
}
