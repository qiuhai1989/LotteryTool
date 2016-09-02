package com.yuncai.modules.lottery.action.software;

import java.util.List;

import com.yuncai.core.tools.CompressFile;
import com.yuncai.core.tools.MD5;
import com.yuncai.core.util.Dates;
import com.yuncai.core.util.HttpUtil;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanType;
import com.yuncai.modules.lottery.model.Oracle.toolType.WinStatus;
import com.yuncai.modules.lottery.software.service.SoftwareService;



import javax.annotation.Resource;

import org.jdom.Element;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;


@SuppressWarnings("serial")
@Controller("ycSoftLotteryTestAction")
@Scope("prototype")
public class SoftwardTestsAction  extends BaseAction{
	
	@Resource
	private SoftwareService softwareService; 
	
	public String index(){
		 Element myBody=new Element("body");
		 //9040
//		 Element toPlanDetail = new Element("toPlanDetail");
//		 Element planNo = new Element("planNo");	
//		 planNo.setText("1050");
//		 toPlanDetail.addContent(planNo);
		 //myBody.addContent(toPlanDetail);
		 //9030
//		 Element hmFound = new Element("hmFound");
//		 Element lotteryType = new Element("lotteryType");
//		 Element readName = new Element("readName");
//		 Element offSet = new Element("offSet");
//		 Element pageSize = new Element("pageSize");
//		 lotteryType.setText("0");
//		 readName.setText(null);
//		 offSet.setText("1");
//		 pageSize.setText("5");
//		 hmFound.addContent(lotteryType);
//		 hmFound.addContent(readName);
//		 hmFound.addContent(offSet);
//		 hmFound.addContent(pageSize);
//		 myBody.addContent(hmFound);
		//4001开奖历史查询 
/*		 Element drawHistory = new Element("drawHistory");
		 Element lotteryType = new Element("lotteryType");
		 Element term = new Element("term");
		 Element offSet = new Element("offSet");
		 Element order = new Element("order");
		 Element pageSize = new Element("pageSize");
//		 
		 lotteryType.setText("78");
		 term.setText("2014050914");
		 order.setText("1");
		 offSet.setText("1");
		 pageSize.setText("15");
		 drawHistory.addContent(lotteryType);
		 drawHistory.addContent(term);
		 drawHistory.addContent(offSet);
		 drawHistory.addContent(order);
		 drawHistory.addContent(pageSize);
		 
		 myBody.addContent(drawHistory);*/
//		 
			//测试交易流水3007
//		 Element transaction = new Element("transaction");
//		 Element planType = new Element("planType");
//		 planType.setText("1");
//		 Element userName = new Element("userName");
//		 userName.setText("admin");
//		 Element listPlanNo = new Element("listPlanNo");
////		 listPlanNo.setText("801,802,798");
//		 Element startDate = new Element("startDate");
//		 Element account = new Element("account");
//		 account.setText("admin");
////		 测试startDate为空情况
//		 startDate.setText("2013-08-01 12:10:30");
//		 Element endDate = new Element("endDate");
//		 endDate.setText("2013-08-31 12:10:30");
//		 Element planNo = new Element("planNo");
//		 planNo.setText("3179");
//		 Element order = new Element("order");
//		 order.setText("1");
//		 Element offset = new Element("offset");
//		 offset.setText("1");
//		 Element pageSize = new Element("pageSize");
//		 pageSize.setText("5");
//		 Element lotteryType = new Element("lotteryId");
//		 lotteryType.setText("-1");
//		 transaction.addContent(account);
//		 transaction.addContent(planType);
//		 transaction.addContent(userName);
//		 transaction.addContent(offset);
//		 transaction.addContent(startDate);
//		 transaction.addContent(endDate);
//		 transaction.addContent(listPlanNo);
//		 transaction.addContent(planNo);
//		 transaction.addContent(order);
//		 transaction.addContent(pageSize);
//		 transaction.addContent(lotteryType);
//		 myBody.addContent(transaction);
//		 版本
//		 Element version=new Element("version");
//		 Element versionName=new Element("versionName");
//		 Element versionNo=new Element("versionNo");
//		 versionName.setText("android");
//		 versionNo.setText("1.0.0");
//		 version.addContent(versionName);
//		 version.addContent(versionNo);
//		 myBody.addContent(version);
//4005
		 Element winQuery = new Element("winQuery");
		 Element lotteryId = new Element("lotteryId");
		 lotteryId.setText("72,39,63,64,74");
		 winQuery.addContent(lotteryId);
		 myBody.addContent(winQuery);
//2004
//		 Element userInfo = new Element("userInfo");
//		 Element userName = new Element("userName");
//		 userName.setText("qiuhai");
//		 userInfo.addContent(userName);
//		 myBody.addContent(userInfo);
		//4009
//		 Element req = new Element("req");
//		 
//		 Element lotteryType = new Element("lotteryType");
//		 lotteryType.setText("3");
//		 Element account = new Element("account");
//		 account.setText("test1");
//		 Element status = new Element("status");
//		 status.setText("-1");
//		 Element date = new Element("date");
//		 date.setText("2013-08");
//		 
//		 Element planNO  = new Element("planNo");
//		 planNO.setText("0");
//		 Element order = new Element("order");
//		 order.setText("1");
//		 Element pageSize  = new Element("pageSize");
// 		 pageSize.setText("100");
//// 		 
// 		 req.addContent(lotteryType);
// 		 req.addContent(account);
// 		 req.addContent(status);
// 		 req.addContent(date);
// 		 req.addContent(planNO);
// 		 req.addContent(order);
// 		 req.addContent(pageSize);
// 		 
// 		 myBody.addContent(req);		

//4010
		 
//		 Element chaseTaskId = new Element("chaseTaskId");
//		 chaseTaskId.setText("70");
//		 myBody.addContent(chaseTaskId);

//4011
/*		 Element toPlanDetail = new Element("toPlanDetail");
		 Element planNo = new Element("planNo");
		 planNo.setText("10879");
		 toPlanDetail.addContent(planNo);
		 myBody.addContent(toPlanDetail);*/
	
//3008
		 
//		 Element hmFound = new Element("hmFound");
//		 Element lotteryType = new Element("lotteryType");
//		 lotteryType.setText("-1");
//		 Element readName = new Element("readName");
//		 Element offSet = new Element("offSet");
//		 offSet.setText("1");
//		 Element pageSize = new Element("pageSize");
//		 pageSize.setText("10");
//		 Element order = new Element("order");
//		 order.setText("0");
//		 Element key = new Element("key");
//		 key.setText("0");
//		 
//		 hmFound.addContent(lotteryType);
//		 hmFound.addContent(readName);
//		 hmFound.addContent(offSet);
//		 hmFound.addContent(pageSize);
//		 hmFound.addContent(order);
//		 hmFound.addContent(key);
//		 
//		 myBody.addContent(hmFound);
//3009
		 
//		 Element toPlanDetail = new Element("toPlanDetail");
//		 Element planNo = new Element("planNo");
//		 planNo.setText("2683");
//		 toPlanDetail.addContent(planNo);
//		 myBody.addContent(toPlanDetail);
		 
//4005
		 
//		 Element winQuery = new Element("winQuery");
//		 Element lotteryId = new Element("lotteryId");
//		 lotteryId.setText("-1");
//		 winQuery.addContent(lotteryId);
//		 myBody.addContent(winQuery);
//4008		 
//		 Element account = new Element("account");
//		 account.setText("qiuhai");
//		 Element time = new Element("time");
//		 time.setText("2013-12");
//		 Element transType = new Element("transType");
//		 transType.setText("0");
//		 Element lineNo = new Element("lineNo");
//		 lineNo.setText("0");
//		 Element order = new Element("order");
//		 order.setText("1");
//		 Element pageSize = new Element("pageSize");
//		 pageSize.setText("15");
//		 Element date = new Element("date");
//		 date.setText("2013-08-20");
//		 Element beginDate = new Element("beginDate");
//		 beginDate.setText("2013-12-01");
//		 Element endDate = new Element("endDate");
//		 endDate.setText("2013-12-05");
//		 Element pageNo = new Element("pageNo");
//		 pageNo.setText("1");
//		 myBody.addContent(account);
//		 myBody.addContent(time);
//		 myBody.addContent(transType);
//		 myBody.addContent(lineNo);
//		 myBody.addContent(order);
//		 myBody.addContent(pageSize);		
//		 myBody.addContent(date);
//		 myBody.addContent(beginDate);
//		 myBody.addContent(endDate);
//		 myBody.addContent(pageNo);
//4015
//		 Element req = new Element("req");
//		 Element account = new Element("account");
//		 Element email = new Element("email");
//		 account.setText("qiuhai");
//		 email.setText("55704123ggdd@163.com");
//		 req.addContent(account);
//		 req.addContent(email);
//		 
//		 myBody.addContent(req);
//4016
//		 Element req = new Element("req");
//		 Element account = new Element("account");
//		 account.setText("qiuhai");
//		 Element pwd = new Element("pwd");
//		 pwd.setText(MD5.encode("1"));
//		 req.addContent(account);
//		 req.addContent(pwd);
//		 myBody.addContent(req);
	//4017
//		 Element toPlanDetail = new Element("toPlanDetail");
//		 Element planNo = new Element("planNo");	
//		 planNo.setText("8252");//2719
//		 toPlanDetail.addContent(planNo);
//		 myBody.addContent(toPlanDetail);
	//4013
//		 Element owner = new Element("owner");
//		 owner.setText("qiuhai");
//		 myBody.addContent(owner);
//4018
		 
	//4019
//		 Element req = new Element("req");
//		 Element req = new Element("req");
//		
//		 myBody.addContent(owner); 
		 
		//1005
//		 Element update = new Element("update");
//		 Element userName = new Element("userName");
//		 Element status = new Element("status");
//		 Element realName = new Element("realName");
//		 Element mobile = new Element("mobile");
//		 Element certNo = new Element("certNo");
//		 Element email = new Element("email");
//		 Element authCode = new Element("authCode");
//		 Element nickName = new Element("nickName");
//
//		 userName.setText("qiuhai");
//		 status.setText("4");
//		 realName.setText("丘海");
//		 mobile.setText("18664315576");
//		 certNo.setText("1111111111111111");
//		 email.setText("55704123ggdd@153.com");
//		 authCode.setText("");
//		 nickName.setText("为人民币服务");
//		 
//		 update.addContent(userName);
//		 update.addContent(status);
//		 update.addContent(realName);
//		 update.addContent(mobile);
//		 update.addContent(certNo);
//		 update.addContent(email);
//		 update.addContent(authCode);
//		 update.addContent(nickName);
//		 
//		 myBody.addContent(update);
		 
		
		 
		 
		//模拟前端提示语
//			Element myBody=new Element("body");
//			Element cl = new Element("cl");
//			Element sendType = new Element("sendType");//0启动时加载 1请求时加载
//			Element cluesID = new Element("cluesID");
//			sendType.setText("1");//0启动时候加载。1请求
//			cluesID.setText("21");//请求时传送，sendType为0时任意值
//			cl.addContent(sendType);
//			cl.addContent(cluesID);
//			myBody.addContent(cl);
//			String countext="";  //testVersion
//			try {
//				countext=this.softwareService.toPackageRs(myBody, "7001", "ycAndroid","channel201");
//			} catch (Exception e) {
//			}
			//广告图管理
//			 Element myBody=new Element("body");
//			 Element ap = new Element("ap");
//			 Element updcode = new Element("updcode");
//			 Element picId = new Element("picId");
//			 picId.setText("0");//编号
//			 updcode.setText("2013-12-10");//2013-12-10 15:10:08
//			 ap.addContent(updcode);
//			 ap.addContent(picId);
//			 myBody.addContent(ap);
//			 String countext="";  //testVersion
//			 try {
//				 countext=this.softwareService.toPackageRs(myBody, "7002", "ycAndroid","ycAndroid");
//			} catch (Exception e) {
//			}
			
			//模拟彩种管理
//			 Element myBody=new Element("body");
//			 String countext="";  //testVersion
//			 try {
//				 countext=this.softwareService.toPackageRs(myBody, "7003", "ycAndroid","ycAndroid");
//			} catch (Exception e) {
//			}
//			公告管理
//			 Element myBody=new Element("body");
//			 Element notices=new Element("notices");
//			 for(int i=1;i<4;i++){
//				 Element notice = new Element("notice");
//				 Element noticeNo = new Element("noticeNo");
//				 Element updcode = new Element("updcode");
//				 noticeNo.setText(""+(i));
//				 updcode.setText("13889933224061");
//				 notice.addContent(noticeNo);
//				 notice.addContent(updcode);
//				 notices.addContent(notice);
//			 }
//			 myBody.addContent(notices);
//			 String countext="";  //testVersion
//			 try {
//				 countext=this.softwareService.toPackageRs(myBody, "7004", "ycAndroid","ycAndroid");
//			} catch (Exception e) {
//			}
			//push管理
//			 Element myBody=new Element("body");
//			 Element pushs=new Element("pushs");
//			 Element user=new Element("user");
//			 for(int i=1;i<20;i++){
//				 Element push = new Element("push");
//				 Element pushNo = new Element("pushNo");
//				 Element updcode = new Element("updcode");
//				 pushNo.setText(""+(i));
//				 updcode.setText("1389162396281");
//				 push.addContent(pushNo);
//				 push.addContent(updcode);
//				 pushs.addContent(push);
//			 }
//			 user.setText("test1");
//			 myBody.addContent(user);
//			 myBody.addContent(pushs);
		 
		 
		 //4022
/*		 	Element req = new Element("req");
		 	Element account = new Element("account");
		 	Element shareNo = new Element("shareNo");
		 	Element shareName = new Element("shareName");
		 	account.setText("qiuhai");
		 	shareNo.setText("1232323");
		 	shareName.setText("沙发");
		 	
		 	req.addContent(account);
		 	req.addContent(shareNo);
		 	req.addContent(shareName);
		 	
		 	myBody.addContent(req);*/
			 String countext="";  //testVersion
			 try {
				 countext=this.softwareService.toPackageRs(myBody, "4005", "ycAndroid","ycAndroid");
			} catch (Exception e) {
			}
			
			super.forwardUrl="http://localhost:8088/software/ycSoftLottery.php";
			String result=HttpUtil.sendMessageSessionID(countext,super.forwardUrl);
			byte[] decompressed=Base64.decode(result);
			result =CompressFile.decompress(decompressed);
			
			return super.renderTextHtml(result);
		}


}
