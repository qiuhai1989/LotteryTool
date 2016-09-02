package com.yuncai.modules.lottery.factorys.chupiao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.omg.CORBA.PUBLIC_MEMBER;

import sun.awt.image.OffScreenImage;

import com.yuncai.core.longExce.ServiceException;
import com.yuncai.core.tools.DateTools;
import com.yuncai.core.tools.NumberTools;
import com.yuncai.modules.lottery.dao.oracleDao.ticket.TicketDAO;
import com.yuncai.modules.lottery.model.Oracle.Ticket;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlayType;
import com.yuncai.modules.lottery.service.oracleService.ticket.TicketService;
import com.yuncai.modules.lottery.sporttery.OptionItem;
import com.yuncai.modules.lottery.sporttery.model.FootBallBetContent;
import com.yuncai.modules.lottery.sporttery.model.SportteryBetContentModel;
import com.yuncai.modules.lottery.sporttery.support.CommonUtils;
import com.yuncai.modules.lottery.sporttery.support.MatchItem;
import com.yuncai.modules.lottery.sporttery.support.SportteryOption;
import com.yuncai.modules.lottery.sporttery.support.football.FootBallMatchItem;
import com.yuncai.modules.lottery.sporttery.support.football.FootballTicketModel;
import com.yuncai.modules.lottery.sporttery.support.football.OptionItemBF;
import com.yuncai.modules.lottery.sporttery.support.football.OptionItemBQC;
import com.yuncai.modules.lottery.sporttery.support.football.OptionItemJQS;
import com.yuncai.modules.lottery.sporttery.support.football.OptionItemSPF;

public class TicketFormater {

	// 老体彩投注格式转换
	public static String LTCFormater(LotteryType lotteryType, PlayType playType, String content) {
		// 返回结果格式：号码#玩法
		String result = "";

		// 排列三
		if (lotteryType == LotteryType.SZPL3) {
			// 单式
			if (playType == PlayType.ZX) {
				result = "[" + content.replace("^", "];[").replace(",", "]/[") + "]" + "#1";
				return result;
			}
			// 直选复式
			if (playType == PlayType.ZXFS) {
				String[] balls = content.split(",");
				for (int i = 0; i < balls.length; i++) {
					// 只有一个号码时
					if (balls[i].length() == 1) {
						result += "[" + balls[i] + "]";
						// 号码大于一个时
					} else if (balls[i].length() > 1) {
						result += "[";
						// 复式号码组合
						String[] ball = new String[balls[i].length()];
						for (int k = 0; k < ball.length; k++) {
							ball[k] = balls[i].substring(k, k + 1);
						}
						// 排序
						ball = sortBalls(ball);
						for (int k = 0; k < ball.length; k++) {
							result += ball[k];
							// 中间用“，”号分隔
							if (k != ball.length - 1) {
								result += ",";
							}
						}
						result += "]";
					}
					// 中间用“/”号分隔
					if (i != balls.length - 1) {
						result += "/";
					}
				}
				result += "#2";
			}
			// 直选和值
			if (playType == PlayType.ZXHZ) {
				result = "[" + content + "]" + "#71";
				return result;
			}

			// 组三单式或组六单式
			if (playType == PlayType.ZHUXUAN) {

			}

		}

		// 排列五、足球胜负、任九场、六场半全、四场进球
		if (lotteryType == LotteryType.SZPL5 || lotteryType == LotteryType.ZCSFC || lotteryType == LotteryType.ZCRJC
				|| lotteryType == LotteryType.JQC || lotteryType == LotteryType.LCBQC || lotteryType == LotteryType.QXC) {
			// 单式
			if (playType == PlayType.DS) {
				result = "[" + content.replace("^", "];[").replace(",", "]/[") + "]" + "#1";
				return result;
			}
			// 复式
			if (playType == PlayType.FS) {
				String[] balls = content.split(",");
				for (int i = 0; i < balls.length; i++) {
					// 只有一个号码时
					if (balls[i].length() == 1) {
						result += "[" + balls[i] + "]";
						// 号码大于一个时
					} else if (balls[i].length() > 1) {
						result += "[";
						// 复式号码组合
						String[] ball = new String[balls[i].length()];
						for (int k = 0; k < ball.length; k++) {
							ball[k] = balls[i].substring(k, k + 1);
						}
						// 排序
						ball = sortBalls(ball);
						for (int k = 0; k < ball.length; k++) {
							result += ball[k];
							// 中间用“，”号分隔
							if (k != ball.length - 1) {
								result += ",";
							}
						}

						result += "]";
					}
					// 中间用“/”号分隔
					if (i != balls.length - 1) {
						result += "/";
					}
				}

				result += "#2";
			}
			// 任九场需去掉“-”号
			if (lotteryType == LotteryType.ZCRJC) {
				result = result.replace("-", "");
			}
		}

		// 大乐透
		if (lotteryType == LotteryType.TCCJDLT) {
			// 单式
			if (playType == PlayType.DS) {
				String[] ticks = content.split("\\^");
				for (int t = 0; t < ticks.length; t++) {
					// 前区号码(从大到小排序)
					String[] redBalls = sortBalls(ticks[t].split("-")[0].split(" "));
					// 后区号码(从大到小排序)
					String[] blueBalls = sortBalls(ticks[t].split("-")[1].split(" "));
					result += "[";
					for (int i = 0; i < redBalls.length; i++) {
						result += redBalls[i];
						// 中间用“，”号分隔
						if (i != redBalls.length - 1) {
							result += ",";
						}
					}
					result += "]/[";
					for (int i = 0; i < blueBalls.length; i++) {
						result += blueBalls[i];
						// 中间用“，”号分隔
						if (i != blueBalls.length - 1) {
							result += ",";
						}
					}
					result += "]";
					if (t != ticks.length - 1) {
						result += ";";
					}
				}
				return result;
			}
			// 复式
			if (playType == PlayType.FS) {
				// 前区号码(从大到小排序)
				String[] redBalls = sortBalls(content.split("-")[0].split(" "));
				// 后区号码(从大到小排序)
				String[] blueBalls = sortBalls(content.split("-")[1].split(" "));
				result += "[";
				for (int i = 0; i < redBalls.length; i++) {
					result += redBalls[i];
					// 中间用“，”号分隔
					if (i != redBalls.length - 1) {
						result += ",";
					}
				}
				result += "]/[";
				for (int i = 0; i < blueBalls.length; i++) {
					result += blueBalls[i];
					// 中间用“，”号分隔
					if (i != blueBalls.length - 1) {
						result += ",";
					}
				}
				result += "]";
				// 前区复式：玩法 2
				if (redBalls.length > 5 && blueBalls.length == 2) {
					result += "#2";
				}
				// 后区复式：玩法 3
				if (redBalls.length == 5 && blueBalls.length > 2) {
					result += "#3";
				}
				// 前后区都复式：玩法4
				if (redBalls.length > 5 && blueBalls.length > 2) {
					result += "#4";
				}
			}

		}

		return result;
	}

	// 竞彩足球投注格式转换
	public static String JcFormater(LotteryType lotteryType, PlayType playType, String innerContent) {
		// 返回结果格式：投注内容#玩法
		String result = "";
		// 玩法
		String playTypeF = JcPlayTypeformater(playType);
		FootballTicketModel t = CommonUtils.Object4TikectJson(innerContent, FootballTicketModel.class, FootBallMatchItem.class);
		// 解析内部投注内容（JSON串）
		List<FootBallMatchItem> itemLst = t.getMatchItems();
		Map<String, String> Bet = new HashMap<String, String>();
		// 场次数组，用于排序
		String[] LineIDArray = new String[itemLst.size()];
		for (int i = 0; i < itemLst.size(); i++) {
			// 期名
			String IssueName = String.valueOf(itemLst.get(i).getIntTime());
			// 周数,星期天：0---星期六：6
			Integer weekStr = DateTools.getWeekInt(DateTools.StringToDate(IssueName + "", "yyyyMMdd")) - 1;
			// 转换星期天为7
			if (weekStr == 0) {
				weekStr = 7;
			}
			// 场次
			String LineID = NumberTools.IntToString(itemLst.get(i).getLineId(), 3);
			LineIDArray[i] = LineID;
			// 竞彩选项
			List<SportteryOption> optLst = itemLst.get(i).getOptions();
			String[] Option = new String[optLst.size()];
			for (int j = 0; j < optLst.size(); j++) {
				String innerOpt = optLst.get(j).getValue();
				Integer optType = optLst.get(j).getType()==null||optLst.get(j).getType().equals("")?-1: Integer.parseInt( optLst.get(j).getType());
				Option[j] = CommonUtils.getByPlayType(lotteryType, innerOpt,optType).getBozhongValue();
			}
			Option = sortBalls(Option);
			String optionString = "";
			for (int k = 0; k < Option.length; k++) {
				optionString += Option[k];
				if (k != Option.length - 1) {
					optionString += ",";
				}
			}
			// 单注投注信息
			Bet.put(LineID, IssueName + "|" + weekStr + "|" + LineID + "|" + optionString);
		}
		// 场次排序
		LineIDArray = sortBalls(LineIDArray);
		for (int i = 0; i < LineIDArray.length; i++) {
			result += Bet.get(LineIDArray[i]);
			if (i != LineIDArray.length - 1) {
				result += ";";
			}
		}
		result += "#" + playTypeF;
		return result;
	}

	// 竞彩足球票口返回结果转换
	public static String JcSPformater(Ticket ticket) {
		String result = "";
		FootballTicketModel t = CommonUtils.Object4TikectJson(ticket.getContent(), FootballTicketModel.class, FootBallMatchItem.class);
		// 解析内部投注内容（JSON串）
		List<FootBallMatchItem> itemLst = t.getMatchItems();
		// 票口返回结果SP值数组
		String[] SPresult = ticket.getSP().split("/");
		// 按场次分组 (key:星期-场次，value:赔率list)
		Map<String, List<SportteryOption>> SPMap = new HashMap<String, List<SportteryOption>>();

		// 循环各场次
		for (int i = 0; i < SPresult.length - 1; i++) {
			// 竞彩选项
			List<SportteryOption> optLst = new ArrayList<SportteryOption>();
			// 获取不同投注结果的SP值
			String[] SPs = SPresult[i].split(":")[1].replace("[", "").replace("]", "").split(",");
			for (int j = 0; j < SPs.length; j++) {
				SportteryOption opt = new SportteryOption();

				// 胜平负、让球胜平负
				if (ticket.getLotteryType() == LotteryType.JC_ZQ_SPF || ticket.getLotteryType() == LotteryType.JC_ZQ_RQSPF) {
					opt.setValue(OptionItemSPF.getItemByText(SPs[j].split("=")[0]).getValue());
				}

				// 总进球数
				if (ticket.getLotteryType() == LotteryType.JC_ZQ_JQS) {
					opt.setValue(OptionItemJQS.getItemByBozhongValue(SPs[j].split("=")[0]).getValue());
				}

				// 比分
				if (ticket.getLotteryType() == LotteryType.JC_ZQ_BF) {
					opt.setValue(OptionItemBF.getItemBybozhongValue(SPs[j].split("=")[0]).getValue());
				}

				// 半全场
				if (ticket.getLotteryType() == LotteryType.JC_ZQ_BQC) {
					opt.setValue(OptionItemBQC.getItemByBozhongValue(SPs[j].split("=")[0]).getValue());
				}
				
				// 让球胜平负 
				if (ticket.getLotteryType() == LotteryType.JC_ZQ_RQSPF) {
					//去掉让球数
					opt.setAward(Double.parseDouble(SPs[j].split("=")[1].split("\\^")[0]));
				} else {
					opt.setAward(Double.parseDouble(SPs[j].split("=")[1]));
				}
				optLst.add(opt);
			}
			SPMap.put(SPresult[i].split(":")[0], optLst);
		}

		for (int i = 0; i < itemLst.size(); i++) {
			// 期名
			String IssueName = String.valueOf(itemLst.get(i).getIntTime());
			// 周数,星期天：0---星期六：6
			Integer weekStr = DateTools.getWeekInt(DateTools.StringToDate(IssueName + "", "yyyyMMdd")) - 1;
			// 转换星期天为7
			if (weekStr == 0) {
				weekStr = 7;
			}
			// 场次
			String LineID = NumberTools.IntToString(itemLst.get(i).getLineId(), 3);

			// 竞彩选项
			itemLst.get(i).setOptions(SPMap.get(weekStr + "-" + LineID));

			result = JSONObject.fromObject(t).toString();
		}

		return result;
	}

	// 竞彩足球玩法格式转换
	public static String JcPlayTypeformater(PlayType playType) {
		String result = "";
		switch (playType.getValue()) {
		case 117:
			result = "0";
			break;
		case 118:
			result = "2-1";
			break;
		case 119:
			result = "3-1";
			break;
		case 120:
			result = "4-1";
			break;
		case 121:
			result = "5-1";
			break;
		case 122:
			result = "6-1";
			break;
		case 123:
			result = "7-1";
			break;
		case 124:
			result = "8-1";
			break;
		default:
			break;
		}
		return result;

	}

	// 彩种标识转换
	public static String playTypeFormater(LotteryType lotteryType) {
		String result = "";
		switch (lotteryType.getValue()) {
		case 39:// 大乐透
			result = "DLT";
			break;
		case 3:// 七星彩
			result = "QXC";
			break;
		case 63:// 排列三
			result = "PL3";
			break;
		case 64:// 排列五
			result = "PL5";
			break;
		case 74:// 胜平负
			result = "SPF14";
			break;
		case 75:// 任九场
			result = "SPF9";
			break;
		case 2:// 四场进球
			result = "CJQ4";
			break;
		case 15:// 六场半全
			result = "CBQC6";
			break;

		case 7207:// 竞彩足球胜负(非让球)
			result = "FTBRQSPF";
			break;
		case 7202:// 竞彩足球比分
			result = "FTBF";
			break;
		case 7203:// 竞彩足球进球数
			result = "FTJQS";
			break;
		case 7204:// 竞彩足球半全场
			result = "FTBQC";
			break;
		case 7201:// 竞彩足球让球胜平负
			result = "FTSPF";
			break;
		default:
			break;
		}

		return result;
	}

	// 冒泡排序
	public static String[] sortBalls(String[] arrStr) {
		String temp;
		for (int i = 0; i < arrStr.length; i++) {
			for (int j = arrStr.length - 1; j > i; j--) {
				if (Integer.parseInt(arrStr[i]) > Integer.parseInt(arrStr[j])) {
					temp = arrStr[i];
					arrStr[i] = arrStr[j];
					arrStr[j] = temp;
				}
			}
		}
		return arrStr;
	}

	public static void main(String[] arg) {
//		 System.out.println("----start-------");
//		 Ticket ticket = new Ticket();
//		 ticket.setContent("{'matchItems':[{'intTime':'20130724','lineId':15,'shedan':false,'options':[{'award':4.15,'value':'2'}],'matchId':2317},{'intTime':'20130724','lineId':16,'shedan':false,'options':[{'award':1.92,'value':'1'},{'award':3.35,'value':'2'}],'matchId':2318},{'intTime':'20130725','lineId':1,'shedan':false,'options':[{'award':2.9,'value':'1'}],'matchId':2319},{'intTime':'20130725','lineId':2,'shedan':false,'options':[{'award':3.4,'value':'2'}],'matchId':2320}],'multiple':1,'passMode':1,'passType':'P2_1','showPassTypes':''}");
//		 ticket.setSP("3-015:[平=1^1]/3-016:[胜=2^2,平=3^-1]/4-001:[胜=4^0]/4-002:[平=5^1]/18.20");
//		 ticket.setLotteryType(LotteryType.JC_ZQ_RQSPF);
//		 System.out.println(JcSPformater(ticket));
//		 System.out.println("----end-------");
		for (int i = 1; i > 0; i--) {
			String gameid = "CBQC6";
			int currentIssuse = 13093;
			Integer issuse = currentIssuse;
			System.out.println("----" + gameid + "第" + issuse + "期-------");
			//testBuy1.getInf101Data(gameid, "");
			testBuy1.getInf112Data(gameid, "");
			//System.out.println(testBuy1.getInf112DataStr(gameid, issuse.toString()));
		}
	}

}
