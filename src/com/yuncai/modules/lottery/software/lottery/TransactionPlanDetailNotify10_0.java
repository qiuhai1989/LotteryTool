package com.yuncai.modules.lottery.software.lottery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.aspectj.weaver.World;
import org.jdom.Element;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.yuncai.core.common.Constant;
import com.yuncai.core.tools.ArithUtil;
import com.yuncai.core.tools.CompressFile;
import com.yuncai.core.tools.DateTools;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.core.tools.NumberTools;
import com.yuncai.core.tools.StringTools;
import com.yuncai.core.util.Strings;
import com.yuncai.modules.lottery.bean.vo.HmShowBean;
import com.yuncai.modules.lottery.model.Oracle.BkImsMatch500;
import com.yuncai.modules.lottery.model.Oracle.BkMatch;
import com.yuncai.modules.lottery.model.Oracle.BkSpRate;
import com.yuncai.modules.lottery.model.Oracle.FtImsJc;
import com.yuncai.modules.lottery.model.Oracle.FtImsSfc;
import com.yuncai.modules.lottery.model.Oracle.LotteryPlanOrder;
import com.yuncai.modules.lottery.model.Oracle.Member;
import com.yuncai.modules.lottery.model.Oracle.WorldCupTeam;
import com.yuncai.modules.lottery.model.Oracle.toolType.BuyType;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.WinStatus;
import com.yuncai.modules.lottery.model.Sql.Isuses;
import com.yuncai.modules.lottery.model.Sql.Match;
import com.yuncai.modules.lottery.model.Sql.PassRate;
import com.yuncai.modules.lottery.sporttery.OptionItem;
import com.yuncai.modules.lottery.sporttery.support.CommonUtils;
import com.yuncai.modules.lottery.sporttery.support.MatchItem;
import com.yuncai.modules.lottery.sporttery.support.MatchItemTurbid;
import com.yuncai.modules.lottery.sporttery.support.SportteryOption;
import com.yuncai.modules.lottery.sporttery.support.basketball.BasketBallMatchItem;

public class TransactionPlanDetailNotify10_0 extends TransactionPlanDetailNotify {

	@Override
	public String execute(Element bodyEle, String agentId) {
		// TODO Auto-generated method stub
		HttpServletRequest request = ServletActionContext.getRequest();
		Element toPlanDetail = bodyEle.getChild("toPlanDetail");

		String message = "组装节点错误!";

		String planNo = "";
		String userName = null;
		// -------------------新建xml包体--------------------------
		Element myBody = new Element("body");
		Element responseCodeEle = new Element("responseCode"); // 返回值
		Element responseMessage = new Element("responseMessage");
		Element planDetail = new Element("planDetail");

		Element planNoEle = new Element("planNo");
		Element lotteryId = new Element("lotteryId");
		Element term = new Element("term");
		Element totalPart = new Element("totalPart");
		Element planOrderStatus = new Element("planOrderStatus");
		Element winStatus = new Element("winStatus");
		Element posttaxPrize = new Element("posttaxPrize");
		Element createTime = new Element("createTime");
		Element planType = new Element("planType");
		Element orderNo = new Element("orderNo");
		Element content = new Element("content");
		Element winNum = new Element("winNum");
		Element multiple = new Element("multiple");
		Element selectType = new Element("selectType");
		Element belongAccount = new Element("belongAccount");
		Element commission = new Element("commission");
		Element totalAmount = new Element("totalAmount");
		Element title = new Element("title");
		Element descript = new Element("descript");

		Element planStatus = new Element("planStatus");

		Element nickName = new Element("nickName");

		Element participates = new Element("participates");

		Element tickets = new Element("tickets");

		Element peoples = new Element("peoples");

		Element matchesEle = new Element("matches");

		Element sfcMatches = new Element("sfcMatches");

		Element championTeams = new Element("championTeams");
		
		Element pretaxPrize = new Element("pretaxPrize");
		Element isPublic = new Element("isPublic");
		Element passTypes = new Element("passTypes");
		// -------------------------------------------------

		try {

			planNo = toPlanDetail.getChildText("planNo");
			userName = toPlanDetail.getChildText("userName");

			message = null;

			// 验证登陆
			Member memberSession = (Member) request.getSession().getAttribute(Constant.MEMBER_LOGIN_SESSION);
			if (memberSession == null) {
				return PackageXml("2000", "请登录!", "9300", agentId);
			}
			if (!userName.equals(memberSession.getAccount())) {
				return PackageXml("9001", "用户名与Cokie用户不匹配", "9300", agentId);
			}

			HmShowBean bean = lotteryPlanOrderService.getHmShowBeanByPlanOrderNo(planNo);
			//对大乐透乱序内容进行排序 
			if(bean.getLotteryType().getValue()==39){
				String tempStr=bean.getContent();
				tempStr=sortDLTContent(tempStr,bean.getPlayType().getValue());
				bean.setContent(tempStr);
			}
			
			// 获取某一方案参与人
			Integer nums = lotteryPlanOrderService.findInPeople(bean.getPlanNo()).size();

			// 获取该方案的对应订单 及对应的用户昵称
			List<Object[]> objs = lotteryPlanOrderService.findHMListNickNameByPlanNoAndBuyType(bean.getPlanNo(), null);

			for (Object[] obj : objs) {
				LotteryPlanOrder order = (LotteryPlanOrder) obj[0];

				// 以下情况保底订单不可见
				// 判断是否是保底订单
				if (order.getBuyType().equals(BuyType.BAODI)) {

					// 判断 方案招募中
					if (bean.getPlanStatus().equals(PlanStatus.RECRUITING)) {
						continue;
					} else if (bean.getPlanStatus().getValue() >= PlanStatus.PAY_FINISH.getValue()) {
						// 如果已退款
						if (order.getStatus().getValue() == 3) {
							continue;
						}

					}

				}

				Element people = new Element("people");
				Element pNickName = new Element("pNickName");
				Element pPart = new Element("pPart");
				Element pAmount = new Element("pAmount");
				Element percent = new Element("percent");
				Element pTime = new Element("pTime");
				Element account = new Element("account");

				pNickName.setText(Strings.encryptionStrPhone((String)obj[1]) );
				pPart.setText(Integer.toString(order.getPart()));
				pAmount.setText(Integer.toString(order.getAmount()));
				percent.setText(NumberTools.doubleToPercent(ArithUtil.div(order.getPart(), bean.getTotalPart(), 2), "0%"));
				pTime.setText(DateTools.dateToString(order.getCreateDateTime()));

				people.addContent(pNickName);
				people.addContent(pPart);
				people.addContent(pAmount);
				people.addContent(percent);
				people.addContent(pTime);
				people.addContent(account);
				peoples.addContent(people);
			}

			bean.setParticipates(nums);
			if (bean == null) {
				message = "没有相应的记录";
			}
			planNoEle.setText(Integer.toString(bean.getPlanNo()));
			String contentHTML = getContentList(bean);
			//如果是冠军玩法则屏蔽掉方案内容
			if(!LotteryType.JCSJBList.contains(bean.getLotteryType())){
				// 方案内容进行编码
				byte[] compressed = CompressFile.compress(contentHTML);
				String contentStr = Base64.encode(compressed);
				content.setText(contentStr);
			}else{
				content.setText("");
			}


			term.setText(bean.getTerm());
			totalPart.setText(bean.getAmount() + "");
			planOrderStatus.setText(Integer.toString(bean.getPlanOrderStatus().getValue()));
			winStatus.setText(Integer.toString(bean.getWinStatus().getValue()));
			posttaxPrize.setText(bean.getPosttaxPrize() + "");
			createTime.setText(DateTools.dateToString(bean.getCreateDateTime()));
			planType.setText(bean.getPlantype().getValue() + "");
			orderNo.setText(bean.getOrderNo() + "");
			pretaxPrize.setText(Double.toString(bean.getPretaxPrize()));

			// 设定一标识在客户端绝对是否显示投注信息 0不公开显示1公开显示
			int flag = 0;

			// 以下代码 根据条件判断公开情况
			switch (bean.getPublicStatus().getValue()) {
			// 公开
			case 0:
				flag = 1;
				break;
			// 截止后公开
			case 1:


				if(bean.getLateDateTime()!=null){

					
					if (bean.getLateDateTime().before(new Date())) {
						flag = 1;
					} else {
						flag = 0;
					}
					
				}else{
					if (bean.getDealDateTime().before(new Date())) {
						flag = 1;
					} else {
						flag = 0;
					}
				}
				
				
				break;
			// 开奖后公开
			case 2:
				// LogUtil.out(bean.getWinStatus().getValue());
				if (bean.getWinStatus().getValue() > 1) {
					flag = 1;
				} else {
					flag = 0;
				}
				break;
			// 不公开
			case 3:
				flag = 0;
				break;

			default:
				flag = 0;
				break;
			}
			if (LotteryType.JCZQList.contains(bean.getLotteryType()) || LotteryType.JCLQList.contains(bean.getLotteryType())||LotteryType.JCSJBList.contains(bean.getLotteryType())) {
				// 投注串
				String passTypess = getMatchPassTypes(bean.getLotteryType(), bean.getContent());
				// System.out.println(passTypess);
				passTypes.setText(passTypess);
			} else {
				passTypes.setText("");
			}

			// 如果是竞彩
			if (LotteryType.JCZQList.contains(bean.getLotteryType())) {
				// 投注的内容
				List<MatchItemTurbid> matchItemList = getMatchItemsList(bean.getLotteryType(), bean.getContent());
				// 获取投注的比赛id List<MatchItem> matchItemList 中item.id
				List<Integer> ids = new ArrayList<Integer>();
				for (int i = 0; i < matchItemList.size(); i++) {
					ids.add(matchItemList.get(i).getMatchId());
				}

				List<Match> matches = new ArrayList<Match>();
				List<PassRate> passRates = new ArrayList<PassRate>();
				List<Object[]> matchAndPassRates = matchService.findMatchAndPassRate(ids);
				for (int i = 0; i < matchAndPassRates.size(); i++) {
					Object[] obj = matchAndPassRates.get(i);
					matches.add((Match) obj[0]);
					passRates.add((PassRate) obj[1]);
				}
				// 判断是否已经开奖取消根据开奖状态判断
				if (bean.getWinStatus().getValue() >= WinStatus.NOT_RESULT.getValue()) {
					String matchResult = matchResult(matches, bean.getLotteryType(), matchItemList);
					// 如果存在null 即有比赛结果未更新不予以显示
					if (matchResult.indexOf("null") > 0) {

					} else {
						bean.setWinNum(matchResult);
						winNum.setText(bean.getWinNum());
					}

				}

				for (int i = 0; i < matches.size(); i++) {
					Match match = matches.get(i);
					PassRate passRate = passRates.get(i);
					// 保证matches 与matchItemList顺序一致

					MatchItemTurbid item = null;
					for (MatchItemTurbid item2 : matchItemList) {
						if (item2.getMatchId().equals(match.getId())) {
							item = item2;
						}
					}

					List<SportteryOption> optionList = item.getOptions();
					String selectValue = "";
					StringBuffer sbf_select = new StringBuffer("");

					SportteryOption tempOption = null;// 中间变量
					// 如果是混投
					if (bean.getLotteryType().getValue() == LotteryType.JC_ZQ_HT.getValue()) {
						for (int j = 0; j < optionList.size(); j++) {
							SportteryOption option = optionList.get(j);
							OptionItem optionItem = CommonUtils.getByPlayType(bean.getLotteryType(), option);
							String optionText = optionItem.getText();
							// 如果是混投
							if (bean.getLotteryType().getValue() == LotteryType.JC_ZQ_HT.getValue()) {
								optionText = changeFTColor(option, optionItem, match);
							}

							// 如果本次选项玩法与上次不同
							if (tempOption == null) {
								sbf_select.append(lotteryTypeMap.get(option.getType()) == null ? "" : lotteryTypeMap.get(option.getType())).append(
										"[" + optionText + ",");
							} else if (tempOption != null && !tempOption.getType().equals(option.getType())) {
								sbf_select.deleteCharAt(sbf_select.length() - 1);
								sbf_select.append("]<br>");
								sbf_select.append(lotteryTypeMap.get(option.getType()) == null ? "" : lotteryTypeMap.get(option.getType())).append(
										"[" + optionText + ",");

							} else {
								sbf_select.append(optionText + ",");
							}
							tempOption = option;
						}
						sbf_select.deleteCharAt(sbf_select.length() - 1);
						sbf_select.append("]");
						selectValue = sbf_select.toString();
						// selectValue = getJCZQHTSelectValue(optionList, bean,
						// match);
					} else {
						for (int j = 0; j < optionList.size(); j++) {
							SportteryOption option = optionList.get(j);
							OptionItem optionItem = CommonUtils.getByPlayType(bean.getLotteryType(), option);
							selectValue += optionItem.getText() + ",";
						}
						selectValue = selectValue.substring(0, selectValue.length() - 1);
					}

					// System.out.println(selectValue);
					Element matchEle = new Element("matche");

					Element matchNumber = new Element("matchNumber");
					matchNumber.setText("" + match.getMatchNumber());

					Element game = new Element("game");
					game.setText(match.getGame());

					Element vs = new Element("vs");
					// 处理一下主客队名过长问题
					match.setGuestTeam(teamNameIntercept(match.getGuestTeam(), NAMELENGTH));
					match.setMainTeam(teamNameIntercept(match.getMainTeam(), NAMELENGTH));

					// 如果是竞彩让球胜平负特殊处理
					if (bean.getLotteryType().getValue() == LotteryType.JC_ZQ_RQSPF.getValue()
							|| bean.getLotteryType().getValue() == LotteryType.JC_ZQ_HT.getValue()) {
						// 混投
						if (bean.getLotteryType().getValue() == LotteryType.JC_ZQ_HT.getValue()) {
							if (passRate.getMainLoseball() > 0) {
								vs.setText(match.getMainTeam() + "<br>" + "<font color='blue'>[+" + passRate.getMainLoseball() + "]</font>" + "<br>"
										+ match.getGuestTeam());
							} else if (passRate.getMainLoseball() < 0) {
								vs.setText(match.getMainTeam() + "<br>" + "<font color='red'>[" + passRate.getMainLoseball() + "]</font>" + "<br>"
										+ match.getGuestTeam());
							}
						} else {
							// 让球
							if (passRate.getMainLoseball() > 0) {
								vs.setText(match.getMainTeam() + "<br>" + "<font color='blue'>[+" + passRate.getMainLoseball() + "]</font>" + "<br>"
										+ match.getGuestTeam());
							} else {
								vs.setText(match.getMainTeam() + "<br>" + "<font color='red'>[" + passRate.getMainLoseball() + "]</font>" + "<br>"
										+ match.getGuestTeam());
							}
						}

					} else {
						// 不让球
						vs.setText(match.getMainTeam() + "<br>" + "vs" + "<br>" + match.getGuestTeam());
					}

					Element stopSellingTime = new Element("stopSellingTime");
					stopSellingTime.setText(DateTools.dateToString(match.getMatchDate()));

					Element shedan = new Element("shedan");
					if (item.isShedan()) {
						shedan.setText("是");
					} else {

					}
					// 如果不是混投
					if (bean.getLotteryType().getValue() != LotteryType.JC_ZQ_HT.getValue()) {
						selectValue = getWinStr(selectValue, match, bean.getLotteryType(), matchItemList);
					}

					// LogUtil.out("selectValue="+selectValue);
					Element contentEle = new Element("content");
					contentEle.setText(selectValue);

					Element result = new Element("result");
					// 有赛果且不存在负分的情况下
					if (match.getResult() != null && match.getResult().indexOf("-") == -1) {
						result.setText(match.getResult());
					} else {
						// 如果无赛果采用及时比分赛果
						String jcResult = ftImsJcService.findMatchJcResult(match.getId());
						if (jcResult != null) {
							result.setText(jcResult);
						} else {
							result.setText("");
						}
					}
					// //比赛取消的情况
					if (match.getResult() != null && match.getResult().trim().equalsIgnoreCase("-1:-1")) {
						// result.setText("<font color='red'>" + "取消" +
						// "</font>");
						result.setText("取消");
					}
					if (match.getResult() != null && match.getResult().trim().indexOf("null") >= 0) {
						// result.setText("<font color='red'>" + "取消" +
						// "</font>");
						result.setText("");
					}
					matchEle.addContent(matchNumber);
					matchEle.addContent(game);
					matchEle.addContent(vs);
					matchEle.addContent(stopSellingTime);
					matchEle.addContent(shedan);
					matchEle.addContent(contentEle);
					matchEle.addContent(result);
					matchesEle.addContent(matchEle);
				}

			} else if (LotteryType.JCLQList.contains(bean.getLotteryType())) {
				// 竞彩篮球

				// 投注的内容
				List<BasketBallMatchItem> matchItemList = getBKMatchItemsList(bean.getLotteryType(), bean.getContent());
				// 投注内容中大小分 map 以initTime+""+lineId为key
				Map<String, String> dxBasic = getBKMatchDXBasic(matchItemList);

				// 获取投注的比赛id List<MatchItem> matchItemList 中item.id
				List<Integer> ids = new ArrayList<Integer>();
				List<Integer> intTimes = new ArrayList<Integer>();
				List<String> lineIds = new ArrayList<String>();
				for (int i = 0; i < matchItemList.size(); i++) {
					// ids.add(matchItemList.get(i).getMatchId());
					intTimes.add(matchItemList.get(i).getIntTime());
				}

				List<BkMatch> matches = new ArrayList<BkMatch>();
				List<BkSpRate> passRates = new ArrayList<BkSpRate>();

				// -----------------------------------------------------------------------
				// 二号方案
				List<Object[]> matchAndPassRates = bkMatchService.findMatchAndPassRateByIntTimes(intTimes);

				for (int i = 0; i < matchItemList.size(); i++) {
					BasketBallMatchItem item = matchItemList.get(i);
					for (int j = 0; j < matchAndPassRates.size(); j++) {
						Object[] obj = matchAndPassRates.get(j);
						BkMatch match = (BkMatch) obj[0];
						BkSpRate rate = (BkSpRate) obj[1];
						if (item.getLineId().equals(Integer.parseInt(match.getLineId())) && item.getIntTime().equals(match.getIntTime())) {
							matches.add(match);
							passRates.add(rate);
						}
					}

				}

				// -------------------------------------------------------------------------
				// 判断是否已经开奖取消根据开奖状态判断
				if (bean.getWinStatus().getValue() >= WinStatus.NOT_RESULT.getValue()) {
					// 跳过待处理
					String matchResult = matchBKResult(matches, bean.getLotteryType(), matchItemList);
					// 如果存在null 即有比赛结果未更新不予以显示
					if (matchResult.indexOf("null") > 0) {

					} else {
						bean.setWinNum(matchResult);

						winNum.setText(bean.getWinNum());
					}

				}

				for (int i = 0; i < matches.size(); i++) {
					BkMatch match = matches.get(i);
					// BkSpRate passRate = passRates.get(i);
					// 保证matches 与matchItemList顺序一致

					BasketBallMatchItem item = null;
					for (BasketBallMatchItem item2 : matchItemList) {
						// 匹配item 与match 匹配条件 initime lineId
						if (item2.getLineId().equals(Integer.parseInt(match.getLineId())) && item2.getIntTime().equals(match.getIntTime())) {
							item = item2;
						}
					}

					List<SportteryOption> optionList = item.getOptions();
					String selectValue = "";
					StringBuffer sbf_select = new StringBuffer();
					SportteryOption tempOption = null;
					if (bean.getLotteryType().getValue() == LotteryType.JC_LQ_HT.getValue()) {
						for (int j = 0; j < optionList.size(); j++) {
							SportteryOption option = optionList.get(j);
							OptionItem optionItem = CommonUtils.getByPlayType(bean.getLotteryType(), option);
							String optionText = optionItem.getCommonText();
							optionText = changeBKColor(option, optionItem, match);

							if (tempOption == null) {
								// 如果上次选项为空
								sbf_select.append(lotteryTypeMap.get(option.getType()) == null ? "" : lotteryTypeMap.get(option.getType())).append(
										"[" + optionText + ",");
							}
							// 如果本次选项玩法与上次不同
							else if (tempOption != null && !tempOption.getType().equals(option.getType())) {
								sbf_select.deleteCharAt(sbf_select.length() - 1);
								sbf_select.append("]<br>");
								sbf_select.append(lotteryTypeMap.get(option.getType()) == null ? "" : lotteryTypeMap.get(option.getType())).append(
										"[" + optionText + ",");

							} else {
								// selectValue += optionItem.getCommonText() +
								// ",";
								sbf_select.append(optionText + ",");
							}
							tempOption = option;
							sbf_select.deleteCharAt(sbf_select.length() - 1);
							// sbf_select.append("]");
							if (j == optionList.size() - 1) {
								// 当最后一次遍历
								sbf_select.append("]");
							} else {
								sbf_select.append(",");
							}

						}
						selectValue = sbf_select.toString();
						selectValue = HtSFCSelectContentHandlig(selectValue);
						// LogUtil.out(selectValue);
					} else {
						for (int j = 0; j < optionList.size(); j++) {
							SportteryOption option = optionList.get(j);
							OptionItem optionItem = CommonUtils.getByPlayType(bean.getLotteryType(), option);
							selectValue += optionItem.getCommonText() + ",";
						}
						selectValue = selectValue.substring(0, selectValue.length() - 1);
					}

					Element matchEle = new Element("matche");

					Element matchNumber = new Element("matchNumber");
					matchNumber.setText("" + match.getMatchNo());

					Element game = new Element("game");
					game.setText(match.getLeagueName());

					Element vs = new Element("vs");
					// 处理一下主客队名过长问题
					match.setTgName(teamNameIntercept(match.getTgName(), NAMELENGTH));
					match.setMbName(teamNameIntercept(match.getMbName(), NAMELENGTH));

					// 如果是竞彩篮球让分胜平负特殊处理
					if (bean.getLotteryType().getValue() == LotteryType.JC_LQ_RFSF.getValue()) {
						// 让球
						if (Double.parseDouble(dxBasic.get(item.getIntTime() + "rf" + item.getLineId())) < 0) {
							vs.setText(match.getTgName() + "<br>" + "<font color='red'>[" + dxBasic.get(item.getIntTime() + "rf" + item.getLineId())
									+ "]</font>" + "<br>" + match.getMbName());
						} else if (Double.parseDouble(dxBasic.get(item.getIntTime() + "rf" + item.getLineId())) > 0) {
							vs.setText(match.getTgName() + "<br>" + "<font color='blue'>[+"
									+ dxBasic.get(item.getIntTime() + "rf" + item.getLineId()) + "]</font>" + "<br>" + match.getMbName());
						}
					} else if (bean.getLotteryType().getValue() == LotteryType.JC_LQ_DXF.getValue()) {
						// 大小分
						vs.setText(match.getTgName() + "<br>" + "<font color='#ff8821'>["
								+ dxBasic.get(match.getIntTime() + "dx" + match.getLineId()) + "]</font>" + "<br>" + match.getMbName());
					} else if (bean.getLotteryType().getValue() == LotteryType.JC_LQ_HT.getValue()) {
						// 混投

						StringBuffer sbf = new StringBuffer("");

						sbf.append(match.getTgName() + "<br>");
						sbf.append("[");
						sbf.append("<font color='#ff8821'>" + dxBasic.get(match.getIntTime() + "dx" + match.getLineId()) + "</font>");
						sbf.append("|");
						if (Double.parseDouble(dxBasic.get(item.getIntTime() + "rf" + item.getLineId())) > 0) {
							sbf.append("<font color='blue'>+" + dxBasic.get(item.getIntTime() + "rf" + item.getLineId()) + "</font>");
						} else if (Double.parseDouble(dxBasic.get(item.getIntTime() + "rf" + item.getLineId())) < 0) {
							sbf.append("<font color='red'>" + dxBasic.get(item.getIntTime() + "rf" + item.getLineId()) + "</font>");
						}
						sbf.append("]<br>");
						sbf.append(match.getMbName());
						// LogUtil.out(sbf.toString());
						vs.setText(sbf.toString());
					} else {
						// 
						vs.setText(match.getTgName() + "<br>" + "vs" + "<br>" + match.getMbName());
					}

					Element stopSellingTime = new Element("stopSellingTime");
					stopSellingTime.setText(DateTools.dateToString(match.getStartTime()));

					Element shedan = new Element("shedan");
					if (item.isShedan()) {
						shedan.setText("是");
					} else {
						shedan.setText("");
					}
					selectValue = getBkWinStr(selectValue, match, bean.getLotteryType());
					Element contentEle = new Element("content");
					contentEle.setText(selectValue);
					List<BkImsMatch500> bkImsList = bkImsMatch500Service.findByPropertys(new String[] { "status", "mid" }, new Object[] { 11,
							match.getMid() });
					BkImsMatch500 bkIms = bkImsList.size() > 0 ? bkImsList.get(0) : null;

					Element result = new Element("result");
					// 有赛果且不存在负分的情况下
					if ((match.getMbScore() != null && match.getMbScore() > 0) && (match.getTgScore() != null && match.getTgScore() > 0)) {
						// LogUtil.out("官方：" + match.getMbName() + "---" +
						// match.getTgName());
						result.setText(match.getTgScore() + ":" + match.getMbScore());
					}
					// 即时比分状态为11结束状态且1至4节比分不为空情况下
					else if (bkIms != null && bkIms.getTgs1() != null && bkIms.getTgs2() != null && bkIms.getTgs3() != null
							&& bkIms.getTgs4() != null && bkIms.getMbs1() != null && bkIms.getMbs2() != null && bkIms.getMbs3() != null
							&& bkIms.getMbs4() != null) {
						LogUtil
								.out((Integer.parseInt(bkIms.getTgs1()) + Integer.parseInt(bkIms.getTgs2()) + Integer.parseInt(bkIms.getTgs3()) + Integer
										.parseInt(bkIms.getTgs4()))
										+ ":"
										+ (Integer.parseInt(bkIms.getMbs1()) + Integer.parseInt(bkIms.getMbs2()) + Integer.parseInt(bkIms.getMbs3()) + Integer
												.parseInt(bkIms.getMbs4())));
						LogUtil.out(bkIms.getSmbName() + "---" + bkIms.getStgName());
						result
								.setText((Integer.parseInt(bkIms.getTgs1()) + Integer.parseInt(bkIms.getTgs2()) + Integer.parseInt(bkIms.getTgs3()) + Integer
										.parseInt(bkIms.getTgs4()))
										+ ":"
										+ (Integer.parseInt(bkIms.getMbs1()) + Integer.parseInt(bkIms.getMbs2()) + Integer.parseInt(bkIms.getMbs3()) + Integer
												.parseInt(bkIms.getMbs4())));
					}

					else {
						result.setText("");
					}
					// //比赛取消的情况
					// if(match.getResult()!=null&&match.getResult().trim().equalsIgnoreCase("-1:-1")){
					// // result.setText("<font color='red'>" + "取消" +
					// "</font>");
					// result.setText("取消");
					// }

					matchEle.addContent(matchNumber);
					matchEle.addContent(game);
					matchEle.addContent(vs);
					matchEle.addContent(stopSellingTime);
					matchEle.addContent(shedan);
					matchEle.addContent(contentEle);
					matchEle.addContent(result);
					matchesEle.addContent(matchEle);
				}

			} else if ((bean.getLotteryType().getValue() == LotteryType.ZCSFC.getValue())
					|| (bean.getLotteryType().getValue() == LotteryType.ZCRJC.getValue())) {
				// 任9 胜负彩
				Map<String, Integer> map = new HashMap<String, Integer>();
				map.put("74", 0);
				map.put("75", 0);
				// map.put("2", 2);
				// map.put("15", 1);
				// 老足彩
				List<FtImsSfc> fts = ftImsSfcService.findFtImsSfc(bean.getTerm(), map.get(bean.getLotteryType().getValue() + ""));
				if (fts.size() == 0) {

				} else {

					int cSize = 0;
					if (bean.getLotteryType().getValue() == 74 || bean.getLotteryType().getValue() == 75) {
						cSize = 14;
					} else if (bean.getLotteryType().getValue() == 2) {
						cSize = 8;
					} else {
						cSize = 12;
					}

					String[] pCon = getSfcContent(bean.getContent(), cSize);
					// Map<String,String>pMap = new HashMap<String, String>();

					// 判断是否开奖
					boolean isopen = bean.getWinStatus().getValue() > 1;
					try {
						// LogUtil.out(fts.size()+"-----");
						for (int i = 0; i < fts.size(); i++) {
							// LogUtil.out("i="+i);
							FtImsSfc ftObj = fts.get(i);
							Element matchEle = new Element("match");
							Element no = new Element("no");
							Element startTime = new Element("startTime");
							Element mainTeam = new Element("mainTeam");
							Element guestTeam = new Element("guestTeam");
							Element contentEle = new Element("content");
							Element bifen = new Element("bifen");
							Element result = new Element("result");

							no.setText(ftObj.getCc() + "");
							startTime.setText(ftObj.getStartTime());
							mainTeam.setText(ftObj.getMbName());
							guestTeam.setText(ftObj.getTgName());

							contentEle.setText(pCon[ftObj.getCc() - 1]);
							// LogUtil.out(ftObj.getMbInball() + ":" +
							// ftObj.getTgInball());
							if (!ftObj.getStatus().equals("4") || StringTools.isNullOrBlank(ftObj.getMbInball())
									|| StringTools.isNullOrBlank(ftObj.getTgInball())) {
								bifen.setText("-");
							} else {
								bifen.setText(ftObj.getMbInball() + ":" + ftObj.getTgInball());
							}

							// 如果已经开奖
							if (isopen && ftObj.getMbInball() != null && ftObj.getTgInball() != null) {
								//
								String temp = "0";
								if (Integer.parseInt(ftObj.getMbInball()) > Integer.parseInt(ftObj.getTgInball())) {
									temp = "3";
								} else if (Integer.parseInt(ftObj.getMbInball()) == Integer.parseInt(ftObj.getTgInball())) {
									temp = "1";
								} else {
									temp = "0";
								}
								result.setText(temp);
								// 比较变色问题
								contentEle.setText(pCon[ftObj.getCc() - 1].replace(temp, "<font color='red'>" + temp + "</font>"));

							} else {
								result.setText("");
							}
							matchEle.addContent(no);
							matchEle.addContent(startTime);
							matchEle.addContent(mainTeam);
							matchEle.addContent(guestTeam);
							matchEle.addContent(contentEle);
							matchEle.addContent(bifen);
							matchEle.addContent(result);
							sfcMatches.addContent(matchEle);
						}
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}

				}

			}
			//冠军玩法
			else if(LotteryType.JCSJBList.contains(bean.getLotteryType())){
				// 投注的内容
				List<MatchItemTurbid> matchItemList = getChampionMatchItemsList(bean.getLotteryType(), bean.getContent());
//				LogUtil.out(matchItemList.size());
				List<String> ids = new ArrayList<String>();
				for (int i = 0; i < matchItemList.size(); i++) {
					ids.add(matchItemList.get(i).getMatchId()+"");
				}
				List<WorldCupTeam> teams = worldCupTeamService.findTeamsByIds(ids);
				//因为WorldCupTeam teamNum为String所以存在从数据库查出来后未按teamNum排序问题
				Collections.sort(teams, new Comparator<WorldCupTeam>(){

					@Override
					public int compare(WorldCupTeam o1, WorldCupTeam o2) {
						// TODO Auto-generated method stub
						if (Integer.parseInt(o1.getTeamNum()) > Integer.parseInt(o2.getTeamNum()) ) {
							return 1;
						} else {
							return 0;
						}
					}
					
				});
				for(int i=0;i<teams.size();i++){
					
					Element teamEle = new Element("team");
					Element teamNum = new Element("team");
					Element competitionType = new Element("team");
					Element teamName = new Element("team");
					Element saleStatus = new Element("team");
					
					WorldCupTeam team = teams.get(i);
					
					teamNum.setText(team.getTeamNum());
					competitionType.setText(team.getCompetitionType().equals("1")?"世界杯冠军":"");
					teamName.setText(team.getTeamName());
					saleStatus.setText(team.getSaleStatus().trim().equals("1")?"否":"是");
					
					teamEle.addContent(teamNum);
					teamEle.addContent(competitionType);
					teamEle.addContent(teamName);
					teamEle.addContent(saleStatus);

					championTeams.addContent(teamEle);


				}
				Isuses isuses = sqlIsusesService.findByProperty("lotteryId", LotteryType.JCSJBGJ.getValue()).get(0);
				//开奖结果暂时屏蔽
				/*				if(isuses!=null&&(isuses.getWinLotteryNumber()!=null||!(isuses.getWinLotteryNumber().indexOf("null") > 0))){
					
					WorldCupTeam winTeam = worldCupTeamService.findObjectByProperty(WorldCupTeam.TEAMNUM, isuses.getWinLotteryNumber());
					
					winNum.setText(winTeam.getTeamName() + ":"+winTeam.getBouns());
				}*/
				
				
			}

			else {
				Isuses isuses = (Isuses) sqlIsusesService.findByPropertys(new String[] { "name", "lotteryId" },
						new Object[] { bean.getTerm(), bean.getLotteryType().getValue() }).get(0);
				// 非竞彩
				winNum.setText(isuses.getWinLotteryNumber() + "");
			}

			lotteryId.setText(Integer.toString(bean.getLotteryType().getValue()));

			multiple.setText(bean.getMultiple() + "");
			selectType.setText(bean.getSelectType().getValue() + "");
			belongAccount.setText(bean.getBelongAccount());
			commission.setText(bean.getCommision() + "");
			totalAmount.setText(bean.getTotalAmount() + "");
			title.setText(bean.getTitle());
			descript.setText(bean.getDescript());
			planStatus.setText(bean.getPlanStatus().getValue() + "");
			participates.setText(bean.getParticipates() + "");
			isPublic.setText(Integer.toString(flag));

			Member member = memberService.findByProperty("account", bean.getBelongAccount()).get(0);
			if (member.getNickName() == null) {
				nickName.setText(member.getAccount());
			} else {
				nickName.setText(Strings.encryptionStrPhone(member.getNickName()) );
			}
			
			planDetail.addContent(planNoEle);
			planDetail.addContent(lotteryId);
			planDetail.addContent(term);
			planDetail.addContent(totalPart);
			planDetail.addContent(planOrderStatus);
			planDetail.addContent(winStatus);
			planDetail.addContent(posttaxPrize);
			planDetail.addContent(createTime);
			planDetail.addContent(planType);
			planDetail.addContent(orderNo);
			planDetail.addContent(content);
			planDetail.addContent(winNum);
			planDetail.addContent(multiple);
			planDetail.addContent(selectType);
			planDetail.addContent(belongAccount);
			planDetail.addContent(commission);
			planDetail.addContent(totalAmount);
			planDetail.addContent(title);
			planDetail.addContent(descript);
			planDetail.addContent(planStatus);
			planDetail.addContent(nickName);
			planDetail.addContent(participates);
			planDetail.addContent(isPublic);
			planDetail.addContent(peoples);

			planDetail.addContent(matchesEle);
			planDetail.addContent(sfcMatches);
			planDetail.addContent(championTeams);
			
			planDetail.addContent(pretaxPrize);
			planDetail.addContent(passTypes);

			responseCodeEle.setText("0");
			responseMessage.setText("处理成功！");
			myBody.addContent(responseCodeEle);
			myBody.addContent(responseMessage);
			myBody.addContent(planDetail);
			return softwareService.toPackage(myBody, "9041", agentId);

		} catch (Exception e) {
			e.printStackTrace();
			try {
				String code = "";
				if (message != null) {
					code = message;
				} else {
					code = "查询处理中或许存在异常";
				}
				return PackageXml("3002", code, "9005", agentId);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		return null;
	}

	/**
	 * 获取竞彩足球 选择内容（方案投注内容）
	 */
	public String getJCZQHTSelectValue(List<SportteryOption> optionList, HmShowBean bean, Match match) {
		// List<SportteryOption> optionList = item.getOptions();
		String selectValue = "";
		StringBuffer sbf_select = new StringBuffer("");
		SportteryOption tempOption = null;// 中间变量

		for (int j = 0; j < optionList.size(); j++) {
			SportteryOption option = optionList.get(j);
			OptionItem optionItem = CommonUtils.getByPlayType(bean.getLotteryType(), option);
			String optionText = optionItem.getText();
			// 如果是混投
			if (bean.getLotteryType().getValue() == LotteryType.JC_ZQ_HT.getValue()) {
				optionText = changeFTColor(option, optionItem, match);
			}

			// 如果本次选项玩法与上次不同
			if (tempOption == null) {
				sbf_select.append(lotteryTypeMap.get(option.getType()) == null ? "" : lotteryTypeMap.get(option.getType())).append(
						"[" + optionText + ",");
			} else if (tempOption != null && !tempOption.getType().equals(option.getType())) {
				sbf_select.deleteCharAt(sbf_select.length() - 1);
				sbf_select.append("]<br>");
				sbf_select.append(lotteryTypeMap.get(option.getType()) == null ? "" : lotteryTypeMap.get(option.getType())).append(
						"[" + optionText + ",");

			} else {
				sbf_select.append(optionText + ",");
			}
			tempOption = option;
		}
		sbf_select.deleteCharAt(sbf_select.length() - 1);
		sbf_select.append("]");
		selectValue = sbf_select.toString();

		return selectValue;
	}

}
