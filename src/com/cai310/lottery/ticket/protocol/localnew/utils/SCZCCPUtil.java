package com.cai310.lottery.ticket.protocol.localnew.utils;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.entity.lottery.ticket.Ticket;

public class SCZCCPUtil extends CPUtil {
	/**
	 * 
	 * @param lottery
	 * @param playType
	 * @return
	 */
	private static final String UNITSPILTCODE = "+";
	private static final String SPILTCODE = ",";

	@Override
	public String getCpLotteryId(Lottery lottery, Byte betType) {
		return "10033";
	}

	@Override
	public String getCpBetType(Ticket ticket) {
		return "0";
	}

	@Override
	public String getCpPlayType(Ticket ticket) {
		if (null == ticket.getMode())
			return null;
		if (SalesMode.SINGLE.equals(ticket.getMode())) {
			return "1";
		} else if (SalesMode.COMPOUND.equals(ticket.getMode())) {
			if (ticket.getUnits() == 1) {
				return "1";
			} else {
				return "0";
			}
		}
		return null;
	}

	@Override
	public String getBetContent(Ticket ticket) {
		if (SalesMode.SINGLE.equals(ticket.getMode())) {
			StringBuffer betCode = new StringBuffer();
			String[] ticketArr = ticket.getContent().split(Constant.TICKET_SINGLE_SEPARATOR_FOR_UNIT);
			for (String ticketStr : ticketArr) {
				betCode.append(ticketStr.trim().replaceAll("#", "*"));
				betCode.append(UNITSPILTCODE);
			}
			betCode = betCode.delete(betCode.length() - UNITSPILTCODE.length(), betCode.length());
			return betCode.toString();
		} else if (SalesMode.COMPOUND.equals(ticket.getMode())) {
			if (ticket.getUnits() == 1) {
				return ticket.getContent().replaceAll("#", "*").replaceAll("-", "");
			} else {
				return ticket.getContent().replaceAll("#", "*").replaceAll("-", ",");
			}
		}
		return null;
	}

	@Override
	public String getSpecialFlag(Ticket ticket) {
		return "";
	}

	@Override
	public String getUpdatePeriodNumber(Ticket ticket) {
		return null;
	}

}
