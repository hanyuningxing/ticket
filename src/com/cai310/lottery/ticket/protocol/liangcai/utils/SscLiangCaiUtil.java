package com.cai310.lottery.ticket.protocol.liangcai.utils;

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

public class SscLiangCaiUtil extends LiangCaiUtil {
	private static final NumberFormat ONE_NF = new DecimalFormat("0");
	public SscLiangCaiUtil(Ticket ticket) {
		super(ticket);
	}
	public SscLiangCaiUtil(){
		super();
	}

	@Override
	public String getLotIssue(Ticket ticket) {
		return ticket.getPeriodNumber();
	}

	@Override
	public String getLotCode(Ticket ticket) throws DataException{
		StringBuffer betCode = new StringBuffer();
		SscPlayType sscPlayType = SscPlayType.values()[ticket.getBetType()];
		if(null==sscPlayType)throw new DataException("拆票投注方式错误");
		String betTypeStr = null;//大赢家玩法
		String ruleStr;//大赢家规则
		if(sscPlayType.equals(SscPlayType.DirectOne)){
			betTypeStr = "1D";
			ruleStr ="-,-,-,-,";
			if(SalesMode.SINGLE.equals(ticket.getMode())){
				String[] ticketArr = ticket.getContent().split(Constant.TICKET_SINGLE_SEPARATOR_FOR_UNIT);
				for (String ticketStr : ticketArr) {
					betCode.append(betTypeStr).append("|").append(ruleStr);
					for (String num : ticketStr.split(Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER)) {
						betCode.append(ONE_NF.format(Integer.valueOf(num))).append(",");
					}
					betCode.delete(betCode.length()-1, betCode.length());
					betCode.append(";");
				}
			}else if(SalesMode.COMPOUND.equals(ticket.getMode())){
				String[] contents = JsonUtil.getStringArray4Json(ticket.getContent());
				for (String content : contents) {
					SscCompoundContent sscCompoundContent = JsonUtil.getObject4JsonString(content,SscCompoundContent.class);
					betCode.append(betTypeStr).append("|").append(ruleStr);
					betCode.append(StringUtils.join(formatBetNum(sscCompoundContent.getArea5List(),ONE_NF),""));
					betCode.append(";");
				}
			}else{
				throw new DataException("拆票单复式错误");
			}
		}else if(sscPlayType.equals(SscPlayType.DirectTwo)){
			betTypeStr = "2D";
			ruleStr ="-,-,-,";
			if(SalesMode.SINGLE.equals(ticket.getMode())){
				String[] ticketArr = ticket.getContent().split(Constant.TICKET_SINGLE_SEPARATOR_FOR_UNIT);
				for (String ticketStr : ticketArr) {
					betCode.append(betTypeStr).append("|").append(ruleStr);
					for (String num : ticketStr.split(Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER)) {
						betCode.append(ONE_NF.format(Integer.valueOf(num))).append(",");
					}
					betCode.delete(betCode.length()-1, betCode.length());
					betCode.append(";");
				}
			}else if(SalesMode.COMPOUND.equals(ticket.getMode())){
				String[] contents = JsonUtil.getStringArray4Json(ticket.getContent());
				for (String content : contents) {
					SscCompoundContent sscCompoundContent = JsonUtil.getObject4JsonString(content,SscCompoundContent.class);
					betCode.append(betTypeStr).append("|").append(ruleStr);
					betCode.append(StringUtils.join(formatBetNum(sscCompoundContent.getArea4List(),ONE_NF),"")).append(",");
					betCode.append(StringUtils.join(formatBetNum(sscCompoundContent.getArea5List(),ONE_NF),""));
					betCode.append(";");
				}
			}else{
				throw new DataException("拆票单复式错误");
			}
		}else if(sscPlayType.equals(SscPlayType.DirectThree)){
			betTypeStr = "3D";
			ruleStr ="-,-,";
			if(SalesMode.SINGLE.equals(ticket.getMode())){
				String[] ticketArr = ticket.getContent().split(Constant.TICKET_SINGLE_SEPARATOR_FOR_UNIT);
				for (String ticketStr : ticketArr) {
					betCode.append(betTypeStr).append("|").append(ruleStr);
					for (String num : ticketStr.split(Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER)) {
						betCode.append(ONE_NF.format(Integer.valueOf(num))).append(",");
					}
					betCode.delete(betCode.length()-1, betCode.length());
					betCode.append(";");
				}
			}else if(SalesMode.COMPOUND.equals(ticket.getMode())){
				String[] contents = JsonUtil.getStringArray4Json(ticket.getContent());
				for (String content : contents) {
					SscCompoundContent sscCompoundContent = JsonUtil.getObject4JsonString(content,SscCompoundContent.class);
					betCode.append(betTypeStr).append("|").append(ruleStr);
					betCode.append(StringUtils.join(formatBetNum(sscCompoundContent.getArea3List(),ONE_NF),"")).append(",");
					betCode.append(StringUtils.join(formatBetNum(sscCompoundContent.getArea4List(),ONE_NF),"")).append(",");
					betCode.append(StringUtils.join(formatBetNum(sscCompoundContent.getArea5List(),ONE_NF),""));
					betCode.append(";");
				}
			}else{
				throw new DataException("拆票单复式错误");
			}
		}else if(sscPlayType.equals(SscPlayType.DirectFive)||sscPlayType.equals(SscPlayType.AllFive)){
			if(sscPlayType.equals(SscPlayType.DirectFive)){
				betTypeStr = "5D";
			}else if(sscPlayType.equals(SscPlayType.AllFive)){
				betTypeStr = "5T";
			}
			if(SalesMode.SINGLE.equals(ticket.getMode())){
				String[] ticketArr = ticket.getContent().split(Constant.TICKET_SINGLE_SEPARATOR_FOR_UNIT);
				for (String ticketStr : ticketArr) {
					betCode.append(betTypeStr).append("|");
					for (String num : ticketStr.split(Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER)) {
						betCode.append(ONE_NF.format(Integer.valueOf(num))).append(",");
					}
					betCode.delete(betCode.length()-1, betCode.length());
					betCode.append(";");
				}
			}else if(SalesMode.COMPOUND.equals(ticket.getMode())){
				String[] contents = JsonUtil.getStringArray4Json(ticket.getContent());
				for (String content : contents) {
					SscCompoundContent sscCompoundContent = JsonUtil.getObject4JsonString(content,SscCompoundContent.class);
					betCode.append(betTypeStr).append("|");
					betCode.append(StringUtils.join(formatBetNum(sscCompoundContent.getArea1List(),ONE_NF),"")).append(",");
					betCode.append(StringUtils.join(formatBetNum(sscCompoundContent.getArea2List(),ONE_NF),"")).append(",");
					betCode.append(StringUtils.join(formatBetNum(sscCompoundContent.getArea3List(),ONE_NF),"")).append(",");
					betCode.append(StringUtils.join(formatBetNum(sscCompoundContent.getArea4List(),ONE_NF),"")).append(",");
					betCode.append(StringUtils.join(formatBetNum(sscCompoundContent.getArea5List(),ONE_NF),""));
					betCode.append(";");
				}
			}else{
				throw new DataException("拆票单复式错误");
			}
		}else if(sscPlayType.equals(SscPlayType.GroupTwo)||sscPlayType.equals(SscPlayType.GroupTwoSum)){
			if(sscPlayType.equals(SscPlayType.GroupTwo)){
				betTypeStr = "Z2";
			}else if(sscPlayType.equals(SscPlayType.GroupTwoSum)){
				betTypeStr = "S2";
			}
			if(SalesMode.SINGLE.equals(ticket.getMode())){
				String[] ticketArr = ticket.getContent().split(Constant.TICKET_SINGLE_SEPARATOR_FOR_UNIT);
				for (String ticketStr : ticketArr) {
					betCode.append(betTypeStr).append("|");
					for (String num : ticketStr.split(Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER)) {
						betCode.append(ONE_NF.format(Integer.valueOf(num))).append(",");
					}
					betCode.delete(betCode.length()-1, betCode.length());
					betCode.append(";");
				}
			}else if(SalesMode.COMPOUND.equals(ticket.getMode())){
				String[] contents = JsonUtil.getStringArray4Json(ticket.getContent());
				for (String content : contents) {
					SscCompoundContent sscCompoundContent = JsonUtil.getObject4JsonString(content,SscCompoundContent.class);
					betCode.append(betTypeStr).append("|");
					betCode.append(StringUtils.join(formatBetNum(sscCompoundContent.getBetList(),ONE_NF),","));
					betCode.append(";");
				}
			}else{
				throw new DataException("拆票单复式错误");
			}
		}else if(sscPlayType.equals(SscPlayType.BigSmallDoubleSingle)){
			if(sscPlayType.equals(SscPlayType.BigSmallDoubleSingle)){
				betTypeStr = "DD";
			}
			if(SalesMode.SINGLE.equals(ticket.getMode())){
				String[] ticketArr = ticket.getContent().split(Constant.TICKET_SINGLE_SEPARATOR_FOR_UNIT);
				for (String ticketStr : ticketArr) {
					betCode.append(betTypeStr).append("|");
					for (String num : ticketStr.split(Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER)) {
						betCode.append(ONE_NF.format(Integer.valueOf(num)));
					}
					betCode.append(";");
				}
			}else if(SalesMode.COMPOUND.equals(ticket.getMode())){
				String[] contents = JsonUtil.getStringArray4Json(ticket.getContent());
				for (String content : contents) {
					SscCompoundContent sscCompoundContent = JsonUtil.getObject4JsonString(content,SscCompoundContent.class);
					betCode.append(betTypeStr).append("|");
					betCode.append(StringUtils.join(formatBetNum(sscCompoundContent.getBetList(),ONE_NF),""));
					betCode.append(";");
				}
			}else{
				throw new DataException("拆票单复式错误");
			}
		}else{
			throw new DataException("拆票投注方式错误");
		}
		
		betCode = betCode.delete(betCode.length()-";".length(), betCode.length());
		return betCode.toString();
	}

	@Override
	public String getAttach(Ticket ticket) {
		return "";
	}

	@Override
	public String getOneMoney(Ticket ticket) {
		return "2";
	}

}
