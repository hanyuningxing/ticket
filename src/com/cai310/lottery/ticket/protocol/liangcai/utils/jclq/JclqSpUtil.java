package com.cai310.lottery.ticket.protocol.liangcai.utils.jclq;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.JclqConstant;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.support.RateItem;
import com.cai310.lottery.support.jclq.ItemDXF;
import com.cai310.lottery.support.jclq.ItemRFSF;
import com.cai310.lottery.support.jclq.ItemSF;
import com.cai310.lottery.support.jclq.ItemSFC;
import com.cai310.lottery.support.jclq.JclqUtil;
import com.cai310.lottery.support.jclq.PlayType;
import com.cai310.lottery.support.jclq.JclqMatchItem;
import com.cai310.lottery.support.jclq.JclqUtil;
import com.cai310.lottery.support.jclq.JclqMatchItem;
import com.cai310.lottery.ticket.common.HttpclientUtil;
import com.cai310.lottery.ticket.disassemble.jclq.JclqPrintItemObj;
import com.cai310.lottery.ticket.disassemble.jclq.JclqPrintItemObj;
import com.cai310.lottery.ticket.protocol.response.dto.JcMatchOddsList;
import com.cai310.lottery.ticket.protocol.support.jclq.JclqItem;
import com.cai310.lottery.ticket.protocol.support.jclq.JclqSpItem;
import com.cai310.lottery.ticket.protocol.support.jclq.JclqSpItemObj;
import com.cai310.utils.JsonUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public  class JclqSpUtil{
	public static void main(String[] args) {
		Ticket ticket = new Ticket();
		ticket.setTicketIndex(0);
        String responseMessage = "SF|110323301=3(2.85)/0(3.20),110323302=3(3.55),110323006=3(2.45)/0(3.85)|3*1";
		JclqSpItemObj jclqSpItemObj = new JclqSpItemObj();
		jclqSpItemObj.setIndex(ticket.getTicketIndex());
		if(StringUtils.isNotBlank(responseMessage)){
			if(responseMessage.indexOf("|")!=-1){
				String[] arr = responseMessage.split("\\|");
				String spStr = arr[1];
				String[] spArr = spStr.split(",");
				List<JclqItem> options  = null;
				JclqItem jclqItem = null;
				JclqSpItem jclqSpItem  = null;
				List <JclqSpItem> awardList = Lists.newArrayList();
				for (String str : spArr) {
					//110323301=3(2.85)/0(3.20)
					jclqSpItem = new JclqSpItem();
					String[] matchArr=str.split("=");
					String matchId = matchArr[0];
					jclqSpItem.setIntTime(Integer.valueOf(matchId.substring(0,6)));
					jclqSpItem.setLineId(Integer.valueOf(matchId.substring(6)));
					String[] matchSpArr = matchArr[1].split("/");
					options  = Lists.newArrayList();
					for (String matchSp : matchSpArr) {
						//0(3.20)
						matchSp = matchSp.replaceAll("\\)", "");
						jclqItem = new JclqItem();
						jclqItem.setValue(matchSp.split("\\(")[0].trim());
						jclqItem.setAward(Double.valueOf(matchSp.split("\\(")[1]));
						options.add(jclqItem);
					}
					jclqSpItem.setOptions(options);
					jclqSpItem.setReferenceValue(null);
					awardList.add(jclqSpItem);
				}
				jclqSpItemObj.setAwardList(awardList);
			}
		}
		int i = 0;
		
	}
	public static PlayType getPlayTypeByPlayTypeText(String playTypeStr){
		return PlayType.valueOfName(playTypeStr.trim());
	}
	//20092304490344047108_6.81_1_HH|SF>130502301=0(1.860),RFSF>130502302=-1.5_0(1.830)|2*1
	//20024733484880001427_6.19_1_RFSF|130502301=-1.5_0(1.820),130502302=-1.5_3(1.700)|2*1
	//SF|110323301=3(2.85)/0(3.20),110323302=3(3.55),110323006=3(2.45)/0(3.85)|3*1
	private static final NumberFormat FOUR_NF = new DecimalFormat("0.0000");
	public static JcMatchOddsList parseResponseSp(String responseMessage,JclqPrintItemObj jclqPrintItemObj,Ticket ticket){
		PlayType playType = PlayType.values()[jclqPrintItemObj.getPlayTypeOrdinal()];
//		@SuppressWarnings("rawtypes")
//		Map classMap = new HashMap();
//		classMap.put("awardList", JclqSpItem.class);
//		classMap.put("options", JclqItem.class);
//		JclqSpItemObj jclqSpItemObj = JsonUtil.getObject4JsonString(responseMessage, JclqSpItemObj.class, classMap);
		JclqSpItemObj jclqSpItemObj = new JclqSpItemObj();
		jclqSpItemObj.setIndex(ticket.getTicketIndex());
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
		if(StringUtils.isNotBlank(responseMessage)){
			if(responseMessage.indexOf("|")!=-1){
				if(PlayType.MIX.equals(playType)){
					String[] arr = responseMessage.split("\\|");
					//{20092304490344047108_6.81_1_HH,SF>130502301=0(1.860),RFSF>130502302=-1.5_0(1.830),2*1}
					String spStr = arr[1];
					String[] spArr = spStr.split(",");
					List<JclqItem> options  = null;
					JclqItem jclqItem = null;
					JclqSpItem jclqSpItem  = null;
					List <JclqSpItem> awardList = Lists.newArrayList();
					for (String str : spArr) {
						//SF>130502301=0(1.860)
						jclqSpItem = new JclqSpItem();
						String[] matchTemp = str.split(">");
						jclqSpItem.setPlayType(getPlayTypeByPlayTypeText(matchTemp[0].trim()));
						String[] matchArr=matchTemp[1].trim().split("=");
						String matchId = matchArr[0];
						jclqSpItem.setIntTime(Integer.valueOf(matchId.substring(0,6)));
						jclqSpItem.setLineId(Integer.valueOf(matchId.substring(6)));
						String[] matchSpArr = null;
						Double referenceValue = Double.valueOf(0);
						if(PlayType.DXF.equals(jclqSpItem.getPlayType())||PlayType.RFSF.equals(jclqSpItem.getPlayType())){
							String[] temp = matchArr[1].split("_");
							referenceValue = Double.valueOf(temp[0]);
							matchSpArr = temp[1].split("/");
						}else{
							matchSpArr = matchArr[1].split("/");
						}
						options  = Lists.newArrayList();
						for (String matchSp : matchSpArr) {
								//0(3.20)
								matchSp = matchSp.replaceAll("\\)", "");
								jclqItem = new JclqItem();
								jclqItem.setValue(matchSp.split("\\(")[0].trim());
								jclqItem.setAward(Double.valueOf(matchSp.split("\\(")[1]));
								options.add(jclqItem);
						}
						jclqSpItem.setOptions(options);
						jclqSpItem.setReferenceValue(referenceValue);
						awardList.add(jclqSpItem);
					}
					jclqSpItemObj.setAwardList(awardList);
				}else{
					String[] arr = responseMessage.split("\\|");
					String spStr = arr[1];
					String[] spArr = spStr.split(",");
					List<JclqItem> options  = null;
					JclqItem jclqItem = null;
					JclqSpItem jclqSpItem  = null;
					List <JclqSpItem> awardList = Lists.newArrayList();
					for (String str : spArr) {
						//110323301=3(2.85)/0(3.20)
						jclqSpItem = new JclqSpItem();
						String[] matchArr=str.split("=");
						String matchId = matchArr[0];
						jclqSpItem.setIntTime(Integer.valueOf(matchId.substring(0,6)));
						jclqSpItem.setLineId(Integer.valueOf(matchId.substring(6)));
						String[] matchSpArr = null;
						Double referenceValue = Double.valueOf(0);;
						if(PlayType.DXF.equals(playType)||PlayType.RFSF.equals(playType)){
							String[] temp = matchArr[1].split("_");
							referenceValue = Double.valueOf(temp[0]);
							matchSpArr = temp[1].split("/");
						}else{
							matchSpArr = matchArr[1].split("/");
						}
						options  = Lists.newArrayList();
						for (String matchSp : matchSpArr) {
								//0(3.20)
								matchSp = matchSp.replaceAll("\\)", "");
								jclqItem = new JclqItem();
								jclqItem.setValue(matchSp.split("\\(")[0].trim());
								jclqItem.setAward(Double.valueOf(matchSp.split("\\(")[1]));
								options.add(jclqItem);
						}
						jclqSpItem.setOptions(options);
						jclqSpItem.setReferenceValue(referenceValue);
						awardList.add(jclqSpItem);
					}
					jclqSpItemObj.setAwardList(awardList);
				}
			}
		}
		
		
		Map<String, Double> awardMap;
		Map<String,Map<String, Double>> spMap = Maps.newHashMap();
		for(JclqSpItem jclqSpItem:jclqSpItemObj.getAwardList()){
			String matchKey = "20"+jclqSpItem.getIntTime()+"-"+JclqUtil.formatLineId(jclqSpItem.getLineId());
			awardMap = spMap.get(matchKey);
			for (JclqItem jclqItem : jclqSpItem.getOptions()) {
				if(PlayType.MIX.equals(playType)){
					if(null==awardMap||awardMap.isEmpty()){
						awardMap = Maps.newHashMap();
						awardMap.put(getAwardValueBySpText(jclqItem.getValue(), jclqSpItem.getPlayType()),jclqItem.getAward());
					}else{
						awardMap.put(getAwardValueBySpText(jclqItem.getValue(), jclqSpItem.getPlayType()),jclqItem.getAward());
					}
				}else{
					if(null==awardMap||awardMap.isEmpty()){
						awardMap = Maps.newHashMap();
						awardMap.put(getAwardValueBySpText(jclqItem.getValue(), playType),jclqItem.getAward());
					}else{
						awardMap.put(getAwardValueBySpText(jclqItem.getValue(), playType),jclqItem.getAward());
					}
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
				if(betValue.equals(type.getCpdyjValue())){
					value = type.getValue();
				}
			}
			break;
		case DXF:
				if("3".equals(betValue)){
					value = "1";
				}else{
					value = "0";
				}
			break;
		default:
			throw new RuntimeException("玩法不正确.");
		}
		return value;
	}
	
	public static JcMatchOddsList parseResponseSp_test(String responseMessage,JclqPrintItemObj jclqPrintItemObj,Ticket ticket) throws ClientProtocolException, IOException{
		PlayType playType = PlayType.values()[jclqPrintItemObj.getPlayTypeOrdinal()];

		JclqSpItemObj jclqSpItemObj = new JclqSpItemObj();
		List <JclqSpItem> awardList = Lists.newArrayList();
		
		Map<String,String> ParamMap=new LinkedHashMap<String,String>();
		ParamMap.put("playType", playType.name());
		StringBuffer sb = new StringBuffer();
		for(JclqMatchItem jclqMatchItem:jclqPrintItemObj.getItems()){
			sb.append(jclqMatchItem.getMatchKey()+",");
		}
		ParamMap.put("matchKey",sb.delete(sb.length()-1,sb.length()).toString());
		String returnString = HttpclientUtil.Utf8HttpClientUtils("http://localhost:8080/jclq/scheme!matchOdds.action",ParamMap);
		returnString = returnString.substring(returnString.indexOf("<odds>")+"<odds>".length(),returnString.indexOf("</odds>"));
		Map<String, Object> matchRates = JsonUtil.getMap4Json(returnString);
		for(JclqMatchItem jclqMatchItem:jclqPrintItemObj.getItems()){
			awardList.add(getAwardValueByRateItem(matchRates,jclqMatchItem,playType));
		}
		jclqSpItemObj.setAwardList(awardList);
		Map<String, Double> awardMap;
		Map<String,Map<String, Double>> spMap = Maps.newHashMap();
		for(JclqSpItem jclqSpItem:jclqSpItemObj.getAwardList()){
			String matchKey = "20"+jclqSpItem.getIntTime()+"-"+JclqUtil.formatLineId(jclqSpItem.getLineId());
			awardMap = spMap.get(matchKey);
			for (JclqItem jclqItem : jclqSpItem.getOptions()) {
				if(null==awardMap||awardMap.isEmpty()){
					awardMap = Maps.newHashMap();
					awardMap.put(getAwardValueBySpText(jclqItem.getValue(), playType),jclqItem.getAward());
				}else{
					awardMap.put(getAwardValueBySpText(jclqItem.getValue(), playType),jclqItem.getAward());
				}
			}
			spMap.put(matchKey, awardMap);
		}
		JcMatchOddsList  jcMatchOddsList =  JclqUtil.bulidPrintAwardMap(spMap);
		return jcMatchOddsList;
	}
	public static JclqSpItem getAwardValueByRateItem(Map<String,Object> matchRates,JclqMatchItem jclqMatchItem,PlayType playType){
		JclqItem jclqItem = null;
		JclqSpItem jclqSpItem  =  new JclqSpItem();
		jclqSpItem.setIntTime(Integer.valueOf((jclqMatchItem.getMatchKey().split("-")[0]).substring(2)));
		jclqSpItem.setLineId(Integer.valueOf(jclqMatchItem.getMatchKey().split("-")[1]));
		List<JclqItem> options  = Lists.newArrayList(); 
		RateItem rateItem = null;
		Map<String, Object> map = JsonUtil.getMap4Json(String.valueOf(matchRates.get(jclqMatchItem.getMatchKey())));
		Map<String, RateItem> matchRate = Maps.newHashMap();
		for (String key : map.keySet()) {
			Map<String, Object> obj = JsonUtil.getMap4Json(String.valueOf(map.get(key)));
			rateItem = new RateItem();
			rateItem.setKey(key);
			rateItem.setValue(Double.valueOf(""+obj.get("value")));
			matchRate.put(key, rateItem);
		}
		if(playType.equals(PlayType.RFSF)||playType.equals(PlayType.DXF)){
			rateItem = matchRate.get("REDUCED_VALUE");
			if(null!=rateItem)jclqSpItem.setReferenceValue(rateItem.getValue());
		}
		switch (playType) {
		case SF:
			for (ItemSF type : ItemSF.values()) {
				int v = 1 << type.ordinal();
				if ((jclqMatchItem.getValue() & v) > 0) {
					jclqItem = new JclqItem();
					jclqItem.setValue(type.getValue());
					rateItem = matchRate.get(type.name());
					jclqItem.setAward(rateItem.getValue());
					options.add(jclqItem);
				}
			}
			break;
		case RFSF:
			for (ItemRFSF type : ItemRFSF.values()) {
				int v = 1 << type.ordinal();
				if ((jclqMatchItem.getValue() & v) > 0) {
					jclqItem = new JclqItem();
					jclqItem.setValue(type.getValue());
					rateItem = matchRate.get(type.name());
					jclqItem.setAward(rateItem.getValue());
					options.add(jclqItem);
				}
			}
			
			break;
		case SFC:
			for (ItemSFC type : ItemSFC.values()) {
				int v = 1 << type.ordinal();
				if ((jclqMatchItem.getValue() & v) > 0) {
					jclqItem = new JclqItem();
					jclqItem.setValue(type.getValue());
					rateItem = matchRate.get(type.name());
					jclqItem.setAward(rateItem.getValue());
					options.add(jclqItem);
				}
			}
			break;
		case DXF:
			for (ItemDXF type : ItemDXF.values()) {
				int v = 1 << type.ordinal();
				if ((jclqMatchItem.getValue() & v) > 0) {
					jclqItem = new JclqItem();
					jclqItem.setValue(type.getValue());
					rateItem = matchRate.get(type.name());
					jclqItem.setAward(rateItem.getValue());
					options.add(jclqItem);
				}
			}
			break;
		default:
			throw new RuntimeException("玩法不正确.");
		}
		jclqSpItem.setOptions(options);
		return jclqSpItem;
	}
}
