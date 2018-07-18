package com.jqsd.core.job;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class TestDate {

	public static void main(String[] args) {

		for (int i = 0; i < 10; i++) {
			//	测试修改
			System.out.println("请输入一个起始时间");
			Scanner scan = new Scanner(System.in);
			String StartDate = scan.nextLine();

			System.out.println("请输入一个结束时间");

			String EndtDate = scan.nextLine();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

			long nd = 1000 * 24 * 60 * 60;
			long nh = 1000 * 60 * 60;
			long nm = 1000 * 60;

			try {
				Date StartDate_new = sdf.parse(StartDate);
				Date EndtDate_new = sdf.parse(EndtDate);

				// 获得两个时间的毫秒时间差异
				long diff = EndtDate_new.getTime() - StartDate_new.getTime();

				// 计算差多少小时
				double day = (double) diff / (1000 * 60 * 60);

				/// int betweenDay = getBetweenDay(StartDate_new,EndtDate_new);
				// System.out.println(betweenDay);

				// 上班时间 9-12 3小时，13.30-18.30 5小时 一共8小时

				// 起始时间和9点比较
				Date NiceDate = sdf.parse(formatter.format(StartDate_new) + " 09:00:00");

				// 起始时间和9点比较2
				Date NiceDate2 = sdf.parse(formatter.format(EndtDate_new) + " 09:00:00");

				// 正常的下班时间
				Date EndtDate_news = sdf.parse(formatter.format(StartDate_new) + " 18:30:00");

				// 正常的下班时间2
				Date EndtDate_news2 = sdf.parse(formatter.format(EndtDate_new) + " 18:30:00");

				// 起始时间的当天的最后一刻
				Date EndDateLast = sdf.parse(formatter.format(StartDate_new) + " 23:59:59");

				// 当天的12点
				Date TwelveDate = sdf.parse(formatter.format(StartDate_new) + " 12:00:00");

				// 当天的12点
				Date TwelveDate2 = sdf.parse(formatter.format(EndtDate_new) + " 12:00:00");

				// 下午的13.30
				Date AfternoonDate = sdf.parse(formatter.format(StartDate_new) + " 13:30:00");

				// 先算出输入的起始时间与9点相差的时间间隔，判断出是在9点前出差还是9点后出差
				double FirstDate = (double)(NiceDate.getTime() - StartDate_new.getTime()) / (1000 * 60 * 60);

				//DecimalFormat df = new DecimalFormat("#.00");

				if (FirstDate > 0) { // 大于0则表示在9点前出差

					// 判断出差的时间是否是一天内
					if (getBetweenDay(NiceDate, EndtDate_new) == 0) { // 如果等于零则表示是出差了一天内

						if (EndtDate_news.getTime() > EndtDate_new.getTime()) { // 表示不是在正常的下班时间打卡的

							if (TwelveDate.getTime() >= EndtDate_new.getTime()) { // 如果出差的时间在早上结束的话

								// 那么加班时间就等于
								double TravelDate = (double) (EndtDate_new.getTime() - NiceDate.getTime())
										/ (1000 * 60 * 60);

								System.out.println("出差天数为：" + formatDouble(TravelDate / 8));
							} else {
								double TravelDate = ((double) (EndtDate_new.getTime() - NiceDate.getTime())
										/ (1000 * 60 * 60) - 1.5);

								System.out.println("出差天数为：" + formatDouble(TravelDate / 8));
							}
						} else {

							double TravelDate = ((double) (EndtDate_news.getTime() - NiceDate.getTime())
									/ (1000 * 60 * 60) - 1.5);
							System.out.println("出差天数为：" + formatDouble(TravelDate / 8));
						}

					} else { // 表示出差天数大于一天的,并且出差的时间为大于等于9点

						// 先算出开始日期和结束日期相差的时间
						int betweenDay = getBetweenDay(StartDate_new, EndtDate_new); // 用此天数减去1代表的是中间相隔完整的多少天

						// 那么最终的出差天数为betweenDay+出差开始的时间到18.30的时间+早上9点到下午18.30的时间
						int dateSx = getDateSx(StartDate_new);
						if (dateSx == 1) {
							double a1 = 0;

							if (getDateSx(StartDate_new) == 1) {
								a1 = ((double) (EndtDate_news.getTime() - NiceDate.getTime()) / (1000 * 60 * 60) - 1.5);
							} else/* (getDateSx(StartDate_new)==2) */ {
								a1 = ((double) (EndtDate_news.getTime() - StartDate_new.getTime() / (1000 * 60 * 60)));
							}
							/*
							 * else { a1 = (double)(EndtDate_news.getTime()-AfternoonDate.getTime()/ (1000 *
							 * 60 * 60)); }
							 */

							double a2 = 0.00;
							if (getDateSx(EndtDate_new) == 1) {

								a2 = ((double) EndtDate_new.getTime() - NiceDate2.getTime()) / (1000 * 60 * 60);
							} else if (getDateSx(EndtDate_new) == 2) {
								// a2 = ((EndtDate_new.getTime()-NiceDate2.getTime()/ (1000 * 60 * 60))-2);
								//a2 = ((double) EndtDate_new.getTime() - NiceDate2.getTime()) / (1000 * 60 * 60) - 1.5;
								if (getHour(EndtDate_new)>=18) {
									if (EndtDate_new.getTime()>EndtDate_news2.getTime()) {
										a2 = ((double) EndtDate_news2.getTime() - NiceDate2.getTime()) / (1000 * 60 * 60)- 1.5;
									}else {
										a2 = ((double) EndtDate_new.getTime() - NiceDate2.getTime()) / (1000 * 60 * 60)- 1.5;
									}
								}else {
									a2 = ((double) EndtDate_new.getTime() - NiceDate2.getTime()) / (1000 * 60 * 60)- 1.5;
								}
							} else {
								a2 = ((double) TwelveDate2.getTime() - EndtDate_new.getTime() / (1000 * 60 * 60));
							}

							double a = (betweenDay - 1) + a1 / 8 + a2 / 8;

							System.out.println("出差时间为：" + formatDouble(a));

						}

					}

				} else {

					/// 判断出差的时间是否是一天内
					if (getBetweenDay(NiceDate, EndtDate_new) == 0) { // 如果等于零则表示是出差了一天内

						if (EndtDate_news.getTime() > EndtDate_new.getTime()) { // 表示不是在正常的下班时间打卡的

							if (TwelveDate.getTime() >= EndtDate_new.getTime()) { // 如果出差的时间在早上结束的话

								// 那么出差时间就等于
								double TravelDate = (double) (TwelveDate.getTime() - EndtDate_new.getTime())
										/ (1000 * 60 * 60);

								System.out.println("出差天数为：" + formatDouble(TravelDate / 8));
							} else {
								double TravelDate=0.00;
								if (EndtDate_new.getTime()>=AfternoonDate.getTime()) {
									if (StartDate_new.getTime()>TwelveDate.getTime()&&StartDate_new.getTime()<AfternoonDate.getTime()) {
										TravelDate = ((double) (EndtDate_new.getTime() - AfternoonDate.getTime())/ (1000 * 60 * 60));
									}else {
										TravelDate = ((double) (EndtDate_new.getTime() - NiceDate.getTime())/ (1000 * 60 * 60) - 1.5);
									}				
								}else {
									TravelDate = ((double) (TwelveDate2.getTime() - StartDate_new.getTime())/ (1000 * 60 * 60));
								}
								

								System.out.println("出差天数为：" + formatDouble(TravelDate / 8));
							}
						} else {
							double TravelDate =0.00;
							if (TwelveDate.getTime()<StartDate_new.getTime()&&StartDate_new.getTime()<AfternoonDate.getTime()) {
								TravelDate = ((double) (EndtDate_news.getTime() - AfternoonDate.getTime())/ (1000 * 60 * 60));
							}else {
								TravelDate = ((double) (EndtDate_news.getTime() - StartDate_new.getTime())/ (1000 * 60 * 60) - 1.5);
							}
							
							System.out.println("出差天数为：" + formatDouble(TravelDate / 8));
						}

					} else { // 表示出差天数大于一天的,并且出差的时间为大于等于9点

						// 先算出开始日期和结束日期相差的时间
						int betweenDay = getBetweenDay(StartDate_new, EndtDate_new); // 用此天数减去1代表的是中间相隔完整的多少天

						// 那么最终的出差天数为betweenDay+出差开始的时间到18.30的时间+早上9点到下午18.30的时间
						int dateSx = getDateSx(StartDate_new);
						/*if (dateSx == 1) {*/
							double a1 = 0;

							if (getDateSx(StartDate_new) == 1) {
								a1 = ((double) (EndtDate_news.getTime() - StartDate_new.getTime()) / (1000 * 60 * 60)
										- 1.5);
							} else/* (getDateSx(StartDate_new)==2) */ {
								if (StartDate_new.getTime()>TwelveDate.getTime()&&StartDate_new.getTime()<AfternoonDate.getTime()) {
									a1 = ((double) (EndtDate_news.getTime() - AfternoonDate.getTime()) / (1000 * 60 * 60));
								}else {
									a1 = ((double) (EndtDate_news.getTime() - StartDate_new.getTime()) / (1000 * 60 * 60));
								}
								
							}
							/*
							 * else { a1 = (double)(EndtDate_news.getTime()-AfternoonDate.getTime()/ (1000 *
							 * 60 * 60)); }
							 */

							double a2 = 0.00;
							if (getDateSx(EndtDate_new) == 1) {

								a2 = ((double) EndtDate_new.getTime() - EndtDate_new.getTime()) / (1000 * 60 * 60);
							} else if (getDateSx(EndtDate_new) == 2) {
								// a2 = ((EndtDate_new.getTime()-NiceDate2.getTime()/ (1000 * 60 * 60))-2);
								if (getHour(EndtDate_new)>=18) {
									if (EndtDate_new.getTime()>EndtDate_news2.getTime()) {
										a2 = ((double) EndtDate_news2.getTime() - NiceDate2.getTime()) / (1000 * 60 * 60)- 1.5;
									}else {
										a2 = ((double) EndtDate_new.getTime() - NiceDate2.getTime()) / (1000 * 60 * 60)- 1.5;
									}
								}else {
									a2 = ((double) EndtDate_new.getTime() - NiceDate2.getTime()) / (1000 * 60 * 60)- 1.5;
								}							
							} else {
								a2 = ((double) TwelveDate2.getTime() - EndtDate_new.getTime() / (1000 * 60 * 60));
							}

							double a = (betweenDay - 1) + a1 / 8 + a2 / 8;

							System.out.println("出差时间为：" + formatDouble(a));

						/*}*/

					}
				}

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// 算出两个时间相差的天数
	public static int getBetweenDay(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		int day1 = cal1.get(Calendar.DAY_OF_YEAR);
		int day2 = cal2.get(Calendar.DAY_OF_YEAR);

		int year1 = cal1.get(Calendar.YEAR);
		int year2 = cal2.get(Calendar.YEAR);
		if (year1 != year2) // 同一年
		{
			int timeDistance = 0;
			for (int i = year1; i < year2; i++) {
				if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) // 闰年
				{
					timeDistance += 366;
				} else // 不是闰年
				{
					timeDistance += 365;
				}
			}

			return timeDistance + (day2 - day1);
		} else // 不同年
		{
			return day2 - day1;
		}

	}

	// 输入一个时间，返回在上午还是下午
	public static int getDateSx(Date date) {

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int hour = c.get(Calendar.HOUR_OF_DAY);// 小时

		int minute = c.get(Calendar.MINUTE);// 分钟

		if (0 < hour && hour <= 12) { // 代表的是上午

			return 1;
		}
		if (hour >= 13) { // 代表的是下午

			return 2;
		} else { // 代表的是中午休息时间

			return 0;
		}

	}
	
	//	输入一个时间返回其输入的小时值
	public static int getHour(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int hour = c.get(Calendar.HOUR_OF_DAY);// 小时
		
		return hour;
	}
	
	//	输入一个时间返回其输入的小时值
	public static int getminute(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int minute = c.get(Calendar.MINUTE);// 分钟
		
		return minute;
	}
	
	public static String formatDouble(double d) {
        String str = String.format("%.2f", d);
        return str;
    }
}
