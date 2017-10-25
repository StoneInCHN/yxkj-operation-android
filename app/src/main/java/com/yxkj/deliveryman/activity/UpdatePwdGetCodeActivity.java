/*
package com.yxkj.deliveryman.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.base.BaseActivity;
import com.yxkj.deliveryman.callback.CommenDialogSureListener;
import com.yxkj.deliveryman.util.IntentUtil;
import com.yxkj.deliveryman.view.popupwindow.GetCodePopWindow;

import butterknife.BindView;
import butterknife.OnClick;

*/
/**
 * 个人资料-修改密码-忘记密码
 *//*


public class UpdatePwdGetCodeActivity extends BaseActivity implements CommenDialogSureListener {

    @BindView(R.id.tv_get_code_update_pwd)
    TextView mTvGetCode;
    @BindView(R.id.bt_confirm_update_pwd_get_code)
    Button mBtConfirm;
    @BindView(R.id.tv_not_recieve_code)
    TextView mTvNotRecieveCode;
    @BindView(R.id.tv_phone_update_pwd_get_code)
    TextView mTvPhone;
    @BindView(R.id.et_code_update_pwd)
    EditText mEtCode;

    @Override
    public int getContentViewId() {
        return R.layout.activity_update_pwd_get_code;
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

    }


    @Override
    public void onSure() {

    }

    @OnClick({R.id.iv_back_get_code_update, R.id.bt_confirm_update_pwd_get_code, R.id.tv_not_recieve_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back_get_code_update:
                finish();
                break;
            case R.id.bt_confirm_update_pwd_get_code:
                IntentUtil.openActivity(this, SetPwdActivity.class);
                break;
            case R.id.tv_not_recieve_code:
                GetCodePopWindow getCodePopWindow = new GetCodePopWindow(this);
                getCodePopWindow.setCommenDialogSureListener(this);
                getCodePopWindow.showAsDropDown(mTvNotRecieveCode);
                break;
        }
    }
}
*/
