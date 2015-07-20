package com.cai310.lottery.support.sevenstar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

import com.cai310.lottery.support.WinItem;
import com.cai310.lottery.support.ssq.SsqWinUnit;

@Embeddable
public class SevenstarWinUnit implements Comparable<SevenstarWinUnit>, Serializable {
	public static final long serialVersionUID = -4572065732856079901L;

	private Integer firstWinUnits;
	private Integer secondWinUnits;
	private Integer thirdWinUnits;
	private Integer fourthWinUnits;
	private Integer fifthWinUnits;
	private Integer sixthWinUnits;

	/**
	 * 更新中奖时用于辅助排序的，非数据库字段
	 */
	private Byte maxHit;

	@Column(name = "first_win_units")
	public Integer getFirstWinUnits() {
		return this.firstWinUnits;
	}

	public void setFirstWinUnits(Integer firstWinUnits) {
		this.firstWinUnits = firstWinUnits;
	}

	@Column(name = "second_win_units")
	public Integer getSecondWinUnits() {
		return this.secondWinUnits;
	}

	public void setSecondWinUnits(Integer secondWinUnits) {
		this.secondWinUnits = secondWinUnits;
	}

	@Column(name = "third_win_units")
	public Integer getThirdWinUnits() {
		return this.thirdWinUnits;
	}

	public void setThirdWinUnits(Integer thirdWinUnits) {
		this.thirdWinUnits = thirdWinUnits;
	}

	@Column(name = "fourth_win_units")
	public Integer getFourthWinUnits() {
		return this.fourthWinUnits;
	}

	public void setFourthWinUnits(Integer fourthWinUnits) {
		this.fourthWinUnits = fourthWinUnits;
	}

	@Column(name = "fifth_win_units")
	public Integer getFifthWinUnits() {
		return this.fifthWinUnits;
	}

	public void setFifthWinUnits(Integer fifthWinUnits) {
		this.fifthWinUnits = fifthWinUnits;
	}

	@Column(name = "sixth_win_units")
	public Integer getSixthWinUnits() {
		return this.sixthWinUnits;
	}

	public void setSixthWinUnits(Integer sixthWinUnits) {
		this.sixthWinUnits = sixthWinUnits;
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

	public void addWinUnit(SsqWinUnit winUnit) {
		this.addFirstWinUnits(winUnit.getFirstWinUnits());
		this.addSecondWinUnits(winUnit.getSecondWinUnits());
		this.addThirdWinUnits(winUnit.getThirdWinUnits());
		this.addFourthWinUnits(winUnit.getFourthWinUnits());
		this.addFifthWinUnits(winUnit.getFifthWinUnits());
		this.addSixthWinUnits(winUnit.getSixthWinUnits());
	}

	public void addSixthWinUnits(Integer winUnits) {
		if (winUnits != null) {
			if (this.getSixthWinUnits() == null) {
				this.setSixthWinUnits(winUnits);
			} else {
				this.setSixthWinUnits(this.getSixthWinUnits() + winUnits);
			}
		}
	}

	public void addFifthWinUnits(Integer winUnits) {
		if (winUnits != null) {
			if (this.getFifthWinUnits() == null) {
				this.setFifthWinUnits(winUnits);
			} else {
				this.setFifthWinUnits(this.getFifthWinUnits() + winUnits);
			}
		}
	}

	public void addFourthWinUnits(Integer winUnits) {
		if (winUnits != null) {
			if (this.getFourthWinUnits() == null) {
				this.setFourthWinUnits(winUnits);
			} else {
				this.setFourthWinUnits(this.getFourthWinUnits() + winUnits);
			}
		}
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
		this.setFourthWinUnits(null);
		this.setFifthWinUnits(null);
		this.setSixthWinUnits(null);
	}

	@Transient
	protected Integer[] getWinUnits() {
		return new Integer[] { this.getFirstWinUnits(), this.getSecondWinUnits(), this.getThirdWinUnits(),
				this.getFourthWinUnits(), this.getFifthWinUnits(), this.getSixthWinUnits() };
	}

	public int compareTo(SevenstarWinUnit winUnit2) {
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

		unit = this.getFourthWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem("四等奖", unit));
		}

		unit = this.getFifthWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem("五等奖", unit));
		}

		unit = this.getSixthWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem("六等奖", unit));
		}

		return list;
	}

	@Override
	public String toString() {
		return "一等奖:" + this.firstWinUnits.toString() + "   二等奖:" + this.secondWinUnits.toString() + "   三等奖:"
				+ this.thirdWinUnits.toString() + "    四等奖:" + this.fourthWinUnits.toString() + "   五等奖:"
				+ this.fifthWinUnits.toString() + "    六等奖:" + this.sixthWinUnits.toString();
	}
}
