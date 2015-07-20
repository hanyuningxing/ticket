package com.cai310.lottery.ticket.protocol.zunao.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.dlt.DltCompoundContent;
import com.cai310.lottery.support.dlt.DltPlayType;
import com.cai310.utils.JsonUtil;
import com.google.common.collect.Lists;

public class DltZunaoUtil extends ZunaoUtil {
	private static final NumberFormat TWO_NF = new DecimalFormat("00");
	private static final String AEARSPLITCODE = "+";
	private static final String NUMSPLITCODE = ",";
	private static final String UNITSPLITCODE = ";";
	public DltZunaoUtil(Ticket ticket) {
		super(ticket);
	}
	public DltZunaoUtil(){
		super();
	}
    /**
     *  <ticket ticketId=\"1023620\" betType=\"P1_1\"  issueNumber=\"2011062\" betUnits=\"1\" multiple=\"1\" betMoney =\"8\" isAppend =\"0\">
		<betContent></betContent>
		</ticket>
     */
	@Override
	public String getLotCode(Ticket ticket) throws DataException{
		StringBuffer betCode = new StringBuffer();
		String betType = "";
		DltPlayType dltPlayType = DltPlayType.values()[ticket.getBetType()];
		if(SalesMode.SINGLE.equals(ticket.getMode())){
			String[] ticketArr = ticket.getContent().split(Constant.TICKET_SINGLE_SEPARATOR_FOR_UNIT);
			for (String ticketStr : ticketArr) {
				if(dltPlayType.equals(DltPlayType.Select12to2)){//12选2
					String[] numArr =ticketStr.split(Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER);
					for (int i = 0; i < numArr.length; i++) {
						betCode.append(TWO_NF.format(Integer.valueOf(numArr[i]))).append(NUMSPLITCODE);
					}
					betCode = betCode.delete(betCode.length()-NUMSPLITCODE.length(), betCode.length());
					betCode.append(UNITSPLITCODE);
					betType="12X2DS";
				}else{
					betType="DS";
					String[] numArr =ticketStr.split(Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER);
					for (int i = 0; i < numArr.length; i++) {
						if(i==5){
							betCode = betCode.delete(betCode.length()-NUMSPLITCODE.length(), betCode.length());
							betCode.append(AEARSPLITCODE);
						}
						betCode.append(TWO_NF.format(Integer.valueOf(numArr[i]))).append(NUMSPLITCODE);
					}
					betCode = betCode.delete(betCode.length()-NUMSPLITCODE.length(), betCode.length());
					betCode.append(UNITSPLITCODE);
				}
			}
			
		}else if(SalesMode.COMPOUND.equals(ticket.getMode())){
			
			String[] contents = JsonUtil.getStringArray4Json(ticket.getContent());
			for (String content : contents) {
				DltCompoundContent dltCompoundContent = JsonUtil.getObject4JsonString(content,DltCompoundContent.class);
				
				if(dltPlayType.equals(DltPlayType.Select12to2)){//12选2
					
					if(null==dltCompoundContent.getBlueDanList()||dltCompoundContent.getBlueDanList().isEmpty()){
						if(ticket.getUnits()==1){
						    betType="12X2DS";
						}else{
							betType="12X2FS";
						}
						betCode.append(StringUtils.join(formatBetNum(dltCompoundContent.getBlueList(),TWO_NF), NUMSPLITCODE));
					}else{
						betType="12X2DT";
						betCode.append("("+StringUtils.join(formatBetNum(dltCompoundContent.getBlueDanList(),TWO_NF),  NUMSPLITCODE)+")"+NUMSPLITCODE);
				    	betCode.append(StringUtils.join(formatBetNum(dltCompoundContent.getBlueList(),TWO_NF),  NUMSPLITCODE));
					}
					betCode.append(UNITSPLITCODE);
			   }else{
					if(null==dltCompoundContent.getRedDanList()||dltCompoundContent.getRedDanList().isEmpty()){
						    betCode.append(StringUtils.join(formatBetNum(dltCompoundContent.getRedList(),TWO_NF),NUMSPLITCODE));
						    if(ticket.getUnits()==1){
						    	betType="DS";
						    }else{
							    betType="FS";
						    }
					}else{
							betCode.append("("+StringUtils.join(formatBetNum(dltCompoundContent.getRedDanList(),TWO_NF), NUMSPLITCODE)+")"+NUMSPLITCODE);
							betCode.append(StringUtils.join(formatBetNum(dltCompoundContent.getRedList(),TWO_NF), NUMSPLITCODE));
							betType="FS";
				    }
					betCode.append(AEARSPLITCODE);
					
					if(null==dltCompoundContent.getBlueDanList()||dltCompoundContent.getBlueDanList().isEmpty()){
						if(ticket.getUnits()==1){
						    betType="DS";
						}else{
							betType="FS";
						}
						betCode.append(StringUtils.join(formatBetNum(dltCompoundContent.getBlueList(),TWO_NF), NUMSPLITCODE));
					}else{
						betType="FS";
						betCode.append("("+StringUtils.join(formatBetNum(dltCompoundContent.getBlueDanList(),TWO_NF),  NUMSPLITCODE)+")"+NUMSPLITCODE);
				    	betCode.append(StringUtils.join(formatBetNum(dltCompoundContent.getBlueList(),TWO_NF),  NUMSPLITCODE));
					}
					betCode.append(UNITSPLITCODE);
			   }
			 }
		}else{
			throw new DataException("拆票单复式错误");
		}
		betCode = betCode.delete(betCode.length()-UNITSPLITCODE.length(), betCode.length());
		StringBuffer betXML = new StringBuffer();
		String isAppend = "0";
		if(dltPlayType.equals(DltPlayType.GeneralAdditional)){
			isAppend = "1";
		}
		betXML.append("<ticket ticketId=\""+ticket.getId()+"\" betType=\""+betType+"\"  issueNumber=\""+ticket.getPeriodNumber()+"\" betUnits=\""+ticket.getUnits()+"\" multiple=\""+ticket.getMultiple()+"\" betMoney =\""+ticket.getSchemeCost()+"\" isAppend =\""+isAppend+"\">");
		betXML.append("<betContent>"+betCode.toString()+"</betContent>");
		betXML.append("</ticket>");
		return betXML.toString();
	}
	protected List<String> formatBetNum(List<String> numList,NumberFormat nf){
		List<String> newNumList = Lists.newArrayList();
		for (String num : numList) {
			newNumList.add(nf.format(Integer.valueOf(num)));
		}
		return newNumList;
	}
}
