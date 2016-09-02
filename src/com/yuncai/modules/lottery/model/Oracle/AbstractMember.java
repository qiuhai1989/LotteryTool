package com.yuncai.modules.lottery.model.Oracle;

import java.util.Date;

public class AbstractMember implements java.io.Serializable {
	// Fields
	// 会员Id
	private Integer id;
	// 帐号
	private String account;
	// 姓名
	private String name;
	// 证件类型
	private Integer certType;
	// 证件号码
	private String certNo;
	// 密码
	private String password;
	// 级别
	private Integer rank;
	// 电子邮箱
	private String email;
	// 电话号码
	private String mobile;
	// 状态
	private Integer status;
	// 注册时间
	private Date registerDateTime;
	// 最后登录时间
	private Date lastLoginDateTime;
	// 总的经验值
	private Integer exprerience;
	// 来源编号
	private Integer sourceId;
	// 是否限制提款：0否 1是
	private Integer recommender;
	// 合作供应商
	private String provider;
	// 供应商方的用户id
	private String outUid;

	private String sign;
	// 用户头像
	private String picture;
	//名片
	private Integer card;//1普通（保持原有不变）、2云彩VIP（V图标识）、3大赢家团队（红旗标识），
	//是否手机验证通过
	private Integer isMobileAuthed;
	
	//活动设置时间
	private Date  ceratePlayDate;

	//活动设置状态
	private Integer  playStatus;
	private Integer buySendSms;

	private String phoneNo; //机身码
	
	private Integer isPhoneBinding;
	
	private String nickName; //昵称
	
	//0：白名单，1：黑名单；2：vip；3：普通用户
	private Integer userGradeType ;
	
	private Double bonus;//返点比例
	
	private String recommendId;// 推荐ID
	
	private Integer recommendQuota;// 推荐限额
	
	private Integer recommendEnable;// 是否启用此推荐人
	
	private String mobilBelongingTo;//号码归属地
	
	public Integer getUserGradeType() {
		return userGradeType;
	}
	public void setUserGradeType(Integer userGradeType) {
		this.userGradeType = userGradeType;
	}
	public Integer getIsPhoneBinding() {
		return isPhoneBinding;
	}
	public void setIsPhoneBinding(Integer isPhoneBinding) {
		this.isPhoneBinding = isPhoneBinding;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public Integer getBuySendSms() {
		return buySendSms;
	}
	public void setBuySendSms(Integer buySendSms) {
		this.buySendSms = buySendSms;
	}
	/** default constructor */
	public AbstractMember() {
	}
	/** minimal constructor */
	public AbstractMember(String account, String name, Integer certType, String certNo, String password, Integer rank, String email, String mobile,
			Integer status, Integer exprerience, Integer sourceId) {
		this.account = account;
		this.name = name;
		this.certType = certType;
		this.certNo = certNo;
		this.password = password;
		this.rank = rank;
		this.email = email;
		this.mobile = mobile;
		this.status = status;
		this.exprerience = exprerience;
		this.sourceId = sourceId;
	}

	/** full constructor */
	public AbstractMember(String account, String name, Integer certType, String certNo, String password, Integer rank, String email, String mobile,
			Integer status, Date registerDateTime, Date lastLoginDateTime, Integer exprerience, Integer sourceId, Integer recommender) {
		this.account = account;
		this.name = name;
		this.certType = certType;
		this.certNo = certNo;
		this.password = password;
		this.rank = rank;
		this.email = email;
		this.mobile = mobile;
		this.status = status;
		this.registerDateTime = registerDateTime;
		this.lastLoginDateTime = lastLoginDateTime;
		this.exprerience = exprerience;
		this.sourceId = sourceId;
		this.recommender = recommender;
	}
	
	
	
	
	public AbstractMember(Integer id, String account, String name, Integer certType, String certNo, String password, Integer rank, String email,
			String mobile, Integer status, Date registerDateTime, Date lastLoginDateTime, Integer exprerience, Integer sourceId, Integer recommender,
			String provider, String outUid, String sign, String picture, Integer card, Integer isMobileAuthed, Date ceratePlayDate,
			Integer playStatus, Integer buySendSms, String phoneNo) {
		super();
		this.id = id;
		this.account = account;
		this.name = name;
		this.certType = certType;
		this.certNo = certNo;
		this.password = password;
		this.rank = rank;
		this.email = email;
		this.mobile = mobile;
		this.status = status;
		this.registerDateTime = registerDateTime;
		this.lastLoginDateTime = lastLoginDateTime;
		this.exprerience = exprerience;
		this.sourceId = sourceId;
		this.recommender = recommender;
		this.provider = provider;
		this.outUid = outUid;
		this.sign = sign;
		this.picture = picture;
		this.card = card;
		this.isMobileAuthed = isMobileAuthed;
		this.ceratePlayDate = ceratePlayDate;
		this.playStatus = playStatus;
		this.buySendSms = buySendSms;
		this.phoneNo = phoneNo;
	}
	
	
	public AbstractMember(Integer id, String account, String name, Integer certType, String certNo, String password, Integer rank, String email,
			String mobile, Integer status, Date registerDateTime, Date lastLoginDateTime, Integer exprerience, Integer sourceId, Integer recommender,
			String provider, String outUid, String sign, String picture, Integer card, Integer isMobileAuthed, Date ceratePlayDate,
			Integer playStatus, Integer buySendSms, String phoneNo, Integer isPhoneBinding) {
		super();
		this.id = id;
		this.account = account;
		this.name = name;
		this.certType = certType;
		this.certNo = certNo;
		this.password = password;
		this.rank = rank;
		this.email = email;
		this.mobile = mobile;
		this.status = status;
		this.registerDateTime = registerDateTime;
		this.lastLoginDateTime = lastLoginDateTime;
		this.exprerience = exprerience;
		this.sourceId = sourceId;
		this.recommender = recommender;
		this.provider = provider;
		this.outUid = outUid;
		this.sign = sign;
		this.picture = picture;
		this.card = card;
		this.isMobileAuthed = isMobileAuthed;
		this.ceratePlayDate = ceratePlayDate;
		this.playStatus = playStatus;
		this.buySendSms = buySendSms;
		this.phoneNo = phoneNo;
		this.isPhoneBinding = isPhoneBinding;
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCertType() {
		return certType;
	}

	public void setCertType(Integer certType) {
		this.certType = certType;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getRegisterDateTime() {
		return registerDateTime;
	}

	public void setRegisterDateTime(Date registerDateTime) {
		this.registerDateTime = registerDateTime;
	}

	public Date getLastLoginDateTime() {
		return lastLoginDateTime;
	}

	public void setLastLoginDateTime(Date lastLoginDateTime) {
		this.lastLoginDateTime = lastLoginDateTime;
	}

	public Integer getExprerience() {
		return exprerience;
	}

	public void setExprerience(Integer exprerience) {
		this.exprerience = exprerience;
	}

	public Integer getSourceId() {
		return sourceId;
	}

	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}

	public Integer getRecommender() {
		return recommender;
	}

	public void setRecommender(Integer recommender) {
		this.recommender = recommender;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getOutUid() {
		return outUid;
	}

	public void setOutUid(String outUid) {
		this.outUid = outUid;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Integer getCard() {
		return card;
	}

	public void setCard(Integer card) {
		this.card = card;
	}

	public Integer getIsMobileAuthed() {
		return isMobileAuthed;
	}

	public void setIsMobileAuthed(Integer isMobileAuthed) {
		this.isMobileAuthed = isMobileAuthed;
	}

	public Date getCeratePlayDate() {
		return ceratePlayDate;
	}

	public void setCeratePlayDate(Date ceratePlayDate) {
		this.ceratePlayDate = ceratePlayDate;
	}

	public Integer getPlayStatus() {
		return playStatus;
	}

	public void setPlayStatus(Integer playStatus) {
		this.playStatus = playStatus;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public Double getBonus() {
		return bonus;
	}
	public void setBonus(Double bonus) {
		this.bonus = bonus;
	}
	public String getRecommendId() {
		return recommendId;
	}
	public void setRecommendId(String recommendId) {
		this.recommendId = recommendId;
	}
	public Integer getRecommendQuota() {
		return recommendQuota;
	}
	public void setRecommendQuota(Integer recommendQuota) {
		this.recommendQuota = recommendQuota;
	}
	public Integer getRecommendEnable() {
		return recommendEnable;
	}
	public void setRecommendEnable(Integer recommendEnable) {
		this.recommendEnable = recommendEnable;
	}
	public String getMobilBelongingTo() {
		return mobilBelongingTo;
	}
	public void setMobilBelongingTo(String mobilBelongingTo) {
		this.mobilBelongingTo = mobilBelongingTo;
	}
	
}
