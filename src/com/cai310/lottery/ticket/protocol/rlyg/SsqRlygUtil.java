package com.cai310.lottery.ticket.protocol.rlyg;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.jclq.JclqUtil;
import com.cai310.lottery.support.jczq.ItemBF;
import com.cai310.lottery.support.jczq.ItemBQQ;
import com.cai310.lottery.support.jczq.ItemJQS;
import com.cai310.lottery.support.jczq.ItemSPF;
import com.cai310.lottery.support.jczq.JczqUtil;
import com.cai310.lottery.support.jczq.PassType;
import com.cai310.lottery.support.jczq.PlayType;
import com.cai310.lottery.support.ssq.SsqCompoundContent;
import com.cai310.utils.DateUtil;
import com.cai310.utils.JsonUtil;
import com.google.common.collect.Maps;

public class SsqRlygUtil extends RlygUtil {
	private static final NumberFormat TWO_NF = new DecimalFormat("00");
	private static final String AEARSPLITCODE = "#";
	private static final String NUMSPLITCODE = ",";
	private static final String UNITSPLITCODE = "&amp;";
	public SsqRlygUtil(Ticket ticket) {
		super(ticket);
	}
	public SsqRlygUtil(){
		super();
	}

	@Override
	public Map<String,String> getLotCode(Ticket ticket) throws DataException{
		Map<String, String> map = Maps.newHashMap();	
		StringBuffer betCode = new StringBuffer();
		String betType = "";
		if(SalesMode.SINGLE.equals(ticket.getMode())){
			String[] ticketArr = ticket.getContent().split(Constant.TICKET_SINGLE_SEPARATOR_FOR_UNIT);
			for (String ticketStr : ticketArr) {
				String[] numArr =ticketStr.split(Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER);
				for (int i = 0; i < numArr.length; i++) {
					if(i==6){
						betCode = betCode.delete(betCode.length()-NUMSPLITCODE.length(), betCode.length());
						betCode.append(AEARSPLITCODE);
					}
					betCode.append(TWO_NF.format(Integer.valueOf(numArr[i]))).append(NUMSPLITCODE);
				}
				betCode = betCode.delete(betCode.length()-NUMSPLITCODE.length(), betCode.length());
			}
			betType="01";
		}else if(SalesMode.COMPOUND.equals(ticket.getMode())){
			String[] contents = JsonUtil.getStringArray4Json(ticket.getContent());
			for (String content : contents) {
				SsqCompoundContent ssqCompoundContent = JsonUtil.getObject4JsonString(content,SsqCompoundContent.class);
				
				if(null==ssqCompoundContent.getRedDanList()||ssqCompoundContent.getRedDanList().isEmpty()){
					    betCode.append(StringUtils.join(formatBetNum(ssqCompoundContent.getRedList(),TWO_NF),NUMSPLITCODE));
					    if(ticket.getUnits()==1){
					    	betType="01";
					    }else{
						    betType="02";
					    }
				}else{
					throw new DataException("拆票单复式错误");
			    }
				betCode.append(AEARSPLITCODE);
				betCode.append(StringUtils.join(formatBetNum(ssqCompoundContent.getBlueList(),TWO_NF),  NUMSPLITCODE));
			 }
		}else{
			throw new DataException("拆票单复式错误");
		}
		StringBuffer sb = new StringBuffer();
		sb.append("<ticket seq=\""+ticket.getId()+"\">00-"+betType+"-"+betCode.toString()+"-"+ticket.getMultiple()+"-"+ticket.getSchemeCost()+"</ticket>");
		
		map.put("betValue", sb.toString());
		map.put("issue", ""+ticket.getPeriodNumber());
		return map;
	}
	 public String getPassTypeStr(Integer pos){
	    	if(pos<10){
	    		return "0"+pos;
	    	}else{
	    		return ""+pos;
	    	}
	    }
	@Override
	public Lottery getLottery() {
		return Lottery.SSQ;
	}

}
