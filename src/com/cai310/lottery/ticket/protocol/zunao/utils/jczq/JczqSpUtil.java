package com.cai310.lottery.ticket.protocol.zunao.utils.jczq;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.jclq.JclqUtil;
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
				matchKey = JclqUtil.getMatchWeek(jczqMatchItem.getMatchKey())+"-"+JclqUtil.getLineId(jczqMatchItem.getMatchKey());
				for (String str : spArr) {
				    if(PlayType.MIX.equals(playType)){
						//混合SPF@3-005:[胜=1.4]
						jczqSpItem = new JczqSpItem();
						jczqSpItem.setIntTime(Integer.valueOf(jczqMatchItem.getMatchKey().split("-")[0]));
						jczqSpItem.setLineId(Integer.valueOf(JclqUtil.getLineId(jczqMatchItem.getMatchKey())));
						String[] match_arr = null;
						if(str.indexOf("@")!=-1){
							String[] _arr = str.split("@");
					    	jczqSpItem.setPlayType(getPlayTypeByPlayTypeText(_arr[0].trim()));
					    	_arr[1] = _arr[1].replace(":[", "AUX");
						    match_arr = _arr[1].split("AUX");
						}else{
					    	jczqSpItem.setPlayType(jczqMatchItem.getPlayType());
					    	str = str.replace(":[", "AUX");
						    match_arr = str.split("AUX");
						}
				    	if(matchKey.equals(match_arr[0].trim())){
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
				    }else{
						//5-031:[胜=1.65]
						str = str.replace(":[", "AUX");
					    String[] match_arr = str.split("AUX");
				    	if(matchKey.equals(match_arr[0].trim())){
							jczqSpItem = new JczqSpItem();
							jczqSpItem.setIntTime(Integer.valueOf(jczqMatchItem.getMatchKey().split("-")[0]));
							jczqSpItem.setLineId(Integer.valueOf(JclqUtil.getLineId(jczqMatchItem.getMatchKey())));
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
	public static PlayType getPlayTypeByPlayTypeText(String playTypeStr){
		if("BQC".equals(playTypeStr.trim())){
			return PlayType.BQQ;
		}else{
			return PlayType.valueOfName(playTypeStr.trim());
		}
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
