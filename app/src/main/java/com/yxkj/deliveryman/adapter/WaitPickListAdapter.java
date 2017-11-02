package com.yxkj.deliveryman.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.bean.response.WaitSupGoodsListBean;
import com.yxkj.deliveryman.callback.OnCommon1Listener;
import com.yxkj.deliveryman.util.DisplayUtil;
import com.yxkj.deliveryman.util.ImageLoadUtil;
import com.yxkj.deliveryman.view.popupwindow.CancelPopupWindow;
import com.yxkj.deliveryman.view.popupwindow.WaitSupGoodsInfoPopupWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * 待补清单列表
 */

public class WaitPickListAdapter extends RecyclerView.Adapter {

    private Context mContext;
    public List<WaitSupGoodsListBean.GroupsBean> mBeanList;
    /**
     * 当前地点
     */
    private String mSceneSn;

    public WaitPickListAdapter(Context context, String sceneSn) {
        mContext = context;
        mBeanList = new ArrayList<>();
        mSceneSn = sceneSn;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_wait_pick_goods, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        WaitSupGoodsListBean.GroupsBean bean = mBeanList.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        ImageLoadUtil.loadImage(viewHolder.ivGoodsPic, bean.goodsPic);
        viewHolder.tvGoodsName.setText(bean.goodsName);
        viewHolder.tvSerialNum.setText("商品条码:" + bean.goodsSn);
        viewHolder.tvShouldSupNum.setText(bean.waitSupplyCount + "");

        ///此需求取消
       /*
        if (bean.isComplete) {
            viewHolder.tvTipComplete.setVisibility(View.VISIBLE);
        } else {
            viewHolder.tvTipComplete.setVisibility(View.GONE);
        }
      viewHolder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WaitSupGoodsInfoPopupWindow popupWindow = new WaitSupGoodsInfoPopupWindow(mContext, bean);
                popupWindow.setOnCommon1Listener(new OnCommon1Listener<Integer>() {
                    @Override
                    public void onCommon1(Integer integer) {
                        viewHolder.tvTipComplete.setVisibility(View.VISIBLE);
                        bean.isComplete = true;
                        popupWindow.dismiss();
                    }
                });

                popupWindow.showAtLocation(viewHolder.ivGoodsPic, Gravity.NO_GRAVITY, 0, 0);
            }
        });

        viewHolder.llItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (bean.isComplete) {
                    showCancelPopupWindow();
                }
                return true;//返回true则不会附加一个短按动作
            }

            private void showCancelPopupWindow() {
                CancelPopupWindow cancelPopupWindow = new CancelPopupWindow(mContext,"取消完成");
                cancelPopupWindow.setOnCommon1Listener(new OnCommon1Listener() {
                    @Override
                    public void onCommon1(Object o) {
                        viewHolder.tvTipComplete.setVisibility(View.GONE);
                        bean.isComplete = false;
                        cancelPopupWindow.dismiss();
                    }
                });

                cancelPopupWindow.showAsDropDown(viewHolder.llItem, (int) (DisplayUtil.getDensity_Width(mContext) * 0.4), -30);
            }

        });*/
    }


    @Override
    public int getItemCount() {
        return mBeanList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivGoodsPic;
        TextView tvGoodsName;
        TextView tvSerialNum;
        TextView tvShouldSupNum;
        TextView tvTipComplete;
        LinearLayout llItem;


        public ViewHolder(View itemView) {
            super(itemView);
            ivGoodsPic = itemView.findViewById(R.id.iv_goods_pic_wait_sup);
            tvGoodsName = itemView.findViewById(R.id.tv_goods_name_wait_sup);
            tvSerialNum = itemView.findViewById(R.id.tv_serial_num_wait_sup);
            tvShouldSupNum = itemView.findViewById(R.id.tv_should_sup_num_wait_sup);
            tvTipComplete = itemView.findViewById(R.id.tv_tip_complete_sup);
            llItem = itemView.findViewById(R.id.ll_item_wait_sup_goods);
        }

    }
}
