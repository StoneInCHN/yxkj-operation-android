package com.yxkj.deliveryman.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.dao.DBManager;
import com.yxkj.deliveryman.dao.WaitSupGoods;
import com.yxkj.deliveryman.dao.gen.DaoMaster;
import com.yxkj.deliveryman.dao.gen.DaoSession;
import com.yxkj.deliveryman.dao.gen.WaitSupGoodsDao;
import com.yxkj.deliveryman.http.BaseObserver;
import com.yxkj.deliveryman.bean.response.SceneListBean;
import com.yxkj.deliveryman.bean.response.WaitSupStateBean;
import com.yxkj.deliveryman.constant.UserInfo;
import com.yxkj.deliveryman.http.HttpApi;
import com.yxkj.deliveryman.util.ToastUtil;
import com.yxkj.deliveryman.view.dialog.CommonYesOrNoDialog;
import com.yxkj.deliveryman.view.dialog.TextButtonDialog;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * 按地址的补货列表
 */

public class AddressSupAdapter extends RecyclerView.Adapter<AddressSupAdapter.ViewHolder> {
    public List<WaitSupStateBean.ScenesBean> mScenesBeanList = new ArrayList<>();
    private List<WaitSupStateBean.ScenesBean.VendingContainerGroupsBean> mGroupsBeanList = new ArrayList<>();
    private Context mContext;

    public AddressSupAdapter(Context context) {
        mContext = context;
    }

    public void setMoreList(List<WaitSupStateBean.ScenesBean> scenesBeanList) {
        mScenesBeanList.addAll(scenesBeanList);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_replenish, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        WaitSupStateBean.ScenesBean bean = mScenesBeanList.get(position);
        holder.tvAddress.setText(bean.sceneName);
        holder.tvNum.setText(bean.sceneSn);
        holder.tvFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmDialog(bean, position);


            }
        });
        //某楼里面按组分的list
        holder.rvGroups.setLayoutManager(new LinearLayoutManager(mContext));
        TeamAdapter teamAdapter = new TeamAdapter(mContext, bean.sceneSn,bean.sceneName);
        holder.rvGroups.setAdapter(teamAdapter);
        teamAdapter.settList(mScenesBeanList.get(position).vendingContainerGroups);
    }

    private void showConfirmDialog(WaitSupStateBean.ScenesBean bean, int position) {
        CommonYesOrNoDialog commonYesOrNoDialog = new CommonYesOrNoDialog(mContext);
        commonYesOrNoDialog.setTv_title("完成补货");
        commonYesOrNoDialog.setTv_content("是否完成对" + bean.sceneName + "的补货吗？");
        commonYesOrNoDialog.setBtn_sure("确定");
        commonYesOrNoDialog.setDialogSureListener(() -> {
            commitRecords(bean.sceneSn, position);
        });
        commonYesOrNoDialog.show();
    }

    private void commitRecords(String sceneSn, int position) {
        HttpApi.getInstance()
                .finishSupplyGoods(UserInfo.USER_ID, sceneSn)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<SceneListBean.GroupsBean>() {
                    @Override
                    protected void onHandleSuccess(SceneListBean.GroupsBean groupsBean) {
                        if (groupsBean.sceneSn == null) {
                            ToastUtil.showShort("提交成功");
                            WaitSupGoodsDao dao = getDao();
                            List<WaitSupGoods> deleteList = dao.queryBuilder().where(WaitSupGoodsDao.Properties.SceneId.eq(sceneSn)).build().list();
                            getDao().deleteInTx(deleteList);

                        } else {//还有未补货完成的优享空间
                            TextButtonDialog textButtonDialog =
                                    new TextButtonDialog(mContext, "系统提示", "你尚未完成" + groupsBean.sceneName + "的补货,请完成后再对下一个空间补货", "确定");
                            textButtonDialog.show();
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    private WaitSupGoodsDao getDao() {
        DaoMaster daoMaster = new DaoMaster(DBManager.getInstance(mContext).getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        return daoSession.getWaitSupGoodsDao();
    }

    @Override
    public int getItemCount() {
        return mScenesBeanList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView rvGroups;
        TextView tvAddress;
        TextView tvNum;
        TextView tvFinish;

        ViewHolder(View itemView) {
            super(itemView);
            rvGroups = itemView.findViewById(R.id.rv_group);
            tvAddress = itemView.findViewById(R.id.tv_address_item_replenish);
            tvNum = itemView.findViewById(R.id.tv_num_item_replenish);
            tvFinish = itemView.findViewById(R.id.tv_finish_item_replenish);
        }
    }
}
