package com.yuncai.modules.lottery.software.lottery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.jdom.Element;

import com.yuncai.core.hibernate.CommonStatus;
import com.yuncai.core.tools.DateTools;
import com.yuncai.core.tools.StringTools;
import com.yuncai.core.util.Strings;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.bean.vo.HmShowBean;
import com.yuncai.modules.lottery.model.Oracle.BkMatch;
import com.yuncai.modules.lottery.model.Oracle.BkSpRate;
import com.yuncai.modules.lottery.model.Oracle.LotteryPlan;
import com.yuncai.modules.lottery.model.Oracle.toolType.BuyType;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanOrderStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.SelectType;
import com.yuncai.modules.lottery.model.Sql.Match;
import com.yuncai.modules.lottery.model.Sql.PassRate;
import com.yuncai.modules.lottery.service.oracleService.baskboll.BkMatchService;
import com.yuncai.modules.lottery.service.oracleService.bkmatch.BkImsMatch500Service;
import com.yuncai.modules.lottery.service.oracleService.fbmatch.FtImsJcService;
import com.yuncai.modules.lottery.service.oracleService.fbmatch.FtImsSfcService;
import com.yuncai.modules.lottery.service.oracleService.lottery.LotteryPlanOrderService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberService;
import com.yuncai.modules.lottery.service.oracleService.ticket.TicketService;
import com.yuncai.modules.lottery.service.oracleService.worldcup.WorldCupTeamService;
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
import com.yuncai.modules.lottery.sporttery.support.SportteryPassType;
import com.yuncai.modules.lottery.sporttery.support.basketball.BasketBallMatchItem;
import com.yuncai.modules.lottery.sporttery.support.football.FootBallMatchTurbidItem;

public class TransactionPlanDetailNotify extends BaseAction implements SoftwareProcess {
	protected SoftwareService softwareService;

	protected LotteryPlanOrderService lotteryPlanOrderService;

	protected IsusesService sqlIsusesService;
	protected MemberService memberService;
	protected SchemesService sqlSchemesService;
	protected MatchService matchService;
	protected TicketService ticketService;
	private Map<String, TransactionPlanDetailNotify>map;
	protected BkMatchService bkMatchService;
	protected  FtImsJcService ftImsJcService;
	
	BkImsMatch500Service bkImsMatch500Service;
	FtImsSfcService ftImsSfcService;
	WorldCupTeamService worldCupTeamService;
	/**
	 * 截取主客队名的长度
	 */
	protected final int NAMELENGTH = 7;
	
	protected Map<String,String>lotteryTypeMap = new HashMap<String, String>();
	{
		lotteryTypeMap.put("7201","让球");
		lotteryTypeMap.put("7202","比分");
		lotteryTypeMap.put("7203","进球");
		lotteryTypeMap.put("7204","半全场");
		lotteryTypeMap.put("7207","非让");
		
		lotteryTypeMap.put("7301","胜负");
		lotteryTypeMap.put("7302","让分");
		lotteryTypeMap.put("7303","胜分差");
		lotteryTypeMap.put("7304","大小");
	}
	
	@Override
	public String execute(Element bodyEle, String agentId) {
		// TODO Auto-generated method stub

		String version = null;
		String message="组装节点不存在";
		SoftwareProcess softwareProcess = null;
		try {
			version = StringTools.isNullOrBlank( bodyEle.getChildText("version"))?null: bodyEle.getChildText("version");
//			version="11";
//			if(version==null){
//				softwareProcess = map.get("4");
//			}else {
//				softwareProcess = map.get(version);
//			}
//			
//			if(softwareProcess==null){
////				message = "对应版本处理不存在";
////				return  PackageXml("004", message, "9116", agentId);
//				softwareProcess = map.get("4");
//			}
			//获取对应版本
			softwareProcess = getPraticableVersion(map, version); 
			
			return  softwareProcess.execute(bodyEle, agentId);
			
		} catch (Exception e) {
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
	 /**获取从指定版本往前可用版本
	 * @param version
	 * @return
	 */
	public SoftwareProcess getPraticableVersion(Map<String, TransactionPlanDetailNotify>map, String version){
		SoftwareProcess softwareProcess = null;
		if(version==null||!version.matches("[0-9]+")){
			softwareProcess = map.get("4");
		}else {
			softwareProcess = map.get(version);
			if(softwareProcess==null){
				softwareProcess = getPraticableVersion(map, Integer.toString(Integer.parseInt(version)-1) );
			}
		}
		return softwareProcess;
	 }
	protected String getWinStr(String selectValue, Match match, LotteryType lotteryType,List<MatchItemTurbid> matchItemList) {
		// TODO Auto-generated method stub
		// 根据不同玩法比较中奖字符串
		switch (lotteryType.getValue()) {
		case 7201:
			if (match.getRQSPFResult() != null) {
				if (selectValue.indexOf(match.getRQSPFResult()) >= 0) {

					selectValue = selectValue.trim().replace(match.getRQSPFResult().trim(),
							"<font color='red'>" + match.getRQSPFResult().trim() + "</font>");
					return selectValue;
				}else {
					return selectValue;
				}
			} else {
				return selectValue;
			}

		case 7202:
			if (match.getZqbfresult() != null) {
				if (selectValue.indexOf(match.getZqbfresult()) >= 0) {

					selectValue = selectValue.trim().replace(match.getZqbfresult().trim(),
							"<font color='red'>" + match.getZqbfresult().trim() + "</font>");
					return selectValue;
				}else {
					return selectValue;
				}
			} else {
				return selectValue;
			}
		case 7203:
			if (match.getZjqsresult() != null) {
				if (selectValue.indexOf(match.getZjqsresult()) >= 0) {

					selectValue = selectValue.trim().replace(match.getZjqsresult().trim(),
							"<font color='red'>" + match.getZjqsresult().trim() + "</font>");
					return selectValue;
				}else {
					return selectValue;
				}
			}  else {
				return selectValue;
			}
		case 7204:
			if (match.getBqcresult() != null) {
				if (selectValue.indexOf(match.getBqcresult()) >= 0) {

					selectValue = selectValue.trim().replace(match.getBqcresult().trim(),
							"<font color='red'>" + match.getBqcresult().trim() + "</font>");
					return selectValue;
				}else {
					return selectValue;
				}
			}  else {
				return selectValue;
			}
		case 7206:
//平,让球平,胜其他
			if(matchItemList!=null&&matchItemList.size()>0){
			for(int j=0;j<matchItemList.size();j++){
				MatchItemTurbid turbid = matchItemList.get(j);
//				LogUtil.out(match.getId().intValue()==turbid.getMatchId().intValue());
				if(match.getId().intValue()==turbid.getMatchId().intValue()){
					//某一场比赛投注的玩法
					List<SportteryOption>options = turbid.getOptions();
					//比赛名称
//					sbf.append(match.getMatchNumber());
					for(SportteryOption option:options){
						String type = option.getType();
						selectValue = replaceRedOption(selectValue,type,match);
					}
					
				}
			}
			}
			return selectValue;
		case 7207:
			if (match.getSpfresult() != null) {
				if (selectValue.indexOf(match.getSpfresult()) >= 0) {

					selectValue = selectValue.trim().replace(match.getSpfresult().trim(),
							"<font color='red'>" + match.getSpfresult().trim() + "</font>");
					return selectValue;
				}else {
					return selectValue;
				}
			}  else {
				return selectValue;
			}

		default:
			break;
		}

		return null;
	}

	/**用于混投 给中奖内容加色 
	 * @param selectValue 投注的内容
	 * @param type 投注的类型
	 * @param match 投注的比赛
	 */
	private String replaceRedOption(String selectValue, String type, Match match) {
		// TODO Auto-generated method stub
		

		
		switch (Integer.parseInt(type)) {
		// 竞彩足球让球胜平负
		case 7201:
			
			if (match.getRQSPFResult() != null) {
				
				if (selectValue.indexOf(match.getRQSPFResult()) >= 0) {

					selectValue = patternResult(selectValue, match, type);
					return selectValue;
				}else {
					return selectValue;
				}
			} else {
				return selectValue;
			}
			
			// 竞彩足球比分
		case 7202:
			if (match.getZqbfresult() != null) {
				if (selectValue.indexOf(match.getZqbfresult()) >= 0) {

					selectValue  = patternResult(selectValue, match, type);
					return selectValue;
				}else {
					return selectValue;
				}
			} else {
				return selectValue;
			}
			// 竞彩足球进球数
		case 7203:
			
			if (match.getZjqsresult() != null) {
				if (selectValue.indexOf(match.getZjqsresult()) >= 0) {

					selectValue  = patternResult(selectValue, match, type);
					return selectValue;
				}else {
					return selectValue;
				}
			}  else {
				return selectValue;
			}
			// 竞彩半全场
		case 7204:
			if (match.getBqcresult() != null) {
				if (selectValue.indexOf(match.getBqcresult()) >= 0) {

					selectValue  = patternResult(selectValue, match, type);
					return selectValue;
				}else {
					return selectValue;
				}
			}  else {
				return selectValue;
			}
			// 竞彩足球胜负
		case 7207:
			if (match.getSpfresult() != null) {
				if (selectValue.indexOf(match.getSpfresult()) >= 0) {

					selectValue  = patternResult(selectValue, match, type);
					return selectValue;
				}else {
					return selectValue;
				}
			}  else {
				return selectValue;
			}
		}
		
		return selectValue;
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
	
	

	public void setFtImsJcService(FtImsJcService ftImsJcService) {
		this.ftImsJcService = ftImsJcService;
	}

	
	public void setBkImsMatch500Service(BkImsMatch500Service bkImsMatch500Service) {
		this.bkImsMatch500Service = bkImsMatch500Service;
	}

	public void setFtImsSfcService(FtImsSfcService ftImsSfcService) {
		this.ftImsSfcService = ftImsSfcService;
	}

	public void setWorldCupTeamService(WorldCupTeamService worldCupTeamService) {
		this.worldCupTeamService = worldCupTeamService;
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

	public String getContentList(HmShowBean lotteryPlan) {
		HttpServletRequest request = ServletActionContext.getRequest();
		String host = request.getServerName();
		int p = request.getServerPort();
		if (lotteryPlan.getIsUploadContent() == CommonStatus.YES.getValue()) {
			if (lotteryPlan.getSelectType().getValue() == SelectType.UPLOAD.getValue()) {
				return "<a href='http://" + host + ":" + p + lotteryPlan.getContent() + "' target='_blank'>下载文件</a>";
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

	//

	public String getMatchPassTypes(LotteryType lt, String content){
		StringBuffer sbf = new StringBuffer("");
		if (content != null && content.length() > 0) {
			SportteryBetContentModelTurbid comtent = null;
			if (LotteryType.JCLQList.contains(lt)) {
				comtent = CommonUtils.Object4JsonTurbid(content, BasketBallBetContent.class, BasketBallMatchItem.class);
			} else {
				comtent = CommonUtils.Object4JsonTurbid(content, FootBallBetContentTurbid.class, FootBallMatchTurbidItem.class);
			}
			
			List<SportteryPassType> passTypes = comtent.getPassTypes();
			for(int i=0;i<passTypes.size();i++){
				SportteryPassType passType  =  passTypes.get(i);
				
				if(passType!=null){
					if(passType.getType().getName().equals("单关")&&lt.getValue()!=8001){
						sbf.append("单关(浮动奖金,赛后竞彩官方公布为准)"+",");
					}else {
						sbf.append(passType.getType().getName()+",");
					}
					
				}
			}
	
		}
		
//		System.out.println((sbf.length()>0)?sbf.toString().substring(0, sbf.toString().length()-1) :sbf.toString());
		return  (sbf.length()>0)?sbf.toString().substring(0, sbf.toString().length()-1) :sbf.toString();
	}
	
	public List<MatchItemTurbid> getMatchItemsList(LotteryType lt, String content) {
		List<MatchItemTurbid> matchItemList = new ArrayList<MatchItemTurbid>();
		StringBuffer sb = new StringBuffer("");
		String selectValue = "";
		if (content != null && content.length() > 0) {
			SportteryBetContentModelTurbid comtent = null;
			if (LotteryType.JCLQList.contains(lt)) {
				comtent = CommonUtils.Object4JsonTurbid(content, BasketBallBetContent.class, BasketBallMatchItem.class);
			} else {
				comtent = CommonUtils.Object4JsonTurbid(content, FootBallBetContentTurbid.class, FootBallMatchTurbidItem.class);
			}
			matchItemList = comtent.getMatchItems();
//		    comtent.getPassTypes().get(0).;
			Collections.sort(matchItemList, new Comparator<MatchItem>() {
				// 先以intTime 排序再以itemid排序
				@Override
				public int compare(MatchItem o1, MatchItem o2) {
					// TODO Auto-generated method stub
					if (o1.getIntTime() > o2.getIntTime()) {
						return 1;
					} else if (o1.getIntTime().equals(o2.getIntTime())) {
						if (o1.getLineId() >= o2.getLineId()) {
							return 1;
						} else {
							return 0;
						}
					} else {
						return 0;
					}
				}

			});

		}
		return matchItemList;
	}

	/**篮球
	 * @param lt
	 * @param content
	 * @return
	 */
	public List<BasketBallMatchItem> getBKMatchItemsList(LotteryType lt, String content) {
		List<BasketBallMatchItem> matchItemList = new ArrayList<BasketBallMatchItem>();
		StringBuffer sb = new StringBuffer("");
		String selectValue = "";
		if (content != null && content.length() > 0) {
			BasketBallBetContent comtent = null;
			if (LotteryType.JCLQList.contains(lt)) {
				comtent = CommonUtils.Object4JsonTurbid(content, BasketBallBetContent.class, BasketBallMatchItem.class);
			} 
			matchItemList = comtent.getMatchItems();
			Collections.sort(matchItemList, new Comparator<MatchItem>() {
				// 先以intTime 排序再以itemid排序
				@Override
				public int compare(MatchItem o1, MatchItem o2) {
					// TODO Auto-generated method stub
					if (o1.getIntTime() > o2.getIntTime()) {
						return 1;
					} else if (o1.getIntTime().equals(o2.getIntTime())) {
						if (o1.getLineId() >= o2.getLineId()) {
							return 1;
						} else {
							return 0;
						}
					} else {
						return 0;
					}
				}

			});

		}
		return matchItemList;
	}
	
	
	/**冠军玩法
	 * @param lt
	 * @param content
	 * @return
	 */
	public List<MatchItemTurbid> getChampionMatchItemsList(LotteryType lt, String content) {
		List<MatchItemTurbid> matchItemList = new ArrayList<MatchItemTurbid>();
		StringBuffer sb = new StringBuffer("");
		String selectValue = "";
		if (content != null && content.length() > 0) {
			SportteryBetContentModelTurbid comtent = null;
		    comtent = CommonUtils.Object4JsonTurbid(content, FootBallBetContentTurbid.class, FootBallMatchTurbidItem.class);
			matchItemList = comtent.getMatchItems();
//		    comtent.getPassTypes().get(0).;
			Collections.sort(matchItemList, new Comparator<MatchItem>() {
				// 先以intTime 排序再以itemid排序
				@Override
				public int compare(MatchItem o1, MatchItem o2) {
					// TODO Auto-generated method stub
					if (o1.getLineId() > o2.getLineId()) {
						return 1;
					} else {
						return 0;
					}
				}

			});

		}
		return matchItemList;
	}
	
	/**获取投注时大小分,让分 基数值
	 * 注意dx rf 用于区分 大小分，让分
	 * @param matchItemList
	 * @return
	 */
	public Map<String,String> getBKMatchDXBasic(List<BasketBallMatchItem> matchItemList){
		Map<String,String> map = new HashMap<String, String>();
		for(BasketBallMatchItem item:matchItemList){
			map.put(item.getIntTime()+"dx"+item.getLineId(), item.getDXF().toString());
			map.put(item.getIntTime()+"rf"+item.getLineId(), item.getRF().toString());
		}
		return map;
	}
	
	
	public void setSqlSchemesService(SchemesService sqlSchemesService) {
		this.sqlSchemesService = sqlSchemesService;
	}

	public void setTicketService(TicketService ticketService) {
		this.ticketService = ticketService;
	}

	/**
	 * 获取 投注 方案的 比赛match id
	 * 
	 * @param lotteryNumber
	 * @return
	 */
	public List<Integer> matches(String lotteryNumber) {
		// 7207;[2735(1,2,3)|2736(1,2,3)|2739(1,2,3)|2740(1,2,3)|2737(1,2,3)|2738(1,2,3)];[A01]
		List<Integer> list = new ArrayList<Integer>();
		int begin = lotteryNumber.indexOf('[');
		int end = lotteryNumber.indexOf(']');
		String str1 = lotteryNumber.substring(begin + 1, end);
		// str1
		// 2735(1,2,3)|2736(1,2,3)|2739(1,2,3)|2740(1,2,3)|2737(1,2,3)|2738(1,2,3)
		String[] strs1 = str1.split("\\|");
		// strs
		// 2735(1,2,3),2736(1,2,3),2739(1,2,3),2740(1,2,3),2737(1,2,3),2738(1,2,3)

		for (int i = 0; i < strs1.length; i++) {
			String str2 = strs1[i];
			// str2 2735(1,2,3)
			list.add(Integer.parseInt(str2.substring(0, str2.indexOf('('))));
			// matches[i] = Integer.parseInt(str2.substring(0,
			// str2.indexOf('(')));
		}

		// [2735, 2736, 2739, 2740, 2737, 2738]
		return list;
	}

	public void setMatchService(MatchService matchService) {
		this.matchService = matchService;
	}

	public String matchResult(List<Match> matchs, LotteryType lotteryType,List<MatchItemTurbid> matchItemList) {
		StringBuffer sbf = new StringBuffer();
		switch (lotteryType.getValue()) {
		// 竞彩足球让球胜平负
		case 7201:
			// 过关:周三1(负),周三2(负) 周三3(负) 周三4(胜) 周三9(平) 周三10(平) 周三11(负) 周三12(负)
			sbf.append("让球:");
			for (Match match : matchs) {
				if(match.getRQSPFResult()==null||match.getRQSPFResult().equals("")){
					continue;
				}
				sbf.append(match.getMatchNumber()).append("(" + match.getRQSPFResult() + "),");

			}
//			LogUtil.out("sbf"+sbf.toString());
			if(sbf.toString().trim()==null||sbf.toString().trim().equals("")){
				return sbf.toString();
			}
			//某些情况下会出现 只有"让球:"情况
			if(sbf.toString().equals("让球:")){
				return "让球:null";
			}
			return sbf.toString().substring(0, sbf.length() - 1);
			// 竞彩足球比分
		case 7202:
			// 比分:周三1(2:1),周三2(2:1) 周三3(2:1) 周三4(2:1) 周三9(2:1) 周三10(2:1)
			// 周三11(2:1) 周三12(2:1)
			for (Match match : matchs) {
				if(match.getResult()==null||match.getResult().equals("")||match.getResult().indexOf("-")>=0){
					continue;
				}
				sbf.append(match.getMatchNumber()).append("(" + match.getResult() + "),");
			}
			if(sbf.toString().trim()==null||sbf.toString().trim().equals("")){
				return sbf.toString();
			}
			return sbf.toString().substring(0, sbf.length() - 1);

			// 竞彩足球进球数
		case 7203:
			// 比分:周三1(2) 周三2(6) 周三3(6) 周三4(7+) 周三5(5) 周三6(5)
			for (Match match : matchs) {
				if(match.getZjqsresult()==null||match.getZjqsresult().equals("")){
					continue;
				}
				sbf.append(match.getMatchNumber()).append("(" + match.getZjqsresult() + "),");
			}
			if(sbf.toString().trim()==null||sbf.toString().trim().equals("")){
				return sbf.toString();
			}
			return sbf.toString().substring(0, sbf.length() - 1);
			// 竞彩半全场
		case 7204:
			// 周三1(胜胜,平胜) 周三2(平平) 周三3(平胜) 周三4(平负)
			for (Match match : matchs) {
				if(match.getBqcresult()==null||match.getBqcresult().equals("")){
					continue;
				}
				sbf.append(match.getMatchNumber()).append("(" + match.getBqcresult() + "),");
			}
			if(sbf.toString().trim()==null||sbf.toString().trim().equals("")){
				return sbf.toString();
			}
			return sbf.toString().substring(0, sbf.length() - 1);
			// 竞彩混头过关
		case 7206:
			//周三1(胜)(3:1) 周三2(平)(7+) 周三3(胜)()
			for(int i=0;i<matchs.size();i++){
				Match match = matchs.get(i);
				//某一场比赛
				for(int j=0;j<matchItemList.size();j++){
					MatchItemTurbid turbid = matchItemList.get(j);
//					LogUtil.out(match.getId().intValue()==turbid.getMatchId().intValue());
					if(match.getId().intValue()==turbid.getMatchId().intValue()){
						//某一场比赛投注的玩法
						List<SportteryOption>options = turbid.getOptions();
						//比赛名称
						sbf.append(match.getMatchNumber());
						List<String>list = new ArrayList<String>();
						for(SportteryOption option:options){
							String type = option.getType();
							//判断玩法是否已经包含 包含则不重复显示
							if(list.contains(type)){
								continue;
							}
							list.add(type);
							sbf.append(getOptionResult(type, match));
						}
						
					}
				}
	
			}
//			LogUtil.out(sbf.toString());
			return sbf.toString();
		// 竞彩足球胜负
		case 7207:
			for (Match match : matchs) {
				if(match.getSpfresult()==null||match.getSpfresult().equals("")){
					continue;
				}
				
				sbf.append(match.getMatchNumber()).append("(" + match.getSpfresult() + "),");
			}
			if(sbf.toString().trim()==null||sbf.toString().trim().equals("")){
				return sbf.toString();
			}
			return sbf.toString().substring(0, sbf.length() - 1);
		}

		return null;
	}

	public String getOptionResult(String type,Match match){
		//赛果置null 没关系 有代码处理为null情况不予显示
		switch (Integer.parseInt(type)) {
		// 竞彩足球让球胜平负
		case 7201:
			if(match.getRQSPFResult()==null||match.getRQSPFResult().equals("")){
				return "让球:null";
			}
			return "(让球" + match.getRQSPFResult() + ")";
			// 竞彩足球比分
		case 7202:
			if(match.getResult()==null||match.getResult().equals("")||match.getResult().indexOf("-")>=0){
				return "(" +"比分：null" + ")";
			}			
			return "(" + match.getResult() + ")";
			// 竞彩足球进球数
		case 7203:
			if(match.getZjqsresult()==null||match.getZjqsresult().equals("")){
				return "(" + "进球数：null" + ")";
			}			
			return "(" + match.getZjqsresult() + ")";
			// 竞彩半全场
		case 7204:
			if(match.getBqcresult()==null||match.getBqcresult().equals("")){
				return "(" + "半全场：null" + ")";
			}			
			return "(" + match.getBqcresult() + ")";
			// 竞彩足球胜负
		case 7207:
			if(match.getSpfresult()==null||match.getSpfresult().equals("")){
				return "(" + "胜负：null" + ")";
			}			
			return "(" + match.getSpfresult() + ")";
		}
		return null;
		
		
	}

	public String getBKOptionResult(String type,BkMatch match){
		
		switch (Integer.parseInt(type)) {
		// 竞彩篮球胜负
		case 7301:
			if(match.getSfResult()==null||match.getSfResult().equals("")){
				return "(" + null + ")";
			}			
			return "(" + match.getSfResult() + ")";
			// 竞彩篮球让分胜负
		case 7302:
			if(match.getRfsfResult()==null||match.getRfsfResult().equals("")){
				return "(" + null + ")";
			}			
			return "(让分" + match.getRfsfResult() + ")";
			// 竞彩篮球胜分差
		case 7303:
			if(match.getSfcResult()==null||match.getSfcResult().equals("")){
				return "(" + null + ")";
			}			
			return "(" + match.getSfcResult() + ")";
			// 竞彩篮球大小分
		case 7304:
			if(match.getDxResult()==null||match.getDxResult().equals("")){
				return "(" + null + ")";
			}			
			return "(" + match.getDxResult() + ")";
		}
		return null;
		
		
	}

	public String getSP(LotteryType lt, String content) {

		StringBuffer sb = new StringBuffer("");
		if (LotteryType.JCLQList.contains(lt) || LotteryType.JCZQList.contains(lt)) {
			String selectValue = "";
			if (content != null && content.length() > 0) {
				SportteryBetContentModelTurbid comtent = null;
				if (LotteryType.JCLQList.contains(lt)) {

				} else {
					comtent = Object4Json(content, FootBallBetContentTurbid.class, FootBallMatchTurbidItem.class);
				}
				List<MatchItem> matchItemList = comtent.getMatchItems();

				if (comtent.getPassMode() == 0) {
					// selectValue = "单关:";
					selectValue = "";
				} else {
					// selectValue = "过关:";
					selectValue = "";
				}

				for (int i = 0; i < matchItemList.size(); i++) {
					MatchItem item = matchItemList.get(i);
					List<SportteryOption> optionList = item.getOptions();
					String weekStr = DateTools.getWeekStr(DateTools.StringToDate(item.getIntTime() + "", "yyyyMMdd"));
					selectValue += weekStr + item.getLineId() + "(";
					for (int j = 0; j < optionList.size(); j++) {
						SportteryOption option = optionList.get(j);
						OptionItem optionItem = CommonUtils.getByPlayType(lt, option);
						selectValue += optionItem.getCommonText() + "(sp:" + option.getAward() + ")" + ",";
					}
					selectValue = selectValue.substring(0, selectValue.length() - 1);
					selectValue += ") ";
				}
			}
			return selectValue;
		}
		sb.append("");
		return sb.toString();

	}

	public static <T extends SportteryBetContentModelTurbid<X>, X extends MatchItemTurbid> T Object4Json(String content, Class<T> modelClass,
			Class<X> matchitemClass) {
		JSONObject json = JSONObject.fromObject(content); // 解释
		T contentObj = (T) JSONObject.toBean(json, modelClass); // 重新组装
		String matchItemStr = json.getString("matchItems");
		Map classMap = new HashMap<String, Class>();
		classMap.put("options", SportteryOption.class);
		List<X> matchItem = CommonUtils.getList4Json(matchItemStr, matchitemClass, classMap);
		contentObj.setMatchItems(matchItem);
		return contentObj;
	}

	public void setMap(Map<String, TransactionPlanDetailNotify> map) {
		this.map = map;
	}

	public Map<String, TransactionPlanDetailNotify> getMap() {
		return map;
	}
	
	public void setBkMatchService(BkMatchService bkMatchService) {
		this.bkMatchService = bkMatchService;
	}

	public String patternResult(String select,Match match ,String type){
		//平,让球平,胜其他
		String[] strs = select.split(",");
		StringBuffer sbf = new StringBuffer();
		// TODO Auto-generated method stub
		switch (Integer.parseInt(type)) {
		// 竞彩足球让球胜平负
		case 7201:
			
			if (match.getRQSPFResult() != null) {
				for(String str:strs){
					if(str.equals(match.getRQSPFResult())){
						str=str.replace(match.getRQSPFResult().trim(),
								"<font color='red'>" + match.getRQSPFResult().trim() + "</font>");
					}
					sbf.append(str+",");
				}
				return sbf.toString().substring(0, sbf.toString().length()-1);
			} else {
				return select;
			}
			
			// 竞彩足球比分
		case 7202:
			if (match.getZqbfresult() != null) {
				for(String str:strs){
					if(str.equals(match.getZqbfresult())){
						str=str.replace(match.getZqbfresult().trim(),
								"<font color='red'>" + match.getZqbfresult().trim() + "</font>");
					}
					sbf.append(str+",");
				}
				return sbf.toString().substring(0, sbf.toString().length()-1);
			} else {
				return select;
			}
			// 竞彩足球进球数
		case 7203:
			
			if (match.getZjqsresult() != null) {
				
				for(String str:strs){
					if(str.equals(match.getZjqsresult())){
						str=str.replace(match.getZjqsresult().trim(),
								"<font color='red'>" + match.getZjqsresult().trim() + "</font>");
					}
					sbf.append(str+",");
				}
				return sbf.toString().substring(0, sbf.toString().length()-1);
				

			}  else {
				return select;
			}
			// 竞彩半全场
		case 7204:
			if (match.getBqcresult() != null) {
				for(String str:strs){
					if(str.equals(match.getBqcresult())){
						str=str.replace(match.getBqcresult().trim(),
								"<font color='red'>" + match.getBqcresult().trim() + "</font>");
					}
					sbf.append(str+",");
				}
				return sbf.toString().substring(0, sbf.toString().length()-1);
				
//				if (selectValue.indexOf(match.getBqcresult()) >= 0) {
//
//					selectValue = selectValue.trim().replace(match.getBqcresult().trim(),
//							"<font color='red'>" + match.getBqcresult().trim() + "</font>");
//					return selectValue;
//				}else {
//					return selectValue;
//				}
			}  else {
				return select;
			}
			// 竞彩足球胜负
		case 7207:
			if (match.getSpfresult() != null) {
				for(String str:strs){
					if(str.equals(match.getSpfresult())){
						str=str.replace(match.getSpfresult().trim(),
								"<font color='red'>" + match.getSpfresult().trim() + "</font>");
					}
					sbf.append(str+",");
				}
				return sbf.toString().substring(0, sbf.toString().length()-1);
				
				
//				if (selectValue.indexOf(match.getSpfresult()) >= 0) {
//
//					selectValue = selectValue.trim().replace(match.getSpfresult().trim(),
//							"<font color='red'>" + match.getSpfresult().trim() + "</font>");
//					return selectValue;
//				}else {
//					return selectValue;
//				}
			}  else {
				return select;
			}
		}
		
		return select;
	}
	public String matchBKResult(List<BkMatch> matches, LotteryType lotteryType, List<BasketBallMatchItem> matchItemList) {
		// TODO Auto-generated method stub
		StringBuffer sbf = new StringBuffer();
		
		switch (lotteryType.getValue()) {
		//竞彩篮球胜负
		case 7301:
			//周三1(负),周三2(负) 周三3(负) 周三4(胜) 周三9(平) 周三10(平) 周三11(负) 周三12
			for(BkMatch match:matches){
				if(match.getSfResult()==null||match.getSfResult().equals("")){
					continue;
				}
				sbf.append(match.getMatchNo()).append("("+match.getSfResult()+"),");
			}
			if(sbf.toString().trim()==null||sbf.toString().trim().equals("")){
				return sbf.toString();
			}
			return sbf.substring(0, sbf.toString().length()-1);
			
			
		//竞彩篮球让分胜负	
		case 7302:			
			for(BkMatch match:matches){
				if(match.getRfsfResult()==null||match.getRfsfResult().equals("")){
					continue;
				}
				sbf.append(match.getMatchNo()).append("("+match.getRfsfResult()+"),");
			}
			if(sbf.toString().trim()==null||sbf.toString().trim().equals("")){
				return sbf.toString();
			}
			return sbf.substring(0, sbf.toString().length()-1);		
			
			//竞彩篮球胜分差
		case 7303:
			for(BkMatch match:matches){
				if(match.getSfcResult()==null||match.getSfcResult().equals("")){
					continue;
				}
				sbf.append(match.getMatchNo()).append("("+match.getSfcResult()+"),");
			}
			if(sbf.toString().trim()==null||sbf.toString().trim().equals("")){
				return sbf.toString();
			}
			return sbf.substring(0, sbf.toString().length()-1);			
			
			//竞彩篮球大小分
		case 7304:
			for(BkMatch match:matches){
				if(match.getDxResult()==null||match.getDxResult().equals("")){
					continue;
				}
				sbf.append(match.getMatchNo()).append("("+match.getDxResult()+"),");
			}
			if(sbf.toString().trim()==null||sbf.toString().trim().equals("")){
				return sbf.toString();
			}
			return sbf.substring(0, sbf.toString().length()-1);	
			
			//竞彩篮球混投
		case 7305:
				for(int i=0;i<matches.size();i++){
					BkMatch match = matches.get(i);
					for(int j=0;j<matchItemList.size();j++){
						BasketBallMatchItem item = matchItemList.get(j);
						//这里存在macht id与item 的match Id不一致的问题
						if((match.getIntTime()+match.getLineId()).equals(item.getIntTime()+""+item.getLineId())){
							List<SportteryOption>options = item.getOptions();
							sbf.append(match.getMatchNo());
							List<String>list = new ArrayList<String>();
							for(SportteryOption option:options){
								String type = option.getType();
								if(list.contains(type)){
									continue;
								}
								list.add(type);
								sbf.append(getBKOptionResult(type, match));
							}
						}
					}
				}
			return sbf.toString();
			
		}
		
		return null;
	}
	
	
	/**篮球中奖 匹配变色处理
	 * @param selectValue
	 * @param match
	 * @param lotteryType
	 * @return
	 */
	protected String getBkWinStr(String selectValue,BkMatch match,LotteryType lotteryType){
		
		StringBuffer sbf = new StringBuffer();
		
		switch (lotteryType.getValue()) {
		//竞彩篮球胜负
		case 7301:
			if(match.getSfResult()!=null){
				if(selectValue.indexOf(match.getSfResult())>=0){
					selectValue = selectValue.trim().replace(match.getSfResult(), "<font color='red'>" + match.getSfResult() + "</font>");
					return selectValue;
				}
			}else{
				return selectValue;
			}
			
		//竞彩篮球让分胜负	
		case 7302:			
			if(match.getRfsfResult()!=null){
				if(selectValue.indexOf("让"+match.getRfsfResult())>=0){
					selectValue = selectValue.trim().replace("让"+match.getRfsfResult(), "<font color='red'>" +("让"+ match.getRfsfResult() )+ "</font>");
				
				}
			}	
			//再去掉让
			selectValue = selectValue.replace("让", "");
			return selectValue;
			//竞彩篮球胜分差
		case 7303:
			if(match.getSfcResult()!=null){
				if(selectValue.indexOf(match.getSfcResult().replace("客胜", "主负"))>=0){
					selectValue = selectValue.trim().replace(match.getSfcResult().replace("客胜", "主负"), "<font color='red'>" + match.getSfcResult().replace("客胜", "主负") + "</font>");
				}
			}	
			try {
				//进一步做胜负分差细分	
				selectValue = SFCSelectContentHandling(selectValue);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
				return selectValue;
			
			//竞彩篮球大小分
		case 7304:
			if(match.getDxResult()!=null){
				if(selectValue.indexOf(match.getDxResult())>=0){
					selectValue = selectValue.trim().replace(match.getDxResult(), "<font color='red'>" + match.getDxResult() + "</font>");
					return selectValue;
				}
			}else{
				return selectValue;
			}		
			
		}
		
		return selectValue;
	}
	
	public String changeFTColor(SportteryOption option,OptionItem item,Match match){
		String str = item.getText();
		switch (Integer.parseInt(option.getType()) ) {
		// 竞彩足球让球胜平负
		case 7201:
			if (match.getRQSPFResult() != null) {
				if(item.getText().equals(match.getRQSPFResult())){
					str=str.replace(match.getRQSPFResult().trim(),
							"<font color='red'>" + match.getRQSPFResult().trim() + "</font>");
				}
			}
			break;
			// 竞彩足球比分
		case 7202:
			if (match.getZqbfresult() != null) {
				if(item.getText().equals(match.getZqbfresult())){
					str=str.replace(match.getZqbfresult().trim(),
							"<font color='red'>" + match.getZqbfresult().trim() + "</font>");
				}
			}		
			
			break;
			// 竞彩足球进球数
		case 7203:
			if (match.getZjqsresult() != null) {
				if(item.getText().equals(match.getZjqsresult())){
					str=str.replace(match.getZjqsresult().trim(),
							"<font color='red'>" + match.getZjqsresult().trim() + "</font>");
				}
			}				
			break;
			// 竞彩半全场
		case 7204:
			if (match.getBqcresult() != null) {
				if(item.getText().equals(match.getBqcresult())){
					str=str.replace(match.getBqcresult().trim(),
							"<font color='red'>" + match.getBqcresult().trim() + "</font>");
				}
			}				
			break;
			// 竞彩足球胜负
		case 7207:
			if (match.getSpfresult() != null) {
				if(item.getText().equals(match.getSpfresult())){
					str=str.replace(match.getSpfresult().trim(),
							"<font color='red'>" + match.getSpfresult().trim() + "</font>");
				}
			}		
			break;

	
		}
		
		return str;
	}
	
	
	public String changeBKColor(SportteryOption option,OptionItem item,BkMatch match){
		String str = item.getCommonText();
		switch (Integer.parseInt(option.getType()) ) {
		// 竞彩篮球胜负
		case 7301:
			if (match.getSfResult() != null) {
				if(item.getCommonText().equals(match.getSfResult())){
					str=str.replace(item.getCommonText(),
							"<font color='red'>" + item.getCommonText() + "</font>");
				}
			}
			break;
			// 竞彩篮球让分胜负
		case 7302:
			if (match.getRfsfResult() != null) {
				if(item.getCommonText().equals("让"+match.getRfsfResult())){
					str=str.replace(item.getCommonText(),
							"<font color='red'>" + item.getCommonText() + "</font>");
				}
			}		
			//去掉让
			str = str.replace("让", "");
			break;
			// 竞彩篮球胜分差
		case 7303:
			if (match.getSfcResult()!= null) {
				if(item.getCommonText().equals(match.getSfcResult().replace("客胜", "主负"))){
					str=str.replace(item.getCommonText(),
							"<font color='red'>" + item.getCommonText() + "</font>");
				}
			}

			break;
			// 竞彩篮球大小分
		case 7304:
			if (match.getDxResult() != null) {
				if(item.getCommonText().equals(match.getDxResult())){
					str=str.replace(item.getCommonText(),
							"<font color='red'>" + item.getCommonText() + "</font>");
				}
			}				
			break;
		}
		
		return str;
	}
	
	
	
	/**对胜分差投注详情内容 加工 分 胜分差 ，负分差
	 * @param content
	 * @return
	 */
	public String SFCSelectContentHandling(String content){
		
		String[]strs = content.split(",");
		StringBuffer sbf1 = new StringBuffer();
		StringBuffer sbf2 = new StringBuffer();
		StringBuffer sbf3 = new StringBuffer();
		for(String str:strs){
			if(str.indexOf("主负")>=0){
				sbf1.append(str+",");
			}
			if(str.indexOf("主胜")>=0){
				sbf2.append(str+",");
			}
		}
		
		String str1 = null;
		String str2 = null;
		
		if(sbf2.length()>0){
			str2 = sbf2.toString().substring(0, sbf2.toString().length()-1);
			sbf3.append("主胜["+str2.replaceAll("主胜", "")+"]<br>");
		}
		
		if(sbf1.length()>0){
			str1 = sbf1.toString().substring(0, sbf1.toString().length()-1);
			sbf3.append("客胜["+str1.replaceAll("主负", "")+"]");
		}
//		str1 = sbf3.toString();

		
		return sbf3.toString();
	}
	
	/**针对混投胜分差投注详情内容 处理
	 * @return
	 */
	public String HtSFCSelectContentHandlig(String content){
		
		String[] strs  = content.split("<br>");
		String temp1 = null;
		for(String str :strs){
			if(str.indexOf("胜分差")>=0){
				//胜分差[主负21-25,主胜21-25]
				temp1 = str;
				break;
			}
		}
		if(temp1!=null){
			//主负21-25,主胜21-25
			String temp2 = temp1.substring(temp1.indexOf("[")+1, temp1.lastIndexOf("]"));
			 String target = SFCSelectContentHandling(temp2);
			return content.replace(temp1, target);
			
		}else{
			return content;
		}

	}
	
	/**根据长度截取队名
	 * @param name 队名
	 * @param length 要指定的长度
	 * @return
	 */
	public String teamNameIntercept(String name,int length){
		if(name==null){
			return "";
		}
		if(name.length()>length){
			
			return name.substring(0, length);
		}
		
		return name;
		
	}
	/**
	 * 联赛排名汉字部分只保留第一位
	 * 
	 * @param str
	 * @return
	 */
	public  String trimRank(String str) {
		if (str == null) {
			return "";
		}
		//数字部分
		String num = str.replaceAll("[^0-9]", "");
		//非数字部分
		String cn = Strings.getByReg(str, "[\u4e00-\u9fa5]");
		if(cn!=null){
			if(cn.length()==1){
				return cn+num;
			}else if(cn.length()>1){
				return cn.charAt(0)+num;
			}
			
		}
		return num;
	}
	
	
	/**解析出来老足彩每场比赛投注的内容
	 * @param content
	 * @param length
	 * @return
	 */
	public String[] getSfcContent(String content,Integer length){
	
		int index=0;
		String[]target=new String[length];
		for(int i=0;i<content.length();i++){
			char temp = content.charAt(i);
			if(temp=='-'||(temp>='0'&&temp<='9')){
				
				target[index]=temp+"";
				    index++;
			}
			if(temp=='('){
				int recent = content.indexOf(')', i);
				target[index]=content.substring(i+1, recent);
				StringBuffer sbf = new StringBuffer();
				for(int a=0;a<target[index].length();a++){
					sbf.append(target[index].charAt(a)+",");
				}
				target[index]= sbf.toString().substring(0, sbf.toString().length()-1);
				i=recent;
			    index++;
			}
			if(index==length){
				break;
			}
		}
		return target;
	}
	
	
	/**用于大乐透传进来的内容排序由于客户端9版本之前大乐透传进来投注内容存在乱序问题
	 * @param content
	 * @param playType
	 * @return
	 */
	public  String sortDLTContent(String content,int playType){
		String sortedContent=null;
		StringBuffer sbf =new StringBuffer();
		String[] rows=content.trim().split("\\\n+");
		
		switch (playType) {
		//标准
		case 3901:
			//[35 05 15 07 12 + 01 03]
			for(int i=0;i<rows.length;i++){
				String row=rows[i];
				String[]strs=row.trim().split("\\+");
				strs[0]= sortString(strs[0].trim());
				strs[1]= sortString(strs[1].trim());
				rows[i]=strs[0]+"+"+strs[1];
				sbf.append(rows[i]+"\n");
			}
			sortedContent=sbf.toString();
			sortedContent=sortedContent.substring(0, sortedContent.lastIndexOf("\n"));
			break;		
		//标准
		case 3902:
			for(int i=0;i<rows.length;i++){
				String row=rows[i];
				String[]strs=row.trim().split("\\+");
				strs[0]= sortString(strs[0].trim());
				strs[1]= sortString(strs[1].trim());
				rows[i]=strs[0]+"+"+strs[1];
				sbf.append(rows[i]+"\n");
			}
			sortedContent=sbf.toString();
			sortedContent=sortedContent.substring(0, sortedContent.lastIndexOf("\n"));
			break;
		//胆拖	
		case 3903:
			for(int i=0;i<rows.length;i++){
				String row=rows[i];
				String[]strs=row.trim().split("\\+");
				String dan = strs[0].split(",")[0];
				String tuo= strs[0].split(",")[1];
				strs[0]= sortString(dan.trim())+","+sortString(tuo.trim());
				strs[1]= sortString(strs[1].trim());
				rows[i]=strs[0]+"+"+strs[1];
				sbf.append(rows[i]+"\n");
				
			}
			sortedContent=sbf.toString();
			sortedContent=sortedContent.substring(0, sortedContent.lastIndexOf("\n"));
			break;
			//胆拖	
		case 3904:
			for(int i=0;i<rows.length;i++){
				String row=rows[i];
				String[]strs=row.trim().split("\\+");
				String dan = strs[0].split(",")[0];
				String tuo= strs[0].split(",")[1];
				strs[0]= sortString(dan.trim())+" , "+sortString(tuo.trim());
				strs[1]= sortString(strs[1].trim());
				rows[i]=strs[0]+"+"+strs[1];
				sbf.append(rows[i]+"\n");
				
			}
			sortedContent=sbf.toString();
			sortedContent=sortedContent.substring(0, sortedContent.lastIndexOf("\n"));
			break;	
			//标准
		case 2:
			for(int i=0;i<rows.length;i++){
				String row=rows[i];
				String[]strs=row.trim().split("\\+");
				strs[0]= sortString(strs[0].trim());
				strs[1]= sortString(strs[1].trim());
				rows[i]=strs[0]+"+"+strs[1];
				sbf.append(rows[i]+"\n");
			}
			sortedContent=sbf.toString();
			sortedContent=sortedContent.substring(0, sortedContent.lastIndexOf("\n"));
			break;			
			//胆拖	
		case 3:
			for(int i=0;i<rows.length;i++){
				String row=rows[i];
				String[]strs=row.trim().split("\\+");
				String dan = strs[0].split(",")[0];
				String tuo= strs[0].split(",")[1];
				strs[0]= sortString(dan.trim())+" , "+sortString(tuo.trim());
				strs[1]= sortString(strs[1].trim());
				rows[i]=strs[0]+"+"+strs[1];
				sbf.append(rows[i]+"\n");
				
			}
			sortedContent=sbf.toString();
			sortedContent=sortedContent.substring(0, sortedContent.lastIndexOf("\n"));
			break;				
		default:
			sortedContent=content;
			break;
		}
		
		return sortedContent;
		
	}
	/**给数字组成的字符串排序后返回
	 * @param content
	 * @return
	 */
	public  String sortString(String content){
		String sortedStr=null;
		
		String[]strs=content.trim().split("\\s+");
//		System.out.println("进来时候："+content+"--"+content.length());
		//System.out.println("排序前："+Arrays.toString(strs));
		
		Arrays.sort(strs,new Comparator<String>(){

			@Override
			public int compare(String o1, String o2) {
				// TODO Auto-generated method stub
				int i1=Integer.parseInt(o1);
				int i2=Integer.parseInt(o2);
				int r=i1-i2;
				if(r>0){
					return 1;
				}else if(r==0){
					
					return 0;
				}else{
					return -1;
				}
				
			}});
		sortedStr=Arrays.toString(strs);
		sortedStr=sortedStr.replaceAll("\\,", "").replaceAll("[\\[|\\]]", "");
//		System.out.println("排序后："+sortedStr.trim()+"--"+sortedStr.length());
//		System.out.println(content.trim().length()==sortedStr.trim().length());
		return sortedStr;
		
	}
	
	
	/**
	 * 获取竞彩足球 选择内容（方案投注内容）
	 */
	public String getJCZQHTSelectValue(List<SportteryOption> optionList, HmShowBean bean, Match match) {
		// List<SportteryOption> optionList = item.getOptions();
		String selectValue = "";
		StringBuffer sbf_select = new StringBuffer("");
		SportteryOption tempOption = null;// 中间变量

		for (int j = 0; j < optionList.size(); j++) {
			SportteryOption option = optionList.get(j);
			OptionItem optionItem = CommonUtils.getByPlayType(bean.getLotteryType(), option);
			String optionText = optionItem.getText();
			// 如果是混投
			if (bean.getLotteryType().getValue() == LotteryType.JC_ZQ_HT.getValue()) {
				optionText = changeFTColor(option, optionItem, match);
			}

			// 如果本次选项玩法与上次不同
			if (tempOption == null) {
				sbf_select.append(lotteryTypeMap.get(option.getType()) == null ? "" : lotteryTypeMap.get(option.getType())).append(
						"[" + optionText + ",");
			} else if (tempOption != null && !tempOption.getType().equals(option.getType())) {
				sbf_select.deleteCharAt(sbf_select.length() - 1);
				sbf_select.append("]<br>");
				sbf_select.append(lotteryTypeMap.get(option.getType()) == null ? "" : lotteryTypeMap.get(option.getType())).append(
						"[" + optionText + ",");

			} else {
				sbf_select.append(optionText + ",");
			}
			tempOption = option;
		}
		sbf_select.deleteCharAt(sbf_select.length() - 1);
		sbf_select.append("]");
		selectValue = sbf_select.toString();

		return selectValue;
	}

	/**
	 * 计算是否公开合买方案内容
	 * 
	 * @param bean
	 * @return
	 */
	public int calculatePublishFlag(HmShowBean bean) {
		int flag = 0;
		// 以下代码 根据条件判断公开情况
		switch (bean.getPublicStatus().getValue()) {
		// 公开
		case 0:
			flag = 1;
			break;
		// 截止后公开
		case 1:
			if(bean.getLateDateTime()!=null){

				
				if (bean.getLateDateTime().before(new Date())) {
					flag = 1;
				} else {
					flag = 0;
				}
				
			}else{
				if (bean.getDealDateTime().before(new Date())) {
					flag = 1;
				} else {
					flag = 0;
				}
			}

			break;
		// 开奖后公开
		case 2:
			// LogUtil.out(bean.getWinStatus().getValue());
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
		return flag;
	}

	/**
	 * 判断订单在方案详情列表是否可见
	 * 
	 * @param buyType
	 * @param planStatus
	 * @param planOrderStatus
	 * @return
	 */
	public boolean isPlanOrderVisible(BuyType buyType, PlanStatus planStatus, PlanOrderStatus planOrderStatus) {
		boolean b = true;
		// 以下情况保底订单不可见
		// 判断是否是保底订单
		if (buyType.equals(BuyType.BAODI)) {

			// 判断 方案招募中
			if (planStatus.equals(PlanStatus.RECRUITING)) {
				b = false;
			} else if (planStatus.getValue() >= PlanStatus.PAY_FINISH.getValue()) {
				// 如果已退款
				if (planOrderStatus.getValue() == 3) {
					b = false;
				}

			}
		}
		return b;

	}

	/**
	 * 根据matchItem 集合 得到ID 后去获取 相应的match 及passRate信息 并给传入到matches， passRates集合
	 * 
	 * @param matchItemList
	 * @param matches
	 * @param passRates
	 */
	public void initMatchAndPassRateSets(List<MatchItemTurbid> matchItemList, List<Match> matches, List<PassRate> passRates) {
		// 获取投注的比赛id List<MatchItem> matchItemList 中item.id
		List<Integer> ids = new ArrayList<Integer>();

		for (int i = 0; i < matchItemList.size(); i++) {
			ids.add(matchItemList.get(i).getMatchId());
		}

		// 获取match 及对应的赔率集合
		List<Object[]> matchAndPassRates = matchService.findMatchAndPassRate(ids);
		ids = null;// 释放
		for (int i = 0; i < matchAndPassRates.size(); i++) {
			Object[] obj = matchAndPassRates.get(i);
			matches.add((Match) obj[0]);
			passRates.add((PassRate) obj[1]);
		}
	}

	/**
	 * 组装出 用户选择的内容
	 * 
	 * @param lotteryType
	 * @param optionList
	 * @param match
	 * @return
	 */
	public String extractSelectedValueFT(LotteryType lotteryType, List<SportteryOption> optionList, Match match) {
		String selectValue = "";
		StringBuffer sbf_select = new StringBuffer("");

		SportteryOption tempOption = null;// 中间变量
		// 如果是混投
		if (lotteryType.getValue() == LotteryType.JC_ZQ_HT.getValue()) {

			for (int j = 0; j < optionList.size(); j++) {
				SportteryOption option = optionList.get(j);
				OptionItem optionItem = CommonUtils.getByPlayType(lotteryType, option);
				String optionText = optionItem.getText();
				// 如果是混投
				if (lotteryType.getValue() == LotteryType.JC_ZQ_HT.getValue()) {
					optionText = changeFTColor(option, optionItem, match);
				}

				// 如果本次选项玩法与上次不同
				if (tempOption == null) {
					sbf_select.append(lotteryTypeMap.get(option.getType()) == null ? "" : lotteryTypeMap.get(option.getType())).append(
							"[" + optionText + ",");
				} else if (tempOption != null && !tempOption.getType().equals(option.getType())) {
					sbf_select.deleteCharAt(sbf_select.length() - 1);
					sbf_select.append("]<br>");
					sbf_select.append(lotteryTypeMap.get(option.getType()) == null ? "" : lotteryTypeMap.get(option.getType())).append(
							"[" + optionText + ",");

				} else {
					sbf_select.append(optionText + ",");
				}
				tempOption = option;
			}
			sbf_select.deleteCharAt(sbf_select.length() - 1);
			sbf_select.append("]");
			selectValue = sbf_select.toString();
			// selectValue = getJCZQHTSelectValue(optionList, bean,
			// match);
		} else {
			for (int j = 0; j < optionList.size(); j++) {
				SportteryOption option = optionList.get(j);
				OptionItem optionItem = CommonUtils.getByPlayType(lotteryType, option);
				selectValue += optionItem.getText() + ",";
			}
			selectValue = selectValue.substring(0, selectValue.length() - 1);
		}
		return selectValue;
	}

	/**
	 * 组装针对竞彩足球 主客队 VS信息内容
	 * 
	 * @param lotteryType
	 * @param match
	 * @param passRate
	 * @return
	 */
	public String assembleVSTextFT(LotteryType lotteryType, Match match, PassRate passRate) {
		String vsText = "";
		// VS 如果是竞彩让球胜平负,或混投特殊处理
		if (lotteryType.getValue() == LotteryType.JC_ZQ_RQSPF.getValue() || lotteryType.getValue() == LotteryType.JC_ZQ_HT.getValue()) {
			// 混投
			if (lotteryType.getValue() == LotteryType.JC_ZQ_HT.getValue()) {
				if (passRate.getMainLoseball() > 0) {

					vsText = match.getMainTeam() + "<br>" + "<font color='blue'>[+" + passRate.getMainLoseball() + "]</font>" + "<br>"
							+ match.getGuestTeam();
				} else if (passRate.getMainLoseball() < 0) {
					vsText = match.getMainTeam() + "<br>" + "<font color='red'>[" + passRate.getMainLoseball() + "]</font>" + "<br>"
							+ match.getGuestTeam();
				}
			} else {
				// 让球
				if (passRate.getMainLoseball() > 0) {
					vsText = match.getMainTeam() + "<br>" + "<font color='blue'>[+" + passRate.getMainLoseball() + "]</font>" + "<br>"
							+ match.getGuestTeam();
				} else {
					vsText = match.getMainTeam() + "<br>" + "<font color='red'>[" + passRate.getMainLoseball() + "]</font>" + "<br>"
							+ match.getGuestTeam();
				}
			}

		} else {
			// 不让球
			vsText = match.getMainTeam() + "<br>" + "vs" + "<br>" + match.getGuestTeam();
		}
		return vsText;
	}
	
	/**
	 * 根据matchItem 集合 得到ID 后去获取 相应的match 及passRate信息 并给传入到matches， passRates集合
	 * 
	 * @param matchItemList
	 * @param matches
	 * @param passRates
	 */
	public void initMatchAndPassRateSetsBK(List<BasketBallMatchItem> matchItemList, List<BkMatch> matches,List<BkSpRate> passRates) {
		
		// 获取投注的比赛id List<MatchItem> matchItemList 中item.id
		List<Integer> intTimes = new ArrayList<Integer>();
		for (int i = 0; i < matchItemList.size(); i++) {
			// ids.add(matchItemList.get(i).getMatchId());
			intTimes.add(matchItemList.get(i).getIntTime());
		}


		// -----------------------------------------------------------------------
		// 二号方案
		List<Object[]> matchAndPassRates = bkMatchService.findMatchAndPassRateByIntTimes(intTimes);

		for (int i = 0; i < matchItemList.size(); i++) {
			BasketBallMatchItem item = matchItemList.get(i);
			for (int j = 0; j < matchAndPassRates.size(); j++) {
				Object[] obj = matchAndPassRates.get(j);
				BkMatch match = (BkMatch) obj[0];
				BkSpRate rate = (BkSpRate) obj[1];
				if (item.getLineId().equals(Integer.parseInt(match.getLineId())) && item.getIntTime().equals(match.getIntTime())) {
					matches.add(match);
					passRates.add(rate);
				}
			}

		}
		
		
	}

	/**
	 * 组装出 用户选择的内容
	 * 
	 * @param lotteryType
	 * @param optionList
	 * @param match
	 * @return
	 */
	public String extractSelectedValueBK(LotteryType lotteryType, List<SportteryOption> optionList, BkMatch match) {
		String selectValue = "";
		
		StringBuffer sbf_select = new StringBuffer();
		SportteryOption tempOption = null;
		if (lotteryType.getValue() == LotteryType.JC_LQ_HT.getValue()) {
			for (int j = 0; j < optionList.size(); j++) {
				SportteryOption option = optionList.get(j);
				OptionItem optionItem = CommonUtils.getByPlayType(lotteryType, option);
				String optionText = optionItem.getCommonText();
				optionText = changeBKColor(option, optionItem, match);

				if (tempOption == null) {
					// 如果上次选项为空
					sbf_select.append(lotteryTypeMap.get(option.getType()) == null ? "" : lotteryTypeMap.get(option.getType())).append(
							"[" + optionText + ",");
				}
				// 如果本次选项玩法与上次不同
				else if (tempOption != null && !tempOption.getType().equals(option.getType())) {
					sbf_select.deleteCharAt(sbf_select.length() - 1);
					sbf_select.append("]<br>");
					sbf_select.append(lotteryTypeMap.get(option.getType()) == null ? "" : lotteryTypeMap.get(option.getType())).append(
							"[" + optionText + ",");

				} else {
					// selectValue += optionItem.getCommonText() +
					// ",";
					sbf_select.append(optionText + ",");
				}
				tempOption = option;
				sbf_select.deleteCharAt(sbf_select.length() - 1);
				// sbf_select.append("]");
				if (j == optionList.size() - 1) {
					// 当最后一次遍历
					sbf_select.append("]");
				} else {
					sbf_select.append(",");
				}

			}
			selectValue = sbf_select.toString();
			selectValue = HtSFCSelectContentHandlig(selectValue);
			// LogUtil.out(selectValue);
		} else {
			for (int j = 0; j < optionList.size(); j++) {
				SportteryOption option = optionList.get(j);
				OptionItem optionItem = CommonUtils.getByPlayType(lotteryType, option);
				selectValue += optionItem.getCommonText() + ",";
			}
			selectValue = selectValue.substring(0, selectValue.length() - 1);
		}
		
		return selectValue;
	}
	
	

	/**
 * 组装针对竞彩篮球 主客队 VS信息内容
 * 
 * @param lotteryType
 * @param match
 * @param passRate
 * @return
 */
public String assembleVSTextBK(LotteryType lotteryType, BkMatch match,List<BasketBallMatchItem> matchItemList,BasketBallMatchItem item) {
	String vsText = "";
	
	// 投注内容中大小分 map 以initTime+""+lineId为key
	Map<String, String> dxBasic = getBKMatchDXBasic(matchItemList);
	// 如果是竞彩篮球让分胜平负特殊处理
	if (lotteryType.getValue() == LotteryType.JC_LQ_RFSF.getValue()) {
		// 让球
		if (Double.parseDouble(dxBasic.get(item.getIntTime() + "rf" + item.getLineId())) < 0) {
//			vs.setText(match.getTgName() + "<br>" + "<font color='red'>[" + dxBasic.get(item.getIntTime() + "rf" + item.getLineId())
//					+ "]</font>" + "<br>" + match.getMbName());
			
			vsText = match.getTgName() + "<br>" + "<font color='red'>[" + dxBasic.get(item.getIntTime() + "rf" + item.getLineId())
			+ "]</font>" + "<br>" + match.getMbName();
			
		} else if (Double.parseDouble(dxBasic.get(item.getIntTime() + "rf" + item.getLineId())) > 0) {
//			vs.setText(match.getTgName() + "<br>" + "<font color='blue'>[+"
//					+ dxBasic.get(item.getIntTime() + "rf" + item.getLineId()) + "]</font>" + "<br>" + match.getMbName());
			
			vsText = match.getTgName() + "<br>" + "<font color='blue'>[+"
			+ dxBasic.get(item.getIntTime() + "rf" + item.getLineId()) + "]</font>" + "<br>" + match.getMbName();
			
		}
	} else if (lotteryType.getValue() == LotteryType.JC_LQ_DXF.getValue()) {
		// 大小分
//		vs.setText(match.getTgName() + "<br>" + "<font color='#ff8821'>["
//				+ dxBasic.get(match.getIntTime() + "dx" + match.getLineId()) + "]</font>" + "<br>" + match.getMbName());
		
		vsText = match.getTgName() + "<br>" + "<font color='#ff8821'>["
		+ dxBasic.get(match.getIntTime() + "dx" + match.getLineId()) + "]</font>" + "<br>" + match.getMbName();
		
	} else if (lotteryType.getValue() == LotteryType.JC_LQ_HT.getValue()) {
		// 混投

		StringBuffer sbf = new StringBuffer("");

		sbf.append(match.getTgName() + "<br>");
		sbf.append("[");
		sbf.append("<font color='#ff8821'>" + dxBasic.get(match.getIntTime() + "dx" + match.getLineId()) + "</font>");
		sbf.append("|");
		if (Double.parseDouble(dxBasic.get(item.getIntTime() + "rf" + item.getLineId())) > 0) {
			sbf.append("<font color='blue'>+" + dxBasic.get(item.getIntTime() + "rf" + item.getLineId()) + "</font>");
		} else if (Double.parseDouble(dxBasic.get(item.getIntTime() + "rf" + item.getLineId())) < 0) {
			sbf.append("<font color='red'>" + dxBasic.get(item.getIntTime() + "rf" + item.getLineId()) + "</font>");
		}
		sbf.append("]<br>");
		sbf.append(match.getMbName());
		// LogUtil.out(sbf.toString());
		vsText = sbf.toString();
//		vs.setText(sbf.toString());
	} else {
		// 
//		vs.setText(match.getTgName() + "<br>" + "vs" + "<br>" + match.getMbName());
		vsText = match.getTgName() + "<br>" + "vs" + "<br>" + match.getMbName();
	}
	
	return vsText;
	
}
	
	
  /**竞彩用 中间变量转换对象
 * @author qiuhai
 *
 */
 class InnerMatch{
		 String matchNumber;
		 String game;
		 String vs ;
		 String stopSellingTime;
		 String shedan;
		 String content;
		 String result;
		 
	}


  /**老足彩用 中间变量转换对象
 * @author Administrator
 *
 */
 class InnerSFCMatch{
	  
	  String no;
	  String startTime;
	  String mainTeam;
	  String guestTeam;
	  String content;
	  String halfContent;
	  String bifen;
	  String halfBifen;
	  String result;
	  String halfResult;
	  
  }
}
