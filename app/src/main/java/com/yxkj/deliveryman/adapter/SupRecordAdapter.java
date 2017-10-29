package com.yxkj.deliveryman.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.activity.ContainerSupRecordActivity;
import com.yxkj.deliveryman.base.BaseRecyclerViewAdapter;
import com.yxkj.deliveryman.base.BaseViewHolder;
import com.yxkj.deliveryman.bean.response.SupRecordBean;
import com.yxkj.deliveryman.util.IntentUtil;

/**
 * 补货记录adapter
 */

public class SupRecordAdapter extends BaseRecyclerViewAdapter<SupRecordBean.GroupsBean> {
    public SupRecordAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_sup_record;
    }

    @Override
    public void onCorvert(BaseViewHolder holder, int position, SupRecordBean.GroupsBean bean) {
        LinearLayout llItemSupRecord = holder.getView(R.id.ll_item_sup_record);
        for (int i = 0; i < tList.size(); i++) {
            View v = View.inflate(context, R.layout.item_sup_detail, null);
            llItemSupRecord.addView(v);
            v.setOnClickListener(view -> {
                IntentUtil.openActivity(context, ContainerSupRecordActivity.class);
            });
        }
    }
}
