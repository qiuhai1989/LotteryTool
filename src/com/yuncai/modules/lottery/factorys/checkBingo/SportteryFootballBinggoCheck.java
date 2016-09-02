package com.yuncai.modules.lottery.factorys.checkBingo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yuncai.core.tools.LogUtil;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlayType;
import com.yuncai.modules.lottery.sporttery.OptionItem;
import com.yuncai.modules.lottery.sporttery.support.CommonUtils;
import com.yuncai.modules.lottery.sporttery.support.PassMode;
import com.yuncai.modules.lottery.sporttery.support.SportteryOption;
import com.yuncai.modules.lottery.sporttery.support.football.FootBallMatchItem;
import com.yuncai.modules.lottery.sporttery.support.football.FootballTicketModel;

public class SportteryFootballBinggoCheck extends SportteryBingoCheck<FootballTicketModel, FootBallMatchItem>  {
	@Override
	public OptionItem getPassOptionItem(String lotteryType, FootBallMatchItem matchItem, Map<String, String> resultMap) {
		return CommonUtils.getZQResultOptionItem(lotteryType, resultMap);
	}
 
	public void execute(String content, PlayType playType, int multiple, HashMap<String, String> openResultMap) {
		FootballTicketModel ticketModel = CommonUtils.Object4TikectJson(content, getTClass(), getXClass());
		List<FootBallMatchItem> matchItems = ticketModel.getMatchItems();
		String type = openResultMap.get("lotteryType");
		if (ticketModel.getPassMode().equals(PassMode.SINGLE.ordinal())) {// 处理单关
			// 根据玩法取得比赛的选项
			Double resultAward = 0d;
			for (FootBallMatchItem x : matchItems) {
				Double tempAward = 0d;
				try {
					Map<String, String> matchResultMap = new HashMap<String, String>();
					String tempKey = x.getIntTime() + "_" + x.getLineId();
					String tempHomeScore = openResultMap.get(CommonUtils.homeScoreKey + tempKey);
					String tempGuestScore = openResultMap.get(CommonUtils.guestScoreKey + tempKey);
					String tempHalfHomeScore = openResultMap.get(CommonUtils.resultHalfHomeScore + tempKey);
					String tempHalfGuestScore = openResultMap.get(CommonUtils.resultHalfGuestScore + tempKey);
					String tempRq = openResultMap.get(CommonUtils.CONCEDEKEY + tempKey);

					matchResultMap.put(CommonUtils.homeScoreKey, tempHomeScore);
					matchResultMap.put(CommonUtils.guestScoreKey, tempGuestScore);
					matchResultMap.put(CommonUtils.resultHalfHomeScore, tempHalfHomeScore);
					matchResultMap.put(CommonUtils.resultHalfGuestScore, tempHalfGuestScore);
					matchResultMap.put(CommonUtils.CONCEDEKEY, tempRq);

					String tempSPFAward = openResultMap.get(CommonUtils.resultSPFKey + tempKey);
					String tempBFAward = openResultMap.get(CommonUtils.resultBFKey + tempKey);
					String tempJQSAward = openResultMap.get(CommonUtils.resultJQSKey + tempKey);
					String tempBQCward = openResultMap.get(CommonUtils.resultBQCKey + tempKey);

					Double SPFAward = Double.valueOf(tempSPFAward);// 胜平负
					//Double BFAward = Double.valueOf(tempBFAward);// 比分
					Double JQSAward = Double.valueOf(tempJQSAward);// 进球数
					Double BQCward = Double.valueOf(tempBQCward);// 半全场
					if (tempHomeScore.equals("-1")) {// 赛事取消
						tempAward = 2d * x.getOptions().size();
					} else {
						OptionItem option = CommonUtils.getZQResultOptionItem(type, matchResultMap);
						//LogUtil.out("------item:"+Double.valueOf(option.getValue()));
						SportteryOption winOption=x.won(option);
						boolean isWon = winOption == null ? false : true;// 是否中奖
						if (!isWon)
							continue;
						//LogUtil.out("------sp:"+Double.valueOf(winOption.getAward()));
						// 单关直接就是每注的奖金值
						if (type.equals("7207")) {
							tempAward = SPFAward;
						} else if (type.equals("7202")) {
							//竟彩足球比分玩法从浮动奖改为固定奖
							tempAward = winOption.getAward()*2;
						} else if (type.equals("7203")) {
							tempAward = JQSAward;
						} else if (type.equals("7204")) {
							tempAward = BQCward;
						}
						if (tempAward.intValue() == 0&&!type.equals("7202")) {
							// 结果出来但是sp值未出来
							// 比分玩法的浮动奖已废弃
							isOpenAble = false;
							break;
						}
					}
					if (tempAward < 2)
						tempAward = 2d;// 不足两元补足两元
					resultAward += Double.valueOf(DF.format(tempAward));
					isBingo = true;
					if (bingoHashMap.get("单关") != null)
						bingoHashMap.put("单关:", bingoHashMap.get("单关") + 1);
					else
						bingoHashMap.put("单关:", 1l);
				} catch (NullPointerException e) {
					isOpenAble = false;
					break;// 空指针异常，说明选择的赛事中有赛事还未结果
				}
			}
			// 计算奖金
			bingoPretaxTotal += Double.valueOf(DF.format(resultAward * ticketModel.getMultiple()));
			if (resultAward > 10000) {// 税后
				resultAward *= 0.8d;
			}
			bingoPosttaxTotal += Double.valueOf(DF.format(resultAward * ticketModel.getMultiple()));
			prizeHashMap.put("单关:", bingoPosttaxTotal);// 单关的奖金加入
		} else {// 处理过关
			boolean hit = true;// 先定义好，假设全部命中
			double resultAward = 1d;
			int cancelSum = 0;
			for (FootBallMatchItem x : matchItems) {
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
						OptionItem item = getPassOptionItem(type, x, matchResultMap);
						SportteryOption wonItem = x.won(item);
						//LogUtil.out("------item:"+Double.valueOf(item.getValue()));
						hit = wonItem == null ? false : true;
						if (hit == false)
							break;
						//LogUtil.out("------sp:"+Double.valueOf(wonItem.getAward()));
						Double tempAward = Double.valueOf(wonItem.getAward());
						resultAward *= tempAward;
					} else {// TODO:赛事取消的情况
						resultAward *= x.getOptions().size();
						cancelSum++;
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
				
				
			}
		}
	}

	@Override
	public Class<FootballTicketModel> getTClass() {
		return FootballTicketModel.class;
	}

	@Override
	public Class<FootBallMatchItem> getXClass() {
		return FootBallMatchItem.class;
	}


}
