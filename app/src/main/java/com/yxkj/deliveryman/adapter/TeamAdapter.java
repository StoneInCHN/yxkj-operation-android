package com.yxkj.deliveryman.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.activity.ContainerManageActivity;
import com.yxkj.deliveryman.activity.ControllerManageActivity;
import com.yxkj.deliveryman.base.BaseObserver;
import com.yxkj.deliveryman.base.BaseRecyclerViewAdapter;
import com.yxkj.deliveryman.base.BaseViewHolder;
import com.yxkj.deliveryman.bean.response.SceneListBean;
import com.yxkj.deliveryman.bean.response.WaitSupStateBean;
import com.yxkj.deliveryman.constant.UserInfo;
import com.yxkj.deliveryman.http.HttpApi;
import com.yxkj.deliveryman.util.IntentUtil;
import com.yxkj.deliveryman.view.dialog.TextButtonDialog;
import com.yxkj.deliveryman.view.popupwindow.ContainerSupPopWindow;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 货柜分组
 */

public class TeamAdapter extends BaseRecyclerViewAdapter<WaitSupStateBean.ScenesBean.VendingContainerGroupsBean> {
    private Context mContext;
    private String mSceneSn;

    public TeamAdapter(Context context, String sceneSn) {
        super(context);
        mContext = context;
        mSceneSn = sceneSn;
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
                if (bean.vendingContainers.get(position).central) {//中控台
                    IntentUtil.openActivity(context, ControllerManageActivity.class);
                } else {//其他货柜
                    checkOtherSceneIsComplete(mSceneSn, rvTeamMember, data);
                }
            }
        });
    }

    private void checkOtherSceneIsComplete(String sceneSn, RecyclerView rvTeamMember, Object data) {
        HttpApi.getInstance()
                .startSupplyGoods(UserInfo.USER_ID, sceneSn)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<SceneListBean.GroupsBean>() {
                    @Override
                    protected void onHandleSuccess(SceneListBean.GroupsBean groupsBean) {
                        if (groupsBean == null) {
                            showPopupWindow(rvTeamMember, data);
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

    private void showPopupWindow(RecyclerView rvTeamMember, Object data) {
        ContainerSupPopWindow popWindow = new ContainerSupPopWindow(mContext) {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                WaitSupStateBean.ScenesBean.VendingContainerGroupsBean.VendingContainersBean bean1 =
                        (WaitSupStateBean.ScenesBean.VendingContainerGroupsBean.VendingContainersBean) data;
                String cntrId = bean1.id + "";
                String cntrSn = bean1.cntrSn;
                bundle.putString("cntrId", cntrId);
                bundle.putString("containerName", cntrSn + "货柜");
                IntentUtil.openActivity(context, ContainerManageActivity.class, bundle);
                dismiss();
            }
        };
        popWindow.showAsDropDown(rvTeamMember);
    }
}
