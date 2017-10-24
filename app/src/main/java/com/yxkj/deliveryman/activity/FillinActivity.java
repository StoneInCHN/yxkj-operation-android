package com.yxkj.deliveryman.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.base.BaseActivity;
import com.yxkj.deliveryman.callback.CommenDialogSureListener;
import com.yxkj.deliveryman.util.IntentUtil;
import com.yxkj.deliveryman.view.popupwindow.GetCodePopWindow;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 个人资料里面的设置密码
 */

public class FillinActivity extends BaseActivity implements CommenDialogSureListener {

    @BindView(R.id.iv_back_set_pwd)
    ImageView mIvBackSetPwd;
    @BindView(R.id.tv_timecounter)
    TextView mTvTimecounter;
    @BindView(R.id.bt_login)
    Button mBtLogin;
    @BindView(R.id.tv_not_recieve_code)
    TextView mTvNotRecieveCode;
    /*收不到验证码*/
    private TextView tv_not_recieve_code;
    /*提交*/
    private Button bt_login;

    @Override
    public int getContentViewId() {
        return R.layout.activity_fillin;
    }

    @Override
    public void beforeInitView() {

    }

    @Override
    public void initView() {
        tv_not_recieve_code = findViewByIdNoCast(R.id.tv_not_recieve_code);
        bt_login = findViewByIdNoCast(R.id.bt_login);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setEvent() {

    }

    @Override
    public void onClick(View view) {

    }


    @Override
    public void onSure() {

    }

    @OnClick({R.id.iv_back_set_pwd, R.id.bt_login, R.id.tv_not_recieve_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back_set_pwd:
                finish();
                break;
            case R.id.bt_login:
                IntentUtil.openActivity(this, SetPwdActivity.class);
                break;
            case R.id.tv_not_recieve_code:
                GetCodePopWindow getCodePopWindow = new GetCodePopWindow(this);
                getCodePopWindow.setCommenDialogSureListener(this);
                getCodePopWindow.showAsDropDown(tv_not_recieve_code);
                break;
        }
    }
}
