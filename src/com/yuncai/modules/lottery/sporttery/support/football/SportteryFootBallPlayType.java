package com.yuncai.modules.lottery.sporttery.support.football;

import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.sporttery.OptionItem;

public enum SportteryFootBallPlayType {
	
	SPF("胜平负", OptionItemSPF.values(), 6, LotteryType.JC_ZQ_SPF), BF("比分", OptionItemBF.values(), 3, LotteryType.JC_ZQ_BF), JQS("进球数", OptionItemJQS
			.values(), 6, LotteryType.JC_ZQ_JQS), BQC("半全场", OptionItemBQC.values(), 3, LotteryType.JC_ZQ_BQC);

	private final String text;

	private final OptionItem[] allOptionItem;

	private final int maxMatchs;

	private LotteryType lotteryType;

	private SportteryFootBallPlayType(String text, OptionItem[] allOptionItem, int maxMatchs, LotteryType type) {
		this.text = text;
		this.allOptionItem = allOptionItem;
		this.maxMatchs = maxMatchs;
		this.lotteryType = type;
	}

	public String getText() {
		return text;
	}

	public OptionItem[] getAllOptionItem() {
		return allOptionItem;
	}

	public int getMaxMatchs() {
		return maxMatchs;
	}

	public LotteryType getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(LotteryType lotteryType) {
		this.lotteryType = lotteryType;
	}

	public static SportteryFootBallPlayType getByLotteryType(LotteryType type) {
		for (SportteryFootBallPlayType t : SportteryFootBallPlayType.values()) {
			if (t.lotteryType.equals(type))
				return t;
		}
		throw new RuntimeException("玩法类型传递出错");
	}


}
