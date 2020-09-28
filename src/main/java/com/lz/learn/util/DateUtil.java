package com.lz.learn.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 * @author DELL
 * @create 2020/9/28
 * @since 1.0.0
 */
public class DateUtil {
    public static final String FMT_YMDHMS="yyyy-MM-dd HH:mm:ss";
    public static final String FMT_YMDHM="yyyy-MM-dd HH:mm";
    public static final String FMT_YMDH="yyyy-MM-dd HH";
    public static final String FMT_YMD="yyyy-MM-dd";
    public static final String FMT_YM="yyyy-MM";
    public static final String FMT_Y="yyyy";
    public static final String FMT_M="MM";
    public static final String FMT_D="dd";
    public static final String FMT_H="HH";
    public static final String FMT_MIN="mm";
    public static final String FMT_S="ss";

    public static final String YEAR="year";
    public static final String MONTH="month";
    public static final String DAY="day";
    public static final String HOUR="hour";
    public static final String MINUTE="minute";
    public static final String SECOND="second";
    public static final String MILLI="milli";

    private DateUtil(){}

    /**
     *  获取当前时间字符串
     * @param fmt 时间格式
     * @return 返回当前时间字符串
     */
    public static String getNow(String fmt){
        return date2str(new Date(),fmt);
    }

    /**
     * 获取当前时间字符串（格式为：yyyy-MM-dd HH:mm:ss）
     * @return
     */
    public static String getNow(){
        return date2str(new Date(), FMT_YMDHMS);
    }

    /**
     * 将日期字符串进行格式转化
     * @param date 字符串格式日期
     * @param oldFmt 原字符串格式
     * @param newFmt 新字符串格式
     * @return 字符串格式日期
     */
    public static String dateFmt(String date,String oldFmt,String newFmt){
        Date date1 = str2date(date, oldFmt);
        return date2str(date1,newFmt);
    }

    /**
     *  将日期类型转化为字符串类型
     * @param date 日期类型
     * @param fmt 转化格式
     * @return 返回日期对应的字符串
     */
    public static String date2str(Date date,String fmt){
        if(date==null){
            throw new NullPointerException();
        }
        SimpleDateFormat sdf=new SimpleDateFormat(fmt);
        return sdf.format(date);
    }

    /**
     *  将日期字符串转化为日期类型
     * @param date 日期字符串
     * @param fmt 日期类型
     * @return 返回日期类型
     */
    public static Date str2date(String date,String fmt){
        SimpleDateFormat sdf=new SimpleDateFormat(fmt);
        Date parse=null;
        try {
            parse = sdf.parse(date);
        }catch (ParseException e){
        }
        return parse;
    }

    /**
     * 计算给定时间对应的时间差
     * @param date 日期字符串
     * @param oldFmt 给定字符串格式
     * @param newFmt 计算时间差后的日期格式
     * @param timeType 时间单位类型（年:year、月:month、日:day、时:hour、分：minute、秒:second，毫秒:milli）
     * @param diff 时间差（毫秒）(负数时间往前推)
     * @return 返回日期字符串
     */
    public static String dateDiff(String date,String oldFmt,String newFmt,String timeType,Long diff){
        long d=diff;
        if(YEAR.equals(timeType)){//年
            d*= 1000*60*60*24;
            d*=getDaysOfYear(date,oldFmt);
        }else if(MONTH.equals(timeType)){//月
            d*= 1000*60*60*24;
            d*=getDaysOfMonth(date,oldFmt);
        }else if(DAY.equals(timeType)){//日
            d*= 1000*60*60*24;
        }else if(HOUR.equals(timeType)){//时
            d*= 1000*60*60;
        }else if(MINUTE.equals(timeType)){//分
            d*= 1000*60;
        }else if(SECOND.equals(timeType)){//秒
            d*= 1000;
        }
        Date dateTime=str2date(date,oldFmt);
        if(dateTime!=null){
            d+=dateTime.getTime();
        }
        return date2str(d,newFmt);
    }

    /**
     * 判断是否为闰年
     * @param date 时间
     * @param fmt 时间格式
     * @return
     */
    public static Boolean isLeapYear(String date,String fmt){
        boolean flag=false;
        String s = dateFmt(date, fmt, FMT_Y);
        int year=Integer.parseInt(s);
        if(year%400==0 || (year%4==0 && year%100!=0)){
            flag=true;
        }
        return flag;
    }

    /**
     * 获取指定日期年份有多少天
     * @param date 日期
     * @param fmt 日期格式字符串
     * @return 返回日期所在年的天数
     */
    public static int getDaysOfYear(String date,String fmt){
        return Boolean.TRUE.equals(isLeapYear(date,fmt))?366:365;
    }

    /**
     * 获取给定时间月份有多少天
     * @param date 时间
     * @param fmt 时间格式
     * @return 返回日期所在月份天数
     */
    public static Integer getDaysOfMonth(String date,String fmt){
        String s = dateFmt(date, fmt,FMT_M);
        int month=Integer.parseInt(s);
        if(month==1 || month==3 || month==5 || month==7 || month==8 || month==10 || month==12){//月大
            return 31;
        }else if(month==2){//二月
            return Boolean.TRUE.equals(isLeapYear(date, fmt))?29:28;
        }else{//月小
            return 30;
        }
    }

    /**
     * 获取给定时间月份第一天
     * @param date 时间字符串
     * @param oldFmt 原始日期格式
     * @param newFmt 输出日期格式
     * @return 日期字符串
     */
    public static String getFirstDayOfMonth(String date,String oldFmt,String newFmt){
        Calendar instance = Calendar.getInstance();
        instance.setTime(str2date(date,oldFmt));
        instance.add(Calendar.MONTH,0);
        instance.set(Calendar.DAY_OF_MONTH, 1);
        return date2str(instance.getTime(),newFmt);
    }

    /**
     * 获取给定时间月份最后天
     * @param date 时间字符串
     * @param oldFmt 原始日期格式
     * @param newFmt 输出日期格式
     * @return 日期字符串
     */
    public static String getLastDayOfMonth(String date,String oldFmt,String newFmt){
        Calendar instance = Calendar.getInstance();
        instance.setTime(str2date(date,oldFmt));
        instance.add(Calendar.MONTH,1);
        instance.set(Calendar.DAY_OF_MONTH, 0);
        return date2str(instance.getTime(),newFmt);
    }

    /**
     * 获取给定时间是星期几
     * @param date 日期字符串
     * @param fmt 日期原始格式
     * @param type 1：返回汉子，否则返回数字
     * @return 返回星期几
     */
    public static String getWeekOfDate(String date,String fmt,int type){
        Calendar instance = Calendar.getInstance();
        instance.setTime(str2date(date,fmt));
        int i = instance.get(Calendar.DAY_OF_WEEK)-1;
        if(type==1){
            String[] weekdays = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
            return weekdays[i];
        }else{
            return (i==0? 7:i)+"";
        }
    }


    /**
     *  将日期类型转化为字符串类型
     * @param m 毫秒
     * @param fmt 转化格式
     * @return 返回日期对应的字符串
     */
    private static String date2str(Long m,String fmt){
        return date2str(new Date(m),fmt);
    }
}
