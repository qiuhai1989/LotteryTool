package com.yuncai.modules.lottery.model.Oracle.toolType;

import java.util.ArrayList;
import java.util.List;

import com.yuncai.core.hibernate.IntegerBeanLabelItem;

public class ChaseStatus extends IntegerBeanLabelItem {

	protected ChaseStatus(String name, int value) {
		super(ChaseStatus.class.getName(), name, value);
	}

	public static ChaseStatus getItem(int value) {
		return (ChaseStatus) ChaseStatus.getResult(ChaseStatus.class.getName(), value);
	}

	public static final ChaseStatus ALL = new ChaseStatus("所有", -1);

	public static final ChaseStatus CHASING = new ChaseStatus("追号中", 1);

	public static final ChaseStatus CHASE_CANCEL = new ChaseStatus("已撤消", 2);

	public static final ChaseStatus CHASE_END = new ChaseStatus("已完成", 3);

	public static List list = new ArrayList();

	static {
		list.add(ALL);
		list.add(CHASING);
		list.add(CHASE_CANCEL);
		list.add(CHASE_END);
	}

}
