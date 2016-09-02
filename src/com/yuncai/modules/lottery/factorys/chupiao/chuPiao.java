package com.yuncai.modules.lottery.factorys.chupiao;

import java.text.Format;
import java.util.List;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.modules.lottery.model.Oracle.Member;
import com.yuncai.modules.lottery.model.Oracle.Ticket;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.service.oracleService.member.MemberService;

public class chuPiao {

	private MemberService memberService;

	public String send(List<Ticket> tickets) throws Exception {
		String result = "";
		int totalmoney = 0; // 投注总金额
		for (int i = 0; i < tickets.size(); i++) {
			totalmoney += tickets.get(i).getAmount();
		}
		testBuy1.dbType = 1;
		// 省份
		String province = "";
		String gameId = TicketFormater.playTypeFormater(tickets.get(0).getLotteryType());

		Member member = memberService.findByAccount(tickets.get(0).getAccount());

		// 组装出票信息XML
		String strXML = "<ticketorder gameid='" + gameId + "' ticketsnum='" + tickets.size() + "' totalmoney='" + totalmoney + "' province='"
				+ province + "'>";
		strXML += "<user userid='" + member.getId().toString() + "' realname='" + testBuy1.getUTF8(member.getName()) + "' idcard='"
				+ member.getCertNo() + "' phone='" + member.getMobile() + "' />";
		strXML += "<tickets>";
		dataXml104 f104 = new dataXml104(testBuy1.dbType, "", "", "", "");
		for (int i = 0; i < tickets.size(); i++) {
			// 票号
			String ticketId = tickets.get(i).getId().toString();
			// 倍数
			String multiple = tickets.get(i).getMultiple().toString();
			// 期号
			String issue = tickets.get(i).getTerm();
			// 该张彩票的金额
			String moneys = tickets.get(i).getAmount().toString();
			// 是否追加
			String addflag = tickets.get(i).getAddAttribute() == null ? "0" : "1";
			// 获取转换后的号码格式与玩法
			String formatStr = null;
			if (LotteryType.JCZQList.contains(tickets.get(i).getLotteryType())) {
				formatStr = TicketFormater.JcFormater(tickets.get(i).getLotteryType(), tickets.get(i).getPlayType(), tickets.get(i).getContent());
			} else {
				formatStr = TicketFormater.LTCFormater(tickets.get(i).getLotteryType(), tickets.get(i).getPlayType(), tickets.get(i).getContent());
			}
			String content[] = formatStr.split("\\#");
			// 号码
			String ball = content[0];
			// 玩法
			String playtype = content[1];
           // realTicketID= tickets.get(0).getId().toString()
			ticketId = testBuy1.getSubmitId();
			f104.Save104(gameId, issue, tickets.size(), totalmoney, province, tickets.get(0).getId().toString(), member.getCertNo(),
					member.getName(), member.getMobile(), ticketId, Integer.parseInt(multiple), Integer.parseInt(moneys), playtype,
					Integer.parseInt(addflag), ball, "");
			f104.SaveIntf104Result(ticketId);

			strXML += "<ticket id='" + ticketId + "' multiple='" + multiple + "' issue='" + issue + "'";
			strXML += " playtype='" + playtype + "' money='" + moneys + "' addflag='" + addflag + "'>";
			strXML += "<ball>" + ball + "</ball>";
			strXML += "</ticket>";
		}

		strXML += "</tickets>";
		strXML += "</ticketorder>";

		LogUtil.out("===================开始送票======================");
		LogUtil.out("==========" + strXML + "==============");
		// 送票
		try {
			f104.doPost(testBuy1.url, strXML);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		LogUtil.out("===================送票成功======================");

		return result;
	}

	public static String getSubmitTime() {
		java.util.Calendar c = java.util.Calendar.getInstance();
		java.text.DateFormat df = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
		return df.format(c.getTime());
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

}
