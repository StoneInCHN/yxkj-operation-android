package com.yxkj.deliveryman.util;

import android.widget.EditText;

/**
 * @项目名： yxkj-operation-android
 * @包名： com.yxkj.deliveryman.util
 * @文件名: EditTextUtil
 * @创建者: hhe
 * @创建时间: 2017/11/6 14:07
 * @描述： edittext的相关工具类
 */
public class EditTextUtil {

    /**
     * 将edittext光标放置最后
     *
     * @param editTexts target
     */
    public static void setEditTextSelectionEnd(EditText... editTexts) {
        for (EditText editText : editTexts) {
            String content = editText.getText().toString().trim();
            editText.setSelection(content.length());
        }

    }

}
