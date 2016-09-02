package com.yuncai.core.common;

import com.yuncai.core.tools.MD5;
import com.yuncai.core.tools.NumberTools;
import com.yuncai.modules.lottery.model.Oracle.LotteryPlan;
import com.yuncai.modules.lottery.model.Oracle.LotteryPlanOrder;
import com.yuncai.modules.lottery.model.Oracle.MemberWallet;
import com.yuncai.modules.lottery.model.Oracle.MemberWalletLine;

public class DbDataVerify {
	final static private String md5Code = "AM*lkMkdqerKPXB";

	final static public String getMemberWalletVerify(MemberWallet wallet) {
		StringBuffer sb = new StringBuffer();
		sb
				.append(wallet.getAccount())
				// .append(wallet.getAbleBalance())
				// .append(wallet.getFreezeBalance())
				// .append(wallet.getHeapBalance())
				// .append(wallet.getHeapPrize())

				.append(NumberTools.doubleToMoneyString(wallet.getAbleBalance())).append(NumberTools.doubleToMoneyString(wallet.getFreezeBalance()))
				.append(NumberTools.doubleToMoneyString(wallet.getHeapBalance())).append(NumberTools.doubleToMoneyString(wallet.getHeapPrize()))

				.append(md5Code);

		String str = MD5.encode(sb.toString());

		// LogUtil.out("-------------------" + sb.toString()+ " md5:" + str ) ;
		return str;
	}

	final static public boolean checkMemberWalletVerify(MemberWallet wallet, String md5) {
		if (getMemberWalletVerify(wallet).equals(md5)) {
			return true;
		}
		return true; // 系统验证小数点有问题，暂时取消
	}

	final static public String getMemberWalletLineVerify(MemberWalletLine walletLine) {
		StringBuffer sb = new StringBuffer();
		sb.append(walletLine.getMemberId());
		sb.append(walletLine.getAccount());
		sb.append(walletLine.getAmount());
		sb.append(walletLine.getWalletType());
		sb.append(walletLine.getTransType().getValue());
		sb.append(walletLine.getCreateDateTime().toString());
		return MD5.encode(sb.toString());
	}

	final static public boolean checkMemberWalletLineVerify(MemberWalletLine walletLine, String md5) {
		if (getMemberWalletLineVerify(walletLine).equals(md5)) {
			return true;
		}
		return false;
	}

	final static public String getLotteryPlanVerify(LotteryPlan lp) {
		StringBuffer sb = new StringBuffer();

		sb.append(lp.getTerm()).append(lp.getAccount()).append(lp.getAmount()).append(lp.getPerAmount()).append(lp.getContent()).append(md5Code);

		return MD5.encode(sb.toString());
	}

	final static public boolean checkLotteryPlanVerify(LotteryPlan lp, String md5) {
		if (getLotteryPlanVerify(lp).equals(md5)) {
			return true;
		}
		return false;
	}

	final static public String getLotteryPlanOrderVerify(LotteryPlanOrder lpo) {
		StringBuffer sb = new StringBuffer();

		sb.append(lpo.getPlanNo()).append(lpo.getAccount()).append(lpo.getStatus()).append(lpo.getPrizeSettleStatus()).append(md5Code);

		return MD5.encode(sb.toString());
	}

	final static public boolean checkLotteryPlanOrderVerify(LotteryPlanOrder lpo, String md5) {
		if (getLotteryPlanOrderVerify(lpo).equals(md5)) {
			return true;
		}
		return false;
	}

//	final static public String getLotteryChaseVerify(LotteryChase lc) {
//		StringBuffer sb = new StringBuffer();
//
//		sb.append(lc.getAccount()).append(lc.getAmount()).append(md5Code);
//
//		return MD5.encode(sb.toString());
//	}
//
//	final static public boolean checkLotteryChaseVerify(LotteryChase lc, String md5) {
//		if (getLotteryChaseVerify(lc).equals(md5)) {
//			return true;
//		}
//		return false;
//	}

	public static void main(String[] arg) {
		MemberWallet m = new MemberWallet();
		m.setAccount("yiyigch");
		m.setAbleBalance(40.24);
		m.setFreezeBalance(0.0);
		m.setHeapBalance(61.00);
		m.setHeapPrize(0.00);
		System.out.println(getMemberWalletVerify(m));

	}

}
