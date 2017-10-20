package com.yxkj.deliveryman.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.yxkj.deliveryman.R;


/*
 *  @项目名：  yxkj-operation-android 
 *  @包名：    com.yxkj.deliveryman.view
 *  @文件名:   VolumeBarView
 *  @创建者:   hhe
 *  @创建时间:  2017/10/20 10:37
 *  @描述：    音量调节条,将音量分成0~10级，坐标也是跟着这个来计算的
 */
public class VolumeBar extends View {

    private Context mContext;
    private int mVolumeProgress = 9;
    private int mWidth;
    private int mHeight;
    /**
     * 滑块半径
     */
    private float mPickRadius;

    public VolumeBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.VolumeBar);
        int defaultVolume = typedArray.getInt(R.styleable.VolumeBar_default_volume, 0);
        typedArray.recycle();

        if (defaultVolume > 10) {
            defaultVolume = 10;
        }
        if (defaultVolume < 0) {
            defaultVolume = 0;
        }

        mVolumeProgress = defaultVolume;

        initPaint();

    }

    /**
     * 滑动块
     */
    private Paint mPickPaint;
    /**
     * 进度条
     */
    private Paint mBarPaint;

    private void initPaint() {
        mPickPaint = new Paint();
        mPickPaint.setColor(getResources().getColor(R.color.gray_stroke));
        mPickPaint.setStyle(Paint.Style.STROKE);
        mBarPaint = new Paint();
        mBarPaint.setStrokeWidth(3);
    }
/*
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


    }*/


    /**
     * 更新进度
     *
     * @param progress
     */
    public void updateProgress(@IntRange(from = 0, to = 10) int progress) {
        mVolumeProgress = progress;
        if (mVolumeProgress > 10) {
            mVolumeProgress = 10;
        }
        if (mVolumeProgress < 0) {
            mVolumeProgress = 0;
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getMeasuredHeight();
        //考虑padding的位置
        int paddingLeft = getPaddingLeft();
        //画圆
        mPickRadius = (getMeasuredWidth() - getPaddingLeft() - getPaddingRight()) / 11 / 2;
        float cicleRadius = mPickRadius;
        //圆不能太小
        if (mPickRadius < 10) {
            cicleRadius = 10;
        }
        canvas.drawCircle((2 * mVolumeProgress + 1) * mPickRadius + paddingLeft, height / 2, cicleRadius, mPickPaint);
        //画左边的绿线
        if (mVolumeProgress != 0) {
            mBarPaint.setColor(getResources().getColor(R.color.green_text));
            canvas.drawLine(mPickRadius + paddingLeft, height / 2, mVolumeProgress * 2 * mPickRadius + paddingLeft, height / 2, mBarPaint);
        }
        //画右边的灰线
        if (mVolumeProgress != 10) {
            mBarPaint.setColor(getResources().getColor(R.color.gray1_text));
            canvas.drawLine(2 * (mVolumeProgress + 1) * mPickRadius + paddingLeft, height / 2, (11 * 2 - 1) * mPickRadius + paddingLeft, height / 2, mBarPaint);
        }
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float xTouch = event.getX();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:

                updateProgress(getTouchVolume(xTouch));
                break;
            case MotionEvent.ACTION_UP:

                break;

        }

        return true;


    }

    private int getTouchVolume(float xTouch) {
        return (int) ((xTouch + 50) / mPickRadius) / 2;

    }
}
