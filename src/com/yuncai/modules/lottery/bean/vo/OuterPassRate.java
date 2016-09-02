package com.yuncai.modules.lottery.bean.vo;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.yuncai.core.tools.DateTools;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.core.tools.StringTools;
import com.yuncai.core.util.DBHelper;
import com.yuncai.core.util.Strings;
import com.yuncai.modules.lottery.model.Oracle.FtBetInfo;
import com.yuncai.modules.lottery.model.Oracle.FtBfAward;
import com.yuncai.modules.lottery.model.Oracle.FtBqcAward;
import com.yuncai.modules.lottery.model.Oracle.FtJqsAward;
import com.yuncai.modules.lottery.model.Oracle.FtMatch;
import com.yuncai.modules.lottery.model.Oracle.FtNearRecord;
import com.yuncai.modules.lottery.model.Oracle.FtSpfAward;
import com.yuncai.modules.lottery.model.Oracle.weixin.CommentBySearch;
import com.yuncai.modules.lottery.model.Sql.SPYaPan;
import com.yuncai.modules.lottery.service.oracleService.fbmatch.FtImsJcService;
import com.yuncai.modules.lottery.service.oracleService.weixin.FtMatchCommentService;
import com.yuncai.modules.lottery.service.sqlService.fbmatch.SPYaPanService;

public class OuterPassRate {
	private String error;
	private String msg;
	private String serverTime;
	private String keys;
	private String allowMaxMultiple;
	private List<Map<String, List<InnerPassRate>>> dtMatch = new ArrayList<Map<String, List<InnerPassRate>>>();

	public String getAllowMaxMultiple() {
		return allowMaxMultiple;
	}

	public void setAllowMaxMultiple(String allowMaxMultiple) {
		this.allowMaxMultiple = allowMaxMultiple;
	}

	public String getKeys() {
		return keys;
	}

	public void setKeys(String keys) {
		this.keys = keys;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getServerTime() {
		return serverTime;
	}

	public void setServerTime(String serverTime) {
		this.serverTime = serverTime;
	}

	public List<Map<String, List<InnerPassRate>>> getDtMatch() {
		return dtMatch;
	}

	public void setDtMatch(List<Map<String, List<InnerPassRate>>> dtMatch) {
		this.dtMatch = dtMatch;
	}

//	public void initDtMatch(List<PassRate> lists, OuterPassRate bigPassRate) {
//		List<InnerPassRate> smallPassRates = new ArrayList<InnerPassRate>();
//		if (lists != null) {
//			for (PassRate passRate : lists) {
//				InnerPassRate sRate = new InnerPassRate();
//				sRate.setMatchId(Integer.toString(passRate.getMatchId()));
//				sRate.setMatchNumber(passRate.getMatchNumber());
//				sRate.setMatchDate(DateTools.dateToString(passRate.getMatchDate()));
//				sRate.setGame(passRate.getGame());
//				sRate.setMainTeam(passRate.getMainTeam());
//				sRate.setGuestTeam(passRate.getGuestTeam());
//				sRate.setStopSellTime(DateTools.dateToString(passRate.getStopSellTime()));
//				sRate.setMainLoseball(Integer.toString(passRate.getMainLoseball()));
//				// -----
//				sRate.setMatchDate1(DateTools.getWeekStr(DateTools.stringToDate(passRate.getDay(), "yyyyMMdd")));
//				sRate.setWin(Double.toString(passRate.getWin()));
//				sRate.setFlat(Double.toString(passRate.getFlat()));
//				sRate.setLose(Double.toString(passRate.getLose()));
//
//				sRate.setSpfwin(Double.toString(passRate.getSpfWin()));
//				sRate.setSpfflat(Double.toString(passRate.getSpfFlat()));
//				sRate.setSpflose(Double.toString(passRate.getSpfLose()));
//
//				sRate.setSother(Double.toString(passRate.getSother()));
//				sRate.setS10(Double.toString(passRate.getS10()));
//				sRate.setS20(Double.toString(passRate.getS20()));
//				sRate.setS21(Double.toString(passRate.getS21()));
//				sRate.setS31(Double.toString(passRate.getS31()));
//				sRate.setS32(Double.toString(passRate.getS32()));
//				sRate.setS40(Double.toString(passRate.getS40()));
//				sRate.setS41(Double.toString(passRate.getS41()));
//				sRate.setS42(Double.toString(passRate.getS42()));
//				sRate.setS50(Double.toString(passRate.getS50()));
//				sRate.setS51(Double.toString(passRate.getS51()));
//				sRate.setS52(Double.toString(passRate.getS52()));
//				sRate.setS30(Double.toString(passRate.getS30()));
//
//				sRate.setPother(Double.toString(passRate.getPother()));
//				sRate.setP00(Double.toString(passRate.getP00()));
//				sRate.setP11(Double.toString(passRate.getP11()));
//				sRate.setP22(Double.toString(passRate.getP22()));
//				sRate.setP33(Double.toString(passRate.getP33()));
//
//				sRate.setFother(Double.toString(passRate.getFother()));
//				sRate.setF01(Double.toString(passRate.getF01()));
//				sRate.setF02(Double.toString(passRate.getF02()));
//				sRate.setF12(Double.toString(passRate.getF12()));
//				sRate.setF03(Double.toString(passRate.getF03()));
//				sRate.setF13(Double.toString(passRate.getF13()));
//				sRate.setF23(Double.toString(passRate.getF23()));
//				sRate.setF04(Double.toString(passRate.getF04()));
//				sRate.setF14(Double.toString(passRate.getF14()));
//				sRate.setF15(Double.toString(passRate.getF15()));
//				sRate.setF24(Double.toString(passRate.getF24()));
//				sRate.setF05(Double.toString(passRate.getF05()));
//				sRate.setF25(Double.toString(passRate.getF25()));
//
//				sRate.setIn0(Double.toString(passRate.getIn0()));
//				sRate.setIn1(Double.toString(passRate.getIn1()));
//				sRate.setIn2(Double.toString(passRate.getIn2()));
//				sRate.setIn3(Double.toString(passRate.getIn3()));
//				sRate.setIn4(Double.toString(passRate.getIn4()));
//				sRate.setIn5(Double.toString(passRate.getIn5()));
//				sRate.setIn6(Double.toString(passRate.getIn6()));
//				sRate.setIn7(Double.toString(passRate.getIn7()));
//
//				sRate.setIsSPF(passRate.getIsSpf() == 1 ? "true" : "false");
//				sRate.setIsZJQ(passRate.getIsTtg() ? "true" : "false");
//				sRate.setIsCBF(passRate.getIsCrs() ? "true" : "false");
//				sRate.setIsNewSPF(passRate.getIsHhad() ? "true" : "false");
//				// 新增day
//				sRate.setDate(passRate.getDay());
//				smallPassRates.add(sRate);
//			}
//		}
//
////		Map<String, List<InnerPassRate>> map = new TreeMap<String, List<InnerPassRate>>();
//		Map<String, List<InnerPassRate>> map = new LinkedHashMap<String, List<InnerPassRate>>();
//		for (int i = 0; i < smallPassRates.size(); i++) {
//
//			InnerPassRate passRate = smallPassRates.get(i);
//			// 根据date分组
//			if (map.get(passRate.getDate()) == null) {
//				List<InnerPassRate> list = new ArrayList<InnerPassRate>();
//				list.add(passRate);
//				 map.put(passRate.getDate(), list);
//			} else {
//				map.get(passRate.getDate()).add(passRate);
//			}
//		}
//		this.dtMatch.add(map);
//		bigPassRate.setKeys(getKeys(map.keySet().toString()));
//		// 设置允许的最大比赛投注倍数
//		bigPassRate.setAllowMaxMultiple("999");
//	}

	public void initDtMatch(List<Object[]> objs, OuterPassRate bigPassRate, FtMatchCommentService ftMatchCommentService,
			FtImsJcService ftImsJcService, SPYaPanService sYaPanService) {
		List<InnerPassRate> smallPassRates = new ArrayList<InnerPassRate>();
		if (objs != null || objs.size() > 0) {
			List<CommentBySearch> comments;
			List<Map<String, Object>> ranks;
			List<Integer> midList = new ArrayList<Integer>();
			StringBuffer mids = new StringBuffer();
			for (Object[] obj : objs) {
				mids.append(Integer.toString(((FtMatch) obj[0]).getMid()) + ",");
				midList.add(((FtMatch) obj[0]).getMid());
			}
			comments = ftMatchCommentService.findComment(mids.toString().substring(0, mids.toString().length() - 1));
			ranks = ftImsJcService.findRank(mids.toString().substring(0, mids.toString().length() - 1));
			// 专家点评
			Map<String, String> comMap = new HashMap<String, String>();
			for (CommentBySearch com : comments) {
				comMap.put(com.getMid().toString(), com.getContent());
			}
			// 排名
			Map<String, Object> rankMap = new HashMap<String, Object>();
			for (Map<String, Object> rank : ranks) {
				rankMap.put(rank.get("mid").toString(), rank);
			}
			// 根据mid查找亚盘 ,大小球 部分比赛可能无数据
			List<SPYaPan> spYapList = sYaPanService.findByProperty("mid", midList);
			Map<String, SPYaPan> spYapMap = new HashMap<String, SPYaPan>();
			for (SPYaPan pan : spYapList) {
				spYapMap.put(pan.getMid().toString(), pan);

			}

			for (int i = 0; i < objs.size(); i++) {
				Object[] obj = objs.get(i);
				FtMatch match = (FtMatch) obj[0];
				FtBfAward bfAward = (FtBfAward) obj[1];
				FtBqcAward bqcAward = (FtBqcAward) obj[2];
				FtJqsAward jqsAward = (FtJqsAward) obj[3];
				FtSpfAward spfAward = (FtSpfAward) obj[4];
				FtBetInfo betInfo = (FtBetInfo) obj[5];
				FtNearRecord record = (FtNearRecord) obj[6];
				InnerPassRate sRate = new InnerPassRate();
				sRate.setMatchId(match.getSqlId().toString());
				sRate.setMid(match.getMid() + "");
				sRate.setMatchNumber(match.getMatchNo());
				sRate.setMatchDate(match.getMatchDate());
				sRate.setGame(match.getLeagueName());
				sRate.setMainTeam(match.getMbName());
				sRate.setGuestTeam(match.getTgName());
				sRate.setStopSellTime(DateTools.dateToString(match.getNosaleTime()));
				sRate.setMainLoseball(Integer.toString(match.getRangBallNum()));
				sRate.setMatchDate1(DateTools.getWeekStr(DateTools.stringToDate(match.getIntTime() + "", "yyyyMMdd")));
				sRate.setWin(doRatio2(spfAward.getHomewinaward()));// 让球
				sRate.setFlat(doRatio2(spfAward.getDrawaward()));
				sRate.setLose(doRatio2(spfAward.getGuestwinaward()));
				// 不让球
				sRate.setSpfwin(doRatio2(spfAward.getNrhomewinaward()));
				sRate.setSpfflat(doRatio2(spfAward.getNrdrawaward()));
				sRate.setSpflose(doRatio2(spfAward.getNrguestwinaward()));
				// 比分
				sRate.setSother(doRatio2(bfAward.getSWinAward()));
				sRate.setS10(doRatio2(bfAward.getS10award()));
				sRate.setS20(doRatio2(bfAward.getS20award()));
				sRate.setS21(doRatio2(bfAward.getS21award()));
				sRate.setS31(doRatio2(bfAward.getS31award()));
				sRate.setS32(doRatio2(bfAward.getS32award()));
				sRate.setS40(doRatio2(bfAward.getS40award()));
				sRate.setS41(doRatio2(bfAward.getS41award()));
				sRate.setS42(doRatio2(bfAward.getS42award()));
				sRate.setS50(doRatio2(bfAward.getS50award()));
				sRate.setS51(doRatio2(bfAward.getS51award()));
				sRate.setS52(doRatio2(bfAward.getS52award()));
				sRate.setS30(doRatio2(bfAward.getS30award()));

				sRate.setPother(doRatio2(bfAward.getSDrawAward()));
				sRate.setP00(doRatio2(bfAward.getS00award()));
				sRate.setP11(doRatio2(bfAward.getS11award()));
				sRate.setP22(doRatio2(bfAward.getS22award()));
				sRate.setP33(doRatio2(bfAward.getS33award()));

				sRate.setFother(doRatio2(bfAward.getSLoseAward()));
				sRate.setF01(doRatio2(bfAward.getS01award()));
				sRate.setF02(doRatio2(bfAward.getS02award()));
				sRate.setF12(doRatio2(bfAward.getS12award()));
				sRate.setF03(doRatio2(bfAward.getS03award()));
				sRate.setF13(doRatio2(bfAward.getS13award()));
				sRate.setF23(doRatio2(bfAward.getS23award()));
				sRate.setF04(doRatio2(bfAward.getS04award()));
				sRate.setF14(doRatio2(bfAward.getS14award()));
				sRate.setF15(doRatio2(bfAward.getS15award()));
				sRate.setF24(doRatio2(bfAward.getS24award()));
				sRate.setF05(doRatio2(bfAward.getS05award()));
				sRate.setF25(doRatio2(bfAward.getS25award()));

				// 进球数
				sRate.setIn0(Double.toString(jqsAward.getS0Award()));
				sRate.setIn1(Double.toString(jqsAward.getS1Award()));
				sRate.setIn2(Double.toString(jqsAward.getS2Award()));
				sRate.setIn3(Double.toString(jqsAward.getS3Award()));
				sRate.setIn4(Double.toString(jqsAward.getS4Award()));
				sRate.setIn5(Double.toString(jqsAward.getS5Award()));
				sRate.setIn6(Double.toString(jqsAward.getS6Award()));
				sRate.setIn7(Double.toString(jqsAward.getS7Award()));

				sRate.setIsSPF(spfAward.getIsHad() == 0 ? "false" : "true");// 是否有胜平负可投
				sRate.setIsZJQ(jqsAward.getIsTrue() == 0 ? "false" : "true");
				sRate.setIsCBF(bfAward.getIsTrue() == 0 ? "false" : "true");
				sRate.setIsNewSPF(spfAward.getIsHhad() == 0 ? "false" : "true");// 是否有让求胜平负可投
				// 新增day
				sRate.setDate(match.getIntTime() + "");
				// ------------------------------------------------------------------------------------------
//				LogUtil.out("match.getMid="+match.getMid());
				Map map = (Map) rankMap.get(match.getMid().toString());
				//有可能出现没有某场比赛主客队排名的记录
				if(map!=null){
					// 根据mid 从map中取数据
					String mbRank=map.get("mb_rank")+"";
					String tgRank=map.get("tg_rank")+"";
					if(DBHelper.isNoNull(mbRank) && !"null".equals(mbRank)){
						sRate.setMRank(trimRank(map.get("mb_rank").toString()));
					} else {
						sRate.setMRank("");
					}
					if(DBHelper.isNoNull(tgRank) && !"null".equals(tgRank)){
						sRate.setGRank(trimRank(map.get("tg_rank").toString()));
					} else {
						sRate.setGRank("");
					}
				}else{
					sRate.setMRank("");
					sRate.setGRank("");
				}

				// 处理亚盘数据
				SPYaPan pan = spYapMap.get(match.getMid().toString());
				if(pan!=null){
					sRate.setZjqRefData("大小球:" + (StringTools.isNullOrBlank(pan.getDmbRate()) ? "" : pan.getDmbRate()) + ","
							+ (StringTools.isNullOrBlank(pan.getDpk()) ? "" : pan.getDpk()) + ","
							+ (StringTools.isNullOrBlank(pan.getDtgRate()) ? "" : pan.getDtgRate()) + "#亚赔Bet365:"
							+ (StringTools.isNullOrBlank(pan.getMbRate()) ? "" : pan.getMbRate()) + ","
							+ (StringTools.isNullOrBlank(pan.getPk()) ? "" : pan.getPk()) + ","
							+ (StringTools.isNullOrBlank(pan.getTgRate()) ? "" : pan.getTgRate()));
					// LogUtil.out(sRate.getZjqRefData());
					sRate.setCbfRefData("大小球:" + (StringTools.isNullOrBlank(pan.getDmbRate()) ? "" : pan.getDmbRate()) + ","
							+ (StringTools.isNullOrBlank(pan.getDpk()) ? "" : pan.getDpk()) + ","
							+ (StringTools.isNullOrBlank(pan.getDtgRate()) ? "" : pan.getDtgRate()) + "#亚赔Bet365:"
							+ (StringTools.isNullOrBlank(pan.getMbRate()) ? "" : pan.getMbRate()) + ","
							+ (StringTools.isNullOrBlank(pan.getPk()) ? "" : pan.getPk()) + ","
							+ (StringTools.isNullOrBlank(pan.getTgRate()) ? "" : pan.getTgRate()));
					sRate.setSpfRefData("投注比例:胜" + (StringTools.isNullOrBlank(betInfo.getNwinPercent()) ? "" : betInfo.getNwinPercent()) + ",平"
							+ (StringTools.isNullOrBlank(betInfo.getNdrawPercent()) ? "" : betInfo.getNdrawPercent()) + ",负"
							+ (StringTools.isNullOrBlank(betInfo.getNlostPercent()) ? "" : betInfo.getNlostPercent()) + "#大小球:"
							+ (StringTools.isNullOrBlank(pan.getDmbRate()) ? "" : pan.getDmbRate()) + ","
							+ (StringTools.isNullOrBlank(pan.getDpk()) ? "" : pan.getDpk()) + ","
							+ (StringTools.isNullOrBlank(pan.getDtgRate()) ? "" : pan.getDtgRate()) + "#亚赔Bet365:"
							+ (StringTools.isNullOrBlank(pan.getMbRate()) ? "" : pan.getMbRate()) + ","
							+ (StringTools.isNullOrBlank(pan.getPk()) ? "" : pan.getPk()) + ","
							+ (StringTools.isNullOrBlank(pan.getTgRate()) ? "" : pan.getTgRate()) + "");
					sRate.setRspfRefData("投注比例:胜" + (StringTools.isNullOrBlank(betInfo.getWinPercent()) ? "" : betInfo.getWinPercent()) + ",平"
							+ (StringTools.isNullOrBlank(betInfo.getDrawPercent()) ? "" : betInfo.getDrawPercent()) + ",负"
							+ (StringTools.isNullOrBlank(betInfo.getLostPercent()) ? "" : betInfo.getLostPercent()) + "#大小球:"
							+ (StringTools.isNullOrBlank(pan.getDmbRate()) ? "" : pan.getDmbRate()) + "," + (pan.getDpk() == null ? "" : pan.getDpk())
							+ "," + (StringTools.isNullOrBlank(pan.getDtgRate()) ? "" : pan.getDtgRate()) + "#亚赔Bet365:"
							+ (StringTools.isNullOrBlank(pan.getMbRate()) ? "" : pan.getMbRate()) + ","
							+ (StringTools.isNullOrBlank(pan.getPk()) ? "" : pan.getPk()) + ","
							+ (StringTools.isNullOrBlank(pan.getTgRate()) ? "" : pan.getTgRate()) + "");
					sRate.setHtRefData("大小球:" + (StringTools.isNullOrBlank(pan.getDmbRate()) ? "" : pan.getDmbRate()) + ","
							+ (StringTools.isNullOrBlank(pan.getDpk()) ? "" : pan.getDpk()) + ","
							+ (StringTools.isNullOrBlank(pan.getDtgRate()) ? "" : pan.getDtgRate()) + "#亚赔Bet365:"
							+ (StringTools.isNullOrBlank(pan.getMbRate()) ? "" : pan.getMbRate()) + ","
							+ (StringTools.isNullOrBlank(pan.getPk()) ? "" : pan.getPk()) + ","
							+ (StringTools.isNullOrBlank(pan.getTgRate()) ? "" : pan.getTgRate()));
//					if (comMap.get(match.getMid().toString()) != null) {
//						sRate.setComments(comMap.get(match.getMid().toString()));
//					} else {
//						sRate.setComments("");
//					}
				}else {
					sRate.setZjqRefData("大小球: , , #亚赔Bet365: , , ");
					sRate.setCbfRefData("大小球: , , #亚赔Bet365: , , ");
					sRate.setSpfRefData("投注比例: , , #大小球: , , #亚赔Bet365: , , ");
					sRate.setRspfRefData("投注比例: , , #大小球: , , #亚赔Bet365: , , ");
					sRate.setHtRefData("大小球: , , #亚赔Bet365: , , ");					
				}

				if(record!=null){
					sRate.setComments(String.format("主队近期战绩:%s胜%s平%s负,进球:%s 失球%s 净胜球:%s <br><br> 客队近期战绩:%s胜%s平%s负,进球:%s 失球%s 净胜球:%s",
							new Object[]{record.getMbWin(),record.getMbDraw(),record.getMbLose(),record.getMbInball(),record.getMbMiss(),record.getMbJing(),
							record.getTgWin(),record.getTgDraw(),record.getTgLose(),record.getTgInball(),record.getTgMiss(),record.getTgJing()}));
				}else{
					sRate.setComments("");
				}

				// --------------------------------------------------------------------------------------------
				smallPassRates.add(sRate);
			}
		}

		Map<String, List<InnerPassRate>> map = new TreeMap<String, List<InnerPassRate>>();
		for (int i = 0; i < smallPassRates.size(); i++) {

			InnerPassRate passRate = smallPassRates.get(i);
			// 根据date分组
			if (map.get(passRate.getDate()) == null) {
				List<InnerPassRate> list = new ArrayList<InnerPassRate>();
				list.add(passRate);
				map.put(passRate.getDate(), list);
			} else {
				map.get(passRate.getDate()).add(passRate);
			}
		}

		this.dtMatch.add(map);
		// bigPassRate.setKeys(getKeys(map.keySet().toString()));
		// 设置允许的最大比赛投注倍数
		bigPassRate.setAllowMaxMultiple("999");

	}

	public String getKeys(String keys) {
		String str = keys.substring(1, keys.length() - 1);
		str = str.replaceAll("\\s+", "");

		return str;
	}

	/**
	 * 保留整数
	 * 
	 * @param ratio
	 * @return
	 */
	public static String doRatio(double ratio) {
		DecimalFormat df = new DecimalFormat("0");
		if (ratio == 0.0 || ratio == -1.00)
			return "";
		return df.format(ratio) + "%";
	}

	/**
	 * 保留两位小时百分比
	 * 
	 * @param ratio
	 * @return
	 */
	public static String doRatio2(double ratio) {
		DecimalFormat df = new DecimalFormat("0.00");
		if (ratio == 0.0 || ratio == -1.00)
			return "";
		return df.format(ratio) + "";
	}

	/**
	 * 联赛排名汉字部分只保留第一位
	 * 
	 * @param str
	 * @return
	 */
	public static String trimRank(String str) {
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

	
	
	public void initDtMatch(List<Object[]> objs, OuterPassRate bigPassRate) {
		// TODO Auto-generated method stub
		List<InnerPassRate> smallPassRates = new ArrayList<InnerPassRate>();
		if(objs!=null){
			for (int i = 0; i < objs.size(); i++) {
				Object[] obj = objs.get(i);
				FtMatch match = (FtMatch) obj[0];
				FtBfAward bfAward = (FtBfAward) obj[1];
				FtJqsAward jqsAward = (FtJqsAward) obj[3];
				FtSpfAward spfAward = (FtSpfAward) obj[4];
				InnerPassRate sRate = new InnerPassRate();
				sRate.setMatchId(match.getSqlId().toString());
				sRate.setMid(match.getMid() + "");
				sRate.setMatchNumber(match.getMatchNo());
				sRate.setMatchDate(match.getMatchDate());
				sRate.setGame(match.getLeagueName());
				sRate.setMainTeam(match.getMbName());
				sRate.setGuestTeam(match.getTgName());
				sRate.setStopSellTime(DateTools.dateToString(match.getNosaleTime()));
				sRate.setMainLoseball(Integer.toString(match.getRangBallNum()));
				sRate.setMatchDate1(DateTools.getWeekStr(DateTools.stringToDate(match.getIntTime() + "", "yyyyMMdd")));
				sRate.setWin(doRatio2(spfAward.getHomewinaward()));// 让球
				sRate.setFlat(doRatio2(spfAward.getDrawaward()));
				sRate.setLose(doRatio2(spfAward.getGuestwinaward()));
				// 不让球
				sRate.setSpfwin(doRatio2(spfAward.getNrhomewinaward()));
				sRate.setSpfflat(doRatio2(spfAward.getNrdrawaward()));
				sRate.setSpflose(doRatio2(spfAward.getNrguestwinaward()));
				// 比分
				sRate.setSother(doRatio2(bfAward.getSWinAward()));
				sRate.setS10(doRatio2(bfAward.getS10award()));
				sRate.setS20(doRatio2(bfAward.getS20award()));
				sRate.setS21(doRatio2(bfAward.getS21award()));
				sRate.setS31(doRatio2(bfAward.getS31award()));
				sRate.setS32(doRatio2(bfAward.getS32award()));
				sRate.setS40(doRatio2(bfAward.getS40award()));
				sRate.setS41(doRatio2(bfAward.getS41award()));
				sRate.setS42(doRatio2(bfAward.getS42award()));
				sRate.setS50(doRatio2(bfAward.getS50award()));
				sRate.setS51(doRatio2(bfAward.getS51award()));
				sRate.setS52(doRatio2(bfAward.getS52award()));
				sRate.setS30(doRatio2(bfAward.getS30award()));

				sRate.setPother(doRatio2(bfAward.getSDrawAward()));
				sRate.setP00(doRatio2(bfAward.getS00award()));
				sRate.setP11(doRatio2(bfAward.getS11award()));
				sRate.setP22(doRatio2(bfAward.getS22award()));
				sRate.setP33(doRatio2(bfAward.getS33award()));

				sRate.setFother(doRatio2(bfAward.getSLoseAward()));
				sRate.setF01(doRatio2(bfAward.getS01award()));
				sRate.setF02(doRatio2(bfAward.getS02award()));
				sRate.setF12(doRatio2(bfAward.getS12award()));
				sRate.setF03(doRatio2(bfAward.getS03award()));
				sRate.setF13(doRatio2(bfAward.getS13award()));
				sRate.setF23(doRatio2(bfAward.getS23award()));
				sRate.setF04(doRatio2(bfAward.getS04award()));
				sRate.setF14(doRatio2(bfAward.getS14award()));
				sRate.setF15(doRatio2(bfAward.getS15award()));
				sRate.setF24(doRatio2(bfAward.getS24award()));
				sRate.setF05(doRatio2(bfAward.getS05award()));
				sRate.setF25(doRatio2(bfAward.getS25award()));

				// 进球数
				sRate.setIn0(Double.toString(jqsAward.getS0Award()));
				sRate.setIn1(Double.toString(jqsAward.getS1Award()));
				sRate.setIn2(Double.toString(jqsAward.getS2Award()));
				sRate.setIn3(Double.toString(jqsAward.getS3Award()));
				sRate.setIn4(Double.toString(jqsAward.getS4Award()));
				sRate.setIn5(Double.toString(jqsAward.getS5Award()));
				sRate.setIn6(Double.toString(jqsAward.getS6Award()));
				sRate.setIn7(Double.toString(jqsAward.getS7Award()));

				sRate.setIsSPF(spfAward.getIsHad() == 0 ? "false" : "true");// 是否有胜平负可投
				sRate.setIsZJQ(jqsAward.getIsTrue() == 0 ? "false" : "true");
				sRate.setIsCBF(bfAward.getIsTrue() == 0 ? "false" : "true");
				sRate.setIsNewSPF(spfAward.getIsHhad() == 0 ? "false" : "true");// 是否有让求胜平负可投
				// 新增day
				sRate.setDate(match.getIntTime() + "");

				// --------------------------------------------------------------------------------------------
				smallPassRates.add(sRate);
			}
		}
//		Map<String, List<InnerPassRate>> map = new TreeMap<String, List<InnerPassRate>>();
		Map<String, List<InnerPassRate>> map = new LinkedHashMap<String, List<InnerPassRate>>();
		for (int i = 0; i < smallPassRates.size(); i++) {
		
					InnerPassRate passRate = smallPassRates.get(i);
					// 根据date分组
					if (map.get(passRate.getDate()) == null) {
						List<InnerPassRate> list = new ArrayList<InnerPassRate>();
						list.add(passRate);
						 map.put(passRate.getDate(), list);
					} else {
						map.get(passRate.getDate()).add(passRate);
					}
				}
		this.dtMatch.add(map);
		bigPassRate.setKeys(getKeys(map.keySet().toString()));
//		// 设置允许的最大比赛投注倍数
		bigPassRate.setAllowMaxMultiple("999");
	}


}
