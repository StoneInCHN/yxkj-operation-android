package com.yxkj.deliveryman.view;

import android.content.Context;
import android.support.v4.view.LayoutInflaterCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.yxkj.deliveryman.R;


/*
 *  @项目名：  yxkj-operation-android 
 *  @包名：    com.yxkj.deliveryman.view
 *  @文件名:   RichToolBar
 *  @创建者:   hhe
 *  @创建时间:  2017/10/16 11:17
 *  @描述：    统一的toolbar
 */
public class RichToolBar extends View {
    private Context mContext;

    public RichToolBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }


    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.layout_toolbar, null);

    }


}
