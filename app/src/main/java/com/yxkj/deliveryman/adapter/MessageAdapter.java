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
import com.yxkj.deliveryman.activity.MessageDetailActivity;
import com.yxkj.deliveryman.activity.WaitSupplementActivity;
import com.yxkj.deliveryman.base.BaseRecyclerViewAdapter;
import com.yxkj.deliveryman.base.BaseViewHolder;
import com.yxkj.deliveryman.bean.response.MessageBean;
import com.yxkj.deliveryman.util.IntentUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息适配器
 */

public class MessageAdapter extends RecyclerView.Adapter {

    private Context mContext;
    public List<MessageBean.GroupsBean> mGroupsBeanList = new ArrayList<>();

    public MessageAdapter(Context context) {
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_message, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MessageBean.GroupsBean bean = mGroupsBeanList.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        if (bean.noticeCount != 0) {
            viewHolder.tvUnreadNum.setVisibility(View.VISIBLE);
            viewHolder.tvUnreadNum.setText(bean.noticeCount + "");
        }
        viewHolder.tvDate.setText(bean.noticeTime);
        switch (bean.type) {
            case "LACK":
                viewHolder.tvTitle.setText(bean.type);
                break;
            default:
                break;
        }

        viewHolder.tvContent.setText(bean.content);

        viewHolder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("type", bean.type);
                IntentUtil.openActivity(mContext, MessageDetailActivity.class, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mGroupsBeanList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivType;
        TextView tvDate;
        TextView tvTitle;
        TextView tvContent;
        LinearLayout llItem;
        TextView tvUnreadNum;

        public ViewHolder(View itemView) {
            super(itemView);
            llItem = itemView.findViewById(R.id.ll_item_message);
            ivType = itemView.findViewById(R.id.iv_type_item_message);
            tvDate = itemView.findViewById(R.id.tv_date_item_message);
            tvTitle = itemView.findViewById(R.id.tv_title_item_message);
            tvUnreadNum = itemView.findViewById(R.id.tv_num_unread_item_message);
            tvContent = itemView.findViewById(R.id.tv_content_item_message);
        }
    }
}
