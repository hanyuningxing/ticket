package com.cai310.lottery.ticket.protocol.win310.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.sdel11to5.SdEl11to5CompoundContent;
import com.cai310.lottery.support.sdel11to5.SdEl11to5PlayType;
import com.cai310.utils.JsonUtil;

public class SdEl11to5Win310Util extends Win310Util {
	private static final NumberFormat TWO_NF = new DecimalFormat("00");
	public SdEl11to5Win310Util(Ticket ticket) {
		super(ticket);
	}
	public SdEl11to5Win310Util(){
		super();
	}

	@Override
	public String getLotCode(Ticket ticket) throws DataException{
		StringBuffer betCode = new StringBuffer();
		SdEl11to5PlayType sdSdEl11to5PlayType = SdEl11to5PlayType.values()[ticket.getBetType()];
		if(null==sdSdEl11to5PlayType)throw new DataException("拆票投注方式错误");
		String betType;//大赢家玩法
		if(sdSdEl11to5PlayType.equals(SdEl11to5PlayType.NormalOne)){
			betType = "11_RX1";
		}else if(sdSdEl11to5PlayType.equals(SdEl11to5PlayType.RandomTwo)){
			betType = "11_RX2";
		}else if(sdSdEl11to5PlayType.equals(SdEl11to5PlayType.RandomThree)){
			betType = "11_RX3";
		}else if(sdSdEl11to5PlayType.equals(SdEl11to5PlayType.RandomFour)){
			betType = "11_RX4";
		}else if(sdSdEl11to5PlayType.equals(SdEl11to5PlayType.RandomFive)){
			betType = "11_RX5";
		}else if(sdSdEl11to5PlayType.equals(SdEl11to5PlayType.RandomSix)){
			betType = "11_RX6";
		}else if(sdSdEl11to5PlayType.equals(SdEl11to5PlayType.RandomSeven)){
			betType = "11_RX7";
		}else if(sdSdEl11to5PlayType.equals(SdEl11to5PlayType.RandomEight)){
			betType = "11_RX8";
		}else if(sdSdEl11to5PlayType.equals(SdEl11to5PlayType.ForeTwoGroup)){
			betType = "11_ZXQ2";
		}else if(sdSdEl11to5PlayType.equals(SdEl11to5PlayType.ForeThreeGroup)){
			betType = "11_ZXQ3";
		}else if(sdSdEl11to5PlayType.equals(SdEl11to5PlayType.ForeTwoDirect)){
			if(ticket.getUnits()==1||SalesMode.SINGLE.equals(ticket.getMode())){
				betType = "11_ZXQ2_D";
			}else{
				betType = "11_ZXQ2_F";
			}
		}else if(sdSdEl11to5PlayType.equals(SdEl11to5PlayType.ForeThreeDirect)){
			if(ticket.getUnits()==1||SalesMode.SINGLE.equals(ticket.getMode())){
				betType = "11_ZXQ3_D";
			}else{
				betType = "11_ZXQ3_F";
			}
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
				SdEl11to5CompoundContent sdSdEl11to5CompoundContent = JsonUtil.getObject4JsonString(content,SdEl11to5CompoundContent.class);
				if(sdSdEl11to5PlayType.equals(SdEl11to5PlayType.ForeTwoDirect)){
					betCode.append(StringUtils.join(formatBetNum(sdSdEl11to5CompoundContent.getBet1List(),TWO_NF),",")).append("|");
					betCode.append(StringUtils.join(formatBetNum(sdSdEl11to5CompoundContent.getBet2List(),TWO_NF),","));
				}else if(sdSdEl11to5PlayType.equals(SdEl11to5PlayType.ForeThreeDirect)){
					betCode.append(StringUtils.join(formatBetNum(sdSdEl11to5CompoundContent.getBet1List(),TWO_NF),",")).append("|");
					betCode.append(StringUtils.join(formatBetNum(sdSdEl11to5CompoundContent.getBet2List(),TWO_NF),",")).append("|");
					betCode.append(StringUtils.join(formatBetNum(sdSdEl11to5CompoundContent.getBet3List(),TWO_NF),","));
				}else{
					betCode.append(StringUtils.join(formatBetNum(sdSdEl11to5CompoundContent.getBetList(),TWO_NF),","));
				}
				betCode.append(";");
			}
		}else{
			throw new DataException("拆票单复式错误");
		}
		betCode = betCode.delete(betCode.length()-";".length(), betCode.length());
		StringBuffer betXML = new StringBuffer();
		betXML.append("<ticket ticketId=\""+ticket.getId()+"\" betType=\""+betType+"\"  issueNumber=\""+ticket.getPeriodNumber().substring(2,ticket.getPeriodNumber().length())+"\" betUnits=\""+ticket.getUnits()+"\" multiple=\""+ticket.getMultiple()+"\" betMoney =\""+ticket.getSchemeCost()+"\" isAppend =\"0\">");
		betXML.append("<betContent>"+betCode.toString()+"</betContent>");
		betXML.append("</ticket>");
		return betXML.toString();
	}

}
