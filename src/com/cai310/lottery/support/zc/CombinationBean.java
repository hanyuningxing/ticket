package com.cai310.lottery.support.zc;

import java.io.Serializable;

/**
 * 中奖组合bean,针对九场
 * 
 * 
 * 
 */
public class CombinationBean implements Serializable {
	private static final long serialVersionUID = 8690157213871611318L;

	/** 组合的场次选项 */
	private SfzcCompoundItem[] items;

	/** 税前奖金 */
	private Double prize;

	/** 税后资金 */
	private Double prizeAfterTax;

	/** 一等奖注数 */
	private int wonUnits;


	public void setItems(SfzcCompoundItem[] items) {
		this.items = items;
	}

	/**
	 * @return {@link #prize}
	 */
	public Double getPrize() {
		return prize;
	}

	/**
	 * @param prize the {@link #prize} to set
	 */
	public void setPrize(Double prize) {
		this.prize = prize;
	}

	/**
	 * @return {@link #prizeAfterTax}
	 */
	public Double getPrizeAfterTax() {
		return prizeAfterTax;
	}

	/**
	 * @param prizeAfterTax the {@link #prizeAfterTax} to set
	 */
	public void setPrizeAfterTax(Double prizeAfterTax) {
		this.prizeAfterTax = prizeAfterTax;
	}

	public int getWonUnits() {
		return wonUnits;
	}

	public void setWonUnits(int wonUnits) {
		this.wonUnits = wonUnits;
	}

	/**
	 * 计算注数
	 * 
	 * @return 注数
	 */
	public int countUnits() {
		int units = ZcUtils.calcBetUnits(items);
		return units;
	}

	/**
	 * 获取组合详情内容310
	 * 
	 * @return
	 */
	public String compoundContent() {
		StringBuilder sb = new StringBuilder();
		for (ZcCompoundItem item : items) {
			sb.append(item.toBetString()).append(ZcUtils.getContentSpiltCode());
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	public String generateKey() {
		if (items == null || items.length < ZcUtils.SFZC9_MATCH_COUNT)
			return null;

		final char seq = '_';
		final char itemSeq = ';';
		StringBuilder sb = new StringBuilder();

		for (ZcCompoundItem item : items) {
			if (item instanceof SfzcCompoundItem) {
				sb.append(((SfzcCompoundItem) item).getLineId()).append(seq).append(item.toBetString()).append(itemSeq);
			}
		}
		if (sb.length() > 1)
			sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof CombinationBean) {
			String thisKey = this.generateKey();
			String otherKey = ((CombinationBean) obj).generateKey();
			return thisKey.equals(otherKey);
		}
		return false;
	}
}
