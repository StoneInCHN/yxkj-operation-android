package com.yxkj.deliveryman.adapter;

import android.content.Context;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.base.BaseRecyclerViewAdapter;
import com.yxkj.deliveryman.base.BaseViewHolder;

/**
 * 消息适配器
 */

public class MessageAdapter extends BaseRecyclerViewAdapter<String> {
    public MessageAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_message;
    }

    @Override
    public void onCorvert(BaseViewHolder holder, int position, String bean) {

    }
}
