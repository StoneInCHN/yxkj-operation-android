package com.yxkj.deliveryman.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.base.BaseActivity;
import com.yxkj.deliveryman.base.BaseObserver;
import com.yxkj.deliveryman.http.HttpApi;
import com.yxkj.deliveryman.response.NullBean;
import com.yxkj.deliveryman.sharepreference.SharePrefreceHelper;
import com.yxkj.deliveryman.sharepreference.SharedKey;
import com.yxkj.deliveryman.util.RsaUtil;
import com.yxkj.deliveryman.util.StringUtil;
import com.yxkj.deliveryman.util.ToastUtil;
import com.yxkj.deliveryman.view.dialog.TextShortTimeDialog;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 设置密码页面
 */
public class SetPwdActivity extends BaseActivity {
    @BindView(R.id.tv_phone_set_pwd)
    TextView mTvPhone;
    @BindView(R.id.et_pwd1_set_pwd)
    TextView mEtPwd1;
    /**
     * 确认密码
     */
    @BindView(R.id.et_pwd2_set_pwd)
    TextView mEtPwd2;
    @BindView(R.id.bt_confirm_set_pwd)
    TextView mBtConfirm;
    private String phone;


    @Override
    public int getContentViewId() {
        return R.layout.activity_set_pwd;
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {
        phone = getIntent().getExtras().getString("phone");
        mTvPhone.setText(phone);
    }

    @Override
    public void setEvent() {
        setOnClick(mBtConfirm);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_confirm_set_pwd:
                setNewPwd();
                break;
        }
    }

    private void setNewPwd() {
        if (!checkPwd()) {
            return;
        }

        String publicKey = SharePrefreceHelper.getInstance().getString(SharedKey.RSA_PUBLIC_KEY);
        String pwdEncryed = "";
        try {
            pwdEncryed = RsaUtil.encryptString(mEtPwd1.getText().toString(), publicKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpApi.getInstance()
                .resetPwd(phone, pwdEncryed)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<NullBean>() {
                    @Override
                    protected void onHandleSuccess(NullBean bean) {
                        showSuccessDialog();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });

    }

    private void showSuccessDialog() {
        new TextShortTimeDialog(mContext, "密码设置成功", new TextShortTimeDialog.OnLoadEndListener() {
            @Override
            public void onLoadEnd() {
                ToastUtil.showShort("假装跳转");
            }
        }).show();
    }

    private boolean checkPwd() {
        String pwd1 = mEtPwd1.getText().toString();
        String pwd2 = mEtPwd2.getText().toString();

        if (pwd1.length() < 8) {
            ToastUtil.showShort("新密码至少8个字符");
            return false;
        }

        if (!pwd1.equals(pwd2)) {
            ToastUtil.showShort("两次密码输入不一致，请重新输入");
            return false;
        }

        if (!StringUtil.isBothContainLetterAndDigit(pwd1)) {
            ToastUtil.showShort("密码必须同时含有数字和字母，请重新输入");
            return false;
        }

        return true;
    }
}
