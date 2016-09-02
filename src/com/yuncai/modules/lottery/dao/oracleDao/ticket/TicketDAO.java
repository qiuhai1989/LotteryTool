package com.yuncai.modules.lottery.dao.oracleDao.ticket;

import java.util.List;

import com.yuncai.modules.lottery.dao.GenericDao;
import com.yuncai.modules.lottery.model.Oracle.LotteryPlan;
import com.yuncai.modules.lottery.model.Oracle.Ticket;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Oracle.toolType.TicketProvider;
import com.yuncai.modules.lottery.model.Oracle.toolType.TicketStatus;
import com.yuncai.modules.lottery.model.Sql.Isuses;



public interface TicketDAO extends GenericDao<Ticket, Integer>{
	public static final String PLAN_NO = "planNo";
	public static final String CONTENT = "content";
	public static final String TERM = "term";
	public static final String LOTTERY_TYPE = "lotteryType";
	public static final String AMOUNT = "amount";
	public static final String MULTIPLE = "multiple";
	public static final String PLAY_TYPE = "playType";
	public static final String IS_BINGO = "isBingo";
	public static final String BINGO_AMOUNT = "bingoAmount";
	public static final String BINGO_CONTENT = "bingoContent";
	public static final String STATUS = "status";
	public static final String ADD_ATTRIBUTE = "addAttribute";
	public static final String ACCOUNT = "account";
	public static final String OUT_ID = "outId";
	public static final String NO_IN_BATCH = "noInBatch";
	public static final String BATCH_NO = "batchNo";
	public static final String IS_CONVERT = "isConvert";

	
	public abstract List findByPlanNo(Object planNo);
	/***************************************************************************
	 * 
	 * 
	 * 根据菜种和打票地点的id找到未分配到打票地点的票
	 * 
	 **************************************************************************/
	public List<Ticket> findByStatusAndlotteryType(final String lotteryType, final TicketStatus status, final Isuses nonceTerm);

	

	/**
	 * 送票后更新ticket 的状态 和送票时间
	 * 
	 * @param ticketIds
	 */
	public void updateStatusForDeliver(final Integer[] ticketIds, final TicketProvider provider);
	
	/**
	 * 送票后更新ticket 的状态 和送票时间，出票时间
	 * 
	 * @param ticketIds
	 */
	public void updateStatusForDeliverSuccess(final Integer[] ticketIds, final TicketProvider provider);
	
	public void updateFailure(final Integer id);
	
	
	/**
	 * 未出票作废更改状态
	 */
	public void updateByAbortUnPrint(final Integer planNo);
	
	/**
	 * 查找出当前方案的所有票
	 * @param lotteryPlan
	 * @return
	 */
	public List<Ticket> findTicketByLotteryPlan(LotteryPlan lotteryPlan);

}
