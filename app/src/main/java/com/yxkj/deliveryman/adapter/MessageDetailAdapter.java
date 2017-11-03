package com.yxkj.deliveryman.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.activity.WaitSupplementActivity;
import com.yxkj.deliveryman.bean.response.MessageBean;
import com.yxkj.deliveryman.bean.response.MessageDetailBean;
import com.yxkj.deliveryman.util.IntentUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * @项目名： yxkj-operation-android
 * @包名： com.yxkj.deliveryman.adapter
 * @文件名: MessageDetailAdapter
 * @创建者: hhe
 * @创建时间: 2017/11/3 10:58
 * @描述： 消息详情
 */
public class MessageDetailAdapter extends RecyclerView.Adapter {
    private Context mContext;

    public List<MessageDetailBean.GroupsBean> mBeanList;

    public MessageDetailAdapter(Context context) {
        mContext = context;
        mBeanList = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_message_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        MessageDetailBean.GroupsBean bean = mBeanList.get(position);

        viewHolder.tvDate.setText(bean.sendDate);
        viewHolder.tvTitle.setText(bean.title);
        viewHolder.tvContent.setText(bean.content);

        viewHolder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("sceneSn", bean.sceneSn);
                IntentUtil.openActivity(mContext, WaitSupplementActivity.class, bundle);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mBeanList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivType;
        TextView tvDate;
        TextView tvTitle;
        TextView tvContent;
        LinearLayout llItem;

        public ViewHolder(View itemView) {
            super(itemView);
            llItem = itemView.findViewById(R.id.ll_item_message_detail);
            ivType = itemView.findViewById(R.id.iv_type_item_message_detail);
            tvDate = itemView.findViewById(R.id.tv_date_item_message_detail);
            tvTitle = itemView.findViewById(R.id.tv_title_item_message_detail);
            tvContent = itemView.findViewById(R.id.tv_content_item_message_detail);
        }
    }

}
