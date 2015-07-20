package com.cai310.lottery.ticket.protocol.localnew.utils.jclq;
import java.util.ArrayList;
import java.util.List;

import com.cai310.lottery.support.jclq.ItemDXF;
import com.cai310.lottery.support.jclq.ItemRFSF;
import com.cai310.lottery.support.jclq.ItemSF;
import com.cai310.lottery.support.jclq.ItemSFC;
import com.cai310.lottery.support.jclq.JclqMatchItem;
import com.cai310.lottery.support.jclq.PlayType;
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
		List <JclqPrintItem> items = Lists.newArrayList();
		PlayType playTypeBet = playType;
		for(JclqMatchItem item:itemList){
			Integer playType_ordinal = playType.ordinal();
			if(PlayType.MIX.equals(playTypeBet)){
				playType = item.getPlayType();
				if(PlayType.DXF.equals(playType)){
					playType_ordinal = 3;
				}else if(PlayType.SFC.equals(playType)){
					playType_ordinal = 2;
				}else if(PlayType.SF.equals(playType)){
					playType_ordinal = 1;
				}else if(PlayType.RFSF.equals(playType)){
					playType_ordinal = 0;
				}
			}
			
			String[] matchKeyArr  = item.getMatchKey().split("-");
			itemValue = item.getValue();
			if (PlayType.MIX.equals(playTypeBet)) {
				JclqPrintMixItem jclqPrintMixItem=new JclqPrintMixItem();
				
				jclqPrintMixItem.setBetType(playType_ordinal);
				jclqPrintMixItem.setHomeTeam(item.getMatchKey());
				jclqPrintMixItem.setGuestTeam(item.getMatchKey());
				jclqPrintMixItem.setLine(matchKeyArr[0].trim() + "" + matchKeyArr[1].trim());
				Integer[] choices = getChoices(itemValue,playType);
				jclqPrintMixItem.setChoices(choices);
				items.add(jclqPrintMixItem);
			}else{
				JclqPrintComItem jclqPrintItem = new JclqPrintComItem();
				
				jclqPrintItem.setHomeTeam(item.getMatchKey());
				jclqPrintItem.setGuestTeam(item.getMatchKey());
				jclqPrintItem.setLine(matchKeyArr[0].trim() + "" + matchKeyArr[1].trim());
				Integer[] choices=getChoices(itemValue, playType);
				jclqPrintItem.setChoices(choices);
				items.add(jclqPrintItem);
			}
		}
		return items;
	}
	
	private static Integer[] getChoices(int itemValue, PlayType playType) {
		List list = new ArrayList();
		Integer[] Choices = null;
		switch (playType) {
		case SF:
			for (ItemSF type : ItemSF.values()) {
				int v = 1 << type.ordinal();
				if ((itemValue & v) > 0) {
					if(ItemSF.LOSE.equals(type)){
						list.add(1);
					}else if(ItemSF.WIN.equals(type)){
						list.add(0);
					}
				}
			}
			Choices = (Integer[]) list.toArray(new Integer[list.size()]);
			break;
		case RFSF:
			for (ItemRFSF type : ItemRFSF.values()) {
				int v = 1 << type.ordinal();
				if ((itemValue & v) > 0) {
					if(ItemRFSF.SF_LOSE.equals(type)){
						list.add(1);
					}else if(ItemRFSF.SF_WIN.equals(type)){
						list.add(0);
					}
				}
			}
			Choices = (Integer[]) list.toArray(new Integer[list.size()]);
			break;
		case SFC:
			for (ItemSFC type : ItemSFC.values()) {
				int v = 1 << type.ordinal();
				if ((itemValue & v) > 0) {
					list.add(type.getLocalValue());
				}
			}
			Choices = (Integer[]) list.toArray(new Integer[list.size()]);
			break;
		case DXF:
			for (ItemDXF type : ItemDXF.values()) {
				int v = 1 << type.ordinal();
				if ((itemValue & v) > 0) {
					list.add(type.ordinal());
				}
			}
			Choices = (Integer[]) list.toArray(new Integer[list.size()]);
			break;
		default:
			throw new RuntimeException("玩法不正确.");
		}
		return Choices;
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
