package com.yuncai.modules.lottery.dao.sqlDao.lottery;

import com.yuncai.modules.lottery.dao.GenericDao;
import com.yuncai.modules.lottery.model.Sql.Schemes;

public interface SchemesDAO extends GenericDao<Schemes, Integer> {

	public Schemes findSchemeByPlanNOForUpdate(final Integer planNo);
//	public Schemes findSchemesByycSchemeNumber(final Integer ycSchemeNumber );
}
