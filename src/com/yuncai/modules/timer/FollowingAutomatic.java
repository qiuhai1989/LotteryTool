package com.yuncai.modules.timer;
import java.util.*;

import com.yuncai.core.tools.LogUtil;
import com.yuncai.modules.lottery.model.Oracle.FollowingPlanInfo;
import com.yuncai.modules.lottery.model.Oracle.LotteryPlan;
import com.yuncai.modules.lottery.model.Oracle.LotteryPlanOrder;
import com.yuncai.modules.lottery.model.Oracle.Member;
import com.yuncai.modules.lottery.model.Oracle.MemberWallet;
import com.yuncai.modules.lottery.model.Oracle.toolType.FollowingPlanStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.FromPage;
import com.yuncai.modules.lottery.model.Oracle.toolType.MemberFollowingType;
import com.yuncai.modules.lottery.model.Oracle.toolType.WalletType;
import com.yuncai.modules.lottery.model.Sql.BuyDetails;
import com.yuncai.modules.lottery.model.Sql.Schemes;
import com.yuncai.modules.lottery.model.Sql.Users;
import com.yuncai.modules.lottery.model.Sql.vo.CustomFriendBean;
import com.yuncai.modules.lottery.service.oracleService.lottery.LotteryPlanOrderService;
import com.yuncai.modules.lottery.service.oracleService.lottery.LotteryPlanService;
import com.yuncai.modules.lottery.service.oracleService.lottery.TradeService;
import com.yuncai.modules.lottery.service.oracleService.member.FollowingPlanInfoService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberWalletService;
import com.yuncai.modules.lottery.service.sqlService.lottery.BusinessService;
import com.yuncai.modules.lottery.service.sqlService.lottery.SchemesService;
import com.yuncai.modules.lottery.service.sqlService.lottery.buyDetailesService;
import com.yuncai.modules.lottery.service.sqlService.user.CustomFriendFollowSchemesService;
import com.yuncai.modules.lottery.service.sqlService.user.UsersService;

public class FollowingAutomatic extends Thread{
	private String initiatorAccount=null;
	private String followersAccount=null;
	private Integer lotteryType=-1;
	private Integer followingType=-1;
	private Integer planNo=-1;
	private Integer shareMoney=-1;  //第份金额
	private Integer buyShareStart=-1;  //每次跟单份数
	private Integer moneyEnd=-1;       //最大金额
	private Integer followSchemesMoney=-1; //最终销售金额
	private Integer remainingMoney=-1; //剩余金额
	private double ableBalance; //可用金额
	private Integer followSchemesShare; //份数;
	private Integer ablePart=-1; //总份数
	
	private FollowingPlanInfoService followingPlanInfoService; //处理存储自动跟单号
	private MemberWalletService memberWalletService;
	private TradeService tradeService;
	private CustomFriendFollowSchemesService sqlCustomFriendFollowSchemesService;
	private LotteryPlanService lotteryPlanService;
	private LotteryPlanOrderService lotteryPlanOrderService;
	private MemberService memberService;
	private UsersService sqlUsersService;
	private BusinessService sqlBusinessService; //sql投注
	private SchemesService sqlSchemesService;
	
	@SuppressWarnings("unchecked")
	public void run() {
		while (true) {
			//LogUtil.out("开始自动跟单");
			/*
			 * 查找未进行处理的自动跟单方案
			 */
			List<FollowingPlanInfo> followingPlanInfoList = getUnprocessedPlanInfoList();

			for (FollowingPlanInfo info : followingPlanInfoList) {
				boolean isProcessed = true;
				// 初始化数据
				planNo = info.getPlanNo();
				followingType = info.getFollowingType();
				LotteryPlan currentPlan = lotteryPlanService.find(planNo);
				lotteryType = currentPlan.getLotteryType().getValue();
				initiatorAccount = currentPlan.getAccount();
				shareMoney=currentPlan.getPerAmount();
				remainingMoney=currentPlan.getAmount()- (currentPlan.getPerAmount() * currentPlan.getSoldPart());
				ablePart=currentPlan.getPart()-currentPlan.getSoldPart();
				
				//先要查找一下sql中是否存在这个数据 //为了防止数据sql还没进行操作
				Schemes sqlplan=this.sqlSchemesService.findSchemeByPlanNOForUpdate(planNo);
				if (sqlplan == null) {
					LogUtil.out("本次操作被中止:没有这个方案编号sql数据暂时不存在这个方案");
					continue;
				}
				
				//检查是否流单或撤单(应该对应Lottery_plan的plan_status。后期需要再更改)
				if(currentPlan.getPlanStatus().getValue() == 5 || currentPlan.getPlanStatus().getValue() == 6){
					continue;
				}
				// 判断该方案是否过期
				if ((currentPlan.getDealDateTime().getTime() - System.currentTimeMillis()) < 0) {
					info.setPlanStatus(FollowingPlanStatus.END.getValue());
					//进行已处理记录操作
					info.setProcessedStatus(0);
					followingPlanInfoService.saveOrUpdate(info);
					continue;
				}
				// 查看方案是否已满员
				Integer planBalance = currentPlan.getAmount() - currentPlan.getSoldPart();
				if (planBalance == 0) {
					info.setPlanStatus(FollowingPlanStatus.COMPLETION.getValue());
					//进行已处理记录操作
					info.setProcessedStatus(0);
					followingPlanInfoService.saveOrUpdate(info);
					continue;
				}
				if (followingType == MemberFollowingType.HEMAI.getValue()) {
					// 查找已成功定制发单人的用户
					
					List<CustomFriendBean> coalitionInfoList=sqlCustomFriendFollowSchemesService.findUserBySchemes(initiatorAccount, lotteryType);
					// 对每个定制用户进行操作
					for (CustomFriendBean coalitionInfo : coalitionInfoList) {
						
						Users user=sqlUsersService.findbyId(coalitionInfo.getFollowUserID());
						if(user==null){
							continue;
						}
						// 初始化info的数据
						followersAccount = user.getName(); //用户
						
						buyShareStart=coalitionInfo.getBuyShareStart();
						moneyEnd=Float.valueOf(Double.toString(coalitionInfo.getMoneyEnd())).intValue();
						
						//如果已经售完
						if(ablePart<=0){
							info.setProcessedStatus(0);
							followingPlanInfoService.saveOrUpdate(info);
							break;
						}
						
						//单位金额*最底份数大于最大金额
						if(shareMoney * buyShareStart > moneyEnd){
							continue;
						}
						
						if(shareMoney * buyShareStart >= moneyEnd){
							followSchemesMoney=moneyEnd;
						}else{
							followSchemesMoney=shareMoney * buyShareStart;
						}
						
						//根总剩余金额对比
						if(followSchemesMoney > remainingMoney){
							followSchemesMoney=remainingMoney;
						}
						
						if(remainingMoney < buyShareStart){
							continue;
						}
						
						//查找他的金额
						Member member=this.memberService.findByAccount(followersAccount);
						if(member==null){
							continue;
						}
						MemberWallet wallet=this.memberWalletService.findByAccount(member.getAccount());
						ableBalance=wallet.getAbleBalance();
						
						if(ableBalance<followSchemesMoney){
							continue;
						}
						
						followSchemesShare=followSchemesMoney/shareMoney;
						
						//开始跟单
						LotteryPlanOrder planOrder=new LotteryPlanOrder();
						planOrder.setAccount(followersAccount);
						planOrder.setPlanNo(planNo);
						List<LotteryPlanOrder> orderExampleList = lotteryPlanOrderService.findByExamble(planOrder);
						boolean isFollowing = false;
						for (LotteryPlanOrder order : orderExampleList) {
							if (order.getOrderNo() != planNo && order.getFollowingType() != MemberFollowingType.NONE) {
								isFollowing = true;
								break;
							}
						}
						if (isFollowing) {
							continue;
						}
						try {
							String autoHm="1"; //判断sql是否自动跟单
							tradeService.buyLotteryPlanJoin(member, 0L, planNo, followSchemesShare, FromPage.INDEX.getValue(), "",
									WalletType.DUOBAO.getValue(), followingType, member.getSourceId(),autoHm,"","","","","","1");
//							
//							//处理sql (因为oralce服务已加入了sql的合买)
//							
//							sqlBusinessService.buySchemesJoin(user, planNo.toString(), followSchemesShare, true, 1, Double.valueOf(Integer.toString(followSchemesShare*shareMoney)));
//							
//							
							//初始总份数
							remainingMoney=remainingMoney-followSchemesMoney;
							ablePart=ablePart-followSchemesShare;
							//info.setProcessedStatus(0);
							//followingPlanInfoService.saveOrUpdate(info);
						} catch (Exception e) {
							e.printStackTrace();
							isProcessed = false;
							info.setProcessedStatus(-1);
							followingPlanInfoService.saveOrUpdate(info);
						}
					}
					if(isProcessed){
						//进行已处理记录操作
						info.setProcessedStatus(0);
						followingPlanInfoService.saveOrUpdate(info);
					}
				} 
			}
			try {
				sleep(60000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		int a=7/3;
		System.out.println(a);
	}
	
	@SuppressWarnings( { "unused", "unchecked" })
	private List<FollowingPlanInfo> getUnprocessedPlanInfoList() {
		FollowingPlanInfo info = new FollowingPlanInfo();
		info.setPlanStatus(FollowingPlanStatus.UNFINISHED.getValue());
		info.setProcessedStatus(-1);
		List<FollowingPlanInfo> list=this.followingPlanInfoService.findByExamble(info);
		return list;
	}

	public void setFollowingPlanInfoService(FollowingPlanInfoService followingPlanInfoService) {
		this.followingPlanInfoService = followingPlanInfoService;
	}

	public void setMemberWalletService(MemberWalletService memberWalletService) {
		this.memberWalletService = memberWalletService;
	}

	public void setTradeService(TradeService tradeService) {
		this.tradeService = tradeService;
	}

	public void setSqlCustomFriendFollowSchemesService(CustomFriendFollowSchemesService sqlCustomFriendFollowSchemesService) {
		this.sqlCustomFriendFollowSchemesService = sqlCustomFriendFollowSchemesService;
	}

	public void setLotteryPlanService(LotteryPlanService lotteryPlanService) {
		this.lotteryPlanService = lotteryPlanService;
	}

	public void setLotteryPlanOrderService(LotteryPlanOrderService lotteryPlanOrderService) {
		this.lotteryPlanOrderService = lotteryPlanOrderService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setSqlUsersService(UsersService sqlUsersService) {
		this.sqlUsersService = sqlUsersService;
	}

	public void setSqlBusinessService(BusinessService sqlBusinessService) {
		this.sqlBusinessService = sqlBusinessService;
	}

	public void setSqlSchemesService(SchemesService sqlSchemesService) {
		this.sqlSchemesService = sqlSchemesService;
	}

	

}
