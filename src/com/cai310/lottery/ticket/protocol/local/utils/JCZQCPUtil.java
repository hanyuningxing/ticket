package com.cai310.lottery.ticket.protocol.local.utils;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.support.jczq.ItemBF;
import com.cai310.lottery.support.jczq.ItemBQQ;
import com.cai310.lottery.support.jczq.ItemJQS;
import com.cai310.lottery.support.jczq.ItemSPF;
import com.cai310.lottery.support.jczq.JczqMatchItem;
import com.cai310.lottery.support.jczq.PassMode;
import com.cai310.lottery.support.jczq.PassType;
import com.cai310.lottery.support.jczq.PlayType;
import com.cai310.lottery.support.jczq.SchemeType;
import com.cai310.lottery.ticket.protocol.local.utils.jczq.JczqContentConver;
import com.cai310.lottery.ticket.protocol.local.utils.jczq.JczqPrintItemObj;
import com.cai310.utils.DateUtil;
import com.cai310.utils.JsonUtil;
import com.google.common.collect.Sets;

public  class JCZQCPUtil extends CPUtil{
	/**
	 *  
	 * @param lottery
	 * @param playType
	 * @return
	 */
	private static final NumberFormat TWO_NF = new DecimalFormat("00");
	@Override
	public String getCpLotteryId(Lottery lottery,Byte betType){
		return "1";
	}
	@Override
	public String getCpBetType(Ticket ticket){
		Map<String, Object> contentMap = getTicketContentMap(ticket);
		PlayType playType = PlayType.values()[Integer.valueOf(String.valueOf(contentMap.get("playTypeOrdinal")))];
		String[] items = JsonUtil.getStringArray4Json(String.valueOf(contentMap.get("items")));
		Set<PlayType> playTypeSet = Sets.newHashSet(); 
		for (String itemStr : items) {
			map = JsonUtil.getMap4Json(itemStr);
			if(PlayType.MIX.equals(playType)){
				playTypeSet.add( PlayType.valueOfName(String.valueOf(map.get("playType"))));
			}
		}
		if(playTypeSet.size()==1){
			for (PlayType temp : playTypeSet) {
				playType = temp;
			}
		}
		Byte betType = ticket.getBetType();
		if(PlayType.SPF.equals(playType)){
			return "0";
		}else if(PlayType.BF.equals(playType)){
			return "1";
		}else if(PlayType.JQS.equals(playType)){
			return "2";
		}else if(PlayType.BQQ.equals(playType)){
			return "3";
		}else if(PlayType.MIX.equals(playType)){
			return "4";
		}else if(PlayType.RQSPF.equals(playType)){
			return "7";
		}
		return null;
	}
	@Override
	public String getCpPlayType(Ticket ticket){
		if(null==ticket.getMode())return null;
		if(SalesMode.SINGLE.equals(ticket.getMode())){
			return "1";
		}else if(SalesMode.COMPOUND.equals(ticket.getMode())){
			if(ticket.getUnits()==1){
				return "1";
			}else{
				return "0";
			}
		}
		return null;

	}
	@Override
	public String getBetContent(Ticket ticket){
		Map<String, Object> contentMap = getTicketContentMap(ticket);
		String[] items = JsonUtil.getStringArray4Json(String.valueOf(contentMap.get("items")));
		int itemValue = 0;//场次选择值
		PlayType playType = PlayType.values()[Integer.valueOf(String.valueOf(contentMap.get("playTypeOrdinal")))];
		PassType passType = PassType.values()[Integer.valueOf(String.valueOf(contentMap.get("passTypeOrdinal")))];
		PassMode passMode = PassMode.values()[Integer.valueOf(String.valueOf(contentMap.get("passModeOrdinal")))];
		SchemeType schemeType = SchemeType.values()[Integer.valueOf(String.valueOf(contentMap.get("schemeTypeOrdinal")))];
		final List<JczqMatchItem> selectList = new ArrayList<JczqMatchItem>();
		Set<PlayType> playTypeSet = Sets.newHashSet(); 
		for (String itemStr : items) {
			map = JsonUtil.getMap4Json(itemStr);
			JczqMatchItem item = new JczqMatchItem();
			item.setMatchKey(String.valueOf(map.get("matchKey")));
			item.setDan(Boolean.valueOf(String.valueOf(map.get("dan"))));
			item.setValue(Integer.valueOf(String.valueOf(map.get("value"))).intValue());
			if(PlayType.MIX.equals(playType)){
				playTypeSet.add( PlayType.valueOfName(String.valueOf(map.get("playType"))));
				item.setPlayType(PlayType.valueOfName(String.valueOf(map.get("playType"))));
			}else{
				item.setPlayType(playType);
			}
			selectList.add(item);
		}
		if(playTypeSet.size()==1){
			for (PlayType temp : playTypeSet) {
				playType = temp;
			}
		}
		
		String firstMatchTime = String.valueOf(contentMap.get("firstMatchTime"));
		String firstEndTime = String.valueOf(contentMap.get("firstEndTime"));
		
		Integer ticketIndex = Integer.valueOf(String.valueOf(contentMap.get("ticketIndex")));
		
		
		JczqPrintItemObj jczqPrintItemObj = new JczqPrintItemObj();
		jczqPrintItemObj.setFirstEndTime(firstEndTime);
		jczqPrintItemObj.setBetUnits(ticket.getUnits());
		jczqPrintItemObj.setItems(JczqContentConver.itemsToStrCompound(selectList, playType,firstMatchTime));
		jczqPrintItemObj.setMultiple(ticket.getMultiple());
		jczqPrintItemObj.setTicketIndex(ticketIndex);
		jczqPrintItemObj.setPassType(passType.ordinal());
		jczqPrintItemObj.setPassModeOrdinal(passMode.ordinal());
		jczqPrintItemObj.setSchemeTypeOrdinal(schemeType.ordinal());
		jczqPrintItemObj.setPlayTypeOrdinal(playType.ordinal());
		jczqPrintItemObj.setPassType(passType.ordinal());
		String printContent = JsonUtil.getJsonString4JavaPOJO(jczqPrintItemObj);
		return printContent;
	}
	@Override
	public String getSpecialFlag(Ticket ticket) {
		Byte betType = ticket.getBetType();
		PlayType playType = PlayType.values()[betType];
		Map<String, Object> map = JsonUtil.getMap4Json(ticket.getContent());
		PassType passType = PassType.values()[Integer.valueOf(String.valueOf(map.get("passType")))];
		return passType.ordinal()+"";
		
		
	}
	@Override
	public String getUpdatePeriodNumber(Ticket ticket) {
		return null;
	}
	
}
