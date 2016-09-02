package com.yuncai.modules.lottery.model.Oracle.toolType;

import java.util.ArrayList;
import java.util.List;

import com.yuncai.core.hibernate.IntegerBeanLabelItem;

public class WinStatus extends IntegerBeanLabelItem {
	protected WinStatus(String name, int value) {
		super(WinStatus.class.getName(), name, value);
	}

	public static WinStatus getItem(int value) {
		return (WinStatus) WinStatus.getResult(WinStatus.class.getName(), value);
	}

	public static final WinStatus ALL = new WinStatus("全部类型", -1);
	public static final WinStatus NOT_RESULT = new WinStatus("未开奖", 1);

	public static final WinStatus NOT_AWARD = new WinStatus("未中奖", 2);

	public static final WinStatus AWARD = new WinStatus("已中奖", 3);

	public static final WinStatus SEND_AWARD = new WinStatus("已派奖", 4);
	
	public static final WinStatus DRAWBACK = new WinStatus("已退款", 11);

	public static List list = new ArrayList();
	
	public static List<WinStatus> aleadyOpenPrize = new ArrayList<WinStatus>();//已开奖

	static {
		list.add(ALL);
		list.add(NOT_RESULT);
		list.add(NOT_AWARD);
		list.add(AWARD);
		list.add(SEND_AWARD);
		list.add(DRAWBACK);
		
		//已开奖状态
		aleadyOpenPrize.add(NOT_AWARD);
		aleadyOpenPrize.add(AWARD);
		aleadyOpenPrize.add(SEND_AWARD);
		
	}

}
