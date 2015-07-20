package com.cai310.lottery.support.ssq;


public class SsqOrder implements Comparable<SsqOrder> {

	private String betUnits;
	private int multiple;
	private String content;
	private SsqWinUnit winUnit;
	private int betNum;

	/**
	 * 复式构造函数
	 * 
	 * @param content 内容
	 * @param multiple 倍数
	 */
	public SsqOrder(String content, int multiple) {
		// polyContent格式 1_1,2,3,4,5,6 7,8,9
		this.content = content;
		this.multiple = multiple;

		String[] arr = content.split("_");

		// 注数
		this.betNum = Integer.parseInt(arr[0]);
		// 内容
		this.betUnits = arr[1];

	}

	/**
	 * 单式构造函数
	 * 
	 * @param Content 内容
	 */
	public SsqOrder(String content) {
		// singleContent格式 1 2 3 4 5 6 7
		this.content = content;
		this.multiple = 1;

		// 注数
		this.betNum = 1;
		// 内容
		this.betUnits = this.content;
	}

	public void setMultiple(int multiple) {
		this.multiple = multiple;
	}

	public String getContent() {
		return content;
	}

	public String getBetUnits() {
		return betUnits;
	}

	public SsqWinUnit getWinUnit() {
		return winUnit;
	}

	public int getMultiple() {
		return multiple;
	}

	public int getBetNum() {
		return betNum;
	}

	public int compareTo(SsqOrder order2) {
		int result = this.getWinUnit().compareTo(order2.getWinUnit());

		// 按注数大小排序
		if (result == 0) {
			if (this.getBetNum() < order2.getBetNum()) {
				return -1;
			} else if (this.getBetNum() > order2.getBetNum()) {
				return 1;
			}
		}

		return result;
	}
}
