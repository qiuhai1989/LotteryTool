package com.yuncai.modules.lottery.model.Oracle;

import java.util.Date;

/**
 * 轻松购彩-推荐赛事实体类
 * @author blackworm
 *
 */
@SuppressWarnings("serial")
public class EasyLotteryRecommend implements java.io.Serializable {
	
	public enum showStatus{
		SHOW(1),HIDE(0);
		public final Integer value;

		private showStatus(Integer value) {
			this.value = value;
		}
		public Integer getValue() {
			return value;
		}
	}
	
	
	// Fields

	private Integer id;
	private Integer intTime; 
	private Integer lineId; 
	private Integer memberId; //会员ID
	private String account; //账号
	private Integer rtype; //玩法类型，7207.竞彩足球胜负 7301.竞彩篮球胜负
	private Integer matchId; //sql server数据库赛事表主键
	private double award; //选定赔率
	private Integer rq; //让球数
	private double rf; //让分数
	private double dxf; //大小分
	private Integer value; //选定值，如：1
	private Integer gtype; //球类型，72.竞彩足球 73.竞彩篮球
	private Integer typeId;//外键，对应t_easy_lottery_type表主键
	private Date createDate;//创建日期
	private int isShow;		//是否显示，0.隐藏 1.显示
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIntTime() {
		return intTime;
	}

	public void setIntTime(Integer intTime) {
		this.intTime = intTime;
	}

	public Integer getLineId() {
		return lineId;
	}

	public void setLineId(Integer lineId) {
		this.lineId = lineId;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Integer getRtype() {
		return rtype;
	}

	public void setRtype(Integer rtype) {
		this.rtype = rtype;
	}

	public Integer getMatchId() {
		return matchId;
	}

	public void setMatchId(Integer matchId) {
		this.matchId = matchId;
	}

	public double getAward() {
		return award;
	}

	public void setAward(double award) {
		this.award = award;
	}

	public Integer getRq() {
		return rq;
	}

	public void setRq(Integer rq) {
		this.rq = rq;
	}

	public double getRf() {
		return rf;
	}

	public void setRf(double rf) {
		this.rf = rf;
	}

	public double getDxf() {
		return dxf;
	}

	public void setDxf(double dxf) {
		this.dxf = dxf;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
	
	public Integer getGtype() {
		return gtype;
	}

	public void setGtype(Integer gtype) {
		this.gtype = gtype;
	}
	
	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public int getIsShow() {
		return isShow;
	}

	public void setIsShow(int isShow) {
		this.isShow = isShow;
	}

	/** default constructor */
	public EasyLotteryRecommend() {
		
	}

	public EasyLotteryRecommend(Integer intTime, Integer lineId,
			Integer memberId, String account, Integer rtype, Integer matchId,
			double award, Integer rq, double rf, double dxf,
			Integer value, Integer gtype, Integer typeId,Date createDate,int isShow) {
		super();
		this.intTime = intTime;
		this.lineId = lineId;
		this.memberId = memberId;
		this.account = account;
		this.rtype = rtype;
		this.matchId = matchId;
		this.award = award;
		this.rq = rq;
		this.rf = rf;
		this.dxf = dxf;
		this.value = value;
		this.gtype=gtype;
		this.typeId=typeId;
		this.createDate=createDate;
		this.isShow=isShow;
	}

	@Override
	public String toString() {
		return "EasyLotteryRecommend [id=" + id + ", intTime=" + intTime
				+ ", lineId=" + lineId + ", memberId=" + memberId
				+ ", account=" + account + ", rtype=" + rtype + ", matchId="
				+ matchId + ", award=" + award + ", rq=" + rq + ", rf=" + rf
				+ ", dxf=" + dxf + ", value=" + value + ", gtype=" + gtype
				+ ", typeId=" + typeId + ", createDate=" + createDate
				+ ", isShow=" + isShow + "]";
	}

}