
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
import com.cai310.lottery.support.jclq.JclqMatchItem;
import com.cai310.lottery.support.jclq.PassMode;
import com.cai310.lottery.support.jclq.PassType;
import com.cai310.lottery.support.jclq.PlayType;
import com.cai310.lottery.support.jclq.SchemeType;
import com.cai310.lottery.ticket.protocol.local.utils.jclq.JclqContentConver;
import com.cai310.lottery.ticket.protocol.local.utils.jclq.JclqPrintItemObj;
import com.cai310.utils.DateUtil;
import com.cai310.utils.JsonUtil;
import com.google.common.collect.Sets;

public  class JCLQCPUtil extends CPUtil{
	/**
	 *  
	 * @param lottery
	 * @param playType
	 * @return
	 */
	private static final NumberFormat TWO_NF = new DecimalFormat("00");
	@Override
	public String getCpLotteryId(Lottery lottery,Byte betType){
		return "2";
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
		if(PlayType.RFSF.equals(playType)){
			return "0";
		}else if(PlayType.SF.equals(playType)){
			return "1";
		}else if(PlayType.SFC.equals(playType)){
			return "2";
		}else if(PlayType.DXF.equals(playType)){
			return "3";
		}else if(PlayType.MIX.equals(playType)){
			return "4";
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
		final List<JclqMatchItem> selectList = new ArrayList<JclqMatchItem>();
		Set<PlayType> playTypeSet = Sets.newHashSet(); 
		for (String itemStr : items) {
			map = JsonUtil.getMap4Json(itemStr);
			JclqMatchItem item = new JclqMatchItem();
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
		
		
		JclqPrintItemObj jclqPrintItemObj = new JclqPrintItemObj();
		jclqPrintItemObj.setFirstEndTime(firstEndTime);
		jclqPrintItemObj.setBetUnits(ticket.getUnits());
		jclqPrintItemObj.setItems(JclqContentConver.itemsToStrCompound(selectList, playType,firstMatchTime));
		jclqPrintItemObj.setMultiple(ticket.getMultiple());
		jclqPrintItemObj.setTicketIndex(ticketIndex);
		jclqPrintItemObj.setPassType(passType.ordinal());
		jclqPrintItemObj.setPassModeOrdinal(passMode.ordinal());
		jclqPrintItemObj.setSchemeTypeOrdinal(schemeType.ordinal());
		jclqPrintItemObj.setPlayTypeOrdinal(playType.ordinal());
		jclqPrintItemObj.setPassType(passType.ordinal());
		String printContent = JsonUtil.getJsonString4JavaPOJO(jclqPrintItemObj);
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

