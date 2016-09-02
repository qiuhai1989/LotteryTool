package com.yuncai.modules.lottery.model.Oracle.toolType;

import java.util.ArrayList;
import java.util.List;

import com.yuncai.core.hibernate.IntegerBeanLabelItem;

public class PlanType extends IntegerBeanLabelItem{
	protected PlanType(String name, int value) {
		super(PlanType.class.getName(), name, value);
	}

	public static PlanType getItem(int value) {
		return (PlanType) PlanType.getResult(PlanType.class.getName(), value);
	}

	public static final PlanType ALL = new PlanType("全部", -1);

	public static final PlanType DG = new PlanType("代购", 1);

	public static final PlanType HM = new PlanType("合买", 2);

	public static List list = new ArrayList();
	public static List listplan=new ArrayList();

	static {
		list.add(DG);
		list.add(HM);
		
		listplan.add(ALL);
		listplan.add(DG);
		listplan.add(HM);
	}

}
