package com.yuncai.modules.lottery.factorys.verify;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yuncai.core.longExce.WebDataException;
import com.yuncai.core.tools.DateTools;
import com.yuncai.modules.lottery.factorys.chaipiao.SportteryFootballChaiPiaoTurbid;
import com.yuncai.modules.lottery.model.Oracle.FtMatch;
import com.yuncai.modules.lottery.model.Oracle.LotteryPlan;
import com.yuncai.modules.lottery.model.Oracle.toolType.MatchStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlayType;
import com.yuncai.modules.lottery.model.Oracle.toolType.SelectType;
import com.yuncai.modules.lottery.service.oracleService.fbmatch.FtMatchService;
import com.yuncai.modules.lottery.sporttery.model.FootBallBetContentTurbid;
import com.yuncai.modules.lottery.sporttery.model.FootBallBetFilterContentTurbid;
import com.yuncai.modules.lottery.sporttery.support.CommonUtils;
import com.yuncai.modules.lottery.sporttery.support.SportteryPassType;
import com.yuncai.modules.lottery.sporttery.support.football.FootBallMatchTurbidItem;

public class SportteryFootballTurbidParser extends SportteryTurbidParser<FootBallBetContentTurbid, FootBallMatchTurbidItem>{
	
	private FtMatchService  ftMatchService;
	
	public FtMatchService getFtMatchService() {
		return ftMatchService;
	}

	public void setFtMatchService(FtMatchService ftMatchService) {
		this.ftMatchService = ftMatchService;
	}

	@Override
	public Class<FootBallMatchTurbidItem> getMatchItemClass() {
		return FootBallMatchTurbidItem.class;
	}

	@Override
	public Class<FootBallBetContentTurbid> getModelClass() {
		return FootBallBetContentTurbid.class;
	}
	
	@Override
	public FtMatchService getSportteryService() {
		return ftMatchService;
	}
	
	@Override
	public ContentCheck checkPlan(String content, double amount) throws Exception {
		FootBallBetFilterContentTurbid betContent = null;
		if (content == null || "".equals(content))
			throw new WebDataException("方案内容不能为空");

		betContent = CommonUtils.Object4JsonTurbid(content, FootBallBetFilterContentTurbid.class, getMatchItemClass());
		if (betContent.getContentList() == null) {// 不是过滤投注
			return super.checkPlan(content, amount);
		}
		
//		if (betContent.getFilePath() != null) {// 是文件上传投注
//			return getHtUploadContent(betContent,content);
//		}
//		
		Date firstTime = null;
		Date lateTime = null;
		List<FootBallMatchTurbidItem> matchItems = betContent.getMatchItems();
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
		List<FtMatch> dbMatchs = getSportteryService().findMatchByStatus(MatchStatus.ONSALE.ordinal());
		Map<String, FtMatch> tempMatchs = new HashMap<String, FtMatch>();// 根据截止时间重新构建一个
		for (FtMatch match : dbMatchs) {// 将数据库中的对阵遍历出来，组装MAP，与投注页面对阵比较
			tempMatchs.put(match.getIntTime() + "_" + match.getLineId(), match);
		}
		for (FootBallMatchTurbidItem item : matchItems) {
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
		check.setContent(null);
		check.setPlayType(p);
		return check;
	}
	
	@Override
	public double getBetAmount(String content, SelectType selectType,
			int multiple) throws Exception {
		LotteryPlan plan = new LotteryPlan();
		plan.setContent(content);
		plan.setSelectType(selectType);
		plan.setMultiple(multiple);
		SportteryFootballChaiPiaoTurbid chaiPiao = new SportteryFootballChaiPiaoTurbid();
		return chaiPiao.getBetAmount(plan);
	}

}

