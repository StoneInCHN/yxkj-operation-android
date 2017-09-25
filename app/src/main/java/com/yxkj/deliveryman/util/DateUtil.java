package com.yxkj.deliveryman.util;

import android.util.SparseArray;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 日期辅助类
 */
public class DateUtil {

    // 这里可以换一种思路，全部用北京时间
    // 开始时间为1970.1.1，记录它的毫秒数；
    // 再计算指定时间与开始时间的毫秒差。这样算得的时间差就与时区无关了
    // 当前时区时间差
    final static int mZoneOffset = Calendar.getInstance().get(Calendar.ZONE_OFFSET);

    // 取得绝对天数
    public static int getDayCount(Calendar calendar) {

        return getDayCount(calendar.getTimeInMillis());
    }

    // 取得绝对天数
    public static int getDayCount(Date date) {

        return getDayCount(date.getTime());
    }

    // 取得绝对天数，采用默认格式
    public static int getDayCount(String date) {

        Integer dayCount = mDateCountCache.get(date);
        if (dayCount == null) {

            dayCount = getDayCount(getDate(date));
            mDateCountCache.put(date, dayCount);
        }
        return dayCount;
    }

    // 取得时间字符串，date1的天数是否在date2之前
    public static boolean isBeforeDay(Date date1, Date date2) {

        return getDayCount(date1) < getDayCount(date2);
    }

    // 取得时间字符串，date1的天数是否在date2之前
    public static boolean isBeforeDay(Date date1, Calendar date2) {

        return getDayCount(date1) < getDayCount(date2);
    }

    // 取得时间字符串，date1的天数是否在date2之前
    public static boolean isBeforeDay(Date date1, String date2) {

        return getDayCount(date1) < getDayCount(date2);
    }

    // 取得时间字符串，date1的天数是否在date2之前
    public static boolean isBeforeDay(Calendar date1, Date date2) {

        return getDayCount(date1) < getDayCount(date2);
    }

    // 取得时间字符串，date1的天数是否在date2之前
    public static boolean isBeforeDay(Calendar date1, Calendar date2) {

        return getDayCount(date1) < getDayCount(date2);
    }

    // 取得时间字符串，date1的天数是否在date2之前
    public static boolean isBeforeDay(Calendar date1, String date2) {

        return getDayCount(date1) < getDayCount(date2);
    }

    // 取得时间字符串，date1的天数是否在date2之前
    public static boolean isBeforeDay(String date1, Date date2) {

        return getDayCount(date1) < getDayCount(date2);
    }

    // 取得时间字符串，date1的天数是否在date2之前
    public static boolean isBeforeDay(String date1, Calendar date2) {

        return getDayCount(date1) < getDayCount(date2);
    }

    // 取得时间字符串，date1的天数是否在date2之前
    public static boolean isBeforeDay(String date1, String date2) {

        return getDayCount(date1) < getDayCount(date2);
    }

    // 取得时间字符串，默认格式
    public static String getDateStr(Date date) {

        return mDefaultDateFormat.format(date);
    }

    // 取得时间字符串，默认格式
    public static String getDateStr(Calendar calendar) {

        return mDefaultDateFormat.format(calendar.getTime());
    }

    // 取得时间字符串，默认格式
    public static String getDateStr(int dayCount) {

        // 缓存
        String result = mCountDateCache.get(dayCount);

        if (result == null) {

            result = getDateStr(getDateByCount(dayCount));
        }
        return result;
    }


    /////////////////////////////////////
    // 日期格式化
    private static final Map<String, SimpleDateFormat> mDateFormatCaches = new HashMap<>();

    // 取得日期字符串，指定格式
    public static String getDateStr(Date date, String format) {

        return getDateFormater(format).format(date);
    }

    // 取得日期字符串，指定格式
    public static String getDateStr(Calendar calendar, String format) {

        return getDateFormater(format).format(calendar.getTime());
    }

    // 取得日期字符串，指定格式
    public static String getDateStr(int dayCount, String format) {

        return getDateStr(getDateByCount(dayCount), format);
    }

    // 取得缓存的格式化对象
    private static SimpleDateFormat getDateFormater(String format) {

        SimpleDateFormat result = mDateFormatCaches.get(format);

        if (result == null) {

            result = new SimpleDateFormat(format);
            mDateFormatCaches.put(format, result);
        }
        return result;
    }

    /////////////////////////////////////

    // 绝对天数到日期
    public static Date getDateByCount(int dayCount) {

        // TODO 待验证
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTimeInMillis(24 * 60 * 60 * 1000 * dayCount + mZoneOffset);

        return calendar.getTime();
    }

    // 取得到1970年的天数，按当前时间时间差计算
    private static int getDayCount(long mills) {

        long dayMills = 24 * 60 * 60 * 1000 - mZoneOffset;
        return (int) (mills / dayMills);
    }

    // 日期格式化
    private static final SimpleDateFormat mDefaultDateFormat = new SimpleDateFormat();

    // 日期天数缓存
    private static final Map<String, Integer> mDateCountCache = new HashMap<String, Integer>();

    // 天数到日期缓存
    private static final SparseArray<String> mCountDateCache = new SparseArray<String>();

    // 静态初始化代码块
    static {
        mDefaultDateFormat.applyPattern("yyyy-MM-dd");
    }

    // 解析日期
    public static Date getDate(String dateStr) {

        try {

            return mDefaultDateFormat.parse(dateStr);

        } catch (Exception e) {

            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取与今天，天数绝对差
     *
     * @param target
     * @return
     */
    public static int getAbsDateTodayCount(String target) {
        return getAbsCountBetween(target, StringUtil.getCurrDate());
    }

    /**
     * 获取与今天，天数差值
     *
     * @param target
     * @return <0，比今天小，>0比今天大
     */
    public static int getDateTodayCount(String target) {
        return getCountBetween(target, StringUtil.getCurrDate());
    }

    /**
     * 获取两个日期间的天数绝对差值
     *
     * @param target
     * @param source
     * @return
     */
    public static int getAbsCountBetween(String target, String source) {
        return Math.abs(getCountBetween(target, source));
    }

    /**
     * 获取两个日期间的天数差
     *
     * @param target
     * @param source
     * @return 0，同一天，<0，target比source小，>0,target比source大
     */
    public static int getCountBetween(String target, String source) {
        int count = 0;
        Date targetDate = getDate(target);
        Date todayDate = getDate(source);
        if (targetDate == null || todayDate == null) {
            return count;
        }
        long targetMill = targetDate.getTime();
        long todayMill = todayDate.getTime();
        long dif = targetMill - todayMill;
        long hour = dif / (1000 * 60 * 60);
        count = (int) (hour / 24);
        if (hour % 24 > 0) {
            count += 1;
        }
        return count;
    }

    /**
     * 获取指定日期与当前日期月分差值
     *
     * @param target
     * @return
     */
    public static int getDateMonthCount(String target) {
        int result = 0;
        Date targetDate = getDate(target);
        Date todayDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(targetDate);
        int targetYear = cal.get(Calendar.YEAR);
        int targetMonth = cal.get(Calendar.MONTH);
        int targetDay = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(todayDate);
        int todayYear = cal.get(Calendar.YEAR);
        int todayMonth = cal.get(Calendar.MONTH);
        int todayDay = cal.get(Calendar.DAY_OF_MONTH);
        //同年
        result = (targetYear - todayYear) * 12 + targetMonth - todayMonth;
        if (targetDay < todayDay) {
            result -= 1;
        }
//        if (result == 0 && targetDate.getTime() > todayDate.getTime()) {
//            result += 1;
//        }
        return result <= 0 ? 0 : result;
    }

    public static int getDateYearCount(String target) {
        int result = 0;
        Date targetDate = getDate(target);
        Date todayDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(targetDate);
        int targetYear = cal.get(Calendar.YEAR);
        int targetMonth = cal.get(Calendar.MONTH);
        int targetDay = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(todayDate);
        int todayYear = cal.get(Calendar.YEAR);
        int todayMonth = cal.get(Calendar.MONTH);
        int todayDay = cal.get(Calendar.DAY_OF_MONTH);
        result = (targetYear - todayYear) * 12 + targetMonth - todayMonth;
        if (targetMonth == todayMonth) {
            //同用，判断天
            if (targetDay < todayDay) {
                result--;
            }
        }
        if (result <= 0) {
            return result;
        }
        int year = result / 12;
        return year;
    }

    /**
     * 获取日期差值：<br >
     * >1年，则返回x年<br >
     * <1年 && >1月，则返回x月<br >
     * <1月 && >1天，则返回x天<br >
     * <0天，则返回0天
     *
     * @param target
     * @return
     */
    public static String getDateCountString(String target) {
        int resultYear = getDateYearCount(target);
        if (resultYear > 0) {
            return resultYear + "年";
        }
        int resultMonth = getDateMonthCount(target);
        if (resultMonth > 0) {
            return resultMonth + "月";
        }
        int resultDay = getDateTodayCount(target);
        if (resultDay > 0) {
            return resultDay + "天";
        }
        return "0天";
    }

    /**
     * 获取时间描述
     *
     * @param time 时长：秒
     * @return >1天，返回x天；<1天 >1小时，返回x小时；<1小时 >1分，返回x分；<1分返回x秒
     */
    public static String getTimeDesc(long time) {
        if (time <= 0) {
            return "";
        }
        long sec = 0;//秒
        long minute = 0;//分
        long hour = 0;//时
        long day = 0;//天
        sec = time % 60;//不足1分钟的秒数
        minute = time = time / 60;//转换成分钟数
        if (minute > 0) {
            minute = time % 60;//不足1小时的分钟数
            hour = time = time / 60;//转换成小时数
            if (hour > 0) {
                hour = time % 24;//不足1天的小时数
                day = time / 24;//转换成天数;
            }
        }
        StringBuilder sBuilder = new StringBuilder();
        if (day > 0) {
            sBuilder.append(day).append("天");
        } else if (hour > 0) {
            sBuilder.append(hour).append("小时");
        } else if (minute > 0) {
            sBuilder.append(minute).append("分");
        } else if (sec > 0) {
            sBuilder.append(sec).append("秒");
        }
        return sBuilder.toString();
    }

    /**
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s, String format) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

}
