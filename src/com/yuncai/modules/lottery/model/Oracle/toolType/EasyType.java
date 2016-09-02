package com.yuncai.modules.lottery.model.Oracle.toolType;

import java.util.ArrayList;
import java.util.List;

import com.yuncai.core.hibernate.IntegerBeanLabelItem;

public class EasyType extends IntegerBeanLabelItem {
	protected EasyType (String name, int value) {
		super(EasyType.class.getName(), name, value);
	}
	
	public static EasyType getItem(int value) {
		return (EasyType) EasyType.getResult(EasyType.class.getName(), value);
	}
	
	public static final EasyType ALL = new EasyType("全部", -1);
	public static final EasyType GMJ =  new EasyType("高命中",1);
	public static final EasyType SLX = new EasyType("实力型",2);
	public static final EasyType GHB = new EasyType("高回报",3);
	
	public static List list = new ArrayList();
	
	static{
		list.add(ALL);
		list.add(GMJ);
		list.add(SLX);
		list.add(GHB);
	}
}
