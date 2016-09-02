package com.yuncai.modules.lottery.bean.vo;

import java.util.ArrayList;
import java.util.List;

public class EasyEbData {
	private String type;//1-高命中 2-实力型 3-高回报
	private String showTxt;//高命中|实力型|高回报|
//	private String AIM;// 命中率
//	private String joinCnt;// 购买人数
	private String planCnt;
	private String defMultiple;
	private String pos;
	private String title;//宣传语 理论中奖率<font color='red' size='3'>30</font>以上，当天购买<font color='red' size='3'>5</font>人
	private List<EasyLotteryMatch>matchs = new ArrayList<EasyLotteryMatch>();
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getShowTxt() {
		return showTxt;
	}
	public void setShowTxt(String showTxt) {
		this.showTxt = showTxt;
	}
//	public String getAIM() {
//		return AIM;
//	}
//	public void setAIM(String aim) {
//		AIM = aim;
//	}
//	public String getJoinCnt() {
//		return joinCnt;
//	}
//	public void setJoinCnt(String joinCnt) {
//		this.joinCnt = joinCnt;
//	}
	public List<EasyLotteryMatch> getMatchs() {
		return matchs;
	}
	public void setMatchs(List<EasyLotteryMatch> matchs) {
		this.matchs = matchs;
	}
	public String getPlanCnt() {
		return planCnt;
	}
	public void setPlanCnt(String planCnt) {
		this.planCnt = planCnt;
	}
	public String getDefMultiple() {
		return defMultiple;
	}
	public void setDefMultiple(String defMultiple) {
		this.defMultiple = defMultiple;
	}
	public String getPos() {
		return pos;
	}
	public void setPos(String pos) {
		this.pos = pos;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
}
