package com.yxkj.deliveryman.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;

import com.yxkj.deliveryman.R;


/*
 *  @项目名：  yxkj-operation-android 
 *  @包名：    com.yxkj.deliveryman.view.dialog
 *  @文件名:   TextProgressbarDialog
 *  @创建者:   hhe
 *  @创建时间:  2017/10/20 11:06
 *  @描述：    带文字和progressbar的dialog
 */
public class TextProgressbarDialog extends Dialog {
    private Context mContext;
    public TextProgressbarDialog(@NonNull Context context) {
        super(context);
        mContext = context;
        initView();
    }

    private void initView() {
        setContentView(R.layout.dialog_text_progressbar);
        setCanceledOnTouchOutside(false);
    }

}
