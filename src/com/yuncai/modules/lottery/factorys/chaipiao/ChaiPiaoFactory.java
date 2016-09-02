package com.yuncai.modules.lottery.factorys.chaipiao;
import java.util.*;

import com.yuncai.modules.lottery.model.Oracle.LotteryPlan;
import com.yuncai.modules.lottery.model.Oracle.Ticket;

public class ChaiPiaoFactory {
	
	private HashMap<Integer, ChaiPiao> piaoMap;

	@SuppressWarnings("unchecked")
	public List<Ticket> chaiPiao(int lotteryType, LotteryPlan lotteryPlan) throws Exception {
		return piaoMap.get(lotteryType).chaiPiao(lotteryPlan);
	}

	public void setPiaoMap(HashMap<Integer, ChaiPiao> piaoMap) {
		this.piaoMap = piaoMap;
	}

}
