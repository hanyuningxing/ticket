package com.cai310.lottery.ticket.protocol.liangcai.utils;

import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;

public class KlpkLiangCaiUtil extends LiangCaiUtil{
	
	public KlpkLiangCaiUtil(){
		super();
	}

	public KlpkLiangCaiUtil(Ticket ticket){
		super(ticket);
	}
	
	@Override
	public String getLotIssue(Ticket ticket) {
		return null;
	}

	@Override
	public String getLotCode(Ticket ticket) throws DataException {
		return null;
	}

	@Override
	public String getAttach(Ticket ticket) {
		return null;
	}

	@Override
	public String getOneMoney(Ticket ticket) throws DataException {
		return null;
	}

}
