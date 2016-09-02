package com.yuncai.modules.lottery.service.oracleService.lottery;
import com.yuncai.core.longExce.ServiceException;
import com.yuncai.modules.lottery.model.Oracle.LotteryPlan;
import com.yuncai.modules.lottery.model.Oracle.Member;
import com.yuncai.modules.lottery.model.Oracle.toolType.GenType;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Oracle.toolType.PublicStatus;
import com.yuncai.modules.lottery.model.Sql.Isuses;
import com.yuncai.modules.lottery.model.Sql.Sites;
import com.yuncai.modules.lottery.model.Sql.Users;
import com.yuncai.modules.lottery.model.Sql.vo.ChaseTasksBean;

import java.util.*;

public interface TradeService {
	
	// 预投补单接口
	public LotteryPlan ytbd(String planNo, String content, String filePath, Double amount,Date dclasttime,PublicStatus publicStatusIn,String multiple,int playType) throws Exception;
	
	/**
	 * 代购，增加consumeOrFreezeToConsume参数
	 */
	public String buyLotteryPlanBySource(Member member, Long ct, String account, String term, int iLotteryType, String content, Integer amount,
			int selectType, String uploadFilePath, int fromPage, String ip, int walletType, int publicStatus, int followintType, GenType gentype,
			String addAttribute,int multiple,int playType,String sqlContext,String phoneNo,String sim,String model,String systemVersion,String channel,Integer easyType,String version)
			throws ServiceException, Exception;
	
	
	/**
	 * 功能： 针对用户发起合买处理接口 输入参数： memberId ---会员ID account ---会员帐号 term ---期次
	 * content ---投注号码内容 amount ---交易金额 perAmount ---每份金额 part ---份数 reservePart
	 * ---保底份数 soldPart ---已经认购份数 selectType ---投注方式 uploadFilePath ---上传方案路径
	 * multiple ---倍数 walletType ---支付类型 lotteryTyep ---彩票类型
	 * 
	 * 
	 * @throws Exception
	 */
	public abstract String buyLotteryPlanTogether(Member member, Long ct, String term, int iLotteryType, String content, Integer amount,
			Integer perAmount, int part, int reservePart, int soldPart, String uploadFilePath, int fromPage, String ip, int walletType,
			int founderPart, String planDesc, int selectType, int publicStatus, int comission,GenType gentype,String isPay,int followingType_
			,String addAttribute,int multiple,int playType,String sqlContext,String phoneNo,String sim,String model,String systemVersion,String channel,String title,String version) throws ServiceException, Exception;
	

	/**
	 * 功能： 针对用户自动跟单参与合买处理接口(增加跟单类型(followingType)字段) 输入参数： member ---会员 lotteryPlanNo ---方案编号 part ---金额
	 * part ---份数 fromPage ---来源页面 ip ---用户IP地址 walletType ---支付类型 followingType ---跟单类型 autoHm ---是否自动跟单 1是0否 输出参数： 作者：lm
	 * 日期： 2013-05-18
	 */
	public abstract String buyLotteryPlanJoin(Member member, Long ct, Integer lotteryPlanNo, int part, int fromPage, String ip, int walletType,int followingType,Integer sourceId,String autoHm,String phoneNo,String sim,String model,String systemVersion,String channel,String verison)
	throws Exception;
	
	/*
	 * 处理追号问题 2013-06-02
	 */
	public void abortChase(Member member, Integer chaseNo, String ip, Long ct) throws Exception;
	
	
	/**
	 * 功能： 针对用户追号处理接口 输入参数： member ---会员信息 ct ---终端ID号 termChase ---追号期次
	 * multipleChase ---追号倍数 amountChase---追号金额 iLotteryType ---彩票类型
	 * content---投注内容 amount ---投注总金额 totalAmount ---总金额 uploadFilePath
	 * ---上传方案路径 chaseType ---追号类别 fromPage---页面来源 ip---ip地址 walletType ---支付类型
	 * addAttribute ---是否追加 startCondition ---开始条件 stopCondition---终止条件 gentype---静态标识 输出参数： 作者： lm 日期： 2013-06-02
	 * 
	 * @throws Exception
	 */
	public abstract String buyLotteryPlanChase(Member member, Long ct, String[] termChase, String[] multipleChase, String[] amountChase,
			int iLotteryType, String content, Integer amount, String uploadFilePath, int chaseType, int fromPage, String ip, int walletType,
			String addAttribute,GenType gentype,String term,String title,Integer stopamount,String description,int fromClient,int iplayType,String phoneNo,String sim,String model,String systemVersion,String channel,String version) throws ServiceException, Exception;
	
	/**
	 * 追号购买
	 * @param chaseTerm
	 * @param nowTerm
	 * @throws Exception
	 */
	public void doChase(ChaseTasksBean chaseTerm, Isuses nowTerm) throws ServiceException, Exception;
	
	/***
	 * 用户每期认购的最大限额
	 * @param lotteryType
	 * @param term
	 * @param account
	 * @return
	 */
	public boolean doBuyLotteryPlanCount(Sites site,LotteryType lotteryType,String term,String account)throws ServiceException, Exception;
	
	
}
