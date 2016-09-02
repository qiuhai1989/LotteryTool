package com.yuncai.modules.lottery.dao.sqlDao.lottery;

import java.util.List;

import com.yuncai.modules.lottery.dao.GenericDao;
import com.yuncai.modules.lottery.model.Sql.BuyDetails;

public interface buyDetailesDAO extends GenericDao<BuyDetails, Integer>{
	public List<BuyDetails> findSuccessByPlanNo(final Integer planNo);
}
