package com.yxkj.deliveryman.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.activity.ControllerManageActivity;
import com.yxkj.deliveryman.base.BaseRecyclerViewAdapter;
import com.yxkj.deliveryman.base.BaseViewHolder;
import com.yxkj.deliveryman.util.IntentUtil;
import com.yxkj.deliveryman.util.ToastUtil;
import com.yxkj.deliveryman.view.dialog.TextButtonDialog;
import com.yxkj.deliveryman.view.dialog.TextShortTimeDialog;
import com.yxkj.deliveryman.view.popupwindow.ContainerSupPopWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * 货柜分组
 */

class TeamAdapter extends BaseRecyclerViewAdapter<String> {
    private List<String> list = new ArrayList<>();

    public TeamAdapter(Context context) {
        super(context);
        for (int i = 0; i < 6; i++) {
            list.add(i + "");
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_team;
    }

    @Override
    public void onCorvert(BaseViewHolder holder, int position, String bean) {
        //某组里面的机器list
        RecyclerView recyclerView = holder.getView(R.id.rv_team_member);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
        ContainerAdapter containerAdapter = new ContainerAdapter(context);
        containerAdapter.settList(list);
        recyclerView.setAdapter(containerAdapter);
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
