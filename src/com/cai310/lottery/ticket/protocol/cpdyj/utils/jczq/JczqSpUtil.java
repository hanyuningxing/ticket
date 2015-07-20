package com.cai310.lottery.ticket.protocol.cpdyj.utils.jczq;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.ClientProtocolException;

import com.cai310.lottery.JczqConstant;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.support.Item;
import com.cai310.lottery.support.RateItem;
import com.cai310.lottery.support.jczq.JczqUtil;
import com.cai310.lottery.support.jczq.ItemBF;
import com.cai310.lottery.support.jczq.ItemBQQ;
import com.cai310.lottery.support.jczq.ItemJQS;
import com.cai310.lottery.support.jczq.ItemSPF;
import com.cai310.lottery.support.jczq.JczqMatchItem;
import com.cai310.lottery.support.jczq.JczqUtil;
import com.cai310.lottery.support.jczq.PlayType;
import com.cai310.lottery.ticket.common.HttpclientUtil;
import com.cai310.lottery.ticket.disassemble.jczq.JczqPrintItemObj;
import com.cai310.lottery.ticket.protocol.response.dto.JcMatchOddsList;
import com.cai310.lottery.ticket.protocol.support.jczq.JczqItem;
import com.cai310.lottery.ticket.protocol.support.jczq.JczqSpItem;
import com.cai310.lottery.ticket.protocol.support.jczq.JczqSpItemObj;
import com.cai310.utils.JsonUtil;
import com.cai310.utils.MD5;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public  class JczqSpUtil{
	//SF|110323301=3(2.85)/0(3.20),110323302=3(3.55),110323006=3(2.45)/0(3.85)|3*1
	public static JcMatchOddsList parseResponseSp(String responseMessage,JczqPrintItemObj jczqPrintItemObj,Ticket ticket){
		PlayType playType = PlayType.values()[jczqPrintItemObj.getPlayTypeOrdinal()];
		////测试
//		@SuppressWarnings("rawtypes")
//		Map classMap = new HashMap();
//		classMap.put("awardList", JczqSpItem.class);
//		classMap.put("options", JczqItem.class);
//		JczqSpItemObj jczqSpItemObj = JsonUtil.getObject4JsonString(responseMessage, JczqSpItemObj.class, classMap);
		JczqSpItemObj jczqSpItemObj = new JczqSpItemObj();
		jczqSpItemObj.setIndex(ticket.getTicketIndex());
		if(StringUtils.isNotBlank(responseMessage)){
			if(responseMessage.indexOf("|")!=-1){
				String[] arr = responseMessage.split("\\|");
				String spStr = arr[1];
				String[] spArr = spStr.split(",");
				List<JczqItem> options  = null;
				JczqItem jczqItem = null;
				JczqSpItem jczqSpItem  = null;
				List <JczqSpItem> awardList = Lists.newArrayList();
				for (String str : spArr) {
					//110323301=3(2.85)/0(3.20)
					jczqSpItem = new JczqSpItem();
					String[] matchArr=str.split("=");
					String matchId = matchArr[0];
					jczqSpItem.setIntTime(Integer.valueOf(matchId.substring(0,6)));
					jczqSpItem.setLineId(Integer.valueOf(matchId.substring(6)));
					String[] matchSpArr = matchArr[1].split("/");
					options  = Lists.newArrayList();
					for (String matchSp : matchSpArr) {
						//0(3.20)
						matchSp = matchSp.replaceAll("\\)", "");
						jczqItem = new JczqItem();
						jczqItem.setValue(matchSp.split("\\(")[0].trim());
						jczqItem.setAward(Double.valueOf(matchSp.split("\\(")[1]));
						options.add(jczqItem);
					}
					jczqSpItem.setOptions(options);
					jczqSpItem.setReferenceValue(null);
					awardList.add(jczqSpItem);
				}
				jczqSpItemObj.setAwardList(awardList);
			}
		}
		Map<String, Double> awardMap;
		Map<String,Map<String, Double>> spMap = Maps.newHashMap();
		for(JczqSpItem jczqSpItem:jczqSpItemObj.getAwardList()){
			String matchKey = "20"+jczqSpItem.getIntTime()+"-"+JczqUtil.formatLineId(jczqSpItem.getLineId());
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
	public static JczqSpItem getAwardValueByRateItem(Map<String,Object> matchRates,JczqMatchItem jczqMatchItem,PlayType playType){
		JczqItem jczqItem = null;
		JczqSpItem jczqSpItem  =  new JczqSpItem();
		jczqSpItem.setIntTime(Integer.valueOf((jczqMatchItem.getMatchKey().split("-")[0]).substring(2)));
		jczqSpItem.setLineId(Integer.valueOf(jczqMatchItem.getMatchKey().split("-")[1]));
		List<JczqItem> options  = Lists.newArrayList(); 
		RateItem rateItem = null;
		Map<String, Object> map = JsonUtil.getMap4Json(String.valueOf(matchRates.get(jczqMatchItem.getMatchKey())));
		Map<String, RateItem> matchRate = Maps.newHashMap();
		for (String key : map.keySet()) {
			Map<String, Object> obj = JsonUtil.getMap4Json(String.valueOf(map.get(key)));
			rateItem = new RateItem();
			rateItem.setKey(key);
			rateItem.setValue(Double.valueOf(""+obj.get("value")));
			matchRate.put(key, rateItem);
		}
		switch (playType) {
		case SPF:
			for (ItemSPF type : ItemSPF.values()) {
				int v = 1 << type.ordinal();
				if ((jczqMatchItem.getValue() & v) > 0) {
					jczqItem = new JczqItem();
					jczqItem.setValue(type.getValue());
					rateItem = matchRate.get(type.name());
					jczqItem.setAward(rateItem.getValue());
					options.add(jczqItem);
				}
			}
			break;
		case JQS:
			for (ItemJQS type : ItemJQS.values()) {
				int v = 1 << type.ordinal();
				if ((jczqMatchItem.getValue() & v) > 0) {
					jczqItem = new JczqItem();
					jczqItem.setValue(type.getValue());
					rateItem = matchRate.get(type.name());
					jczqItem.setAward(rateItem.getValue());
					options.add(jczqItem);
				}
			}
			break;
		case BF:
			for (ItemBF type : ItemBF.values()) {
				int v = 1 << type.ordinal();
				if ((jczqMatchItem.getValue() & v) > 0) {
					jczqItem = new JczqItem();
					jczqItem.setValue(type.getValue());
					rateItem = matchRate.get(type.name());
					jczqItem.setAward(rateItem.getValue());
					options.add(jczqItem);
				}
			}
			break;
		case BQQ:
			for (ItemBQQ type : ItemBQQ.values()) {
				int v = 1 << type.ordinal();
				if ((jczqMatchItem.getValue() & v) > 0) {
					jczqItem = new JczqItem();
					jczqItem.setValue(type.getValue());
					rateItem = matchRate.get(type.name());
					jczqItem.setAward(rateItem.getValue());
					options.add(jczqItem);
				}
			}
			break;
		default:
			throw new RuntimeException("玩法不正确.");
		}
		jczqSpItem.setOptions(options);
		return jczqSpItem;
	}
	public static String getAwardValueBySpText(String betValue,PlayType playType){
		String value = null;
				switch (playType) {
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
						if("90".equals(betValue)){
							value = ItemBF.WIN_OTHER.getValue();
						}else if("99".equals(betValue)){
							value = ItemBF.DRAW_OTHER.getValue();
						}else if("09".equals(betValue)){
							value = ItemBF.LOSE_OTHER.getValue();
						}else if(betValue.equals(type.getValue())){
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
