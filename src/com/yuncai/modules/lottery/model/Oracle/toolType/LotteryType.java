package com.yuncai.modules.lottery.model.Oracle.toolType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yuncai.core.hibernate.IntegerBeanLabelItem;

@SuppressWarnings("unchecked")
public class LotteryType extends IntegerBeanLabelItem {
	protected LotteryType(String name, int value) {
		super(LotteryType.class.getName(), name, value);
	}
	
	public static LotteryType getItem(int value) {
		return (LotteryType) LotteryType.getResult(LotteryType.class.getName(), value);
	}
	public final static LotteryType NENO = new LotteryType("全部", -1);
	// public final static LotteryType ALL = new LotteryType("所有", 9999);
	// 体彩
	public final static LotteryType TCCJDLT = new LotteryType("大乐透", 39);
	public final static LotteryType QXC = new LotteryType("七星彩", 3);
	public final static LotteryType SZPL3 = new LotteryType("排列三", 63);
	public final static LotteryType SZPL5 = new LotteryType("排列五", 64);
	public final static LotteryType ZCSFC = new LotteryType("胜负彩", 74);
	public final static LotteryType ZCRJC = new LotteryType("任选9场", 75);
	public final static LotteryType JQC = new LotteryType("4场进球", 2);
	public final static LotteryType LCBQC=new LotteryType("6场半全",15);
	public final static LotteryType TC22X5=new LotteryType("22选5",9);
	public final static LotteryType NUM_COLOR = new LotteryType("数字彩", 11);


	// 30-33 竟彩篮球
	public final static LotteryType JC_LQ_SF = new LotteryType("竞彩篮球胜负", 7301);
	public final static LotteryType JC_LQ_RFSF = new LotteryType("竞彩篮球让分胜负", 7302);
	public final static LotteryType JC_LQ_SFC = new LotteryType("竞彩篮球胜分差", 7303);
	public final static LotteryType JC_LQ_DXF = new LotteryType("竞彩篮球大小分", 7304);
	public final static LotteryType JC_LQ_HT = new LotteryType("竞篮混投", 7305);
	public final static LotteryType JCLQ = new LotteryType("竞彩篮球", 73);

	// 34-37 竟彩足球
	public final static LotteryType JC_ZQ_SPF = new LotteryType("竞彩足球胜负", 7207);
	public final static LotteryType JC_ZQ_BF = new LotteryType("竞彩足球比分", 7202);
	public final static LotteryType JC_ZQ_JQS = new LotteryType("竞彩足球进球数", 7203);
	public final static LotteryType JC_ZQ_BQC = new LotteryType("竞彩半全场", 7204);
	public final static LotteryType JC_ZQ_HT = new LotteryType("竞足混投", 7206);
	public final static LotteryType JC_ZQ_RQSPF = new LotteryType("竞彩足球让球胜平负", 7201);
	public final static LotteryType JC_ZQ_LTS = new LotteryType("擂台赛", 7208);
	public final static LotteryType JCZQ = new LotteryType("竞彩足球", 72);
	
	
	// 体彩高频
	public final static LotteryType SYYDJ = new LotteryType("11运夺金", 62);
	public final static LotteryType GD11X5 = new LotteryType("广东11选5", 78);
	public final static LotteryType JX11X5 = new LotteryType("11选5", 70);
	public final static LotteryType SHSSL=new LotteryType("时时乐",29);
	
	// 福彩
	public final static LotteryType SSQ = new LotteryType("双色球", 5);
	public final static LotteryType QLC = new LotteryType("七乐彩", 13);
	public final static LotteryType FC3D = new LotteryType("3D", 6);
	public final static LotteryType HD15X5 = new LotteryType("15选5", 59);

	
	// 福彩高频
	public final static LotteryType CQSSC = new LotteryType("重庆时时彩", 28);
	public final static LotteryType JXSSC = new LotteryType("江西时时彩", 61);
	
	
	//世界杯冠军玩法
	public final static LotteryType JCSJBGJ = new LotteryType("世界杯冠军", 8001);
	
	public static Map xpMap = new HashMap(); // 小盘玩法
	
	public static Map xianhaoMap = new HashMap(); // 会限号的彩种
	
	public static Map lotteryMap=new HashMap();
	public static List<LotteryType> list = new ArrayList<LotteryType>();
	public static List<LotteryType> JCZQList = new ArrayList<LotteryType>(); // 足球竞赛集合
	
	public static List<LotteryType> CTZQList = new ArrayList<LotteryType>(); // 传统足彩集合

	public static List<LotteryType> JCLQList = new ArrayList<LotteryType>(); // 竞彩篮球集合
	public static List<LotteryType> GPList = new ArrayList<LotteryType>(); // 高频集合
	
	public static List<LotteryType> JCSJBList = new ArrayList<LotteryType>();//冠军玩法
	
	static{
		xpMap.put(SZPL3.getValue(), SZPL3);
		xpMap.put(SZPL5.getValue(), SZPL5);
		xpMap.put(FC3D.getValue(), FC3D);
		
		//组装名称
		lotteryMap.put(TCCJDLT.getValue(), "TCCJDLT");
		lotteryMap.put(QXC.getValue(), "QXC");
		lotteryMap.put(SZPL3.getValue(), "SZPL3");
		lotteryMap.put(SZPL5.getValue(), "SZPL5");
		lotteryMap.put(ZCSFC.getValue(), "ZCSFC");
		lotteryMap.put(ZCRJC.getValue(), "ZCRJC");
		lotteryMap.put(JQC.getValue(), "JQC");
		lotteryMap.put(LCBQC.getValue(), "LCBQC");
		lotteryMap.put(TC22X5.getValue(), "TC22X5");
		lotteryMap.put(JCLQ.getValue(), "JCLQ");
		//竞彩足球
		lotteryMap.put(JC_ZQ_SPF.getValue(), "JCZQ");
		lotteryMap.put(JC_ZQ_BF.getValue(), "JCZQ");
		lotteryMap.put(JC_ZQ_BQC.getValue(), "JCZQ");
		lotteryMap.put(JC_ZQ_HT.getValue(), "JCZQ");
		lotteryMap.put(JC_ZQ_JQS.getValue(), "JCZQ");
		lotteryMap.put(JC_ZQ_RQSPF.getValue(), "JCZQ");
		lotteryMap.put(SYYDJ.getValue(), "SYYDJ");
		lotteryMap.put(GD11X5.getValue(), "GD11X5");
		lotteryMap.put(JX11X5.getValue(), "JX11X5");
		lotteryMap.put(SHSSL.getValue(), "SHSSL");
		lotteryMap.put(SSQ.getValue(), "SSQ");
		lotteryMap.put(QLC.getValue(), "QLC");
		lotteryMap.put(FC3D.getValue(), "FC3D");
		lotteryMap.put(CQSSC.getValue(), "CQSSC");
		lotteryMap.put(JXSSC.getValue(), "JXSSC");
		lotteryMap.put(JC_LQ_SF.getValue(), "JCLQ");
		lotteryMap.put(JC_LQ_RFSF.getValue(), "JCLQ");
		lotteryMap.put(JC_LQ_SFC.getValue(), "JCLQ");
		lotteryMap.put(JC_LQ_DXF.getValue(), "JCLQ");
		lotteryMap.put(JC_LQ_HT.getValue(), "JCLQ");
		//世界杯
		lotteryMap.put(JCSJBGJ.getValue(), "JCSJBGJ"); 
		
		CTZQList.add(LotteryType.ZCSFC);
		CTZQList.add(LotteryType.ZCRJC);
		CTZQList.add(LotteryType.JQC);
		CTZQList.add(LotteryType.LCBQC);
		
		
		JCZQList.add(LotteryType.JC_ZQ_SPF);
		JCZQList.add(LotteryType.JC_ZQ_BF);
		JCZQList.add(LotteryType.JC_ZQ_BQC);
		JCZQList.add(LotteryType.JC_ZQ_HT);
		JCZQList.add(LotteryType.JC_ZQ_JQS);
		JCZQList.add(LotteryType.JC_ZQ_RQSPF);
		
		JCLQList.add(LotteryType.JC_LQ_DXF);
		JCLQList.add(LotteryType.JC_LQ_RFSF);
		JCLQList.add(LotteryType.JC_LQ_SF);
		JCLQList.add(LotteryType.JC_LQ_SFC);
		JCLQList.add(LotteryType.JC_LQ_HT);

		JCSJBList.add(LotteryType.JCSJBGJ);
		
		
		list.add(NENO);
		// list.add(ALL);

		
		//世界杯
		list.add(JCSJBGJ);
		list.add(TCCJDLT);
		list.add(QXC);
		list.add(SZPL3);
		list.add(SZPL5);
		list.add(ZCSFC);
		list.add(ZCRJC);
		list.add(JQC);
		list.add(LCBQC);
		list.addAll(JCZQList);
		
		//高频集合
		GPList.add(SYYDJ);
		GPList.add(GD11X5);
		GPList.add(JX11X5);
		GPList.add(SHSSL);
		
		
		// 会限号的彩种，用来自动发送限号短信用的
		xianhaoMap.put(CQSSC.getValue(), CQSSC);
		xianhaoMap.put(JXSSC.getValue(), JXSSC);
		xianhaoMap.put(SYYDJ.getValue(), SYYDJ);
		xianhaoMap.put(GD11X5.getValue(), GD11X5);
		
		
		xianhaoMap.put(SZPL3.getValue(), SZPL3);
		xianhaoMap.put(FC3D.getValue(), FC3D);
		
		
	

	}
	
	public static void main(String[] args) {
		
		System.out.println(LotteryType.getItem(39).getName());
	}
	
	
	

}
