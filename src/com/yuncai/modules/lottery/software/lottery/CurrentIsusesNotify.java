package com.yuncai.modules.lottery.software.lottery;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.jdom.Element;

import com.yuncai.core.tools.DateTools;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.core.tools.StringTools;
import com.yuncai.core.util.Dates;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Sql.Isuses;
import com.yuncai.modules.lottery.service.oracleService.lottery.LotteryPlanService;
import com.yuncai.modules.lottery.service.sqlService.lottery.IsusesService;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;

public class CurrentIsusesNotify extends BaseAction implements SoftwareProcess{
	private SoftwareService softwareService; 
	private IsusesService sqlIsusesService;
	private LotteryPlanService lotteryPlanService;
	public void setSqlIsusesService(IsusesService sqlIsusesService) {
		this.sqlIsusesService = sqlIsusesService;
	}

	public void setSoftwareService(SoftwareService softwareService) {
		this.softwareService = softwareService;
	}
	
	

	public void setLotteryPlanService(LotteryPlanService lotteryPlanService) {
		this.lotteryPlanService = lotteryPlanService;
	}

	@Override
	@SuppressWarnings("unchecked")
	public String execute(Element bodyEle, String agentId) {
		// TODO Auto-generated method stub
		HttpServletRequest request=ServletActionContext.getRequest();
		
		//获取请求的body
		Element winQuery=bodyEle.getChild("winQuery");
		
		String message="组装节点错误!";
		
		Integer lotteryId = null;
		//ID集合
		 String lotteryIds=null;
		 String version = null;
		 version = StringTools.isNullOrBlank( bodyEle.getChildText("version"))?null: bodyEle.getChildText("version");

		//-------------------新建xml包体--------------------------
		Element myBody=new Element("body");
		Element responseCodeEle =new Element("responseCode");  //返回值
		Element responseMessage=new Element("responseMessage");
		Element lotteryTerms=new Element("lotteryTerms");
		Element serverTime=new Element("serverTime");
		serverTime.setText(DateTools.dateToString(new Date()));

		
		//-------------------------------------------------
		
		try {
			lotteryIds = StringTools.isNullOrBlank(winQuery.getChildText("lotteryId"))?null:winQuery.getChildText("lotteryId");
			
			List<Integer>ids = null;
				if(lotteryIds!=null){
					ids = new ArrayList<Integer>();					
					for(String s:lotteryIds.split(",")){
						ids.add(Integer.parseInt(s));
					}
					for(Integer id:ids){
						//未开放玩法先屏蔽
						if(id==8101){
							continue;
						}
						if(id !=null &&LotteryType.getItem(id)==null){
							message = " 该彩种不存在";
							return PackageXml("3002", message, "9005", agentId);
						} 
					}
				}else{
					message = "请求的彩期的lotteryId字段不能为空";
					return PackageXml("3002", message, "9005", agentId);
				}
				

				

			
			List<Isuses> list = sqlIsusesService.findCurrentTermList(ids);
			if(list==null||list.isEmpty()){
				message = " 找不到相应的记录";
				return PackageXml("3002", message, "9005", agentId);
			}
			
			
			Object[] obj = lotteryPlanService.winPercent(LotteryType.JCZQList, getBeginTime(), getNowTime());
//			LogUtil.out("出票金额："+obj[0].toString()+",中奖金额："+obj[1].toString()+",返奖率："+obj[2].toString());
			Element returnPrizeRate = new Element("returnPrizeRate");
//			LogUtil.out("最近30天返奖率"+doRatio(Double.parseDouble(obj[2].toString())) +"|");
//			returnPrizeRate.setText("近30天云彩会员投注返奖"+doRatio(Double.parseDouble(obj[2].toString())) +"|");
			returnPrizeRate.setText("竞彩达人“姚钱树”升为尊享红包VIP|");
			for(Isuses isu :list){
				try {
					Element isuses = new Element("isuses");
					Element termId = new Element("termId");
					Element lotteryType = new Element("lotteryType");
					Element term = new Element("term");
					Element endTime = new Element("endTime");
					Element preResult = new Element("preResult");
					Element preTerm = new Element("preTerm");
					Element openDateTime = new Element("openDateTime");
					String loid=isu.getLotteryId()==null?"":isu.getLotteryId().toString();
					lotteryType.setText(loid);
					term.setText(isu.getName());
					String endT=isu.getEndTime()==null?"":DateTools.dateToString(isu.getEndTime());
					endTime.setText(endT);
					String tId=isu.getId()==null?"":isu.getId().toString();
					termId.setText(tId);
//					System.out.println(isu.getName() + "---" + isu.getLotteryId() + "----" + isu.getOpenDateTime());
					String opT=isu.getOpenDateTime()==null?"":DateTools.dateToString(isu.getOpenDateTime());
					openDateTime.setText(opT);
					//上期开奖号码
					Isuses preIsuses = sqlIsusesService.findPreByLotteryTypeAndTerm(LotteryType.getItem(isu.getLotteryId()), isu.getName());
					if (preIsuses != null) {
						preResult.setText(preIsuses.getWinLotteryNumber());
						preTerm.setText(preIsuses.getName());
					}
					isuses.addContent(termId);
					isuses.addContent(lotteryType);
					isuses.addContent(term);
					isuses.addContent(endTime);
					isuses.addContent(openDateTime);
					isuses.addContent(preResult);
					isuses.addContent(preTerm);
					lotteryTerms.addContent(isuses);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					continue;
				}
				
			}
			//版本7之前竞彩无彩期数据
			if(Integer.parseInt(version)>6){
				
			
			List<Isuses> list2 = sqlIsusesService.findCurrentTermList2();
			for(Isuses isu :list2){
				try {
					Element isuses = new Element("isuses");
					Element termId = new Element("termId");
					Element lotteryType = new Element("lotteryType");
					Element term = new Element("term");
					Element endTime = new Element("endTime");
					Element preResult = new Element("preResult");
					Element preTerm = new Element("preTerm");
					String lid=isu.getLotteryId()==null?"":isu.getLotteryId().toString();
					lotteryType.setText(lid);
					term.setText(isu.getName());
					String endT=isu.getEndTime()==null?"":DateTools.dateToString(isu.getEndTime());
					endTime.setText(endT);
					String tid=isu.getId()==null?"":isu.getId().toString();
					termId.setText(tid);
					isuses.addContent(termId);
					isuses.addContent(lotteryType);
					isuses.addContent(term);
					isuses.addContent(endTime);
					isuses.addContent(preResult);
					isuses.addContent(preTerm);
					lotteryTerms.addContent(isuses);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					continue;
				}
				
			}
			}
			responseCodeEle.setText("0");
			responseMessage.setText("处理成功！");
			myBody.addContent(responseCodeEle);
			myBody.addContent(responseMessage);
			myBody.addContent(lotteryTerms);
			myBody.addContent(serverTime);
			myBody.addContent(returnPrizeRate);
			return softwareService.toPackage(myBody, "9045", agentId);
			
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

	
	/**
	 * 
	 * @param ratio
	 * @return
	 */
	public static String doRatio(double ratio){
		DecimalFormat df = new DecimalFormat("0");
		if(ratio==0.0 || ratio==-1.00) return "";
		return df.format(ratio*100)+"%";
	}
	
	/**获取最近的前30天
	 * @return
	 */
	public Date getBeginTime(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		Date beginTime  =  calendar.getTime();
		//为了获取0点0分。。
		String beginTimeStr = Dates.format(beginTime, "yyyy-MM-dd");
		beginTime = DateTools.stringToDate(beginTimeStr, "yyyy-MM-dd");
		calendar=null;
		beginTimeStr = null;
		return beginTime;
	}
	
	/**获取当前时间
	 * @return
	 */
	public Date getNowTime(){
		Date nowDate = new Date();
		String nowDateStr = Dates.format(nowDate, "yyyy-MM-dd");
		nowDate = DateTools.stringToDate(nowDateStr, "yyyy-MM-dd");
		nowDateStr = null;
		return  nowDate;
	}
	
}
