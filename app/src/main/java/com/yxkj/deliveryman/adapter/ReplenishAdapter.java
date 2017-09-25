package com.yxkj.deliveryman.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.util.DisplayUtil;
import com.yxkj.deliveryman.view.CanNotScrollGridLayoutManager;
import com.yxkj.deliveryman.view.ContainerSupPopWindow;

import java.util.ArrayList;
import java.util.List;


/**
 * 补货列表
 */

public class ReplenishAdapter extends RecyclerView.Adapter<ReplenishAdapter.ViewHolder> {
    private List<String> strings = new ArrayList<>();
    private Context context;

    public ReplenishAdapter(List<String> strings, Context context) {
        this.strings = strings;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_replenish, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        for (int i = 0; i < 2; i++) {
            RecyclerView recyclerView = new RecyclerView(context);
            recyclerView.setEnabled(false);
            TextView text = new TextView(context);
            text.setText(i + "组");
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(10, 10, 10, 10);
            RecyclerView.LayoutParams lpText = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lpText.setMargins(DisplayUtil.dip2px(context, 10), 5, 5, 5);
            recyclerView.setLayoutParams(lp);
            text.setLayoutParams(lpText);
            recyclerView.setBackgroundResource(R.drawable.replenish_bg);
            recyclerView.setEnabled(false);
            recyclerView.setLayoutManager(new CanNotScrollGridLayoutManager(context, 4));
            ContainerAdapter teamAdpter = new ContainerAdapter(context);
            teamAdpter.settList(strings);
            teamAdpter.setOnItemClickListener((position1, data) -> {
                ContainerSupPopWindow popWindow = new ContainerSupPopWindow(context);
                popWindow.showAtLocation(holder.itemView, Gravity.NO_GRAVITY, 0, 0);
            });
            recyclerView.setAdapter(teamAdpter);
            holder.recyclerView.addView(text);
            holder.recyclerView.addView(recyclerView);
        }
    }

    @Override
    public int getItemCount() {
        return strings == null ? 0 : strings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout recyclerView;

        public ViewHolder(View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recyclerView);
        }
    }
}
