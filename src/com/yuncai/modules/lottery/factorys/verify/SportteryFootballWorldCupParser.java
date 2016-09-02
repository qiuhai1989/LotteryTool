package com.yuncai.modules.lottery.factorys.verify;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yuncai.core.longExce.WebDataException;
import com.yuncai.core.tools.DateTools;
import com.yuncai.modules.lottery.factorys.chaipiao.SportteryFootballChaiPiaoTurbid;
import com.yuncai.modules.lottery.model.Oracle.FtChampion;
import com.yuncai.modules.lottery.model.Oracle.FtMatch;
import com.yuncai.modules.lottery.model.Oracle.LotteryPlan;
import com.yuncai.modules.lottery.model.Oracle.toolType.MatchStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlayType;
import com.yuncai.modules.lottery.model.Oracle.toolType.SelectType;
import com.yuncai.modules.lottery.service.oracleService.fbmatch.FtChampionService;
import com.yuncai.modules.lottery.service.oracleService.fbmatch.FtMatchService;
import com.yuncai.modules.lottery.sporttery.model.FootBallBetContentTurbid;
import com.yuncai.modules.lottery.sporttery.model.FootBallBetFilterContentTurbid;
import com.yuncai.modules.lottery.sporttery.support.CommonUtils;
import com.yuncai.modules.lottery.sporttery.support.SportteryPassType;
import com.yuncai.modules.lottery.sporttery.support.football.FootBallMatchTurbidItem;

public class SportteryFootballWorldCupParser extends SportteryTurbidParser<FootBallBetContentTurbid, FootBallMatchTurbidItem>{
	 
	private FtChampionService ftChampionService ;
	
 
	public FtChampionService getFtChampionService() {
		return ftChampionService;
	}

	public void setFtChampionService(FtChampionService ftChampionService) {
		this.ftChampionService = ftChampionService;
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
	public ContentCheck checkPlan(String content, double amount) throws Exception {
		FootBallBetFilterContentTurbid betContent = null;
		if (content == null || "".equals(content))
			throw new WebDataException("方案内容不能为空");

		betContent = CommonUtils.Object4JsonTurbid(content, FootBallBetFilterContentTurbid.class, getMatchItemClass());
		
		
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
		List<FtChampion> dbChampions=getFtChampions(FtChampion.COMPETITION_TYPE_WORLDCUP);
		 
		Map<String, FtChampion> tempMatchs = new HashMap<String, FtChampion>();// 根据截止时间重新构建一个
		for (FtChampion champion : dbChampions) {// 将数据库中的对阵遍历出来，组装MAP，与投注页面对阵比较
			tempMatchs.put(champion.getTeam_num(), champion);
		}
		for (FootBallMatchTurbidItem item : matchItems) {
			String key = item.getLineId().toString();

			FtChampion m = tempMatchs.get(key);
			if (m != null&& (FtChampion.SALE_STATUS_NO.equals(m.getSale_status()))) {
				String weekStr = "编号："+item.getLineId()+" 球队："+m.getTeam_name();
				throw new WebDataException("您选择的场次中有已经出局的队伍："+weekStr);
			} else {// 放入比赛时间，最早的
				Date startTime=DateTools.StringToDate("20140713");
				if (firstTime == null || firstTime.getTime() > startTime.getTime()) {
					firstTime = startTime;
				}
				

				//最晚载止时间,用于方案截止后公开
				if (lateTime == null || lateTime.getTime() <startTime.getTime()) {
					lateTime =startTime;
				}
			}
		}
		 
		int tempBetCount = matchItems.size(); 
		
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

	public List<FtChampion> getFtChampions(String competitionType){
		List<FtChampion> dbChampions=ftChampionService.findByProperty("competition_type", competitionType);
		return dbChampions;
	}
	
	@Override
	public FtMatchService getSportteryService() {
		// TODO Auto-generated method stub
		return null;
	}

}
