package com.yuncai.modules.lottery.model.Oracle.toolType;

import java.util.ArrayList;
import java.util.List;

import com.yuncai.core.hibernate.IntegerBeanLabelItem;

public class WalletTransType extends IntegerBeanLabelItem{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4374311954577256855L;

	protected WalletTransType(String name, int value) {
		super(WalletTransType.class.getName(), name, value);
	}

	public static WalletTransType getItem(int value) {
		return (WalletTransType) WalletTransType.getResult(WalletTransType.class.getName(), value);
	}

	public static final WalletTransType ALL = new WalletTransType("全部", -1);

	public static final WalletTransType CHARGE = new WalletTransType("网上充值", 0);
	public static final WalletTransType CASH_CHARGE = new WalletTransType("现金充值", 1000);
	public static final WalletTransType CHONGZHENG = new WalletTransType("冲正", -1000);//调账
	public static final WalletTransType CHONGFU = new WalletTransType("代充", 1100);//调账
	public static final WalletTransType PRESENT = new WalletTransType("赠送", 1);
	public static final WalletTransType PRESENT_REMOVE = new WalletTransType("彩金扣除", -1001);
	public static final WalletTransType REG_PRESENT = new WalletTransType("注册赠送", 1001);
	public static final WalletTransType CHARGE_PRESENT = new WalletTransType("充值赠送", 1002);
	public static final WalletTransType CARD_PRESENT = new WalletTransType("彩金卡赠送", 1003);
	public static final WalletTransType CONSUME_PRESENT = new WalletTransType("消费赠送", 1004);
	public static final WalletTransType ADVISE_PRESENT = new WalletTransType("提建议赠送", 1005);
	public static final WalletTransType ARTICLE_PRESENT = new WalletTransType("发稿赠送", 1006);
	public static final WalletTransType VIP_PRESENT = new WalletTransType("VIP红包赠送", 1007);
	
	public static final WalletTransType HM_RAISE_PRESENT = new WalletTransType("合买发单赠送", 1010);
	public static final WalletTransType JIFEN_EXCHANGE = new WalletTransType("积分兑换", 1011);
	
	public static final WalletTransType CHARE_PACKAGE_PRESENT = new WalletTransType("追号套餐赠送", 1014);
	
	public static final WalletTransType AWARD_PRESENT = new WalletTransType("加奖赠送", 1015);
	public static final WalletTransType TEST_PRESENT = new WalletTransType("体验赠送", 1016);
	public static final WalletTransType PRIVATE_PRESENT = new WalletTransType("公司账号充值", 1017);
	
	
	public static final WalletTransType CHEAP_PRESENT = new WalletTransType("优惠赠送", 1020);
	

	public static final WalletTransType CONSUME = new WalletTransType("消费", 30);
	
	public static final WalletTransType TELEFEE_CHARGE = new WalletTransType("话费充值", 3001);

	public static final WalletTransType FREEZE = new WalletTransType("冻结", 31);

	public static final WalletTransType UN_FREEZE = new WalletTransType("解冻", 32);

	public static final WalletTransType RETURN = new WalletTransType("退款", 34);

	public static final WalletTransType RETURN_PRIZE = new WalletTransType("返奖", 35);

	public static final WalletTransType SUBSTRACT = new WalletTransType("提款", 36);
	public static final WalletTransType BANKRETURN = new WalletTransType("银行退单", 37);
	public static final WalletTransType DEDUCTED = new WalletTransType("扣除手续费", 38);
	
	public static final WalletTransType RETURN_BONUS = new WalletTransType("VIP返利红包", 41);
	
	public static List<WalletTransType> list = new ArrayList<WalletTransType>();

	public static List<WalletTransType> outTypeList = new ArrayList<WalletTransType>(); // 交易类型属于支出的
	public static List<WalletTransType> chargeTypeList = new ArrayList<WalletTransType>(); // 交易类型属于充值的
	public static List<WalletTransType> consumeTypeList = new ArrayList<WalletTransType>(); // 交易类型属于消费的
	public static List<WalletTransType> returnTypeList = new ArrayList<WalletTransType>(); // 交易类型属于退款的
	public static List<WalletTransType> prizeTypeList = new ArrayList<WalletTransType>(); // 交易类型属于奖金的
	public static List<WalletTransType> presentTypeList = new ArrayList<WalletTransType>(); // 交易类型属于赠送的
	public static List<WalletTransType> TikuanTypeList = new ArrayList<WalletTransType>(); // 交易类型属于提款的
	public static List<WalletTransType> TikuanReturnTypeList = new ArrayList<WalletTransType>(); // 交易类型属于提款退款的
	public static List<WalletTransType> incomeList = new ArrayList<WalletTransType>();//交易类型属于收入的
	
	static {
		list.add(ALL);
		list.add(CHARGE);
		list.add(CASH_CHARGE);
		list.add(CHONGZHENG);
		list.add(CHONGFU);

		list.add(PRESENT);
		list.add(REG_PRESENT);
		list.add(CHARGE_PRESENT);
		
		list.add(CARD_PRESENT);
		list.add(CONSUME_PRESENT);
		list.add(ADVISE_PRESENT);
		list.add(ARTICLE_PRESENT);

	
		list.add(HM_RAISE_PRESENT);
		list.add(JIFEN_EXCHANGE);

		
		list.add(CHARE_PACKAGE_PRESENT);
		list.add(AWARD_PRESENT);
		list.add(TEST_PRESENT);
		
		list.add(CHEAP_PRESENT);
		
		list.add(CONSUME);
		list.add(FREEZE);
		list.add(UN_FREEZE);
		list.add(TELEFEE_CHARGE);
		list.add(RETURN);
		list.add(RETURN_PRIZE);
		list.add(SUBSTRACT);
		list.add(BANKRETURN);
		list.add(PRESENT_REMOVE);
		
		
	
		
		//逻辑类型
		//支出类型
		outTypeList.add(WalletTransType.CHONGZHENG);
		outTypeList.add(WalletTransType.PRESENT_REMOVE);
		
		outTypeList.add(WalletTransType.TELEFEE_CHARGE);
		outTypeList.add(WalletTransType.FREEZE);
		outTypeList.add(WalletTransType.SUBSTRACT);
		outTypeList.add(WalletTransType.CONSUME);
		outTypeList.add(WalletTransType.DEDUCTED);
		//充值的类型
		chargeTypeList.add(WalletTransType.CHARGE);
		chargeTypeList.add(WalletTransType.CASH_CHARGE);
		//消费的类型
		consumeTypeList.add(WalletTransType.CONSUME);
		consumeTypeList.add(WalletTransType.TELEFEE_CHARGE);
		//退款的类型
		returnTypeList.add(WalletTransType.RETURN);
		
		//奖金的类型
		prizeTypeList.add(WalletTransType.RETURN_PRIZE);
		
		//赠送的类型
		presentTypeList.add(WalletTransType.PRESENT);
		presentTypeList.add(WalletTransType.REG_PRESENT);
		presentTypeList.add(WalletTransType.CHARGE_PRESENT);
		presentTypeList.add(WalletTransType.CARD_PRESENT);
		presentTypeList.add(WalletTransType.CONSUME_PRESENT);
		presentTypeList.add(WalletTransType.ADVISE_PRESENT);
		presentTypeList.add(WalletTransType.ARTICLE_PRESENT);
		
		presentTypeList.add(WalletTransType.HM_RAISE_PRESENT);
		presentTypeList.add(WalletTransType.JIFEN_EXCHANGE);
		
		presentTypeList.add(WalletTransType.CHARE_PACKAGE_PRESENT);
		presentTypeList.add(WalletTransType.AWARD_PRESENT);
		presentTypeList.add(WalletTransType.TEST_PRESENT);
		presentTypeList.add(WalletTransType.CHEAP_PRESENT);
		presentTypeList.add(WalletTransType.VIP_PRESENT);
		presentTypeList.add(WalletTransType.RETURN_BONUS);
		
		//提款的
		TikuanTypeList.add(WalletTransType.SUBSTRACT);
		//提款退单的
		TikuanReturnTypeList.add(WalletTransType.BANKRETURN);
		
		//收入类型的
		incomeList.add(WalletTransType.UN_FREEZE);
		incomeList.addAll(chargeTypeList);
		incomeList.addAll(returnTypeList);
		incomeList.addAll(prizeTypeList);
		incomeList.addAll(presentTypeList);
		

	}

}
