package com.mlh.utils.common;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author:jilongliang
 * @Date :2012-8-19
 * @Description:时间处理类
 */
@SuppressWarnings("all")
public class DateUtils
{
	/**
	 * 字符串转换为java.util.Date<br>
	 * 支持格式为 yyyy.MM.dd G 'at' hh:mm:ss z Example:'2002-1-1 AD at 22:10:59 PSD'<br>
	 * yy/MM/dd HH:mm:ss 如 '2002/1/1 17:55:00'<br>
	 * yy/MM/dd HH:mm:ss pm 如 '2002/1/1 17:55:00 pm'<br>
	 * yy-MM-dd HH:mm:ss 如 '2002-1-1 17:55:00' <br>
	 * yy-MM-dd HH:mm:ss am 如 '2002-1-1 17:55:00 am' <br>
	 * 
	 * @param time
	 *            String 字符串<br>
	 * @return Date 日期<br>
	 */
	public static Date getStringToDate(String time)
	{
		SimpleDateFormat formatter;
		int tempPos = time.indexOf("AD");
		time = time.trim();
		formatter = new SimpleDateFormat("yyyy.MM.dd G 'at' hh:mm:ss z");
		if (tempPos > -1)
		{
			time = time.substring(0, tempPos) + "公元" + time.substring(tempPos + "AD".length());// china
			formatter = new SimpleDateFormat("yyyy.MM.dd G 'at' hh:mm:ss z");
		}
		tempPos = time.indexOf("-");
		if (tempPos > -1 && (time.indexOf(" ") < 0))
		{
			formatter = new SimpleDateFormat("yyyyMMddHHmmssZ");
		} else if ((time.indexOf("/") > -1) && (time.indexOf(" ") > -1))
		{
			formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		} else if ((time.indexOf("-") > -1) && (time.indexOf(" ") > -1))
		{
			formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		} else if ((time.indexOf("/") > -1) && (time.indexOf("am") > -1) || (time.indexOf("pm") > -1))
		{
			formatter = new SimpleDateFormat("yyyy-MM-dd KK:mm:ss a");
		} else if ((time.indexOf("-") > -1) && (time.indexOf("am") > -1) || (time.indexOf("pm") > -1))
		{
			formatter = new SimpleDateFormat("yyyy-MM-dd KK:mm:ss a");
		}
		ParsePosition pos = new ParsePosition(0);
		Date date = formatter.parse(time, pos);
		return date;
	}

	/**
	 * 将java.util.Date 格式转换为字符串格式'yyyy-MM-dd HH:mm:ss'(24小时制)<br>
	 * 如Sat May 11 17:24:21 CST 2002 to '2002-05-11 17:24:21'<br>
	 * 
	 * @param time
	 *            Date 日期<br>
	 * @return String 字符串<br>
	 */
	public static String getDateToString(Date time)
	{
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String Time = formatter.format(time);
		return Time;
	}

	/**
	 * 将java.util.Date 格式转换为字符串格式'yyyy-MM-dd HH:mm:ss a'(12小时制)<br>
	 * 如Sat May 11 17:23:22 CST 2002 to '2002-05-11 05:23:22 下午'<br>
	 * 
	 * @param time
	 *            Date 日期<br>
	 * @param x
	 *            int 任意整数如：1<br>
	 * @return String 字符串<br>
	 */
	public static String getDateToString(Date time, int x)
	{
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("yyyy-MM-dd KK:mm:ss a");
		String date = formatter.format(time);
		return date;
	}

	/**
	 * 取系统当前时间:返回只值为如下形式 2002-10-30 20:24:39
	 * 
	 * @return String
	 */
	public static String getNow()
	{
		return getDateToString(new Date());
	}

	/**
	 * 取系统当前时间:返回只值为如下形式 2002-10-30 08:28:56 下午
	 * 
	 * @param hour
	 *            为任意整数
	 * @return String
	 */
	public static String getNow(int hour)
	{
		return getDateToString(new Date(), hour);
	}

	/**
	 * 获取小时
	 * 
	 * @return
	 */
	public static String getHour()
	{
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("H");
		String hour = formatter.format(new Date());
		return hour;
	}

	/**
	 * 获取当前日日期返回 <return>Day</return>
	 */
	public static String getDay()
	{
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("d");
		String day = formatter.format(new Date());
		return day;
	}

	/**
	 * 获取周
	 * 
	 * @return
	 */
	public static String getWeek()
	{
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("E");
		String week = formatter.format(new Date());
		return week;
	}

	/**
	 * 获取上周的第一天
	 * 
	 * @return
	 */
	public static String getBeforeWeekFirstDay()
	{
		SimpleDateFormat sdf = new SimpleDateFormat();
		Date d = new Date();
		return null;

	}

	/**
	 * 获取月份
	 * 
	 * @return
	 */
	public static String getMonth()
	{
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("M");
		String month = formatter.format(new Date());
		return month;
	}

	/**
	 * 获取年
	 * 
	 * @return
	 */
	public static String getYear()
	{
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("yyyy");
		String year = formatter.format(new Date());
		return year;
	}

	/**
	 * 对日期格式的转换成("yyyy-MM-dd)格式的方法
	 * 
	 * @param str
	 * @return
	 */
	public static java.sql.Date Convert(String str)
	{
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		try
		{
			java.util.Date d = sdf.parse(str);
			java.sql.Date d1 = new java.sql.Date(d.getTime());
			return d1;
		} catch (Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取当前年、月、日：
	 * 
	 * @return
	 */
	public static int getYYMMDD()
	{
		Date date = new Date();
		int year = date.getYear() + 1900;// thisYear = 2003
		int month = date.getMonth() + 1;// thisMonth = 5
		int day = date.getDate();// thisDate = 30
		return year + month + day;
	}

	/**
	 * 取系统当前时间:返回值为如下形式 2002-10-30
	 * 
	 * @return String
	 */
	public static String getYYYY_MM_DD()
	{
		return getDateToString(new Date()).substring(0, 10);
	}

	/**
	 * 取系统给定时间:返回值为如下形式 2002-10-30
	 * 
	 * @return String
	 */
	public static String getYYYY_MM_DD(String date)
	{
		return date.substring(0, 10);
	}

	/**
	 * 在jsp页面中的日期格式和sqlserver中的日期格式不一样，怎样统一? 在页面上显示输出时，用下面的函数处理一下
	 * 
	 * @param date
	 * @return
	 */
	public static String getFromateDate(Date date)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		String strDate = formatter.format(date);
		return strDate;
	}

	/**
	 * 获取当前时间是本年的第几周
	 * 
	 * @return
	 */
	public static String getWeeK_OF_Year()
	{
		Calendar cal = Calendar.getInstance();
		Date date = new Date();
		cal.setTime(date);
		int week = cal.get(Calendar.WEEK_OF_YEAR);
		return "当日是本年的第" + week + "周";
	}

	/**
	 * 获取当日是本年的的第几天
	 * 
	 * @return
	 */
	public static String getDAY_OF_YEAR()
	{
		Calendar cal = Calendar.getInstance();
		Date date = new Date();
		cal.setTime(date);
		int day = cal.get(Calendar.DAY_OF_YEAR);
		return "当日是本年的第" + day + "天";
	}

	/**
	 * 获取本周是在本个月的第几周
	 * 
	 * @return
	 */
	public static String getDAY_OF_WEEK_IN_MONTH()
	{
		Calendar cal = Calendar.getInstance();
		Date date = new Date();
		cal.setTime(date);
		/*
		 * 这里这个值可以完全看JDK里面调用一下 或者点一下调用运行看看结果,看看里面的 English说明就知道它是干嘛的
		 */
		int week = cal.get(Calendar.DAY_OF_WEEK_IN_MONTH);
		return "本月第" + week + "周";
	}

	/**
	 * 阳历转阴历农历:http://zuoming.iteye.com/blog/1554001 GregorianCalendar使用：
	 * http://zmx.iteye.com/blog/409465 GregorianCalendar 类提供处理日期的方法。
	 * 一个有用的方法是add().使用add()方法，你能够增加象年 月数，天数到日期对象中。要使用add()方法，你必须提供要增加的字段
	 * 要增加的数量。一些有用的字段是DATE, MONTH, YEAR, 和 WEEK_OF_YEAR
	 * 下面的程序使用add()方法计算未来80天的一个日期. 在Jules的<环球80天>是一个重要的数字，使用这个程序可以计算 Phileas
	 * Fogg从出发的那一天1872年10月2日后80天的日期：
	 */
	public static void getGregorianCalendarDate()
	{
		GregorianCalendar worldTour = new GregorianCalendar(1872, Calendar.OCTOBER, 2);
		worldTour.add(GregorianCalendar.DATE, 80);
		Date d = worldTour.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String s = df.format(d);
		System.out.println("80 day trip will end " + s);
	}

	/**
	 * 用来处理时间转换格式的方法
	 * 
	 * @param formate
	 * @param time
	 * @return
	 */
	private static String getConvertDateFormat(String formate, Date time)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat(formate);
		String date = dateFormat.format(time);
		return date;
	}

	/**
	 * 得到本月的第一天
	 * 
	 * @return
	 */
	public static String getCurrentMonthFirstDay()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		return getConvertDateFormat("yyyy-MM-dd", calendar.getTime());
	}

	/**
	 * 得到本月的最后一天
	 * 
	 * @return
	 */
	public static String getCurrentMonthLastDay()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return getConvertDateFormat("yyyy-MM-dd", calendar.getTime());
	}

	/**
	 * 
	 * 获取上个月的第一天
	 * 
	 * @return
	 */
	public static String getBeforeMonthFirstDay()
	{
		Calendar cal = Calendar.getInstance();
		Date date = new Date();
		cal.setTime(date);
		int year = 0;
		int month = cal.get(Calendar.MONTH); // 上个月月份
		int day = cal.getActualMinimum(Calendar.DAY_OF_MONTH);// 起始天数

		if (month == 0)
		{
			year = cal.get(Calendar.YEAR) - 1;
			month = 12;
		} else
		{
			year = cal.get(Calendar.YEAR);
		}
		String endDay = year + "-" + month + "-" + day;
		return endDay;
	}

	/**
	 * 获取上个月的最一天
	 * 
	 * @return
	 */
	public static String getBeforeMonthLastDay()
	{
		// 实例一个日历单例对象
		Calendar cal = Calendar.getInstance();
		Date date = new Date();
		cal.setTime(date);
		int year = 0;
		int month = cal.get(Calendar.MONTH); // 上个月月份
		// int day1 = cal.getActualMinimum(Calendar.DAY_OF_MONTH);//起始天数
		int day = cal.getActualMaximum(Calendar.DAY_OF_MONTH); // 结束天数

		if (month == 0)
		{
			// year = cal.get(Calendar.YEAR) - 1;
			month = 12;
		} else
		{
			year = cal.get(Calendar.YEAR);
		}
		String endDay = year + "-" + month + "-" + day;
		return endDay;
	}

	/**
	 * 
	 * 获取下月的第一天
	 * 
	 * @return
	 */
	public static String getNextMonthFirstDay()
	{
		Calendar cal = Calendar.getInstance();
		Date date = new Date();
		cal.setTime(date);
		int year = 0;
		int month = cal.get(Calendar.MONDAY) + 2; // 下个月月份
		/*
		 * 如果是这样的加一的话代表本月的第一天 int month = cal.get(Calendar.MONDAY)+1; int month
		 * = cal.get(Calendar.MONTH)+1
		 */
		int day = cal.getActualMinimum(Calendar.DAY_OF_MONTH);// 起始天数

		if (month == 0)
		{
			year = cal.get(Calendar.YEAR) - 1;
			month = 12;
		} else
		{
			year = cal.get(Calendar.YEAR);
		}
		String Day = year + "-" + month + "-" + day;
		return Day;
	}

	/**
	 * 获取下个月的最一天
	 * 
	 * @return
	 */
	public static String getNextMonthLastDay()
	{
		// 实例一个日历单例对象
		Calendar cal = Calendar.getInstance();
		Date date = new Date();
		cal.setTime(date);
		int year = 0;
		int month = cal.get(Calendar.MONDAY) + 2; // 下个月份
		// int day1 = cal.getActualMinimum(Calendar.DAY_OF_MONTH);//起始天数
		int day = cal.getActualMaximum(Calendar.DAY_OF_MONTH); // 结束天数

		if (month == 0)
		{
			// year = cal.get(Calendar.YEAR) - 1;
			month = 12;
		} else
		{
			year = cal.get(Calendar.YEAR);
		}
		String endDay = year + "-" + month + "-" + day;
		return endDay;
	}

	/**
	 * 本地时区输出当前日期 GMT时间
	 */
	public static String getLocalDate()
	{
		Date date = new Date();
		return date.toLocaleString();// date.toGMTString();
	}

	/**
	 * 判断客户端输入的是闰年Leap还是平年Average
	 * 
	 * @return
	 */
	public static String getLeapOrAverage(int year)
	{

		if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
		{
			return year + "闰年";
		} else
		{
			return year + "平年";
		}
	}

	/**
	 * 
	 * @return
	 */
	public static String getYYYYMMddHHmmss()
	{
		Calendar calendar = Calendar.getInstance();
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());

	}

	/**
	 * 传入的日期按照指定格式进行格式化
	 * 
	 * @param date
	 * @param format
	 * @return String
	 */
	public static String formatDate(Date date, String format)
	{
		SimpleDateFormat fmt = new SimpleDateFormat(format);
		return fmt.format(date);
	}

	/**
	 * 格式化 yyyy-MM-dd（年-月-日）
	 * 
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static String formatYmdDate(Date date)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return date==null?"":sdf.format(date);
	}
	/**
	 * 返回当前时间
	 * 
	 * @param pattern
	 * @return
	 */
	public static String textForNow(String pattern)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(Calendar.getInstance().getTime());
	}

	/**
	 * 返回当前时间
	 * 
	 * @param pattern
	 * @return
	 */
	public static String textForNow(String pattern, Date date)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	/**
	 * @author BHF
	 * @param pattern
	 *            如期格式化样式
	 * @param date
	 *            日期对象
	 * @return
	 */
	public static String textPrevDate(String pattern, Date date)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONDAY, calendar.get(Calendar.MONDAY) - 1);
		return sdf.format(calendar.getTime());
	}

 

	public static String textForDate(String pattern, String date)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		String format = null;
		try
		{
			format = dateFormat.format(dateFormat.parse(date));
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
		return format;
	}

	/**
	 * 数据库时间类型,数据库显示时、分、秒
	 * 
	 * @param date
	 *            时间类型
	 * @return 返回Timestamp时间类型
	 */
	public static Timestamp getTimestampDate(Date date)
	{
		return new Timestamp(date.getTime());
	}

	/**
	 * 获取当前时间
	 * 
	 * @author LiuQing
	 * @return String
	 */
	public static String textForNow()
	{
		String time = textForNow("yyyy-MM-dd HH:mm:ss");
		String cTime = time.substring(0, 19);
		return cTime;
	}

	/**
	 * 获取当前时间
	 * 
	 * @author LiuQing
	 * @return String
	 */
	public static String subStrTime(Date time)
	{
		String date = textForNow("yyyy-MM-dd HH:mm:ss", time);
		String cTime = date.substring(0, 19);
		return cTime;
	}

	/**
	 * 转换字符串格式为Timestamp时间格式
	 * 
	 * @param dateStr
	 *            时间字符串格式
	 * @return Timestamp时间类型
	 */
	public static Timestamp parseStrDate(String dateType, String dateStr)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat(dateType);
		Timestamp date = null;
		try
		{
			date = getTimestampDate(dateFormat.parse(dateStr));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 日期相加减,接收要操作的年数
	 * 
	 * @param day
	 *            操作天数
	 * @return 时间类型
	 */
	public static Date calendarAdd(int year)
	{
		Calendar cal = Calendar.getInstance();// 获取该实例
		cal.add(Calendar.YEAR, year); // 从当前的年数中减去/加传入的年数
		return cal.getTime();// 获取减去/加去后的结果
	}

	public static Date parseToDate(String dateStr, String dateType)
	{

		SimpleDateFormat inSdf = new SimpleDateFormat(dateType);

		Date dateS = new Date();
		try
		{
			dateS = inSdf.parse(dateStr);

		} catch (ParseException e)
		{
			e.printStackTrace();
		}
		return dateS;
	}

	public static Date addDate(Date dateNow, int days)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(dateNow);

		c.add(Calendar.DATE, days);
		return c.getTime();
	}

	public static Date addMonth(Date dateNow, int month)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(dateNow);
		c.add(Calendar.MONTH, month);
		return c.getTime();
	}

	/**
	 * 比较两个日期的大小 date1和date2格式:yyyy-mm-dd hh:mi Jul 6, 2009 9:29:37 AM tian
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean compareTime(String date1, String date2)
	{
		boolean flag = false;
		if (covertTimeToLong(date1) < covertTimeToLong(date2))
		{
			flag = false;
		} else if (covertTimeToLong(date1) >= covertTimeToLong(date2))
		{
			flag = true;
		}
		return flag;
	}

	/**
	 * 将字符串日期2009-7-3 01:01转换成long型毫秒数 Jul 3, 2009 2:52:32 PM tian
	 * 
	 * @param time
	 * @return
	 */
	/**
	 * 两个GregorianCalendar的构造函数可以用来处理时间。前者创建一个表示日期，小时和分钟的对象：
	 * 
	 * GregorianCalendar(int year, int month, int date, int hour, int minute)
	 * 第二个创建一个表示一个日期，小时，分钟和秒：
	 * 
	 * GregorianCalendar(int year, int month, int date, int hour, int minute,
	 * int second)
	 */

	public static long covertTimeToLong(String time)
	{
		long ll = 0l;
		int yy = 0;
		int mm = 0;
		int dd = 0;
		int hh = 0;
		int mi = 0;
		if (time != null && !"".equals(time))
		{// 可以根据自己的参数进行判断控制
			yy = Integer.parseInt(time.substring(0, 4));
			mm = Integer.parseInt(time.substring(5, time.lastIndexOf("-")));
			dd = Integer.parseInt(time.substring(time.lastIndexOf("-") + 1, time.length() - 6));
			hh = Integer.parseInt(time.substring(time.length() - 5, time.indexOf(":")));
			mi = Integer.parseInt(time.substring(time.length() - 2));
			GregorianCalendar gc = new GregorianCalendar(yy, mm, dd, hh, mi);
			Date d = gc.getTime();
			ll = d.getTime();
		} else
		{
			ll = Long.MAX_VALUE;
		}
		return ll;
	}

	

	/**
	 * 测试
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws Exception
	{

		try
		{
			System.out.println(new DateUtils().formatYmdDate(new Date()));
		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}
    /**
     * 年月日
     * @return
     */
	public static String dateToFolder(){
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
		return fmt.format(new Date());
	}

	public static String dateToFileName() {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return fmt.format(new Date());
	}
	
	/**
	 * yyyy-MM-dd HH:mm 格式的字符串转为Date
	 * @author sjl
	 * @param str  yyyy-MM-dd HH:mm 格式的字符串
	 */
	public static Date StringToDate(String str){
		Date date = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if (StringUtils.isNotBlank(str)) {
			try {
				date = formatter.parse(str);
			} catch (ParseException e) {
				e.printStackTrace();
			}	
		}
		return date;
		
	}
	/**
	 * yyyy-MM-dd 格式的字符串转为Date
	 * @author sjl
	 * @param str  yyyy-MM-dd  格式的字符串
	 */
	public static Date StringToDateyyyy_MM_dd(String str){
		Date date = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		if (StringUtils.isNotBlank(str)) {
			try {
				date = formatter.parse(str);
			} catch (ParseException e) {
				e.printStackTrace();
			}	
		}
		return date;
		
	}
	
}