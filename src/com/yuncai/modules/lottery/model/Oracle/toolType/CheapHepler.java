package com.yuncai.modules.lottery.model.Oracle.toolType;

import com.yuncai.core.tools.ArithUtil;

public class CheapHepler {
public static final double SSQ_DISCOUNT = 0.92;//双色球9.2折
	
	/**
	 * 返回优惠金额,没有找到优惠条款则返回0
	 * 比如购买2元彩票 享受9.2折 则返回优惠金额0.16元
	 * 
	 * @param lotteryType 彩票类型
	 * @param amount 购买金额
	 * @return 返回优惠后金额
	 */
	public static Double getCheapAmount(int lotteryType, Double amount) {
		Double cheapAmount = 0d;//优惠后的金额
		//取消双色球92折活动所以注释以下2行代码，若有类似活动打开注释并修改优惠基数即可
//		if (lotteryType == LotteryType.SSQ.getValue()) {// 双色球
//			cheapAmount = amount * ArithUtil.sub(1,SSQ_DISCOUNT);
//		} 
		
		return cheapAmount;
	}
	
	public static void main(String[] args) {
		System.out.println(ArithUtil.sub(2,0.0));
		System.out.println(getCheapAmount(100,new Double(2)));
	}

}
