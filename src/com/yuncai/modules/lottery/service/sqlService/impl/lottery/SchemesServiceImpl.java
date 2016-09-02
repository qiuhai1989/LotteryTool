package com.yuncai.modules.lottery.service.sqlService.impl.lottery;
import com.yuncai.modules.lottery.dao.sqlDao.lottery.SchemesDAO;
import com.yuncai.modules.lottery.model.Sql.Schemes;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.sqlService.lottery.SchemesService;

public class SchemesServiceImpl extends BaseServiceImpl<Schemes, Integer> implements SchemesService{
	private SchemesDAO sqlSchemesDAO;

	public void setSqlSchemesDAO(SchemesDAO sqlSchemesDAO) {
		this.sqlSchemesDAO = sqlSchemesDAO;
	}

	@Override
	public Schemes findSchemeByPlanNOForUpdate(Integer planNo) {
		
		return this.sqlSchemesDAO.findSchemeByPlanNOForUpdate(planNo);
	}


}
