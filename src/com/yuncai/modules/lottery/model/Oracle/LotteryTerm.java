package com.yuncai.modules.lottery.model.Oracle;

import java.util.Date;

import com.yuncai.core.hibernate.CommonStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryTermStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;

public class LotteryTerm extends AbstractLotteryTerm  implements java.io.Serializable{
	// Constructors
	private Date hmEndDateTime; // 做为单式的截止时间

	/** default constructor */
	public LotteryTerm() {
	}

	/** minimal constructor */
	public LotteryTerm(Integer id, LotteryType lotteryType, String term, CommonStatus isAble, CommonStatus isCurrentTerm, LotteryTermStatus status) {
		super(id, lotteryType, term, isAble, isCurrentTerm, status);
	}

	/** full constructor */
	public LotteryTerm(Integer id, LotteryType lotteryType, String term, Date openDateTime, Date startDateTime, Date endDateTime,
			Date terminalEndDateTime, CommonStatus isAble, CommonStatus isCurrentTerm, LotteryTermStatus status, String result, String resultDetail,
			String awardPool) {
		super(id, lotteryType, term, openDateTime, startDateTime, endDateTime, terminalEndDateTime, isAble, isCurrentTerm, status, result,
				resultDetail, awardPool);
	}

}
