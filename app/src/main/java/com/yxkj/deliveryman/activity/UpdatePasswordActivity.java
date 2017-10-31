package com.yxkj.deliveryman.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.base.BaseActivity;
import com.yxkj.deliveryman.base.BaseEntity;
import com.yxkj.deliveryman.http.BaseObserver;
import com.yxkj.deliveryman.http.HttpApi;
import com.yxkj.deliveryman.bean.response.NullBean;
import com.yxkj.deliveryman.sharepreference.SharePrefreceHelper;
import com.yxkj.deliveryman.sharepreference.SharedKey;
import com.yxkj.deliveryman.util.IntentUtil;
import com.yxkj.deliveryman.util.RsaUtil;
import com.yxkj.deliveryman.util.StringUtil;
import com.yxkj.deliveryman.util.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 修改密码
 */
public class UpdatePasswordActivity extends BaseActivity {
    @BindView(R.id.tv_forget_pwd_update)
    TextView mTvForgetPwdUpdate;
    @BindView(R.id.tv_tip_new_pwd)
    TextView mTvTipNewPwd;
    @BindView(R.id.et_old_pwd_update)
    EditText mEtOldPwd;
    @BindView(R.id.et_new_pwd_update)
    EditText mEtNewPwd;
    @BindView(R.id.et_sure_pwd_update)
    EditText mEtSurePwd;
    @BindView(R.id.bt_confirm_update)
    Button mBtConfirm;
    @BindView(R.id.iv_eye_update_pwd)
    ImageView ivEye;

    @Override
    public int getContentViewId() {
        return R.layout.activity_update_password;
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


    @OnClick({R.id.tv_forget_pwd_update, R.id.bt_confirm_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_forget_pwd_update:
                Bundle bundle = new Bundle();
                bundle.putInt("type", 0);
                IntentUtil.openActivity(this, ForgetPwdActivity.class, bundle);
                break;
            case R.id.bt_confirm_update:
                updatePassword();
                break;
        }
    }

    private void updatePassword() {
        String oldPwd = mEtOldPwd.getText().toString();
        String newPwd = mEtNewPwd.getText().toString();
        String surePwd = mEtSurePwd.getText().toString();

        //未填写完整
        if (TextUtils.isEmpty(oldPwd) || TextUtils.isEmpty(newPwd) || TextUtils.isEmpty(surePwd)) {
            ToastUtil.showShort("请填写密码");
            return;
        }
        //至少8个字符
        if (newPwd.length() < 8) {
            mTvTipNewPwd.setText(R.string.pwd_least_8);
            mTvTipNewPwd.setVisibility(View.VISIBLE);
            return;
        }
        //新旧密码不一致
        if (!newPwd.equals(surePwd)) {
            mTvTipNewPwd.setText(R.string.pwd_not_same);
            mTvTipNewPwd.setVisibility(View.VISIBLE);
            return;
        }
        //必须同时包含字母和数字
        if (!StringUtil.isBothContainLetterAndDigit(newPwd)) {
            mTvTipNewPwd.setText(R.string.pwd_both_letter_digit);
            mTvTipNewPwd.setVisibility(View.VISIBLE);
            return;
        }

        String publicKeyString = SharePrefreceHelper.getInstance().getString(SharedKey.RSA_PUBLIC_KEY);
        String oldPwdEncrypted = "";
        String newPwdEncrypted = "";
        try {
            oldPwdEncrypted = RsaUtil.encryptString(oldPwd, publicKeyString);
            newPwdEncrypted = RsaUtil.encryptString(newPwd, publicKeyString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String phone = SharePrefreceHelper.getInstance().getString(SharedKey.PHONE);
        HttpApi.getInstance()
                .updatePwd(phone, oldPwdEncrypted, newPwdEncrypted)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<NullBean>() {
                    @Override
                    protected void onHandleSuccess(NullBean bean) {
                        ToastUtil.showShort("修改成功");
                        finish();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }

                    @Override
                    protected void onHandleSuccess(BaseEntity<NullBean> baseEntity) {
                        super.onHandleSuccess(baseEntity);
                        SharePrefreceHelper.getInstance().setString(SharedKey.TOKEN, baseEntity.token);
                    }
                });

    }

    @Override
    public void onClick(View v) {

    }
}
