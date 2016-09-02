<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>  
<%@taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

     System.out.println("--开始接收参数-----");

 
	String money =  request.getParameter("money"); 
	String sid =  request.getParameter("sid");
	String orderId =  request.getParameter("orderId");
	String cdkey = request.getParameter("cdkey");
	String respCode = request.getParameter("respCode");
	String payNo = request.getParameter("payNo");
 
     
     System.out.println("money=="+money);
     System.out.println("sid=="+sid);
     System.out.println("orderId=="+orderId);
     System.out.println("cdkey=="+cdkey);
     System.out.println("respCode=="+respCode);
     System.out.println("payNo=="+payNo); 
     
     System.out.println("--接收参数结束---");
     %>
     错误信息：<s:property value="errorMessage"/>

