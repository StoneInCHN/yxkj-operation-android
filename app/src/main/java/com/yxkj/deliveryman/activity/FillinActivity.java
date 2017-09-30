package com.yxkj.deliveryman.activity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.base.BaseActivity;
import com.yxkj.deliveryman.callback.CommenDialogSureListener;
import com.yxkj.deliveryman.util.IntentUtil;
import com.yxkj.deliveryman.view.GetCodePopWindow;

public class FillinActivity extends BaseActivity implements CommenDialogSureListener {

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
        setOnClick(tv_not_recieve_code, bt_login);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_not_recieve_code:
                GetCodePopWindow getCodePopWindow = new GetCodePopWindow(this);
                getCodePopWindow.setCommenDialogSureListener(this);
                getCodePopWindow.showAsDropDown(tv_not_recieve_code);
                break;
            case R.id.bt_login:
                IntentUtil.openActivity(this, SetPwdActivity.class);
                break;
        }
    }

    @Override
    public void onSure() {

    }
}
