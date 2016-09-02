package com.yuncai.core.common;

import java.util.HashMap;
import java.util.Map;

import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;

@SuppressWarnings("unchecked")
public class PrizeListDef {
	public static HashMap<Integer, Map<String, String>> prizeHashMap;

	// public static HashMap<Integer,List<String>> prizeNameHashMap;

	static {
		prizeHashMap = new HashMap<Integer, Map<String, String>>();
		// prizeNameHashMap = new HashMap<Integer,List<String>>();

		Map ssqMap = new HashMap<String, String>();
		ssqMap.put("prize1", "一等奖");
		ssqMap.put("prize2", "二等奖");
		ssqMap.put("prize3", "三等奖");
		ssqMap.put("prize4", "四等奖");
		ssqMap.put("prize5", "五等奖");
		ssqMap.put("prize6", "六等奖");
		ssqMap.put("prize7", "鼓励奖");
		ssqMap.put("prize8", "中3红0蓝奖");
		ssqMap.put("prize9", "中8+1复式奖");

		prizeHashMap.put(LotteryType.SSQ.getValue(), ssqMap);

		Map pl5Map = new HashMap<String, String>();
		pl5Map.put("prize1", "一等奖");// 排五直选奖金
		prizeHashMap.put(LotteryType.SZPL5.getValue(), pl5Map);

		Map pl3Map = new HashMap<String, String>();
		pl3Map.put("prize1", "直选");// 排三直选奖金
		pl3Map.put("prize2", "组选3");// 组选3奖金
		pl3Map.put("prize3", "组选6");// 组选6奖金
		prizeHashMap.put(LotteryType.SZPL3.getValue(), pl3Map);

		Map fc3dMap = new HashMap<String, String>();
		fc3dMap.put("prize1", "直选");// 3D直选奖金
		fc3dMap.put("prize2", "组选3");// 3D组选3奖金
		fc3dMap.put("prize3", "组选6");// 3D组选6奖金
		prizeHashMap.put(LotteryType.FC3D.getValue(), fc3dMap);

		Map sscMap = new HashMap<String, String>();
		sscMap.put("prize1", "五星奖");
		sscMap.put("prize2", "三星奖");
		sscMap.put("prize3", "二星奖");
		sscMap.put("prize4", "一星奖");
		sscMap.put("prize5", "大小单双奖");
		sscMap.put("prize6", "二星组选奖");
		sscMap.put("prize7", "五星通选一等奖");
		sscMap.put("prize8", "五星通选二等奖");
		sscMap.put("prize9", "五星通选三等奖");
		sscMap.put("prize10", "组三奖");
		sscMap.put("prize11", "组六奖");
		prizeHashMap.put(LotteryType.CQSSC.getValue(), sscMap);

		Map jxsscMap = new HashMap<String, String>();
		jxsscMap.put("prize1", "五星奖");
		jxsscMap.put("prize2", "三星奖");
		jxsscMap.put("prize3", "二星奖");
		jxsscMap.put("prize4", "一星奖");
		jxsscMap.put("prize5", "大小单双奖");
		jxsscMap.put("prize6", "二星组选奖");
		jxsscMap.put("prize7", "五星通选一等奖");
		jxsscMap.put("prize8", "五星通选二等奖");
		jxsscMap.put("prize9", "五星通选三等奖");
		jxsscMap.put("prize10", "组三奖");
		jxsscMap.put("prize11", "组六奖");
		jxsscMap.put("prize12", "四星奖");
		jxsscMap.put("prize13", "四星二等奖");

		prizeHashMap.put(LotteryType.JXSSC.getValue(), jxsscMap);

		Map qlcMap = new HashMap<String, String>();
		qlcMap.put("prize1", "一等奖");
		qlcMap.put("prize2", "二等奖");
		qlcMap.put("prize3", "三等奖");
		qlcMap.put("prize4", "四等奖");
		qlcMap.put("prize5", "五等奖");
		qlcMap.put("prize6", "六等奖");
		qlcMap.put("prize7", "七等奖");
		qlcMap.put("prize8", "特别奖");
		prizeHashMap.put(LotteryType.QLC.getValue(), qlcMap);

		

		Map dltMap = new HashMap<String, String>();
		dltMap.put("prize1", "一等奖");
		dltMap.put("prize2", "一等奖追加");
		dltMap.put("prize3", "二等奖");
		dltMap.put("prize4", "二等奖追加");
		dltMap.put("prize5", "三等奖");
		dltMap.put("prize6", "三等奖追加");
		dltMap.put("prize7", "四等奖");
		dltMap.put("prize8", "五等奖");
		dltMap.put("prize9", "六等奖");
		dltMap.put("prize10", "七等奖");
		dltMap.put("prize11", "八等奖");
		dltMap.put("prize12", "幸运奖");
		dltMap.put("prize13", "四等奖追加");
		dltMap.put("prize14", "五等奖追加");
		dltMap.put("prize15", "六等奖追加");
		dltMap.put("prize16", "七等奖追加");
		
		dltMap.put("prize17", "宝石一等奖");
		dltMap.put("prize18", "宝石二等奖");
		dltMap.put("prize19", "宝石三等奖");
		dltMap.put("prize20", "宝石四等奖");
		
		dltMap.put("prize21", "钻石一等奖");
		dltMap.put("prize22", "钻石二等奖");
		dltMap.put("prize23", "钻石三等奖");
		dltMap.put("prize24", "钻石四等奖");
		
		prizeHashMap.put(LotteryType.TCCJDLT.getValue(), dltMap);
		
		Map qxcMap = new HashMap<String, String>();
		qxcMap.put("prize1", "一等奖");
		qxcMap.put("prize2", "二等奖");
		qxcMap.put("prize3", "三等奖");
		qxcMap.put("prize4", "四等奖");
		qxcMap.put("prize5", "五等奖");
		qxcMap.put("prize6", "六等奖");
		prizeHashMap.put(LotteryType.QXC.getValue(), qxcMap);


		Map sslMap = new HashMap<String, String>();
		sslMap.put("prize1", "直选奖金");
		sslMap.put("prize2", "组选3奖金");
		sslMap.put("prize3", "组选6奖金");
		sslMap.put("prize4", "前二奖金");
		sslMap.put("prize5", "后二奖金");
		sslMap.put("prize6", "前一奖金");
		sslMap.put("prize7", "后一奖金");
		prizeHashMap.put(LotteryType.SHSSL.getValue(), sslMap);

		Map sfcMap = new HashMap<String, String>();
		sfcMap.put("prize1", "一等奖");
		sfcMap.put("prize2", "二等奖");
		sfcMap.put("prize3", "任选九场一等奖");

		prizeHashMap.put(LotteryType.ZCSFC.getValue(), sfcMap);

		Map sfcr9Map = new HashMap<String, String>();
		sfcr9Map.put("prize1", "一等奖");
		prizeHashMap.put(LotteryType.ZCRJC.getValue(), sfcr9Map);

		Map bqcMap = new HashMap<String, String>();
		bqcMap.put("prize1", "一等奖");
		prizeHashMap.put(LotteryType.LCBQC.getValue(), bqcMap);

		/*
		 * Map t22x5Map = new HashMap<String,String>(); t22x5Map.put("prize1",
		 * "一等奖"); t22x5Map.put("prize2", "二等奖"); t22x5Map.put("prize3", "三等奖");
		 * prizeHashMap.put(LotteryType.T22X5.getValue(), t22x5Map);
		 */

		Map jqcMap = new HashMap<String, String>();
		jqcMap.put("prize1", "一等奖");
		prizeHashMap.put(LotteryType.JQC.getValue(), jqcMap);


		Map sd11x5Map = new HashMap<String, String>();
		sd11x5Map.put("prize1", "任选一奖");
		sd11x5Map.put("prize2", "任选二奖");
		sd11x5Map.put("prize3", "任选三奖");
		sd11x5Map.put("prize4", "任选四奖");
		sd11x5Map.put("prize5", "任选五奖");
		sd11x5Map.put("prize6", "任选六奖");
		sd11x5Map.put("prize7", "任选七奖");
		sd11x5Map.put("prize8", "任选八奖");
		sd11x5Map.put("prize9", "前二直选奖");
		sd11x5Map.put("prize10", "前二组选奖");
		sd11x5Map.put("prize11", "前三直选奖");
		sd11x5Map.put("prize12", "前三组选奖");
		prizeHashMap.put(LotteryType.SYYDJ.getValue(), sd11x5Map);
		prizeHashMap.put(LotteryType.GD11X5.getValue(), sd11x5Map);
	
		Map JCMap = new HashMap<String, String>();
		JCMap.put("prize0", "自由过关");
		JCMap.put("prize1", "单关");
		JCMap.put("prize2", "2串1");
		JCMap.put("prize3", "3串1");
		JCMap.put("prize4", "4串1");
		JCMap.put("prize5", "5串1");
		JCMap.put("prize6", "6串1");
		JCMap.put("prize7", "7串1");
		JCMap.put("prize8", "8串1");
		prizeHashMap.put(LotteryType.JC_LQ_DXF.getValue(), JCMap);
		prizeHashMap.put(LotteryType.JC_LQ_RFSF.getValue(), JCMap);
		prizeHashMap.put(LotteryType.JC_LQ_SF.getValue(), JCMap);
		prizeHashMap.put(LotteryType.JC_LQ_SFC.getValue(), JCMap);
		prizeHashMap.put(LotteryType.JC_ZQ_BF.getValue(), JCMap);
		prizeHashMap.put(LotteryType.JC_ZQ_BQC.getValue(), JCMap);
		prizeHashMap.put(LotteryType.JC_ZQ_JQS.getValue(), JCMap);
		prizeHashMap.put(LotteryType.JC_ZQ_SPF.getValue(), JCMap);
		prizeHashMap.put(LotteryType.JC_ZQ_HT.getValue(), JCMap);
		prizeHashMap.put(LotteryType.JC_ZQ_RQSPF.getValue(), JCMap);
	}

}
