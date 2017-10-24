package com.yxkj.deliveryman.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.response.WaitSupStateBean;
import com.yxkj.deliveryman.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * 按地址的补货列表
 */

public class AddressSupAdapter extends RecyclerView.Adapter<AddressSupAdapter.ViewHolder> {
    public List<WaitSupStateBean.ScenesBean> mScenesBeanList = new ArrayList<>();
    private List<WaitSupStateBean.ScenesBean.VendingContainerGroupsBean> mGroupsBeanList = new ArrayList<>();
    private Context mContext;

    public AddressSupAdapter(Context context) {
        mContext = context;
    }

    public void setMoreList(List<WaitSupStateBean.ScenesBean> scenesBeanList) {
        mScenesBeanList.addAll(scenesBeanList);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_replenish, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        WaitSupStateBean.ScenesBean bean = mScenesBeanList.get(position);
        holder.tvAddress.setText(bean.sceneName);
        holder.tvNum.setText(bean.sceneSn);
        holder.tvFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2017/10/24
                ToastUtil.showShort("完成补货");
            }
        });
        //某楼里面按组分的list
        holder.rvGroups.setLayoutManager(new LinearLayoutManager(mContext));
        TeamAdapter teamAdapter = new TeamAdapter(mContext);
        holder.rvGroups.setAdapter(teamAdapter);
        teamAdapter.settList(mScenesBeanList.get(position).vendingContainerGroups);
    }

    @Override
    public int getItemCount() {
        return mScenesBeanList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView rvGroups;
        TextView tvAddress;
        TextView tvNum;
        TextView tvFinish;

        ViewHolder(View itemView) {
            super(itemView);
            rvGroups = itemView.findViewById(R.id.rv_group);
            tvAddress = itemView.findViewById(R.id.tv_address_item_replenish);
            tvNum = itemView.findViewById(R.id.tv_num_item_replenish);
            tvFinish = itemView.findViewById(R.id.tv_finish_item_replenish);
        }
    }
}
