package com.yxkj.deliveryman.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yxkj.deliveryman.R;

/**
 * 项目名： yxkj-operation-android
 * 包名： com.yxkj.deliveryman.view
 * 文件名: NetErrorView
 * 创建者: hhe
 * 创建时间: 2017/11/6 17:00
 * 描述： 无网络页面
 */
public class NetErrorView extends FrameLayout {
    public interface OnRetryListener {
        /**
         * 重试
         */
        void onRetry();
    }

    public NetErrorView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.view_net_error, this, false);
        addView(view);

        initView();
    }

    private TextView tvRetry;
    private LinearLayout llRootView;

    private OnRetryListener mOnRetryListener;

    public void setOnRetryListener(OnRetryListener onRetryListener) {
        mOnRetryListener = onRetryListener;
    }

    private void initView() {
        tvRetry = findViewById(R.id.tv_retry_view_net_error);
        llRootView = findViewById(R.id.ll_view_net_error);
        tvRetry.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnRetryListener.onRetry();
            }
        });
    }

    public void show() {
        llRootView.setVisibility(VISIBLE);
    }

    public void dismiss() {
        llRootView.setVisibility(GONE);
    }
}
