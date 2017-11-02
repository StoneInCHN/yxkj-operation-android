package com.yxkj.deliveryman.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.activity.ContainerManageActivity;
import com.yxkj.deliveryman.activity.ControllerManageActivity;
import com.yxkj.deliveryman.http.BaseObserver;
import com.yxkj.deliveryman.base.BaseRecyclerViewAdapter;
import com.yxkj.deliveryman.base.BaseViewHolder;
import com.yxkj.deliveryman.bean.response.SceneListBean;
import com.yxkj.deliveryman.bean.response.WaitSupStateBean;
import com.yxkj.deliveryman.constant.UserInfo;
import com.yxkj.deliveryman.http.HttpApi;
import com.yxkj.deliveryman.util.IntentUtil;
import com.yxkj.deliveryman.view.dialog.TextButtonDialog;
import com.yxkj.deliveryman.view.popupwindow.AbstractStartSupPopWindow;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 货柜分组
 */

public class TeamAdapter extends BaseRecyclerViewAdapter<WaitSupStateBean.ScenesBean.VendingContainerGroupsBean> {
    private Context mContext;
    private String mSceneSn;
    private String mSceneName;


    public TeamAdapter(Context context, String sceneSn, String sceneName) {
        super(context);
        mContext = context;
        mSceneName = sceneName;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_team;
    }

    @Override
    public void onCorvert(BaseViewHolder holder, int position, WaitSupStateBean.ScenesBean.VendingContainerGroupsBean bean) {
        //某组里面的机器list
        TextView tvTeam = holder.getView(R.id.tv_teamnum_item_team);
        tvTeam.setText((position + 1) + "组");
        RecyclerView rvTeamMember = holder.getView(R.id.rv_team_member);
        rvTeamMember.setLayoutManager(new GridLayoutManager(context, 3));
        ContainerMemberAdapter containerAdapter = new ContainerMemberAdapter(context);
        rvTeamMember.setAdapter(containerAdapter);
        containerAdapter.settList(bean.vendingContainers);
        containerAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object data) {
                WaitSupStateBean.ScenesBean.VendingContainerGroupsBean.VendingContainersBean vendingContainersBean
                        = bean.vendingContainers.get(position);

                if (vendingContainersBean.central) {//中控台
                    Bundle bundle = new Bundle();
                    bundle.putString("deviceNo", vendingContainersBean.cntrSn);
                    IntentUtil.openActivity(context, ControllerManageActivity.class, bundle);
                } else {//其他货柜
                    showPopupWindow(rvTeamMember, vendingContainersBean);
                }
            }
        });
    }


    private void showPopupWindow(RecyclerView rvTeamMember, WaitSupStateBean.ScenesBean.VendingContainerGroupsBean.VendingContainersBean bean) {
        AbstractStartSupPopWindow popWindow = new AbstractStartSupPopWindow(mContext, mSceneName, bean.vendingContainerName) {
            @Override
            public void onClick(View v) {
                checkOtherSceneIsComplete(mSceneSn, bean);
                dismiss();
            }
        };
        popWindow.showAtLocation(rvTeamMember, Gravity.NO_GRAVITY, 0, 0);
        //popWindow.showAsDropDown(rvTeamMember);
    }

    private void checkOtherSceneIsComplete(String sceneSn, WaitSupStateBean.ScenesBean.VendingContainerGroupsBean.VendingContainersBean bean1) {
        HttpApi.getInstance()
                .startSupplyGoods(UserInfo.USER_ID, sceneSn)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<SceneListBean.GroupsBean>() {
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    protected void onHandleSuccess(SceneListBean.GroupsBean groupsBean) {
                        if (groupsBean == null) {

                            Bundle bundle = new Bundle();
                            String cntrId = bean1.id + "";
                            String cntrSn = bean1.cntrSn;
                            bundle.putString("sceneSn", mSceneSn);
                            bundle.putString("cntrId", cntrId);
                            bundle.putString("containerName", cntrSn + "货柜");
                            IntentUtil.openActivity(context, ContainerManageActivity.class, bundle);
                        } else {//存在其他未补货完成的优享空间
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
}
