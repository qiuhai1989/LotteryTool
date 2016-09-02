package com.yuncai.modules.lottery.service.oracleService.Impl.card;

import java.util.List;

import com.yuncai.modules.lottery.dao.oracleDao.card.CardDAO;
import com.yuncai.modules.lottery.model.Oracle.Card;
import com.yuncai.modules.lottery.model.Oracle.toolType.CardStatus;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.oracleService.card.CardService;

public class CardServiceImpl extends BaseServiceImpl<Card, Integer> implements
		CardService {
	private CardDAO cardDAO;

	@Override
	public List<Object[]> cardPopulation(Integer memberId, CardStatus cardStatus) {
		// TODO Auto-generated method stub
		return cardDAO.cardPopulation(memberId, cardStatus);
	}

	public void setCardDAO(CardDAO cardDAO) {
		this.cardDAO = cardDAO;
	}
	@Override
	public List<Object[]> cardPopulationChannel(String account) {
		// TODO Auto-generated method stub
		
		return cardDAO.cardPopulationChannel(account);
	}
	@Override
	public List<Object[]> cardPopulation(String owner) {
		// TODO Auto-generated method stub
		return cardDAO.cardPopulation(owner);
	}

	public List<Object[]> validCardMenu(String account) {
		return cardDAO.validCardMenu(account);
	}

	public List<Object[]> personalCardMenu(String account) {
		return cardDAO.personalCardMenu(account);
	}

	public List<Object[]> validCardList(String account, Double amount){
		return cardDAO.validCardList(account, amount);
	}

	public List<Object[]> personalCardList(String account, Double amount){
		return cardDAO.personalCardList(account, amount);
	}
}
