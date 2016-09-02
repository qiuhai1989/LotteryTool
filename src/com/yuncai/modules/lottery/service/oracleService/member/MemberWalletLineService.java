package com.yuncai.modules.lottery.service.oracleService.member;

import java.util.Date;
import java.util.List;

import com.yuncai.modules.lottery.model.Oracle.MemberWalletLine;
import com.yuncai.modules.lottery.model.Oracle.toolType.WalletTransType;
import com.yuncai.modules.lottery.service.BaseService;

public interface MemberWalletLineService extends BaseService<MemberWalletLine, Integer> {


	/**查找对应类型的总额
	 * @param lines
	 * @return
	 */
	public Double countAmount(String account,Date startDate,Date endDate,List<WalletTransType> transTypes);
	
	public List<MemberWalletLine> findByAccountAndDate(String account,Date begin,Date end,Integer order,Integer pageSize,Date date,List<WalletTransType> transTypes);

	public List<MemberWalletLine> findByAccountAndDate(String account,Date begin,Date end,Integer pageSize,List<WalletTransType> transTypes,int pageNo);

	public List<Object[]>listMoney(String account,Date startDate,Date endDate);

}
