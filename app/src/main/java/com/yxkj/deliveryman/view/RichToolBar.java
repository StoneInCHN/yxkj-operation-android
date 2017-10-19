package com.yxkj.deliveryman.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yxkj.deliveryman.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
 *  @项目名：  yxkj-operation-android 
 *  @包名：    com.yxkj.deliveryman.view
 *  @文件名:   RichToolBar
 *  @创建者:   hhe
 *  @创建时间:  2017/10/16 11:17
 *  @描述：    统一的toolbar
 */
public class RichToolBar extends RelativeLayout {
    private Context mContext;
    @BindView(R.id.iv_back_toolbar)
    ImageView mIvBack;
    @BindView(R.id.tv_title_toolbar)
    TextView mTvTitle;
    @BindView(R.id.tv_right_toolbar)
    TextView mTvRight;
    @BindView(R.id.iv_right_toolbar)
    ImageView mIvRight;
    @BindView(R.id.view_line_divider)
    View mLineDivider;

    public RichToolBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        initView(attrs);

    }


    private void initView(AttributeSet attrs) {
        LayoutInflater.from(mContext).inflate(R.layout.layout_toolbar, this);
        ButterKnife.bind(this);

        TypedArray ta = mContext.obtainStyledAttributes(attrs, R.styleable.RichToolBar);
        String title = ta.getString(R.styleable.RichToolBar_title_text);
        String rightText = ta.getString(R.styleable.RichToolBar_right_text);
        Drawable leftImgSrc = ta.getDrawable(R.styleable.RichToolBar_left_img_src);
        Drawable rightImgSrc = ta.getDrawable(R.styleable.RichToolBar_right_img_src);
        boolean isLeftBackVisible = ta.getBoolean(R.styleable.RichToolBar_left_back_visible, false);
        boolean isDividerVisible = ta.getBoolean(R.styleable.RichToolBar_is_divider_visible, true);
        ta.recycle();

        mIvBack.setVisibility(isLeftBackVisible ? VISIBLE : INVISIBLE);
        mLineDivider.setVisibility(isDividerVisible ? VISIBLE : INVISIBLE);

        setTitle(title);
        setRightText(rightText);
        setLeftImgSrc(leftImgSrc);
        setRightImgSrc(rightImgSrc);
    }


    public void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            mTvTitle.setVisibility(VISIBLE);
            mTvTitle.setText(title);
        }
    }

    public void setLeftImgSrc(Drawable drawable) {
        if (drawable != null) {
            mIvBack.setVisibility(VISIBLE);
            mIvBack.setImageDrawable(drawable);
        }
    }

    public void setRightImgSrc(Drawable drawable) {
        if (drawable != null) {
            mIvRight.setVisibility(VISIBLE);
            mIvRight.setImageDrawable(drawable);
        }
    }


    public void setRightText(String text) {
        if (!TextUtils.isEmpty(text)) {
            mTvRight.setVisibility(VISIBLE);
            mTvRight.setText(text);
        }
    }


    @OnClick({R.id.iv_back_toolbar, R.id.fl_right_toolbar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back_toolbar:
                onClickBack();
                break;

            //点击右边，text or image
            case R.id.fl_right_toolbar:
                if (mOnViewClickListener != null) {
                    mOnViewClickListener.onClickRight();
                }
                break;

        }
    }

    protected void onClickBack() {
        if (mContext instanceof Activity) {
            ((Activity) mContext).finish();
        }
    }


    public interface OnViewClickListener {
        void onClickRight();
    }

    private OnViewClickListener mOnViewClickListener;

    public void setOnViewClickListener(OnViewClickListener onViewClickListener) {
        mOnViewClickListener = onViewClickListener;
    }
}
