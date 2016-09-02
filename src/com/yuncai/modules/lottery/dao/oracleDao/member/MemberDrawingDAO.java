package com.yuncai.modules.lottery.dao.oracleDao.member;

import com.yuncai.modules.lottery.dao.GenericDao;
import com.yuncai.modules.lottery.model.Oracle.MemberDrawing;


public interface MemberDrawingDAO extends GenericDao<MemberDrawing, Integer> {
	public abstract void saves(MemberDrawing transientInstance);
	public abstract void update(MemberDrawing transientInstance);
}
