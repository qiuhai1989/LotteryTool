package com.yuncai.modules.lottery.service.oracleService.Impl.member;

import java.util.Date;

import com.yuncai.core.hibernate.CommonStatus;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.core.tools.ip.IpSeeker;
import com.yuncai.modules.lottery.dao.oracleDao.lottery.LotteryPlanOrderDAO;
import com.yuncai.modules.lottery.dao.oracleDao.member.MemberChargeLineDAO;
import com.yuncai.modules.lottery.dao.oracleDao.member.MemberOperLineDAO;
import com.yuncai.modules.lottery.model.Oracle.Member;
import com.yuncai.modules.lottery.model.Oracle.MemberChargeLine;
import com.yuncai.modules.lottery.model.Oracle.MemberOperLine;
import com.yuncai.modules.lottery.model.Oracle.MemberWallet;
import com.yuncai.modules.lottery.model.Oracle.toolType.ChargeType;
import com.yuncai.modules.lottery.model.Oracle.toolType.MemberOperType;
import com.yuncai.modules.lottery.model.Oracle.toolType.OperLineStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.WalletTransType;
import com.yuncai.modules.lottery.model.Oracle.toolType.WalletType;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.oracleService.member.MemberChargeLineService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberWalletService;

public class MemberChargeLineServiceImpl extends BaseServiceImpl<MemberChargeLine, String> implements MemberChargeLineService {

	private MemberChargeLineDAO memberChargeLineDAO;
	private IpSeeker ipSeeker;
	private MemberOperLineDAO memberOperLineDao;
	private MemberWalletService memberWalletService;
	private MemberService memberService;

	public void setMemberChargeLineDAO(MemberChargeLineDAO memberChargeLineDAO) {
		this.memberChargeLineDAO = memberChargeLineDAO;
	}

	@Override
	public MemberChargeLine findByIdForUpdate(String id) {

		return this.memberChargeLineDAO.findByIdForUpdate(id);
	}

	@Override
	public String charger(int walletType, Double amount, String extendInfo, String chargeNo, String responseInfo, String payedNo, String ip, Long ct,
			int chargeType, double formalitiesFees,String channel) throws Exception {

		MemberChargeLine chargeLine = memberChargeLineDAO.findByIdForUpdate(chargeNo);
		ChargeType changeTypeIn = (ChargeType) ChargeType.getItem(chargeType);

//		if (chargeLine.getChargeType().getValue() != changeTypeIn.getValue() ) {// 定单金额不匹配 && amount != chargeLine.getAmount().intValue()
//			throw new Exception("充值金额有误，请联系客服！");
//		} //

		// 以数据库中记录的金额为准
		//amount = chargeLine.getAmount().intValue();
		amount = chargeLine.getAmount();
		Member member = this.memberService.findByAccount(chargeLine.getAccount());
		if (chargeLine.getStatus().getValue() == CommonStatus.NO.getValue()) {// ！！！如果不是处理成功的才处理充值

			chargeLine.setStatus(CommonStatus.YES);
			chargeLine.setSuccessDateTime(new Date());
			chargeLine.setResponseInfo(responseInfo);
			chargeLine.setPayedNo(payedNo);
			chargeLine.setChargeType(changeTypeIn);
			chargeLine.setChannel(channel);
			chargeLine.setFormalitiesFees(formalitiesFees);
			this.memberChargeLineDAO.saveOrUpdate(chargeLine);

			MemberOperLine operLine = new MemberOperLine();
			operLine.setAccount(member.getAccount());
			// LogUtil.out("account: " + member.getAccount());
			operLine.setTerminalId(ct);
			operLine.setSourceId(member.getSourceId());
			operLine.setExtendInfo(extendInfo + "");
			operLine.setCreateDateTime(new Date());
			operLine.setReferer("");
			operLine.setUrl("");
			operLine.setIp(ip);
			String country = ipSeeker.getCountry(ip);
			String area = ipSeeker.getArea(ip);
			String fromPlace = country + area;

			operLine.setFromPlace(fromPlace);
			MemberOperLine example = new MemberOperLine();
			example.setAccount(member.getAccount());
			example.setOperType(MemberOperType.ADD_MONEY);
			if (this.memberOperLineDao.findByExamble(example).size() > 0) {
				operLine.setOperType(MemberOperType.RE_ADD_MONEY);
			} else {
				operLine.setOperType(MemberOperType.ADD_MONEY);
			}

			operLine.setStatus(OperLineStatus.SUCCESS);
			memberOperLineDao.save(operLine);

			String returnStr = "";

			memberWalletService.increaseAble(member, new Double(amount), -1, 0, 0, operLine.getOperLineNo(), WalletType.DUOBAO.getValue(),
					WalletTransType.CHARGE.getValue(), member.getSourceId(), changeTypeIn.getName() + "充值");
			if (formalitiesFees > 0) {
				memberWalletService.increaseAble(member, new Double(-formalitiesFees), -1, 0, 0, null, WalletType.DUOBAO.getValue(),
						WalletTransType.DEDUCTED.getValue(), member.getSourceId(), "充值流水号：" + chargeNo + " 扣除手续费");
			}

			{
				double takeCashQuotaAmount = chargeLine.getAmount() - formalitiesFees;// 充值的钱减去手续费
				// 增加提款额度
				takeCashQuota(walletType, takeCashQuotaAmount, member);
			}
			// {
			// //处理提款
			// takeCashQuota(walletType, amount, member);
			// }

			return returnStr;
		} else {
			// 已经处理过了，直接返回，不抛错
			return "0";
		}
	}

	// 提现额度处理
	private void takeCashQuota(int walletType, double amount, Member member) {
		MemberWallet wallet = memberWalletService.findByMemberIdAndWalletTypeForUpdate(member.getId(), WalletType.getItem(walletType));
		double takeCashQuotaOffset = amount * 0.7;// 网上充值和现金充值增加充值金额的70%提现额度
		wallet.setTakeCashQuota(wallet.getTakeCashQuota() + takeCashQuotaOffset);
		memberWalletService.saveOrUpdate(wallet);
	}

	@Override
	public void saveNumber(MemberChargeLine transientInstance) {
		this.memberChargeLineDAO.saveNumber(transientInstance);

	}

	public MemberChargeLineDAO getMemberChargeLineDAO() {
		return memberChargeLineDAO;
	}

	public void setIpSeeker(IpSeeker ipSeeker) {
		this.ipSeeker = ipSeeker;
	}

	public void setMemberOperLineDao(MemberOperLineDAO memberOperLineDao) {
		this.memberOperLineDao = memberOperLineDao;
	}

	public void setMemberWalletService(MemberWalletService memberWalletService) {
		this.memberWalletService = memberWalletService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

}
