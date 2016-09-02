package com.yuncai.modules.lottery.service.sqlService.user;

import java.util.List;

import com.yuncai.modules.lottery.model.Sql.BankDetail;
import com.yuncai.modules.lottery.service.BaseService;

public interface BankDetailService extends BaseService<BankDetail, Integer>{
	/**获取省份列表
	 * @return
	 */
	public List<String>getProvoinces();
	
	/**获取城市列表
	 * @param provinceName
	 * @return
	 */
	public List<String>getCityNames(String provinceName);
	
	/**获取银行类型列表
	 * @return
	 */
	public List<String>getBankTypeNames();
	
	/**获取支行列表
	 * @return
	 */
	public List<String>getBankNames(String province,String city,String bank);
}
