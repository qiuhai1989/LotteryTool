package com.yuncai.modules.lottery.model.Oracle;

import java.util.Date;

/**胜平负
 * FtSpfAward entity. @author MyEclipse Persistence Tools
 */

public class FtSpfAward extends FtAward implements java.io.Serializable {

	// Fields

	private Double homewinaward;
	private Double guestwinaward;
	private Double drawaward;
	private Double nrhomewinaward;
	private Double nrguestwinaward;
	private Double nrdrawaward;
	private String homechange;
	private String drawchange;
	private String guestchange;
	// Constructors
	private Integer isHhad;
	private Integer isHad;
	

	public Integer getIsHhad() {
		return isHhad;
	}

	public void setIsHhad(Integer isHhad) {
		this.isHhad = isHhad;
	}

	public Integer getIsHad() {
		return isHad;
	}

	public void setIsHad(Integer isHad) {
		this.isHad = isHad;
	}

	/** default constructor */
	public FtSpfAward() {
	}
	
	public Double getHomewinaward() {
		return homewinaward;
	}

	public void setHomewinaward(Double homewinaward) {
		this.homewinaward = homewinaward;
	}

	public Double getGuestwinaward() {
		return guestwinaward;
	}

	public void setGuestwinaward(Double guestwinaward) {
		this.guestwinaward = guestwinaward;
	}

	public Double getDrawaward() {
		return drawaward;
	}

	public void setDrawaward(Double drawaward) {
		this.drawaward = drawaward;
	}

	public String getHomechange() {
		return homechange;
	}

	public void setHomechange(String homechange) {
		this.homechange = homechange;
	}

	public String getDrawchange() {
		return drawchange;
	}

	public void setDrawchange(String drawchange) {
		this.drawchange = drawchange;
	}

	public String getGuestchange() {
		return guestchange;
	}

	public void setGuestchange(String guestchange) {
		this.guestchange = guestchange;
	}
	
	public Double getNrhomewinaward() {
		return nrhomewinaward;
	}

	public void setNrhomewinaward(Double nrhomewinaward) {
		this.nrhomewinaward = nrhomewinaward;
	}

	public Double getNrguestwinaward() {
		return nrguestwinaward;
	}

	public void setNrguestwinaward(Double nrguestwinaward) {
		this.nrguestwinaward = nrguestwinaward;
	}

	public Double getNrdrawaward() {
		return nrdrawaward;
	}

	public void setNrdrawaward(Double nrdrawaward) {
		this.nrdrawaward = nrdrawaward;
	}

	public FtSpfAward(Integer mid, Integer inttime, String lineid,
			String matchtime, Integer passmode, Date lastupdatetime,
			Double homewinaward,Double drawaward, Double guestwinaward, 
			Double nrhomewinaward,Double nrdrawaward, Double nrguestwinaward, 
			String homechange, String drawchange, String guestchange) {
		super(mid, inttime, lineid, matchtime, passmode, lastupdatetime);
		this.homewinaward = homewinaward;
		this.drawaward = drawaward;
		this.guestwinaward = guestwinaward;
		this.nrhomewinaward = nrhomewinaward;
		this.nrdrawaward = nrdrawaward;
		this.nrguestwinaward = nrguestwinaward;
		this.homechange = homechange;
		this.drawchange = drawchange;
		this.guestchange = guestchange;
	}

	/**
	 * 用于抓取回来的数据更新封装
	 * @param mid
	 * @param inttime
	 * @param lineid
	 * @param matchtime
	 * @param passmode
	 * @param lastupdatetime
	 * @param homewinaward
	 * @param guestwinaward
	 * @param drawaward
	 * @param homechange
	 * @param drawchange
	 * @param guestchange
	 */
	public void setFtSpfAward(Integer mid, Integer inttime, String lineid,
			String matchtime, Integer passmode, Date lastupdatetime,
			Double homewinaward, Double drawaward,Double guestwinaward, 
			Double nrhomewinaward,Double nrdrawaward, Double nrguestwinaward, 
			String homechange, String drawchange, String guestchange) {
		this.setMid(mid);
		this.setInttime(inttime);
		this.setLineid(lineid);
		this.setMatchtime(matchtime);
		this.setPassmode(passmode);
		this.setLastupdatetime(lastupdatetime);
		this.homewinaward = homewinaward;
		this.drawaward = drawaward;
		this.guestwinaward = guestwinaward;
		this.nrhomewinaward = nrhomewinaward;
		this.nrdrawaward = nrdrawaward;
		this.nrguestwinaward = nrguestwinaward;
		this.homechange = homechange;
		this.drawchange = drawchange;
		this.guestchange = guestchange;
	}
	
	
	public FtSpfAward(Integer mid, Integer inttime, String lineid,
			String matchtime, Integer passmode, Date lastupdatetime,Double homewinaward, Double guestwinaward, Double drawaward, Double nrhomewinaward, Double nrguestwinaward, Double nrdrawaward,
			String homechange, String drawchange, String guestchange, Integer isHhad, Integer isHad) {
		super(mid, inttime, lineid, matchtime, passmode, lastupdatetime);
		this.homewinaward = homewinaward;
		this.guestwinaward = guestwinaward;
		this.drawaward = drawaward;
		this.nrhomewinaward = nrhomewinaward;
		this.nrguestwinaward = nrguestwinaward;
		this.nrdrawaward = nrdrawaward;
		this.homechange = homechange;
		this.drawchange = drawchange;
		this.guestchange = guestchange;
		this.isHhad = isHhad;
		this.isHad = isHad;
	}

	public double getAward(String optionsValue){
		if("1".equals(optionsValue)){
			return homewinaward;
		}else if("2".equals(optionsValue)){
			return drawaward;
		}else if("3".equals(optionsValue)){
			return guestwinaward;
		}
		return 0.0;
	}
	@Override
	public void setAwardArr(Double[] awardArr) {
		if (awardArr != null) {
			if (awardArr.length != 3)
				throw new RuntimeException("数组长度错误，请重新设值");
			this.setHomewinaward(awardArr[0]);
			this.setDrawaward(awardArr[1]);
			this.setGuestwinaward(awardArr[2]);
		}
	}
	public void setWbwAwardArr(Double[] awardArr) {
		setAwardArr(awardArr);
	}
	public Double[] getAwardArr() {
		return new Double[] { homewinaward, drawaward, guestwinaward };
	}
	public void setAwardChangeStatus(Integer[] changeStatus){
		if(changeStatus!=null){
			if(changeStatus.length!=3){
				throw new RuntimeException("数组长度错误，请重新设值");
			}
			this.setHomechange(changeStatus[0].toString());
			this.setDrawchange(changeStatus[1].toString());
			this.setGuestchange(changeStatus[2].toString());
		}
	}
	public Integer[] getAwardChangeStatus() {
		return new Integer[] { Integer.parseInt(homechange), Integer.parseInt(drawchange), Integer.parseInt(guestchange) };
	}
	public boolean checkAwardIsChange(FtAward tempAward){
		FtSpfAward award=(FtSpfAward) tempAward;
		boolean flag=false;
		if(award==null){
			return flag;
		}
		Double[] awardArr=award.getAwardArr();
		Double[] thisAwardArr=getAwardArr();
		Integer[] changeStatus=award.getAwardChangeStatus();
		if(thisAwardArr.length!=awardArr.length){
			throw new RuntimeException("数组长度错误，不能比较");
		}
		for(int i=0;i<thisAwardArr.length;i++){
			if(!thisAwardArr[i].equals(0d)&&awardArr[i].equals(0d)){
				return false;
			}else if(!thisAwardArr[i].equals(awardArr[i])){
				if(thisAwardArr[i]>awardArr[i]){
					changeStatus[i]=-1;
				}else{
					changeStatus[i]=1;
				}
				flag= true;
			}
		}
		award.setAwardChangeStatus(changeStatus);
		return flag;
	}
}