package com.yxkj.deliveryman.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.base.BaseRecyclerViewAdapter;
import com.yxkj.deliveryman.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;


/**
 * 补货列表
 */

public class ReplenishAdapter extends BaseRecyclerViewAdapter<String> {
    private List<String> list = new ArrayList<>();

    public ReplenishAdapter(Context context) {
        super(context);
        list.add("e");
        list.add("e");
    }


    @Override
    public int getLayoutId() {
        return R.layout.item_replenish;
    }

    @Override
    public void onCorvert(BaseViewHolder holder, int position, String bean) {
        RecyclerView recyclerView = holder.getView(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        TeamAdapter teamAdapter = new TeamAdapter(context);
        teamAdapter.settList(list);
        recyclerView.setAdapter(teamAdapter);
    }
}
