package com.yxkj.deliveryman.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.base.BaseRecyclerViewAdapter;
import com.yxkj.deliveryman.base.BaseViewHolder;
import com.yxkj.deliveryman.view.ContainerSupPopWindow;

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
            list.add(i+"");
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_team;
    }

    @Override
    public void onCorvert(BaseViewHolder holder, int position, String bean) {
        RecyclerView recyclerView = holder.getView(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 4));
        ContainerAdapter containerAdapter = new ContainerAdapter(context);
        containerAdapter.settList(list);
        recyclerView.setAdapter(containerAdapter);
        containerAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object data) {
                ContainerSupPopWindow popWindow = new ContainerSupPopWindow(context);
                popWindow.showAtLocation(holder.itemView, Gravity.NO_GRAVITY, 0, 0);
            }
        });
    }
}