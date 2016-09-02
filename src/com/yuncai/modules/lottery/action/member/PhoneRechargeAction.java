package com.yuncai.modules.lottery.action.member;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.yuncai.core.common.Constant;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.model.Oracle.Member;

@Controller("PhoneRechargeAction")
@Scope("prototype")
public class PhoneRechargeAction extends BaseAction {
	HttpServletRequest request = ServletActionContext.getRequest();
	HttpServletResponse response = ServletActionContext.getResponse();
	private String money=null;
	private String mobile=null;
	private String company="0";
	private String password=null; 
	private String orderid=null;
	private String cdkey=null; 
	private String sid=null; 
	
	public String execute(){
		
		//验证登陆
		Member memberSession=(Member)request.getSession().getAttribute(Constant.MEMBER_LOGIN_SESSION);
		if(memberSession==null){
			String message="请先登录";
			return renderTextHtml(message);
		}
		
		//提交表单
		if(money!=null){
			//sid=memberSession.getId().toString();
			cdkey="234";
			orderid="456";
			
			try {
				response.sendRedirect("http://xxxx/ xxx.jsp ?money="+money+"&sid="+sid+"&orderid="+orderid +"&cdkey="+cdkey);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}

	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
