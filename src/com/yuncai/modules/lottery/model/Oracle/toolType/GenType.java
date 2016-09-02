package com.yuncai.modules.lottery.model.Oracle.toolType;

import java.util.ArrayList;
import java.util.List;

import com.yuncai.core.hibernate.IntegerBeanLabelItem;

public class GenType extends IntegerBeanLabelItem{

	protected GenType(String name, int value) {
		super(GenType.class.getName(), name, value);
	}

	public static GenType getItem(int value) {
		return (GenType) GenType.getResult(GenType.class.getName(), value);
	}

	public static final GenType ALL = new GenType("全部", -1);

	public static final GenType TC = new GenType("套餐", 1);

	public static final GenType ZH = new GenType("追号", 2);

	public static final GenType ZG = new GenType("再次购买", 4);

	public static final GenType SJ = new GenType("手机买彩票", 5);
	
	public static final GenType SJZH=new GenType("手机追号",6);
	
	public static List list = new ArrayList();
	public static List listSJ = new ArrayList();

	static {
		list.add(ALL);
		list.add(TC);
		list.add(ZH);
		list.add(ZG);
		
		listSJ.add(ALL);
		listSJ.add(SJ);
	}

}
