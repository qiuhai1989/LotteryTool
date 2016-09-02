package com.yuncai.modules.lottery.model.Oracle.toolType;

import com.yuncai.core.hibernate.IntegerBeanLabelItem;
import java.util.*;

public class MemberOperType extends IntegerBeanLabelItem{
	private static final long serialVersionUID = -8621003869402167413L;

	protected MemberOperType(String name, int value) {
		super(MemberOperType.class.getName(), name, value);
	}

	public static MemberOperType getItem(int value) {
		return (MemberOperType) MemberOperType.getResult(MemberOperType.class.getName(), value);
	}

	public static final MemberOperType ALL = new MemberOperType("全部", -1);

	public static final MemberOperType REGISTER = new MemberOperType("注册", 1);

	public static final MemberOperType LOGIN = new MemberOperType("登录", 2);

	public static final MemberOperType ADD_MONEY = new MemberOperType("充值", 3);

	public static final MemberOperType DGCHOOSE = new MemberOperType("代购选中方案", 4);

	public static final MemberOperType CONSUME = new MemberOperType("消费", 5);

	public static final MemberOperType RETURN_PRIZE = new MemberOperType("返奖", 6);

	public static final MemberOperType RETURN_MONEY = new MemberOperType("退款", 7);

	public static final MemberOperType HMCHOOSE = new MemberOperType("发起合买方案", 9);

	public static final MemberOperType CYCHOOSE = new MemberOperType("参与合买方案", 10);

	public static final MemberOperType ZHCHOOSE = new MemberOperType("追号", 11);

	public static final MemberOperType CHASE_CANCEL = new MemberOperType("取消追号", 12);

	public static final MemberOperType JOIN_CANCEL = new MemberOperType("参与合买撤单", 13);

	public static final MemberOperType MUTI_CANCEL = new MemberOperType("发起合买撤单", 14);

	public static final MemberOperType GET_MONEY = new MemberOperType("提款", 15);

	public static final MemberOperType GET_PASSWORD = new MemberOperType("取回密码", 16);

	public static final MemberOperType TRY_REGISTER = new MemberOperType("尝试注册", 17);

	public static final MemberOperType TRY_ADD_MONEY = new MemberOperType("尝试充值", 18);

	public static final MemberOperType VISTER = new MemberOperType("访问", 19);
	public static final MemberOperType RE_VISTER = new MemberOperType("回访", 20);
	public static final MemberOperType RE_ADD_MONEY = new MemberOperType("再充值", 21);

	public static final MemberOperType ADD_MONEYR_2CP = new MemberOperType("2彩票充值", 30);

	public static final MemberOperType CYJIJIN = new MemberOperType("参与合基金", 31);

	public static final MemberOperType CYORDER = new MemberOperType("参与订餐方案", 32);
	
	public static final MemberOperType USERUPDATE = new MemberOperType("用户修改", 33);
	
	public static final MemberOperType USERSAVE = new MemberOperType("用户创建", 34);

	public static final MemberOperType BINDBANKCARD = new MemberOperType("用户绑定银行", 35);
	
	public static final MemberOperType CHARGE_PRESENT=new MemberOperType("充值赠送",45);
	
	public static final MemberOperType VIP_GIFT=new MemberOperType("赠送红包",46);
	
	public static final MemberOperType GIFT_BONUS = new MemberOperType("VIP返利红包",47);
	
	public static final MemberOperType APPLY_VIP = new MemberOperType("申请VIP",48);
	
	public static List list = new ArrayList();

	static {
		list.add(REGISTER);
		list.add(LOGIN);
		list.add(ADD_MONEY);
		list.add(DGCHOOSE);
		list.add(CONSUME);
		list.add(RETURN_PRIZE);
		list.add(RETURN_MONEY);
		list.add(HMCHOOSE);
		list.add(CYCHOOSE);
		list.add(ZHCHOOSE);
		list.add(CYJIJIN);
		list.add(CHASE_CANCEL);
		list.add(JOIN_CANCEL);
		list.add(MUTI_CANCEL);
		list.add(GET_MONEY);
		list.add(GET_PASSWORD);
		list.add(TRY_REGISTER);
		list.add(TRY_ADD_MONEY);
		list.add(VISTER);
		list.add(RE_VISTER);
		list.add(RE_ADD_MONEY);
		list.add(CYORDER);
		list.add(USERUPDATE);
		list.add(USERSAVE);
		list.add(BINDBANKCARD);
		list.add(CHARGE_PRESENT);
		// list.add(VISTER_WAP);
		// list.add(VISTER_FAG);
		// list.add(TRY_REGISTER_WAP);
		// list.add(TRY_REGISTER_FAG);
		// list.add(TRY_ADD_MONEY_WAP);
		// list.add(TRY_ADD_MONEY_FAG);
		// list.add(REGISTER_WAP);
		// list.add(REGISTER_FAG);
		// list.add(ADD_MONEY_WAP);
		// list.add(ADD_MONEYR_FAG);

	}

}
