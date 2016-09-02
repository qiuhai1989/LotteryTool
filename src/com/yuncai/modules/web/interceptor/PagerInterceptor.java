package com.yuncai.modules.web.interceptor;

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.yuncai.modules.lottery.action.BaseAction;

public class PagerInterceptor extends AbstractInterceptor {

	private final static String PARAM_PAGE_NUMBER = "page";
	private static final Log logger = LogFactory.getLog(PagerInterceptor.class);

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		logger.debug("进行分页设置...");
		String result = invocation.invokeActionOnly();
		BaseAction action = (BaseAction) invocation.getAction();
		if (action.isNeedBuildPage()) {
			logger.debug("需要分页设置...");
			ActionContext ac = invocation.getInvocationContext();
			String actionName = ac.getName();
			Map params = ac.getParameters();
			int total = action.getTotal();
			int pageSize = action.getPageSize();
			int page = action.getPage();

			String forwardAction = "./" + actionName + ".php?";

			if (page == 0)
				page = 1;
			int pages = total / pageSize;
			if (pages * pageSize < total)
				pages++;

			if (params.size() > 0) {
				for (Iterator it = params.keySet().iterator(); it.hasNext();) {
					String param = (String) it.next();
					if (PARAM_PAGE_NUMBER.equals(param))
						continue;
					String[] values = (String[]) params.get(param);
					if (values[0] != null && !values[0].trim().equals(""))
						forwardAction += param + "=" + java.net.URLEncoder.encode(new String(values[0]), "GBK") + "&";
				}
			}

			StringBuffer pager = new StringBuffer();
			
			if (page > 1) {
				pager.append("<a href='" + forwardAction + "page=" + 1 + "'  >首页</a> ");
				pager.append("<a href='" + forwardAction + "page=" + (page - 1) + "' >上一页</a> ");
			} else {
				pager.append("<span>首页</span> ");
				pager.append("<span>上一页</span> ");
			}

			if (page < pages) {
				pager.append("<a href='" + forwardAction + "page=" + (page + 1) + "'>下一页</a> ");
				pager.append("<a href='" + forwardAction + "page=" + pages + "'>尾页</a> ");
			} else {
				pager.append("<span>下一页</span> ");
				pager.append("<span>尾页</span> ");
			}
			
			pager.append("&nbsp;转到第");
			pager.append("<input  onchange='window.location=\"" + forwardAction + "page=\" + this.value' value='"+page+"' style='width:2em'>页 ");
			
			pager.append("&nbsp;共 " + pages + "页&nbsp;" + total + "条记录&nbsp;每页显示 " + pageSize + "条&nbsp;&nbsp;");
			
			
			//重新构造
			String baseForwardAction = action.getForwardAction();
			if(!"".equals(baseForwardAction)){
				pager = new StringBuffer();
				pager.append("共:" + total + "条&nbsp;每页显示:" + pageSize + "条&nbsp;&nbsp;");
				pager.append("当前是第" + page + "页&nbsp;总共" + pages + "页&nbsp;&nbsp;");
				forwardAction = baseForwardAction;
				if (page > 1) {
					pager.append("<a href='" + forwardAction  + 1 + ");'  ><img src='http://img.cailele.com/img/images/ico_arrowleftC.gif' border='0'/></a> ");
					pager.append("<a href='" + forwardAction + (page - 1) + ");' ><img src='http://img.cailele.com/img/images/ico_arrowleftA.gif' border='0'/></a> ");
				} else {
					pager.append("<span><img src='http://img.cailele.com/img/images/ico_arrowleftC.gif' border='0'/></span> ");
					pager.append("<span><img src='http://img.cailele.com/img/images/ico_arrowleftA.gif' border='0'/></span> ");
				}

				if (page < pages) {
					pager.append("<a href='" + forwardAction +  (page + 1) + ");'><img src='http://img.cailele.com/img/images/ico_arrowrightA.gif' border='0'/></a> ");
					pager.append("<a href='" + forwardAction +  pages + ");'><img src='http://img.cailele.com/img/images/ico_arrowrightC.gif' border='0'/></a> ");
				} else {
					pager.append("<span><img src='http://img.cailele.com/img/images/ico_arrowrightA.gif' border='0'/></span> ");
					pager.append("<span><img src='http://img.cailele.com/img/images/ico_arrowrightC.gif' border='0'/></span> ");
				}
			}
			//改动结束
			
			
			action.setPagerView(pager.toString());

		}
		return result;
	}

}
