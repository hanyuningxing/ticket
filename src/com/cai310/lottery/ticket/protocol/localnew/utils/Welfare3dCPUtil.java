package com.cai310.lottery.ticket.protocol.localnew.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.welfare3d.Welfare3dCompoundContent;
import com.cai310.lottery.support.welfare3d.Welfare3dPlayType;
import com.cai310.utils.JsonUtil;

public class Welfare3dCPUtil extends CPUtil {

	private static final String NUMCODE = ",";
	private static final String CONTENTCONNECTCODE = "-";
	private static final String DANSPLITCODE = ":";
	private static final String SINGSPILTCODE = "+";
	private static final NumberFormat ONE_NF = new DecimalFormat("0");

	@Override
	public String getCpLotteryId(Lottery lottery, Byte betType) {
		return "50012";
	}

	@Override
	public String getUpdatePeriodNumber(Ticket ticket) {
		return null;
	}

	@Override
	public String getCpBetType(Ticket ticket) throws DataException {
		Welfare3dPlayType welfare3dPlayType = Welfare3dPlayType.values()[ticket.getBetType()];
		if (welfare3dPlayType.equals(Welfare3dPlayType.Direct)) {
			if (SalesMode.SINGLE.equals(ticket.getMode())) {
				return "0";
			} else {
				return "1";
			}
		} else if (welfare3dPlayType.equals(Welfare3dPlayType.Group3)) {
			if (SalesMode.SINGLE.equals(ticket.getMode())) {
				return "3";
			} else {
				return "4";
			}
		} else if (welfare3dPlayType.equals(Welfare3dPlayType.Group6)) {
			if (SalesMode.SINGLE.equals(ticket.getMode())) {
				return "5";
			} else {
				return "6";
			}
		}
		return null;
	}

	@Override
	public String getCpPlayType(Ticket ticket) {
		if (ticket.getMode() == null)
			return null;
		Welfare3dPlayType welfare3dPlayType = Welfare3dPlayType.values()[ticket.getBetType()];
		if (SalesMode.SINGLE.equals(ticket.getMode())) {
			return "1";
		} else if (SalesMode.COMPOUND.equals(ticket.getMode())) {
			if (welfare3dPlayType.equals(Welfare3dPlayType.Direct) || welfare3dPlayType.equals(Welfare3dPlayType.Group3) || welfare3dPlayType.equals(Welfare3dPlayType.Group6)) {
				if (ticket.getUnits() == 1) {
					return "1";
				}else{
					return "2";
				}
			} 
		}
		return null;
	}

	@Override
	public String getBetContent(Ticket ticket) throws DataException {
		Welfare3dPlayType welfare3dPlayType = Welfare3dPlayType.values()[ticket.getBetType()];
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
			if(welfare3dPlayType.equals(Welfare3dPlayType.Direct)){
				String[] contents=JsonUtil.getStringArray4Json(ticket.getContent());
				for(String content:contents){
					Welfare3dCompoundContent welfare3dCompoundContent=JsonUtil.getObject4JsonString(content, Welfare3dCompoundContent.class);
					sb.append(StringUtils.join(formatBetNum(welfare3dCompoundContent.getArea1List(),ONE_NF), "")).append(CONTENTCONNECTCODE);
					sb.append(StringUtils.join(formatBetNum(welfare3dCompoundContent.getArea2List(),ONE_NF), "")).append(CONTENTCONNECTCODE);
					sb.append(StringUtils.join(formatBetNum(welfare3dCompoundContent.getArea3List(),ONE_NF), ""));
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
