package com.yxkj.deliveryman.view.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.callback.CommenDialogSureListener;

/**
 * 通用Dialog，有yes和no两种选项
 */

public class CommonYesOrNoDialog extends DialogFragment {
    /*主题*/
    private TextView tv_title;
    /*内容*/
    private TextView tv_content;
    /*取消按钮*/
    private Button btn_cancle;
    /*确认按钮*/
    private Button btn_sure;
    /*确认按钮监听*/
    private CommenDialogSureListener dialogSureListener;

    public void setDialogSureListener(CommenDialogSureListener dialogSureListener) {
        this.dialogSureListener = dialogSureListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.view_commen_dialog, container);
        tv_title = view.findViewById(R.id.tv_title);
        tv_content = view.findViewById(R.id.tv_content);
        btn_cancle = view.findViewById(R.id.btn_cancle);
        btn_sure = view.findViewById(R.id.btn_sure);
        btn_cancle.setOnClickListener(view1 -> dismiss());
        btn_sure.setOnClickListener(view1 -> {
            dismiss();
            if (dialogSureListener != null) {
                dialogSureListener.onSure();
            }
        });
        return view;
    }

    public void setTv_title(String tv_title) {
        this.tv_title.setText(tv_title);
    }

    public void setBtn_cancle(String btn_cancle) {
        this.btn_cancle.setText(btn_cancle);
    }

    public void setBtn_sure(String btn_sure) {
        this.btn_sure.setText(btn_sure);
    }

    public void setTv_content(String tv_content) {
        this.tv_content.setText(tv_content);
    }
}
