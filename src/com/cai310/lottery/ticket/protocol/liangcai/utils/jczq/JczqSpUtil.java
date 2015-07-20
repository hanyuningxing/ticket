package com.cai310.lottery.ticket.protocol.liangcai.utils.jczq;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.ClientProtocolException;

import com.cai310.lottery.JczqConstant;
import com.cai310.lottery.JczqConstant;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.support.Item;
import com.cai310.lottery.support.RateItem;
import com.cai310.lottery.support.jczq.JczqUtil;
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
import com.cai310.lottery.ticket.disassemble.jczq.JczqPrintItemObj;
import com.cai310.lottery.ticket.protocol.response.dto.JcMatchOddsList;
import com.cai310.lottery.ticket.protocol.support.jczq.JczqItem;
import com.cai310.lottery.ticket.protocol.support.jczq.JczqSpItem;
import com.cai310.lottery.ticket.protocol.support.jczq.JczqSpItemObj;
import com.cai310.lottery.ticket.protocol.support.jczq.JczqItem;
import com.cai310.lottery.ticket.protocol.support.jczq.JczqSpItem;
import com.cai310.lottery.ticket.protocol.support.jczq.JczqSpItemObj;
import com.cai310.utils.JsonUtil;
import com.cai310.utils.MD5;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public  class JczqSpUtil{
	   public static PlayType getPlayTypeByPlayTypeText(String playTypeStr){
				if("BQC".equals(playTypeStr.trim())){
					return PlayType.BQQ;
				}else if("CBF".equals(playTypeStr.trim())){
					return PlayType.BF;
				}else{
					return PlayType.valueOfName(playTypeStr.trim());
				}
	    }
		private static final NumberFormat FOUR_NF = new DecimalFormat("0.0000");
		public static JcMatchOddsList parseResponseSp(String responseMessage,JczqPrintItemObj jczqPrintItemObj,Ticket ticket){
			PlayType playType = PlayType.values()[jczqPrintItemObj.getPlayTypeOrdinal()];
//			@SuppressWarnings("rawtypes")
//			Map classMap = new HashMap();
//			classMap.put("awardList", JczqSpItem.class);
//			classMap.put("options", JczqItem.class);
//			JczqSpItemObj jczqSpItemObj = JsonUtil.getObject4JsonString(responseMessage, JczqSpItemObj.class, classMap);
			JczqSpItemObj jczqSpItemObj = new JczqSpItemObj();
			jczqSpItemObj.setIndex(ticket.getTicketIndex());
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
			if(StringUtils.isNotBlank(responseMessage)){
				if(responseMessage.indexOf("|")!=-1){
					if(PlayType.MIX.equals(playType)){
						String[] arr = responseMessage.split("\\|");
						//{20092304490344047108_6.81_1_HH,SF>130502301=0(1.860),RFSF>130502302=-1.5_0(1.830),2*1}
						String spStr = arr[1];
						String[] spArr = spStr.split(",");
						List<JczqItem> options  = null;
						JczqItem jczqItem = null;
						JczqSpItem jczqSpItem  = null;
						List <JczqSpItem> awardList = Lists.newArrayList();
						for (String str : spArr) {
							//SF>130502301=0(1.860)
							jczqSpItem = new JczqSpItem();
							String[] matchTemp = str.split(">");
							jczqSpItem.setPlayType(getPlayTypeByPlayTypeText(matchTemp[0].trim()));
							String[] matchArr=matchTemp[1].trim().split("=");
							String matchId = matchArr[0];
							jczqSpItem.setIntTime(Integer.valueOf(matchId.substring(0,6)));
							jczqSpItem.setLineId(Integer.valueOf(matchId.substring(6)));
							String[] matchSpArr = null;
							Double referenceValue = Double.valueOf(0);
							matchSpArr = matchArr[1].split("/");
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
							awardList.add(jczqSpItem);
						}
						jczqSpItemObj.setAwardList(awardList);
					}else{
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
							String[] matchSpArr = null;
							Double referenceValue = Double.valueOf(0);
							matchSpArr = matchArr[1].split("/");
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
							awardList.add(jczqSpItem);
						}
						jczqSpItemObj.setAwardList(awardList);
					}
				}
			}
			
			
			Map<String, Double> awardMap;
			Map<String,Map<String, Double>> spMap = Maps.newHashMap();
			for(JczqSpItem jczqSpItem:jczqSpItemObj.getAwardList()){
				String matchKey = "20"+jczqSpItem.getIntTime()+"-"+JczqUtil.formatLineId(jczqSpItem.getLineId());
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
		case RQSPF:
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
				case RQSPF:
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
						if("9:0".equals(betValue)){
							value = ItemBF.WIN_OTHER.getValue();
						}else if("9:9".equals(betValue)){
							value = ItemBF.DRAW_OTHER.getValue();
						}else if("0:9".equals(betValue)){
							value = ItemBF.LOSE_OTHER.getValue();
						}else if(betValue.equals(type.getText())){
							value = type.getValue();
						}
						
					}
					break;
				case BQQ:
					betValue = betValue.replaceAll("-", "");
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
