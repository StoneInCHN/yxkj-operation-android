package com.yxkj.deliveryman.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.bean.SupRecordItemBean;
import com.yxkj.deliveryman.bean.response.SupRecordBean;

import java.util.ArrayList;
import java.util.List;


/*
 *  @项目名：  yxkj-operation-android 
 *  @包名：    com.yxkj.deliveryman.adapter
 *  @文件名:   SupRecordsItemAdapter
 *  @创建者:   hhe
 *  @创建时间:  2017/10/30 18:15
 *  @描述：    补货记录内层列表
 */
public class SupRecordsItemAdapter extends RecyclerView.Adapter {
    private Context mContext;

    public SupRecordsItemAdapter(Context context) {
        mContext = context;
    }

    public List<SupRecordBean.GroupsBean.SupplementListBean> mSupRecordItemBeanList = new ArrayList<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_sup_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SupRecordBean.GroupsBean.SupplementListBean bean = mSupRecordItemBeanList.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.tvName.setText(bean.sceneName);
        viewHolder.tvTime.setText(bean.supplyTime);
        viewHolder.tvOrder.setText(bean.sceneSn);
        viewHolder.tvWaitNum.setText("总待补数:" + bean.waitSupplyCount);
        viewHolder.tvLackNum.setText("总缺货数:" + bean.lackCount);
        viewHolder.tvTotalSupNUm.setText("总补货数:" + bean.supplyCount);

    }

    @Override
    public int getItemCount() {
        return mSupRecordItemBeanList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvTime;
        TextView tvOrder;
        TextView tvWaitNum;
        TextView tvLackNum;
        TextView tvTotalSupNUm;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name_item_sup_records);
            tvTime = itemView.findViewById(R.id.tv_order_item_sup_records);
            tvOrder = itemView.findViewById(R.id.tv_time_item_sup_records);
            tvWaitNum = itemView.findViewById(R.id.tv_wait_sup_num_item_sup_records);
            tvLackNum = itemView.findViewById(R.id.tv_total_sup_num_item_sup_records);
            tvTotalSupNUm = itemView.findViewById(R.id.tv_lack_num_item_sup_records);

        }
    }

}
