package com.yxkj.deliveryman.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.response.WaitSupStateBean;

import java.util.ArrayList;
import java.util.List;


/**
 * 补货列表
 */

public class SupplementAdapter extends RecyclerView.Adapter<SupplementAdapter.ViewHolder> {
    private List<WaitSupStateBean.ScenesBean> mScenesBeanList = new ArrayList<>();
    private List<WaitSupStateBean.ScenesBean.VendingContainerGroupsBean> mGroupsBeanList = new ArrayList<>();
    private Context mContext;

    public SupplementAdapter(Context context) {
        mContext = context;
    }

    public void setScenesBeanList(List<WaitSupStateBean.ScenesBean> scenesBeanList) {
        mScenesBeanList = scenesBeanList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_replenish, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //某楼里面按组分的list
        holder.rvGroups.setLayoutManager(new LinearLayoutManager(mContext));
        TeamAdapter teamAdapter = new TeamAdapter(mContext);
        holder.rvGroups.setAdapter(teamAdapter);
        teamAdapter.settList(mGroupsBeanList);
    }

    @Override
    public int getItemCount() {
        return mScenesBeanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView rvGroups;

        public ViewHolder(View itemView) {
            super(itemView);
            rvGroups = itemView.findViewById(R.id.rv_group);
        }
    }
}
