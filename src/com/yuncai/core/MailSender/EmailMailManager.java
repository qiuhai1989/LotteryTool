package com.yuncai.core.MailSender;

import com.yuncai.core.tools.LogUtil;



public class EmailMailManager {
	private MailBean emailBean;
	
	private static EmailMailManager emailMailManager;

	public void setEmailBean(MailBean emailBean) {
		this.emailBean = emailBean;
	}

	/**
	 * 初始化方法，spring实例化本类实体时被调用
	 */
	public final void init() {
		emailMailManager = this;
	}
	
	/**
	 * 处理发送邮件
	 * @param subject
	 * @param text
	 */
	public static void sendMail(String subject,String text){
		String serverHost=emailMailManager.emailBean.getServerHost();
		String userName=emailMailManager.emailBean.getUserName();
		String passWord=emailMailManager.emailBean.getPassWrod();
		String toAddress=emailMailManager.emailBean.getToAddress();
		javaMail mail=new javaMail();
		//处理发送
		try {
			mail.sendMail(serverHost, userName, passWord, userName, toAddress, subject, text);
		} catch (Exception e) {
			LogUtil.out("邮件发送失败!");
			//e.printStackTrace();
		}
		
	}

	public static void setEmailMailManager(EmailMailManager emailMailManager) {
		EmailMailManager.emailMailManager = emailMailManager;
	}
	

	
}
