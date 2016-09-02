package com.yuncai.modules.lottery.service.oracleService.Impl.lottery;

import com.yuncai.core.common.Constant;
import com.yuncai.core.common.DbDataVerify;
import com.yuncai.core.hibernate.CommonStatus;
import com.yuncai.core.longExce.ServiceException;
import com.yuncai.core.sporttery.TimeTools;
import com.yuncai.core.tools.FileTools;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.core.tools.ip.IpSeeker;
import com.yuncai.core.util.CompileUtil;
import com.yuncai.modules.lottery.WebConstants;
import com.yuncai.modules.lottery.dao.oracleDao.lottery.LotteryPlanOrderDAO;
import com.yuncai.modules.lottery.model.Oracle.LotteryPlan;
import com.yuncai.modules.lottery.model.Oracle.Member;
import com.yuncai.modules.lottery.model.Oracle.toolType.ChaseStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.ChaseTermStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.GenType;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryTermStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanTicketStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanType;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlayType;
import com.yuncai.modules.lottery.model.Oracle.toolType.PublicStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.WalletType;
import com.yuncai.modules.lottery.model.Oracle.toolType.WinStatus;
import com.yuncai.modules.lottery.service.oracleService.lottery.TradeService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberWalletService;
import com.yuncai.modules.lottery.service.sqlService.lottery.BusinessService;
import com.yuncai.modules.lottery.service.sqlService.lottery.ChaseTaskDetailsService;
import com.yuncai.modules.lottery.service.sqlService.lottery.ChaseTasksService;
import com.yuncai.modules.lottery.service.sqlService.lottery.LotteryTypePropsService;
import com.yuncai.modules.lottery.service.sqlService.user.SitesService;
import com.yuncai.modules.lottery.service.sqlService.user.UsersService;
import com.yuncai.modules.lottery.model.Oracle.toolType.SelectType;
import com.yuncai.modules.lottery.dao.oracleDao.lottery.LotteryPlanDAO;
import com.yuncai.modules.lottery.dao.oracleDao.member.MemberOperLineDAO;
import com.yuncai.modules.lottery.dao.sqlDao.lottery.IsusesDAO;
import com.yuncai.modules.lottery.factorys.verify.ContentCheck;
import com.yuncai.modules.lottery.factorys.verify.ContentFactory;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanOrderStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.BuyType;
import com.yuncai.modules.lottery.model.Oracle.toolType.MemberFollowingType;
import com.yuncai.modules.lottery.model.Oracle.MemberOperLine;
import com.yuncai.modules.lottery.model.Oracle.toolType.MemberOperType;
import com.yuncai.modules.lottery.model.Oracle.LotteryPlanOrder;
import com.yuncai.modules.lottery.model.Oracle.toolType.OperLineStatus;
import com.yuncai.modules.lottery.model.Sql.ChaseTaskDetails;
import com.yuncai.modules.lottery.model.Sql.ChaseTasks;
import com.yuncai.modules.lottery.model.Sql.Isuses;
import com.yuncai.modules.lottery.model.Sql.LotteryTypeProps;
import com.yuncai.modules.lottery.model.Sql.Sites;
import com.yuncai.modules.lottery.model.Sql.Users;
import com.yuncai.modules.lottery.model.Sql.vo.ChaseTasksBean;

import java.util.*;

public class TradeServiceImpl implements TradeService {

	private LotteryPlanDAO lotteryPlanDAO;
	private IpSeeker ipSeeker;
	private MemberOperLineDAO memberOperLineDao;
	private LotteryPlanOrderDAO lotteryPlanOrderDAO;
	private MemberWalletService memberWalletService;
	private MemberService memberService;
	private IsusesDAO sqlIsusesDAO;
	private ChaseTaskDetailsService sqlChaseTaskDetailsService;
	private ChaseTasksService sqlChaseTasksService;
	private UsersService sqlUsersService;
	private BusinessService sqlBusinessService; // sql投注
	private ContentFactory contentFactory;
	private LotteryTypePropsService sqlLotteryTypePropsService;
	private SitesService sqlSitesService;

	public void setSqlSitesService(SitesService sqlSitesService) {
		this.sqlSitesService = sqlSitesService;
	}

	public void setMemberWalletService(MemberWalletService memberWalletService) {
		this.memberWalletService = memberWalletService;
	}

	// 预投补单处理，方案金额扩展
	public LotteryPlan ytbd(String planNo, String content, String filePath, Double amount, Date dclasttime, PublicStatus publicStatusIn,
			String multiple, int playType) throws Exception {
		// 锁定plan,防止修改订单金额时有用户加入合买逻辑出错
		LotteryPlan lotteryPlan = this.lotteryPlanDAO.findLotteryPlanByPlanNoForUpdate(new Integer(planNo));
		if (amount > lotteryPlan.getAmount()) {
			lotteryPlan.setAmount(amount.intValue());// 修改订单金额
			lotteryPlan.setPart(amount.intValue()); // 修改总份数
			lotteryPlan.setPlanStatus(PlanStatus.RECRUITING); // 如果已经满员的修改成未满员

		}
		if (lotteryPlan.getSelectType().getValue() == SelectType.UPLOAD.getValue()) {
			if (filePath != null && filePath.length() > 0) {
				lotteryPlan.setContent(filePath);
				lotteryPlan.setIsUploadContent(CommonStatus.YES.getValue());
				if (dclasttime != null) {
					lotteryPlan.setDealDateTime(dclasttime);
					lotteryPlan.setPublicStatus(publicStatusIn);
				}
				if (multiple != null) {
					lotteryPlan.setMultiple(Integer.parseInt(multiple)); // 修改倍投
				}

				if (lotteryPlan.getPlayType().getValue() != playType) {
					PlayType playTypeIn = PlayType.changeMap.get(playType);
					lotteryPlan.setPlayType(playTypeIn); // 修改投注类型
				}
			} else {
				throw new Exception("上传文件错误！");
			}
		} else if (lotteryPlan.getSelectType().getValue() == SelectType.CHOOSE_SELF.getValue()) {
			if (content != null && content.length() > 0) {
				lotteryPlan.setContent(content);
				// 投注方式
				lotteryPlan.setIsUploadContent(CommonStatus.YES.getValue());
			} else {
				throw new Exception("方案内容错误！");
			}
		}

		// 检查出票条件进行出票操作
		this.planPrePrintTicket(lotteryPlan);

		lotteryPlanDAO.saveOrUpdate(lotteryPlan);

		// 如果此时份数达到，合买成功，进行方案成功处理
		if (lotteryPlan.getPart().intValue() == lotteryPlan.getSoldPart().intValue()
				&& lotteryPlan.getIsUploadContent() == CommonStatus.YES.getValue()) {
			// 设置方案状态为已支付
			lotteryPlan.setPlanStatus(PlanStatus.PAY_FINISH);

			List planOrderList = this.lotteryPlanOrderDAO.findSuccessByPlanNo(lotteryPlan.getPlanNo());

			for (int i = 0; i < planOrderList.size(); i++) {
				LotteryPlanOrder order = (LotteryPlanOrder) planOrderList.get(i);
				// 查询用户信息
				Member m = memberService.find(order.getMemberId());
				if (order.getBuyType().getValue() == BuyType.BAODI.getValue()) {// 保底的进行退款
					order.setStatus(PlanOrderStatus.RETUREN_MONEY);
					order.setReturnAmountDateTime(new Date());
					this.lotteryPlanOrderDAO.saveOrUpdate(order);
					double quto_amount=order.getQuotaAmount();
					memberWalletService.freezeToAble(m, order.getWalletType().getValue(), new Double(order.getAmount()), lotteryPlan.getLotteryType()
							.getValue(), order.getPlanNo(), order.getOrderNo(), new Integer(0), new Integer(0), "保底退款",order.getBuyType().getValue(),null,quto_amount);
				} else {
					if (lotteryPlan.getAccount().equals(order.getAccount())) {
						memberWalletService.freezeToConsume(m, order.getWalletType().getValue(), new Double(order.getAmount()), lotteryPlan
								.getLotteryType().getValue(), order.getPlanNo(), order.getOrderNo(), new Integer(0), m.getSourceId(), "合买认购",lotteryPlan.getPlanType().getValue(),new Integer(0),null);
					} else {
						//LogUtil.out("会员:" + order.getAccount() + " 认购的合买单，冻结转消费");
						memberWalletService.freezeToConsume(m, order.getWalletType().getValue(), new Double(order.getAmount()), lotteryPlan
								.getLotteryType().getValue(), order.getPlanNo(), order.getOrderNo(), new Integer(0), m.getSourceId(),"合买认购",lotteryPlan.getPlanType().getValue(),new Integer(0),null);
					}

				}
			}
		}

		return lotteryPlan;
	}

	/**
	 * 代购，增加followingType参数
	 */
	public String buyLotteryPlanBySource(Member member, Long ct, String account, String term, int lotteryType, String content, Integer amount,
			int selectType, String uploadFilePath, int fromPage, String ip, int walletType, int publicStatus, int followintType, GenType gentype,
			String addAttribute, int multiple, int playType, String sqlContext, String phoneNo, String sim, String model, String systemVersion,
			String channel,Integer easyType,String version) throws ServiceException, Exception {
//		LogUtil.out("---------------------------请求代购服务--------------------------------");
		// LogUtil.out("account:"+account);
		// LogUtil.out("term:"+term);
		// LogUtil.out("amount:"+amount);
		String term_buy = null;

		LotteryType lotType = (LotteryType) LotteryType.getItem(lotteryType);
		// 如果是竞彩足球、竞彩篮球另外做处理
		if (LotteryType.JCZQList.contains(lotType)) {
			lotType = (LotteryType) LotteryType.getItem(72);
		} else if (LotteryType.JCLQList.contains(lotType)) {
			lotType = (LotteryType) LotteryType.getItem(73);
		}

		// 07-25 12:03:29.328 account:admin
		// 07-25 12:03:29.328 term:13199
		// 07-25 12:03:29.328 amount:2
//		LogUtil.out(lotType.getName() + "---" + lotType.getValue());
		Isuses buyTerm = this.sqlIsusesDAO.findByLotteryTypeAndTerm(lotType, term);

		// 竞彩足球、竞彩篮球特殊需要处理转换
		if (lotType.getValue() == LotteryType.JCZQ.getValue() || lotType.getValue() == LotteryType.JCLQ.getValue()) {
			lotType = (LotteryType) LotteryType.getItem(lotteryType);
		}

		// 有没有这个彩期
		if (buyTerm == null) {
			throw new ServiceException("1", "本次操作被中止:彩期异常");
		}

		Sites sites = this.sqlSitesService.getSitesInfo();
		if (sites == null) {
			throw new ServiceException("2", "本次操作被中止:站点信息异常");
		}
		// 判断每人每期最多认购多小方案
		try {
			boolean lotteryCount = doBooleanBuyCount(sites, lotType, buyTerm.getName(), account);
			if (!lotteryCount) {
				throw new ServiceException("3", "本次操作被中止:超过了设定每期每人最大限额！");
			}
		} catch (ServiceException e) {
			throw new ServiceException("3", "本次操作被中止:" + e.getMessage());
		}
		
		if(amount<0){
			throw new ServiceException("2", "本次操作被中止:投注金额有误!");
		}
		

		term_buy = buyTerm.getName();
		Integer memberId = member.getId();
		// 查出可支付的walletType
		walletType = findWallet(amount, member);
		// LogUtil.out("------walletType:" + walletType);

		LotteryTypeProps props = this.sqlLotteryTypePropsService.findbuyLotteryType(lotType);
//		LogUtil.out("playType=" + playType);
		PlayType plType = PlayType.changeMap.get(playType); // PlayType.getItem(playType);
		// 判断是否已经关闭销售
		if (buyTerm.getState().getValue() != LotteryTermStatus.OPEN.getValue()
				&& buyTerm.getState().getValue() != LotteryTermStatus.BEFORE_OPEN_SALE.getValue() && !LotteryType.JCZQList.contains(lotType)
				&& !LotteryType.JCLQList.contains(lotType))
			throw new ServiceException("2", "本次操作被中止:彩期销售截止了");

		if (new Date().after(buyTerm.getEndTime()))
			throw new ServiceException("2", "本次操作被中止:彩期销售截止了");

		ContentCheck contentCheck = null;
		// 是文件上传，并且路径不为空 || 自选 || IVR投注
		if (((uploadFilePath != null && !uploadFilePath.equals("")) && selectType == SelectType.UPLOAD.getValue())
				|| selectType == SelectType.CHOOSE_SELF.getValue()) {
			// 初始化工厂类，解析content字符串,构造工厂类,检查方案内容是否为恶意注入、金额是否正确

			if ((LotteryType.JCZQList.contains(lotType) || LotteryType.JCLQList.contains(lotType)) && uploadFilePath != null && !"".equals(uploadFilePath)) {
				if (!content.startsWith("{")) {
					content = FileTools.getFileContent(WebConstants.WEB_PATH + uploadFilePath);
				}
			}

			String strContent = "";
		//	LogUtil.out("加工前content="+content);
			if (LotteryType.JCZQList.contains(lotType) || LotteryType.JCLQList.contains(lotType)||LotteryType.JCSJBList.contains(lotType)) {
				strContent = content;
			} else {
				strContent = plType.getValue() + ":" + content + "%" + multiple;
			}
			// 加奖
			if (lotType.getValue() == 39) {
				if (addAttribute != null && !"".equals(addAttribute)) {
					strContent = strContent + "-" + addAttribute;
				}

			}
			contentCheck = contentFactory.initFactory(lotType.getValue(), amount, strContent);

			if (contentCheck.isPass() != true) {
				if (contentCheck.getMessage() != null && !"".equals(contentCheck.getMessage())) {
					throw new ServiceException("3", "本次操作被中止:" + contentCheck.getMessage());
				} else {
					throw new ServiceException("3", "本次操作被中止:金额错误");
				}
			}

			/* 返回处理后的投注类容。。主要是去掉投注类容的追加 */
			content = contentCheck.getContent();
		//	LogUtil.out("返回处理后内容content="+content);
		}

		// 竟彩截止时间
		Date jclasttime = null;
		Date jclatetime=null;
		if (LotteryType.JCZQList.contains(lotType)) {
			jclasttime = TimeTools.getFbEndSaleTime(contentCheck.getFirstTime(), props.getFsAheadTime());
			jclatetime = TimeTools.getFbEndSaleTime(contentCheck.getLateTime(), props.getFsAheadTime());
			if (jclasttime.getTime() - System.currentTimeMillis() <= 0) {// 如果赛事截止
				throw new ServiceException("20", "你购买的方案过期了");
			}
		}
		
		//竞彩篮球截止时间计算
		if(LotteryType.JCLQList.contains(lotType)){
			jclasttime = TimeTools.getBbEndSaleTime(contentCheck.getFirstTime(), props.getFsAheadTime());
			jclatetime = TimeTools.getBbEndSaleTime(contentCheck.getLateTime(), props.getFsAheadTime());
			if (jclasttime.getTime() - System.currentTimeMillis() <= 0) {// 如果赛事截止
				throw new ServiceException("20", "你购买的方案过期了");
			}
		}
		
		// 实体对像
		LotteryPlan lotteryPlan = new LotteryPlan();
		// lotteryPlan.setPlanNo(planNo);
		lotteryPlan.setAddAttribute(addAttribute);
		lotteryPlan.setLotteryType(lotType);
		lotteryPlan.setMemberId(member.getId());
		lotteryPlan.setAccount(account);
		lotteryPlan.setNickName(member.getNickName());
		lotteryPlan.setAmount(amount);
		lotteryPlan.setMultiple(multiple);
		lotteryPlan.setClicks(new Integer(0));
		lotteryPlan.setCreateDateTime(new Date());

		// 终端截止时间内定
		if (LotteryType.JCZQList.contains(lotType) || LotteryType.JCLQList.contains(lotType)) {
			lotteryPlan.setDealDateTime(jclasttime);
			lotteryPlan.setLateDateTime(jclatetime);
		} else{
			lotteryPlan.setDealDateTime(buyTerm.getTerminalEndDateTime());
			lotteryPlan.setLateDateTime(buyTerm.getTerminalEndDateTime());
		}

		// --------------------------------------------这个只是暂进，需要屏掉以后做了出票------------------------------------
		// if(LotteryType.JCZQList.contains(lotType)){
		// lotteryPlan.setPrintTicketDateTime(new Date());
		// }
		// -------------------------------------------------end
		// -------------------------------
		lotteryPlan.setExperience(new Integer(0));

		lotteryPlan.setIsSuperMan(new Integer(1));

		lotteryPlan.setPart(new Integer(1));
		lotteryPlan.setPerAmount(amount);
		lotteryPlan.setReservePart(new Integer(0));
		lotteryPlan.setFounderPart(new Integer(1));
		lotteryPlan.setSoldPart(new Integer(1));
		lotteryPlan.setPlanDesc("");
		lotteryPlan.setPlanStatus(PlanStatus.PAY_FINISH);
		lotteryPlan.setIsAbleTicketing(CommonStatus.YES.getValue()); // 判断是否可以出票
		if (1 == PlanTicketStatus.NO_PROCESS.getValue()) {
			lotteryPlan.setPlanTicketStatus(PlanTicketStatus.NO_PROCESS);
		}
		lotteryPlan.setPlanType(PlanType.DG);// 代购

		lotteryPlan.setPlayType(plType);// 单式？复式？ //TODO:
		// 需要根据号码解析验证的工厂里面获取玩法类型
		lotteryPlan.setPosttaxPrize(new Double(0));
		lotteryPlan.setPretaxPrize(new Double(0));
		lotteryPlan.setPrizeContent("");
		lotteryPlan.setPublicStatus(PublicStatus.getItem(publicStatus));// 代购的方案是否公开
		//增加轻松购彩标志
		lotteryPlan.setEasyType(easyType);
		
		if (gentype != null) {
			if (GenType.SJ.getValue() == gentype.getValue()) {
				lotteryPlan.setGenType(gentype);
			} else {
				lotteryPlan.setGenType(gentype);
			}
		}

		if (selectType != SelectType.UPLOAD.getValue()) {
			lotteryPlan.setContent(content);
			lotteryPlan.setSelectType(SelectType.getItem(selectType));
			lotteryPlan.setIsUploadContent(CommonStatus.YES.getValue());
		} else {

			// 如果是上传方案,content存的是上传后的路径
			if (uploadFilePath != null && !"".equals(uploadFilePath)) {// 代购的时候有上传文件
				lotteryPlan.setContent(uploadFilePath);
				lotteryPlan.setSelectType(SelectType.UPLOAD);
				lotteryPlan.setIsUploadContent(CommonStatus.YES.getValue());
			} else {
				lotteryPlan.setContent("");
				lotteryPlan.setSelectType(SelectType.UPLOAD);
				lotteryPlan.setIsUploadContent(CommonStatus.NO.getValue());// 还没有上传文件
			}
		}

		lotteryPlan.setChannel(channel);
		lotteryPlan.setTerm(term_buy);
		lotteryPlan.setWinStatus(WinStatus.NOT_RESULT);
		lotteryPlan.setVerifyMd5(DbDataVerify.getLotteryPlanVerify(lotteryPlan));
		this.lotteryPlanDAO.save(lotteryPlan);
		Integer planNo = lotteryPlan.getPlanNo();

		// 生成订单
		Integer planOrderNo = this.makePlanOrder(memberId, account, planNo, amount, 1, PlanOrderStatus.PAYED, term_buy, BuyType.SELF_BUY, 100,
				amount * 1, "", WalletType.getItem(walletType), MemberFollowingType.getItem(followintType), channel,member.getNickName());

		// 保存会员操作流水
		MemberOperLine operLine = new MemberOperLine();
		operLine.setAccount(member.getAccount());
		operLine.setSourceId(new Integer(0));
		operLine.setCreateDateTime(new Date());
		operLine.setExtendInfo("");
		operLine.setReferer("");
		operLine.setUrl("");
		operLine.setFromPlace(ipSeeker.getCountry(ip) + ipSeeker.getArea(ip));
		operLine.setIp(ip);
		operLine.setTerminalId(ct);
		operLine.setOperType(MemberOperType.DGCHOOSE);
		operLine.setStatus(OperLineStatus.SUCCESS);
		operLine.setLotteryType(lotType);
		operLine.setPhoneNo(phoneNo);
		operLine.setSim(sim);
		operLine.setModel(model);
		operLine.setSystemVersion(systemVersion);
		operLine.setChannel(channel);
		operLine.setVersion(version);
		memberOperLineDao.save(operLine);
		Integer operLineNo = operLine.getOperLineNo();

//		LogUtil.out("记录会员操作:代购前置操作成功");

		String dermk = "购买彩票";

		if (gentype != null) {

		}

		// 消费
		double quotaAmount=memberWalletService.cheapConsume(member, new Double(amount), lotType.getValue(), planNo, planOrderNo, operLineNo, walletType, member
				.getSourceId(), "购买彩票",null,new Integer(0),null);

		
		//根据订单号处理提款额度
		this.takeOrderUPdate(planOrderNo, quotaAmount); 
//		LogUtil.out("--------------------代购服务结束------------------------");

		// start同步sql数据库--------------------------------------------------------------------
		Integer schemesId = 0;
		Short atTopStatus = 0;
		String sqlContent = content;
		Boolean isPurchasing = false; // 代购 、合买
		Integer fromClient = 1;
		String lotteryNumberNew = null;

		String sqlPlayIn = (String) LotteryType.lotteryMap.get(lotType.getValue());
		{
			List<Users> userList = sqlUsersService.findByProperty("name", member.getAccount());
			Users user = new Users();
			if (userList != null && !userList.isEmpty()) {
				user = (Users) userList.get(0);
			}

			// 内容竞彩特殊处理

			if (LotteryType.JCZQList.contains(lotType) || LotteryType.JCLQList.contains(lotType)||LotteryType.JCSJBList.contains(lotType)) {
				sqlContent = sqlContext;
				lotteryNumberNew = content;
			}

			isPurchasing = true;

			if (gentype != null) {
				if (gentype.getValue() == GenType.SJ.getValue() || gentype.getValue() == GenType.SJZH.getValue()) {
					fromClient = 2;
				} else {
					fromClient = 1;
				}
			}

			// 明确的字段 schedule , reSchedule isPurchasing
			schemesId = this.sqlBusinessService.makeSchemes(user, "", null, "", playType, buyTerm.getId(), multiple, Double.valueOf(amount),
					new Integer(1), Short.parseShort(PublicStatus.getItem(publicStatus).getValue() + ""), Short.valueOf("0"), atTopStatus,
					new Integer(1), Double.valueOf("100"), Double.valueOf("0"), sqlContent, fromClient, planNo, gentype, isPurchasing, Double
							.valueOf(0), Double.valueOf(0), lotteryNumberNew, channel,"");

			String schemeNumber = buyTerm.getName() + sqlPlayIn + schemesId;

			// 更新方案编号
			schemeNumber = this.sqlBusinessService.currentSchemes(schemesId, schemeNumber);

			// 备份方案
			Integer ids = this.sqlBusinessService.makeSchemesNumber(schemesId, Double.valueOf(amount), multiple, sqlContent, lotteryNumberNew);

			// 处得购买详情 perAmount * soldPart, soldPart
			Integer buyDetaileId = this.sqlBusinessService
					.makeBuyDetailes(user, schemesId, new Integer(1), Double.valueOf(amount), fromClient, false);

		}
		// start同步end数据库--------------------------------------------------------------------
		return planNo.toString()+"|"+planOrderNo;// +"|"+planOrderNo;
	}

	/**
	 * 发起合买 功能： 针对用户发起合买处理接口 输入参数： memberId ---会员ID account ---会员帐号 term ---期次
	 * content ---投注号码内容 amount ---交易金额 perAmount ---每份金额 part ---份数 reservePart
	 * ---保底份数 soldPart ---已经认购份数 selectType ---投注方式 uploadFilePath ---上传方案路径
	 * multiple ---倍数 walletType ---支付类型 lotteryTyep ---彩票类型
	 * 
	 * 
	 * @throws Exception
	 */
	public String buyLotteryPlanTogether(Member member, Long ct, String term, int iLotteryType, String content, Integer amount, Integer perAmount,
			int part, int reservePart, int soldPart, String uploadFilePath, int fromPage, String ip, int walletType, int founderPart,
			String planDesc, int selectType, int publicStatus, int comission, GenType gentype, String isPay, int followingType_, String addAttribute,
			int multiple, int iplayType, String sqlContext, String phoneNo, String sim, String model, String systemVersion, String channel,
			String title,String version) throws ServiceException, Exception {

		boolean isSuccess = false;
		boolean isYt = false;
		Integer memberId = member.getId();
		String account = member.getAccount();
		walletType = findWallet(soldPart, member);
		String term_buy = null;
		// LogUtil.out("------walletType:" + walletType);

		LotteryType lotteryType = LotteryType.getItem(iLotteryType);
		// 如果是竞彩足球、竞彩篮球另外做处理
		if (LotteryType.JCZQList.contains(lotteryType)) {
			lotteryType = (LotteryType) LotteryType.getItem(72);
		} else if (LotteryType.JCLQList.contains(lotteryType)) {
			lotteryType = (LotteryType) LotteryType.getItem(73);
		}

		Isuses buyTerm = this.sqlIsusesDAO.findByLotteryTypeAndTerm(lotteryType, term);

		// 竞彩足球、竞彩篮球特殊需要处理转换
		if (lotteryType.getValue() == LotteryType.JCZQ.getValue() || lotteryType.getValue() == LotteryType.JCLQ.getValue()) {
			lotteryType = (LotteryType) LotteryType.getItem(iLotteryType);
		}
		// 有没有这个彩期
		if (buyTerm == null) {
			throw new ServiceException("1", "本次操作被中止:彩期异常");
		}

		// 判断每人每期最多认购多小方案
		Sites sites = this.sqlSitesService.getSitesInfo();
		if (sites == null) {
			throw new ServiceException("2", "本次操作被中止:站点信息异常");
		}
		try {
			boolean lotteryCount = doBooleanBuyCount(sites, lotteryType, buyTerm.getName(), account);
			if (!lotteryCount) {
				throw new ServiceException("3", "本次操作被中止:超过了设定每期每人最大限额！");
			}
		} catch (ServiceException e) {
			throw new ServiceException("3", "本次操作被中止:" + e.getMessage());
		}

		term_buy = buyTerm.getName();
//		LogUtil.out("--------------------请求发起合买服务------------------------");

		LotteryTypeProps props = this.sqlLotteryTypePropsService.findbuyLotteryType(lotteryType);

		Date dclasttime = null;
		Date jclatetime = null;
		// 判断是否已经关闭销售
		if (buyTerm.getState().getValue() != LotteryTermStatus.OPEN.getValue()
				&& buyTerm.getState().getValue() != LotteryTermStatus.BEFORE_OPEN_SALE.getValue() && !LotteryType.JCZQList.contains(lotteryType)
				&& !LotteryType.JCLQList.contains(lotteryType))
			throw new ServiceException("2", "本次操作被中止:您投注的彩期已经停止销售了");

		dclasttime = buyTerm.getTerminalEndDateTime();
		jclatetime  = buyTerm.getTerminalEndDateTime();

		// 复式截止时间与彩期截止时间相同
		if (buyTerm.getEndTime().getTime() < System.currentTimeMillis()) {
			throw new ServiceException("5", "本次操作被中止:您投注的彩期已停止发起合买了");
		}

		if (amount <= 0) {
			throw new ServiceException("0", "本次操作被中止:金额错误");
		}

		// 方案标题和方案描述不能为空
		if (planDesc.equals("") || planDesc == null)
			throw new ServiceException("6", "本次操作被中止:宣传口号没有填写");
		// 检查每份金额*份数是否超出总的金额(由于有除不尽的情况，这个地方屏蔽）

		if(founderPart<0)
			throw new ServiceException("7", "本次操作被中止:购买金额错误");
		
		if (perAmount * part > amount)
			throw new ServiceException("7", "本次操作被中止:购买金额错误");

		// 自动合买与发起合买区别 (自动合买不需要认购)
		if (isPay == null || "".equals(isPay)) {
			if (Float.valueOf(soldPart) < 1) {
				throw new ServiceException("8", "本次操作被中止:发起人认购金额必须一元起");
			}
		}

		//有效验证
		{
			boolean isreservePart=CompileUtil.isNumber(reservePart+"");
			if(!isreservePart)
				throw new ServiceException("9", "本次操作被中止:您认购金额有误!");
			boolean issoldPart=CompileUtil.isNumber(soldPart+"");
			if(!issoldPart)
				throw new ServiceException("9", "本次操作被中止:您认购金额保底有误!");
		}

		if(reservePart<0){
			throw new ServiceException("9", "本次操作被中止:您认购金额有误!");
		}
		if(soldPart<0){
			throw new ServiceException("9", "本次操作被中止:您认购金额保底有误!");
		}
		
		if (soldPart + reservePart > part) {
			throw new ServiceException("9", "本次操作被中止:您认购金额大于方案总金额");
		}

		double re = (double) soldPart / part * 100;
		double rd = (double) (Math.round(re * 100) / 100.0);
		double Opt_InitiateSchemeLimitLowerScale = sites.getOptInitiateSchemeLimitLowerScale() * 100;
		if (rd < Opt_InitiateSchemeLimitLowerScale) {
			throw new ServiceException("11", "本次操作被中止:您认购金额比例小于3%");
		}

		PlanType planType = PlanType.HM;
		PlayType playType = PlayType.changeMap.get(iplayType);// (PlayType)PlayType.getItem(iplayType);
		String addStr = addAttribute;

		String[] cnts = content.split("\\%");
		if (cnts[0] == null || cnts[0].equals("")) {// 预投合买，不用验证内容
			isYt = true;
			content = "";
			// multiple = Integer.parseInt(cnts[1]);
			if (selectType == 1) {// 上传方式
				if (playType.getValue() != PlayType.ZXFS.getValue())
					playType = PlayType.DS;
			} else {
				playType = PlayType.FS;
			}
		} else {
			// 初始化工厂类，解析content字符串,构造工厂类,检查方案内容是否为恶意注入、金额是否正确
			ContentCheck contentCheck = new ContentCheck();

			if ((selectType == SelectType.UPLOAD.getValue() && uploadFilePath != null && !"".equals(uploadFilePath))
					|| selectType != SelectType.UPLOAD.getValue()) {

				if ((LotteryType.JCZQList.contains(lotteryType) || LotteryType.JCLQList.contains(lotteryType)) && uploadFilePath != null && !"".equals(uploadFilePath)) {
					if (!content.startsWith("{")) {
						content = FileTools.getFileContent(WebConstants.WEB_PATH + uploadFilePath);
					}
				}
				String strContent = "";
				if (LotteryType.JCZQList.contains(lotteryType) || LotteryType.JCLQList.contains(lotteryType)) {
					strContent = content;
				} else {
					strContent = playType.getValue() + ":" + content + "%" + multiple;
				}
				// 追加
				if (lotteryType.getValue() == 39) {
					if (addAttribute != null && !"".equals(addAttribute)) {
						strContent = strContent + "-" + addAttribute;
					}

				}

				contentCheck = contentFactory.initFactory(lotteryType.getValue(), amount, strContent);
				if (contentCheck.isPass() != true) {
					if (contentCheck.getMessage() != null && !"".equals(contentCheck.getMessage())) {
						throw new ServiceException("3", "本次操作被中止:" + contentCheck.getMessage());
					} else {
						throw new ServiceException("3", "本次操作被中止:方案金额错误");
					}
				}

				content = contentCheck.getContent();
			}

			multiple = contentCheck.getMultiple();
			playType = contentCheck.getPlayType();
			addStr = contentCheck.getIsAttribute();

			//竞彩足球截止时间计算
			if (LotteryType.JCZQList.contains(lotteryType)) {
				dclasttime = TimeTools.getFbEndSaleTime(contentCheck.getFirstTime(), props.getFsAheadTime());
				jclatetime = TimeTools.getFbEndSaleTime(contentCheck.getLateTime(), props.getFsAheadTime());
				if (dclasttime.getTime() - System.currentTimeMillis() <= 0) {// 如果赛事截止
					throw new ServiceException("20", "你购买的方案过期了");
				}
			}
			
			//竞彩篮球截止时间计算
			if (LotteryType.JCLQList.contains(lotteryType)) {
				dclasttime = TimeTools.getBbEndSaleTime(contentCheck.getFirstTime(), props.getFsAheadTime());
				jclatetime = TimeTools.getBbEndSaleTime(contentCheck.getLateTime(), props.getFsAheadTime());
				if (dclasttime.getTime() - System.currentTimeMillis() <= 0) {// 如果赛事截止
					throw new ServiceException("20", "你购买的方案过期了");
				}
			}
		}

		if (selectType == SelectType.UPLOAD.getValue()) {

			if (buyTerm.getEndTime().getTime() - props.getDsAheadTime() < System.currentTimeMillis()) {
				throw new ServiceException("4", "本次操作被中止:您投注的彩期已停止单式上传");
			}
			if ((LotteryType.JCZQList.contains(lotteryType) || LotteryType.JCLQList.contains(lotteryType)) && content != null && content.startsWith("{")) {

			} else {
				content = uploadFilePath;// 如果是上传方案,content存的是上传后的路径
			}

		}

		PlanStatus statusInDb = PlanStatus.RECRUITING;
		if (soldPart == part) {
			isSuccess = true;
			statusInDb = PlanStatus.PAY_FINISH;
		}

		// 生成合买方案，存储方案,返回方案编号
		Integer planNo = this.makePlan(lotteryType, memberId, account, amount, multiple, content, part, perAmount, reservePart, soldPart, statusInDb,
				PublicStatus.getItem(publicStatus), SelectType.getItem(selectType), term_buy, addStr, founderPart, planDesc, playType, planType,
				dclasttime, comission, null, gentype, isPay, channel, title,member.getNickName(),jclatetime);

		// 判断手机购彩
		MemberFollowingType followingtypeIn = null;
		if (gentype != null) {
			if (gentype.getValue() == GenType.SJ.getValue()) {
				if (followingType_ == MemberFollowingType.IPHONE_PHONE.getValue()) {
					followingtypeIn = MemberFollowingType.getItem(followingType_);
				} else {
					followingtypeIn = MemberFollowingType.getItem(MemberFollowingType.PHONE.getValue());
				}
			} else
				followingtypeIn = null;

		}

		Integer orderNo = 0;

		// //自动合买与发起合买区别 (自动合买不需要认购)
		if (isPay == null || "".equals(isPay)) {
			// 生成发起人认购订单
			Integer planOrderNo = this.makePlanOrder(memberId, account, planNo, perAmount * soldPart, soldPart, PlanOrderStatus.PAYED, term_buy,
					BuyType.SELF_BUY, "", WalletType.getItem(walletType),member.getNickName(),channel);
			orderNo = planOrderNo;

			// 会员操作流水（认购）
			MemberOperLine operLine = new MemberOperLine();
			operLine.setAccount(member.getAccount());
			operLine.setSourceId(new Integer(0));
			operLine.setCreateDateTime(new Date());
			operLine.setExtendInfo("");
			operLine.setReferer("");
			operLine.setUrl("");
			operLine.setFromPlace(ipSeeker.getCountry(ip) + ipSeeker.getArea(ip));
			operLine.setIp(ip);
			operLine.setTerminalId(ct);
			operLine.setOperType(MemberOperType.HMCHOOSE);
			operLine.setStatus(OperLineStatus.SUCCESS);
			operLine.setLotteryType(lotteryType);
			operLine.setPhoneNo(phoneNo);
			operLine.setSim(sim);
			operLine.setModel(model);
			operLine.setSystemVersion(systemVersion);
			operLine.setChannel(channel);
			operLine.setVersion(version);
			memberOperLineDao.save(operLine);
			Integer operLineNo = operLine.getOperLineNo();
//			LogUtil.out("记录会员操作:合买前置操作成功");

			if (isSuccess) {
				// 发起时已成功 进行消费
				double quotaAmount=memberWalletService.cheapConsume(member, new Double(perAmount * soldPart), iLotteryType, planNo, planOrderNo, operLineNo, walletType,
						member.getSourceId(), "发起合买",null,new Integer(0),null);
				//根据订单号处理提款额度
				this.takeOrderUPdate(planOrderNo, quotaAmount); 
			} else {
				// 调用冻结接口，冻结认购金额
				double quotaAmount=memberWalletService.freeze(member, new Double(perAmount * soldPart), iLotteryType, planNo, planOrderNo, operLineNo, walletType,
						member.getSourceId(), "发起合买",PlanType.getItem(2).getValue(),new Integer(0));
				//根据订单号处理提款额度
				this.takeOrderUPdate(planOrderNo, quotaAmount);
			}

			if (reservePart > 0) {
				// 会员操作流水（保底）
				MemberOperLine operLineDB = new MemberOperLine();
				operLineDB.setAccount(member.getAccount());
				operLineDB.setSourceId(new Integer(0));
				operLineDB.setCreateDateTime(new Date());
				operLineDB.setExtendInfo("");
				operLine.setReferer("");
				operLine.setUrl("");
				operLineDB.setFromPlace(ipSeeker.getCountry(ip) + ipSeeker.getArea(ip));
				operLineDB.setIp(ip);
				operLineDB.setTerminalId(ct);
				operLineDB.setOperType(MemberOperType.HMCHOOSE);
				operLineDB.setStatus(OperLineStatus.SUCCESS);
				this.memberOperLineDao.save(operLineDB);
				Integer operLineNoBD = operLineDB.getOperLineNo();
//				LogUtil.out("记录会员操作:合买前置失败操作成功");
				// 生成发起人保底订单
				Integer planOrderNoBD = this.makePlanOrder(memberId, account, planNo, perAmount * reservePart, reservePart, PlanOrderStatus.PAYED,
						term_buy, BuyType.BAODI, "", WalletType.getItem(walletType),member.getNickName(),channel);

				double quotaAmount=memberWalletService.freeze(member, new Double(perAmount * reservePart), iLotteryType, planNo, planOrderNoBD, operLineNoBD,
						walletType, member.getSourceId(), "保底",PlanType.getItem(2).getValue(),new Integer(0));
				
				//根据订单号处理提款额度
				this.takeOrderUPdate(planOrderNoBD, quotaAmount); 
			}
		}

//		LogUtil.out("--------------------发起合买服务结束------------------------");

		// start同步sql数据库--------------------------------------------------------------------
		Integer schemesId = 0;
		Short atTopStatus = 0;
		String sqlContent = content;
		Boolean isPurchasing = false; // 代购 、合买
		Integer fromClient = 1;
		Integer sqlParAmount = perAmount * soldPart;
		Double Schedule = Double.valueOf(0);
		Double ReSchedule = Double.valueOf(0);
		Double SchemeBonusScale = comission / 100d;
		Double assureMoney = Double.valueOf(reservePart * perAmount);
		String lotteryNumberNew = null;
		String sqlPlayIn = (String) LotteryType.lotteryMap.get(lotteryType.getValue());
		{
			List<Users> userList = sqlUsersService.findByProperty("name", member.getAccount());
			Users user = new Users();
			if (userList != null && !userList.isEmpty()) {
				user = (Users) userList.get(0);
			}

			// 内容竞彩特殊处理

			if (LotteryType.JCZQList.contains(lotteryType) || LotteryType.JCLQList.contains(lotteryType)) {
				sqlContent = sqlContext;
				lotteryNumberNew = content;
			}

			if (planType.getValue() == PlanType.HM.getValue()) {
				isPurchasing = false;
			} else {
				isPurchasing = true;
			}
			double c = (double) soldPart / part * 100;
			double d = (double) (Math.round(c * 100)) / 100.0;
			Schedule = d; // ROUND(CAST(@BuyShareCount AS float) / Share * 100,
							// 2) //进度

			// 认购=总份数
			if (soldPart == part) {
				atTopStatus = 0;
				ReSchedule = Double.valueOf(0);
				// isPurchasing=true;
			} else {
				ReSchedule = Schedule;
			}
			if (gentype != null) {
				if (gentype.getValue() == GenType.SJ.getValue() || gentype.getValue() == GenType.SJZH.getValue()) {
					fromClient = 2;
				} else {
					fromClient = 1;
				}
			}
			// 明确的字段 schedule , reSchedule isPurchasing
			schemesId = this.sqlBusinessService.makeSchemes(user, "", null, title, iplayType, buyTerm.getId(), multiple, Double.valueOf(amount),
					part, Short.parseShort(PublicStatus.getItem(publicStatus).getValue() + ""), Short.valueOf("0"), atTopStatus, soldPart, Schedule,
					ReSchedule, sqlContent, fromClient, planNo, gentype, isPurchasing, SchemeBonusScale, assureMoney, lotteryNumberNew, channel,planDesc);

			String schemeNumber = buyTerm.getName() + sqlPlayIn + schemesId;

			// 更新方案编号
			schemeNumber = this.sqlBusinessService.currentSchemes(schemesId, schemeNumber);

			// 方案备份
			Integer ids = this.sqlBusinessService.makeSchemesNumber(schemesId, Double.valueOf(amount), multiple, sqlContent, lotteryNumberNew);

			// 处得购买详情 perAmount * soldPart, soldPart
			Integer buyDetaileId = this.sqlBusinessService.makeBuyDetailes(user, schemesId, soldPart, sqlParAmount.doubleValue(), fromClient, false);

		}
		// start同步end数据库--------------------------------------------------------------------

		return planNo.toString()+"|"+orderNo;// +"|"+orderNo;
	}

	/**
	 * 加入合买 功能： 针对用户自动跟单参与合买处理接口(增加跟单类型(followingType)字段) 输入参数： member ---会员
	 * lotteryPlanNo ---方案编号 part ---金额 part ---份数 fromPage ---来源页面 ip ---用户IP地址
	 * walletType ---支付类型 followingType ---跟单类型 输出参数：
	 */
	public String buyLotteryPlanJoin(Member member, Long ct, Integer lotteryPlanNo, int part, int fromPage, String ip, int walletType,
			int followingType, Integer sourceId, String autoHm, String phoneNo, String sim, String model, String systemVersion, String channel,String version)
			throws Exception {
		Integer memberId = member.getId();
		String account = member.getAccount();

		// 查出可支付的walletType
		walletType = findWallet(part, member);
		// LogUtil.out("------walletType:" + walletType);
		if (part <= 0) {
			throw new Exception("本次操作被中止:您认购份数有误");
		}
		// 根据方案编号获取方案相关信息
		LotteryPlan plan = lotteryPlanDAO.findLotteryPlanByPlanNoForUpdate(lotteryPlanNo);
		if (plan == null) {
			throw new Exception("本次操作被中止:没有这个方案编号");
		}
		// 发起人是正式帐号 但用虚拟帐号加入合买,则提示错误
		String hmAccount = Constant.VirtualAccount;
		if (hmAccount.indexOf("," + plan.getAccount() + ",") == -1 && hmAccount.indexOf("," + account + ",") >= 0) {
			throw new Exception("本次操作被中止:测试帐号禁止加入正式合买单");
		}

		int sourceFounderPart = plan.getFounderPart().intValue();
		int sourceSoldPart = plan.getSoldPart().intValue();
		Integer perAmount = plan.getPerAmount();
		Integer amount = perAmount * part;
		LogUtil.out("--------------------请求参与合买服务------------------------");
		String term = plan.getTerm();
		LotteryType lotteryType = plan.getLotteryType();

		Isuses buyTerm = this.sqlIsusesDAO.findByIsusesTerm(lotteryType, term);
		// 有没有这个彩期
		if (buyTerm == null) {
			throw new Exception("本次操作被中止:您投注的彩期有误");
		}

		Date dclasttime = null;
		LotteryTypeProps props = this.sqlLotteryTypePropsService.findbuyLotteryType(lotteryType);
		if (buyTerm.getState().getValue() != LotteryTermStatus.OPEN.getValue()
				&& buyTerm.getState().getValue() != LotteryTermStatus.BEFORE_OPEN_SALE.getValue() &&!LotteryType.JCZQList.contains(lotteryType)){
			//既不是足彩也不是篮彩
			if(!LotteryType.JCLQList.contains(lotteryType)){
				throw new ServiceException("2", "本次操作被中止:彩期销售截止了");
			}
			
		}
			

		if (plan.getPlanStatus().getValue() != PlanStatus.RECRUITING.getValue()) {
			if (plan.getPlanStatus().getValue() == PlanStatus.ABORT.getValue()) {
				throw new Exception("本次操作被中止:您认购的方案已经流单");
			} else {
				throw new Exception("本次操作被中止:您认购的方案已经满员");
			}
		}

		Integer allPart = plan.getPart();
		Integer soldPart = plan.getSoldPart();
		if (part > allPart - soldPart)
			throw new Exception("本次操作被中止:您认购份数超过方案的剩余份数,请刷新后重试");

		// 生成合买认购订单
		Integer planOrderNo = this.makePlanOrder(memberId, account, lotteryPlanNo, amount, part, PlanOrderStatus.PAYED, term, BuyType.SELF_BUY,
				(100 * part / allPart), amount * Constant.CONSUME_PRESENT_RATE, "", WalletType.getItem(walletType), MemberFollowingType
						.getItem(followingType), channel,member.getNickName());

		// 会员操作流水（参与合买）设置为失败
		MemberOperLine operLine = new MemberOperLine();
		operLine.setAccount(member.getAccount());
		operLine.setSourceId(sourceId);
		operLine.setCreateDateTime(new Date());
		operLine.setExtendInfo("");
		operLine.setReferer("");
		operLine.setUrl("");
		operLine.setFromPlace(ipSeeker.getCountry(ip) + ipSeeker.getArea(ip));
		operLine.setIp(ip);
		operLine.setTerminalId(ct);
		operLine.setOperType(MemberOperType.CYCHOOSE);
		operLine.setLotteryType(plan.getLotteryType());
		operLine.setStatus(OperLineStatus.SUCCESS);
		operLine.setPhoneNo(phoneNo);
		operLine.setSim(sim);
		operLine.setModel(model);
		operLine.setSystemVersion(systemVersion);
		operLine.setChannel(channel);
		operLine.setVersion(version);
		memberOperLineDao.save(operLine);
		Integer operLineNo = operLine.getOperLineNo();
		LogUtil.out("记录会员操作:合买前置操作成功,流水号:"+operLineNo);

		Integer selectStatus=0; //开奖都默认为0可用
		// 调用冻结接口，冻结认购金额
		double quotaAmount=memberWalletService.freeze(member, new Double(amount), lotteryType.getValue(), lotteryPlanNo, planOrderNo, operLineNo, walletType, member
				.getSourceId(), "加入合买",plan.getPlanType().getValue(),selectStatus);

		//根据订单号处理提款额度
		this.takeOrderUPdate(planOrderNo, quotaAmount); 
		// 判断是否方案发起人购买
		String sourceAccount = plan.getAccount();
		if (sourceAccount.equals(account)) {
			// 更新方案
			int founderPart = part + sourceFounderPart;
			int newPart = part + sourceSoldPart;
			plan.setFounderPart(new Integer(founderPart));
			plan.setSoldPart(new Integer(newPart));
		} else {
			int newPart = part + sourceSoldPart;
			plan.setSoldPart(new Integer(newPart));
		}

		// 份数达到，合买成功。设置状态为成功，扣用户的款。 (增加预投合买后，如果满员时方案未上传，则不处理冻结的金额)
		if (plan.getPart().intValue() == plan.getSoldPart().intValue() && plan.getIsUploadContent() == CommonStatus.YES.getValue()) {

			plan.setPlanStatus(PlanStatus.PAY_FINISH);

			// 检查当前方案是否出票成功，如果成功就将方案置为已出票状态
			if (plan.getPlanTicketStatus() == PlanTicketStatus.TICKET_FINISH) {
				plan.setPlanStatus(PlanStatus.TICKET_OUT);
			}

			List planOrderList = this.lotteryPlanOrderDAO.findSuccessByPlanNo(plan.getPlanNo());

			for (int i = 0; i < planOrderList.size(); i++) {
				LotteryPlanOrder order = (LotteryPlanOrder) planOrderList.get(i);

				Member m = memberService.find(order.getMemberId());// 查询用户信息

				if (order.getBuyType().getValue() == BuyType.BAODI.getValue()) {// 保底的进行退款
					order.setStatus(PlanOrderStatus.RETUREN_MONEY);
					order.setReturnAmountDateTime(new Date());
					this.lotteryPlanOrderDAO.saveOrUpdate(order);
					double quto_amount=order.getQuotaAmount(); //提款退款的提款的额度
					memberWalletService.freezeToAble(m, order.getWalletType().getValue(), new Double(order.getAmount()), lotteryType.getValue(),
							order.getPlanNo(), order.getOrderNo(), new Integer(0), m.getSourceId(), "保底退款",order.getBuyType().getValue(),null,quto_amount);
				} else {
					if (plan.getAccount().equals(order.getAccount())) {
						memberWalletService.freezeToConsume(m, order.getWalletType().getValue(), new Double(order.getAmount()), lotteryType
								.getValue(), order.getPlanNo(), order.getOrderNo(), new Integer(0), m.getSourceId(), "认购合买方案",plan.getPlanType().getValue(),new Integer(0),null);
					} else {
						//LogUtil.out("会员:" + order.getAccount() + " 认购的合买单，冻结转消费");
						memberWalletService.freezeToConsume(m, order.getWalletType().getValue(), new Double(order.getAmount()), lotteryType
								.getValue(), order.getPlanNo(), order.getOrderNo(), new Integer(0), m.getSourceId(), "认购合买方案",plan.getPlanType().getValue(),new Integer(0),null);
					}
				}
			}

		}

		// 检查出票条件进行出票操作
		this.planPrePrintTicket(plan);

		lotteryPlanDAO.saveOrUpdate(plan);
		LogUtil.out("--------------------参与合买服务结束------------------------");

		// start同步sql数据库--------------------------------------------------------------------
		boolean isAutoFollowScheme = false;
		{
			MemberFollowingType.getItem(followingType);
			if (followingType < 0) {
				followingType = -1;
			}
			if ("1".equals(autoHm)) {
				isAutoFollowScheme = true;
			}
			List<Users> userList = sqlUsersService.findByProperty("name", member.getAccount());
			Users user = new Users();
			if (userList != null && !userList.isEmpty()) {
				user = (Users) userList.get(0);
			}
			sqlBusinessService.buySchemesJoin(user, plan.getPlanNo().toString(), part, isAutoFollowScheme, followingType, Double.valueOf(amount),
					plan.getPlanStatus());

		}
		// end同步sql数据库--------------------------------------------------------------------

		return planOrderNo.toString();
	}

	/**
	 * 处理追号逻辑
	 */
	@Override
	public String buyLotteryPlanChase(Member member, Long ct, String[] termChase, String[] multipleChase, String[] amountChase, int iLotteryType,
			String content, Integer amount, String uploadFilePath, int chaseType, int fromPage, String ip, int walletType, String addAttribute,
			GenType gentype, String term, String title, Integer topamount, String description, int fromClient, int iplayType, String phoneNo,
			String sim, String model, String systemVersion, String channel,String version) throws ServiceException, Exception {

		LotteryType lotteryType = (LotteryType) LotteryType.getItem(iLotteryType);
		PlayType playType = PlayType.changeMap.get(iplayType);// (PlayType)PlayType.getItem(iplayType);
//		LogUtil.out("--------------------请求发起追号服务------------------------");
//		LogUtil.out("iLotteryType:" + iLotteryType);
//		LogUtil.out("content:" + content);
//		LogUtil.out("amount:" + amount);
//		LogUtil.out("uploadFilePath:" + uploadFilePath);
//		LogUtil.out("chaseType:" + chaseType);
//		LogUtil.out("fromPage:" + fromPage);
//		LogUtil.out("ip:" + ip);
//		LogUtil.out("walletType:" + walletType);
//		LogUtil.out("genType:" + gentype);
		// System.out.print("Terms:");

		// 查出可支付的walletType
		walletType = findWallet(amount, member);
		
		
		for (int i = 0; i < termChase.length; i++) {
			boolean istermChase=CompileUtil.isNumber(termChase[i]);
			if(!istermChase)
				throw new ServiceException("1", "本次操作被中止:追号彩期验证失败");
		}

		for (int i = 0; i < multipleChase.length; i++) {
			int multip=Integer.parseInt(multipleChase[i]);
			boolean ismultipleChase=CompileUtil.isNumber(multipleChase[i]);
			if(!ismultipleChase)
				throw new ServiceException("1", "本次操作被中止:追号倍数验证失败");
			if(multip<0 || multip >999)
				throw new ServiceException("1", "本次操作被中止:对不起,最大倍数为999");
		}

		for (int i = 0; i < amountChase.length; i++) {
			boolean isamountChase=CompileUtil.isNumber(amountChase[i]);
			if(!isamountChase)
				throw new ServiceException("1", "本次操作被中止:追号金额验证失败");
		}

		// Integer isPackage = PackageType.NOT_PACKAGE.getValue();// 非套餐
		Integer totalTerm = 0;// 总期数
		for (int i = 0; i < termChase.length; i++) {
			totalTerm += 1;
		}

		// 判断传入的期数，倍数，金额个数是否一样
		if (termChase.length != multipleChase.length || termChase.length != amountChase.length)
			throw new ServiceException("1", "本次操作被中止:追号验证失败");

		ContentCheck contentCheck = null;
		if (addAttribute != null && !"".equals(addAttribute)) {
			contentCheck = contentFactory.initFactory(lotteryType.getValue(), Double.parseDouble(amountChase[0]), playType.getValue() + ":" + content
					+ "%" + multipleChase[0] + "-" + addAttribute);
		} else {
			contentCheck = contentFactory.initFactory(lotteryType.getValue(), Double.parseDouble(amountChase[0]), playType.getValue() + ":" + content
					+ "%" + multipleChase[0]);
		}

		if (contentCheck.isPass() != true) {
			if (contentCheck.getMessage() != null && !"".equals(contentCheck.getMessage())) {
				// 3:本次操作被中止:传入金额错误
				throw new ServiceException("3", "本次操作被中止:" + contentCheck.getMessage());
			} else {
				throw new ServiceException("3", "本次操作被中止:追号金额错误");
			}
		}

		// 判断手机购彩
		MemberFollowingType followingtypeIn = null;
		// 判断标识状态

		if (gentype != null) {
			if (gentype.getValue() == GenType.SJ.getValue()) {
				gentype = GenType.getItem(7); // 手机追号
				followingtypeIn = MemberFollowingType.getItem(3); // 手机购彩标识
			}

		}
		Double packageAmount = new Double(amount);// 没有优惠存购彩总额

		// LogUtil.out("totalTerm:" + totalTerm);
		// LogUtil.out("packageAmount:" + packageAmount);

		Isuses buyTerm = this.sqlIsusesDAO.findByLotteryTypeAndTerm(lotteryType, term);
		// 有没有这个彩期
		if (buyTerm == null) {
			throw new ServiceException("1", "本次操作被中止:彩期异常");
		}

		String contentInDb = "";
		SelectType selectType = null;
		if (uploadFilePath == null || uploadFilePath.equals("")) {
			contentInDb = content; // 内容
			selectType = SelectType.CHOOSE_SELF;
		} else {
			contentInDb = uploadFilePath;
			selectType = SelectType.UPLOAD;
		}

		// 检查是否存在追号条件

		boolean isCheck = false;
		if (isCheck) {// 符合条件 将当前期保存到条件追号列表中的第一期
			termChase[0] = buyTerm.getId().toString();
		}

		ChaseStatus chaseStatus = ChaseStatus.CHASING;
		if (termChase.length == 1 && termChase[0].equals(buyTerm.getId().toString())) {
			chaseStatus = ChaseStatus.CHASE_END;
		}

		List<Users> userList = sqlUsersService.findByProperty("name", member.getAccount());
		Users user = new Users();
		if (userList != null && !userList.isEmpty()) {
			user = (Users) userList.get(0);
		}

		// 生成追号表
		Integer chaseNo = this.makeChase(user.getId(), lotteryType, gentype, title, topamount, description, fromClient);

		Integer planNo = 0; // 单据号
		// 会员操作流水（追号）
		MemberOperLine operLine = new MemberOperLine();
		operLine.setAccount(member.getAccount());
		operLine.setSourceId(new Integer(0));
		operLine.setCreateDateTime(new Date());
		operLine.setExtendInfo("");
		operLine.setReferer("");
		operLine.setUrl("");
		operLine.setFromPlace(ipSeeker.getCountry(ip) + ipSeeker.getArea(ip));
		operLine.setIp(ip);
		operLine.setTerminalId(ct);
		operLine.setOperType(MemberOperType.ZHCHOOSE);
		operLine.setStatus(OperLineStatus.SUCCESS);
		operLine.setLotteryType(lotteryType);
		operLine.setPhoneNo(phoneNo);
		operLine.setModel(model);
		operLine.setSim(sim);
		operLine.setSystemVersion(systemVersion);
		operLine.setChannel(channel);
		operLine.setVersion(version);
		memberOperLineDao.save(operLine);
		Integer operLineNo = operLine.getOperLineNo();
//		LogUtil.out("记录会员操作:追号操作成功");

		Integer planType=PlanType.getItem(2).getValue(); //追号进行虚为2
		 Integer selectStatus=0; //追号账户明细显示不处理
		// 开始冻结
		 double qutofAmount=memberWalletService.freeze(member, new Double(amount), iLotteryType, new Integer(0), new Integer(0), operLineNo, walletType, member
				.getSourceId(), "追号方案" + chaseNo,planType,selectStatus);

		 
		//计算每份的提款额度
		double c=(double)qutofAmount/termChase.length*100;
		double d=(double)(Math.round(c))/100.0;
		double qutoperAmount=d; //每份提款额度
			
		int findLotteryType = lotteryType.getValue();
		double chaseAmountSum = 0d;
		// 生成追号期数表
		// 生成代购方案，存储方案,返回方案编号

		String sqlPlayIn = (String) LotteryType.lotteryMap.get(lotteryType.getValue());

		for (int i = 0; i < termChase.length; i++) {
			Isuses chaseLotteryTerm = null;
			if (!termChase[i].equals("0")) {// 非条件追号才检查
				chaseLotteryTerm = this.sqlIsusesDAO.findByLotteryTypeAndTerm(LotteryType.getItem(findLotteryType), termChase[i]);
				if (chaseLotteryTerm.getState().getValue() >= LotteryTermStatus.CLOSE.getValue()) {
					throw new ServiceException("5", "本次操作被中止:您追号的彩期:" + chaseLotteryTerm.getName() + "已经截止投注");
				}

			}

			// 检查每一期追号数据是否正确
			String contentChase = playType.getValue() + ":" + content + "%" + multipleChase[i];
			if (addAttribute != null && !"".equals(addAttribute)) {
				contentChase = contentChase + "-" + addAttribute;
			}
			//LogUtil.out("Verify:" + termChase[i] + " amount:" + amountChase[i] + " content=" + contentChase);
			// 检查内容
			contentCheck = new ContentCheck();
			contentCheck = contentFactory.initFactory(lotteryType.getValue(), Double.valueOf(amountChase[i]), contentChase);
			if (contentCheck.isPass() != true) {
				if (contentCheck.getMessage() != null && !"".equals(contentCheck.getMessage())) {
					throw new ServiceException("3", "本次操作被中止:" + contentCheck.getMessage());
				} else {
					throw new ServiceException("3", "本次操作被中止:方案金额错误");
				}
			}

			chaseAmountSum += Double.valueOf(amountChase[i]);
			String contentNow = "";
			if (termChase[i].equals(buyTerm.getId().toString())) {// 如果追的是当前期，需要去生成方案和订单
				if (selectType == SelectType.UPLOAD) {
					LotteryTypeProps props = this.sqlLotteryTypePropsService.findbuyLotteryType(lotteryType); // buyTerm.getEndTime().getTime()
																												// <
																												// System.currentTimeMillis()
					if (buyTerm.getEndTime().getTime() - props.getDsAheadTime() < System.currentTimeMillis()) {
						throw new ServiceException("4", "本次操作被中止:您追的彩期:" + buyTerm.getName() + "已经截止单式上传");
					}
					contentNow = uploadFilePath;
				} else {
					contentNow = contentCheck.getContent();
					// contentNow = content;
				}
				int multiplePlan = Integer.valueOf(multipleChase[i]);
				Integer amountPlan = Integer.valueOf(amountChase[i]);

				// 判断购买渠道（PC、中奖王、手机）
				if (gentype == null) {
					gentype = GenType.ZH;
				}

				// 判断每人每期最多认购多小方案
				Sites sites = this.sqlSitesService.getSitesInfo();
				if (sites == null) {
					throw new ServiceException("2", "本次操作被中止:站点信息异常");
				}
				try {
					boolean lotteryCount = doBooleanBuyCount(sites, lotteryType, buyTerm.getName(), member.getAccount());
					if (!lotteryCount) {
						throw new ServiceException("3", "本次操作被中止:超过了设定每期每人最大限额！");
					}
				} catch (ServiceException e) {
					throw new ServiceException("3", "本次操作被中止:" + e.getMessage());
				}

				planNo = this.makePlan(lotteryType, member.getId(), member.getAccount(), amountPlan, multiplePlan, contentNow, 1, amountPlan, 0, 1,
						PlanStatus.PAY_FINISH, PublicStatus.NOT_PUBLIC, selectType, buyTerm.getName(), addAttribute, 1, "", playType, PlanType.DG,
						buyTerm.getTerminalEndDateTime(), 0, chaseNo, gentype, null, channel, title,member.getNickName(),buyTerm.getTerminalEndDateTime());

				// 生成订单
				Integer planOrderNo = this.makePlanOrder(member.getId(), member.getAccount(), planNo, amountPlan, 1, PlanOrderStatus.PAYED, buyTerm
						.getName(), BuyType.SELF_BUY, 100, amountPlan * 1, "", WalletType.getItem(walletType), followingtypeIn, channel,member.getNickName());

				// 冻结的钱转消费
				memberWalletService.freezeToConsume(member, walletType, new Double(amountPlan), lotteryType.getValue(), planNo, planOrderNo,
						new Integer(0), member.getSourceId(), "追号购彩",planType,selectStatus,new Integer(0));

				//根据订单号处理提款额度
				this.takeOrderUPdate(planOrderNo, qutoperAmount); 
				
				Integer schemesId = 0;
				{

					// 处理sql方案
					schemesId = this.sqlBusinessService.makeSchemes(user, "", null, title, iplayType, chaseLotteryTerm.getId(), multiplePlan, Double
							.valueOf(amountPlan.toString()), 1, Short.parseShort(PublicStatus.NOT_PUBLIC.getValue() + ""), Short.valueOf("0"), Short
							.valueOf("0"), 1, Double.valueOf("100"), Double.valueOf("0"), content, fromClient, planNo, gentype, true, Double
							.valueOf("0"), Double.valueOf(0), null, channel,"");

					String schemeNumber = chaseLotteryTerm.getName() + sqlPlayIn + schemesId;

					// 更新方案编号
					schemeNumber = this.sqlBusinessService.currentSchemes(schemesId, schemeNumber);

					// 方案备份
					Integer ids = this.sqlBusinessService.makeSchemesNumber(schemesId, Double.valueOf(amountPlan.toString()), multiplePlan,
							contentNow, null);

					// 处得购买详情
					Integer buyDetaileId = this.sqlBusinessService.makeBuyDetailes(user, schemesId, 1, Double.valueOf(amountPlan.toString()),
							fromClient, false);

				}
				ChaseTaskDetails lotteryChaseTerm = new ChaseTaskDetails();

				lotteryChaseTerm.setChaseTaskId(chaseNo);
				lotteryChaseTerm.setSiteId(1);
				lotteryChaseTerm.setDateTime(new Date());
				lotteryChaseTerm.setIsuseId(chaseLotteryTerm.getId());
				lotteryChaseTerm.setPlayTypeId(iplayType);
				lotteryChaseTerm.setMultiple(multiplePlan);
				lotteryChaseTerm.setMoney(Double.valueOf(amountPlan.toString()));
				lotteryChaseTerm.setQuashStatus(Short.parseShort("0"));
				lotteryChaseTerm.setExecuted(true);
				lotteryChaseTerm.setSchemeId(schemesId);
				lotteryChaseTerm.setSecrecyLevel(Short.parseShort("0"));
				lotteryChaseTerm.setLotteryNumber(content);
				lotteryChaseTerm.setShare(1);
				lotteryChaseTerm.setBuyedShare(1);
				lotteryChaseTerm.setAssureShare(0);
				lotteryChaseTerm.setChannel(channel);
				lotteryChaseTerm.setQuotaAmount(qutoperAmount);

				this.sqlChaseTaskDetailsService.save(lotteryChaseTerm);
				// 更新已认购期数

			} else {// 如果追的不是当前期，只需要生成追号期数表
				boolean Executed = false;
				this.makeChaseTerm(chaseNo, lotteryType, termChase[i].toString(), Integer.valueOf(multipleChase[i]), Integer.valueOf(amountChase[i]),
						Executed, iplayType, content, 0,channel,qutoperAmount);

			}

		}
		if (chaseAmountSum != amount) {
//			LogUtil.out("chaseAmountSum:" + chaseAmountSum);
//			LogUtil.out("amount:" + amount);
			throw new ServiceException("4", "本次操作被中止:追号金额错误");
		}
//		LogUtil.out("--------------------发起追号服务结束------------------------");

		return chaseNo.toString();
	}

	/**
	 * 取消条件符合追号条件
	 */
	public void abortChase(Member member, Integer chaseNo, String ip, Long ct) throws Exception {
		ChaseTasks chase = sqlChaseTasksService.find(chaseNo);
		double amount = 0;
		double qutoPerAmount=0d;
		LotteryType lotTypeIn=LotteryType.getItem(chase.getLotteryId());
		if (chase.getQuashStatus() == ChaseTermStatus.NOT_CANCEL.getValue()) {
			List<ChaseTasksBean> chaseTermList = this.sqlChaseTaskDetailsService.findByChaseNoTermList(chaseNo);
			for (int i = 0; i < chaseTermList.size(); i++) {
				ChaseTasksBean chaseTerm = (ChaseTasksBean) chaseTermList.get(i);
				ChaseTaskDetails detail = this.sqlChaseTaskDetailsService.findByIdForUpdate(chaseTerm.getDetailId());
				if (!detail.isExecuted()) {
					if (detail.getQuashStatus() == ChaseTermStatus.NOT_CANCEL.getValue()) {
						amount += detail.getMoney();
						double qutoamount=detail.getQuotaAmount()==null?detail.getMoney():detail.getQuotaAmount();
						qutoPerAmount +=qutoamount;
						detail.setQuashStatus(Short.valueOf(ChaseTermStatus.SYS_CANCEL.getValue() + ""));
						this.sqlChaseTaskDetailsService.saveOrUpdate(detail);
					}
				}
			}
		}
		if (amount > 0) {
			chase.setQuashStatus(Short.valueOf(ChaseTermStatus.SYS_CANCEL.getValue() + ""));
			this.sqlChaseTasksService.saveOrUpdate(chase);
			Integer operNo = null;
			if (ip != null && !ip.equals("")) {
				MemberOperLine operLine = new MemberOperLine();
				operLine.setAccount(member.getAccount());
				operLine.setSourceId(member.getSourceId());
				operLine.setCreateDateTime(new Date());
				operLine.setExtendInfo("");
				operLine.setReferer("");
				operLine.setUrl("");
				operLine.setIp(ip);
				operLine.setTerminalId(ct);
				operLine.setOperType(MemberOperType.CHASE_CANCEL);
				operLine.setStatus(OperLineStatus.SUCCESS);
				operLine.setLotteryType(lotTypeIn);
				operLine.setFromPlace("null");
				this.memberOperLineDao.save(operLine);
				operNo = operLine.getOperLineNo();
			}
			Integer planType=PlanType.getItem(2).getValue(); //追号虚拟默认为2
			Integer change=0; //标识追号的处理
			memberWalletService.freezeToAble(member, WalletType.DUOBAO.getValue(), amount, chase.getLotteryId(), 0, 0, operNo, member.getSourceId(),
					"撤销追号方案:" + chaseNo,planType,change,qutoPerAmount);
		}
	}

	// 执行追号购买
	public void doChase(ChaseTasksBean chaseTerm, Isuses nowTerm) throws ServiceException, Exception {
		if (nowTerm.getState().getValue() == LotteryTermStatus.CLOSE.getValue()) {
			throw new ServiceException("11", nowTerm.getName() + "暂停销售");
		} else {
			ChaseTasks chase = this.sqlChaseTasksService.findByIdForUpdate(chaseTerm.getChaseTaskId());

			Users user = sqlUsersService.findbyId(chaseTerm.getUserId());
			if (user == null) {
//				LogUtil.out("追号执行不存在该用户==================");
				return;
			}

			Member member = (Member) this.memberService.findByAccount(user.getName());
//			LogUtil.out("开始执行追号==>追号No:" + chaseTerm.getChaseTaskId() + " Account:" + member.getAccount());
			ChaseTaskDetails detailTerm = this.sqlChaseTaskDetailsService.findByIdForUpdate(chaseTerm.getDetailId());
			if (chase.getQuashStatus() == ChaseTermStatus.NOT_CANCEL.getValue()) {
				if (!detailTerm.isExecuted()) {
					if (detailTerm.getQuashStatus() == ChaseTermStatus.NOT_CANCEL.getValue()) {

						GenType gentype = null;
						if (chase.getFromClient() == 1) {
							gentype = GenType.ZH;
						}

						String addAttribute = "1";
						PlayType playType = PlayType.changeMap.get(detailTerm.getPlayTypeId());// (PlayType)PlayType.getItem(iplayType);

						LotteryType lotTypeIn = LotteryType.getItem(chase.getLotteryId());

						// 判断每人每期最多认购多小方案
						Sites sites = this.sqlSitesService.getSitesInfo();
						if (sites == null) {
							throw new ServiceException("2", "本次操作被中止:站点信息异常");
						}
						try {
							boolean lotteryCount = doBooleanBuyCount(sites, lotTypeIn, nowTerm.getName(), member.getAccount());
							if (!lotteryCount) {
								throw new ServiceException("3", "本次操作被中止:超过了设定每期每人最大限额！");
							}
						} catch (ServiceException e) {
							throw new ServiceException("3", "本次操作被中止:" + e.getMessage());
						}

						// 生成订单
						Integer planNo = this.makePlan(lotTypeIn, member.getId(), member.getAccount(), Float.valueOf(
								Double.toString(detailTerm.getMoney())).intValue(), detailTerm.getMultiple(), detailTerm.getLotteryNumber(), 1, Float
								.valueOf(Double.toString(detailTerm.getMoney())).intValue(), 0, 1, PlanStatus.PAY_FINISH, PublicStatus.NOT_PUBLIC,
								SelectType.CHOOSE_SELF, nowTerm.getName(), addAttribute, 1, "", playType, PlanType.DG, nowTerm.getTerminalEndDateTime(),
								0, chase.getId(), gentype, null, "", "",member.getNickName(),nowTerm.getTerminalEndDateTime());

						// 判断手机购彩
						MemberFollowingType followingtypeIn = null;
						// 判断标识状态

						if (gentype != null) {
							if (gentype.getValue() == GenType.SJ.getValue()) {
								gentype = GenType.getItem(7); // 手机追号
								followingtypeIn = MemberFollowingType.getItem(3); // 手机购彩标识
							}

						}

						// 生成订单详情
						Integer planOrderNo = this.makePlanOrder(member.getId(), member.getAccount(), planNo, Float.valueOf(
								Double.toString(detailTerm.getMoney())).intValue(), 1, PlanOrderStatus.PAYED, nowTerm.getName(), BuyType.SELF_BUY,
								100, Float.valueOf(Double.toString(detailTerm.getMoney())).intValue() * 1, "", WalletType.getItem(1),
								followingtypeIn, "",member.getNickName());

						Integer planType=PlanType.getItem(2).getValue(); //追号虚拟默认为2
						// 冻结的钱转消费
						Integer selectStatus=0; //追号消费先全部默认为可用
						Integer change=0; //标识追号
						memberWalletService.freezeToConsume(member, WalletType.getItem(1).getValue(), detailTerm.getMoney(), lotTypeIn.getValue(),
								planNo, planOrderNo, new Integer(0), member.getSourceId(), "追号方案:" + chase.getId(),planType,selectStatus,change);

						double qutoperAmount=detailTerm.getQuotaAmount()==null?detailTerm.getMoney():detailTerm.getQuotaAmount();
						//根据订单号处理提款额度
						this.takeOrderUPdate(planOrderNo, qutoperAmount); 
						
						Integer schemesId = 0;
						{

							String sqlPlayIn = (String) LotteryType.lotteryMap.get(lotTypeIn.getValue());

							// 处理sql方案
							schemesId = this.sqlBusinessService.makeSchemes(user, "", null, chase.getTitle(), detailTerm.getPlayTypeId(), nowTerm
									.getId(), detailTerm.getMultiple(), detailTerm.getMoney(), 1, Short.parseShort(PublicStatus.NOT_PUBLIC.getValue()
									+ ""), Short.valueOf("0"), Short.valueOf("0"), 1, Double.valueOf("100"), Double.valueOf("0"), detailTerm
									.getLotteryNumber(), chase.getFromClient(), planNo, gentype, true, Double.valueOf("0"), Double.valueOf(0), null,
									"","");

							String schemeNumber = nowTerm.getName() + sqlPlayIn + schemesId;

							// 更新方案编号
							schemeNumber = this.sqlBusinessService.currentSchemes(schemesId, schemeNumber);

							// 方案备份

							Integer ids = this.sqlBusinessService.makeSchemesNumber(schemesId, detailTerm.getMoney(), detailTerm.getMultiple(),
									detailTerm.getLotteryNumber(), null);
							// 处得购买详情
							Integer buyDetaileId = this.sqlBusinessService.makeBuyDetailes(user, schemesId, 1, detailTerm.getMoney(), chase
									.getFromClient(), true);

						}

						// 更新追号状状

						detailTerm.setSchemeId(schemesId);
						detailTerm.setExecuted(true);
						this.sqlChaseTaskDetailsService.saveOrUpdate(detailTerm);
//						LogUtil.out("开始执行追号==>追号No:" + chase.getId() + " Account:" + member.getAccount() + " PlanNo:" + planNo + " OrderNo:"
//								+ planOrderNo + "<==结束");

					}
				}

			}
		}
	}

	// 方案处理(根据条件判断是否符合出票条件)
	private void planPrePrintTicket(LotteryPlan lotteryPlan) {

		// 检查方案条件是否符合出票条件
		if (!checkPlanCondition(lotteryPlan))
			return;

		// 置方案PLANTICKETSTATUS 标志为可出票
		lotteryPlan.setIsAbleTicketing(CommonStatus.YES.getValue());
	}

	private boolean checkPlanCondition(LotteryPlan lotteryPlan) {
		try {

			// 未上传方案不符合出票要求
			if (lotteryPlan.getSelectType().getValue() == SelectType.UPLOAD.getValue()
					&& lotteryPlan.getIsUploadContent() == CommonStatus.NO.getValue()) {
				return false;
			}
			// ----------------------------------------------------------------------------------

			// 如果合买方案参与+保底 >= 方案总份数 * 90% 就可以进行出票操作
			if ((lotteryPlan.getSoldPart() + lotteryPlan.getReservePart()) * 1.0 / lotteryPlan.getPart() >= 0.9)
				return true;

			// 如果是奇虎科技发的单，保证出票
			// if ("奇虎科技".equals(lotteryPlan.getAccount()))
			// return true;

			return false;

		} catch (Exception Ex) {
//			LogUtil.out("=====检查方案出票条件失败，方案编号:" + String.valueOf(lotteryPlan.getPlanNo()));
			Ex.printStackTrace();
			return false;
		}
	}

	/**
	 * 功能： 生成订单(增加followingType参数) 传入参数： 输入参数： memberId ---会员ID account ---会员帐号
	 * term ---期次 content ---投注号码内容 amount ---交易金额 selectType ---投注方式
	 * uploadFilePath ---上传方案路径 multiple ---倍数 walletType ---支付类型 lotteryTyep
	 * ---彩票类型 输出参数： 作者： lm 日期： 2013-05-23
	 */
	private Integer makePlanOrder(Integer memberId, String account, Integer planNo, Integer amount, int part, PlanOrderStatus planOrderStatus,
			String term, BuyType buyType, int rebate, int score, String title, WalletType walletType, MemberFollowingType followingType,
			String channel,String nickName) {
		LotteryPlanOrder lotteryPlanOrder = new LotteryPlanOrder();
		lotteryPlanOrder.setPlanNo(planNo);
		lotteryPlanOrder.setAccount(account);
		lotteryPlanOrder.setNickName(nickName);
		lotteryPlanOrder.setAmount(amount);
		lotteryPlanOrder.setBuyType(buyType);
		lotteryPlanOrder.setCreateDateTime(new Date());
		lotteryPlanOrder.setExperience(new Integer(0));
		lotteryPlanOrder.setScore(new Integer(amount * Constant.CONSUME_PRESENT_RATE));
		lotteryPlanOrder.setMemberId(memberId);
		lotteryPlanOrder.setPosttaxPrize(new Double(0));
		// 未开奖，不用设置奖金结算日期
		// lotteryPlanOrder.setPrizeSettleDateTime(((LotteryTerm)clientLotteryTermDAO.findByTerm(term)).getOpenDateTime());
		lotteryPlanOrder.setPart(new Integer(part));
		lotteryPlanOrder.setPrizeSettleStatus(new Integer(1));
		// lotteryPlanOrder.setScore(new Integer(score));
		lotteryPlanOrder.setStatus(planOrderStatus);
		lotteryPlanOrder.setWalletType(walletType);
		lotteryPlanOrder.setVerifyMd5(DbDataVerify.getLotteryPlanOrderVerify(lotteryPlanOrder));
		lotteryPlanOrder.setFollowingType(followingType);
		lotteryPlanOrder.setChannel(channel);
 		lotteryPlanOrderDAO.save(lotteryPlanOrder);
		return lotteryPlanOrder.getOrderNo();
	}

	/**
	 * 
	 * 
	 * 功能： 生成方案 输入参数： lotteryType ---彩票类型 memberId ---会员ID account ---会员帐号
	 * amount ---交易金额 multiple ---倍数 content ---投注号码内容 part ---购买份数 perAmount
	 * ---每份金额 reservePart ---保底份数 soldPart ---已经认购份数 planStatus ---方案状态
	 * publicStatus ---方案是否公开 selectType ---投注方式 term ---期次 isAddAttribute
	 * ---是否追加 founderPart ---发起者购买份数 planDesc ---方案描述 playType ---玩法类别 planType
	 * ---方案类别 endDate ---结束时间 comission ---佣金 chaseNo ---追号编号 genType ---方案类型
	 * --nickName 昵称
	 * 
	 * 输出参数： 作者： lm 日期： 2013-05-23
	 */
	private Integer makePlan(LotteryType lotteryType, Integer memberId, String account, Integer amount, int multiple, String content, int part,
			Integer perAmount, int reservePart, int soldPart, PlanStatus planStatus, PublicStatus publicStatus, SelectType selectType, String term,
			String isAddAttribute, int founderPart, String planDesc, PlayType playType, PlanType planType, Date endDate, int comission,
			Integer chaseNo, GenType genType, String isPay, String channel, String title,String nickName,Date lateTime) {

		LotteryPlan lotteryPlan = new LotteryPlan();
		lotteryPlan.setLotteryType(lotteryType);
		lotteryPlan.setMemberId(memberId);
		lotteryPlan.setAccount(account);
		lotteryPlan.setNickName(nickName);
		lotteryPlan.setAmount(new Integer(amount));
		lotteryPlan.setMultiple(new Integer(multiple));
		lotteryPlan.setContent(content);
		lotteryPlan.setClicks(new Integer(0));
		lotteryPlan.setCreateDateTime(new Date());
		lotteryPlan.setDealDateTime(endDate);
		lotteryPlan.setExperience(new Integer(0));
		lotteryPlan.setCommision(comission);
		lotteryPlan.setChannel(channel);
		
		lotteryPlan.setLateDateTime(lateTime);

		lotteryPlan.setIsSuperMan(new Integer(1));

		lotteryPlan.setSelectType(selectType);
		{
			if (content != null && !content.equals("")) {// 如果有上传文件的话
				lotteryPlan.setIsUploadContent(CommonStatus.YES.getValue());
			} else {
				lotteryPlan.setIsUploadContent(CommonStatus.NO.getValue());
			}

		}

		lotteryPlan.setPart(new Integer(part));
		lotteryPlan.setPerAmount(perAmount);
		lotteryPlan.setReservePart(new Integer(reservePart));
		lotteryPlan.setSoldPart(new Integer(soldPart));
		lotteryPlan.setPlanDesc(planDesc);
		lotteryPlan.setTitle(title);

		lotteryPlan.setPlanStatus(planStatus);
		lotteryPlan.setPlanType(planType);
		lotteryPlan.setPlayType(playType);
		lotteryPlan.setPosttaxPrize(new Double(0));
		lotteryPlan.setPretaxPrize(new Double(0));
		lotteryPlan.setPrizeContent("");
		lotteryPlan.setPublicStatus(publicStatus);
		lotteryPlan.setTerm(term);
		lotteryPlan.setWinStatus(WinStatus.NOT_RESULT);
		lotteryPlan.setAddAttribute(isAddAttribute);
		lotteryPlan.setFounderPart(new Integer(founderPart));
		lotteryPlan.setIsTop(CommonStatus.NO.getValue());
		lotteryPlan.setVerifyMd5(DbDataVerify.getLotteryPlanVerify(lotteryPlan));
		lotteryPlan.setIsAbleTicketing(CommonStatus.NO.getValue());
		lotteryPlan.setPlanTicketStatus(PlanTicketStatus.NO_PROCESS);
		if (isPay != null) {
			lotteryPlan.setAutomaticType(isPay);
		}

		// --------------------------------------------这个只是暂进，需要屏掉以后做了出票------------------------------------
		// if(LotteryType.JCZQList.contains(lotteryType)){
		// lotteryPlan.setPrintTicketDateTime(new Date());
		// }
		// -------------------------------------------------end
		// -------------------------------

		// 检查出票条件进行出票操作
		this.planPrePrintTicket(lotteryPlan);
		lotteryPlan.setModel(chaseNo);
		lotteryPlan.setGenType(genType);
		lotteryPlanDAO.save(lotteryPlan);
		return lotteryPlan.getPlanNo();
	}

	/**
	 * 功能： 生成订单 传入参数： 输入参数： memberId ---会员ID account ---会员帐号 term ---期次 content
	 * ---投注号码内容 amount ---交易金额 selectType ---投注方式 uploadFilePath ---上传方案路径
	 * multiple ---倍数 walletType ---支付类型 lotteryTyep ---彩票类型 输出参数： 作者： 滕云 日期：
	 * 2008-3-5
	 */
	@SuppressWarnings("unused")
	private Integer makePlanOrder(Integer memberId, String account, Integer planNo, Integer amount, int part, PlanOrderStatus planOrderStatus,
			String term, BuyType buyType, String title, WalletType walletType,String nickName,String channel) {
		LotteryPlanOrder lotteryPlanOrder = new LotteryPlanOrder();
		lotteryPlanOrder.setPlanNo(planNo);
		lotteryPlanOrder.setAccount(account);
		lotteryPlanOrder.setAmount(amount);
		lotteryPlanOrder.setBuyType(buyType);
		lotteryPlanOrder.setCreateDateTime(new Date());
		lotteryPlanOrder.setExperience(new Integer(0));
		lotteryPlanOrder.setScore(new Integer(amount * Constant.CONSUME_PRESENT_RATE));
		lotteryPlanOrder.setMemberId(memberId);
		lotteryPlanOrder.setPosttaxPrize(new Double(0));
		// 未开奖，不用设置奖金结算日期
		// lotteryPlanOrder.setPrizeSettleDateTime(((LotteryTerm)clientLotteryTermDAO.findByTerm(term)).getOpenDateTime());
		lotteryPlanOrder.setPart(new Integer(part));
		lotteryPlanOrder.setPrizeSettleStatus(new Integer(1));
		lotteryPlanOrder.setStatus(planOrderStatus);
		lotteryPlanOrder.setWalletType(walletType);
		lotteryPlanOrder.setNickName(nickName);
		lotteryPlanOrder.setChannel(channel);
		lotteryPlanOrder.setVerifyMd5(DbDataVerify.getLotteryPlanOrderVerify(lotteryPlanOrder));
		lotteryPlanOrderDAO.save(lotteryPlanOrder);
		return lotteryPlanOrder.getOrderNo();
	}

	/**
	 * 功能： 生成追号表 传入参数： 输入参数： account ---会员帐号 lotteryType ---彩种 chaseType ---追号类型
	 * amount ---金额 content ---投注号码内容 status ---追号状态 walletType ---钱包类型
	 * selectType ---方案类型 playType ---玩法类型 multiple ---倍数 addAttribute ---是否追加
	 * 
	 * 输出参数： 作者： lm 日期： 2013-6-2
	 */
	private Integer makeChase(Integer userId, LotteryType lotteryType, GenType gentype, String title, double topamount, String description,
			int fromClient) {

		ChaseTasks lotteryChase = new ChaseTasks();
		lotteryChase.setSiteId(1);
		lotteryChase.setUserId(userId);
		lotteryChase.setDateTime(new Date());
		lotteryChase.setTitle(title);
		lotteryChase.setLotteryId(lotteryType.getValue());
		lotteryChase.setQuashStatus(Short.parseShort("0"));
		lotteryChase.setStopWhenWinMoney(topamount);
		lotteryChase.setDescription(description);
		lotteryChase.setSchemeBonusScale(Double.valueOf("0"));
		lotteryChase.setFromClient(fromClient);

		sqlChaseTasksService.save(lotteryChase);
		return lotteryChase.getId();
	}

	/**
	 * 功能： 生成追号期次表 传入参数： 输入参数： chaseNo ---追号编号 lotteryType ---彩种 content
	 * ---投注号码内容 term ---期次 status ---该期追号状态 multiple ---倍数 amount ---每期金额 输出参数：
	 * 作者： lm 日期： 2013-6-2
	 */
	private void makeChaseTerm(Integer chaseNo, LotteryType lotteryType, String term, int multiple, Integer amount, boolean Executed,
			Integer playTypeId, String lotteryNumber, Integer SecrecyLevel,String channel,double qutoAmount) {
		ChaseTaskDetails lotteryChaseTerm = new ChaseTaskDetails();

		lotteryChaseTerm.setChaseTaskId(chaseNo);
		lotteryChaseTerm.setSiteId(1);
		lotteryChaseTerm.setDateTime(new Date());
		lotteryChaseTerm.setIsuseId(Integer.parseInt(term));
		lotteryChaseTerm.setPlayTypeId(playTypeId);
		lotteryChaseTerm.setMultiple(multiple);
		lotteryChaseTerm.setMoney(Double.valueOf(amount.toString()));
		lotteryChaseTerm.setQuashStatus(Short.parseShort("0"));
		lotteryChaseTerm.setExecuted(Executed);
		lotteryChaseTerm.setSchemeId(null);
		lotteryChaseTerm.setSecrecyLevel(Short.parseShort(SecrecyLevel.toString()));
		lotteryChaseTerm.setLotteryNumber(lotteryNumber);
		lotteryChaseTerm.setShare(1);
		lotteryChaseTerm.setBuyedShare(1);
		lotteryChaseTerm.setAssureShare(0);
		lotteryChaseTerm.setChannel(channel);
        lotteryChaseTerm.setQuotaAmount(qutoAmount);
		
		this.sqlChaseTaskDetailsService.save(lotteryChaseTerm);

	}

	private boolean doBooleanBuyCount(Sites info, LotteryType lotteryType, String term, String account) throws ServiceException, Exception {
		// 获取站点信息
		boolean count = false;
		Sites sites = info;
		// 获取每期认购的最大份数;
		Short Opt_InitiateSchemeMaxNum = sites.getOptInitiateSchemeMaxNum();

		int maxNum = Integer.valueOf(Opt_InitiateSchemeMaxNum);

		int PlanCount = this.lotteryPlanDAO.findBuyTermMaxCount(lotteryType, term, account);

		if (PlanCount >= maxNum) {
			throw new ServiceException("3", "本次操作被中止:每人每期最多只能发起" + maxNum + "个方案");
		}
		count = true;

		return count;

	}

	/***
	 * 用户每期认购的最大限额
	 * 
	 * @param lotteryType
	 * @param term
	 * @param account
	 * @return
	 */
	@Override
	public boolean doBuyLotteryPlanCount(Sites info, LotteryType lotteryType, String term, String account) throws ServiceException, Exception {

		// 获取站点信息
		boolean count = doBooleanBuyCount(info, lotteryType, term, account);
		return count;
	}
	
	//根据订单号处理当前提款额度
	public void takeOrderUPdate(int lotteryOrderId,double quotaAmount){
		LotteryPlanOrder order =this.lotteryPlanOrderDAO.find(LotteryPlanOrder.class, lotteryOrderId);
		if(order!=null){
			order.setQuotaAmount(quotaAmount);
			this.lotteryPlanOrderDAO.saveOrUpdate(order);
		}
	}

	private int findWallet(double amount, Member member) {
		return WalletType.DUOBAO.getValue();
	}

	public void setLotteryPlanDAO(LotteryPlanDAO lotteryPlanDAO) {
		this.lotteryPlanDAO = lotteryPlanDAO;
	}

	public void setIpSeeker(IpSeeker ipSeeker) {
		this.ipSeeker = ipSeeker;
	}

	public void setMemberOperLineDao(MemberOperLineDAO memberOperLineDao) {
		this.memberOperLineDao = memberOperLineDao;
	}

	public void setLotteryPlanOrderDAO(LotteryPlanOrderDAO lotteryPlanOrderDAO) {
		this.lotteryPlanOrderDAO = lotteryPlanOrderDAO;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setSqlIsusesDAO(IsusesDAO sqlIsusesDAO) {
		this.sqlIsusesDAO = sqlIsusesDAO;
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

	public void setSqlBusinessService(BusinessService sqlBusinessService) {
		this.sqlBusinessService = sqlBusinessService;
	}

	public ContentFactory getContentFactory() {
		return contentFactory;
	}

	public void setContentFactory(ContentFactory contentFactory) {
		this.contentFactory = contentFactory;
	}

	public void setSqlLotteryTypePropsService(LotteryTypePropsService sqlLotteryTypePropsService) {
		this.sqlLotteryTypePropsService = sqlLotteryTypePropsService;
	}

}
