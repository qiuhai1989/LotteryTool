package com.yuncai.modules.lottery.dao.oracleDao.member;

import com.yuncai.modules.lottery.dao.GenericDao;
import com.yuncai.modules.lottery.model.Oracle.MemberChargeLine;

public interface MemberChargeLineDAO extends GenericDao<MemberChargeLine, String>{
	public MemberChargeLine findByIdForUpdate(java.lang.String id);
	
	public void saveNumber(MemberChargeLine transientInstance);

}
