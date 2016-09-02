package com.yuncai.modules.lottery.model.Oracle.toolType;

import java.util.ArrayList;
import java.util.List;

import com.yuncai.core.hibernate.IntegerBeanLabelItem;

public class PageType  extends IntegerBeanLabelItem  {
	protected PageType(String name, int value) {
		super(PageType.class.getName(), name, value);
	}
	public static PageType getItem(int value) {
		return (PageType) PageType.getResult(PageType.class.getName(), value);
	}
	
	public final static PageType NENO = new PageType("全部", -1);
	public final static PageType INDEX = new PageType("首页", 1);
	public final static PageType LOTTERY = new PageType("购彩页面", 2);
	public final static PageType CHARGE = new PageType("充值页面", 3);
	public final static PageType JOIN = new PageType("合买跟单页面", 4);
	public final static PageType DRAWIING = new PageType("提款页面", 5); 
	
	public static List<PageType> list = new ArrayList<PageType>();
	
	static{
		list.add(NENO);
		list.add(INDEX);
		list.add(LOTTERY);
		list.add(CHARGE);
		list.add(JOIN);
		list.add(DRAWIING);
	}
	
}
