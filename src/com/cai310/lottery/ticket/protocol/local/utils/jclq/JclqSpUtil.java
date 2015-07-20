package com.cai310.lottery.ticket.protocol.local.utils.jclq;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.JclqConstant;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.support.jclq.ItemDXF;
import com.cai310.lottery.support.jclq.ItemRFSF;
import com.cai310.lottery.support.jclq.ItemSF;
import com.cai310.lottery.support.jclq.ItemSFC;
import com.cai310.lottery.support.jclq.JclqMatchItem;
import com.cai310.lottery.support.jclq.JclqUtil;
import com.cai310.lottery.support.jclq.PlayType;
import com.cai310.lottery.support.jczq.JczqMatchItem;
import com.cai310.lottery.ticket.disassemble.jclq.JclqPrintItemObj;
import com.cai310.lottery.ticket.protocol.response.dto.JcMatchOddsList;
import com.cai310.lottery.ticket.protocol.support.jclq.JclqItem;
import com.cai310.lottery.ticket.protocol.support.jclq.JclqSpItem;
import com.cai310.lottery.ticket.protocol.support.jclq.JclqSpItemObj;
import com.cai310.utils.JsonUtil;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public  class JclqSpUtil{
	///{"awardList":[{"intTime":20111217,"lineId":20,"options":[{"award":2.8,"value":"0"}],"referenceValue":0},{"intTime":20111217,"lineId":21,"options":[{"award":1.4,"value":"3"},{"award":4.1,"value":"1"}],"referenceValue":0},{"intTime":20111217,"lineId":22,"options":[{"award":3.8,"value":"1"}],"referenceValue":0},{"intTime":20111217,"lineId":23,"options":[{"award":3.55,"value":"1"}],"referenceValue":0}],"index":0}
	///{"awardList":[{"intTime":20130909,"lineId":1,"options":[{"award":1.97,"value":"[0]3"}],"referenceValue":0},{"intTime":20130909,"lineId":2,"options":[{"award":1.75,"value":"[7]0"}],"referenceValue":1}],"index":0
	public static JcMatchOddsList parseResponseSp(String responseMessage,JclqPrintItemObj jclqPrintItemObj,Ticket ticket){
		PlayType playType = PlayType.values()[jclqPrintItemObj.getPlayTypeOrdinal()];
		if(PlayType.MIX.equals(playType)){
			Set<PlayType> playTypeSet = Sets.newHashSet(); 
			for (JclqMatchItem jclqMatchItem : jclqPrintItemObj.getItems()) {
				playTypeSet.add(jclqMatchItem.getPlayType());
			}
			if(playTypeSet.size()==1){
				for (PlayType temp : playTypeSet) {
					playType = temp;
				}
			}
		}
		@SuppressWarnings("rawtypes")
		Map classMap = new HashMap();
		classMap.put("awardList", JclqSpItem.class);
		classMap.put("options", JclqItem.class);
		JclqSpItemObj jclqSpItemObj = JsonUtil.getObject4JsonString(responseMessage, JclqSpItemObj.class, classMap);
		Map<String, Double> awardMap;
		Map<String,Map<String, Double>> spMap = Maps.newHashMap();
		for(JclqSpItem jclqSpItem:jclqSpItemObj.getAwardList()){
			String matchKey = jclqSpItem.getIntTime()+"-"+JclqUtil.formatLineId(jclqSpItem.getLineId());
			awardMap = spMap.get(matchKey);
			for (JclqItem jclqItem : jclqSpItem.getOptions()) {
				if(null==awardMap||awardMap.isEmpty()){
					awardMap = Maps.newHashMap();
					awardMap.put(getAwardValueBySpText(jclqItem.getValue(), playType),jclqItem.getAward());
				}else{
					awardMap.put(getAwardValueBySpText(jclqItem.getValue(), playType),jclqItem.getAward());
				}
			}
			if(null==awardMap||awardMap.isEmpty()){
				awardMap = Maps.newHashMap();
				awardMap.put(JclqConstant.REFERENCE_VALUE_KEY,jclqSpItem.getReferenceValue());
			}else{
				awardMap.put(JclqConstant.REFERENCE_VALUE_KEY,jclqSpItem.getReferenceValue());
			}
			spMap.put(matchKey, awardMap);
		}
		JcMatchOddsList  jcMatchOddsList =  JclqUtil.bulidPrintAwardMap(spMap);
		return jcMatchOddsList;
	}
	public static String getAwardValueBySpText(String betValue,PlayType playType){
		String value = null;
		if(PlayType.MIX.equals(playType)){
			//[0]3
			betValue = betValue.replace("[", "");
			String playStr = betValue.split("]")[0].trim();
			playType = PlayType.values()[Integer.valueOf(playStr)];
			String betTemp = betValue.split("]")[1];
			betValue = betTemp;
		}else{
			
		}
		switch (playType) {
		case SF:
			for (ItemSF type : ItemSF.values()) {
				if(betValue.equals(type.getValue())){
					value = type.getValue();
				}
			}
			break;
		case RFSF:
			for (ItemRFSF type : ItemRFSF.values()) {
				if(betValue.equals(type.getValue())){
					value = type.getValue();
				}
			}
			break;
		case SFC:
			for (ItemSFC type : ItemSFC.values()) {
				if(betValue.equals(type.getValue())){
					value = type.getValue();
				}
			}
			break;
		case DXF:
			for (ItemDXF type : ItemDXF.values()) {
				if(betValue.equals(type.getValue())){
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
