package com.yuncai.modules.lottery.service.oracleService.card;

import java.util.List;

import com.yuncai.modules.lottery.model.Oracle.Card;
import com.yuncai.modules.lottery.model.Oracle.toolType.CardStatus;
import com.yuncai.modules.lottery.service.BaseService;

public interface CardService extends BaseService<Card, Integer> {
	  public List<Object[]> cardPopulation (Integer memberId,CardStatus cardStatus);
	  public List<Object[]> cardPopulationChannel (String account);
	  public List<Object[]> cardPopulation (String owner);
	  
	  public List<Object[]> validCardMenu(String account);
	  public List<Object[]> personalCardMenu(String account);
	  public List<Object[]> validCardList(String account,Double amount);
	  public List<Object[]> personalCardList(String account,Double amount);

}
