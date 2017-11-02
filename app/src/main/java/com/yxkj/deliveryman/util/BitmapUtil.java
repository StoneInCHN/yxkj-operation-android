package com.yxkj.deliveryman.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.DrawableRes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Snow on 2017/5/5.
 */

public class BitmapUtil {

    /**
     * @param filePath 文件路径
     * @return 如果文件加载成图片失败返回null，int[0]_width, int[1]_height
     */
    public static int[] getBitmapSize(String filePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        int[] result = getSizeArray(options.outWidth, options.outHeight);
        LogUtil.i("BitmapSize:" + "width：" + result[0] + "\theight：" + result[1]);
        return result;
    }

    /**
     * @return 如果文件加载成图片失败返回null，int[0]_width, int[1]_height
     */
    public static int[] getBitmapSize(Context context, @DrawableRes int resId) {
        if (context == null) {
            return getSizeArray(0, 0);
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), resId, options);
        int[] result = getSizeArray(options.outWidth, options.outHeight);
        LogUtil.i("BitmapSize:" + "width：" + result[0] + "\theight：" + result[1]);
        return result;
    }

    private static int[] getSizeArray(int... value) {
        return value;
    }

    /**
     * 保存方法
     */
    public static File saveBitmap(String path, String picName, Bitmap bitmap) {
        File file = new File(path, picName);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

}
