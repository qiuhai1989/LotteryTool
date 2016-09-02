package com.yuncai.modules.lottery.dao.sqlDao.lottery;

import java.util.Date;
import java.util.List;

import com.yuncai.modules.lottery.dao.GenericDao;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Sql.Isuses;

public interface IsusesDAO extends GenericDao<Isuses, Integer>{
	
	public Isuses findByLotteryTypeAndTerm(LotteryType lotType,String term);
	
	public Isuses findByIsusesTerm(LotteryType lottery,String termID);
	/**
	 * 获取下一期的彩期
	 * @param lottery
	 * @param term
	 * @return
	 */
	public Isuses findNextByLotteryTypeAndTerm(LotteryType lottery,String term);
	
	/**获取当前彩期
	 * @param lotteryType
	 * @param nowDate
	 * @return
	 */
	public Isuses findCurrentTermByDate(LotteryType lotteryType,Date nowDate);

	/**获取上一期的彩期
	 * @param lotteryType
	 * @param name
	 * @return
	 */
	public Isuses findPreByLotteryTypeAndTerm(LotteryType lotteryType,
			String name);
	
	/**获取彩期已过，未派将彩期
	 * @param lotteryType
	 * @return
	 */
	public List<Isuses> findOverdueByDateAndUnReturnPrizeTerm(
			LotteryType lotteryType);
	
	/**重新初始化当前彩期，清除所有当前期
	 * @param lotteryType
	 */
	public void initCurrentFlag(LotteryType lotteryType);

	
	/**
	 * 按彩种找出当前期出票期
	 * 
	 * @param lotteryType
	 * @param term
	 * @return
	 */
	public Isuses findPrintingTermByLotteryType(final LotteryType lotteryType);

	
	/**开奖历史查询
	 * @param lotteryId 彩种id
	 * @param term
	 * @param offset 设置页数 默认为1
	 * @param order 设置按彩期传进行查询上下级的数据了 默认为0向上，1向下
	 * @param pageSize
	 * @return
	 */
	public List<Isuses> FindPrizeHistory(Integer lotteryId,String term,Integer offset,Integer order,Integer pageSize);

	
	/**获取所有彩种或指定彩种当前彩期
	 * @param lotteryType
	 * @return
	 */
	public List<Isuses> findCurrentTermList(Integer lotteryType);
	
	
	/**获取所有彩种或指定彩种当前彩期
	 * @param lotteryType
	 * @return
	 */
	public List<Isuses> findCurrentTermList(List<Integer>list);
	/**获取所有足彩，篮彩彩种当前彩期
	 * @param lotteryType
	 * @return
	 */
	public List<Isuses> findCurrentTermList2();	
	
	/**获取从当前期开始到未来 N期的集合
	 * @param lotteryId
	 * @param count N=count
	 * @return
	 */
	public List<Isuses> findAfterTerms(Integer lotteryId,Integer count,String currentTerm);

	/**获取前100期彩期信息
	 * @param LotteryType
	 * @return
	 */
	public List<Isuses> findPre100OpenPrizeTerm(LotteryType lotteryType);
	
}
