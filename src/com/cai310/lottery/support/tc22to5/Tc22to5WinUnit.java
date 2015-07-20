package com.cai310.lottery.support.tc22to5;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

import com.cai310.lottery.support.WinItem;

@Embeddable
public class Tc22to5WinUnit implements Comparable<Tc22to5WinUnit>, Serializable {
	public static final long serialVersionUID = -4572065732856079901L;

	private Integer firstWinUnits;
	private Integer secondWinUnits;
	private Integer thirdWinUnits;

	/**
	 * 更新中奖时用于辅助排序的，非数据库字段
	 */
	private Byte maxHit;

	@Column(name = "firstWinUnits")
	public Integer getFirstWinUnits() {
		return this.firstWinUnits;
	}

	public void setFirstWinUnits(Integer firstWinUnits) {
		this.firstWinUnits = firstWinUnits;
	}

	@Column(name = "secondWinUnits")
	public Integer getSecondWinUnits() {
		return this.secondWinUnits;
	}

	public void setSecondWinUnits(Integer secondWinUnits) {
		this.secondWinUnits = secondWinUnits;
	}

	@Column(name = "thirdWinUnits")
	public Integer getThirdWinUnits() {
		return this.thirdWinUnits;
	}

	public void setThirdWinUnits(Integer thirdWinUnits) {
		this.thirdWinUnits = thirdWinUnits;
	}
	

	/**
	 * 更新中奖时用于辅助排序的，非数据库字段
	 */
	@Transient
	public Byte getMaxHit() {
		return maxHit;
	}

	/**
	 * 更新中奖时用于辅助排序的，非数据库字段
	 */
	@Transient
	public void setMaxHit(Byte maxHit) {
		this.maxHit = maxHit;
	}

	public void addWinUnit(Tc22to5WinUnit winUnit) {
		this.addFirstWinUnits(winUnit.getFirstWinUnits());
		this.addSecondWinUnits(winUnit.getSecondWinUnits());
		this.addThirdWinUnits(winUnit.getThirdWinUnits());
	}

	
	public void addThirdWinUnits(Integer winUnits) {
		if (winUnits != null) {
			if (this.getThirdWinUnits() == null) {
				this.setThirdWinUnits(winUnits);
			} else {
				this.setThirdWinUnits(this.getThirdWinUnits() + winUnits);
			}
		}
	}

	public void addSecondWinUnits(Integer winUnits) {
		if (winUnits != null) {
			if (this.getSecondWinUnits() == null) {
				this.setSecondWinUnits(winUnits);
			} else {
				this.setSecondWinUnits(this.getSecondWinUnits() + winUnits);
			}
		}
	}

	public void addFirstWinUnits(Integer winUnits) {
		if (winUnits != null) {
			if (this.getFirstWinUnits() == null) {
				this.setFirstWinUnits(winUnits);
			} else {
				this.setFirstWinUnits(this.getFirstWinUnits() + winUnits);
			}
		}
	}

	/**
	 * @return 是否中奖
	 */
	@Transient
	public boolean isWon() {
		Integer[] winUnits = this.getWinUnits();
		for (Integer winUnit : winUnits) {
			if (winUnit != null && winUnit.intValue() > 0) {
				return true;
			}
		}
		return false;
	}

	public void reset() {
		this.setFirstWinUnits(null);
		this.setSecondWinUnits(null);
		this.setThirdWinUnits(null);
	}

	@Transient
	protected Integer[] getWinUnits() {
		return new Integer[] { this.getFirstWinUnits(), this.getSecondWinUnits(), this.getThirdWinUnits()};
	}

	public int compareTo(Tc22to5WinUnit winUnit2) {
		if (winUnit2 == null)
			return -1;

		Integer[] arr1 = this.getWinUnits();
		Integer[] arr2 = winUnit2.getWinUnits();
		int a, b;
		for (int i = 0; i < arr1.length; i++) {
			a = (arr1[i] != null) ? arr1[i].intValue() : 0;
			b = (arr2[i] != null) ? arr2[i].intValue() : 0;
			if (a > b)
				return -1;
			else if (a < b)
				return 1;
		}

		a = (this.getMaxHit() != null) ? this.getMaxHit().intValue() : 0;
		b = (winUnit2.getMaxHit() != null) ? winUnit2.getMaxHit().intValue() : 0;
		if (a > b)
			return -1;
		else if (a < b)
			return 1;

		return 0;
	}

	@Transient
	public List<WinItem> getWinItemList() {
		List<WinItem> list = new ArrayList<WinItem>();

		Integer unit = this.getFirstWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem("一等奖", unit));
		}

		unit = this.getSecondWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem("二等奖", unit));
		}

		unit = this.getThirdWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem("三等奖", unit));
		}

		return list;
	}

	@Override
	public String toString() {
		return "一等奖:" + this.firstWinUnits.toString() + "   二等奖:" + this.secondWinUnits.toString() + "   三等奖:"
				+ this.thirdWinUnits.toString();
	}
}
