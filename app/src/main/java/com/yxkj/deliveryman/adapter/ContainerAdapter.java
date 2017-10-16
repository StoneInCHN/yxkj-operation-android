package com.yxkj.deliveryman.adapter;

import android.content.Context;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.base.BaseRecyclerViewAdapter;
import com.yxkj.deliveryman.base.BaseViewHolder;

/**
 * 货柜适配器
 */

public class ContainerAdapter extends BaseRecyclerViewAdapter<String> {


    public ContainerAdapter(Context context) {
        super(context);
    }


    @Override
    public int getLayoutId() {
        return R.layout.item_container;
    }

    @Override
    public void onCorvert(BaseViewHolder holder, int position, String bean) {

    }
}
