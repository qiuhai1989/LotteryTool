package com.yuncai.modules.lottery.model.Oracle.toolType;

import com.yuncai.core.hibernate.IntegerBeanLabelItem;
import java.util.*;

public class JumpType extends IntegerBeanLabelItem {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected JumpType(String name, int value) {
		super(JumpType.class.getName(), name, value);
	}

	public static JumpType getItem(int value) {
		return (JumpType) JumpType.getResult(JumpType.class.getName(), value);
	}
	public static final JumpType XSLR = new JumpType("显示内容", 1);
	public static final JumpType HMFA = new JumpType("合买方案", 2);
	public static final JumpType CZ = new JumpType("充值", 3);
	public static final JumpType ZJD = new JumpType("中奖单", 4);
	public static final JumpType CAIZ = new JumpType("彩种", 5);
	public static final JumpType HDJM = new JumpType("活动界面", 6);
	public static final JumpType LLQ = new JumpType("调用浏览器访问URL", 7);
	public static final JumpType GRZX = new JumpType("个人中心页面", 8);
	public static final JumpType ZXSS = new JumpType("自选页面(实时显示)", 9);
	public static final JumpType ZXDS = new JumpType("自选页面(定时显示)", 10);
	public static List<JumpType> list = new ArrayList<JumpType>();
	static {
		list.add(HMFA);
		list.add(CZ);
		list.add(ZJD);
		list.add(CAIZ);
		list.add(HDJM);
		list.add(LLQ);
		list.add(GRZX);
		list.add(XSLR);
		list.add(ZXSS);
		list.add(ZXDS);
	}

	public static final JumpType ZN = new JumpType("站内",0);
	public static final JumpType ZW = new JumpType("站外",1);
	public static final JumpType WB = new JumpType("文本",2);
	public static List<JumpType> list1 = new ArrayList<JumpType>();
	static {
		list1.add(ZN);
		list1.add(ZW);
		list1.add(WB);
	}
	
	public static final JumpType ALL = new JumpType("全部",0);
	public static final JumpType QD = new JumpType("渠道",1);
	public static final JumpType YH = new JumpType("用户",2);
	public static List<JumpType> sendGroupList = new ArrayList<JumpType>();
	static {
		sendGroupList.add(ALL);
		sendGroupList.add(QD);
		sendGroupList.add(YH);
	}
}
