package com.yxkj.deliveryman.activity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.base.BaseActivity;
import com.yxkj.deliveryman.callback.CommenDialogSureListener;
import com.yxkj.deliveryman.callback.CompleteListener;
import com.yxkj.deliveryman.util.IntentUtil;
import com.yxkj.deliveryman.util.TimeCountUtil;
import com.yxkj.deliveryman.view.dialog.CommonYesOrNoDialog;

/**
 * 忘记密码和短信验证码登录页面
 */
public class ForgetPwdActivity extends BaseActivity implements CommenDialogSureListener, CompleteListener {
    /*获取短信验证码*/
    private TextView tv_get_code;
    /*登录*/
    private Button btn_login;
    /*倒计时*/
    private TimeCountUtil timeCountUtil;
    /*0->忘密码，1->短信验证码登录*/
    private int type;

    @Override
    public int getContentViewId() {
        return R.layout.activity_forget_pwd;
    }

    @Override
    public void beforeInitView() {
        type = getIntent().getIntExtra("type", 0);
    }

    @Override
    public void initView() {
        tv_get_code = findViewByIdNoCast(R.id.tv_get_code);
        btn_login = findViewByIdNoCast(R.id.btn_login);
    }

    @Override
    public void initData() {
        timeCountUtil = new TimeCountUtil();
    }


    @Override
    public void setEvent() {
        setOnClick(btn_login, tv_get_code);
        timeCountUtil.setCompleteListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                if (type == 0) {
                    IntentUtil.openActivity(this, SetPwdActivity.class);
                    cancleTimeCount();
                }
                break;
            case R.id.tv_get_code:
                CommonYesOrNoDialog commonYesOrNoDialog = new CommonYesOrNoDialog();
                commonYesOrNoDialog.setDialogSureListener(this);
                commonYesOrNoDialog.show(getSupportFragmentManager(), "commonYesOrNoDialog");
                break;
        }
    }

    /**
     * dialog点击确认
     */
    @Override
    public void onSure() {
        startTimeCount();
    }

    /**
     * 倒计时结束
     */
    @Override
    public void onComplete() {
        initTvGetCode();
    }

    /**
     * 开始倒计时
     */
    private void startTimeCount() {
        tv_get_code.setEnabled(false);
        timeCountUtil.countDown(0, 60, tv_get_code, "后重新发送");
    }

    /**
     * 初始化倒计时
     */
    private void initTvGetCode() {
        tv_get_code.setEnabled(true);
        tv_get_code.setText("获取验证码");
    }

    /**
     * 取消倒计时
     */
    private void cancleTimeCount() {
        timeCountUtil.cancle();
        initTvGetCode();
    }

    @Override
    protected void onDestroy() {
        cancleTimeCount();
        super.onDestroy();
    }
}
