package com.yuncai.modules.lottery.factorys.verify;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.log.Log;
import com.yuncai.core.longExce.WebDataException;
import com.yuncai.core.tools.DateTools;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.modules.lottery.model.Oracle.FtMatch;
import com.yuncai.modules.lottery.model.Oracle.LotteryPlan;
import com.yuncai.modules.lottery.model.Oracle.toolType.MatchStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlayType;
import com.yuncai.modules.lottery.model.Oracle.toolType.SelectType;
import com.yuncai.modules.lottery.service.oracleService.fbmatch.FtMatchService;
import com.yuncai.modules.lottery.sporttery.model.SportteryBetContentModel;
import com.yuncai.modules.lottery.sporttery.support.CommonUtils;
import com.yuncai.modules.lottery.sporttery.support.MatchItem;
import com.yuncai.modules.lottery.sporttery.support.SportteryOption;
import com.yuncai.modules.lottery.sporttery.support.SportteryPassType;

public abstract class SportteryParser<T extends SportteryBetContentModel<X>, X extends MatchItem> implements ParserBuilder {
	
	public ContentCheck checkPlan(String content, double amount) throws Exception {
		Date firstTime = null;
		Date lateTime = null;
		// 将结果转化成对象
		if (content == null || "".equals(content))
			throw new WebDataException("方案内容不能为空");
		T betContent = CommonUtils.Object4Json(content, getModelClass(), getMatchItemClass());
		List<X> matchItems = betContent.getMatchItems();
		List<SportteryPassType> passType = betContent.getPassTypes();
		if (matchItems.isEmpty())
			throw new WebDataException("您没有选择任何场次");
		if (passType.isEmpty())
			throw new WebDataException("您没有选择过关方式");

		if (betContent.getMultiple() < 1)
			throw new WebDataException("倍数不允许小于1");
		/*
		 * if (betContent.getMultiple() < 1 || betContent.getMultiple() > 1000)
		 * throw new WebDataException("倍数应该在1-999倍");
		 */
		int danCount = 0;
		// 检查对阵是否已经截止
		List<FtMatch> dbMatchs =getSportteryService().findMatchByStatus(MatchStatus.ONSALE.ordinal());
		Map<String, FtMatch> tempMatchs = new HashMap<String, FtMatch>();// 根据截止时间重新构建一个
		for (FtMatch match : dbMatchs) {// 将数据库中的对阵遍历出来，组装MAP，与投注页面对阵比较
			tempMatchs.put(match.getIntTime() + "_" + match.getLineId(), match);
		}
		for (X item : matchItems) {
			String key = item.getIntTime() + "_" + item.getLineId();
			FtMatch m = tempMatchs.get(key);
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

			if (item.isShedan())
				danCount++;
		}
		PlayType p = null;
		if (betContent.getPassTypes().size() > 1) {// 检查自由过关的设胆
			for (SportteryPassType passTy : betContent.getPassTypes()) {
				int matchCount = passTy.getMatchCount();
				if (matchCount < danCount)
					throw new WebDataException("胆码个数不能大于最低过关场次数目");
			}
			p = PlayType.JC_ZYGG;
		} else {// 检查多选过关设胆
			if (matchItems.size() <= danCount)
				throw new WebDataException("胆码个数不能大于或者等于场次数目");
			SportteryPassType tempT = betContent.getPassTypes().get(0);
			p = tempT.getType();
		}
//		LogUtil.out("content="+content);
		double tempAmount = getBetAmount(content, SelectType.CHOOSE_SELF, betContent.getMultiple());
		// 检查选项是否合法
		for (X matchItem : matchItems) {
			for (SportteryOption option : matchItem.getOptions()) {
				if (null == option.getValue() || "".equals(option.getValue().trim())) {
					throw new WebDataException("选项出错");
				}
			}
		}
//		LogUtil.out("tempAmount="+tempAmount);
//		LogUtil.out("amount="+amount);
		if (tempAmount != amount) {
			throw new WebDataException("投注金额出错");
		}
		LotteryPlan plan = new LotteryPlan();
		plan.setContent(content);
		ContentCheck check = new ContentCheck();
		check.setFirstTime(firstTime);
		check.setLateTime(lateTime);
		check.setMultiple(betContent.getMultiple());
		check.setPass(true);
		check.setContent(content);
		check.setPlayType(p);
		return check;
	}

	public abstract double getBetAmount(String content, SelectType selectType, int multiple) throws Exception;
	
	public abstract Class<T> getModelClass();

	public abstract Class<X> getMatchItemClass();
	
	public abstract FtMatchService getSportteryService();
	

	

	
}
	
