package com.yuncai.modules.lottery.service.sqlService.impl.user;

import java.util.List;

import com.yuncai.modules.lottery.dao.sqlDao.user.BankDetailDAO;
import com.yuncai.modules.lottery.model.Sql.BankDetail;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.sqlService.user.BankDetailService;

public class BankDetailServiceImpl extends BaseServiceImpl<BankDetail, Integer> implements BankDetailService {
	private BankDetailDAO bankDetailDAO;
	@Override
	public List<String> getBankNames(String province, String city, String bank) {
		// TODO Auto-generated method stub
		return bankDetailDAO.getBankNames(province, city, bank);
	}

	@Override
	public List<String> getBankTypeNames() {
		// TODO Auto-generated method stub
		return bankDetailDAO.getBankTypeNames();
	}

	@Override
	public List<String> getCityNames(String provinceName) {
		// TODO Auto-generated method stub
		return bankDetailDAO.getCityNames(provinceName);
	}

	@Override
	public List<String> getProvoinces() {
		// TODO Auto-generated method stub
		return bankDetailDAO.getProvoinces();
	}

	public void setBankDetailDAO(BankDetailDAO bankDetailDAO) {
		this.bankDetailDAO = bankDetailDAO;
	}

}
