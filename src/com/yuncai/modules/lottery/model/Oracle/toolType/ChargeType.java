package com.yuncai.modules.lottery.model.Oracle.toolType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yuncai.core.hibernate.IntegerBeanLabelItem;

public class ChargeType extends IntegerBeanLabelItem  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1410849485815957495L;

	protected ChargeType(String name, int value) {
		super(ChargeType.class.getName(), name, value);
	}

	public static ChargeType getItem(int value) {
		return (ChargeType) ChargeType.getResult(ChargeType.class.getName(), value);
	}

	public static final ChargeType ALL = new ChargeType("全部类型", 0);

	public static final ChargeType ZFB = new ChargeType("支付宝", 1);

	public static final ChargeType CFT = new ChargeType("财付通", 2);

	public static final ChargeType CMM = new ChargeType("移动支付", 3);
	
	public static final ChargeType XFB = new ChargeType("银联",4);	

	
	public static final ChargeType SZC = new ChargeType("手机卡充值",5);
	
	public static final ChargeType CARD=new ChargeType("彩金卡充值",6);

	public static final List list = new ArrayList();
	
	public static Map<Integer,ChargeType> changeMap = new HashMap<Integer,ChargeType>(); //转换

	static {
		list.add(ALL);
		list.add(ZFB);
		list.add(CFT);
		list.add(CMM);
		list.add(CARD);
		
		changeMap.put(1, XFB);
		changeMap.put(2, ZFB);
		changeMap.put(3, CFT);
		changeMap.put(4, CMM);
		changeMap.put(5, SZC);
		changeMap.put(6, CARD);

	}


}
