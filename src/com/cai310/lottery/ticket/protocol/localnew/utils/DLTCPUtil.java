package com.cai310.lottery.ticket.protocol.localnew.utils;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collection;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.ContentBean;
import com.cai310.lottery.support.dlt.DltCompoundContent;
import com.cai310.lottery.support.dlt.DltContentBeanBuilder;
import com.cai310.lottery.support.dlt.DltPlayType;
import com.cai310.utils.JsonUtil;

public  class DLTCPUtil extends CPUtil{
	/**
	 * 
	 * @param lottery
	 * @param playType
	 * @return
	 */
	private static final String NUMCODE = ",";
	private static final String CONTENTCONNECTCODE = "-";
	private static final String DANSPLITCODE = ":";
	private static final String SINGSPILTCODE = "+";
	private static final NumberFormat TWO_NF = new DecimalFormat("00");

	@Override
	public String getCpLotteryId(Lottery lottery, Byte betType) {
		return "10051";
	}

	@Override
	public String getCpBetType(Ticket ticket) throws DataException {
		return "0";
	}

	@Override
	public String getCpPlayType(Ticket ticket) {
		if (ticket.getMode() == null)
			return null;
		if (SalesMode.SINGLE.equals(ticket.getMode())) {
			return "1";
		} else if (SalesMode.COMPOUND.equals(ticket.getMode())) {
			if(ticket.getUnits()==1){
				return "1";
			}else{
				return "2";
			}
		} else {
			return null;
		}
	}

	@Override
	public String getBetContent(Ticket ticket) {
		StringBuffer sb = new StringBuffer();
		if (SalesMode.SINGLE.equals(ticket.getMode())) {
			String[] arr = ticket.getContent().split(Constant.TICKET_SINGLE_SEPARATOR_FOR_UNIT);
			for (int i = 0; i < arr.length; i++) {
				String[] str=arr[i].split(Constant.SINGLE_SEPARATOR_FOR_NUMBER);
				for(int j=0;j<str.length;j++){
					sb.append(TWO_NF.format(Integer.valueOf(str[j]))).append(NUMCODE);
					if (j == 4) {
						sb.delete(sb.length()-1, sb.length());
						sb.append(CONTENTCONNECTCODE);
					}
				}
				sb.delete(sb.length()-1, sb.length());
				sb.append(SINGSPILTCODE);
			}
			return sb.substring(0, sb.length() - SINGSPILTCODE.length());
		} else if (SalesMode.COMPOUND.equals(ticket.getMode())) {
			String[] contents = JsonUtil.getStringArray4Json(ticket.getContent());
			for(String content:contents){
				DltCompoundContent dltCompoundContent = JsonUtil.getObject4JsonString(content, DltCompoundContent.class);
				if (ticket.getUnits() == 1) {
					sb.append(StringUtils.join(dltCompoundContent.getRedList(), NUMCODE)).append(CONTENTCONNECTCODE);
					sb.append(StringUtils.join(dltCompoundContent.getBlueList(), NUMCODE));
				} else {
					sb.append(StringUtils.join(dltCompoundContent.getRedDanList(), NUMCODE)).append(DANSPLITCODE);
					sb.append(StringUtils.join(dltCompoundContent.getRedList(), NUMCODE)).append(DANSPLITCODE);
					sb.append(StringUtils.join(dltCompoundContent.getBlueDanList(), NUMCODE)).append(DANSPLITCODE);
					sb.append(StringUtils.join(dltCompoundContent.getBlueList(), NUMCODE));
				}
			}
			return sb.toString();
		}else{
			return null;
		}
	}

	@Override
	public String getSpecialFlag(Ticket ticket) {
		if(ticket.getBetType()==2){
			return "1";
		}else{
			return "";
		}
	}

	@Override
	public String getUpdatePeriodNumber(Ticket ticket) {
		return null;
	}
}
