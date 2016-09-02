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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.yuncai.core.tools.CompressFile;
import com.yuncai.core.tools.DateTools;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.core.tools.StringTools;
import com.yuncai.modules.lottery.bean.vo.BBOuterPassRate;
import com.yuncai.modules.lottery.bean.vo.OuterPassRate;
import com.yuncai.modules.lottery.model.Oracle.BkSpRate;
import com.yuncai.modules.lottery.model.Sql.PassRate;
import com.yuncai.modules.lottery.service.oracleService.bkmatch.BkBetRateRatioService;
import com.yuncai.modules.lottery.service.oracleService.bkmatch.BkImsMatch500Service;
import com.yuncai.modules.lottery.service.oracleService.bkmatch.BkMatchBet365Service;
import com.yuncai.modules.lottery.service.oracleService.fbmatch.BkSpRateService;

public class BasketBallMatchServlet extends HttpServlet{
	private BkSpRateService bkSpRateService;
	private BkMatchBet365Service bkMatchBet365Service;
	private BkBetRateRatioService bkBetRateRatioService;
	private BkImsMatch500Service bkImsMatch500Service;
	// 定义Spring上下文环境
	private static ApplicationContext ctx = null;
	// 获取Spring上下文环境
	public Object getBean(String name) {

		return ctx.getBean(name);
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

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		if (ctx == null) {
			ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		}
		bkSpRateService = (BkSpRateService) getBean("bkSpRateService");
		bkMatchBet365Service = (BkMatchBet365Service) getBean("bkMatchBet365Service");
		bkBetRateRatioService = (BkBetRateRatioService) getBean("bkBetRateRatioService");
		bkImsMatch500Service = (BkImsMatch500Service) getBean("bkImsMatch500Service");
	}

	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
	    PrintWriter out = resp.getWriter();
	    List<Object[]>lists = null;
		BBOuterPassRate bigPassRate = new BBOuterPassRate();
//		String version = req.getParameter("version");
		String date = req.getParameter("date");
		String version = req.getParameter("version");
		String keys  = bkSpRateService.findMatchKey();
		
		//20140324 默认取当前天的赛事 如果当前天没有则顺延从keys排序后第一天开始取
		
		if(keys==null||keys.trim().equals("")){
			isAbleBet(null, bigPassRate);
			lists = null;
		}else {
			
			keys = SortAbleBetDayString(keys);
			
			if(Integer.parseInt(version)>=8){
				String nowDateStr = DateTools.getNowDateYYYYMMDD();	
				lists = bkSpRateService.findCurrentPassRate((StringTools.isNullOrBlank(date)?null:Integer.parseInt(date)),(keys.indexOf(nowDateStr)>=0?Integer.parseInt(nowDateStr):Integer.parseInt(keys.split(",")[0])));
			}else{
				
				lists = bkSpRateService.findCurrentPassRate((StringTools.isNullOrBlank(date)?null:Integer.parseInt(date)),Integer.parseInt(keys.split(",")[0]));
			}
			isAbleBet(lists, bigPassRate);
		}
		

		
		


		bigPassRate.setServerTime(DateTools.dateToString(new Date()));
		bigPassRate.initDtMatch(lists,bigPassRate,bkMatchBet365Service,bkBetRateRatioService,bkImsMatch500Service);	
		
//		LogUtil.out("keys="+keys);
		bigPassRate.setKeys(keys);
		
		ObjectMapper mapper = new ObjectMapper();
		String result = mapper.writeValueAsString(bigPassRate);
		
		byte[] compress = CompressFile.compress(result);
		result = Base64.encode(compress);

		out.write(result);
		out.close();
	}

	public void setBkSpRateService(BkSpRateService bkSpRateService) {
		this.bkSpRateService = bkSpRateService;
	}

	public static void setCtx(ApplicationContext ctx) {
		BasketBallMatchServlet.ctx = ctx;
	}
	
	/**
	 * 判断是否有比赛可投,并组装相应响应信息
	 * 
	 * @param objs
	 * @param bigPassRate
	 */
	public void isAbleBet(List<Object[]> objs, BBOuterPassRate bigPassRate) {
		if (objs == null || objs.size() == 0) {
			bigPassRate.setError("3001");
			bigPassRate.setMsg("无可投注比赛");
		} else {
			bigPassRate.setError("0");
			bigPassRate.setMsg("响应正常");
		}
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
	
	
}
