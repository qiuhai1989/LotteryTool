package com.yuncai.modules.lottery.model.Oracle.toolType;
import com.yuncai.core.hibernate.IntegerBeanLabelItem;
import java.util.*;

public class FromPage extends IntegerBeanLabelItem{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8492070074632578571L;

	protected FromPage(String name, int value) {
		super(FromPage.class.getName(), name, value);
	}

	public static final FromPage INDEX = new FromPage("首页", 0);

	public static final List<FromPage> list = new ArrayList<FromPage>();

	public static final Map<Integer, FromPage> map = new HashMap<Integer, FromPage>();

	static {
		list.add(INDEX);
		map.put(INDEX.getValue(), INDEX);
	}
}
