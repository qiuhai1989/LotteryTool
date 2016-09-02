package com.yuncai.modules.lottery.service.oracleService.Impl.member;

import com.yuncai.modules.lottery.dao.oracleDao.member.MemberOperLineDAO;
import com.yuncai.modules.lottery.model.Oracle.MemberOperLine;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.oracleService.member.MemberOperLineService;

public class MemberOperLineServiceImpl extends BaseServiceImpl<MemberOperLine, Integer> implements MemberOperLineService {

	private MemberOperLineDAO memberOperLineDao;

	public void setMemberOperLineDao(MemberOperLineDAO memberOperLineDao) {
		this.memberOperLineDao = memberOperLineDao;
	}
}
