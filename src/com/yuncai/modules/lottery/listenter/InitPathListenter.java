package com.yuncai.modules.lottery.listenter;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.yuncai.Config;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.modules.lottery.WebConstants;

/**
 * 初始化路径监听器
 * @author blackworm
 *
 */
public class InitPathListenter implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		String webroot = servletContextEvent.getServletContext().getRealPath("/");
		Config.FT_MATCH_PATH=webroot+"data\\";
		WebConstants.WEB_PATH=webroot+"/";
		LogUtil.out("拿到当前应用的绝对路径:" + WebConstants.WEB_PATH);
        //System.setProperty("webapp.root", webroot);
	}
	
}
