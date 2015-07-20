package com.cai310.lottery.support.shrink;

import java.util.List;


public class ShrinkBean {
	/** AC值 */
	private List<Integer> acList;

	/** 首位奇偶(即开奖号中的最小号) */
	private OddEvenType firstType;

	/** 末位奇偶 (即开奖号中的最大号) */
	private OddEvenType lastType;

	/** 和值下限 */
	private int minSum;

	/** 和值上限 */
	private int maxSum;

	/** 奇偶比 */
	private List<String> oddEvenContrastList;
	/** 大小比 */
	private List<String> bigSmallContrastList;

	/** 质合比 */
	private List<String> primeCompositeContrastList;

	/** 连号组数 */
	private List<Integer> consecutiveSizeList;

	/** 个位数不相同的数目 */
	private List<Integer> unitDistinctList;

	/** 原始内容 */
	private String content;

	public List<Integer> getAcList() {
		return acList;
	}

	public void setAcList(List<Integer> acList) {
		this.acList = acList;
	}

	public OddEvenType getFirstType() {
		return firstType;
	}

	public void setFirstType(OddEvenType firstType) {
		this.firstType = firstType;
	}

	public OddEvenType getLastType() {
		return lastType;
	}

	public void setLastType(OddEvenType lastType) {
		this.lastType = lastType;
	}

	public int getMinSum() {
		return minSum;
	}

	public void setMinSum(int minSum) {
		this.minSum = minSum;
	}

	public int getMaxSum() {
		return maxSum;
	}

	public void setMaxSum(int maxSum) {
		this.maxSum = maxSum;
	}

	public List<String> getOddEvenContrastList() {
		return oddEvenContrastList;
	}

	public void setOddEvenContrastList(List<String> oddEvenContrastList) {
		this.oddEvenContrastList = oddEvenContrastList;
	}

	public List<String> getBigSmallContrastList() {
		return bigSmallContrastList;
	}

	public void setBigSmallContrastList(List<String> bigSmallContrastList) {
		this.bigSmallContrastList = bigSmallContrastList;
	}

	public List<String> getPrimeCompositeContrastList() {
		return primeCompositeContrastList;
	}

	public void setPrimeCompositeContrastList(List<String> primeCompositeContrastList) {
		this.primeCompositeContrastList = primeCompositeContrastList;
	}

	public List<Integer> getConsecutiveSizeList() {
		return consecutiveSizeList;
	}

	public void setConsecutiveSizeList(List<Integer> consecutiveSizeList) {
		this.consecutiveSizeList = consecutiveSizeList;
	}

	public List<Integer> getUnitDistinctList() {
		return unitDistinctList;
	}

	public void setUnitDistinctList(List<Integer> unitDistinctList) {
		this.unitDistinctList = unitDistinctList;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
