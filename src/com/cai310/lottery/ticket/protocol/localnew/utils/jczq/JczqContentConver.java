package com.cai310.lottery.ticket.protocol.localnew.utils.jczq;

import java.util.ArrayList;
import java.util.List;

import com.cai310.lottery.support.jczq.ItemBF;
import com.cai310.lottery.support.jczq.ItemBQQ;
import com.cai310.lottery.support.jczq.ItemJQS;
import com.cai310.lottery.support.jczq.ItemSPF;
import com.cai310.lottery.support.jczq.JczqMatchItem;
import com.cai310.lottery.support.jczq.PlayType;
import com.google.common.collect.Lists;

public class JczqContentConver {
	/**
	 * 复式：将场次内容转为发送格式
	 * 
	 * @param itemList
	 * @param playType
	 * @return
	 */
	public static List<JczqPrintItem> itemsToStrCompound(List<JczqMatchItem> itemList, PlayType playType, String firstMatchTime) {
		int itemValue = 0;// 场次选择值

		List<JczqPrintItem> items = Lists.newArrayList();
		PlayType playTypeBet = playType;
		for (JczqMatchItem item : itemList) {
			Integer playType_ordinal = playType.ordinal();
			if (PlayType.MIX.equals(playTypeBet)) {
				playType = item.getPlayType();
				if (PlayType.BQQ.equals(playType)) {
					playType_ordinal = 4;
				} else if (PlayType.JQS.equals(playType)) {
					playType_ordinal = 3;
				} else if (PlayType.BF.equals(playType)) {
					playType_ordinal = 2;
				} else if (PlayType.RQSPF.equals(playType)) {
					playType_ordinal = 1;
				} else if (PlayType.SPF.equals(playType)) {
					playType_ordinal = 0;
				}
			}
			String[] matchKeyArr = item.getMatchKey().split("-");
			itemValue = item.getValue();
			if (PlayType.MIX.equals(playTypeBet)) {
				JczqPrintMixItem jczqPrintMixItem = new JczqPrintMixItem();
				jczqPrintMixItem.setBetType(playType_ordinal);
				jczqPrintMixItem.setHomeTeam(item.getMatchKey());
				jczqPrintMixItem.setGuestTeam(item.getMatchKey());
				jczqPrintMixItem.setLine(matchKeyArr[0].trim() + "" + matchKeyArr[1].trim());
				Integer[] Choices = getChoices(itemValue,playType);
				jczqPrintMixItem.setChoices(Choices);
				items.add(jczqPrintMixItem);
			}else{
				JczqPrintComItem jczqPrintItem = new JczqPrintComItem();
				jczqPrintItem.setHomeTeam(item.getMatchKey());
				jczqPrintItem.setGuestTeam(item.getMatchKey());
				jczqPrintItem.setLine(matchKeyArr[0].trim() + "" + matchKeyArr[1].trim());
				Integer[] Choices = getChoices(itemValue,playType);
				jczqPrintItem.setChoices(Choices);
				items.add(jczqPrintItem);
			}
		}
		return items;
	}

	private static Integer[] getChoices(int itemValue, PlayType playType) {
		Integer[] Choices =null;
		List list = new ArrayList();
		switch (playType) {
		case SPF:
		case RQSPF:
			for (ItemSPF type : ItemSPF.values()) {
				int v = 1 << type.ordinal();
				if ((itemValue & v) > 0) {
					list.add(type.ordinal());
				}
			}
			Choices = (Integer[]) list.toArray(new Integer[list.size()]);
			break;
		case JQS:
			for (ItemJQS type : ItemJQS.values()) {
				int v = 1 << type.ordinal();
				if ((itemValue & v) > 0) {
					list.add(type.ordinal());
				}
			}
			Choices = (Integer[]) list.toArray(new Integer[list.size()]);
			break;
		case BF:
			for (ItemBF type : ItemBF.values()) {
				int v = 1 << type.ordinal();
				if ((itemValue & v) > 0) {
					list.add(type.ordinal());
				}
			}
			Choices = (Integer[]) list.toArray(new Integer[list.size()]);
			break;
		case BQQ:
			for (ItemBQQ type : ItemBQQ.values()) {
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
	private static String reText(String text, String insertCode) {
		if (text.length() == 2) {
			return text.charAt(0) + insertCode + text.charAt(1);
		} else {
			return text;
		}
	}

}
