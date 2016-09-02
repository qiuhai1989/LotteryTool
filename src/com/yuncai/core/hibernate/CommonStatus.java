package com.yuncai.core.hibernate;

import java.util.ArrayList;
import java.util.List;

public class CommonStatus extends IntegerBeanLabelItem {

	protected CommonStatus(String name, int value) {
		super(CommonStatus.class.getName(), name, value);
	}

	public static CommonStatus getItem(int value) {
		return (CommonStatus) CommonStatus.getResult(CommonStatus.class.getName(), value);
	}

	public static final CommonStatus ALL = new CommonStatus("ȫ��", -1);
	public static final CommonStatus NO = new CommonStatus("否", 0);
	public static final CommonStatus YES = new CommonStatus("是", 1);

	public static final List list = new ArrayList();

	static {
		list.add(ALL);
		list.add(YES);
		list.add(NO);
	}
}
