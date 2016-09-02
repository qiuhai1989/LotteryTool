package com.yuncai.modules.lottery.model.Oracle.toolType;

import java.util.ArrayList;
import java.util.List;

import com.yuncai.core.hibernate.IntegerBeanLabelItem;

public class CardStatus  extends IntegerBeanLabelItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = -621003869402167413L;

	protected CardStatus(String name, int value) {
		super(CardStatus.class.getName(), name, value);
	}

	public static CardStatus getItem(int value) {
		return (CardStatus) CardStatus.getResult(CardStatus.class.getName(), value);
	}

	public static final CardStatus ALL = new CardStatus("全部", -1);
	
	public static final CardStatus NOTUSE = new CardStatus("未使用", 0);

	public static final CardStatus USEED = new CardStatus("已使用", 1);

	public static final CardStatus RESERVED = new CardStatus("已回收", 2);

	public static List list = new ArrayList();

	static {
		list.add(ALL);
		list.add(NOTUSE);
		list.add(USEED);
		list.add(RESERVED);

	}

}
