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
import com.yxkj.deliveryman.callback.CompleteListener;
import com.yxkj.deliveryman.constant.VerificationCodeType;
import com.yxkj.deliveryman.http.HttpApi;
import com.yxkj.deliveryman.bean.response.GetCodeBean;
import com.yxkj.deliveryman.bean.response.LoginBean;
import com.yxkj.deliveryman.bean.response.NullBean;
import com.yxkj.deliveryman.sharepreference.SharePrefreceHelper;
import com.yxkj.deliveryman.sharepreference.SharedKey;
import com.yxkj.deliveryman.util.IntentUtil;
import com.yxkj.deliveryman.util.TimeCountUtil;
import com.yxkj.deliveryman.util.ToastUtil;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 忘记密码/短信验证码登录 共同页面
 */
public class ForgetPwdActivity extends BaseActivity {
    /*获取短信验证码*/
    private TextView mTvGetCode;
    /*登录*/
    private Button bt_confirm;
    /*倒计时*/
    private TimeCountUtil timeCountUtil;

    private ImageView iv_back;

    @BindView(R.id.et_phone_forget_pwd)
    EditText mEtPhone;
    @BindView(R.id.et_code_forget_pwd)
    EditText mEtCode;

    /*0->忘密码，1->短信验证码登录*/
    private int mType;
    private String phone;

    @Override
    public int getContentViewId() {
        return R.layout.activity_forget_pwd;
    }

    @Override
    public void beforeInitView() {
        mType = getIntent().getIntExtra("type", 0);
    }

    @Override
    public void initView() {
        mTvGetCode = findViewByIdNoCast(R.id.tv_get_code);
        bt_confirm = findViewByIdNoCast(R.id.bt_confirm);
        iv_back = findViewByIdNoCast(R.id.iv_back_get_code);
    }

    @Override
    public void initData() {
        timeCountUtil = new TimeCountUtil();
        timeCountUtil.setCompleteListener(new CompleteListener() {
            @Override
            public void onComplete() {
                initTvGetCode();
            }
        });
    }


    @Override
    public void setEvent() {
        setOnClick(bt_confirm, mTvGetCode, iv_back);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.bt_confirm:
                switch (mType) {
                    case 0://忘记密码,下一步是设置新密码
                        goToSetNewPwd();
                        break;
                    case 1://验证码登录
                        loginByCode();
                        break;
                }
                break;
            case R.id.tv_get_code:
                getVertificationCode();
                break;
            case R.id.iv_back_get_code:
                finish();
                break;
        }
    }

    private void loginByCode() {
        String code = mEtCode.getText().toString();
        if (TextUtils.isEmpty(code)) {
            ToastUtil.showShort("请填写验证码");
            return;
        }

        HttpApi.getInstance()
                .loginByVft(phone, code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<LoginBean>() {
                    @Override
                    protected void onHandleSuccess(LoginBean loginBean) {
                        SharePrefreceHelper.getInstance().setString(SharedKey.USER_ID, loginBean.id+"");
                        IntentUtil.openActivity(mContext, MainActivity.class);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }

                    @Override
                    protected void onHandleSuccess(BaseEntity<LoginBean> baseEntity) {
                        super.onHandleSuccess(baseEntity);
                        SharePrefreceHelper.getInstance().setString(SharedKey.TOKEN, baseEntity.token);
                    }
                });
    }

    private void goToSetNewPwd() {
        String code = mEtCode.getText().toString();
        if (TextUtils.isEmpty(code)) {
            ToastUtil.showShort("请填写验证码");
            return;
        }

        HttpApi.getInstance()
                .forgetPwdVft(phone, code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<NullBean>() {
                    @Override
                    protected void onHandleSuccess(NullBean bean) {
                        Bundle bundle = new Bundle();
                        bundle.putString("phone", phone);
                        IntentUtil.openActivity(mContext, SetPwdActivity.class, bundle);
                        cancleTimeCount();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });


    }

    private void getVertificationCode() {
        phone = mEtPhone.getText().toString();
        if (phone.length() != 11) {
            ToastUtil.showShort("请输入11位手机号");
            return;
        }

        String verificationCodeType;
        switch (mType) {
            case 0:
                verificationCodeType = VerificationCodeType.TYPE_RESETPWD;
                break;
            case 1:
                verificationCodeType = VerificationCodeType.TYPE_LOGIN;
                break;
            default:
                verificationCodeType = VerificationCodeType.TYPE_LOGIN;
                break;
        }

        HttpApi.getInstance()
                .getVerificationCode(phone, verificationCodeType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<GetCodeBean>() {
                    @Override
                    protected void onHandleSuccess(GetCodeBean bean) {
                        startTimeCount();
                        mEtCode.setText(bean.code);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });

    }

    /**
     * 开始倒计时
     */
    private void startTimeCount() {
        mTvGetCode.setEnabled(false);
        mTvGetCode.setTextColor(getResources().getColor(R.color.orange_text));
        timeCountUtil.countDown(0, 60, mTvGetCode, "后重新发送");
    }

    /**
     * 初始化倒计时
     */
    private void initTvGetCode() {
        mTvGetCode.setEnabled(true);
        mTvGetCode.setTextColor(getResources().getColor(R.color.gray_text));
        mTvGetCode.setEnabled(true);
        mTvGetCode.setText("获取验证码");
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
