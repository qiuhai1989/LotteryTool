package com.yuncai.modules.lottery.dao.oracleDao.member;
import java.util.*;

import com.yuncai.modules.lottery.action.member.WalletLineSearch;
import com.yuncai.modules.lottery.bean.search.PhoneShowBean;
import com.yuncai.modules.lottery.dao.GenericDao;
import com.yuncai.modules.lottery.model.Oracle.MemberScoreLine;
import com.yuncai.modules.lottery.model.Oracle.toolType.ScoreType;

public interface MemberScoreLineDAO extends GenericDao<MemberScoreLine, Integer>{
	public static final String MEMBER_ID = "memberId";
	public static final String ACCOUNT = "account";
	public static final String VALUE = "value";
	public static final String SCORE_TYPE = "scoreType";

	public List<MemberScoreLine> findScoreLineByAccount(final String account, final int offset, final int length);
	public List<MemberScoreLine> findScoreLineByAccount(final PhoneShowBean bean,final Date startDate,final Date endDate,final int offset,final int pagesize);

	public List<MemberScoreLine> findByAccountAndScoreType(final String account, final ScoreType scoreType);

	public List findBySearch(final WalletLineSearch search, final int offset, final int length); // 积分查询

	public int getCountBySearch(final WalletLineSearch search);
	
	public int getCountByScoreSearch(final WalletLineSearch search, final int countAmountBase);

	public int getCountByByAccount(final String account);

	public abstract void attachClean(MemberScoreLine instance);

	public abstract Integer getRecordCount(final Map<String, Object> parameterMap, final Date startDate, final Date endDate);

	public abstract Long getSumScoreValue(Map<String, Object> parameterMap, Date startDate, Date endDate);


}
