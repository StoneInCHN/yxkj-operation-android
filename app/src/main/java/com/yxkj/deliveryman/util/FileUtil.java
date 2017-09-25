package com.yxkj.deliveryman.util;

import java.io.File;
import java.io.IOException;

/**
 * Created by Snow on 2016/11/19.
 */

public class FileUtil {
    /**
     * 创建文件
     * @param filePath
     *      文件路径
     * @return
     *      true，创建成功，false，创建失败
     */
    public static boolean createFile(String filePath){
        File file = new File(filePath);
        boolean value = false;
        // 目录不存在，创建目录
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        if(!file.exists()){
            try {
                value = file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return value;
    }
}
