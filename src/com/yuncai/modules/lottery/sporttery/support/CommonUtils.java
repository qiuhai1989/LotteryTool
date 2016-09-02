package com.yuncai.modules.lottery.sporttery.support;

import java.util.List;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

import java.util.Map;

import com.yuncai.modules.lottery.model.Oracle.FtMatch;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.sporttery.OptionItem;
import com.yuncai.modules.lottery.sporttery.model.SportteryBetContentModel;
import com.yuncai.modules.lottery.sporttery.model.SportteryBetContentModelTurbid;
import com.yuncai.modules.lottery.sporttery.support.basketball.OptionItemDXF;
import com.yuncai.modules.lottery.sporttery.support.basketball.OptionItemRFSF;
import com.yuncai.modules.lottery.sporttery.support.basketball.OptionItemSF;
import com.yuncai.modules.lottery.sporttery.support.basketball.OptionItemSFC;
import com.yuncai.modules.lottery.sporttery.support.football.OptionItemBF;
import com.yuncai.modules.lottery.sporttery.support.football.OptionItemBQC;
import com.yuncai.modules.lottery.sporttery.support.football.OptionItemHT;
import com.yuncai.modules.lottery.sporttery.support.football.OptionItemJQS;
import com.yuncai.modules.lottery.sporttery.support.football.OptionItemSPF;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class CommonUtils {
	public static final String beginIntTimeKey = "btk";
	public static final String endIntTimeKey = "etk";
	public static final String homeScoreKey = "hsk";
	public static final String guestScoreKey = "gsk";
	public static final String handicapKey = "hck";
	public static final String bigSmallKey = "bsk";
	public static final String resultSFKey = "rsfk";
	public static final String resultRFSFKey = "rfsfk";
	public static final String resultSFCKey = "rsfck";
	public static final String resultDXFKey = "rdxfk";
	public static final String CONCEDEKEY = "concede";
	public static final String resultHalfHomeScore = "halfHomeScore";
	public static final String resultHalfGuestScore = "halfGuestScore";
	public static final String resultSPFKey = "spfk";
	public static final String resultBFKey = "bfk";
	public static final String resultJQSKey = "jqsk";
	public static final String resultBQCKey = "bqck";
	public static final String resultRQSPFKey="rqspfk";

	public static String genMatchKey(Integer intTime, Integer lineId) {
		return intTime + "_" + lineId;
	}

	public static String getShowMatchKey(Integer intTime, Integer lineId) {
		String lineIdText = lineId.toString();
		for (int i = lineIdText.length(); i < 3; i++) {
			lineIdText = "0" + lineIdText;
		}
		String showLineId=lineIdText;
		String showMatchKey=intTime.toString().substring(2)+showLineId;
		return showMatchKey;
	}
	
	public static String getShowMatchLined(Integer lineId) {
		String lineIdText = lineId.toString();
		for (int i = lineIdText.length(); i < 3; i++) {
			lineIdText = "0" + lineIdText;
		}
		String showLineId=lineIdText;
		return showLineId;
	}
	
	public static Double getAward(String award) {
		double wAward = 0.0;
		if (!"".equals(award)) {
			wAward = Double.parseDouble(award);
		}
		return wAward;
	}

	public static String genAwardCacheKey(Integer matchId, String playType, Integer passMode) {
		return matchId + "_" + playType + "_" + passMode;
	}

	/**
	 * 从json对象集合表达式中得到一个java对象列表
	 * 
	 * @param jsonString
	 * @param pojoClass
	 * @param classMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> getList4Json(String jsonString, Class<T> pojoClass, Map classMap) {
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		JSONObject jsonObject;
		T pojoValue;
		List<T> list = new ArrayList<T>();
		for (int i = 0; i < jsonArray.size(); i++) {
			jsonObject = jsonArray.getJSONObject(i);
			if (classMap != null) {
				pojoValue = (T) JSONObject.toBean(jsonObject, pojoClass, classMap);
			} else {
				pojoValue = (T) JSONObject.toBean(jsonObject, pojoClass);
			}
			list.add(pojoValue);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public static <T> List<List<T>> getPlanarList4Json(String jsonString, Class<T> pojoClass, Map classMap) {
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		JSONArray jsonArray2;
		JSONObject jsonObject;
		T pojoValue;
		List<List<T>> planarList = new ArrayList<List<T>>();
		for (int i = 0; i < jsonArray.size(); i++) {
			jsonArray2 = jsonArray.getJSONArray(i);
			List<T> list = new ArrayList<T>();
			for (int j = 0; j < jsonArray2.size(); j++) {
				jsonObject = jsonArray2.getJSONObject(j);
				if (classMap != null) {
					pojoValue = (T) JSONObject.toBean(jsonObject, pojoClass, classMap);
				} else {
					pojoValue = (T) JSONObject.toBean(jsonObject, pojoClass);
				}
				list.add(pojoValue);
			}
			planarList.add(list);
		}
		return planarList;
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> getJsonData(Object obj, Class clazz) {
		JSONObject jsonObject = JSONObject.fromObject(obj);
		List<T> list = new ArrayList<T>();
		for (Iterator iter = jsonObject.keys(); iter.hasNext();) {
			String key = (String) iter.next();
			JSONArray array = jsonObject.getJSONArray(key);
			for (int i = 0; i < array.size(); i++) {
				JSONObject object = (JSONObject) array.get(i);
				T t = (T) JSONObject.toBean(object, clazz);
				if (t != null)
					list.add(t);
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public static <T extends SportteryBetContentModel<X>, X extends MatchItem> T Object4Json(String content, Class<T> modelClass,
			Class<X> matchitemClass) {
		JSONObject json = JSONObject.fromObject(content);   //解释
		T contentObj = (T) JSONObject.toBean(json, modelClass); //重新组装
		String matchItemStr = json.getString("matchItems");
		Map classMap = new HashMap<String, Class>();
		classMap.put("options", SportteryOption.class);
		List<X> matchItem = CommonUtils.getList4Json(matchItemStr, matchitemClass, classMap);
		contentObj.setMatchItems(matchItem);
		JSONArray arr = json.getJSONArray("passTypes");
		List<SportteryPassType> type = new ArrayList<SportteryPassType>();
		for (int i = 0; i < arr.size(); i++) {
			SportteryPassType t = SportteryPassType.valueOf(arr.get(i).toString());
			type.add(t);
		}
		contentObj.setPassTypes(type);
		return contentObj;
	}
	
	/**
	 * 混串
	 * @param <T>
	 * @param <X>
	 * @param content
	 * @param modelClass
	 * @param matchitemClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends SportteryBetContentModelTurbid<X>, X extends MatchItemTurbid> T Object4JsonTurbid(String content, Class<T> modelClass,
			Class<X> matchitemClass) {
		JSONObject json = JSONObject.fromObject(content);
		if(!json.containsKey("passTypes")){//把passType组建passTypes[]
			String str = json.getString("passType");
			JSONArray arr=new JSONArray();
			arr.add(str);
			json.put("passTypes", arr);
			json.remove("passType");
		}
		T contentObj = (T) JSONObject.toBean(json, modelClass);
		String matchItemStr = json.getString("matchItems");
		Map classMap = new HashMap<String, Class>();
		classMap.put("options", SportteryOption.class);
		List<X> matchItem = CommonUtils.getList4Json(matchItemStr, matchitemClass, classMap);
		contentObj.setMatchItems(matchItem);
		List<SportteryPassType> type = new ArrayList<SportteryPassType>();
		JSONArray arr = json.getJSONArray("passTypes");
		for (int i = 0; i < arr.size(); i++) {
			SportteryPassType t = SportteryPassType.valueOf(arr.get(i).toString());
			type.add(t);
		}
		contentObj.setPassTypes(type);
		return contentObj;
	}


	@SuppressWarnings("unchecked")
	public static <T extends SportteryTicketModel<X>, X extends MatchItem> T Object4TikectJson(String content, Class<T> modelClass,
			Class<X> matchitemClass) {
		JSONObject json = JSONObject.fromObject(content);
		T contentObj = (T) JSONObject.toBean(json, modelClass);
		String matchItemStr = json.getString("matchItems");
		Map classMap = new HashMap<String, Class>();
		classMap.put("options", SportteryOption.class);
		List<X> matchItem = CommonUtils.getList4Json(matchItemStr, matchitemClass, classMap);
		contentObj.setMatchItems(matchItem);
		return contentObj;
	}
	
	/**
	 * 混串
	 * @param <T>
	 * @param <X>
	 * @param content
	 * @param modelClass
	 * @param matchitemClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends SportteryTicketModelTurbid<X>, X extends MatchItemTurbid> T Object4TikectJsonTurbid(String content, Class<T> modelClass,
			Class<X> matchitemClass) {
		JSONObject json = JSONObject.fromObject(content);
		T contentObj = (T) JSONObject.toBean(json, modelClass);
		String matchItemStr = json.getString("matchItems");
		Map classMap = new HashMap<String, Class>();
		classMap.put("options", SportteryOption.class);
		List<X> matchItem = CommonUtils.getList4Json(matchItemStr, matchitemClass, classMap);
		contentObj.setMatchItems(matchItem);
		return contentObj;
	}
	
	

	public static OptionItem getByPlayType(LotteryType type, SportteryOption option) {
		String value = option.getValue();
		String itemType = option.getType();
		itemType = itemType == null ? "-1" : itemType;
		itemType = itemType.equals("") ? "-1" : itemType;
		return getByPlayType(type, value, Integer.parseInt(itemType));
	}

	public static OptionItem getByPlayType(LotteryType type, String value) {
		switch (type.getValue()) {
		case 7207:
			return OptionItemSPF.getItemByValue(value);
		case 7202:
			return OptionItemBF.getItemByValue(value);
		case 7203:
			return OptionItemJQS.getItemByValue(value);
		case 7204:
			return OptionItemBQC.getItemByValue(value);
		case 7201:
			return OptionItemSPF.getItemByValue(value);
		default:
			throw new RuntimeException("玩法类型不存在");
		}
	}
	
	public static OptionItem getByPlayType(LotteryType type, String value, int itemType) {
		switch (type.getValue()) {
		case 7201:
		case 7207:
			return OptionItemSPF.getItemByValue(value);
		case 7202:
			return OptionItemBF.getItemByValue(value);
		case 7203:
			return OptionItemJQS.getItemByValue(value);
		case 7204:
			return OptionItemBQC.getItemByValue(value);
		case 7206:
			return OptionItemHT.getItemByValue(itemType + "", value);
		case 7301:
			return OptionItemSF.getItemByValue(value);
		case 7302:
			return OptionItemRFSF.getItemByValue(value);
		case 7303:
			return OptionItemSFC.getItemByValue(value);
		case 7304:
			return OptionItemDXF.getItemByValue(value);
		case 7305: 
			return com.yuncai.modules.lottery.sporttery.support.basketball.OptionItemHT.getItemByValue(itemType + "", value);
		default:
			throw new RuntimeException("玩法类型不存在");
		}
	}

//	public static OptionItem getByPlayTypeAndBoZhongValue(LotteryType type, String value) {
//		switch (type.getValue()) {
//		case 30:
//			return OptionItemSF.getItemByBozhongValue(value);
//		case 31:
//			return OptionItemRFSF.getItemByBozhongValue(value);
//		case 32:
//			return OptionItemSFC.getItemByBozhongValue(value);
//		case 33:
//			return OptionItemDXF.getItemByBozhongValue(value);
//		default:
//			throw new RuntimeException("玩法类型不存在");
//		}
//	}

	public static Map<String, Integer> getBgTimeAndEdTime() {
		return getBgTimeAndEdTime(15);
	}

	public static Map<String, Integer> getBgTimeAndEdTime(int dayLength) {
		Map<String, Integer> timeMap = new HashMap<String, Integer>();
		Calendar d = Calendar.getInstance();//
		String dateformat = "yyyyMMdd";
		SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
		String endDateIntTimeStr = sdf.format(d.getTime());
		Integer endDateIntTime = Integer.valueOf(endDateIntTimeStr);
		timeMap.put(endIntTimeKey, endDateIntTime);
		d.add(Calendar.DAY_OF_MONTH, -dayLength);
		String beginDateIntTimeStr = sdf.format(d.getTime());
		Integer beginDateIntTime = Integer.valueOf(beginDateIntTimeStr);
		timeMap.put(beginIntTimeKey, beginDateIntTime);
		return timeMap;
	}



	public static OptionItem getZQResultOptionItem(String type, Map<String, String> resultMap) {// 只是用于单关，过关不需要
		String tempHomeScore = resultMap.get(homeScoreKey);
		String tempGuestScore = resultMap.get(guestScoreKey);
		String tempconcede = resultMap.get(CONCEDEKEY);
		String halfHomeScoreStr = resultMap.get(resultHalfHomeScore);
		String halfGuestScoreStr = resultMap.get(resultHalfGuestScore);

		Integer homeScore = Integer.valueOf(tempHomeScore);// 主队分数:为-1表示赛事取消
		Integer guestScore = Integer.valueOf(tempGuestScore);// 客队分数:为-1表示赛事取消
		Integer concede = Integer.valueOf(tempconcede);// 让分
		Integer halfHomeScore = Integer.valueOf(halfHomeScoreStr);
		Integer halfGuestScore = Integer.valueOf(halfGuestScoreStr);

		switch (Integer.valueOf(type)) {
		case 7207: {
			concede=0; //非让球胜平负 让球数都是0
			if (homeScore + concede > guestScore)
				return OptionItemSPF.WIN;// 主胜
			else if (homeScore + concede < guestScore)
				return OptionItemSPF.LOSE;// 主负
			else
				return OptionItemSPF.DRAW;// 主平
		}
		case 7201: {
			if (homeScore + concede > guestScore)
				return OptionItemSPF.WIN;// 主胜
			else if (homeScore + concede < guestScore)
				return OptionItemSPF.LOSE;// 主负
			else
				return OptionItemSPF.DRAW;// 主平
		}
		case 7202: {
			if (homeScore == 1 & guestScore == 0) {
				return OptionItemBF.WIN10;// 1:0
			} else if (homeScore == 2 & guestScore == 0) {
				return OptionItemBF.WIN20;// 2:0
			} else if (homeScore == 2 & guestScore == 1) {
				return OptionItemBF.WIN21;// 2:1
			} else if (homeScore == 3 & guestScore == 0) {
				return OptionItemBF.WIN30;// 3:0
			} else if (homeScore == 3 & guestScore == 1) {
				return OptionItemBF.WIN31;// 1:3
			} else if (homeScore == 3 & guestScore == 2) {
				return OptionItemBF.WIN32;// 2:3
			} else if (homeScore == 4 & guestScore == 0) {
				return OptionItemBF.WIN40;// 0:4
			} else if (homeScore == 4 & guestScore == 1) {
				return OptionItemBF.WIN41;// 1:4
			} else if (homeScore == 4 & guestScore == 2) {
				return OptionItemBF.WIN42;// 2:4
			} else if (homeScore == 5 & guestScore == 0) {
				return OptionItemBF.WIN50;// 0:5
			} else if (homeScore == 5 & guestScore == 1) {
				return OptionItemBF.WIN51;// 1:5
			} else if (homeScore == 5 & guestScore == 2) {
				return OptionItemBF.WIN52;// 2:5
			}

			else if (homeScore == 0 & guestScore == 0) {
				return OptionItemBF.DRAW00;// 0:0
			} else if (homeScore == 1 & guestScore == 1) {
				return OptionItemBF.DRAW11;// 1:1
			} else if (homeScore == 2 & guestScore == 2) {
				return OptionItemBF.DRAW22;// 2:2
			} else if (homeScore == 3 & guestScore == 3) {
				return OptionItemBF.DRAW33;// 3:3
			}

			else if (homeScore == 0 & guestScore == 1) {
				return OptionItemBF.LOSE01;// 0:1
			} else if (homeScore == 0 & guestScore == 2) {
				return OptionItemBF.LOSE02;// 0:2
			} else if (homeScore == 1 & guestScore == 2) {
				return OptionItemBF.LOSE12;// 1:2
			} else if (homeScore == 0 & guestScore == 3) {
				return OptionItemBF.LOSE03;// 0:3
			} else if (homeScore == 1 & guestScore == 3) {
				return OptionItemBF.LOSE13;// 1:3
			} else if (homeScore == 2 & guestScore == 3) {
				return OptionItemBF.LOSE23;// 2:3
			} else if (homeScore == 0 & guestScore == 4) {
				return OptionItemBF.LOSE04;// 0:4
			} else if (homeScore == 1 & guestScore == 4) {
				return OptionItemBF.LOSE14;// 1:4
			} else if (homeScore == 2 & guestScore == 4) {
				return OptionItemBF.LOSE24;// 2:4
			} else if (homeScore == 0 & guestScore == 5) {
				return OptionItemBF.LOSE05;// 0:5
			} else if (homeScore == 1 & guestScore == 5) {
				return OptionItemBF.LOSE15;// 1:5
			} else if (homeScore == 2 & guestScore == 5) {
				return OptionItemBF.LOSE25;// 2:5
			} else if (homeScore > guestScore) {
				return OptionItemBF.WIN_OTHER;// 
			} else if (homeScore == guestScore) {
				return OptionItemBF.DRAW_OTHER;// 
			} else if (homeScore < guestScore) {
				return OptionItemBF.LOSE_OTHER;// 
			}

		}
		case 7203: {
			if ((homeScore + guestScore) == 0)
				return OptionItemJQS.S0;
			else if ((homeScore + guestScore) == 1)
				return OptionItemJQS.S1;
			else if ((homeScore + guestScore) == 2)
				return OptionItemJQS.S2;
			else if ((homeScore + guestScore) == 3)
				return OptionItemJQS.S3;
			else if ((homeScore + guestScore) == 4)
				return OptionItemJQS.S4;
			else if ((homeScore + guestScore) == 5)
				return OptionItemJQS.S5;
			else if ((homeScore + guestScore) == 6)
				return OptionItemJQS.S6;
			else
				return OptionItemJQS.S7;
		}
		case 7204: {
			if (halfHomeScore > halfGuestScore && homeScore > guestScore)
				return OptionItemBQC.WIN_WIN;// 胜胜
			else if (halfHomeScore > halfGuestScore && homeScore.equals(guestScore))
				return OptionItemBQC.WIN_DRAW;// 胜平
			else if (halfHomeScore > halfGuestScore && homeScore < guestScore)
				return OptionItemBQC.WIN_LOSE;// 胜负
			else if (halfHomeScore.equals(halfGuestScore) && homeScore > guestScore)
				return OptionItemBQC.DRAW_WIN;// 平胜
			else if (halfHomeScore.equals(halfGuestScore) && homeScore.equals(guestScore))
				return OptionItemBQC.DRAW_DRAW;// 平平
			else if (halfHomeScore.equals(halfGuestScore) && homeScore < guestScore)
				return OptionItemBQC.DRAW_LOSE;// 平负
			else if (halfHomeScore < halfGuestScore && homeScore > guestScore)
				return OptionItemBQC.LOSE_WIN;// 负胜
			else if (halfHomeScore < halfGuestScore && homeScore.equals(guestScore))
				return OptionItemBQC.LOSE_DRAW;// 负平
			else if (halfHomeScore < halfGuestScore && homeScore < guestScore)
				return OptionItemBQC.LOSE_LOSE;// 负负
		}
		default:
			throw new RuntimeException("玩法传递错误");
		}
	}
	
	public static OptionItem getResultOptionItem(String type, Map<String, String> resultMap) {// 只是用于单关，过关不需要
		String tempHomeScore = resultMap.get(homeScoreKey);
		String tempGuestScore = resultMap.get(guestScoreKey);
		String temphandicap = resultMap.get(handicapKey);
		String tempbigOrSmallbase = resultMap.get(bigSmallKey);
		Integer homeScore = Integer.valueOf(tempHomeScore);// 主队分数:为0表示赛事取消
		Integer guestScore = Integer.valueOf(tempGuestScore);// 客队分数:为0表示赛事取消
		Double handicap = Double.valueOf(temphandicap);// 让分
		Double bigOrSamllBase = Double.valueOf(tempbigOrSmallbase);// 大小分基数
		switch (Integer.valueOf(type)) {
		case 7301: {
			if (homeScore > guestScore)
				return OptionItemSF.WIN;// 主胜
			else
				return OptionItemSF.LOSE;// 主负
		}
		case 7302: {
			if (homeScore + handicap.doubleValue() > guestScore)
				return OptionItemRFSF.WIN;// 主胜
			else
				return OptionItemRFSF.LOSE;// 主负
		}
		case 7303: {
			if (1 <= (homeScore - guestScore) && (homeScore - guestScore) <= 5)
				return OptionItemSFC.HW1TO5;
			else if (6 <= (homeScore - guestScore) && (homeScore - guestScore) <= 10)
				return OptionItemSFC.HW6TO10;
			else if (11 <= (homeScore - guestScore) && (homeScore - guestScore) <= 15)
				return OptionItemSFC.HW11TO15;
			else if (16 <= (homeScore - guestScore) && (homeScore - guestScore) <= 20)
				return OptionItemSFC.HW16TO20;
			else if (21 <= (homeScore - guestScore) && (homeScore - guestScore) <= 25)
				return OptionItemSFC.HW21TO25;
			else if (26 <= (homeScore - guestScore))
				return OptionItemSFC.HW26;
			else if (1 <= (guestScore - homeScore) && (guestScore - homeScore) <= 5)
				return OptionItemSFC.GW1TO5;
			else if (6 <= (guestScore - homeScore) && (guestScore - homeScore) <= 10)
				return OptionItemSFC.GW6TO10;
			else if (11 <= (guestScore - homeScore) && (guestScore - homeScore) <= 15)
				return OptionItemSFC.GW11TO15;
			else if (16 <= (guestScore - homeScore) && (guestScore - homeScore) <= 20)
				return OptionItemSFC.GW16TO20;
			else if (21 <= (guestScore - homeScore) && (guestScore - homeScore) <= 25)
				return OptionItemSFC.GW21TO25;
			else if (26 <= (guestScore - homeScore))
				return OptionItemSFC.GW26;
		}
		case 7304: {
			if (homeScore + guestScore > bigOrSamllBase.doubleValue())
				return OptionItemDXF.BIG;// 主胜
			else
				return OptionItemDXF.SMALL;// 主负
		}
		default:
			throw new RuntimeException("玩法传递错误");
		}
	}
	
	

	public static void main(String args[]) {
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(homeScoreKey, "67");
		resultMap.put(guestScoreKey, "78");
		resultMap.put(handicapKey, "0");
		resultMap.put(bigSmallKey, "0");
		OptionItem item =null; //CommonUtils.getResultOptionItem("32", resultMap);
		System.out.println(item.getText());
	}

	/***************************************************************************
	 * JSON拿到博众那边的奖金值
	 * 
	 * @param json
	 * @return
	 */
//	public static BoZhongBackBean getBoZhongBackBeanByJson(String jsonStr) {
//		JSONObject jsonObject = JSONObject.fromObject(jsonStr);
//		BoZhongBackBean bean = new BoZhongBackBean();
//		String jsonTypeValue = jsonObject.getString("JsonType");
//		bean.setJsonType(jsonTypeValue);
//		JSONArray list = jsonObject.getJSONArray("DataList");
//		List<BackAwardModel> modelList = new ArrayList<BackAwardModel>();
//		for (int i = 0; i < list.size(); i++) {
//			JSONObject tempObj = list.getJSONObject(i);
//			BackAwardModel tempModel = new BackAwardModel();
//			String Transcode = tempObj.getString("Transcode");
//			String Mac = tempObj.getString("Mac");
//			String Merchant_id = tempObj.getString("Merchant_id");
//			String Printstatus = tempObj.getString("Printstatus");
//			tempModel.setTranscode(Transcode);
//			tempModel.setMerchant_id(Merchant_id);
//			tempModel.setPrintstatus(Printstatus);
//			tempModel.setMac(Mac);
//			JSONObject oddsJsonObject = tempObj.getJSONObject("Odds");
//			tempModel.setOddstr(oddsJsonObject.toString());
//			OddsModel omd = (OddsModel) JSONObject.toBean(oddsJsonObject, OddsModel.class);
//			Map<String, Map<String, String>> betMap = (Map<String, Map<String, String>>) oddsJsonObject.get("bet");
//			;
//			for (String key : betMap.keySet()) {
//				JSONObject mapJson = JSONObject.fromObject(betMap.get(key));
//				Map<String, String> optionMap = (Map<String, String>) JSONObject.toBean(mapJson, Map.class);
//				betMap.put(key, optionMap);
//			}
//			omd.setBet(betMap);
//			tempModel.setOdds(omd);
//			modelList.add(tempModel);
//		}
//		bean.setDataList(modelList);
//		return bean;
//	}

	public static Map<String, String> getMyselfMapfromBoZhong(Map<String, String> bozhongMap) {
		Map<String, String> mySelfMap = new HashMap<String, String>();
		for (String key : bozhongMap.keySet()) {
			if (key.indexOf("(") != -1) {
				String tempkey = key.substring(0, key.indexOf("("));
				String rf = key.substring(key.indexOf("("), key.indexOf(")"));
				mySelfMap.put(tempkey, bozhongMap.get(key) + rf);
			} else {
				mySelfMap.put(key, bozhongMap.get(key));
			}
		}
		return mySelfMap;
	}

	
	public static Map<String, String> getZQResultInfo(FtMatch match) {
		Map<String, String> resultInfoMap = new HashMap<String, String>();
//		resultInfoMap.put(homeScoreKey, match.getHomeScore() + "");
//		resultInfoMap.put(guestScoreKey, match.getGuestScore() + "");
//		resultInfoMap.put(resultHalfHomeScore, match.getHalfHomeScore() + "");
//		resultInfoMap.put(resultHalfGuestScore, match.getHalfGuestScore() + "");
//		resultInfoMap.put(CONCEDEKEY, match.getConcede() + "");
		return resultInfoMap;
	}

}
