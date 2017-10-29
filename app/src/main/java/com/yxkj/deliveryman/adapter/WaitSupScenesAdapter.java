package com.yxkj.deliveryman.adapter;

import android.content.Context;
import android.widget.TextView;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.base.BaseRecyclerViewAdapter;
import com.yxkj.deliveryman.base.BaseViewHolder;
import com.yxkj.deliveryman.bean.response.WaitSupGoodsDetailBean;

/**
 * 待补商品详情，弹框中优享空间列表
 */
public class WaitSupScenesAdapter extends BaseRecyclerViewAdapter<WaitSupGoodsDetailBean.SceneCountListBean> {

    public WaitSupScenesAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_info_wait;
    }

    @Override
    public void onCorvert(BaseViewHolder holder, int position, WaitSupGoodsDetailBean.SceneCountListBean bean) {
        TextView tvContainerName = holder.getView(R.id.tv_container_name_popup_goods_info);
        TextView tvGoodsNum = holder.getView(R.id.tv_goods_num_popup_goods_info);

        tvContainerName.setText(bean.sceneName);
        tvGoodsNum.setText("：" + bean.waitSupplyCount);
    }

}
