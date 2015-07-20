package com.cai310.lottery.support.shrink;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;

public class ShrinkUtil {

	/**
	 * 计算AC值
	 * 
	 * @param list 号码集合
	 * @return AC值
	 */
	public static int countAc(List<Integer> list) {
		Collections.sort(list, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o2.compareTo(o1);
			}
		});
		Set<Integer> set = Sets.newHashSet();
		for (int i = 0; i < list.size(); i++) {
			int num = list.get(i);
			for (int j = i + 1; j < list.size(); j++) {
				int num2 = list.get(j);
				set.add(num - num2);
			}
		}
		return set.size() - (list.size() - 1);
	}

	/**
	 * 计算和值
	 * 
	 * @param list 号码集合
	 * @return 和值
	 */
	public static int countSum(List<Integer> list) {
		int sum = 0;
		for (Integer num : list) {
			sum += num;
		}
		return sum;
	}

	/**
	 * 计算奇偶比
	 * 
	 * @param list 号码集合
	 * @return 奇偶比（格式：1:5）
	 */
	public static String countOddEvenContrast(List<Integer> list) {
		int odd = 0;
		int even = 0;
		for (Integer num : list) {
			if (num % 2 == 0) {
				even++;
			} else {
				odd++;
			}
		}
		return String.format("%s:%s", odd, even);
	}

	/**
	 * 计算大小比
	 * 
	 * @param list 号码集合
	 * @param bigBeginNum 第一个大数
	 * @return 大小比（格式：1:5）
	 */
	public static String countBigSmallContrast(List<Integer> list, int bigBeginNum) {
		int big = 0;
		int small = 0;
		for (Integer num : list) {
			if (num >= bigBeginNum) {
				big++;
			} else {
				small++;
			}
		}
		return String.format("%s:%s", big, small);
	}

	/**
	 * 计算质合比
	 * 
	 * @param list 号码集合
	 * @param primeList 质数集合
	 * @return 质合比（格式：1:5）
	 */
	public static String countPrimeCompositeContrast(List<Integer> list, List<Integer> primeList) {
		int prime = 0;
		int composite = 0;
		for (Integer num : list) {
			if (primeList.contains(num)) {
				prime++;
			} else {
				composite++;
			}
		}
		return String.format("%s:%s", prime, composite);
	}

	/**
	 * 计算连号组数
	 * 
	 * @param list 号码集合
	 * @return 连号组数
	 */
	public static int countConsecutiveSize(List<Integer> list) {
		Collections.sort(list, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			}
		});
		int consecutiveSize = 0;
		int prevNum = list.get(0);
		for (int i = 1; i < list.size(); i++) {
			int num = list.get(i);
			if (num - prevNum == 1) {
				consecutiveSize++;
			}
			prevNum = num;
		}
		return consecutiveSize;
	}

	/**
	 * 计算个位数不相同的数目
	 * 
	 * @param list
	 * @return
	 */
	public static int countUnitDistinct(List<Integer> list) {
		Set<Integer> set = Sets.newHashSet();
		for (Integer num : list) {
			int unit = num % 10;
			set.add(unit);
		}
		return set.size();
	}
}
