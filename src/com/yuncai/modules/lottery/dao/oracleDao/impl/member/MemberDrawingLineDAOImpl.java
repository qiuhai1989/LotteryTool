package com.yuncai.modules.lottery.dao.oracleDao.impl.member;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuncai.modules.lottery.dao.GenericDaoImpl;
import com.yuncai.modules.lottery.dao.oracleDao.member.MemberDrawingLineDAO;
import com.yuncai.modules.lottery.model.Oracle.MemberDrawing;
import com.yuncai.modules.lottery.model.Oracle.MemberDrawingLine;

public class MemberDrawingLineDAOImpl extends GenericDaoImpl<MemberDrawingLine, Integer> implements MemberDrawingLineDAO{
	private static final Log log = LogFactory.getLog(MemberWalletDAOImpl.class);
	
	public void saves(MemberDrawingLine transientInstance){
		log.debug("saving MemberDrawingLine instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	public void update(MemberDrawingLine transientInstance){
		log.debug("updating MemberDrawingLine instance");
		try {
			getHibernateTemplate().update(transientInstance);
			log.debug("update successful");
		} catch (RuntimeException re) {
			log.error("update failed", re);
			throw re;
		}
	}
}
