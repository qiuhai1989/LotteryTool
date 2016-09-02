package com.yuncai.modules.lottery.factorys.verify;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yuncai.core.longExce.WebDataException;
import com.yuncai.core.tools.DateTools;
import com.yuncai.modules.lottery.factorys.chaipiao.SportteryBasketChaiPiao;
import com.yuncai.modules.lottery.model.Oracle.BkMatch;
import com.yuncai.modules.lottery.model.Oracle.LotteryPlan;
import com.yuncai.modules.lottery.model.Oracle.toolType.MatchStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlayType;
import com.yuncai.modules.lottery.model.Oracle.toolType.SelectType;
import com.yuncai.modules.lottery.service.oracleService.baskboll.BkMatchService;
import com.yuncai.modules.lottery.sporttery.model.baskball.BasketBallBetContent;
import com.yuncai.modules.lottery.sporttery.model.baskball.BasketBallBetFilterContent;
import com.yuncai.modules.lottery.sporttery.support.CommonUtils;
import com.yuncai.modules.lottery.sporttery.support.SportteryPassType;
import com.yuncai.modules.lottery.sporttery.support.basketball.BasketBallMatchItem;


public class SportteryBaskbollVerfiyParser extends SportteryBaskParser<BasketBallBetContent, BasketBallMatchItem>{

	private BkMatchService bkMatchService;
	

	public BkMatchService getBkMatchService() {
		return bkMatchService;
	}

	public void setBkMatchService(BkMatchService bkMatchService) {
		this.bkMatchService = bkMatchService;
	}

	@Override
	public BkMatchService getSportteryService() {
		return bkMatchService;
	}
	
	@Override
	public ContentCheck checkPlan(String content, double amount) throws Exception {
		// TODO Auto-generated method stub
		BasketBallBetFilterContent betContent = null;
		if (content == null || "".equals(content))
			throw new WebDataException("方案内容不能为空");

		betContent = CommonUtils.Object4JsonTurbid(content, BasketBallBetFilterContent.class, getMatchItemClass());
		if (betContent.getContentList() == null) {// 不是过滤投注
			return super.checkPlan(content, amount);
		}
		Date firstTime = null;
		Date lateTime = null;
		List<BasketBallMatchItem> matchItems = betContent.getMatchItems();
		List<SportteryPassType> passType = betContent.getPassTypes();
		if (matchItems.isEmpty())
			throw new WebDataException("您没有选择任何场次");
		if (passType.isEmpty())
			throw new WebDataException("您没有选择过关方式");
		if (passType.size() > 1)
			throw new WebDataException("过滤投注暂不支持多个过关方式");
		if (betContent.getMultiple() < 1)
			throw new WebDataException("倍数不允许小于1");

		// 检查对阵是否已经截止
		List<BkMatch> dbMatchs = getSportteryService().findMatchByStatus(MatchStatus.ONSALE.ordinal());
		Map<String, BkMatch> tempMatchs = new HashMap<String, BkMatch>();// 根据截止时间重新构建一个
		for (BkMatch match : dbMatchs) {// 将数据库中的对阵遍历出来，组装MAP，与投注页面对阵比较
			tempMatchs.put(match.getIntTime() + "_" + match.getLineId(), match);
		}
		for (BasketBallMatchItem item : matchItems) {
			String key = item.getIntTime() + "_" + item.getLineId();

			BkMatch m = tempMatchs.get(key);
			if (m == null) {
				String weekStr = DateTools.getWeekStr(DateTools.StringToDate(item.getIntTime() + "", "yyyyMMdd"));
				weekStr +=item.genMatchLined();
				throw new WebDataException("您选择的场次中有取消或延期赛事："+weekStr);
			} else {// 放入比赛时间，最早的
				if (firstTime == null || firstTime.getTime() > m.getStartTime().getTime()) {
					firstTime = m.getStartTime();
				}
				

				//最晚载止时间,用于方案截止后公开
				if (lateTime == null || lateTime.getTime() < m.getStartTime().getTime()) {
					lateTime = m.getStartTime();
				}
			}
		}
		List<String> contentList = betContent.getContentList();
		for (String tempContent : contentList) {
			String[] comtentElement = tempContent.split(",");
			if (comtentElement.length != matchItems.size()) {
				throw new WebDataException("投注内容格式出错，请联系管理员");
			}
		}
		int tempBetCount = 0;
		for (String tempContent : contentList) {
			if (tempContent != null) {
				String[] ops = tempContent.split(",");
				int tempCount = 1;
				for (String op : ops) {
					if (!"#".equals(op)) {
						tempCount *= op.split("\\|").length;
					}
				}
				tempBetCount += tempCount;
			}
		}
		if (tempBetCount * betContent.getMultiple() * 2 != amount) {
			throw new WebDataException("投注金额异常");
		}
		PlayType p = null;
		SportteryPassType tempT = betContent.getPassTypes().get(0);
		p = tempT.getType();
		ContentCheck check = new ContentCheck();
		check.setFirstTime(firstTime);
		check.setLateTime(lateTime);
		check.setMultiple(betContent.getMultiple());
		check.setPass(true);
		check.setContent(content);
		check.setPlayType(p);
		return check;
	}

	public double getBetAmount(String comment, SelectType selectType, int multiple) throws Exception {
		LotteryPlan plan = new LotteryPlan();
		plan.setContent(comment);
		plan.setSelectType(selectType);
		plan.setMultiple(multiple);
		SportteryBasketChaiPiao chaiPiao = new SportteryBasketChaiPiao();
		return chaiPiao.getBetAmount(plan);
	}

	@Override
	public Class<BasketBallBetContent> getModelClass() {
		
		return BasketBallBetContent.class;
	}

	@Override
	public Class<BasketBallMatchItem> getMatchItemClass() {
		return BasketBallMatchItem.class;
	}

}

