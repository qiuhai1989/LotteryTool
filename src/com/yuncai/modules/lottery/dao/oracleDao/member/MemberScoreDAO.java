package com.yuncai.modules.lottery.dao.oracleDao.member;

import com.yuncai.modules.lottery.dao.GenericDao;
import com.yuncai.modules.lottery.model.Oracle.MemberScore;
import java.util.*;

public interface MemberScoreDAO extends GenericDao<MemberScore, Integer> {
	public void attachClean(MemberScore instance);
	public MemberScore findByMemberId(final Integer memberId);//按照会员ID查找积分明细
	
	public abstract MemberScore findByIdForUpdate(Integer id);

}
