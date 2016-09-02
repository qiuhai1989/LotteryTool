package com.yuncai.modules.lottery.action.member;

import com.yuncai.core.common.Constant;
import com.yuncai.core.tools.DateTools;
import com.yuncai.core.tools.MD5;
import com.yuncai.core.tools.StringTools;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.model.Oracle.Member;
import com.yuncai.modules.lottery.service.oracleService.member.MemberService;

import java.util.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("memberRegAction")
@Scope("prototype")
public class MemberRegAction extends BaseAction {
	private String username;
	private String realName;
	private String certNo;// 证件号码
	private String password;
	private String mobile;
	private int amount = 0;
	private String provider;// 合作供应商(eg:支付宝)
	private String outUid;// 供应商方的用户id
	private String authCode;// 验证码
	private String message;
	private String smsAuthCode;
	private String email;
	
	@Resource
	private MemberService memberService;
	public String reg(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		
		//判断用户是否存在
		if (username != null && !"".equals(username)) {
			List list=memberService.findByProperty("account", username);
			if (list!= null && !list.isEmpty()) {
				super.errorMessage = "用户名已存在!";
				return renderTextHtml("0|"+this.errorMessage);
			}
		}
		String s_id = (String) request.getSession().getAttribute("s_id");
		String s = (String) request.getSession().getAttribute("s");
		if (s_id == null || s_id.length() < 1)
			s_id = "0";
		if (s == null || s.length() < 1)
			s = "0";

		String ip = super.remoteIp;
		Member member = new Member();
		member.setAccount(username);
		member.setCertNo(certNo);
		member.setEmail(email);
		member.setExprerience(new Integer(0));
		member.setLastLoginDateTime(new Date());
		member.setMobile(mobile);
		member.setName(realName);
		member.setPassword(MD5.encode(password));
		member.setRank(1);
		member.setRecommender(0);
		member.setRegisterDateTime(new Date());
		member.setSourceId(new Integer(s_id));
		member.setStatus(0);
		member.setSign(s);
		member.setProvider(provider);
		member.setOutUid(outUid);
		member.setPlayStatus(0);
		Long ct = 0L;
		try {
			String _s = super.getSession().get("s").toString();
			ct = Long.parseLong(_s);
		} catch (Exception e) {
		}
		
		//处理业务
		int operLineNo=0;
		try {
			//版本处理默认为8
			operLineNo=memberService.register(member, ct, super.remoteIp, "", "","","8","");
		} catch (Exception e) {
			operLineNo=0;
		}
		
		
		String client = "";// cookie
		try {
			javax.servlet.http.Cookie[] cookies = request.getCookies();
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("client")) {
					client = cookie.getValue();
				}
			}
		} catch (Exception e) {
		}

		HashMap map = new HashMap();
		if (!client.equals("")) {
			client = StringTools.DecodeBase64(client);
			map = StringTools.getParametersByContents(client);
		}
		map.put("reg_date", DateTools.getNowDateYYMMDD());

		Cookie clientCookie = new Cookie("client", StringTools.EncodeBase64(StringTools.getStringByHashMap(map)));
		clientCookie.setMaxAge(60 * 60 * 24 * 365);// cookie时间(保存一年)
		clientCookie.setPath("/");
		response.addCookie(clientCookie); // 添加clientCookie

		System.out.println("会员注册" + ip + " reg time:" + new Date());
		
		String message="";
		if(operLineNo!=0) message="1|注册成功！";
		else message="0|注册失败！";
		return renderTextHtml(message);
	}
	
	public static void main(String[] args) {
		String a="3F4F3733AECBC890ED200EE3C536E55A";
		String b="aaaaaa";
		String c=MD5.encode(b);
		System.out.println(c);
		System.out.println("a:"+a.length()+"  |   c:"+c.length());
		if(c.equals(a)){
			System.out.println("成功");
		}else{
			System.out.println("失败");
		}
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getCertNo() {
		return certNo;
	}
	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getOutUid() {
		return outUid;
	}
	public void setOutUid(String outUid) {
		this.outUid = outUid;
	}
	public String getAuthCode() {
		return authCode;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getSmsAuthCode() {
		return smsAuthCode;
	}
	public void setSmsAuthCode(String smsAuthCode) {
		this.smsAuthCode = smsAuthCode;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

}
