package com.cai310.lottery.support.pl;

public enum LotteryPlayType {

	PL3("排列3"), PL5("排列5");

	private final String text;

	private LotteryPlayType(String text) {
		this.text = text;
	}

	/**
	 * @return {@link #text}
	 */
	public String getText() {
		return text;
	}
}
