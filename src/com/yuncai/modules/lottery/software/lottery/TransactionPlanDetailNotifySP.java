package com.yuncai.modules.lottery.software.lottery;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.jdom.Element;

import com.yuncai.core.common.Constant;
import com.yuncai.core.tools.DateTools;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.core.tools.NumberTools;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.business.lottery.TermBusiness;
import com.yuncai.modules.lottery.model.Oracle.LotteryPlan;
import com.yuncai.modules.lottery.model.Oracle.Member;
import com.yuncai.modules.lottery.model.Oracle.Ticket;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Sql.Match;
import com.yuncai.modules.lottery.service.oracleService.baskboll.BkMatchService;
import com.yuncai.modules.lottery.service.oracleService.fbmatch.FtMatchService;
import com.yuncai.modules.lottery.service.oracleService.lottery.LotteryPlanOrderService;
import com.yuncai.modules.lottery.service.oracleService.lottery.LotteryPlanService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberService;
import com.yuncai.modules.lottery.service.oracleService.ticket.TicketService;
import com.yuncai.modules.lottery.service.sqlService.fbmatch.MatchService;
import com.yuncai.modules.lottery.service.sqlService.lottery.IsusesService;
import com.yuncai.modules.lottery.service.sqlService.lottery.SchemesService;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;
import com.yuncai.modules.lottery.sporttery.OptionItem;
import com.yuncai.modules.lottery.sporttery.model.FootBallBetContentTurbid;
import com.yuncai.modules.lottery.sporttery.model.SportteryBetContentModelTurbid;
import com.yuncai.modules.lottery.sporttery.model.baskball.BasketBallBetContent;
import com.yuncai.modules.lottery.sporttery.support.CommonUtils;
import com.yuncai.modules.lottery.sporttery.support.MatchItem;
import com.yuncai.modules.lottery.sporttery.support.MatchItemTurbid;
import com.yuncai.modules.lottery.sporttery.support.SportteryOption;
import com.yuncai.modules.lottery.sporttery.support.basketball.BasketBallMatchItem;
import com.yuncai.modules.lottery.sporttery.support.football.FootBallMatchTurbidItem;

public class TransactionPlanDetailNotifySP extends BaseAction implements SoftwareProcess{
	private SoftwareService softwareService; 
	
	private LotteryPlanOrderService lotteryPlanOrderService;
	
	private IsusesService sqlIsusesService;
	private MemberService memberService;
	private SchemesService sqlSchemesService;
	private MatchService matchService;
	private TicketService ticketService;
	private LotteryPlanService lotteryPlanService;
	
	private TermBusiness termBusiness;
	
	@Override
	public String execute(Element bodyEle, String agentId) {
		// TODO Auto-generated method stub
		HttpServletRequest request = ServletActionContext.getRequest();
		Element toPlanDetail = bodyEle.getChild("toPlanDetail");
		
		String message="组装节点错误!";
		
		Integer planNo = null;
		String userName = null;
		
		//-------------------新建xml包体--------------------------
		Element myBody=new Element("body");
		Element responseCodeEle =new Element("responseCode");  //返回值
		Element responseMessage=new Element("responseMessage");
		
		Element tickets = new Element("tickets");
		
		//-------------------------------------------------
		
		try {
			planNo = Integer.parseInt(toPlanDetail.getChildText("planNo"))  ;
			userName = toPlanDetail.getChildText("userName");
//			planNo = 1814;
			message = null;
			// 验证登陆
			Member memberSession = (Member) request.getSession().getAttribute(Constant.MEMBER_LOGIN_SESSION);
			if (memberSession == null) {
				return PackageXml("2000", "请登录!", "9300", agentId);
			}
			if (!userName.equals(memberSession.getAccount())) {
				return PackageXml("9001", "用户名与Cokie用户不匹配", "9300", agentId);
			}
//			HmShowBean bean = lotteryPlanOrderService.getHmShowBeanByPlanOrderNo(planNo);
			
			LotteryPlan bean = lotteryPlanService.findByProperty("planNo", planNo).get(0);
			if(bean==null){
				message = "响应的方案不存在";
				return PackageXml("200", message, "9005", agentId);
			}
			
			
			
			// 设定一标识在客户端绝对是否显示投注信息 0不显示1显示
			int flag = 0;
			//如果是发起人本身可见
			if(memberSession.getAccount().equals(bean.getAccount())){
				flag=1;
			}else{
				// 以下代码 根据条件判断公开情况
				switch (bean.getPublicStatus().getValue()) {
				// 公开
				case 0:
					flag = 1;
					break;
				// 截止后公开
				case 1:
					if (bean.getDealDateTime().before(new Date())) {
						flag = 1;
					} else {
						flag = 0;
					}

					break;
				// 开奖后公开
				case 2:
					if (bean.getWinStatus().getValue() > 1) {
						flag = 1;
					} else {
						flag = 0;
					}
					break;
				// 不公开
				case 3:
					flag = 0;
					break;

				default:
					flag = 0;
					break;
				}
			}
			
			if(flag>0){
				//如果是竞彩
				if(LotteryType.JCZQList.contains(bean.getLotteryType())||LotteryType.JCLQList.contains(bean.getLotteryType())){
					
					//根据方案内容获取赛事日期
					String countext=bean.getContent(); 
					String intTime=getCountextDate(countext);
					HashMap<String, String> openResultMap =termBusiness.getOpenInfoTicket(bean.getLotteryType(), intTime);
					
					
					List<Ticket>ticketList = ticketService.findByProperty("planNo", bean.getPlanNo());
					for(int i=1;i<=ticketList.size();i++){
						
						Ticket t = ticketList.get(i-1);
						//出票成功后
						if(t.getStatus().getValue()!=4){
							continue;
						}
						
						Element ticket = new Element("ticket");
						Element no = new Element("no");
						Element contentEle = new Element("content");
						Element playType = new Element("playType");
						Element parts = new Element("parts");
						Element multipleEle = new Element("multiple");
						Element amount = new Element("amount");
						Element isBingoEle = new Element("isBingo");
						Element winNums = new Element("winNums");
						Element bingoAmount = new Element("bingoAmount");
						
						Element ticketPrize=new Element("ticketPrize");
						

						
						no.setText(Integer.toString(i));
						contentEle.setText(getSP(bean.getLotteryType(), t.getContent()));
						playType.setText(t.getPlayType().getName());
						parts.setText(Integer.toString((t.getAmount()/2)/t.getMultiple()));
						multipleEle.setText(Integer.toString(t.getMultiple()));
						amount.setText(Integer.toString(t.getAmount()));
						isBingoEle.setText(Integer.toString(t.getIsBingo()));
						String []strs = null;
						//如果中奖
						if(t.getIsBingo()==0){
							if(t.getBingoContent()!=null){
								strs = t.getBingoContent().split("#");
								winNums.setText(Integer.toString(strs.length));
								bingoAmount.setText(Double.toString(t.getBingoAmount()));
								String prizeCountext=this.ticketService.TicketCheckBingo(openResultMap, t,bean.getLotteryType());
								ticketPrize.setText(prizeCountext);
							}else {
								winNums.setText(Integer.toString(0));
								bingoAmount.setText(Double.toString(0));
								ticketPrize.setText("");
							}
						}else{
							winNums.setText(Integer.toString(0));
							bingoAmount.setText(Double.toString(0));
							ticketPrize.setText("");
						}

					
//						LogUtil.out("dfsdfdsf----");
						ticket.addContent(no);
						ticket.addContent(contentEle);
						ticket.addContent(playType);
						ticket.addContent(parts);
						ticket.addContent(multipleEle);
						ticket.addContent(amount);
						ticket.addContent(isBingoEle);
						ticket.addContent(winNums);
						ticket.addContent(bingoAmount);
						ticket.addContent(ticketPrize);
//						tickets.addContent(ticket);
						tickets.addContent(ticket);
					}
					
				}else if(LotteryType.JCSJBList.contains(bean.getLotteryType())){
					//世界杯玩法
					//获取投注内容
					String countext=bean.getContent(); 
					
					List<Ticket>ticketList = ticketService.findByProperty("planNo", bean.getPlanNo());
					
					for(int i=1;i<=ticketList.size();i++){
						
						Ticket t = ticketList.get(i-1);
						//出票成功后
						if(t.getStatus().getValue()!=4){
							continue;
						}
						Element ticket = new Element("ticket");
						Element no = new Element("no");
						Element contentEle = new Element("content");
						Element playType = new Element("playType");
						Element parts = new Element("parts");
						Element multipleEle = new Element("multiple");
						Element amount = new Element("amount");
						Element isBingoEle = new Element("isBingo");
						Element winNums = new Element("winNums");
						Element bingoAmount = new Element("bingoAmount");
						
						Element ticketPrize=new Element("ticketPrize");
						
						no.setText(Integer.toString(i));
						contentEle.setText(getChampionSP(bean.getLotteryType(), t.getContent()));
						playType.setText(t.getPlayType().getName());
						parts.setText(Integer.toString((t.getAmount()/2)/t.getMultiple()));
						multipleEle.setText(Integer.toString(t.getMultiple()));
						amount.setText(Integer.toString(t.getAmount()));
						isBingoEle.setText(Integer.toString(t.getIsBingo()));
//						String []strs = null;
						//如果中奖
						if(t.getIsBingo()==0){
							winNums.setText(t.getAmount()/(t.getMultiple()*2)+"");
							bingoAmount.setText(Double.toString(t.getBingoAmount()));
							// 赔率x注数x2x倍数=总额
							
//							ticketPrize.setText(text)
/*							if(t.getBingoContent()!=null){
								strs = t.getBingoContent().split("#");
								winNums.setText(Integer.toString(strs.length));
								bingoAmount.setText(Double.toString(t.getBingoAmount()));
								String prizeCountext=this.ticketService.TicketCheckBingo(openResultMap, t,bean.getLotteryType());
								ticketPrize.setText(prizeCountext);
							}else {
								winNums.setText(Integer.toString(0));
								bingoAmount.setText(Double.toString(0));
								ticketPrize.setText("");
							}*/
							
						}else{
							winNums.setText(Integer.toString(0));
							bingoAmount.setText(Double.toString(0));
							ticketPrize.setText("");
						}
						ticket.addContent(no);
						ticket.addContent(contentEle);
						ticket.addContent(playType);
						ticket.addContent(parts);
						ticket.addContent(multipleEle);
						ticket.addContent(amount);
						ticket.addContent(isBingoEle);
						ticket.addContent(winNums);
						ticket.addContent(bingoAmount);
						ticket.addContent(ticketPrize);
//						tickets.addContent(ticket);
						tickets.addContent(ticket);
						
					}
				}
				responseCodeEle.setText("0");
				responseMessage.setText("处理成功！");
				myBody.addContent(responseCodeEle);
				myBody.addContent(responseMessage);
				myBody.addContent(tickets);
			}else{
				
				responseCodeEle.setText("904701");
				responseMessage.setText("该方案出票内容保密");
				myBody.addContent(responseCodeEle);
				myBody.addContent(responseMessage);
				myBody.addContent(tickets);
			}
			
			


			return softwareService.toPackage(myBody, "9041", agentId);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				String code="";
				if(message!=null){
					code=message;
				}else{
					code="查询或处理中存在异常";
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
	public  String getSP(LotteryType lt, String content) {

		StringBuffer sb = new StringBuffer("");
		if (LotteryType.JCLQList.contains(lt) || LotteryType.JCZQList.contains(lt)) {
			String selectValue = "";
			if (content != null && content.length() > 0) {
				SportteryBetContentModelTurbid comtent = null;
				if (LotteryType.JCLQList.contains(lt)) {
					comtent = Object4Json(content, BasketBallBetContent.class, BasketBallMatchItem.class);
				} else {
					comtent = Object4Json(content, FootBallBetContentTurbid.class, FootBallMatchTurbidItem.class);
				}
				
				List<MatchItem> matchItemList = comtent.getMatchItems();
				//被限制的单关玩法
				List<LotteryType>danguanLimit =new ArrayList<LotteryType>();
				danguanLimit.add(LotteryType.JC_ZQ_SPF);
				danguanLimit.add(LotteryType.JC_ZQ_RQSPF);
				danguanLimit.add(LotteryType.JC_ZQ_JQS);
				danguanLimit.add(LotteryType.JC_ZQ_BQC);
				danguanLimit.add(LotteryType.JC_LQ_RFSF);
				danguanLimit.add(LotteryType.JC_LQ_DXF);
				if (comtent.getPassMode() == 0&&danguanLimit.contains(lt)) {
//					selectValue = "单关:";
					selectValue = "";
					for (int i = 0; i < matchItemList.size(); i++) {
						MatchItem item = matchItemList.get(i);
						List<SportteryOption> optionList = item.getOptions();
						String weekStr = DateTools.getWeekStr(DateTools.StringToDate(item.getIntTime() + "", "yyyyMMdd"));
						selectValue += weekStr + String.format("%03d", item.getLineId()) + "[";
						for (int j = 0; j < optionList.size(); j++) {
							SportteryOption option = optionList.get(j);
							OptionItem optionItem = CommonUtils.getByPlayType(lt, option);
							selectValue += optionItem.getCommonText() + ",";
						}
						selectValue = selectValue.substring(0, selectValue.length() - 1);
						if(i==matchItemList.size()-1){
							selectValue += "]";
						}else {
							selectValue += "]|";
						}
						
					}
					
				} else {

//					selectValue = "过关:";
					selectValue = "";
					for (int i = 0; i < matchItemList.size(); i++) {
						MatchItem item = matchItemList.get(i);
						List<SportteryOption> optionList = item.getOptions();
						String weekStr = DateTools.getWeekStr(DateTools.StringToDate(item.getIntTime() + "", "yyyyMMdd"));
						selectValue += weekStr + String.format("%03d", item.getLineId()) + "[";
						for (int j = 0; j < optionList.size(); j++) {
							SportteryOption option = optionList.get(j);
							OptionItem optionItem = CommonUtils.getByPlayType(lt, option);
							selectValue += optionItem.getCommonText() +"(sp:"+option.getAward()+")"+ ",";
						}
						selectValue = selectValue.substring(0, selectValue.length() - 1);
						if(i==matchItemList.size()-1){
							selectValue += "]";
						}else {
							selectValue += "]|";
						}
						
					}
				}
				

//				selectValue = selectValue.substring(0, selectValue.length()-1);
			}
//			LogUtil.out(selectValue);
			return selectValue;
		} 
		sb.append("");
//		String str  = sb.toString();
//		LogUtil.out(str);
		return sb.toString();

	}	
	
	public  String getChampionSP(LotteryType lt, String content) {
		String selectValue = "";
		if(LotteryType.JCSJBList.contains(lt)){
			if (content != null && content.length() > 0) {
				SportteryBetContentModelTurbid comtent = null;
			    comtent = CommonUtils.Object4JsonTurbid(content, FootBallBetContentTurbid.class, FootBallMatchTurbidItem.class);
			    List<MatchItem> matchItemList = comtent.getMatchItems();
			    selectValue = "单关:";
			    	MatchItem item = matchItemList.get(0);
			    	//法国[2.3]
			    	selectValue += item.getOptions().get(0).getTypeValueStr()+"["+ String.format("%.2f", item.getOptions().get(0).getAward()) +"]";
			}
		}
		
		
		
		return selectValue;
	}
	
	
	public static <T extends SportteryBetContentModelTurbid<X>, X extends MatchItemTurbid> T Object4Json(String content, Class<T> modelClass,
			Class<X> matchitemClass) {
		JSONObject json = JSONObject.fromObject(content);   //解释
		T contentObj = (T) JSONObject.toBean(json, modelClass); //重新组装
		String matchItemStr = json.getString("matchItems");
		Map classMap = new HashMap<String, Class>();
		classMap.put("options", SportteryOption.class);
		List<X> matchItem = CommonUtils.getList4Json(matchItemStr, matchitemClass, classMap);
		contentObj.setMatchItems(matchItem);
//		JSONArray arr = json.getJSONArray("passTypes");
//		List<SportteryPassType> type = new ArrayList<SportteryPassType>();
//		for (int i = 0; i < arr.size(); i++) {
//			SportteryPassType t = SportteryPassType.valueOf(arr.get(i).toString());
//			type.add(t);
//		}
//		contentObj.setPassTypes(type);
		return contentObj;
	}

	public void setSoftwareService(SoftwareService softwareService) {
		this.softwareService = softwareService;
	}

	public void setLotteryPlanOrderService(LotteryPlanOrderService lotteryPlanOrderService) {
		this.lotteryPlanOrderService = lotteryPlanOrderService;
	}

	public void setSqlIsusesService(IsusesService sqlIsusesService) {
		this.sqlIsusesService = sqlIsusesService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setSqlSchemesService(SchemesService sqlSchemesService) {
		this.sqlSchemesService = sqlSchemesService;
	}

	public void setMatchService(MatchService matchService) {
		this.matchService = matchService;
	}

	public void setTicketService(TicketService ticketService) {
		this.ticketService = ticketService;
	}
	
	/**获取 投注 方案的 比赛match id
	 * @param lotteryNumber
	 * @return
	 */
	public List<Integer> matches(String lotteryNumber){
		//7207;[2735(1,2,3)|2736(1,2,3)|2739(1,2,3)|2740(1,2,3)|2737(1,2,3)|2738(1,2,3)];[A01]
		List <Integer> list = new ArrayList<Integer>();
		int begin = lotteryNumber.indexOf('[');
		int end = lotteryNumber.indexOf(']');
		String str1 = lotteryNumber.substring(begin+1, end);
		//str1 2735(1,2,3)|2736(1,2,3)|2739(1,2,3)|2740(1,2,3)|2737(1,2,3)|2738(1,2,3)
		String[]strs1 = str1.split("\\|");
		//strs 2735(1,2,3),2736(1,2,3),2739(1,2,3),2740(1,2,3),2737(1,2,3),2738(1,2,3)
		
		for(int i=0;i<strs1.length;i++){
			String str2 = strs1[i];
			//str2 2735(1,2,3)
			list.add(Integer.parseInt(str2.substring(0, str2.indexOf('('))));
//			matches[i] = Integer.parseInt(str2.substring(0, str2.indexOf('(')));
		}
		
		//[2735, 2736, 2739, 2740, 2737, 2738]
		return list;
	}
	public String matchResult (List<Match>matchs,LotteryType lotteryType){
		StringBuffer sbf = new StringBuffer();
		switch (lotteryType.getValue()) {
		//竞彩足球让球胜平负
		case 7201:
		// 过关:周三1(负),周三2(负) 周三3(负) 周三4(胜) 周三9(平) 周三10(平) 周三11(负) 周三12(负) 
			for(Match match:matchs){
				sbf.append(match.getMatchNumber()).append("("+match.getRQSPFResult()+"),");
			}
			return sbf.toString().substring(0, sbf.length()-1);
		//竞彩足球比分	
		case 7202:
	// 比分:周三1(2:1),周三2(2:1) 周三3(2:1) 周三4(2:1) 周三9(2:1) 周三10(2:1) 周三11(2:1) 周三12(2:1) 	
			for(Match match:matchs){
				sbf.append(match.getMatchNumber()).append("("+match.getResult()+"),");
			}
			return sbf.toString().substring(0, sbf.length()-1);		
			
		//竞彩足球进球数
		case 7203:
		// 比分:周三1(2) 周三2(6) 周三3(6) 周三4(7+) 周三5(5) 周三6(5) 		
			for(Match match:matchs){
				sbf.append(match.getMatchNumber()).append("("+match.getZjqsresult()+"),");
			}
			return sbf.toString().substring(0, sbf.length()-1);					
		//竞彩半全场
		case 7204:
			
			break;
		//竞彩混头过关
		case 7206:
			
			break;	
		//竞彩足球胜负
		case 7207:
			for(Match match:matchs){
				sbf.append(match.getMatchNumber()).append("("+match.getSpfresult()+"),");
			}
			return sbf.toString().substring(0, sbf.length()-1);
		}
		
		return null;
	}
	public void setLotteryPlanService(LotteryPlanService lotteryPlanService) {
		this.lotteryPlanService = lotteryPlanService;
	}
	
	
	//内容转换获取时间
	public String getCountextDate(String context){
		String intTime="";
		JSONObject json = JSONObject.fromObject(context);   //解释
		String matchItemStr = json.getString("matchItems");
		JSONArray jsonArray = JSONArray.fromObject(matchItemStr);
		JSONObject jsonObject;
		Map<String,String> map=new  TreeMap<String,String>();
		for (int j = 0; j < jsonArray.size(); j++) {
			jsonObject = jsonArray.getJSONObject(j);
			 String str =jsonObject.getString("intTime");
			 map.put(str, str);
			// intTime  +=  "'"+str +"',";
		}
		Set<String> keySet = map.keySet();
		
        Iterator<String> iter = keySet.iterator();
        int index=0;
        String start="";
        String end="";
        while (iter.hasNext()) {
        	String key = iter.next();
//        	LogUtil.out("key="+key);
        	index++;
        	if(index==map.size() || index==1){
        		end=key;
        		if(index==1){
        			start=key;
        		}
        	}else if(index==1){
        		start=key;
        	}
        }
		//intTime=intTime.substring(0, intTime.length()-1);
		return intTime=start+":"+end;
	}
	
/*	public static void main(String[] args) {
		String ff="{\"matchItems\":[{\"intTime\":20130806,\"lineId\":2,\"matchId\":2789,\"options\":[{\"award\":3.2,\"value\":\"2\"}],\"shedan\":false},{\"intTime\":20130806,\"lineId\":6,\"matchId\":2793,\"options\":[{\"award\":2.26,\"value\":\"1\"}],\"shedan\":false}],\"multiple\":1,\"passMode\":1,\"passType\":\"P2_1\"}";
		ff="{\"multiple\":1,\"passMode\":1,\"matchItems\":[{\"intTime\":\"20140120\",\"lineId\":1,\"shedan\":\"false\",\"matchId\":\"7209\",\"options\":[{\"award\":4.5,\"value\":\"2\"}]},{\"intTime\":\"20140120\",\"lineId\":2,\"shedan\":\"false\",\"matchId\":\"7210\",\"options\":[{\"award\":2.35,\"value\":\"1\"}]},{\"intTime\":\"20140120\",\"lineId\":3,\"shedan\":\"false\",\"matchId\":\"7211\",\"options\":[{\"award\":3.85,\"value\":\"2\"},{\"award\":3.8,\"value\":\"3\"}]},{\"intTime\":\"20140120\",\"lineId\":4,\"shedan\":\"false\",\"matchId\":\"7212\",\"options\":[{\"award\":4.1,\"value\":\"2\"}]},{\"intTime\":\"20140120\",\"lineId\":5,\"shedan\":\"false\",\"matchId\":\"7213\",\"options\":[{\"award\":3.15,\"value\":\"3\"}]}],\"passTypes\":[\"P5_1\"],\"showPassTypes\":\"\"}";
		JSONObject json = JSONObject.fromObject(ff);   //解释
		String matchItemStr = json.getString("matchItems");
		System.out.println(matchItemStr);
		JSONArray jsonArray = JSONArray.fromObject(matchItemStr);
		JSONObject jsonObject;
		String intTime="";
		Map map=new HashMap();
		for (int j = 0; j < jsonArray.size(); j++) {
			jsonObject = jsonArray.getJSONObject(j);
			 String str =jsonObject.getString("intTime");
			// map.put(str, str);
			 intTime  +=  "'"+str +"',";
		}
		map.put("20131113", "20131113");
		map.put("20131112", "20131112");
		map.put("20131114", "20131114");
		 Set<String> keySet = map.keySet();
        Iterator<String> iter = keySet.iterator();
        int index=0;
        String start="";
        String end="";
        while (iter.hasNext()) {
        	String key = iter.next();
        	index++;
        	if(index==map.size() || index==1){
        		end=key;
        		if(index==1){
        			start=key;
        		}
        	}else if(index==1){
        		start=key;
        	}
            
            
            System.out.println(key + ":" + map.get(key));
        }
		
		System.out.println(start+": "+end);
		
		System.out.println(intTime);
		
		
	}*/

	public static void main(String[] args) {
		
	}
	
	public void setTermBusiness(TermBusiness termBusiness) {
		this.termBusiness = termBusiness;
	}
	
	
	
	
}
