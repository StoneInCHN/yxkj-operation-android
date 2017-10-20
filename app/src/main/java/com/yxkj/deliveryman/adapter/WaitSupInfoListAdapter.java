package com.yxkj.deliveryman.adapter;

import android.content.Context;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.base.BaseRecyclerViewAdapter;
import com.yxkj.deliveryman.base.BaseViewHolder;

/**
 * 某商品待补货物列表
 * // TODO: 2017/10/20  无用，待删除
 */

public class WaitSupInfoListAdapter extends BaseRecyclerViewAdapter<String> {

    public WaitSupInfoListAdapter(Context context) {
        super(context);

    }

    @Override
    public int getLayoutId() {
        return R.layout.item_info_wait;
    }

    @Override
    public void onCorvert(BaseViewHolder holder, int position, String bean) {

    }
}
