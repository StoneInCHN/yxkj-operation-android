package com.yxkj.deliveryman.util;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.yxkj.deliveryman.R;
import com.yxkj.deliveryman.application.MyApplication;

import java.io.ByteArrayOutputStream;

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

    public static void loadImageWithNoCache(ImageView imageView, String url) {
        Glide.with(MyApplication.getAppContext())
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(R.mipmap.ic_launcher)
                .thumbnail(0.1f)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(imageView);

    }


    public static void loadImageWithBitmap(ImageView imageView, Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();

        Glide.with(MyApplication.getAppContext())
                .load(bytes)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.mipmap.ic_launcher)
                .thumbnail(0.1f)
                .into(imageView);

    }

}
