package com.cai310.lottery.support;

import java.util.List;

import com.cai310.utils.ExtensionCombCallBack;

public class UnitsCountCallBack<T extends SelectedCount> implements ExtensionCombCallBack {

	private final List<T> danList;
	private final List<T> unDanList;
	private int totalUntis = 0;

	public UnitsCountCallBack(List<T> danList, List<T> unDanList) {
		super();
		this.danList = danList;
		this.unDanList = unDanList;
	}

	public boolean run(boolean[] comb1, int m1, boolean[] comb2, int m2) {
		int units = 1;
		int count = 0;
		for (int i = 0; i < comb1.length; i++) {
			if (comb1[i]) {
				units *= danList.get(i).selectedCount();
				count++;
				if (count == m1)
					break;
			}
		}
		count = 0;
		for (int i = 0; i < comb2.length; i++) {
			if (comb2[i]) {
				units *= unDanList.get(i).selectedCount();
				count++;
				if (count == m2)
					break;
			}
		}
		totalUntis += units;
		return false;
	}

	public int getTotalUntis() {
		return totalUntis;
	}

}
