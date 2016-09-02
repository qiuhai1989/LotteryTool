package com.yuncai.core.tools;

import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlayType;

public class BallCodeConverter {
	
	public static String convert(int lt, int pt, String code) {
		String label = code;
		
		// 江西时时彩
		if (lt == LotteryType.JXSSC.getValue()) {
//			if (pt == PlayType.JXSSC_DSDXDS.getValue() || pt == PlayType.JXSSC_DSDXFS.getValue()) {
//				label = code.replaceAll("2", "大").replaceAll("1", "小").replaceAll("5", "单").replaceAll("4", "双");
//			}
		}
		// 重庆时时彩
		if (lt == LotteryType.CQSSC.getValue()) {
			if (pt == PlayType.DSDXDS.getValue() || pt == PlayType.DSDXFS.getValue()) {
				label = code.replaceAll("2", "大").replaceAll("1", "小").replaceAll("5", "单").replaceAll("4", "双");
			}
		}
		

		return label;
	}


}
