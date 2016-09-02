package com.yuncai.modules.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.yuncai.core.common.Constant;
import com.yuncai.modules.lottery.model.Oracle.Member;
import com.yuncai.modules.lottery.model.Oracle.MemberWallet;
import com.yuncai.modules.lottery.service.oracleService.member.MemberService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberWalletService;

public class DrawingServlet extends HttpServlet{
	// 定义Spring上下文环境
	private static ApplicationContext ctx = null;
	private MemberService memberService;
	private MemberWalletService memberWalletService;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		int sendInterval = 60;// 发送间隔(秒)
		int activeTime = 60;// 验证码有效时间(秒)
		String result = "";

		String action = req.getParameter("action"); //
		String mobile = req.getParameter("mobile"); //
		String authCode = req.getParameter("authCode"); //
		
		String amount = req.getParameter("amount");//提款金额
		
		String name = req.getParameter("name");//真是姓名
		memberService = (MemberService) getBean("memberService");
		memberWalletService = (MemberWalletService) getBean("memberWalletService");
		Member member = (Member) req.getSession().getAttribute(Constant.MEMBER_LOGIN_SESSION);
//		if(member==null){
//			
//		}
		MemberWallet wallet =  memberWalletService.findByAccount(member.getAccount());
		
		if(action.trim().equals("valid")){
			
		}
		
		super.doGet(req, resp);
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
