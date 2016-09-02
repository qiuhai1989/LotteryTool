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
import com.yuncai.modules.lottery.sporttery.support.basketball.BasketBallMatchItem;
import com.yuncai.modules.lottery.sporttery.support.basketball.BasketBallTicketModel;

public class BasketBallBingoCheckHT extends SportteryBingoCheckTurbid<BasketBallTicketModel, BasketBallMatchItem> {

	@Override
	public Class<BasketBallTicketModel> getTClass() {
		return BasketBallTicketModel.class;
	}

	@Override
	public Class<BasketBallMatchItem> getXClass() {
		return BasketBallMatchItem.class;
	}
	
	public void execute(String content, PlayType playType, int multiple, HashMap<String, String> openResultMap) {
		BasketBallTicketModel ticketModel = CommonUtils.Object4TikectJsonTurbid(content, getTClass(), getXClass());
		List<BasketBallMatchItem> matchItems =ticketModel.getMatchItems();
		if(ticketModel.getPassMode().equals(PassMode.PASS.ordinal())){
			//混合过关直接处理过关
			boolean hit = true; //先定义好,假设全部命中
			double resultAward =1d;
			int cancelSum = 0; 
			//先判断赛事是否完场次
			for(BasketBallMatchItem f : matchItems){
				try {
					String tempKey = f.getIntTime() + "_" + f.getLineId();
					String tempHomeScore = openResultMap.get(CommonUtils.homeScoreKey + tempKey);
					if (!tempHomeScore.equals("0")) {// 赛事取消
						
					}
				}catch (NullPointerException e) {
					isOpenAble = false;
					break;// 空指针异常，说明选择的赛事中有赛事还未结果
				}
			}
		
			int index=0;
			for(BasketBallMatchItem x : matchItems){
				index++;
				Double tempAward = 0d;
				try {// TODO:赛事取消的情况
					Map<String, String> matchResultMap = new HashMap<String, String>();
					String tempKey = x.getIntTime() + "_" + x.getLineId();
					String tempHomeScore = openResultMap.get(CommonUtils.homeScoreKey + tempKey);
					String tempGuestScore = openResultMap.get(CommonUtils.guestScoreKey + tempKey);
					Double RF = x.getRF();
					Double DXF = x.getDXF();
					// TODO:赛事取消的情况
					if (!tempHomeScore.equals("0")) {
						matchResultMap.put(CommonUtils.homeScoreKey, tempHomeScore);
						matchResultMap.put(CommonUtils.guestScoreKey, tempGuestScore);
						matchResultMap.put(CommonUtils.handicapKey, RF.toString());
						matchResultMap.put(CommonUtils.bigSmallKey, DXF.toString());
						List<SportteryOption> item = getPassOptionItem(x, matchResultMap);
						hit=item.size() <=0 ? false : true;
						if(hit==false)
							break;
						
						for(SportteryOption sportteryOptionHunTou : item){
							tempAward = Double.valueOf(sportteryOptionHunTou.getAward());
							resultAward *= tempAward;
							ticketCountPrize.append(tempAward +"×");
						}
						
					} else {
						resultAward *= x.getOptions().size();
						cancelSum++;
						
						if(index==1){
							this.ticketCountPrize.append(x.getOptions().size()+"×");
						}else{
							this.ticketCountPrize.append(x.getOptions().size()+"×");
						}
					}
				} catch (NullPointerException e) {
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
			}
			
		}
	}
	
	public List<SportteryOption> getPassOptionItem(BasketBallMatchItem matchItem,Map<String, String> resultMap){
		List<SportteryOption> oi = new ArrayList<SportteryOption>();
		List<SportteryOption> options = matchItem.getOptions();
		for(SportteryOption hunTou : options){
			OptionItem item = CommonUtils.getResultOptionItem(hunTou.getType(), resultMap);//结果
			SportteryOption sportteryOption = matchItem.wons(hunTou,item);
			if(sportteryOption != null){
				oi.add(sportteryOption);
			}
		}
		return oi;
	}

	@Override
	public OptionItem getPassOptionItem(String lotteryType, BasketBallMatchItem matchItem, Map<String, String> resultMap) {
		return null;
	}

}
