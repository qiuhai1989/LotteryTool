package com.yuncai.modules.lottery.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;
import com.opensymphony.xwork2.ActionSupport;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.core.tools.StringTools;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import javax.servlet.http.Cookie;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
/**
 * Action基本类
 * @author lm
 *
 */
public class BaseAction extends ActionSupport implements ServletContextAware, ServletResponseAware, ServletRequestAware, SessionAware {
	
	protected static final Logger logger = Logger.getLogger(BaseAction.class);
	
	public static final java.lang.String INDEX = "index";
	public static final java.lang.String LIST = "list";
	
	private static final long serialVersionUID = 1L;

	protected ServletContext servletContext;

	protected HttpServletRequest httpServletRequest;

	protected HttpServletResponse httpServletResponse;

	protected HttpSession httpSession;

	protected Map<String, Object> session;
	
	private String action = "index";

	protected static final String EDIT = "edit";

	protected static final String REDIRECT = "redirect";

	protected static final String FAILURE = "failure";

	protected static final String VIEW = "view";

	protected static final String AJAXJSON = "ajaxjson";

	protected static final String AJAXJSONTERM = "ajaxjsonterm";

	protected static final String GOBAL_SUCCESS = "gobalSuccess";

	// Other Define

	protected String forwardUrl = "";

	protected String errorMessage = "";

	protected String successMessage = "";

	protected String remoteIp = "";
	
	protected String channelID = "";// 渠道
	
	protected String sessionCode = ""; //投注防重

	protected String render(String text, String contentType) {
		HttpServletResponse response = null;
		PrintWriter printWriter = null;
		try {
			response = ServletActionContext.getResponse();
			response.setContentType(contentType);
			printWriter = response.getWriter();
			printWriter.write(text);
			printWriter.flush();
		} catch (IOException e) {
			logger.error(e);
		} finally {
			printWriter.close();
			printWriter = null;
		}
		return null;
	}
	
	protected String requestUrl;

	protected String forwardAction = "";

	// 用户cookie识别
	long s = 0; // 用户电脑终端标识，默认为0
	int s_id = 0; // 推广渠道编号，默认为0：直接输入云彩网址

	protected String executeMethod(String method) throws Exception {
		String sActionName = this.getClass().getSimpleName();
		String requestInfo = "请求:" + sActionName + " 方法:" + method;
		if( ! (sActionName.equals("DateAction") || sActionName.equals("LoginCheckAction")) ) {
			//LogUtil.out(requestInfo);
		}
		Class[] c = null;
		Method m = this.getClass().getMethod(method, c);
		Object[] o = null;
		String result = (String) m.invoke(this, o);
		if( ! (sActionName.equals("DateAction") || sActionName.equals("LoginCheckAction")) ) {
			//LogUtil.out(requestInfo + " 结果:" + result);
		}
		return result;
	}

	public String execute() {
		try {
			readCookie();
			return this.executeMethod(this.getAction());
		} catch (Throwable e) {
			e.printStackTrace();
			logger.error("ActionError", e);
			this.addActionError(this.getText("error.message"));
			this.errorMessage = "系统出错了，请联系客服！";
			return FAILURE;
		}
	}

	// 读取用户cookie中的相关标识
	private void readCookie() {
		try {
			HttpServletRequest request = this.getHttpServletRequest();
			if(request !=  null){
				javax.servlet.http.Cookie[] cookies = request.getCookies();

				if (cookies == null)
					return;

				for (Cookie cookie : cookies) {
					if (cookie.getName().equals("client")) {
						String client = cookie.getValue(); // 取得用户信息串，（已经加密）
						HashMap map = new HashMap();
						if (!StringTools.isNullOrBlank(client)) {
							client = StringTools.DecodeBase64(client); // 解码用户信息
							map = StringTools.getParametersByContents(client); // 拆分每条信息，放入map
							String _sid = (String) map.get("s_id");
							String _s = (String) map.get("s");
							try {
								s_id = _sid == null ? 0 : Integer.valueOf(_sid);
							} catch (Exception e) {
							}
							try {
								s = _s == null ? 0 : Long.parseLong(_s);
							} catch (Exception e) {
							}
						}
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
	/**
	 * 绕过Template,直接输出内容的简便函数,增加response.setCharacterEncoding方法
	 */
	protected String render(String text, String contentType,String character) {
		HttpServletResponse response = null;
		PrintWriter printWriter = null;
		try {
			response = ServletActionContext.getResponse();
			response.setContentType(contentType);
			response.setCharacterEncoding(character);
			printWriter = response.getWriter();
			printWriter.write(text);
			printWriter.flush();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			printWriter.close();
			printWriter = null;
		}
		return null;
	}

	/**
	 * 直接输出字符串.编码为GBK
	 * 
	 * @author zkr
	 */
	protected String renderText(String text) {
		return render(text, "text/plain;charset=GBK");
	}
	/**
	 * 直接输出字符串.编码为utf-8
	 * 
	 * @author zkr
	 */
	protected String renderTextHtml(String text) {
		return render(text, "text/plain;charset=utf-8");
	}

	/**
	 * 直接输出HTML.
	 * 
	 * @author zkr
	 */
	protected String renderHtml(String html) {
		return render(html, "text/html;charset=GBK");
	}

	/**
	 * 直接输出XML.
	 * 
	 * @author zkr
	 */
	protected String renderXML(String xml) {
		return render(xml, "text/xml;charset=GBK");
	}

	/**
	 * 输出JSON字符串
	 * 
	 * @param obj
	 * @author zkr
	 */
	protected String renderJson(Object obj) {
		return renderJson(obj, null);
	}

	/**
	 * 输出JSON字符串
	 * 
	 * @param obj
	 * @param jsonConfig
	 * @return
	 * @author zkr
	 */
	protected String renderJson(Object obj, JsonConfig jsonConfig) {
		if (obj instanceof Object[] || obj instanceof Collection) {
			if (jsonConfig != null) {
				return renderText(JSONArray.fromObject(obj, jsonConfig).toString());
			} else {
				return renderText(JSONArray.fromObject(obj).toString());
			}
		} else {
			if (jsonConfig != null) {
				return renderText(JSONObject.fromObject(obj, jsonConfig).toString());
			} else {
				return renderText(JSONObject.fromObject(obj).toString());
			}
		}
	}
	
	// 分页功能
	protected int total;
	protected int pageSize = 20;
	protected int page = 1;
	protected boolean isNeedBuildPage = false;
	private String pagerView = "none";

	protected String renderPager(String text, String contentType) {
		HttpServletResponse response = null;
		PrintWriter printWriter = null;
		try {
			response = ServletActionContext.getResponse();
			response.setContentType(contentType);
			printWriter = response.getWriter();
			printWriter.write(text);
			printWriter.flush();
		} catch (IOException e) {
			logger.error(e);
		} finally {
			printWriter.close();
			printWriter = null;
		}
		return null;
	}
	
	
	 public String getIpAddr(HttpServletRequest request) {
		    String ip = request.getHeader("x-forwarded-for");
		    if(ip == null || ip.length() == 0 ||"unknown".equalsIgnoreCase(ip)) {
		        ip = request.getHeader("Proxy-Client-IP");
		    }
		    if(ip == null || ip.length() == 0 ||"unknown".equalsIgnoreCase(ip)) {
		        ip = request.getHeader("WL-Proxy-Client-IP");
		    }
		    if(ip == null || ip.length() == 0 ||"unknown".equalsIgnoreCase(ip)) {
		        ip = request.getRemoteAddr();
		    }
		    return ip;
		 }
	
	public void setServletContext(ServletContext context) {
		this.servletContext = context;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.httpServletResponse = response;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.httpServletRequest = request;
		this.httpSession = request.getSession();
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	public Map<String, Object> getSession() {
		return session;
	}

	public HttpServletRequest getHttpServletRequest() {
		return httpServletRequest;
	}

	public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
		this.httpServletRequest = httpServletRequest;
	}

	public HttpServletResponse getHttpServletResponse() {
		return httpServletResponse;
	}

	public void setHttpServletResponse(HttpServletResponse httpServletResponse) {
		this.httpServletResponse = httpServletResponse;
	}

	public HttpSession getHttpSession() {
		return httpSession;
	}

	public void setHttpSession(HttpSession httpSession) {
		this.httpSession = httpSession;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getForwardUrl() {
		return forwardUrl;
	}

	public void setForwardUrl(String forwardUrl) {
		this.forwardUrl = forwardUrl;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getSuccessMessage() {
		return successMessage;
	}

	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}

	public String getRemoteIp() {
		return remoteIp;
	}

	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public String getForwardAction() {
		return forwardAction;
	}

	public void setForwardAction(String forwardAction) {
		this.forwardAction = forwardAction;
	}

	public long getS() {
		return s;
	}

	public void setS(long s) {
		this.s = s;
	}

	public int getS_id() {
		return s_id;
	}

	public void setS_id(int s_id) {
		this.s_id = s_id;
	}

	public ServletContext getServletContext() {
		return servletContext;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public boolean isNeedBuildPage() {
		return isNeedBuildPage;
	}

	public void setNeedBuildPage(boolean isNeedBuildPage) {
		this.isNeedBuildPage = isNeedBuildPage;
	}

	public String getPagerView() {
		return pagerView;
	}

	public void setPagerView(String pagerView) {
		this.pagerView = pagerView;
	}
	public String getChannelID() {
		return channelID;
	}

	public void setChannelID(String channelID) {
		this.channelID = channelID;
	}

	public String getSessionCode() {
		return sessionCode;
	}

	public void setSessionCode(String sessionCode) {
		this.sessionCode = sessionCode;
	}
}