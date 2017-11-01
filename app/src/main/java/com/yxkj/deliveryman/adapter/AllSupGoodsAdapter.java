package com.yxkj.deliveryman.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.bean.response.AllSupContainerGoodsBean;
import com.yxkj.deliveryman.bean.response.NullBean;
import com.yxkj.deliveryman.callback.OnCommon1Listener;
import com.yxkj.deliveryman.constant.Constants;
import com.yxkj.deliveryman.http.BaseObserver;
import com.yxkj.deliveryman.http.HttpApi;
import com.yxkj.deliveryman.util.DisplayUtil;
import com.yxkj.deliveryman.util.ImageLoadUtil;
import com.yxkj.deliveryman.util.ToastUtil;
import com.yxkj.deliveryman.view.popupwindow.CancelPopupWindow;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/*
 *  @项目名：  yxkj-operation-android 
 *  @包名：    com.yxkj.deliveryman.adapter
 *  @文件名:   WaitSupGoodsAdapter
 *  @创建者:   hhe
 *  @创建时间:  2017/10/26 17:02
 *  @描述：    货柜管理 - 全部商品
 */
public class AllSupGoodsAdapter extends RecyclerView.Adapter {
    private Context mContext;

    public List<AllSupContainerGoodsBean.GroupsBean> mGroupsBeanList;

    public AllSupGoodsAdapter(Context context) {
        mContext = context;
        mGroupsBeanList = new ArrayList<>();
    }

    public void setGroupsBeanList(List<AllSupContainerGoodsBean.GroupsBean> groupsBeanList) {
        mGroupsBeanList.addAll(groupsBeanList);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_all_sup_goods, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AllSupContainerGoodsBean.GroupsBean bean = mGroupsBeanList.get(position);

        ViewHolder viewHolder = (ViewHolder) holder;
        ImageLoadUtil.loadImage(viewHolder.ivGoodsPic, Constants.BASE_URL + bean.goodsPic);
        viewHolder.tvGoodsName.setText(bean.goodsName);
        viewHolder.tvContainerName.setText(bean.channelSn);
        viewHolder.tvRemainNum.setText("剩余数量：" + bean.waitSupplyCount);
        viewHolder.tvWaitNum.setText("待补货数：" + bean.waitSupplyCount);

        viewHolder.rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  SupGoodsPopupWindow popupWindow = new SupGoodsPopupWindow(mContext, bean);
                popupWindow.setOnCommon1Listener(new CommonDialogSureListener() {
                    @Override
                    public void onSure() {
                        if (popupWindow.isActualNumIllegal()) {
                            mGroupsBeanList.get(position).isSupped = true;
                            popupWindow.dismiss();
                            viewHolder.tvCompleteSup.setVisibility(View.VISIBLE);
                        } else {
                            ToastUtil.showShort("实际补货数量不能大于待补数量");
                        }
                    }
                });
                popupWindow.showAsDropDown(viewHolder.rlItem);*/
            }
        });

        viewHolder.rlItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                CancelPopupWindow cancelPopupWindow = new CancelPopupWindow(mContext, "出货测试");
                cancelPopupWindow.setOnCommon1Listener(new OnCommon1Listener() {
                    @Override
                    public void onCommon1(Object o) {
                        testDeliver(bean.id, viewHolder.tvTestDeliver);
                        viewHolder.tvTestDeliver.setVisibility(View.VISIBLE);
                        cancelPopupWindow.dismiss();
                    }
                });
                cancelPopupWindow.showAsDropDown(viewHolder.rlItem, (int) (DisplayUtil.getDensity_Width(mContext) * 0.4), -30);
                return true;//返回true则不会附加一个短按动作
            }
        });

    }

    /**
     * 出货测试
     *
     * @param id
     * @param tvTestDeliver
     */
    private void testDeliver(int id, TextView tvTestDeliver) {
        HttpApi.getInstance()
                .testDeliver(id + "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<NullBean>() {
                    @Override
                    protected void onHandleSuccess(NullBean nullBean) {
                        tvTestDeliver.setVisibility(View.GONE);
                        ToastUtil.showShort("出货完成");
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        tvTestDeliver.setVisibility(View.GONE);
                        ToastUtil.showShort("出货完成");
                    }
                });
    }

    @Override
    public int getItemCount() {
        return mGroupsBeanList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivGoodsPic;
        TextView tvGoodsName;
        TextView tvContainerName;
        TextView tvRemainNum;
        TextView tvWaitNum;
        TextView tvTestDeliver;
        RelativeLayout rlItem;

        ViewHolder(View itemView) {
            super(itemView);
            ivGoodsPic = itemView.findViewById(R.id.iv_goods_pic_item_sup_goods);
            tvGoodsName = itemView.findViewById(R.id.tv_goods_name_item_sup_goods);
            tvContainerName = itemView.findViewById(R.id.tv_container_name_item_sup_goods);
            tvRemainNum = itemView.findViewById(R.id.tv_remain_num_item_sup_goods);
            tvWaitNum = itemView.findViewById(R.id.tv_wait_sup_num_item_sup_goods);
            tvTestDeliver = itemView.findViewById(R.id.tv_test_deliver);
            rlItem = itemView.findViewById(R.id.rl_item_sup_goods);
        }
    }
}
