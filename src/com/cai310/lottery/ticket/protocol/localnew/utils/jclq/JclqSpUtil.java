package com.cai310.lottery.ticket.protocol.localnew.utils.jclq;

import java.util.Map;
import java.util.Set;

import com.cai310.lottery.JclqConstant;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.support.jclq.ItemDXF;
import com.cai310.lottery.support.jclq.ItemRFSF;
import com.cai310.lottery.support.jclq.ItemSF;
import com.cai310.lottery.support.jclq.ItemSFC;
import com.cai310.lottery.support.jclq.JclqMatchItem;
import com.cai310.lottery.support.jclq.JclqUtil;
import com.cai310.lottery.support.jclq.PlayType;
import com.cai310.lottery.ticket.disassemble.jclq.JclqPrintItemObj;
import com.cai310.lottery.ticket.protocol.localnew.utils.QueryTicket;
import com.cai310.lottery.ticket.protocol.response.dto.JcMatchOddsList;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.Gson;

public class JclqSpUtil {
	public static JcMatchOddsList parseResponseSp(QueryTicket queryTicket,
			JclqPrintItemObj jclqPrintItemObj, Ticket ticket) {
		PlayType playType = PlayType.values()[jclqPrintItemObj
				.getPlayTypeOrdinal()];
		Map<String, PlayType> playMap = Maps.newHashMap();
		Map<String, Double> awardMap;
		Map<String, Map<String, Double>> spMap = Maps.newHashMap();
		Set<PlayType> playTypeSet = Sets.newHashSet();
		if (PlayType.MIX.equals(playType)) {
			for (JclqMatchItem jclqMatchItem : jclqPrintItemObj.getItems()) {
				playTypeSet.add(jclqMatchItem.getPlayType());
				playMap.put(jclqMatchItem.getMatchKey(),
						jclqMatchItem.getPlayType());
			}
			if (playTypeSet.size() == 1) {
				for (PlayType temp : playTypeSet) {
					playType = temp;
				}
			}
		}
		if (PlayType.MIX.equals(playType)) {

			// 混合过关{"Items":[{"BetType":0,"Choices":[{"Value":0,"Odds":12.0},{"Value":1,"Odds":12.0}],"Line":"20140601301","Extra":2.5},
			// {"BetType":1,"Choices":[{"Value":0,"Odds":12.0},{"Value":1,"Odds":12.0}],"Line":"20140601302","Extra":0.0},
			// {"BetType":2,"Choices":[{"Value":0,"Odds":12.0},{"Value":1,"Odds":12.0}],"Line":"20140601303","Extra":0.0},
			// {"BetType":3,"Choices":[{"Value":0,"Odds":12.0},{"Value":1,"Odds":12.0}],"Line":"20140601304","Extra":198.5}]}
			//{"Items":[{"BetType":1,"Choices":[{"Value":0,"Odds":2.07}],"Line":"20141105302","Extra":0.0},
			//{"BetType":0,"Choices":[{"Value":0,"Odds":1.75}],"Line":"20141105303","Extra":2.5},
			//{"BetType":2,"Choices":[{"Value":0,"Odds":4.2}],"Line":"20141105304","Extra":0.0},
			//{"BetType":3,"Choices":[{"Value":0,"Odds":1.75}],"Line":"20141105305","Extra":205.5}]}
			Gson gson = new Gson();
			MixSpItem spItem = gson.fromJson(queryTicket.getOdds(),
					MixSpItem.class);
			for (MixSpPrintItem mixSpPrintItem : spItem.getItems()) {
				String matchKey = mixSpPrintItem.getLine().substring(0, 8)
						+ "-" + mixSpPrintItem.getLine().substring(8);
				awardMap = spMap.get(matchKey);
				playType = playMap.get(matchKey);
				for (MixSpItemValue mixSpItemValue : mixSpPrintItem
						.getChoices()) {
					if (null == awardMap || awardMap.isEmpty()) {
						awardMap = Maps.newHashMap();
						awardMap.put(
								getAwardValueBySpText(
										mixSpItemValue.getValue(), playType),
								mixSpItemValue.getOdds());
					} else {
						awardMap.put(
								getAwardValueBySpText(
										mixSpItemValue.getValue(), playType),
								mixSpItemValue.getOdds());
					}
				}
				if (null == awardMap || awardMap.isEmpty()) {
					awardMap = Maps.newHashMap();
					awardMap.put(JclqConstant.REFERENCE_VALUE_KEY,
							mixSpPrintItem.getExtra());
				} else {
					awardMap.put(JclqConstant.REFERENCE_VALUE_KEY,
							mixSpPrintItem.getExtra());
				}
				spMap.put(matchKey, awardMap);
			}
		} else {
			// 普通过关{"Items":[{"Choices":[{"Value":1,"Odds":4.2}],"Line":"20140604061","Extra":0.0},
			// {"Choices":[{"Value":0,"Odds":2.03}],"Line":"20140604062","Extra":0.0},
			// {"Choices":[{"Value":2,"Odds":14.5}],"Line":"20140604063","Extra":0.0}]}
			Gson gson = new Gson();
			SpItem spItem = gson.fromJson(queryTicket.getOdds(), SpItem.class);
			for (SpPrintItem spPrintItem : spItem.getItems()) {
				String matchKey = spPrintItem.getLine().substring(0, 8) + "-"
						+ spPrintItem.getLine().substring(8);
				awardMap = spMap.get(matchKey);
				for (SpItemValue spItemValue : spPrintItem.getChoices()) {
					if (null == awardMap || awardMap.isEmpty()) {
						awardMap = Maps.newHashMap();
						awardMap.put(
								getAwardValueBySpText(spItemValue.getValue(),
										playType), spItemValue.getOdds());
					} else {
						awardMap.put(
								getAwardValueBySpText(spItemValue.getValue(),
										playType), spItemValue.getOdds());
					}
				}
				if (null == awardMap || awardMap.isEmpty()) {
					awardMap = Maps.newHashMap();
					awardMap.put(JclqConstant.REFERENCE_VALUE_KEY,
							spPrintItem.getExtra());
				} else {
					awardMap.put(JclqConstant.REFERENCE_VALUE_KEY,
							spPrintItem.getExtra());
				}
				spMap.put(matchKey, awardMap);
			}
		}

		JcMatchOddsList jcMatchOddsList = JclqUtil.bulidPrintAwardMap(spMap);
		return jcMatchOddsList;
		// @SuppressWarnings("rawtypes")
		// Map classMap = new HashMap();
		// classMap.put("awardList", JclqSpItem.class);
		// classMap.put("options", JclqItem.class);
		// JclqSpItemObj jclqSpItemObj =
		// JsonUtil.getObject4JsonString(responseMessage, JclqSpItemObj.class,
		// classMap);
		// Map<String, Double> awardMap;
		// Map<String,Map<String, Double>> spMap = Maps.newHashMap();
		// for(JclqSpItem jclqSpItem:jclqSpItemObj.getAwardList()){
		// String matchKey =
		// jclqSpItem.getIntTime()+"-"+JclqUtil.formatLineId(jclqSpItem.getLineId());
		// awardMap = spMap.get(matchKey);
		// for (JclqItem jclqItem : jclqSpItem.getOptions()) {
		// if(null==awardMap||awardMap.isEmpty()){
		// awardMap = Maps.newHashMap();
		// awardMap.put(getAwardValueBySpText(jclqItem.getValue(),
		// playType),jclqItem.getAward());
		// }else{
		// awardMap.put(getAwardValueBySpText(jclqItem.getValue(),
		// playType),jclqItem.getAward());
		// }
		// }
		// if(null==awardMap||awardMap.isEmpty()){
		// awardMap = Maps.newHashMap();
		// awardMap.put(JclqConstant.REFERENCE_VALUE_KEY,jclqSpItem.getReferenceValue());
		// }else{
		// awardMap.put(JclqConstant.REFERENCE_VALUE_KEY,jclqSpItem.getReferenceValue());
		// }
		// spMap.put(matchKey, awardMap);
		// }
		// JcMatchOddsList jcMatchOddsList = JclqUtil.bulidPrintAwardMap(spMap);
		// return jcMatchOddsList;
	}

	public static String getAwardValueBySpText(Integer betValue,
			PlayType playType) {
		String value = null;
		switch (playType) {
		case SF:
			if(1==betValue){
				value = ItemSF.LOSE.getValue();
			}else if(0==betValue){
				value = ItemSF.WIN.getValue();
			}else{
				throw new RuntimeException("玩法不正确.");
			}
			break;
		case RFSF:
			if(1==betValue){
				value = ItemRFSF.SF_LOSE.getValue();
			}else if(0==betValue){
				value = ItemRFSF.SF_WIN.getValue();
			}else{
				throw new RuntimeException("玩法不正确.");
			}
			break;
		case SFC:
			for (ItemSFC type : ItemSFC.values()) {
				if (betValue.equals(type.getLocalValue())) {
					value = type.getValue();
				}
			}
			break;
		case DXF:
			for (ItemDXF type : ItemDXF.values()) {
				if (betValue.equals(type.ordinal())) {
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
