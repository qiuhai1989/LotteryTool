package com.yuncai.modules.lottery.factorys.chaipiao;
import java.util.*;

import com.yuncai.core.tools.MathUtil;
import com.yuncai.core.tools.MathUtils;
import com.yuncai.core.tools.ticket.SetTicket;
import com.yuncai.modules.lottery.model.Oracle.LotteryPlan;
import com.yuncai.modules.lottery.model.Oracle.Ticket;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlayType;
import com.yuncai.modules.lottery.model.Oracle.toolType.SelectType;

public class test implements ChaiPiao{
	
	@SuppressWarnings("unchecked")
	public List chaiPiao(LotteryPlan lotteryPlan) throws Exception {
		int maxBeishu = 50;
		List ticketList = new ArrayList();
		String tempContent = "";
		Map playTypeContentMap = new HashMap();
		if (lotteryPlan.getSelectType().getValue() == 1) {
//			try {
//				tempContent = FileTools.getFileSDContent(WebConstants.WEB_PATH + lotteryPlan.getContent());
//				tempContent = tempContent.substring(0, tempContent.length() - 1);
//			} catch (Exception e) {
//				e.printStackTrace();
//				throw e;
//			}
		} else {
			tempContent = lotteryPlan.getContent().split("\\%")[0];
		}

		String[] contentGroup = tempContent.split("\\~");

		for (int i = 0; i < contentGroup.length; i++) {
			List subList = new ArrayList();
			String ticketContent = "";
			int playType = 0;
			if (lotteryPlan.getSelectType().getValue() == 1) {
				// 混合投注
				ticketContent = contentGroup[i];
				playType = lotteryPlan.getPlayType().getValue();
			} else {
				String contentSingle[] = contentGroup[i].split("\\:");
				playType = Integer.parseInt(contentSingle[0]);
				ticketContent = contentSingle[1];
			}
			if (playType == PlayType.ZX.getValue()) {
				if (ticketContent.length() == 5) {
					addNewContent(playTypeContentMap, PlayType.ZX.getValue(), ticketContent.replaceAll("\\,", "").replaceAll("\\|", ","));
				} else {
					addNewContent(playTypeContentMap, PlayType.ZXFS.getValue(), ticketContent.replaceAll("\\,", "").replaceAll("\\|", ","));
				}
			} else if (playType == PlayType.ZHUXUAN.getValue()) {

				addNewContent(playTypeContentMap, PlayType.ZHUXUAN.getValue(), ticketContent.replaceAll("\\|", ","));

			} else if (playType == PlayType.ZXHZ.getValue()) {
				String[] subContent = ticketContent.split("\\|");
				for (int j = 0; j < subContent.length; j++) {
					addNewContent(playTypeContentMap, PlayType.ZXHZ.getValue(), subContent[j]);
				}
			} else if (playType == PlayType.ZS.getValue()) {
				addNewContent(playTypeContentMap, PlayType.ZS.getValue(), ticketContent.replaceAll("\\,", ""));
			} else if (playType == PlayType.ZSHZ.getValue()) {
				// .replaceAll("\\|", ",")
				String[] subContent = ticketContent.split("\\|");
				for (int j = 0; j < subContent.length; j++) {
					addNewContent(playTypeContentMap, PlayType.ZHUXUAN.getValue(),  zshz(subContent[j]));
				}
			} else if (playType == PlayType.ZL.getValue()) {
				if (ticketContent.length() == 5) {
					addNewContent(playTypeContentMap, PlayType.ZHUXUAN.getValue(), ticketContent);
				} else {
					addNewContent(playTypeContentMap, PlayType.ZL.getValue(), ticketContent.replaceAll("\\,", ""));
				}
			} else if (playType == PlayType.ZLHZ.getValue()) {
				// .replaceAll("\\|", ",")
				String[] subContent = ticketContent.split("\\|");
				for (int j = 0; j < subContent.length; j++) {
					addNewContent(playTypeContentMap, PlayType.ZHUXUAN.getValue(), zlhz(subContent[j]));
				}
			} else if (playType == PlayType.ZLDT.getValue()) {
				addNewContent(playTypeContentMap, PlayType.ZHUXUAN.getValue(), zldt(ticketContent));

			} else if (playType == PlayType.ZSBZ.getValue()) {
				addNewContent(playTypeContentMap, PlayType.ZHUXUAN.getValue(), zsbz(ticketContent));
				
			} else if (playType == PlayType.ZXDT.getValue()) {//直选胆拖
				addNewContent(playTypeContentMap, PlayType.ZX.getValue(), zxdt(ticketContent));
				
			} else if (playType == PlayType.ZXKD.getValue()) {//直选跨度
				String[] subContent = ticketContent.split("\\|");
				for (int j = 0; j < subContent.length; j++) {
					addNewContent(playTypeContentMap, PlayType.ZX.getValue(), zxkd(subContent[j]));
				}
			} else if (playType == PlayType.ZXBH.getValue()) {//直选包号
				addNewContent(playTypeContentMap, PlayType.ZXFS.getValue(), ticketContent.replaceAll("\\,", "").replaceAll("\\|", ","));
			} else if (playType == PlayType.ZHIXUANDS.getValue()) {//直选单式上传
				String[] lines = ticketContent.split("\\^");
				for (String temp : lines) {
					addNewContent(playTypeContentMap, PlayType.ZX.getValue(), temp.replaceAll("\\,", "").replaceAll("\\|", ","));
				}
			} else if (playType == PlayType.ZUXUANDS.getValue()) {//组选单式上传
				String[] lines = ticketContent.split("\\^");
				for (String temp : lines) {
					String content = temp.replaceAll("\\|", ",");
					addNewContent(playTypeContentMap, PlayType.ZHUXUAN.getValue(), content);
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
			if (subPlayType == PlayType.ZXFS.getValue()) {
				for (int i = 0; i < subList.size(); i++) {
					Ticket ticket = new Ticket();
					ticket.setPlayType(PlayType.getItem(subPlayType));
					ticket.setAmount(getCommonZS(subList.get(i).toString()) * 2 * lotteryPlan.getMultiple());
					ticket.setContent(subList.get(i).toString());
					ticket = SetTicket.setTicket(ticket, lotteryPlan);
					ticketList.add(ticket);
				}
			} else if (subPlayType == PlayType.ZHUXUAN.getValue() || subPlayType == PlayType.ZX.getValue()) {
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
			} else if (subPlayType == PlayType.ZS.getValue()) {
				for (int i = 0; i < subList.size(); i++) {
					Ticket ticket = new Ticket();
					ticket.setPlayType(PlayType.getItem(subPlayType));
					ticket.setAmount(getZsZhuShu(subList.get(i).toString()) * 2 * lotteryPlan.getMultiple());
					ticket.setContent(subList.get(i).toString());
					ticket = SetTicket.setTicket(ticket, lotteryPlan);
					ticketList.add(ticket);
				}
			} else if (subPlayType == PlayType.ZL.getValue()) {
				for (int i = 0; i < subList.size(); i++) {
					Ticket ticket = new Ticket();
					ticket.setPlayType(PlayType.getItem(subPlayType));
					ticket.setAmount(getZlZhuShu(subList.get(i).toString()) * 2 * lotteryPlan.getMultiple());
					ticket.setContent(subList.get(i).toString());
					ticket = SetTicket.setTicket(ticket, lotteryPlan);
					ticketList.add(ticket);
				}
			} else if (subPlayType == PlayType.ZXHZ.getValue()) {// 组三和值
				for (int i = 0; i < subList.size(); i++) {
					Ticket ticket = new Ticket();
					ticket.setPlayType(PlayType.getItem(subPlayType));
					int zhushu = 0;
					String[] codes = subList.get(i).toString().split("\\|");
					for (int m = 0; m < codes.length; m++) {
						zhushu += zxhz(Integer.valueOf(codes[m]));
					}
					ticket.setAmount(zhushu * 2 * lotteryPlan.getMultiple());
					ticket.setContent(subList.get(i).toString());
					ticket = SetTicket.setTicket(ticket, lotteryPlan);
					ticketList.add(ticket);
				}
			} else if (subPlayType == PlayType.ZSHZ.getValue()) {// 组三和值
				for (int i = 0; i < subList.size(); i++) {
					Ticket ticket = new Ticket();
					ticket.setPlayType(PlayType.getItem(subPlayType));
					int zhushu = 0;
					String[] codes = subList.get(i).toString().split("\\|");
					for (int m = 0; m < codes.length; m++) {
						zhushu += z3hz(Integer.valueOf(codes[m]));
					}
					ticket.setAmount(zhushu * 2 * lotteryPlan.getMultiple());
					ticket.setContent(subList.get(i).toString());
					ticket = SetTicket.setTicket(ticket, lotteryPlan);
					ticketList.add(ticket);
				}
			} else if (subPlayType == PlayType.ZLHZ.getValue()) {// 组三和值
				for (int i = 0; i < subList.size(); i++) {
					Ticket ticket = new Ticket();
					ticket.setPlayType(PlayType.getItem(subPlayType));
					int zhushu = 0;
					String[] codes = subList.get(i).toString().split("\\|");
					for (int m = 0; m < codes.length; m++) {
						zhushu += z6hz(Integer.valueOf(codes[m]));
					}
					ticket.setAmount(zhushu * 2 * lotteryPlan.getMultiple());
					ticket.setContent(subList.get(i).toString());
					ticket = SetTicket.setTicket(ticket, lotteryPlan);
					ticketList.add(ticket);
				}
			} else {
				throw new RuntimeException("未知的玩法类型");
			}
		}
		// 处理倍数
		ticketList = SetTicket.processBeishu(ticketList, lotteryPlan.getMultiple(), maxBeishu);

		return ticketList;
	}

	public int z6hz(int n) {
		int zu = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = i + 1; j < 9; j++) {

				for (int k = j + 1; k < 10; k++) {
					if (i + j + k == n)
						zu++;
				}

			}
		}
		return zu;
	}

	public int zxhz(int n) {
		int zu = 0;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {

				for (int k = 0; k < 10; k++) {
					if (i + j + k == n)
						zu++;
				}

			}
		}
		return zu;
	}

	public int z3hz(int n) {
		int zu = 0;
		for (int i = 0; i < 9; i++) {
			for (int j = i; j < 10; j++) {
				for (int k = j; k < 10; k++) {
					if (i + j + k == n) {
						if ((i == j && i == k) || i != j && i != k && j != k) {
							zu = zu;
						} else {
							zu++;
						}
					}
				}
			}
		}
		return zu;
	}

	public int combination(int m, int n)// m为下标，n为上标
	{
		if (m < 0 || n < 0 || m < n)
			return -1;
		// 数据不符合要求，返回错误信息
		n = n < (m - n) ? n : m - n;// C(m,n)=C(m,m-n)
		if (n == 0)
			return 1;
		int result = m;// C(m,1);
		for (int i = 2, j = result - 1; i <= n; i++, j--) {
			result = result * j / i;// 得到C(m,i)
		}
		return result;
	}

	public int getZsZhuShu(String content) {
		return 2 * combination(content.length(), 2);
	}

	public int getZlZhuShu(String content) {
		return combination(content.length(), 3);
	}

	// 组六和值
	public List<String> zlhz(String a) {
		int m = 9;
		int n = Integer.parseInt(a);
		if (n < 9)
			m = n;
		List<String> returnStrLsit = new ArrayList<String>();
		for (int i = 0; i < 8; i++) {
			for (int j = i + 1; j < 9; j++) {
				for (int k = j + 1; k < 10; k++) {
					if (i + j + k == n)
						returnStrLsit.add(i + "," + j + "," + k);
				}
			}
		}
		return returnStrLsit;
	}

	// 组六包号
	public List<String> zlbh(String content) {
		String[] a = content.split("\\,");
		List<String> returnStrLsit = new ArrayList<String>();
		for (int i = 0; i < a.length - 2; i++) {
			for (int j = i + 1; j < a.length - 1; j++) {
				for (int k = j + 1; k < a.length; k++) {
					returnStrLsit.add(a[i] + "," + a[j] + "," + a[k]);
				}
			}
		}
		return returnStrLsit;
	}

	// 组三和值
	public List<String> zshz(String a) {
		int m = 9;
		int n = Integer.parseInt(a);
		if (n < 9)
			m = n;
		List<String> returnStrLsit = new ArrayList<String>();
		for (int i = 0; i <= m; i++) {
			for (int j = 0; j <= m; j++) {
				if (2 * i + j == n && i != j)
					returnStrLsit.add(i + "," + i + "," + j);
			}
		}
		return returnStrLsit;
	}

	// 直选和值
	public List<String> zxhz(String a) {
		List<String> returnStrLsit = new ArrayList<String>();
		int m = 9;
		int n = Integer.parseInt(a);
		if (n < 9)
			m = n;
		for (int i = 0; i <= m; i++) {
			for (int j = 0; j <= m; j++) {
				for (int k = 0; k <= m; k++) {
					if (i + j + k == n)
						returnStrLsit.add(i + "," + j + "," + k);
				}
			}
		}
		return returnStrLsit;
	}

	// 组三标准
	public List<String> zsbz(String content) {
		List<String> returnStrLsit = new ArrayList<String>();
		String[] areas = content.split("\\|");
		String[] area1 = areas[0].split("\\,");
		String[] area2 = areas[2].split("\\,");

		for (int i = 0; i < area1.length; i++) {
			for (int j = 0; j < area2.length; j++) {
				if (!area1[i].equals(area2[j])) {
					returnStrLsit.add(area1[i] + "," + area1[i] + "," + area2[j]);
				}
			}
		}
		return returnStrLsit;
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

	public List<String> zldt(String content) {
		String a[] = content.split("\\|")[0].split("\\,");
		String b[] = content.split("\\|")[1].split("\\,");
		List<String> returnStrLsit = new ArrayList<String>();
		if (a.length == 2) {
			for (int i = 0; i < b.length; i++) {
				if (!a[1].equals(b[i]) && !a[0].equals(b[i])) {
					returnStrLsit.add(a[0] + "," + a[1] + "," + b[i]);
				}
			}
		}

		if (a.length == 1) {
			for (int i = 0; i < b.length - 1; i++) {
				for (int j = i + 1; j < b.length; j++) {
					if (!a[0].equals(b[i]) && !a[0].equals(b[j]) && !b[i].equals(b[j]))
						returnStrLsit.add(a[0] + "," + b[i] + "," + b[j]);
				}
			}
		}
		return returnStrLsit;
	}
	
	public List<String> zxdt(String content) {
		List<String> baseList = zldt(content);//直选胆拖组合的情况只有组6
		List<String> returnStrLsit = new ArrayList<String>();
		for (int i = 0; i < baseList.size(); i++) {
			String[] code = baseList.get(i).split(",");
			List<String> list = MathUtils.permutationResult(Arrays.asList(code),3);
			returnStrLsit.addAll(list);
		}
		return returnStrLsit;
	}
	
	public List<String> zxkd(String content) {
		List<String> returnStrLsit = new ArrayList<String>();
		for(int i=0; i<=9; i++) {
			for(int j=0; j<=9; j++) {
				for(int k=0; k<=9; k++) {
					String code = i + "," + j + "," + k;
					int kuaDu = MathUtil.getArrayDev(code.split("\\,"));
					if(kuaDu == Integer.parseInt(content)) {
						returnStrLsit.add(code);
					}
				}
			}
		}
		return returnStrLsit;
	}
	
	public static void main(String[] args) {
		StringBuffer sb = new StringBuffer();
		//sb.append("28:1,2|3|4"); //12,3,4
		//sb.append("29:1,2");  //12
		//sb.append("30:1,2,3,4"); //1234
		//sb.append("31:7|8"); //两张票只支持单式投注7 | 8
		//sb.append("~32:7|8");
		//sb.append("~33:7|8");
		//sb.append("~38:1|2,3");
		//sb.append("~29001:0|0|2,3");
		//sb.append("~29002:0|1,2");
		//sb.append("~29003:2|3");
		//sb.append("~29004:0,1,2,3,4,5,6,7,8,9|1|2,3");
		sb.append("%1");
		//ContentCheck check = parser.checkPlan(sb.toString(), 4 + 4 + 8 + 162 + +18 + 18 + 2 + 4 + 12 + 444 + 40);
		LotteryPlan plan=new LotteryPlan();
		//StringBuffer bf=new StringBuffer();
		//bf.append("04 07 17 20 33 + 05 06").append("\n").append("07 08 12 13 28 30 + 01 10 02")
		//.append("\n").append("05 15 21 29 31 34 35 + 01 02");
		plan.setContent(sb.toString());
		//plan.setPlayType(PlayType.FS);
		plan.setSelectType(SelectType.CHOOSE_SELF);
		plan.setMultiple(1);
		plan.setAddAttribute("1");
		test dlt=new test();
		try {
			dlt.chaiPiao(plan);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("sssssssssssss");
		
	}
}
