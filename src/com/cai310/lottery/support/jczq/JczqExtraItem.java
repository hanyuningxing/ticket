package com.cai310.lottery.support.jczq;

import java.io.Serializable;

import com.cai310.lottery.support.Item;

public class JczqExtraItem implements Serializable, Comparable<JczqExtraItem> {
	private static final long serialVersionUID = -769274396953729506L;

	private Item item;

	private Double sp;

	/**
	 * @return {@link #item}
	 */
	public Item getItem() {
		return item;
	}

	/**
	 * @param item the {@link #item} to set
	 */
	public void setItem(Item item) {
		this.item = item;
	}

	/**
	 * @return {@link #sp}
	 */
	public Double getSp() {
		return sp;
	}

	/**
	 * @param sp the {@link #sp} to set
	 */
	public void setSp(Double sp) {
		this.sp = sp;
	}

	public int compareTo(JczqExtraItem o) {
		Double sp1 = (this.getSp() != null) ? this.getSp() : Double.valueOf(0);
		Double sp2 = (o.getSp() != null) ? o.getSp() : Double.valueOf(0);
		return sp1.compareTo(sp2);
	}
}
