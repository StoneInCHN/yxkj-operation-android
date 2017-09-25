package com.yxkj.deliveryman.adapter;

import android.content.Context;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.base.BaseRecyclerViewAdapter;
import com.yxkj.deliveryman.base.BaseViewHolder;

/**
 *
 */

public class SupRecordDetailAdapter extends BaseRecyclerViewAdapter<String> {
    public SupRecordDetailAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_sup_detail;
    }

    @Override
    public void onCorvert(BaseViewHolder holder, int position, String bean) {

    }
}
