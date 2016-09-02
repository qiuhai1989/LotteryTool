package com.yuncai.modules.lottery.software.lottery;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.jdom.Element;

import com.yuncai.core.common.Constant;
import com.yuncai.core.hibernate.CommonStatus;
import com.yuncai.core.longExce.ServiceException;
import com.yuncai.modules.lottery.action.lottery.AbstractDsUpLoadAction;
import com.yuncai.modules.lottery.factorys.verify.ContentCheck;
import com.yuncai.modules.lottery.factorys.verify.ContentFactory;
import com.yuncai.modules.lottery.model.Oracle.LotteryPlan;
import com.yuncai.modules.lottery.model.Oracle.Member;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.PublicStatus;
import com.yuncai.modules.lottery.service.oracleService.lottery.LotteryPlanService;
import com.yuncai.modules.lottery.service.oracleService.lottery.TradeService;
import com.yuncai.modules.lottery.service.sqlService.lottery.LotteryTypePropsService;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;
import java.util.*;

/*
 * 单式上传
 */
public class YtbdNotify extends AbstractDsUpLoadAction implements SoftwareProcess{
	public static final double amountRange = 1.1; // 方案范围 + 10%以内
	private static final int BUFFER_SIZE = 5024 * 1024;
	private SoftwareService softwareService; 
	private LotteryPlanService lotteryPlanService;
	private TradeService tradeService;
	private ContentFactory contentFactory;

	public String execute(Element bodyEle, String agentId) {
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		Integer planNO=0; //单据号
		String content=null; //内容
		String mult="1";  //倍数
		int count=0; //注数
		String userName=null; 
		String uploadFilePath=null; //上传文件路径
		Integer playType=0; //投注玩法
		Date dclasttime = null;
		Integer publicStatus=0;
		//----------------新建xml包体--------------------
	    @SuppressWarnings("unused")
		Element myBody=new Element("body");
	    Element responseCodeEle=new Element("responseCode");
	    Element responseMessage=new Element("responseMessage");
	    
	    
		//获取请过的body
		Element ytInfo=bodyEle.getChild("ytInfo");
		String node="组装节点不存在";
		try {
			planNO=Integer.parseInt(ytInfo.getChildText("planNo"));
			content=ytInfo.getChildText("content");
			mult=ytInfo.getChildText("multiple");
			userName=ytInfo.getChildText("userName");
			count=Integer.parseInt(ytInfo.getChildText("count"));
			playType=Integer.parseInt(ytInfo.getChildText("playType"));
			node=null;
			
			Member memberSession=(Member)request.getSession().getAttribute(Constant.MEMBER_LOGIN_SESSION);
			if(memberSession==null){
			  return PackageXml("2000", "请登录!", "9300", agentId);
			}
			
			if(!userName.equals(memberSession.getAccount())){
				return PackageXml("9001", "用户名与Cokie用户不匹配", "9300", agentId);
			}
			
			LotteryPlan lotteryPlan = lotteryPlanService.find(planNO);
			
			if (lotteryPlan == null) {// 方案未找到
				super.errorMessage = "方案编号错误!";
				return INDEX;
			}
			if (!memberSession.getAccount().equals(lotteryPlan.getAccount())) {// 用户不是方案发起人
				return PackageXml("9001", "您不是方案发起人!", "9300", agentId);
			}
			if (lotteryPlan.getIsUploadContent() == CommonStatus.YES.getValue()) {// 已经上传了
				return PackageXml("9001", "方案内容已经上传过了!", "9300", agentId);
			}
			if (lotteryPlan.getPlanStatus().getValue() != PlanStatus.RECRUITING.getValue()
					&& lotteryPlan.getPlanStatus().getValue() != PlanStatus.PAY_FINISH.getValue()) {// 方案状态不正常
				return PackageXml("9001", "方案状态错误!", "9300", agentId);
				
			}
			
			//方案内容是否公开
			if (publicStatus == null || publicStatus == 0) {
				publicStatus = 2;
			}
			
			PublicStatus publicStatusIn=PublicStatus.getItem(publicStatus);
			
			//生成文件
			if (content.length() > BUFFER_SIZE) {
				return PackageXml("102", "文件超过限制大小", "9300", agentId);
			}
			
			ContentCheck contentCheck = null;
			try {
				LotteryType lotteryTypeIn=(LotteryType)LotteryType.getItem(lotteryPlan.getLotteryType().getValue());
				String strContent="";
				if (lotteryTypeIn.getValue() >= 7201 && lotteryTypeIn.getValue() <= 7207) {
					strContent = content;
				}else{
					strContent = lotteryPlan.getPlayType().getValue()+":"+content + "%" + lotteryPlan.getMultiple();
				}
				//追加
				if(lotteryTypeIn.getValue()==39){
					if (lotteryPlan.getAddAttribute() != null && !"".equals(lotteryPlan.getAddAttribute())) {
						strContent = strContent + "-" + lotteryPlan.getAddAttribute();
					}
				}
				
				contentCheck = contentFactory.initFactory(lotteryTypeIn.getValue(), lotteryPlan.getAmount(), strContent);
				if (contentCheck.isPass() != true){
					if(contentCheck.getMessage()!=null && !"".equals(contentCheck.getMessage())){
						return PackageXml("3", "本次操作被中止:"+contentCheck.getMessage(), "9300", agentId);
					}else{
						return PackageXml("3", "本次操作被中止:金额错误", "9300", agentId);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				return PackageXml("102", "文件格式不正确!"+contentCheck.getMessage(), "9300", agentId);
			}
			
			
			String contextton=this.checkContext(content);
			
			// 对上传的文件进行本地持久化
			uploadFilePath = super.saveContextFile(contextton);
			
			
			
			//组装单场内容-------------------------
			int multiple=0;
			if(mult!=null){
				multiple=Integer.parseInt(mult);
			}else 
				multiple=lotteryPlan.getMultiple(); 
			
			int upAmount= count * 2*multiple;
			
			//业务逻辑处理
			try {
				if (upAmount >= lotteryPlan.getAmount() && upAmount <= lotteryPlan.getAmount() * amountRange) {
					tradeService.ytbd(planNO.toString(), content, uploadFilePath, new Double(upAmount),dclasttime,publicStatusIn,mult,playType);
				}
			}catch (ServiceException en) {
				  en.printStackTrace();
				  try {
					 return PackageXml(en.getErrorCode(), en.getErrorMesg(), "9300", agentId);
				  } catch (Exception e) {
					e.printStackTrace();
				  }
			}catch (Exception e) {
				e.printStackTrace();
				return PackageXml("1", e.getMessage(), "9300", agentId);
			}
			
			responseCodeEle.setText("0");  //上传成功
			responseMessage.setText("上传成功");
			myBody.addContent(responseCodeEle);
			myBody.addContent(responseMessage);
			return softwareService.toPackage(myBody, "9300", agentId);
			
			
		} catch (Exception e) {
			 e.printStackTrace();
				try {
					String message="";
					String status="200";
					if(node!=null)
						message=node;
					else{
						message=e.getMessage();
						status="1";
					}
					
					return PackageXml(status, message, "9300", agentId);
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
		}
		return null;
	}
	
	//组装错误信息
	public String PackageXml(String CodeEle,String message,String type,String agentId) throws Exception{
		//----------------新建包体--------------------
		 String returnContent="";
		 Element myBody=new Element("body");
		 Element responseCodeEle=new Element("responseCode");
		 Element responseMessage=new Element("responseMessage");
		 responseCodeEle.setText(CodeEle); 
		 responseMessage.setText(message);
		 myBody.addContent(responseCodeEle);
		 myBody.addContent(responseMessage);
		 returnContent= this.softwareService.toPackage(myBody, type, agentId);
		 return returnContent;
		 
	}

	public void setSoftwareService(SoftwareService softwareService) {
		this.softwareService = softwareService;
	}

	public void setLotteryPlanService(LotteryPlanService lotteryPlanService) {
		this.lotteryPlanService = lotteryPlanService;
	}

	public void setTradeService(TradeService tradeService) {
		this.tradeService = tradeService;
	}

	public void setContentFactory(ContentFactory contentFactory) {
		this.contentFactory = contentFactory;
	}

}
