package com.yuncai.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * 公共类
 * @author TYH
 *
 */
public class DBHelper {

	/***
	 * 判断是否为空
	 * @param obj
	 * @return
	 */
	public static boolean isNoNull(Object obj){
		boolean result = false;
		if(null != obj && !"".equals(obj)){
			result = true;
		}
		return result;
	}
	
	/***
	 * 全部 是  否集合
	 * @return
	 */
	public static List<Map<String, Object>> getIsOrNot(){
		
		String[] names ={"全部","是","否"};
		int[] values = {-1,1,0};
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
		for(int i = 0; i< names.length; i++){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("value", values[i]);
			map.put("name", names[i]);
			
			list.add(map);
		}
		return list;
	}
	
	/***
	 * 用时间生成的唯一标识，可用于更新标识
	 * @return String
	 */
	public static String updCode(){
		String rand=String.valueOf(System.currentTimeMillis());//new Date().getTime());
		return rand;
	}
	
	/***
	 * 获取当前时间
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String getNow(){
		SimpleDateFormat sDateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
		String time=sDateFormat.format(new java.util.Date()); //当前时间
		return formatTime(time);
	}
	
	/***
	 * 时间格式转换
	 * @param str
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String formatTime(String str){
		String time=str;
		if(DBHelper.isNoNull(str)){
			if(!"".equals(str) && str.length() >= 19){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		        try {
					Date d=sdf.parse(str);
					time=sdf.format(d);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		return time;
	}
	
	/**
	 * 获取当月第一天
	 * 
	 * @return
	 */
	public static String getFirstDayOfMonth() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		str = sdf.format(lastDate.getTime());
		return str;
	}
	
	/***
	 * 当前时间间隔时间
	 * @param day 前几天 -day;后几天+day
	 * @return
	 * @throws ParseException
	 */
	public static String getDate(int day){
		Calendar now =Calendar.getInstance();     
		now.set(Calendar.DATE,now.get(Calendar.DATE)+day);
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = sDateFormat.format(now.getTime());
		return date;
	}
}
