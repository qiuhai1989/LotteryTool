package com.yuncai.modules.lottery.model.Oracle.toolType;


import java.util.*;

import com.yuncai.core.hibernate.IntegerBeanLabelItem;
/**
 * 这个只是一个大概的东西。。不全还没有跟晓风软件对接
 */
public class PlayType extends IntegerBeanLabelItem {
	
	protected PlayType(String name, int value) {
		super(PlayType.class.getName(), name, value);
	}

	public static PlayType getItem(int value) {
		return (PlayType) PlayType.getResult(PlayType.class.getName(), value);
	}

	public static final PlayType ALL = new PlayType("全部", -1);
	public static final PlayType HT = new PlayType("混投", 0);
	public static final PlayType DS = new PlayType("单式", 1);
	public static final PlayType FS = new PlayType("标准", 2);
	public static final PlayType DT = new PlayType("胆拖", 3);

	// start cqssc
	public static final PlayType WXDS = new PlayType("五星单式", 4);
	public static final PlayType WXFS = new PlayType("五星复式", 5);

	public static final PlayType SXDS = new PlayType("三星单式", 6);
	public static final PlayType SXFS = new PlayType("三星复式", 7);

	public static final PlayType LXDS = new PlayType("两星单式", 8);
	public static final PlayType LXFS = new PlayType("两星复式", 9);

	public static final PlayType YXDS = new PlayType("一星单式", 10);
	public static final PlayType YXFS = new PlayType("一星复式", 11);

	public static final PlayType Z3DS = new PlayType("组三单式", 12);
	public static final PlayType Z3BH = new PlayType("组三包号", 13);
	public static final PlayType Z3HZ = new PlayType("组三和值", 14);
	public static final PlayType Z6DS = new PlayType("组六单式", 15);
	public static final PlayType Z6BH = new PlayType("组六包号", 16);
	public static final PlayType Z6HZ = new PlayType("组六和值", 17);
	public static final PlayType Z6DT = new PlayType("组六胆拖", 18);

	public static final PlayType EXHZ = new PlayType("二星和值", 19);
	public static final PlayType EXBHDS = new PlayType("二星包号单式", 20);
	public static final PlayType EXBHFS = new PlayType("二星包号复式", 21);
	public static final PlayType SXZHUXHZ = new PlayType("三星组选和值", 22);

	public static final PlayType WXTXDS = new PlayType("五星通选单式", 23);
	public static final PlayType WXTXFS = new PlayType("五星通选复式", 24);
	public static final PlayType DSDXDS = new PlayType("大小单双单式", 25);
	public static final PlayType DSDXFS = new PlayType("大小单双复式", 26);
	public static final PlayType EXBHHZ = new PlayType("二星组选和值", 27);
	
	public static final PlayType EXZHDT = new PlayType("二星组选胆拖", 15028);
	public static final PlayType SXZ6DT = new PlayType("三星组六胆拖", 15029);
	
	public static final PlayType SXZUX = new PlayType("三星组选", 15030);
	public static final PlayType LXZUX = new PlayType("二星组选", 15031);
	
	public static final PlayType SXZHIXHZ = new PlayType("三星直选和值", 15037);
	public static final PlayType SXZHIXDT = new PlayType("三星直选胆拖", 15038);
	public static final PlayType SXZHIXKD = new PlayType("三星直选跨度", 15039);
	// */ //end cqssc

	

	public static final PlayType ZX = new PlayType("直选", 28);
	public static final PlayType ZS = new PlayType("组三包号", 29);
	public static final PlayType ZSBZ = new PlayType("组三标准", 29001);
	public static final PlayType ZL = new PlayType("组六包号", 30);
	public static final PlayType ZXHZ = new PlayType("直选和值", 31);
	public static final PlayType ZSHZ = new PlayType("组三和值", 32);
	public static final PlayType ZLHZ = new PlayType("组六和值", 33);
	public static final PlayType QY = new PlayType("前一", 34);
	public static final PlayType HY = new PlayType("后一", 35);
	public static final PlayType QE = new PlayType("前二", 36);
	public static final PlayType HE = new PlayType("后二", 37);
	public static final PlayType ZLDT = new PlayType("组六胆拖", 38);
	public static final PlayType ZXDT = new PlayType("直选胆拖", 29002);
	public static final PlayType ZXKD = new PlayType("直选跨度", 29003);
	public static final PlayType ZXBH = new PlayType("直选包号", 29004);
	public static final PlayType ZLBZ = new PlayType("组六标准", 29005);
	
	//票才有
	public static final PlayType ZXFS = new PlayType("直选复式", 39);
	public static final PlayType ZHUXUAN = new PlayType("组选", 40);
	
	public static final PlayType ZHIXUANDS = new PlayType("直选单式上传", 29006);
	public static final PlayType ZUXUANDS = new PlayType("组选单式上传", 29007);
	
	public static final PlayType ZYGG = new PlayType("自由过关", 100);
	public static final PlayType DC_1 = new PlayType("单关", 101);
	public static final PlayType DC_2_1 = new PlayType("2串1", 102);
	public static final PlayType DC_3_1 = new PlayType("3串1", 103);
	public static final PlayType DC_4_1 = new PlayType("4串1", 104);
	public static final PlayType DC_5_1 = new PlayType("5串1", 105);
	public static final PlayType DC_6_1 = new PlayType("6串1", 106);
	public static final PlayType DC_7_1 = new PlayType("7串1", 107);
	public static final PlayType DC_8_1 = new PlayType("8串1", 108);
	public static final PlayType DC_9_1 = new PlayType("9串1", 109);
	public static final PlayType DC_10_1 = new PlayType("10串1", 110);

	public static final PlayType DC_11_1 = new PlayType("11串1", 111);
	public static final PlayType DC_12_1 = new PlayType("12串1", 112);
	public static final PlayType DC_13_1 = new PlayType("13串1", 113);
	public static final PlayType DC_14_1 = new PlayType("14串1", 114);
	public static final PlayType DC_15_1 = new PlayType("15串1", 115);

	public static final PlayType JC_ZYGG = new PlayType("自由过关", 116);
	public static final PlayType JC_1 = new PlayType("单关", 117);
	public static final PlayType JC_2_1 = new PlayType("2串1", 118);
	public static final PlayType JC_3_1 = new PlayType("3串1", 119);
	public static final PlayType JC_4_1 = new PlayType("4串1", 120);
	public static final PlayType JC_5_1 = new PlayType("5串1", 121);
	public static final PlayType JC_6_1 = new PlayType("6串1", 122);
	public static final PlayType JC_7_1 = new PlayType("7串1", 123);
	public static final PlayType JC_8_1 = new PlayType("8串1", 124);
	
	//竞彩
	public static final PlayType JCSPF = new PlayType("胜平负",7207);
	public static final PlayType JCBF = new PlayType("比分",7202);
	public static final PlayType JCJQS = new PlayType("总进球",7203);
	public static final PlayType JCBQC = new PlayType("半全场",7204);
	public static final PlayType JCHTGG = new PlayType("混投过关",7206);
	public static final PlayType JCRQSFP = new PlayType("让球胜平负",7201);
	
	//竞彩篮球
	public static final PlayType LQSF = new PlayType("胜负",7301);
	public static final PlayType LQRFSF = new PlayType("让分胜负",7302);
	public static final PlayType LQSFC = new PlayType("胜分差",7303);
	public static final PlayType LQDXF = new PlayType("大小分",7304);
	public static final PlayType LQHT = new PlayType("混投过关",7305);

	public static final PlayType R1_BZ = new PlayType("任选1标准", 50);
	public static final PlayType R2_BZ = new PlayType("任选2标准", 51);
	public static final PlayType R3_BZ = new PlayType("任选3标准", 52);
	public static final PlayType R4_BZ = new PlayType("任选4标准", 53);
	public static final PlayType R5_BZ = new PlayType("任选5标准", 54);
	public static final PlayType R6_BZ = new PlayType("任选6标准", 55);
	public static final PlayType R7_BZ = new PlayType("任选7标准", 56);
	public static final PlayType R8_BZ = new PlayType("任选8标准", 57);
	public static final PlayType R2_DT = new PlayType("任选2胆拖", 5022);
	public static final PlayType R3_DT = new PlayType("任选3胆拖", 5023);
	public static final PlayType R4_DT = new PlayType("任选4胆拖", 5024);
	public static final PlayType R5_DT = new PlayType("任选5胆拖", 5025);
	public static final PlayType R6_DT = new PlayType("任选6胆拖", 5026);
	public static final PlayType R7_DT = new PlayType("任选7胆拖", 5027);
	public static final PlayType R8_DT = new PlayType("任选8胆拖", 5028);
	public static final PlayType QE_ZHUX_DT = new PlayType("前二组选胆拖", 5029);
	public static final PlayType QS_ZHUX_DT = new PlayType("前三组选胆拖", 5030);
	public static final PlayType QE_ZX = new PlayType("前二直选标准", 58);
	public static final PlayType QE_ZHUX = new PlayType("前二组选标准", 59);
	public static final PlayType QS_ZX = new PlayType("前三直选标准", 60);
	public static final PlayType QS_ZHUX = new PlayType("前三组选标准", 73);

	// 票才有
	public static final PlayType R1_FS = new PlayType("任选1复式", 61);
	public static final PlayType R2_FS = new PlayType("任选2复式", 62);
	public static final PlayType R3_FS = new PlayType("任选3复式", 63);
	public static final PlayType R4_FS = new PlayType("任选4复式", 64);
	public static final PlayType R5_FS = new PlayType("任选5复式", 65);
	public static final PlayType R6_FS = new PlayType("任选6复式", 66);
	public static final PlayType R7_FS = new PlayType("任选7复式", 67);
	public static final PlayType R8_FS = new PlayType("任选8复式", 68);
	public static final PlayType QE_ZHUX_FS = new PlayType("前二组选复式", 69);
	public static final PlayType QS_ZHUX_FS = new PlayType("前三组选复式", 70);
	public static final PlayType QE_ZX_FS = new PlayType("前二直选复式", 71);
	public static final PlayType QS_ZX_FS = new PlayType("前三直选复式", 72);
	

	public static final PlayType SXL_DS = new PlayType("生肖乐单式", 45);
	public static final PlayType SXL_FS = new PlayType("生肖乐复式", 46);
	
	public static final PlayType JCSJBGJ = new PlayType("冠军",8001);
	
	//转换字符
	public static Map<Integer,PlayType> changeMap = new HashMap<Integer,PlayType>(); //转换
	static{
		//大乐透
		changeMap.put(3901, FS);
		changeMap.put(3902, FS);
		changeMap.put(3903, DT);
		changeMap.put(3904, DT);
		
		//七星彩
		changeMap.put(301, FS);
		
		//排列三
		changeMap.put(6301, ZXFS);
		changeMap.put(6302, ZX);
		changeMap.put(6303, ZL);
		changeMap.put(6304, ZS);
		changeMap.put(6305, ZXHZ);
		changeMap.put(6306, ZXHZ);
		changeMap.put(6307, ZSHZ);
		changeMap.put(6308, ZLHZ);
		
		//排列五
		changeMap.put(6401, FS);
		
		//老足彩
		changeMap.put(7401, FS);
		changeMap.put(7501, FS);
		changeMap.put(201, FS);
		changeMap.put(1501, FS);
		
		//竟彩足球
		changeMap.put(7201, JCRQSFP);
		changeMap.put(7202, JCBF);
		changeMap.put(7203, JCJQS);
		changeMap.put(7204, JCBQC);
		changeMap.put(7206, JCHTGG);
		changeMap.put(7207, JCSPF);
		
		//竞彩篮球
		changeMap.put(7301, LQSF);
		changeMap.put(7302, LQRFSF);
		changeMap.put(7303, LQSFC);
		changeMap.put(7304, LQDXF);
		changeMap.put(7305, LQHT);
		
		//七乐彩 
		changeMap.put(1301, FS);
		changeMap.put(1302, FS);
		changeMap.put(1303, DT);	

		//广东11选5
		changeMap.put(7801, R1_BZ);
		changeMap.put(7802, R2_BZ);
		changeMap.put(7803, R3_BZ);
		changeMap.put(7804, R4_BZ);
		changeMap.put(7805, R5_BZ);
		changeMap.put(7806, R6_BZ);
		changeMap.put(7807, R7_BZ);
		changeMap.put(7808, R8_BZ);
		changeMap.put(7809, QE_ZX);
		changeMap.put(7810, QS_ZX);
		changeMap.put(7811, QE_ZHUX);
		changeMap.put(7812, QS_ZHUX);
		changeMap.put(7813, QE_ZHUX_DT);
		changeMap.put(7814, QS_ZHUX_DT);
		changeMap.put(7815, R2_DT);
		changeMap.put(7816, R3_DT);
		changeMap.put(7817, R4_DT);
		changeMap.put(7818, R5_DT);
		changeMap.put(7819, R6_DT);
		changeMap.put(7820, R7_DT);
		changeMap.put(7821, R8_DT);

		//十一运夺金
		changeMap.put(6201, R1_BZ);
		changeMap.put(6202, R2_BZ);
		changeMap.put(6203, R3_BZ);
		changeMap.put(6204, R4_BZ);
		changeMap.put(6205, R5_BZ);
		changeMap.put(6206, R6_BZ);
		changeMap.put(6207, R7_BZ);
		changeMap.put(6208, R8_BZ);
		changeMap.put(6209, QE_ZX);
		changeMap.put(6210, QS_ZX);
		changeMap.put(6211, QE_ZHUX);
		changeMap.put(6212, QS_ZHUX);
		changeMap.put(6213, QE_ZHUX_DT);
		changeMap.put(6214, QS_ZHUX_DT);
		changeMap.put(6215, R2_DT);
		changeMap.put(6216, R3_DT);
		changeMap.put(6217, R4_DT);
		changeMap.put(6218, R5_DT);
		changeMap.put(6219, R6_DT);
		changeMap.put(6220, R7_DT);
		changeMap.put(6221, R8_DT);
		
		
		//福彩3D
		changeMap.put(601, ZXFS);
		changeMap.put(602, ZX);
		changeMap.put(603, ZL);
		changeMap.put(604, ZS);
		changeMap.put(605, ZXHZ);
		changeMap.put(606, ZXHZ);
		changeMap.put(607, ZSHZ);
		changeMap.put(608, ZLHZ);

		//双色球
		changeMap.put(501, FS);
		changeMap.put(502, FS);
		changeMap.put(503, DT);

		changeMap.put(8001, JCSJBGJ);
		
		
	}
	
	
	public static void main(String[] args) {
		PlayType plType=PlayType.changeMap.get(3901); //PlayType.getItem(playType);
		System.out.println("fff");
	}
	

}
