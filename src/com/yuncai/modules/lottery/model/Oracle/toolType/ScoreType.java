package com.yuncai.modules.lottery.model.Oracle.toolType;

import java.util.ArrayList;
import java.util.List;

import com.yuncai.core.hibernate.IntegerBeanLabelItem;

public class ScoreType extends IntegerBeanLabelItem {
	protected ScoreType(String name, int value) {
		super(ScoreType.class.getName(), name, value);
	}

	public static final ScoreType ALL = new ScoreType("所有类型", -1); // 买彩票时送的积分
	public static final ScoreType CONSUME = new ScoreType("消费赠送", 1); // 买彩票时送的积分
	public static final ScoreType CONSUME_CANCEL = new ScoreType("消费退款", 2); // 退款时扣除积分
	public static final ScoreType JIFEN_EXCHANGE = new ScoreType("积分兑换", 3);	
	public static final ScoreType CONSUME_TAOCAN = new ScoreType("积分买套餐", 4);
	
	public static List<ScoreType> CONSUMEList = new ArrayList<ScoreType>(); // 支出集合
	public static List<ScoreType> INCOMEList = new ArrayList<ScoreType>(); // 收入集合
	
	static{
		CONSUMEList.add(ScoreType.CONSUME_TAOCAN);
		CONSUMEList.add(ScoreType.JIFEN_EXCHANGE);
		CONSUMEList.add(ScoreType.CONSUME_CANCEL);
		
		
		INCOMEList.add(ScoreType.CONSUME_CANCEL);
		INCOMEList.add(ScoreType.CONSUME);
		
	}


	public static ScoreType getItem(int value) {
		return (ScoreType) ScoreType.getResult(ScoreType.class.getName(), value);
	}

	public static final List list = new ArrayList();

	static {
		list.add(ALL);
		list.add(CONSUME);
		list.add(CONSUME_CANCEL);
		list.add(JIFEN_EXCHANGE);
		list.add(CONSUME_TAOCAN);
		
	}

}
