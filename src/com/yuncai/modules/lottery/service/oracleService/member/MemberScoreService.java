package com.yuncai.modules.lottery.service.oracleService.member;

import com.yuncai.core.longExce.ServiceException;
import com.yuncai.modules.lottery.model.Oracle.Member;
import com.yuncai.modules.lottery.model.Oracle.MemberScore;
import com.yuncai.modules.lottery.service.BaseService;

public interface MemberScoreService extends BaseService<MemberScore, Integer> {
	public MemberScore findByAccount(Object account);

	public void increaseScore(Member member, Integer value, Integer scoreType,
			Integer planNo, Integer relatedUserID, String remark)
			throws ServiceException;

}
