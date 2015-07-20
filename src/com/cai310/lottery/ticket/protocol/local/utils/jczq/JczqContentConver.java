package com.cai310.lottery.ticket.protocol.local.utils.jczq;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.cai310.lottery.support.jczq.JczqMatch;
import com.cai310.lottery.support.jczq.JczqMatchItem;
import com.cai310.lottery.support.jczq.ItemBF;
import com.cai310.lottery.support.jczq.ItemSPF;
import com.cai310.lottery.support.jczq.JczqUtil;
import com.cai310.lottery.support.jczq.PassType;
import com.cai310.lottery.support.jczq.PlayType;
import com.cai310.lottery.support.jczq.ItemJQS;
import com.cai310.lottery.support.jczq.ItemBQQ;
import com.cai310.utils.DateUtil;
import com.google.common.collect.Lists;

public class JczqContentConver {
	/**
	 * 复式：将场次内容转为发送格式
	 * @param itemList
	 * @param playType
	 * @return 
	 */ 
	public static List <JczqPrintItem> itemsToStrCompound(List<JczqMatchItem> itemList,PlayType playType,String firstMatchTime){
		int itemValue = 0;//场次选择值
		List <JczqItem> options;
		List <JczqPrintItem> items = Lists.newArrayList();
		PlayType playTypeBet = playType;
		for(JczqMatchItem item:itemList){
			String mixBetValue = "";
			Integer playType_ordinal = playType.ordinal();
			if(PlayType.MIX.equals(playTypeBet)){
				playType = item.getPlayType();
				if(PlayType.RQSPF.equals(playType)){
					playType_ordinal = 7;
				}else if(PlayType.BQQ.equals(playType)){
					playType_ordinal = 3;
				}else if(PlayType.JQS.equals(playType)){
					playType_ordinal = 2;
				}else if(PlayType.BF.equals(playType)){
					playType_ordinal = 1;
				}else if(PlayType.SPF.equals(playType)){
					playType_ordinal = 0;
				}
				mixBetValue="["+playType_ordinal+"]";
			}
			String[] matchKeyArr  = item.getMatchKey().split("-");
			JczqPrintItem jczqPrintItem = new JczqPrintItem();
			jczqPrintItem.setConcede(0);
			jczqPrintItem.setGuestName(item.getMatchKey());
			jczqPrintItem.setHomeName(item.getMatchKey());
			jczqPrintItem.setIntTime(Integer.valueOf(matchKeyArr[0].trim()));
			jczqPrintItem.setLineId(Integer.valueOf(matchKeyArr[1].trim()));
			jczqPrintItem.setMatchTime(firstMatchTime);
			jczqPrintItem.setSubBetType(playType_ordinal);
			jczqPrintItem.setShedan(item.isDan());
			options = Lists.newArrayList();
			JczqItem jczqItem;
			itemValue = item.getValue();
			switch (playType) {
			case SPF:
			case RQSPF:
				for (ItemSPF type : ItemSPF.values()) {
					int v = 1 << type.ordinal();
					if ((itemValue & v) > 0) {
						jczqItem = new JczqItem();
						jczqItem.setValue(mixBetValue+type.getValue());
						options.add(jczqItem);
					}
				}
				break;
			case JQS:
				for (ItemJQS type : ItemJQS.values()) {
					int v = 1 << type.ordinal();
					if ((itemValue & v) > 0) {
						jczqItem = new JczqItem();
						jczqItem.setValue(mixBetValue+type.getValue());
						options.add(jczqItem);
					}
				}
				break;
			case BF:
				for (ItemBF type : ItemBF.values()) {
					int v = 1 << type.ordinal();
					if ((itemValue & v) > 0) {
						jczqItem = new JczqItem();
						jczqItem.setValue(mixBetValue+type.getValue());
						options.add(jczqItem);
					}
				}
				break;
			case BQQ:
				for (ItemBQQ type : ItemBQQ.values()) {
					int v = 1 << type.ordinal();
					if ((itemValue & v) > 0) {
						jczqItem = new JczqItem();
						jczqItem.setValue(mixBetValue+type.getValue());
						options.add(jczqItem);
					}
				}
				break;
			default:
				throw new RuntimeException("玩法不正确.");
			}
			jczqPrintItem.setOptions(options);
			items.add(jczqPrintItem);
		}
		return items;
	}
	

	
	/**
	 * 
	 * @param text
	 * @param insertCode
	 * @return
	 */
	private static String reText(String text,String insertCode){
		if(text.length()==2){
			return text.charAt(0)+insertCode+text.charAt(1);
		}else{
			return text;
		}
	}
	
	
}
