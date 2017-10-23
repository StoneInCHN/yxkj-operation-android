package com.yxkj.deliveryman.base;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.widget.Toast;

import com.yxkj.deliveryman.application.MyApplication;
import com.yxkj.deliveryman.util.LogUtil;

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

    protected BaseObserver( ) {
        this.mContext = MyApplication.getAppContext();
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(BaseEntity<T> value) {
        LogUtil.i(TAG, "values  " + value.toString());
        if (value.code == 0000) {
            T t = value.msg;
            onHandleSuccess(t);
        } else {
            onHandleError(value.desc);
        }
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
            } else {
                onFailure(e, false);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void onComplete() {
        LogUtil.d(TAG, "onComplete");
    }

    protected abstract void onHandleSuccess(T t);

    protected abstract void onFailure(Throwable e, boolean isNetWorkError) throws Exception;

    protected void onHandleError(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }
}
