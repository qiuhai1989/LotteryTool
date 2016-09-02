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

public class FbSfc9ChaiPiao implements ChaiPiao{

	@SuppressWarnings({ "unchecked", "unchecked" })
	public List chaiPiao(LotteryPlan lotteryPlan) throws Exception {
		int maxBeishu = 40;
		List ticketList = new ArrayList();
		String tempContent = "";
		Map playTypeContentMap = new HashMap();
		if (lotteryPlan.getSelectType().getValue() == 1) {
			try {
				tempContent = FileTools.getFileContentForUploadPlan(WebConstants.WEB_PATH  + lotteryPlan.getContent(), " #");

				StringBuffer sb = new StringBuffer();
				List list = Algorithm.checkOut(tempContent, Algorithm.FB_SFP_9);
				for (int i = 0; i < list.size(); i++) {
					char[] cs = list.get(i).toString().toCharArray();
					for (int j = 0; j < cs.length; j++) {
						if (cs[j] == '-') {
							sb.append('-').append("");
						} else {
							sb.append(cs[j]).append("");
						}

					}
					//sb.deleteCharAt(sb.length() - 1);
					sb.append(FuHao.CZS);
				}
				sb.deleteCharAt(sb.length() - 1);
				tempContent = sb.toString();
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
		String[] contentGroup = tempContent.split("\\\n");
		
		//子玩法
		int playType = lotteryPlan.getPlayType().getValue();
		
		for (int i = 0; i < contentGroup.length; i++) {
			List subList = new ArrayList();
			String ticketContent = contentGroup[i];
			
			if (playType == PlayType.DS.getValue()) {
				ticketContent=ChangeContext(ticketContent);
				String[] lines = ticketContent.split("\\^");
				for (int j = 0; j < lines.length; j++)
					addNewContent(playTypeContentMap, PlayType.DS.getValue(), lines[j]);
			} else if (playType == PlayType.FS.getValue()) {
				ticketContent=ChangeContext(ticketContent);
				if (ticketContent.length() == 27) {
					addNewContent(playTypeContentMap, PlayType.DS.getValue(), ticketContent);
				} else {
					addNewContent(playTypeContentMap, PlayType.FS.getValue(), ticketContent);
				}
			} else if (playType == PlayType.DT.getValue()) {

				String content = ticketContent;

				int[] emptyFlag = new int[14];
				int[] danFlag = new int[14];
				String[] areas = content.split("\\,");
				StringBuffer sb = new StringBuffer();
				int tuoCount = 0;
				int danCount = 0;
				for (int j = 0; j < areas.length; j++) {
					if (areas[j].equals("-")) {
						emptyFlag[j] = 0;
						danFlag[j] = 1;
					} else if (areas[j].indexOf("(") != -1) {
						areas[j] = areas[j].substring(1, areas[j].length() - 1);
						danFlag[j] = 0;
						emptyFlag[j] = 1;
						danCount++;
					} else {
						emptyFlag[j] = 1;
						danFlag[j] = 1;
						tuoCount++;
						if (j < 10) {
							sb.append("0" + j).append("#");
						} else {
							sb.append(j).append("#");
						}

					}
				}
				sb.deleteCharAt(sb.length() - 1);

				System.out.println(sb);
				List<String> resultList = Algorithm.combine(sb.toString().split("#"), tuoCount, 9 - danCount);

				for (int j = 0; j < resultList.size(); j++) {
					sb = new StringBuffer();
					String line = resultList.get(j);
					int lineCount = 1;
					for (int k = 0; k < 14; k++) {
						if (emptyFlag[k] == 0) {
							// 当前位为空
							sb.append("-").append(",");
						} else if (danFlag[k] == 0) {
							sb.append(areas[k]).append(",");
							lineCount = lineCount * areas[k].length();
						} else {
							String findJ = null;
							if (k < 10) {
								findJ = "0" + k;
							} else {
								findJ = String.valueOf(k);
							}
							if (line.indexOf(findJ) != -1) {
								sb.append(areas[k]).append(",");
								lineCount = lineCount * areas[k].length();
							} else {
								// 当前位为空
								sb.append("-").append(",");
							}
						}
					}
					sb.deleteCharAt(sb.length() - 1);

					if (sb.toString().length() == 27) {
						addNewContent(playTypeContentMap, PlayType.DS.getValue(), sb.toString());
					} else {
						addNewContent(playTypeContentMap, PlayType.FS.getValue(), sb.toString());
					}
				}

			} else {
				throw new RuntimeException("未知的玩法类型");
			}
		}
		
		
		int num = 0;
		Iterator it = playTypeContentMap.keySet().iterator();
		while (it.hasNext()) {
			int subPlayType = (Integer) it.next();
			List subList = (List) playTypeContentMap.get(subPlayType);
			if (subPlayType == PlayType.DS.getValue()) {
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < subList.size(); i++) {
					if ((i + 1) % 5 == 0) {
						sb.append(subList.get(i).toString());
						Ticket ticket = new Ticket();
						ticket.setPlayType(PlayType.getItem(subPlayType));
						ticket.setAmount(5 * 2 * lotteryPlan.getMultiple());
						ticket.setContent(sb.toString());
						ticket = SetTicket.setTicket(ticket, lotteryPlan);
						ticketList.add(ticket);
						sb = new StringBuffer();
					} else if (i + 1 == subList.size()) {
						sb.append(subList.get(i).toString());
						Ticket ticket = new Ticket();
						ticket.setPlayType(PlayType.getItem(subPlayType));
						ticket.setAmount(((i + 1) % 5) * 2 * lotteryPlan.getMultiple());
						ticket.setContent(sb.toString());
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
					int zhushu = getCommonZS(temp);
					ticket.setAmount(zhushu * 2 * lotteryPlan.getMultiple());
					ticket.setContent(subList.get(i).toString());
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
	

	private int getCommonZS(String content) {
		String[] areas = content.split("\\,");
		int count = 1;
		for (int i = 0; i < areas.length; i++) {
			count *= areas[i].length();
		}
		return count;
	}

	@SuppressWarnings("unused")
	private int getDanTuoZs(String content) {
		String[] areas = content.split("\\,");
		int count = 1;

		return count;
	}

	@SuppressWarnings("unchecked")
	private void addNewContent(Map map, int playType, String content) {
		if (map.containsKey(playType)) {
			((List) map.get(playType)).add(content);
		} else {
			List list = new ArrayList();
			list.add(content);
			map.put(playType, list);
		}
	}

	@SuppressWarnings({ "unchecked", "unused" })
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
	public static String ChangeContext(String content_){
		String str=content_;
		String[] content=str.split("\\(");
		StringBuffer sb=new StringBuffer();
		
		if(content.length>1){
			for(String t:content){
				String[] tem=t.split("\\)");
				if(t.indexOf(")")!=-1){
					for(int i=0;i<tem.length;i++){
			    		String text=tem[i];
			    		if(i==0){
			    			StringBuffer sb1=new StringBuffer();
			    			sb1.append(text.replaceAll("", ""));
			    			sb.append(",").append(sb1);
			    		}else{
			    			sb.append(text.replaceAll("", ",")).deleteCharAt(sb.length()-1);
			    		}
			    	}
				}else{
					String temp=tem[0];
			    	sb.append(temp.replaceAll("", ",")).deleteCharAt(sb.length()-1);
				}
			}
			
		}else{
			sb.append(str.replaceAll("", ",")).deleteCharAt(sb.length()-1);
			
		}
		//System.out.println(sb.delete(0, 1).toString());
		return sb.delete(0, 1).toString();
		
	}
	public static void main(String[] args) {
//		String str="1234567(85)(910)-----";
//		str=ChangeContext(str);
//		System.out.println(str);
		
		LotteryPlan plan=new LotteryPlan();
		StringBuffer bf=new StringBuffer();
		bf.append("(310)0(31)03(31)01--(31)131").append("\n").append("130031133-----");
		
		plan.setContent(bf.toString());
		plan.setPlayType(PlayType.FS);
		plan.setSelectType(SelectType.CHOOSE_SELF);
		plan.setMultiple(1);
		plan.setAddAttribute("1");
		FbSfc9ChaiPiao dlt=new FbSfc9ChaiPiao();
		try {
			dlt.chaiPiao(plan);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("sssssssssssss");
	}


}
