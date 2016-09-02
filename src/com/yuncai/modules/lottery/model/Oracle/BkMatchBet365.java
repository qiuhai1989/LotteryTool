package com.yuncai.modules.lottery.model.Oracle;

/**
 * 竞彩篮球赛事-亚指-总分-bet365-即时盘赔率
 */

@SuppressWarnings("serial")
public class BkMatchBet365 implements java.io.Serializable {

	private Integer id;
	private Integer mid;
	private String ymbRate;
	private int ymbStatus;
	private String ypk;
	private String ytgRate;
	private int ytgStatus;
	private String dmbRate;
	private int dmbStatus;
	private String dpk;
	private String dtgRate;
	private int dtgStatus;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMid() {
		return mid;
	}

	public void setMid(Integer mid) {
		this.mid = mid;
	}

	
	public String getYmbRate() {
		return ymbRate;
	}

	public void setYmbRate(String ymbRate) {
		this.ymbRate = ymbRate;
	}

	public String getYpk() {
		return ypk;
	}

	public void setYpk(String ypk) {
		this.ypk = ypk;
	}

	public String getYtgRate() {
		return ytgRate;
	}

	public void setYtgRate(String ytgRate) {
		this.ytgRate = ytgRate;
	}
	
	public int getYmbStatus() {
		return ymbStatus;
	}

	public void setYmbStatus(int ymbStatus) {
		this.ymbStatus = ymbStatus;
	}

	public int getYtgStatus() {
		return ytgStatus;
	}

	public void setYtgStatus(int ytgStatus) {
		this.ytgStatus = ytgStatus;
	}

	public String getDmbRate() {
		return dmbRate;
	}

	public void setDmbRate(String dmbRate) {
		this.dmbRate = dmbRate;
	}

	public int getDmbStatus() {
		return dmbStatus;
	}

	public void setDmbStatus(int dmbStatus) {
		this.dmbStatus = dmbStatus;
	}

	public String getDpk() {
		return dpk;
	}

	public void setDpk(String dpk) {
		this.dpk = dpk;
	}

	public String getDtgRate() {
		return dtgRate;
	}

	public void setDtgRate(String dtgRate) {
		this.dtgRate = dtgRate;
	}
	
	public int getDtgStatus() {
		return dtgStatus;
	}

	public void setDtgStatus(int dtgStatus) {
		this.dtgStatus = dtgStatus;
	}

	public BkMatchBet365() {
	}

	public BkMatchBet365(Integer mid, String ymbRate, int ymbStatus, String ypk,
			String ytgRate, int ytgStatus, String dmbRate, int dmbStatus,
			String dpk, String dtgRate,int dtgStatus) {
		super();
		this.mid = mid;
		this.ymbRate = ymbRate;
		this.ymbStatus = ymbStatus;
		this.ypk = ypk;
		this.ytgRate = ytgRate;
		this.ytgStatus = ytgStatus;
		this.dmbRate = dmbRate;
		this.dmbStatus = dmbStatus;
		this.dpk = dpk;
		this.dtgRate = dtgRate;
		this.dtgStatus=dtgStatus;
	}
	
	//亚
	public void setBkMatchYa(String ymbRate, int ymbStatus, String ypk,
			String ytgRate, int ytgStatus) {
		this.ymbRate = ymbRate;
		this.ymbStatus = ymbStatus;
		this.ypk = ypk;
		this.ytgRate = ytgRate;
		this.ytgStatus = ytgStatus;
	}
	
	//大小
	public void setBkMatchDaXiao(String dmbRate, int dmbStatus,String dpk,
			String dtgRate,int dtgStatus) {
		this.dmbRate = dmbRate;
		this.dmbStatus = dmbStatus;
		this.dpk = dpk;
		this.dtgRate = dtgRate;
		this.dtgStatus = dtgStatus;
	}

	@Override
	public String toString() {
		return "BkMatchBet365 [id=" + id + ", mid=" + mid + ", ymbRate=" + ymbRate
				+ ", ymbStatus=" + ymbStatus + ", ypk=" + ypk + ", ytgRate="
				+ ytgRate + ", ytgStatus=" + ytgStatus + ", dmbRate=" + dmbRate
				+ ", dmbStatus=" + dmbStatus + ", dpk=" + dpk + ", dtgRate="
				+ dtgRate + "]";
	}

	
	
}