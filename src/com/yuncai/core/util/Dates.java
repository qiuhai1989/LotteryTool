package com.yuncai.core.util;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 时间工具类
 * @author ysh
 *
 */
public class Dates {
	public static final String SIMPLE_DATE_FORMAT="yyyy-MM-dd HH:mm:ss";
	
	public static final String SIMPLE_TIME_FORMAT="HH:mm:ss";
	
	public static Date format(String dateText){
		return format(dateText,SIMPLE_DATE_FORMAT);
	}
	public static String format(Date date){
		
		if(date==null){
			return "";
		}
		
		return format(date,SIMPLE_DATE_FORMAT);
	}
	public static Date format(String dateText, String formatText) {
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(formatText);
			date = sdf.parse(dateText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static String format(Date date, String formatText) {
		String dates = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(formatText);
			dates = sdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dates;
	}

}
