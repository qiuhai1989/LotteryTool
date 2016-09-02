package com.yuncai.modules.lottery.service.oracleService.Impl.member;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.yuncai.core.common.DbDataVerify;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.core.tools.ip.IpSeeker;
import com.yuncai.modules.lottery.action.member.MemberSearch;
import com.yuncai.modules.lottery.bean.vo.MemberInfoShowBean;
import com.yuncai.modules.lottery.dao.oracleDao.member.MemberDao;
import com.yuncai.modules.lottery.dao.oracleDao.member.MemberInfoDAO;
import com.yuncai.modules.lottery.dao.oracleDao.member.MemberOperLineDAO;
import com.yuncai.modules.lottery.dao.oracleDao.member.MemberScoreDAO;
import com.yuncai.modules.lottery.dao.oracleDao.member.MemberWalletDAO;
import com.yuncai.modules.lottery.dao.sqlDao.user.UsersDAO;
import com.yuncai.modules.lottery.model.Oracle.Member;
import com.yuncai.modules.lottery.model.Oracle.MemberInfo;
import com.yuncai.modules.lottery.model.Oracle.MemberOperLine;
import com.yuncai.modules.lottery.model.Oracle.MemberScore;
import com.yuncai.modules.lottery.model.Oracle.MemberWallet;
import com.yuncai.modules.lottery.model.Oracle.toolType.MemberOperType;
import com.yuncai.modules.lottery.model.Oracle.toolType.OperLineStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.WalletType;
import com.yuncai.modules.lottery.model.Sql.Users;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.oracleService.member.MemberService;


public class MemberServiceImpl extends BaseServiceImpl<Member, Integer> implements MemberService{
	private MemberDao memberDao;
	private MemberWalletDAO memberWalletDao;
	private MemberScoreDAO memberScoreDao;
	private IpSeeker ipSeeker;
	private MemberOperLineDAO memberOperLineDao;
	private MemberInfoDAO memberInfoDAO;
	private UsersDAO sqlUsersDAO;
	public Integer register(Member member, Long ct, String ip, String referer, String key,String channel,String version,String phoneNo) {

		Integer operLineNo = null;
//		LogUtil.out("开始注册");
		// 保存会员信息
		this.memberDao.saves(member);
		//member=this.memberDao.find(Member.class, 2);
//		LogUtil.out("保存会员基本信息成功");

		// 初始化会员钱包
		MemberWallet wallet = new MemberWallet();
		wallet.setWalletType(WalletType.DUOBAO);
		wallet.setMemberId(member.getId());
		wallet.setAccount(member.getAccount());
		wallet.setAbleBalance(BigDecimal.valueOf(new Double(0)).doubleValue());
		wallet.setFreezeBalance(BigDecimal.valueOf(new Double(0)).doubleValue());
		wallet.setHeapBalance(BigDecimal.valueOf(new Double(0)).doubleValue());
		wallet.setHeapPrize(BigDecimal.valueOf(new Double(0)).doubleValue());
		wallet.setHeapGold(BigDecimal.valueOf(new Double(0)).doubleValue());
		wallet.setVerifyMd5(DbDataVerify.getMemberWalletVerify(wallet));
		this.memberWalletDao.saves(wallet);
//		LogUtil.out("初始化会员钱包成功");

		member.setWallet(wallet);

		// 初始化会员积分
		MemberScore score = new MemberScore();
		score.setMemberId(member.getId());
		score.setAbleScore(new Integer(0));
		score.setAccount(member.getAccount());
		score.setHeapScore(new Integer(0));
		this.memberScoreDao.save(score);
//		LogUtil.out("初始化会员积分成功");

		MemberOperLine operLine = new MemberOperLine();
		operLine.setAccount(member.getAccount());
		operLine.setSourceId(member.getSourceId());
		operLine.setCreateDateTime(new Date());
		operLine.setReferer(referer);
		operLine.setUrl("");
		operLine.setIp(ip);
		operLine.setPhoneNo(phoneNo);
		String country = ipSeeker.getCountry(ip);
		String area = ipSeeker.getArea(ip);
		String fromPlace = country + area;

		operLine.setFromPlace(fromPlace);

		operLine.setTerminalId(ct);
		operLine.setOperType(MemberOperType.REGISTER);
		operLine.setStatus(OperLineStatus.SUCCESS);
		operLine.setChannel(channel);
		operLine.setExtendInfo(key);
		operLine.setVersion(version);
		memberOperLineDao.save(operLine);
		operLineNo = operLine.getOperLineNo();

		MemberInfo info = new MemberInfo();
		info.setAccount(member.getAccount());
		info.setMemberId(member.getId());
		memberInfoDAO.save(info);
		
		
		return operLineNo;
	}

	public void setSqlUsersDAO(UsersDAO sqlUsersDAO) {
		this.sqlUsersDAO = sqlUsersDAO;
	}

	public Integer update(Member member, Long ct, String ip, String referer, String key,String channel,String version) {
		Integer operLineNo = null;
//		LogUtil.out("开始更新");
		//更新会员信息
		this.memberDao.update(member);
		//member=this.memberDao.find(Member.class, 2);
//		LogUtil.out("更新会员基本信息成功");
		
		MemberOperLine operLine = new MemberOperLine();
		operLine.setAccount(member.getAccount());
		operLine.setSourceId(member.getSourceId());
		operLine.setCreateDateTime(new Date());
		operLine.setReferer(referer);
		operLine.setUrl("");
		operLine.setIp(ip);
		String country = ipSeeker.getCountry(ip);
		String area = ipSeeker.getArea(ip);
		String fromPlace = country + area;

		operLine.setFromPlace(fromPlace);

		operLine.setTerminalId(ct);
		operLine.setOperType(MemberOperType.USERUPDATE);
		operLine.setStatus(OperLineStatus.SUCCESS);
		operLine.setChannel(channel);
		operLine.setExtendInfo(key);
		operLine.setVersion(version);
		memberOperLineDao.save(operLine);
		operLineNo = operLine.getOperLineNo();
		
		return operLineNo;
	}
	
	
	public List<Member> findBySearch(MemberSearch memberSearch, int offset,int length) {
		
		 return memberDao.findBySearch(memberSearch, offset, length);
	}

	public int getCountBySearch(MemberSearch memberSearch) {
		
		return memberDao.getCountBySearch(memberSearch);
	}

	public void saves(Member transientInstance) {
		this.memberDao.saves(transientInstance);
		
	}

	/**
	 * 更新最后一次登录时间
	 * 
	 * @param id
	 */
	public void updateLastLogin(final Member member, Long ct, String ip,String channel,String version,String phoneNo) {
		MemberOperLine operLine = new MemberOperLine();
		operLine.setAccount(member.getAccount());
		operLine.setSourceId(member.getSourceId());
		operLine.setCreateDateTime(new Date());
		operLine.setExtendInfo("");
		operLine.setReferer("");
		operLine.setUrl("");
		operLine.setIp(ip);
		operLine.setVersion(version);
		operLine.setChannel(channel);
		operLine.setPhoneNo(phoneNo);
		String country = ipSeeker.getCountry(ip);
		String area = ipSeeker.getArea(ip);
		String fromPlace = country + area;

		operLine.setFromPlace(fromPlace);

		operLine.setTerminalId(ct);
		operLine.setOperType(MemberOperType.LOGIN);
		operLine.setStatus(OperLineStatus.SUCCESS);

		memberOperLineDao.save(operLine);
		
		this.memberDao.updateLastLogin(member.getId());
	}
	
	public boolean loginEMd5(String account, String password) {
		Member member=new Member();
		List list=this.memberDao.findByProperty(Member.class, "account", account);
		if(list ==null || list.size() ==0){
			return false;
		}
		member = (Member) list.get(0);
		if(!password.equals(member.getPassword())){
			return false;
		}
		if(member.getStatus() !=0){
			return false;
		}
		return true;
		
	}
	public void updateLastLogin(Integer id) {
		this.memberDao.updateLastLogin(id);
		
	}


	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}


	public void setMemberScoreDao(MemberScoreDAO memberScoreDao) {
		this.memberScoreDao = memberScoreDao;
	}


	public void setIpSeeker(IpSeeker ipSeeker) {
		this.ipSeeker = ipSeeker;
	}


	public void setMemberOperLineDao(MemberOperLineDAO memberOperLineDao) {
		this.memberOperLineDao = memberOperLineDao;
	}


	public void setMemberInfoDAO(MemberInfoDAO memberInfoDAO) {
		this.memberInfoDAO = memberInfoDAO;
	}


	public void setMemberWalletDao(MemberWalletDAO memberWalletDao) {
		this.memberWalletDao = memberWalletDao;
	}


	public Member findByAccount(Object account) {
		List list = memberDao.findByProperty(Member.class, "account", account);
		if (list == null || list.size() == 0) {
			return null;
		}
		Member member = (Member) list.get(0);
		return member;
	}

	@Override
	public MemberInfoShowBean findMemberInfoShowBeanByName(String  account) {
		// TODO Auto-generated method stub
		return memberDao.findMemberInfoShowBeanByName(account);
	}

	


}
