package com.yuncai.modules.lottery.service.oracleService.Impl.easy;

import java.util.List;

import com.yuncai.modules.lottery.dao.oracleDao.easy.EasyLotteryRecommendDao;
import com.yuncai.modules.lottery.model.Oracle.EasyLotteryRecommend;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.oracleService.easy.EasyLotteryRecommendService;

public class EasyLotteryRecommendServiceImpl extends BaseServiceImpl<EasyLotteryRecommend, Integer> implements EasyLotteryRecommendService {
	private EasyLotteryRecommendDao easyLotteryRecommendDao;

	@Override
	public List<Object[]> findRecommendFtInformation() {
		// TODO Auto-generated method stub
		return easyLotteryRecommendDao.findRecommendFtInformation();
	}



	@Override
	public List<Object[]> findRecommendBkInformation() {
		// TODO Auto-generated method stub
		return easyLotteryRecommendDao.findRecommendBkInformation();
	}
	
	public EasyLotteryRecommendDao getEasyLotteryRecommendDao() {
		return easyLotteryRecommendDao;
	}

	public void setEasyLotteryRecommendDao(EasyLotteryRecommendDao easyLotteryRecommendDao) {
		this.easyLotteryRecommendDao = easyLotteryRecommendDao;
	}



	@Override
	public Integer findMinPos(int gtype) {
		// TODO Auto-generated method stub
		return this.easyLotteryRecommendDao.findMinPos(gtype);
	}

}
