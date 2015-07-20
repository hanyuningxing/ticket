package com.cai310.lottery.support.welfare36to7;

import com.cai310.utils.MathUtils;

public class Welfare36to7Utils {

	/**
	 * 计算中奖注数
	 * 
	 * @param oneBetNums 一注彩票的号码个数
	 * @param commonMustHitNum 一注彩票中需命中基本号码的号码个数
	 * @param specialMustHitNum 一注彩票中需命中特殊号码的号码个数
	 * @param commonHits 命中基本号码的个数
	 * @param specialHits 命中特殊号码的个数
	 * @param unHits 未命中的个数
	 * @return 中奖注数
	 */
	public static int calcWinUnits(int oneBetNums, int commonMustHitNum, int specialMustHitNum, int commonHits,
			int specialHits, int unHits) {
		int mustUnHitNum = oneBetNums - commonMustHitNum - specialMustHitNum;// 一注彩票中需不命中的号码个数
		return MathUtils.comp(commonMustHitNum, commonHits) * MathUtils.comp(specialMustHitNum, specialHits)
				* MathUtils.comp(mustUnHitNum, unHits);
	}
}
