package com.yuncai.modules.lottery.model.Oracle.toolType;

import java.util.ArrayList;
import java.util.List;

import com.yuncai.core.hibernate.IntegerBeanLabelItem;

public class RechargeScoreType extends IntegerBeanLabelItem {

	protected RechargeScoreType(String name, int value) {
		super(RechargeScoreType.class.getName(), name, value);
	}

	public static RechargeScoreType getItem(int value) {
		return (RechargeScoreType) RechargeScoreType.getResult(RechargeScoreType.class.getName(), value);
	}
	

	public static final RechargeScoreType OFF = new RechargeScoreType("不参与活动", 0);

	public static final RechargeScoreType NO = new RechargeScoreType("待参与活动", 1);
	
	public static final RechargeScoreType SUCCEED = new RechargeScoreType("已参与活动", 2);
	
	public static final RechargeScoreType LOSE = new RechargeScoreType("参与活动失败", 3);

	
	public static final List list = new ArrayList();
	
	static {
		list.add(OFF);
		list.add(NO);
		list.add(SUCCEED);
		list.add(LOSE);
	}

}
