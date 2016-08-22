package com.cai310.utils;

/**
 * chess-日期类
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


public class DateUtil {

	/**
	 * 返回当前时间长整型
	 * 
	 * @return
	 */
	public static long getLongTime() {
		return System.currentTimeMillis();
	}

	/**
	 * 返回当前时间字符型
	 * 
	 * @return
	 */
	public static String getLongDate() {
		long d = System.currentTimeMillis();
		return String.valueOf(d);
	}

	/**
	 * 格式化日期,格式化后可直接insert into [DB]
	 * 
	 * @return
	 * 
	 */
	public static String dateToStr(Date date) {

		if (date == null)
			return "";
		else {
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
			String str_Date = sdFormat.format(date);
			return str_Date;
		}
	}
	
	public static String dateToStrYyyyMMdd(Date date) {
		if (date == null)
			return "";
		else {
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
			String str_Date = sdFormat.format(date);
			return str_Date;
		}
	}

	public static String dateToStrMMdd(Date date) {
		if (date == null)
			return "";
		else {
			SimpleDateFormat sdFormat = new SimpleDateFormat("MMdd", Locale.getDefault());
			String str_Date = sdFormat.format(date);
			return str_Date;
		}
	}

	public static String dateToStrHHmmss(Date date) {
		if (date == null)
			return "";
		else {
			SimpleDateFormat sdFormat = new SimpleDateFormat("HHmmss", Locale.getDefault());
			String str_Date = sdFormat.format(date);
			return str_Date;
		}
	}
	
	public static String dateToStr(Date date, String format) {

		if (date == null)
			return "";
		else {
			SimpleDateFormat sdFormat = new SimpleDateFormat(format, Locale.getDefault());
			String str_Date = sdFormat.format(date);
			return str_Date;
		}
	}

	/**
	 * 反向格式化日期
	 * 
	 * @return
	 * 
	 */
	public static Date strToDate(String str) {
		if (str == null)
			return null;
		// DateFormat defaultDate = DateFormat.getDateInstance();
		// 细化日期的格式
		SimpleDateFormat defaultDate = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		Date date = null;
		try {
			date = defaultDate.parse(str);
		} catch (ParseException pe) {
		}
		return date;
	}

	/**
	 * 反向格式化日期
	 * 
	 * @param str 要格式化字符串
	 * @param formatStr 字符串的日期格式
	 * @return
	 */
	public static Date strToDate(String str, String formatStr) {
		try {
			if (str == null)
				return null;
			if (formatStr == null) {
				formatStr = "yyyy-MM-dd hh:mm";
			}
	
			SimpleDateFormat defaultDate = new SimpleDateFormat(formatStr);
	
			Date date = null;
			try {
				date = defaultDate.parse(str);
			} catch (ParseException pe) {
			}
			return date;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 日期计算
	 * 
	 * @param date 起始日期
	 * @param yearNum 年增减数
	 * @param monthNum 月增减数
	 * @param dateNum 日增减数
	 */
	public static String calDate(String date, int yearNum, int monthNum, int dateNum) {
		String result = "";
		try {
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			cal.setTime(sd.parse(date));
			cal.add(Calendar.MONTH, monthNum);
			cal.add(Calendar.YEAR, yearNum);
			cal.add(Calendar.DATE, dateNum);
			result = sd.format(cal.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static Date calDate(Date date, int yearNum, int monthNum, int dateNum) {
		Date result = null;
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.MONTH, monthNum);
			cal.add(Calendar.YEAR, yearNum);
			cal.add(Calendar.DATE, dateNum);
			result = cal.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 日期计算
	 * 
	 * @param date 起始日期
	 * @param yearNum 年增减数
	 * @param monthNum 月增减数
	 * @param dateNum 日增减数
	 * @param hourNum 小时增减数
	 * @param minuteNum 分钟增减数
	 * @param secondNum 秒增减数
	 * @return
	 */
	public static Date calDate(Date date, int yearNum, int monthNum, int dateNum, int hourNum, int minuteNum,
			int secondNum) {
		Date result = null;
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.MONTH, monthNum);
			cal.add(Calendar.YEAR, yearNum);
			cal.add(Calendar.DATE, dateNum);
			cal.add(Calendar.HOUR_OF_DAY, hourNum);
			cal.add(Calendar.MINUTE, minuteNum);
			cal.add(Calendar.SECOND, secondNum);
			result = cal.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 返回当前时间，格式'yyyy-mm-dd HH:mm:ss' 可为插入当前时间
	 * 
	 * @return
	 */
	public static String getLocalDate() {
		java.util.Date dt = new java.util.Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		df.setTimeZone(TimeZone.getDefault());
		return df.format(dt);
	}

	public static String getLocalDate(String f) {
		java.util.Date dt = new java.util.Date();
		SimpleDateFormat df = new SimpleDateFormat(f);
		df.setTimeZone(TimeZone.getDefault());
		return df.format(dt);
	}

	/**
	 * 返回当前时间，格式'yyyyMMddHHmmss' 可为插入当前时间
	 * 
	 * @return
	 */
	public static String getSimpleDate() {
		//
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		Date dt = new Date();
		return df.format(dt);
	}

	/**
	 * 返回当前时间，格式'f' 可为插入当前时间
	 * 
	 * @return
	 */
	public static String getFormatDate(String f) {
		//
		SimpleDateFormat df = new SimpleDateFormat(f);
		Date dt = new Date();
		return df.format(dt);
	}

	public static String getFormatDate(String f, Date dt) {
		//
		SimpleDateFormat df = new SimpleDateFormat(f);
		return df.format(dt);

	}
	
	/**
	 * 得到当天凌晨的6点，包括明天凌晨6点前的
	 * 
	 * @param orlTime
	 * @return
	 */
	public static String getTodaySixStr() {
		Date date = new Date();
		Date toDaySix = strToDate(dateToStr(date,"yyyy-MM-dd")+" 06:00");
		if(date.getTime()-toDaySix.getTime()>0){
			////大于6点
			return dateToStr(date,"yyyy-MM-dd");
		}else{
			return dateToStr(calDate(date,0,0,-1),"yyyy-MM-dd");
		}
	}
	/**
	 * 得到当天日期和之前日期列表
	 * 
	 * @param orlTime
	 * @return
	 */
	public static List<String> getSixList() {
		Date date = new Date();
		Date toDaySix = strToDate(dateToStr(date,"yyyy-MM-dd")+" 06:00");
		Date daySix = null;
		if(date.getTime()-toDaySix.getTime()>0){
			////大于6点
			daySix  = date;
		}else{
			daySix = calDate(date,0,0,-1);
		}
		List<String> l= new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			l.add(dateToStr(calDate(daySix,0,0,-i),"yyyy-MM-dd"));
		}
		return l;
	}
	/**
	 * 得到当天凌晨的6点
	 * 
	 * @param orlTime
	 * @return
	 */
	public static Date getTodaySixDate(String dateStr) {
		Date toDaySix = strToDate(dateStr+" 06:00");
		return toDaySix;
	}
	/**
	 * 得到明天凌晨的6点
	 * 
	 * @param todaysix 今天6点
	 * @return
	 */
	public static Date getNextdaySixDate(Date todaysix) {
		return calDate(todaysix,0,0,1);
	}
	/**
	 * 格式化日期为“2004年9月13日”
	 * 
	 * @param orlTime
	 * @return
	 */
	public static String parseCnDate(String orlTime) {
		if (orlTime == null || orlTime.length() <= 0) {
			return "";
		}

		if (orlTime.length() < 10) {
			return "";
		}
		String sYear = orlTime.substring(0, 4);
		String sMonth = delFrontZero(orlTime.substring(5, 7));
		String sDay = delFrontZero(orlTime.substring(8, 10));
		return sYear + "年" + sMonth + "月" + sDay + "日";
	}
    
	/**
	 * 格式化日期为"9.13"
	 * 
	 * @param orlTime
	 * @return
	 */
	public static String parsePointDate(String orlTime) {
		if (orlTime == null || orlTime.length() <= 0) {
			return "";
		}
		String sMonth = delFrontZero(orlTime.substring(5, 7));
		String sDay = delFrontZero(orlTime.substring(8, 10));
		return sMonth + "." + sDay;
	}

	/**
	 * 取整函数
	 * 
	 * @param mord
	 * @return
	 */
	public static String delFrontZero(String mord) {
		int im = -1;
		try {
			im = Integer.parseInt(mord);
			return String.valueOf(im);
		} catch (Exception e) {
			return mord;
		}
	}

	public static String getWeekStr(Date time) {
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		return getWeekStr(c);
	}

	public static String getWeekStr(Calendar c) {
		int a = c.get(Calendar.DAY_OF_WEEK);
		switch (a) {
		case 1:
			return "星期日";
		case 2:
			return "星期一";
		case 3:
			return "星期二";
		case 4:
			return "星期三";
		case 5:
			return "星期四";
		case 6:
			return "星期五";
		case 7:
			return "星期六";
		default:
			return null;
		}
	}
	public static Integer getWeek(Date time) {
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		return c.get(Calendar.WEEK_OF_YEAR);
	}
	public static Integer getYear(Date time) {
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		return c.get(Calendar.YEAR);
	}
	public static Integer getMonth(Date time) {
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		return c.get(Calendar.MONTH)+1;
	}
	public static Integer getQuarter(Date time) {
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		int month=c.get(Calendar.MONTH);
		if(month<3){
			return 1;
		}else if(month>=3&&month<6){
			return 2;
		}else if(month>=6&&month<9){
			return 3;
		}else if(month>=9&&month<12){
			return 4;
		}else{
			return null;
		}
	}	
	
	public static Date getdecDateOfMinute(Date time,int minute) { 
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		c.add(Calendar.MINUTE, - minute);
		return c.getTime();
	}
	
	public static Date getdecDateOfDate(Date time,int date) { 
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		c.add(Calendar.DATE, - date);
		return c.getTime();
	}
	
	
	/**
	 * 构造函数
	 */
	private DateUtil() {
	}
	public static String getStringMonday(Date date){  
        Calendar c = Calendar.getInstance();  
        c.setTime(date);  
        c.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);  
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.HOUR, 23);
		c.set(Calendar.SECOND, 59);
        return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());  
      }
	
	public static Date getTodayDate(Date date){  
        Calendar c = Calendar.getInstance();  
        c.setTime(date);  
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
        return c.getTime();  
      }
	
	public static Date getYesterDate(Date date){  
        Calendar c = Calendar.getInstance(); 
        c.setTime(date);
		c.set(Calendar.DATE,c.get(Calendar.DATE)-1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
        return c.getTime();  
      }
	
	//获取本周 第一天
	public static Date getFirstDateOfWeek(Date date){  
		
        Calendar c = Calendar.getInstance(); 
        c.setFirstDayOfWeek(Calendar.MONDAY);

        c.setTime(date);  
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		
        return c.getTime();  
      }
	
	public static Date getMonthFirstDate(Date date){  
        Calendar c = Calendar.getInstance();  
        c.setTime(date);  
        c.set(Calendar.DAY_OF_MONTH,1);  
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
        return c.getTime();  
      }
	
	
	public static void main(String[] args){ 
		System.out.println(DateUtil.dateToStr(getTodayDate(new Date()),"yyyyMMdd"));

		System.out.println(DateUtil.dateToStr(getYesterDate(new Date()),"yyyyMMdd"));
		System.out.println(DateUtil.dateToStr(DateUtil.getdecDateOfDate(new Date(),7),"yyyy-MM-dd HH:mm:ss"));
	}
	
}
