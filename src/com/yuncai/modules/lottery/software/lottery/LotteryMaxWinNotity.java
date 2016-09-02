package com.yuncai.modules.lottery.software.lottery;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.jdom.Element;

import com.yuncai.core.tools.DateTools;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.core.tools.StringTools;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.model.Oracle.LotteryPlan;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Oracle.toolType.WinStatus;
import com.yuncai.modules.lottery.service.oracleService.lottery.LotteryPlanService;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;

public class LotteryMaxWinNotity extends BaseAction implements SoftwareProcess{

	private SoftwareService softwareService; 
//	private LotteryPlanDAO lotteryPlanDAO;
	private LotteryPlanService lotteryPlanService;
	
	public void setLotteryPlanService(LotteryPlanService lotteryPlanService) {
		this.lotteryPlanService = lotteryPlanService;
	}
	public void setSoftwareService(SoftwareService softwareService) {
		this.softwareService = softwareService;
	}
//	public void setLotteryPlanDAO(LotteryPlanDAO lotteryPlanDAO) {
//		this.lotteryPlanDAO = lotteryPlanDAO;
//	}
	@Override
	public String execute(Element bodyEle, String agentId) {
		// TODO Auto-generated method stub
		HttpServletRequest request=ServletActionContext.getRequest();
		//----------------新建xml包体--------------------
	    Element myBody=new Element("body");
	    Element responseCodeEle=new Element("responseCode");
	    Element responseMessage=new Element("responseMessage");
	    Element winQueryShow = new Element("winQueryShow");
	    
	    //-------------------------------------------------
		//获取请求的body
		Element winQuery=bodyEle.getChild("winQuery");
		LotteryType lotteryType = null;
		Integer pretaxPrize = null;
		Date beginTime = null;
		Date endTime = null;
		WinStatus winStatus = null;
		String account = null;
		try {
			lotteryType = winQuery.getChildText("pretaxPrize")!=null&& Integer.parseInt(winQuery.getChildText("pretaxPrize"))!=0?lotteryType = LotteryType.getItem(Integer.parseInt(winQuery.getChildText("pretaxPrize"))):null;
			pretaxPrize = winQuery.getChildText("pretaxPrize")!=null?Integer.parseInt(winQuery.getChildText("pretaxPrize")):null;
			Date startDate = null;
			Calendar c = Calendar.getInstance();
			c.set(Calendar.DAY_OF_MONTH, 1);
			startDate = c.getTime();
			beginTime = StringTools.isNullOrBlank(winQuery.getChildText("beginTime"))?startDate :DateTools.StringToDate(winQuery.getChildText("beginTime"));
			endTime = StringTools.isNullOrBlank(winQuery.getChildText("endTime"))?new Date():DateTools.StringToDate(winQuery.getChildText("endTime"));
			winStatus = StringTools.isNullOrBlank(winQuery.getChildText("winStatus"))||Integer.parseInt(winQuery.getChildText("winStatus"))==0?null:WinStatus.getItem(Integer.parseInt(winQuery.getChildText("winStatus")));
			account = StringTools.isNullOrBlank(winQuery.getChildText("account"))?null:winQuery.getChildText("account");
			
			LotteryPlan lotteryPlan = lotteryPlanService.findMaxWinPrizePlan(lotteryType, pretaxPrize, beginTime, endTime, winStatus, account);
			
			{
				responseCodeEle.setText("0");
				responseMessage.setText("查询成功");
				LogUtil.out(lotteryPlan.winQueryInfo());
				winQueryShow.setText(lotteryPlan.winQueryInfo());
				myBody.addContent(responseCodeEle);
				myBody.addContent(responseMessage);
				myBody.addContent(winQueryShow);
				return softwareService.toPackage(myBody, "9036", agentId);
			}

		} catch (Exception e) {
			// TODO: handle exception

	    	e.printStackTrace();
			try {
				return PackageXml("3002", "组装节点不存在", "9001", agentId);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
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
}
