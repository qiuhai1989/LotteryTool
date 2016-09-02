package com.yuncai.modules.lottery.service.sqlService.impl.lottery;

import java.util.Date;
import java.util.List;




import com.yuncai.core.longExce.ServiceException;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.modules.lottery.dao.sqlDao.lottery.SchemesDAO;

import com.yuncai.modules.lottery.model.Oracle.toolType.GenType;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanTicketStatus;

import com.yuncai.modules.lottery.model.Sql.BuyDetails;
import com.yuncai.modules.lottery.model.Sql.Schemes;
import com.yuncai.modules.lottery.model.Sql.SchemesNumber;
import com.yuncai.modules.lottery.model.Sql.Users;
import com.yuncai.modules.lottery.service.sqlService.lottery.BusinessService;
import com.yuncai.modules.lottery.service.sqlService.lottery.SchemesNumberService;
import com.yuncai.modules.lottery.service.sqlService.lottery.SchemesService;
import com.yuncai.modules.lottery.service.sqlService.lottery.buyDetailesService;

public class BusinessServiceImpl implements BusinessService{

	private buyDetailesService sqlbuyDetailesService; //sql投注
	private SchemesService sqlSchemesService;
	private SchemesNumberService sqlSchemesNumberService;
	
	/**
	 * 参与合买 针对sql自动跟单参与合买处理接口
	 * @param use //用户
	 * @param planNO //订单
	 * @param share  //份数
	 * @param isAutoFollowScheme //是否是自动跟单的购买记录
	 * @param fromClient 方案来自网页或手机，1表示来自网页  2表示来自手机应用
	 * @return
	 * lm 2013-05-30
	 */
	@Override
	public String buySchemesJoin(Users use, String planNO, int share, boolean isAutoFollowScheme, int fromClient,double amount,PlanStatus planStatus)throws ServiceException, Exception {
		int sourceSoldPart=0;
		int sourceAccount=0;
		double Schedule =0; //进度
		if(share<=0){
			throw new Exception("本次操作被中止:您认购份数有误");
		}
		
		
		// 根据方案编号获取方案相关信息
		Schemes plan=this.sqlSchemesService.findSchemeByPlanNOForUpdate(Integer.parseInt(planNO));
		
		if (plan == null) {
			throw new Exception("本次操作被中止:没有这个方案编号");
		}
		sourceSoldPart=plan.getBuyedShare();
		//LogUtil.out("--------------------请求参与sql合买服务------------------------");
		
		Integer orderId=this.makeBuyDetailes(use, plan.getId(), share, amount, fromClient, isAutoFollowScheme);
		//LogUtil.out("--------------------sql:orderId:"+orderId+"------------------------");
		
		// 判断是否方案发起人购买
		sourceAccount = plan.getInitiateUserId();
		int newPart = share + sourceSoldPart;
		double c=(double)newPart/plan.getShare()*100;
		double d=(double)(Math.round(c*100))/100.0;
		Schedule=d; //ROUND(CAST(@BuyShareCount AS float) / Share * 100, 2) //进度
		
		//LogUtil.out("buyedShare:"+plan.getBuyedShare());
		
		plan.setBuyedShare(newPart);
		plan.setSchedule(Schedule);
		plan.setUpdateDatetime(new Date()); 
		
		
		//份数达到，合买成功。设置状态为成功，扣用户的款
		if(plan.getShare()==plan.getBuyedShare()){
			plan.setReSchedule(Double.valueOf("0"));
			plan.setAtTopStatus(Short.valueOf("0"));
		}else{
			plan.setReSchedule(Double.valueOf(Schedule));
		}
		
		if(planStatus!=null && planStatus.getValue()==PlanStatus.TICKET_OUT.getValue()){
			plan.setBuyed(true);
		}
		
		sqlSchemesService.saveOrUpdate(plan);
		
		//LogUtil.out("--------------------请求参与sql结束合买服务:"+plan.getId()+"------------------------");
		
		return null;
	}
	
	/**
	 * 追号
	 */
	public String buySchemesChase(Users use){
		
		return null;
	}
	//购买方案(合买，代购都OK)
	public Integer makeSchemes(Users user,String uploadeFileContent,String schemeNumber,String title,Integer playTypeId,Integer isuseId
			,Integer multiple,Double money,Integer share,Short secrecyLevel,Short quashStatus,Short AtTopStatus
			,Integer BuyedShare,Double Schedule,Double ReSchedule,String LotteryNumber,Integer fromClient,Integer ycSchemeNumber,
			GenType gentype,boolean IsPurchasing,Double SchemeBonusScale,Double assureMoney,String lotteryNumberNew,String channel,String planDec){
		Integer Id=0;
		Schemes buy=new Schemes();
		buy.setSiteId(1);
		buy.setDateTime(new Date());
		buy.setSchemeNumber(schemeNumber);
		buy.setTitle(title);
		buy.setInitiateUserId(user.getId());
		buy.setIsuseId(isuseId);
		buy.setPlayTypeId(playTypeId);
		buy.setMultiple(multiple);
		buy.setMoney(money);
		buy.setAssureMoney(assureMoney);
		buy.setShare(share);
		buy.setSecrecyLevel(secrecyLevel);
		buy.setQuashStatus(quashStatus);
		buy.setBuyed(false);
		buy.setPrintOutType(Short.valueOf("1"));
		buy.setIdentifiers(null);
		buy.setIsOpened(false);
		buy.setOpenOperatorId(0);
		buy.setWinMoney(0.0);
		buy.setWinMoneyNoWithTax(0.0);
		buy.setInitiateBonus(0.00);
		buy.setAtTopStatus(AtTopStatus);
		buy.setIsCanChat(false);
		buy.setPreWinMoney(0.00);
		buy.setPreWinMoneyNoWithTax(0.00);
		buy.setEditWinMoney(0.00);
		buy.setEditWinMoneyNoWithTax(0.00);
		buy.setBuyedShare(BuyedShare);
		buy.setSchedule(Schedule);
		buy.setReSchedule(ReSchedule);
		buy.setIsSchemeCalculatedBonus(false);
		buy.setDescription(planDec);
		buy.setLotteryNumber(LotteryNumber);
		if(uploadeFileContent!=null){
			buy.setUploadFileContent(uploadeFileContent);
		}
		buy.setOt(Short.valueOf("-1"));
		buy.setOutTo(Short.valueOf("-1"));
		buy.setCorrelationSchemeId(-1);
		buy.setIsPurchasing(IsPurchasing); //代购还是合买
		buy.setFromClient(fromClient);
		buy.setYcSchemeNumber(ycSchemeNumber);
		buy.setChangeType(gentype);
		buy.setSchemeBonusScale(SchemeBonusScale);
		buy.setLotteryNumberNew(lotteryNumberNew);
		buy.setChannel(channel);
		Id=this.sqlSchemesService.save(buy);
		
		return buy.getId();
	}
	
	//购买详情
	public Integer makeBuyDetailes(Users user,Integer planNo,int share,double amount,int fromClient,boolean isAutofollowScheme){
		Integer order=0;
		BuyDetails buy=new BuyDetails();
		buy.setSiteId(1);
		buy.setUserId(user.getId());
		buy.setDateTime(new Date());
		buy.setSchemeId(planNo);
		buy.setShare(share);
		buy.setQuashStatus(Short.valueOf("0"));
		buy.setIsWhenInitiate(false);
		buy.setDetailMoney(amount);
		buy.setFromClient(fromClient);
		buy.setIsAutoFollowScheme(isAutofollowScheme);
		buy.setWinMoneyNoWithTax(0.0);
		
		order=this.sqlbuyDetailesService.save(buy);
		
		return order;
	}
	
	/**
	 * 方案备份
	 * @param schemesId
	 * @param money
	 * @param multiple
	 * @param lotteryNumber
	 * @param lotteryNumberNew
	 * @return
	 */
	public Integer makeSchemesNumber(Integer schemesId,Double money,Integer multiple,String lotteryNumber,String lotteryNumberNew){
		Integer ids=0;
		SchemesNumber number=new SchemesNumber();
		number.setDateTime(new Date());
		number.setMoney(money);
		number.setMultiple(multiple);
		number.setLotteryNumber(lotteryNumber);
		number.setLotteryNumberNew(lotteryNumberNew);
		number.setSchemeId(schemesId);
		
		ids=this.sqlSchemesNumberService.save(number);
		
		return ids;
	}
	//合买订单返款处理sql
	public String buyFreezeToAble(String planNo,Short quashStatus) throws Exception,ServiceException{
		// 根据方案编号获取方案相关信息
		Schemes plan=this.sqlSchemesService.findSchemeByPlanNOForUpdate(Integer.parseInt(planNo));
		if(plan==null){
			//LogUtil.out("方案号"+planNo+"没有查找到");
			String message = "方案号"+planNo+"没有查找到";
			throw new ServiceException("没有该方案");
		}
		
		List orderList = sqlbuyDetailesService.findSuccessByPlanNo(plan.getId());
		//LogUtil.out("正在撤销没有满员的合买单==>" + plan.getId());
		for (int i = 0; i < orderList.size(); i++) {
			BuyDetails buyDetailsOrder = (BuyDetails) orderList.get(i);
			if (buyDetailsOrder.getQuashStatus()==0) {
				buyDetailsOrder.setQuashStatus(quashStatus);
				buyDetailsOrder.setDateTime(new Date());
				this.sqlbuyDetailesService.saveOrUpdate(buyDetailsOrder);
			}
		}
		//撤销置顶标记
		plan.setAtTopStatus((Short.parseShort("0")));
		plan.setQuashStatus(quashStatus);
		sqlSchemesService.saveOrUpdate(plan);
		
		return null;
		
		
	}
	
	/**
	 * 更新方案编号
	 */
	public String currentSchemes(Integer planNo,String schemeNumber){
		Schemes current=this.sqlSchemesService.find(planNo);
		current.setSchemeNumber(schemeNumber);
		current.setUpdateDatetime(new Date());
		this.sqlSchemesService.saveOrUpdate(current);
		
		return current.getSchemeNumber();
		
	}
	
	public void setSqlbuyDetailesService(buyDetailesService sqlbuyDetailesService) {
		this.sqlbuyDetailesService = sqlbuyDetailesService;
	}
	public void setSqlSchemesService(SchemesService sqlSchemesService) {
		this.sqlSchemesService = sqlSchemesService;
	}
	

	public void setSqlSchemesNumberService(SchemesNumberService sqlSchemesNumberService) {
		this.sqlSchemesNumberService = sqlSchemesNumberService;
	}
	public static void main(String[] args) {
		System.out.println(Math.round(5 /10));
		//ROUND(/ Share * 100, 2)
		//float   a   =   123.2334f;
		//float   b   =   (float)(Math.round(a*100))/100;
		int a=51;
		int b=126;
		double c=(double)a/b*100;
		System.out.println("c:"+c);
		double d=(double)(Math.round(c*100)/100.0);
		System.out.println(d);
		
		//System.out.println(Math.round(5/10*100));
		System.out.println((float)120/150);
		
		
		System.out.println((float)50/120);
		int soldPart=5;
		int part=120;
		double re=(double)soldPart/part*100;
		System.out.println(re);
		double rd=(double)(Math.round(re*100)/100.0);
		System.out.println(rd);
		
		Short ff=Short.valueOf("5");
		Integer dd=Integer.valueOf(ff);
		System.out.println(dd);
		
	}
	



}
