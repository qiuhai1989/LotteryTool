package com.yuncai.modules.lottery.dao.oracleDao.impl.member;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.yuncai.modules.lottery.dao.GenericDaoImpl;
import com.yuncai.modules.lottery.dao.oracleDao.member.MemberDrawingDAO;
import com.yuncai.modules.lottery.model.Oracle.MemberDrawing;

public class MemberDrawingDAOImpl extends GenericDaoImpl<MemberDrawing, Integer> implements MemberDrawingDAO{
	private static final Log log = LogFactory.getLog(MemberWalletDAOImpl.class);

	public void saves(MemberDrawing transientInstance){
		log.debug("saving MemberDrawing instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	public void update(MemberDrawing transientInstance){
		log.debug("updating MemberDrawing instance");
		try {
			getHibernateTemplate().update(transientInstance);
			log.debug("update successful");
		} catch (RuntimeException re) {
			log.error("update failed", re);
			throw re;
		}
	}
}
