package com.yuncai.modules.lottery.model.Oracle.toolType;

import com.yuncai.core.hibernate.IntegerBeanLabelItem;
import java.util.*;

public class TicketStatus extends IntegerBeanLabelItem{
	protected TicketStatus(String name, int value) {
		super(TicketStatus.class.getName(), name, value);
	}

	public static TicketStatus getItem(int value) {
		return (TicketStatus) TicketStatus.getResult(TicketStatus.class.getName(), value);
	}

	public static final TicketStatus ALL = new TicketStatus("全部", -1);

	public static final TicketStatus WSP = new TicketStatus("未送票", 1);

	public static final TicketStatus WCP = new TicketStatus("未分配到终端", 2);

	public static final TicketStatus WDP = new TicketStatus("未确认", 3);

	public static final TicketStatus CPCG = new TicketStatus("出票成功", 4);

	public static final TicketStatus CPSB = new TicketStatus("出票失败", 5);

	
	public static final TicketStatus CPSBWDP = new TicketStatus("失败未确认", 7);

	public static final TicketStatus SGCP = new TicketStatus("需手工出票", 6);


	public static List list = new ArrayList();

	static {
		list.add(ALL);
		list.add(WSP);
		list.add(WDP);
		list.add(CPCG);
		list.add(CPSB);
		list.add(CPSBWDP);
		list.add(SGCP);
	}

}
