package com.yxkj.deliveryman.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.base.BaseActivity;
import com.yxkj.deliveryman.base.BaseEntity;
import com.yxkj.deliveryman.http.BaseObserver;
import com.yxkj.deliveryman.constant.UserInfo;
import com.yxkj.deliveryman.http.HttpApi;
import com.yxkj.deliveryman.bean.response.LoginBean;
import com.yxkj.deliveryman.bean.response.PublicKeyBean;
import com.yxkj.deliveryman.sharepreference.SharePrefreceHelper;
import com.yxkj.deliveryman.sharepreference.SharedKey;
import com.yxkj.deliveryman.util.IntentUtil;
import com.yxkj.deliveryman.util.LogUtil;
import com.yxkj.deliveryman.util.RsaUtil;
import com.yxkj.deliveryman.util.ToastUtil;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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

    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.tv_tip_account_error)
    TextView mTvTip;
    @BindView(R.id.et_pwd)
    EditText mEtPwd;

    @Override
    public int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        tv_forget_pwd = findViewByIdNoCast(R.id.tv_forget_pwd);
        tv_login_msg = findViewByIdNoCast(R.id.tv_login_msg);
        btn_login = findViewByIdNoCast(R.id.bt_confirm);
    }

    private String publicKeyString;

    @Override
    public void initData() {
        publicKeyString = SharePrefreceHelper.getInstance().getString(SharedKey.RSA_PUBLIC_KEY);
        LogUtil.i(TAG, "密钥" + publicKeyString);
        if (TextUtils.isEmpty(publicKeyString)) {
            getRSAPublicKey();
        }
    }

    /**
     * 获取公钥
     */
    private void getRSAPublicKey() {
        HttpApi.getInstance()
                .getPublicKey()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<PublicKeyBean>() {
                    @Override
                    protected void onHandleSuccess(PublicKeyBean bean) {
                        publicKeyString = bean.publicKey;
                        SharePrefreceHelper.getInstance().setString(SharedKey.RSA_PUBLIC_KEY, publicKeyString);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    @Override
    public void setEvent() {
        setOnClick(tv_forget_pwd, tv_login_msg, btn_login);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //登录
            case R.id.tv_login_msg:
                goToForgetPwdActivity(1);
                break;
            //忘记密码
            case R.id.tv_forget_pwd:
                goToForgetPwdActivity(0);
                break;
            case R.id.bt_confirm:
                login();
                break;
        }
    }

    private void login() {
        String phone = mEtPhone.getText().toString();
        if (phone.length() != 11) {
            ToastUtil.showShort("请输入11位手机号");
            return;
        }
        String password = mEtPwd.getText().toString();
        if (TextUtils.isEmpty(password)) {
            ToastUtil.showShort("请输入密码");
            return;
        }

        publicKeyString = SharePrefreceHelper.getInstance().getString(SharedKey.RSA_PUBLIC_KEY);

        String pwdEncryped = null;
        try {
            pwdEncryped = RsaUtil.encryptString(password, publicKeyString);
        } catch (Exception e) {
            e.printStackTrace();
            //ToastUtil.showShort("加密失败");
        }
        LogUtil.e(TAG, pwdEncryped);
        try {
            HttpApi.getInstance()
                    .loginByPwd(phone, pwdEncryped)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<LoginBean>() {
                        @Override
                        protected void onHandleSuccess(LoginBean loginBean) {
                            UserInfo.USER_ID = loginBean.id + "";
                            SharePrefreceHelper.getInstance().setString(SharedKey.USER_ID, loginBean.id);
                            SharePrefreceHelper.getInstance().setString(SharedKey.PHONE, phone);
                            IntentUtil.openActivity(mContext, MainActivity.class);
                        }

                        @Override
                        protected void onHandleSuccess(BaseEntity<LoginBean> baseEntity) {
                            super.onHandleSuccess(baseEntity);
                            SharePrefreceHelper.getInstance().setString(SharedKey.TOKEN, baseEntity.token);
                        }

                        @Override
                        protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
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
