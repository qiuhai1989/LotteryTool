package com.yuncai.modules.lottery.service.oracleService.Impl.fbmatch;

import java.util.List;
import java.util.Map;

import com.yuncai.core.tools.DateTools;
import com.yuncai.modules.lottery.dao.oracleDao.fbmatch.FtImsJcDao;
import com.yuncai.modules.lottery.model.Oracle.FtImsJc;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.oracleService.fbmatch.FtImsJcService;

public class FtImsJcServiceImpl extends BaseServiceImpl<FtImsJc, Integer> implements FtImsJcService {

	private FtImsJcDao ftImsJcDao;

	public void setFtImsJcDao(FtImsJcDao ftImsJcDao) {
		this.ftImsJcDao = ftImsJcDao;
	}

	@Override
	public List<Object[]> findFtImsJc(String expect) {
		return ftImsJcDao.findFtImsJc(expect);
	}

	@Override
	public List<Object[]> findFtIms(String expect) {
		return ftImsJcDao.findFtIms(expect);
	}

	@Override
	public List<Object[]> findFtImsByIds(String jids) {
		return this.ftImsJcDao.findFtImsByIds(jids);
	}

	@Override
	public List<Map<String, Integer>> findMidAndJid(String mids) {
		return this.ftImsJcDao.findMidAndJid(mids);
	}

	@Override
	public List<Map<String, Object>> findRank(String mids) {
		return this.ftImsJcDao.findRank(mids);
	}

	@Override
	public List<Object[]> findFtIms10(String expect) {
		String startDate="";
		String endDate="";
		try {
//			startDate = DateTools.toString(DateTools.jiaOrJian(DateTools.toDate(expect), -1), "M-d")+" 10:00";
//			endDate = DateTools.toString(DateTools.toDate(expect), "M-d")+" 10:00";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.ftImsJcDao.findFtIms10(startDate, endDate);
	}

	@Override
	public String findMatchJcResult(Integer sqlId) {
		// TODO Auto-generated method stub
		return this.ftImsJcDao.findMatchJcResult(sqlId);
	}


}
