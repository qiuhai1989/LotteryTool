package com.yuncai.modules.lottery.software.lottery;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.jdom.Element;
import org.omg.PortableServer.POA;

import com.yuncai.core.common.CheckBingoXml;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.business.lottery.PlanBusiness;
import com.yuncai.modules.lottery.business.lottery.TermBusiness;
import com.yuncai.modules.lottery.service.sqlService.lottery.IsusesService;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;
import com.yuncai.modules.lottery.model.Oracle.LotteryPlan;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryTermStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanStatus;
import com.yuncai.modules.lottery.model.Sql.Isuses;

public class CheckBingoNotify extends BaseAction implements SoftwareProcess{

	private SoftwareService softwareService; 
	private IsusesService sqlIsusesService;
	private TermBusiness termBusiness;
	private PlanBusiness planBusiness;
	protected static DecimalFormat DF = new DecimalFormat("#.00");
	public static Map<LotteryType, Boolean> flags = new HashMap<LotteryType, Boolean>();// 开奖标志map
	public static Map<LotteryType, Object> locks = new HashMap<LotteryType, Object>();// 同步锁map
	static {// 初始化每个彩种的开奖标志
		for (LotteryType lt : LotteryType.list) {
			flags.put(lt, false);
			locks.put(lt, new Object());
		}

	}
	@SuppressWarnings("unchecked")
	@Override
	public String execute(Element bodyEle, String agentId) {
		HttpServletRequest request=ServletActionContext.getRequest();
		int lotteryType = 0;
		String term = "";
		List<LotteryPlan> planList=new ArrayList<LotteryPlan>();
		String winNumber="";
		String resultDetail=null;
		Boolean includeOpenedPlan=true;
		//-------------------新建xml包体--------------------------
		Element myBody=new Element("body");
		Element responseCodeEle =new Element("responseCode");  //返回值
		Element responseMessage=new Element("responseMessage");
		//-------------------------------------------------
		Element openWin=bodyEle.getChild("openWin");
		String message="组装节点错误!";
		try {
			winNumber=openWin.getChildText("winNumber"); //开奖结果
			lotteryType=Integer.parseInt(openWin.getChildText("lotteryType"));  //彩种
			term=openWin.getChildText("isuseId"); //彩期ID
			Element winLists=openWin.getChild("winLists");
			resultDetail=CheckBingoXml.getResultDetail(LotteryType.getItem(lotteryType), winLists);
			winNumber=CheckBingoXml.getResult(LotteryType.getItem(lotteryType), winNumber);
			
			message=null;
			//具体处理开奖
			Boolean flag = flags.get(LotteryType.getItem(lotteryType));
			if (flag) {
				super.errorMessage = LotteryType.getItem(lotteryType).getName() + " 开奖运行中，请稍侯再试！！！";
				return PackageXml("3001", "开奖运行中，请稍侯再试！！！！", "9800", agentId);
			}
			Object lock = locks.get(LotteryType.getItem(lotteryType));
			synchronized (lock) {
				flags.put(LotteryType.getItem(lotteryType), true);
				try {
					LotteryType tempLotteryType = null;
					tempLotteryType = LotteryType.getItem(lotteryType);
					Isuses lotteryTerm =this.sqlIsusesService.findByLotteryTypeAndTerm(tempLotteryType, term);
					
					LogUtil.out(tempLotteryType.getName());
					LogUtil.out(term);
					if (lotteryTerm == null) {
						return PackageXml("001", "未找到奖期:" + term, "9800", agentId);
					}
					LogUtil.out(lotteryTerm);
				
					
					HashMap<String, String> openResultMap = termBusiness.getOpenInfoMapForCheckBingo(lotteryTerm, LotteryType.getItem(lotteryType),winNumber,resultDetail);
					
					// 增加胜负彩赛果验证
					String result = winNumber;
					if (lotteryType == LotteryType.ZCSFC.getValue() || lotteryType == LotteryType.ZCRJC.getValue()) {
						String[] results = result.split(",");
						for (String resultCode : results) {
							if ("".equals(resultCode.trim())) {
								return PackageXml("0012", "老足球开奖结果有误!","9800", agentId);
							}
						}
					}
					
					// 开奖
					planList = new ArrayList<LotteryPlan>();
					includeOpenedPlan=PlanBusiness.UNINCLUDE_OPENEDPLAN;
					
					planList = planBusiness.checkBingo(openResultMap,includeOpenedPlan);

					if(!LotteryType.JCZQList.contains(tempLotteryType)){
						if (lotteryTerm.getState().getValue() <= LotteryTermStatus.CLOSE.getValue()) {
							lotteryTerm.setState(LotteryTermStatus.OPEN_PRIZE);
							lotteryTerm.setStateUpdateTime(new Date());
							this.sqlIsusesService.saveOrUpdate(lotteryTerm);
						}
					}
					
					
				} catch (Exception e) {
					e.printStackTrace();
					return PackageXml("005", e.getMessage(), "9800", agentId);
				} finally {
					flags.put(LotteryType.getItem(lotteryType), false);
				}
			}
			
			//处理push到ssq
			if(!planList.isEmpty()){
				responseCodeEle.setText("0");
				responseMessage.setText("处理成功！");
				myBody.addContent(responseCodeEle);
				myBody.addContent(responseMessage);
				for(LotteryPlan open:planList){
					Element openResult=new Element("openResult");
					Element planNo=new Element("planNo"); //yc方案编号
					Element status=new Element("status"); //中奖类型0（中奖方案）1（已流单、末出票作废）
					Element bingoContent=new Element("bingoContent"); //yc方案编号
					Element bingoPretaxTotal=new Element("bingoPretaxTotal"); //yc方案编号
					Element bingoPosttaxTotal=new Element("bingoPosttaxTotal"); //yc方案编号
					Element winDescription=new Element("winDescription");
					
					
					planNo.setText(open.getPlanNo().toString());
					
					if(open.getPlanStatus().getValue()==PlanStatus.ABORT.getValue() || open.getPlanStatus().getValue()==PlanStatus.TICKET_GQ.getValue()){
						status.setText("1");
					}else{
						status.setText("0");
					}
					bingoContent.setText(open.getPrizeContent());
					bingoPretaxTotal.setText(DF.format(open.getPretaxPrize()).toString());
					bingoPosttaxTotal.setText(DF.format(open.getPosttaxPrize()).toString());
					winDescription.setText(open.getPrizeContent());
					
					openResult.addContent(planNo);
					openResult.addContent(status);
					openResult.addContent(bingoContent);
					openResult.addContent(bingoPretaxTotal);
					openResult.addContent(bingoPosttaxTotal);
					openResult.addContent(winDescription);
					myBody.addContent(openResult);
				}
				return softwareService.toPackage(myBody, "9800", agentId);
			}else{
				return PackageXml("3001", "中奖方案为空！", "9800", agentId);
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
	public static void main(String[] args) {
		String str="01 02 03 04 05 06 07";
		System.out.println(str.length());
	}

	public void setSoftwareService(SoftwareService softwareService) {
		this.softwareService = softwareService;
	}

	public void setSqlIsusesService(IsusesService sqlIsusesService) {
		this.sqlIsusesService = sqlIsusesService;
	}

	public void setTermBusiness(TermBusiness termBusiness) {
		this.termBusiness = termBusiness;
	}

	public void setPlanBusiness(PlanBusiness planBusiness) {
		this.planBusiness = planBusiness;
	}

	public static Map<LotteryType, Boolean> getFlags() {
		return flags;
	}

	public static Map<LotteryType, Object> getLocks() {
		return locks;
	}
	
	
	

}
