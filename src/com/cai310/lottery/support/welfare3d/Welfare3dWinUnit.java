package com.cai310.lottery.support.welfare3d;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

import com.cai310.lottery.support.WinItem;

@Embeddable
public class Welfare3dWinUnit implements Comparable<Welfare3dWinUnit>, Serializable {
	public static final long serialVersionUID = -4572065732856079901L;

	private Integer winUnits;// 直选中奖注数
	private Integer g3WinUnits;// 组3中奖注数
	private Integer g6WinUnits;// 组6中奖注数

	public int compareTo(Welfare3dWinUnit o) {
		return 0;
	}

	@Column(name = "win_units")
	public Integer getWinUnits() {
		return winUnits;
	}

	public void setWinUnits(Integer winUnits) {
		this.winUnits = winUnits;
	}

	@Column(name = "g3_win_units")
	public Integer getG3WinUnits() {
		return g3WinUnits;
	}

	public void setG3WinUnits(Integer winUnits) {
		g3WinUnits = winUnits;
	}

	@Column(name = "g6_win_units")
	public Integer getG6WinUnits() {
		return g6WinUnits;
	}

	public void setG6WinUnits(Integer winUnits) {
		g6WinUnits = winUnits;
	}

	/**
	 * @return 是否中奖
	 */
	@Transient
	public boolean isWon() {
		Integer[] winUnits = new Integer[3];
		winUnits[0] = this.getWinUnits();
		winUnits[1] = this.getG3WinUnits();
		winUnits[2] = this.getG6WinUnits();
		for (Integer winUnit : winUnits) {
			if (winUnit != null && winUnit.intValue() > 0) {
				return true;
			}
		}
		return false;
	}

	public void addWinUnits(Integer winUnits) {
		if (winUnits != null) {
			if (this.getWinUnits() == null) {
				this.setWinUnits(winUnits);
			} else {
				this.setWinUnits(this.getWinUnits() + winUnits);
			}
		}
	}

	public void addG3WinUnits(Integer winUnits) {
		if (winUnits != null) {
			if (this.getG3WinUnits() == null) {
				this.setG3WinUnits(winUnits);
			} else {
				this.setG3WinUnits(this.getG3WinUnits() + winUnits);
			}
		}
	}

	public void addG6WinUnits(Integer winUnits) {
		if (winUnits != null) {
			if (this.getG6WinUnits() == null) {
				this.setG6WinUnits(winUnits);
			} else {
				this.setG6WinUnits(this.getG6WinUnits() + winUnits);
			}
		}
	}

	public void addWelfare3dWinUnits(Welfare3dWinUnit welfare3dWinUnit) {
		this.addWinUnits(welfare3dWinUnit.getWinUnits());
		this.addG6WinUnits(welfare3dWinUnit.getG6WinUnits());
		this.addG3WinUnits(welfare3dWinUnit.getG3WinUnits());
	}

	@Transient
	public List<WinItem> getWinItemList() {
		List<WinItem> list = new ArrayList<WinItem>();

		Integer unit = this.getWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem("直选", unit));
		}

		unit = this.getG3WinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem("组选3", unit));
		}

		unit = this.getG6WinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem("组选6", unit));
		}
		return list;
	}
}
