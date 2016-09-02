package com.yuncai.modules.lottery.model.Oracle.toolType;

import com.yuncai.core.hibernate.IntegerBeanLabelItem;

public class FollowingPlanStatus extends IntegerBeanLabelItem {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1410849485815957495L;

	protected FollowingPlanStatus(String name, int value) {
		super(FollowingPlanStatus.class.getName(), name, value);
	}

	public static FollowingPlanStatus getItem(int value) {
		return (FollowingPlanStatus) FollowingPlanStatus.getResult(FollowingPlanStatus.class.getName(), value);
	}

	public static final FollowingPlanStatus END = new FollowingPlanStatus("已截止", 1);

	public static final FollowingPlanStatus COMPLETION = new FollowingPlanStatus("已满员", 2);
	
	public static final FollowingPlanStatus UNFINISHED = new FollowingPlanStatus("未满员", 3);

}
