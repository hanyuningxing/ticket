package com.cai310.lottery.support.dczc;

import java.io.Serializable;

import com.cai310.lottery.support.Item;
import com.cai310.lottery.support.LineOption;

public class DczcMatchItem implements LineOption, Serializable, Comparable<DczcMatchItem> {
	private static final long serialVersionUID = -2542287020185175888L;

	/** 场次ID */
	private int lineId;

	/** 选择的内容 */
	private int value;

	/** 是否设胆 */
	private boolean dan;

	/**
	 * @return {@link #lineId}
	 */
	public int getLineId() {
		return lineId;
	}

	/**
	 * @param lineId the {@link #lineId} to set
	 */
	public void setLineId(int lineId) {
		this.lineId = lineId;
	}

	/**
	 * @return {@link #value}
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @param value the {@link #value} to set
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * @return {@link #dan}
	 */
	public boolean isDan() {
		return dan;
	}

	/**
	 * @param dan the {@link #dan} to set
	 */
	public void setDan(boolean dan) {
		this.dan = dan;
	}

	public int compareTo(DczcMatchItem o) {
		if (this.lineId > o.lineId)
			return 1;
		else if (this.lineId < o.lineId)
			return -1;
		else
			return 0;
	}

	/**
	 * 计算选择的选项个数
	 * 
	 * @return 选择的选项个数
	 */
	public int selectedCount() {
		int v = value;
		int i = 0;
		int count = 0;
		while (v > 0) {
			int a = 1 << i;
			if ((v & a) > 0) {
				count++;
				v ^= a;
			}
			i++;
			if (i > 25)
				throw new RuntimeException("数据异常！");
		}
		return count;
	}

	/**
	 * 是否选择了某个选项
	 * 
	 * @param item 某个选项
	 * @return 是否有选择
	 */
	public boolean hasSelect(Item item) {
		return hasSelect(item.ordinal());
	}

	/**
	 * 是否有选择某个选项
	 * 
	 * @param itemOrdinal 选项的序号
	 * @return 是否有选择
	 */
	public boolean hasSelect(int itemOrdinal) {
		return (this.value & (1 << itemOrdinal)) > 0;
	}

}
