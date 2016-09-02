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

public class plsChaiPiao  implements ChaiPiao{

	@SuppressWarnings("unchecked")
	public List chaiPiao(LotteryPlan lotteryPlan) throws Exception {
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
		//子玩法
		int playType = lotteryPlan.getPlayType().getValue();
		if(playType== PlayType.ZXHZ.getValue() || playType == PlayType.ZSHZ.getValue()){
			tempContent=tempContent.replaceAll("\r\n", "|");
		}
		String[] contentGroup = tempContent.split("\\\n");
		
		
		
		for (int i = 0; i < contentGroup.length; i++) {
			List subList = new ArrayList();
			String ticketContent = contentGroup[i];
			if (playType == PlayType.ZX.getValue() || playType == PlayType.ZXFS.getValue()) {
				//内容转换
				ticketContent=ChangeContext(ticketContent);
				if (ticketContent.length() == 5) {
					addNewContent(playTypeContentMap, PlayType.ZX.getValue(), ticketContent.replaceAll("\\,", "").replaceAll("\\|", ","));
				} else {
					addNewContent(playTypeContentMap, PlayType.ZXFS.getValue(), ticketContent.replaceAll("\\,", "").replaceAll("\\|", ","));
				}
			} else if (playType == PlayType.ZS.getValue()) {
				//转换内容
				addNewContent(playTypeContentMap, PlayType.ZS.getValue(), ticketContent);
			} else if (playType == PlayType.ZL.getValue()) {
				//转换内容
				ticketContent=ChangeContextZX(ticketContent);
				if (ticketContent.length() == 5) {
					addNewContent(playTypeContentMap, PlayType.ZHUXUAN.getValue(), ticketContent);
				} else {
					addNewContent(playTypeContentMap, PlayType.ZL.getValue(), ticketContent.replaceAll("\\,", ""));
				}
			} else if (playType == PlayType.ZXHZ.getValue()) { //直选和值
				String[] subContent = ticketContent.split("\\|");
				for (int j = 0; j < subContent.length; j++) {
					addNewContent(playTypeContentMap, PlayType.ZXHZ.getValue(), subContent[j]);
				}
			}else if (playType == PlayType.ZSHZ.getValue()) { //组三和值
				String[] subContent = ticketContent.split("\\|");
				for (int j = 0; j < subContent.length; j++) {
					addNewContent(playTypeContentMap, PlayType.ZHUXUAN.getValue(),  zshz(subContent[j]));
				}
			} else if (playType == PlayType.ZLHZ.getValue()) { //组六和值
				// .replaceAll("\\|", ",")
				String[] subContent = ticketContent.split("\\|");
				for (int j = 0; j < subContent.length; j++) {
					addNewContent(playTypeContentMap, PlayType.ZHUXUAN.getValue(), zlhz(subContent[j]));
				}
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
			}else if (subPlayType == PlayType.ZS.getValue()) {
				for (int i = 0; i < subList.size(); i++) {
					Ticket ticket = new Ticket();
					ticket.setPlayType(PlayType.getItem(subPlayType));
					ticket.setAmount(getZsZhuShu(subList.get(i).toString()) * 2 * lotteryPlan.getMultiple());
					ticket.setContent(subList.get(i).toString());
					ticket = SetTicket.setTicket(ticket, lotteryPlan);
					ticketList.add(ticket);
				}
			}else if (subPlayType == PlayType.ZL.getValue()) {
				for (int i = 0; i < subList.size(); i++) {
					Ticket ticket = new Ticket();
					ticket.setPlayType(PlayType.getItem(subPlayType));
					ticket.setAmount(getZlZhuShu(subList.get(i).toString()) * 2 * lotteryPlan.getMultiple());
					ticket.setContent(subList.get(i).toString());
					ticket = SetTicket.setTicket(ticket, lotteryPlan);
					ticketList.add(ticket);
				}
			}else if (subPlayType == PlayType.ZXHZ.getValue()) {// 组三和值
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
			}  else if (subPlayType == PlayType.ZLHZ.getValue()) {// 组三和值
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
			}else {
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

	@SuppressWarnings("unchecked")
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
	public String ChangeContextZX(String context){
		StringBuffer sb=new StringBuffer();
		sb.append(context.replaceAll("", ",")).delete(0, 1).deleteCharAt(sb.length()-1);
		return sb.toString();
	}
	

	
	private int getCommonZS(String content) {
		String[] areas = content.split("\\,");
		int count = 1;
		for (int i = 0; i < areas.length; i++) {
			count *= areas[i].length();
		}
		return count;
	}
	
	public int getZsZhuShu(String content) {
		return 2 * combination(content.length(), 2);
	}

	public int getZlZhuShu(String content) {
		return combination(content.length(), 3);
	}
	
	public static void main(String[] args) {
		StringBuffer sb = new StringBuffer();
		//sb.append("(12)34"); //12,3,4
		//sb.append("01234");  //12
		//sb.append("1234"); //1234
		//sb.append("1\r\n2\r\n3"); //两张票只支持单式投注7 | 8
		sb.append("3"); //两张票只支持单式投注7 | 8
		//sb.append("\n").append("1\r\n2\r\n4");
		System.out.println(sb.toString());
		LotteryPlan plan=new LotteryPlan();
		plan.setContent(sb.toString());
		plan.setPlayType(PlayType.ZSHZ);
		plan.setSelectType(SelectType.CHOOSE_SELF);
		plan.setMultiple(1);
		plan.setAddAttribute("1");
		plsChaiPiao dlt=new plsChaiPiao();
		try {
			dlt.chaiPiao(plan);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("sssssssssssss");
	}

}
