package com.yuncai.modules.lottery.service.oracleService.Impl.member;

import com.yuncai.modules.lottery.dao.oracleDao.member.MemberDrawingDAO;
import com.yuncai.modules.lottery.model.Oracle.MemberDrawing;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.oracleService.member.MemberDrawingService;

public class MemberDrawingServiceImpl extends BaseServiceImpl<MemberDrawing, Integer> implements MemberDrawingService{
	
	private MemberDrawingDAO memberDrawingDAO;

	public void setMemberDrawingDAO(MemberDrawingDAO memberDrawingDAO) {
		this.memberDrawingDAO = memberDrawingDAO;
	}
	

}
