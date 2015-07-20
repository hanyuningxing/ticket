package com.cai310.lottery.ticket.protocol.win310.utils.jczq;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.jczq.JczqUtil;
import com.cai310.lottery.support.jczq.ItemBF;
import com.cai310.lottery.support.jczq.ItemBQQ;
import com.cai310.lottery.support.jczq.ItemJQS;
import com.cai310.lottery.support.jczq.ItemSPF;
import com.cai310.lottery.support.jczq.JczqMatchItem;
import com.cai310.lottery.support.jczq.JczqUtil;
import com.cai310.lottery.support.jczq.PlayType;
import com.cai310.lottery.ticket.disassemble.jczq.JczqPrintItemObj;
import com.cai310.lottery.ticket.protocol.response.dto.JcMatchOdds;
import com.cai310.lottery.ticket.protocol.response.dto.JcMatchOddsList;
import com.cai310.lottery.ticket.protocol.response.dto.JcMatchOddsListObj;
import com.cai310.lottery.ticket.protocol.support.jczq.JczqItem;
import com.cai310.lottery.ticket.protocol.support.jczq.JczqSpItem;
import com.cai310.lottery.ticket.protocol.support.jczq.JczqSpItemObj;
import com.cai310.utils.JsonUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public  class JczqSpUtil{
    //4-002:[胜=1.64]/5-031:[胜=1.65,负=1.65]
	//混合SPF@3-005:[胜=1.4]/JQS@3-006:[5=8.5]/BF@3-008:[5:1=250]/BQC@3-037:[负胜=28]
	//周一001[胜=3.4,负=3.1]/周一002[胜=3.2]
	public static JcMatchOddsList parseResponseSp(String responseMessage,JczqPrintItemObj jczqPrintItemObj,Ticket ticket) throws DataException{
		PlayType playType = PlayType.values()[jczqPrintItemObj.getPlayTypeOrdinal()];
		JczqSpItemObj jczqSpItemObj = new JczqSpItemObj();
		jczqSpItemObj.setIndex(ticket.getTicketIndex());
		List <JczqSpItem> awardList = Lists.newArrayList();
		if(null!=jczqPrintItemObj&&!jczqPrintItemObj.getItems().isEmpty()){
			String[] spArr = responseMessage.split("/");
			List<JczqItem> options  = null;
			JczqItem jczqItem = null;
			JczqSpItem jczqSpItem  = null;
			int pos = 0;
			String matchKey = null;
			for (JczqMatchItem jczqMatchItem : jczqPrintItemObj.getItems()) {
				matchKey = JczqUtil.getDayOfWeekStr(Integer.valueOf(jczqMatchItem.getMatchKey().split("-")[0]))+JczqUtil.getLineId(jczqMatchItem.getMatchKey());
				for (String str : spArr) {//周一001[胜=3.4,负=3.1]
					str = str.replace("[", "AUX");
				    String[] match_arr = str.split("AUX");
			    	if(matchKey.equals(match_arr[0].trim())){
						jczqSpItem = new JczqSpItem();
						if(PlayType.MIX.equals(playType)){
							jczqSpItem.setPlayType(jczqMatchItem.getPlayType());
						}
						jczqSpItem.setIntTime(Integer.valueOf(jczqMatchItem.getMatchKey().split("-")[0]));
						jczqSpItem.setLineId(Integer.valueOf(JczqUtil.getLineId(jczqMatchItem.getMatchKey())));
						String temp = match_arr[1].replace("[", "");
						temp = temp.replace("]", "");
						String[] matchSpArr =temp.split(",");
						options  = Lists.newArrayList();
						for (String matchSp : matchSpArr) {
							String[] sp_arr = matchSp.split("=");
							jczqItem = new JczqItem();
							jczqItem.setValue(sp_arr[0].trim());
							jczqItem.setAward(Double.valueOf(sp_arr[1]));
							options.add(jczqItem);
						}
						jczqSpItem.setOptions(options);
						jczqSpItem.setReferenceValue(null);
						awardList.add(jczqSpItem);
					}
			    }
			}
		}
		jczqSpItemObj.setAwardList(awardList);
		Map<String, Double> awardMap;
		Map<String,Map<String, Double>> spMap = Maps.newHashMap();
		for(JczqSpItem jczqSpItem:jczqSpItemObj.getAwardList()){
			String matchKey = jczqSpItem.getIntTime()+"-"+JczqUtil.formatLineId(jczqSpItem.getLineId());
			awardMap = spMap.get(matchKey);
			for (JczqItem jczqItem : jczqSpItem.getOptions()) {
				if(PlayType.MIX.equals(playType)){
					if(null==awardMap||awardMap.isEmpty()){
						awardMap = Maps.newHashMap();
						awardMap.put(getAwardValueBySpText(jczqItem.getValue(), jczqSpItem.getPlayType()),jczqItem.getAward());
					}else{
						awardMap.put(getAwardValueBySpText(jczqItem.getValue(), jczqSpItem.getPlayType()),jczqItem.getAward());
					}
				}else{
					if(null==awardMap||awardMap.isEmpty()){
						awardMap = Maps.newHashMap();
						awardMap.put(getAwardValueBySpText(jczqItem.getValue(), playType),jczqItem.getAward());
					}else{
						awardMap.put(getAwardValueBySpText(jczqItem.getValue(), playType),jczqItem.getAward());
					}
				}
			}
			spMap.put(matchKey, awardMap);
		}
		JcMatchOddsList  jcMatchOddsList =  JczqUtil.bulidPrintAwardMap(spMap);
		return jcMatchOddsList;
	}
	public static String getAwardValueBySpText(String betValue,PlayType playType){
		String value = null;
				switch (playType) {
				case SPF:
					for (ItemSPF type : ItemSPF.values()) {
						if(betValue.equals(type.getText())){
							value = type.getValue();
						}
					}
					break;
				case RQSPF:
					for (ItemSPF type : ItemSPF.values()) {
						if(betValue.equals("让球"+type.getText())){
							value = type.getValue();
						}
					}
					break;
				case JQS:
					for (ItemJQS type : ItemJQS.values()) {
						if(betValue.equals(type.getText())){
							value = type.getValue();
						}
					}
					break;
				case BF:
					for (ItemBF type : ItemBF.values()) {
						if(betValue.equals(type.getText())){
							value = type.getValue();
						}
					}
					break;
				case BQQ:
					for (ItemBQQ type : ItemBQQ.values()) {
						if(betValue.equals(type.getText())){
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
