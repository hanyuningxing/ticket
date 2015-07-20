package com.cai310.lottery.support.welfare36to7;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

import com.cai310.lottery.support.WinItem;

@Embeddable
public class Welfare36to7WinUnit implements Comparable<Welfare36to7WinUnit>, Serializable {
	public static final long serialVersionUID = -4572065732856079901L;

	private Integer firstWinUnits;
	private Integer secondWinUnits;
	private Integer thirdWinUnits;
	private Integer fourthWinUnits;
	private Integer fifthWinUnits;
	private Integer sixthWinUnits;
	private Integer haocai1WinUnits;
	private Integer haocai2WinUnits;
	private Integer haocai3WinUnits;
	private Integer zodiacWinUnits;
	private Integer seasonWinUnits;
	private Integer azimuthWinUnits;

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

	@Column(name = "fourth_WinUnits")
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
	 * @return the zodiacWinUnits
	 */
	@Column(name = "zodiac_win_units")
	public Integer getZodiacWinUnits() {
		return zodiacWinUnits;
	}

	/**
	 * @param zodiacWinUnits the zodiacWinUnits to set
	 */
	public void setZodiacWinUnits(Integer zodiacWinUnits) {
		this.zodiacWinUnits = zodiacWinUnits;
	}

	/**
	 * @return the seasonWinUnits
	 */
	@Column(name = "season_win_units")
	public Integer getSeasonWinUnits() {
		return seasonWinUnits;
	}

	/**
	 * @param seasonWinUnits the seasonWinUnits to set
	 */
	public void setSeasonWinUnits(Integer seasonWinUnits) {
		this.seasonWinUnits = seasonWinUnits;
	}

	/**
	 * @return the azimuthWinUnits
	 */
	@Column(name = "azimuth_win_units")
	public Integer getAzimuthWinUnits() {
		return azimuthWinUnits;
	}

	/**
	 * @param azimuthWinUnits the azimuthWinUnits to set
	 */
	public void setAzimuthWinUnits(Integer azimuthWinUnits) {
		this.azimuthWinUnits = azimuthWinUnits;
	}

	/**
	 * @return the maxHit
	 */
	@Transient
	public Byte getMaxHit() {
		return maxHit;
	}

	/**
	 * @param maxHit the maxHit to set
	 */
	@Transient
	public void setMaxHit(Byte maxHit) {
		this.maxHit = maxHit;
	}

	/**
	 * @return the haocai2WinUnits
	 */
	public Integer getHaocai1WinUnits() {
		return haocai1WinUnits;
	}

	/**
	 * @param haocai2WinUnits the haocai2WinUnits to set
	 */
	public void setHaocai1WinUnits(Integer haocai1WinUnits) {
		this.haocai1WinUnits = haocai1WinUnits;
	}

	/**
	 * @return the haocai2WinUnits
	 */
	public Integer getHaocai2WinUnits() {
		return haocai2WinUnits;
	}

	/**
	 * @param haocai2WinUnits the haocai2WinUnits to set
	 */
	public void setHaocai2WinUnits(Integer haocai2WinUnits) {
		this.haocai2WinUnits = haocai2WinUnits;
	}

	/**
	 * @return the haocai3WinUnits
	 */
	public Integer getHaocai3WinUnits() {
		return haocai3WinUnits;
	}

	/**
	 * @param haocai3WinUnits the haocai3WinUnits to set
	 */
	public void setHaocai3WinUnits(Integer haocai3WinUnits) {
		this.haocai3WinUnits = haocai3WinUnits;
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

	public void addHaocai1WinUnits(Integer winUnits) {
		if (winUnits != null) {
			if (this.getHaocai1WinUnits() == null) {
				this.setHaocai1WinUnits(winUnits);
			} else {
				this.setHaocai1WinUnits(this.getHaocai1WinUnits() + winUnits);
			}
		}
	}

	public void addHaocai2WinUnits(Integer winUnits) {
		if (winUnits != null) {
			if (this.getHaocai2WinUnits() == null) {
				this.setHaocai2WinUnits(winUnits);
			} else {
				this.setHaocai2WinUnits(this.getHaocai2WinUnits() + winUnits);
			}
		}
	}

	public void addHaocai3WinUnits(Integer winUnits) {
		if (winUnits != null) {
			if (this.getHaocai3WinUnits() == null) {
				this.setHaocai3WinUnits(winUnits);
			} else {
				this.setHaocai3WinUnits(this.getHaocai3WinUnits() + winUnits);
			}
		}
	}

	public void addZodiacWinUnits(Integer winUnits) {
		if (winUnits != null) {
			if (this.getZodiacWinUnits() == null) {
				this.setZodiacWinUnits(winUnits);
			} else {
				this.setZodiacWinUnits(this.getZodiacWinUnits() + winUnits);
			}
		}
	}

	public void addSeasonWinUnits(Integer winUnits) {
		if (winUnits != null) {
			if (this.getSeasonWinUnits() == null) {
				this.setSeasonWinUnits(winUnits);
			} else {
				this.setSeasonWinUnits(this.getSeasonWinUnits() + winUnits);
			}
		}
	}

	public void addAzimuthWinUnits(Integer winUnits) {
		if (winUnits != null) {
			if (this.getAzimuthWinUnits() == null) {
				this.setAzimuthWinUnits(winUnits);
			} else {
				this.setAzimuthWinUnits(this.getAzimuthWinUnits() + winUnits);
			}
		}
	}

	public void addWelfare36to7WinUnit(Welfare36to7WinUnit winUnit) {
		this.addFirstWinUnits(winUnit.getFirstWinUnits());
		this.addSecondWinUnits(winUnit.getSecondWinUnits());
		this.addThirdWinUnits(winUnit.getThirdWinUnits());
		this.addFourthWinUnits(winUnit.getFourthWinUnits());
		this.addFifthWinUnits(winUnit.getFifthWinUnits());
		this.addSixthWinUnits(winUnit.getSixthWinUnits());
		this.addHaocai1WinUnits(winUnit.getHaocai1WinUnits());
		this.addHaocai2WinUnits(winUnit.getHaocai2WinUnits());
		this.addHaocai3WinUnits(winUnit.getHaocai3WinUnits());
		this.addZodiacWinUnits(winUnit.getZodiacWinUnits());
		this.addSeasonWinUnits(winUnit.getSeasonWinUnits());
		this.addAzimuthWinUnits(winUnit.getAzimuthWinUnits());
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
		this.setHaocai1WinUnits(null);
		this.setHaocai2WinUnits(null);
		this.setHaocai3WinUnits(null);
		this.setZodiacWinUnits(null);
		this.setSeasonWinUnits(null);
		this.setAzimuthWinUnits(null);
	}

	@Transient
	protected Integer[] getWinUnits() {
		return new Integer[] { this.getFirstWinUnits(), this.getSecondWinUnits(), this.getThirdWinUnits(),
				this.getFourthWinUnits(), this.getFifthWinUnits(), this.getSixthWinUnits(), this.getHaocai1WinUnits(),
				this.getHaocai2WinUnits(), this.getHaocai3WinUnits(), this.getZodiacWinUnits(),
				this.getSeasonWinUnits(), this.getAzimuthWinUnits() };
	}

	public int compareTo(Welfare36to7WinUnit winUnit2) {
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

		unit = this.getHaocai1WinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem("好彩1", unit));
		}
		unit = this.getHaocai2WinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem("好彩2", unit));
		}
		unit = this.getHaocai3WinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem("好彩3", unit));
		}
		unit = this.getZodiacWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem("生肖", unit));
		}
		unit = this.getSeasonWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem("季节", unit));
		}
		unit = this.getAzimuthWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem("方位", unit));
		}

		return list;
	}

	@Override
	public String toString() {
		return "一等奖:" + this.firstWinUnits.toString() + "   二等奖:" + this.secondWinUnits.toString() + "   三等奖:"
				+ this.thirdWinUnits.toString() + "    四等奖:" + this.fourthWinUnits.toString() + "   五等奖:"
				+ this.fifthWinUnits.toString() + "    六等奖:" + this.sixthWinUnits.toString() + "    好彩1:"
				+ this.haocai1WinUnits.toString() + "   好彩2:" + this.haocai2WinUnits.toString() + "   好彩3:"
				+ this.haocai3WinUnits.toString() + "    生肖:" + this.zodiacWinUnits.toString() + "   季节:"
				+ this.seasonWinUnits.toString() + "    方位:" + this.azimuthWinUnits.toString();
	}
}
