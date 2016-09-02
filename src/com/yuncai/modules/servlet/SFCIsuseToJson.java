package com.yuncai.modules.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.yuncai.core.tools.CompressFile;
import com.yuncai.core.tools.DateTools;
import com.yuncai.modules.lottery.bean.vo.PackageSFCJsonForApk;
import com.yuncai.modules.lottery.model.Sql.IsuseForSFC;
import com.yuncai.modules.lottery.service.sqlService.fbmatch.IsuseForSFCService;

public class SFCIsuseToJson extends HttpServlet {

	private IsuseForSFCService isuseForSFCService;
	private Integer isusesId;
	// 定义Spring上下文环境
	private static ApplicationContext ctx = null;

	// 获取Spring上下文环境
	public Object getBean(String name) {
		if (ctx == null) {
			ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		}
		return ctx.getBean(name);
	}

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		isuseForSFCService = (IsuseForSFCService) getBean("isuseForSFCService");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		execute(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}

	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");

		try {
			PrintWriter out = resp.getWriter();
			
			String result;
			String version = req.getParameter("version");
			ObjectMapper mapper = new ObjectMapper();
			isusesId = Integer.parseInt(req.getParameter("isusesId"));
			List<IsuseForSFC> lists = isuseForSFCService.findByProperty("isusesId", isusesId);
			PackageSFCJsonForApk pApk = new PackageSFCJsonForApk();
			if (lists == null || lists.size() == 0) {
				pApk.setError("3001");
				pApk.setMsg(" 系统异常找不到数据");
			} else {
				pApk.setError("0");
				pApk.setMsg("响应正常");
			}
			
			
			pApk.setServerTime(DateTools.dateToString(new Date()));
			if(version!=null&&Integer.parseInt(version)==9){
				pApk.initDtMatch2(lists);
			}else if(version!=null&&Integer.parseInt(version)>=10){
				pApk.initDtMatch(lists);
//				pApk.initDtMatch2(lists);
			}else{
				pApk.initDtMatch2(lists);
			}
			
			result = mapper.writeValueAsString(pApk);
			byte[] compress = CompressFile.compress(result);
			result = Base64.encode(compress);
			out.write(result);
			out.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public Integer getIsusesId() {
		return isusesId;
	}

	public void setIsusesId(Integer isusesId) {
		this.isusesId = isusesId;
	}

}
