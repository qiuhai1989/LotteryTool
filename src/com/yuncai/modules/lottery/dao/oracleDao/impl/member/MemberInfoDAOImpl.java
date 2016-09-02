package com.yuncai.modules.lottery.dao.oracleDao.impl.member;



import java.sql.SQLException;
import java.util.ArrayList;

import com.yuncai.modules.lottery.dao.GenericDaoImpl;
import com.yuncai.modules.lottery.dao.oracleDao.member.MemberInfoDAO;
import com.yuncai.modules.lottery.model.Oracle.Member;
import com.yuncai.modules.lottery.model.Oracle.MemberInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

public class MemberInfoDAOImpl extends GenericDaoImpl<MemberInfo, Integer> implements MemberInfoDAO{
	private static final Log log = LogFactory.getLog(MemberInfoDAOImpl.class);

}
