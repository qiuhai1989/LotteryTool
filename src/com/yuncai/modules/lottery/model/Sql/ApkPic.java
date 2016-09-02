package com.yuncai.modules.lottery.model.Sql;

import java.util.Date;

/***
 * 广告图管理
 * @author gx
 *
 */
public class ApkPic implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private Integer isValid=0 ;//客户端显示：0不显示，1显示，默认0
	private String pciGroup;//图片分组描述
	private Integer bandpos;//位置编号，例1001首位为组别 后3图序
	private Integer jumpType = 1;//站内:A 代表合买方案 B充值 C中奖单 D彩种 E活动界面 站外:F 调用浏览器访问URL 文本:G 显示内容
	private String adContext;//类型内容，受Jump_Type
	private String imageAdd;//图片地址
	private String imageAddB;//大图
	private String imageAddS;//小图
	private Date updtime;//修改时间
	private String updcode;//更新标识，更新时间转换成唯一的字符串
	private int code;//编号ID
	public ApkPic() {
		super();
	}
	/*****************************************/
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public String getPciGroup() {
		return pciGroup;
	}

	public void setPciGroup(String pciGroup) {
		this.pciGroup = pciGroup;
	}

	public Integer getBandpos() {
		return bandpos;
	}

	public void setBandpos(Integer bandpos) {
		this.bandpos = bandpos;
	}

	public Integer getJumpType() {
		return jumpType;
	}

	public void setJumpType(Integer jumpType) {
		this.jumpType = jumpType;
	}

	public String getAdContext() {
		return adContext;
	}

	public void setAdContext(String adContext) {
		this.adContext = adContext;
	}

	public Date getUpdtime() {
		return updtime;
	}

	public void setUpdtime(Date updtime) {
		this.updtime = updtime;
	}

	public String getUpdcode() {
		return updcode;
	}

	public void setUpdcode(String updcode) {
		this.updcode = updcode;
	}
	public String getImageAdd() {
		return imageAdd;
	}
	public void setImageAdd(String imageAdd) {
		this.imageAdd = imageAdd;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getImageAddB() {
		return imageAddB;
	}
	public void setImageAddB(String imageAddB) {
		this.imageAddB = imageAddB;
	}
	public String getImageAddS() {
		return imageAddS;
	}
	public void setImageAddS(String imageAddS) {
		this.imageAddS = imageAddS;
	}
	
}
