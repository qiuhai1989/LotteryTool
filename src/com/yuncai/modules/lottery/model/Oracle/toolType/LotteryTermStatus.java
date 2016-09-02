package com.yuncai.modules.lottery.model.Oracle.toolType;

import java.util.ArrayList;
import java.util.List;

import com.yuncai.core.hibernate.IntegerBeanLabelItem;

public class LotteryTermStatus extends IntegerBeanLabelItem {
	protected LotteryTermStatus(String name, int value) {
		super(LotteryTermStatus.class.getName(), name, value);
	}
	public static final LotteryTermStatus CANCLED = new LotteryTermStatus("已取消", -100);
	public static final LotteryTermStatus BEFORE_OPEN_SALE = new LotteryTermStatus("未开启", 0);
	public static final LotteryTermStatus OPEN = new LotteryTermStatus("已开启", 1);
	public static final LotteryTermStatus CLOSE = new LotteryTermStatus("关闭", 2);
	public static final LotteryTermStatus OPEN_PRIZE = new LotteryTermStatus("已开奖", 4);
	public static final LotteryTermStatus RETURN_PRIZE = new LotteryTermStatus("派奖", 5);
	public static final LotteryTermStatus RETURN_PRIZE_STOP = new LotteryTermStatus("派奖结束", 6);
	
	public static List<LotteryTermStatus> aleadyOpenPrize = new ArrayList<LotteryTermStatus>();//已开奖
	
	static{
		aleadyOpenPrize.add(OPEN_PRIZE);
		aleadyOpenPrize.add(RETURN_PRIZE);
	}

	public static LotteryTermStatus getItem(int value) {
		return (LotteryTermStatus) LotteryTermStatus.getResult(LotteryTermStatus.class.getName(), value);
	}

}
