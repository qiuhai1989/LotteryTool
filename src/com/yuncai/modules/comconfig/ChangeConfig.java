package com.yuncai.modules.comconfig;

import java.util.Map;

public class ChangeConfig {
	
	private String urlpth;
	private String returnNotity;
	private String returnPath;
	private String key;
	private String loginPath;
	
	private Map<String, ChangeConfig> changeMap;

	private static ChangeConfig myself;
	static {
		myself = new ChangeConfig();
	}
	public String getReturnNotity() {
		return returnNotity;
	}

	public void setReturnNotity(String returnNotity) {
		this.returnNotity = returnNotity;
	}

	public String getReturnPath() {
		return returnPath;
	}

	public void setReturnPath(String returnPath) {
		this.returnPath = returnPath;
	}

	public static ChangeConfig getInstance() {
		return myself;
	}
	
	public Map<String, ChangeConfig > getChangeMap() {
		return changeMap;
	}
	public void setChangeMap(Map<String, ChangeConfig> changeMap) {
		this.changeMap = changeMap;
	}

	public String getUrlpth() {
		return urlpth;
	}

	public void setUrlpth(String urlpth) {
		this.urlpth = urlpth;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getLoginPath() {
		return loginPath;
	}

	public void setLoginPath(String loginPath) {
		this.loginPath = loginPath;
	}

	
	

}
