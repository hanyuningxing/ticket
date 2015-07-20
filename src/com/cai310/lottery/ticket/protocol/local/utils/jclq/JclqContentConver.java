package com.cai310.lottery.ticket.protocol.local.utils.jclq;
import java.util.List;
import java.util.Map;

import com.cai310.lottery.support.jclq.ItemRFSF;
import com.cai310.lottery.support.jclq.ItemSF;
import com.cai310.lottery.support.jclq.ItemSFC;
import com.cai310.lottery.support.jclq.ItemDXF;
import com.cai310.lottery.support.jclq.JclqMatch;
import com.cai310.lottery.support.jclq.JclqMatchItem;
import com.cai310.lottery.support.jclq.PassType;
import com.cai310.lottery.support.jclq.PlayType;
import com.cai310.lottery.support.jczq.JczqMatchItem;
import com.cai310.utils.DateUtil;
import com.google.common.collect.Lists;

public class JclqContentConver {
	/**
	 * 复式：将场次内容转为发送格式
	 * @param itemList
	 * @param playType
	 * @return 
	 */ 
	public static List <JclqPrintItem> itemsToStrCompound(List<JclqMatchItem> itemList,PlayType playType,String firstMatchTime){
		int itemValue = 0;//场次选择值
		List <JclqItem> options;
		List <JclqPrintItem> items = Lists.newArrayList();
		PlayType playTypeBet = playType;
		for(JclqMatchItem item:itemList){
			String mixBetValue = "";
			Integer playType_ordinal = playType.ordinal();
			if(PlayType.MIX.equals(playTypeBet)){
				playType = item.getPlayType();
				playType_ordinal = playType.ordinal();
				mixBetValue="["+playType_ordinal+"]";
			}
			
			String[] matchKeyArr  = item.getMatchKey().split("-");
			JclqPrintItem jclqPrintItem = new JclqPrintItem();
			Double referenceValue  =0d;
			if(PlayType.RFSF.equals(playType)){
				referenceValue = Double.valueOf(0);
			}else if(PlayType.DXF.equals(playType)){
				referenceValue = Double.valueOf(0);
			}
			jclqPrintItem.setReferenceValue(referenceValue);
			jclqPrintItem.setGuestName(item.getMatchKey());
			jclqPrintItem.setHomeName(item.getMatchKey());
			jclqPrintItem.setIntTime(Integer.valueOf(matchKeyArr[0].trim()));
			jclqPrintItem.setLineId(Integer.valueOf(matchKeyArr[1].trim()));
			jclqPrintItem.setMatchTime(firstMatchTime);
			jclqPrintItem.setSubBetType(playType_ordinal);
			jclqPrintItem.setShedan(item.isDan());
			options = Lists.newArrayList();
			JclqItem jclqItem;
			itemValue = item.getValue();
			switch (playType) {
			case SF:
				for (ItemSF type : ItemSF.values()) {
					int v = 1 << type.ordinal();
					if ((itemValue & v) > 0) {
						jclqItem = new JclqItem();
						jclqItem.setValue(mixBetValue+type.getValue());
						options.add(jclqItem);
					}
				}
				break;
			case RFSF:
				for (ItemRFSF type : ItemRFSF.values()) {
					int v = 1 << type.ordinal();
					if ((itemValue & v) > 0) {
						jclqItem = new JclqItem();
						jclqItem.setValue(mixBetValue+type.getValue());
						options.add(jclqItem);
					}
				}
				break;
			case SFC:
				for (ItemSFC type : ItemSFC.values()) {
					int v = 1 << type.ordinal();
					if ((itemValue & v) > 0) {
						jclqItem = new JclqItem();
						jclqItem.setValue(mixBetValue+type.getValue());
						options.add(jclqItem);
					}
				}
				break;
			case DXF:
				for (ItemDXF type : ItemDXF.values()) {
					int v = 1 << type.ordinal();
					if ((itemValue & v) > 0) {
						jclqItem = new JclqItem();
						jclqItem.setValue(mixBetValue+type.getValue());
						options.add(jclqItem);
					}
				}
				break;
			default:
				throw new RuntimeException("玩法不正确.");
			}
			jclqPrintItem.setOptions(options);
			items.add(jclqPrintItem);
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
