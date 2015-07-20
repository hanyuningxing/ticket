package com.cai310.lottery.support.pl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

import com.cai310.lottery.support.WinItem;

@Embeddable
public class PlWinUnit implements Comparable<PlWinUnit>, Serializable {
	public static final long serialVersionUID = -4572065732856079901L;
	private Integer p5WinUnits;// 排五直选中奖注数
	private Integer p3WinUnits;// 排三直选中奖注数
	private Integer p3G3WinUnits;// 排三组三中奖注数
	private Integer p3G6WinUnits;// 排三组六中奖注数

	public int compareTo(PlWinUnit o) {
		return 0;
	}

	@Column(name = "p5_win_units")
	public Integer getP5WinUnits() {
		return p5WinUnits;
	}

	public void setP5WinUnits(Integer p5WinUnits) {
		this.p5WinUnits = p5WinUnits;
	}

	@Column(name = "p3_win_units")
	public Integer getP3WinUnits() {
		return p3WinUnits;
	}

	public void setP3WinUnits(Integer p3WinUnits) {
		this.p3WinUnits = p3WinUnits;
	}

	@Column(name = "p3_g3_win_units")
	public Integer getP3G3WinUnits() {
		return p3G3WinUnits;
	}

	public void setP3G3WinUnits(Integer p3G3WinUnits) {
		this.p3G3WinUnits = p3G3WinUnits;
	}

	@Column(name = "p3_g6_win_units")
	public Integer getP3G6WinUnits() {
		return p3G6WinUnits;
	}

	public void setP3G6WinUnits(Integer p3G6WinUnits) {
		this.p3G6WinUnits = p3G6WinUnits;
	}

	/**
	 * @return 是否中奖
	 */
	@Transient
	public boolean isWon() {
		Integer[] winUnits = new Integer[4];
		winUnits[0] = this.getP5WinUnits();
		winUnits[1] = this.getP3WinUnits();
		winUnits[2] = this.getP3G3WinUnits();
		winUnits[3] = this.getP3G6WinUnits();
		for (Integer winUnit : winUnits) {
			if (winUnit != null && winUnit.intValue() > 0) {
				return true;
			}
		}
		return false;
	}

	public void addP5WinUnits(Integer winUnits) {
		if (winUnits != null) {
			if (this.getP5WinUnits() == null) {
				this.setP5WinUnits(winUnits);
			} else {
				this.setP5WinUnits(this.getP5WinUnits() + winUnits);
			}
		}
	}

	public void addP3WinUnits(Integer winUnits) {
		if (winUnits != null) {
			if (this.getP3WinUnits() == null) {
				this.setP3WinUnits(winUnits);
			} else {
				this.setP3WinUnits(this.getP3WinUnits() + winUnits);
			}
		}
	}

	public void addP3G3WinUnits(Integer winUnits) {
		if (winUnits != null) {
			if (this.getP3G3WinUnits() == null) {
				this.setP3G3WinUnits(winUnits);
			} else {
				this.setP3G3WinUnits(this.getP3G3WinUnits() + winUnits);
			}
		}
	}

	public void addP3G6WinUnits(Integer winUnits) {
		if (winUnits != null) {
			if (this.getP3G6WinUnits() == null) {
				this.setP3G6WinUnits(winUnits);
			} else {
				this.setP3G6WinUnits(this.getP3G6WinUnits() + winUnits);
			}
		}
	}

	@Transient
	public List<WinItem> getWinItemList() {
		List<WinItem> list = new ArrayList<WinItem>();

		Integer unit = this.getP5WinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem("排列5直选", unit));
		}

		unit = this.getP3WinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem("排列3直选", unit));
		}

		unit = this.getP3G3WinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem("排列3组选3", unit));
		}

		unit = this.getP3G6WinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem("排列3组选6", unit));
		}
		return list;
	}

	public void addPlWinUnits(PlWinUnit plWinUnit) {
		this.addP5WinUnits(plWinUnit.getP5WinUnits());
		this.addP3WinUnits(plWinUnit.getP3WinUnits());
		this.addP3G3WinUnits(plWinUnit.getP3G3WinUnits());
		this.addP3G6WinUnits(plWinUnit.getP3G6WinUnits());
	}
}
