package com.yuncai.modules.lottery.software.lottery;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.jdom.Element;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.yuncai.core.hibernate.CommonStatus;
import com.yuncai.core.tools.ArithUtil;
import com.yuncai.core.tools.CompressFile;
import com.yuncai.core.tools.DateTools;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.core.tools.NumberTools;
import com.yuncai.core.util.Dates;
import com.yuncai.core.util.HttpUtil;
import com.yuncai.core.util.Strings;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.model.Oracle.LotteryPlan;
import com.yuncai.modules.lottery.model.Oracle.LotteryPlanOrder;
import com.yuncai.modules.lottery.model.Oracle.Member;
import com.yuncai.modules.lottery.model.Oracle.toolType.BuyType;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.SelectType;
import com.yuncai.modules.lottery.service.oracleService.lottery.LotteryPlanOrderService;
import com.yuncai.modules.lottery.service.oracleService.lottery.LotteryPlanService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberService;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;

public class PlanDetailNotify extends BaseAction implements SoftwareProcess {
	private LotteryPlanService lotteryPlanService;
	private LotteryPlanOrderService lotteryPlanOrderService;
	private SoftwareService softwareService;
	private MemberService memberService;
	@Override
	public String execute(Element bodyEle, String agentId) {
		// TODO Auto-generated method stub
		int planNo = 0;
		//-------------------新建xml包体--------------------------
		Element myBody=new Element("body");
		Element responseCodeEle =new Element("responseCode");  //返回值
		Element responseMessage=new Element("responseMessage");
		//-------------------------------------------------
		Element toPlanDetail = bodyEle.getChild("toPlanDetail");
		String message="组装节点不存在";
		try {
		
			planNo = Integer.parseInt(toPlanDetail.getChildText("planNo"));
			
			message=null;
			LotteryPlan lotteryPlan=new LotteryPlan();
			lotteryPlan = (LotteryPlan) lotteryPlanService.findByProperty("planNo", planNo).get(0);
			
			if (lotteryPlan != null) {
				// 新建xml包装体
				Element planDetail = new Element("planDetail");
				responseCodeEle.setText("0");
				responseMessage.setText("查询成功");
				Element rPlanNo = new Element("planNo");
				System.out.println(Integer.toString(planNo));
				rPlanNo.setText(Integer.toString(planNo));
				Element belongAccount = new Element("belongAccount");
				belongAccount.setText(lotteryPlan.getAccount());
				
				Member member = memberService.findByProperty("account",lotteryPlan.getAccount()).get(0);
				Element nickName = new Element("nickName");
				if(member.getNickName()==null){
					nickName.setText(member.getAccount());
				}else {
					nickName.setText(member.getNickName());
				}
				
				Element term = new Element("term");
				//如果是竞彩把彩期换成 发起时间
				term.setText(lotteryPlan.getTermToCreateDate());
				
				Element dealDateTime = new Element("dealDateTime");
				//排除掉竞彩的情况
				if(!LotteryType.JCZQList.contains(lotteryPlan.getLotteryType())&&!LotteryType.JCLQList.contains(lotteryPlan.getLotteryType())  ){
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(lotteryPlan.getDealDateTime());
					calendar.add(Calendar.MINUTE, -20);
					dealDateTime.setText(Dates.format(calendar.getTime()));
					
				}else{
					dealDateTime.setText(Dates.format(lotteryPlan.getDealDateTime()));
				}
				
				Element game = new Element("game");
				game.setText(lotteryPlan.getLotteryType().getName());
				Element playType = new Element("playType");
				playType.setText(lotteryPlan.getPlayType().getName());
				Element multiple = new Element("multiple");
				multiple.setText(Integer.toString(lotteryPlan.getMultiple()));
				Element planDesc = new Element("planDesc");
				planDesc.setText(lotteryPlan.getPlanDesc());
				Element amount = new Element("amount");
				amount.setText(Integer.toString(lotteryPlan.getAmount()));
				Element content = new Element("content");// 需进行base64编码
				// 方案内容解析并组装到html中去
//				LogUtil.out(lotteryPlan.getContent());
				String contentHTML = getContentList(lotteryPlan);
			
//				LogUtil.out(contentHTML);
				// 方案内容进行编码
				byte[] compressed = CompressFile.compress(contentHTML);
				String contentStr = Base64.encode(compressed);
				content.setText(contentStr);
				Element founderPart = new Element("founderPart");
				founderPart.setText(Integer.toString(lotteryPlan.getFounderPart()));
				Element reservePart = new Element("reservePart");
				reservePart.setText(Integer.toString(lotteryPlan.getReservePart()));
				Element schedule = new Element("schedule");
				// 组装进度数据
				schedule.setText(NumberTools.doubleToPercent(ArithUtil.div(lotteryPlan.getSoldPart(), lotteryPlan.getPart(), 2), "0%") + "+"
						+ NumberTools.doubleToPercent(ArithUtil.div(lotteryPlan.getReservePart(), lotteryPlan.getPart(), 2), "0%"));
				
				Element commision = new Element("commision");
				commision.setText(Integer.toString(lotteryPlan.getCommision()));
				Element planStatus = new Element("planStatus");
				planStatus.setText(lotteryPlan.getPlanStatus().getName());
				Element ohterAmount = new Element("otherAmount");// 剩余金额
				ohterAmount.setText(Integer.toString((lotteryPlan.getPart() - lotteryPlan.getSoldPart()) * lotteryPlan.getPerAmount()));
				Element InPeople = new Element("InPeople");// 参与人数
				InPeople.setText(Integer.toString(lotteryPlanOrderService.findInPeople(planNo).size()));
				Element part = new Element("soldPart");
				part.setText(Integer.toString(lotteryPlan.getSoldPart()));
				Element planTicketStatus = new Element("planTicketStatus");
				planTicketStatus.setText(lotteryPlan.getPlanTicketStatus().getName());
				Element publicStatus = new Element("publicStatus");
				publicStatus.setText(Integer.toString( lotteryPlan.getPublicStatus().getValue()));
				Element title = new Element("title");
				title.setText(lotteryPlan.getTitle());
				
				Element selectType = new Element("selectType");
				selectType.setText(Integer.toString(lotteryPlan.getSelectType().getValue()));
				
				//获取该方案的对应订单
				List<LotteryPlanOrder>orders = lotteryPlanOrderService.findHMListByPlanNoAndBuyType(lotteryPlan.getPlanNo(), null);
				
				//新增参与购买用户的信息列表
				Element participants = new Element("participants");

				
				for(LotteryPlanOrder order:orders){
//					LogUtil.out("order ="+order.getOrderNo()+"--"+order.getAmount());
					//以下情况保底订单不可见 
					//判断是否是保底订单
					if(order.getBuyType().equals(BuyType.BAODI)){
						//判断 方案招募中
						if(lotteryPlan.getPlanStatus().equals(PlanStatus.RECRUITING)){
							continue;
						}else if(lotteryPlan.getPlanStatus().getValue()>=PlanStatus.PAY_FINISH.getValue()) {
						//如果已退款
							if(order.getStatus().getValue()==3){
							continue;
						}
						
						}
						
					}
					
					Element participant = new Element("participant");
					Element userName = new Element("userName");
					userName.setText(order.getAccount());
					Element usernickName = new Element("nickName");
					Member member2 = memberService.findByProperty("account", order.getAccount()).get(0);
					if(member2.getNickName()==null){
						usernickName.setText(member2.getAccount());
					}else {
						usernickName.setText(Strings.encryptionStrPhone(member2.getNickName()));
					}
					Element inPart = new Element("inPart");
					inPart.setText(Integer.toString(order.getPart()));
					Element inAmount = new Element("inAmount");
					inAmount.setText(Integer.toString(order.getAmount()));
					Element inDate = new Element("inDate");
					inDate.setText(DateTools.dateToString(order.getCreateDateTime()));
					
					participant.addContent(userName);
					participant.addContent(inPart);
					participant.addContent(inAmount);
					participant.addContent(inDate);
					participant.addContent(usernickName);
					participants.addContent(participant);
					
				}
				
				
				planDetail.addContent(rPlanNo);
				planDetail.addContent(belongAccount);
				planDetail.addContent(nickName);
				planDetail.addContent(term);
				planDetail.addContent(dealDateTime);
				planDetail.addContent(game);
				planDetail.addContent(playType);
				planDetail.addContent(multiple);
				planDetail.addContent(planDesc);
				planDetail.addContent(amount);
				planDetail.addContent(content);
				planDetail.addContent(founderPart);
				planDetail.addContent(reservePart);
				planDetail.addContent(part);
				planDetail.addContent(schedule);
				planDetail.addContent(commision);
				planDetail.addContent(planStatus);
				planDetail.addContent(ohterAmount);
				planDetail.addContent(InPeople);
				planDetail.addContent(planTicketStatus);
				planDetail.addContent(publicStatus);
				planDetail.addContent(title);
				planDetail.addContent(selectType);
				planDetail.addContent(participants);
				
				myBody.addContent(responseCodeEle);
				myBody.addContent(responseMessage);
				myBody.addContent(planDetail);
				// ------------------组装完毕
				
				return softwareService.toPackage(myBody, "9040", agentId);
				
			} else {
				return PackageXml("002", "查询过程出错请稍后再试", "9115", agentId);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				String code="";
				if(message!=null){
					code=message;
				}else{
					code="查询处理中或许存在异常";
				}
				return PackageXml("002", code, "9005", agentId);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return null;

	}

	public void setLotteryPlanService(LotteryPlanService lotteryPlanService) {
		this.lotteryPlanService = lotteryPlanService;
	}

	public void setSoftwareService(SoftwareService softwareService) {
		this.softwareService = softwareService;
	}

	public void setLotteryPlanOrderService(LotteryPlanOrderService lotteryPlanOrderService) {
		this.lotteryPlanOrderService = lotteryPlanOrderService;
	}

	public String getContentList(LotteryPlan lotteryPlan) {
		HttpServletRequest request = ServletActionContext.getRequest();
		String host=request.getServerName();
		int p=request.getServerPort();
		if (lotteryPlan.getIsUploadContent() == CommonStatus.YES.getValue()) {
			if (lotteryPlan.getSelectType().getValue() == SelectType.UPLOAD.getValue()) {
				return "<a href='http://" + host +":"+ p + lotteryPlan.getContent() + "' target='_blank'>下载文件</a>";
			} else {
				return LotteryPlan.genContentList(lotteryPlan.getLotteryType(), lotteryPlan.getContent(), lotteryPlan.getPlayType().getValue() + "");
			}
		} else {
			if (lotteryPlan.getSelectType().getValue() == SelectType.UPLOAD.getValue()) {
				return "暂未上传";
			} else {
				return "暂未选号";
			}
		}

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
	
}
