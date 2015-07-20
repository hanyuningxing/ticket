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
import com.cai310.lottery.support.sdel11to5.SdEl11to5CompoundContent;
import com.cai310.lottery.support.sdel11to5.SdEl11to5PlayType;
import com.cai310.lottery.support.ssq.SsqCompoundContent;
import com.cai310.utils.DateUtil;
import com.cai310.utils.JsonUtil;
import com.google.common.collect.Maps;

public class SdEl11to5RlygUtil extends RlygUtil {
	private static final NumberFormat TWO_NF = new DecimalFormat("00");
	private static final String AEARSPLITCODE = " ";
	private static final String NUMSPLITCODE = ",";
	private static final String UNITSPLITCODE = "&amp;";
	public SdEl11to5RlygUtil(Ticket ticket) {
		super(ticket);
	}
	public SdEl11to5RlygUtil(){
		super();
	}

	@Override
	public Map<String,String> getLotCode(Ticket ticket) throws DataException{
		Map<String, String> map = Maps.newHashMap();	
		StringBuffer betCode = new StringBuffer();
		SdEl11to5PlayType sdSdEl11to5PlayType = SdEl11to5PlayType.values()[ticket.getBetType()];
		if(null==sdSdEl11to5PlayType)throw new DataException("拆票投注方式错误");
		String playType;//玩法
		String betType;//投注方法
		if(sdSdEl11to5PlayType.equals(SdEl11to5PlayType.NormalOne)){
			playType = "01";
		}else if(sdSdEl11to5PlayType.equals(SdEl11to5PlayType.RandomTwo)){
			playType = "02";
		}else if(sdSdEl11to5PlayType.equals(SdEl11to5PlayType.RandomThree)){
			playType = "03";
		}else if(sdSdEl11to5PlayType.equals(SdEl11to5PlayType.RandomFour)){
			playType = "04";
		}else if(sdSdEl11to5PlayType.equals(SdEl11to5PlayType.RandomFive)){
			playType = "05";
		}else if(sdSdEl11to5PlayType.equals(SdEl11to5PlayType.RandomSix)){
			playType = "06";
		}else if(sdSdEl11to5PlayType.equals(SdEl11to5PlayType.RandomSeven)){
			playType = "07";
		}else if(sdSdEl11to5PlayType.equals(SdEl11to5PlayType.RandomEight)){
			playType = "08";
		}else if(sdSdEl11to5PlayType.equals(SdEl11to5PlayType.ForeTwoGroup)){
			playType = "11";
		}else if(sdSdEl11to5PlayType.equals(SdEl11to5PlayType.ForeThreeGroup)){
			playType = "12";
		}else if(sdSdEl11to5PlayType.equals(SdEl11to5PlayType.ForeTwoDirect)){
			playType = "09";
		}else if(sdSdEl11to5PlayType.equals(SdEl11to5PlayType.ForeThreeDirect)){
			playType = "10";
			
		}else{
			throw new DataException("拆票投注方式错误");
		}
		if(SalesMode.SINGLE.equals(ticket.getMode())){
			betType = "01";
			String[] ticketArr = ticket.getContent().split(Constant.TICKET_SINGLE_SEPARATOR_FOR_UNIT);
			for (String ticketStr : ticketArr) {
				for (String num : ticketStr.split(Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER)) {
					betCode.append(TWO_NF.format(Integer.valueOf(num))).append(NUMSPLITCODE);
				}
				betCode = betCode.delete(betCode.length()-NUMSPLITCODE.length(), betCode.length());
				betCode.append(UNITSPLITCODE);
			}
		}else if(SalesMode.COMPOUND.equals(ticket.getMode())){
			if(ticket.getUnits()==1||SalesMode.SINGLE.equals(ticket.getMode())){
				betType = "01";
			}else{
				betType = "02";
			}
			String[] contents = JsonUtil.getStringArray4Json(ticket.getContent());
			for (String content : contents) {
				SdEl11to5CompoundContent sdSdEl11to5CompoundContent = JsonUtil.getObject4JsonString(content,SdEl11to5CompoundContent.class);
				if(sdSdEl11to5PlayType.equals(SdEl11to5PlayType.ForeTwoDirect)){
					betCode.append(StringUtils.join(formatBetNum(sdSdEl11to5CompoundContent.getBet1List(),TWO_NF),AEARSPLITCODE)).append(NUMSPLITCODE);
					betCode.append(StringUtils.join(formatBetNum(sdSdEl11to5CompoundContent.getBet2List(),TWO_NF),AEARSPLITCODE));
				}else if(sdSdEl11to5PlayType.equals(SdEl11to5PlayType.ForeThreeDirect)){
					betCode.append(StringUtils.join(formatBetNum(sdSdEl11to5CompoundContent.getBet1List(),TWO_NF),AEARSPLITCODE)).append(NUMSPLITCODE);
					betCode.append(StringUtils.join(formatBetNum(sdSdEl11to5CompoundContent.getBet2List(),TWO_NF),AEARSPLITCODE)).append(NUMSPLITCODE);
					betCode.append(StringUtils.join(formatBetNum(sdSdEl11to5CompoundContent.getBet3List(),TWO_NF),AEARSPLITCODE));
				}else{
					betCode.append(StringUtils.join(formatBetNum(sdSdEl11to5CompoundContent.getBetList(),TWO_NF),NUMSPLITCODE));
				}
				betCode.append(UNITSPLITCODE);
			}
		}else{
			throw new DataException("拆票单复式错误");
		}
		betCode = betCode.delete(betCode.length()-UNITSPLITCODE.length(), betCode.length());
		
		StringBuffer sb = new StringBuffer();
		sb.append("<ticket seq=\""+ticket.getId()+"\">"+playType+"-"+betType+"-"+betCode.toString()+"-"+ticket.getMultiple()+"-"+ticket.getSchemeCost()+"</ticket>");
		
		map.put("betValue", sb.toString());
		map.put("issue", ""+ticket.getPeriodNumber().substring(2));
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
		return Lottery.SDEL11TO5;
	}

}
