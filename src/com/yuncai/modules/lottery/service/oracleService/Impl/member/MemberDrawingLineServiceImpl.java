package com.yuncai.modules.lottery.service.oracleService.Impl.member;

import com.yuncai.modules.lottery.dao.oracleDao.member.MemberDrawingLineDAO;
import com.yuncai.modules.lottery.model.Oracle.MemberDrawingLine;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.oracleService.member.MemberDrawingLineService;

public class MemberDrawingLineServiceImpl extends BaseServiceImpl<MemberDrawingLine, Integer> implements MemberDrawingLineService {
	private MemberDrawingLineDAO memberDrawingLineDAO;

	public void setMemberDrawingLine(MemberDrawingLine memberDrawingLine) {
		this.memberDrawingLineDAO = memberDrawingLineDAO;
	}
	
}
