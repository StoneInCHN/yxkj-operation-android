package com.yxkj.deliveryman.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.base.BaseActivity;
import com.yxkj.deliveryman.base.BaseEntity;
import com.yxkj.deliveryman.constant.Constants;
import com.yxkj.deliveryman.constant.UserInfo;
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
    @BindView(R.id.tv_tip_old_pwd)
    TextView mTvTipOldPwd;
    @BindView(R.id.et_old_pwd_update)
    EditText mEtOldPwd;
    @BindView(R.id.et_new_pwd_update)
    EditText mEtNewPwd;
    @BindView(R.id.et_sure_pwd_update)
    EditText mEtSurePwd;
    @BindView(R.id.bt_confirm_update)
    Button mBtConfirm;
    private Handler mHandler;

    @Override
    public int getContentViewId() {
        return R.layout.activity_update_password;
    }

    @Override
    public void beforeInitView() {

    }

    @Override
    public void initView() {
        mHandler = new Handler();
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
            default:
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
            setNewPwdTipError(R.string.pwd_least_8);
            return;
        }
        //新旧密码不一致
        if (!newPwd.equals(surePwd)) {
            setNewPwdTipError(R.string.pwd_not_same);
            return;
        }
        //必须同时包含字母和数字
        if (!StringUtil.isBothContainLetterAndDigit(newPwd)) {
            setNewPwdTipError(R.string.pwd_both_letter_digit);
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
                .updatePwd(UserInfo.USER_ID, phone, oldPwdEncrypted, newPwdEncrypted)
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
                    protected void onHandleError(BaseEntity<NullBean> baseEntity) {
                        switch (baseEntity.code) {
                            case 1002://旧密码错误
                                mTvTipOldPwd.setVisibility(View.VISIBLE);
                                mHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        mTvTipOldPwd.setVisibility(View.GONE);
                                        //mEtOldPwd.setText("");
                                    }
                                }, Constants.ERROR_TIP_TIME);
                                break;
                            default:
                                ToastUtil.showShort(baseEntity.desc);
                                break;
                        }

                    }

                    @Override
                    protected void onHandleSuccess(BaseEntity<NullBean> baseEntity) {
                        super.onHandleSuccess(baseEntity);
                        SharePrefreceHelper.getInstance().setString(SharedKey.TOKEN, baseEntity.token);
                    }
                });

    }

    private void setNewPwdTipError(@StringRes int errorText) {
        mTvTipNewPwd.setVisibility(View.VISIBLE);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mTvTipNewPwd.setVisibility(View.GONE);
                mTvTipNewPwd.setText(errorText);
                // mEtNewPwd.setText("");
                // mEtSurePwd.setText("");
            }
        }, Constants.ERROR_TIP_TIME);
    }


    @Override
    public void onClick(View v) {

    }
}
