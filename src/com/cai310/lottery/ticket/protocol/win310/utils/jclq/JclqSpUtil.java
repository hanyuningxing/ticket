package com.cai310.lottery.ticket.protocol.win310.utils.jclq;

import java.util.List;
import java.util.Map;

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
import com.cai310.lottery.ticket.protocol.response.dto.JcMatchOddsList;
import com.cai310.lottery.ticket.protocol.support.jclq.JclqItem;
import com.cai310.lottery.ticket.protocol.support.jclq.JclqSpItem;
import com.cai310.lottery.ticket.protocol.support.jclq.JclqSpItemObj;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class JclqSpUtil {
	public static JcMatchOddsList parseResponseSp(String responseMessage, JclqPrintItemObj jclqPrintItemObj, Ticket ticket) {
		PlayType playType = PlayType.values()[jclqPrintItemObj.getPlayTypeOrdinal()];
		JclqSpItemObj jclqSpItemObj = new JclqSpItemObj();
		jclqSpItemObj.setIndex(ticket.getTicketIndex());
		List<JclqSpItem> awardList = Lists.newArrayList();
		if (null != jclqPrintItemObj && !jclqPrintItemObj.getItems().isEmpty()) {
			String[] spArr = responseMessage.split("/");
			List<JclqItem> options = null;
			JclqItem jclqItem = null;
			JclqSpItem jclqSpItem = null;
			int pos = 0;
			String matchKey = null;
			for (JclqMatchItem jclqMatchItem : jclqPrintItemObj.getItems()) {
				matchKey = JclqUtil.getDayOfWeekStr(Integer.valueOf(jclqMatchItem.getMatchKey().split("-")[0])) + JclqUtil.getLineId(jclqMatchItem.getMatchKey());
				for (String str : spArr) {// [周三301[0.0|客1-5=2.0，主6-10=2.0]]
					str = str.replace("[", "AUX");
					String[] match_arr = str.split("AUX");
					if (matchKey.equals(match_arr[0].trim())) {
						jclqSpItem = new JclqSpItem();
						if (PlayType.MIX.equals(playType)) {
							jclqSpItem.setPlayType(jclqMatchItem.getPlayType());
						}
						jclqSpItem.setIntTime(Integer.valueOf(jclqMatchItem.getMatchKey().split("-")[0]));
						jclqSpItem.setLineId(Integer.valueOf(JclqUtil.getLineId(jclqMatchItem.getMatchKey())));
						String[] match_rf = match_arr[1].split("\\|");
						jclqSpItem.setReferenceValue(Double.valueOf(match_rf[0]));
						String temp = match_rf[1].replace("]", "");
						String[] matchSpArr = temp.split(",");
						options = Lists.newArrayList();
						for (String matchSp : matchSpArr) {
							String[] sp_arr = matchSp.split("=");
							jclqItem = new JclqItem();
							if (PlayType.RFSF.equals(playType)) {
								jclqItem.setValue("让分"+sp_arr[0].trim());
							} else {
								jclqItem.setValue(sp_arr[0].trim());
							}
							jclqItem.setAward(Double.valueOf(sp_arr[1]));
							options.add(jclqItem);
						}
						jclqSpItem.setOptions(options);
						awardList.add(jclqSpItem);

					}
				}
			}
		}
		jclqSpItemObj.setAwardList(awardList);
		Map<String, Double> awardMap;
		Map<String, Map<String, Double>> spMap = Maps.newHashMap();
		for (JclqSpItem jclqSpItem : jclqSpItemObj.getAwardList()) {
			String matchKey = jclqSpItem.getIntTime() + "-" + JclqUtil.formatLineId(jclqSpItem.getLineId());
			awardMap = spMap.get(matchKey);
			for (JclqItem jclqItem : jclqSpItem.getOptions()) {
				if (PlayType.MIX.equals(playType)) {
					if (null == awardMap || awardMap.isEmpty()) {
						awardMap = Maps.newHashMap();
						awardMap.put(getAwardValueBySpText(jclqItem.getValue(), jclqSpItem.getPlayType()), jclqItem.getAward());
					} else {
						awardMap.put(getAwardValueBySpText(jclqItem.getValue(), jclqSpItem.getPlayType()), jclqItem.getAward());
					}
				} else {
					if (null == awardMap || awardMap.isEmpty()) {
						awardMap = Maps.newHashMap();
						awardMap.put(getAwardValueBySpText(jclqItem.getValue(), playType), jclqItem.getAward());
					} else {
						awardMap.put(getAwardValueBySpText(jclqItem.getValue(), playType), jclqItem.getAward());
					}
				}
			}
			if (null == awardMap || awardMap.isEmpty()) {
				awardMap = Maps.newHashMap();
				awardMap.put(JclqConstant.REFERENCE_VALUE_KEY, jclqSpItem.getReferenceValue());
			} else {
				awardMap.put(JclqConstant.REFERENCE_VALUE_KEY, jclqSpItem.getReferenceValue());
			}
			spMap.put(matchKey, awardMap);
		}
		JcMatchOddsList jcMatchOddsList = JclqUtil.bulidPrintAwardMap(spMap);
		return jcMatchOddsList;
	}

	public static PlayType getPlayTypeByPlayTypeText(String playTypeStr) {
		if ("FC".equals(playTypeStr.trim()))
			return PlayType.SFC;
		return PlayType.valueOfName(playTypeStr.trim());
	}

	public static String getAwardValueBySpText(String betValue, PlayType playType) {
		String value = null;

		switch (playType) {
		case SF:
			if (betValue.equals("胜")) {
				value = ItemSF.WIN.getValue();
			} else if (betValue.equals("负")) {
				value = ItemSF.LOSE.getValue();
			}
			break;
		case RFSF:
			String winValue = "让分胜";
			String loseValue = "让分负";
			if (betValue.equals(winValue)) {
				value = ItemRFSF.SF_WIN.getValue();
			} else if (betValue.equals(loseValue)) {
				value = ItemRFSF.SF_LOSE.getValue();
			}
			break;
		case SFC:
			for (ItemSFC type : ItemSFC.values()) {
				String valueTemp = type.name().replace("HOME", "胜");
				valueTemp = valueTemp.replace("GUEST", "负");
				if (type.equals(ItemSFC.GUEST26) || type.equals(ItemSFC.HOME26)) {
					valueTemp = valueTemp + "分以上";
				} else {
					valueTemp = valueTemp.replace("_", "-");
					valueTemp = valueTemp + "分";
				}
				String betVal = betValue.replace("客", "负");
				betVal = betVal.replace("主", "胜");
				if (type.equals(ItemSFC.GUEST26) || type.equals(ItemSFC.HOME26)) {
					betVal = betVal.replace("+", "分以上");
				} else {
					betVal = betVal + "分";
				}
				if (betVal.equals(valueTemp)) {
					value = type.getValue();
				}
			}
			break;
		case DXF:
			for (ItemDXF type : ItemDXF.values()) {
				if (betValue.equals(type.getText())) {
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
