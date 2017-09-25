package com.yxkj.deliveryman.adapter;

import android.content.Context;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.base.BaseRecyclerViewAdapter;
import com.yxkj.deliveryman.base.BaseViewHolder;

/**
 * 缺货列表
 */

public class LackGoodsInfoAdapter extends BaseRecyclerViewAdapter<String> {
    public LackGoodsInfoAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_lack;
    }

    @Override
    public void onCorvert(BaseViewHolder holder, int position, String bean) {

    }
}
