package com.yuncai.modules.lottery.model.Oracle;

import java.util.Date;

import com.yuncai.modules.lottery.model.Oracle.toolType.CardStatus;

public class AbstractCard implements java.io.Serializable{

	private Integer id;
	//批号
	private String batchNo;
	//卡号
	private String no;
	//金额
	private Double amount;
	//创建时间
	private Date createDateTime;
	//过期时间
	private Date expireDateTime;
	//消费者id
	private Integer memberId;
	//消费者账号
	private String account;
	//
	private  CardStatus status;
	//使用时间
	private Date useDateTime;
	//渠道
	private String provider ;
	
	private Integer providerId;
	
	private String owner;//拥有者
	
	private Integer ownerId;//所有者ID
	
	//是否实名认证
	private Integer isValid;

	
	
	public AbstractCard() {
		super();
	}
	
	
	

	public AbstractCard(Integer id, String batchNo, String no, Double amount, Date createDateTime, Date expireDateTime, Integer memberId,
			String account, CardStatus status, Date useDateTime, String provider, Integer providerId, Integer isValid) {
		super();
		this.id = id;
		this.batchNo = batchNo;
		this.no = no;
		this.amount = amount;
		this.createDateTime = createDateTime;
		this.expireDateTime = expireDateTime;
		this.memberId = memberId;
		this.account = account;
		this.status = status;
		this.useDateTime = useDateTime;
		this.provider = provider;
		this.providerId = providerId;
		this.isValid = isValid;
	}




	public AbstractCard(Integer id, String batchNo, String no, Double amount, Date createDateTime, Date expireDateTime, Integer memberId,
			String account, CardStatus status, Date useDateTime, String provider, Integer providerId, Integer isValid, String owner, Integer ownerId) {
		super();
		this.id = id;
		this.batchNo = batchNo;
		this.no = no;
		this.amount = amount;
		this.createDateTime = createDateTime;
		this.expireDateTime = expireDateTime;
		this.memberId = memberId;
		this.account = account;
		this.status = status;
		this.useDateTime = useDateTime;
		this.provider = provider;
		this.providerId = providerId;
		this.isValid = isValid;
		this.owner = owner;
		this.ownerId = ownerId;
	}




	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public Date getExpireDateTime() {
		return expireDateTime;
	}

	public void setExpireDateTime(Date expireDateTime) {
		this.expireDateTime = expireDateTime;
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

	public CardStatus getStatus() {
		return status;
	}

	public void setStatus(CardStatus status) {
		this.status = status;
	}

	public Date getUseDateTime() {
		return useDateTime;
	}

	public void setUseDateTime(Date useDateTime) {
		this.useDateTime = useDateTime;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public Integer getProviderId() {
		return providerId;
	}

	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}




	public Integer getIsValid() {
		return isValid;
	}




	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}




	public String getOwner() {
		return owner;
	}




	public void setOwner(String owner) {
		this.owner = owner;
	}




	public Integer getOwnerId() {
		return ownerId;
	}




	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}




	
	
	
	
	
	
	
}
