package com.yuncai.modules.lottery.software.service;

import java.util.Map;

public class ProcessSoftware {
	
	private Map<String, SoftwareProcess> processMap;

	private static ProcessSoftware myself;
	static {
		myself = new ProcessSoftware();
	}

	public static ProcessSoftware getInstance() {
		return myself;
	}

	public Map<String, SoftwareProcess> getProcessMap() {
		return processMap;
	}

	public void setProcessMap(Map<String, SoftwareProcess> processMap) {
		this.processMap = processMap;
	}

}
