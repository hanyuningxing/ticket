package com.cai310.lottery.entity.lottery.ticket;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.type.EnumType;

import com.cai310.entity.IdEntity;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.ticket.common.TicketSupporter;

@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "LOTTERY_SUPPORTER")
@Entity
public class LotterySupporter extends IdEntity{
	
	private static final long serialVersionUID = 5463582835818247528L;

	/** 彩种 */
	private Lottery lotteryType;
	
	/** 出票商*/
	private TicketSupporter ticketSupporter;
	
	/** 是否启用*/
	private boolean usable;

	/**
	 * @return the lotteryType
	 */
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.Lottery"),
			@Parameter(name = EnumType.TYPE, value = SalesMode.SQL_TYPE) })
	@Column(nullable = false)
	public Lottery getLotteryType() {
		return lotteryType;
	}

	/**
	 * @param lotteryType the lotteryType to set
	 */	
	public void setLotteryType(Lottery lotteryType) {
		this.lotteryType = lotteryType;
	}

	/**
	 * @return the ticketSupporter
	 */
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.ticket.common.TicketSupporter"),
			@Parameter(name = EnumType.TYPE, value = SalesMode.SQL_TYPE) })
	@Column(nullable = false)
	public TicketSupporter getTicketSupporter() {
		return ticketSupporter;
	}

	/**
	 * @param ticketSupporter the ticketSupporter to set
	 */
	public void setTicketSupporter(TicketSupporter ticketSupporter) {
		this.ticketSupporter = ticketSupporter;
	}

	/**
	 * @return the usable
	 */
	@Column(name="usable")
	public boolean isUsable() {
		return usable;
	}

	/**
	 * @param usable the usable to set
	 */
	public void setUsable(boolean usable) {
		this.usable = usable;
	}
	
	
}
