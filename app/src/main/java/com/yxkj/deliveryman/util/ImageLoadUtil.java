package com.yxkj.deliveryman.util;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.application.MyApplication;

/*
 *  @项目名：  yxkj-operation-android 
 *  @包名：    com.yxkj.deliveryman.util
 *  @文件名:   ImageLoadUtil
 *  @创建者:   hhe
 *  @创建时间:  2017/10/25 16:26
 *  @描述：    图片加载封装
 */
public class ImageLoadUtil {
    public static void loadImage(ImageView imageView, String url) {
        Glide.with(MyApplication.getAppContext())
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.mipmap.ic_launcher)
                .thumbnail(0.1f)
                .into(imageView);

    }

}