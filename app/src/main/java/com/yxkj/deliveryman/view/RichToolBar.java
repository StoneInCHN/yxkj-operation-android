package com.yxkj.deliveryman.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
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
public class RichToolBar extends View {
    private Context mContext;
    @BindView(R.id.iv_back_toolbar)
    ImageView mIvBack;
    @BindView(R.id.tv_title_toolbar)
    TextView mTvTitle;
    @BindView(R.id.tv_right_toolbar)
    TextView mTvRight;

    public RichToolBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        initView();
    }


    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.layout_toolbar, null);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.iv_back_toolbar, R.id.tv_right_toolbar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back_toolbar:
                onClickBack();
                break;

            case R.id.tv_right_toolbar:
                onClickRightText();
                break;
        }
    }

    protected void onClickBack() {
        if (mContext instanceof Activity) {
            ((Activity) mContext).finish();
        }
    }

    protected void onClickRightText() {

    }
}
