package com.yuncai.modules.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.yuncai.core.tools.DateTools;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.core.util.HttpUtil;
import com.yuncai.modules.lottery.model.Oracle.Member;
import com.yuncai.modules.lottery.service.oracleService.member.MemberService;

/**
 * 发送短信验证码，以及验证码的验证
 * 调用格式: 发送：/servlet/SmsAuth?action=send&mobile=xxxxxxx
 *         验证：/servlet/SmsAuth?action=auth&authCode=xxxxxxx
 *         同一session 1分钟只能发送一次， 验证码错误30次后失效
 * @author qiuhai
 *
 */
public class SmsAuthServletForTakeMoneyBefore5 extends HttpServlet{
	// 定义Spring上下文环境
	private static ApplicationContext ctx = null;
	private MemberService memberService;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int sendInterval = 60;// 发送间隔(秒)
		int activeTime = 60;// 验证码有效时间(秒)
		String result = "";

		String action = req.getParameter("action"); //
		String mobile = req.getParameter("mobile"); //
		String authCode = req.getParameter("authCode"); //
		
	     //取得Application对象 
		 ServletContext application=this.getServletContext();  
		 Long lastSendTime = (Long) application.getAttribute(mobile+"lastSendTime");
		 LogUtil.out(mobile+"lastSendTime");
		if(lastSendTime!=null){
			LogUtil.out(DateTools.dateToString(new Date(lastSendTime)));
		}
		
		if(action.equals("send")){
			
				if (lastSendTime == null || System.currentTimeMillis() - lastSendTime.longValue() > sendInterval * 1000) {// 第一次发送或者超过发送间隔
					Random random = new Random();
					String sRand = "";
					for (int i = 0; i < 4; i++) {
						String rand = String.valueOf(random.nextInt(10));
						sRand += rand;
					}
//					LogUtil.out("验证码"+sRand);
					//获取网站的域名
					String serverName = req.getServerName();
					String webName = "";
					if(serverName != null && !"".equals(serverName)){
						webName = "云彩网";
					}
					String content = "尊敬的"+webName+"用户，您的验证码为：" + sRand + "。请勿将验证码告诉他人并在使用后删除此短信！-【"+webName+"】";
					//发送验证信息
					Boolean tag = HttpUtil.SmsSend(mobile,content);
					
					if(tag){
						result = "发送成功！";
//						LogUtil.out(result);
						
						application.setAttribute(mobile+"SmsAuthCode", sRand);
						application.setAttribute(mobile+"lastSendTime", new Long(System.currentTimeMillis()));
						application.setAttribute(mobile+"retryCount", 1);
					}else {
						result = "发送失败，请稍后再试一次！";
					}
					
					
				}else {
					result = "发送间隔太短，请稍后再试！";
				}
			

		}else if(action.equals("auth")){// 验证码重试30次失效
			final int retryLimit = 5;
			Integer retryCount = (Integer) application.getAttribute(mobile+"retryCount");
//			LogUtil.out("retryCount="+retryCount);
			
			if(lastSendTime==null){
				result = "0";
			}
			//验证码超时失效
//			LogUtil.out(DateTools.dateToString(new Date(System.currentTimeMillis())));
//			LogUtil.out(DateTools.dateToString(new Date(lastSendTime.longValue())));
//			LogUtil.out(System.currentTimeMillis() - lastSendTime.longValue());
			if(lastSendTime!=null&&(System.currentTimeMillis() - lastSendTime.longValue()>activeTime*1000)){
				// 清除本次验证码，返回失败
				application.setAttribute(mobile+"SmsAuthCode", null);
				application.setAttribute(mobile+"retryCount", null);
				result = "false";
			}
			
			if (retryCount == null || retryCount.intValue() < retryLimit) {
				String authCodeSession = (String) application.getAttribute(mobile+"SmsAuthCode");
				if (authCode != null && authCodeSession != null && authCode.equals(authCodeSession)) {
					result = "1";// +retryCount;
					//验证通过清除相关内容
					application.removeAttribute(mobile+"SmsAuthCode");
					application.removeAttribute(mobile+"retryCount");
					application.removeAttribute(mobile+"lastSendTime");

					
				} else {
					result = "0";// +retryCount;
					application.setAttribute(mobile+"retryCount", new Integer(retryCount == null ? 0 : ++retryCount));
				}
			} else {
				// 清除本次验证码，返回失败
				application.setAttribute(mobile+"SmsAuthCode", null);
				application.setAttribute(mobile+"retryCount", null);
				result = "false";

			}
			
		}
		LogUtil.out("result="+result);
		resp.getWriter().write(result);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}

	// 获取Spring上下文环境
	public Object getBean(String name) {
		if (ctx == null) {
			ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		}
		return ctx.getBean(name);
	}
	
}
