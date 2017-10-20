package com.yxkj.deliveryman.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;


/*
 *  @项目名：  yxkj-operation-android 
 *  @包名：    com.yxkj.deliveryman.view
 *  @文件名:   VolumeBarView
 *  @创建者:   hhe
 *  @创建时间:  2017/10/20 10:37
 *  @描述：    音量调节条
 *  todo 待完成
 */
public class VolumeBar extends View {

    private Context mContext;
    public VolumeBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext=context;


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int mode=MeasureSpec.getMode(widthMeasureSpec);
        switch (mode){
            case MeasureSpec.AT_MOST:

                break;
            case MeasureSpec.EXACTLY:

                break;
            case MeasureSpec.UNSPECIFIED:

                break;

        }

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


    }
}
