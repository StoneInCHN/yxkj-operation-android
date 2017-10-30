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
import com.yxkj.deliveryman.dao.DBManager;
import com.yxkj.deliveryman.dao.FetchGoods;
import com.yxkj.deliveryman.gen.DaoMaster;
import com.yxkj.deliveryman.gen.DaoSession;
import com.yxkj.deliveryman.gen.FetchGoodsDao;
import com.yxkj.deliveryman.util.ImageLoadUtil;
import com.yxkj.deliveryman.view.popupwindow.WaitSupGoodsInfoPopupWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * 待补清单列表
 */

public class WaitSupListAdapter extends RecyclerView.Adapter {

    private Context mContext;
    public List<WaitSupGoodsListBean.GroupsBean> mBeanList;
    /**
     * 当前地点
     */
    private String mSceneSn;

    public WaitSupListAdapter(Context context, String sceneSn) {
        mContext = context;
        mBeanList = new ArrayList<>();
        mSceneSn = sceneSn;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_wait_sup_goods, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        WaitSupGoodsListBean.GroupsBean bean = mBeanList.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        ImageLoadUtil.loadImage(viewHolder.ivGoodsPic, bean.goodsPic);
        viewHolder.tvGoodsName.setText(bean.goodsName);
        viewHolder.tvSerialNum.setText(bean.goodsSn);
        viewHolder.tvShouldSupNum.setText(bean.waitSupplyCount + "");

        if (bean.isComplete) {
            viewHolder.tvTipComplete.setVisibility(View.VISIBLE);
        } else {
            viewHolder.tvTipComplete.setVisibility(View.GONE);
        }
        viewHolder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WaitSupGoodsInfoPopupWindow popupWindow = new WaitSupGoodsInfoPopupWindow(mContext, bean) {
                    @Override
                    public void onClick(View v) {
                        viewHolder.tvTipComplete.setVisibility(View.VISIBLE);
                        bean.isComplete = true;
                        String id = mSceneSn + bean.goodsSn;
                        FetchGoods fetchGoods = new FetchGoods(id, true);
                        DaoMaster daoMaster = new DaoMaster(DBManager.getInstance(mContext).getWritableDatabase());
                        DaoSession daoSession = daoMaster.newSession();
                        FetchGoodsDao fetchGoodsDao = daoSession.getFetchGoodsDao();
                        fetchGoodsDao.insert(fetchGoods);
                        dismiss();
                    }
                };
                popupWindow.showAtLocation(viewHolder.ivGoodsPic, Gravity.NO_GRAVITY, 0, 0);
            }
        });
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
