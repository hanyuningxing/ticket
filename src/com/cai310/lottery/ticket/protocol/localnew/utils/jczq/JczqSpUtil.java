package com.cai310.lottery.ticket.protocol.localnew.utils.jczq;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jdom.Element;

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
import com.cai310.lottery.ticket.protocol.localnew.utils.XmlHttpUtil;
import com.cai310.lottery.ticket.protocol.response.dto.JcMatchOddsList;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.Gson;

public class JczqSpUtil {
	
	private static final String preUrl = "http://www.159cai.com/tdata/jczq/sp/";

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
	
	public static JcMatchOddsList parseResponseSp2(QueryTicket queryTicket, JczqPrintItemObj jczqPrintItemObj, Ticket ticket) {
		PlayType playType = PlayType.values()[jczqPrintItemObj.getPlayTypeOrdinal()];
		Map<String, PlayType> playMap = Maps.newHashMap();
		Map<String, Double> awardMap;
		Map<String, Map<String, Double>> spMap = Maps.newHashMap();
		Set<PlayType> playTypeSet = Sets.newHashSet();
		if (PlayType.MIX.equals(playType)) {
			for (JczqMatchItem jczqMatchItem : jczqPrintItemObj.getItems()) {
				//System.out.println(jczqMatchItem.getPlayType());
				playTypeSet.add(jczqMatchItem.getPlayType());
				playMap.put(jczqMatchItem.getMatchKey(), jczqMatchItem.getPlayType());
			}
			if (playTypeSet.size() == 1) {
				for (PlayType temp : playTypeSet) {
					playType = temp;
				}
			}
		}
		System.out.println(playMap.toString());
		if (PlayType.MIX.equals(playType)) {

			// 混合过关{"Items":[{"BetType":0,"Choices":[{"Value":0,"Odds":12.0},{"Value":1,"Odds":12.0}],"Line":"20140601301","Extra":2.5},
			// {"BetType":1,"Choices":[{"Value":0,"Odds":12.0},{"Value":1,"Odds":12.0}],"Line":"20140601302","Extra":0.0},
			// {"BetType":2,"Choices":[{"Value":0,"Odds":12.0},{"Value":1,"Odds":12.0}],"Line":"20140601303","Extra":0.0},
			// {"BetType":3,"Choices":[{"Value":0,"Odds":12.0},{"Value":1,"Odds":12.0}],"Line":"20140601304","Extra":198.5}]}
			
			//queryTicket.setOdds("{\"Items\":[{\"BetType\":5,\"Choices\":[{\"Value\":0,\"Odds\":1.23}],\"Line\":\"20151029002\",\"Extra\":0.0},"
			//		+ "{\"BetType\":0,\"Choices\":[{\"Value\":1,\"Odds\":4.56}],\"Line\":\"20151029009\",\"Extra\":0.0}]}");
			
			//Gson gson = new Gson();
			//MixSpItem spItem = gson.fromJson(queryTicket.getOdds(), MixSpItem.class);
			
			
			MixSpItem spItem = new MixSpItem();
			List<MixSpPrintItem> list = new ArrayList<MixSpPrintItem>();
			
			for (JczqMatchItem jczqMatchItem : jczqPrintItemObj.getItems()) {
				//System.out.println(jczqMatchItem.getMatchKey() + ":" + jczqMatchItem.getPlayType().ordinal());
				
				String pid = jczqMatchItem.getMatchKey().substring(2,8);
				String url = preUrl + pid + "/" + pid + jczqMatchItem.getMatchKey().substring(9)+".xml";
				System.out.println(url);
				Element element = null;
				try {
					element = XmlHttpUtil.url2Xml(url, null, "utf-8", 15);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				MixSpPrintItem item = new MixSpPrintItem();
				item.setLine(jczqMatchItem.getMatchKey().replaceAll("-", ""));
				item.setBetType(jczqMatchItem.getPlayType().ordinal());
				List<MixSpItemValue> choices = new ArrayList<MixSpItemValue>();
				
				List<Integer> choiceList = getChoices(jczqMatchItem.getValue(),jczqMatchItem.getPlayType());
				for(Integer value : choiceList){
					//System.out.println("value" + value);
					String sp = getSpValue(element,value,jczqMatchItem.getPlayType(),queryTicket.getOperateTime());
					MixSpItemValue spItemValue = new MixSpItemValue();
					spItemValue.setValue(value);
					spItemValue.setOdds(Double.parseDouble(sp));
					choices.add(spItemValue);
				}
				item.setChoices(choices);
				list.add(item);
			}
			spItem.setItems(list);
			
			for (MixSpPrintItem mixSpPrintItem : spItem.getItems()) {
				String matchKey = mixSpPrintItem.getLine().substring(0, 8) + "-" + mixSpPrintItem.getLine().substring(8);
				//System.out.println("matchKey="+matchKey);
				awardMap = spMap.get(matchKey);
				playType = playMap.get(matchKey);
				//System.out.println("play:" + playType);
				for (MixSpItemValue mixSpItemValue : mixSpPrintItem.getChoices()) {
					if (null == awardMap || awardMap.isEmpty()) {
						awardMap = Maps.newHashMap();
						awardMap.put(getAwardValueBySpText(mixSpItemValue.getValue(), playType), mixSpItemValue.getOdds());
					} else {
						awardMap.put(getAwardValueBySpText(mixSpItemValue.getValue(), playType), mixSpItemValue.getOdds());
					}
				}
				System.out.println(matchKey + "-" + playType + "-" + awardMap);
				spMap.put(matchKey, awardMap);
			}
		} else {
			// 普通过关{"Items":[{"Choices":[{"Value":1,"Odds":4.2}],"Line":"20140604061","Extra":0.0},
			// {"Choices":[{"Value":0,"Odds":2.03}],"Line":"20140604062","Extra":0.0},
			// {"Choices":[{"Value":2,"Odds":14.5}],"Line":"20140604063","Extra":0.0}]}
			//Gson gson = new Gson();
			//SpItem spItem = gson.fromJson(queryTicket.getOdds(), SpItem.class);
			
			SpItem spItem = new SpItem();
			List<SpPrintItem> list = new ArrayList<SpPrintItem>();
			
			for (JczqMatchItem jczqMatchItem : jczqPrintItemObj.getItems()) {
				//System.out.println(jczqMatchItem.getMatchKey() + ":" + jczqMatchItem.getPlayType().ordinal());
				
				String pid = jczqMatchItem.getMatchKey().substring(2,8);
				String url = preUrl + pid + "/" + pid + jczqMatchItem.getMatchKey().substring(9)+".xml";
				System.out.println(url);
				Element element = null;
				try {
					element = XmlHttpUtil.url2Xml(url, null, "utf-8", 15);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				SpPrintItem item = new SpPrintItem();
				item.setLine(jczqMatchItem.getMatchKey().replaceAll("-", ""));
				//item.setBetType(jczqMatchItem.getPlayType().ordinal());
				List<SpItemValue> choices = new ArrayList<SpItemValue>();
				//System.out.println(jczqMatchItem.getValue());
				System.out.println(PlayType.values()[jczqPrintItemObj.getPlayTypeOrdinal()]);
				PlayType pt = jczqMatchItem.getPlayType()==null?PlayType.values()[jczqPrintItemObj.getPlayTypeOrdinal()]:jczqMatchItem.getPlayType();
 				List<Integer> choiceList = getChoices(jczqMatchItem.getValue(),pt);
				for(Integer value : choiceList){
					//System.out.println("value" + value);
					String sp = getSpValue(element,value,pt,queryTicket.getOperateTime());
					SpItemValue spItemValue = new SpItemValue();
					spItemValue.setValue(value);
					spItemValue.setOdds(Double.parseDouble(sp));
					choices.add(spItemValue);
				}
				item.setChoices(choices);
				list.add(item);
			}
			spItem.setItems(list);
			System.out.println(spItem.getItems().size());
			
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
				System.out.println(matchKey + "-" + playType + "-" + awardMap);
				spMap.put(matchKey, awardMap);
			}
		}

		JcMatchOddsList jcMatchOddsList = JczqUtil.bulidPrintAwardMap(spMap);
		return jcMatchOddsList;
	}
	
	public static List<Integer> getChoices(Integer betValue, PlayType playType) {
		List<Integer> list = new ArrayList<Integer>();
		switch (playType) {
		case RQSPF:
		case SPF:
			for (ItemSPF type : ItemSPF.values()) {
				if((betValue.longValue() & (1L<<type.ordinal())) == (1L<<type.ordinal())){
					list.add(type.ordinal());
				}
			}
			break;
		case JQS:
			for (ItemJQS type : ItemJQS.values()) {
				if((betValue.longValue() & (1L<<type.ordinal())) == (1L<<type.ordinal())){
					list.add(type.ordinal());
				}
			}
			break;
		case BF:
			for (ItemBF type : ItemBF.values()) {
				if((betValue.longValue() & (1L<<type.ordinal())) == (1L<<type.ordinal())){
					list.add(type.ordinal());
				}
			}
			break;
		case BQQ:
			for (ItemBQQ type : ItemBQQ.values()) {
				if((betValue.longValue() & (1L<<type.ordinal())) == (1L<<type.ordinal())){
					list.add(type.ordinal());
				}
			}
			break;

		default:
			throw new RuntimeException("玩法不正确.");
		}
		return list;
	}
	
	public static String getSpValue(Element element,Integer value,PlayType playType,String ticketTime){
		
		String sp = "0.0";
		List<Element> list = null;
		switch (playType) {
		case RQSPF:
			list = element.getChildren("rspf");
			for(Element rspf : list){
				String time = rspf.getAttributeValue("time");
				if(ticketTime.compareToIgnoreCase(time) > 0){
					String sps = rspf.getAttributeValue("value");
					sp = sps.split(",")[value];
					//System.out.println(time + "   " + sps);
				}
			}
			break;
		case SPF:
			list = element.getChildren("spf");
			for(Element spf : list){
				String time = spf.getAttributeValue("time");
				if(ticketTime.compareToIgnoreCase(time) > 0){
					String sps = spf.getAttributeValue("value");
					sp = sps.split(",")[value];
					//System.out.println(time + "   " + sps);
				}
			}
			break;
		case JQS:
			list = element.getChildren("jqs");
			for(Element jqs : list){
				String time = jqs.getAttributeValue("time");
				if(ticketTime.compareToIgnoreCase(time) > 0){
					String sps = jqs.getAttributeValue("value");
					sp = sps.split(",")[value];
					//System.out.println(time + "   " + sps);
				}
			}
			break;
		case BF:
			list = element.getChildren("cbf");
			for(Element cbf : list){
				String time = cbf.getAttributeValue("time");
				if(ticketTime.compareToIgnoreCase(time) > 0){
					String sps = cbf.getAttributeValue("value");
					sp = sps.split(",")[value];
					//System.out.println(time + "   " + sps);
				}
			}
			break;
		case BQQ:
			list = element.getChildren("bqc");
			for(Element bqc : list){
				String time = bqc.getAttributeValue("time");
				if(ticketTime.compareToIgnoreCase(time) > 0){
					String sps = bqc.getAttributeValue("value");
					sp = sps.split(",")[value];
					//System.out.println(time + "   " + sps);
				}
			}
			break;

		default:
			throw new RuntimeException("玩法不正确.");
		}
		return sp;
	}
	
	

	public static String getAwardValueBySpText(Integer betValue, PlayType playType) {
		//System.out.println(betValue + " - " + playType);
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
