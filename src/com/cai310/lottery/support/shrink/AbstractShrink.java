package com.cai310.lottery.support.shrink;

import static com.cai310.lottery.support.shrink.ShrinkUtil.countAc;
import static com.cai310.lottery.support.shrink.ShrinkUtil.countBigSmallContrast;
import static com.cai310.lottery.support.shrink.ShrinkUtil.countConsecutiveSize;
import static com.cai310.lottery.support.shrink.ShrinkUtil.countOddEvenContrast;
import static com.cai310.lottery.support.shrink.ShrinkUtil.countPrimeCompositeContrast;
import static com.cai310.lottery.support.shrink.ShrinkUtil.countUnitDistinct;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.common.collect.Lists;

public abstract class AbstractShrink implements Shrink {

	protected abstract int getBigBeginNum();

	protected abstract List<Integer> getPrimeList();

	protected List<ShrinkProcessor> buildShrinkProcessorList(final ShrinkBean bean) {
		List<ShrinkProcessor> shrinkProcessorList = Lists.newArrayList();
		if (bean.getAcList() != null && !bean.getAcList().isEmpty()) {
			shrinkProcessorList.add(new ShrinkProcessor() {
				@Override
				public boolean check(List<Integer> list) {
					Integer rs = countAc(list);
					return bean.getAcList().contains(rs);
				}
			});
		}
		if (bean.getFirstType() != null) {
			shrinkProcessorList.add(new ShrinkProcessor() {
				@Override
				public boolean check(List<Integer> list) {
					Collections.sort(list, new Comparator<Integer>() {
						@Override
						public int compare(Integer o1, Integer o2) {
							return o1.compareTo(o2);
						}
					});
					int first = list.get(0);
					return bean.getFirstType().check(first);
				}
			});
		}
		if (bean.getLastType() != null) {
			shrinkProcessorList.add(new ShrinkProcessor() {
				@Override
				public boolean check(List<Integer> list) {
					Collections.sort(list, new Comparator<Integer>() {
						@Override
						public int compare(Integer o1, Integer o2) {
							return o1.compareTo(o2);
						}
					});
					int last = list.get(list.size() - 1);
					return bean.getLastType().check(last);
				}
			});
		}
		if (bean.getMinSum() > 0 || bean.getMaxSum() > 0) {
			shrinkProcessorList.add(new ShrinkProcessor() {
				@Override
				public boolean check(List<Integer> list) {
					Collections.sort(list, new Comparator<Integer>() {
						@Override
						public int compare(Integer o1, Integer o2) {
							return o1.compareTo(o2);
						}
					});
					int last = list.get(list.size() - 1);
					return bean.getLastType().check(last);
				}
			});
		}
		if (bean.getOddEvenContrastList() != null && !bean.getOddEvenContrastList().isEmpty()) {
			shrinkProcessorList.add(new ShrinkProcessor() {
				@Override
				public boolean check(List<Integer> list) {
					String rs = countOddEvenContrast(list);
					return bean.getOddEvenContrastList().contains(rs);
				}
			});
		}
		if (bean.getBigSmallContrastList() != null && !bean.getBigSmallContrastList().isEmpty()) {
			shrinkProcessorList.add(new ShrinkProcessor() {
				@Override
				public boolean check(List<Integer> list) {
					String rs = countBigSmallContrast(list, getBigBeginNum());
					return bean.getBigSmallContrastList().contains(rs);
				}
			});
		}
		if (bean.getPrimeCompositeContrastList() != null && !bean.getPrimeCompositeContrastList().isEmpty()) {
			shrinkProcessorList.add(new ShrinkProcessor() {
				@Override
				public boolean check(List<Integer> list) {
					String rs = countPrimeCompositeContrast(list, getPrimeList());
					return bean.getPrimeCompositeContrastList().contains(rs);
				}
			});
		}
		if (bean.getConsecutiveSizeList() != null && !bean.getConsecutiveSizeList().isEmpty()) {
			shrinkProcessorList.add(new ShrinkProcessor() {
				@Override
				public boolean check(List<Integer> list) {
					Integer rs = countConsecutiveSize(list);
					return bean.getConsecutiveSizeList().contains(rs);
				}
			});
		}
		if (bean.getUnitDistinctList() != null && !bean.getUnitDistinctList().isEmpty()) {
			shrinkProcessorList.add(new ShrinkProcessor() {
				@Override
				public boolean check(List<Integer> list) {
					Integer rs = countUnitDistinct(list);
					return bean.getUnitDistinctList().contains(rs);
				}
			});
		}
		return shrinkProcessorList;
	}

}
