package com.yuncai.modules.lottery.factorys.chaipiao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.yuncai.core.tools.FileTools;
import com.yuncai.core.tools.ticket.Algorithm;
import com.yuncai.core.tools.ticket.FuHao;
import com.yuncai.core.tools.ticket.SetTicket;
import com.yuncai.modules.lottery.WebConstants;
import com.yuncai.modules.lottery.model.Oracle.LotteryPlan;
import com.yuncai.modules.lottery.model.Oracle.Ticket;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlayType;
import com.yuncai.modules.lottery.model.Oracle.toolType.SelectType;

public class DltChaiPiao implements ChaiPiao {

	@SuppressWarnings("unchecked")
	public List chaiPiao(LotteryPlan lotteryPlan) throws Exception {
		int maxBeishu = 40;  //最大线数
		List ticketList = new ArrayList();
		String tempContent = "";
		Map playTypeContentMap = new HashMap();
		if (lotteryPlan.getSelectType().getValue() == 1) {
			try {
				tempContent = FileTools.getFileUpdateDltContent(WebConstants.WEB_PATH + lotteryPlan.getContent());
				tempContent = tempContent.substring(0, tempContent.length() - 1);
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		} else {
			tempContent = lotteryPlan.getContent();
		}
		if(tempContent==null){
			throw new RuntimeException("内容为空:" + tempContent);
		}
		//内容
		String[] contentGroup = tempContent.split("\\\n");
		//子玩法
		int playType = lotteryPlan.getPlayType().getValue();
		
		
		for(int i=0;i<contentGroup.length;i++){
			List subList = new ArrayList();
			String ticketContent = contentGroup[i];
			if(playType==PlayType.DS.getValue()){
				//格式转换
				String[] lines = ticketContent.split("\\^");
				for (String temp : lines) {
					temp=dltFS(temp);
					addNewContent(playTypeContentMap, PlayType.DS.getValue(), temp);
				}
			}else if(playType==PlayType.FS.getValue()){
				//格式转换
				ticketContent=dltFS(ticketContent);
				if (ticketContent.length() == 20) {
					addNewContent(playTypeContentMap, PlayType.DS.getValue(), ticketContent);
				} else {
					addNewContent(playTypeContentMap, PlayType.FS.getValue(), ticketContent);
				}
			}else if(playType==PlayType.DT.getValue()){
				// 拆单式模式
				List strList = Algorithm.dltDtL(ticketContent);
				for (int m = 0; m < strList.size(); m++) {
					String string = strList.get(m).toString();
					String[] lines = string.split("\\^");
					for (int l = 0; l < lines.length; l++) {
						addNewContent(playTypeContentMap, PlayType.DS.getValue(), lines[l]);
					}
				}
			}
			
		}
		
		int num = 0;
		Iterator it = playTypeContentMap.keySet().iterator();
		while (it.hasNext()) {
			int subPlayType = (Integer) it.next();
			List subList = (List) playTypeContentMap.get(subPlayType);
			if (subPlayType == PlayType.DS.getValue() ) {
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < subList.size(); i++) {
					if ((i + 1) % 5 == 0) {
						sb.append(subList.get(i).toString());
						Ticket ticket = new Ticket();
						ticket.setPlayType(PlayType.getItem(subPlayType));
						ticket.setAmount(5 * 2 * lotteryPlan.getMultiple());
						if (lotteryPlan.getAddAttribute().equals("0") && subPlayType == PlayType.DS.getValue())// 基本玩法&追加
						{
							ticket.setAddAttribute("0");
							ticket.setAmount(ticket.getAmount().intValue() / 2 * 3);
						}
						ticket.setContent(sb.toString().replaceAll("\\,", " ").replaceAll("\\|", "\\-"));
						ticket = SetTicket.setTicket(ticket, lotteryPlan);
						ticketList.add(ticket);
						sb = new StringBuffer();
					} else if (i + 1 == subList.size()) {
						sb.append(subList.get(i).toString());
						Ticket ticket = new Ticket();
						ticket.setPlayType(PlayType.getItem(subPlayType));
						ticket.setAmount(((i + 1) % 5) * 2 * lotteryPlan.getMultiple());
						if (lotteryPlan.getAddAttribute().equals("0") && subPlayType == PlayType.DS.getValue())// 基本玩法&追加
						{
							ticket.setAddAttribute("0");
							ticket.setAmount(ticket.getAmount().intValue() / 2 * 3);
						}
						ticket.setContent(sb.toString().replaceAll("\\,", " ").replaceAll("\\|", "\\-"));
						ticket = SetTicket.setTicket(ticket, lotteryPlan);
						ticketList.add(ticket);
					} else {
						sb.append(subList.get(i).toString()).append("^");
					}
				}
			} else if (subPlayType == PlayType.FS.getValue()) {// 复式
				for (int i = 0; i < subList.size(); i++) {
					Ticket ticket = new Ticket();
					ticket.setPlayType(PlayType.getItem(subPlayType));
					String temp = subList.get(i).toString();
					int zhushu = Algorithm.combination(temp.split("\\|")[0].split("\\,").length, 5)
							* Algorithm.combination(temp.split("\\|")[1].split("\\,").length, 2);
					ticket.setAmount(zhushu * 2 * lotteryPlan.getMultiple());
					if (lotteryPlan.getAddAttribute().equals("0"))// 追加
					{
						ticket.setAddAttribute("0");
						ticket.setAmount(ticket.getAmount().intValue() / 2 * 3);
					}
					ticket.setContent(subList.get(i).toString().replaceAll("\\,", " ").replaceAll("\\|", "\\-"));
					ticket = SetTicket.setTicket(ticket, lotteryPlan);
					ticketList.add(ticket);
				}
			} else if (subPlayType == PlayType.DT.getValue()) {// 复式
				for (int i = 0; i < subList.size(); i++) {
					Ticket ticket = new Ticket();
					ticket.setPlayType(PlayType.getItem(subPlayType));
					String temp = subList.get(i).toString();
					String redDan = temp.split("\\+")[0].split(",")[0];
					String redTuo = temp.split("\\+")[0].split(",")[1];
					String buleDan = temp.split("\\+")[1].split(",")[0];
					String buleTuo = temp.split("\\+")[1].split(",")[1];

					int redDanCount = 0;
					if (!"".equals(redDan)) {
						redDanCount = redDan.trim().split(" ").length;
					}

					int blueDanCount = 0;
					if (!"".equals(buleDan)) {
						blueDanCount = buleDan.trim().split(" ").length;
					}

					int zhushu = Algorithm.combination(redTuo.trim().split(" ").length, 5 - redDanCount)
							* Algorithm.combination(buleTuo.trim().split(" ").length, 2 - blueDanCount);
					ticket.setAmount(zhushu * 2 * lotteryPlan.getMultiple());
					if (lotteryPlan.getAddAttribute().equals("0"))// 追加
					{
						ticket.setAddAttribute("0");
						ticket.setAmount(ticket.getAmount().intValue() / 2 * 3);
					}
					ticket.setContent(temp.replaceAll("\\,", "\\$").replaceAll("\\+", "\\-").trim());//.replaceAll("\\,", " "));
					ticket = SetTicket.setTicket(ticket, lotteryPlan);
					ticketList.add(ticket);
				}
			} else {
				throw new RuntimeException("未知的玩法类型:" + subPlayType);
			}
		}
		// 处理倍数
		ticketList = SetTicket.processBeishu(ticketList, lotteryPlan.getMultiple(), maxBeishu);

		return ticketList;
		
	}
	
	@SuppressWarnings("unused")
	private void addNewContent(Map map, int playType, String content) {
		if (map.containsKey(playType)) {
			((List) map.get(playType)).add(content);
		} else {
			List list = new ArrayList();
			list.add(content);
			map.put(playType, list);
		}
	}

	@SuppressWarnings("unused")
	private void addNewContent(Map map, int playType, List contentList) {
		if (map.containsKey(playType)) {
			((List) map.get(playType)).addAll(contentList);
		} else {
			List list = new ArrayList();
			list.addAll(contentList);
			map.put(playType, list);
		}
	}
	
	//把内容转换一下
	public static String dltFS(String content){
		String[] redAndBlue = content.split("\\+");
		String dan=redAndBlue[0].trim().replaceAll(" ", "\\,");
		String tan=redAndBlue[1].trim().replaceAll(" ", "\\,");
		return dan+FuHao.LQ+tan;
	}
	
	public static void main(String[] args) {
		String strf="ffffffffff"+"\n"+"aaaaaaaaaaa";
		System.out.println(strf);
		String[] tem=strf.split("\\\n");
		System.out.println(tem[0].toString());
		
//		String ticketContent=" 01,02,03,06#04,05,08|04#06,07,08";
//		//List strListf = Algorithm.dltDt(ticketContent);
//		System.out.println(strListf.size());
		
		
//		String context="02 17 18 19 , 04 05 08 + 06 07 08 04 05";
//		List strListb = Algorithm.dltDtL(context);
//		System.out.println(strListb.size());
		
		String a="07 10 12 30 32 35 + 06 10";
		System.out.println(dltFS(a));
		
		LotteryPlan plan=new LotteryPlan();
		StringBuffer bf=new StringBuffer();
		bf.append("04 07 17 20 33 + 05 06").append("\n").append("07 08 12 13 28 30 + 01 10 02")
		.append("\n").append("05 15 21 29 31 34 35 + 01 02");
		plan.setContent(bf.toString());
		plan.setPlayType(PlayType.FS);
		plan.setSelectType(SelectType.CHOOSE_SELF);
		plan.setMultiple(1);
		plan.setAddAttribute("1");
		DltChaiPiao dlt=new DltChaiPiao();
		try {
			dlt.chaiPiao(plan);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("sssssssssssss");
		
		
		

	}

	

}
