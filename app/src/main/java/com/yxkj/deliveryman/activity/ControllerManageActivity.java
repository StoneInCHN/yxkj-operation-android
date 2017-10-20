package com.yxkj.deliveryman.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.base.BaseActivity;
import com.yxkj.deliveryman.view.dialog.TextButtonDialog;
import com.yxkj.deliveryman.view.dialog.TextProgressbarDialog;

/**
 * 中控管理
 */
public class ControllerManageActivity extends BaseActivity {

    @Override
    public int getContentViewId() {
        return R.layout.activity_controller_manage;
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

    private TextProgressbarDialog mTextProgressbarDialog;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_reboot_controller:
                mTextProgressbarDialog = new TextProgressbarDialog(mContext);
                mTextProgressbarDialog.show();
                break;
        }
    }

    private void rebootController() {
        // TODO: 2017/10/20 做重启中控操作

        mTextProgressbarDialog.dismiss();
        new TextButtonDialog(mContext,"","重启完成","确定").show();
    }
}
