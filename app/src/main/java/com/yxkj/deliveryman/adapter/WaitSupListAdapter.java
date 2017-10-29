package com.yxkj.deliveryman.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.base.BaseRecyclerViewAdapter;
import com.yxkj.deliveryman.base.BaseViewHolder;
import com.yxkj.deliveryman.bean.response.WaitSupGoodsListBean;
import com.yxkj.deliveryman.util.ImageLoadUtil;
import com.yxkj.deliveryman.view.popupwindow.WaitSupGoodsInfoPopupWindow;

/**
 * 待补清单列表
 */

public class WaitSupListAdapter extends BaseRecyclerViewAdapter<WaitSupGoodsListBean.GroupsBean> {

    private Context mContext;

    public WaitSupListAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_wait_sup_goods;
    }

    @Override
    public void onCorvert(BaseViewHolder holder, int position, WaitSupGoodsListBean.GroupsBean bean) {
        ImageView ivGoodsPic = holder.getView(R.id.iv_goods_pic_wait_sup);
        TextView tvGoodsName = holder.getView(R.id.tv_goods_name_wait_sup);
        TextView tvSerialNum = holder.getView(R.id.tv_serial_num_wait_sup);
        TextView tvShouldSupNum = holder.getView(R.id.tv_should_sup_num_wait_sup);
        TextView tvTipComplete = holder.getView(R.id.tv_tip_complete_sup);
        LinearLayout llItem = holder.getView(R.id.ll_item_wait_sup_goods);

        ImageLoadUtil.loadImage(ivGoodsPic, bean.goodsPic);
        tvGoodsName.setText(bean.goodsName);
        tvSerialNum.setText(bean.goodsSn);
        tvShouldSupNum.setText(bean.waitSupplyCount + "");
        llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WaitSupGoodsInfoPopupWindow popupWindow = new WaitSupGoodsInfoPopupWindow(mContext, bean) {
                    @Override
                    public void onClick(View v) {
                        tvTipComplete.setVisibility(View.VISIBLE);
                        dismiss();
                    }
                };
                popupWindow.showAtLocation(ivGoodsPic, Gravity.NO_GRAVITY, 0, 0);
            }
        });

    }


}
