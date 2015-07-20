package com.cai310.lottery.support.jclq;

import java.io.Serializable;

import com.cai310.lottery.support.Item;
import com.cai310.lottery.support.SelectedCount;
import com.cai310.lottery.support.jclq.PlayType;

public class JclqMatchItem implements SelectedCount, Serializable, Comparable<JclqMatchItem> {
	private static final long serialVersionUID = -2542287020185175888L;

	/** 场次标识 */
	private String matchKey;

	/** 选择的内容 */
	private int value;

	/** 是否设胆 */
	private boolean dan;
	/** 混合配 */
	private PlayType playType;
	/**
	 * @return the matchKey
	 */
	public String getMatchKey() {
		return matchKey;
	}

	/**
	 * @param matchKey the matchKey to set
	 */
	public void setMatchKey(String matchKey) {
		this.matchKey = matchKey;
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

	public int compareTo(JclqMatchItem o) {
		return this.matchKey.compareTo(o.matchKey);
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
	public PlayType getPlayType() {
		return playType;
	}

	public void setPlayType(PlayType playType) {
		this.playType = playType;
	}
}
