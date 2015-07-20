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
import com.cai310.lottery.support.ssc.SscCompoundContent;
import com.cai310.lottery.support.ssc.SscPlayType;
import com.cai310.lottery.support.ssq.SsqCompoundContent;
import com.cai310.utils.DateUtil;
import com.cai310.utils.JsonUtil;
import com.google.common.collect.Maps;

public class SscRlygUtil extends RlygUtil {
	private static final NumberFormat ONE_NF = new DecimalFormat("0");
	private static final String AEARSPLITCODE = " ";
	private static final String NUMSPLITCODE = ",";
	private static final String UNITSPLITCODE = "&amp;";
	public SscRlygUtil(Ticket ticket) {
		super(ticket);
	}
	public SscRlygUtil(){
		super();
	}

	@Override
	public Map<String,String> getLotCode(Ticket ticket) throws DataException{
		Map<String, String> map = Maps.newHashMap();	
		StringBuffer betCode = new StringBuffer();
		SscPlayType sscPlayType = SscPlayType.values()[ticket.getBetType()];
		if(null==sscPlayType)throw new DataException("拆票投注方式错误");
		String playType = null;//玩法
		String ruleStr;//大赢家规则
		String betType = null;//投注方法
		if(sscPlayType.equals(SscPlayType.DirectOne)){
			playType = "01";
			ruleStr ="_,_,_,_,";
			if(SalesMode.SINGLE.equals(ticket.getMode())){
				String[] ticketArr = ticket.getContent().split(Constant.TICKET_SINGLE_SEPARATOR_FOR_UNIT);
				for (String ticketStr : ticketArr) {
					betCode.append(ruleStr);
					for (String num : ticketStr.split(Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER)) {
						betCode.append(Integer.valueOf(num)).append(NUMSPLITCODE);
					}
					betCode.delete(betCode.length()-1, betCode.length());
					betCode.append(UNITSPLITCODE);
				}
				betType = "01";
			}else if(SalesMode.COMPOUND.equals(ticket.getMode())){
				String[] contents = JsonUtil.getStringArray4Json(ticket.getContent());
				for (String content : contents) {
					SscCompoundContent sscCompoundContent = JsonUtil.getObject4JsonString(content,SscCompoundContent.class);
					betCode.append(ruleStr+StringUtils.join(formatBetNum(sscCompoundContent.getArea5List(),ONE_NF),""));
					betCode.append(UNITSPLITCODE);
				}
				betType = "01";
			}else{
				throw new DataException("拆票单复式错误");
			}
		}else if(sscPlayType.equals(SscPlayType.DirectTwo)){
			playType = "02";
			ruleStr ="_,_,_,";
			if(SalesMode.SINGLE.equals(ticket.getMode())){
				String[] ticketArr = ticket.getContent().split(Constant.TICKET_SINGLE_SEPARATOR_FOR_UNIT);
				for (String ticketStr : ticketArr) {
					betCode.append(ruleStr);
					for (String num : ticketStr.split(Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER)) {
						betCode.append(Integer.valueOf(num)).append(NUMSPLITCODE);
					}
					betCode.delete(betCode.length()-1, betCode.length());
					betCode.append(UNITSPLITCODE);
				}
				betType = "01";
			}else if(SalesMode.COMPOUND.equals(ticket.getMode())){
				String[] contents = JsonUtil.getStringArray4Json(ticket.getContent());
				for (String content : contents) {
					SscCompoundContent sscCompoundContent = JsonUtil.getObject4JsonString(content,SscCompoundContent.class);
					betCode.append(ruleStr);
					betCode.append(StringUtils.join(formatBetNum(sscCompoundContent.getArea4List(),ONE_NF),AEARSPLITCODE)).append(NUMSPLITCODE);
					betCode.append(StringUtils.join(formatBetNum(sscCompoundContent.getArea5List(),ONE_NF),AEARSPLITCODE));
					betCode.append(UNITSPLITCODE);
				}
				if(ticket.getUnits()==1){
					betType = "01";
				}else{
					betType = "02";
				}
			}else{
				throw new DataException("拆票单复式错误");
			}
		}else if(sscPlayType.equals(SscPlayType.DirectThree)){
			playType = "03";
			ruleStr ="_,_,";
			if(SalesMode.SINGLE.equals(ticket.getMode())){
				String[] ticketArr = ticket.getContent().split(Constant.TICKET_SINGLE_SEPARATOR_FOR_UNIT);
				for (String ticketStr : ticketArr) {
					betCode.append(ruleStr);
					for (String num : ticketStr.split(Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER)) {
						betCode.append(Integer.valueOf(num)).append(NUMSPLITCODE);
					}
					betCode.delete(betCode.length()-1, betCode.length());
					betCode.append(UNITSPLITCODE);
					
				}
				betType = "01";
			}else if(SalesMode.COMPOUND.equals(ticket.getMode())){
				String[] contents = JsonUtil.getStringArray4Json(ticket.getContent());
				for (String content : contents) {
					SscCompoundContent sscCompoundContent = JsonUtil.getObject4JsonString(content,SscCompoundContent.class);
					betCode.append(ruleStr);
					betCode.append(StringUtils.join(formatBetNum(sscCompoundContent.getArea3List(),ONE_NF),AEARSPLITCODE)).append(NUMSPLITCODE);
					betCode.append(StringUtils.join(formatBetNum(sscCompoundContent.getArea4List(),ONE_NF),AEARSPLITCODE)).append(NUMSPLITCODE);
					betCode.append(StringUtils.join(formatBetNum(sscCompoundContent.getArea5List(),ONE_NF),AEARSPLITCODE));
					betCode.append(UNITSPLITCODE);
				}
				if(ticket.getUnits()==1){
					betType = "01";
				}else{
					betType = "02";
				}
			}else{
				throw new DataException("拆票单复式错误");
			}
		}else if(sscPlayType.equals(SscPlayType.GroupTwo)){
			playType = "06";
			betType = "01";
			ruleStr ="_,_,_,";
			if(SalesMode.SINGLE.equals(ticket.getMode())){
				String[] ticketArr = ticket.getContent().split(Constant.TICKET_SINGLE_SEPARATOR_FOR_UNIT);
				for (String ticketStr : ticketArr) {
					betCode.append(ruleStr);
					for (String num : ticketStr.split(Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER)) {
						betCode.append(Integer.valueOf(num)).append(NUMSPLITCODE);
					}
					betCode.delete(betCode.length()-1, betCode.length());
					betCode.append(UNITSPLITCODE);
					
				}
			}else{
				throw new DataException("拆票单复式错误");
			}
		}else if(sscPlayType.equals(SscPlayType.DirectFive)||sscPlayType.equals(SscPlayType.AllFive)){
			if(sscPlayType.equals(SscPlayType.DirectFive)){
				playType = "05";
			}else if(sscPlayType.equals(SscPlayType.AllFive)){
				playType = "13";
			}
			if(SalesMode.SINGLE.equals(ticket.getMode())){
				String[] ticketArr = ticket.getContent().split(Constant.TICKET_SINGLE_SEPARATOR_FOR_UNIT);
				for (String ticketStr : ticketArr) {
					for (String num : ticketStr.split(Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER)) {
						betCode.append(Integer.valueOf(num)).append(NUMSPLITCODE);
					}
					betCode.delete(betCode.length()-1, betCode.length());
					betCode.append(UNITSPLITCODE);
				}
				betType = "01";
			}else if(SalesMode.COMPOUND.equals(ticket.getMode())){
				String[] contents = JsonUtil.getStringArray4Json(ticket.getContent());
				for (String content : contents) {
					SscCompoundContent sscCompoundContent = JsonUtil.getObject4JsonString(content,SscCompoundContent.class);
					betCode.append(StringUtils.join(formatBetNum(sscCompoundContent.getArea1List(),ONE_NF),AEARSPLITCODE)).append(NUMSPLITCODE);
					betCode.append(StringUtils.join(formatBetNum(sscCompoundContent.getArea2List(),ONE_NF),AEARSPLITCODE)).append(NUMSPLITCODE);
					betCode.append(StringUtils.join(formatBetNum(sscCompoundContent.getArea3List(),ONE_NF),AEARSPLITCODE)).append(NUMSPLITCODE);
					betCode.append(StringUtils.join(formatBetNum(sscCompoundContent.getArea4List(),ONE_NF),AEARSPLITCODE)).append(NUMSPLITCODE);
					betCode.append(StringUtils.join(formatBetNum(sscCompoundContent.getArea5List(),ONE_NF),AEARSPLITCODE));
					betCode.append(UNITSPLITCODE);
				}
				if(ticket.getUnits()==1){
					betType = "01";
				}else{
					betType = "02";
				}
			}else{
				throw new DataException("拆票单复式错误");
			}
		}else if(sscPlayType.equals(SscPlayType.GroupTwo)||sscPlayType.equals(SscPlayType.GroupTwoSum)||sscPlayType.equals(SscPlayType.BigSmallDoubleSingle)){
			if(sscPlayType.equals(SscPlayType.GroupTwoSum)){
				playType = "06";
				betType = "04";
			}else if(sscPlayType.equals(SscPlayType.BigSmallDoubleSingle)){
				playType = "23";
				betType = "01";
			}
			if(SalesMode.SINGLE.equals(ticket.getMode())){
				String[] ticketArr = ticket.getContent().split(Constant.TICKET_SINGLE_SEPARATOR_FOR_UNIT);
				for (String ticketStr : ticketArr) {
					for (String num : ticketStr.split(Constant.TICKET_SINGLE_SEPARATOR_FOR_NUMBER)) {
						betCode.append(Integer.valueOf(num)).append(NUMSPLITCODE);
					}
					betCode.delete(betCode.length()-1, betCode.length());
					betCode.append(UNITSPLITCODE);
				}
			}else if(SalesMode.COMPOUND.equals(ticket.getMode())){
				String[] contents = JsonUtil.getStringArray4Json(ticket.getContent());
				for (String content : contents) {
					SscCompoundContent sscCompoundContent = JsonUtil.getObject4JsonString(content,SscCompoundContent.class);
					betCode.append(StringUtils.join(formatBetNum(sscCompoundContent.getBetList(),ONE_NF),NUMSPLITCODE));
					betCode.append(UNITSPLITCODE);
				}
			}else{
				throw new DataException("拆票单复式错误");
			}
		}else{
			throw new DataException("拆票投注方式错误");
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
		return Lottery.SSC;
	}

}
