package com.yuncai.modules.lottery.model.Oracle.toolType;

import com.yuncai.core.hibernate.IntegerBeanLabelItem;
import java.util.*;

public class TicketProvider extends IntegerBeanLabelItem {
	protected TicketProvider(String name, int value) {
		super(TicketProvider.class.getName(), name, value);
	}

	public static TicketProvider getItem(int value) {
		return (TicketProvider) TicketProvider.getResult(TicketProvider.class.getName(), value);
	}

	public static final TicketProvider ALL = new TicketProvider("全部", -1);

	public static final TicketProvider NO = new TicketProvider("无", 0);
	
	public static final TicketProvider FULLNET = new TicketProvider("全网", 1);


	
	public static List list = new ArrayList();

	static {
		list.add(ALL);
	}

}
