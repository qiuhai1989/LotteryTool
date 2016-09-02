package com.yuncai.modules.lottery.model.Oracle;

public class AbstractChannel implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer platformId;//平台ID
	private String name;//渠道名
	
	private String realName; //简称
	private String demo; //备注
	
	private int isHide;//是否隐藏 默认0显示 1隐藏
	
	public AbstractChannel() {
	}
	
	public AbstractChannel(Integer id,Integer platformId,String name){
		this.id=id;
		this.platformId=platformId;
		this.name=name;
	}
	
	public AbstractChannel(Integer id, Integer platformId, String name,
			String realName, String demo) {
		super();
		this.id = id;
		this.platformId = platformId;
		this.name = name;
		this.realName = realName;
		this.demo = demo;
	}

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

	public Integer getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Integer platformId) {
		this.platformId = platformId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getDemo() {
		return demo;
	}

	public void setDemo(String demo) {
		this.demo = demo;
	}

	public int getIsHide() {
		return isHide;
	}

	public void setIsHide(int isHide) {
		this.isHide = isHide;
	}

}
