package com.yuncai.modules.lottery.dao.oracleDao.member;
import java.util.*;

import com.yuncai.modules.lottery.action.member.MemberSearch;
import com.yuncai.modules.lottery.bean.vo.MemberInfoShowBean;
import com.yuncai.modules.lottery.dao.GenericDao;
import com.yuncai.modules.lottery.model.Oracle.Member;

public interface MemberDao extends GenericDao<Member, Integer>{
	public static final String ACCOUNT = "account";
	public static final String NAME = "name";
	public static final String CERT_TYPE = "certType";
	public static final String CERT_NO = "certNo";
	public static final String PASSWORD = "password";
	public static final String RANK = "rank";
	public static final String EMAIL = "email";
	public static final String MOBILE = "mobile";
	public static final String STATUS = "status";
	public static final String EXPRERIENCE = "exprerience";
	public static final String SOURCE_ID = "sourceId";
	public static final String RECOMMENDER = "recommender";
	public List<Member> findBySearch(final MemberSearch memberSearch, final int offset, final int length);
	public int getCountBySearch(final MemberSearch memberSearch);
	
	/**
	 * 更新最后一次登录时间
	 * 
	 * @param id
	 */
	public void updateLastLogin(final Integer id);
	
	public abstract void saves(Member transientInstance);
	public abstract void attachClean(Member instance);
	public abstract void update(Member transientInstance);

	public MemberInfoShowBean findMemberInfoShowBeanByName(final String account);


}
