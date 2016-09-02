package com.yuncai.modules.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.yuncai.core.tools.CompressFile;
import com.yuncai.core.tools.DateTools;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.core.tools.StringTools;
import com.yuncai.modules.lottery.bean.vo.OuterPassRate;
import com.yuncai.modules.lottery.model.Sql.PassRate;
import com.yuncai.modules.lottery.service.oracleService.fbmatch.FtImsJcService;
import com.yuncai.modules.lottery.service.oracleService.fbmatch.FtMatchService;
import com.yuncai.modules.lottery.service.oracleService.weixin.FtMatchCommentService;
import com.yuncai.modules.lottery.service.sqlService.fbmatch.PassRateService;
import com.yuncai.modules.lottery.service.sqlService.fbmatch.SPYaPanService;

public class FTMatchServlet extends HttpServlet {

	private PassRateService passRateService;

	private SPYaPanService sYaPanService;

	private FtMatchService ftMatchService;

	private FtMatchCommentService ftMatchCommentService;
	private FtImsJcService ftImsJcService;

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
		passRateService = (PassRateService) getBean("passRateService");
		sYaPanService = (SPYaPanService) getBean("sYaPanService");
		ftMatchService = (FtMatchService) getBean("ftMatchService");
		ftMatchCommentService = (FtMatchCommentService) getBean("ftMatchCommentService");
		ftImsJcService = (FtImsJcService) getBean("ftImsJcService");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		execute(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		execute(req, resp);
	}

	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		// LogUtil.out(resp.getCharacterEncoding());
		PrintWriter out = resp.getWriter();
		List<PassRate> lists = null;
		OuterPassRate bigPassRate = new OuterPassRate();
		String version = req.getParameter("version");
		String date = req.getParameter("date");
		final List<Object[]> objs = null;
		
		if (version == null || Integer.parseInt(version) < 5) {
			bigPassRate.setError("1");
			bigPassRate.setMsg("当前版本不支持竞彩足球，请在[个人设置]中升级后使用。详询400-850-6636");
			lists = new ArrayList<PassRate>();
		} else {


			String keys = ftMatchService.findMatchKey();// 20140323,20140324,20140325

			if (keys == null || keys.trim().equals("")) {
//				objs = null;
				isAbleBet(null, bigPassRate);
			} else {
				
				initBigPassRate(keys, version, bigPassRate, date);

			}

		}

		 
		out.write(finalAssembleString(bigPassRate));
		out.close();
		// LogUtil.out(System.currentTimeMillis()-l);
	}

	public void setSYaPanService(SPYaPanService yaPanService) {
		sYaPanService = yaPanService;
	}

	public void setFtMatchService(FtMatchService ftMatchService) {
		this.ftMatchService = ftMatchService;
	}

	public void setPassRateService(PassRateService passRateService) {
		this.passRateService = passRateService;
	}

	/**
	 * 排序并返回可投注日期字符
	 * 
	 * @return
	 */
	public String SortAbleBetDayString(String keys) {
		String result = keys;

		String[] arr = keys.split(",");
		int[] arrs = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			arrs[i] = Integer.parseInt(arr[i]);
		}
		Arrays.sort(arrs);
		String tmp = Arrays.toString(arrs);
		result = tmp.substring(1, tmp.length() - 1).replaceAll(" ", "");

		return result;
	}

	/**
	 * 判断是否有比赛可投,并组装相应响应信息
	 * 
	 * @param objs
	 * @param bigPassRate
	 */
	public void isAbleBet(List<Object[]> objs, OuterPassRate bigPassRate) {
		if (objs == null || objs.size() == 0) {
			bigPassRate.setError("3001");
			bigPassRate.setMsg("无可投注比赛");
		} else {
			bigPassRate.setError("0");
			bigPassRate.setMsg("响应正常");
		}
	}
	
	/**组装最终响应字符串
	 * @param bigPassRate
	 * @return
	 * @throws JsonProcessingException
	 */
	public String finalAssembleString(OuterPassRate bigPassRate) throws JsonProcessingException{
		String result=null;
		bigPassRate.setServerTime(DateTools.dateToString(new Date()));

		ObjectMapper mapper = new ObjectMapper();
		 result = mapper.writeValueAsString(bigPassRate);

		byte[] compress = CompressFile.compress(result);
		result = Base64.encode(compress);
		return result;
	}
	
	/**初始化 OuterPassRate 即bigPassRate
	 * @param keys 可投注日期字符串
	 * @param version 
	 * @param bigPassRate 待初始化对象
	 * @param date 需要显示的制定date的对阵信息
	 */
	public void initBigPassRate(String keys,String version,OuterPassRate bigPassRate,String date){
		List<Object[]> objs=null;
//		OuterPassRate tempBigPassRate = bigPassRate;
		// 20140324 默认取当前天的赛事 如果当前天没有则顺延从keys排序后第一天开始取
		
		keys = SortAbleBetDayString(keys);
		
		if (Integer.parseInt(version) >= 8) {
			String nowDateStr = DateTools.getNowDateYYYYMMDD();
			objs = ftMatchService.findCurrentMatch((StringTools.isNullOrBlank(date) ? null : Integer.parseInt(date)), (keys
					.indexOf(nowDateStr) >= 0 ? Integer.parseInt(nowDateStr) : Integer.parseInt(keys.split(",")[0])));
			nowDateStr = null;
			
			bigPassRate.initDtMatch(objs, bigPassRate, ftMatchCommentService, ftImsJcService, sYaPanService);
		}

		if (Integer.parseInt(version) == 7 || Integer.parseInt(version) == 6) {
			objs = ftMatchService.findCurrentMatch((StringTools.isNullOrBlank(date) ? null : Integer.parseInt(date)), Integer.parseInt(keys
					.split(",")[0]));
			bigPassRate.initDtMatch(objs, bigPassRate, ftMatchCommentService, ftImsJcService, sYaPanService);
		}

		if (Integer.parseInt(version) == 5) {
			// lists = passRateService.findCurrentPassRate();
			objs = ftMatchService.findCurrentMatch();

			bigPassRate.initDtMatch(objs, bigPassRate);
		}
		isAbleBet(objs, bigPassRate);
		bigPassRate.setKeys(keys);
	}

}
