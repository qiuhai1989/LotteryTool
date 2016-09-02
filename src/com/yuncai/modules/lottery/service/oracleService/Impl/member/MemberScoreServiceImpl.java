package com.yuncai.modules.lottery.service.oracleService.Impl.member;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.yuncai.core.longExce.ServiceException;
import com.yuncai.modules.lottery.dao.oracleDao.member.MemberScoreDAO;
import com.yuncai.modules.lottery.dao.oracleDao.member.MemberScoreLineDAO;
import com.yuncai.modules.lottery.model.Oracle.Member;
import com.yuncai.modules.lottery.model.Oracle.MemberScore;
import com.yuncai.modules.lottery.model.Oracle.MemberScoreLine;
import com.yuncai.modules.lottery.model.Oracle.toolType.ScoreType;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.oracleService.member.MemberScoreService;

public class MemberScoreServiceImpl extends
		BaseServiceImpl<MemberScore, Integer> implements MemberScoreService {

	private MemberScoreDAO memberScoreDao;
	private MemberScoreLineDAO memberScoreLineDao;

	public MemberScore findByAccount(Object account) {
		List list = memberScoreDao.findByProperty(MemberScore.class, "account",
				account);
		if (list == null || list.size() == 0) {
			return null;
		}
		MemberScore score = (MemberScore) list.get(0);
		return score;
	}

	public void increaseScore(Member member, Integer value, Integer scoreType,
			Integer planNo, Integer relatedUserId, String remark)
			throws ServiceException {
		MemberScore memberScore = new MemberScore();
		memberScore = memberScoreDao.findByMemberId(member.getId());
		BigDecimal scoreDec = BigDecimal.valueOf(memberScore.getAbleScore());
		BigDecimal valueDec = BigDecimal.valueOf(value);
		BigDecimal heapDec = BigDecimal.valueOf(memberScore.getHeapScore());

		switch (scoreType) {
		case 1://购买彩票赠送积分
			memberScore.setAbleScore(scoreDec.add(valueDec).intValue());
			memberScore.setHeapScore(heapDec.add(valueDec).intValue());
			break;
		case 2://退款时扣除积分
			if(value>memberScore.getAbleScore()){
				throw new ServiceException("2001", "对不起，超过限额!");
			}
			memberScore.setAbleScore(scoreDec.subtract(valueDec).intValue());
			memberScore.setHeapScore(heapDec.subtract(valueDec).intValue());
			break;
		case 3://积分兑换，扣除积分
		case 4://积分购买套餐，扣除积分
			if(value>memberScore.getAbleScore()){
				throw new ServiceException("2001", "对不起，超过限额!");
			}	
			memberScore.setAbleScore(scoreDec.subtract(valueDec).intValue());
			break;
		default:
			throw new ServiceException("2001", "操作类型出错!");	
		}

		memberScoreDao.update(memberScore);
		
		//保存积分操作记录
		MemberScoreLine memberScoreLine = new MemberScoreLine();
		memberScoreLine.setAccount(member.getAccount());
		memberScoreLine.setAbleScore(memberScore.getAbleScore());
		memberScoreLine.setMemberId(member.getId());
		memberScoreLine.setValue(value);
		memberScoreLine.setScoreType(ScoreType.getItem(scoreType.intValue()));
		memberScoreLine.setCreateDateTime(new Date());
		memberScoreLine.setRemark(remark);
		memberScoreLine.setPlanNo(planNo);
		memberScoreLine.setRelatedUserId(relatedUserId);
		memberScoreLineDao.save(memberScoreLine);
	}

	public void setMemberScoreLineDao(MemberScoreLineDAO memberScoreLineDao) {
		this.memberScoreLineDao = memberScoreLineDao;
	}

	public void setMemberScoreDao(MemberScoreDAO memberScoreDao) {
		this.memberScoreDao = memberScoreDao;
	}
}
