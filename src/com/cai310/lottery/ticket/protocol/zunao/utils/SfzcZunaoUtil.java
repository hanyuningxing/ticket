package com.cai310.lottery.ticket.protocol.zunao.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.dlt.DltPlayType;
import com.cai310.lottery.support.zc.ZcUtils;

public class SfzcZunaoUtil extends ZunaoUtil {
	private static final String UNITSPLITCODE = ";";
	public SfzcZunaoUtil(Ticket ticket) {
		super(ticket);
	}
	public SfzcZunaoUtil(){
		super();
	}
	@Override
	public String getLotCode(Ticket ticket) throws DataException{
		String betType = "";
		StringBuffer betCode = new StringBuffer();
		if(SalesMode.SINGLE.equals(ticket.getMode())){
			betType="DS";
			String[] ticketArr = ticket.getContent().split(Constant.TICKET_SINGLE_SEPARATOR_FOR_UNIT);
			for (String line : ticketArr) {
				if (line.length() != ZcUtils.getMatchCount(ticket.getLotteryType()))
					throw new DataException("单式选项不符");
				char bet;// 投注内容及结果存放变量
				for (int i = 0; i < line.length(); i++) {
					bet = line.charAt(i);
					if(ZcUtils.getSfzc9NoSelectedCode()==bet){
						betCode.append("-").append(",");
					}else{
						betCode.append(bet).append(",");
					}
				}
				betCode = betCode.delete(betCode.length()-",".length(), betCode.length());
				betCode.append(UNITSPLITCODE);
			}
			betCode = betCode.delete(betCode.length()-UNITSPLITCODE.length(), betCode.length());
		}else if(SalesMode.COMPOUND.equals(ticket.getMode())){
			if(ticket.getUnits()==1){
		    	betType="DS";
		    }else{
			    betType="FS";
		    }
			betCode.append(ticket.getContent().replaceAll(String.valueOf(ZcUtils.getContentSpiltCode()), ",").replaceAll(""+ZcUtils.getSfzc9NoSelectedCode(), "-"));
		}else{
			throw new DataException("拆票单复式错误");
		}
		StringBuffer betXML = new StringBuffer();
		betXML.append("<ticket ticketId=\""+ticket.getId()+"\" betType=\""+betType+"\"  issueNumber=\""+ticket.getPeriodNumber()+"\" betUnits=\""+ticket.getUnits()+"\" multiple=\""+ticket.getMultiple()+"\" betMoney =\""+ticket.getSchemeCost()+"\" isAppend =\"0\">");
		betXML.append("<betContent>"+betCode.toString()+"</betContent>");
		betXML.append("</ticket>");
		return betXML.toString();
	}
}
