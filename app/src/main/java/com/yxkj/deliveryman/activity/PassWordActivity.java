package com.yxkj.deliveryman.activity;

import android.view.View;
import android.widget.TextView;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.base.BaseActivity;
import com.yxkj.deliveryman.callback.CommenDialogSureListener;
import com.yxkj.deliveryman.util.IntentUtil;
import com.yxkj.deliveryman.view.dialog.CommonYesOrNoDialog;

public class PassWordActivity extends BaseActivity implements CommenDialogSureListener {
    /*忘记密码*/
    private TextView tv_forget_pwd;

    @Override
    public int getContentViewId() {
        return R.layout.activity_pass_word;
    }

    @Override
    public void beforeInitView() {

    }

    @Override
    public void initView() {
        tv_forget_pwd = findViewByIdNoCast(R.id.tv_forget_pwd);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setEvent() {
        setOnClick(tv_forget_pwd);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_forget_pwd:
                CommonYesOrNoDialog commonYesOrNoDialog = new CommonYesOrNoDialog();
                commonYesOrNoDialog.setDialogSureListener(this);
                commonYesOrNoDialog.show(getSupportFragmentManager(), "commonYesOrNoDialog");
                break;
        }
    }

    @Override
    public void onSure() {
        IntentUtil.openActivity(this, FillinActivity.class);
    }
}
