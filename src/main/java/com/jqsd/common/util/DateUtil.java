package com.jqsd.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * String工具
 */
public class DateUtil {

	private static final Logger LOG = LoggerFactory.getLogger(DateUtil.class);

	/**
	 * 格式化日期
	 * @param format 格式
	 * @param date 日期
	 * @return 日期字符串
	 */
	public static String dateToStr(String format, Date date){
		try {
			return new SimpleDateFormat(format).format(date);
		} catch (Exception e) {
			LOG.error("格式化日期异常:" + e.toString());
		}

		return "";
	}

	public static Date strToDate(String time){
		try {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
		} catch (Exception e) {
			LOG.error("格式化日期异常:" + e.toString());
		}

		return new Date();
	}

	/**
	 * 返回相应间隔天数的方法
	 * @param todayStr 当前时间
	 * @param i 前1天 -1  后一天为1
	 * @return 所需yyyy-MM-dd格式的时间
	 */
	public static String addDay(String todayStr, int i) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			Calendar cd = Calendar.getInstance();
			cd.setTime(sdf.parse(todayStr));
			cd.add(Calendar.DATE, i);//增加一天
			//cd.add(Calendar.MONTH, n);//增加一个月
			return sdf.format(cd.getTime());
		} catch (Exception e) {
			return null;
		}
	}
	/**
	 * 求某天所在星期天的日期
	 * @param timeStr
	 * @return
	 */
	public static String getSunday(String timeStr){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式
		Calendar cal = Calendar.getInstance();
		Date time=null;
		try {
			time = sdf.parse(timeStr);
		} catch (ParseException e) {
			System.out.println(timeStr);
			e.printStackTrace();
		}
		cal.setTime(time);
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		String imptimeBegin = sdf.format(cal.getTime());
		return imptimeBegin;
	}
	/**
	 * 获取当天是星期几
	 * @param day
	 * @return int value= 1-7 分别为周一到周日
	 */
	public static int getWeeksDay(String day){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式
		Calendar cal = Calendar.getInstance();
		Date time=null;
		try {
			time = sdf.parse(day);
		} catch (ParseException e) {
			System.out.println(day);
			e.printStackTrace();
		}
		cal.setTime(time);
		int dayWeeks= cal.get(Calendar.DAY_OF_WEEK)-1;
		return dayWeeks==0?7:dayWeeks;// 获得当前日期是一个星期的第几天
	}

	/**
	 * 比较两个日期大小
	 *
	 * @param DATE1
	 * @param DATE2
	 * @return	date1>date2 return1; date2>date1 return-1	相等等于0 异常等于2
	 */
	public static int compare_date(String DATE1, String DATE2) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date dt1 = df.parse(DATE1);
			Date dt2 = df.parse(DATE2);
			if (dt1.getTime() > dt2.getTime()) {
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("~~~~~~~哎呦卧槽,比较日期大小异常~~~~~~");
			return 2;
		}
	}
	/**
	 *
	 * @param today  yyyy-MM-dd		海外  以每周日为当周第一天
	 * @return	int 获取当前年的第几周
	 */
	public static int getWeeks(String today){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date=null;
		try {
			date = format.parse(today);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int i= calendar.get(Calendar.WEEK_OF_YEAR);
		return i;
	}
	/**
	 *
	 * @param today  yyyy-MM-dd		国内  以每周一为当周第一天
	 * @return	int 获取当前年的第几周
	 */
	public static int getWeeksByInland(String today){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date=null;
		try {
			date = format.parse(today);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);//设置星期一为周一
		calendar.setTime(date);
		int i= calendar.get(Calendar.WEEK_OF_YEAR);
		return i;
	}




	/**
	 * 返回相隔某几个月数的方法
	 * @param todayStr 当前时间
	 * @param i 前1月 -1  后一月为1
	 * @return 所需yyyy-MM-dd格式的时间
	 */
	public static String addMonth(String todayStr, int i) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			Calendar cd = Calendar.getInstance();
			cd.setTime(sdf.parse(todayStr));
			//cd.add(Calendar.DATE, i);//增加一天
			cd.add(Calendar.MONTH, i);//增加一个月
			return sdf.format(cd.getTime());
		} catch (Exception e) {
			return null;
		}
	}
	/**
	 * 获取日期-yyyy-MM-dd
	 * @param date 日期
	 * @return 时间
	 */
	public static String getDateToStr(Date date) {
		return dateToStr("yyyy-MM-dd", date);
	}

	/**
	 * 获取当前日期-yyyy-MM-dd
	 * @return 当前日期
	 */
	public static String getCurDateToStr() {
		return dateToStr("yyyy-MM-dd", new Date());
	}

	/**
	 * 获取当前年月(无分割)-yyyyMM
	 * @return 当前年月
	 */
	public static String getCurYearMonNoSeqToStr() {
		return dateToStr("yyyyMM", new Date());
	}

	/**
	 * 获取日期时间-yyyy-MM-dd HH:mm:ss
	 * @param date 日期时间
	 * @return 日期时间
	 */
	public static String getDateTimeToStr(Date date) {
		return dateToStr("yyyy-MM-dd HH:mm:ss", date);
	}

	/**
	 * 获取当前年月日-yyyy/MM/dd
	 * @return 当前年月
	 */
	public static String getCurDateByFileSeqToStr() {
		return dateToStr("yyyy/MM/dd", new Date());
	}

	/**
	 * 获取字符串最大日期-yyyy-MM-dd 59:59:59
	 * @return 时间
	 */
	public static String getMaxDateByStrToStr(String str) {
		return str + " 23:59:59";
	}

	/**
	 * 获取字符串最小日期-yyyy-MM-dd 00:00:00
	 * @return 时间
	 */
	public static String getMinDateByStrToStr(String str) {
		return str + " 00:00:00";
	}
	/**
	 * String 类型的日期相减得天数
	 * @throws ParseException
	 */
	public static long getDaySub(String startDate,String endDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date start = null;
		Date end = null;
		try {
			start = sdf.parse(startDate);
			end = sdf.parse(endDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long day = (end.getTime() - start.getTime())/(1000*60*60*24);
		return day;
	}
	/**
	 * 获取某年第一天日期
	 * @param year 年份
	 * @return Date
	 */
	public static Date getYearFirst(int year){
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		Date currYearFirst = calendar.getTime();
		return currYearFirst;
	}

	/**
	 * 获取某年最后一天日期
	 * @param year 年份
	 * @return Date
	 */
	public static Date getYearLast(int year){
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.roll(Calendar.DAY_OF_YEAR, -1);
		Date currYearLast = calendar.getTime();

		return currYearLast;
	}
	/**
	 * 获取某年第一天日期
	 * @param year 年份
	 * @return String
	 */
	public static String getYearFirstByStr(int year){
		return dateToStr("yyyy-MM-dd",getYearFirst(year));
	}
	/**
	 * 获取某年最后一天日期
	 * @param year 年份
	 * @return String
	 */
	public static String getYearLastByStr(int year){
		return dateToStr("yyyy-MM-dd",getYearLast(year));
	}
	/**
	 * 计算当前年第一天是星期几
	 * @param year
	 * @return
	 */
	public static int getYearFirstToWeeks(int year){
		Calendar calendar = Calendar.getInstance();//获得一个日历
		calendar.set(year, 0, 1);//设置当前时间,月份是从0月开始计算
		int number = calendar.get(Calendar.DAY_OF_WEEK);//星期表示1-7，是从星期日开始，
		// String [] str = {"","星期日","星期一","星期二","星期三","星期四","星期五","星期六",};
		// System.out.println(str[number]);
		return number;
	}
	/**
	 * 根据年份和周数 得到  时间段	根据国内周一为startdate
	 * @param year
	 * @param weeks
	 * @return
	 */
	public static String[] getDateByWeeks(int year,int weeks){
		String firstSunDay=getSunday(getYearFirstByStr(year));
		int num =(weeks-1)*7;
		String mon=addDay(firstSunDay, num+1);
		String sunday=addDay(firstSunDay, num+7);
		String weeksTime[]={mon,sunday};
		return weeksTime;
	}
	/**
	 * 根据年份和周数 得到  时间段	根据海外周日为startdate
	 * @param year
	 * @param weeks
	 * @return
	 */
	public static String[] getDateByWeeks2(int year,int weeks){
		String firstSunDay=getSunday(getYearFirstByStr(year));
		int num =(weeks-1)*7;
		String mon=addDay(firstSunDay, num);
		String sunday=addDay(firstSunDay, num+6);
		String weeksTime[]={mon,sunday};
		return weeksTime;
	}

	/**
	 * 根据周数 算出 时间段 周日开始 周六结束
	 * @param year
	 * @param weeks
	 * @return
	 */
	public  static String[] getDateByWeeks3(int year,int weeks){
		LocalDate ld = LocalDate.now().of(year, 1, 1).plusWeeks(weeks - 1);
		String sunday = ld.minusWeeks(1).with(DayOfWeek.SUNDAY).toString();
		String saturday = ld.with(DayOfWeek.SATURDAY).toString();
		return new String[]{sunday,saturday};
	}

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
	 * 返回今天日期
	 * 格式 yyyy-MM-dd
	 * @return
	 */
	public static String getLocalDate(int i){
		return LocalDate.now().plusDays(i).toString();
	}

//    public static void main(String[] args) {
//        int year = 2016;
//        int weeks = 28;
//
//        //System.out.println(getDateByWeeks(year, weeks)[0]+" - "+getDateByWeeks(year, weeks)[1]);
//        System.out.println(getDateByWeeks2(year,weeks)[0]+" - "+getDateByWeeks2(year,weeks)[1]);
//
//        System.out.println(getDateByWeeks3(year,weeks)[0]+" - "+getDateByWeeks3(year,weeks)[1]);
//
//
//
//    }


	/**
	 * 根据当前日期与之后第一个星期几之间的时间间隔
	 * @return
	 */
	public static long getSubtime(Calendar cal){
		long nowTime = cal.getTimeInMillis();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
		cal.set(Calendar.HOUR_OF_DAY, 8);
		cal.set(Calendar.MINUTE, 50);
		long runTime = cal.getTimeInMillis();
		if(runTime<nowTime){
			cal.add(Calendar.DATE, 7);
			runTime = cal.getTimeInMillis();
		}
		return runTime-nowTime;
	}
	/**
	 * 根据当前日期与每天九点之间的时间间隔
	 * @return
	 */
	public static long getDaySubtime(Calendar cal){
		long nowTime = cal.getTimeInMillis();
		cal.set(Calendar.HOUR_OF_DAY, 8);
		cal.set(Calendar.MINUTE, 50);
		long runTime = cal.getTimeInMillis();
		if(runTime<nowTime){
			cal.add(Calendar.DATE, 1);
			runTime = cal.getTimeInMillis();
		}
		return runTime-nowTime;
	}
	/**
	 * 根据当前日期与每天N点N分之间的时间间隔
	 * @return
	 */
	public static long getDaySubtime(Calendar cal,int hour,int minute){
		long nowTime = cal.getTimeInMillis();
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		long runTime = cal.getTimeInMillis();
		if(runTime<nowTime){
			cal.add(Calendar.DATE, 1);
			runTime = cal.getTimeInMillis();
		}
		return runTime-nowTime;
	}

	/**
	 * 获取当前时间到明天零点的秒数差，用于redis设置的keys过期时间
	 * @return
	 */
	public static int getTomorrow(){
		Calendar cal = Calendar.getInstance();
		long now = cal.getTimeInMillis();
		cal.add(Calendar.DATE,1);
		cal.set(Calendar.HOUR_OF_DAY,0);
		cal.set(Calendar.MINUTE,0);
		long tomorrow = cal.getTimeInMillis();
		return (int)((tomorrow-now)/1000);
	}

	/**
	 * 根据当前日期与之后第一个星期几之间的时间间隔
	 * @return
	 */
	public static long getSubtime(Calendar cal,int hour,int minute,int week){
		long nowTime = cal.getTimeInMillis();
		cal.set(Calendar.DAY_OF_WEEK,week);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		long runTime = cal.getTimeInMillis();
		if(runTime<nowTime){
			cal.add(Calendar.DATE, 7);
			runTime = cal.getTimeInMillis();
		}
		return runTime-nowTime;
	}
	/**
	 * yyyy-MM-dd String类型时间相减
	 * @param time
	 * @return
	 */
	public static String getSubString(String time,int day){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date=sdf.parse(time);
			date = new Date(date.getTime()-1000L*60*60*24*day);
			String result=sdf.format(date);
			return result;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * yyyy-MM-dd 返回某时间那一周的星期一
	 * @param time
	 * @return
	 */
	public static String getMondayOfWeek(String time){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		String monday = "";
		try {
			cal.setTime(sdf.parse(time));
			int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
			if(1==dayWeek){
				cal.add(Calendar.DAY_OF_MONTH, -1);
				dayWeek = cal.get(Calendar.DAY_OF_WEEK);
			}
			cal.add(Calendar.DATE, Calendar.MONDAY-dayWeek);
			monday = sdf.format(cal.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return monday;
	}

	public static String getSundayOfWeek(String time){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		String sunday = "";
		try {
			cal.setTime(sdf.parse(time));
			cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			sunday = sdf.format(cal.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sunday;
	}

	/**
	 * 获取第一次经历给定时间的毫秒数
	 * @param startTime(00:00:00)
	 * @return
	 */
	public static long getMillisOfFirst(String startTime){
		Calendar cal = Calendar.getInstance();
		Date now = new Date();
		cal.setTime(now);
		long nowL = cal.getTimeInMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowStr = sdf.format(now);
		String startDate = nowStr + " " + startTime;
		long startL = 0;
		try {
			cal.setTime(sdf1.parse(startDate));
			startL = cal.getTimeInMillis();
			if(startL<nowL){
				cal.add(Calendar.DAY_OF_MONTH, 1);
				startL = cal.getTimeInMillis();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return startL;
	}

	/**
	 * 根据当前时间获取上个月和本月 月初与月末时间
	 * yyyy-MM-dd
	 * @return
	 * @throws ParseException
	 */
	public static String[] getMonthDay(String day) throws Exception{
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		cal.setTime(sdf.parse(day));
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)+1);
		cal.set(Calendar.DAY_OF_MONTH, 0);
		//本月末
		String thisMonthEnd=sdf.format(cal.getTime());
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH));
		cal.set(Calendar.DAY_OF_MONTH, 1);
		String thisMonthStart=sdf.format(cal.getTime());
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)-1);
		String lastMonthStart=sdf.format(cal.getTime());
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)+1);
		cal.set(Calendar.DAY_OF_MONTH, 0);
		String lastMonthEnd=sdf.format(cal.getTime());
		String[] monthDays={lastMonthStart,lastMonthEnd,thisMonthStart,thisMonthEnd};
		return monthDays;
	}

	/**
	 * 根据当前时间获取上周和本周的周一和周日
	 * yyyy-MM-dd
	 * @return
	 * @throws ParseException
	 */
	public static String[] getWeekDay(String day) throws Exception{
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		cal.setTime(sdf.parse(day));
		String thisMonday=getMondayOfWeek(day);
		String thisSunday=sdf.format(sdf.parse(thisMonday).getTime()+1000*60*60*24*6);
		String lastMonday=sdf.format(sdf.parse(thisMonday).getTime()-1000*60*60*24*7);
		String lastSunday=sdf.format(sdf.parse(thisMonday).getTime()-1000*60*60*24*1);
		String[] weekDay={lastMonday,lastSunday,thisMonday,thisSunday};
		return weekDay;
	}
	/**
	 * 根据某天得到该天所在星期的星期几
	 * @param today 当前日期
	 * @param day	转换为星期几(1-7)
	 * @return
	 */
	public static String getSomeDay(String today,int day){
		try {
			if(day>7 || day<=0){
				throw new Exception("请输入1-7之间的数字");
			}else{
				day = day + 1;
			}
			Calendar cal = Calendar.getInstance();
			cal.setFirstDayOfWeek(Calendar.MONDAY);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			cal.setTime(sdf.parse(today));
			int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
			if(dayWeek==1){
				cal.add(Calendar.DATE, -1);
				dayWeek = cal.get(Calendar.DAY_OF_WEEK);
			}
			cal.add(Calendar.DATE, day-dayWeek);
			return sdf.format(cal.getTime());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 将 日期字符串 转换成 date
	 * 也就是request 头域中的 Date 格式 :Date: Mon, 12-Dec-2016 02:17:30 GMT
	 * @param dateStr
	 * @return
	 */
	public static Date parseDateGMT(String dateStr) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("EEE, d-MMM-yyyy HH:mm:ss 'GMT'", Locale.US);
		return sdf.parse(dateStr);
	}

}
