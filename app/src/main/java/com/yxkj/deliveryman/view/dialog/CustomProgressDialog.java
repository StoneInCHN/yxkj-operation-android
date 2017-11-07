package com.yxkj.deliveryman.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yxkj.deliveryman.R;

/**
 * 项目名：  yxkj-operation-android
 * 包名：    com.yxkj.deliveryman.view.dialog
 * 文件名:   CustomProgressDialog
 * 创建者:   hhe
 * 创建时间:  2017/11/7 10:24
 * 描述：    加载框
 */
public class CustomProgressDialog {

    private Dialog mDialog;
    private TextView tvContent;
    private Context mContext;

    private CustomProgressDialog(Context context) {
        mContext = context;
        initDialogView();
    }

    private void initDialogView() {
        mDialog = new Dialog(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_progress, null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mDialog.setContentView(view, params);

        tvContent = view.findViewById(R.id.tv_dialog_progress);
    }

    private static CustomProgressDialog mCustomProgressDialog;

    public static CustomProgressDialog getInstance(Context context) {
        if (mCustomProgressDialog == null) {
            synchronized (CustomProgressDialog.class) {
                if (mCustomProgressDialog == null) {
                    mCustomProgressDialog = new CustomProgressDialog(context);
                }
            }
        }
        return mCustomProgressDialog;
    }

    /**
     * 开始显示的时间
     */
    private long startShowTime;

    public void show(String tipText) {
        tvContent.setText(tipText);
        show();
    }

    public void show() {
        mDialog.show();
        startShowTime = System.currentTimeMillis();
    }

    /**
     * 最少显示时间
     */
    private final long MIN_SHOW_TIME = 500;

    public void dismiss() {
        long nowTime = System.currentTimeMillis();
        if (nowTime - startShowTime < MIN_SHOW_TIME) {
            long delayTime = MIN_SHOW_TIME - (nowTime - startShowTime);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mDialog.dismiss();
                }
            }, delayTime);
        } else {
            mDialog.dismiss();
        }


    }
}
