package com.cai310.lottery.ticket.protocol.local.utils;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.entity.lottery.ticket.Ticket;

public  class SCZCCPUtil extends CPUtil{
	/**
	 *  
	 * @param lottery
	 * @param playType
	 * @return
	 */
	private static final String SINGSPILTCODE = "+";
	private static final String COMPOUNDSPILTCODE = ",";
	@Override
	public String getCpLotteryId(Lottery lottery,Byte betType){
		return "5";
	}
	@Override
	public String getCpBetType(Ticket ticket){
		return "0";
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
			String content = ticket.getContent();
			content = content.replaceAll("#", "*");
			return content;
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
