package com.yuncai.core.sporttery;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.yuncai.core.tools.DateTools;
import com.yuncai.core.tools.LogUtil;

public class TimeTools {
	// 竞彩足球计算赛事截止时间
	public static Date getFbEndSaleTime(Date matchTime, int aheadMilli) {// 计算实际的截止时间
		Calendar c = Calendar.getInstance();
		c.setTime(matchTime);
		// 计算提前量之后的时间
		c.add(Calendar.MILLISECOND, -aheadMilli);
		Date realEndTime = c.getTime();

		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);

		// 如果截止小于9点，并在周日或周六早上，要调整到1:00截止，其它时间调整到前一天23点截止
		// 如果截止时间大于23:00点，周六或周日不做处理，其它日期要调整到23点截止
		int dayMilli=10*60*1000;
		if (hour < 9) {
			if (dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.MONDAY) {
				if ((hour > 0 || (hour == 0 && minute >= 60 - aheadMilli / 1000 / 60))) {
					// 截止时间>=（1点 - 提前量）
					c.set(Calendar.HOUR_OF_DAY, 1);
					c.set(Calendar.MINUTE, 0);
					c.set(Calendar.SECOND, 0);
					
					c.add(Calendar.MILLISECOND, -dayMilli);
					//System.out.println(DateTools.dateToString(c.getTime(), "yyyy-MM-dd HH:mm:ss"));
					realEndTime = c.getTime();
					
				} else {
					// 时间<（1点 - 提前量） ，保持原样(按比赛时间减提前量计算)
					//return realEndTime;
				}
			} else {
				dayMilli=dayMilli-1000;
				c.add(Calendar.DAY_OF_MONTH, -1);
				c.set(Calendar.HOUR_OF_DAY, 23);
				c.set(Calendar.MINUTE, 59);
				c.set(Calendar.SECOND, 59);
				c.add(Calendar.MILLISECOND, -dayMilli);
				realEndTime = c.getTime();
			}
			
		} else if (hour >= 24 || (hour >= 23 && minute >= 60 - aheadMilli / 1000 / 60)) {

			if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
				// do nothing!!!
				//return realEndTime;
			} else {
				dayMilli=dayMilli-1000;
				c.set(Calendar.HOUR_OF_DAY, 23);
				c.set(Calendar.MINUTE, 59);
				c.set(Calendar.SECOND, 59);
				realEndTime = c.getTime();
				c.add(Calendar.MILLISECOND, -aheadMilli);
				realEndTime = c.getTime();

			}

		}

		//春节特殊处理 休市时间为2014年1月30日0:00至2月6日24:00。　　
		//如果截止时间在 1-21 22:52  到 1-29 0:00之间的，调整到1-21 22:52截止
		Date start_ = DateTools.StringToDate("2014-01-29 23:50:00") ;
		Date end_ = DateTools.StringToDate("2014-02-07 00:00:00") ;
		if(realEndTime.getTime() > start_.getTime() && realEndTime.getTime() <end_.getTime() ){
			realEndTime = start_;
		}
		//欧洲杯比赛日特殊处理
		//如果截止时间在 06-09 00:00  到 07-02 02:45之间的，且有欧洲杯赛事顺延到 次日02:45截止 
		//06-21,06-26,06-27,06-30,07-1凌晨00:00至凌晨02:45的比赛不延迟(该日凌晨无欧洲杯赛事)
		Date euroCupStart = DateTools.StringToDate("2014-06-13 23:59:00") ;
		Date euroCupEnd = DateTools.StringToDate("2014-07-14 09:16:00") ;
		Map<Integer,Integer> unHandleDays9 = new HashMap<Integer,Integer>();
		unHandleDays9.put(615, 15);
		Map<Integer,Integer> unHandleDays4 = new HashMap<Integer,Integer>();
		unHandleDays4.put(624, 24);
		unHandleDays4.put(625, 25);
		unHandleDays4.put(626, 26);
		unHandleDays4.put(627, 27);
		unHandleDays4.put(629, 29);
		unHandleDays4.put(630, 30);
		unHandleDays4.put(71, 1);
		unHandleDays4.put(72, 2);
		unHandleDays4.put(75, 5);
		unHandleDays4.put(76, 6);
		unHandleDays4.put(79, 9);
		unHandleDays4.put(710, 10);
		unHandleDays4.put(713, 13);
		Map<Integer,Integer> unHandleDays6 = new HashMap<Integer,Integer>();
		unHandleDays6.put(614, 14);
		unHandleDays6.put(616, 16);
		unHandleDays6.put(617, 17);
		unHandleDays6.put(618, 18);
		unHandleDays6.put(619, 19);
		unHandleDays6.put(620, 20);
		unHandleDays6.put(621, 21);
		unHandleDays6.put(622, 22);
		unHandleDays6.put(623, 23);
		Map<Integer,Integer> unHandleDays3 = new HashMap<Integer,Integer>();
		unHandleDays3.put(714, 14);
		Map<Integer,Integer> unHandleDays0 = new HashMap<Integer,Integer>();
		unHandleDays0.put(628, 28);
		unHandleDays0.put(73, 3);
		unHandleDays0.put(74, 4);
		unHandleDays0.put(77, 7);
		unHandleDays0.put(78, 8);
		unHandleDays0.put(711, 11);
		unHandleDays0.put(712, 12);
		c.setTime(matchTime);
		if(matchTime.getTime()> euroCupStart.getTime() && matchTime.getTime() <euroCupEnd.getTime() ){
			int month=c.get(Calendar.MONTH) + 1;
			int day=c.get(Calendar.DAY_OF_MONTH);
			int key=Integer.parseInt(month+""+day);
			int hh=c.get(Calendar.HOUR_OF_DAY);
			minute = c.get(Calendar.MINUTE);
			if(unHandleDays9.get(key)!=null ){
				if(hh<9 || (hh >=14 && minute>15)){
					c.add(Calendar.MILLISECOND, -aheadMilli);
					realEndTime = c.getTime();
				}else if (hh>=9 || hh<14){
					aheadMilli=aheadMilli-1000;
					c.set(Calendar.HOUR_OF_DAY,8);
					c.set(Calendar.MINUTE, 59);
					c.set(Calendar.SECOND, 59);
					c.add(Calendar.MILLISECOND, -aheadMilli);
					realEndTime = c.getTime();
				}
			}else if(unHandleDays4.get(key)!=null){
				if(hh<4 || (hh >=9 && minute>15)){
					c.add(Calendar.MILLISECOND, -aheadMilli);
					realEndTime = c.getTime();
				}else if (hh>=4 || hh<9){
					aheadMilli=aheadMilli-1000;
					c.set(Calendar.HOUR_OF_DAY,3);
					c.set(Calendar.MINUTE, 59);
					c.set(Calendar.SECOND, 59);
					c.add(Calendar.MILLISECOND, -aheadMilli);
					realEndTime = c.getTime();
				}
			}
			else if(unHandleDays6.get(key)!=null){
				if(hh<6 || (hh >=11 && minute>15)){
					c.add(Calendar.MILLISECOND, -aheadMilli);
					realEndTime = c.getTime();
				}else if (hh>=6 || hh<11){
					aheadMilli=aheadMilli-1000;
					c.set(Calendar.HOUR_OF_DAY,5);
					c.set(Calendar.MINUTE, 59);
					c.set(Calendar.SECOND, 59);
					c.add(Calendar.MILLISECOND, -aheadMilli);
					realEndTime = c.getTime();
				}
			}
			else if(unHandleDays3.get(key)!=null){
				if(hh<3 || (hh >=9 && minute>15)){
					c.add(Calendar.MILLISECOND, -aheadMilli);
					realEndTime = c.getTime();
				}else if (hh>=3 || hh<9){
					aheadMilli=aheadMilli-1000;
					c.set(Calendar.HOUR_OF_DAY,2);
					c.set(Calendar.MINUTE, 59);
					c.set(Calendar.SECOND, 59);
					c.add(Calendar.MILLISECOND, -aheadMilli);
					realEndTime = c.getTime();
				}
			}
			else if(unHandleDays0.get(key)!=null){
				if (hh < 9 || (hh==9 && minute<15)) {
					if ((hh > 0 || (hh == 0 && minute >= 60 - aheadMilli / 1000 / 60))) {
						aheadMilli=aheadMilli-1000;
						c.add(Calendar.DAY_OF_MONTH, -1);
						c.set(Calendar.HOUR_OF_DAY, 23);
						c.set(Calendar.MINUTE, 59);
						c.set(Calendar.SECOND, 59);
						realEndTime = c.getTime();
						c.add(Calendar.MILLISECOND, -aheadMilli);
						realEndTime = c.getTime();
					}else{
						c.add(Calendar.MILLISECOND, -aheadMilli);
						realEndTime = c.getTime();
					}
				}else if (hh >= 24 || (hh >= 23 && minute >= 60 - aheadMilli / 1000 / 60)) {
						aheadMilli=aheadMilli-1000;
						c.set(Calendar.HOUR_OF_DAY, 23);
						c.set(Calendar.MINUTE, 59);
						c.set(Calendar.SECOND, 59);
						realEndTime = c.getTime();
						c.add(Calendar.MILLISECOND, -aheadMilli);
						realEndTime = c.getTime();

					}else{
						c.add(Calendar.MILLISECOND, -aheadMilli);
						realEndTime = c.getTime();
					}

			}
			
		}
		return realEndTime;
	}
	
	
	public static Date getFbEndSaleTime_ba(Date matchTime, int aheadMilli) {// 计算实际的截止时间
		Calendar c = Calendar.getInstance();
		c.setTime(matchTime);
		// 计算提前量之后的时间
		c.add(Calendar.MILLISECOND, -aheadMilli);
		Date realEndTime = c.getTime();

		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);

		// 如果截止小于9点，并在周日或周六早上，要调整到1:00截止，其它时间调整到前一天23点截止
		// 如果截止时间大于23:00点，周六或周日不做处理，其它日期要调整到23点截止
		int dayMilli=10*60*1000;
		if (hour < 9) {
			if (dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.MONDAY) {
				if ((hour > 0 || (hour == 0 && minute >= 60 - aheadMilli / 1000 / 60))) {
					// 截止时间>=（1点 - 提前量）
					c.set(Calendar.HOUR_OF_DAY, 1);
					c.set(Calendar.MINUTE, 0);
					c.set(Calendar.SECOND, 0);
					
					c.add(Calendar.MILLISECOND, -dayMilli);
					//System.out.println(DateTools.dateToString(c.getTime(), "yyyy-MM-dd HH:mm:ss"));
					realEndTime = c.getTime();
					
				} else {
					// 时间<（1点 - 提前量） ，保持原样(按比赛时间减提前量计算)
					//return realEndTime;
				}
			} else {
				dayMilli=dayMilli-1000;
				c.add(Calendar.DAY_OF_MONTH, -1);
				c.set(Calendar.HOUR_OF_DAY, 23);
				c.set(Calendar.MINUTE, 59);
				c.set(Calendar.SECOND, 59);
				c.add(Calendar.MILLISECOND, -dayMilli);
				realEndTime = c.getTime();
			}
			
		} else if (hour >= 24 || (hour >= 23 && minute >= 60 - aheadMilli / 1000 / 60)) {

			if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
				// do nothing!!!
				//return realEndTime;
			} else {
				dayMilli=dayMilli-1000;
				c.set(Calendar.HOUR_OF_DAY, 23);
				c.set(Calendar.MINUTE, 59);
				c.set(Calendar.SECOND, 59);
				realEndTime = c.getTime();
				c.add(Calendar.MILLISECOND, -aheadMilli);
				realEndTime = c.getTime();

			}

		}

		//春节特殊处理 休市时间为2014年1月30日0:00至2月6日24:00。　　
		//如果截止时间在 1-21 22:52  到 1-29 0:00之间的，调整到1-21 22:52截止
		Date start_ = DateTools.StringToDate("2014-01-29 23:50:00") ;
		Date end_ = DateTools.StringToDate("2014-02-07 00:00:00") ;
		if(realEndTime.getTime() > start_.getTime() && realEndTime.getTime() <end_.getTime() ){
			realEndTime = start_;
		}
		//欧洲杯比赛日特殊处理
		//如果截止时间在 06-09 00:00  到 07-02 02:45之间的，且有欧洲杯赛事顺延到 次日02:45截止 
		//06-21,06-26,06-27,06-30,07-1凌晨00:00至凌晨02:45的比赛不延迟(该日凌晨无欧洲杯赛事)
		Date euroCupStart = DateTools.StringToDate("2012-06-08 23:59:00") ;
		Date euroCupEnd = DateTools.StringToDate("2012-07-02 09:00:00") ;
		Map<Integer,Integer> unHandleDays = new HashMap<Integer,Integer>();
		unHandleDays.put(21,21);
		unHandleDays.put(26,26);
		unHandleDays.put(27,27);
		unHandleDays.put(30,30);
		unHandleDays.put(1,1);
		c.setTime(matchTime);
		if(matchTime.getTime()> euroCupStart.getTime() && matchTime.getTime() <euroCupEnd.getTime() ){
			if((c.get(Calendar.HOUR_OF_DAY)<2||(c.get(Calendar.HOUR_OF_DAY)==2&&c.get(Calendar.MINUTE)<=45))){
				if(unHandleDays.get(c.get(Calendar.DAY_OF_MONTH))==null){
					c.add(Calendar.MILLISECOND, -aheadMilli);
					realEndTime = c.getTime();
				}
			}else if(c.get(Calendar.HOUR_OF_DAY)<9||(c.get(Calendar.HOUR_OF_DAY)==9&&c.get(Calendar.MINUTE)<8)){
				if(unHandleDays.get(c.get(Calendar.DAY_OF_MONTH))==null){
					c.set(Calendar.HOUR_OF_DAY,2);
					c.set(Calendar.MINUTE, 45);
					c.set(Calendar.SECOND, 0);
					c.add(Calendar.MILLISECOND, -aheadMilli);
					realEndTime = c.getTime();
				}
			}
		}
		return realEndTime;
	}

	// 竞彩篮球球计算赛事截止时间
	public static Date getBbEndSaleTime(Date matchTime, int aheadMilli) {// 计算实际的截止时间
		Calendar c = Calendar.getInstance();
		c.setTime(matchTime);
		// 计算提前量之后的时间
		c.add(Calendar.MILLISECOND, -aheadMilli);
		Date realEndTime = c.getTime();

		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);

		// 周3，周4早上是7：30截止
		// 如果截止小于9点，并在周日或周一早上，要调整到1:00截止，，其它时间调整到前一天23点截止
		// 如果截止时间大于23:00点，周六或周日不做处理，其它日期要调整到23点截止
		int dayMilli=12*60*1000;
		if (hour < 9) {
			if (dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.MONDAY) {// 周日、周1
				if ((hour > 0 || (hour == 0 && minute >= 60 - aheadMilli / 1000 / 60))) {
					// 截止时间>=（1点 - 提前量）
					c.set(Calendar.HOUR_OF_DAY, 1);
					c.set(Calendar.MINUTE, 0);
					c.set(Calendar.SECOND, 0);

					c.add(Calendar.MILLISECOND, -dayMilli);
					realEndTime = c.getTime();
					
				} else {
					// 时间<（1点 - 提前量） ，保持原样(按比赛时间减提前量计算)
					//return realEndTime;
				}
			} else if ((dayOfWeek == Calendar.WEDNESDAY || dayOfWeek == Calendar.THURSDAY) && (hour >= 8 || (hour == 7 && minute >= 30))) {
				// 周3、4 && 7:30后的 (9点前),保持原样, 7:30以前的归到前一天23：00
				//return realEndTime;

			} else {
				dayMilli=dayMilli-1000;
				// 当天无法出票的，移动到前一天 23 点
				c.add(Calendar.DAY_OF_MONTH, -1);
				c.set(Calendar.HOUR_OF_DAY, 23);
				c.set(Calendar.MINUTE, 59);
				c.set(Calendar.SECOND, 59);
				
				c.add(Calendar.MILLISECOND, -dayMilli);
				realEndTime = c.getTime();
				
			}
			
		} else if (hour >= 24 || (hour == 23 && minute >= 60 - aheadMilli / 1000 / 60)) {

			if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
				// do nothing!!!
				//return realEndTime;
			} else {
				dayMilli=dayMilli-1000;
				c.set(Calendar.HOUR_OF_DAY, 23);
				c.set(Calendar.MINUTE, 59);
				c.set(Calendar.SECOND, 59);
				realEndTime = c.getTime();
				c.add(Calendar.MILLISECOND, -dayMilli);
				realEndTime = c.getTime();

			}
		}

		//春节特殊处理
		//如果截止时间在 1-21 22:52  到 1-29 0:00之间的，调整到1-21 22:52截止
		Date start_ = DateTools.StringToDate("2014-01-29 23:50:00") ;
		Date end_ = DateTools.StringToDate("2014-02-07 00:00:00") ;
		if(realEndTime.getTime() > start_.getTime() && realEndTime.getTime() <end_.getTime() ){
			realEndTime = start_;
		}
		
		//世界杯调整
		Date euroCupStart = DateTools.StringToDate("2014-06-13 23:59:00") ;
		Date euroCupEnd = DateTools.StringToDate("2014-07-14 09:16:00") ;
		Map<Integer,Integer> unHandleDays9 = new HashMap<Integer,Integer>();
		unHandleDays9.put(615, 15);
		Map<Integer,Integer> unHandleDays4 = new HashMap<Integer,Integer>();
		unHandleDays4.put(624, 24);
		unHandleDays4.put(625, 25);
		unHandleDays4.put(626, 26);
		unHandleDays4.put(627, 27);
		unHandleDays4.put(629, 29);
		unHandleDays4.put(630, 30);
		unHandleDays4.put(71, 1);
		unHandleDays4.put(72, 2);
		unHandleDays4.put(75, 5);
		unHandleDays4.put(76, 6);
		unHandleDays4.put(79, 9);
		unHandleDays4.put(710, 10);
		unHandleDays4.put(713, 13);
		Map<Integer,Integer> unHandleDays6 = new HashMap<Integer,Integer>();
		unHandleDays6.put(614, 14);
		unHandleDays6.put(616, 16);
		unHandleDays6.put(617, 17);
		unHandleDays6.put(618, 18);
		unHandleDays6.put(619, 19);
		unHandleDays6.put(620, 20);
		unHandleDays6.put(621, 21);
		unHandleDays6.put(622, 22);
		unHandleDays6.put(623, 23);
		Map<Integer,Integer> unHandleDays3 = new HashMap<Integer,Integer>();
		unHandleDays3.put(714, 14);
		Map<Integer,Integer> unHandleDays0 = new HashMap<Integer,Integer>();
		unHandleDays0.put(628, 28);
		unHandleDays0.put(73, 3);
		unHandleDays0.put(74, 4);
		unHandleDays0.put(77, 7);
		unHandleDays0.put(78, 8);
		unHandleDays0.put(711, 11);
		unHandleDays0.put(712, 12);
		c.setTime(matchTime);
		if(matchTime.getTime()> euroCupStart.getTime() && matchTime.getTime() <euroCupEnd.getTime() ){
			int month=c.get(Calendar.MONTH) + 1;
			int day=c.get(Calendar.DAY_OF_MONTH);
			int key=Integer.parseInt(month+""+day);
			int hh=c.get(Calendar.HOUR_OF_DAY);
			minute = c.get(Calendar.MINUTE);
			if(unHandleDays9.get(key)!=null ){
				if(hh<9 || (hh >=14 && minute>15)){
					c.add(Calendar.MILLISECOND, -aheadMilli);
					realEndTime = c.getTime();
				}else if (hh>=9 || hh<14){
					aheadMilli=aheadMilli-1000;
					c.set(Calendar.HOUR_OF_DAY,8);
					c.set(Calendar.MINUTE, 59);
					c.set(Calendar.SECOND, 59);
					c.add(Calendar.MILLISECOND, -aheadMilli);
					realEndTime = c.getTime();
				}
			}else if(unHandleDays4.get(key)!=null){
				if(hh<4 || (hh >=9 && minute>15)){
					c.add(Calendar.MILLISECOND, -aheadMilli);
					realEndTime = c.getTime();
				}else if (hh>=4 || hh<9){
					aheadMilli=aheadMilli-1000;
					c.set(Calendar.HOUR_OF_DAY,3);
					c.set(Calendar.MINUTE, 59);
					c.set(Calendar.SECOND, 59);
					c.add(Calendar.MILLISECOND, -aheadMilli);
					realEndTime = c.getTime();
				}
			}
			else if(unHandleDays6.get(key)!=null){
				if(hh<6 || (hh >=11 && minute>15)){
					c.add(Calendar.MILLISECOND, -aheadMilli);
					realEndTime = c.getTime();
				}else if (hh>=6 || hh<11){
					aheadMilli=aheadMilli-1000;
					c.set(Calendar.HOUR_OF_DAY,5);
					c.set(Calendar.MINUTE, 59);
					c.set(Calendar.SECOND, 59);
					c.add(Calendar.MILLISECOND, -aheadMilli);
					realEndTime = c.getTime();
				}
			}
			else if(unHandleDays3.get(key)!=null){
				if(hh<3 || (hh >=9 && minute>15)){
					c.add(Calendar.MILLISECOND, -aheadMilli);
					realEndTime = c.getTime();
				}else if (hh>=3 || hh<9){
					aheadMilli=aheadMilli-1000;
					c.set(Calendar.HOUR_OF_DAY,2);
					c.set(Calendar.MINUTE, 59);
					c.set(Calendar.SECOND, 59);
					c.add(Calendar.MILLISECOND, -aheadMilli);
					realEndTime = c.getTime();
				}
			}
			else if(unHandleDays0.get(key)!=null){
				if (hh < 9 || (hh==9 && minute<15)) {
					if ((hh > 0 || (hh == 0 && minute >= 60 - aheadMilli / 1000 / 60))) {
						aheadMilli=aheadMilli-1000;
						c.add(Calendar.DAY_OF_MONTH, -1);
						c.set(Calendar.HOUR_OF_DAY, 23);
						c.set(Calendar.MINUTE, 59);
						c.set(Calendar.SECOND, 59);
						realEndTime = c.getTime();
						c.add(Calendar.MILLISECOND, -aheadMilli);
						realEndTime = c.getTime();
					}else{
						c.add(Calendar.MILLISECOND, -aheadMilli);
						realEndTime = c.getTime();
					}
				}else if (hh >= 24 || (hh >= 23 && minute >= 60 - aheadMilli / 1000 / 60)) {
						aheadMilli=aheadMilli-1000;
						c.set(Calendar.HOUR_OF_DAY, 23);
						c.set(Calendar.MINUTE, 59);
						c.set(Calendar.SECOND, 59);
						realEndTime = c.getTime();
						c.add(Calendar.MILLISECOND, -aheadMilli);
						realEndTime = c.getTime();

					}else{
						c.add(Calendar.MILLISECOND, -aheadMilli);
						realEndTime = c.getTime();
					}

			}
			
		}
		return realEndTime;
	}
	
	// 竞彩篮球球计算赛事截止时间
	public static Date getBbEndSaleTime_fb(Date matchTime, int aheadMilli) {// 计算实际的截止时间
		Calendar c = Calendar.getInstance();
		c.setTime(matchTime);
		// 计算提前量之后的时间
		c.add(Calendar.MILLISECOND, -aheadMilli);
		Date realEndTime = c.getTime();

		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);

		// 周3，周4早上是7：30截止
		// 如果截止小于9点，并在周日或周一早上，要调整到1:00截止，，其它时间调整到前一天23点截止
		// 如果截止时间大于23:00点，周六或周日不做处理，其它日期要调整到23点截止
		int dayMilli=12*60*1000;
		if (hour < 9) {
			if (dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.MONDAY) {// 周日、周1
				if ((hour > 0 || (hour == 0 && minute >= 60 - aheadMilli / 1000 / 60))) {
					// 截止时间>=（1点 - 提前量）
					c.set(Calendar.HOUR_OF_DAY, 1);
					c.set(Calendar.MINUTE, 0);
					c.set(Calendar.SECOND, 0);

					c.add(Calendar.MILLISECOND, -dayMilli);
					realEndTime = c.getTime();
					
				} else {
					// 时间<（1点 - 提前量） ，保持原样(按比赛时间减提前量计算)
					//return realEndTime;
				}
			} else if ((dayOfWeek == Calendar.WEDNESDAY || dayOfWeek == Calendar.THURSDAY) && (hour >= 8 || (hour == 7 && minute >= 30))) {
				// 周3、4 && 7:30后的 (9点前),保持原样, 7:30以前的归到前一天23：00
				//return realEndTime;

			} else {
				dayMilli=dayMilli-1000;
				// 当天无法出票的，移动到前一天 23 点
				c.add(Calendar.DAY_OF_MONTH, -1);
				c.set(Calendar.HOUR_OF_DAY, 23);
				c.set(Calendar.MINUTE, 59);
				c.set(Calendar.SECOND, 59);
				
				c.add(Calendar.MILLISECOND, -dayMilli);
				realEndTime = c.getTime();
				
			}
			
		} else if (hour >= 24 || (hour == 23 && minute >= 60 - aheadMilli / 1000 / 60)) {

			if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
				// do nothing!!!
				//return realEndTime;
			} else {
				dayMilli=dayMilli-1000;
				c.set(Calendar.HOUR_OF_DAY, 23);
				c.set(Calendar.MINUTE, 59);
				c.set(Calendar.SECOND, 59);
				realEndTime = c.getTime();
				c.add(Calendar.MILLISECOND, -dayMilli);
				realEndTime = c.getTime();

			}
		}

		//春节特殊处理
		//如果截止时间在 1-21 22:52  到 1-29 0:00之间的，调整到1-21 22:52截止
		Date start_ = DateTools.StringToDate("2014-01-29 23:50:00") ;
		Date end_ = DateTools.StringToDate("2014-02-07 00:00:00") ;
		if(realEndTime.getTime() > start_.getTime() && realEndTime.getTime() <end_.getTime() ){
			realEndTime = start_;
		}
		return realEndTime;
	}


	public static boolean isFbInTime(Date d) {
		boolean res = true;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		String weekStr = DateTools.getWeekStr(calendar.getTime());
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);

		Date euroCupStart = DateTools.StringToDate("2014-06-13 23:59:00") ;
		Date euroCupEnd = DateTools.StringToDate("2014-07-14 09:00:00") ;
		Map<Integer,Integer> unHandleDays9 = new HashMap<Integer,Integer>();
		unHandleDays9.put(615, 15);
		Map<Integer,Integer> unHandleDays4 = new HashMap<Integer,Integer>();
		unHandleDays4.put(624, 24);
		unHandleDays4.put(625, 25);
		unHandleDays4.put(626, 26);
		unHandleDays4.put(627, 27);
		unHandleDays4.put(629, 29);
		unHandleDays4.put(630, 30);
		unHandleDays4.put(71, 1);
		unHandleDays4.put(72, 2);
		unHandleDays4.put(75, 5);
		unHandleDays4.put(76, 6);
		unHandleDays4.put(79, 9);
		unHandleDays4.put(710, 10);
		unHandleDays4.put(713, 13);
		Map<Integer,Integer> unHandleDays6 = new HashMap<Integer,Integer>();
		unHandleDays6.put(614, 14);
		unHandleDays6.put(616, 16);
		unHandleDays6.put(617, 17);
		unHandleDays6.put(618, 18);
		unHandleDays6.put(619, 19);
		unHandleDays6.put(620, 20);
		unHandleDays6.put(621, 21);
		unHandleDays6.put(622, 22);
		unHandleDays6.put(623, 23);
		Map<Integer,Integer> unHandleDays3 = new HashMap<Integer,Integer>();
		unHandleDays3.put(714, 14);
		Map<Integer,Integer> unHandleDays0 = new HashMap<Integer,Integer>();
		unHandleDays0.put(628, 28);
		unHandleDays0.put(73, 3);
		unHandleDays0.put(74, 4);
		unHandleDays0.put(77, 7);
		unHandleDays0.put(78, 8);
		unHandleDays0.put(711, 11);
		unHandleDays0.put(712, 12);
		calendar.setTime(d);
		if(d.getTime()> euroCupStart.getTime() && d.getTime() <euroCupEnd.getTime() ){
			int month=calendar.get(Calendar.MONTH) + 1;
			int day=calendar.get(Calendar.DAY_OF_MONTH);
			int key=Integer.parseInt(month+""+day);
			int hh=calendar.get(Calendar.HOUR_OF_DAY);
			minute = calendar.get(Calendar.MINUTE);
			if(unHandleDays9.get(key)!=null ){
				if(hh<9 || hh >=14){
					res = true;
				}else if (hh>=9 || hh<14){
					res = false;
				}
			}else if(unHandleDays4.get(key)!=null){
				if(hh<4 || hh >=9 ){
					res = true;
				}else if (hh>=4 || hh<9){
					res = false;
				}
			}
			else if(unHandleDays6.get(key)!=null){
				if(hh<6 || (hh >=11)){
					res = true;
				}else if (hh>=6 || hh<11){
					res = false;
				}
			}
			else if(unHandleDays3.get(key)!=null){
				if(hh<3 || hh >=9){
					res = true;
				}else if (hh>=3 || hh<9){
					res = false;
				}
			}
			else if(unHandleDays0.get(key)!=null){
				if (hh < 9) {
					if (hh > 0 || ( hh==0 &&  minute>=0) ){
						res = false;
					}else{
						res = true;
					}
				}else if (hh >= 24 || (hh >= 23 && minute>=59)) {
						res = false;
					}else{
						res = true;
					}

			}
			
		}else{
			if (weekStr.equals("周六")) {
				if ( (hour < 9 || (hour == 8 && minute < 59))) {
					res = false;
				}else{
					if(hour == 9 && minute <2){
						LogUtil.out("竟彩足球：" + weekStr + DateTools.dateToString(d) + "9:00送票开始！");
					}
				}
			} else
			if (weekStr.equals("周日")) {
				if (((hour == 0 && minute >= 55) || (hour > 0)) && (hour < 9 || (hour == 8 && minute < 59))) {
					
					res = false;
				}else{
					if((hour == 0 && (minute > 55 && minute <57))){
						LogUtil.out("竟彩足球：" + weekStr + DateTools.dateToString(d) + " 00:55送票结束！");
					}else if(hour == 9 && minute <2){
						LogUtil.out("竟彩足球：" + weekStr + DateTools.dateToString(d) + "9:00送票开始！");
					}
				}
			} else if (weekStr.equals("周一")) {
				if ((((hour == 0 && minute >= 55) || (hour > 0)) && (hour < 9 || (hour == 8 && minute < 59))) || ((hour == 23 && minute >= 55))
						|| (hour > 23)) {
					
					res = false;
					
				}else{
					if(hour == 9 && minute <2 ){
						LogUtil.out("竟彩足球：" + weekStr + DateTools.dateToString(d) + "9:00送票开始！");
					}else if((hour == 23 && (minute > 55 && minute<57))){
						LogUtil.out("竟彩足球：" + weekStr + DateTools.dateToString(d) + "23:55送票结束！");
					}
				}
			} else {
				if (((hour == 23 && minute >= 55) || (hour > 23)) || (hour < 9 || (hour == 8 && minute < 59))) {
					//LogUtil.out("竟彩足球：" + weekStr + DateTools.dateToString(d) + " 9:00前 ,23:55后不送票！");
					res = false;
				}else{
					if(hour == 9 && minute <2){
						LogUtil.out("竟彩足球：" + weekStr + DateTools.dateToString(d) + "9:00送票开始！");
					}else if((hour == 23 && (minute >55 && minute<57))){
						LogUtil.out("竟彩足球：" + weekStr + DateTools.dateToString(d) + "23:55送票结束！");
					}
				}
				
			}
		}
		return res;
	}
	
	
	public static boolean isFbInTime_fb(Date d) {
		boolean res = true;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		String weekStr = DateTools.getWeekStr(calendar.getTime());
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);

		if (weekStr.equals("周六")) {
			if ( (hour < 9 || (hour == 8 && minute < 59))) {
				//LogUtil.out("竟彩足球：" + weekStr + DateTools.dateToString(d) + " 00:55 到 9:00不送票！");
				res = false;
			}else{
				if(hour == 9 && minute <2){
					LogUtil.out("竟彩足球：" + weekStr + DateTools.dateToString(d) + "9:00送票开始！");
				}
			}
		} else
		if (weekStr.equals("周日")) {
			if (((hour == 0 && minute >= 55) || (hour > 0)) && (hour < 9 || (hour == 8 && minute < 59))) {
				//LogUtil.out("竟彩足球：" + weekStr + DateTools.dateToString(d) + " 00:55 到 9:00不送票！");
				res = false;
			}else{
				if((hour == 0 && (minute > 55 && minute <57))){
					LogUtil.out("竟彩足球：" + weekStr + DateTools.dateToString(d) + " 00:55送票结束！");
				}else if(hour == 9 && minute <2){
					LogUtil.out("竟彩足球：" + weekStr + DateTools.dateToString(d) + "9:00送票开始！");
				}
			}
		} else if (weekStr.equals("周一")) {
			if ((((hour == 0 && minute >= 55) || (hour > 0)) && (hour < 9 || (hour == 8 && minute < 59))) || ((hour == 23 && minute >= 55))
					|| (hour > 23)) {
				//LogUtil.out("竟彩足球：" + weekStr + DateTools.dateToString(d) + " 00:55 到 9:00, 23:55后不送票！");
				res = false;
				//(((hour == 0 && minute >= 59) || (hour > 0)) && (hour < 9 || (hour == 8 && minute < 59))) || ((hour == 23 && minute >= 55 || (hour > 23))
				//((hour == 23 && minute >= 55) || (hour > 23)) || (hour < 9 || (hour == 8 && minute < 59))
			}else{
				if(hour == 9 && minute <2 ){
					LogUtil.out("竟彩足球：" + weekStr + DateTools.dateToString(d) + "9:00送票开始！");
				}else if((hour == 23 && (minute > 55 && minute<57))){
					LogUtil.out("竟彩足球：" + weekStr + DateTools.dateToString(d) + "23:55送票结束！");
				}
			}
		} else {
			if (((hour == 23 && minute >= 55) || (hour > 23)) || (hour < 9 || (hour == 8 && minute < 59))) {
				//LogUtil.out("竟彩足球：" + weekStr + DateTools.dateToString(d) + " 9:00前 ,23:55后不送票！");
				res = false;
			}else{
				if(hour == 9 && minute <2){
					LogUtil.out("竟彩足球：" + weekStr + DateTools.dateToString(d) + "9:00送票开始！");
				}else if((hour == 23 && (minute >55 && minute<57))){
					LogUtil.out("竟彩足球：" + weekStr + DateTools.dateToString(d) + "23:55送票结束！");
				}
			}
			
//			if (((hour == 20 && minute >= 55) || (hour > 20)) || (hour < 9 || (hour == 8 && minute < 59))) {
//				//LogUtil.out("竟彩足球：" + weekStr + DateTools.dateToString(d) + " 9:00前 ,23:55后不送票！");
//				res = false;
//			}else{
//				if(hour == 9 && minute <2){
//					LogUtil.out("竟彩足球：" + weekStr + DateTools.dateToString(d) + "9:00送票开始！");
//				}else if((hour == 23 && (minute >55 && minute<57))){
//					LogUtil.out("竟彩足球：" + weekStr + DateTools.dateToString(d) + "23:55送票结束！");
//				}
//			}
		}
		return res;
	}
	
	public static boolean isBbInTime(Date d) {
		boolean res = true;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		String weekStr = DateTools.getWeekStr(calendar.getTime());
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);

		Date euroCupStart = DateTools.StringToDate("2014-06-13 23:59:00") ;
		Date euroCupEnd = DateTools.StringToDate("2014-07-14 09:00:00") ;
		Map<Integer,Integer> unHandleDays9 = new HashMap<Integer,Integer>();
		unHandleDays9.put(615, 15);
		Map<Integer,Integer> unHandleDays4 = new HashMap<Integer,Integer>();
		unHandleDays4.put(624, 24);
		unHandleDays4.put(625, 25);
		unHandleDays4.put(626, 26);
		unHandleDays4.put(627, 27);
		unHandleDays4.put(629, 29);
		unHandleDays4.put(630, 30);
		unHandleDays4.put(71, 1);
		unHandleDays4.put(72, 2);
		unHandleDays4.put(75, 5);
		unHandleDays4.put(76, 6);
		unHandleDays4.put(79, 9);
		unHandleDays4.put(710, 10);
		unHandleDays4.put(713, 13);
		Map<Integer,Integer> unHandleDays6 = new HashMap<Integer,Integer>();
		unHandleDays6.put(614, 14);
		unHandleDays6.put(616, 16);
		unHandleDays6.put(617, 17);
		unHandleDays6.put(618, 18);
		unHandleDays6.put(619, 19);
		unHandleDays6.put(620, 20);
		unHandleDays6.put(621, 21);
		unHandleDays6.put(622, 22);
		unHandleDays6.put(623, 23);
		Map<Integer,Integer> unHandleDays3 = new HashMap<Integer,Integer>();
		unHandleDays3.put(714, 14);
		Map<Integer,Integer> unHandleDays0 = new HashMap<Integer,Integer>();
		unHandleDays0.put(628, 28);
		unHandleDays0.put(73, 3);
		unHandleDays0.put(74, 4);
		unHandleDays0.put(77, 7);
		unHandleDays0.put(78, 8);
		unHandleDays0.put(711, 11);
		unHandleDays0.put(712, 12);
		calendar.setTime(d);
		if(d.getTime()> euroCupStart.getTime() && d.getTime() <euroCupEnd.getTime() ){
			int month=calendar.get(Calendar.MONTH) + 1;
			int day=calendar.get(Calendar.DAY_OF_MONTH);
			int key=Integer.parseInt(month+""+day);
			int hh=calendar.get(Calendar.HOUR_OF_DAY);
			minute = calendar.get(Calendar.MINUTE);
			if(unHandleDays9.get(key)!=null ){
				if(hh<9 || hh >=14){
					res = true;
				}else if (hh>=9 || hh<14){
					res = false;
				}
			}else if(unHandleDays4.get(key)!=null){
				if(hh<4 || hh >=9 ){
					res = true;
				}else if (hh>=4 || hh<9){
					res = false;
				}
			}
			else if(unHandleDays6.get(key)!=null){
				if(hh<6 || (hh >=11)){
					res = true;
				}else if (hh>=6 || hh<11){
					res = false;
				}
			}
			else if(unHandleDays3.get(key)!=null){
				if(hh<3 || hh >=9){
					res = true;
				}else if (hh>=3 || hh<9){
					res = false;
				}
			}
			else if(unHandleDays0.get(key)!=null){
				if (hh < 9) {
					if (hh > 0 || ( hh==0 &&  minute>=0) ){
						res = false;
					}else{
						res = true;
					}
				}else if (hh >= 24 || (hh >= 23 && minute>=59)) {
						res = false;
					}else{
						res = true;
					}

			}
			
		}else{
			if (weekStr.equals("周六")) {
				if ((hour < 9 || (hour == 8 && minute < 59))) {
					res = false;
				}
			} else 
			if (weekStr.equals("周日")) {
			   if (((hour == 0 && minute >= 55) || (hour > 0)) && (hour < 9 || (hour == 8 && minute < 59))) {
				
					res = false;
				}
			} else if (weekStr.equals("周一")) {
				if ((((hour == 0 && minute >= 59) || (hour > 0)) && (hour < 9 || (hour == 8 && minute < 59))) || ((hour == 23 && minute >= 55))
						|| (hour > 23)) {
					
					res = false;
				}
			} else if (weekStr.equals("周三") || weekStr.equals("周四")) {
				if (((hour == 23 && minute >= 55) || (hour > 23)) || (hour < 7 || (hour == 7 && minute < 31))) {
					
					res = false;
				}
			} else {
				if (((hour == 23 && minute >= 55) || (hour > 23)) || (hour < 9 || (hour == 8 && minute < 59))) {
					
					res = false;
				}
			}
		
		}
		return res;
	}
	
	
	public static boolean isBbInTime_fb(Date d) {
		boolean res = true;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		String weekStr = DateTools.getWeekStr(calendar.getTime());
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);

		if (weekStr.equals("周六")) {
			if ((hour < 9 || (hour == 8 && minute < 59))) {
				//LogUtil.out("竟彩篮球：" + weekStr + DateTools.dateToString(d) + " 9:00前不送票！");
				res = false;
			}
//			if (((hour == 0 && minute >= 55) || (hour > 0)) && (hour < 9 || (hour == 8 && minute < 59))) {
//				//LogUtil.out("竟彩篮球：" + weekStr + DateTools.dateToString(d) + " 9:00前不送票！");
//				res = false;
//			}
		} else 
		if (weekStr.equals("周日")) {
		   if (((hour == 0 && minute >= 55) || (hour > 0)) && (hour < 9 || (hour == 8 && minute < 59))) {
				//LogUtil.out("竟彩篮球：" + weekStr + DateTools.dateToString(d) + " 00:55 到 9:00不送票！");
				res = false;
			}
		} else if (weekStr.equals("周一")) {
			if ((((hour == 0 && minute >= 59) || (hour > 0)) && (hour < 9 || (hour == 8 && minute < 59))) || ((hour == 23 && minute >= 55))
					|| (hour > 23)) {
				//LogUtil.out("竟彩篮球：" + weekStr + DateTools.dateToString(d) + " 01:00 到 9:00, 23:55后不送票！");
				res = false;
			}
		} else if (weekStr.equals("周三") || weekStr.equals("周四")) {
			if (((hour == 23 && minute >= 55) || (hour > 23)) || (hour < 7 || (hour == 7 && minute < 31))) {
				//LogUtil.out("竟彩篮球：" + weekStr + DateTools.dateToString(d) + " 7:30前 ,23:55后不送票！");
				res = false;
			}
		} else {
//			if (((hour == 20 && minute >= 55) || (hour > 20)) || (hour < 9 || (hour == 8 && minute < 59))) {
//				//LogUtil.out("竟彩篮球：" + weekStr + DateTools.dateToString(d) + " 9:00前 ,23:55后不送票！");
//				res = false;
//			}
			if (((hour == 23 && minute >= 55) || (hour > 23)) || (hour < 9 || (hour == 8 && minute < 59))) {
				//LogUtil.out("竟彩篮球：" + weekStr + DateTools.dateToString(d) + " 9:00前 ,23:55后不送票！");
				res = false;
			}
		}
		return res;
	}

	public static void main(String[] arg) {

		TimeTools t = new TimeTools();
	
		Date endTime = null;
		int aheadTime = 15 * 60 * 1000;
		Calendar c = Calendar.getInstance(); 
		
		Date realEndTime = c.getTime();
		
		//c.setTime(DateTools.StringToDate("2014-06-014 08:54:59"));
		//System.out.println(c.get(Calendar.HOUR_OF_DAY));
		//System.out.println(c.get(Calendar.DAY_OF_MONTH));
		//System.out.println(c.get(Calendar.MONTH) + 1);   //获取月份，0表示1月份));
		
//		Date euroCupStart = DateTools.StringToDate("2014-06-13 23:59:00") ;
//		Date euroCupEnd = DateTools.StringToDate("2014-07-14 09:00:00") ;
//		
//		Date matchTime = DateTools.StringToDate("2014-06-28 09:19:00");
//		Map<Integer,Integer> unHandleDays9 = new HashMap<Integer,Integer>();
//		unHandleDays9.put(627, 14);
//		Map<Integer,Integer> unHandleDays5 = new HashMap<Integer,Integer>();
//		unHandleDays5.put(628, 15);
//		c.setTime(matchTime);
//		if(matchTime.getTime()> euroCupStart.getTime() && matchTime.getTime() <euroCupEnd.getTime() ){
//			int month=c.get(Calendar.MONTH) + 1;
//			int day=c.get(Calendar.DAY_OF_MONTH);
//			int key=Integer.parseInt(month+""+day);
//			int hh=c.get(Calendar.HOUR_OF_DAY);
//			int minute=c.get(Calendar.MINUTE);
//			System.out.println(minute >= 60 - aheadTime / 1000 / 60);
//			if(unHandleDays9.get(key)!=null ){
//				if(hh<4 || (hh >=9 && minute>15)){
//					c.add(Calendar.MILLISECOND, -aheadTime);
//					realEndTime = c.getTime();
//				}else if (hh>=4 || hh<9){
//					c.set(Calendar.HOUR_OF_DAY,3);
//					c.set(Calendar.MINUTE, 59);
//					c.set(Calendar.SECOND, 59);
//					c.add(Calendar.MILLISECOND, -aheadTime);
//					realEndTime = c.getTime();
//				}
//			}else if(unHandleDays5.get(key)!=null){
//				if (hh < 9 || (hh==9 && minute<15)) {
//					if ((hh > 0 || (hh == 0 && minute >= 60 - aheadTime / 1000 / 60))) {
//						aheadTime=aheadTime-1000;
//						c.add(Calendar.DAY_OF_MONTH, -1);
//						c.set(Calendar.HOUR_OF_DAY, 23);
//						c.set(Calendar.MINUTE, 59);
//						c.set(Calendar.SECOND, 59);
//						realEndTime = c.getTime();
//						c.add(Calendar.MILLISECOND, -aheadTime);
//						realEndTime = c.getTime();
//					}else{
//						c.add(Calendar.MILLISECOND, -aheadTime);
//						realEndTime = c.getTime();
//					}
//				}else if (hh >= 24 || (hh >= 23 && minute >= 60 - aheadTime / 1000 / 60)) {
//
//						aheadTime=aheadTime-1000;
//						c.set(Calendar.HOUR_OF_DAY, 23);
//						c.set(Calendar.MINUTE, 59);
//						c.set(Calendar.SECOND, 59);
//						realEndTime = c.getTime();
//						c.add(Calendar.MILLISECOND, -aheadTime);
//						realEndTime = c.getTime();
//
//					}else{
//						c.add(Calendar.MILLISECOND, -aheadTime);
//						realEndTime = c.getTime();
//					}
//
//				}
////				if(hh<0 || (hh >=9 && minute>15)){
////					c.add(Calendar.MILLISECOND, -aheadTime);
////					realEndTime = c.getTime();
////				}else if (hh>=24 || hh<9){
////					c.add(Calendar.DAY_OF_MONTH, -1);
////					c.set(Calendar.HOUR_OF_DAY,23);
////					c.set(Calendar.MINUTE, 59);
////					c.set(Calendar.SECOND, 59);
////					c.add(Calendar.MILLISECOND, -aheadTime);
////					realEndTime = c.getTime();
////				}
//			
//
//		}
//		
//		System.out.println("最后结果:"+DateTools.dateToString(realEndTime));
		
		c.setTime(DateTools.StringToDate("2014-07-06 03:59:00"));
		boolean is=isFbInTime(c.getTime());
		if(is){
			System.out.println("1");
		}else{
			System.out.println("2");
		}
		
		//System.out.println("特殊处理前"+DateTools.dateToString(getFbEndSaleTime(c.getTime(),aheadTime)));
		
//		System.out.println("特殊处理开始"+DateTools.dateToString(getFbEndSaleTime(c.getTime(),aheadTime)));
//		c.setTime(DateTools.StringToDate("2014-01-31 05:40:00"));
//		System.out.println("特殊处理中的特殊处理前"+DateTools.dateToString(getFbEndSaleTime(c.getTime(),aheadTime)));
//		c.setTime(DateTools.StringToDate("2014-02-07 09:45:00"));
//		System.out.println("特殊处理中的特殊处理开始"+DateTools.dateToString(getFbEndSaleTime(c.getTime(),aheadTime)));
//		c.setTime(DateTools.StringToDate("2012-06-28 09:02:00"));
//		System.out.println("特殊处理结束前"+DateTools.dateToString(getFbEndSaleTime(c.getTime(),aheadTime)));
//		c.setTime(DateTools.StringToDate("2012-07-28 09:02:00"));
//		System.out.println("特殊处理结束后"+DateTools.dateToString(getFbEndSaleTime(c.getTime(),aheadTime)));
//		
//		int step = 5; // 分钟
//
//		for (int i = 0; i < 300; i++) {
//
//			matchTime = c.getTime();
//			if (c.get(c.HOUR_OF_DAY) >= 10 && c.get(c.HOUR_OF_DAY) < 22) {
//
//			} else {
//
//				System.out.print(DateTools.getWeekStr(matchTime) + " " + DateTools.dateToString(matchTime));
//				endTime = t.getFbEndSaleTime(matchTime, aheadTime);
//				System.out.print("  endTime:  " + DateTools.getWeekStr(endTime) + " " + DateTools.dateToString(endTime));
//
//				if (matchTime.getTime() != endTime.getTime() + aheadTime) {
//					System.out.println("  xxxxx ");
//				} else {
//					System.out.println("   ");
//				}
//			}
//
//			c.add(c.MINUTE, step);
//
//		}
	}

}
