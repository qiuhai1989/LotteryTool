package com.yuncai.modules.lottery.sporttery.support;

public class SportteryOption implements java.io.Serializable {

	private static final long serialVersionUID = 7249892069132454534L;

	/** 选项值 */
	private String value;

	/** 奖金值 */
	private Double award;
	
	/** 彩种 */
	private String type;
	
	/** 让球数 */
	private Integer rq;
	
	/** 让分*/
	private Double rf;
	
	/** 大小分 */
	private Double dxf;
	/** 选项名 */
	private String typeValueStr;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getRq() {
		return rq;
	}

	public void setRq(Integer rq) {
		this.rq = rq;
	}

	public Double getRf() {
		return rf;
	}

	public void setRf(Double rf) {
		this.rf = rf;
	}

	public Double getDxf() {
		return dxf;
	}

	public void setDxf(Double dxf) {
		this.dxf = dxf;
	}

	
	
	public String getTypeValueStr() {
		return typeValueStr;
	}

	public void setTypeValueStr(String typeValueStr) {
		this.typeValueStr = typeValueStr;
	}

	/**
	 * @return the {@link #value}
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the {@link #value} to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the {@link #award}
	 */
	public Double getAward() {
		return award;
	}

	/**
	 * @param award
	 *            the {@link #award} to set
	 */
	public void setAward(Double award) {
		this.award = award;
	}
}
