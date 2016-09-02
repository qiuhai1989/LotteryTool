package com.yuncai.modules.lottery.software.service;
import java.util.*;

public class CommonConfigFactory {
	public  Map<String, CommonConfig> commonConfigMap;

    public Map<String, CommonConfig> getCommonConfigMap() {
		return commonConfigMap;
	}

	public void setCommonConfigMap(Map<String, CommonConfig> commonConfigMap) {
		this.commonConfigMap = commonConfigMap;
	}

	public CommonConfig getCommonConfig(String domain){
  	  return commonConfigMap.get(domain);
    }

}
