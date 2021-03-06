package com.cai310.lottery.support.dczc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cai310.utils.ExtensionCombCallBack;
import com.cai310.utils.MathUtils;

/**
 * 多选过关更新中奖
 * 
 */
public class DczcMultiplePassPrizeWork extends DczcPrizeWork {
	private static final long serialVersionUID = 2252895705840043960L;

	// ===================================================================

	/** 命中的胆码场次 */
	private final List<DczcMatchItem> danCorrectList;

	/** 命中的拖码场次 */
	private final List<DczcMatchItem> unDanCorrectList;

	/** 胆码最小命中数 */
	private final int danMinHit;

	/** 胆码最大命中数 */
	private final int danMaxHit;

	/** 过关方式 */
	private final List<PassType> passTypes;

	// ===================================================================

	/** 总命中数 */
	private final int totalCorrectSize;

	private final int minPassMatchSize;

	// ===================================================================

	public DczcMultiplePassPrizeWork(Map<Integer, DczcResult> resultMap, List<PassType> passTypes,
			List<DczcMatchItem> danCorrectList, List<DczcMatchItem> unDanCorrectList, int danMinHit, int danMaxHit) {
		super(resultMap);
		this.passTypes = passTypes;
		this.danCorrectList = danCorrectList;
		this.unDanCorrectList = unDanCorrectList;
		this.danMinHit = danMinHit;
		this.danMaxHit = danMaxHit;

		totalCorrectSize = this.danCorrectList.size() + this.unDanCorrectList.size();

		int minPassMatchSize = Integer.MAX_VALUE;// 最小过关场次数
		for (PassType passType : passTypes) {
			if (passType.getUnits() > 1)
				throw new RuntimeException("此算法只支持N串1的过关方式.");

			int minSize = passType.getMatchCount();
			if (minPassMatchSize > minSize) {
				minPassMatchSize = minSize;
				if (minPassMatchSize == 1)
					break;
			}
		}
		this.minPassMatchSize = minPassMatchSize;

		init();
	}

	private void init() {
		if (danCorrectList.size() >= danMinHit && totalCorrectSize >= minPassMatchSize) {
			PrizeWorkExtensionCombCallBack call = new PrizeWorkExtensionCombCallBack();
			for (PassType passType : passTypes) {
				call.setPassType(passType);
				MathUtils.efficientCombExtension(passType.getMatchCount(), danCorrectList.size(), danMinHit, danMaxHit,
						unDanCorrectList.size(), call);
			}
			won = true;
		}
	}

	// ===================================================================

	class PrizeWorkExtensionCombCallBack implements ExtensionCombCallBack {
		private PassType passType;

		public void setPassType(PassType passType) {
			this.passType = passType;
		}

		public boolean run(boolean[] comb1, int m1, boolean[] comb2, int m2) {
			List<DczcMatchItem> combList = new ArrayList<DczcMatchItem>();
			for (int i = 0; i < comb1.length; i++) {
				if (comb1[i]) {
					combList.add(danCorrectList.get(i));
					if (combList.size() == m1)
						break;
				}
			}
			int n = m1 + m2;
			for (int i = 0; i < comb2.length; i++) {
				if (comb2[i]) {
					combList.add(unDanCorrectList.get(i));
					if (combList.size() == n)
						break;
				}
			}

			handle(this.passType, combList);
			return false;
		}

	}
}
