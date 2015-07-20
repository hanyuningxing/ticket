package com.cai310.lottery.ticket.protocol.local.utils.jczq;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.support.jczq.JczqMatchItem;
import com.cai310.lottery.support.jczq.ItemBF;
import com.cai310.lottery.support.jczq.ItemBQQ;
import com.cai310.lottery.support.jczq.ItemJQS;
import com.cai310.lottery.support.jczq.ItemSPF;
import com.cai310.lottery.support.jczq.JczqUtil;
import com.cai310.lottery.support.jczq.PlayType;
import com.cai310.lottery.ticket.disassemble.jczq.JczqPrintItemObj;
import com.cai310.lottery.ticket.protocol.response.dto.JcMatchOddsList;
import com.cai310.lottery.ticket.protocol.support.jczq.JczqItem;
import com.cai310.lottery.ticket.protocol.support.jczq.JczqSpItem;
import com.cai310.lottery.ticket.protocol.support.jczq.JczqSpItemObj;
import com.cai310.utils.JsonUtil;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public  class JczqSpUtil{
	///{"awardList":[{"intTime":20111217,"lineId":20,"options":[{"award":2.8,"value":"0"}],"referenceValue":0},{"intTime":20111217,"lineId":21,"options":[{"award":1.4,"value":"3"},{"award":4.1,"value":"1"}],"referenceValue":0},{"intTime":20111217,"lineId":22,"options":[{"award":3.8,"value":"1"}],"referenceValue":0},{"intTime":20111217,"lineId":23,"options":[{"award":3.55,"value":"1"}],"referenceValue":0}],"index":0}
	///{"awardList":[{"intTime":20130909,"lineId":1,"options":[{"award":1.97,"value":"[0]3"}],"referenceValue":0},{"intTime":20130909,"lineId":2,"options":[{"award":1.75,"value":"[7]0"}],"referenceValue":1}],"index":0
	public static JcMatchOddsList parseResponseSp(String responseMessage,JczqPrintItemObj jczqPrintItemObj,Ticket ticket){
		PlayType playType = PlayType.values()[jczqPrintItemObj.getPlayTypeOrdinal()];
		if(PlayType.MIX.equals(playType)){
			Set<PlayType> playTypeSet = Sets.newHashSet(); 
			for (JczqMatchItem jczqMatchItem : jczqPrintItemObj.getItems()) {
				playTypeSet.add(jczqMatchItem.getPlayType());
			}
			if(playTypeSet.size()==1){
				for (PlayType temp : playTypeSet) {
					playType = temp;
				}
			}
		}
		@SuppressWarnings("rawtypes")
		Map classMap = new HashMap();
		classMap.put("awardList", JczqSpItem.class);
		classMap.put("options", JczqItem.class);
		JczqSpItemObj jczqSpItemObj = JsonUtil.getObject4JsonString(responseMessage, JczqSpItemObj.class, classMap);
		Map<String, Double> awardMap;
		Map<String,Map<String, Double>> spMap = Maps.newHashMap();
		for(JczqSpItem jczqSpItem:jczqSpItemObj.getAwardList()){
			String matchKey = jczqSpItem.getIntTime()+"-"+JczqUtil.formatLineId(jczqSpItem.getLineId());
			awardMap = spMap.get(matchKey);
			for (JczqItem jczqItem : jczqSpItem.getOptions()) {
				if(null==awardMap||awardMap.isEmpty()){
					awardMap = Maps.newHashMap();
					awardMap.put(getAwardValueBySpText(jczqItem.getValue(), playType),jczqItem.getAward());
				}else{
					awardMap.put(getAwardValueBySpText(jczqItem.getValue(), playType),jczqItem.getAward());
				}
			}
			spMap.put(matchKey, awardMap);
		}
		JcMatchOddsList  jcMatchOddsList =  JczqUtil.bulidPrintAwardMap(spMap);
		return jcMatchOddsList;
	}
	public static String getAwardValueBySpText(String betValue,PlayType playType){
		String value = null;
		if(PlayType.MIX.equals(playType)){
			//[0]3
			betValue = betValue.replace("[", "");
			String playStr = betValue.split("]")[0].trim();
			if("7".equals(playStr)){
				playType = PlayType.RQSPF;
			}else if("3".equals(playStr)){
				playType = PlayType.BQQ;
			}else if("2".equals(playStr)){
				playType = PlayType.JQS;
			}else if("1".equals(playStr)){
				playType = PlayType.BF;
			}else if("0".equals(playStr)){
				playType = PlayType.SPF;
			}
			String betTemp = betValue.split("]")[1];
			betValue = betTemp;
		}else{
			
		}
		switch (playType) {
		case RQSPF:
		case SPF:
			for (ItemSPF type : ItemSPF.values()) {
				if(betValue.equals(type.getValue())){
					value = type.getValue();
				}
			}
			break;
		case JQS:
			for (ItemJQS type : ItemJQS.values()) {
				if(betValue.equals(type.getValue())){
					value = type.getValue();
				}
			}
			break;
		case BF:
			for (ItemBF type : ItemBF.values()) {
				if(type.getValue().equals(betValue)){
					value = type.getValue();
				}
			}
			break;
		case BQQ:
			for (ItemBQQ type : ItemBQQ.values()) {
				if(type.getValue().equals(betValue)){
					value = type.getValue();
				}
			}
			break;
		
		default:
			throw new RuntimeException("玩法不正确.");
		}
		return value;
	}
}
