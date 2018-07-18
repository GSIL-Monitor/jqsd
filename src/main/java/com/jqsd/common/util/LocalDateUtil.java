package com.jqsd.common.util;

import com.google.common.collect.Maps;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.WeekFields;
import java.util.Map;

import static java.time.temporal.ChronoField.*;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;

/**
 * Created by sam on 17-2-28.
 */
public class LocalDateUtil {

    /**
     * 返回当前日期，在一年中的第几周
     * @param date
     * @return
     */
    public static int getLocalWeek(LocalDate date){
        // Or use a specific locale, or configure your own rules
        //WeekFields weekFields = WeekFields.of(Locale.CHINESE);
        int weekNumber = date.get(WeekFields.ISO.weekOfWeekBasedYear());
        return weekNumber;
    }

    /**
     * 返回当前日期，在一年中的第几周
     * @return
     */
    public static int getLocalWeek(){
        return getLocalWeek(LocalDate.now());
    }

    /**
     * 返回今天日期
     * 格式 yyyy-MM-dd
     * @return
     */
    public static String getLocalDate(){
        return LocalDate.now().toString();
    }

    /**
     * 返回date日期的后i天日期
     * @param date
     * @param i
     * @return
     */
    public static String getLocalDate(String date,int i){
        LocalDate ld = LocalDate.parse(date);
        return ld.plusDays(i).toString();
    }

    /**
     * 返回今天日期
     * 格式 yyyy-MM-dd
     * @return
     */
    public static String getLocalDate(int i){
        return LocalDate.now().plusDays(i).toString();
    }

    public static Map<String,Object> getDaysToBewteen(String today,int days){
        LocalDate ld = LocalDate.parse(today);
        String eTime = ld.toString();
        String sTime = ld.minusDays(days).toString();
        Map<String,Object> map = Maps.newHashMap();
        map.put("startTime",sTime);
        map.put("endTime",eTime);
        map.put("localDate",ld);

        return map;
    }

    public static Map<String,Object> getWeeksToBewteen(String today, int weeks){

        LocalDate date = LocalDate.parse(today);
        int w = getLocalWeek(date);
        LocalDate ld = LocalDate.ofYearDay(date.getYear(),w*7);

        String eTime = ld.plusDays(1).toString();
        String sTime = ld.minusWeeks(weeks).plusDays(2).toString();
        Map<String,Object> map = Maps.newHashMap();
        map.put("startTime",sTime);
        map.put("endTime",eTime);
        map.put("localDate",ld);

        return map;
    }

    public static Map<String,Object> getDateByWeek(int year,int week){
        LocalDate ld = LocalDate.ofYearDay(year,week*7);

        String eTime = ld.plusDays(1).toString();
        String sTime = ld.minusWeeks(1).plusDays(2).toString();

        Map<String,Object> map = Maps.newHashMap();
        map.put("startTime",sTime);
        map.put("endTime",eTime);
        map.put("localDate",ld);

        return map;
    }

    /**
     * 返回上周日期
     * 周一 为第一天
     * @return
     */
    public static Map<String,Object> getLastWeekDate(){
        LocalDate date = LocalDate.now();
        int w = getLocalWeek(date)-1;
        LocalDate ld = LocalDate.ofYearDay(date.getYear(),w*7);

        String eTime = ld.plusDays(1).toString();
        String sTime = ld.minusWeeks(1).plusDays(2).toString();

        Map<String,Object> map = Maps.newHashMap();
        map.put("startTime",sTime);
        map.put("endTime",eTime);
        map.put("localDate",ld);
        return map;
    }

    /**
     * 返回上个月第一天与最后一天
     * @return
     */
    public static Map<String,Object> getLastMonthDate(){
        LocalDate date = LocalDate.now();
        LocalDate ld = date.minusMonths(1);

        String sTime = ld.withDayOfMonth(1).toString();
        String eTime = ld.withDayOfMonth(ld.lengthOfMonth()).toString();

        Map<String,Object> map = Maps.newHashMap();
        map.put("startTime",sTime);
        map.put("endTime",eTime);
        map.put("localDate",ld);
        return map;
    }

    /**
     * 返回本周的周一
     * @return
     */
    public static String getMondayOfWeek(){
        LocalDate date = LocalDate.now();
        LocalDate ld = date.minusDays(date.getDayOfWeek().getValue()-1);
        return ld.toString();
    }


    public static Integer[] getWeekArr(LocalDate ld,int weeks){
        Integer[] arr = new Integer[weeks];
        int index = weeks-1;
        for(int i=0;i<weeks;i++) {
            int ss = getLocalWeek(ld.minusWeeks(i));
            arr[index--] = ss;
        }
        return arr;
    }

    public static String[] getDayArr(LocalDate ld,int days){
        String[] arr = new String[days];
        int index = days-1;
        for(int i=0;i<days;i++) {
            String day = formatDay(ld.minusDays(i));
            arr[index--] = day;
        }
        return arr;
    }
    public static Integer[] getDayArrInt(LocalDate ld,int days){
        Integer[] arr = new Integer[days];
        int index = days-1;
        for(int i=0;i<days;i++) {
            int day = ld.minusDays(i).getDayOfWeek().getValue()-1;
            arr[index--] = day;
        }
        return arr;
    }

    /**
     * 格式化日期
     * @param ld
     * @return m-d
     */
    public static String formatDay(LocalDate ld){
        DateTimeFormatter df =  new DateTimeFormatterBuilder()
                .appendValue(MONTH_OF_YEAR,2)
                .appendLiteral('-')
                .appendValue(DAY_OF_MONTH,2).toFormatter();
        return ld.format(df);
    }


    public static String format(){
        return format(LocalDateTime.now());
    }

    /**
     * 格式化日期时间
     * @param ldt
     * @return YYYY-MM-dd HH-mm-ss
     */
    public static String format(LocalDateTime ldt){
        DateTimeFormatter dt = new DateTimeFormatterBuilder()
                .appendValue(HOUR_OF_DAY, 2)
                .appendLiteral(':')
                .appendValue(MINUTE_OF_HOUR, 2)
                .optionalStart()
                .appendLiteral(':')
                .appendValue(SECOND_OF_MINUTE, 2).toFormatter();

        DateTimeFormatter df = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .append(DateTimeFormatter.ISO_LOCAL_DATE)
                .appendLiteral(' ')
                .append(dt).toFormatter();

        return ldt.format(df);
    }

    public static void main(String[] args) {
       /* LocalDate ld = LocalDate.now();
        String[] arr = getDayArr(ld,7);
        Integer[] arr2 = getDayArrInt(ld,7);
        for (String s : arr){
            System.out.println(s);
        }
        for(int i=0;i<arr.length;i++){
            System.out.println(arr[i]+"<--->"+arr2[i]);
        }

        System.out.println(ld.getDayOfWeek().getValue());

        LocalDate d = LocalDate.parse("2017-03-27");
        System.out.println(d.toString());

        System.out.println(LocalDate.now().getYear());

        String str = "2017-05-14";

        System.out.println(getLocalDate(str,-6));*/

        System.out.println(getWeeksToBewteen("2017-07-11",0));

        System.out.println(getLastWeekDate());

        System.out.println(getLastMonthDate());

        LocalDate date = LocalDate.now().plusDays(6);
        System.out.println(date.toString());
        System.out.println(date.getDayOfWeek().getValue());

        LocalDate ld = date.minusDays(date.getDayOfWeek().getValue()-1);
        System.out.println(ld);


        System.out.println(getDateByWeek(2017,29));

    }
}
