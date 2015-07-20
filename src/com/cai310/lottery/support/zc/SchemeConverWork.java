package com.cai310.lottery.support.zc;

import java.util.ArrayList;
import java.util.List;

import com.cai310.utils.ExtensionCombCallBack;
import com.cai310.utils.MathUtils;

/**
 * 去除模糊设胆及设胆的转换工具類
 * 
 */
public class SchemeConverWork<T extends ZcCompoundItem> {

	// ===================================================================

	/** 命中的胆码场次 */
	private final List<T> danCorrectList;

	/** 命中的拖码场次 */
	private final List<T> unDanCorrectList;

	/** 胆码最小命中数 */
	private final int danMinHit;

	/** 胆码最大命中数 */
	private final int danMaxHit;

	/** 要组合的个数 */
	private final int combinNum;

	/** 转换后的小复式集合 */
	private List<List<T>> resultList;

	// ===================================================================

	public List<List<T>> getResultList() {
		return resultList;
	}

	/**
	 * 
	 * @param combinNum 组合项个数
	 * @param danCorrectList 设胆项集合
	 * @param unDanCorrectList 未设胆项集合
	 * @param danMinHit 最小命中胆码小于零相当于不设
	 * @param danMaxHit 最大命中胆码 小于零相当于不设
	 */
	public SchemeConverWork(int combinNum, List<T> danCorrectList, List<T> unDanCorrectList, int danMinHit,
			int danMaxHit) {
		this.resultList = new ArrayList<List<T>>();
		this.danCorrectList = danCorrectList;
		this.unDanCorrectList = unDanCorrectList;
		this.danMinHit = danMinHit;
		this.danMaxHit = danMaxHit;
		this.combinNum = combinNum;
		init();
	}

	private void init() {
		PrizeWorkExtensionCombCallBack call = new PrizeWorkExtensionCombCallBack();
		MathUtils.efficientCombExtension(combinNum, danCorrectList.size(), danMinHit, danMaxHit, unDanCorrectList
				.size(), call);
	}

	// ===================================================================

	class PrizeWorkExtensionCombCallBack implements ExtensionCombCallBack {
		public boolean run(boolean[] comb1, int m1, boolean[] comb2, int m2) {
			List<T> combList = new ArrayList<T>();
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

			resultList.add(combList);
			return false;
		}

	}
}
