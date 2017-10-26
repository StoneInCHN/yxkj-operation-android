package com.yxkj.deliveryman.fragment;


import android.view.View;
import android.widget.TextView;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.activity.UpdatePasswordActivity;
import com.yxkj.deliveryman.base.BaseFragment;
import com.yxkj.deliveryman.util.IntentUtil;

/**
 * 首页个人中心页面
 */
public class MineFragment extends BaseFragment {
    /*密码*/
    private TextView tv_pwd;

    @Override
    protected int getResource() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void beforeInitView() {

    }

    @Override
    protected void initView(View rootView) {
        tv_pwd = findViewByIdNoCast(R.id.tv_pwd);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setEvent() {
        setOnClick(tv_pwd);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_pwd:
                IntentUtil.openActivity(getActivity(), UpdatePasswordActivity.class);
                break;
        }
    }
}
