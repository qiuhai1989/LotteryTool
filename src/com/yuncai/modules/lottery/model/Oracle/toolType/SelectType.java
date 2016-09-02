package com.yuncai.modules.lottery.model.Oracle.toolType;

import java.util.ArrayList;
import java.util.List;

import com.yuncai.core.hibernate.IntegerBeanLabelItem;

public class SelectType extends IntegerBeanLabelItem{
	protected SelectType(String name, int value) {
		super(SelectType.class.getName(), name, value);
	}

	public static SelectType getItem(int value) {
		return (SelectType) SelectType.getResult(SelectType.class.getName(), value);
	}

	public static final SelectType ALL = new SelectType("全部方式", -1);
	public static final SelectType UPLOAD = new SelectType("文件上传", 1);

	public static final SelectType CHOOSE_SELF = new SelectType("自选", 2);
	
	public static final SelectType YUTOU=new SelectType("预头",3);
	
	public static List list = new ArrayList();

	static {
		list.add(UPLOAD);
		list.add(CHOOSE_SELF);
	}

}
