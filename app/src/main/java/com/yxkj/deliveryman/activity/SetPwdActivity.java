package com.yxkj.deliveryman.activity;

import android.view.View;
import android.widget.TextView;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.base.BaseActivity;
import com.yxkj.deliveryman.util.ToastUtil;

/**
 * 设置密码页面
 */
public class SetPwdActivity extends BaseActivity {
    /*设置密码完成*/

    @Override
    public int getContentViewId() {
        return R.layout.activity_set_pwd;
    }

    @Override
    public void beforeInitView() {

    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {

    }

    @Override
    public void setEvent() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_complete:
                ToastUtil.showToastCenter("设置完成");
                break;
        }
    }
}
