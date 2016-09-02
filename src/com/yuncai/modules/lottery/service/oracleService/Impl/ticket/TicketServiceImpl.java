package com.yuncai.modules.lottery.service.oracleService.Impl.ticket;

import com.yuncai.core.common.Constant;
import com.yuncai.core.common.DbDataVerify;

import com.yuncai.modules.lottery.dao.oracleDao.lottery.LotteryPlanDAO;
import com.yuncai.modules.lottery.dao.oracleDao.ticket.TicketDAO;
import com.yuncai.modules.lottery.dao.sqlDao.lottery.IsusesDAO;

import com.yuncai.modules.lottery.factorys.chaipiao.ChaiPiaoFactory;
import com.yuncai.modules.lottery.factorys.checkBingo.BingoCheck;
import com.yuncai.modules.lottery.factorys.chupiao.chuPiao;
import com.yuncai.modules.lottery.model.Oracle.LotteryPlan;
import com.yuncai.modules.lottery.model.Oracle.Ticket;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanTicketStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.TicketProvider;
import com.yuncai.modules.lottery.model.Oracle.toolType.TicketStatus;
import com.yuncai.modules.lottery.model.Sql.Isuses;

import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.oracleService.ticket.TicketService;
import com.yuncai.modules.lottery.software.service.HeadResposnseBean;

import java.io.Reader;
import java.io.StringReader;
import java.util.*;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class TicketServiceImpl extends BaseServiceImpl<Ticket, Integer> implements TicketService {
	
	private HashMap<String, String> ticketbingoCheckMap;
	
	private TicketDAO ticketDAO;
	private ChaiPiaoFactory chaiPiaoFactory;
	private LotteryPlanDAO lotteryPlanDAO;
	private IsusesDAO sqlIsusesDAO;
	private chuPiao chuPiao;

	/**
	 * 拆票
	 */
	public void chaipiao(LotteryPlan lotteryPlan) throws Exception {
		List<Ticket> ticketList = chaiPiaoFactory.chaiPiao(lotteryPlan.getLotteryType().getValue(), lotteryPlan);
		for (Ticket ticket : ticketList) {

			String hmAccount = Constant.VirtualAccount;

			if (hmAccount.indexOf("," + ticket.getAccount() + ",") >= 0) {
				ticket.setStatus(TicketStatus.CPCG);
			}

			ticket.setProvider(TicketProvider.ALL);
			//备份拆票内容
			ticket.setBoZhongContent(ticket.getContent());
			ticketDAO.save(ticket);
		}
		// 锁定记录，防止并发重入
		lotteryPlan = lotteryPlanDAO.findByIdForUpdate(lotteryPlan.getPlanNo());
		if (lotteryPlan.getPlanTicketStatus().getValue() == PlanTicketStatus.TICKETING.getValue()) {
			// 已经拆过票了
			return;
		}

		// 修改方案状态
		lotteryPlan.setPlanTicketStatus(PlanTicketStatus.TICKETING);
		lotteryPlan.setVerifyMd5(DbDataVerify.getLotteryPlanVerify(lotteryPlan));

		// ---------------配合流程开奖(送票OK到时候调整回来)------------------
		//lotteryPlan.setPlanStatus(PlanStatus.TICKET_OUT);
		// ----------------------end----------
		lotteryPlanDAO.saveOrUpdate(lotteryPlan);
		// ---------------------更改sql状态:-----------------------------
	}

	@Override
	public List<Ticket> findByStatusAndlotteryType(LotteryType lotteryType, TicketStatus status) {
		Isuses lotteryTerm = sqlIsusesDAO.findPrintingTermByLotteryType(lotteryType);
		List<Ticket> ticketList = ticketDAO.findByStatusAndlotteryType(String.valueOf(lotteryType.getValue()), status, lotteryTerm);
		return ticketList;
	}

	/**
	 * 全网送票
	 */
	public void songPiaoFullNet(List<Ticket> tickets) throws Exception {
		String result=chuPiao.send(tickets);
		
//		if (!"".equals(result)){
//			SAXBuilder builder = new SAXBuilder();
//			Document doc = null;
//			Reader in = new StringReader(result);
//			
//			Element root = doc.getRootElement();
//			HeadResposnseBean head = new HeadResposnseBean();
//			head.elementToObje(root.getChild("head"));
//			Element body = root.getChild("body");
//
//		}
				
		
	}

	/**
	 * 全网检票
	 */
	public void checkPiaoFullNet(Ticket ticket) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * 全网送票(竞彩)
	 */
	@Override
	public void songPiaoFullNetJC(Ticket ticket) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * 全网检票(竞彩)
	 */
	@Override
	public void checkPiaoFullNetJC(Ticket ticket) throws Exception {
		// TODO Auto-generated method stub

	}
	

	/**
	 *出票成功开奖处理
	 * @param openResultMap
	 * @param lotteryPlan
	 * @return
	 * @throws Exception
	 */
	public String TicketCheckBingo(HashMap<String, String> openResultMap, Ticket t,LotteryType lotteryType)throws Exception{
		String ticketprize=null;
		String clazzString = ticketbingoCheckMap.get(lotteryType.getValue() + "");
		{
			BingoCheck check = (BingoCheck) Class.forName(clazzString).newInstance();
			String tempContent=null;
			if(LotteryType.JCZQList.contains(t.getLotteryType()) || LotteryType.JCLQList.contains(t.getLotteryType())){
				tempContent=t.getContent();
			}
			check.execute(tempContent, t.getPlayType(), t.getMultiple(), openResultMap);
			ticketprize=check.getTicketCountPrize();
		}
		return ticketprize;
	}

	public void setTicketDAO(TicketDAO ticketDAO) {
		this.ticketDAO = ticketDAO;
	}

	public void setChaiPiaoFactory(ChaiPiaoFactory chaiPiaoFactory) {
		this.chaiPiaoFactory = chaiPiaoFactory;
	}

	public void setLotteryPlanDAO(LotteryPlanDAO lotteryPlanDAO) {
		this.lotteryPlanDAO = lotteryPlanDAO;
	}

	public void setSqlIsusesDAO(IsusesDAO sqlIsusesDAO) {
		this.sqlIsusesDAO = sqlIsusesDAO;
	}

	public void setChuPiao(chuPiao chuPiao) {
		this.chuPiao = chuPiao;
	}

	public HashMap<String, String> getTicketbingoCheckMap() {
		return ticketbingoCheckMap;
	}

	public void setTicketbingoCheckMap(HashMap<String, String> ticketbingoCheckMap) {
		this.ticketbingoCheckMap = ticketbingoCheckMap;
	}

	


}
