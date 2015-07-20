package com.cai310.lottery.ticket.protocol.win310.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.ssc.SscCompoundContent;
import com.cai310.lottery.support.ssc.SscPlayType;
import com.cai310.utils.JsonUtil;

public class SscWin310Util extends Win310Util {
	private static final NumberFormat ONE_NF = new DecimalFormat("0");
	public SscWin310Util(Ticket ticket) {
		super(ticket);
	}
	public SscWin310Util(){
		super();
	}

	@Override
	public String getLotCode(Ticket ticket) throws DataException{
		StringBuffer betCode = new StringBuffer();
		SscPlayType sscPlayType = SscPlayType.values()[ticket.getBetType()];
		if(null==sscPlayType)throw new DataException("拆票投注方式错误");
		String betType = null;//大赢家玩法
		String ruleStr;//大赢家规则
		if(sscPlayType.equals(SscPlayType.DirectOne)){
			betType = "ZQSSC_1X_DS";
			if(SalesMode.SINGLE.equals(ticket.getMode())){
				String[] ticketArr = ticket.getContent().split(Constant.TICKET_SINGLE_SEPARATOR_FOR_UNIT);
				for (String ticketStr : ticketArr) {
					betCode.append(ticketStr);
					betCode.append(";");
				}
			}else{
				throw new DataException("拆票单复式错误");
			}
		}else if(sscPlayType.equals(SscPlayType.DirectTwo)){
			if(SalesMode.SINGLE.equals(ticket.getMode())){
				betType = "ZQSSC_2X_DS";
				String[] ticketArr = ticket.getContent().split(Constant.TICKET_SINGLE_SEPARATOR_FOR_UNIT);
				for (String ticketStr : ticketArr) {
					for (String num : ticketStr.split(Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER)) {
						betCode.append(""+Integer.valueOf(num));
					}
					betCode.append(";");
				}
			}else if(SalesMode.COMPOUND.equals(ticket.getMode())){
				if(ticket.getUnits()==1){
					betType = "ZQSSC_2X_DS";
					String[] contents = JsonUtil.getStringArray4Json(ticket.getContent());
					for (String content : contents) {
						SscCompoundContent sscCompoundContent = JsonUtil.getObject4JsonString(content,SscCompoundContent.class);
						betCode.append(StringUtils.join(formatBetNum(sscCompoundContent.getArea4List(),ONE_NF),""));
						betCode.append(StringUtils.join(formatBetNum(sscCompoundContent.getArea5List(),ONE_NF),""));
						betCode.append(";");
					}
				}else{
					betType = "ZQSSC_2X_FS";
					String[] contents = JsonUtil.getStringArray4Json(ticket.getContent());
					for (String content : contents) {
						SscCompoundContent sscCompoundContent = JsonUtil.getObject4JsonString(content,SscCompoundContent.class);
						betCode.append(StringUtils.join(formatBetNum(sscCompoundContent.getArea4List(),ONE_NF),"")).append(",");
						betCode.append(StringUtils.join(formatBetNum(sscCompoundContent.getArea5List(),ONE_NF),""));
						betCode.append(";");
					}
				}
			}else{
				throw new DataException("拆票单复式错误");
			}
		}else if(sscPlayType.equals(SscPlayType.DirectThree)){
			if(SalesMode.SINGLE.equals(ticket.getMode())){
				String[] ticketArr = ticket.getContent().split(Constant.TICKET_SINGLE_SEPARATOR_FOR_UNIT);
				betType = "ZQSSC_3X_DS";
				for (String ticketStr : ticketArr) {
					for (String num : ticketStr.split(Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER)) {
						betCode.append(""+Integer.valueOf(num));
					}
					betCode.append(";");
				}
			}else if(SalesMode.COMPOUND.equals(ticket.getMode())){
				if(ticket.getUnits()==1){
					betType = "ZQSSC_3X_DS";
					String[] contents = JsonUtil.getStringArray4Json(ticket.getContent());
					for (String content : contents) {
						SscCompoundContent sscCompoundContent = JsonUtil.getObject4JsonString(content,SscCompoundContent.class);
						betCode.append(StringUtils.join(formatBetNum(sscCompoundContent.getArea3List(),ONE_NF),""));
						betCode.append(StringUtils.join(formatBetNum(sscCompoundContent.getArea4List(),ONE_NF),""));
						betCode.append(StringUtils.join(formatBetNum(sscCompoundContent.getArea5List(),ONE_NF),""));
						betCode.append(";");
					}
				}else{
					betType = "ZQSSC_3X_FS";
					String[] contents = JsonUtil.getStringArray4Json(ticket.getContent());
					for (String content : contents) {
						SscCompoundContent sscCompoundContent = JsonUtil.getObject4JsonString(content,SscCompoundContent.class);
						betCode.append(StringUtils.join(formatBetNum(sscCompoundContent.getArea3List(),ONE_NF),"")).append(",");
						betCode.append(StringUtils.join(formatBetNum(sscCompoundContent.getArea4List(),ONE_NF),"")).append(",");
						betCode.append(StringUtils.join(formatBetNum(sscCompoundContent.getArea5List(),ONE_NF),""));
						betCode.append(";");
					}
				}
				
			}else{
				throw new DataException("拆票单复式错误");
			}
		}else if(sscPlayType.equals(SscPlayType.DirectFive)||sscPlayType.equals(SscPlayType.AllFive)){
			if(SalesMode.SINGLE.equals(ticket.getMode())){
				if(sscPlayType.equals(SscPlayType.DirectFive)){
					betType = "ZQSSC_5X_DS";
				}else if(sscPlayType.equals(SscPlayType.AllFive)){
					betType = "ZQSSC_5XTX";;
				}
				String[] ticketArr = ticket.getContent().split(Constant.TICKET_SINGLE_SEPARATOR_FOR_UNIT);
				for (String ticketStr : ticketArr) {
					for (String num : ticketStr.split(Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER)) {
						betCode.append(""+Integer.valueOf(num));
					}
					betCode.append(";");
				}
			}else if(SalesMode.COMPOUND.equals(ticket.getMode())){
				if(sscPlayType.equals(SscPlayType.DirectFive)){
					if(ticket.getUnits()==1){
						betType = "ZQSSC_5X_DS";
						String[] contents = JsonUtil.getStringArray4Json(ticket.getContent());
						for (String content : contents) {
							SscCompoundContent sscCompoundContent = JsonUtil.getObject4JsonString(content,SscCompoundContent.class);
							betCode.append(StringUtils.join(formatBetNum(sscCompoundContent.getArea1List(),ONE_NF),""));
							betCode.append(StringUtils.join(formatBetNum(sscCompoundContent.getArea2List(),ONE_NF),""));
							betCode.append(StringUtils.join(formatBetNum(sscCompoundContent.getArea3List(),ONE_NF),""));
							betCode.append(StringUtils.join(formatBetNum(sscCompoundContent.getArea4List(),ONE_NF),""));
							betCode.append(StringUtils.join(formatBetNum(sscCompoundContent.getArea5List(),ONE_NF),""));
							betCode.append(";");
						}
					}else{
						betType = "ZQSSC_5X_FS";
						String[] contents = JsonUtil.getStringArray4Json(ticket.getContent());
						for (String content : contents) {
							SscCompoundContent sscCompoundContent = JsonUtil.getObject4JsonString(content,SscCompoundContent.class);
							betCode.append(StringUtils.join(formatBetNum(sscCompoundContent.getArea1List(),ONE_NF),"")).append(",");
							betCode.append(StringUtils.join(formatBetNum(sscCompoundContent.getArea2List(),ONE_NF),"")).append(",");
							betCode.append(StringUtils.join(formatBetNum(sscCompoundContent.getArea3List(),ONE_NF),"")).append(",");
							betCode.append(StringUtils.join(formatBetNum(sscCompoundContent.getArea4List(),ONE_NF),"")).append(",");
							betCode.append(StringUtils.join(formatBetNum(sscCompoundContent.getArea5List(),ONE_NF),""));
							betCode.append(";");
						}
					}
				}else if(sscPlayType.equals(SscPlayType.AllFive)){
					throw new DataException("拆票单复式错误");
				}
			}else{
				throw new DataException("拆票单复式错误");
			}
		}else if(sscPlayType.equals(SscPlayType.GroupTwo)||sscPlayType.equals(SscPlayType.GroupTwoSum)||sscPlayType.equals(SscPlayType.BigSmallDoubleSingle)){
			
			if(SalesMode.SINGLE.equals(ticket.getMode())){
				if(sscPlayType.equals(SscPlayType.GroupTwo)){
					betType = "ZQSSC_2XZX_DS";
					String[] ticketArr = ticket.getContent().split(Constant.TICKET_SINGLE_SEPARATOR_FOR_UNIT);
					for (String ticketStr : ticketArr) {
						for (String num : ticketStr.split(Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER)) {
							betCode.append(""+Integer.valueOf(num));
						}
						betCode.append(";");
					}
				}else if(sscPlayType.equals(SscPlayType.GroupTwoSum)){
					betType = "ZQSSC_2XZXHZ";
					String[] ticketArr = ticket.getContent().split(Constant.TICKET_SINGLE_SEPARATOR_FOR_UNIT);
					for (String ticketStr : ticketArr) {
						for (String num : ticketStr.split(Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER)) {
							betCode.append(""+Integer.valueOf(num));
						}
						betCode.append(";");
					}
				}else if(sscPlayType.equals(SscPlayType.BigSmallDoubleSingle)){
					betType = "ZQSSC_DXDS";
					String[] ticketArr = ticket.getContent().split(Constant.TICKET_SINGLE_SEPARATOR_FOR_UNIT);
					for (String ticketStr : ticketArr) {
						for (String num : ticketStr.split(Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER)) {
							///** 大小双单 2 1 4 5
							//出票方(  1234  ) 大小双单 
						    if("2".equals(num.trim())){
						    	betCode.append("1");
						    }
						    if("1".equals(num.trim())){
						    	betCode.append("2");
						    }
						    if("4".equals(num.trim())){
						    	betCode.append("3");
						    }
						    if("5".equals(num.trim())){
						    	betCode.append("4");
						    }
						}
						betCode.append(";");
					}
				}
			}else{
				throw new DataException("拆票单复式错误");
			}
		}else{
			throw new DataException("拆票投注方式错误");
		}
		betCode = betCode.delete(betCode.length()-";".length(), betCode.length());
		StringBuffer betXML = new StringBuffer();
		betXML.append("<ticket ticketId=\""+ticket.getId()+"\" betType=\""+betType+"\"  issueNumber=\""+ticket.getPeriodNumber()+"\" betUnits=\""+ticket.getUnits()+"\" multiple=\""+ticket.getMultiple()+"\" betMoney =\""+ticket.getSchemeCost()+"\" isAppend =\"0\">");
		betXML.append("<betContent>"+betCode.toString()+"</betContent>");
		betXML.append("</ticket>");
		return betXML.toString();
	}

}
