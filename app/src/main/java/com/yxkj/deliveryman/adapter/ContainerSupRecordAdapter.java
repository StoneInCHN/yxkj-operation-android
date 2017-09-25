package com.yxkj.deliveryman.adapter;

import android.content.Context;
import android.widget.LinearLayout;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.base.BaseRecyclerViewAdapter;
import com.yxkj.deliveryman.base.BaseViewHolder;
import com.yxkj.deliveryman.view.WaitSupListView;

/**
 * 某货柜取货记录
 */

public class ContainerSupRecordAdapter extends BaseRecyclerViewAdapter<String> {
    public ContainerSupRecordAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_con_sup_recd;
    }

    @Override
    public void onCorvert(BaseViewHolder holder, int position, String bean) {
        LinearLayout recyclerView = holder.getView(R.id.recyclerView);
        for (int i = 0; i < tList.size(); i++) {
            WaitSupListView v = new WaitSupListView(context);
            recyclerView.addView(v);
        }
    }
}
