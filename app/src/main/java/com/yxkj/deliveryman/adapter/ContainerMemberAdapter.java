package com.yxkj.deliveryman.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.base.BaseRecyclerViewAdapter;
import com.yxkj.deliveryman.base.BaseViewHolder;
import com.yxkj.deliveryman.bean.response.WaitSupStateBean;

/**
 * 具体货柜
 */

public class ContainerMemberAdapter extends BaseRecyclerViewAdapter<WaitSupStateBean.ScenesBean.VendingContainerGroupsBean.VendingContainersBean> {


    public ContainerMemberAdapter(Context context) {
        super(context);
    }


    @Override
    public int getLayoutId() {
        return R.layout.item_container;
    }

    @Override
    public void onCorvert(BaseViewHolder holder, int position, WaitSupStateBean.ScenesBean.VendingContainerGroupsBean.VendingContainersBean bean) {
        TextView tvName = holder.getView(R.id.tv_name_container);
        TextView tvNum = holder.getView(R.id.tv_num_item_container);

        if (bean.central) {
            tvName.setText("中控台");
            tvNum.setVisibility(View.INVISIBLE);
            tvName.setBackgroundResource(R.drawable.bg_container_orange);
        } else {
            tvName.setText(bean.cntrSn + "货柜");
            if (bean.waitSupplyCount == 0) {
                tvNum.setVisibility(View.GONE);
            } else {
                tvNum.setText(bean.waitSupplyCount + "");
            }
            tvName.setBackgroundResource(R.drawable.bg_container_green);
        }

    }

}
