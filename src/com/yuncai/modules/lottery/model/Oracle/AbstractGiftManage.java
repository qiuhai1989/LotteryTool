package com.yuncai.modules.lottery.model.Oracle;
import java.util.Date;
/***
 * 红包管理
 * @author gx
 *
 */
public class AbstractGiftManage implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String giftName;
	private Platform platform;//平台表
	private Channel channel;//渠道表
	private Integer postType;//赠送方式：0自动，1手动
	private Integer giftType;//红包类型：0直接赠送金额，1按充值单元
	private Double giftMoney;//红包金额，单位元
	private Double giftRequest;//赠送要求，需要至少充值多少，单位元
	private String postLimit;//赠送限制
	private String info;//活动说明
	private String remark;//备注
	private Date startTime;//活动开始时间
	private Date endTime;//活动结束时间
	private Integer isValid;//启动
	private String topNumber; //字头
	private Integer type;//赠送类型 2注册，1充值
	public String getTopNumber() {
		return topNumber;
	}
	public void setTopNumber(String topNumber) {
		this.topNumber = topNumber;
	}
	public AbstractGiftManage() {
		super();
	}
	/*************************************/
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getGiftName() {
		return giftName;
	}
	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}
	public Integer getPostType() {
		return postType;
	}
	public void setPostType(Integer postType) {
		this.postType = postType;
	}
	public Integer getGiftType() {
		return giftType;
	}
	public void setGiftType(Integer giftType) {
		this.giftType = giftType;
	}
	public Double getGiftMoney() {
		return giftMoney;
	}
	public void setGiftMoney(Double giftMoney) {
		this.giftMoney = giftMoney;
	}
	public Double getGiftRequest() {
		return giftRequest;
	}
	public void setGiftRequest(Double giftRequest) {
		this.giftRequest = giftRequest;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Integer getIsValid() {
		return isValid;
	}
	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}
	public String getPostLimit() {
		return postLimit;
	}
	public void setPostLimit(String postLimit) {
		this.postLimit = postLimit;
	}
	public Platform getPlatform() {
		return platform;
	}
	public void setPlatform(Platform platform) {
		this.platform = platform;
	}
	public Channel getChannel() {
		return channel;
	}
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
}
