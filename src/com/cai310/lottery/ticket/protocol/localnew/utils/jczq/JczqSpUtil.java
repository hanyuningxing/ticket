package com.cai310.lottery.ticket.protocol.localnew.utils.jczq;

import java.util.Map;
import java.util.Set;

import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.support.jczq.ItemBF;
import com.cai310.lottery.support.jczq.ItemBQQ;
import com.cai310.lottery.support.jczq.ItemJQS;
import com.cai310.lottery.support.jczq.ItemSPF;
import com.cai310.lottery.support.jczq.JczqMatchItem;
import com.cai310.lottery.support.jczq.JczqUtil;
import com.cai310.lottery.support.jczq.PlayType;
import com.cai310.lottery.ticket.disassemble.jczq.JczqPrintItemObj;
import com.cai310.lottery.ticket.protocol.localnew.utils.QueryTicket;
import com.cai310.lottery.ticket.protocol.response.dto.JcMatchOddsList;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.Gson;

public class JczqSpUtil {

	public static JcMatchOddsList parseResponseSp(QueryTicket queryTicket, JczqPrintItemObj jczqPrintItemObj, Ticket ticket) {
		PlayType playType = PlayType.values()[jczqPrintItemObj.getPlayTypeOrdinal()];
		Map<String, PlayType> playMap = Maps.newHashMap();
		Map<String, Double> awardMap;
		Map<String, Map<String, Double>> spMap = Maps.newHashMap();
		Set<PlayType> playTypeSet = Sets.newHashSet();
		if (PlayType.MIX.equals(playType)) {
			for (JczqMatchItem jczqMatchItem : jczqPrintItemObj.getItems()) {
				playTypeSet.add(jczqMatchItem.getPlayType());
				playMap.put(jczqMatchItem.getMatchKey(), jczqMatchItem.getPlayType());
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
			Gson gson = new Gson();
			MixSpItem spItem = gson.fromJson(queryTicket.getOdds(), MixSpItem.class);
			for (MixSpPrintItem mixSpPrintItem : spItem.getItems()) {
				String matchKey = mixSpPrintItem.getLine().substring(0, 8) + "-" + mixSpPrintItem.getLine().substring(8);
				awardMap = spMap.get(matchKey);
				playType = playMap.get(matchKey);
				for (MixSpItemValue mixSpItemValue : mixSpPrintItem.getChoices()) {
					if (null == awardMap || awardMap.isEmpty()) {
						awardMap = Maps.newHashMap();
						awardMap.put(getAwardValueBySpText(mixSpItemValue.getValue(), playType), mixSpItemValue.getOdds());
					} else {
						awardMap.put(getAwardValueBySpText(mixSpItemValue.getValue(), playType), mixSpItemValue.getOdds());
					}
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
				String matchKey = spPrintItem.getLine().substring(0, 8) + "-" + spPrintItem.getLine().substring(8);
				awardMap = spMap.get(matchKey);
				for (SpItemValue spItemValue : spPrintItem.getChoices()) {
					if (null == awardMap || awardMap.isEmpty()) {
						awardMap = Maps.newHashMap();
						awardMap.put(getAwardValueBySpText(spItemValue.getValue(), playType), spItemValue.getOdds());
					} else {
						awardMap.put(getAwardValueBySpText(spItemValue.getValue(), playType), spItemValue.getOdds());
					}
				}
				spMap.put(matchKey, awardMap);
			}
		}

		JcMatchOddsList jcMatchOddsList = JczqUtil.bulidPrintAwardMap(spMap);
		return jcMatchOddsList;
	}

	public static String getAwardValueBySpText(Integer betValue, PlayType playType) {
		String value = null;
		switch (playType) {
		case RQSPF:
		case SPF:
			for (ItemSPF type : ItemSPF.values()) {
				if (betValue.equals(type.ordinal())) {
					value = type.getValue();
				}
			}
			break;
		case JQS:
			for (ItemJQS type : ItemJQS.values()) {
				if (betValue.equals(type.ordinal())) {
					value = type.getValue();
				}
			}
			break;
		case BF:
			for (ItemBF type : ItemBF.values()) {
				if (betValue.equals(type.ordinal())) {
					value = type.getValue();
				}
			}
			break;
		case BQQ:
			for (ItemBQQ type : ItemBQQ.values()) {
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
