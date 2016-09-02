package com.yuncai.modules.lottery.dao.oracleDao.card;

import java.util.List;

import com.yuncai.modules.lottery.dao.GenericDao;
import com.yuncai.modules.lottery.model.Oracle.Card;
import com.yuncai.modules.lottery.model.Oracle.toolType.CardStatus;

public interface CardDAO  extends GenericDao<Card, Integer>{
  /**某面额彩金券 数量统计列表
 * @param memberId
 * @param cardStatus
 * @return
 */
public List<Object[]> cardPopulation (Integer memberId,CardStatus cardStatus);



public List<Object[]> cardPopulationChannel (String account);


public List<Object[]> cardPopulation (String owner);


/**彩金卡菜单（渠道卡）
 * 
 * @param account
 * @return
 */
public List<Object[]> validCardMenu(String account);


/**彩金卡列表（渠道卡）
 * 
 * @param account
 * @return
 */
public List<Object[]> validCardList(String account,Double amount);

/**彩金卡菜单（自用卡）
 * 
 * @param account
 * @return
 */
public List<Object[]> personalCardMenu(String account);

/**彩金卡列表（自用卡）
 * 
 * @param account
 * @return
 */
public List<Object[]> personalCardList(String account,Double amount);
}
