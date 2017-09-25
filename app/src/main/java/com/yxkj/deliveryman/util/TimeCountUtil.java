package com.yxkj.deliveryman.util;

import android.widget.TextView;

import com.yxkj.deliveryman.callback.CompleteListener;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 倒计时
 */

public class TimeCountUtil {
    private Disposable disposable;
    private CompleteListener completeListener;

    public void setCompleteListener(CompleteListener completeListener) {
        this.completeListener = completeListener;
    }

    /**
     * 支付倒计时
     */
    public void countDown(long start, long end, TextView textView, String desc) {
        Observable.intervalRange(start, end + 1, 0, 1, TimeUnit.SECONDS, Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(@NonNull Long aLong) {
                textView.setText((end - aLong) + "s" + desc);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {
                if (completeListener != null) {
                    completeListener.onComplete();
                }
            }
        });
    }

    public void cancle() {
        if (disposable != null) {
            if (!disposable.isDisposed())
                disposable.dispose();
        }
    }
}
