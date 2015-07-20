package com.cai310.lottery.ticket.protocol.localnew.utils;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.support.jczq.JczqMatchItem;
import com.cai310.lottery.support.jczq.PassMode;
import com.cai310.lottery.support.jczq.PassType;
import com.cai310.lottery.support.jczq.PlayType;
import com.cai310.lottery.support.jczq.SchemeType;
import com.cai310.lottery.ticket.protocol.localnew.utils.jczq.JczqContentConver;
import com.cai310.lottery.ticket.protocol.localnew.utils.jczq.JczqPrintItemObj;
import com.cai310.utils.JsonUtil;
import com.google.common.collect.Sets;
import com.google.gson.Gson;

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
		return "10011";
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
		}else if(PlayType.RQSPF.equals(playType)){
			return "1";
		}else if(PlayType.BF.equals(playType)){
			return "2";
		}else if(PlayType.JQS.equals(playType)){
			return "3";
		}else if(PlayType.BQQ.equals(playType)){
			return "4";
		}else if(PlayType.MIX.equals(playType)){
			return "99";
		}
		return null;
	}
	@Override
	public String getCpPlayType(Ticket ticket){
		if(null==ticket.getMode())return null;
		return "0";

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
		JczqPrintItemObj jczqPrintItemObj = new JczqPrintItemObj();
		jczqPrintItemObj.setItems(JczqContentConver.itemsToStrCompound(selectList, playType,firstMatchTime));
		Gson gson = new Gson();
		String printContent = gson.toJson(jczqPrintItemObj);


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
		return "";
	}
	
}
