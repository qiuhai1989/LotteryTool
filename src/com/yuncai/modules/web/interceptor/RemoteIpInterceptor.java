package com.yuncai.modules.web.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.yuncai.modules.lottery.action.BaseAction;

public class RemoteIpInterceptor extends AbstractInterceptor {

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext ac = invocation.getInvocationContext();

		HttpServletRequest request = (HttpServletRequest) ac.get(ServletActionContext.HTTP_REQUEST);
		//String remoteIp = "";
		String ip = "";
		try {
			ip = request.getHeader("x-forwarded-for");
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
//			remoteIp = request.getHeader("Cdn-Src-Ip");
//			if (remoteIp == null) {
//				remoteIp = request.getHeader("x-forwarded-for");
//
//				if (remoteIp == null) {
//					remoteIp = "127.0.0.1";
//				} else {
//					remoteIp = remoteIp.split("\\,")[0];
//				}
//			}

		} catch (Exception e) {
			e.printStackTrace();
			//remoteIp = "127.0.0.1";
		}
		BaseAction action = (BaseAction) invocation.getAction();
		action.setRemoteIp(ip);
		return invocation.invoke();
		// String remoteIp = request.getRemoteAddr();

	}
}
