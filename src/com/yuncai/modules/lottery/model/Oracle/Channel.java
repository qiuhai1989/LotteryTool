package com.yuncai.modules.lottery.model.Oracle;

public class Channel extends AbstractChannel implements java.io.Serializable {
	
	public Channel(){}
	
	public Channel(Integer id,Integer platformId,String name){
		super(id, platformId, name);
	}
}
