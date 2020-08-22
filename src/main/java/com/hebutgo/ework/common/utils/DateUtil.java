package com.hebutgo.ework.common.utils;

import com.hebutgo.ework.common.exception.BizException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static SimpleDateFormat dayDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static SimpleDateFormat monthDateFormat = new SimpleDateFormat("yyyy-MM");

    public static SimpleDateFormat yearDateFormat = new SimpleDateFormat("yyyy");

    public static SimpleDateFormat secondDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static Calendar calendar = Calendar.getInstance();
    /**
     * yyyy-MM-dd HH:mm:ss
     * 时间格式转date
     * @param time
     * @return
     * @throws Exception
     */
    public static Date toDate(String time) throws Exception {
        Date date = new Date();
        try {
            date = secondDateFormat.parse(time);
        } catch (ParseException e) {
            throw new Exception("时间格式不对");
        }
        return date;
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     * date转化为string
     * @param date
     * @return
     * @throws Exception
     */
    public static String dateToString(Date date) throws Exception {
        String dataString;
        try {
            dataString = secondDateFormat.format(date);
        } catch (Exception e) {
            throw new Exception("时间转化异常！");
        }
        return dataString;
    }

    /**
     * 由当前时间戳获取某日的时间戳
     * @param time
     * @return
     * @throws Exception
     */
    public static Date getDayByTimeStamp(long time){
        calendar.setTimeInMillis(time);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Date(calendar.getTimeInMillis());
    }

    public static Date getHourByTimeStamp(long time){
        calendar.setTimeInMillis(time);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Date(calendar.getTimeInMillis());
    }

    /**
     * 由当前时间戳获取某月第一天的date
     * @param time
     * @return
     * @throws Exception
     */
    public static Date getMonthStartTimeByTimeStamp(long time){
        calendar.setTimeInMillis(time);
        calendar.add(Calendar.YEAR, 0);
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Date(calendar.getTimeInMillis());
    }

    /**
     * 由当前时间戳获取某月最后一天的date
     * @param time
     * @return
     * @throws Exception
     */
    public static Date getMonthEndTimeByTimeStamp(long time){
        calendar.setTimeInMillis(time);
        calendar.add(Calendar.YEAR, 0);
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));// 获取当前月最后一天
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new Date(calendar.getTimeInMillis());
    }

    /**
     * 获取当年的开始时间戳
     * @return
     */
    public static Date getYearStartTimeByTimeStamp(long time) {
        Calendar calendar = Calendar.getInstance();// 获取当前日期
        calendar.setTimeInMillis(time);
        calendar.add(Calendar.YEAR, 0);
        calendar.add(Calendar.DATE, 0);
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Date(calendar.getTimeInMillis());
    }

    /**
     * 获取当年的最后时间
     * @return
     */
    public static Date getYearEndTimeByTimeStamp(long time) {
        calendar.setTimeInMillis(time);
        int year = calendar.get(Calendar.YEAR);
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        return new Date(calendar.getTimeInMillis());
    }

    /**
     * 时间戳转date
     * @param time
     * @return
     */
    public static Date toDateFromTimeStamp(String time){
        Date date = new Date();
        try {
            date.setTime(Long.parseLong(time));
        } catch (Exception e) {
            throw new BizException("时间戳格式错误");
        }
        return date;
    }

    /**
     * 给日期加秒
     * @param date
     * @param seconds
     * @return
     */
    public static Date addSeconds(Date date, String seconds){
        int secondsLong = Integer.parseInt(seconds);
        Calendar after = Calendar.getInstance();
        after.setTime(date);
        after.add(Calendar.SECOND,secondsLong);
        return new Date(after.getTimeInMillis());
    }

    /**
     * 给日期减秒
     * @param date
     * @param seconds
     * @return
     */
    public static Date minSeconds(Date date, String seconds){
        int secondsLong = 0 - Integer.parseInt(seconds);
        Calendar after = Calendar.getInstance();
        after.setTime(date);
        after.add(Calendar.SECOND,secondsLong);
        return new Date(after.getTimeInMillis());
    }

    /**
     * 获取这一天的开始时间
     * @param now
     * @return
     */
    public static Date getDayBegin(long now) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(now);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        return new Date(calendar.getTimeInMillis());
    }
    /**
     * 获取这一天的结束时间
     * @param now
     * @return
     */
    public static Date getDayEnd(long now) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(now);
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);
        return new Date(calendar.getTimeInMillis());
    }

    public static String getStringDay(){
        return dayDateFormat.format(getDayByTimeStamp(System.currentTimeMillis()));
    }
}
