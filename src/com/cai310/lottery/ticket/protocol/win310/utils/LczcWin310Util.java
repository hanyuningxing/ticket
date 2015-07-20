package com.cai310.lottery.ticket.protocol.win310.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.zc.ZcUtils;

public class LczcWin310Util extends Win310Util {
	private static final String UNITSPLITCODE = ";";
	public LczcWin310Util(Ticket ticket) {
		super(ticket);
	}
	public LczcWin310Util(){
		super();
	}
	@Override
	public String getLotCode(Ticket ticket) throws DataException{
//		ticket.setPeriodNumber("2014032");
		String betType = "";
		String playType=getPlayType(ticket);
		String content;
		betType="0";
		if(SalesMode.SINGLE.equals(ticket.getMode())){
			 content=ticket.getContent();
		}else if(SalesMode.COMPOUND.equals(ticket.getMode())){
			if(ticket.getUnits()==1){
				content=ticket.getContent().replaceAll("-", "");
			}else{
				content=ticket.getContent();
			}
		}else{
			throw new DataException("拆票单复式错误");
		}
		StringBuffer betXML = new StringBuffer();
		betXML.append("<ticket ticketId=\""+ticket.getId()+"\" betType=\""+betType+"\"  playType=\""
				+playType+"\" schemeNum=\""+ticket.getSchemeNumber()+"\"  issueNumber=\""+ticket.getPeriodNumber()+"\" betUnits=\""
				+ticket.getUnits()+"\"   multiple=\""+ticket.getMultiple()+"\" betCost =\""
				+ticket.getSchemeCost()+"\" betTime=\""+System.currentTimeMillis()+"\">");
		betXML.append("<betContent>"+content+"</betContent>");
		betXML.append("</ticket>");
		return betXML.toString();
		
	}
	public String getPlayType(Ticket ticket){
		if(SalesMode.SINGLE.equals(ticket.getMode())){
			return "1";
		}else if(SalesMode.COMPOUND.equals(ticket.getMode())){
			if(ticket.getUnits()==1){
				return "1";
			}
			return "0";
		}else{
			return null;
		}
	}
}
