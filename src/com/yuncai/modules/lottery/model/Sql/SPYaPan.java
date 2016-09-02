package com.yuncai.modules.lottery.model.Sql;

/**亚盘 大小球
 * @author Administrator
 *
 */
public class SPYaPan implements java.io.Serializable{
	
	private Integer id;
	private Integer mid;
	
	//亚盘部分
	private String mbRate;//主队赔率
	private String pk;//让球情况
	private String tgRate;
	
	//大小球部分
	private String dmbRate;
	private String dpk;
	private String dtgRate;
	
	
	
	
	
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
	public String getMbRate() {
		return mbRate;
	}
	public void setMbRate(String mbRate) {
		this.mbRate = mbRate;
	}
	public String getPk() {
		return pk;
	}
	public void setPk(String pk) {
		this.pk = pk;
	}
	public String getTgRate() {
		return tgRate;
	}
	public void setTgRate(String tgRate) {
		this.tgRate = tgRate;
	}
	public String getDmbRate() {
		return dmbRate;
	}
	public void setDmbRate(String dmbRate) {
		this.dmbRate = dmbRate;
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
	
	
}
