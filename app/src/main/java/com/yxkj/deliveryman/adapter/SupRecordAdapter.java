package com.yxkj.deliveryman.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.activity.ContainerSupRecordActivity;
import com.yxkj.deliveryman.base.BaseRecyclerViewAdapter;
import com.yxkj.deliveryman.base.BaseViewHolder;
import com.yxkj.deliveryman.bean.response.SupRecordBean;
import com.yxkj.deliveryman.util.DateUtil;
import com.yxkj.deliveryman.util.IntentUtil;
import com.yxkj.deliveryman.util.RecyclerViewSetUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 补货记录adapter
 */

public class SupRecordAdapter extends RecyclerView.Adapter {

    private Context mContext;

    public SupRecordAdapter(Context context) {
        mContext = context;
    }

    public List<SupRecordBean.GroupsBean> mBeanList = new ArrayList<>();


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_sup_record, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SupRecordBean.GroupsBean bean = mBeanList.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.tvDate.setText(bean.date);
        viewHolder.tvTotalSupNum.setText("总待补数：" + bean.sumWaitSupplyCount);
        viewHolder.tvTotalSupNum.setText("总补货数：" + bean.sumSupplyCount);

        SupRecordsItemAdapter adapter = new SupRecordsItemAdapter(mContext);
        RecyclerViewSetUtil.setRecyclerView(mContext, viewHolder.mLRecyclerView, adapter);
        viewHolder.mLRecyclerView.setLoadMoreEnabled(false);
        viewHolder.mLRecyclerView.setPullRefreshEnabled(false);

        adapter.mSupRecordItemBeanList.clear();
        adapter.mSupRecordItemBeanList.addAll(bean.supplementList);
        adapter.notifyDataSetChanged();
       /* for (int i = 0; i < mBeanList.size(); i++) {
            View v = View.inflate(mContext, R.layout.item_sup_detail, null);
            viewHolder.llItemSupRecord.addView(v);
            v.setOnCommon1Listener(view -> {
                IntentUtil.openActivity(mContext, ContainerSupRecordActivity.class);
            });
        }*/
    }

    @Override
    public int getItemCount() {
        return mBeanList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate;
        TextView tvWaitNum;
        TextView tvTotalSupNum;
        //LinearLayout llItemSupRecord;
        LRecyclerView mLRecyclerView;

        public ViewHolder(View itemView) {
            super(itemView);

            tvDate = itemView.findViewById(R.id.tv_date_item_sup_record);
            tvWaitNum = itemView.findViewById(R.id.tv_wait_sip_num_item_sup_record);
            tvTotalSupNum = itemView.findViewById(R.id.tv_sup_num_item_sup_record);
            //llItemSupRecord = itemView.findViewById(R.id.ll_item_sup_record);
            mLRecyclerView = itemView.findViewById(R.id.lrv_item_sup_record);
        }

    }


}
