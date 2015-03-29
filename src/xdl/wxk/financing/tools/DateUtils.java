package xdl.wxk.financing.tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期工具. 取得特定日期月份的第一天
 * 
 * @author wangxukun
 * 
 */
public class DateUtils {
	/**
	 * 取得特定日期月份的第一天对应的日期
	 * @param day	特定日期
	 * @return 特定日期所在月份的第一天
	 */
	public static String getFirstDay(Date day) {
		Calendar calendar =  Calendar.getInstance(Locale.CHINA);
		calendar.setTime(day);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String theDay = df.format(calendar.getTime());
		return theDay;
	}
	/**
	 * 取得特定日期所属年度的
	 * @param day 特定日期
	 * @return 特定年度
	 */
	public static String getYear(Date day){
		return  getFirstDay(day).substring(0, 4);
	}
	/**
	 * 取得特定日期所属年度的第一天对应的日期
	 * @param day 特定日期
	 * @return 当年的第一天对应的日期（字符串格式）
	 */
	public static String getYearFirstDayOfString(Date day){
		Calendar calendar =  Calendar.getInstance(Locale.CHINA);
		calendar.setTime(day);
		calendar.set(Calendar.DAY_OF_YEAR,1);
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String theDay = df.format(calendar.getTime());
		return theDay;
	}
	
	/**
	 * 取得特定日期所属年度的第一天对应的日期
	 * @param day 特定日期
	 * @return 当年的第一天对应的日期（Date格式）
	 */
	public static Date getYearFirstDayOfDate(Date day){
		Calendar calendar =  Calendar.getInstance(Locale.CHINA);
		calendar.setTime(day);
		calendar.set(Calendar.DAY_OF_YEAR,1);
		
		Date theDay = calendar.getTime(); 
		return theDay;
	}
}
