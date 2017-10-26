package com.yxkj.deliveryman.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.base.BaseRecyclerViewAdapter;
import com.yxkj.deliveryman.base.BaseViewHolder;
import com.yxkj.deliveryman.response.WaitSupGoodsListBean;
import com.yxkj.deliveryman.util.ImageLoadUtil;

/**
 * 待补清单列表
 */

public class WaitSupListAdapter extends BaseRecyclerViewAdapter<WaitSupGoodsListBean.GroupsBean> {

    public WaitSupListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_wait_sup_goods;
    }

    @Override
    public void onCorvert(BaseViewHolder holder, int position, WaitSupGoodsListBean.GroupsBean bean) {
        ImageView ivGoodsPic = holder.getView(R.id.iv_goods_pic_wait_sup);
        TextView tvGoodsName = holder.getView(R.id.tv_goods_name_wait_sup);
        TextView tvContainerName = holder.getView(R.id.tv_container_name_wait_sup);
        TextView tvShouldSupNum = holder.getView(R.id.tv_should_sup_num_wait_sup);
        TextView tvSuppedNum = holder.getView(R.id.tv_supped_num_wait_sup);

        ImageLoadUtil.loadImage(ivGoodsPic, bean.goodsPic);
        tvGoodsName.setText(bean.goodsName);
        tvContainerName.setText(bean.goodsSn);
        tvShouldSupNum.setText("应补数量:" + bean.waitSupplyCount);
    }
}
