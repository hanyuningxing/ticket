package com.cai310.lottery.support.ssq;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class SsqUtils {

	/**
	 * 计算无胆码单复式中奖注数
	 * 
	 * @param redMustHitNum 一注彩票中需命中红球个数
	 * @param blueMustHitNum 一注彩票中需命中蓝球个数
	 * @param redUnhit 未命中红球的个数
	 * @param redHit 命中红球的个数
	 * @param blueHit 命中蓝球的个数
	 * @param blueUnhit 未命中蓝球的个数
	 * @return 中奖注数
	 */
	public static int calcWinUnits(int redMustHitNum, int blueMustHitNum, int redHit, int redUnhit, int blueHit,
			int blueUnhit) {

		int RED = 6;
		int BLUE = 1;

		return comp(redMustHitNum, redHit) * comp((RED - redMustHitNum), redUnhit) * comp(blueMustHitNum, blueHit)
				* comp((BLUE - blueMustHitNum), blueUnhit);

	}

	/**
	 * 计算含胆码复式中奖注数
	 * 
	 * @param redMustHitNum 一注彩票中需命中红球个数
	 * @param blueMustHitNum 一注彩票中需命中蓝球个数
	 * @param redSpecileHit 命中胆码红球的个数
	 * @param redSpecileUnHit 未命中胆码红球总数
	 * @param redCommonHit 命中普通红球的个数
	 * @param redCommonUnhit 未命中普通红球的个数
	 * @param blueHit 命中蓝球的个数
	 * @param blueUnhit 未命中蓝球的个数
	 * @return 中奖注数
	 */
	public static int calcWinUnits(int redMustHitNum, int blueMustHitNum, int redSpecileHit, int redSpecileUnHit,
			int redCommonHit, int redCommonUnhit, int blueHit, int blueUnhit) {

		int RED = 6;
		int BLUE = 1;

		// comp(RSH,RSH)*comp(RSUH,RSUH)*comp(RMH-RSH,RCH)*comp((R-RSH-(RMH-RSH)-RSUH),RCUH)
		return comp((redMustHitNum - redSpecileHit), redCommonHit)
				* comp((RED - redSpecileUnHit - redMustHitNum), redCommonUnhit) * comp(blueMustHitNum, blueHit)
				* comp((BLUE - blueMustHitNum), blueUnhit);
	}

	/**
	 * 随机生成投注内容
	 * 
	 * @param betUtits
	 * @param redDanSet 红球胆码
	 * @param blueDanSet 蓝球胆码
	 * @return
	 */
	public static String makeRndContents(int betUtits, Set<Integer> redDanSet, Set<Integer> blueDanSet) {
		StringBuffer content = new StringBuffer();
		Set<String> tempContents = new TreeSet<String>();

		for (int i = 0; i < betUtits; i++) {
			String code = makeCode(redDanSet, blueDanSet);
			int tried = 0;
			while (tempContents.contains(code) && tried < 20) {
				code = makeCode(redDanSet, redDanSet);
				tried++;
			}
			tempContents.add(code);
			content.append(code);
			if (i < betUtits - 1)
				content.append("\r\n");
		}
		return content.toString();
	}

	/**
	 * 组装投注内容
	 * 
	 * @param redSets 红胆
	 * @param blueSets
	 * @return
	 */
	private static String makeCode(Set<Integer> redSets, Set<Integer> blueSets) {
		Set<Integer> set = null;
		StringBuffer content = new StringBuffer();
		Integer blue = 0;
		// 红球
		if (redSets.size() < 6) {
			set = new TreeSet<Integer>(redSets);
			while (set.size() < 6) {
				set.add(getRandomNum(1, 33));
			}
		} else {
			List<Integer> redList = new ArrayList<Integer>(redSets);
			Collections.shuffle(redList);
			redList = redList.subList(0, 6);
			set = new TreeSet<Integer>(redList);
		}
		// 蓝球
		if (blueSets.size() < 1) {
			blue = getRandomNum(1, 16);
		} else {
			List<Integer> blueList = new ArrayList<Integer>(blueSets);
			Collections.shuffle(blueList);
			blue = blueList.get(0);
		}
		Iterator<Integer> it = set.iterator();
		DecimalFormat df = new DecimalFormat("00");
		while (it.hasNext()) {
			content.append(df.format(it.next().intValue()));
			content.append(" ");
		}
		content.append(df.format(blue.intValue()));
		return content.toString();
	}

	/**
	 * 计算组合数
	 */
	public static int comp(int r, int n) {
		long C = 1;
		if (r < 0) {
			return 0;
		} else {
			for (int i = n - r + 1; i <= n; i++) {
				C = C * i;
			}
			for (int i = 2; i <= r; i++) {
				C = C / i;
			}
			return (int) C;
		}
	}

	/**
	 * 取随机数
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	private static int getRandomNum(int start, int end) {
		return (int) (java.lang.Math.random() * end + start);
	}
}
