package com.yuncai.modules.lottery.factorys.verify;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yuncai.core.longExce.WebDataException;
import com.yuncai.core.tools.DateTools;
import com.yuncai.core.tools.FileTools;
import com.yuncai.modules.lottery.WebConstants;
import com.yuncai.modules.lottery.model.Oracle.FtMatch;
import com.yuncai.modules.lottery.model.Oracle.toolType.MatchStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlayType;
import com.yuncai.modules.lottery.service.oracleService.fbmatch.FtMatchService;
import com.yuncai.modules.lottery.sporttery.model.SportteryFileUploadContentModel;
import com.yuncai.modules.lottery.sporttery.support.CommonUtils;
import com.yuncai.modules.lottery.sporttery.support.SportteryPassType;
import com.yuncai.modules.lottery.sporttery.support.football.FootBallMatchItem;

public class SportteryFootballUploadParse extends SportteryFootballParser{
	
	
	private  FtMatchService  ftMatchService;
	@Override
	public ContentCheck checkPlan(String content, double amount) throws Exception {
		ContentCheck check = new ContentCheck();
		// TODO Auto-generated method stub
		SportteryFileUploadContentModel betContent = null;
		if (content == null || "".equals(content))
			throw new WebDataException("方案内容不能为空");

		betContent = CommonUtils.Object4Json(content, SportteryFileUploadContentModel.class, getMatchItemClass());
		if (betContent.getFilePath() == null) {// 不是文件上传投注
			return super.checkPlan(content, amount);
		}
		Date firstTime = null;
		Date lateTime = null;
		List<FootBallMatchItem> matchItems = betContent.getMatchItems();
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
		List<FtMatch> dbMatchs = ftMatchService.findMatchByStatus(MatchStatus.ONSALE.ordinal());
		Map<String, FtMatch> tempMatchs = new HashMap<String, FtMatch>();// 根据截止时间重新构建一个
		Date nowDate = new Date();
		for (FtMatch match : dbMatchs) {// 将数据库中的对阵遍历出来，组装MAP，与投注页面对阵比较
			tempMatchs.put(match.getIntTime() + "_" + match.getLineId(), match);
		}
		for (FootBallMatchItem item : matchItems) {
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
		String fileContent=FileTools.getFileContent(WebConstants.WEB_PATH+betContent.getFilePath());
		String[] lines = fileContent.split("\n");
		int zhushu = 0;
		int mutiple=betContent.getMultiple();
		for (String line : lines) {
			if (!"".equals(line.replaceAll(" ", ""))) {
				zhushu++;
			} else {
				throw new Exception("上传的文件有空行");
			}
		}
		if (Double.valueOf(zhushu * 2 * mutiple) == amount) {
			check.setPass(true);
			check.setMultiple(mutiple);
			check.setContent(content);
			check.setPlayType(PlayType.getItem(100));
		} else {
			check.setPass(false);
		}
		PlayType p = null;
		SportteryPassType tempT = betContent.getPassTypes().get(0);
		p = tempT.getType();
		
		check.setFirstTime(firstTime);
		check.setLateTime(lateTime);
		check.setMultiple(betContent.getMultiple());
		check.setContent(content);
		check.setPlayType(p);
		return check;
	}

}
