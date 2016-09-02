package com.yuncai.modules.lottery.service.oracleService.Impl.lottery;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.yuncai.core.common.Constant;
import com.yuncai.core.common.DbDataVerify;
import com.yuncai.core.hibernate.CommonStatus;
import com.yuncai.core.tools.ArithUtil;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.modules.lottery.bean.vo.HmShowBean;
import com.yuncai.modules.lottery.dao.oracleDao.lottery.LotteryPlanDAO;
import com.yuncai.modules.lottery.dao.oracleDao.lottery.LotteryPlanOrderDAO;
import com.yuncai.modules.lottery.dao.oracleDao.member.MemberScoreDAO;
import com.yuncai.modules.lottery.dao.oracleDao.member.MemberScoreLineDAO;
import com.yuncai.modules.lottery.dao.oracleDao.ticket.TicketDAO;
import com.yuncai.modules.lottery.factorys.chaipiao.ChaiPiaoFactory;
import com.yuncai.modules.lottery.factorys.checkBingo.BingoCheck;
import com.yuncai.modules.lottery.factorys.chupiao.TicketFormater;
import com.yuncai.modules.lottery.dao.sqlDao.lottery.IsusesDAO;
import com.yuncai.modules.lottery.dao.sqlDao.user.UsersDAO;
import com.yuncai.modules.lottery.model.Oracle.LotteryPlan;
import com.yuncai.modules.lottery.model.Oracle.LotteryPlanOrder;
import com.yuncai.modules.lottery.model.Oracle.Member;
import com.yuncai.modules.lottery.model.Oracle.MemberScore;
import com.yuncai.modules.lottery.model.Oracle.MemberScoreLine;
import com.yuncai.modules.lottery.model.Oracle.Ticket;
import com.yuncai.modules.lottery.model.Oracle.toolType.BuyType;
import com.yuncai.modules.lottery.model.Oracle.toolType.GenType;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanOrderStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanSearch;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanTicketStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanType;
import com.yuncai.modules.lottery.model.Oracle.toolType.ScoreType;
import com.yuncai.modules.lottery.model.Oracle.toolType.SelectType;
import com.yuncai.modules.lottery.model.Oracle.toolType.TicketStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.WalletTransType;
import com.yuncai.modules.lottery.model.Oracle.toolType.WalletType;
import com.yuncai.modules.lottery.model.Oracle.toolType.WinStatus;
import com.yuncai.modules.lottery.model.Sql.Isuses;
import com.yuncai.modules.lottery.model.Sql.Schemes;
import com.yuncai.modules.lottery.model.Sql.Users;
import com.yuncai.modules.lottery.model.Sql.vo.ChaseTasksBean;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.oracleService.lottery.LotteryPlanService;
import com.yuncai.modules.lottery.service.oracleService.lottery.TradeService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberWalletService;
import com.yuncai.modules.lottery.service.sqlService.lottery.BusinessService;
import com.yuncai.modules.lottery.service.sqlService.lottery.ChaseTaskDetailsService;
import com.yuncai.modules.lottery.service.sqlService.lottery.ChaseTasksService;
import com.yuncai.modules.lottery.service.sqlService.lottery.SchemesService;
import com.yuncai.modules.lottery.service.sqlService.user.UsersService;

public class LotteryPlanServiceImpl extends BaseServiceImpl<LotteryPlan, Integer> implements LotteryPlanService {
	private HashMap<String, String> bingoCheckMap;
	
	

	private LotteryPlanDAO lotteryPlanDAO;
	private MemberService memberService;
	private SchemesService sqlSchemesService;
	private MemberWalletService memberWalletService;
	private LotteryPlanOrderDAO lotteryPlanOrderDAO;
	private TradeService tradeService;
	private ChaseTaskDetailsService sqlChaseTaskDetailsService;
	private ChaseTasksService sqlChaseTasksService;
	private UsersService sqlUsersService;
	private ChaiPiaoFactory chaiPiaoFactory;
	private TicketDAO ticketDAO;
	private BusinessService sqlBusinessService;
	private UsersDAO sqlUsersDAO;
	private IsusesDAO sqlIsusesDAO;

	private MemberScoreDAO memberScoreDao;
	private MemberScoreLineDAO memberScoreLineDao;

	public void setChaiPiaoFactory(ChaiPiaoFactory chaiPiaoFactory) {
		this.chaiPiaoFactory = chaiPiaoFactory;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public List findByIsAbleTicketingAndPlanTickctStatus(LotteryType lotteryType, int isAbleTicketing, PlanTicketStatus planTicketStatus) {

		return this.lotteryPlanDAO.findByIsAbleTicketingAndPlanTickctStatus(lotteryType, isAbleTicketing, planTicketStatus);
	}

	public List findByLotteryTypeAndPlanStatus(LotteryType lotteryType, PlanStatus planStatus) {

		return this.lotteryPlanDAO.findByLotteryTypeAndPlanStatus(lotteryType, planStatus);
	}

	public List findByLotteryTypeAndPlanTicketStatus(LotteryType lotteryType, PlanTicketStatus planTicketStatus) {

		return this.lotteryPlanDAO.findByLotteryTypeAndPlanTicketStatus(lotteryType, planTicketStatus);
	}

	public List findBySearch(PlanSearch search, int offset, int length) {

		return this.lotteryPlanDAO.findBySearch(search, offset, length);
	}

	public List<LotteryPlan> findByTicketOut(LotteryType lotteryType, String term) {

		return this.lotteryPlanDAO.findByTicketOut(lotteryType, term);
	}

	public List<LotteryPlan> findByTicketOutAndNotOpenPrized(LotteryType lotteryType, String term) {

		return this.lotteryPlanDAO.findByTicketOutAndNotOpenPrized(lotteryType, term);
	}

	public List<LotteryPlan> findByTicketOutAndNotReturnPrized(LotteryType lotteryType, String term) {

		return this.lotteryPlanDAO.findByTicketOutAndNotOpenPrized(lotteryType, term);
	}

	public List findFailurePlan(LotteryType lotteryType, String term) {

		return this.lotteryPlanDAO.findFailurePlan(lotteryType, term);
	}

	public List findHmPlanForFailure(LotteryType lotteryType, PlanType planType, SelectType st, String term) {

		return this.lotteryPlanDAO.findHmPlanForFailure(lotteryType, planType, st, term);
	}

	public LotteryPlan findLotteryPlanByPlanNoForUpdate(Integer planNo) {

		return this.lotteryPlanDAO.findLotteryPlanByPlanNoForUpdate(planNo);
	}

	public List findPrintedPlan(LotteryType lotteryType, String term) {

		return this.lotteryPlanDAO.findPrintedPlan(lotteryType, term);
	}

	public List findUnPrintPlanByLotteryTypeAndTerm(LotteryType lotteryType, String term) {

		return this.lotteryPlanDAO.findUnPrintPlanByLotteryTypeAndTerm(lotteryType, term);
	}

	public int getCountBySearch(PlanSearch search) {

		return this.lotteryPlanDAO.getCountBySearch(search);
	}

	public List getSumBySearch(PlanSearch search) {

		return this.lotteryPlanDAO.getSumBySearch(search);
	}

	public void setLotteryPlanDAO(LotteryPlanDAO lotteryPlanDAO) {
		this.lotteryPlanDAO = lotteryPlanDAO;
	}

	@Override
	/*
	 * 派奖 lm 2013-06-02
	 */
	public void returnPrize(String planNo, String amount, String pretaxPrize) throws Exception {
		int lotteryType = 0;
		{
			if (planNo != null) {
				LotteryPlan plan = this.lotteryPlanDAO.findByIdForUpdate(Integer.valueOf(planNo));
				// 防重入
				if (plan.getWinStatus().getValue() == WinStatus.SEND_AWARD.getValue()) {
					return;
				}

				Schemes buyPlan = this.sqlSchemesService.findSchemeByPlanNOForUpdate(Integer.valueOf(planNo));
				lotteryType = plan.getLotteryType().getValue();

				Member planMember = memberService.find(plan.getMemberId());

				if (buyPlan.getWinMoney() < 0 || buyPlan.getWinMoneyNoWithTax() < 0) {// 如果方案不是已中奖状态，跳过
					return;
				}

				// 取到全部的奖金
				double totalPrize = Double.parseDouble(pretaxPrize);
				double totalePretax = Double.parseDouble(amount);
				// 方案的份数
				int part = plan.getPart().intValue();
				double commisionPrize = 0;
				// 提成大于0并且有盈利
				if (plan.getCommision() != 0 && totalPrize > plan.getAmount()) {
					// 提成金额=提成比例*(税后奖金-合买方案本金)
					commisionPrize = ArithUtil.trunc(plan.getCommision() / 100d * (totalPrize - plan.getAmount()), 2);

					Member member = this.memberService.findByAccount(plan.getAccount());
					memberWalletService
							.increaseAble(member, commisionPrize, plan.getLotteryType().getValue(), plan.getPlanNo(), 0, 0,
									WalletType.DUOBAO.getValue(), WalletTransType.RETURN_PRIZE.getValue(), member.getSourceId(),
									"合买奖金提成：" + plan.getPlanNo());
				}

				// 每份应派奖的金额
				double perAmount = ArithUtil.div(ArithUtil.sub(totalPrize, commisionPrize), part);

				// LogUtil.out("PerAmount====>" + perAmount);

				// 相到方案的所有订单
				List orderList = this.lotteryPlanOrderDAO.findSuccessByPlanNo(plan.getPlanNo());
				// LogUtil.out("-----------" + orderList.size());
				for (int j = 0; j < orderList.size(); j++) {
					// 更新订单的奖金结算日期
					LotteryPlanOrder order = (LotteryPlanOrder) orderList.get(j);
					// LogUtil.out("totalPrize====>" + totalPrize);
					// LogUtil.out("order.getPart().intValue()====>" +
					// order.getPart().intValue());
					// LogUtil.out("part====>" + part);
					order.setPosttaxPrize(ArithUtil.trunc(perAmount * order.getPart().floatValue(), 2));
					// LogUtil.out("PerPosttaxPrize====>" +
					// order.getPosttaxPrize());
					order.setPrizeSettleStatus(new Integer(0));
					order.setPrizeSettleDateTime(new Date());
					this.lotteryPlanOrderDAO.saveOrUpdate(order);
					// 对该订单的会员钱包进行派奖
					Member member = memberService.find(order.getMemberId());

					memberWalletService.increaseAble(member, order.getPosttaxPrize(), plan.getLotteryType().getValue(), plan.getPlanNo(),
							order.getOrderNo(), new Integer(0), order.getWalletType().getValue(), WalletTransType.RETURN_PRIZE.getValue(),
							member.getSourceId(), "派奖：" + plan.getPlanNo());

					// 以后处理加奖的用户
				}

				// 更改方案的中奖状态为已派奖，防止二次派奖。
				plan.setWinStatus(WinStatus.SEND_AWARD);
				// 更新方案中奖金额
				plan.setPosttaxPrize((double) totalPrize);
				plan.setPretaxPrize(totalePretax);
				this.lotteryPlanDAO.saveOrUpdate(plan);
				// 对于追号需要将税后奖金累加到追号主表的total_prize字段

				// 是否是追号方案
				// 2013-6-13中奖停止追号功能
				Integer chaseNo = 0;
				if (plan.getPlanType().getValue() == PlanType.DG.getValue()) {
					// LogUtil.out("该中奖方案为代购方案，检查是不否为追号方案");
					if (buyPlan.getChangeType() != null && (buyPlan.getChangeType().getValue() == GenType.ZH.getValue())) {

						// LogUtil.out("该中奖方案为追号方案，开始进行中止追号功能");
						ChaseTasksBean chase = sqlChaseTaskDetailsService.findBuyPlanNO(buyPlan.getId().toString());
						chaseNo = chase.getChaseTaskId();
						String context = chase.getLotteryNumber();
						// LogUtil.out("context:"+context);
						// LogUtil.out("getStopWhenWinMoney:"+Float.valueOf(Double.toString(chase.getStopWhenWinMoney())).intValue());
						Integer stopCondition = Float.valueOf(Double.toString(chase.getStopWhenWinMoney())).intValue();// 终止条件
						boolean isAbort = true;// 是否取消追号(true表示取消追号)
						String winStopFlag = "1";// 中奖后停止追号标记
						// LogUtil.out("该中奖方案，检查终止条件");
						// LogUtil.out("该中奖方案终止条件:" + stopCondition);

						if (stopCondition != null && stopCondition > 0) {

							if (stopCondition == 1 && winStopFlag.equals(stopCondition.toString())) {
								LogUtil.out("该中奖方案与设置的终止条件[中奖后停止追号]符合,取消追号");
							} else {
								LogUtil.out("该中奖方案的终止条件不符合,不取消追号");
								isAbort = false;
							}
						} else {
							LogUtil.out("该中奖方案的终止条件为空,不取消追号");
							isAbort = false;
						}

						// 撤消追号
						if (isAbort) {
							tradeService.abortChase(planMember, chaseNo, "", 0L);
						}
						LogUtil.out("该中奖方案不是追号方案");
					}

				}
			}
		}

	}

	// 合买方案自动补单（使用云彩账户）
	public void planHMAutoFill(LotteryPlan plan) {
		try {
			// 如果方案已经出票成功并且方案参与份数 + 保底份数 < 总份数就进行自动补单
			if (plan.getIsAbleTicketing() == CommonStatus.YES.getValue() && plan.getReservePart() + plan.getSoldPart() < plan.getPart()) {
				// LogUtil.out("===合买方案 " + plan.getPlanNo().toString() +
				// " 满足补单条件，进行自动补单操作!!!====");
				Member member = memberService.findByAccount("yc6636");
				//Users user = sqlUsersDAO.findUsersByAccount(member.getAccount());
				// 计算需要补单份数和金额
				int fillPart = plan.getPart() - plan.getReservePart() - plan.getSoldPart();
				//int fillAmount = fillPart * plan.getPerAmount();
				String autoHm="1"; //判断sql是否自动跟单
				// 调用合买参与接口
				String result = tradeService.buyLotteryPlanJoin(member, Long.valueOf(0), plan.getPlanNo(), fillPart, 0, "127.0.0.0",WalletType.DUOBAO.getValue(), -1, 0, autoHm,"","","","","","1");
				
				//如果逻辑没有问题,处理sql  (因为oralce服务已加入了sql的合买)
				
//				if(result !=null && !result.equals("")){
//					sqlBusinessService.buySchemesJoin(user, plan.getPlanNo().toString(), fillPart, true, 1, Double.valueOf(Integer.toString(fillPart*fillAmount)));
//				}	

			}
		} catch (Exception Ex) {
			LogUtil.out("===合买方案 " + plan.getPlanNo().toString() + " 自动补单失败!!!====");
			Ex.printStackTrace();
		}
	}

	/**
	 * 合买订单保底成功
	 */
	public void dealPlanBdSuccess(LotteryPlan lotteryPlan) throws Exception {
		try {
			// 锁定订单
			lotteryPlan = this.lotteryPlanDAO.findByIdForUpdate(lotteryPlan.getPlanNo());

			if (lotteryPlan.getPlanStatus().getValue() != PlanStatus.RECRUITING.getValue()) {
				// 不是招募中的方案，不处理
				return;
			}

			// 计算超出的份数
			int overParts = lotteryPlan.getSoldPart() + lotteryPlan.getReservePart() - lotteryPlan.getPart();

			//LogUtil.out("超出份数:" + overParts);
			// 计算超出的金额
			int overAmounts = overParts * lotteryPlan.getPerAmount();
			//LogUtil.out("超出金额:" + overAmounts);

			Integer baoPart = 0;
			Integer baoAmount = 0;
			String baoName = "";

			// 找出所有的参与合买订单，包括保底
			List orderList = lotteryPlanOrderDAO.findSuccessByPlanNo(lotteryPlan.getPlanNo());

			for (int i = 0; i < orderList.size(); i++) {
				LotteryPlanOrder planOrder = (LotteryPlanOrder) orderList.get(i);
				// 必须是已支付或部份退款(部份出票)
				if (planOrder.getStatus().getValue() != PlanOrderStatus.PAYED.getValue()
						&& planOrder.getStatus().getValue() != PlanOrderStatus.PART_RETUREN_MONEY.getValue()) {
					continue;
				}

				// 查询用户信息
				Member m = memberService.find(planOrder.getMemberId());
				// 如果是保底，并且需要退款
				if (planOrder.getBuyType().getValue() == BuyType.BAODI.getValue() && overParts > 0) {
					// 更新保底为份数为实际的份数
					planOrder.setPart(planOrder.getPart() - overParts);
					planOrder.setAmount(planOrder.getAmount() - overAmounts);
					planOrder.setVerifyMd5(DbDataVerify.getLotteryPlanOrderVerify(planOrder));
					this.lotteryPlanOrderDAO.saveOrUpdate(planOrder);

					baoPart = planOrder.getPart();
					baoAmount = planOrder.getAmount();
					// 将超出的份数的金额返款处理
					this.memberWalletService.freezeToAble(m, planOrder.getWalletType().getValue(), new Double(overAmounts), lotteryPlan
							.getLotteryType().getValue(), lotteryPlan.getPlanNo(), planOrder.getOrderNo(), new Integer(0), m.getSourceId(), "保底退款",planOrder.getBuyType().getValue(),null,0d);
					// 冻结转支付
					//LogUtil.out("冻结转支付=>帐号:" + planOrder.getAccount() + "金额:" + planOrder.getAmount());
					memberWalletService.freezeToConsume(m, planOrder.getWalletType().getValue(), new Double(planOrder.getAmount()), lotteryPlan
							.getLotteryType().getValue(), lotteryPlan.getPlanNo(), planOrder.getOrderNo(), 0, m.getSourceId(), "保底认购",lotteryPlan.getPlanType().getValue(),new Integer(0),null);

				} else if (planOrder.getBuyType().getValue() == BuyType.BAODI.getValue() && overParts == 0) {
					baoPart = planOrder.getPart();
					baoAmount = planOrder.getAmount();
					baoName = m.getAccount();
					// 冻结转支付
					// LogUtil.out("冻结转支付=>帐号:" + planOrder.getAccount() + "金额:"
					// + planOrder.getAmount());
					this.memberWalletService.freezeToConsume(m, planOrder.getWalletType().getValue(), planOrder.getAmount().doubleValue(),
							lotteryPlan.getLotteryType().getValue(), lotteryPlan.getPlanNo(), planOrder.getOrderNo(), 0, m.getSourceId(), "保底认购",lotteryPlan.getPlanType().getValue(),new Integer(0),null);

				} else {
					// 冻结转支付
					//LogUtil.out("冻结转支付=>帐号:" + planOrder.getAccount() + "金额:" + planOrder.getAmount());
					this.memberWalletService.freezeToConsume(m, planOrder.getWalletType().getValue(), planOrder.getAmount().doubleValue(),
							lotteryPlan.getLotteryType().getValue(), lotteryPlan.getPlanNo(), planOrder.getOrderNo(), 0, m.getSourceId(), "认购合买方案",lotteryPlan.getPlanType().getValue(),new Integer(0),null);
				}

			}

			lotteryPlan.setSoldPart(lotteryPlan.getPart());
			lotteryPlan.setFounderPart(lotteryPlan.getFounderPart() + lotteryPlan.getReservePart() - overParts);
			lotteryPlan.setPlanStatus(PlanStatus.PAY_FINISH);

			// 附加处理，如果方案已经出票成功，同步出票状态到方案状态上
			if (lotteryPlan.getPlanTicketStatus().getValue() == PlanTicketStatus.TICKET_FINISH.getValue()) {
				lotteryPlan.setPlanStatus(PlanStatus.TICKET_OUT);
			}

			lotteryPlanDAO.saveOrUpdate(lotteryPlan);

			// 调用参与合买接口,处理sql
			// LogUtil.out("份数加金额："+baoName+": "+baoAmount+":"+baoPart);
			Users user = sqlUsersDAO.findUsersByAccount(baoName);
			sqlBusinessService.buySchemesJoin(user, lotteryPlan.getPlanNo().toString(), baoPart, true, 1, Double.valueOf(baoAmount),lotteryPlan.getPlanStatus());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 撤销合买失败的方案
	 */
	public void dealPlanBdFailure(LotteryPlan lotteryPlan, PlanStatus planstatus) throws Exception {
		if (lotteryPlan.getPlanType().getValue() != PlanType.HM.getValue()) {
			return;
		}
		// 锁定订单
		lotteryPlan = this.lotteryPlanDAO.findByIdForUpdate(lotteryPlan.getPlanNo());
		// 找出所有的参与合买订单，包括保底
		List orderList = lotteryPlanOrderDAO.findSuccessByPlanNo(lotteryPlan.getPlanNo());
		// LogUtil.out("正在撤销没有满员的合买单==>" + lotteryPlan.getPlanNo());
		for (int i = 0; i < orderList.size(); i++) {
			LotteryPlanOrder planOrder = (LotteryPlanOrder) orderList.get(i);
			if (planOrder.getStatus().getValue() == PlanOrderStatus.PAYED.getValue()) {
				planOrder.setStatus(PlanOrderStatus.RETUREN_MONEY);
				planOrder.setReturnAmountDateTime(new Date());
				planOrder.setVerifyMd5(DbDataVerify.getLotteryPlanOrderVerify(planOrder));
				this.lotteryPlanOrderDAO.saveOrUpdate(planOrder);
				// 查询用户信息
				Member m = memberService.find(planOrder.getMemberId());
				double quto_amount=planOrder.getQuotaAmount()==null?planOrder.getAmount():planOrder.getQuotaAmount();
				//撤单默认虚拟保底
				Integer buyType=BuyType.BAODI.getValue();
				// 成功合买返款处理
				this.memberWalletService.freezeToAble(m, planOrder.getWalletType().getValue(), planOrder.getAmount().doubleValue(), lotteryPlan
						.getLotteryType().getValue(), lotteryPlan.getPlanNo(), planOrder.getOrderNo(), 0, m.getSourceId(), "合买撤单",buyType,null,quto_amount);
			}
		}
		// 撤销置顶标记
		lotteryPlan.setIsTop(CommonStatus.NO.getValue());
		lotteryPlan.setPlanStatus(planstatus);
		lotteryPlanDAO.saveOrUpdate(lotteryPlan);

		// 处理sql
		{
			Short quashStatus = Short.valueOf("0");
			if (planstatus.getValue() == PlanStatus.CANCEL.getValue()) {
				quashStatus = Short.valueOf("1");
			} else if (planstatus.getValue() == PlanStatus.ABORT.getValue()) {
				quashStatus = Short.valueOf("2");
			}
			this.sqlBusinessService.buyFreezeToAble(lotteryPlan.getPlanNo().toString(), quashStatus);
		}

	}

	/**
	 * 撤销没有打票方案(包括合买和代购)
	 */
	public void abortUnPrintPlan(LotteryPlan lotteryPlan) throws Exception {
		double successAmount = 0d;

		// 未出票作废更改状态
		ticketDAO.updateByAbortUnPrint(lotteryPlan.getPlanNo());

		if (successAmount < lotteryPlan.getAmount().doubleValue()) {
			successAmount = 0d;// TODO:全额退款
		}

		double perReturnAmount = ArithUtil.trunc((lotteryPlan.getAmount() - successAmount) / lotteryPlan.getPart().intValue(), 2);

		// 锁定记录，防止并发重入
		lotteryPlan = lotteryPlanDAO.findByIdForUpdate(lotteryPlan.getPlanNo());
		if (lotteryPlan.getPlanStatus().getValue() == PlanStatus.TICKET_GQ.getValue()) {
			// 已经是打票失败的单（已撤单了），不处理
			return;
		}

		// LogUtil.out(perReturnAmount);
		List<Member> users = new ArrayList<Member>();
		// 找出所有的参与合买订单，包括保底
		List orderList = lotteryPlanOrderDAO.findSuccessByPlanNo(lotteryPlan.getPlanNo());
		LogUtil.out("正在撤销没有打票单==>" + lotteryPlan.getPlanNo());
		for (int i = 0; i < orderList.size(); i++) {
			LotteryPlanOrder planOrder = (LotteryPlanOrder) orderList.get(i);
			if (planOrder.getStatus().getValue() == PlanOrderStatus.PAYED.getValue()) {
				planOrder.setStatus(PlanOrderStatus.RETUREN_MONEY);
				planOrder.setReturnAmountDateTime(new Date());
				planOrder.setVerifyMd5(DbDataVerify.getLotteryPlanOrderVerify(planOrder));
				this.lotteryPlanOrderDAO.saveOrUpdate(planOrder);
				// 查询用户信息
				Member m = memberService.find(planOrder.getMemberId());
				this.memberWalletService.cheapHeapToAble(m, planOrder.getWalletType().getValue(), perReturnAmount * planOrder.getPart().intValue(),
						lotteryPlan.getLotteryType().getValue(), lotteryPlan.getPlanNo(), planOrder.getOrderNo(), 0, m.getSourceId(), "方案撤销");
				users.add(m);
			}
		}

		lotteryPlan.setPlanStatus(PlanStatus.TICKET_GQ);
		lotteryPlan.setPlanTicketStatus(PlanTicketStatus.TICKET_FAIL);
		lotteryPlanDAO.saveOrUpdate(lotteryPlan);

		// 处理sql
		{
			Short quashStatus = Short.valueOf("2");//系统撤单
			this.sqlBusinessService.buyFreezeToAble(lotteryPlan.getPlanNo().toString(), quashStatus);
		}

		// 高频、小盘，通知用户
		int lt = lotteryPlan.getLotteryType().getValue();
		if (LotteryType.xianhaoMap.containsKey(lt)) {
			for (Member m : users) {
				try {

					// 2.判断是否为手机号
					if (m != null && m.getMobile() != null && m.getMobile().startsWith("1") && m.getMobile().length() == 11) {
						// // 3.发送短信
						// smsNotify.send(m.getMobile(), "您购买的第" +
						// lotteryPlan.getTerm() + "期" +
						// lotteryPlan.getLotteryType().getName() + "方案编号"
						// + lotteryPlan.getPlanNo().intValue() +
						// "因中心限号未能出票成功。给您带来不便，敬请谅解！-【云彩网】");
					}

				} catch (Exception e) {
					e.printStackTrace();
					LogUtil.out("通知用户失败！");
				}
			}
		}

	}

	/**
	 * 出票检控
	 */
	@Override
	public boolean planCheckBingo(HashMap<String, String> openResultMap, LotteryPlan lotteryPlan) throws Exception {
		boolean isBingo = false;
		String clazzString = bingoCheckMap.get(lotteryPlan.getLotteryType().getValue() + "");
		{
			BingoCheck check = (BingoCheck) Class.forName(clazzString).newInstance();
			BingoCheck oldCheck = (BingoCheck) Class.forName(clazzString).newInstance();
			int planStatusVal = lotteryPlan.getPlanStatus().getValue();
			List ticketList = new ArrayList();
			boolean isFailurePlan = planStatusVal == PlanStatus.ABORT.getValue() || planStatusVal == PlanStatus.TICKET_GQ.getValue();
			if (isFailurePlan) {
				// 流单方案调用拆票算法，自选方案默认已上传方案内容，
				if (lotteryPlan.getIsUploadContent().intValue() == CommonStatus.YES.getValue()) {
					ticketList = chaiPiaoFactory.chaiPiao(lotteryPlan.getLotteryType().getValue(), lotteryPlan);
				} else {
					// 未上传方案内容的直接返回false
					return false;
				}
			} else {
				ticketList = ticketDAO.findByPlanNo(lotteryPlan.getPlanNo());
			}

			LogUtil.out("-----------------方案号：" + lotteryPlan.getPlanNo() + " 进行开奖..........");

			for (int j = 0; j < ticketList.size(); j++) {
				Ticket t = (Ticket) ticketList.get(j);
				if (t.getLotteryType().getValue() == LotteryType.TCCJDLT.getValue()) {
					check.execute(t.getPlayType().getValue() + ":" + t.getContent() + "=" + t.getAddAttribute(), t.getPlayType(), t.getMultiple(),
							openResultMap);
				} else {
					String tempContent = t.getPlayType().getValue() + ":" + t.getContent();
					if (LotteryType.JCZQList.contains(t.getLotteryType())) {
						tempContent = t.getContent();
					}
					check.execute(tempContent, t.getPlayType(), t.getMultiple(), openResultMap);
				}

				if (check.isBingo()) {
					t.setIsBingo(0);
				} else {
					t.setIsBingo(1);
				}
				t.setBingoContent(check.getBingoContent());
				t.setBingoAmount(check.getBingoPosttaxTotal());
				if (!isFailurePlan) {// 非流单
					ticketDAO.saveOrUpdate(t);
				}
				if (j == 0) {
					oldCheck = check.clone();
				} else {
					oldCheck.addCheck(check);
				}
				check = (BingoCheck) Class.forName(clazzString).newInstance();
			}
			if (oldCheck.isBingo()) {
				isBingo = true;
			}
			this.updateBingoPlan(lotteryPlan, oldCheck);

		}
		return isBingo;
	}

	
	public LotteryPlan updateBingoPlan(LotteryPlan plan, BingoCheck check) {
		// 添加开奖时间信息
		if (plan.getPretaxPrize() != check.getBingoPretaxTotal() && plan.getPosttaxPrize() != check.getBingoPosttaxTotal()) {
			plan.setOpenResultTime(new Date());
		}
		plan.setPrizeContent(check.getBingoContent());
		LogUtil.out(plan.getPlanNo() + "getBingoContent:" + check.getBingoContent());
		plan.setPretaxPrize(check.getBingoPretaxTotal());
		plan.setPosttaxPrize(check.getBingoPosttaxTotal());
		if (check.isBingo()) {
			plan.setWinStatus(WinStatus.AWARD);
		} else {
			plan.setWinStatus(WinStatus.NOT_AWARD);
		}

		this.lotteryPlanDAO.saveOrUpdate(plan);
		return plan;
	}

	/**
	 * 检票
	 * 
	 * @throws Exception
	 */
	public void jianPiao(LotteryPlan lotteryPlan) throws Exception {

		// LogUtil.out("方案：" + lotteryPlan.getPlanNo() + "  检票开始...");
		// 查找出当前方案的所有票
		List<Ticket> ticketList = ticketDAO.findTicketByLotteryPlan(lotteryPlan);
		int succeedTicketNum = 0;
		int failTicketNum = 0;

		double price = 0;// 失败方案的总额
		for (Ticket ticketvo : ticketList) {
			if (ticketvo.getStatus().getValue() == TicketStatus.CPCG.getValue()) {
				succeedTicketNum++;
				// 更新竞彩SP值
				if (LotteryType.JCZQList.contains(ticketvo.getLotteryType())) {
					String boZhongContent = TicketFormater.JcSPformater(ticketvo);
					ticketvo.setBoZhongContent(boZhongContent);
					ticketDAO.saveOrUpdate(ticketvo);
				}

			} else if (ticketvo.getStatus().getValue() == TicketStatus.CPSB.getValue()) {
				failTicketNum++;
				price += ticketvo.getAmount();
			} else {
				return;
			}
		}

		if (ticketList.size() > 0) {
			// !!!准备修改方案状态，先锁定方案记录！！！
			lotteryPlan = lotteryPlanDAO.findByIdForUpdate(lotteryPlan.getPlanNo());
			Schemes buyPlan = this.sqlSchemesService.findSchemeByPlanNOForUpdate(Integer.valueOf(lotteryPlan.getPlanNo()));

			// 如果方案的票数与成功的票数相等则该方案设为已出票
			if (ticketList.size() == succeedTicketNum) {
				// 如果方案已经满员就将方案状态设置为已出票状态
				if (lotteryPlan.getPlanStatus().getValue() == PlanStatus.PAY_FINISH.getValue()){
					lotteryPlan.setPlanStatus(PlanStatus.TICKET_OUT);
					buyPlan.setBuyed(true);
				}
				lotteryPlan.setPlanTicketStatus(PlanTicketStatus.TICKET_FINISH);

				Date pDate = ticketList.get(0).getPrintDateTime();
				if (pDate == null)
					pDate = new Date();
				lotteryPlan.setPrintTicketDateTime(pDate);

				lotteryPlanDAO.saveOrUpdate(lotteryPlan);

				// 处理SQL成功出票状态
				{
					buyPlan.setPrintOutDateTime(pDate);
					this.sqlSchemesService.saveOrUpdate(buyPlan);
				}

				// LogUtil.out("方案：" + lotteryPlan.getPlanNo() + "  检票处理为成功。");

				List orderList = lotteryPlanOrderDAO.findSuccessByPlanNo(lotteryPlan.getPlanNo());
				// 处理明细送积分
				for (int i = 0; i < orderList.size(); i++) {
					LotteryPlanOrder planOrder = (LotteryPlanOrder) orderList.get(i);
					if (planOrder.getStatus().getValue() == PlanOrderStatus.PAYED.getValue()) {
						Integer scoreValue = (planOrder.getAmount().intValue() * Constant.CONSUME_PRESENT_RATE);
						String memo = "投注赠送积分";
						Member member = this.memberService.findByAccount(planOrder.getAccount());
						// 增加积分
						MemberScore score = memberScoreDao.findByIdForUpdate(member.getId());
						score.setAbleScore(score.getAbleScore() + scoreValue);
						score.setHeapScore(score.getHeapScore() + scoreValue);
						memberScoreDao.saveOrUpdate(score);

						MemberScoreLine scoreLine = new MemberScoreLine();
						scoreLine.setAccount(member.getAccount());
						scoreLine.setCreateDateTime(new Date());
						scoreLine.setMemberId(member.getId());
						scoreLine.setScoreType(ScoreType.CONSUME);
						scoreLine.setValue(scoreValue);
						scoreLine.setAbleScore(score.getAbleScore());
						scoreLine.setRemark(memo);
						memberScoreLineDao.save(scoreLine);
					}
				}
			} else {
				// 如果成功的票加上失败的票等于方案的票则设置该方案为部分出票
				if (failTicketNum > 0 && failTicketNum < ticketList.size()) {

				} else {
					if (ticketList.size() == failTicketNum) {

						LogUtil.out("方案：" + lotteryPlan.getPlanNo() + "  检票：出票失败！");

						if (lotteryPlan.getPlanStatus().getValue() == PlanStatus.RECRUITING.getValue()) {
							// 增加提前出票功能后，方案可能还未满员，则使用撤单功能
							dealPlanBdFailure(lotteryPlan, PlanStatus.ABORT);
						} else {
							// 其他状态，照旧处理（代购已支付及合买已经成功）
							abortUnPrintPlan(lotteryPlan);
						}
					}
				}
			}
		} else {
			LogUtil.out("方案" + lotteryPlan.getPlanNo() + "检票无效.本方案的票数为0");
		}

	}

	@Override
	public List<LotteryPlan> findLotteryPlans(LotteryType lotteryType, String readName, int offset, int pageSize, PlanStatus planStatus,
			PlanType planType,int order,int key) {
		// TODO Auto-generated method stub
		// aa
		return this.lotteryPlanDAO.findLotteryPlans(lotteryType, readName, offset, pageSize, planStatus, planType,order,key);
	}

	@Override
	public List<LotteryPlan> findLotteryPlans(LotteryType lotteryType, String readName, int offset, int pageSize, PlanType planType) {
		// TODO Auto-generated method stub
		return this.lotteryPlanDAO.findLotteryPlans(lotteryType, readName, offset, pageSize, planType);
	}

	@Override
	public List<HmShowBean> findHmShowBeans(String username, Date startDate, Date endDate, PlanType planType, List<Integer> listPlanNo, int planNo,
			int order, int offset, int pageSize) {
		// TODO Auto-generated method stub
		return this.lotteryPlanDAO.findHmShowBeans(username, startDate, endDate, planType, listPlanNo, planNo, order, offset, pageSize);
	}

	@Override
	public List<HmShowBean> findHmShowBeans(String username, Date startDate, Date endDate, PlanType planType, List<Integer> listPlanNo, int planNo,
			int order, int offset, int pageSJize, PlanStatus planStatus) {
		// TODO Auto-generated method stub
		return this.lotteryPlanDAO.findHmShowBeans(username, startDate, endDate, planType, listPlanNo, planNo, order, offset, pageSJize, planStatus);
	}

	/**
	 * 
	 * 通过彩种查找当前期方案
	 * 
	 */
	public List findCurrentLotteryPlan(LotteryType lotteryType, PlanTicketStatus status) {
		Isuses lotteryTerm;
		lotteryTerm = sqlIsusesDAO.findPrintingTermByLotteryType(lotteryType);

		if (lotteryTerm != null) {
			return lotteryPlanDAO.findByLotteryTypeAndTermsAndPlanTicketStatus(lotteryType, status, lotteryTerm.getName());
		} else
			return null;
	}

	public void setSqlIsusesDAO(IsusesDAO sqlIsusesDAO) {
		this.sqlIsusesDAO = sqlIsusesDAO;
	}

	public void setTradeService(TradeService tradeService) {
		this.tradeService = tradeService;
	}

	public void setSqlSchemesService(SchemesService sqlSchemesService) {
		this.sqlSchemesService = sqlSchemesService;
	}

	public void setMemberWalletService(MemberWalletService memberWalletService) {
		this.memberWalletService = memberWalletService;
	}

	public void setLotteryPlanOrderDAO(LotteryPlanOrderDAO lotteryPlanOrderDAO) {
		this.lotteryPlanOrderDAO = lotteryPlanOrderDAO;
	}

	public void setSqlChaseTaskDetailsService(ChaseTaskDetailsService sqlChaseTaskDetailsService) {
		this.sqlChaseTaskDetailsService = sqlChaseTaskDetailsService;
	}

	public void setSqlChaseTasksService(ChaseTasksService sqlChaseTasksService) {
		this.sqlChaseTasksService = sqlChaseTasksService;
	}

	public void setSqlUsersService(UsersService sqlUsersService) {
		this.sqlUsersService = sqlUsersService;
	}

	public HashMap<String, String> getBingoCheckMap() {
		return bingoCheckMap;
	}

	public void setBingoCheckMap(HashMap<String, String> bingoCheckMap) {
		this.bingoCheckMap = bingoCheckMap;
	}

	public void setTicketDAO(TicketDAO ticketDAO) {
		this.ticketDAO = ticketDAO;
	}

	public void setSqlUsersDAO(UsersDAO sqlUsersDAO) {
		this.sqlUsersDAO = sqlUsersDAO;
	}

	public void setSqlBusinessService(BusinessService sqlBusinessService) {
		this.sqlBusinessService = sqlBusinessService;
	}

	public void setMemberScoreDao(MemberScoreDAO memberScoreDao) {
		this.memberScoreDao = memberScoreDao;
	}

	public void setMemberScoreLineDao(MemberScoreLineDAO memberScoreLineDao) {
		this.memberScoreLineDao = memberScoreLineDao;
	}

	@Override
	public LotteryPlan findMaxWinPrizePlan(LotteryType lotteryType, Integer pretaxPrize, Date beginTime, Date endTime, WinStatus winStatus,
			String account) {
		// TODO Auto-generated method stub
		return this.lotteryPlanDAO.findMaxWinPrizePlan(lotteryType, pretaxPrize, beginTime, endTime, winStatus, account);
	}

	/**查询中奖率
	 * @param lotteryList  彩种列表
	 * @param bedinTime  开始时间
	 * @param endTime  截止时间
	 * @return
	 */
	public Object[] winPercent(List<LotteryType> lotteryList,Date bedinTime,Date endTime){
		return this.lotteryPlanDAO.winPercent(lotteryList, bedinTime, endTime);
	}

	@Override
	public Integer getEasyBuyCountByEasyType(Integer easyType) {
		// TODO Auto-generated method stub
		return this.lotteryPlanDAO.getEasyBuyCountByEasyType(easyType);
	}
}
