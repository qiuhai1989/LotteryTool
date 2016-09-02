package com.yuncai.modules.lottery.factorys.ticketBingo;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yuncai.modules.lottery.model.Oracle.toolType.PlayType;
import com.yuncai.modules.lottery.sporttery.OptionItem;
import com.yuncai.modules.lottery.sporttery.support.CommonUtils;
import com.yuncai.modules.lottery.sporttery.support.PassMode;
import com.yuncai.modules.lottery.sporttery.support.SportteryOption;
import com.yuncai.modules.lottery.sporttery.support.football.FootBallMatchTurbidItem;
import com.yuncai.modules.lottery.sporttery.support.football.FootballTicketModelTurbid;

/**
 * 竞彩混串开奖算法
 * @author Administrator
 *
 */
public class SportteryFootballBinggoCheckTurbid extends SportteryBingoCheckTurbid<FootballTicketModelTurbid, FootBallMatchTurbidItem> {


	public void execute(String content, PlayType playType, int multiple, HashMap<String, String> openResultMap) {
		FootballTicketModelTurbid ticketModel = CommonUtils.Object4TikectJsonTurbid(content, getTClass(), getXClass());
		List<FootBallMatchTurbidItem> matchItems = ticketModel.getMatchItems();
		if (ticketModel.getPassMode().equals(PassMode.PASS.ordinal())){// 处理过关
			boolean hit = true;// 先定义好，假设全部命中
			double resultAward = 1d;
			int cancelSum = 0;
			//先得判断赛事是否完场
			for (FootBallMatchTurbidItem f : matchItems) {
				try {
					String tempKey = f.getIntTime() + "_" + f.getLineId();
					String tempHomeScore = openResultMap.get(CommonUtils.homeScoreKey + tempKey);
					if(!tempHomeScore.equals("-1"))
					{
						
					}
						
				}catch (NullPointerException e) {
					hit = false;
					isOpenAble = false;
					break;// 空指针异常，说明选择的赛事中有赛事还未结果
				}
			}
			
			for (FootBallMatchTurbidItem x : matchItems) {
				try {
					Map<String, String> matchResultMap = new HashMap<String, String>();
					String tempKey = x.getIntTime() + "_" + x.getLineId();
					String tempHomeScore = openResultMap.get(CommonUtils.homeScoreKey + tempKey);
					String tempGuestScore = openResultMap.get(CommonUtils.guestScoreKey + tempKey);
					String tempHalfHomeScore = openResultMap.get(CommonUtils.resultHalfHomeScore + tempKey);
					String tempHalfGuestScore = openResultMap.get(CommonUtils.resultHalfGuestScore + tempKey);
					String tempRq = openResultMap.get(CommonUtils.CONCEDEKEY + tempKey);
					if (!tempHomeScore.equals("-1")) {
						matchResultMap.put(CommonUtils.homeScoreKey, tempHomeScore);
						matchResultMap.put(CommonUtils.guestScoreKey, tempGuestScore);
						matchResultMap.put(CommonUtils.resultHalfHomeScore, tempHalfHomeScore);
						matchResultMap.put(CommonUtils.resultHalfGuestScore, tempHalfGuestScore);
						matchResultMap.put(CommonUtils.CONCEDEKEY, tempRq);
						List<SportteryOption> item = getPassOptionItem(x, matchResultMap);
						hit = item.size()<=0 ? false : true;
						if (hit == false)
							break;
						
						for(SportteryOption sportteryOptionHunTou : item){
							Double tempAward = Double.valueOf(sportteryOptionHunTou.getAward());
							resultAward *= tempAward;
							ticketCountPrize.append(tempAward +"×");
						}
						
					} else {// TODO:赛事取消的情况
						resultAward *= x.getOptions().size();
						cancelSum++;
						this.ticketCountPrize.append(x.getOptions().size()+"×");
					}
				} catch (NullPointerException e) {
					//e.printStackTrace();
					hit = false;
					isOpenAble = false;
					break;
				}
			}
			if (hit) {
				resultAward *= 2;// 乘以2
				Double tempAward = Double.valueOf(DF.format(resultAward));
				int passCount = matchItems.size() - cancelSum;
				if (tempAward > 200000) {// 如果金额超过20万:一般都很难所以这里以金额做判断
					if (passCount <= 3) {
						tempAward = 200000d;
					} else if (4 <= passCount && passCount <= 5) {
						tempAward = tempAward > 500000 ? 500000d : tempAward;
					} else if (6 <= passCount) {
						tempAward = tempAward > 1000000 ? 1000000d : tempAward;
					}
				}
				// 税前奖金
				bingoPretaxTotal += Double.valueOf(tempAward * ticketModel.getMultiple());
				// 税后
				if (tempAward > 10000 && passCount > 0)// 所有的赛事都取消不算税
					tempAward *= 0.8;
				bingoPosttaxTotal += Double.valueOf(DF.format(tempAward * ticketModel.getMultiple()));
				bingoHashMap.put(matchItems.size() + "串1", 1l);
				isBingo = true;
				prizeHashMap.put(matchItems.size() + "串1", bingoPosttaxTotal);// 过关的奖金加入
				ticketCountPrize.append("2×"+ticketModel.getMultiple()+"=" + bingoPosttaxTotal);
//				//加奖操作 仅2串1且单票奖金大于或等于400 
//				if(matchItems.size()==2&&bingoPretaxTotal>=400){
//					bingoHashMap.put("2串1加奖", 1l);
//					prizeHashMap.put("2串1加奖", 50d);
//					bingoPosttaxTotal+=50d;
//					bingoPretaxTotal+=50d;
//				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<SportteryOption> getPassOptionItem(FootBallMatchTurbidItem matchItem, Map<String, String> resultMap) {
		List<SportteryOption> oi = new ArrayList<SportteryOption>();
		List<SportteryOption> options = matchItem.getOptions();
		for(SportteryOption hunTou : options){
				OptionItem item = CommonUtils.getZQResultOptionItem(hunTou.getType(), resultMap);//结果
				SportteryOption sportteryOption = matchItem.wons(hunTou,item);
				if(sportteryOption != null){
					oi.add(sportteryOption);
				}
		}
		return oi;
	}
	
	@Override
	public OptionItem getPassOptionItem(String lotteryType, FootBallMatchTurbidItem matchItem, Map<String, String> resultMap) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Class<FootballTicketModelTurbid> getTClass() {
		return FootballTicketModelTurbid.class;
	}

	@Override
	public Class<FootBallMatchTurbidItem> getXClass() {
		return FootBallMatchTurbidItem.class;
	}
}
