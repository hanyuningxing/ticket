package com.cai310.lottery.ticket.protocol.zunao.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.dlt.DltPlayType;
import com.cai310.lottery.support.pl.PlCompoundContent;
import com.cai310.lottery.support.pl.PlPlayType;
import com.cai310.utils.JsonUtil;

public class PlZunaoUtil extends ZunaoUtil {
	private static final NumberFormat ONE_NF = new DecimalFormat("0");
	public PlZunaoUtil(Ticket ticket) {
		super(ticket);
	}
	public PlZunaoUtil(){
		super();
	}
	@Override
	public String getLotCode(Ticket ticket) throws DataException{
		StringBuffer betCode = new StringBuffer();
		PlPlayType plPlayType = PlPlayType.values()[ticket.getBetType()];
		if(null==plPlayType)throw new DataException("拆票投注方式错误");
		String betType;//大赢家玩法
		if(plPlayType.equals(PlPlayType.P3Direct)){
			if(ticket.getUnits()==1||SalesMode.SINGLE.equals(ticket.getMode())){
				betType = "ZXDS";
			}else{
				betType = "ZXFS";
			}
		}else if(plPlayType.equals(PlPlayType.Group3)){
			if(ticket.getUnits()==1||SalesMode.SINGLE.equals(ticket.getMode())){
				betType = "ZX_DS";
			}else{
				betType = "ZXZ3";
			}
		}else if(plPlayType.equals(PlPlayType.Group6)){
			if(ticket.getUnits()==1||SalesMode.SINGLE.equals(ticket.getMode())){
				betType = "ZX_DS";
			}else{
				betType = "ZXZ6";
			}
		}else if(plPlayType.equals(PlPlayType.P5Direct)){
			if(ticket.getUnits()==1||SalesMode.SINGLE.equals(ticket.getMode())){
				betType = "DS";
			}else{
				betType = "FS";
			}
		}else if(plPlayType.equals(PlPlayType.DirectSum)){
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
				PlCompoundContent plCompoundContent = JsonUtil.getObject4JsonString(content,PlCompoundContent.class);
				if (ticket.getBetType() == PlPlayType.P5Direct.ordinal()) {// 直选复式
					betCode.append(StringUtils.join(formatBetNum(plCompoundContent.getArea1List(),ONE_NF), "")).append(",");
					betCode.append(StringUtils.join(formatBetNum(plCompoundContent.getArea2List(),ONE_NF), "")).append(",");
					betCode.append(StringUtils.join(formatBetNum(plCompoundContent.getArea3List(),ONE_NF), "")).append(",");
					betCode.append(StringUtils.join(formatBetNum(plCompoundContent.getArea4List(),ONE_NF), "")).append(",");
					betCode.append(StringUtils.join(formatBetNum(plCompoundContent.getArea5List(),ONE_NF), ""));
				}else if (ticket.getBetType() == PlPlayType.P3Direct.ordinal()) {// 直选复式
					betCode.append(StringUtils.join(formatBetNum(plCompoundContent.getArea1List(),ONE_NF), "")).append(",");
					betCode.append(StringUtils.join(formatBetNum(plCompoundContent.getArea2List(),ONE_NF), "")).append(",");
					betCode.append(StringUtils.join(formatBetNum(plCompoundContent.getArea3List(),ONE_NF), ""));
				}else if (ticket.getBetType() == PlPlayType.Group3.ordinal()) {// 组三复式
					betCode.append(StringUtils.join(formatBetNum(plCompoundContent.getGroup3List(),ONE_NF),","));
				}else if (ticket.getBetType() == PlPlayType.Group6.ordinal()) {
					betCode.append(StringUtils.join(formatBetNum(plCompoundContent.getGroup6List(),ONE_NF),","));
				}else if (ticket.getBetType() == PlPlayType.DirectSum.ordinal()) {
					betCode.append(StringUtils.join(formatBetNum(plCompoundContent.getDirectSumList(),ONE_NF),","));
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
