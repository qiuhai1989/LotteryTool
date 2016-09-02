package com.yuncai.modules.lottery.service.sqlService.impl.user;

import java.util.List;

import com.yuncai.modules.lottery.dao.sqlDao.user.UsersDAO;
import com.yuncai.modules.lottery.model.Sql.SensitiveKeyWords;
import com.yuncai.modules.lottery.model.Sql.Users;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.sqlService.user.UsersService;

public class UsersServiceImpl extends BaseServiceImpl<Users, Integer> implements UsersService {
	
	private UsersDAO sqlUsersDAO;

	public void setSqlUsersDAO(UsersDAO sqlUsersDAO) {
		this.sqlUsersDAO = sqlUsersDAO;
	}

	@Override
	public Integer findUserByAuthority(String account, String password, int competenceId1, int competenceId2, int groupId1, int groupId2) {
		return sqlUsersDAO.findUserByAuthority(account, password, competenceId1, competenceId2, groupId1, groupId2);
	}

	@Override
	public Integer findMaxId() {
		// TODO Auto-generated method stub
		return sqlUsersDAO.findMaxId();
	}

	@Override
	public Users findbyId(Integer id) {
		
		return null;
	}

	@Override
	public List<SensitiveKeyWords> findSensitiveKeyWords() {
		// TODO Auto-generated method stub
		
		return sqlUsersDAO.findSensitiveKeyWords();
	}

	

}
