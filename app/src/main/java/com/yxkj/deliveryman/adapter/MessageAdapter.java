package com.yxkj.deliveryman.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.base.BaseRecyclerViewAdapter;
import com.yxkj.deliveryman.base.BaseViewHolder;
import com.yxkj.deliveryman.bean.response.MessageBean;

/**
 * 消息适配器
 */

public class MessageAdapter extends BaseRecyclerViewAdapter<MessageBean.GroupsBean> {
    public MessageAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_message;
    }

    @Override
    public void onCorvert(BaseViewHolder holder, int position, MessageBean.GroupsBean bean) {
        ImageView ivType = holder.getView(R.id.iv_type_item_message);
        TextView tvDate = holder.getView(R.id.tv_date_item_message);
        TextView tvTitle = holder.getView(R.id.tv_title_item_message);
        TextView tvContent = holder.getView(R.id.tv_content_item_message);

        tvDate.setText(bean.noticeTime);
        tvTitle.setText(bean.type);
        tvContent.setText(bean.content);
    }
}
