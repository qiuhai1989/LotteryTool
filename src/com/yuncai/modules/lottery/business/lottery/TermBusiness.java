package com.yuncai.modules.lottery.business.lottery;

import java.util.HashMap;

import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Sql.Isuses;

public interface TermBusiness {
	/**
	 * 获取开奖数据进行开奖
	 * @param term
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public HashMap getOpenInfoMapForCheckBingo(Isuses term,LotteryType type,String result,String ResultDetail) throws Exception ;

	/**
	 * 出票中奖的处理
	 * @param type
	 * @param intString
	 * @return
	 */
	public HashMap getOpenInfoTicket(LotteryType type,String intString);
}
