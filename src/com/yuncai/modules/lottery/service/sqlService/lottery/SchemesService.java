package com.yuncai.modules.lottery.service.sqlService.lottery;



import com.yuncai.modules.lottery.model.Sql.Schemes;
import com.yuncai.modules.lottery.service.BaseService;

public interface SchemesService extends BaseService<Schemes, Integer>{
	public Schemes findSchemeByPlanNOForUpdate(final Integer planNo);
	
//	public Schemes findSchemesByycSchemeNumber(final Integer ycSchemeNumber );
}
