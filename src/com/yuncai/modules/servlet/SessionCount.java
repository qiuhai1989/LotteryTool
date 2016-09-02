package com.yuncai.modules.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.yuncai.core.common.Constant;
import com.yuncai.core.tools.DateTools;
import com.yuncai.modules.lottery.model.Oracle.Member;


public class SessionCount extends HttpServlet implements HttpSessionListener {

	private static Map<String, HttpSession> list = new LinkedHashMap<String, HttpSession>();

	public void sessionCreated(HttpSessionEvent se) {

		list.put(se.getSession().getId(), se.getSession());

	}

	public void sessionDestroyed(HttpSessionEvent se) {

		list.remove(se.getSession().getId());

	}
	
	public static HttpSession getSession(String sessionId)
	{
		return list.get(sessionId);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String t = request.getParameter("t");
		String content = "在线用户数:" + list.size() + " 登录用户数：";
		int logined = 0;
		String uList = "";
		Object[] ol = list.values().toArray();
		for (int i = 0; i < ol.length; i++) {
			HttpSession s = (HttpSession) ol[i];
			if (s == null)
				continue;
			String line = "";
			Member m = null;
			try {
				line += "<br>" + s.getId().substring(0, 15) + " 创建：" + DateTools.dateToString(new Date(s.getCreationTime())) + " 最后："
						+ DateTools.dateToString(new Date(s.getLastAccessedTime()));
				line += " pc_id：" + s.getAttribute("s");
				m = (Member) s.getAttribute(Constant.MEMBER_LOGIN_SESSION);
			} catch (Exception e) {
				// e.printStackTrace();
			}
			if (m != null) {

				line += " 用户：" + m.getAccount();
				logined++;
				uList += line;
			} else {

				line += " 用户：------------";
				if ("1".equals(t)) {
					;
				} else {
					uList += line;
				}
			}

		}
		response.getWriter().write(content + logined + uList);
	}
}