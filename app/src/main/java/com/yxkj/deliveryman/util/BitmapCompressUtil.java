package com.yxkj.deliveryman.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 图片压缩
 * Created by Snow on 2017/5/3.
 */

public class BitmapCompressUtil {

    private static final String TAG = BitmapCompressUtil.class.getSimpleName();

    public static final int MAX_LENGTH = 1024;//最大边长度

    public static final int QUALITY = 80;//图片保存质量

    /**
     * 压缩图片
     *
     * @param filePath 源图片路径
     * @param outfile  目标图片路径
     * @return
     */
    public static void compress(String filePath, String outfile) {
        //获取图片大小
        LogUtil.i("compress bitmap start！");
        int[] sizes = BitmapUtil.getBitmapSize(filePath);
        //获取压缩比例
        int sampleSize = getSampleSize(sizes);
        //不压缩
        if (sampleSize == 1) {
            LogUtil.i("don't compress bitmap");
//            FileHelper.copyFile(filePath, outfile);
            return;
        }
        // 2017/5/3 压缩图片
        bitmapToFile(compressBitmap(filePath, sampleSize), outfile);
        LogUtil.i("compress bitmap finish！");
    }

    /**
     * 获取图片压缩比例
     *
     * @param sizes
     * @return
     */
    public static int getSampleSize(int[] sizes) {
        int maxLength = Math.max(sizes[0], sizes[1]);//获取最大边长度
        if (maxLength <= MAX_LENGTH) {
            //当长宽都小于最大边长度时，不压缩
            return 1;
        }
        int sampleSize = Math.round((float) maxLength / MAX_LENGTH);
        LogUtil.i("sampleSize：" + sampleSize);
        return sampleSize;
    }

    /**
     * 压缩成bitmap
     *
     * @param filePath
     * @param sampleSize
     * @return
     */
    public static Bitmap compressBitmap(String filePath, int sampleSize) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inSampleSize = sampleSize;
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 将bitmap保存为File
     *
     * @param bitmap
     * @param outFile
     */
    public static void bitmapToFile(Bitmap bitmap, String outFile) {
        File file = new File(outFile);
        BufferedOutputStream bos = null;
        try {
            FileUtil.createFile(outFile);
            bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, QUALITY, bos);
            bos.flush();
            LogUtil.i("compress bitmap to file success!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                    bos = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
