package com.yxkj.deliveryman.adapter;

import android.content.Context;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.base.BaseRecyclerViewAdapter;
import com.yxkj.deliveryman.base.BaseViewHolder;

/**
 * 待补清单列表
 */

public class WaitSupListAdapter extends BaseRecyclerViewAdapter<String> {

    public WaitSupListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_wait_sup;
    }

    @Override
    public void onCorvert(BaseViewHolder holder, int position, String bean) {
    }
}
