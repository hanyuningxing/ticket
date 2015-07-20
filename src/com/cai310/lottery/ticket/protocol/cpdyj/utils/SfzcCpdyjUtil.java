package com.cai310.lottery.ticket.protocol.cpdyj.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.zc.ZcUtils;

public class SfzcCpdyjUtil extends CpdyjUtil {
	private static final NumberFormat TWO_NF = new DecimalFormat("00");
	private static final String UNITSPLITCODE = ";";
	public SfzcCpdyjUtil(Ticket ticket) {
		super(ticket);
	}
	public SfzcCpdyjUtil(){
		super();
	}

	@Override
	public String getLotIssue(Ticket ticket) {
		return ticket.getPeriodNumber();
	}

	@Override
	public String getLotCode(Ticket ticket) throws DataException{
		StringBuffer betCode = new StringBuffer();
		if(SalesMode.SINGLE.equals(ticket.getMode())){
			betCode.append(ticket.getContent().replaceAll(Constant.TICKET_SINGLE_SEPARATOR_FOR_UNIT, UNITSPLITCODE));
		}else if(SalesMode.COMPOUND.equals(ticket.getMode())){
			betCode.append(ticket.getContent().replaceAll(String.valueOf(ZcUtils.getContentSpiltCode()), ","));
		}else{
			throw new DataException("拆票单复式错误");
		}
		return betCode.toString();
	}
	@Override
	public String getAttach(Ticket ticket) {
		return "";
	}

	@Override
	public String getOneMoney(Ticket ticket) throws DataException {
		return "2";
	}

}
