package com.yuncai.modules.lottery.factorys.chaipiao;
import java.util.*;

import com.yuncai.modules.lottery.model.Oracle.LotteryPlan;

public interface ChaiPiao {
	/**
	 * 拆票
	 * 
	 * @param lotteryPlan
	 * 
	 */
	public List chaiPiao(LotteryPlan lotteryPlan) throws Exception;

}
