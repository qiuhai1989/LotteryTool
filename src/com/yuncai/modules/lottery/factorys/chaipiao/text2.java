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
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlayType;
import com.yuncai.modules.lottery.model.Oracle.toolType.SelectType;

public class text2 implements ChaiPiao {
	public List chaiPiao(LotteryPlan lotteryPlan) throws Exception {
		int maxBeishu = 40;
		List ticketList = new ArrayList();
		String tempContent = "";
		Map playTypeContentMap = new HashMap();
		if (lotteryPlan.getSelectType().getValue() == 1) {
			try {
				tempContent = FileTools.getFileContentForUploadPlan(WebConstants.WEB_PATH + lotteryPlan.getContent(), " #");
				StringBuffer sb = new StringBuffer("");
				// sb.append(lotteryPlan.getPlayType().getValue()).append(String.valueOf(FuHao.WF));
				List list = Algorithm.checkOut(tempContent, Algorithm.FB_BQC_6);
				for (int i = 0; i < list.size(); i++) {
					char[] cs = list.get(i).toString().toCharArray();
					for (int j = 0; j < cs.length; j++) {
						sb.append(cs[j]).append(",");
					}
					sb.deleteCharAt(sb.length() - 1);
					sb.append("^");
				}
				sb.deleteCharAt(sb.length() - 1);
				tempContent = sb.toString();
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}

		} else {
			tempContent = lotteryPlan.getContent().split("\\%")[0];
		}

		String[] contentGroup = tempContent.split("\\~");

		for (int i = 0; i < contentGroup.length; i++) {
			List subList = new ArrayList();
			String ticketContent = "";
			int playType = 0;
			if (lotteryPlan.getSelectType().getValue() == 1) {
				// 上传投注
				ticketContent = contentGroup[i];
				playType = lotteryPlan.getPlayType().getValue();
			} else {
				String contentSingle[] = contentGroup[i].split("\\:");
				playType = Integer.parseInt(contentSingle[0]);
				ticketContent = contentSingle[1];
			}

			if (playType == PlayType.DS.getValue()) {
				String[] tc = ticketContent.split("\\^");
				for (int j = 0; j < tc.length; j++) {
					addNewContent(playTypeContentMap, PlayType.DS.getValue(), tc[j]);
				}
			} else if (playType == PlayType.FS.getValue()) {
				if (ticketContent.length() == 23) {
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

	private void addNewContent(Map map, int playType, String content) {
		if (map.containsKey(playType)) {
			((List) map.get(playType)).add(content);
		} else {
			List list = new ArrayList();
			list.add(content);
			map.put(playType, list);
		}
	}

	private void addNewContent(Map map, int playType, List contentList) {
		if (map.containsKey(playType)) {
			((List) map.get(playType)).addAll(contentList);
		} else {
			List list = new ArrayList();
			list.addAll(contentList);
			map.put(playType, list);
		}
	}

	public static void main(String[] args) {
		LotteryPlan plan = new LotteryPlan();
		plan.setSelectType(SelectType.CHOOSE_SELF);
		plan.setLotteryType(LotteryType.getItem(4));
		plan.setPlayType(PlayType.HT);
		plan.setMultiple(1);
		plan.setContent("1:3,0,3,0,3,1,0,1,1,0,3,1~2:3,0,3,0,3,1,310,1,1,0,3,1%1");
		plan.setAmount(6);

		FbBqcChaiPiao cp = new FbBqcChaiPiao();
		List list = null;
		try {
			list = cp.chaiPiao(plan);
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (int i = 0; i < list.size(); i++) {
			Ticket t = (Ticket) list.get(i);
			System.out.println(t.getPlayType().getName());
			System.out.println(t.getContent());
			System.out.println(t.getAmount());
		}
	}

}
