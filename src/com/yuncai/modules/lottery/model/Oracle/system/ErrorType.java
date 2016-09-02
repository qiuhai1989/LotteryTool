package com.yuncai.modules.lottery.model.Oracle.system;

import com.yuncai.core.hibernate.IntegerBeanLabelItem;
import java.util.*;
/**
 * 错误类别
 * @author lm
 *
 */

public class ErrorType extends IntegerBeanLabelItem{
	private static final long serialVersionUID = 1L;

	protected ErrorType(String name, int value) {
		super(ErrorType.class.getName(), name, value);
	}
	
	public static ErrorType getItem(int value) {
		return (ErrorType) ErrorType.getResult(ErrorType.class.getName(), value);
	}
	
	public final static ErrorType ALL = new ErrorType("全部", -1);
	//后端事件
	public final static ErrorType 彩期守护 = new ErrorType("彩期守护", 2000);
	public final static ErrorType 拆票 = new ErrorType("拆票", 2010);
	public final static ErrorType 送票 = new ErrorType("送票", 2020);
	public final static ErrorType 查票 = new ErrorType("查票", 2030);
	public final static ErrorType 检票 = new ErrorType("检票", 2040);
	public final static ErrorType 彩果抓取 = new ErrorType("彩果抓取", 2050);
	public final static ErrorType 开奖 = new ErrorType("开奖", 2060);
	public final static ErrorType 流单开奖 = new ErrorType("流单开奖", 2061);
	public final static ErrorType 派奖 = new ErrorType("派奖", 2070);
	public final static ErrorType 追号 = new ErrorType("追号", 2080);
	
	//前端事件
	public final static ErrorType 注册 = new ErrorType("注册", 1000);
	public final static ErrorType 充值 = new ErrorType("充值", 1010);
	public final static ErrorType 购彩 = new ErrorType("购彩", 1020);
	public final static ErrorType 提现 = new ErrorType("提现", 1030);
	public final static ErrorType VIP提现 = new ErrorType("VIP提现", 1040);
	
	public static List<ErrorType> list = new ArrayList<ErrorType>();
	public static List<ErrorType> focusList = new ArrayList<ErrorType>();
	static {
		list.add(ErrorType.ALL);
		//后端事件
		list.add(ErrorType.彩期守护);
		list.add(ErrorType.拆票);
		list.add(ErrorType.送票);
		list.add(ErrorType.查票);
		list.add(ErrorType.检票);
		list.add(ErrorType.彩果抓取);
		list.add(ErrorType.开奖);
		list.add(ErrorType.流单开奖);
		list.add(ErrorType.派奖);
		list.add(ErrorType.追号);
		//前端事件
		list.add(ErrorType.注册);
		list.add(ErrorType.充值);
		list.add(ErrorType.购彩);
		list.add(ErrorType.提现);
		list.add(ErrorType.VIP提现);
		focusList.addAll(list);
		focusList.remove(ErrorType.流单开奖);
		
	}

}
