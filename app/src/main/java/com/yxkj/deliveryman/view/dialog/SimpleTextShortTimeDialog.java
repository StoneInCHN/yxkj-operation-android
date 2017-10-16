package com.yxkj.deliveryman.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.yxkj.deliveryman.R;


/*
 *  @项目名：  yxkj-operation-android 
 *  @包名：    com.yxkj.deliveryman.view.dialog
 *  @文件名:   SimpleTextShortTimeDialog
 *  @创建者:   hhe
 *  @创建时间:  2017/10/16 15:51
 *  @描述：    只弹出文字的dialog,只显示一秒
 */
public class SimpleTextShortTimeDialog extends Dialog {
    private Context mContext;
    private String mContent;
    private int DELAYED_TIME = 1000;

    public SimpleTextShortTimeDialog(@NonNull Context context, String content, @NonNull OnLoadEndListener onLoadEndListener) {
        super(context);
        mContext = context;
        mContent = content;
        mOnLoadEndListener = onLoadEndListener;
        initView();
    }

    private TextView mTvContent;

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_simple_text, null);
        mTvContent = view.findViewById(R.id.tv_content_dialog_simple_text);
        mTvContent.setText(mContent);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
                mOnLoadEndListener.onLoadEnd();
            }
        }, DELAYED_TIME);
    }

    public interface OnLoadEndListener {
        void onLoadEnd();
    }

    private OnLoadEndListener mOnLoadEndListener;

}