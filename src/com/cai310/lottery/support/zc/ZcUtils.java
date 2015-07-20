package com.cai310.lottery.support.zc;

import java.util.Collections;
import java.util.List;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.exception.DataException;

public class ZcUtils {

	public static final int SFZC14_MATCH_COUNT = 14;
	public static final int SFZC9_MATCH_COUNT = 14;
	public static final int SFZC9_MATCH_MINSELECT_COUNT = 9;
	private static final int LCZC_MATCH_COUNT = 6;
	private static final int SCZC_MATCH_COUNT = 4;	
	
	
	/*************************************************************************************************/

	/**胜负任选9场单式不选项字符*/
	public static char getSfzc9NoSelectedCode() {
		return '#';
	}

	/**复式方案分割符*/
	public static char getContentSpiltCode() {
		return '-';
	}

	/**胜负任选9场方案最小命中与投注内容分割符*/
	public static char getDanMinHitContentSpiltCode() {
		return ';';
	}
	
	/**
	 * 根据彩种获取对阵数
	 * 
	 * @param lottery
	 * @return
	 * @throws DataException
	 */
	public static int getMatchCount(Lottery lottery) throws DataException {
		switch (lottery) {
		case SFZC:
			return SFZC14_MATCH_COUNT;
		case LCZC:
			return LCZC_MATCH_COUNT;
		case SCZC:
			return SCZC_MATCH_COUNT;
		}
		throw new DataException("获取对阵场次未匹配到彩种.");
	}

	/**
	 * 计算投注注数（复式）
	 * 
	 * @param lotteryType
	 * @param items
	 * @return 方案注数
	 */
	public static int calcBetUnits(ZcCompoundItem[] items) {
		int totalBet = 1;
		int selectedCount = 0;
		byte[] bet = new byte[items.length];
		for (int i = 0; i < items.length; i++) {
			ZcCompoundItem betItem = items[i];
			bet[i] = items[i].toByte();
			if (betItem.selectedCount() == 0)
				continue;
			totalBet = totalBet * betItem.selectedCount();
			selectedCount++;
		}

		return totalBet;
	}

	/**
	 * 获取标准的胜负彩(14场次)投注内容（主要针对组合后的任九只有9个项） 补足14项
	 * 
	 * @param betItems
	 * @return
	 */
	public static <X extends ZcCompoundItem> ZcCompoundItem[] getStandardSfItems(List<X> betItems) {
		ZcCompoundItem[] itemArr = new ZcCompoundItem[SFZC14_MATCH_COUNT];
		for (int i = 0; i < itemArr.length; i++) {
			itemArr[i] = new SfzcCompoundItem(i);
		}
		Collections.sort(betItems);
		SfzcCompoundItem item;
		for (int i = 0; i < betItems.size(); i++) {
			item = (SfzcCompoundItem) betItems.get(i);
			itemArr[item.getLineId()] = item;
		}
		return itemArr;
	}
}
