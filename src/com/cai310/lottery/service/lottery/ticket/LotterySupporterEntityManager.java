package com.cai310.lottery.service.lottery.ticket;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.ticket.LotterySupporter;
import com.cai310.lottery.ticket.common.TicketSupporter;

public interface LotterySupporterEntityManager {

	public LotterySupporter getLotterySupporter(Long id);

	public LotterySupporter saveLotterySupporter(LotterySupporter entity);

	public LotterySupporter updateLotterySupporter(LotterySupporter entity);

	public void saveLotterySupporters(List<LotterySupporter> lotterySupporterList);

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<LotterySupporter> findLotterySupporter();

	/**
	 * 根据出票商查找开启的彩种
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<LotterySupporter> findLotteryBySupporter(
			final TicketSupporter ticketSupporter);

	/**
	 * 根据彩种查找出票商
	 * @return
	 */
	public LotterySupporter findSupporterByLottery(final Lottery lotteryType);

}