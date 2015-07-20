package com.cai310.lottery.ticket.protocol.win310.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.dlt.DltPlayType;
import com.cai310.lottery.support.welfare3d.Welfare3dCompoundContent;
import com.cai310.lottery.support.welfare3d.Welfare3dPlayType;
import com.cai310.utils.JsonUtil;

public class Weflare3dWin310Util extends Win310Util {
	private static final NumberFormat ONE_NF = new DecimalFormat("0");
	public Weflare3dWin310Util(Ticket ticket) {
		super(ticket);
	}
	public Weflare3dWin310Util(){
		super();
	}
	@Override
	public String getLotCode(Ticket ticket) throws DataException{
		StringBuffer betCode = new StringBuffer();
		Welfare3dPlayType welfare3dPlayType = Welfare3dPlayType.values()[ticket.getBetType()];
		if(null==welfare3dPlayType)throw new DataException("拆票投注方式错误");
		String betType;//大赢家玩法
		if(welfare3dPlayType.equals(Welfare3dPlayType.Direct)){
			if(ticket.getUnits()==1||SalesMode.SINGLE.equals(ticket.getMode())){
				betType = "ZXDS";
			}else{
				betType = "ZXFS";
			}
		}else if(welfare3dPlayType.equals(Welfare3dPlayType.Group3)){
			if(ticket.getUnits()==1||SalesMode.SINGLE.equals(ticket.getMode())){
				betType = "ZX_DS";
			}else{
				betType = "Z3FS";
			}
		}else if(welfare3dPlayType.equals(Welfare3dPlayType.Group6)){
			if(ticket.getUnits()==1||SalesMode.SINGLE.equals(ticket.getMode())){
				betType = "ZX_DS";
			}else{
				betType = "Z6FS";
			}
		}else if(welfare3dPlayType.equals(Welfare3dPlayType.DirectSum)){
			betType = "ZXHZ";
		}else{
			throw new DataException("拆票投注方式错误");
		}
		if(SalesMode.SINGLE.equals(ticket.getMode())){
			String[] ticketArr = ticket.getContent().split(Constant.TICKET_SINGLE_SEPARATOR_FOR_UNIT);
			for (String ticketStr : ticketArr) {
				for (String num : ticketStr.split(Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER)) {
					betCode.append(Integer.valueOf(num)).append(",");
				}
				betCode = betCode.delete(betCode.length()-",".length(), betCode.length());
				betCode.append(";");
			}
		}else if(SalesMode.COMPOUND.equals(ticket.getMode())){
			String[] contents = JsonUtil.getStringArray4Json(ticket.getContent());
			for (String content : contents) {
				Welfare3dCompoundContent welfare3dCompoundContent = JsonUtil.getObject4JsonString(content,Welfare3dCompoundContent.class);
				if (ticket.getBetType() == Welfare3dPlayType.Direct.ordinal()) {// 直选复式
					betCode.append(StringUtils.join(formatBetNum(welfare3dCompoundContent.getArea1List(),ONE_NF), "")).append(",");
					betCode.append(StringUtils.join(formatBetNum(welfare3dCompoundContent.getArea2List(),ONE_NF), "")).append(",");
					betCode.append(StringUtils.join(formatBetNum(welfare3dCompoundContent.getArea3List(),ONE_NF), ""));
				}else if (ticket.getBetType() == Welfare3dPlayType.Group3.ordinal()) {// 组三复式
					betCode.append(StringUtils.join(formatBetNum(welfare3dCompoundContent.getGroup3List(),ONE_NF),","));
				}else if (ticket.getBetType() == Welfare3dPlayType.Group6.ordinal()) {
					betCode.append(StringUtils.join(formatBetNum(welfare3dCompoundContent.getGroup6List(),ONE_NF),","));
				}else if (ticket.getBetType() == Welfare3dPlayType.DirectSum.ordinal()) {
					betCode.append(StringUtils.join(formatBetNum(welfare3dCompoundContent.getDirectSumList(),ONE_NF),","));
				}else{
					throw new DataException("拆票投注方式错误");
				}
				betCode.append(";");
			}
		}else{
			throw new DataException("拆票单复式错误");
		}
		betCode = betCode.delete(betCode.length()-";".length(), betCode.length());
		StringBuffer betXML = new StringBuffer();
		String isAppend = "0";
		betXML.append("<ticket ticketId=\""+ticket.getId()+"\" betType=\""+betType+"\"  issueNumber=\""+ticket.getPeriodNumber()+"\" betUnits=\""+ticket.getUnits()+"\" multiple=\""+ticket.getMultiple()+"\" betMoney =\""+ticket.getSchemeCost()+"\" isAppend =\""+isAppend+"\">");
		betXML.append("<betContent>"+betCode.toString()+"</betContent>");
		betXML.append("</ticket>");
		return betXML.toString();
	}

}
