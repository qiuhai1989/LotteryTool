package com.yuncai.modules.lottery.model.Oracle;

/**
 * 轻松购彩-类型定义实体类
 * @author blackworm
 *
 */
@SuppressWarnings("serial")
public class EasyLotteryType implements java.io.Serializable {

	// Fields

	private int id; 
	private String name; 		//类型名称
	private double minRate; 	//最小赔率
	private double maxRate; 	//最大赔率
	private int planCount; 	//方案数
	private String slogan; 		//广告语
	private int defaultMultiple; //默认倍数
	private int gtype; 		//玩法类型，0.竞彩足球 1.竞彩篮球
	private int isShow;		//是否显示，0.隐藏 1.显示
	private int pos;//排序
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getMinRate() {
		return minRate;
	}
	public void setMinRate(double minRate) {
		this.minRate = minRate;
	}
	public double getMaxRate() {
		return maxRate;
	}
	public void setMaxRate(double maxRate) {
		this.maxRate = maxRate;
	}
	public int getPlanCount() {
		return planCount;
	}
	public void setPlanCount(int planCount) {
		this.planCount = planCount;
	}
	public String getSlogan() {
		return slogan;
	}
	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}
	public int getDefaultMultiple() {
		return defaultMultiple;
	}
	public void setDefaultMultiple(int defaultMultiple) {
		this.defaultMultiple = defaultMultiple;
	}
	public int getGtype() {
		return gtype;
	}
	public void setGtype(int gtype) {
		this.gtype = gtype;
	}
	public int getIsShow() {
		return isShow;
	}
	public void setIsShow(int isShow) {
		this.isShow = isShow;
	}
	
	public int getPos() {
		return pos;
	}
	public void setPos(int pos) {
		this.pos = pos;
	}
	public EasyLotteryType(String name, double minRate, double maxRate,
			int planCount, String slogan, int defaultMultiple, int gtype,
			int isShow,int pos) {
		super();
		this.name = name;
		this.minRate = minRate;
		this.maxRate = maxRate;
		this.planCount = planCount;
		this.slogan = slogan;
		this.defaultMultiple = defaultMultiple;
		this.gtype = gtype;
		this.isShow = isShow;
		this.pos = pos;
	}
	public EasyLotteryType() {
		super();
	}
	@Override
	public String toString() {
		return "EasyLotteryType [id=" + id + ", name=" + name + ", minRate="
				+ minRate + ", maxRate=" + maxRate + ", planCount=" + planCount
				+ ", slogan=" + slogan + ", defaultMultiple=" + defaultMultiple
				+ ", gtype=" + gtype + ", isShow=" + isShow + "]";
	}
	
	
}