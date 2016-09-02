package com.yuncai.modules.lottery.dao.oracleDao.member;

import java.util.Date;
import java.util.List;


import com.yuncai.modules.lottery.action.member.WalletLineSearch;
import com.yuncai.modules.lottery.dao.GenericDao;
import com.yuncai.modules.lottery.model.Oracle.MemberWalletLine;
import com.yuncai.modules.lottery.model.Oracle.toolType.WalletTransType;

public interface MemberWalletLineDAO extends GenericDao<MemberWalletLine, Integer>{

	/**查找对应类型的总额
	 * @param lines
	 * @return
	 */
	public Double countAmount(String account,Date startDate,Date endDate,List<WalletTransType> transTypes);

	public List<MemberWalletLine> findByAccountAndDate(String account,Date begin,Date end,Integer order,Integer pageSize,Date date,List<WalletTransType> transTypes);

	public List<MemberWalletLine> findByAccountAndDate(String account,Date begin,Date end,Integer pageSize,List<WalletTransType> transTypes,int pageNo);
	
	public List findBySelectStatus(final WalletLineSearch search);
	public List<Object[]>listMoney(String account,Date startDate,Date endDate);
}
