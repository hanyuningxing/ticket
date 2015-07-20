package com.cai310.lottery.support.zc;

import java.util.ArrayList;
import java.util.List;

import com.cai310.lottery.exception.DataException;

public class CombineBet {

	public static int singleCount(byte[] betItems, int count) {
		List<byte[]> save = new ArrayList<byte[]>();
		int c = 0;
		for (int i = 0; i < betItems.length; i++) {
			if (betItems[i] == 0)
				c++;
		}
		if (count - c > 0) {
			comvertPoly(save, betItems.clone(), 0, count - c);
		} else {
			save.add(betItems);
		}
		int summult = 0;
		for (int i = 0; i < save.size(); i++) {
			int mult = 1;
			byte[] polyBet = (byte[]) save.get(i);
			for (int j = 0; j < polyBet.length; j++) {
				int size = 0;
				if ((polyBet[j] & 0x1 << 0) != 0)
					size++;
				if ((polyBet[j] & 0x1 << 1) != 0)
					size++;
				if ((polyBet[j] & 0x1 << 2) != 0)
					size++;
				if (size == 0)
					size++;
				mult *= size;
			}
			summult += mult;
		}
		return summult;
	}

	/**
	 * 包含胆拖信息的复式注码组合成N注没胆拖信息的复式，算法核心部份。
	 * 
	 * @param save
	 * @param betItems
	 * @param start
	 * @param count
	 */
	private static void comvertPoly(List<byte[]> save, byte[] betItems, int start, int count) {
		if (count == 0)
			return;
		int tempindex = -1;
		byte tempvalue = 0;
		for (int i = start; i < betItems.length; i++) {
			if (betItems[i] == 0 || (betItems[i] & (0x1 << 3)) != 0)
				continue;
			if (tempindex != -1) {
				betItems[tempindex] = tempvalue;
			}
			tempvalue = betItems[i];
			tempindex = i;
			betItems[i] = 0;
			if (count == 1) {
				byte[] bet = betItems.clone();
				save.add(bet);
			} else {
				comvertPoly(save, betItems.clone(), i + 1, count - 1);
			}
		}
	}

	/**
	 * 模糊设胆拆分为非模糊设胆
	 * 
	 * @param betItems
	 * @param danmaMinHit
	 * @return
	 * @throws DataException
	 */
	public static List<byte[]> sfzc9VagueDanCombine(SfzcCompoundItem[] betItems, int danmaMinHit) throws DataException {
		byte[] content = new byte[betItems.length];
		int danmaNum = 0;// 胆码数
		int unDanmaNum = 0;// 非胆码数
		for (int i = 0; i < content.length; i++) {
			content[i] = betItems[i].toByte();

			if ((content[i] & 8) > 0) {
				danmaNum++;
				if ((content[i] & 7) <= 0) {
					throw new DataException("第" + i + 1 + "场次无投注内容.");
				}
			} else {
				if ((content[i] & 7) > 0) {
					unDanmaNum++;
				}
			}
		}
		if (danmaNum + unDanmaNum < ((danmaNum > 0) ? 10 : 9)) {
			throw new DataException("选择的场次不对或者不能设胆.");
		}
		if (danmaMinHit > danmaNum) {
			throw new DataException("胆码至少命中数大于胆码数.");
		}
		if (danmaMinHit > 0 && danmaMinHit < (9 - unDanmaNum)) {
			throw new DataException("胆码至少命中数不正确.");
		}

		List<byte[]> save = new ArrayList<byte[]>();
		if (danmaMinHit > 0 && danmaMinHit < danmaNum) {// 模糊设胆
			for (int i = danmaMinHit; i < danmaNum; i++) {// 计算胆码非全中
				sfzc9PolyVagueDanComvert(save, content.clone(), 0, danmaNum - i);
			}
			save.add(content);// 加上胆码全中
		} else {
			save.add(content);
		}
		return save;
	}

	private static void sfzc9PolyVagueDanComvert(List<byte[]> save, byte[] betItems, int start, int count) {
		if (count == 0)
			return;
		int tempindex = -1;
		byte tempvalue = 0;
		for (int i = start; i < betItems.length; i++) {
			if ((betItems[i] & 8) == 0)
				continue;
			if (tempindex != -1) {
				betItems[tempindex] = tempvalue;
			}
			tempvalue = betItems[i];
			tempindex = i;
			betItems[i] = 0;
			if (count == 1) {
				byte[] bet = betItems.clone();
				save.add(bet);
			} else {
				sfzc9PolyVagueDanComvert(save, betItems.clone(), i + 1, count - 1);
			}
		}
	}

	/**
	 * 算法测试
	 * 
	 * @param arg
	 */
	public static void main(String[] arg) {
		List<byte[]> l = null;
		int ll = 0;
		byte[] aa = null;
		SfzcCompoundItem[] s = new SfzcCompoundItem[14];
		SfzcCompoundItem[] items = new SfzcCompoundItem[14];
		for (int i = 0; i < items.length; i++) {
			items[i] = new SfzcCompoundItem();
			if (i > 12)
				continue;
			items[i].setDraw(true);
			items[i].setGuestWin(true);
			items[i].setHomeWin(true);
			if (i > 6)
				continue;
			items[i].setShedan(true);
		}
		try {
			l = sfzc9VagueDanCombine(items, 3);
			for (int i = 0; i < l.size(); i++) {
				aa = l.get(i);

				for (int j = 0; j < aa.length; j++) {
					s[j] = new SfzcCompoundItem(aa[j], j);
				}
				ll += ZcUtils.calcBetUnits(s);
			}
		} catch (DataException e) {
			e.printStackTrace();
		}
		System.out.println(ll);
	}
}
