package com.yuncai.modules.lottery.software.lottery;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;



import org.jdom.Element;

import com.yuncai.core.longExce.ServiceException;
import com.yuncai.core.longExce.WebDataException;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.business.lottery.PlanBusiness;
import com.yuncai.modules.lottery.model.Oracle.LotteryPlan;

import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;



import com.yuncai.modules.lottery.service.oracleService.lottery.LotteryPlanService;

import com.yuncai.modules.lottery.software.service.ResultPrizeBean;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;


public class UpdateResultPrizeNotify extends BaseAction implements SoftwareProcess{
	private SoftwareService softwareService;
	private LotteryPlanService lotteryPlanService;
	private PlanBusiness planBusiness;
	
	

	@SuppressWarnings("unchecked")
	public String execute(Element bodyEle, String agentId){
		// 获取请过的body
		Integer lotteryType=0; //彩种 
		String term=null; //彩期
		Element wallet = bodyEle.getChild("resultPrize");
		String node = "组装节点不存在";
		List<ResultPrizeBean> listbean=new ArrayList<ResultPrizeBean>();
		Map resultMap=new HashMap();
		try {
			lotteryType=Integer.parseInt(wallet.getChildText("lotteryType"));
			term=wallet.getChildText("term");
			List<Element> listxml=wallet.getChildren("mwList");
			if(!listxml.isEmpty()){
				for(Element ele:listxml){
					ResultPrizeBean bean=new ResultPrizeBean();
					bean.setAccount(ele.getChildText("account"));
					bean.setAmount(Double.parseDouble(ele.getChildText("amount")));
					bean.setAmountPer(Double.parseDouble(ele.getChildText("amountPer")));
					String schemeNumber=ele.getChildText("schemeNumber");
					bean.setSchemeNumber(schemeNumber);
					addCount(resultMap,schemeNumber,bean);
				}
			}
			node=null;
			//找到是否派奖的方案
			Iterator it=resultMap.keySet().iterator();
			while(it.hasNext()){
				String plan_NO =(String)it.next();
				List prizeList=(List)resultMap.get(plan_NO);
				LotteryPlan plan=this.lotteryPlanService.findLotteryPlanByPlanNoForUpdate(Integer.parseInt(plan_NO));
			
				if(plan!=null){
					for(int i=0;i<prizeList.size();i++){
						ResultPrizeBean prize=(ResultPrizeBean)prizeList.get(i);
						String planNo=prize.getSchemeNumber();
						String amount=prize.getAmount().toString();
						String amountPer=prize.getAmountPer().toString();
						try {
							planBusiness.returnPrize(planNo, amount, amountPer);
						
						} catch (Exception e) {
						    e.printStackTrace();
						    return PackageXml("1", e.getMessage(), "9300", agentId);
						}
						
					}
				}
				
			}
			
			//处理执行追号
			{
				LotteryType lotTypeIn=(LotteryType)LotteryType.getItem(lotteryType);
				//去除竞彩
				if(!LotteryType.JCZQList.contains(lotTypeIn) || !LotteryType.CTZQList.contains(lotTypeIn) || !LotteryType.JCLQList.contains(lotTypeIn)){
					try {
						planBusiness.executeChase(lotTypeIn, term);
					} catch (ServiceException en) {
						en.printStackTrace();
						return PackageXml(en.getErrorCode(), en.getErrorMesg(), "9300", agentId);
					}catch (WebDataException e) {
						e.printStackTrace();
						return PackageXml(e.getErrorCode(), e.getMessage(), "9300", agentId);
					
					}catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
			
			{
				return PackageXml("0", "处理成功", "9005", agentId);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				String message = "";
				String errStatus = "200";
				if (node != null)
					message = node;
				else {
					message = e.getMessage();
					errStatus = "1";
				}

				return PackageXml(errStatus, message, "9300", agentId);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}
	
	private void addCount(Map map, String planNo, ResultPrizeBean bean) {
		if (map.containsKey(planNo)) {
			((List<ResultPrizeBean>) map.get(planNo)).add(bean);
		} else {
			List<ResultPrizeBean> list = new ArrayList<ResultPrizeBean>();
			list.add(bean);
			map.put(planNo, list);
		}
	}
	
	public String PackageXml(String CodeEle, String message, String type,
			String agentId) throws Exception {
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

	

	public void setSoftwareService(SoftwareService softwareService) {
		this.softwareService = softwareService;
	}

	public void setLotteryPlanService(LotteryPlanService lotteryPlanService) {
		this.lotteryPlanService = lotteryPlanService;
	}

	public void setPlanBusiness(PlanBusiness planBusiness) {
		this.planBusiness = planBusiness;
	}


	
}
