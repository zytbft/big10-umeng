package com.oldboy.umeng.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期工具类
 */
public class DateUtil {

	/**
	 * 对时间串进行格式化,格成毫秒数
	 * 24/May/2018:18:10:59 -0700" ;
	 */
	public static long formatDateStr(String dateStr) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z", Locale.US);
			return sdf.parse(dateStr).getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1 ;
	}

	public static long dayBegin(Date d , int offset ){
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(d);

			//对天进行滚动
			c.add(Calendar.DAY_OF_MONTH , offset);
			//取出天的起始时刻
			Date lastDate = c.getTime() ;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd 00:00:00") ;
			String dateStr = sdf.format(lastDate) ;
			return sdf.parse(dateStr).getTime() ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1 ;
	}

	public static long weekBegin(Date d , int offset ){
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(d);

			//得到天位于周的位置
			int dayTh = c.get(Calendar.DAY_OF_WEEK) ;
			c.add(Calendar.DAY_OF_MONTH , -(dayTh - 1));
			c.add(Calendar.DAY_OF_MONTH , offset * 7);

			//取出天的起始时刻
			Date lastDate = c.getTime() ;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd 00:00:00") ;
			String dateStr = sdf.format(lastDate) ;
			return sdf.parse(dateStr).getTime() ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1 ;
	}

	//月起始
	public static long monthBegin(Date d , int offset ){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/01 00:00:00") ;
			String yyyymm0d = sdf.format(d) ;
			Date newDate = sdf.parse(yyyymm0d) ;

			//
			Calendar c = Calendar.getInstance();
			c.setTime(newDate);
			c.add(Calendar.MONTH , offset);
			return c.getTime().getTime() ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1 ;
	}

	public static String dateFormat(long ms){
		Date d = new Date(ms) ;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss") ;
		return sdf.format(d) ;
	}

}
