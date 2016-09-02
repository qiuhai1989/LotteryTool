package com.yuncai;

public class Config {
	public static final String LOG_PROPERTIES_PATH="E://workSpace//webWork//LotteryTool//src//resources//log4j//log4j.properties";

	
	
	
	
	//----------------------------------------数据抓取来源url-------------------------------------------------
	//竞彩官网足球计算器数据地址(旧版，已过时)
	public static final String FT_CALC_FIXED_URL="http://info.sporttery.cn/interface/interface_wms.php?action=wf_list&pkey=051749a7b47e012a2ec33ac11955801b&2019717458";
	//竞彩官网足球结果数据地址
	public static final String FT_RESULT_URL="http://info.sporttery.cn/football/match_result.php";//可带参数，如:?start_date=2013-05-15&end_date=2013-05-15
	//竞彩官网足球结果和开奖明细地址
	public static final String FT_POOL_RESULT_URL="http://info.sporttery.cn/football/pool_result.php?id=";//id为mid
	//竞彩官网足球单关让球胜平负地址
	public static final String FT_DAN_GUAN_R_SPF_URL="http://info.sporttery.cn/football/hhad_vp.php";
	//竞彩官网足球单关不让胜平负地址
	public static final String FT_DAN_GUAN_NR_SPF_URL="http://info.sporttery.cn/football/had_vp.php";
	//竞彩官网足球单关总进球数地址
	public static final String FT_DAN_GUAN_JQS_URL="http://info.sporttery.cn/football/ttg_vp.php";
	//竞彩官网足球单关半全场胜平负地址
	public static final String FT_DAN_GUAN_BQC_URL="http://info.sporttery.cn/football/hafu_vp.php";
	
	
	//竞彩官网足球SP值数据地址(新版)
	//http://i.sporttery.cn/odds_calculator/get_odds?i_format=json&i_callback=getData&poolcode%5B%5D=hhad&poolcode%5B%5D=had&poolcode%5B%5D=crs&poolcode%5B%5D=hafu&poolcode%5B%5D=ttg&_=1370307432395
	public static final String FT_SP_DATA_URL="http://i.sporttery.cn/odds_calculator/get_odds?i_format=json&i_callback=getData&poolcode[]=hhad&poolcode[]=had&poolcode[]=crs&poolcode[]=hafu&poolcode[]=ttg&_=1370307432395";
	
	//500.com足球过关胜平负(不让球)地址
	public static final String FT_PASS_NR_SPF_RATE_500_URL="http://trade.500.com/jczq/index.php?playid=354&g=2";
	//500.com足球过关让球胜平负地址
	public static final String FT_PASS_SPF_RATE_500_URL="http://trade.500.com/jczq/index.php?playid=269&g=2";
	
	//310win.com足球过关胜平负地址
	public static final String FT_PASS_SPF_RATE_310_URL="http://www.310win.com/buy/jingcai.aspx?typeID=101&oddstype=2";
	
	//310win.com足球【亚赔】地址
	public static final String FT_YA_310_URL="http://www.310win.com/handicap/%s.html";
	
	//310win.com足球【大小球】地址
	public static final String FT_ODD_310_URL="http://www.310win.com/overunder/%s.html";
	
	//310win.com足球【欧赔】地址
	public static final String FT_OU_310_URL="http://1x2.nowscore.com/%s.js";
	
	//310win.com足球【分析】地址
	public static final String FT_XI_IMM_310_URL="http://www.310win.com/analysis/odds/%s.htm";//即时赔率比较、ＳＢ全赔率
	public static final String FT_XI_310_URL="http://www.310win.com/analysis/%s.htm";
	
	//500.com老足彩地址
	public static final String FT_OLD_14_SPF_500_URL="http://zc.trade.500.com/sfc/index.php";//14场胜平负
	public static final String FT_OLD_9_RCJC_500_URL="http://zc.trade.500.com/rcjc/?lotid=1&playid=43";//任选9场
	public static final String FT_OLD_4_JQ_500_URL="http://zc.trade.500.com/jq4/?lotid=17&playid=1";//进球4
	public static final String FT_OLD_6_BQ_500_URL="http://zc.trade.500.com/zc6/?lotid=15&playid=1";//6场半全
	//310win.com老足彩地址
	public static final String FT_OLD_14_SPF_310_URL="http://www.310win.com/buy/toto14.aspx";
	public static final String FT_OLD_9_RCJC_310_URL="http://www.310win.com/buy/toto9.aspx";
	public static final String FT_OLD_4_JQ_310_URL="http://www.310win.com/buy/toto4.aspx";
	public static final String FT_OLD_6_BQ_310_URL="http://www.310win.com/buy/toto6.aspx";
	
	
	
	//310win.com足球资料库(请勿修改url地址)
	public static final String FT_LEFT_DATA_310_URL="http://info.310win.com/jsData/leftData/leftData.js";//左边联盟名称等数据
	public static final String FT_INFO_HEAD_310_URL="http://info.310win.com/jsData/infoHeader.js";//国家icon js
	public static final String FT_COUNTRY_ICON_310_URL="http://info.310win.com/Image/info/images/"; //国家icon jpg
	public static final String FT_TEAM_JS_310_URL="http://info.310win.com/jsData/teamInfo/team%s.js?version=20130616";//联赛和球队icon js
	public static final String FT_TEAM_ICON_310_URL="http://info.310win.com/Image/"; //联赛和球队icon jpg 如果是球队加“team/”
	public static final String FT_DB_YEARS_310_URL="http://info.310win.com/jsData/LeagueSeason/sea%s.js"; //足球资料库赛事年份
	public static final String FT_DB_SCORE_310_URL="http://info.310win.com/jsData/matchResult/%s/%s.js?version=2013061800"; //赛程积分   param1:2012-2013 param2:s35_148 or c103 or s36
	public static final String FT_DB_RB_PL_310_URL="http://info.310win.com/jsData/letGoal/%s/l%s.js?version=20130625120619"; //足球资料库让球盘路榜
	public static final String FT_DB_RB_PL_DETAIL_310_URL="http://info.310win.com/cn/Team/HandicapDetail.aspx?sclassid=%s&teamid=%s&matchseason=%s&halfOrAll=%s"; //足球资料库让球盘路榜详细
	public static final String FT_DB_ODD_PL_310_URL="http://info.310win.com/jsData/bigSmall/%s/bs%s.js?version=20130625122408"; //足球资料库大小盘路榜
	public static final String FT_DB_ODD_PL_DETAIL_310_URL="http://info.310win.com/cn/Team/OuHandicap.aspx?sclassid=%s&teamid=%s&matchseason=%s&halfOrAll=%s"; //足球资料库大小球盘路榜详细
	public static final String FT_DB_SCORER_CUP_310_URL="http://info.310win.com/cn/ArcherCup/%s/%s.html"; //射手榜-杯赛地址
	public static final String FT_DB_SCORER_LEAGUE_310_URL="http://info.310win.com/cn/Archer.aspx?matchSeason=%s&sclassID=%s&lang=0&Round=%s"; //射手榜-联赛地址
	public static final String FT_DB_SUBLEAGUE_310_URL="http://info.310win.com/cn/SubLeague/%s.html"; //如果t_ft_league表里atype=0即为子联赛时还要来此抓取s37_90.js
	public static final String FT_DB_NORMAL_310_URL="http://info.310win.com/jsData/normal/%s/nor%s.js?version=20130621090631"; //足球资料库最常见结果
	public static final String FT_DB_SIN_DOU_310_URL="http://info.310win.com/jsData/SinDou/%s/sd%s.js?version=20130621125937"; //足球资料库入球总数及单双统计
	public static final String FT_DB_ALL_HALF_310_URL="http://info.310win.com/jsData/allHalf/%s/ah%s.js?version=20130621130508"; //足球资料库半全场胜负统计
	public static final String FT_DB_UD_INBALL_310_URL="http://info.310win.com/jsData/moreBall/%s/mb%s.js?version=20130621130508"; //足球资料库上下半场入球数较多
	public static final String FT_DB_FGL_310_URL="http://info.310win.com/jsData/firGetLose/%s/fgl%s.js?version=20130624152606"; //足球资料库最先入球/失球统计   最先入球/失球赛果
	public static final String FT_DB_TD_310_URL="http://info.310win.com/jsData/timeDistri/%s/td%s.js?version=20130624152819"; //足球资料库入球时间分布
	public static final String FT_DB_BD_310_URL="http://info.310win.com/jsData/boDanDistri/%s/bd%s.js?version=20130624152940"; //足球资料库波胆分布
	public static final String FT_DB_OW_310_URL="http://info.310win.com/jsData/onlyWin/%s/ow%s.js?version=20130624152606"; //足球资料库净胜/净负球数总计
	public static final String FT_DB_NGL_310_URL="http://info.310win.com/jsData/nogetlose/%s/ngl%s.js?version=20130624154352"; //足球资料库球队没入球没失球
	public static final String FT_DB_CT_310_URL="http://info.310win.com/jsData/contrast/%s/ct%s.js?version=20130624154605"; //足球资料库标准盘亚洲盘对照
	//球队资料
	public static final String FT_DB_TEAM_TDL_310_URL="http://info.310win.com/jsData/teamInfo/teamDetail/tdl%s.js?version=2013070310";//球队信息
	public static final String FT_DB_TEAM_MATCH_310_URL="http://info.310win.com//cn/team/TeamScheAjax.aspx?TeamID=%s&pageNo=%s";//球队赛事
	
	
	
	
	//-------------------------------------json数据文件存储路径------------------------------------------------
	public static String FT_MATCH_PATH="d://6636lottery//data//";
}
