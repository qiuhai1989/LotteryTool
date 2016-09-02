package com.yuncai.modules.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import com.yuncai.core.tools.CompressFile;
import com.yuncai.core.tools.DateTools;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.modules.lottery.bean.vo.EasyEbData;
import com.yuncai.modules.lottery.bean.vo.EasyLottery;
import com.yuncai.modules.lottery.bean.vo.EasyLotteryMatch;
import com.yuncai.modules.lottery.model.Oracle.BkMatch;
import com.yuncai.modules.lottery.model.Oracle.EasyLotteryRecommend;
import com.yuncai.modules.lottery.model.Oracle.EasyLotteryType;
import com.yuncai.modules.lottery.model.Oracle.FtMatch;
import com.yuncai.modules.lottery.service.oracleService.easy.EasyLotteryRecommendService;
import com.yuncai.modules.lottery.service.oracleService.easy.EasyLotteryTypeService;
import com.yuncai.modules.lottery.service.oracleService.lottery.LotteryPlanService;
import com.yuncai.modules.lottery.service.sqlService.lottery.IsusesService;

public class EasyLotteryServlet extends HttpServlet {
	private EasyLotteryRecommendService easyLotteryRecommendService;
	private IsusesService sqlIsusesService;
	private LotteryPlanService lotteryPlanService;
	private EasyLotteryTypeService easyLotteryTypeService;
	// 定义Spring上下文环境
	private static ApplicationContext ctx = null;
	private int count_1=0;//足球稳健型
/*	private int count_2=0;//足球高回报
	private int count_3=0;//足球实力型
	private int count_4=0;//篮球先锋
	private int count_5=0;//篮球实力
	private int count_6=0;//篮球高回报
*/	
	private int  lastTime=0;
	
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
		easyLotteryRecommendService = (EasyLotteryRecommendService) getBean("easyLotteryRecommendService");
		sqlIsusesService = (IsusesService) getBean("sqlIsusesService");
		lotteryPlanService = (LotteryPlanService) getBean("lotteryPlanService");
		 
	}

	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		PrintWriter out = resp.getWriter();
		List<Object[]> lists = null;
		Integer minPos = null;
		String lid = req.getParameter("lid");
		EasyLottery easyLottery = new EasyLottery();
		
		Calendar calendar = Calendar.getInstance();
		int nowNum = calendar.get(Calendar.DAY_OF_MONTH);
		if(nowNum!=lastTime){
			 count_1=0;//足球稳健型
/*			 count_2=0;//足球高回报
			count_3=0;//足球实力型
			 count_4=0;//篮球先锋
			 count_5=0;//篮球实力
			count_6=0;//篮球高回报
*/			
		}
		lastTime=nowNum;
		
		
		
			 Random random = new Random();
		try {
			if (lid != null && lid.equals("72")) {
				String term = sqlIsusesService.findByProperty("lotteryId", 72).get(0).getId().toString();
				easyLottery.setTerm(term);
				lists = easyLotteryRecommendService.findRecommendFtInformation();
				//获取高命中
				minPos = easyLotteryRecommendService.findMinPos(72);
				Map<String, List<Object[]>> map = new HashMap<String, List<Object[]>>();
				// 将不同的recommend类型来分组
				for (Object[] aObj : lists) {
					EasyLotteryRecommend recommend = (EasyLotteryRecommend) aObj[0];
					EasyLotteryType type = (EasyLotteryType) aObj[2];
					if (map.get(recommend.getTypeId().toString()) != null) {
						List<Object[]> list = map.get(recommend.getTypeId().toString());
						list.add(aObj);
					} else {
						List<Object[]> list = new ArrayList<Object[]>();
						list.add(aObj);
						map.put(recommend.getTypeId().toString(), list);
					}

				}
				// 遍历分组好的比赛
				Iterator<Entry<String, List<Object[]>>> iter = map.entrySet().iterator();

				while (iter.hasNext()) {
					Map.Entry<String, List<Object[]>> entry = (Map.Entry) iter.next();
					List<Object[]> objs = entry.getValue();
					EasyEbData easyEb = new EasyEbData();
					//当某一类型赛事小于2场时候不显示
					if(objs.size()<=1){
						continue;
					}
					
					
					String s = ((EasyLotteryType) objs.get(0)[2]).getSlogan();
					String aim = objs.size() > 0 ? getNumInString(s).get(0) : "70";
					int typeId=((EasyLotteryRecommend)objs.get(0)[0]).getTypeId();
//					EasyLotteryType type = ((EasyLotteryType) objs.get(0)[2]);
					// 购买人数
					Integer count = objs.size() > 0 ? (lotteryPlanService.getEasyBuyCountByEasyType(((EasyLotteryRecommend) objs.get(0)[0])
							.getTypeId())) : 0;
//					count = floatAdd(count);
					if(((EasyLotteryType) objs.get(0)[2]).getPos()==(int)minPos){
						if(random.nextInt(10)>=9){
							count_1++;		
						}
						count+=count_1;
					}
					
					String title = objs.size()>0? ((EasyLotteryType) objs.get(0)[2]).getSlogan():"";
//					title="";
					easyEb.setTitle(processTitle(title, count));
//					easyEb.setTitle();
					//乱序以达到随机取两场比赛的目的
					Collections.shuffle(objs);
					int temp=0;//用于计数
					FtMatch tempMatch=null;//用于存储集合第一场比赛信息
					for (int i=0;i<objs.size();i++) {
//						/Object[] aObj : objs
						if(temp>=2){
							break;
						}
						
						Object[] aObj = objs.get(i);
						EasyLotteryRecommend recommend = (EasyLotteryRecommend) aObj[0];
						FtMatch match = (FtMatch) aObj[1];
//						System.out.println(recommend.getTypeId()+"添加一个");
						if(i>=1){
							//判断是否 与已取出的第一场比赛是同一场比赛
							if(match.getId().equals(tempMatch.getId())){
								continue;
							}
						}else{
							tempMatch = match;
						}
						EasyLotteryType type = (EasyLotteryType) aObj[2];
						
						
						EasyLotteryMatch eEmatch = new EasyLotteryMatch();
						eEmatch.setMid(match.getMid().toString());
						eEmatch.setMatchNo(match.getMatchNo());
						// 为跟web统一 matchid 用sql的
						eEmatch.setMatchId(match.getSqlId().toString());
						eEmatch.setLineId(match.getLineId());
						eEmatch.setIntTime(match.getIntTime().toString());
						eEmatch.setMTeam(match.getMbName());
						eEmatch.setGTeam(match.getTgName());
						eEmatch.setGame(match.getLeagueName());
						eEmatch.setZqRq(recommend.getRq().toString());
						eEmatch.setLqRf(recommend.getRf() + "");
						eEmatch.setLqZf(recommend.getDxf() + "");
						eEmatch.setSp(recommend.getAward() + "");
						eEmatch.setSRes(recommend.getValue().toString());
						eEmatch.setPt(recommend.getRtype().toString());
						eEmatch.setStopSellTime(DateTools.dateToString(match.getNosaleTime()));
						easyEb.getMatchs().add(eEmatch);

						easyEb.setPos(type.getPos() + "");
						easyEb.setType(type.getId().toString());
						easyEb.setShowTxt(type.getName());
						easyEb.setDefMultiple(type.getDefaultMultiple() + "");
						easyEb.setPlanCnt(type.getPlanCount() + "");
						temp++;
					}

					easyLottery.setTerm(term);
					easyLottery.getEbData().add(easyEb);
				}

			} else {
				String term = sqlIsusesService.findByProperty("lotteryId", 73).get(0).getId().toString();
				easyLottery.setTerm(term);
				lists = easyLotteryRecommendService.findRecommendBkInformation();
				minPos = easyLotteryRecommendService.findMinPos(73);
				Map<String, List<Object[]>> map = new HashMap<String, List<Object[]>>();
				// 将不同的recommend类型来分组
				for (Object[] aObj : lists) {
					EasyLotteryRecommend recommend = (EasyLotteryRecommend) aObj[0];
					EasyLotteryType type = (EasyLotteryType) aObj[2];
					// LogUtil.out(type.getId()+"---"+type.getName()+"---"+
					// type.getIsShow());
					// FtMatch match = (FtMatch) aObj[1];
					if (map.get(recommend.getTypeId().toString()) != null) {
						List<Object[]> list = map.get(recommend.getTypeId().toString());
						list.add(aObj);
					} else {
						List<Object[]> list = new ArrayList<Object[]>();
						list.add(aObj);
						map.put(recommend.getTypeId().toString(), list);
					}

				}
				// 遍历分组好的比赛
				Iterator<Entry<String, List<Object[]>>> iter = map.entrySet().iterator();

				while (iter.hasNext()) {
					Map.Entry<String, List<Object[]>> entry = (Map.Entry) iter.next();
					List<Object[]> objs = entry.getValue();
					//当某一类型赛事小于2场时候不显示
					if(objs.size()<=1){
						continue;
					}
					EasyEbData easyEb = new EasyEbData();

					String s = ((EasyLotteryType) objs.get(0)[2]).getSlogan();
					String aim = objs.size() > 0 ? getNumInString(s).get(0) : "70";
					// LogUtil.out(aim);
//					easyEb.setAIM(aim + "%");
					int typeId=((EasyLotteryRecommend)objs.get(0)[0]).getTypeId();
					// 购买人数
					Integer count = objs.size() > 0 ? (lotteryPlanService.getEasyBuyCountByEasyType(((EasyLotteryRecommend) objs.get(0)[0])
							.getTypeId())) : 0;
//					easyEb.setJoinCnt(count.toString());
					
					if(((EasyLotteryType) objs.get(0)[2]).getPos()==(int)minPos){
						if(random.nextInt(10)>=9){
							count_1++;		
						}
						count+=count_1;
					}
					
/*					switch (typeId) {
					case 4:
						if(random.nextInt(10)>=4){
//							count_4=Integer.parseInt(pros.readValue("count_4"));
							count_4++;					
						}
						count+=count_4;
						break;
					case 5:
						if(random.nextInt(10)>=6){
							count_5++;					
						}						
						count+=count_5;
						break;
					case 6:
						if(random.nextInt(10)>=8){
							count_6++;					
						}							
						count+=count_6;
						break;
					default:
						break;
					}*/
//					System.out.println("count="+count);
					easyEb.setShowTxt("高命中");
					easyEb.setType("1");
					easyEb.setDefMultiple("10");
					easyEb.setPlanCnt("4");
					
					String title = objs.size()>0? ((EasyLotteryType) objs.get(0)[2]).getSlogan():"";;
					easyEb.setTitle(processTitle(title, count));
					
					for (Object[] aObj : objs) {
						EasyLotteryRecommend recommend = (EasyLotteryRecommend) aObj[0];
						BkMatch match = (BkMatch) aObj[1];
						EasyLotteryType type = (EasyLotteryType) aObj[2];
						EasyLotteryMatch eEmatch = new EasyLotteryMatch();
						eEmatch.setMid(match.getSqlId().toString());
						eEmatch.setMatchNo(match.getMatchNo());
						eEmatch.setMatchId(match.getId().toString());
						eEmatch.setLineId(match.getLineId());
						eEmatch.setIntTime(match.getIntTime().toString());
						eEmatch.setMTeam(match.getMbName());
						eEmatch.setGTeam(match.getTgName());
						eEmatch.setGame(match.getLeagueName());
						eEmatch.setZqRq(recommend.getRq().toString());
						eEmatch.setLqRf(recommend.getRf() + "");
						eEmatch.setLqZf(recommend.getDxf() + "");
						eEmatch.setSp(recommend.getAward() + "");
						eEmatch.setSRes(recommend.getValue().toString());
						eEmatch.setPt(recommend.getRtype().toString());
						eEmatch.setStopSellTime(DateTools.dateToString(match.getNosaleTime()));
						easyEb.getMatchs().add(eEmatch);

						easyEb.setPos(type.getPos() + "");
						easyEb.setType(type.getId().toString());
						easyEb.setShowTxt(type.getName());
						easyEb.setDefMultiple(type.getDefaultMultiple() + "");
						easyEb.setPlanCnt(type.getPlanCount() + "");

					}

					easyLottery.setTerm(term);
					easyLottery.getEbData().add(easyEb);
				}

			}
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogUtil.out(e);
		}
		ObjectMapper mapper = new ObjectMapper();
		String result = mapper.writeValueAsString(easyLottery);

		byte[] compress = CompressFile.compress(result);
		result = Base64.encode(compress);

		out.write(result);
		out.close();
	}

	public void setEasyLotteryRecommendService(EasyLotteryRecommendService easyLotteryRecommendService) {
		this.easyLotteryRecommendService = easyLotteryRecommendService;
	}

	public void setSqlIsusesService(IsusesService sqlIsusesService) {
		this.sqlIsusesService = sqlIsusesService;
	}

	/**
	 * 返回字符串中包含的数字
	 * 
	 * @param str
	 * @return
	 */
	public List<String> getNumInString(String str) {
		// String str = "理论中奖率80%以上，已有@buyCount人认购";
		List<String> nums = new ArrayList<String>();
		Matcher matcher = Pattern.compile("[\\d]+").matcher(str);
		while (matcher.find()) {
			String strNum = matcher.group();
			nums.add(strNum);
		}
		return nums;
	}

	/**
	 * 加工
	 * 
	 * @param title
	 * @param count 参与人数
	 * @return
	 */
	public String processTitle(String title, Integer count) {
		//理论中奖率[85%]以上，已有[@buyCount]人认购
		String reg = "\\[@*\\w*\\%*\\]";
		Matcher matcher = Pattern.compile(reg).matcher(title);
		List<String> nums = new ArrayList<String>();
		List<String> matches = new ArrayList<String>();
		while (matcher.find()) {
			String strNum = matcher.group();
			matches.add(strNum);
			String sNum = strNum.replaceAll("\\[|\\]", "");
			nums.add(sNum);
		}
//		title= title.replaceAll(matches.get(0), "<font color='red' size='3'>"+nums.get(0)+"%"+"</font>").replaceAll(matches.get(1), "<font color='red' size='3'>"+count+"</font>");
		for(int i=0;i<matches.size();i++){
			String match=matches.get(i);
			String num = nums.get(i);
			if(match.contains("@")){
				title=title.replace(match, "<font color='red' size='3'>"+count+"</font>");
			}else{
				title=title.replace(match, "<font color='red' size='3'>"+num+"</font>");
			}
			
		}
		return title;
	}

	public   int floatAdd(int count){
		if(count<=0){
			count=0;
		}else if(count <23){
			count+=1;
		}else if(count<106){
			count=(int)(Math.floor((count+1)*(1+0.07)));
		}else if(count<500){
			count=(int)(Math.floor(((count+1)*(1+0.07))*(1+0.01)));
		}else {
			count=(int)(Math.floor((((count+1)*(1+0.07))*(1+0.01))*(1+0.007)));
		}
		return count;
	}

	public int getCount_1() {
		return count_1;
	}

	public void setCount_1(int count_1) {
		this.count_1 = count_1;
	}

/*	public int getCount_2() {
		return count_2;
	}

	public void setCount_2(int count_2) {
		this.count_2 = count_2;
	}

	public int getCount_3() {
		return count_3;
	}

	public void setCount_3(int count_3) {
		this.count_3 = count_3;
	}

	public int getCount_4() {
		return count_4;
	}

	public void setCount_4(int count_4) {
		this.count_4 = count_4;
	}

	public int getCount_5() {
		return count_5;
	}

	public void setCount_5(int count_5) {
		this.count_5 = count_5;
	}

	public int getCount_6() {
		return count_6;
	}

	public void setCount_6(int count_6) {
		this.count_6 = count_6;
	}*/

	public void setLotteryPlanService(LotteryPlanService lotteryPlanService) {
		this.lotteryPlanService = lotteryPlanService;
	}

	public void setEasyLotteryTypeService(EasyLotteryTypeService easyLotteryTypeService) {
		this.easyLotteryTypeService = easyLotteryTypeService;
	}
	
}
