package com.yxkj.deliveryman.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.base.BaseRecyclerViewAdapter;
import com.yxkj.deliveryman.base.BaseViewHolder;
import com.yxkj.deliveryman.constant.Constants;
import com.yxkj.deliveryman.response.WaitSupContainerGoodsBean;
import com.yxkj.deliveryman.util.ImageLoadUtil;


/*
 *  @项目名：  yxkj-operation-android 
 *  @包名：    com.yxkj.deliveryman.adapter
 *  @文件名:   SupGoodsAdapter
 *  @创建者:   hhe
 *  @创建时间:  2017/10/26 17:02
 *  @描述：    真正在补货页面
 */
public class SupGoodsAdapter extends BaseRecyclerViewAdapter<WaitSupContainerGoodsBean.GroupsBean> {
    public SupGoodsAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_sup_goods;
    }

    @Override
    public void onCorvert(BaseViewHolder holder, int position, WaitSupContainerGoodsBean.GroupsBean bean) {
        ImageView ivGoodsPic = holder.getView(R.id.iv_goods_pic_item_sup_goods);
        TextView tvGoodsName = holder.getView(R.id.tv_goods_name_item_sup_goods);
        TextView tvContainerName = holder.getView(R.id.tv_container_name_item_sup_goods);
        TextView tvRemainNum = holder.getView(R.id.tv_remain_num_item_sup_goods);
        TextView tvWaitNum = holder.getView(R.id.tv_wait_sup_num_item_sup_goods);

        ImageLoadUtil.loadImage(ivGoodsPic, Constants.BASE_URL + bean.goodsPic);
        tvGoodsName.setText(bean.goodsName);
        tvContainerName.setText(bean.channelSn);
        tvRemainNum.setText("剩余数量：" + bean.waitSupplyCount);
        tvWaitNum.setText("待补货数：" + bean.waitSupplyCount);
    }
}
