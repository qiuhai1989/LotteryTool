package com.yuncai.modules.lottery.service.oracleService.Impl.member;

import com.yuncai.modules.lottery.dao.oracleDao.member.MemberScoreLineDAO;
import com.yuncai.modules.lottery.model.Oracle.MemberScoreLine;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.oracleService.member.MemberScoreLineService;

public class MemberScoreLineServiceImpl extends BaseServiceImpl<MemberScoreLine, Integer> implements MemberScoreLineService {

	private MemberScoreLineDAO memberScoreLineDao;

	public void setMemberScoreLineDao(MemberScoreLineDAO memberScoreLineDao) {
		this.memberScoreLineDao = memberScoreLineDao;
	}
}
