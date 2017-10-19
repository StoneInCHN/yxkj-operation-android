package com.yxkj.deliveryman.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.base.BaseActivity;
import com.yxkj.deliveryman.util.IntentUtil;

/**
 * 登录页面
 */
public class LoginActivity extends BaseActivity {
    /*忘记密码*/
    private TextView tv_forget_pwd;
    /*短信验证码登录*/
    private TextView tv_login_msg;
    /*登录*/
    private Button btn_login;

    @Override
    public int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    public void beforeInitView() {

    }

    @Override
    public void initView() {
        tv_forget_pwd = findViewByIdNoCast(R.id.tv_forget_pwd);
        tv_login_msg = findViewByIdNoCast(R.id.tv_login_msg);
        btn_login = findViewByIdNoCast(R.id.btn_login);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setEvent() {
        setOnClick(tv_forget_pwd, tv_login_msg, btn_login);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login_msg:
                goToForgetPwdActivity(1);
                break;
            case R.id.tv_forget_pwd:
                goToForgetPwdActivity(0);
                break;
            case R.id.btn_login:
                IntentUtil.openActivity(this, MainActivity.class);
                break;
        }
    }

    /**
     * 跳转忘记密码页
     *
     * @param type 0:忘记密码 1:短信验证码登录
     */
    private void goToForgetPwdActivity(int type) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        IntentUtil.openActivity(this, ForgetPwdActivity.class, bundle);
    }
}
