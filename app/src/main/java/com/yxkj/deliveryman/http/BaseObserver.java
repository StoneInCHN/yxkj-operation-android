package com.yxkj.deliveryman.http;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.yxkj.deliveryman.activity.LoginActivity;
import com.yxkj.deliveryman.application.MyApplication;
import com.yxkj.deliveryman.base.BaseEntity;
import com.yxkj.deliveryman.util.IntentUtil;
import com.yxkj.deliveryman.util.LogUtil;
import com.yxkj.deliveryman.util.ToastUtil;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * BaseObserver
 * Created by zengqiang on 2017/8/24.
 */
public abstract class BaseObserver<T> implements Observer<BaseEntity<T>> {

    private static final String TAG = "BaseObserver";
    private Context mContext;

    protected BaseObserver() {
        this.mContext = MyApplication.getAppContext();
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(BaseEntity<T> value) {
        LogUtil.i(TAG, "values  " + value.toString());
        switch (value.code) {
            case 0000:
                T t = value.msg;
                try {
                    onHandleSuccess(t);
                } catch (Exception e) {
                    try {
                        onFailure(e, false);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
                onHandleSuccess(value);
                break;
            case 8://过期
                IntentUtil.logout(mContext);
                ToastUtil.showShort("登录已过期，请重新登录");
                break;
            default:
                onHandleError(value.desc);
                break;
        }
    }

    @Override
    public void onComplete() {
    }

    @Override
    public void onError(Throwable e) {
        LogUtil.e(TAG, "onError:" + e.toString());
        try {
            if (e instanceof ConnectException
                    || e instanceof TimeoutException
                    || e instanceof NetworkErrorException
                    || e instanceof UnknownHostException) {
                onFailure(e, true);
                ToastUtil.showShort("网络出错了，请检查网络");
            } else {
                onFailure(e, false);
                ToastUtil.showShort("服务器发生了一个问题，请稍后再试");
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    protected abstract void onHandleSuccess(T t);

    protected void onHandleSuccess(BaseEntity<T> baseEntity) {

    }

    protected abstract void onFailure(Throwable e, boolean isNetWorkError) throws Exception;

    protected void onHandleError(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }
}
