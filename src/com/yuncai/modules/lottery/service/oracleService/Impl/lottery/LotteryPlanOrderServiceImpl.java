package com.yuncai.modules.lottery.service.oracleService.Impl.lottery;

import java.util.Date;
import java.util.List;

import com.yuncai.modules.lottery.bean.vo.HmShowBean;
import com.yuncai.modules.lottery.dao.oracleDao.lottery.LotteryPlanOrderDAO;
import com.yuncai.modules.lottery.model.Oracle.LotteryPlanOrder;
import com.yuncai.modules.lottery.model.Oracle.toolType.BuyType;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanType;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.oracleService.lottery.LotteryPlanOrderService;

public class LotteryPlanOrderServiceImpl extends BaseServiceImpl<LotteryPlanOrder, Integer> implements LotteryPlanOrderService{
	
	private LotteryPlanOrderDAO lotteryPlanOrderDAO;

	public void setLotteryPlanOrderDAO(LotteryPlanOrderDAO lotteryPlanOrderDAO) {
		this.lotteryPlanOrderDAO = lotteryPlanOrderDAO;
	}

	public List<LotteryPlanOrder> findSuccessByPlanNo(Integer planNo) {
		
		return this.lotteryPlanOrderDAO.findSuccessByPlanNo(planNo);
	}

	@Override
	public LotteryPlanOrder findSearchList(Integer planNo, Integer orderNo,
			String account) {
		
		return this.lotteryPlanOrderDAO.findSearchList(planNo, orderNo, account);
	}

	@Override
	public List<String> findInPeople(Integer planNo) {
		// TODO Auto-generated method stub

		return this.lotteryPlanOrderDAO.findInPeople(planNo);
	}



	@Override
	public List<HmShowBean> findHmShowBeans(String account, Date startDate,
			Date endDate, PlanType planType, List<Integer> listPlanNo,
			int planNo, int order, int offset, int pageSize, String userName,LotteryType lotteryType) {
		// TODO Auto-generated method stub
		
		return findHmShowBeans(account, startDate, endDate, planType, listPlanNo, planNo, order, offset, pageSize, userName,lotteryType, PlanStatus.getItem(-1));
	}

	@Override
	public List<HmShowBean> findHmShowBeans(String account, Date startDate,
			Date endDate, PlanType planType, List<Integer> listPlanNo,
			int planNo, int order, int offset, int pageSize, String userName,LotteryType lotteryType,
			PlanStatus planStatus) {
		// TODO Auto-generated method stub
		
		return this.lotteryPlanOrderDAO.findHmShowBeans(account, startDate, endDate, planType, listPlanNo, planNo, order, offset, pageSize, userName, lotteryType,planStatus);
	}

	@Override
	public List<LotteryPlanOrder> findHMListByPlanNoAndBuyType(int planNo, BuyType buyType) {
		// TODO Auto-generated method stub
		return this.lotteryPlanOrderDAO.findHMListByPlanNoAndBuyType(planNo,buyType);
	}

	@Override
	public HmShowBean getHmShowBeanByPlanOrderNo(String planOrderNo) {
		// TODO Auto-generated method stub
		return this.lotteryPlanOrderDAO.getHmShowBeanByPlanOrderNo(planOrderNo);
	}

	@Override
	public List<Object[]> findHMListNickNameByPlanNoAndBuyType(int planNo, BuyType buyType) {
		// TODO Auto-generated method stub
		return this.lotteryPlanOrderDAO.findHMListNickNameByPlanNoAndBuyType(planNo, buyType);
	}

	@Override
	public List<HmShowBean> findTransationFlow(String account, Date startDate, Date endDate, PlanType planType, List<Integer> listPlanNo, int planNo,
			int order, int offset, int pageSize, String userName, LotteryType lotteryType, List<LotteryType> refuseList) {
		// TODO Auto-generated method stub
		return this.lotteryPlanOrderDAO.findTransationFlow(account, startDate, endDate, planType, listPlanNo, planNo, order, offset, pageSize, userName, lotteryType, refuseList);
	}

}
