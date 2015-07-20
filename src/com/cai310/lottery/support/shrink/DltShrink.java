package com.cai310.lottery.support.shrink;

import java.util.List;

import com.google.common.collect.Lists;

public abstract class DltShrink extends AbstractShrink {
	private static final List<Integer> PRIME_LIST = Lists.newArrayList(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31);

	protected List<Integer> convert(String[] arr) throws ShrinkException {
		List<Integer> numList = Lists.newArrayList();
		for (String str : arr) {
			if (!str.matches("\\d{1,2}"))
				throw new ShrinkException("号码只能从01-35中选择.");
			Integer num = Integer.valueOf(str);
			if (num < 1 || num > 35)
				throw new ShrinkException("号码只能从01-35中选择.");
			if (numList.contains(num))
				throw new ShrinkException("每一注里的号码不能重复.");
			numList.add(num);
		}
		return numList;
	}

	protected String toStr(List<Integer> numList) {
		StringBuilder sb = new StringBuilder();
		for (Integer num : numList) {
			sb.append(String.format("%1$02d", num)).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	@Override
	protected List<Integer> getPrimeList() {
		return PRIME_LIST;
	}

}
