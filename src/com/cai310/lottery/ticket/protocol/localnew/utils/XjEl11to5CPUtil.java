package com.cai310.lottery.ticket.protocol.localnew.utils;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.xjel11to5.XjEl11to5CompoundContent;
import com.cai310.lottery.support.xjel11to5.XjEl11to5PlayType;
import com.cai310.utils.JsonUtil;

public class XjEl11to5CPUtil extends CPUtil{
	private static final String UNITSPILTCODE = "+";
	private static final String SPILTCODE = ",";

	@Override
	public String getCpLotteryId(Lottery lottery, Byte betType) {
		return "10771";
	}

	@Override
	public String getUpdatePeriodNumber(Ticket ticket) {
		return ticket.getPeriodNumber().substring(2);
	}

	@Override
	public String getCpBetType(Ticket ticket) throws DataException {
		XjEl11to5PlayType xjel11to5PlayType = XjEl11to5PlayType.values()[ticket.getBetType()];
		if (null == xjel11to5PlayType)
			throw new DataException("拆票投注方式错误");
		if (xjel11to5PlayType.equals(XjEl11to5PlayType.NormalOne)) {
			return "0";
		} else if (xjel11to5PlayType.equals(XjEl11to5PlayType.RandomTwo)) {
			return "1";
		} else if (xjel11to5PlayType.equals(XjEl11to5PlayType.RandomThree)) {
			return "2";
		} else if (xjel11to5PlayType.equals(XjEl11to5PlayType.RandomFour)) {
			return "3";
		} else if (xjel11to5PlayType.equals(XjEl11to5PlayType.RandomFive)) {
			return "4";
		} else if (xjel11to5PlayType.equals(XjEl11to5PlayType.RandomSix)) {
			return "5";
		} else if (xjel11to5PlayType.equals(XjEl11to5PlayType.RandomSeven)) {
			return "6";
		} else if (xjel11to5PlayType.equals(XjEl11to5PlayType.RandomEight)) {
			return "7";
		} else if (xjel11to5PlayType.equals(XjEl11to5PlayType.ForeTwoGroup)) {
			return "8";
		} else if (xjel11to5PlayType.equals(XjEl11to5PlayType.ForeThreeGroup)) {
			return "10";
		} else if (xjel11to5PlayType.equals(XjEl11to5PlayType.ForeTwoDirect)) {
			return "9";
		} else if (xjel11to5PlayType.equals(XjEl11to5PlayType.ForeThreeDirect)) {
			return "11";
		} else {
			return null;
		}
	}

	@Override
	public String getCpPlayType(Ticket ticket) {
		if (null == ticket.getMode())
			return null;
		XjEl11to5PlayType xjel11to5PlayType = XjEl11to5PlayType.values()[ticket.getBetType()];
		if (SalesMode.SINGLE.equals(ticket.getMode())) {
			 if (xjel11to5PlayType.equals(XjEl11to5PlayType.NormalOne)) {
				return "0";
			} else{
				return "1";
			}	
		} else if (SalesMode.COMPOUND.equals(ticket.getMode())) {
			if (xjel11to5PlayType.equals(XjEl11to5PlayType.ForeTwoDirect)) {
				if (ticket.getUnits() == 1) {
					return "1";
				} else {
					return "5";
				}
			} else if (xjel11to5PlayType.equals(XjEl11to5PlayType.ForeThreeDirect)) {
				if (ticket.getUnits() == 1) {
					return "1";
				} else {
					return "5";
				}
			} else if (xjel11to5PlayType.equals(XjEl11to5PlayType.NormalOne)) {
					return "0";
			} else {
				if (ticket.getUnits() == 1) {
					return "1";
				} else {
					return "2";
				}
			}

		}
		return null;
	}

	@Override
	public String getBetContent(Ticket ticket) throws DataException {
		StringBuffer betCode = new StringBuffer();
		XjEl11to5PlayType xjel11to5PlayType =XjEl11to5PlayType.values()[ticket.getBetType()];
		if (null == xjel11to5PlayType)
			throw new DataException("拆票投注方式错误");
		if (SalesMode.SINGLE.equals(ticket.getMode())) {
			String[] ticketArr = ticket.getContent().split(Constant.TICKET_SINGLE_SEPARATOR_FOR_UNIT);
			for (String ticketStr : ticketArr) {
				betCode.append(ticketStr.trim().replace(Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER, SPILTCODE));
				betCode.append(UNITSPILTCODE);
			}
			betCode = betCode.delete(betCode.length() - UNITSPILTCODE.length(), betCode.length());

		} else if (SalesMode.COMPOUND.equals(ticket.getMode())) {
			String[] contents = JsonUtil.getStringArray4Json(ticket.getContent());
			for (String content : contents) {
				XjEl11to5CompoundContent xjel11to5CompoundContent = JsonUtil.getObject4JsonString(content, XjEl11to5CompoundContent.class);
				if (xjel11to5PlayType.equals(XjEl11to5PlayType.ForeTwoDirect)) {
					if (xjel11to5CompoundContent.getUnits() == 1) {
						betCode.append(StringUtils.join(formatBetNum(xjel11to5CompoundContent.getBet1List(), TWO_NF), "")).append(SPILTCODE);
						betCode.append(StringUtils.join(formatBetNum(xjel11to5CompoundContent.getBet2List(), TWO_NF), ""));
					} else {
						betCode.append(StringUtils.join(formatBetNum(xjel11to5CompoundContent.getBet1List(), TWO_NF), SPILTCODE)).append("-");
						betCode.append(StringUtils.join(formatBetNum(xjel11to5CompoundContent.getBet2List(), TWO_NF), SPILTCODE));
					}
				} else if (xjel11to5PlayType.equals(XjEl11to5PlayType.ForeThreeDirect)) {
					if (xjel11to5CompoundContent.getUnits() == 1) {
						betCode.append(StringUtils.join(formatBetNum(xjel11to5CompoundContent.getBet1List(), TWO_NF), "")).append(SPILTCODE);
						betCode.append(StringUtils.join(formatBetNum(xjel11to5CompoundContent.getBet2List(), TWO_NF), "")).append(SPILTCODE);
						betCode.append(StringUtils.join(formatBetNum(xjel11to5CompoundContent.getBet3List(), TWO_NF), ""));
					} else {
						betCode.append(StringUtils.join(formatBetNum(xjel11to5CompoundContent.getBet1List(), TWO_NF), SPILTCODE)).append("-");
						betCode.append(StringUtils.join(formatBetNum(xjel11to5CompoundContent.getBet2List(), TWO_NF), SPILTCODE)).append("-");
						betCode.append(StringUtils.join(formatBetNum(xjel11to5CompoundContent.getBet3List(), TWO_NF), SPILTCODE));
					}
				} else if(xjel11to5PlayType.equals(XjEl11to5PlayType.NormalOne)){
					betCode.append(StringUtils.join(formatBetNum(xjel11to5CompoundContent.getBetList(), TWO_NF), SPILTCODE));
				}else {
					betCode.append(":");
					betCode.append(StringUtils.join(formatBetNum(xjel11to5CompoundContent.getBetList(), TWO_NF), SPILTCODE));
				}
				betCode.append(UNITSPILTCODE);
			}
			betCode = betCode.delete(betCode.length() - UNITSPILTCODE.length(), betCode.length());
			return betCode.toString();
		} else {
			throw new DataException("拆票单复式错误");
		}
		return betCode.toString();
	}

	@Override
	public String getSpecialFlag(Ticket ticket) {
		return "";
	}
}
