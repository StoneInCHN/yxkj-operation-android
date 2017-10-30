package com.yxkj.deliveryman.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.bean.response.SupRecordDetailBean;
import com.yxkj.deliveryman.constant.Constants;
import com.yxkj.deliveryman.util.ImageLoadUtil;

import java.util.ArrayList;
import java.util.List;


/*
 *  @项目名：  yxkj-operation-android 
 *  @包名：    com.yxkj.deliveryman.adapter
 *  @文件名:   SupRecordDetailItemAdapter
 *  @创建者:   hhe
 *  @创建时间:  2017/10/30 19:58
 *  @描述：    补货记录详情item
 */
public class SupRecordDetailItemAdapter extends RecyclerView.Adapter {
    private Context mContext;

    public SupRecordDetailItemAdapter(Context context) {
        mContext = context;
    }

    public List<SupRecordDetailBean.GroupsBean.CntrSupplementRecordsBean> mSupplementRecordsBeanList = new ArrayList<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_wait_sup, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        SupRecordDetailBean.GroupsBean.CntrSupplementRecordsBean bean = mSupplementRecordsBeanList.get(position);
        ImageLoadUtil.loadImage(viewHolder.ivGoodsPic, Constants.BASE_URL + bean.goodsPic);
        viewHolder.tvGoodsName.setText(bean.goodsName);
        viewHolder.tvSerialNum.setText(bean.channelSn);
        viewHolder.tvShouldSupNum.setText("应补数量:" + bean.waitSupplyCount);
        viewHolder.tvSuppedNum.setText("补货数量:" + bean.supplyCount);


    }

    @Override
    public int getItemCount() {
        return mSupplementRecordsBeanList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivGoodsPic;
        TextView tvGoodsName;
        TextView tvSerialNum;
        TextView tvShouldSupNum;
        TextView tvSuppedNum;


        public ViewHolder(View itemView) {
            super(itemView);
            ivGoodsPic = itemView.findViewById(R.id.iv_goods_pic_wait_sup);
            tvGoodsName = itemView.findViewById(R.id.tv_goods_name_wait_sup);
            tvSerialNum = itemView.findViewById(R.id.tv_serial_num_wait_sup);
            tvShouldSupNum = itemView.findViewById(R.id.tv_should_sup_num_wait_sup);
            tvSuppedNum = itemView.findViewById(R.id.tv_supped_num_wait_sup);
        }
    }
}
