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
import com.cai310.lottery.support.seven.SevenCompoundContent;
import com.cai310.utils.DateUtil;
import com.cai310.utils.JsonUtil;
import com.google.common.collect.Maps;

public class SevenRlygUtil extends RlygUtil {
	private static final NumberFormat TWO_NF = new DecimalFormat("00");
	private static final String UNITSPLITCODE = "&amp;";
	private static final String DANSPLITCODE = "@";
	private static final String NUMSPLITCODE = ",";
	public SevenRlygUtil(Ticket ticket) {
		super(ticket);
	}
	public SevenRlygUtil(){
		super();
	}

	@Override
	public Map<String,String> getLotCode(Ticket ticket) throws DataException{
		Map<String, String> map = Maps.newHashMap();	
		String betType = "";
		StringBuffer betCode = new StringBuffer();
		if(SalesMode.SINGLE.equals(ticket.getMode())){
			String content = ticket.getContent().replaceAll(Constant.TICKET_SINGLE_SEPARATOR_FOR_UNIT, UNITSPLITCODE);
			content = content.replaceAll(Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER, NUMSPLITCODE);
			betCode.append(content);
			 betType="01";
		}else if(SalesMode.COMPOUND.equals(ticket.getMode())){
			String[] contents = JsonUtil.getStringArray4Json(ticket.getContent());
			for (String content : contents) {
				SevenCompoundContent sevenCompoundContent = JsonUtil.getObject4JsonString(content,SevenCompoundContent.class);
				if(ticket.getUnits()==1){
				    betType="01";
				}else{
					betType="02";
				}
				if(null==sevenCompoundContent.getDanList()||sevenCompoundContent.getDanList().isEmpty()){
					betCode.append(StringUtils.join(formatBetNum(sevenCompoundContent.getBallList(),TWO_NF), NUMSPLITCODE));
				}else{
					betType="03";
				    betCode.append(StringUtils.join(formatBetNum(sevenCompoundContent.getDanList(),TWO_NF), NUMSPLITCODE)).append(DANSPLITCODE);
					betCode.append(StringUtils.join(formatBetNum(sevenCompoundContent.getBallList(),TWO_NF), NUMSPLITCODE));
				}
			    betCode.append(UNITSPLITCODE);
			}
			betCode = betCode.delete(betCode.length()-UNITSPLITCODE.length(), betCode.length());
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
		return Lottery.SEVEN;
	}

}
