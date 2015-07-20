package com.cai310.lottery.support.tc22to5;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Tc22to5Utils {

	/**
	 * 计算无胆码单复式中奖注数
	 * 
	 * @param hitNum 一注彩票中需命中个数
	 * @param betUnhit 未命中的个数
	 * @param betHit 命中的个数
	 * @return 中奖注数
	 */
	public static int calcWinUnits(int hitNum, int betHit, int betUnhit) {
		return comp(hitNum, betHit) * comp((5 - betHit), betUnhit);
	}

	/**
	 * 计算含胆码复式中奖注数
	 * 
	 * @param hitNum 一注彩票中需命中个数
	 * @param betSpecileHit 命中胆码的个数
	 * @param betSpecileUnHit 未命中胆码总数
	 * @param betCommonHit 命中的个数
	 * @param betCommonUnhit 未命中的个数
	 * @return 中奖注数
	 */
	public static int calcWinUnits(int hitNum, int betSpecileHit, int betSpecileUnHit,
			int betCommonHit, int betCommonUnhit) {

		int BET = 5;

		// comp(RSH,RSH)*comp(RSUH,RSUH)*comp(RMH-RSH,RCH)*comp((R-RSH-(RMH-RSH)-RSUH),RCUH)
		return comp((hitNum - betSpecileHit), betCommonHit)
				* comp((BET - betSpecileUnHit - hitNum), betCommonUnhit);
	}

	/**
	 * 随机生成投注内容
	 * 
	 * @param betUtits
	 * @param danSet 胆码
	 * @return
	 */
	public static String makeRndContents(int betUtits, Set<Integer> danSet) {
		StringBuffer content = new StringBuffer();
		Set<String> tempContents = new TreeSet<String>();

		for (int i = 0; i < betUtits; i++) {
			String code = makeCode(danSet);
			int tried = 0;
			while (tempContents.contains(code) && tried < 20) {
				code = makeCode(danSet);
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
	 * @param danSets 胆内容
	 * @return
	 */
	private static String makeCode(Set<Integer> danSets) {
		Set<Integer> set = null;
		StringBuffer content = new StringBuffer();
		if (danSets.size() < 5) {
			set = new TreeSet<Integer>(danSets);
			while (set.size() < 5) {
				set.add(getRandomNum(1, 22));
			}
		} else {
			List<Integer> betList = new ArrayList<Integer>(danSets);
			Collections.shuffle(betList);
			betList = betList.subList(0, 5);
			set = new TreeSet<Integer>(betList);
		}
		
		Iterator<Integer> it = set.iterator();
		DecimalFormat df = new DecimalFormat("00");
		while (it.hasNext()) {
			content.append(df.format(it.next().intValue()));
			content.append(" ");
		}
		content.deleteCharAt(content.length()-1);
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
