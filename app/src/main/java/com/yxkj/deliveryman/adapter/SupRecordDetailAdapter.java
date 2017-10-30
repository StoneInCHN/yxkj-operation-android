package com.yxkj.deliveryman.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.bean.response.SupRecordDetailBean;
import com.yxkj.deliveryman.util.RecyclerViewSetUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 补货记录详情
 */

public class SupRecordDetailAdapter extends RecyclerView.Adapter {
    private Context mContext;

    public SupRecordDetailAdapter(Context context) {
        mContext = context;
    }

    public List<SupRecordDetailBean.GroupsBean> mGroupsBeanList = new ArrayList<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_record_sup_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.tvTitle.setText(mGroupsBeanList.get(position).cntrSn + "货柜");
        SupRecordDetailItemAdapter adapter = new SupRecordDetailItemAdapter(mContext);
        RecyclerViewSetUtil.setRecyclerView(mContext, viewHolder.mLrvItem, adapter);
        viewHolder.mLrvItem.setPullRefreshEnabled(false);
        viewHolder.mLrvItem.setLoadMoreEnabled(false);

        adapter.mSupplementRecordsBeanList.addAll(mGroupsBeanList.get(position).cntrSupplementRecords);
        adapter.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mGroupsBeanList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        LRecyclerView mLrvItem;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title_item_record_sup_detail);
            mLrvItem = itemView.findViewById(R.id.lrv_item_item_record_sup_detail);
        }
    }
}
