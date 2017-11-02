package com.yxkj.deliveryman.fragment;


import android.view.View;
import android.widget.TextView;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.activity.UpdatePasswordActivity;
import com.yxkj.deliveryman.base.BaseFragment;
import com.yxkj.deliveryman.constant.UserInfo;
import com.yxkj.deliveryman.sharepreference.SharePrefreceHelper;
import com.yxkj.deliveryman.sharepreference.SharedKey;
import com.yxkj.deliveryman.util.IntentUtil;

/**
 * 首页个人中心页面
 */
public class MineFragment extends BaseFragment {
    /*密码*/
    private TextView mTvPwd;
    private TextView mTvLogout;
    private TextView mTvName;

    @Override
    protected int getResource() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void beforeInitView() {

    }

    @Override
    protected void initView(View rootView) {
        mTvPwd = findViewByIdNoCast(R.id.tv_pwd);
        mTvName = findViewByIdNoCast(R.id.tv_user_name_fragment_my);
        mTvLogout = findViewByIdNoCast(R.id.tv_logout);
    }

    @Override
    protected void initData() {
        String userName = SharePrefreceHelper.getInstance().getString(SharedKey.PHONE);
        mTvName.setText("账户:" + userName);
    }

    @Override
    protected void setEvent() {
        setOnClick(mTvPwd, mTvLogout);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_pwd:
                IntentUtil.openActivity(getActivity(), UpdatePasswordActivity.class);
                break;
            case R.id.tv_logout:
                IntentUtil.logout(getActivity());
                break;
            default:
                break;
        }
    }
}
