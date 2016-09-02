package com.yuncai.modules.lottery.factorys.chaipiao;

import java.util.*;

import com.yuncai.core.tools.FileTools;
import com.yuncai.core.tools.ticket.FuHao;
import com.yuncai.core.tools.ticket.SetTicket;
import com.yuncai.modules.lottery.WebConstants;
import com.yuncai.modules.lottery.model.Oracle.LotteryPlan;
import com.yuncai.modules.lottery.model.Oracle.Ticket;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlayType;
import com.yuncai.modules.lottery.model.Oracle.toolType.SelectType;

public class QxcChaiPiao implements ChaiPiao{
	
	@SuppressWarnings({ "unchecked", "unchecked" })
	public List chaiPiao(LotteryPlan lotteryPlan) throws Exception{
		int maxBeishu = 50;
		List ticketList = new ArrayList();
		String tempContent = "";
		Map playTypeContentMap = new HashMap();
		if (lotteryPlan.getSelectType().getValue() == 1) {
			try {
				tempContent = FileTools.getFileDltContent(WebConstants.WEB_PATH + lotteryPlan.getContent());
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
		String[] contentGroup = tempContent.split("\\\n");
		
		//子玩法
		int playType = lotteryPlan.getPlayType().getValue();
		
		for (int i = 0; i < contentGroup.length; i++) {
			List subList = new ArrayList();
			String ticketContent = contentGroup[i];
			ticketContent=ChangeContext(ticketContent);
			if (playType == PlayType.DS.getValue()) {
				addNewContent(playTypeContentMap, PlayType.DS.getValue(), ticketContent);
			} else if (playType == PlayType.FS.getValue()) {
				if (ticketContent.length() == 9) {
					addNewContent(playTypeContentMap, PlayType.DS.getValue(), ticketContent);
				} else {
					addNewContent(playTypeContentMap, PlayType.FS.getValue(), ticketContent);
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
						ticket.setContent(sb.toString().replaceAll("\\,", "").replaceAll("\\|", "\\,"));
						ticket = SetTicket.setTicket(ticket, lotteryPlan);
						ticketList.add(ticket);
						sb = new StringBuffer();
					} else if (i + 1 == subList.size()) {
						sb.append(subList.get(i).toString());
						Ticket ticket = new Ticket();
						ticket.setPlayType(PlayType.getItem(subPlayType));
						ticket.setAmount(((i + 1) % 5) * 2 * lotteryPlan.getMultiple());
						ticket.setContent(sb.toString().replaceAll("\\,", "").replaceAll("\\|", "\\,"));
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
					ticket.setContent(subList.get(i).toString().replaceAll("\\,", "").replaceAll("\\|", "\\,"));
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
	private int getCommonZS(String content) {
		String[] areas = content.split("\\|");
		int count = 1;
		for (int i = 0; i < areas.length; i++) {
			count *= areas[i].length() - (areas[i].length() / 2);
		}
		return count;
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
			    			sb1.append(text.replaceAll("", ",")).delete(0, 1).deleteCharAt(sb1.length()-1);
			    			sb.append(FuHao.LQ).append(sb1);
			    		}else{
			    			sb.append(text.replaceAll("", "|")).deleteCharAt(sb.length()-1);
			    		}
			    	}
				}else{
					String temp=tem[0];
			    	sb.append(temp.replaceAll("", "|")).deleteCharAt(sb.length()-1);
				}
			}
			
		}else{
			sb.append(str.replaceAll("", "|")).deleteCharAt(sb.length()-1);
			
		}
		//System.out.println(sb.delete(0, 1).toString());
		return sb.delete(0, 1).toString();
		
	}
	
	public static void main(String[] args) {
		LotteryPlan plan=new LotteryPlan();
		StringBuffer bf=new StringBuffer();
		bf.append("0000012").append("\n").append("02(13)(342)56(789)");
		
		plan.setContent(bf.toString());
		plan.setPlayType(PlayType.FS);
		plan.setSelectType(SelectType.CHOOSE_SELF);
		plan.setMultiple(1);
		plan.setAddAttribute("1");
		QxcChaiPiao dlt=new QxcChaiPiao();
		try {
			dlt.chaiPiao(plan);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("sssssssssssss");
		
		
	}

}
