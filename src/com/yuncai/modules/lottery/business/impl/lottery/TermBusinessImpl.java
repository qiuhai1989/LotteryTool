package com.yuncai.modules.lottery.business.impl.lottery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yuncai.core.common.PrizeBean;
import com.yuncai.core.common.PrizeCommon;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.modules.lottery.business.lottery.TermBusiness;
import com.yuncai.modules.lottery.model.Oracle.BkMatch;
import com.yuncai.modules.lottery.model.Oracle.FtMatch;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Sql.Isuses;
import com.yuncai.modules.lottery.service.oracleService.baskboll.BkMatchService;
import com.yuncai.modules.lottery.service.oracleService.fbmatch.FtMatchService;
import com.yuncai.modules.lottery.sporttery.support.CommonUtils;

public class TermBusinessImpl implements TermBusiness {
	
	private FtMatchService  ftMatchService;

	private BkMatchService bkMatchService;   //篮球赛事
	

	/**
	 * 获取开奖数据进行开奖
	 * @param term
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public HashMap getOpenInfoMapForCheckBingo(Isuses term, LotteryType type,String result,String ResultDetail) throws Exception {
		int lotteryTypeValue = type.getValue();
		HashMap<String, String> openResultMap = new HashMap<String, String>();
		openResultMap.put("lotteryType", lotteryTypeValue + "");
		openResultMap.put("term", term.getName());
		//竞彩足球
		if(lotteryTypeValue>=7201 && lotteryTypeValue<=7207){
			// 查出最近三十天的开奖的数据
			Map<String, Integer> timeMap = CommonUtils.getBgTimeAndEdTime(30);
			Integer bgintTime = timeMap.get(CommonUtils.beginIntTimeKey);
			Integer edintTime = timeMap.get(CommonUtils.endIntTimeKey);
			List<FtMatch> matchList = this.ftMatchService.findMatchListBetweenIntTimes(bgintTime, edintTime);
			List<FtMatch> tempmatchList = new ArrayList<FtMatch>();
			for (FtMatch match : matchList) {//
				//LogUtil.out("------------->>赛事ID："+match.getIntTime()+"周期"+match.getLineId());
				if (match.getStatus()!= 0) {
					tempmatchList.add(match);
				}
			}
			matchList = tempmatchList;
			// 无论是什么玩法，将所需要的参数全部设置进去
			for (FtMatch bm : matchList) {
				String tempKey = bm.getIntTime() + "_" + bm.getLineId();
				LogUtil.out("赛事ID：-----------------"+tempKey);
				Integer homeScore = bm.getMbScoreAll();
				Integer guestScore = bm.getTgScoreAll();
				Integer halfhomeScore = bm.getMbScoreHalf();
				Integer halfGuestScore = bm.getTgScoreHalf();
				if (homeScore == null || guestScore == null) {
					if (bm.getStatus().equals(2)) {// 取消的赛事
						openResultMap.put(CommonUtils.homeScoreKey + tempKey, "-1");
						openResultMap.put(CommonUtils.guestScoreKey + tempKey, "-1");
						openResultMap.put(CommonUtils.resultHalfHomeScore + tempKey, "-1");
						openResultMap.put(CommonUtils.resultHalfGuestScore + tempKey, "-1");
						openResultMap.put(CommonUtils.resultSPFKey + tempKey, "1");
						openResultMap.put(CommonUtils.resultBFKey + tempKey, "1");
						openResultMap.put(CommonUtils.resultJQSKey + tempKey, "1");
						openResultMap.put(CommonUtils.resultBQCKey + tempKey, "1");
						openResultMap.put(CommonUtils.CONCEDEKEY + tempKey, "0");
					} else {
						continue;
					}
				}else{
					String Handicap = "0";
					if (bm.getRangBallNum() != null)
						Handicap = bm.getRangBallNum().toString();// 让球
					Double SPFResult = bm.getWplGold() == null ? 0d : bm.getWplGold();
					Double BFResult = bm.getScoreGold() == null ? 0d : bm.getScoreGold();
					Double JQSResult = bm.getInballGold() == null ? 0d : bm.getInballGold();
					Double BQCResult = bm.getHaGold() == null ? 0d : bm.getHaGold();

					openResultMap.put(CommonUtils.resultHalfHomeScore + tempKey, halfhomeScore.toString());
					openResultMap.put(CommonUtils.resultHalfGuestScore + tempKey, halfGuestScore.toString());
					openResultMap.put(CommonUtils.CONCEDEKEY + tempKey, Handicap);
					openResultMap.put(CommonUtils.homeScoreKey + tempKey, homeScore.toString());
					openResultMap.put(CommonUtils.guestScoreKey + tempKey, guestScore.toString());
					openResultMap.put(CommonUtils.resultSPFKey + tempKey, SPFResult.toString());
					openResultMap.put(CommonUtils.resultBFKey + tempKey, BFResult.toString());
					openResultMap.put(CommonUtils.resultJQSKey + tempKey, JQSResult.toString());
					openResultMap.put(CommonUtils.resultBQCKey + tempKey, BQCResult.toString());
				}
			}
		}else if(lotteryTypeValue == LotteryType.ZCSFC.getValue() || lotteryTypeValue == LotteryType.ZCRJC.getValue()){
			String[] results = result.split(",");
			for (String resultCode : results) {
				if ("".equals(resultCode.trim())) {
					throw new Exception("开奖结果有误!");
				}
			}
		}
		if (!(lotteryTypeValue >=7201 && lotteryTypeValue <=7207)) {
			if (result == null || result.equals("")) {
				throw new Exception("开奖结果未录入!");
			}
			openResultMap.put("result", result);
			// 将奖金描述解析成 List<PrizeBean>
			List<PrizeBean> beanList = PrizeCommon.buildToList(type.getValue(), ResultDetail);
			if (beanList == null || beanList.size() == 0) {
				throw new Exception("奖金解析失败，请联系管理员!");
			}
			if (ResultDetail == null || ResultDetail.equals("")) {
				throw new Exception("开奖奖金未录入!");
			}
			// 奖List<PrizeBean>注入到openResultMap,以供开奖
			for (int i = 0; i < beanList.size(); i++) {
				PrizeBean bean = beanList.get(i);
				openResultMap.put(bean.getPrizeName(), bean.getAmount());
			}
		}
		return openResultMap;
	}
	
	/**
	 * 针对开
	 * @param type
	 * @return
	 */
	public HashMap getOpenInfoTicket(LotteryType type,String intString){
		int lotteryTypeValue = type.getValue();
		
		HashMap<String, String> openResultMap = new HashMap<String, String>();
		openResultMap.put("lotteryType", lotteryTypeValue + "");
		//竞彩足球
		if(LotteryType.JCZQList.contains(type)){
			
			Integer bgintTime =Integer.parseInt(intString.split("\\:")[0]);
			Integer edintTime = Integer.parseInt(intString.split("\\:")[1]);
			List<FtMatch> matchList = this.ftMatchService.findMatchListBetweenIntTimes(bgintTime, edintTime);
			List<FtMatch> tempmatchList = new ArrayList<FtMatch>();
			for (FtMatch match : matchList) {//
				Integer matchStatus=Integer.parseInt(match.getStatus().toString());
				if (matchStatus != 0) {
					tempmatchList.add(match);
				}
			}
			matchList = tempmatchList;
			// 无论是什么玩法，将所需要的参数全部设置进去
			for (FtMatch bm : matchList) {
				String tempKey = bm.getIntTime() + "_" + bm.getLineId();
				
				Integer homeScore=null;
				Integer guestScore=null;
				Integer halfhomeScore=null;
				Integer halfGuestScore=null;
				try{
					homeScore = Integer.parseInt(bm.getMbScoreAll().toString());
				}catch (Exception e) {
					homeScore=null;
				}
				
				try{
					guestScore =Integer.parseInt(bm.getTgScoreAll().toString());
				}catch (Exception e) {
					guestScore = null;
				}
				
				try{
					halfhomeScore =Integer.parseInt(bm.getMbScoreHalf().toString());
				}catch (Exception e) {
					halfhomeScore = null;
				}
				

				try{
					halfGuestScore =Integer.parseInt(bm.getTgScoreHalf().toString());
				}catch (Exception e) {
					halfGuestScore = null;
				}
				
				//暂时修改一下数据
				if (homeScore == null || guestScore == null || homeScore==-1 || guestScore ==-1 
						|| homeScore ==-2 || guestScore ==-2 || homeScore ==-3 || guestScore==-3) {
					if (bm.getStatus().equals(2)) {// 取消的赛事
						
						//LogUtil.out("赛事ID：-----------------"+tempKey +"homeScore:------------"+homeScore); 
						
						openResultMap.put(CommonUtils.homeScoreKey + tempKey, "-1");
						openResultMap.put(CommonUtils.guestScoreKey + tempKey, "-1");
						openResultMap.put(CommonUtils.resultHalfHomeScore + tempKey, "-1");
						openResultMap.put(CommonUtils.resultHalfGuestScore + tempKey, "-1");
						openResultMap.put(CommonUtils.resultSPFKey + tempKey, "1");
						openResultMap.put(CommonUtils.resultRQSPFKey + tempKey, "1");
						openResultMap.put(CommonUtils.resultBFKey + tempKey, "1");
						openResultMap.put(CommonUtils.resultJQSKey + tempKey, "1");
						openResultMap.put(CommonUtils.resultBQCKey + tempKey, "1");
						openResultMap.put(CommonUtils.CONCEDEKEY + tempKey, "0");
					} else {
						continue;
					}
				}else{
					//LogUtil.out("赛事ID：-----------------"+tempKey +"homeScore:------------"+homeScore); 
					
					String Handicap = "0";
					if (bm.getRangBallNum() != null)
						Handicap = bm.getRangBallNum().toString();// 让球
					Double SPFResult = bm.getNwplGold() == null ? 0d : bm.getNwplGold();
					Double BFResult = bm.getScoreGold() == null ? 0d : bm.getScoreGold();
					Double JQSResult = bm.getInballGold() == null ? 0d : bm.getInballGold();
					Double BQCResult = bm.getHaGold() == null ? 0d : bm.getHaGold();
					Double RQSPFResult =bm.getWplGold() ==null ? 0d :bm.getWplGold();

					openResultMap.put(CommonUtils.resultHalfHomeScore + tempKey, halfhomeScore.toString());
					openResultMap.put(CommonUtils.resultHalfGuestScore + tempKey, halfGuestScore.toString());
					openResultMap.put(CommonUtils.CONCEDEKEY + tempKey, Handicap);
					openResultMap.put(CommonUtils.homeScoreKey + tempKey, homeScore.toString());
					openResultMap.put(CommonUtils.guestScoreKey + tempKey, guestScore.toString());
					openResultMap.put(CommonUtils.resultSPFKey + tempKey, SPFResult.toString());
					openResultMap.put(CommonUtils.resultRQSPFKey + tempKey, RQSPFResult.toString());
					openResultMap.put(CommonUtils.resultBFKey + tempKey, BFResult.toString());
					openResultMap.put(CommonUtils.resultJQSKey + tempKey, JQSResult.toString());
					openResultMap.put(CommonUtils.resultBQCKey + tempKey, BQCResult.toString());
				}
			}
		}else if(LotteryType.JCLQList.contains(type)){
			Integer bgintTime =Integer.parseInt(intString.split("\\:")[0]);
			Integer edintTime = Integer.parseInt(intString.split("\\:")[1]);
			List<BkMatch> matchList = this.bkMatchService.findMatchListBetweenIntTimes(bgintTime, edintTime);
			List<BkMatch> tempmatchList = new ArrayList<BkMatch>();
			for (BkMatch match : matchList) {//
				if (match.getStatus().intValue() != 0) {
					tempmatchList.add(match);
				}
			}
			matchList = tempmatchList;
			// 无论是什么玩法，将所需要的参数全部设置进去
			for (BkMatch bm : matchList) {
				String tempKey = bm.getIntTime() + "_" + bm.getLineId();
				
				Integer homeScore = null;
				Integer guestScore = null;
				
				try{
					homeScore = Integer.parseInt(bm.getMbScore().toString());
				}catch (Exception e) {
					homeScore=null;
				}
				try{
					guestScore = Integer.parseInt(bm.getTgScore().toString());
				}catch (Exception e) {
					homeScore=null;
				}
				if (homeScore == null || guestScore == null || homeScore==-1 || guestScore ==-1 
						|| homeScore ==-2 || guestScore ==-2 || homeScore ==-3 || guestScore==-3) {
					if (bm.getStatus().equals(2)) {// 取消的赛事
						openResultMap.put(CommonUtils.homeScoreKey + tempKey, "0");
						openResultMap.put(CommonUtils.guestScoreKey + tempKey, "0");
						openResultMap.put(CommonUtils.handicapKey + tempKey, "0");
						openResultMap.put(CommonUtils.bigSmallKey + tempKey, "0");
						openResultMap.put(CommonUtils.resultSFKey + tempKey, "1");
						openResultMap.put(CommonUtils.resultSFCKey + tempKey, "1");
						openResultMap.put(CommonUtils.resultRFSFKey + tempKey, "1");
						openResultMap.put(CommonUtils.resultDXFKey + tempKey, "1");
					} else {
						continue;
					}
				} else {
					String Handicap = "0";
					Double SFResult = bm.getSfGold() == null ? 0d : bm.getSfGold();
					Double RFSFResult = bm.getRsfGold() == null ? 0d : bm.getRsfGold();
					Double DXFResult = bm.getDxGold() == null ? 0d : bm.getDxGold();
					Double SFCResult = bm.getSfcGold() == null ? 0d : bm.getSfcGold();
					Double bigSmallBase =0d;
					openResultMap.put(CommonUtils.homeScoreKey + tempKey, homeScore.toString());
					openResultMap.put(CommonUtils.guestScoreKey + tempKey, guestScore.toString());
					openResultMap.put(CommonUtils.handicapKey + tempKey, Handicap);
					openResultMap.put(CommonUtils.bigSmallKey + tempKey, bigSmallBase.toString());
					openResultMap.put(CommonUtils.resultSFKey + tempKey, SFResult.toString());
					openResultMap.put(CommonUtils.resultSFCKey + tempKey, SFCResult.toString());
					openResultMap.put(CommonUtils.resultRFSFKey + tempKey, RFSFResult.toString());
					openResultMap.put(CommonUtils.resultDXFKey + tempKey, DXFResult.toString());
				}
			}
		}
		
		return openResultMap;
	}
	

	public void setFtMatchService(FtMatchService ftMatchService) {
		this.ftMatchService = ftMatchService;
	}
	
	
	public void setBkMatchService(BkMatchService bkMatchService) {
		this.bkMatchService = bkMatchService;
	}
	

}
