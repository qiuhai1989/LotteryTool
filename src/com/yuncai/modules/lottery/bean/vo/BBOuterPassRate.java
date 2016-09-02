package com.yuncai.modules.lottery.bean.vo;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.yuncai.core.tools.DateTools;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.core.tools.StringTools;
import com.yuncai.core.util.Strings;
import com.yuncai.modules.lottery.model.Oracle.BkBetRateRatio;
import com.yuncai.modules.lottery.model.Oracle.BkImsMatch500;
import com.yuncai.modules.lottery.model.Oracle.BkMatch;
import com.yuncai.modules.lottery.model.Oracle.BkMatchBet365;
import com.yuncai.modules.lottery.model.Oracle.BkSpRate;
import com.yuncai.modules.lottery.model.Sql.SPYaPan;
import com.yuncai.modules.lottery.service.oracleService.bkmatch.BkBetRateRatioService;
import com.yuncai.modules.lottery.service.oracleService.bkmatch.BkImsMatch500Service;
import com.yuncai.modules.lottery.service.oracleService.bkmatch.BkMatchBet365Service;

/**组装篮球对阵数据外层
 * @author Administrator
 *
 */
public class BBOuterPassRate {
	private String error;
	private String msg;
	private String serverTime;
	private String keys;
	private String allowMaxMultiple;
	// map("20131006",List<InnerPassRate>)
	private List<Map<String, List<BBInnerPassRate>>> dtMatch = new ArrayList<Map<String, List<BBInnerPassRate>>>();

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

	public List<Map<String, List<BBInnerPassRate>>> getDtMatch() {
		return dtMatch;
	}

	public void setDtMatch(List<Map<String, List<BBInnerPassRate>>> dtMatch) {
		this.dtMatch = dtMatch;
	}

	public void initDtMatch(List<Object[]> lists, BBOuterPassRate bigPassRate, BkMatchBet365Service bkMatchBet365Service,
			BkBetRateRatioService bkBetRateRatioService,BkImsMatch500Service bkImsMatch500Service) {
		List<BBInnerPassRate> smallPassRates = new ArrayList<BBInnerPassRate>();
		List<Integer> midList = new ArrayList<Integer>();
		try {
			if (lists != null) {

				for (int i = 0; i < lists.size(); i++) {
					Object[] obj = lists.get(i);
					BkMatch match = (BkMatch) obj[1];
					midList.add(match.getMid());
				}
			//	LogUtil.out(midList.toString());
				// 根据mid查找亚盘 ,大小球 部分比赛可能无数据
				List<BkMatchBet365> spYapList = null;
				if(midList==null||midList.size()==0){
					spYapList=new ArrayList<BkMatchBet365>();
				}else{
					 spYapList = bkMatchBet365Service.findByProperty("mid", midList);
				}
				
				Map<String, BkMatchBet365> bkBetMap = new HashMap<String, BkMatchBet365>();
				for (BkMatchBet365 pan : spYapList) {
					bkBetMap.put(pan.getMid().toString(), pan);

				}
				
				Map<String,Object>forPercentMap = new HashMap<String, Object>();
				
				//根据mid 查找投注比例
				List<BkBetRateRatio>bkPercentList = new ArrayList<BkBetRateRatio>();
				if(midList.size()>0){
					bkPercentList = bkBetRateRatioService.findByProperty("mid", midList);
				}
				
				for(BkBetRateRatio bkBetRateRatio:bkPercentList){
					forPercentMap.put(bkBetRateRatio.getMid().toString(), bkBetRateRatio);
				}
				
				Map<String,Object>forTeamRankMap = new HashMap<String, Object>();
				//根据mid 查找主客队排名
				List<BkImsMatch500>teamRankList = new ArrayList<BkImsMatch500>();
				if(midList.size()>0){
					teamRankList = bkImsMatch500Service.findByProperty("mid", midList);
				}
				for(BkImsMatch500 teamRank:teamRankList){
					forTeamRankMap.put(Integer.toString(teamRank.getMid()), teamRank);
				}
				
				for (int i = 0; i < lists.size(); i++) {
					Object[] obj = lists.get(i);
					BkSpRate rate = (BkSpRate) obj[0];
					BkMatch match = (BkMatch) obj[1];
					//				BkBetRateRatio bkrate = (BkBetRateRatio) obj[2];
					BBInnerPassRate sRate = new BBInnerPassRate();
					sRate.setMatchId(match.getSqlId().toString());
					sRate.setUrl("");
					sRate.setMatchNumber(match.getMatchNo());
					sRate.setMatchDate(match.getMatchDate());
					sRate.setMatchDate1(DateTools.getWeekStr(DateTools.stringToDate(rate.getIntTime().toString(), "yyyyMMdd")));
					sRate.setMid(match.getMid().toString());
					sRate.setIntTime(match.getIntTime().toString());
					sRate.setLineId(match.getLineId().toString());
					sRate.setGame(match.getLeagueName());
					sRate.setMainTeam(match.getMbName());
					sRate.setGuestTeam(match.getTgName());
					sRate.setStopSellTime(DateTools.dateToString(match.getNosaleTime()));
					sRate.setRangScore(rate.getRangScore() + "");
					sRate.setRsfWinRate(Double.toString(rate.getRsfWinRate()));
					sRate.setRsfLoseRate(Double.toString(rate.getRsfloseRate()));
					sRate.setSfWinRate(Double.toString(rate.getSfWinRate()));
					sRate.setSfLoseRate(Double.toString(rate.getSfloseRate()));
					sRate.setSfcMbRate1_5(Double.toString(rate.getSfcMbRate1_5()));
					sRate.setSfcMbRate6_10(Double.toString(rate.getSfcMbRate6_10()));
					sRate.setSfcMbRate11_15(Double.toString(rate.getSfcMbRate11_15()));
					sRate.setSfcMbRate16_20(Double.toString(rate.getSfcMbRate16_20()));
					sRate.setSfcMbRate21_25(Double.toString(rate.getSfcMbRate21_25()));
					sRate.setSfcMbRate26(Double.toString(rate.getSfcMbRate26()));
					sRate.setSfcTgRate1_5(Double.toString(rate.getSfcTgRate1_5()));
					sRate.setSfcTgRate6_10(Double.toString(rate.getSfcTgRate6_10()));
					sRate.setSfcTgRate11_15(Double.toString(rate.getSfcTgRate11_15()));
					sRate.setSfcTgRate16_20(Double.toString(rate.getSfcTgRate16_20()));
					sRate.setSfcTgRate21_25(Double.toString(rate.getSfcTgRate21_25()));
					sRate.setSfcTgRate26(Double.toString(rate.getSfcTgRate26()));
					sRate.setDxfBigRate(Double.toString(rate.getDxfBigRate()));
					sRate.setDxfSmallRate(Double.toString(rate.getDxfSmallRate()));
					sRate.setDxScore(Double.toString(rate.getDxScore()));
					
					if(forTeamRankMap.get(match.getMid().toString())!=null){
						BkImsMatch500 rank = (BkImsMatch500) (forTeamRankMap.get(match.getMid().toString()));
						sRate.setMRank(StringTools.isNullOrBlank(rank.getMbRank())?"":trimRank(rank.getMbRank()) );
						sRate.setGRank(StringTools.isNullOrBlank(rank.getTgRank())?"":trimRank(rank.getTgRank()));
					}else{
						sRate.setMRank("");
						sRate.setGRank("");
					}


//					sRate.setSfRefData("");
//					sRate.setRsfRefData("");
//					sRate.setSfcRefData("");
//					sRate.setDxfRefData("");

					sRate.setIsSF(rate.getIsSF() == 0 ? "false" : "true");
					sRate.setIsRFSF(rate.getIsRFSF() == 0 ? "false" : "true");
					sRate.setIsSFC(rate.getIsSF() == 0 ? "false" : "true");
					sRate.setIsDX(rate.getIsDX() == 0 ? "false" : "true");
					// 处理亚盘,大小球数据
					BkMatchBet365 pan = bkBetMap.get(match.getMid().toString());
					BkBetRateRatio ratio = (BkBetRateRatio) forPercentMap.get(match.getMid().toString());
					if (pan != null&&ratio!=null) {
						sRate.setSfRefData("投注比例:主负" + (StringTools.isNullOrBlank(ratio.getSfGuestPercent()) ? "" : ratio.getSfGuestPercent())  + ",主胜" + (StringTools.isNullOrBlank(ratio.getSfMainPercent()) ? "" : ratio.getSfMainPercent()) + "#大小球:"
								+ (StringTools.isNullOrBlank(pan.getDtgRate()) ? "" : pan.getDtgRate()) + ","
								+ (StringTools.isNullOrBlank(pan.getDpk()) ? "" : pan.getDpk()) + ","
								+ (StringTools.isNullOrBlank(pan.getDmbRate()) ? "" : pan.getDmbRate()) + "#亚赔Bet365:"
								+ (StringTools.isNullOrBlank(pan.getYtgRate()) ? "" : pan.getYtgRate()) + ","
								+ (StringTools.isNullOrBlank(pan.getYpk()) ? "" : pan.getYpk()) + ","
								+ (StringTools.isNullOrBlank(pan.getYmbRate()) ? "" : pan.getYmbRate()) + "");
						sRate.setRsfRefData("投注比例:主负" + (StringTools.isNullOrBlank(ratio.getRfsfGuestPercent()) ? "" : ratio.getRfsfGuestPercent())  + ",主胜" + (StringTools.isNullOrBlank(ratio.getRfsfMainPercent()) ? "" : ratio.getRfsfMainPercent()) + "#大小球:"
								+ (StringTools.isNullOrBlank(pan.getDtgRate()) ? "" : pan.getDtgRate()) + ","
								+ (StringTools.isNullOrBlank(pan.getDpk()) ? "" : pan.getDpk()) + ","
								+ (StringTools.isNullOrBlank(pan.getDmbRate()) ? "" : pan.getDmbRate()) + "#亚赔Bet365:"
								+ (StringTools.isNullOrBlank(pan.getYtgRate()) ? "" : pan.getYtgRate()) + ","
								+ (StringTools.isNullOrBlank(pan.getYpk()) ? "" : pan.getYpk()) + ","
								+ (StringTools.isNullOrBlank(pan.getYmbRate()) ? "" : pan.getYmbRate()) + "");
						sRate.setSfcRefData(""  + "大小球:"
								+ (StringTools.isNullOrBlank(pan.getDtgRate()) ? "" : pan.getDtgRate()) + ","
								+ (StringTools.isNullOrBlank(pan.getDpk()) ? "" : pan.getDpk()) + ","
								+ (StringTools.isNullOrBlank(pan.getDmbRate()) ? "" : pan.getDmbRate()) + "#亚赔Bet365:"
								+ (StringTools.isNullOrBlank(pan.getYtgRate()) ? "" : pan.getYtgRate()) + ","
								+ (StringTools.isNullOrBlank(pan.getYpk()) ? "" : pan.getYpk()) + ","
								+ (StringTools.isNullOrBlank(pan.getYmbRate()) ? "" : pan.getYmbRate()) + "");
						sRate.setDxfRefData("投注比例:大" + (StringTools.isNullOrBlank(ratio.getDxfdPercent()) ? "" : ratio.getDxfdPercent())  + ",小" + (StringTools.isNullOrBlank(ratio.getDxfxPercent()) ? "" : ratio.getDxfxPercent()) + "#大小球:"
								+ (StringTools.isNullOrBlank(pan.getDtgRate()) ? "" : pan.getDtgRate()) + ","
								+ (StringTools.isNullOrBlank(pan.getDpk()) ? "" : pan.getDpk()) + ","
								+ (StringTools.isNullOrBlank(pan.getDmbRate()) ? "" : pan.getDmbRate()) + "#亚赔Bet365:"
								+ (StringTools.isNullOrBlank(pan.getYtgRate()) ? "" : pan.getYtgRate()) + ","
								+ (StringTools.isNullOrBlank(pan.getYpk()) ? "" : pan.getYpk()) + ","
								+ (StringTools.isNullOrBlank(pan.getYmbRate()) ? "" : pan.getYmbRate()) + "");

						sRate.setHtRefData(""  + "大小球:"
								+ (StringTools.isNullOrBlank(pan.getDtgRate()) ? "" : pan.getDtgRate()) + ","
								+ (StringTools.isNullOrBlank(pan.getDpk()) ? "" : pan.getDpk()) + ","
								+ (StringTools.isNullOrBlank(pan.getDmbRate()) ? "" : pan.getDmbRate()) + "#亚赔Bet365:"
								+ (StringTools.isNullOrBlank(pan.getYtgRate()) ? "" : pan.getYtgRate()) + ","
								+ (StringTools.isNullOrBlank(pan.getYpk()) ? "" : pan.getYpk()) + ","
								+ (StringTools.isNullOrBlank(pan.getYmbRate()) ? "" : pan.getYmbRate()) + "");
					
					
					}else if(ratio!=null){


						sRate.setSfRefData(String.format("投注比例:主负%s,主胜%s#大小球:,,#亚赔Bet365:,,",(StringTools.isNullOrBlank(ratio.getSfGuestPercent()) ? "" : ratio.getSfGuestPercent()), (StringTools.isNullOrBlank(ratio.getSfMainPercent()) ? "" : ratio.getSfMainPercent()))  );
						sRate.setRsfRefData(String.format("投注比例:主负%s,主胜%s#大小球:,,#亚赔Bet365:,,",(StringTools.isNullOrBlank(ratio.getRfsfGuestPercent()) ? "" : ratio.getRfsfGuestPercent()), (StringTools.isNullOrBlank(ratio.getRfsfMainPercent()) ? "" : ratio.getRfsfMainPercent()))  );
						sRate.setSfcRefData("");
						sRate.setDxfRefData(String.format("投注比例:大%s,小%s#大小球:,,#亚赔Bet365:,,",(StringTools.isNullOrBlank(ratio.getDxfdPercent()) ? "" : ratio.getDxfdPercent()), (StringTools.isNullOrBlank(ratio.getDxfxPercent()) ? "" : ratio.getDxfxPercent()))  );

						sRate.setHtRefData("");
					
					
											
					}else{
						sRate.setSfRefData("");
						sRate.setRsfRefData("");
						sRate.setSfcRefData("");
						sRate.setDxfRefData("");
						sRate.setHtRefData("");
					}
					smallPassRates.add(sRate);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		Map<String, List<BBInnerPassRate>> map = new TreeMap<String, List<BBInnerPassRate>>();
		for (int i = 0; i < smallPassRates.size(); i++) {

			BBInnerPassRate passRate = smallPassRates.get(i);
			// 根据date分组
			if (map.get(passRate.getIntTime()) == null) {
				List<BBInnerPassRate> list = new ArrayList<BBInnerPassRate>();
				list.add(passRate);
				map.put(passRate.getIntTime(), list);
			} else {
				map.get(passRate.getIntTime()).add(passRate);
			}
		}
		this.dtMatch.add(map);
//		bigPassRate.setKeys(getKeys(map.keySet().toString()));
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
}
