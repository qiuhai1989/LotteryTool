package com.yuncai.modules.lottery.service.sqlService.user;

import java.util.List;

import com.yuncai.modules.lottery.model.Sql.SensitiveKeyWords;
import com.yuncai.modules.lottery.model.Sql.Users;
import com.yuncai.modules.lottery.service.BaseService;

public interface UsersService extends BaseService<Users, Integer> {

	public Integer findUserByAuthority(String account,String password,int competenceId1,int competenceId2,int groupId1,int groupId2);
	public Integer findMaxId();
	
	public Users findbyId(final Integer id);
	
	/**获取敏感词
	 * @return
	 */
	public List<SensitiveKeyWords> findSensitiveKeyWords();
}
