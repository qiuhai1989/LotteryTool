package com.yuncai.modules.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.yuncai.core.common.Constant;
import com.yuncai.core.tools.CompressFile;
import com.yuncai.modules.lottery.bean.vo.ChampionMatchAgainst;
import com.yuncai.modules.lottery.model.Oracle.WorldCupTeam;
import com.yuncai.modules.lottery.service.oracleService.worldcup.WorldCupTeamService;

public class WorldCupChampionServlet extends HttpServlet {
	// 定义Spring上下文环境
	private static ApplicationContext ctx = null;
	private WorldCupTeamService worldCupTeamService;
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
		if (ctx == null) {
			ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		}
		worldCupTeamService=(WorldCupTeamService)getBean("worldCupTeamService");
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
		PrintWriter out = resp.getWriter();
		ChampionMatchAgainst champion = new ChampionMatchAgainst();
		List<WorldCupTeam>teams= worldCupTeamService.findTeams();
		//给图片路径加上线上地址
		for(WorldCupTeam team:teams){
			team.setIconsrc(Constant.DOMAIN_NAME+team.getIconsrc());
		}
		champion.setTeams(teams);
		ObjectMapper mapper = new ObjectMapper();
		String result = mapper.writeValueAsString(champion);

		byte[] compress = CompressFile.compress(result);
		result = Base64.encode(compress);

		out.write(result);
		out.close();
	}

	public void setWorldCupTeamService(WorldCupTeamService worldCupTeamService) {
		this.worldCupTeamService = worldCupTeamService;
	}
	
	
}
