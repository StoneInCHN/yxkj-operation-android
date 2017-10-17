package com.yxkj.deliveryman.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.yxkj.deliveryman.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/*
 *  @项目名：  yxkj-operation-android 
 *  @包名：    com.yxkj.deliveryman.view.dialog
 *  @文件名:   TextButtonDialog
 *  @创建者:   hhe
 *  @创建时间:  2017/10/17 10:53
 *  @描述：    带一个确认按钮的dialog
 */
public class TextButtonDialog extends Dialog {

    @BindView(R.id.tv_title_dialog_text_button)
    TextView mTvTitle;
    @BindView(R.id.tv_content_dialog_text_button)
    TextView mTvContent;
    @BindView(R.id.tv_confirm_dialog_text_button)
    TextView mTvConfirm;
    private Context mContext;

    public TextButtonDialog(@NonNull Context context, String title, String content, String confirmText) {
        super(context);
        mContext = context;
        setContentView(R.layout.dialog_text_button);
        ButterKnife.bind(this);
        initData(title, content, confirmText);
    }

    private void initData(String title, String content, String confirmText) {
        mTvTitle.setText(title);
        mTvContent.setText(content);
        mTvConfirm.setText(confirmText);
    }


    /**
     * 点击确认按钮
     */
    @OnClick(R.id.tv_confirm_dialog_text_button)
    public void onClickConfirm() {
        dismiss();
    }

}
