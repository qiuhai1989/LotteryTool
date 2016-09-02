package com.yuncai.modules.lottery.model.Oracle;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.yuncai.core.tools.BallCodeConverter;
import com.yuncai.core.tools.DateTools;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.modules.lottery.model.Oracle.toolType.GenType;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanTicketStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanType;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlayType;
import com.yuncai.modules.lottery.model.Oracle.toolType.PublicStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.SelectType;
import com.yuncai.modules.lottery.model.Oracle.toolType.WinStatus;
import com.yuncai.modules.lottery.sporttery.OptionItem;
import com.yuncai.modules.lottery.sporttery.model.FootBallBetContent;
import com.yuncai.modules.lottery.sporttery.model.FootBallBetContentTurbid;
import com.yuncai.modules.lottery.sporttery.model.SportteryBetContentModel;
import com.yuncai.modules.lottery.sporttery.model.SportteryBetContentModelTurbid;
import com.yuncai.modules.lottery.sporttery.model.baskball.BasketBallBetContent;
import com.yuncai.modules.lottery.sporttery.support.CommonUtils;
import com.yuncai.modules.lottery.sporttery.support.MatchItem;
import com.yuncai.modules.lottery.sporttery.support.SportteryOption;
import com.yuncai.modules.lottery.sporttery.support.basketball.BasketBallMatchItem;
import com.yuncai.modules.lottery.sporttery.support.football.FootBallMatchItem;
import com.yuncai.modules.lottery.sporttery.support.football.FootBallMatchTurbidItem;

public class LotteryPlan extends AbstractLotteryPlan implements java.io.Serializable{
	
	//发起人名片
	private Integer card;//1普通（保持原有不变）、2云彩VIP（V图标识）、3大赢家团队（红旗标识），
	
	/** default constructor */
	public LotteryPlan() {
	}

	/** minimal constructor */
	public LotteryPlan(Integer planNo, LotteryType lotteryType, String term, PlanType planType, SelectType selectType, Integer memberId,
			String account, Integer amount, Integer perAmount, Integer part, Integer soldPart, Integer founderPart, Integer reservePart,
			Date dealDateTime, Integer multiple, PlanStatus planStatus, WinStatus winStatus, Integer clicks, String content, Integer isUploadContent,
			PlayType playType, String verifyMd5, Integer isAbleTicketing, PlanTicketStatus planTicketStatus) {
		super(planNo, lotteryType, term, planType, selectType, memberId, account, amount, perAmount, part, soldPart, founderPart, reservePart,
				dealDateTime, multiple, planStatus, winStatus, clicks, content, isUploadContent, playType, verifyMd5, isAbleTicketing,
				planTicketStatus);
	}

	/** full constructor */
	public LotteryPlan(Integer planNo, LotteryType lotteryType, String term, PlanType planType, SelectType selectType, Integer memberId,
			String account, Date createDateTime, Integer amount, Integer perAmount, Integer part, Integer soldPart, Integer founderPart,
			Integer reservePart, Date dealDateTime, Integer multiple, PlanStatus planStatus, Date printTicketDateTime, WinStatus winStatus,
			Double pretaxPrize, Double posttaxPrize, String prizeContent, Integer clicks, String planDesc, String content, Integer isUploadContent,
			Integer experience, Integer isSuperMan, PlayType playType, String addAttribute, Integer isTop, Integer model, String verifyMd5,
			Integer bookCount, GenType genType, Integer isAbleTicketing, PlanTicketStatus planTicketStatus) {
		super(planNo, lotteryType, term, planType, selectType, memberId, account, createDateTime, amount, perAmount, part, soldPart, founderPart,
				reservePart, dealDateTime, multiple, planStatus, printTicketDateTime, winStatus, pretaxPrize, posttaxPrize, prizeContent, clicks,
				planDesc, content, isUploadContent, experience, isSuperMan, playType, addAttribute, isTop, model, verifyMd5, bookCount, genType,
				isAbleTicketing, planTicketStatus);
		// TODO Auto-generated constructor stub
	}
	
	public LotteryPlan(Integer planNo, LotteryType lotteryType, String term, PlanType planType, SelectType selectType, Integer memberId,
			String account, Date createDateTime, Integer amount, Integer perAmount, Integer part, Integer soldPart, Integer founderPart,
			Integer reservePart, Date dealDateTime, Integer multiple, PlanStatus planStatus, Date printTicketDateTime, WinStatus winStatus,
			Double pretaxPrize, Double posttaxPrize, String prizeContent, Integer clicks, String planDesc, String content, Integer isUploadContent,
			Integer experience, Integer isSuperMan, PlayType playType, String addAttribute, Integer isTop, Integer model, String verifyMd5,
			Integer bookCount, GenType genType, Integer isAbleTicketing, PlanTicketStatus planTicketStatus,String channel) {
		super(planNo, lotteryType, term, planType, selectType, memberId, account, createDateTime, amount, perAmount, part, soldPart, founderPart,
				reservePart, dealDateTime, multiple, planStatus, printTicketDateTime, winStatus, pretaxPrize, posttaxPrize, prizeContent, clicks,
				planDesc, content, isUploadContent, experience, isSuperMan, playType, addAttribute, isTop, model, verifyMd5, bookCount, genType,
				isAbleTicketing, planTicketStatus,channel);
		// TODO Auto-generated constructor stub
	}	
	
	public LotteryPlan(Integer planNo, LotteryType lotteryType, String term, PlanType planType, SelectType selectType, Integer memberId,
			String account, String nickName, Date createDateTime, Integer amount, Integer perAmount, Integer part, Integer soldPart,
			Integer founderPart, Integer reservePart, Date dealDateTime, Integer multiple, PlanStatus planStatus, Date printTicketDateTime,
			WinStatus winStatus, Double pretaxPrize, Double posttaxPrize, String prizeContent, Integer clicks, String planDesc, String content,
			Integer isUploadContent, Integer experience, Integer isSuperMan, PlayType playType, String addAttribute, Integer isTop, Integer model,
			String verifyMd5, PublicStatus publicStatus, int commision, Integer bookCount, GenType genType, Integer isAbleTicketing,
			PlanTicketStatus planTicketStatus, Double addPrize, Date openResultTime, Integer planHmOg, String automaticType, String channel,
			String title, Integer easyType){
		
		super(planNo, lotteryType, term, planType, selectType, memberId, account, nickName, createDateTime, amount, perAmount, part, soldPart, founderPart, reservePart, dealDateTime, multiple, planStatus, printTicketDateTime, winStatus, pretaxPrize, posttaxPrize, prizeContent, clicks, planDesc, content, isUploadContent, experience, isSuperMan, playType, addAttribute, isTop, model, verifyMd5, publicStatus, commision, bookCount, genType, isAbleTicketing, planTicketStatus, addPrize, openResultTime, planHmOg, automaticType, channel, title, easyType);
	}
	
	
	
	@SuppressWarnings("unchecked")
	public static String genContentList(LotteryType lt, String content,String playType) {

		StringBuffer sb = new StringBuffer("");
		if (LotteryType.JCLQList.contains(lt) || LotteryType.JCZQList.contains(lt)) {
			String selectValue = "";
			if (content != null && content.length() > 0) {
				SportteryBetContentModelTurbid comtent = null;
				if (LotteryType.JCLQList.contains(lt)) {
					comtent = CommonUtils.Object4JsonTurbid(content, BasketBallBetContent.class, BasketBallMatchItem.class);
				} else {
					comtent = CommonUtils.Object4JsonTurbid(content, FootBallBetContentTurbid.class, FootBallMatchTurbidItem.class);
				}
				List<MatchItem> matchItemList = comtent.getMatchItems();
				Collections.sort(matchItemList,new Comparator<MatchItem>(){
					//先以intTime 排序再以itemid排序             
					@Override
					public int compare(MatchItem o1, MatchItem o2) {
						// TODO Auto-generated method stub
						if(o1.getIntTime()>o2.getIntTime()){
							return 1;
						}else if (o1.getIntTime().equals(o2.getIntTime())) {
							if(o1.getLineId()>=o2.getLineId()){
								return 1;
							}else {
								return 0;
							}
						}else {
							return 0;
						}
					}
					
				});
				if (comtent.getPassMode() == 0) {
					selectValue = "单关:";
				} else {
					selectValue = "过关:";
				}
				
				for (int i = 0; i < matchItemList.size(); i++) {
					MatchItem item = matchItemList.get(i);
					List<SportteryOption> optionList = item.getOptions();
					String weekStr = DateTools.getWeekStr(DateTools.StringToDate(item.getIntTime() + "", "yyyyMMdd"));
					selectValue += weekStr +String.format("%03d", item.getLineId())  + "(";
					for (int j = 0; j < optionList.size(); j++) {
						SportteryOption option = optionList.get(j);
						OptionItem optionItem = CommonUtils.getByPlayType(lt,option);
						selectValue += optionItem.getCommonText() + ",";
					}
					selectValue = selectValue.substring(0, selectValue.length() - 1);
					selectValue += ") ";
				}
			}
			return selectValue;
		} else {
			
			content = content.replaceAll("\r\n", "\n");
			
			String[] lines = content.split("\\\n");
			
			for (int i = 0; i < lines.length; i++) {
				sb.append("").append(PlayType.getItem(Integer.valueOf(playType)).getName()).append(":");
				String peace = lines[i];
				if(i!=lines.length-1){
					sb.append(BallCodeConverter.convert(lt.getValue(), Integer.valueOf(playType), peace)).append("<br>");
				}
				else{
					sb.append(BallCodeConverter.convert(lt.getValue(), Integer.valueOf(playType), peace));
				}
			}
			
		}
		sb.append("");
		return sb.toString();

	}

	public String getTermToCreateDate(){
		if(LotteryType.JCZQList.contains(this.getLotteryType())||LotteryType.JCLQList.contains(this.getLotteryType())){
			return DateTools.dateToString(this.getCreateDateTime(), "yyyyMMdd");
		}else{
			return this.getTerm();
		}
		
		
	}
	
	public String winQueryInfo(){
		//大乐透：3元中3.25亿！！！
		return this.getLotteryType().getName()+":"+this.getAmount()+"元中"+this.getPretaxPrize() ;
	}

	public Integer getCard() {
		return card;
	}

	public void setCard(Integer card) {
		this.card = card;
	}
	
}
