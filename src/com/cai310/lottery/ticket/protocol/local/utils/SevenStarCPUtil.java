package com.cai310.lottery.ticket.protocol.local.utils;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.sevenstar.SevenstarCompoundContent;
import com.cai310.utils.JsonUtil;

public class SevenStarCPUtil extends CPUtil {

	private static final String CONTENTCONNECTCODE = "-";
	private static final String SINGSPILTCODE = "+";

	@Override
	public String getCpLotteryId(Lottery lottery, Byte betType) {
		return "9";
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
			String[] arr = ticket.getContent().split(Constant.SINGLE_SEPARATOR_FOR_NUMBER);
			for (String content : arr) {
				int num = Integer.valueOf(content);
				sb.append(num);
			}
		} else {
			String[] contents = JsonUtil.getStringArray4Json(ticket.getContent());
			for (String content : contents) {
				SevenstarCompoundContent sevenstarCompoundContent = JsonUtil.getObject4JsonString(content, SevenstarCompoundContent.class);
				if (ticket.getUnits() == 1) {
					sb.append(StringUtils.join(sevenstarCompoundContent.getArea1List(), ""));
					sb.append(StringUtils.join(sevenstarCompoundContent.getArea2List(), ""));
					sb.append(StringUtils.join(sevenstarCompoundContent.getArea3List(), ""));
					sb.append(StringUtils.join(sevenstarCompoundContent.getArea4List(), ""));
					sb.append(StringUtils.join(sevenstarCompoundContent.getArea5List(), ""));
					sb.append(StringUtils.join(sevenstarCompoundContent.getArea6List(), ""));
					sb.append(StringUtils.join(sevenstarCompoundContent.getArea7List(), ""));
				} else {
					sb.append(StringUtils.join(sevenstarCompoundContent.getArea1List(), "")).append(CONTENTCONNECTCODE);
					sb.append(StringUtils.join(sevenstarCompoundContent.getArea2List(), "")).append(CONTENTCONNECTCODE);
					sb.append(StringUtils.join(sevenstarCompoundContent.getArea3List(), "")).append(CONTENTCONNECTCODE);
					sb.append(StringUtils.join(sevenstarCompoundContent.getArea4List(), "")).append(CONTENTCONNECTCODE);
					sb.append(StringUtils.join(sevenstarCompoundContent.getArea5List(), "")).append(CONTENTCONNECTCODE);
					sb.append(StringUtils.join(sevenstarCompoundContent.getArea6List(), "")).append(CONTENTCONNECTCODE);
					sb.append(StringUtils.join(sevenstarCompoundContent.getArea7List(), ""));
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
