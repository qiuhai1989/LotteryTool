package com.yuncai.modules.timer.ticket;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.yuncai.core.sporttery.TimeTools;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.modules.lottery.model.Oracle.Ticket;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Oracle.toolType.TicketStatus;
import com.yuncai.modules.lottery.service.oracleService.ticket.TicketService;

public class SongPiaoJCZQFullNetThread extends Thread {
	private TicketService ticketService;

	private List<Integer> lotteryTypeList;
	private boolean isRun = true;
	private long sleepTime = 10000;

	public void run() {
		while (true) {
			if (isRun) {
				try {
					Thread.currentThread().sleep(100);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				for (int lotteryType : lotteryTypeList) {
					LotteryType type = LotteryType.getItem(lotteryType);

					if (LotteryType.JCZQList.contains(type)) {
						if (!TimeTools.isFbInTime(new Date()))
							continue;
					}

					LogUtil.out("------------" + LotteryType.getItem(lotteryType).getName() + "  开始往全网送票------------");
					try {
						
						List<Ticket> ticketList = ticketService.findByStatusAndlotteryType(LotteryType.getItem(lotteryType), TicketStatus.WSP);
						if (ticketList.size() <= 0) {
							LogUtil.out(LotteryType.getItem(lotteryType).getName() + ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>没有查到票");
						} else {

							// 用Map对票进行分组，key：用户名，value:该用户的票集合
							Map<String, List<Ticket>> ticketMap = new HashMap<String, List<Ticket>>();
							for (int i = 0; i < ticketList.size(); i++) {
								addCount(ticketMap, ticketList.get(i).getAccount(), ticketList.get(i));
							}

							Iterator<String> it = ticketMap.keySet().iterator();
							// 遍历Map
							while (it.hasNext()) {
								// 用户名
								String account = it.next();
								// 该用户的票集合
								List<Ticket> ticketsForSend = ticketMap.get(account);
								// 出票次数（一次最多50票）
								int listCount = ticketsForSend.size() / 50 + 1;
								LogUtil.out("-------------- " + account + " 有 " + ticketsForSend.size() + " 张 "
										+ LotteryType.getItem(lotteryType).getName() + " 票，需分为 " + listCount + " 次送票======================");
								for (int i = 0; i < listCount; i++) {
									LogUtil.out("===================第 " + (i + 1) + " 次送票======================");
									if (i < (listCount - 1)) {
										ticketService.songPiaoFullNet(ticketsForSend.subList(i, i * 50));
									} else if (i == (listCount - 1)) {
										ticketService.songPiaoFullNet(ticketsForSend.subList(i, i * 50 + ticketsForSend.size() % 50));
									}
								}
							}
						}
						

					} catch (Exception e) {
						e.printStackTrace();
					}

					LogUtil.out("---------------" + LotteryType.getItem(lotteryType).getName() + "  往全网送票结束---------");

				}
				try {
					sleep(sleepTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				try {
					synchronized (this) {
						this.wait();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void addCount(Map<String, List<Ticket>> map, String account, Ticket ticket) {
		if (map.containsKey(account)) {
			(map.get(account)).add(ticket);
		} else {
			List<Ticket> tickets = new ArrayList<Ticket>();
			tickets.add(ticket);
			map.put(account, tickets);
		}
	}

	public List<Integer> getLotteryTypeList() {
		return lotteryTypeList;
	}

	public void setLotteryTypeList(List<Integer> lotteryTypeList) {
		this.lotteryTypeList = lotteryTypeList;
	}

	public boolean isRun() {
		return isRun;
	}

	public void setRun(boolean isRun) {
		this.isRun = isRun;
	}

	public long getSleepTime() {
		return sleepTime;
	}

	public void setSleepTime(long sleepTime) {
		this.sleepTime = sleepTime;
	}

	public void setTicketService(TicketService ticketService) {
		this.ticketService = ticketService;
	}
}
