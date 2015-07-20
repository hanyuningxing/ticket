package com.cai310.lottery.support.dczc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.cai310.utils.CombCallBack;
import com.cai310.utils.MathUtils;

public class DczcPassCombination implements Serializable {
	private static final long serialVersionUID = 7473632827807803154L;

	/** 胆码场次 */
	private final List<DczcMatchItem> danList;

	/** 拖码场次 */
	private final List<DczcMatchItem> unDanList;

	/** 胆码最小命中数 */
	private final int danMinHit;

	/** 胆码最大命中数 */
	private final int danMaxHit;

	/** 过关方式 */
	private final List<PassType> passTypes;

	// ===================================================================

	/** 胆码数目 */
	private final int danSize;

	/** 拖码数目 */
	private final int unDanSize;

	/** 总数 */
	private final int totalSize;

	private final int minPassMatchSize;

	// ===================================================================

	public DczcPassCombination(List<PassType> passTypes, List<DczcMatchItem> danCorrectList,
			List<DczcMatchItem> unDanCorrectList, int danMinHit, int danMaxHit) {
		super();
		this.danList = danCorrectList;
		this.unDanList = unDanCorrectList;
		this.danMinHit = danMinHit;
		this.danMaxHit = danMaxHit;
		this.passTypes = passTypes;

		danSize = danCorrectList.size();
		unDanSize = unDanCorrectList.size();

		totalSize = danSize + unDanSize;

		int minPassMatchSize = Integer.MAX_VALUE;// 最小过关场次数
		for (PassType passType : passTypes) {
			int minSize = passType.getPassMatchs()[0];
			if (minPassMatchSize > minSize) {
				minPassMatchSize = minSize;
				if (minPassMatchSize == 1)
					break;
			}
		}
		this.minPassMatchSize = minPassMatchSize;

		work();
	}

	// ===================================================================

	// ===================================================================

	private void work() {
		if (danSize >= danMinHit && totalSize >= minPassMatchSize) {
			for (PassType passType : passTypes) {// 循环所选的各个过关方式
				for (int needNum : passType.getPassMatchs()) {// 循环本过关方式的各个过关
					if (totalSize >= needNum) {
						int danEnd = Math.min(needNum, danMaxHit);
						for (int danNum = danMinHit; danNum <= danEnd; danNum++) {
							combDan(new ArrayList<DczcMatchItem>(), needNum, danNum);
						}
					}
				}
			}
		}
	}

	private void combDan(final List<DczcMatchItem> combList, final int n, final int m) {
		MathUtils.efficientComb(danSize, m, new CombCallBack() {

			public boolean callback(boolean[] comb, int m) {
				final List<DczcMatchItem> danCombList = new ArrayList<DczcMatchItem>();
				for (int i = 0; i < comb.length; i++) {
					if (comb[i] == true) {
						danCombList.add(danList.get(i));
						if (danCombList.size() == m)
							break;
					}
				}

				combUnDan(new ArrayList<DczcMatchItem>(danCombList), n - m, n - m);
				return false;
			}
		});
	}

	private void combUnDan(final List<DczcMatchItem> combList, final int n, final int m) {
		MathUtils.efficientComb(unDanSize, m, new CombCallBack() {
			public boolean callback(boolean[] comb, int m) {
				for (int i = 0; i < comb.length; i++) {
					if (comb[i] == true) {
						combList.add(unDanList.get(i));
						if (combList.size() == m)
							break;
					}
				}

				// TODO:
				return false;
			}
		});
	}

	// ===================================================================

}
