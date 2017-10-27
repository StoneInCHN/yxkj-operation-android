package com.yxkj.deliveryman.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.base.BaseActivity;
import com.yxkj.deliveryman.util.ImageLoadUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShowBigImageActivity extends BaseActivity {

    @BindView(R.id.iv_image_show_big_image)
    ImageView mIvImageShowBigImage;

    @Override
    public int getContentViewId() {
        return R.layout.activity_show_big_image;
    }

    private String picPath;

    @Override
    public void initView() {
        Bundle bundle = getIntent().getExtras();
        picPath = bundle.getString("pic_path");
    }

    @Override
    public void initData() {
        ImageLoadUtil.loadImage(mIvImageShowBigImage, picPath);
    }

    @Override
    public void setEvent() {

    }

    @Override
    public void onClick(View v) {

    }

    @OnClick({R.id.tv_restart_take_photo_show_big_image, R.id.tv_use_image_show_big_image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_restart_take_photo_show_big_image:
                break;
            case R.id.tv_use_image_show_big_image:
                break;
        }
    }

}
