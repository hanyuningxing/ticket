package com.cai310.lottery.ticket.protocol.zunao.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.tc22to5.Tc22to5CompoundContent;
import com.cai310.lottery.support.tc22to5.Tc22to5CompoundContent;
import com.cai310.utils.JsonUtil;

public class Tc22to5ZunaoUtil extends ZunaoUtil {
	private static final NumberFormat TWO_NF = new DecimalFormat("00");
	private static final String DANSPLITCODE = "$";
	private static final String UNITSPLITCODE = ";";
	private static final String NUMSPLITCODE = ",";
	public Tc22to5ZunaoUtil(Ticket ticket) {
		super(ticket);
	}
	public Tc22to5ZunaoUtil(){
		super();
	}
	@Override
	public String getLotCode(Ticket ticket) throws DataException{
		StringBuffer betCode = new StringBuffer();
		String betType = "";
		if(SalesMode.SINGLE.equals(ticket.getMode())){
			String[] ticketArr = ticket.getContent().split(Constant.TICKET_SINGLE_SEPARATOR_FOR_UNIT);
			for (String ticketStr : ticketArr) {
				String[] numArr =ticketStr.split(Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER);
				for (int i = 0; i < numArr.length; i++) {
					betCode.append(TWO_NF.format(Integer.valueOf(numArr[i]))).append(NUMSPLITCODE);
				}
				betCode = betCode.delete(betCode.length()-NUMSPLITCODE.length(), betCode.length());
				betCode.append(UNITSPLITCODE);
			}
			betType="DS";
		}else if(SalesMode.COMPOUND.equals(ticket.getMode())){
			String[] contents = JsonUtil.getStringArray4Json(ticket.getContent());
			for (String content : contents) {
				Tc22to5CompoundContent tc22to5CompoundContent = JsonUtil.getObject4JsonString(content,Tc22to5CompoundContent.class);
			    betCode.append(StringUtils.join(formatBetNum(tc22to5CompoundContent.getBetList(),TWO_NF),NUMSPLITCODE)).append(NUMSPLITCODE);
			    if(ticket.getUnits()==1){
					betType="DS";
				}else{
					betType="FS";
				}
				betCode.append(UNITSPLITCODE);
			 }
		}else{
			throw new DataException("拆票单复式错误");
		}
		betCode = betCode.delete(betCode.length()-UNITSPLITCODE.length(), betCode.length());
		StringBuffer betXML = new StringBuffer();
		betXML.append("<ticket ticketId=\""+ticket.getId()+"\" betType=\""+betType+"\"  issueNumber=\""+ticket.getPeriodNumber()+"\" betUnits=\""+ticket.getUnits()+"\" multiple=\""+ticket.getMultiple()+"\" betMoney =\""+ticket.getSchemeCost()+"\" isAppend =\"0\">");
		betXML.append("<betContent>"+betCode.toString()+"</betContent>");
		betXML.append("</ticket>");
		return betXML.toString();
	}

}
