package com.cai310.lottery.ticket.protocol.tianjin.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.ssq.SsqCompoundContent;
import com.cai310.utils.JsonUtil;
import com.google.common.collect.Lists;

public class SsqTianjinUtil extends TianjinUtil {
	private static final NumberFormat TWO_NF = new DecimalFormat("00");
	private static final String DANSPLITCODE = "$";
	private static final String AEARSPLITCODE = "-";
	private static final String UNITSPLITCODE = ";";
	public SsqTianjinUtil(Ticket ticket) {
		super(ticket);
	}
	public SsqTianjinUtil(){
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
			String[] ticketArr = ticket.getContent().split(Constant.TICKET_SINGLE_SEPARATOR_FOR_UNIT);
			for (String ticketStr : ticketArr) {
				String[] numArr =ticketStr.split(Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER);
				for (int i = 0; i < numArr.length; i++) {
					if(i==6){
						betCode = betCode.delete(betCode.length()-Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER.length(), betCode.length());
						betCode.append(AEARSPLITCODE);
					}
					betCode.append(TWO_NF.format(Integer.valueOf(numArr[i]))).append(Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER);
				}
				betCode = betCode.delete(betCode.length()-Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER.length(), betCode.length());
				betCode.append(UNITSPLITCODE);
			}
		}else if(SalesMode.COMPOUND.equals(ticket.getMode())){
			String[] contents = JsonUtil.getStringArray4Json(ticket.getContent());
			for (String content : contents) {
				SsqCompoundContent ssqCompoundContent = JsonUtil.getObject4JsonString(content,SsqCompoundContent.class);
				
				if(null==ssqCompoundContent.getRedDanList()||ssqCompoundContent.getRedDanList().isEmpty()){
					    betCode.append(StringUtils.join(formatBetNum(ssqCompoundContent.getRedList(),TWO_NF),  Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER));
				}else{
						betCode.append(StringUtils.join(formatBetNum(ssqCompoundContent.getRedDanList(),TWO_NF),  Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER)).append(DANSPLITCODE);
						betCode.append(StringUtils.join(formatBetNum(ssqCompoundContent.getRedList(),TWO_NF),  Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER));
			    }
				betCode.append(AEARSPLITCODE);
				betCode.append(StringUtils.join(formatBetNum(ssqCompoundContent.getBlueList(),TWO_NF),  Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER));
				 betCode.append(UNITSPLITCODE);
			 }
		}else{
			throw new DataException("拆票单复式错误");
		}
		betCode = betCode.delete(betCode.length()-AEARSPLITCODE.length(), betCode.length());
		return betCode.toString();
	}
	protected List<String> formatBetNum(List<String> numList,NumberFormat nf){
		List<String> newNumList = Lists.newArrayList();
		for (String num : numList) {
			newNumList.add(nf.format(Integer.valueOf(num)));
		}
		return newNumList;
	}
	@Override
	public String getAttach(Ticket ticket) {
		return "";
	}

	@Override
	public String getOneMoney(Ticket ticket) throws DataException {
		return "2";
	}

}
