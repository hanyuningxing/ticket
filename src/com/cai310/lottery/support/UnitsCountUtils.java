package com.cai310.lottery.support;

import java.util.List;

import com.cai310.utils.CombCallBack;
import com.cai310.utils.MathUtils;

public final class UnitsCountUtils {
    /**
     * 
     * @param <T>
     * @param list 选择的列表
     * @param needArr 需要组合的数目 如【2，3】，就是要在列表里面组出需要的数目的组合
     * @return
     */
	public static <T extends SelectedCount> int countUnits(final List<T> list, int[] needArr) {
		final int[] betUnitArr = { 0 };
		CombCallBack call = new CombCallBack() {

			public boolean callback(boolean[] comb, int m) {
				int units = 1;
				int c = 0;
				for (int i = 0; i < comb.length; i++) {
					if (comb[i]) {
						units *= list.get(i).selectedCount();
						c++;
						if (c == m || units == 0)
							break;
					}
				}
				betUnitArr[0] += units;
				return false;
			}
		};
		for (int m : needArr) {
			MathUtils.efficientComb(list.size(), m, call);
		}
		return betUnitArr[0];
	}
	
	public static int countUnits(int count, int need) {
		final int[] betUnitArr = { 0 };
		CombCallBack call = new CombCallBack() {

			public boolean callback(boolean[] comb, int m) {				
				betUnitArr[0]++;
				return false;
			}
		};
		MathUtils.efficientComb(count, need, call);
		return betUnitArr[0];
	}

	public static <T extends SelectedCount> int countUnits(int needs, final List<T> danList, final List<T> unDanList) {
		if (danList.size() + unDanList.size() >= needs) {
			UnitsCountCallBack<T> call = new UnitsCountCallBack<T>(danList, unDanList);
			MathUtils.efficientCombExtension(needs, danList.size(), unDanList.size(), call);
			return call.getTotalUntis();
		}
		return 0;
	}

	public static <T extends SelectedCount> int countUnits(final int needs, final List<T> danList, final int danMin,
			final int danMax, final List<T> unDanList) {
		if (danList.size() + unDanList.size() >= needs) {
			UnitsCountCallBack<T> call = new UnitsCountCallBack<T>(danList, unDanList);
			MathUtils.efficientCombExtension(needs, danList.size(), danMin, danMax, unDanList.size(), call);
			return call.getTotalUntis();
		}
		return 0;
	}
}
