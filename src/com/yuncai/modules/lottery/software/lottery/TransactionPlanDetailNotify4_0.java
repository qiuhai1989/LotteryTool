package com.yuncai.modules.lottery.software.lottery;


import java.util.List;


import javax.servlet.http.HttpServletRequest;


import org.apache.struts2.ServletActionContext;
import org.jdom.Element;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.yuncai.core.common.Constant;

import com.yuncai.core.tools.ArithUtil;

import com.yuncai.core.tools.CompressFile;
import com.yuncai.core.tools.DateTools;

import com.yuncai.core.tools.NumberTools;

import com.yuncai.modules.lottery.bean.vo.HmShowBean;
import com.yuncai.modules.lottery.model.Oracle.LotteryPlanOrder;
import com.yuncai.modules.lottery.model.Oracle.Member;
import com.yuncai.modules.lottery.model.Oracle.toolType.BuyType;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.WinStatus;
import com.yuncai.modules.lottery.model.Sql.Isuses;
import com.yuncai.modules.lottery.model.Sql.Match;
import com.yuncai.modules.lottery.model.Sql.Schemes;
import com.yuncai.modules.lottery.service.oracleService.lottery.LotteryPlanOrderService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberService;
import com.yuncai.modules.lottery.service.oracleService.ticket.TicketService;
import com.yuncai.modules.lottery.service.sqlService.fbmatch.MatchService;
import com.yuncai.modules.lottery.service.sqlService.lottery.IsusesService;
import com.yuncai.modules.lottery.service.sqlService.lottery.SchemesService;

import com.yuncai.modules.lottery.software.service.SoftwareService;


public class TransactionPlanDetailNotify4_0 extends TransactionPlanDetailNotify{
	
	@Override
	public String execute(Element bodyEle, String agentId) {
		// TODO Auto-generated method stub
		HttpServletRequest request = ServletActionContext.getRequest();
		Element toPlanDetail = bodyEle.getChild("toPlanDetail");
		
		String message="组装节点错误!";
		
		String planNo = "";
		String userName = null;
		//-------------------新建xml包体--------------------------
		Element myBody=new Element("body");
		Element responseCodeEle =new Element("responseCode");  //返回值
		Element responseMessage=new Element("responseMessage");
		Element planDetail = new Element("planDetail");
		
		Element planNoEle = new Element("planNo");
		Element lotteryId = new Element("lotteryId");
		Element term = new Element("term");
		Element totalPart = new Element("totalPart");
		Element planOrderStatus = new Element("planOrderStatus");
		Element winStatus = new Element("winStatus");
		Element posttaxPrize = new Element("posttaxPrize");
		Element createTime = new Element("createTime");
		Element planType = new Element("planType");
		Element orderNo = new Element("orderNo");
		Element content = new Element("content");
		Element winNum = new Element("winNum");
		Element multiple = new Element("multiple");
		Element selectType = new Element("selectType");
		Element belongAccount = new Element("belongAccount");
		Element commission = new Element("commission");
		Element totalAmount = new Element("totalAmount");
		Element title = new Element("title");
		Element descript = new Element("descript");
		
		Element planStatus = new Element("planStatus");
		
		Element nickName = new Element("nickName");
		
		Element participates = new Element("participates");
		
		Element tickets = new Element("tickets");
		
		Element peoples = new Element("peoples");

		Element pretaxPrize = new Element("pretaxPrize");
		
		
		
		
		//-------------------------------------------------
		
		try {
			
			planNo = toPlanDetail.getChildText("planNo");
			userName = toPlanDetail.getChildText("userName");
			
			
			message = null;
			
			
			
			// 验证登陆
			Member memberSession = (Member) request.getSession().getAttribute(Constant.MEMBER_LOGIN_SESSION);
			if (memberSession == null) {
				return PackageXml("2000", "请登录!", "9300", agentId);
			}
			if (!userName.equals(memberSession.getAccount())) {
				return PackageXml("9001", "用户名与Cokie用户不匹配", "9300", agentId);
			}
			
			
			HmShowBean bean = lotteryPlanOrderService.getHmShowBeanByPlanOrderNo(planNo);
			//获取某一方案参与人
			Integer nums = lotteryPlanOrderService.findInPeople(bean.getPlanNo()).size();
			
			//获取该方案的对应订单 及对应的用户昵称
//			List<LotteryPlanOrder>orders = lotteryPlanOrderService.findHMListByPlanNoAndBuyType(bean.getPlanNo(), BuyType.SELF_BUY);
			List<Object[]> objs = lotteryPlanOrderService.findHMListNickNameByPlanNoAndBuyType(bean.getPlanNo(),null);
			
			for(Object[] obj:objs){
				LotteryPlanOrder order = (LotteryPlanOrder)obj[0];
				
				//以下情况保底订单不可见 
				//判断是否是保底订单
				if(order.getBuyType().equals(BuyType.BAODI)){
					
					//判断 方案招募中
					if(bean.getPlanStatus().equals(PlanStatus.RECRUITING)){
						continue;
					}else if(bean.getPlanStatus().getValue()>=PlanStatus.PAY_FINISH.getValue()) {
					//如果已退款
						if(order.getStatus().getValue()==3){
						continue;
					}
					
					}
					
				}
				
				Element people = new Element("people");
				Element pNickName = new Element("pNickName");
				Element pPart = new Element("pPart");
				Element pAmount = new Element("pAmount");
				Element percent = new Element("percent");
				Element pTime = new Element("pTime");
				Element account = new Element("account");
				
				account.setText(order.getAccount()+"---"+order.getOrderNo());
				pNickName.setText((String)obj[1]);
				pPart.setText(Integer.toString(order.getPart()));
				pAmount.setText(Integer.toString(order.getAmount()));
				percent.setText(NumberTools.doubleToPercent(ArithUtil.div(order.getPart(), bean.getTotalPart(), 2), "0%"));
				pTime.setText(DateTools.dateToString(order.getCreateDateTime()));
				
				people.addContent(pNickName);
				people.addContent(pPart);
				people.addContent(pAmount);
				people.addContent(percent);
				people.addContent(pTime);
				people.addContent(account);
				peoples.addContent(people);
			}
			
			bean.setParticipates(nums);
			if(bean==null){
				message = "没有相应的记录";
			}
//			planNoEle.setText(Integer.toString(bean.getOrderNo()) );
			planNoEle.setText(Integer.toString(bean.getPlanNo()) );
			String contentHTML = getContentList(bean);
			
//			LogUtil.out(contentHTML);
			// 方案内容进行编码
			byte[] compressed = CompressFile.compress(contentHTML);
			String contentStr = Base64.encode(compressed);
			content.setText(contentStr);


			term.setText(bean.getTerm());
			totalPart.setText(bean.getAmount()+"");
			planOrderStatus.setText(Integer.toString(bean.getPlanOrderStatus().getValue()) );
			winStatus.setText(Integer.toString(bean.getWinStatus().getValue()));
			posttaxPrize.setText(bean.getPosttaxPrize()+"");
			createTime.setText(DateTools.dateToString(bean.getCreateDateTime()));
			planType.setText(bean.getPlantype().getValue()+"");
			orderNo.setText(bean.getOrderNo()+"");
			pretaxPrize.setText(Double.toString(bean.getPretaxPrize()));
			
			
			//如果是竞彩
			if(LotteryType.JCZQList.contains(bean.getLotteryType())){
				Schemes schemes = sqlSchemesService.findByProperty("ycSchemeNumber", bean.getPlanNo()).get(0);
				//投注的内容
				String lotteryNumber = schemes.getLotteryNumber();
				//获取投注的比赛id
				List<Integer> ids  = matches(lotteryNumber);
//				System.out.println(ids.toString());
				List<Match> matches = matchService.findMatchList(ids);
//				for(int i=0;i<matches.size();i++){
//					System.out.println(matches.get(i).getId());
//				}
//				LogUtil.out(matchResult(matches, bean.getLotteryType()));
				//判断是否已经开奖
				if(bean.getWinStatus().getValue()>WinStatus.NOT_RESULT.getValue()){
					String matchResult = matchResult(matches, bean.getLotteryType(),null);
//					LogUtil.out(matchResult);
					//如果存在null 即有比赛结果未更新不予以显示
					if(matchResult.indexOf("null")>0){
						
					}else {
						bean.setWinNum(matchResult);
						winNum.setText(bean.getWinNum());
					}

				}
				
				
				
			}else {
				//获取彩期////
//				Isuses isuses = (Isuses) sqlIsusesService.findByProperty("name", bean.getTerm()).get(0);
				Isuses isuses = (Isuses) sqlIsusesService.findByPropertys(new String[]{"name","lotteryId"}, new Object[]{bean.getTerm(),bean.getLotteryType().getValue()}).get(0);
//				LogUtil.out(isuses.getWinLotteryNumber()+"");
				//非竞彩
				winNum.setText(isuses.getWinLotteryNumber()+"");
			}

			

			
			
			lotteryId.setText(Integer.toString(bean.getLotteryType().getValue()));
			
			
			multiple.setText(bean.getMultiple()+"");
			selectType.setText(bean.getSelectType().getValue()+"");
			belongAccount.setText(bean.getBelongAccount());
			commission.setText(bean.getCommision()+"");
			totalAmount.setText(bean.getTotalAmount()+"");
			title.setText(bean.getTitle());
			descript.setText(bean.getDescript());
			planStatus.setText(bean.getPlanStatus().getValue()+"");
			participates.setText(bean.getParticipates()+"");
			
			
			Member member = memberService.findByProperty("account", bean.getBelongAccount()).get(0);
			if(member.getNickName()==null){
				nickName.setText(member.getAccount());
			}else {
				nickName.setText(member.getNickName());
			}
		
			
			
			planDetail.addContent(planNoEle);
			planDetail.addContent(lotteryId);
			planDetail.addContent(term);
			planDetail.addContent(totalPart);
			planDetail.addContent(planOrderStatus);
			planDetail.addContent(winStatus);
			planDetail.addContent(posttaxPrize);
			planDetail.addContent(createTime);
			planDetail.addContent(planType);
			planDetail.addContent(orderNo);
			planDetail.addContent(content);
			planDetail.addContent(winNum);
			planDetail.addContent(multiple);
			planDetail.addContent(selectType);
			planDetail.addContent(belongAccount);
			planDetail.addContent(commission);
			planDetail.addContent(totalAmount);
			planDetail.addContent(title);
			planDetail.addContent(descript);
			planDetail.addContent(planStatus);
			planDetail.addContent(nickName);
			planDetail.addContent(participates);
			planDetail.addContent(peoples);
			planDetail.addContent(pretaxPrize);
//			planDetail.addContent(tickets);
			
			responseCodeEle.setText("0");
			responseMessage.setText("处理成功！");
			myBody.addContent(responseCodeEle);
			myBody.addContent(responseMessage);
			myBody.addContent(planDetail);
			return softwareService.toPackage(myBody, "9041", agentId);
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				String code="";
				if(message!=null){
					code=message;
				}else{
					code="查询处理中或许存在异常";
				}
				return PackageXml("3002", code, "9005", agentId);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		
		return null;
	}
	
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public SoftwareService getSoftwareService() {
		return softwareService;
	}

	public void setSoftwareService(SoftwareService softwareService) {
		this.softwareService = softwareService;
	}

	public LotteryPlanOrderService getLotteryPlanOrderService() {
		return lotteryPlanOrderService;
	}

	public void setLotteryPlanOrderService(LotteryPlanOrderService lotteryPlanOrderService) {
		this.lotteryPlanOrderService = lotteryPlanOrderService;
	}
	
	
	
	public void setSqlIsusesService(IsusesService sqlIsusesService) {
		this.sqlIsusesService = sqlIsusesService;
	}


	public void setSqlSchemesService(SchemesService sqlSchemesService) {
		this.sqlSchemesService = sqlSchemesService;
	}
	
	public void setTicketService(TicketService ticketService) {
		this.ticketService = ticketService;
	}


	
	public void setMatchService(MatchService matchService) {
		this.matchService = matchService;
	}

}
