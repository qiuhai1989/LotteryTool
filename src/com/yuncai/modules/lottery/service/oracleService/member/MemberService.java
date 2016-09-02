package com.yuncai.modules.lottery.service.oracleService.member;

import java.util.List;

import com.yuncai.modules.lottery.action.member.MemberSearch;
import com.yuncai.modules.lottery.bean.vo.MemberInfoShowBean;
import com.yuncai.modules.lottery.model.Oracle.Member;
import com.yuncai.modules.lottery.service.BaseService;

public interface MemberService extends BaseService<Member, Integer>{
	public List<Member> findBySearch(final MemberSearch memberSearch, final int offset, final int length);
	public int getCountBySearch(final MemberSearch memberSearch);
	
	public boolean loginEMd5(String account,String password);
	
	public abstract Member findByAccount(Object account);
	
	
	
	/**
	 * 更新最后一次登录时间
	 * 
	 * @param id
	 */
	public void updateLastLogin(final Member member, Long ct, String ip,String channel,String version,String phoneNo);
	
	public abstract void saves(Member transientInstance);
	
	//注册
	public Integer register(Member member, Long ct, String ip, String referer, String key,String channel,String version,String phoneNo);
	//更新
	public Integer update(Member member,Long ct,String ip, String referer, String key,String channel,String version);

	public MemberInfoShowBean findMemberInfoShowBeanByName(final String account);
	
//	/**获取敏感词
//	 * @return
//	 */
//	public List<Object[]> findSensitiveKeyWords();
	
}
