package com.cai310.lottery.ticket.protocol.localnew.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.sevenstar.SevenstarCompoundContent;
import com.cai310.utils.JsonUtil;

public class SevenStarCPUtil extends CPUtil {
	private static final NumberFormat ONE_NF = new DecimalFormat("0");
	private static final String CONTENTCONNECTCODE = "-";
	private static final String SINGSPILTCODE = "+";

	@Override
	public String getCpLotteryId(Lottery lottery, Byte betType) {
		return "10052";
	}

	@Override
	public String getUpdatePeriodNumber(Ticket ticket) {
		return null;
	}

	@Override
	public String getCpBetType(Ticket ticket) throws DataException {
		return "0";
	}

	@Override
	public String getCpPlayType(Ticket ticket) {
		if (null == ticket.getMode())
			return null;
		if (SalesMode.SINGLE.equals(ticket.getMode())) {
			return "1";
		} else if (SalesMode.COMPOUND.equals(ticket.getMode())) {
			if(ticket.getUnits()==1){
				return "1";
			}else{
				return "5";
			}
		}
		return null;
	}

	@Override
	public String getBetContent(Ticket ticket) {
		StringBuffer sb = new StringBuffer();
		if (SalesMode.SINGLE.equals(ticket.getMode())) {
			String[] arr = ticket.getContent().split(Constant.TICKET_SINGLE_SEPARATOR_FOR_UNIT);
			for (int i = 0; i < arr.length; i++) {
				String[] str = arr[i].split(Constant.SINGLE_SEPARATOR_FOR_NUMBER);
				for (String st : str) {
					int num = Integer.valueOf(st);
					sb.append(num);
				}
				sb.append(SINGSPILTCODE);
			}
			return sb.substring(0, sb.length() - SINGSPILTCODE.length());
		} else {
			String[] contents = JsonUtil.getStringArray4Json(ticket.getContent());
			for (String content : contents) {
				SevenstarCompoundContent sevenstarCompoundContent = JsonUtil.getObject4JsonString(content, SevenstarCompoundContent.class);
				if (ticket.getUnits() == 1) {
					sb.append(StringUtils.join(formatBetNum(sevenstarCompoundContent.getArea1List(),ONE_NF), ""));
					sb.append(StringUtils.join(formatBetNum(sevenstarCompoundContent.getArea2List(),ONE_NF), ""));
					sb.append(StringUtils.join(formatBetNum(sevenstarCompoundContent.getArea3List(),ONE_NF), ""));
					sb.append(StringUtils.join(formatBetNum(sevenstarCompoundContent.getArea4List(),ONE_NF), ""));
					sb.append(StringUtils.join(formatBetNum(sevenstarCompoundContent.getArea5List(),ONE_NF), ""));
					sb.append(StringUtils.join(formatBetNum(sevenstarCompoundContent.getArea6List(),ONE_NF), ""));
					sb.append(StringUtils.join(formatBetNum(sevenstarCompoundContent.getArea7List(),ONE_NF), ""));
				} else {
					sb.append(StringUtils.join(formatBetNum(sevenstarCompoundContent.getArea1List(),ONE_NF), "")).append(CONTENTCONNECTCODE);
					sb.append(StringUtils.join(formatBetNum(sevenstarCompoundContent.getArea2List(),ONE_NF), "")).append(CONTENTCONNECTCODE);
					sb.append(StringUtils.join(formatBetNum(sevenstarCompoundContent.getArea3List(),ONE_NF), "")).append(CONTENTCONNECTCODE);
					sb.append(StringUtils.join(formatBetNum(sevenstarCompoundContent.getArea4List(),ONE_NF), "")).append(CONTENTCONNECTCODE);
					sb.append(StringUtils.join(formatBetNum(sevenstarCompoundContent.getArea5List(),ONE_NF), "")).append(CONTENTCONNECTCODE);
					sb.append(StringUtils.join(formatBetNum(sevenstarCompoundContent.getArea6List(),ONE_NF), "")).append(CONTENTCONNECTCODE);
					sb.append(StringUtils.join(formatBetNum(sevenstarCompoundContent.getArea7List(),ONE_NF), ""));
				}
			}
		}
		return sb.toString();
	}

	@Override
	public String getSpecialFlag(Ticket ticket) {
		return "";
	}

}
