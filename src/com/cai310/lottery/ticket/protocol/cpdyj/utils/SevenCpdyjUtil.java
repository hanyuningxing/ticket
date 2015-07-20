package com.cai310.lottery.ticket.protocol.cpdyj.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.seven.SevenCompoundContent;
import com.cai310.utils.JsonUtil;

public class SevenCpdyjUtil extends CpdyjUtil {
	private static final NumberFormat TWO_NF = new DecimalFormat("00");
	private static final String DANSPLITCODE = "$";
	private static final String UNITSPLITCODE = ";";
	public SevenCpdyjUtil(Ticket ticket) {
		super(ticket);
	}
	public SevenCpdyjUtil(){
		super();
	}

	@Override
	public String getLotIssue(Ticket ticket) {
		return ticket.getPeriodNumber();
	}

	@Override
	public String getLotCode(Ticket ticket) throws DataException{
		StringBuffer betCode = new StringBuffer();
		if(SalesMode.SINGLE.equals(ticket.getMode())){
			betCode.append(ticket.getContent().replaceAll(Constant.TICKET_SINGLE_SEPARATOR_FOR_UNIT, UNITSPLITCODE));
		}else if(SalesMode.COMPOUND.equals(ticket.getMode())){
			String[] contents = JsonUtil.getStringArray4Json(ticket.getContent());
			for (String content : contents) {
				SevenCompoundContent sevenCompoundContent = JsonUtil.getObject4JsonString(content,SevenCompoundContent.class);
				if(null==sevenCompoundContent.getDanList()||sevenCompoundContent.getDanList().isEmpty()){
					betCode.append(StringUtils.join(formatBetNum(sevenCompoundContent.getBallList(),TWO_NF),  Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER));
				}else{
				    betCode.append(StringUtils.join(formatBetNum(sevenCompoundContent.getDanList(),TWO_NF),  Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER)).append(DANSPLITCODE);
					betCode.append(StringUtils.join(formatBetNum(sevenCompoundContent.getBallList(),TWO_NF),  Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER));
				}
			 betCode.append(UNITSPLITCODE);
			}
			betCode = betCode.delete(betCode.length()-";".length(), betCode.length());
		}else{
			throw new DataException("拆票单复式错误");
		}
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
