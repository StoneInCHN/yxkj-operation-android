package com.yxkj.deliveryman.util;

import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * 字符串工具
 */
public class StringUtil {

    /**
     * 取得随机字符串，只有英文和数字
     */
    public static String getRandomString(int length) {

        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder builder = new StringBuilder();

        for (int i = 0, charSize = str.length(); i < length; ++i) {

            int number = random.nextInt(charSize); // [0,62)
            builder.append(str.charAt(number));
        }
        return builder.toString();
    }

    /**
     * 验证手机号码是否正确
     */
    public static boolean verifyMobile(String mobile) {

        // 有效字符串
        String vaildChars = "0123456789";
        String vaildFirstChars = "1";
        String vaildSecondChars = "345678";
        String vaildThirdChars = "023456789";
        // 有效位数
        int nLength = 11;

        if (mobile != null) {

            mobile = mobile.trim();
            int size = mobile.length();

            if (size != nLength) {
                return false;
            }

            // 查看是否有无效字符
            for (int i = 0; i < size; i++) {

                String c = String.valueOf(mobile.charAt(i));
                // 若包含无效字符，则格式不正确
                if (!vaildChars.contains(c)) {
                    return false;
                }
            }
            String first = String.valueOf(mobile.charAt(0));
            if (!vaildFirstChars.contains(first)) {
                return false;
            }
            String second = String.valueOf(mobile.charAt(1));
            if (!vaildSecondChars.contains(second)) {
                return false;
            }
            String third = String.valueOf(mobile.charAt(2));
            if (!vaildThirdChars.contains(third)) {
                return false;
            }

            return true;
        }
        return false;
    }

    /**
     * 验证邮箱格式是否正确
     */
    public static boolean verifyEmail(String email) {

        if (email != null) {

            email = email.trim();

            // 邮箱格式
            String emailFormat = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";

            return email.matches(emailFormat);
        }

        return false;
    }

    /**
     * @param string
     * @return
     */
    public static boolean isNotEmpty(String string) {
        if (null != string && !"".equals(string.trim())) {
            return true;
        }
        return false;
    }

    /**
     * 验证密码格式和位数是否正确
     */
    public static boolean verifyPassword(String pwd) {

        // 有效字符串
        String vaildChars = "!@#$%-_^0123456789abcdefghijklmnopqrstuvwxyzABCD.EFGHIJKLIMNOPQRSTUVWXYZ";
        // 有效位数
        int minSize = 6;
        int maxSize = 20;

        if (pwd != null) {

            pwd = pwd.trim();
            int size = pwd.length();

            // 若位数超出了限制，则格式不正确
            if (size < minSize || size > maxSize) {

                return false;
            }

            // 查看是否有无效字符
            for (int i = 0; i < size; i++) {

                String c = String.valueOf(pwd.charAt(i));

                // 若包含无效字符，则格式不正确
                if (!vaildChars.contains(c)) {

                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 比较两个字符串，若有一个字符串为null，则排在后面
     */
    public static int compare(String str1, String str2) {

        if (str1 != null && str2 != null) {

            return str1.compareTo(str2);

        } else if (str1 == null && str2 != null) {

            return -1;

        } else if (str1 != null && str2 == null) {

            return 1;
        }

        return 0;
    }

    /**
     * 检测一系列字符串是否全为空，null也算作空
     */
    public static boolean isAllNotEmpty(String... strs) {

        boolean isAllNotEmpty = true;

        for (String string : strs) {

            if (string == null || string.isEmpty()) {

                isAllNotEmpty = false;
                break;
            }
        }

        return isAllNotEmpty;
    }

    /**
     * 判断两字符串是否相等，若均为null则也为相等
     */
    public static boolean equal(String str1, String str2) {

        if (str1 != null && str2 != null) {

            return str1.equals(str2);
        }

        return str1 == str2;
    }

    /**
     * 当前日期字符串
     *
     * @return 年-月-日
     */
    public static String getCurrDate() {
        int[] dateArr = getCurrDateArray();
        String year = dateArr[0] + "";
        String month = String.format("%02d", dateArr[1]);
        String day = String.format("%02d", dateArr[2]);
        return year + "-" + month + "-" + day;
    }

    /**
     * 当前日期数值
     *
     * @return date[0]年；date[1]月；date[2]日；date[3]时；date[4]分
     */
    public static int[] getCurrDateArray() {
        int[] dateArr = {0, 0, 0, 0, 0};
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        dateArr[0] = year;
        dateArr[1] = month;
        dateArr[2] = day;
        dateArr[3] = hour;
        dateArr[4] = minute;
        return dateArr;
    }

    /**
     * 删除指定位置字符
     *
     * @param position 指定位置
     * @param maxCount 最大字数
     * @return newStr 去掉指定位置字符的字符串
     */
    public static String removeChar(String str, int position, int maxCount) {
        // String newStr;
        // 删除字符的长度
        int delLenth = str.length() - maxCount;
        String temp1 = str.substring(0, position - delLenth);
        String temp2 = str.substring(position, str.length());

        return temp1 + temp2;
    }

    /**
     * 保留最后指定位数字符，其余用其他字符替换
     *
     * @param str     目标字符串
     * @param replace 替换成的字符
     * @param length  保留的数据
     * @return
     */
    public static String keepLastWords(String str, char replace, int length) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        if (str.length() <= length) {
            return str;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0, count = str.length() - length; i < count; i++) {
            stringBuilder.append(replace);
        }
        str = str.substring(str.length() - length);
        stringBuilder.append(str);
        return stringBuilder.toString();
    }

    /**
     * 保留前指定位数字符，其余用其他字符代替，可以指定，替换字符保留位数
     *
     * @param str
     * @param replace
     * @param firstLength
     * @param lastKeepLengh -1表示，不指定，即其余
     * @return
     */
    public static String keepFirstWords(String str, char replace, int firstLength, int lastKeepLengh) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        if (str.length() <= firstLength) {
            return str;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str.substring(0, firstLength));
        int count = 0;
        if (lastKeepLengh == -1) {
            count = str.length() - firstLength;
        } else {
            count = lastKeepLengh;
        }
        for (int i = 0; i < count; i++) {
            stringBuilder.append(replace);
        }
        return stringBuilder.toString();
    }

    public static String keepLast3(String str) {
        return StringUtil.keepLastWords(str, '*', 3);
    }

    public static String keepLast4(String str) {
        return StringUtil.keepLastWords(str, '*', 4);
    }

    public static String keepFirst5(String str) {
        return StringUtil.keepFirstWords(str, '*', 5, 3);
    }

    /**
     * 获得当前日期字符串 年-月-日
     */
    public static String getNowDateStr() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date nowDate = new Date();
        return sdf.format(nowDate);
    }

    /**
     * 裁剪字符串
     *
     * @param str
     * @param maxLength
     * @return
     */
    public static String cropString(String str, int maxLength) {
        StringBuilder sBuilder = new StringBuilder();
        if (str.length() > maxLength) {
            sBuilder.append(str.substring(0, maxLength));
        } else {
            sBuilder.append(str);
        }
        return sBuilder.toString();
    }

    /**
     * 获取id字符串，若小于0则为空
     */
    public static String getIdStr(long id) {
        return id < 0 ? "" : id + "";
    }

    /**
     * 保留小数点后2位
     *
     * @param s
     * @return
     */
    public static String keep2Decimal(CharSequence s) {
        //是否包含小数点
        String value = s.toString();
        if (!value.contains(".")) {
            //不包含
            return value;
        }
        // 保留两位小数
        int index = value.indexOf(".");//获取小数点的索引
        if (index + 3 > s.length() - 1) {
            //只有两位小数
            return value;
        }
        value = value.substring(0, index + 3);
        return value;
    }

    public static String getNoNullString(String text) {
        return text == null ? "" : text;
    }

    /**
     * 获得带下划线的字符串
     */
    public static Spanned getLinkedString(String str) {
        return Html.fromHtml("<u>" + str + "</u>");
    }

    /**
     * 判断是否是同一个数据
     *
     * @param s
     * @return
     */
    public static boolean isSame(String s) {
        boolean result = true;
        char first = s.charAt(0);
        for (int i = 1; i < s.length(); i++) {
            char child = s.charAt(i);
            if (child != first) {
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * 判断字符串是否是升序连续
     *
     * @param s
     * @return
     */
    public static boolean isSeriesAsc(String s) {
        boolean result = true;
        char first = s.charAt(0);
        for (int i = 1; i < s.length(); i++) {
            char child = s.charAt(i);
            if (child != first + i) {
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * 判断字符串是否是降序连续
     *
     * @param s
     * @return
     */
    public static boolean isSeriesDesc(String s) {
        boolean result = true;
        char first = s.charAt(0);
        for (int i = 1; i < s.length(); i++) {
            char child = s.charAt(i);
            if (child != first - i) {
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * 判断字符串是否同时包含数字和字母
     */
    public static boolean isBothContainLetterAndDigit(String s) {
        boolean isContainLetter = false;
        boolean isContainDigit = false;
        char[] arr = s.toCharArray();

        for (char c : arr) {
            //判断大小写字母
            if ((c > 64 && c < 91) || (c > 96 && c < 123)) {
                isContainLetter = true;
            }

            //判断数字
            if (c > 47 && c < 58) {
                isContainDigit = true;
            }
        }

        return isContainDigit && isContainLetter;
    }
}
