package com.yxkj.deliveryman.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.callback.CommonDialogSureListener;

/**
 * 通用Dialog，有yes和no两种选项
 */

public class CommonYesOrNoDialog extends Dialog {
    /*主题*/
    private TextView tv_title;
    /*内容*/
    private TextView tv_content;
    /*取消按钮*/
    private TextView tvCancel;
    /*确认按钮*/
    private TextView tvSure;
    /*确认按钮监听*/
    private CommonDialogSureListener dialogSureListener;
    private Context mContext;

    public CommonYesOrNoDialog(@NonNull Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public void setDialogSureListener(CommonDialogSureListener dialogSureListener) {
        this.dialogSureListener = dialogSureListener;
    }

    public void initView() {
        setContentView(R.layout.dialog_common_yesorno);
        tv_title = findViewById(R.id.tv_title_dialog_yesorno);
        tv_content = findViewById(R.id.tv_content_dialog_yesorno);
        tvCancel = findViewById(R.id.tv_cancel_dialog_yesorno);
        tvSure = findViewById(R.id.tv_sure_dialog_yesorno);
        tvCancel.setOnClickListener(view1 -> dismiss());
        tvSure.setOnClickListener(view1 -> {
            dismiss();
            if (dialogSureListener != null) {
                dialogSureListener.onSure();
            }
        });
    }

    public void setTv_title(String tv_title) {
        this.tv_title.setText(tv_title);
    }

    public void setBtn_cancle(String btn_cancle) {
        this.tvCancel.setText(btn_cancle);
    }

    public void setBtn_sure(String btn_sure) {
        this.tvSure.setText(btn_sure);
    }

    public void setTv_content(String tv_content) {
        this.tv_content.setText(tv_content);
    }
}
