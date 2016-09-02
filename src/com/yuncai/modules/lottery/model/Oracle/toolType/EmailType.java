package com.yuncai.modules.lottery.model.Oracle.toolType;

import java.util.ArrayList;
import java.util.List;

import com.yuncai.core.hibernate.IntegerBeanLabelItem;

public class EmailType  extends IntegerBeanLabelItem {
	protected EmailType (String name, int value) {
		super(EmailType.class.getName(), name, value);
	}
	
	public static EmailType getItem(int value) {
		return (EmailType) EmailType.getResult(EmailType.class.getName(), value);
	}

	public static final EmailType ALL = new EmailType("全部", -1);
	public static final EmailType SGCP = new EmailType("手工出票", 1);
	public static final EmailType TKSH = new EmailType("提款审核",2);
	public static final EmailType TKHK = new EmailType("提款汇款",3);
	public static List list = new ArrayList();

	static {
		list.add(ALL);
		list.add(SGCP);
		list.add(TKSH);
		list.add(TKHK);
	}
}
