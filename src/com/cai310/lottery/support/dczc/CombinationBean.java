package com.cai310.lottery.support.dczc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.cai310.lottery.support.Item;
import com.cai310.lottery.support.UnitsCountUtils;

public class CombinationBean implements Serializable {
	private static final long serialVersionUID = 8690157213871611318L;

	/** 组合的场次选项 */
	private List<DczcMatchItem> items;

	/** 税前奖金 */
	private Double prize;

	/** 简捷资金 */
	private Double prizeAfterTax;

	/** 过关方式序号 */
	private int passTypeOrdinal;

	/**
	 * @return {@link #items}
	 */
	public List<DczcMatchItem> getItems() {
		return items;
	}

	/**
	 * @param items the {@link #items} to set
	 */
	public void setItems(List<DczcMatchItem> items) {
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

	/**
	 * @return {@link #passTypeOrdinal}
	 */
	public int getPassTypeOrdinal() {
		return passTypeOrdinal;
	}

	/**
	 * @param passTypeOrdinal the {@link #passTypeOrdinal} to set
	 */
	public void setPassTypeOrdinal(int passTypeOrdinal) {
		this.passTypeOrdinal = passTypeOrdinal;
	}

	/**
	 * @return 过关方式类型
	 */
	public PassType returnPassType() {
		return PassType.values()[passTypeOrdinal];
	}

	/**
	 * 计算注数
	 * 
	 * @return 注数
	 */
	public int countUnits() {
		final List<DczcMatchItem> danItems = new ArrayList<DczcMatchItem>();
		final List<DczcMatchItem> unDanItems = new ArrayList<DczcMatchItem>();
		for (DczcMatchItem item : items) {
			if (item.isDan())
				danItems.add(item);
			else
				unDanItems.add(item);

		}
		PassType passType = returnPassType();
		int units = 0;
		for (int needs : passType.getPassMatchs()) {
			units += UnitsCountUtils.countUnits(needs, danItems, unDanItems);
		}
		return units;
	}

	public <X extends Item> String toText(X[] allItems) {
		StringBuilder sb = new StringBuilder();
		final String seq = ", ";
		final char seq2 = ',';
		for (DczcMatchItem matchItem : items) {
			sb.append(matchItem.getLineId() + 1).append("→(");
			int v = matchItem.getValue();
			for (int i = 0; i < allItems.length; i++) {
				int a = 1 << i;
				if ((v & a) > 0) {
					sb.append(allItems[i].getText()).append(seq2);
					v ^= a;
					if (v == 0)
						break;
				}
			}
			sb.deleteCharAt(sb.length() - 1);
			sb.append(")").append(seq);
		}
		sb.delete(sb.length() - seq.length(), sb.length());
		return sb.toString();
	}

	public String generateKey() {
		if (items == null || items.isEmpty())
			return null;

		final char seq = 'c';
		final char itemSeq = 'b';
		StringBuilder sb = new StringBuilder();
		sb.append(passTypeOrdinal).append('a');
		for (DczcMatchItem item : items) {
			sb.append(item.getLineId()).append(seq).append(item.getValue()).append(itemSeq);
		}
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
