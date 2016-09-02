package com.yuncai.modules.lottery.model.Oracle.toolType;

import com.yuncai.core.hibernate.IntegerBeanLabelItem;

public class PublicStatus extends IntegerBeanLabelItem{
	protected PublicStatus(String name, int value) {
		super(PublicStatus.class.getName(), name, value);
	}

	public static PublicStatus getItem(int value) {
		return (PublicStatus) PublicStatus.getResult(PublicStatus.class.getName(), value);
	}

	public static final PublicStatus NOT_PUBLIC = new PublicStatus("不公开", 3);

	public static final PublicStatus PUBLIC = new PublicStatus("公开", 0);

	public static final PublicStatus AWARD_PUBLIC = new PublicStatus("开奖后公开", 2);
	
	public static final PublicStatus STOP_PUBLIC = new PublicStatus("截止后公开", 1);

}
