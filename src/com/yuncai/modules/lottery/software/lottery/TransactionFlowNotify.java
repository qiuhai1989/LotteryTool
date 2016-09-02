package com.yuncai.modules.lottery.software.lottery;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.jdom.Element;

import com.yuncai.core.common.Constant;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.core.tools.StringTools;
import com.yuncai.core.util.Dates;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.bean.vo.HmShowBean;
import com.yuncai.modules.lottery.model.Oracle.Member;
import com.yuncai.modules.lottery.model.Oracle.MemberWallet;
import com.yuncai.modules.lottery.model.Oracle.MemberWalletLine;
import com.yuncai.modules.lottery.model.Oracle.toolType.BuyType;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanOrderStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanType;
import com.yuncai.modules.lottery.model.Oracle.toolType.WalletTransType;
import com.yuncai.modules.lottery.model.Oracle.toolType.WalletType;
import com.yuncai.modules.lottery.model.Oracle.toolType.WinStatus;
import com.yuncai.modules.lottery.service.oracleService.lottery.LotteryPlanOrderService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberWalletLineService;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;
import com.yuncai.modules.lottery.sporttery.model.FootBallBetContentTurbid;
import com.yuncai.modules.lottery.sporttery.model.SportteryBetContentModelTurbid;
import com.yuncai.modules.lottery.sporttery.model.baskball.BasketBallBetContent;
import com.yuncai.modules.lottery.sporttery.support.CommonUtils;
import com.yuncai.modules.lottery.sporttery.support.SportteryPassType;
import com.yuncai.modules.lottery.sporttery.support.basketball.BasketBallMatchItem;
import com.yuncai.modules.lottery.sporttery.support.football.FootBallMatchTurbidItem;

public class TransactionFlowNotify extends BaseAction implements SoftwareProcess {
	private LotteryPlanOrderService lotteryPlanOrderService;
	private SoftwareService softwareService;
	private MemberService memberService;
	private MemberWalletLineService memberWalletLineService;
	@Override
	public String execute(Element bodyEle, String agentId) {
		// TODO Auto-generated method stub
		HttpServletRequest request = ServletActionContext.getRequest();
		PlanType planType = null;
		Date startDate = null;
		Date endDate = null;
		String account = null;
		String userName = null;
		String userPwd = null;
		Integer lotteryType = null;
		List<HmShowBean> showBeans = null;
		String version = null;
		// ----------------新建xml包体--------------------
		@SuppressWarnings("unused")
		Element myBody = new Element("body");
		Element responseCodeEle = new Element("responseCode");
		Element responseMessage = new Element("responseMessage");
		// -------------------------------------------------
		Element transaction = bodyEle.getChild("transaction");// transaction
		String message = "组装节点不存在";
		try {

			version = bodyEle.getChildText("version");
			
			
			// 解析字符串如：101,102,103 并存入到list中
			List<Integer> listPlanNo;
			int planNo = 0;
			int order = 1;
			int offset = 1;
			int pageSize = 1000;
			listPlanNo = new ArrayList<Integer>();
		
			lotteryType = StringTools.isNullOrBlank(transaction.getChildText("lotteryId"))?-1: Integer.parseInt(transaction.getChildText("lotteryId"));
			
			if (transaction.getChildText("planType") != null && Integer.parseInt(transaction.getChildText("planType").trim()) > 0) {
				planType = PlanType.getItem(Integer.parseInt(transaction.getChildText("planType").trim()));
			} else {
				planType = PlanType.getItem(-1);
			}

			if (transaction.getChildText("startDate").trim() != null && !"".equals(transaction.getChildText("startDate").trim())) {
//				startDate = Dates.format(transaction.getChildText("startDate").trim());
				startDate = Dates.format(transaction.getChildText("startDate").trim(), "yyyy-MM-dd");
			} else {
				// 默认为当前月第一天
				Calendar c = Calendar.getInstance();
				c.set(Calendar.DAY_OF_MONTH, 1);
				startDate = c.getTime();
			}

			if (transaction.getChildText("endDate").trim() != null && !"".equals(transaction.getChildText("endDate").trim())) {
				endDate = Dates.format(transaction.getChildText("endDate").trim());
//				endDate = Dates.format(transaction.getChildText("endDate").trim(), "yyyy-MM-dd");
				
			} else {
				// 默认为当前时间
				endDate = Calendar.getInstance().getTime();
			}
			account = transaction.getChildText("account").trim();

			if (transaction.getChildText("listPlanNo") != null && !"".equals(transaction.getChildText("listPlanNo"))) {
				String testStr = transaction.getChildText("listPlanNo").trim();
				try {
					String[] strs = testStr.trim().split(",");
					for (String str : strs) {
						listPlanNo.add(Integer.parseInt(str.trim()));
					}
				} catch (Exception e) {
					return PackageXml("002", "数据格式有误", "9005", agentId);
				}
			}

			if (transaction.getChildText("planNo") != null) {

				planNo = Integer.parseInt(transaction.getChildText("planNo"));
			}

			order = 0;
			if (transaction.getChildText("order") != null) {

				order = Integer.parseInt(transaction.getChildText("order"));
			}

			offset = 1;
			if (transaction.getChildText("offset") != null) {

				offset = Integer.parseInt(transaction.getChildText("offset"));
			}

			pageSize = 10;
			if (transaction.getChildText("pageSize") != null) {

				pageSize = Integer.parseInt(transaction.getChildText("pageSize"));
			}
		
			
			userName = transaction.getChildText("userName");
			
			message=null;
			
			
			
			// 验证登陆
			Member memberSession = (Member) request.getSession().getAttribute(Constant.MEMBER_LOGIN_SESSION);
			if (memberSession == null) {
				return PackageXml("2000", "请登录!", "9300", agentId);
			}
			if (!userName.equals(memberSession.getAccount())) {
				return PackageXml("9001", "用户名与Cokie用户不匹配", "9300", agentId);
			}

			// 判断用户是否存在
			if (userName != null && !"".equals(userName)) {
				List<Member> list = memberService.findByProperty("account", userName);
				if (list == null || list.isEmpty()) {
					super.errorMessage = "此用户不存在!";
					return PackageXml("002", super.errorMessage, "8005", agentId);
				}

			} else {
				super.errorMessage = "用户名不能为空!";
				return PackageXml("002", super.errorMessage, "8005", agentId);
			}
			//用于过滤不想让客户端显示的彩种
			List<LotteryType>refuseList=null;

			if(Integer.parseInt(version)<=6){
				//小于等于版本6 部分未添加彩种不可见
				refuseList=new ArrayList<LotteryType>();
				refuseList=new ArrayList<LotteryType>();
				refuseList.addAll(LotteryType.JCLQList);
				refuseList.addAll(LotteryType.GPList);
				refuseList.add(LotteryType.JC_ZQ_HT);
				refuseList.add(LotteryType.JC_ZQ_LTS);
				//冠军玩法
				refuseList.addAll(LotteryType.JCSJBList);	
			}else if(Integer.parseInt(version)<=8){
				//小于等于版本8 部分未添加彩种不可见 
				refuseList=new ArrayList<LotteryType>();
				refuseList.addAll(LotteryType.GPList);
				refuseList.addAll(LotteryType.JCSJBList);
				refuseList.add(LotteryType.JC_ZQ_LTS);
			}else if(Integer.parseInt(version)==9){
				//小于等于版本9 部分未添加彩种不可见 冠军玩法
				refuseList=new ArrayList<LotteryType>();
				refuseList.addAll(LotteryType.JCSJBList);
				refuseList.add(LotteryType.JC_ZQ_LTS);
			}else if(Integer.parseInt(version)==10){
				refuseList=new ArrayList<LotteryType>();
				refuseList.add(LotteryType.JC_ZQ_LTS);
			}else if(Integer.parseInt(version)==11){
				refuseList=new ArrayList<LotteryType>();
				refuseList.add(LotteryType.JC_ZQ_LTS);
			}

//			showBeans = lotteryPlanOrderService.findHmShowBeans(account, startDate, endDate, planType, listPlanNo, planNo, order, offset, pageSize,userName,LotteryType.getItem(lotteryType));
			showBeans=lotteryPlanOrderService.findTransationFlow(account, startDate, endDate, planType, listPlanNo, planNo, order, offset, pageSize, userName, LotteryType.getItem(lotteryType), refuseList);
			if (showBeans == null || showBeans.size() == 0) {
				return PackageXml("998", "没有相应交易记录", "9112", agentId);
			}
			//结果集倒序
			if(order==0&&planNo!=0){
				Collections.reverse(showBeans);
			}
			
			// --------------------------组装接收的请求参数完毕-----------------------------

			
			Element recordDetails = new Element("recordDetails");
			responseCodeEle.setText("0");
			responseMessage.setText("查询成功");
			for (HmShowBean record : showBeans) {
				
				//以下情况保底订单不可见 
				//判断是否是保底订单
				if(record.getBuyType().equals(BuyType.BAODI)){
					//判断 方案招募中
					if(record.getPlanStatus().equals(PlanStatus.RECRUITING)){
						continue;
					}else if(record.getPlanStatus().getValue()>=PlanStatus.PAY_FINISH.getValue()) {
					//如果已退款
						if(record.getPlanOrderStatus().getValue()==3){
						continue;
					}
					
					}
				}
				
				
				Element recordDetail = new Element("recordDetail");
				Element lotteryId = new Element("lotteryId");
				
//				if(LotteryType.JCZQList.contains(record.getLotteryType())){
//					record.setLotteryType(LotteryType.JCZQ);
//				}
				
				
				
				lotteryId.setText(Integer.toString(record.getLotteryType().getValue())  );
				Element planNoEle = new Element("planNo");
				Element belongAccount = new Element("belongAccount");
				Element term = new Element("term");
				Element totalPart = new Element("totalPart");
				Element amount = new Element("amount");
				Element winStauts = new Element("winStatus");
				Element posttaxPrize = new Element("posttaxPrize");
				Element planOrderStatus = new Element("planOrderStatus");
				Element createTime = new Element("createTime");
				Element planTypeEle = new Element("planType");
				Element planStatus = new Element("planStatus");
				Element passTypes = new Element("passTypes");
				
				planNoEle.setText(Integer.toString(record.getOrderNo()));
				
				belongAccount.setText(record.getBelongAccount());
				totalPart.setText(Integer.toString(record.getAmount()));
				
				//部分出票的情况下 订单状态为退款所以 中奖也需改为退款状态
				
				if(PlanOrderStatus.RETUREN_MONEY.getValue()==record.getPlanOrderStatus().getValue()){
					
					winStauts.setText(Integer.toString(WinStatus.DRAWBACK.getValue()) );
				}else{
					winStauts.setText(Integer.toString(record.getWinStatus().getValue()) );
				}
				
				posttaxPrize.setText(Double.toString(record.getPosttaxPrize()));
				planOrderStatus.setText(Integer.toString(record.getPlanOrderStatus().getValue()));
				createTime.setText(Dates.format(record.getCreateDateTime()));
				planTypeEle.setText(Integer.toString(record.getPlantype().getValue()));
				term.setText(record.getTerm());
				amount.setText(Integer.toString(record.getAmount()));
//				LogUtil.out(record.getOrderNo() +"----"+record.getPlanStatus().getValue());
				planStatus.setText(Integer.toString(record.getPlanStatus().getValue()));
				
				passTypes.setText("");
				if(LotteryType.JCZQList.contains(record.getLotteryType())||LotteryType.JCLQList.contains(record.getLotteryType())||LotteryType.JCSJBList.contains(record.getLotteryType())){
					// 投注串
					String passTypess = getMatchPassTypes(record.getLotteryType(), record.getContent());
//					 System.out.println(passTypess);
					passTypes.setText(passTypess);
				}else{
					passTypes.setText("");
				}				
				recordDetail.addContent(passTypes);
				recordDetail.addContent(planNoEle);
				recordDetail.addContent(lotteryId);
				recordDetail.addContent(belongAccount);
				recordDetail.addContent(totalPart);
				recordDetail.addContent(amount);
				recordDetail.addContent(winStauts);
				recordDetail.addContent(posttaxPrize);
				recordDetail.addContent(term);
				recordDetail.addContent(planOrderStatus);
				recordDetail.addContent(createTime);
				recordDetail.addContent(planTypeEle);
				recordDetail.addContent(planStatus);
				recordDetails.addContent(recordDetail);
			}
			myBody.addContent(responseCodeEle);
			myBody.addContent(responseMessage);
			myBody.addContent(recordDetails);
			return softwareService.toPackage(myBody, "9020", agentId);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			try {
				String code = "";
				if (message != null) {
					code = message;
				} else {
					code = "查询处理中或许存在异常";
				}
				return PackageXml("3002", code, "9005", agentId);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return null;
	}

	private String getMatchPassTypes(LotteryType lotteryType, String content) {
		StringBuffer sbf = new StringBuffer("");
		if (content != null && content.length() > 0) {
			SportteryBetContentModelTurbid comtent = null;
			if (LotteryType.JCLQList.contains(lotteryType)) {
				comtent = CommonUtils.Object4JsonTurbid(content, BasketBallBetContent.class, BasketBallMatchItem.class);
			} else {
				comtent = CommonUtils.Object4JsonTurbid(content, FootBallBetContentTurbid.class, FootBallMatchTurbidItem.class);
			}
			
			List<SportteryPassType> passTypes = comtent.getPassTypes();
			for(int i=0;i<passTypes.size();i++){
				SportteryPassType passType  =  passTypes.get(i);
				if(passType!=null){
					sbf.append(passType.getType().getName()+",");
				}
			}
	
		}
		
		
		return  (sbf.length()>0)?sbf.toString().substring(0, sbf.toString().length()-1) :sbf.toString();
	}

	public void setSoftwareService(SoftwareService softwareService) {
		this.softwareService = softwareService;
	}

	// 组装错误信息
	public String PackageXml(String CodeEle, String message, String type, String agentId) throws Exception {
		// ----------------新建包体--------------------
		String returnContent = "";
		Element myBody = new Element("body");
		Element responseCodeEle = new Element("responseCode");
		Element responseMessage = new Element("responseMessage");
		responseCodeEle.setText(CodeEle);
		responseMessage.setText(message);
		myBody.addContent(responseCodeEle);
		myBody.addContent(responseMessage);
		returnContent = this.softwareService.toPackage(myBody, type, agentId);
		return returnContent;

	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setLotteryPlanOrderService(LotteryPlanOrderService lotteryPlanOrderService) {
		this.lotteryPlanOrderService = lotteryPlanOrderService;
	}

	public void setMemberWalletLineService(MemberWalletLineService memberWalletLineService) {
		this.memberWalletLineService = memberWalletLineService;
	}


	
}
