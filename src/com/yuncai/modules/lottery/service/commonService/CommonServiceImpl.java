package com.yuncai.modules.lottery.service.commonService;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.yuncai.core.tools.DateTools;
import com.yuncai.core.util.Dates;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.service.oracleService.lottery.LotteryPlanService;
public class CommonServiceImpl implements CommonService{
	@Resource
	private LotteryPlanService lotteryPlanService;
	
	
	

	@Override
	public String getInformation(Object... params) {
		// TODO Auto-generated method stub
		String result = null;
		String type = (String) params[0];
		if(type.equals("latest30")){
			result = getLatest30();
		}
		
		if(result==null){
			result = "";
		}
		return result;
	}
	
	/**获取最近30天竞彩
	 * @return
	 */
	private String getLatest30(){
		String result = null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		Date beginTime ;
		Date nowDate = new Date();
		calendar.setTime(nowDate);
		calendar.add(Calendar.DAY_OF_MONTH, -30);
		beginTime = calendar.getTime();
		//为了获取0点0分。。
		String beginTimeStr = Dates.format(beginTime, "yyyy-MM-dd");
		beginTime = DateTools.stringToDate(beginTimeStr, "yyyy-MM-dd");
		String nowDateStr = Dates.format(nowDate, "yyyy-MM-dd");
		nowDate = DateTools.stringToDate(nowDateStr, "yyyy-MM-dd");
		
		try {
			Object[] obj = lotteryPlanService.winPercent(LotteryType.JCZQList, beginTime, nowDate);
			result = "近30天云彩会员投注返奖"+doRatio(Double.parseDouble(obj[2].toString()));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result = "查询过程中存在错误";
		}
	
		
		return result;
	}
	
	/**
	 * 
	 * @param ratio
	 * @return
	 */
	public static String doRatio(double ratio){
		DecimalFormat df = new DecimalFormat("0");
		if(ratio==0.0 || ratio==-1.00) return "";
		return df.format(ratio*100)+"%";
	}


}
