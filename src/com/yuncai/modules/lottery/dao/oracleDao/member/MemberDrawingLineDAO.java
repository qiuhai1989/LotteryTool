package com.yuncai.modules.lottery.dao.oracleDao.member;

import com.yuncai.modules.lottery.dao.GenericDao;
import com.yuncai.modules.lottery.model.Oracle.MemberDrawingLine;

public interface MemberDrawingLineDAO extends GenericDao<MemberDrawingLine, Integer> {
	public abstract void saves(MemberDrawingLine transientInstance);
	public abstract void update(MemberDrawingLine transientInstance);
}
