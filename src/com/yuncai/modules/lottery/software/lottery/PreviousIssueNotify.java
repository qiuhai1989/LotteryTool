package com.yuncai.modules.lottery.software.lottery;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.jdom.Element;

import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Sql.Isuses;
import com.yuncai.modules.lottery.service.oracleService.lottery.LotteryPlanService;
import com.yuncai.modules.lottery.service.sqlService.lottery.IsusesService;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;

public class PreviousIssueNotify extends BaseAction implements SoftwareProcess {
	private SoftwareService softwareService;
	private IsusesService sqlIsusesService;
		
	@Override
	@SuppressWarnings("unchecked")
	public String execute(Element bodyEle, String agentId) {
		Integer lastIssue = null;// 彩期，用于判断是否是新的
		Integer lotteryId = null;
		// -------------------新建xml包体--------------------------
		Element myBody = new Element("body");
		Element responseCodeEle = new Element("responseCode"); // 返回值
		Element responseMessage = new Element("responseMessage");
		Element openResults = new Element("openResults");
		// -------------------------------------------------
		String message = "组装节点不存在或查询存在异常";
		try {
			// 获取彩种
			lotteryId = Integer.parseInt(bodyEle.getChildText("lotteryType"));
			LotteryType lotterytype = LotteryType.getItem(lotteryId);
			// 彩期
			lastIssue = Integer.parseInt(bodyEle.getChildText("issue"));
			Integer currentIssue = lastIssue + 10;

			if (lastIssue != -1) {
				Integer tmpIssue = lastIssue + 10000;
				Isuses isuses = sqlIsusesService.findPreByLotteryTypeAndTerm(lotterytype, tmpIssue.toString());

				if (isuses != null) {
					currentIssue = Integer.parseInt(isuses.getName());
					if (isuses.getName().equals(lastIssue.toString())) {
						responseCodeEle.setText("1");
						responseMessage.setText("暂无新的彩期数据！");
						myBody.addContent(responseCodeEle);
						myBody.addContent(responseMessage);
						return softwareService.toPackage(myBody, "9031", agentId);
					}
				}
			}
			if (lotteryId != null && LotteryType.getItem(lotteryId) == null) {
				message = "该彩种不存在";
				return PackageXml("3010", message, "9031", agentId);
			}
			LotteryType lotteryType = LotteryType.getItem(lotteryId);

			// 获取前100次彩期信息
			List<Isuses> list = sqlIsusesService.findPre100OpenPrizeTerm(lotteryType);

			if (list == null || list.size() == 0) {
				message = "暂无开奖的彩期";
				return PackageXml("3010", message, "9031", agentId);
			}

			Integer issueCount = list.size();

			Integer difCount = lastIssue == -1 ? 10 : (currentIssue - lastIssue);

			Integer issueNeedCount = issueCount > difCount ? difCount : issueCount; // 所需返回的记录条数

			if (list != null && issueCount > 0) {
				if (lotterytype == LotteryType.GD11X5 || lotterytype == LotteryType.SYYDJ) {
					Package11x5(lotteryId, list, issueCount, difCount, issueNeedCount, openResults);
				}
				if (lotterytype == LotteryType.TCCJDLT) {
					PackageDlt(lotteryId, list, issueCount, difCount, issueNeedCount, openResults);
				}
				if (lotterytype == LotteryType.SZPL3) {
					PackagePl3(lotteryId, list, issueCount, difCount, issueNeedCount, openResults);
				}
				if (lotterytype == LotteryType.SZPL5) {
					PackagePl5(lotteryId, list, issueCount, difCount, issueNeedCount, openResults);
				}
				if (lotterytype == LotteryType.QXC) {
					PackageQxc(lotteryId, list, issueCount, difCount, issueNeedCount, openResults);
				}
			}

			responseCodeEle.setText("0");
			responseMessage.setText("处理成功！");
			myBody.addContent(responseCodeEle);
			myBody.addContent(responseMessage);
			myBody.addContent(openResults);
			return softwareService.toPackage(myBody, "9031", agentId);

		} catch (Exception e) {
			try {
				return PackageXml("3010", message, "9031", agentId);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

		return null;
	}

	// 大乐透组装信息
	public void PackageDlt(Integer lotteryId, List<Isuses> list, Integer issueCount, Integer difCount, Integer issueNeedCount, Element openResults) {
		Integer redBallCount = 35;// 红球个数
		Integer blueBallCount = 12;// 蓝球个数

		// 存贮红球中奖次数
		Integer[] bingoRedBallCounts = new Integer[redBallCount];
		// 存贮蓝球中奖次数
		Integer[] bingoBlueBallCounts = new Integer[redBallCount];
		// 存贮红球遗漏次数
		Integer[] missingRedBallCounts = new Integer[redBallCount];
		// 存贮红球遗漏次数
		Integer[] missingBlueBallCounts = new Integer[blueBallCount];
		
		// 存贮红球单双遗漏次数(1位)
		Integer[] _1missingRedBallCounts = new Integer[2];
		// 存贮红球单双遗漏次数(2位)
		Integer[] _2missingRedBallCounts = new Integer[2];
		// 存贮红球单双遗漏次数(3位)
		Integer[] _3missingRedBallCounts = new Integer[2];
		// 存贮红球单双遗漏次数(4位)
		Integer[] _4missingRedBallCounts = new Integer[2];
		// 存贮红球单双遗漏次数(5位)
		Integer[] _5missingRedBallCounts = new Integer[2];
		

		// 初始化
		for (Integer i = 0; i < redBallCount; i++) {
			bingoRedBallCounts[i] = 0;
			missingRedBallCounts[i] = 0;
		}
		for (Integer i = 0; i < blueBallCount; i++) {
			bingoBlueBallCounts[i] = 0;
			missingBlueBallCounts[i] = 0;
		}
		for (Integer i = 0; i < 2; i++){
			_1missingRedBallCounts[i] = 0;
			_2missingRedBallCounts[i] = 0;
			_3missingRedBallCounts[i] = 0;
			_4missingRedBallCounts[i] = 0;
			_5missingRedBallCounts[i] = 0;
		}

		// 储存遗漏值
		String[] redBingos = new String[issueNeedCount];
		String[] blueBingos = new String[issueNeedCount];
		// 储存命中值
		String[] redMissings = new String[issueNeedCount];
		String[] _1dsRedMissings = new String[issueNeedCount];
		String[] _2dsRedMissings = new String[issueNeedCount];
		String[] _3dsRedMissings = new String[issueNeedCount];
		String[] _4dsRedMissings = new String[issueNeedCount];
		String[] _5dsRedMissings = new String[issueNeedCount];
		String[] blueMissings = new String[issueNeedCount];

		Integer startTag = issueCount > difCount ? issueCount - difCount : 0; // 开始记录信息的下标
		for (Integer k = 0; k < issueCount; k++) {
			// 获取开奖信息
			String winNumber = list.get(issueCount - k - 1).getWinLotteryNumber();
			String[] winBalls = winNumber.split(" \\+ ");
			String[] winRedBalls = winBalls[0].split(" ");
			String[] winBlueBalls = winBalls[1].split(" ");

			// 遗漏标志
			Integer[] redMissingTags = new Integer[redBallCount];
			Integer[] blueMissingTags = new Integer[blueBallCount];
			// 单号遗漏标志
			Integer[] dRedMissingTags = new Integer[redBallCount];
			
			for (int i = 0; i < redBallCount; i++) {
				redMissingTags[i] = -1;
				dRedMissingTags[i] = -1;
			}
			for (int i = 0; i < blueBallCount; i++) {
				blueMissingTags[i] = -1;
			}

			// 获取当期期每个球的中奖次数以及遗漏标记
			for (Integer i = 1; i <= redBallCount; i++) {
				for (Integer j = 0; j < winRedBalls.length; j++) {
					// 球号
					String ball = i < 10 ? "0" + i.toString() : i.toString();
					// 累加中奖次数与重置遗漏值
					if (winRedBalls[j].equals(ball)) {
						bingoRedBallCounts[i - 1] += 1;
						missingRedBallCounts[i - 1] = 0;
						redMissingTags[i - 1] = 0;
					} else {
						if (redMissingTags[i - 1] == -1) {
							redMissingTags[i - 1] = 1;
						}
					}
				}
			}
			
			for (Integer i = 1; i <= blueBallCount; i++) {
				for (Integer j = 0; j < winBlueBalls.length; j++) {
					// 球号
					String ball = i < 10 ? "0" + i.toString() : i.toString();
					// 累加中奖次数与重置遗漏值
					if (winBlueBalls[j].equals(ball)) {
						bingoBlueBallCounts[i - 1] += 1;
						missingBlueBallCounts[i - 1] = 0;
						blueMissingTags[i - 1] = 0;
					} else {
						if (blueMissingTags[i - 1] == -1) {
							blueMissingTags[i - 1] = 1;
						}
					}
				}
			}
			
			// 标志单双遗漏值标记
			for (Integer j = 1; j <= winRedBalls.length; j++) {
				
				if(Integer.parseInt(winRedBalls[j-1])%2 == 0){
					dRedMissingTags[j-1] = 1;		
					if(j==1){
						_1missingRedBallCounts[1] = 0;
					}
					if(j==2){
						_2missingRedBallCounts[1] = 0;
					}
					if(j==3){
						_3missingRedBallCounts[1] = 0;
					}
					if(j==4){
						_4missingRedBallCounts[1] = 0;
					}
					if(j==5){
						_5missingRedBallCounts[1] = 0;
					}
				}else{
					dRedMissingTags[j-1] = 0;
					if(j==1){
						_1missingRedBallCounts[0] = 0;
					}
					if(j==2){
						_2missingRedBallCounts[0] = 0;
					}
					if(j==3){
						_3missingRedBallCounts[0] = 0;
					}
					if(j==4){
						_4missingRedBallCounts[0] = 0;
					}
					if(j==5){
						_5missingRedBallCounts[0] = 0;
					}
				}
			}

			// 获取遗漏值
			for (Integer i = 1; i <= redBallCount; i++) {
				if (redMissingTags[i - 1] == 1) {
					missingRedBallCounts[i - 1] += 1;
				}
				if(dRedMissingTags[i-1] == 1){
					if(i == 1){
						_1missingRedBallCounts[0] += 1;
					}	
					if(i == 2){
						_2missingRedBallCounts[0] += 1;
					}
					if(i == 3){
						_3missingRedBallCounts[0] += 1;
					}
					if(i == 4){
						_4missingRedBallCounts[0] += 1;
					}
					if(i == 5){
						_5missingRedBallCounts[0] += 1;
					}
				}else if(dRedMissingTags[i-1] == 0){
					if(i == 1){
						_1missingRedBallCounts[1] += 1;
					}	
					if(i == 2){
						_2missingRedBallCounts[1] += 1;
					}
					if(i == 3){
						_3missingRedBallCounts[1] += 1;
					}
					if(i == 4){
						_4missingRedBallCounts[1] += 1;
					}
					if(i == 5){
						_5missingRedBallCounts[1] += 1;
					}	
				}
			}
			for (Integer i = 1; i <= blueBallCount; i++) {
				if (blueMissingTags[i - 1] == 1) {
					missingBlueBallCounts[i - 1] += 1;
				}
			}

			// 开始记录
			if (k >= startTag) {
				// 组装遗漏值与中奖次数
				String redBingoStr = "";
				String redMissingStr = "";
				String _1dsRedMissingStr = "";
				String _2dsRedMissingStr = "";
				String _3dsRedMissingStr = "";
				String _4dsRedMissingStr = "";
				String _5dsRedMissingStr = "";
				String blueBingoStr = "";
				String blueMissingStr = "";
				for (Integer b = 0; b < redBallCount; b++) {
					redBingoStr += bingoRedBallCounts[b].toString();
					redMissingStr += missingRedBallCounts[b].toString();
					if (b < redBallCount - 1) {
						redBingoStr += " ";
						redMissingStr += " ";
					}
					
					if(b == 0){
						_1dsRedMissingStr = _1missingRedBallCounts[0] != 0 ? _1missingRedBallCounts[0].toString() : _1missingRedBallCounts[1].toString();
					}
					if(b == 1){
						_2dsRedMissingStr = _2missingRedBallCounts[0] != 0 ? _2missingRedBallCounts[0].toString() :  _2missingRedBallCounts[1].toString();
					}
					if(b == 2){
						_3dsRedMissingStr = _3missingRedBallCounts[0] != 0 ? _3missingRedBallCounts[0].toString() : _3missingRedBallCounts[1].toString();
					}
					if(b == 3){
						_4dsRedMissingStr = _4missingRedBallCounts[0] != 0 ? _4missingRedBallCounts[0].toString() : _4missingRedBallCounts[1].toString();
					}
					if(b == 4){
						_5dsRedMissingStr = _5missingRedBallCounts[0] != 0 ? _5missingRedBallCounts[0].toString() : _5missingRedBallCounts[1].toString();
					}
				}
				for (Integer b = 0; b < blueBallCount; b++) {
					blueBingoStr += bingoBlueBallCounts[b].toString();
					blueMissingStr += missingBlueBallCounts[b].toString();
					if (b < blueBallCount - 1) {
						blueBingoStr += " ";
						blueMissingStr += " ";
					}
				}

				redBingos[k - startTag] = redBingoStr;
				redMissings[k - startTag] = redMissingStr;
				_1dsRedMissings[k - startTag] = _1dsRedMissingStr;
				_2dsRedMissings[k - startTag] = _2dsRedMissingStr;
				_3dsRedMissings[k - startTag] = _3dsRedMissingStr;
				_4dsRedMissings[k - startTag] = _4dsRedMissingStr;
				_5dsRedMissings[k - startTag] = _5dsRedMissingStr;
				blueBingos[k - startTag] = blueBingoStr;
				blueMissings[k - startTag] = blueMissingStr;
			}
		}

		for (Integer k = 0; k < issueNeedCount; k++) {
			// 组装遗漏值
			String missingStr = redMissings[k] + " + " + blueMissings[k];
			// 组装命中次数
			String bingoStr = redBingos[k] + " + " + blueBingos[k];
			// 插入信息
			Element eleResult = new Element("result");
			Element eleStatus = new Element("status");
			Element eleIssue = new Element("issue");
			Element eleBalls = new Element("balls");
			Element eleMissing = new Element("missing");
			Element ele1dsMissing = new Element("_1dsMissing");
			Element ele2dsMissing = new Element("_2dsMissing");
			Element ele3dsMissing = new Element("_3dsMissing");
			Element ele4dsMissing = new Element("_4dsMissing");
			Element ele5dsMissing = new Element("_5dsMissing");
			Element eleBingo = new Element("bingo");
			Element eleLotteryId = new Element("lotteryId");

			eleStatus.setText(list.get(issueNeedCount - k - 1).getIsOpened() ? "1" : "0");
			eleIssue.setText(list.get(issueNeedCount - k - 1).getName());
			eleBalls.setText(list.get(issueNeedCount - k - 1).getWinLotteryNumber());
			eleMissing.setText(missingStr);
			ele1dsMissing.setText(_1dsRedMissings[k]);
			ele2dsMissing.setText(_2dsRedMissings[k]);
			ele3dsMissing.setText(_3dsRedMissings[k]);
			ele4dsMissing.setText(_4dsRedMissings[k]);
			ele5dsMissing.setText(_5dsRedMissings[k]);
			eleBingo.setText(bingoStr);
			eleLotteryId.setText(lotteryId.toString());
			
			eleResult.addContent(eleStatus);
			eleResult.addContent(eleIssue);
			eleResult.addContent(eleBalls);
			eleResult.addContent(eleMissing);
			eleResult.addContent(ele1dsMissing);
			eleResult.addContent(ele2dsMissing);
			eleResult.addContent(ele3dsMissing);
			eleResult.addContent(ele4dsMissing);
			eleResult.addContent(ele5dsMissing);
			eleResult.addContent(eleBingo);
			eleResult.addContent(eleLotteryId);

			openResults.addContent(eleResult);
		}

		if (openResults.getChildren("result") != null && openResults.getChildren("result").size() == 1) {
			// 插入信息
			Element eleResult = new Element("result");
			Element eleStatus = new Element("status");
			Element eleIssue = new Element("issue");
			Element eleBalls = new Element("balls");
			Element eleMissing = new Element("missing");
			Element ele1dsMissing = new Element("_1dsMissing");
			Element ele2dsMissing = new Element("_2dsMissing");
			Element ele3dsMissing = new Element("_3dsMissing");
			Element ele4dsMissing = new Element("_4dsMissing");
			Element ele5dsMissing = new Element("_5dsMissing");
			Element eleBingo = new Element("bingo");
			Element eleLotteryId = new Element("lotteryId");
			
			eleLotteryId.setText("");	
			eleResult.addContent(eleLotteryId);
			eleStatus.setText("");
			eleIssue.setText("");
			eleBalls.setText("");
			eleMissing.setText("");
			ele1dsMissing.setText("");
			ele2dsMissing.setText("");
			ele3dsMissing.setText("");
			ele4dsMissing.setText("");
			ele5dsMissing.setText("");
			eleBingo.setText("");

			eleResult.addContent(eleStatus);
			eleResult.addContent(eleIssue);
			eleResult.addContent(eleBalls);
			eleResult.addContent(eleMissing);
			eleResult.addContent(ele1dsMissing);
			eleResult.addContent(ele2dsMissing);
			eleResult.addContent(ele3dsMissing);
			eleResult.addContent(ele4dsMissing);
			eleResult.addContent(ele5dsMissing);
			eleResult.addContent(eleBingo);

			openResults.addContent(eleResult);
		}
	}

	// 排列3组装信息
	public void PackagePl3(Integer lotteryId, List<Isuses> list, Integer issueCount, Integer difCount, Integer issueNeedCount, Element openResults) {
		Integer redBallCount = 10;// 红球个数
		// 存贮红球中奖次数
		Integer[] bingoRedBallCounts = new Integer[redBallCount];
		// 存贮红球中奖次数(百位)
		Integer[] WbingoRedBallCounts = new Integer[redBallCount];
		// 存贮红球中奖次数(个位)
		Integer[] QbingoRedBallCounts = new Integer[redBallCount];
		// 存贮红球中奖次数(十位)
		Integer[] BbingoRedBallCounts = new Integer[redBallCount];
		// 存贮红球中奖次数(重位)
		Integer[] CbingoRedBallCounts = new Integer[redBallCount];
		// 存贮红球中奖次数(单位)
		Integer[] DbingoRedBallCounts = new Integer[redBallCount];
		
		// 存贮红球遗漏次数
		Integer[] missingRedBallCounts = new Integer[redBallCount];
		// 存贮红球遗漏次数(百位)
		Integer[] WmissingRedBallCounts = new Integer[redBallCount];
		// 存贮红球遗漏次数(个位)
		Integer[] QmissingRedBallCounts = new Integer[redBallCount];
		// 存贮红球遗漏次数(十位)
		Integer[] BmissingRedBallCounts = new Integer[redBallCount];
		// 存贮红球遗漏次数(重位)
		Integer[] CmissingRedBallCounts = new Integer[redBallCount];
		// 存贮红球遗漏次数(单位)
		Integer[] DmissingRedBallCounts = new Integer[redBallCount];

		// 初始化
		for (Integer i = 0; i < redBallCount; i++) {
			bingoRedBallCounts[i] = 0;
			WbingoRedBallCounts[i] = 0;
			QbingoRedBallCounts[i] = 0;
			BbingoRedBallCounts[i] = 0;
			CbingoRedBallCounts[i] = 0;
			DbingoRedBallCounts[i] = 0;

			missingRedBallCounts[i] = 0;
			WmissingRedBallCounts[i] = 0;
			QmissingRedBallCounts[i] = 0;
			BmissingRedBallCounts[i] = 0;
			CmissingRedBallCounts[i] = 0;
			DmissingRedBallCounts[i] = 0;
		}

		// 储存遗漏值
		String[] bingos = new String[issueNeedCount];
		// 储存命中值
		String[] missings = new String[issueNeedCount];

		// 储存百位遗漏值
		String[] Wbingos = new String[issueNeedCount];
		// 储存百位命中值
		String[] Wmissings = new String[issueNeedCount];

		// 储存十位遗漏值
		String[] Qbingos = new String[issueNeedCount];
		// 储存十位命中值
		String[] Qmissings = new String[issueNeedCount];

		// 储存个位遗漏值
		String[] Bbingos = new String[issueNeedCount];
		// 储存个位命中值
		String[] Bmissings = new String[issueNeedCount];

		// 储存个位遗漏值
		String[] Cbingos = new String[issueNeedCount];
		// 储存个位命中值
		String[] Cmissings = new String[issueNeedCount];
		
		// 储存个位遗漏值
		String[] Dbingos = new String[issueNeedCount];
		// 储存个位命中值
		String[] Dmissings = new String[issueNeedCount];
		
		Integer startTag = issueCount > difCount ? issueCount - difCount : 0; // 开始记录信息的下标

		for (Integer k = 0; k < issueCount; k++) {
			// 获取开奖信息
			String winNumber = list.get(issueCount - k - 1).getWinLotteryNumber();
			String[] winRedBalls = new String[3];
			winRedBalls[0] = winNumber.substring(0, 1);
			winRedBalls[1] = winNumber.substring(1, 2);
			winRedBalls[2] = winNumber.substring(2, 3);

			Integer[] missingTags = new Integer[redBallCount];
			Integer[] WmissingTags = new Integer[redBallCount];
			Integer[] QmissingTags = new Integer[redBallCount];
			Integer[] BmissingTags = new Integer[redBallCount];
			Integer[] CmissingTags = new Integer[redBallCount];
			Integer[] DmissingTags = new Integer[redBallCount];
			
			Integer[] tmpBingoRedBallCounts = new Integer[redBallCount];//存储本期命中次数		
			
			// 获取当期期每个球的中奖次数以及遗漏标记
			for (Integer i = 0; i < redBallCount; i++) {
				
				missingTags[i] = -1;
				WmissingTags[i] = -1;
				QmissingTags[i] = -1;
				BmissingTags[i] = -1;
				CmissingTags[i] = -1;
				DmissingTags[i] = -1;
				
				tmpBingoRedBallCounts[i] = 0;
				
				for (Integer j = 0; j < winRedBalls.length; j++) {
					// 球号
					String ball = i.toString();
					// 累加中奖次数与重置遗漏值
					if (winRedBalls[j].equals(ball)) {	
						tmpBingoRedBallCounts[i] += 1;
						bingoRedBallCounts[i] += 1;
						missingRedBallCounts[i] = 0;
						missingTags[i] = 0;
						// 累加
						if (j == 0) {
							WbingoRedBallCounts[i] += 1;
							WmissingRedBallCounts[i] = 0;
							WmissingTags[i] = 0;
						} else if (j == 1) {
							QbingoRedBallCounts[i] += 1;
							QmissingRedBallCounts[i] = 0;
							QmissingTags[i] = 0;
						} else if (j == 2) {
							BbingoRedBallCounts[i] += 1;
							BmissingRedBallCounts[i] = 0;
							BmissingTags[i] = 0;
						}
					} else {
						if (missingTags[i] == -1) {
							missingTags[i] = 1;
						}
						if (j == 0) {
							if (WmissingTags[i] == -1) {
								WmissingTags[i] = 1;
							}
						} else if (j == 1) {
							if (QmissingTags[i] == -1) {
								QmissingTags[i] = 1;
							}
						} else if (j == 2) {
							if (BmissingTags[i] == -1) {
								BmissingTags[i] = 1;
							}
						}
					}
				}
			}
			
			// 获取当期期每个球的中奖次数以及遗漏标记(重位，单位)
			for (Integer i = 0; i < redBallCount; i++) {	
				if(tmpBingoRedBallCounts[i] > 1){ // 重位中
					CbingoRedBallCounts[i] += 1;
					CmissingRedBallCounts[i] = 0;
					CmissingTags[i] = 0;
					DmissingTags[i] = 1;
				}else if(tmpBingoRedBallCounts[i] == 1){// 单位中
					DbingoRedBallCounts[i] += 1;
					DmissingRedBallCounts[i] = 0;
					DmissingTags[i] = 0;
					CmissingTags[i] = 1;
				}else{ // 都不中
					CmissingTags[i] = 1;
					DmissingTags[i] = 1;
				}
			}

			// 获取遗漏值
			for (Integer i = 0; i < redBallCount; i++) {
				if (missingTags[i] == 1) {
					missingRedBallCounts[i] += 1;
				}
				if (WmissingTags[i] == 1) {
					WmissingRedBallCounts[i] += 1;
				}
				if (QmissingTags[i] == 1) {
					QmissingRedBallCounts[i] += 1;
				}
				if (BmissingTags[i] == 1) {
					BmissingRedBallCounts[i] += 1;
				}
				if (CmissingTags[i] == 1) {
					CmissingRedBallCounts[i] += 1;
				}
				if (DmissingTags[i] == 1) {
					DmissingRedBallCounts[i] += 1;
				}
			}

			// 开始记录
			if (k >= startTag) {
				// 组装遗漏值与中奖次数
				String bingoStr = "";
				String missingStr = "";
				// 组装百位遗漏值与中奖次数
				String WbingoStr = "";
				String WmissingStr = "";
				// 组装个位遗漏值与中奖次数
				String QbingoStr = "";
				String QmissingStr = "";
				// 组装十位遗漏值与中奖次数
				String BbingoStr = "";
				String BmissingStr = "";
				// 组装重位遗漏值与中奖次数
				String CbingoStr = "";
				String CmissingStr = "";
				// 组装单位遗漏值与中奖次数
				String DbingoStr = "";
				String DmissingStr = "";
				
				for (Integer b = 0; b < redBallCount; b++) {
					bingoStr += bingoRedBallCounts[b].toString();
					missingStr += missingRedBallCounts[b].toString();

					WbingoStr += WbingoRedBallCounts[b].toString();
					WmissingStr += WmissingRedBallCounts[b].toString();

					QbingoStr += QbingoRedBallCounts[b].toString();
					QmissingStr += QmissingRedBallCounts[b].toString();

					BbingoStr += BbingoRedBallCounts[b].toString();
					BmissingStr += BmissingRedBallCounts[b].toString();

					CbingoStr += CbingoRedBallCounts[b].toString();
					CmissingStr += CmissingRedBallCounts[b].toString();

					DbingoStr += DbingoRedBallCounts[b].toString();
					DmissingStr += DmissingRedBallCounts[b].toString();
					
					if (b < redBallCount - 1) {
						bingoStr += " ";
						missingStr += " ";
						WbingoStr += " ";
						WmissingStr += " ";
						QbingoStr += " ";
						QmissingStr += " ";
						BbingoStr += " ";
						BmissingStr += " ";
						CbingoStr += " ";
						CmissingStr += " ";
						DbingoStr += " ";
						DmissingStr += " ";
					}
				}
				bingos[k - startTag] = bingoStr;
				missings[k - startTag] = missingStr;
				Wbingos[k - startTag] = WbingoStr;
				Wmissings[k - startTag] = WmissingStr;
				Qbingos[k - startTag] = QbingoStr;
				Qmissings[k - startTag] = QmissingStr;
				Bbingos[k - startTag] = BbingoStr;
				Bmissings[k - startTag] = BmissingStr;
				Cbingos[k - startTag] = CbingoStr;
				Cmissings[k - startTag] = CmissingStr;
				Dbingos[k - startTag] = DbingoStr;
				Dmissings[k - startTag] = DmissingStr;
			}
		}

		for (Integer k = 0; k < issueNeedCount; k++) {		
			// 获取开奖信息
			String winNumber = list.get(issueNeedCount - k - 1).getWinLotteryNumber();
			String[] winRedBalls = new String[3];
			winRedBalls[0] = winNumber.substring(0, 1);
			winRedBalls[1] = winNumber.substring(1, 2);
			winRedBalls[2] = winNumber.substring(2, 3);
			
			// 组装遗漏值
			String missingStr = missings[k];
			// 组装命中次数
			String bingoStr = bingos[k];

			// 组装遗漏值
			String WmissingStr = Wmissings[k];
			// 组装命中次数
			String WbingoStr = Wbingos[k];
			// 组装遗漏值
			String QmissingStr = Qmissings[k];
			// 组装命中次数
			String QbingoStr = Qbingos[k];
			// 组装遗漏值
			String BmissingStr = Bmissings[k];
			// 组装命中次数
			String BbingoStr = Bbingos[k];
			// 组装遗漏值
			String CmissingStr = Cmissings[k];
			// 组装命中次数
			String CbingoStr = Cbingos[k];
			// 组装遗漏值
			String DmissingStr = Dmissings[k];
			// 组装命中次数
			String DbingoStr = Dbingos[k];

			// 插入信息
			Element eleResult = new Element("result");
			Element eleStatus = new Element("status");
			Element eleIssue = new Element("issue");
			Element eleBalls = new Element("balls");
			Element eleMissing = new Element("missing");
			Element eleBingo = new Element("bingo");
			Element eleWMissing = new Element("Bmissing");
			Element eleWBingo = new Element("Bbingo");
			Element eleQMissing = new Element("Smissing");
			Element eleQBingo = new Element("Sbingo");
			Element eleBMissing = new Element("Gmissing");
			Element eleBBingo = new Element("Gbingo");
			Element eleCMissing = new Element("Cmissing");
			Element eleCBingo = new Element("Cbingo");
			Element eleDMissing = new Element("Dmissing");
			Element eleDBingo = new Element("Dbingo");
			
			Element eleLotteryId = new Element("lotteryId");
			eleLotteryId.setText(lotteryId.toString());
			eleResult.addContent(eleLotteryId);
			
			eleStatus.setText(list.get(issueNeedCount - k - 1).getIsOpened() ? "1" : "0");
			eleIssue.setText(list.get(issueNeedCount - k - 1).getName());
			eleBalls.setText(winRedBalls[0] + " " + winRedBalls[1] + " " + winRedBalls[2]);
			eleMissing.setText(missingStr);
			eleBingo.setText(bingoStr);
			eleWMissing.setText(WmissingStr);
			eleWBingo.setText(WbingoStr);
			eleQMissing.setText(QmissingStr);
			eleQBingo.setText(QbingoStr);
			eleBMissing.setText(BmissingStr);
			eleBBingo.setText(BbingoStr);
			eleCMissing.setText(CmissingStr);
			eleCBingo.setText(CbingoStr);
			eleDMissing.setText(DmissingStr);
			eleDBingo.setText(DbingoStr);

			eleResult.addContent(eleStatus);
			eleResult.addContent(eleIssue);
			eleResult.addContent(eleBalls);
			eleResult.addContent(eleMissing);
			eleResult.addContent(eleBingo);
			eleResult.addContent(eleWMissing);
			eleResult.addContent(eleWBingo);
			eleResult.addContent(eleQMissing);
			eleResult.addContent(eleQBingo);
			eleResult.addContent(eleBMissing);
			eleResult.addContent(eleBBingo);
			eleResult.addContent(eleCMissing);
			eleResult.addContent(eleCBingo);
			eleResult.addContent(eleDMissing);
			eleResult.addContent(eleDBingo);

			openResults.addContent(eleResult);
		}

		if (openResults.getChildren("result") != null && openResults.getChildren("result").size() == 1) {
			// 插入信息
			Element eleResult = new Element("result");
			Element eleStatus = new Element("status");
			Element eleIssue = new Element("issue");
			Element eleBalls = new Element("balls");
			Element eleMissing = new Element("missing");
			Element eleBingo = new Element("bingo");
			Element eleWMissing = new Element("Bmissing");
			Element eleWBingo = new Element("Bbingo");
			Element eleQMissing = new Element("Smissing");
			Element eleQBingo = new Element("Sbingo");
			Element eleBMissing = new Element("Gmissing");
			Element eleBBingo = new Element("Gbingo");
			Element eleCMissing = new Element("Cmissing");
			Element eleCBingo = new Element("Cbingo");
			Element eleDMissing = new Element("Dmissing");
			Element eleDBingo = new Element("Dbingo");
			
			Element eleLotteryId = new Element("lotteryId");
			eleLotteryId.setText("");
			eleResult.addContent(eleLotteryId);
			
			eleStatus.setText("");
			eleIssue.setText("");
			eleBalls.setText("");
			eleMissing.setText("");
			eleBingo.setText("");
			eleWMissing.setText("");
			eleWBingo.setText("");
			eleQMissing.setText("");
			eleQBingo.setText("");
			eleBMissing.setText("");
			eleBBingo.setText("");
			eleResult.addContent("");
			eleResult.addContent("");
			eleResult.addContent("");
			eleResult.addContent("");

			eleResult.addContent(eleStatus);
			eleResult.addContent(eleIssue);
			eleResult.addContent(eleBalls);
			eleResult.addContent(eleMissing);
			eleResult.addContent(eleBingo);
			eleResult.addContent(eleWMissing);
			eleResult.addContent(eleWBingo);
			eleResult.addContent(eleQMissing);
			eleResult.addContent(eleQBingo);
			eleResult.addContent(eleBMissing);
			eleResult.addContent(eleBBingo);
			eleResult.addContent(eleCMissing);
			eleResult.addContent(eleCBingo);
			eleResult.addContent(eleDMissing);
			eleResult.addContent(eleDBingo);

			openResults.addContent(eleResult);
		}
	}

	// 排列5组装信息
	public void PackagePl5(Integer lotteryId, List<Isuses> list, Integer issueCount, Integer difCount, Integer issueNeedCount, Element openResults) {
		Integer redBallCount = 10;// 红球个数
		// 存贮红球中奖次数
		Integer[] bingoRedBallCounts = new Integer[redBallCount];
		// 存贮红球中奖次数(万位)
		Integer[] WbingoRedBallCounts = new Integer[redBallCount];
		// 存贮红球中奖次数(千位)
		Integer[] QbingoRedBallCounts = new Integer[redBallCount];
		// 存贮红球中奖次数(百位)
		Integer[] BbingoRedBallCounts = new Integer[redBallCount];
		// 存贮红球中奖次数(十位)
		Integer[] SbingoRedBallCounts = new Integer[redBallCount];
		// 存贮红球中奖次数(个位)
		Integer[] GbingoRedBallCounts = new Integer[redBallCount];

		// 存贮红球遗漏次数
		Integer[] missingRedBallCounts = new Integer[redBallCount];
		// 存贮红球遗漏次数(万位)
		Integer[] WmissingRedBallCounts = new Integer[redBallCount];
		// 存贮红球遗漏次数(千位)
		Integer[] QmissingRedBallCounts = new Integer[redBallCount];
		// 存贮红球遗漏次数(百位)
		Integer[] BmissingRedBallCounts = new Integer[redBallCount];
		// 存贮红球遗漏次数(十位)
		Integer[] SmissingRedBallCounts = new Integer[redBallCount];
		// 存贮红球遗漏次数(个位)
		Integer[] GmissingRedBallCounts = new Integer[redBallCount];

		// 初始化
		for (Integer i = 0; i < redBallCount; i++) {
			bingoRedBallCounts[i] = 0;
			WbingoRedBallCounts[i] = 0;
			QbingoRedBallCounts[i] = 0;
			BbingoRedBallCounts[i] = 0;
			SbingoRedBallCounts[i] = 0;
			GbingoRedBallCounts[i] = 0;

			missingRedBallCounts[i] = 0;
			WmissingRedBallCounts[i] = 0;
			QmissingRedBallCounts[i] = 0;
			BmissingRedBallCounts[i] = 0;
			SmissingRedBallCounts[i] = 0;
			GmissingRedBallCounts[i] = 0;
		}

		// 储存遗漏值
		String[] bingos = new String[issueNeedCount];
		// 储存命中值
		String[] missings = new String[issueNeedCount];

		// 储存万位遗漏值
		String[] Wbingos = new String[issueNeedCount];
		// 储存万位命中值
		String[] Wmissings = new String[issueNeedCount];

		// 储存千位遗漏值
		String[] Qbingos = new String[issueNeedCount];
		// 储存千位命中值
		String[] Qmissings = new String[issueNeedCount];

		// 储存百位遗漏值
		String[] Bbingos = new String[issueNeedCount];
		// 储存百位命中值
		String[] Bmissings = new String[issueNeedCount];

		// 储存十位遗漏值
		String[] Sbingos = new String[issueNeedCount];
		// 储存十位命中值
		String[] Smissings = new String[issueNeedCount];

		// 储存个位遗漏值
		String[] Gbingos = new String[issueNeedCount];
		// 储存个位命中值
		String[] Gmissings = new String[issueNeedCount];

		Integer startTag = issueCount > difCount ? issueCount - difCount : 0; // 开始记录信息的下标

		for (Integer k = 0; k < issueCount; k++) {
			// 获取开奖信息
			String winNumber = list.get(issueCount - k - 1).getWinLotteryNumber();
			String[] winRedBalls = new String[5];
			winRedBalls[0] = winNumber.substring(0, 1);
			winRedBalls[1] = winNumber.substring(1, 2);
			winRedBalls[2] = winNumber.substring(2, 3);
			winRedBalls[3] = winNumber.substring(3, 4);
			winRedBalls[4] = winNumber.substring(4, 5);

			Integer[] missingTags = new Integer[redBallCount];
			Integer[] WmissingTags = new Integer[redBallCount];
			Integer[] QmissingTags = new Integer[redBallCount];
			Integer[] BmissingTags = new Integer[redBallCount];
			Integer[] SmissingTags = new Integer[redBallCount];
			Integer[] GmissingTags = new Integer[redBallCount];

			for (int i = 0; i < redBallCount; i++) {
				missingTags[i] = -1;
				WmissingTags[i] = -1;
				QmissingTags[i] = -1;
				BmissingTags[i] = -1;
				SmissingTags[i] = -1;
				GmissingTags[i] = -1;
			}

			// 获取当期期每个球的中奖次数以及遗漏标记
			for (Integer i = 0; i < redBallCount; i++) {
				for (Integer j = 0; j < winRedBalls.length; j++) {
					// 球号
					String ball = i.toString();
					// 累加中奖次数与重置遗漏值
					if (winRedBalls[j].equals(ball)) {
						bingoRedBallCounts[i] += 1;
						missingRedBallCounts[i] = 0;
						missingTags[i] = 0;
						// 累加
						if (j == 0) {
							WbingoRedBallCounts[i] += 1;
							WmissingRedBallCounts[i] = 0;
							WmissingTags[i] = 0;
						} else if (j == 1) {
							QbingoRedBallCounts[i] += 1;
							QmissingRedBallCounts[i] = 0;
							QmissingTags[i] = 0;
						} else if (j == 2) {
							BbingoRedBallCounts[i] += 1;
							BmissingRedBallCounts[i] = 0;
							BmissingTags[i] = 0;
						} else if (j == 3) {
							SbingoRedBallCounts[i] += 1;
							SmissingRedBallCounts[i] = 0;
							SmissingTags[i] = 0;
						} else if (j == 4) {
							GbingoRedBallCounts[i] += 1;
							GmissingRedBallCounts[i] = 0;
							GmissingTags[i] = 0;
						}
					} else {
						if (missingTags[i] == -1) {
							missingTags[i] = 1;
						}
						if (j == 0) {
							if (WmissingTags[i] == -1) {
								WmissingTags[i] = 1;
							}
						} else if (j == 1) {
							if (QmissingTags[i] == -1) {
								QmissingTags[i] = 1;
							}
						} else if (j == 2) {
							if (BmissingTags[i] == -1) {
								BmissingTags[i] = 1;
							}
						} else if (j == 3) {
							if (SmissingTags[i] == -1) {
								SmissingTags[i] = 1;
							}
						} else if (j == 4) {
							if (GmissingTags[i] == -1) {
								GmissingTags[i] = 1;
							}
						}
					}
				}
			}

			// 获取遗漏值
			for (Integer i = 0; i < redBallCount; i++) {
				if (missingTags[i] == 1) {
					missingRedBallCounts[i] += 1;
				}
				if (WmissingTags[i] == 1) {
					WmissingRedBallCounts[i] += 1;
				}
				if (QmissingTags[i] == 1) {
					QmissingRedBallCounts[i] += 1;
				}
				if (BmissingTags[i] == 1) {
					BmissingRedBallCounts[i] += 1;
				}
				if (SmissingTags[i] == 1) {
					SmissingRedBallCounts[i] += 1;
				}
				if (GmissingTags[i] == 1) {
					GmissingRedBallCounts[i] += 1;
				}
			}

			// 开始记录
			if (k >= startTag) {
				// 组装遗漏值与中奖次数
				String bingoStr = "";
				String missingStr = "";
				// 组装万位遗漏值与中奖次数
				String WbingoStr = "";
				String WmissingStr = "";
				// 组装千位遗漏值与中奖次数
				String QbingoStr = "";
				String QmissingStr = "";
				// 组装百位遗漏值与中奖次数
				String BbingoStr = "";
				String BmissingStr = "";
				// 组装十位遗漏值与中奖次数
				String SbingoStr = "";
				String SmissingStr = "";
				// 组装个位遗漏值与中奖次数
				String GbingoStr = "";
				String GmissingStr = "";
				for (Integer b = 0; b < redBallCount; b++) {
					bingoStr += bingoRedBallCounts[b].toString();
					missingStr += missingRedBallCounts[b].toString();

					WbingoStr += WbingoRedBallCounts[b].toString();
					WmissingStr += WmissingRedBallCounts[b].toString();

					QbingoStr += QbingoRedBallCounts[b].toString();
					QmissingStr += QmissingRedBallCounts[b].toString();

					BbingoStr += BbingoRedBallCounts[b].toString();
					BmissingStr += BmissingRedBallCounts[b].toString();
					
					SbingoStr += SbingoRedBallCounts[b].toString();
					SmissingStr += SmissingRedBallCounts[b].toString();
					
					GbingoStr += GbingoRedBallCounts[b].toString();
					GmissingStr += GmissingRedBallCounts[b].toString();
					if (b < redBallCount - 1) {
						bingoStr += " ";
						missingStr += " ";
						WbingoStr += " ";
						WmissingStr += " ";
						QbingoStr += " ";
						QmissingStr += " ";
						BbingoStr += " ";
						BmissingStr += " ";
						SbingoStr += " ";
						SmissingStr += " ";
						GbingoStr += " ";
						GmissingStr += " ";
					}
				}
				bingos[k - startTag] = bingoStr;
				missings[k - startTag] = missingStr;
				Wbingos[k - startTag] = WbingoStr;
				Wmissings[k - startTag] = WmissingStr;
				Qbingos[k - startTag] = QbingoStr;
				Qmissings[k - startTag] = QmissingStr;
				Bbingos[k - startTag] = BbingoStr;
				Bmissings[k - startTag] = BmissingStr;
				Sbingos[k - startTag] = SbingoStr;
				Smissings[k - startTag] = SmissingStr;
				Gbingos[k - startTag] = GbingoStr;
				Gmissings[k - startTag] = GmissingStr;
			}
		}

		for (Integer k = 0; k < issueNeedCount; k++) {
			
			// 获取开奖信息
			String winNumber = list.get(issueNeedCount - k - 1).getWinLotteryNumber();
			String[] winRedBalls = new String[5];
			winRedBalls[0] = winNumber.substring(0, 1);
			winRedBalls[1] = winNumber.substring(1, 2);
			winRedBalls[2] = winNumber.substring(2, 3);
			winRedBalls[3] = winNumber.substring(3, 4);
			winRedBalls[4] = winNumber.substring(4, 5);
			
			// 组装遗漏值
			String missingStr = missings[k];
			// 组装命中次数
			String bingoStr = bingos[k];

			// 组装遗漏值
			String WmissingStr = Wmissings[k];
			// 组装命中次数
			String WbingoStr = Wbingos[k];
			// 组装遗漏值
			String QmissingStr = Qmissings[k];
			// 组装命中次数
			String QbingoStr = Qbingos[k];
			// 组装遗漏值
			String BmissingStr = Bmissings[k];
			// 组装命中次数
			String BbingoStr = Bbingos[k];
			// 组装遗漏值
			String SmissingStr = Smissings[k];
			// 组装命中次数
			String SbingoStr = Sbingos[k];
			// 组装遗漏值
			String GmissingStr = Gmissings[k];
			// 组装命中次数
			String GbingoStr = Gbingos[k];

			// 插入信息
			Element eleResult = new Element("result");
			Element eleStatus = new Element("status");
			Element eleIssue = new Element("issue");
			Element eleBalls = new Element("balls");
			Element eleMissing = new Element("missing");
			Element eleBingo = new Element("bingo");
			Element eleWMissing = new Element("Wmissing");
			Element eleWBingo = new Element("Wbingo");
			Element eleQMissing = new Element("Qmissing");
			Element eleQBingo = new Element("Qbingo");
			Element eleBMissing = new Element("Bmissing");
			Element eleBBingo = new Element("Bbingo");
			Element eleSMissing = new Element("Smissing");
			Element eleSBingo = new Element("Sbingo");
			Element eleGMissing = new Element("Gmissing");
			Element eleGBingo = new Element("Gbingo");

			Element eleLotteryId = new Element("lotteryId");
			eleLotteryId.setText(lotteryId.toString());
			eleResult.addContent(eleLotteryId);
			
			eleStatus.setText(list.get(issueNeedCount - k - 1).getIsOpened() ? "1" : "0");
			eleIssue.setText(list.get(issueNeedCount - k - 1).getName());
			eleBalls.setText(winRedBalls[0] + " " + winRedBalls[1] + " " + winRedBalls[2] + " " + winRedBalls[3] + " " + winRedBalls[4]);
			eleMissing.setText(missingStr);
			eleBingo.setText(bingoStr);
			eleWMissing.setText(WmissingStr);
			eleWBingo.setText(WbingoStr);
			eleQMissing.setText(QmissingStr);
			eleQBingo.setText(QbingoStr);
			eleBMissing.setText(BmissingStr);
			eleBBingo.setText(BbingoStr);
			eleSMissing.setText(SmissingStr);
			eleSBingo.setText(SbingoStr);
			eleGMissing.setText(GmissingStr);
			eleGBingo.setText(GbingoStr);

			eleResult.addContent(eleStatus);
			eleResult.addContent(eleIssue);
			eleResult.addContent(eleBalls);
			eleResult.addContent(eleMissing);
			eleResult.addContent(eleBingo);
			eleResult.addContent(eleWMissing);
			eleResult.addContent(eleWBingo);
			eleResult.addContent(eleQMissing);
			eleResult.addContent(eleQBingo);
			eleResult.addContent(eleBMissing);
			eleResult.addContent(eleBBingo);
			eleResult.addContent(eleSMissing);
			eleResult.addContent(eleSBingo);
			eleResult.addContent(eleGMissing);
			eleResult.addContent(eleGBingo);

			openResults.addContent(eleResult);
		}

		if (openResults.getChildren("result") != null && openResults.getChildren("result").size() == 1) {
			// 插入信息
			Element eleResult = new Element("result");
			Element eleStatus = new Element("status");
			Element eleIssue = new Element("issue");
			Element eleBalls = new Element("balls");
			Element eleMissing = new Element("missing");
			Element eleBingo = new Element("bingo");
			Element eleWMissing = new Element("Wmissing");
			Element eleWBingo = new Element("Wbingo");
			Element eleQMissing = new Element("Qmissing");
			Element eleQBingo = new Element("Qbingo");
			Element eleBMissing = new Element("Bmissing");
			Element eleBBingo = new Element("Bbingo");
			Element eleSMissing = new Element("Smissing");
			Element eleSBingo = new Element("Sbingo");
			Element eleGMissing = new Element("Gmissing");
			Element eleGBingo = new Element("Gbingo");
			
			Element eleLotteryId = new Element("lotteryId");
			eleLotteryId.setText(lotteryId.toString());
			eleResult.addContent(eleLotteryId);

			eleStatus.setText("");
			eleIssue.setText("");
			eleBalls.setText("");
			eleMissing.setText("");
			eleBingo.setText("");
			eleWMissing.setText("");
			eleWBingo.setText("");
			eleQMissing.setText("");
			eleQBingo.setText("");
			eleBMissing.setText("");
			eleBBingo.setText("");
			eleSMissing.setText("");
			eleSBingo.setText("");
			eleGMissing.setText("");
			eleGBingo.setText("");

			eleResult.addContent(eleStatus);
			eleResult.addContent(eleIssue);
			eleResult.addContent(eleBalls);
			eleResult.addContent(eleMissing);
			eleResult.addContent(eleBingo);
			eleResult.addContent(eleWMissing);
			eleResult.addContent(eleWBingo);
			eleResult.addContent(eleQMissing);
			eleResult.addContent(eleQBingo);
			eleResult.addContent(eleBMissing);
			eleResult.addContent(eleBBingo);
			eleResult.addContent(eleSMissing);
			eleResult.addContent(eleSBingo);
			eleResult.addContent(eleGMissing);
			eleResult.addContent(eleGBingo);

			openResults.addContent(eleResult);
		}
	}
	
	// 七星彩组装信息
	public void PackageQxc(Integer lotteryId, List<Isuses> list, Integer issueCount, Integer difCount, Integer issueNeedCount, Element openResults) {
		Integer redBallCount = 10;// 红球个数
		// 存贮红球中奖次数
		Integer[] bingoRedBallCounts = new Integer[redBallCount];
		// 存贮红球中奖次数(万位)
		Integer[] WbingoRedBallCounts = new Integer[redBallCount];
		// 存贮红球中奖次数(千位)
		Integer[] QbingoRedBallCounts = new Integer[redBallCount];
		// 存贮红球中奖次数(百位)
		Integer[] BbingoRedBallCounts = new Integer[redBallCount];
		// 存贮红球中奖次数(十位)
		Integer[] SbingoRedBallCounts = new Integer[redBallCount];
		// 存贮红球中奖次数(个位)
		Integer[] GbingoRedBallCounts = new Integer[redBallCount];
		// 存贮红球中奖次数(六位)
		Integer[] LbingoRedBallCounts = new Integer[redBallCount];
		// 存贮红球中奖次数(七位)
		Integer[] QibingoRedBallCounts = new Integer[redBallCount];

		// 存贮红球遗漏次数
		Integer[] missingRedBallCounts = new Integer[redBallCount];
		// 存贮红球遗漏次数(万位)
		Integer[] WmissingRedBallCounts = new Integer[redBallCount];
		// 存贮红球遗漏次数(千位)
		Integer[] QmissingRedBallCounts = new Integer[redBallCount];
		// 存贮红球遗漏次数(百位)
		Integer[] BmissingRedBallCounts = new Integer[redBallCount];
		// 存贮红球遗漏次数(十位)
		Integer[] SmissingRedBallCounts = new Integer[redBallCount];
		// 存贮红球遗漏次数(个位)
		Integer[] GmissingRedBallCounts = new Integer[redBallCount];
		// 存贮红球遗漏次数(六位)
		Integer[] LmissingRedBallCounts = new Integer[redBallCount];
		// 存贮红球遗漏次数(七位)
		Integer[] QimissingRedBallCounts = new Integer[redBallCount];

		// 初始化
		for (Integer i = 0; i < redBallCount; i++) {
			bingoRedBallCounts[i] = 0;
			WbingoRedBallCounts[i] = 0;
			QbingoRedBallCounts[i] = 0;
			BbingoRedBallCounts[i] = 0;
			SbingoRedBallCounts[i] = 0;
			GbingoRedBallCounts[i] = 0;
			LbingoRedBallCounts[i] = 0;
			QibingoRedBallCounts[i] = 0;

			missingRedBallCounts[i] = 0;
			WmissingRedBallCounts[i] = 0;
			QmissingRedBallCounts[i] = 0;
			BmissingRedBallCounts[i] = 0;
			SmissingRedBallCounts[i] = 0;
			GmissingRedBallCounts[i] = 0;
			LmissingRedBallCounts[i] = 0;
			QimissingRedBallCounts[i] = 0;
		}

		// 储存遗漏值
		String[] bingos = new String[issueNeedCount];
		// 储存命中值
		String[] missings = new String[issueNeedCount];

		// 储存万位遗漏值
		String[] Wbingos = new String[issueNeedCount];
		// 储存万位命中值
		String[] Wmissings = new String[issueNeedCount];

		// 储存千位遗漏值
		String[] Qbingos = new String[issueNeedCount];
		// 储存千位命中值
		String[] Qmissings = new String[issueNeedCount];

		// 储存百位遗漏值
		String[] Bbingos = new String[issueNeedCount];
		// 储存百位命中值
		String[] Bmissings = new String[issueNeedCount];

		// 储存十位遗漏值
		String[] Sbingos = new String[issueNeedCount];
		// 储存十位命中值
		String[] Smissings = new String[issueNeedCount];

		// 储存个位遗漏值
		String[] Gbingos = new String[issueNeedCount];
		// 储存个位命中值
		String[] Gmissings = new String[issueNeedCount];
		
		// 储存百位遗漏值
		String[] Lbingos = new String[issueNeedCount];
		// 储存百位命中值
		String[] Lmissings = new String[issueNeedCount];
		

		// 储存百位遗漏值
		String[] Qibingos = new String[issueNeedCount];
		// 储存百位命中值
		String[] Qimissings = new String[issueNeedCount];

		Integer startTag = issueCount > difCount ? issueCount - difCount : 0; // 开始记录信息的下标

		for (Integer k = 0; k < issueCount; k++) {
			// 获取开奖信息
			String winNumber = list.get(issueCount - k - 1).getWinLotteryNumber();
			String[] winRedBalls = new String[7];
			winRedBalls[0] = winNumber.substring(0, 1);
			winRedBalls[1] = winNumber.substring(1, 2);
			winRedBalls[2] = winNumber.substring(2, 3);
			winRedBalls[3] = winNumber.substring(3, 4);
			winRedBalls[4] = winNumber.substring(4, 5);
			winRedBalls[5] = winNumber.substring(5, 6);
			winRedBalls[6] = winNumber.substring(6, 7);

			Integer[] missingTags = new Integer[redBallCount];
			Integer[] WmissingTags = new Integer[redBallCount];
			Integer[] QmissingTags = new Integer[redBallCount];
			Integer[] BmissingTags = new Integer[redBallCount];
			Integer[] SmissingTags = new Integer[redBallCount];
			Integer[] GmissingTags = new Integer[redBallCount];
			Integer[] LmissingTags = new Integer[redBallCount];
			Integer[] QimissingTags = new Integer[redBallCount];


			for (int i = 0; i < redBallCount; i++) {
				missingTags[i] = -1;
				WmissingTags[i] = -1;
				QmissingTags[i] = -1;
				BmissingTags[i] = -1;
				SmissingTags[i] = -1;
				GmissingTags[i] = -1;
				LmissingTags[i] = -1;
				QimissingTags[i] = -1;
			}

			// 获取当期期每个球的中奖次数以及遗漏标记
			for (Integer i = 0; i < redBallCount; i++) {
				for (Integer j = 0; j < winRedBalls.length; j++) {
					// 球号
					String ball = i.toString();
					// 累加中奖次数与重置遗漏值
					if (winRedBalls[j].equals(ball)) {
						bingoRedBallCounts[i] += 1;
						missingRedBallCounts[i] = 0;
						missingTags[i] = 0;
						// 累加
						if (j == 0) {
							WbingoRedBallCounts[i] += 1;
							WmissingRedBallCounts[i] = 0;
							WmissingTags[i] = 0;
						} else if (j == 1) {
							QbingoRedBallCounts[i] += 1;
							QmissingRedBallCounts[i] = 0;
							QmissingTags[i] = 0;
						} else if (j == 2) {
							BbingoRedBallCounts[i] += 1;
							BmissingRedBallCounts[i] = 0;
							BmissingTags[i] = 0;
						} else if (j == 3) {
							SbingoRedBallCounts[i] += 1;
							SmissingRedBallCounts[i] = 0;
							SmissingTags[i] = 0;
						} else if (j == 4) {
							GbingoRedBallCounts[i] += 1;
							GmissingRedBallCounts[i] = 0;
							GmissingTags[i] = 0;
						}else if (j == 5) {
							LbingoRedBallCounts[i] += 1;
							LmissingRedBallCounts[i] = 0;
							LmissingTags[i] = 0;
						}else if (j == 6) {
							QibingoRedBallCounts[i] += 1;
							QimissingRedBallCounts[i] = 0;
							QimissingTags[i] = 0;
						}
					} else {
						if (missingTags[i] == -1) {
							missingTags[i] = 1;
						}
						if (j == 0) {
							if (WmissingTags[i] == -1) {
								WmissingTags[i] = 1;
							}
						} else if (j == 1) {
							if (QmissingTags[i] == -1) {
								QmissingTags[i] = 1;
							}
						} else if (j == 2) {
							if (BmissingTags[i] == -1) {
								BmissingTags[i] = 1;
							}
						} else if (j == 3) {
							if (SmissingTags[i] == -1) {
								SmissingTags[i] = 1;
							}
						} else if (j == 4) {
							if (GmissingTags[i] == -1) {
								GmissingTags[i] = 1;
							}
						}else if (j == 5) {
							if (LmissingTags[i] == -1) {
								LmissingTags[i] = 1;
							}
						}else if (j == 6) {
							if (QimissingTags[i] == -1) {
								QimissingTags[i] = 1;
							}
						}
					}
				}
			}

			// 获取遗漏值
			for (Integer i = 0; i < redBallCount; i++) {
				if (missingTags[i] == 1) {
					missingRedBallCounts[i] += 1;
				}
				if (WmissingTags[i] == 1) {
					WmissingRedBallCounts[i] += 1;
				}
				if (QmissingTags[i] == 1) {
					QmissingRedBallCounts[i] += 1;
				}
				if (BmissingTags[i] == 1) {
					BmissingRedBallCounts[i] += 1;
				}
				if (SmissingTags[i] == 1) {
					SmissingRedBallCounts[i] += 1;
				}
				if (GmissingTags[i] == 1) {
					GmissingRedBallCounts[i] += 1;
				}
				if (LmissingTags[i] == 1) {
					LmissingRedBallCounts[i] += 1;
				}
				if (QimissingTags[i] == 1) {
					QimissingRedBallCounts[i] += 1;
				}
			}

			// 开始记录
			if (k >= startTag) {
				// 组装遗漏值与中奖次数
				String bingoStr = "";
				String missingStr = "";
				// 组装万位遗漏值与中奖次数
				String WbingoStr = "";
				String WmissingStr = "";
				// 组装千位遗漏值与中奖次数
				String QbingoStr = "";
				String QmissingStr = "";
				// 组装百位遗漏值与中奖次数
				String BbingoStr = "";
				String BmissingStr = "";
				// 组装十位遗漏值与中奖次数
				String SbingoStr = "";
				String SmissingStr = "";
				// 组装个位遗漏值与中奖次数
				String GbingoStr = "";
				String GmissingStr = "";
				// 组装六位遗漏值与中奖次数
				String LbingoStr = "";
				String LmissingStr = "";
				// 组装七位遗漏值与中奖次数
				String QibingoStr = "";
				String QimissingStr = "";
				for (Integer b = 0; b < redBallCount; b++) {
					bingoStr += bingoRedBallCounts[b].toString();
					missingStr += missingRedBallCounts[b].toString();

					WbingoStr += WbingoRedBallCounts[b].toString();
					WmissingStr += WmissingRedBallCounts[b].toString();

					QbingoStr += QbingoRedBallCounts[b].toString();
					QmissingStr += QmissingRedBallCounts[b].toString();

					BbingoStr += BbingoRedBallCounts[b].toString();
					BmissingStr += BmissingRedBallCounts[b].toString();
					
					SbingoStr += SbingoRedBallCounts[b].toString();
					SmissingStr += SmissingRedBallCounts[b].toString();
					
					GbingoStr += GbingoRedBallCounts[b].toString();
					GmissingStr += GmissingRedBallCounts[b].toString();
					
					LbingoStr += LbingoRedBallCounts[b].toString();
					LmissingStr += LmissingRedBallCounts[b].toString();
					
					QibingoStr += QibingoRedBallCounts[b].toString();
					QimissingStr += QimissingRedBallCounts[b].toString();
					if (b < redBallCount - 1) {
						bingoStr += " ";
						missingStr += " ";
						WbingoStr += " ";
						WmissingStr += " ";
						QbingoStr += " ";
						QmissingStr += " ";
						BbingoStr += " ";
						BmissingStr += " ";
						SbingoStr += " ";
						SmissingStr += " ";
						GbingoStr += " ";
						GmissingStr += " ";
						LbingoStr += " ";
						LmissingStr += " ";
						QibingoStr += " ";
						QimissingStr += " ";
					}
				}
				bingos[k - startTag] = bingoStr;
				missings[k - startTag] = missingStr;
				Wbingos[k - startTag] = WbingoStr;
				Wmissings[k - startTag] = WmissingStr;
				Qbingos[k - startTag] = QbingoStr;
				Qmissings[k - startTag] = QmissingStr;
				Bbingos[k - startTag] = BbingoStr;
				Bmissings[k - startTag] = BmissingStr;
				Sbingos[k - startTag] = SbingoStr;
				Smissings[k - startTag] = SmissingStr;
				Gbingos[k - startTag] = GbingoStr;
				Gmissings[k - startTag] = GmissingStr;
				Lbingos[k - startTag] = LbingoStr;
				Lmissings[k - startTag] = LmissingStr;
				Qibingos[k - startTag] = QibingoStr;
				Qimissings[k - startTag] = QimissingStr;
			}
		}

		for (Integer k = 0; k < issueNeedCount; k++) {
			
			// 获取开奖信息
			String winNumber = list.get(issueNeedCount - k - 1).getWinLotteryNumber();
			String[] winRedBalls = new String[7];
			winRedBalls[0] = winNumber.substring(0, 1);
			winRedBalls[1] = winNumber.substring(1, 2);
			winRedBalls[2] = winNumber.substring(2, 3);
			winRedBalls[3] = winNumber.substring(3, 4);
			winRedBalls[4] = winNumber.substring(4, 5);
			winRedBalls[5] = winNumber.substring(5, 6);
			winRedBalls[6] = winNumber.substring(6, 7);
			
			// 组装遗漏值
			String missingStr = missings[k];
			// 组装命中次数
			String bingoStr = bingos[k];

			// 组装遗漏值
			String WmissingStr = Wmissings[k];
			// 组装命中次数
			String WbingoStr = Wbingos[k];
			// 组装遗漏值
			String QmissingStr = Qmissings[k];
			// 组装命中次数
			String QbingoStr = Qbingos[k];
			// 组装遗漏值
			String BmissingStr = Bmissings[k];
			// 组装命中次数
			String BbingoStr = Bbingos[k];
			// 组装遗漏值
			String SmissingStr = Smissings[k];
			// 组装命中次数
			String SbingoStr = Sbingos[k];
			// 组装遗漏值
			String GmissingStr = Gmissings[k];
			// 组装命中次数
			String GbingoStr = Gbingos[k];
			// 组装遗漏值
			String LmissingStr = Lmissings[k];
			// 组装命中次数
			String LbingoStr = Lbingos[k];
			// 组装遗漏值
			String QimissingStr = Qimissings[k];
			// 组装命中次数
			String QibingoStr = Qibingos[k];

			// 插入信息
			Element eleResult = new Element("result");
			Element eleStatus = new Element("status");
			Element eleIssue = new Element("issue");
			Element eleBalls = new Element("balls");
			Element eleMissing = new Element("missing");
			Element eleBingo = new Element("bingo");
			Element eleWMissing = new Element("_1missing");
			Element eleWBingo = new Element("_1bingo");
			Element eleQMissing = new Element("_2missing");
			Element eleQBingo = new Element("_2bingo");
			Element eleBMissing = new Element("_3missing");
			Element eleBBingo = new Element("_3bingo");
			Element eleSMissing = new Element("_4missing");
			Element eleSBingo = new Element("_4bingo");
			Element eleGMissing = new Element("_5missing");
			Element eleGBingo = new Element("_5bingo");
			Element eleLMissing = new Element("_6missing");
			Element eleLBingo = new Element("_6bingo");
			Element eleQiMissing = new Element("_7missing");
			Element eleQiBingo = new Element("_7bingo");

			Element eleLotteryId = new Element("lotteryId");
			eleLotteryId.setText(lotteryId.toString());
			eleResult.addContent(eleLotteryId);
			
			eleStatus.setText(list.get(issueNeedCount - k - 1).getIsOpened() ? "1" : "0");
			eleIssue.setText(list.get(issueNeedCount - k - 1).getName());
			eleBalls.setText(winRedBalls[0] + " " + winRedBalls[1] + " " + winRedBalls[2] + " " + winRedBalls[3] + " " + winRedBalls[4]+ " " + winRedBalls[5] + " " + winRedBalls[6]);
			eleMissing.setText(missingStr);
			eleBingo.setText(bingoStr);
			eleWMissing.setText(WmissingStr);
			eleWBingo.setText(WbingoStr);
			eleQMissing.setText(QmissingStr);
			eleQBingo.setText(QbingoStr);
			eleBMissing.setText(BmissingStr);
			eleBBingo.setText(BbingoStr);
			eleSMissing.setText(SmissingStr);
			eleSBingo.setText(SbingoStr);
			eleGMissing.setText(GmissingStr);
			eleGBingo.setText(GbingoStr);
			eleLMissing.setText(LmissingStr);
			eleLBingo.setText(LbingoStr);
			eleQiMissing.setText(QimissingStr);
			eleQiBingo.setText(QibingoStr);

			eleResult.addContent(eleStatus);
			eleResult.addContent(eleIssue);
			eleResult.addContent(eleBalls);
			eleResult.addContent(eleMissing);
			eleResult.addContent(eleBingo);
			eleResult.addContent(eleWMissing);
			eleResult.addContent(eleWBingo);
			eleResult.addContent(eleQMissing);
			eleResult.addContent(eleQBingo);
			eleResult.addContent(eleBMissing);
			eleResult.addContent(eleBBingo);
			eleResult.addContent(eleSMissing);
			eleResult.addContent(eleSBingo);
			eleResult.addContent(eleGMissing);
			eleResult.addContent(eleGBingo);
			eleResult.addContent(eleLMissing);
			eleResult.addContent(eleLBingo);
			eleResult.addContent(eleQiMissing);
			eleResult.addContent(eleQiBingo);

			openResults.addContent(eleResult);
		}

		if (openResults.getChildren("result") != null && openResults.getChildren("result").size() == 1) {
			// 插入信息
			Element eleResult = new Element("result");
			Element eleStatus = new Element("status");
			Element eleIssue = new Element("issue");
			Element eleBalls = new Element("balls");
			Element eleMissing = new Element("missing");
			Element eleBingo = new Element("bingo");
			Element eleWMissing = new Element("_1missing");
			Element eleWBingo = new Element("_1bingo");
			Element eleQMissing = new Element("_2missing");
			Element eleQBingo = new Element("_2bingo");
			Element eleBMissing = new Element("_3missing");
			Element eleBBingo = new Element("_3bingo");
			Element eleSMissing = new Element("_4missing");
			Element eleSBingo = new Element("_4bingo");
			Element eleGMissing = new Element("_5missing");
			Element eleGBingo = new Element("_5bingo");
			Element eleLMissing = new Element("_6missing");
			Element eleLBingo = new Element("_6bingo");
			Element eleQiMissing = new Element("_7missing");
			Element eleQiBingo = new Element("_7bingo");
			
			Element eleLotteryId = new Element("lotteryId");
			eleLotteryId.setText(lotteryId.toString());
			eleResult.addContent(eleLotteryId);

			eleStatus.setText("");
			eleIssue.setText("");
			eleBalls.setText("");
			eleMissing.setText("");
			eleBingo.setText("");
			eleWMissing.setText("");
			eleWBingo.setText("");
			eleQMissing.setText("");
			eleQBingo.setText("");
			eleBMissing.setText("");
			eleBBingo.setText("");
			eleSMissing.setText("");
			eleSBingo.setText("");
			eleGMissing.setText("");
			eleGBingo.setText("");
			eleLMissing.setText("");
			eleLBingo.setText("");
			eleQiMissing.setText("");
			eleQiBingo.setText("");

			eleResult.addContent(eleStatus);
			eleResult.addContent(eleIssue);
			eleResult.addContent(eleBalls);
			eleResult.addContent(eleMissing);
			eleResult.addContent(eleBingo);
			eleResult.addContent(eleWMissing);
			eleResult.addContent(eleWBingo);
			eleResult.addContent(eleQMissing);
			eleResult.addContent(eleQBingo);
			eleResult.addContent(eleBMissing);
			eleResult.addContent(eleBBingo);
			eleResult.addContent(eleSMissing);
			eleResult.addContent(eleSBingo);
			eleResult.addContent(eleGMissing);
			eleResult.addContent(eleGBingo);
			eleResult.addContent(eleLMissing);
			eleResult.addContent(eleLBingo);
			eleResult.addContent(eleQiMissing);
			eleResult.addContent(eleQiBingo);

			openResults.addContent(eleResult);
		}
	}
	
	// 11选5 组装信息
	public void Package11x5(Integer lotteryId, List<Isuses> list, Integer issueCount, Integer difCount, Integer issueNeedCount, Element openResults) {
		Integer redBallCount = 11;// 红球个数
		// 存贮红球中奖次数
		Integer[] bingoRedBallCounts = new Integer[redBallCount];
		// 存贮红球中奖次数(万位)
		Integer[] WbingoRedBallCounts = new Integer[redBallCount];
		// 存贮红球中奖次数(千位)
		Integer[] QbingoRedBallCounts = new Integer[redBallCount];
		// 存贮红球中奖次数(百位)
		Integer[] BbingoRedBallCounts = new Integer[redBallCount];

		// 存贮红球遗漏次数
		Integer[] missingRedBallCounts = new Integer[redBallCount];
		// 存贮红球遗漏次数(万位)
		Integer[] WmissingRedBallCounts = new Integer[redBallCount];
		// 存贮红球遗漏次数(千位)
		Integer[] QmissingRedBallCounts = new Integer[redBallCount];
		// 存贮红球遗漏次数(百位)
		Integer[] BmissingRedBallCounts = new Integer[redBallCount];

		// 初始化
		for (Integer i = 0; i < redBallCount; i++) {
			bingoRedBallCounts[i] = 0;
			WbingoRedBallCounts[i] = 0;
			QbingoRedBallCounts[i] = 0;
			BbingoRedBallCounts[i] = 0;

			missingRedBallCounts[i] = 0;
			WmissingRedBallCounts[i] = 0;
			QmissingRedBallCounts[i] = 0;
			BmissingRedBallCounts[i] = 0;
		}

		// 储存遗漏值
		String[] bingos = new String[issueNeedCount];
		// 储存命中值
		String[] missings = new String[issueNeedCount];

		// 储存万位遗漏值
		String[] Wbingos = new String[issueNeedCount];
		// 储存万位命中值
		String[] Wmissings = new String[issueNeedCount];

		// 储存千位遗漏值
		String[] Qbingos = new String[issueNeedCount];
		// 储存千位命中值
		String[] Qmissings = new String[issueNeedCount];

		// 储存百位遗漏值
		String[] Bbingos = new String[issueNeedCount];
		// 储存百位命中值
		String[] Bmissings = new String[issueNeedCount];

		Integer startTag = issueCount > difCount ? issueCount - difCount : 0; // 开始记录信息的下标

		for (Integer k = 0; k < issueCount; k++) {
			// 获取开奖信息
			String winNumber = list.get(issueCount - k - 1).getWinLotteryNumber();
			String[] winRedBalls = winNumber.split(",");
			// String[] winBlueBalls = winNumber.split(" ");

			Integer[] missingTags = new Integer[redBallCount];
			Integer[] WmissingTags = new Integer[redBallCount];
			Integer[] QmissingTags = new Integer[redBallCount];
			Integer[] BmissingTags = new Integer[redBallCount];

			for (int i = 0; i < redBallCount; i++) {
				missingTags[i] = -1;
				WmissingTags[i] = -1;
				QmissingTags[i] = -1;
				BmissingTags[i] = -1;
			}

			// 获取当期期每个球的中奖次数以及遗漏标记
			for (Integer i = 1; i <= redBallCount; i++) {
				for (Integer j = 0; j < winRedBalls.length; j++) {
					// 球号
					String ball = i < 10 ? "0" + i.toString() : i.toString();
					// 累加中奖次数与重置遗漏值
					if (winRedBalls[j].equals(ball)) {
						bingoRedBallCounts[i - 1] += 1;
						missingRedBallCounts[i - 1] = 0;
						missingTags[i - 1] = 0;
						// 累加
						if (j == 0) {
							WbingoRedBallCounts[i - 1] += 1;
							WmissingRedBallCounts[i - 1] = 0;
							WmissingTags[i - 1] = 0;
						} else if (j == 1) {
							QbingoRedBallCounts[i - 1] += 1;
							QmissingRedBallCounts[i - 1] = 0;
							QmissingTags[i - 1] = 0;
						} else if (j == 2) {
							BbingoRedBallCounts[i - 1] += 1;
							BmissingRedBallCounts[i - 1] = 0;
							BmissingTags[i - 1] = 0;
						}
					} else {
						if (missingTags[i - 1] == -1) {
							missingTags[i - 1] = 1;
						}
						if (j == 0) {
							if (WmissingTags[i - 1] == -1) {
								WmissingTags[i - 1] = 1;
							}
						} else if (j == 1) {
							if (QmissingTags[i - 1] == -1) {
								QmissingTags[i - 1] = 1;
							}
						} else if (j == 2) {
							if (BmissingTags[i - 1] == -1) {
								BmissingTags[i - 1] = 1;
							}
						}
					}
				}
			}

			// 获取遗漏值
			for (Integer i = 1; i <= redBallCount; i++) {
				if (missingTags[i - 1] == 1) {
					missingRedBallCounts[i - 1] += 1;
				}
				if (WmissingTags[i - 1] == 1) {
					WmissingRedBallCounts[i - 1] += 1;
				}
				if (QmissingTags[i - 1] == 1) {
					QmissingRedBallCounts[i - 1] += 1;
				}
				if (BmissingTags[i - 1] == 1) {
					BmissingRedBallCounts[i - 1] += 1;
				}
			}

			// 开始记录
			if (k >= startTag) {
				// 组装遗漏值与中奖次数
				String bingoStr = "";
				String missingStr = "";
				// 组装万位遗漏值与中奖次数
				String WbingoStr = "";
				String WmissingStr = "";
				// 组装千位遗漏值与中奖次数
				String QbingoStr = "";
				String QmissingStr = "";
				// 组装百位遗漏值与中奖次数
				String BbingoStr = "";
				String BmissingStr = "";
				for (Integer b = 0; b < redBallCount; b++) {
					bingoStr += bingoRedBallCounts[b].toString();
					missingStr += missingRedBallCounts[b].toString();

					WbingoStr += WbingoRedBallCounts[b].toString();
					WmissingStr += WmissingRedBallCounts[b].toString();

					QbingoStr += QbingoRedBallCounts[b].toString();
					QmissingStr += QmissingRedBallCounts[b].toString();

					BbingoStr += BbingoRedBallCounts[b].toString();
					BmissingStr += BmissingRedBallCounts[b].toString();
					if (b < redBallCount - 1) {
						bingoStr += " ";
						missingStr += " ";
						WbingoStr += " ";
						WmissingStr += " ";
						QbingoStr += " ";
						QmissingStr += " ";
						BbingoStr += " ";
						BmissingStr += " ";
					}
				}
				bingos[k - startTag] = bingoStr;
				missings[k - startTag] = missingStr;
				Wbingos[k - startTag] = WbingoStr;
				Wmissings[k - startTag] = WmissingStr;
				Qbingos[k - startTag] = QbingoStr;
				Qmissings[k - startTag] = QmissingStr;
				Bbingos[k - startTag] = BbingoStr;
				Bmissings[k - startTag] = BmissingStr;

			}
		}

		for (Integer k = 0; k < issueNeedCount; k++) {
			// 组装遗漏值
			String missingStr = missings[k];
			// 组装命中次数
			String bingoStr = bingos[k];

			// 组装遗漏值
			String WmissingStr = Wmissings[k];
			// 组装命中次数
			String WbingoStr = Wbingos[k];
			// 组装遗漏值
			String QmissingStr = Qmissings[k];
			// 组装命中次数
			String QbingoStr = Qbingos[k];
			// 组装遗漏值
			String BmissingStr = Bmissings[k];
			// 组装命中次数
			String BbingoStr = Bbingos[k];

			// 插入信息
			Element eleResult = new Element("result");
			Element eleStatus = new Element("status");
			Element eleIssue = new Element("issue");
			Element eleBalls = new Element("balls");
			Element eleMissing = new Element("missing");
			Element eleBingo = new Element("bingo");
			Element eleWMissing = new Element("Wmissing");
			Element eleWBingo = new Element("Wbingo");
			Element eleQMissing = new Element("Qmissing");
			Element eleQBingo = new Element("Qbingo");
			Element eleBMissing = new Element("Bmissing");
			Element eleBBingo = new Element("Bbingo");

			Element eleLotteryId = new Element("lotteryId");
			eleLotteryId.setText(lotteryId.toString());
			eleResult.addContent(eleLotteryId);
			
			eleStatus.setText(list.get(issueNeedCount - k - 1).getIsOpened() ? "1" : "0");
			eleIssue.setText(list.get(issueNeedCount - k - 1).getName());
			eleBalls.setText(list.get(issueNeedCount - k - 1).getWinLotteryNumber());
			eleMissing.setText(missingStr);
			eleBingo.setText(bingoStr);
			eleWMissing.setText(WmissingStr);
			eleWBingo.setText(WbingoStr);
			eleQMissing.setText(QmissingStr);
			eleQBingo.setText(QbingoStr);
			eleBMissing.setText(BmissingStr);
			eleBBingo.setText(BbingoStr);

			eleResult.addContent(eleStatus);
			eleResult.addContent(eleIssue);
			eleResult.addContent(eleBalls);
			eleResult.addContent(eleMissing);
			eleResult.addContent(eleBingo);
			eleResult.addContent(eleWMissing);
			eleResult.addContent(eleWBingo);
			eleResult.addContent(eleQMissing);
			eleResult.addContent(eleQBingo);
			eleResult.addContent(eleBMissing);
			eleResult.addContent(eleBBingo);

			openResults.addContent(eleResult);
		}

		if (openResults.getChildren("result") != null && openResults.getChildren("result").size() == 1) {
			// 插入信息
			Element eleResult = new Element("result");
			Element eleStatus = new Element("status");
			Element eleIssue = new Element("issue");
			Element eleBalls = new Element("balls");
			Element eleMissing = new Element("missing");
			Element eleBingo = new Element("bingo");
			Element eleWMissing = new Element("Wmissing");
			Element eleWBingo = new Element("Wbingo");
			Element eleQMissing = new Element("Qmissing");
			Element eleQBingo = new Element("Qbingo");
			Element eleBMissing = new Element("Bmissing");
			Element eleBBingo = new Element("Bbingo");
			
			Element eleLotteryId = new Element("lotteryId");
			eleLotteryId.setText(lotteryId.toString());
			eleResult.addContent(eleLotteryId);

			eleStatus.setText("");
			eleIssue.setText("");
			eleBalls.setText("");
			eleMissing.setText("");
			eleBingo.setText("");
			eleWMissing.setText("");
			eleWBingo.setText("");
			eleQMissing.setText("");
			eleQBingo.setText("");
			eleBMissing.setText("");
			eleBBingo.setText("");

			eleResult.addContent(eleStatus);
			eleResult.addContent(eleIssue);
			eleResult.addContent(eleBalls);
			eleResult.addContent(eleMissing);
			eleResult.addContent(eleBingo);
			eleResult.addContent(eleWMissing);
			eleResult.addContent(eleWBingo);
			eleResult.addContent(eleQMissing);
			eleResult.addContent(eleQBingo);
			eleResult.addContent(eleBMissing);
			eleResult.addContent(eleBBingo);

			openResults.addContent(eleResult);
		}
	}

	// 组装错误信息
	public String PackageXml(String CodeEle, String message, String type, String agentId) throws Exception {
		// ----------------新建包体--------------------
		String returnContent = "";
		Element myBody = new Element("body");
		Element responseCodeEle = new Element("responseCode");
		Element responseMessage = new Element("responseMessage");
		responseCodeEle.setText(CodeEle);
		responseMessage.setText(message);
		myBody.addContent(responseCodeEle);
		myBody.addContent(responseMessage);
		returnContent = this.softwareService.toPackage(myBody, type, agentId);
		return returnContent;

	}

	public void setSoftwareService(SoftwareService softwareService) {
		this.softwareService = softwareService;
	}

	public void setSqlIsusesService(IsusesService sqlIsusesService) {
		this.sqlIsusesService = sqlIsusesService;
	}

}
