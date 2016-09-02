package com.yuncai.modules.lottery.model.Oracle;

import java.util.Date;

import com.yuncai.core.tools.StringTools;

public class Member extends AbstractMember implements java.io.Serializable {
	/** default constructor */
	private MemberWallet wallet;
	
	public MemberWallet getWallet() {
		return wallet;
	}

	public void setWallet(MemberWallet wallet) {
		this.wallet = wallet;
	}
	
	public MemberScore score;
	

	public MemberScore getScore() {
		return score;
	}

	public void setScore(MemberScore score) {
		this.score = score;
	}

	public Member() {
		
	}

	/** minimal constructor */
	public Member(String account, String name, Integer certType, String certNo, String password, Integer rank, String email, String mobile,
			Integer status, Integer exprerience, Integer sourceId) {
		super(account, name, certType, certNo, password, rank, email, mobile, status, exprerience, sourceId);
	}

	/** full constructor */
	public Member(String account, String name, Integer certType, String certNo, String password, Integer rank, String email, String mobile,
			Integer status, Date registerDateTime, Date lastLoginDateTime, Integer exprerience, Integer sourceId, Integer recommender) {
		super(account, name, certType, certNo, password, rank, email, mobile, status, registerDateTime, lastLoginDateTime, exprerience, sourceId,
				recommender);
	}

	

	public String getNameSec() {
		String src = this.getName();
		if(src == null ) return "";
		return StringTools.getSecStr(src, 1, src.length());

	}

	public String getCertNoSec() {
		String src = this.getCertNo();
		if(src == null ) return "";
		return StringTools.getSecStr(src, 4, src.length() - 3);

	}
	
	public String getMobileSec() {
		String src = this.getMobile();
		if(src == null ) return "";
		return StringTools.getSecStr(src, 3, src.length() - 3);

	}
	
	public String getEmailSec(){
		String src = this.getEmail();
		if(src == null ) return "";
		String tem = this.getEmail().split("@")[1];
		String tem2 = "@" + tem;
		return  StringTools.getSecStr(src, 3, src.length() - tem2.length());
	}
}
