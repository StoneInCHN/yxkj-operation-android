package com.yxkj.deliveryman.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.callback.OnCommon1Listener;
import com.yxkj.deliveryman.constant.Constants;
import com.yxkj.deliveryman.bean.response.WaitSupContainerGoodsBean;
import com.yxkj.deliveryman.dao.DBManager;
import com.yxkj.deliveryman.dao.WaitSupGoods;
import com.yxkj.deliveryman.dao.gen.DaoMaster;
import com.yxkj.deliveryman.dao.gen.DaoSession;
import com.yxkj.deliveryman.dao.gen.WaitSupGoodsDao;
import com.yxkj.deliveryman.util.DisplayUtil;
import com.yxkj.deliveryman.util.ImageLoadUtil;
import com.yxkj.deliveryman.util.ToastUtil;
import com.yxkj.deliveryman.view.popupwindow.CancelPopupWindow;
import com.yxkj.deliveryman.view.popupwindow.SupGoodsPopupWindow;

import java.util.ArrayList;
import java.util.List;


/*
 *  @项目名：  yxkj-operation-android 
 *  @包名：    com.yxkj.deliveryman.adapter
 *  @文件名:   WaitSupGoodsAdapter
 *  @创建者:   hhe
 *  @创建时间:  2017/10/26 17:02
 *  @描述：    真正在补货页面
 */
public class WaitSupGoodsAdapter extends RecyclerView.Adapter {
    private Context mContext;
    /**
     * 服务器获取到的数据
     */
    public List<WaitSupContainerGoodsBean.GroupsBean> mGroupsBeanList;
    /**
     * 已经完成（全部or部分）补货的list
     */
    private List<WaitSupGoods> mCompletedBeanList;

    private String mCntrId;
    private String mSceneSn;
    private WaitSupGoodsDao waitSupGoodsDao;

    public WaitSupGoodsAdapter(Context context, String cntrId, String sceneSn) {
        mContext = context;
        mCntrId = cntrId;
        mGroupsBeanList = new ArrayList<>();
        mCompletedBeanList = new ArrayList<>();
        waitSupGoodsDao = initDao();
        mSceneSn = sceneSn;

    }

    public void setGroupsBeanList(List<WaitSupContainerGoodsBean.GroupsBean> groupsBeanList) {
        List<WaitSupContainerGoodsBean.GroupsBean> mHandleList = traverseHandleData(groupsBeanList);
        mGroupsBeanList.addAll(mHandleList);
        notifyDataSetChanged();
    }

    /**
     * 在这里遍历去除那些已经补货的item
     *
     * @param groupsBeanList
     */
    private List<WaitSupContainerGoodsBean.GroupsBean> traverseHandleData(List<WaitSupContainerGoodsBean.GroupsBean> groupsBeanList) {
        mCompletedBeanList = getComletedDataFromDB();

        List<WaitSupContainerGoodsBean.GroupsBean> resultList = new ArrayList<>();
        for (WaitSupContainerGoodsBean.GroupsBean bean : groupsBeanList) {
            for (WaitSupGoods goods : mCompletedBeanList) {
                if (goods.getSceneId().equals(mSceneSn)
                        && goods.getCntrId().equals(mCntrId)
                        && goods.getGoodsSn().equals(bean.goodsSn)) {//此商品已经补货
                    bean.actualNum = goods.getSupNum();
                    bean.isSupped = true;
                }
            }

            resultList.add(bean);
        }


        return resultList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_wait_sup_goods, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        WaitSupContainerGoodsBean.GroupsBean bean = mGroupsBeanList.get(position);

        ViewHolder viewHolder = (ViewHolder) holder;
        ImageLoadUtil.loadImage(viewHolder.ivGoodsPic, Constants.BASE_URL + bean.goodsPic);
        viewHolder.tvGoodsName.setText(bean.goodsName);
        viewHolder.tvContainerName.setText(bean.channelSn);
        viewHolder.tvRemainNum.setText("剩余数量：" + bean.waitSupplyCount);
        viewHolder.tvWaitNum.setText("待补货数：" + bean.waitSupplyCount);

        if (bean.isSupped) {
            viewHolder.rlShadow.setVisibility(View.VISIBLE);
            viewHolder.tvRemainNum.setText("剩余数量：" + (bean.waitSupplyCount - bean.actualNum));
        } else {
            viewHolder.rlShadow.setVisibility(View.GONE);
        }

        viewHolder.rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SupGoodsPopupWindow popupWindow = new SupGoodsPopupWindow(mContext, bean);
                popupWindow.setOnCommon1Listener(new OnCommon1Listener<Integer>() {
                    @Override
                    public void onCommon1(Integer integer) {
                        if (popupWindow.isActualNumIllegal()) {
                            bean.actualNum = integer;
                            mGroupsBeanList.get(position).isSupped = true;
                            popupWindow.dismiss();
                            viewHolder.rlShadow.setVisibility(View.VISIBLE);
                            int remainNum = bean.waitSupplyCount - bean.actualNum;
                            viewHolder.tvRemainNum.setText("剩余数量：" + remainNum);
                            // 存储在本地数据库中
                            saveToDB(bean.goodsSn, integer);
                        } else {
                            ToastUtil.showShort("实际补货数量不能大于待补数量");
                        }
                    }
                });
                popupWindow.showAtLocation(viewHolder.rlItem, Gravity.NO_GRAVITY, 0, 0);
            }
        });

        viewHolder.rlItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (viewHolder.rlShadow.getVisibility() == View.VISIBLE) {//完成状态
                    showCancelPopupWindow(position, viewHolder);
                }
                return true;//返回true则不会附加一个短按动作
            }
        });
    }

    private void showCancelPopupWindow(final int position, final ViewHolder viewHolder) {
        CancelPopupWindow cancelPopupWindow = new CancelPopupWindow(mContext, "取消完成");
        cancelPopupWindow.setOnCommon1Listener(new OnCommon1Listener() {
            @Override
            public void onCommon1(Object o) {
                deleteFromDB(mGroupsBeanList.get(position));
                viewHolder.rlShadow.setVisibility(View.GONE);
                ToastUtil.showShort("取消完成");
                cancelPopupWindow.dismiss();
            }
        });
        cancelPopupWindow.showAsDropDown(viewHolder.rlItem, (int) (DisplayUtil.getDensity_Width(mContext) * 0.4), -30);
    }

    /**
     * 将该item从数据库中删除
     *
     * @param groupsBean
     */
    private void deleteFromDB(WaitSupContainerGoodsBean.GroupsBean groupsBean) {
        waitSupGoodsDao.deleteByKeyInTx(Long.parseLong(mSceneSn), Long.parseLong(mCntrId), Long.parseLong(groupsBean.goodsSn));
    }

    /**
     * 存储在数据库中
     *
     * @param supNum 补货的数量
     */
    private void saveToDB(String goodsSn, Integer supNum) {
        WaitSupGoods waitSupGoods = new WaitSupGoods();
        waitSupGoods.setCntrId(mCntrId);
        waitSupGoods.setSceneId(mSceneSn);
        waitSupGoods.setGoodsSn(goodsSn);
        waitSupGoods.setSupNum(supNum);
        waitSupGoodsDao.insertOrReplace(waitSupGoods);
    }

    private WaitSupGoodsDao initDao() {
        DaoMaster daoMaster = new DaoMaster(DBManager.getInstance(mContext).getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        return daoSession.getWaitSupGoodsDao();
    }


    private List<WaitSupGoods> getComletedDataFromDB() {
        return waitSupGoodsDao.queryBuilder().list();
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
        TextView tvCompleteSup;
        //TextView tvRemainNumShadow;
        RelativeLayout rlItem;
        RelativeLayout rlShadow;

        ViewHolder(View itemView) {
            super(itemView);
            ivGoodsPic = itemView.findViewById(R.id.iv_goods_pic_item_sup_goods);
            tvGoodsName = itemView.findViewById(R.id.tv_goods_name_item_sup_goods);
            tvContainerName = itemView.findViewById(R.id.tv_container_name_item_sup_goods);
            tvRemainNum = itemView.findViewById(R.id.tv_remain_num_item_sup_goods);
            tvWaitNum = itemView.findViewById(R.id.tv_wait_sup_num_item_sup_goods);
            tvCompleteSup = itemView.findViewById(R.id.tv_tip_complete_sup_goods);
            //tvRemainNumShadow = itemView.findViewById(R.id.tv_num_remain_shadow);
            rlItem = itemView.findViewById(R.id.rl_item_sup_goods);
            rlShadow = itemView.findViewById(R.id.rl_shadow);
        }
    }
}
