package com.yuncai.modules.lottery.model.Oracle.toolType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yuncai.core.hibernate.IntegerBeanLabelItem;

public class DrawingStatus extends IntegerBeanLabelItem {

	protected DrawingStatus(String name, int value) {
		super(DrawingStatus.class.getName(), name, value);
	}

	public static DrawingStatus getItem(int value) {
		return (DrawingStatus) DrawingStatus.getResult(DrawingStatus.class.getName(), value);
	}

	public static final DrawingStatus ALL = new DrawingStatus("全部", -1);

	public static final DrawingStatus WAIT = new DrawingStatus("待审核", 1);
	public static final DrawingStatus FAILURE = new DrawingStatus("已驳回", 2);
	public static final DrawingStatus CHECKED = new DrawingStatus("待汇款", 3);
	public static final DrawingStatus SENT = new DrawingStatus("已汇出", 4);
	public static final DrawingStatus BANKRETURN = new DrawingStatus("银行退单", 5);
	public static final DrawingStatus SUCCESS = new DrawingStatus("提款成功", 0);

	public static final List list = new ArrayList();
	public static final Map<Integer,String> map = new HashMap<Integer,String>();

	static {
		list.add(ALL);
		list.add(WAIT);
		list.add(FAILURE);
		list.add(CHECKED);
		list.add(SENT);
		list.add(BANKRETURN);
		list.add(SUCCESS);	
		
		map.put(ALL.getValue(), "");
		map.put(WAIT.getValue(), "用户发起提款申请，进入值班经理审核流程。");
		map.put(FAILURE.getValue(), "订单已驳回，提款失败，流程结束。");
		map.put(CHECKED.getValue(), "值班经理审核通过，进入财务人员汇款流程。");
		map.put(SENT.getValue(), "财务人员已汇款，进入确认成功流程。");
		map.put(SUCCESS.getValue(), "提款成功，流程结束。");
	}
}
