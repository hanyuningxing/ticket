package com.cai310.lottery.support;

public class JcWonMatchItem {

	/** 场次标识 */
	private String matchKey;

	/** 是否取消 */
	private boolean cancel;

	/** 奖金值 */
	private Double award;

	/** 开奖结果 */
	private Item resultItem;

	/** 选择的选项数目 */
	private int selectCount;

	public String getMatchKey() {
		return matchKey;
	}

	public void setMatchKey(String matchKey) {
		this.matchKey = matchKey;
	}

	public boolean isCancel() {
		return cancel;
	}

	public void setCancel(boolean cancel) {
		this.cancel = cancel;
	}

	public Double getAward() {
		return award;
	}

	public void setAward(Double award) {
		this.award = award;
	}

	public Item getResultItem() {
		return resultItem;
	}

	public void setResultItem(Item resultItem) {
		this.resultItem = resultItem;
	}

	public int getSelectCount() {
		return selectCount;
	}

	public void setSelectCount(int selectCount) {
		this.selectCount = selectCount;
	}

}
