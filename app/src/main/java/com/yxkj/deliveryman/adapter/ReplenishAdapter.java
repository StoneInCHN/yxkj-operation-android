package com.yxkj.deliveryman.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yxkj.deliveryman.R;

import java.util.ArrayList;
import java.util.List;


/**
 * 补货列表
 */

public class ReplenishAdapter extends RecyclerView.Adapter<ReplenishAdapter.ViewHolder> {
    private List<String> strings = new ArrayList<>();
    private List<String> list = new ArrayList<>();
    private Context context;

    public ReplenishAdapter(List<String> strings, Context context) {
        this.strings = strings;
        this.context = context;
        list.add("e");
        list.add("e");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_replenish, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        TeamAdapter teamAdapter = new TeamAdapter(context);
        teamAdapter.settList(list);
        holder.recyclerView.setAdapter(teamAdapter);
    }

    @Override
    public int getItemCount() {
        return strings == null ? 0 : strings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;

        public ViewHolder(View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recyclerView);
        }
    }
}
