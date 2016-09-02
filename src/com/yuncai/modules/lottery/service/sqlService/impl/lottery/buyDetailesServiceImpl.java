package com.yuncai.modules.lottery.service.sqlService.impl.lottery;

import java.util.List;

import com.yuncai.modules.lottery.dao.sqlDao.lottery.buyDetailesDAO;
import com.yuncai.modules.lottery.model.Sql.BuyDetails;
import com.yuncai.modules.lottery.model.Sql.Schemes;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.sqlService.lottery.buyDetailesService;

public class buyDetailesServiceImpl extends BaseServiceImpl<BuyDetails, Integer> implements buyDetailesService {
	
	private buyDetailesDAO sqlBuyDetailesDAO;

	public void setSqlBuyDetailesDAO(buyDetailesDAO sqlBuyDetailesDAO) {
		this.sqlBuyDetailesDAO = sqlBuyDetailesDAO;
	}

	public List<BuyDetails> findSuccessByPlanNo(final Integer planNo){
		return this.sqlBuyDetailesDAO.findSuccessByPlanNo(planNo);
	}
	

}
