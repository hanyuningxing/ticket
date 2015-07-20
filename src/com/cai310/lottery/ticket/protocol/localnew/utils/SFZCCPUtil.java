package com.cai310.lottery.ticket.protocol.localnew.utils;
import com.cai310.lottery.Constant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.entity.lottery.ticket.Ticket;

public  class SFZCCPUtil extends CPUtil{
	/**
	 *  
	 * @param lottery
	 * @param playType
	 * @return
	 */
	private static final String UNITSPILTCODE = "+";
	private static final String SPILTCODE = ",";
	@Override
	public String getCpLotteryId(Lottery lottery,Byte betType){
		if(null==lottery)return null;
		if(Lottery.SFZC.equals(lottery)){
			return "10031";
		}
		return null;
	}
	@Override
	public String getCpBetType(Ticket ticket){
		if(Lottery.SFZC.equals(ticket.getLotteryType())){
			if(Byte.valueOf("0").equals(ticket.getBetType())){
				return "0";
				//14
			}else if(Byte.valueOf("1").equals(ticket.getBetType())){
				return "1";
				//9
			}
		}
		return "404";
	}
	@Override
	public String getCpPlayType(Ticket ticket){
		if(null==ticket.getMode())return null;
		if(SalesMode.SINGLE.equals(ticket.getMode())){
			return "1";
		}else if(SalesMode.COMPOUND.equals(ticket.getMode())){
			if(ticket.getUnits()==1){
				return "1";
			}else{
				return "0";
			}
		}
		return null;
	}
	@Override
	public String getBetContent(Ticket ticket){
		if(SalesMode.SINGLE.equals(ticket.getMode())){
			StringBuffer betCode = new StringBuffer();
			String[] ticketArr = ticket.getContent().split(Constant.TICKET_SINGLE_SEPARATOR_FOR_UNIT);
			for (String ticketStr : ticketArr) {
				betCode.append(ticketStr.trim().replaceAll("#", "*"));
				betCode.append(UNITSPILTCODE);
			}
			betCode = betCode.delete(betCode.length()-UNITSPILTCODE.length(), betCode.length());
			return betCode.toString();
		}else if(SalesMode.COMPOUND.equals(ticket.getMode())){
			if(ticket.getUnits()==1){
				return ticket.getContent().replaceAll("#", "*").replaceAll("-", "");
			}else{
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
