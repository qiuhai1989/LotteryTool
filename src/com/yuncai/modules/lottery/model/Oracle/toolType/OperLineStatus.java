package com.yuncai.modules.lottery.model.Oracle.toolType;

import com.yuncai.core.hibernate.IntegerBeanLabelItem;
import java.util.*;

public class OperLineStatus extends IntegerBeanLabelItem{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1410849485815957495L;

	protected OperLineStatus(String name, int value) {
		super(OperLineStatus.class.getName(), name, value);
	}

	public static OperLineStatus getItem(int value) {
		return (OperLineStatus) OperLineStatus.getResult(OperLineStatus.class.getName(), value);
	}

	public static final OperLineStatus ALL = new OperLineStatus("全", -1);

	public static final OperLineStatus SUCCESS = new OperLineStatus("成功", 0);

	public static final OperLineStatus FAILURE = new OperLineStatus("失败", 1);

	public static List list = new ArrayList();

	static {
		list.add(SUCCESS);
		list.add(FAILURE);
	}

}
