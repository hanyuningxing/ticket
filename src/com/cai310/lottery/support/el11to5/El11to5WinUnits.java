package com.cai310.lottery.support.el11to5;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;

import com.cai310.lottery.Constant;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.prizeutils.template.VariableString;
import com.cai310.lottery.support.WinItem;

public class El11to5WinUnits {

	/** 选一数投中奖注数 **/
	private Integer normalOneWinUnits;

	/** 任选二中奖注数 **/
	private Integer randomTwoWinUnits;

	/** 选二连组中奖注数 **/
	private Integer foreTwoGroupWinUnits;

	/** 选二连直中奖注数 **/
	private Integer foreTwoDirectWinUnits;

	/** 任选三中奖注数 **/
	private Integer randomThreeWinUnits;

	/** 选三前组中奖注数 **/
	private Integer foreThreeGroupWinUnits;

	/** 选三前直中奖注数 **/
	private Integer foreThreeDirectWinUnits;

	/** 任选四中奖注数 **/
	private Integer randomFourWinUnits;

	/** 任选五中奖注数 **/
	private Integer randomFiveWinUnits;

	/** 任选六中奖注数 **/
	private Integer randomSixWinUnits;

	/** 任选七中奖注数 **/
	private Integer randomSevenWinUnits;

	/** 任选八中奖注数 **/
	private Integer randomEightWinUnits;

	/**
	 * 模板：{PRIZE_ITEM}:{WINUNITS}注,{PRIZE}元;}
	 */
	private String prizeTemplate = "{PRIZEITEM}:{WINUNITS}注,{PRIZE}元;";

	// /////////////////////////////////////////////////////////////

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
		this.setNormalOneWinUnits(null);
		this.setRandomTwoWinUnits(null);
		this.setForeTwoGroupWinUnits(null);
		this.setForeTwoDirectWinUnits(null);
		this.setRandomThreeWinUnits(null);
		this.setForeThreeGroupWinUnits(null);
		this.setForeThreeDirectWinUnits(null);
		this.setRandomFourWinUnits(null);
		this.setRandomFiveWinUnits(null);
		this.setRandomSixWinUnits(null);
		this.setRandomSevenWinUnits(null);
		this.setRandomEightWinUnits(null);
	}

	private Integer[] getWinUnits() {
		return new Integer[] { this.getNormalOneWinUnits(), this.getRandomTwoWinUnits(),
				this.getForeTwoGroupWinUnits(), this.getForeTwoDirectWinUnits(), this.getRandomThreeWinUnits(),
				this.getForeThreeGroupWinUnits(), this.getForeThreeDirectWinUnits(), this.getRandomFourWinUnits(),
				this.getRandomFiveWinUnits(), this.getRandomSixWinUnits(), this.getRandomSevenWinUnits(),
				this.getRandomEightWinUnits() };
	}

	public List<WinItem> getWinItemList() {
		List<WinItem> list = new ArrayList<WinItem>();

		Integer unit = this.getNormalOneWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(El11to5PlayType.NormalOne.getTypeName(), unit));
		}

		unit = this.getRandomTwoWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(El11to5PlayType.RandomTwo.getTypeName(), unit));
		}

		unit = this.getForeTwoGroupWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(El11to5PlayType.ForeTwoGroup.getTypeName(), unit));
		}
		unit = this.getForeTwoDirectWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(El11to5PlayType.ForeTwoDirect.getTypeName(), unit));
		}
		unit = this.getRandomThreeWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(El11to5PlayType.RandomThree.getTypeName(), unit));
		}

		unit = this.getForeThreeGroupWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(El11to5PlayType.ForeThreeGroup.getTypeName(), unit));
		}

		unit = this.getForeThreeDirectWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(El11to5PlayType.ForeThreeDirect.getTypeName(), unit));
		}

		unit = this.getRandomFourWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(El11to5PlayType.RandomFour.getTypeName(), unit));
		}

		unit = this.getRandomFiveWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(El11to5PlayType.RandomFive.getTypeName(), unit));
		}

		unit = this.getRandomSixWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(El11to5PlayType.RandomSix.getTypeName(), unit));
		}

		unit = this.getRandomSevenWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(El11to5PlayType.RandomSeven.getTypeName(), unit));
		}

		unit = this.getRandomEightWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(El11to5PlayType.RandomEight.getTypeName(), unit));
		}

		return list;
	}

	/**
	 * 中奖描述
	 * 
	 * @return
	 * @throws DataException
	 */
	public String getResultDesc() throws DataException {
		List<WinItem> list = this.getWinItemList();
		if (list == null || list.isEmpty())
			throw new DataException("中奖信息为空！");

		VariableString varWonLineText = new VariableString(Constant.WON_LINE_TMPLATE, null);
		StringBuffer sb = new StringBuffer();
		for (WinItem item : list) {
			varWonLineText.setVar("PRIZENAME", item.getWinName());
			varWonLineText.setVar("WINUNITS", item.getWinUnit());
			sb.append(varWonLineText.toString());
		}
		return sb.toString();
	}

	/**
	 * 奖金描述
	 * 
	 * @return
	 */
	public String getPrizeDetail() {
		VariableString varWonLineText = new VariableString(prizeTemplate, null);
		String prizeDesc = "";
		int prize = 0;
		Integer unit = this.getNormalOneWinUnits();
		if (unit != null && unit > 0) {
			prize = El11to5PlayType.NormalOne.getPrize();
			varWonLineText.setVar("PRIZEITEM", El11to5PlayType.NormalOne.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getRandomTwoWinUnits();
		if (unit != null && unit > 0) {
			prize = El11to5PlayType.RandomTwo.getPrize();
			varWonLineText.setVar("PRIZEITEM", El11to5PlayType.RandomTwo.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getForeTwoGroupWinUnits();
		if (unit != null && unit > 0) {
			prize = El11to5PlayType.ForeTwoGroup.getPrize();
			varWonLineText.setVar("PRIZEITEM", El11to5PlayType.ForeTwoGroup.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getForeTwoDirectWinUnits();
		if (unit != null && unit > 0) {
			prize = El11to5PlayType.ForeTwoDirect.getPrize();
			varWonLineText.setVar("PRIZEITEM", El11to5PlayType.ForeTwoDirect.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getRandomThreeWinUnits();
		if (unit != null && unit > 0) {
			prize = El11to5PlayType.RandomThree.getPrize();
			varWonLineText.setVar("PRIZEITEM", El11to5PlayType.RandomThree.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getForeThreeGroupWinUnits();
		if (unit != null && unit > 0) {
			prize = El11to5PlayType.ForeThreeGroup.getPrize();
			varWonLineText.setVar("PRIZEITEM", El11to5PlayType.ForeThreeGroup.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getForeThreeDirectWinUnits();
		if (unit != null && unit > 0) {
			prize = El11to5PlayType.ForeThreeDirect.getPrize();
			varWonLineText.setVar("PRIZEITEM", El11to5PlayType.ForeThreeDirect.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getRandomFourWinUnits();
		if (unit != null && unit > 0) {
			prize = El11to5PlayType.RandomFour.getPrize();
			varWonLineText.setVar("PRIZEITEM", El11to5PlayType.RandomFour.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getRandomFiveWinUnits();
		if (unit != null && unit > 0) {
			prize = El11to5PlayType.RandomFive.getPrize();
			varWonLineText.setVar("PRIZEITEM", El11to5PlayType.RandomFive.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}

		unit = this.getRandomSixWinUnits();
		if (unit != null && unit > 0) {
			prize = El11to5PlayType.RandomSix.getPrize();
			varWonLineText.setVar("PRIZEITEM", El11to5PlayType.RandomSix.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}

		unit = this.getRandomSevenWinUnits();
		if (unit != null && unit > 0) {
			prize = El11to5PlayType.RandomSeven.getPrize();
			varWonLineText.setVar("PRIZEITEM", El11to5PlayType.RandomSeven.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}

		unit = this.getRandomEightWinUnits();
		if (unit != null && unit > 0) {
			prize = El11to5PlayType.RandomEight.getPrize();
			varWonLineText.setVar("PRIZEITEM", El11to5PlayType.RandomEight.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		return prizeDesc;
	}

	public double getPrizeAfterTax() {
		double totalPrize = 0;
		int prize = 0;
		Integer unit = this.getNormalOneWinUnits();
		if (unit != null && unit > 0) {
			prize = El11to5PlayType.NormalOne.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getRandomTwoWinUnits();
		if (unit != null && unit > 0) {
			prize = El11to5PlayType.RandomTwo.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getForeTwoGroupWinUnits();
		if (unit != null && unit > 0) {
			prize = El11to5PlayType.ForeTwoGroup.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getForeTwoDirectWinUnits();
		if (unit != null && unit > 0) {
			prize = El11to5PlayType.ForeTwoDirect.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getRandomThreeWinUnits();
		if (unit != null && unit > 0) {
			prize = El11to5PlayType.RandomThree.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getForeThreeGroupWinUnits();
		if (unit != null && unit > 0) {
			prize = El11to5PlayType.ForeThreeGroup.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getForeThreeDirectWinUnits();
		if (unit != null && unit > 0) {
			prize = El11to5PlayType.ForeThreeDirect.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getRandomFourWinUnits();
		if (unit != null && unit > 0) {
			prize = El11to5PlayType.RandomFour.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getRandomFiveWinUnits();
		if (unit != null && unit > 0) {
			prize = El11to5PlayType.RandomFive.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getRandomSixWinUnits();
		if (unit != null && unit > 0) {
			prize = El11to5PlayType.RandomSix.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getRandomSevenWinUnits();
		if (unit != null && unit > 0) {
			prize = El11to5PlayType.RandomSeven.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getRandomEightWinUnits();
		if (unit != null && unit > 0) {
			prize = El11to5PlayType.RandomEight.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		return totalPrize;
	}

	public double getPrize() {
		double totalPrize = 0;
		int prize = 0;
		Integer unit = this.getNormalOneWinUnits();
		if (unit != null && unit > 0) {
			prize = El11to5PlayType.NormalOne.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getRandomTwoWinUnits();
		if (unit != null && unit > 0) {
			prize = El11to5PlayType.RandomTwo.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getForeTwoGroupWinUnits();
		if (unit != null && unit > 0) {
			prize = El11to5PlayType.ForeTwoGroup.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getForeTwoDirectWinUnits();
		if (unit != null && unit > 0) {
			prize = El11to5PlayType.ForeTwoDirect.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getRandomThreeWinUnits();
		if (unit != null && unit > 0) {
			prize = El11to5PlayType.RandomThree.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getForeThreeGroupWinUnits();
		if (unit != null && unit > 0) {
			prize = El11to5PlayType.ForeThreeGroup.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getForeThreeDirectWinUnits();
		if (unit != null && unit > 0) {
			prize = El11to5PlayType.ForeThreeDirect.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getRandomFourWinUnits();
		if (unit != null && unit > 0) {
			prize = El11to5PlayType.RandomFour.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getRandomFiveWinUnits();
		if (unit != null && unit > 0) {
			prize = El11to5PlayType.RandomFive.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getRandomSixWinUnits();
		if (unit != null && unit > 0) {
			prize = El11to5PlayType.RandomSix.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getRandomSevenWinUnits();
		if (unit != null && unit > 0) {
			prize = El11to5PlayType.RandomSeven.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getRandomEightWinUnits();
		if (unit != null && unit > 0) {
			prize = El11to5PlayType.RandomEight.getPrize();
			totalPrize += unit * prize;
		}
		return totalPrize;
	}

	// ////////////////////////////////////////////////////////////////////////
	/**
	 * @return the normalOneWinUnits
	 */
	public Integer getNormalOneWinUnits() {
		return normalOneWinUnits;
	}

	/**
	 * @param normalOneWinUnits the normalOneWinUnits to set
	 */
	public void setNormalOneWinUnits(Integer normalOneWinUnits) {
		this.normalOneWinUnits = normalOneWinUnits;
	}

	/**
	 * 
	 */
	public void addNormalOneWinUnits(Integer winUnits) {
		if (this.normalOneWinUnits != null) {
			this.normalOneWinUnits += winUnits;
		} else {
			this.normalOneWinUnits = winUnits;
		}
	}

	/**
	 * @return the randomTwoWinUnits
	 */
	public Integer getRandomTwoWinUnits() {
		return randomTwoWinUnits;
	}

	/**
	 * @param randomTwoWinUnits the randomTwoWinUnits to set
	 */
	public void setRandomTwoWinUnits(Integer randomTwoWinUnits) {
		this.randomTwoWinUnits = randomTwoWinUnits;
	}

	public void addRandomTwoWinUnits(Integer winUnits) {
		if (this.randomTwoWinUnits != null) {
			this.randomTwoWinUnits += winUnits;
		} else {
			this.randomTwoWinUnits = winUnits;
		}
	}

	/**
	 * @return the foreTwoGroupWinUnits
	 */
	public Integer getForeTwoGroupWinUnits() {
		return foreTwoGroupWinUnits;
	}

	/**
	 * @param foreTwoGroupWinUnits the foreTwoGroupWinUnits to set
	 */
	public void setForeTwoGroupWinUnits(Integer foreTwoGroupWinUnits) {
		this.foreTwoGroupWinUnits = foreTwoGroupWinUnits;
	}

	public void addForeTwoGroupWinUnits(Integer winUnits) {
		if (this.foreTwoGroupWinUnits != null) {
			this.foreTwoGroupWinUnits += winUnits;
		} else {
			this.foreTwoGroupWinUnits = winUnits;
		}
	}

	/**
	 * @return the foreTwoDirectWinUnits
	 */
	public Integer getForeTwoDirectWinUnits() {
		return foreTwoDirectWinUnits;
	}

	/**
	 * @param foreTwoDirectWinUnits the foreTwoDirectWinUnits to set
	 */
	public void setForeTwoDirectWinUnits(Integer foreTwoDirectWinUnits) {
		this.foreTwoDirectWinUnits = foreTwoDirectWinUnits;
	}

	public void addForeTwoDirectWinUnits(Integer winUnits) {
		if (this.foreTwoDirectWinUnits != null) {
			this.foreTwoDirectWinUnits += winUnits;
		} else {
			this.foreTwoDirectWinUnits = winUnits;
		}
	}

	/**
	 * @return the randomThreeWinUnits
	 */
	public Integer getRandomThreeWinUnits() {
		return randomThreeWinUnits;
	}

	/**
	 * @param randomThreeWinUnits the randomThreeWinUnits to set
	 */
	public void setRandomThreeWinUnits(Integer randomThreeWinUnits) {
		this.randomThreeWinUnits = randomThreeWinUnits;
	}

	public void addRandomThreeWinUnits(Integer winUnits) {
		if (this.randomThreeWinUnits != null) {
			this.randomThreeWinUnits += winUnits;
		} else {
			this.randomThreeWinUnits = winUnits;
		}
	}

	/**
	 * @return the foreThreeGroupWinUnits
	 */
	public Integer getForeThreeGroupWinUnits() {
		return foreThreeGroupWinUnits;
	}

	/**
	 * @param foreThreeGroupWinUnits the foreThreeGroupWinUnits to set
	 */
	public void setForeThreeGroupWinUnits(Integer foreThreeGroupWinUnits) {
		this.foreThreeGroupWinUnits = foreThreeGroupWinUnits;
	}

	public void addForeThreeGroupWinUnits(Integer winUnits) {
		if (this.foreThreeGroupWinUnits != null) {
			this.foreThreeGroupWinUnits += winUnits;
		} else {
			this.foreThreeGroupWinUnits = winUnits;
		}
	}

	/**
	 * @return the foreThreeDirectWinUnits
	 */
	public Integer getForeThreeDirectWinUnits() {
		return foreThreeDirectWinUnits;
	}

	/**
	 * @param foreThreeDirectWinUnits the foreThreeDirectWinUnits to set
	 */
	public void setForeThreeDirectWinUnits(Integer foreThreeDirectWinUnits) {
		this.foreThreeDirectWinUnits = foreThreeDirectWinUnits;
	}

	public void addForeThreeDirectWinUnits(Integer winUnits) {
		if (this.foreThreeDirectWinUnits != null) {
			this.foreThreeDirectWinUnits += winUnits;
		} else {
			this.foreThreeDirectWinUnits = winUnits;
		}
	}

	/**
	 * @return the randomFourWinUnits
	 */
	public Integer getRandomFourWinUnits() {
		return randomFourWinUnits;
	}

	/**
	 * @param randomFourWinUnits the randomFourWinUnits to set
	 */
	public void setRandomFourWinUnits(Integer randomFourWinUnits) {
		this.randomFourWinUnits = randomFourWinUnits;
	}

	public void addRandomFourWinUnits(Integer winUnits) {
		if (this.randomFourWinUnits != null) {
			this.randomFourWinUnits += winUnits;
		} else {
			this.randomFourWinUnits = winUnits;
		}
	}

	/**
	 * @return the randomFiveWinUnits
	 */
	public Integer getRandomFiveWinUnits() {
		return randomFiveWinUnits;
	}

	/**
	 * @param randomFiveWinUnits the randomFiveWinUnits to set
	 */
	public void setRandomFiveWinUnits(Integer randomFiveWinUnits) {
		this.randomFiveWinUnits = randomFiveWinUnits;
	}

	public void addRandomFiveWinUnits(Integer winUnits) {
		if (this.randomFiveWinUnits != null) {
			this.randomFiveWinUnits += winUnits;
		} else {
			this.randomFiveWinUnits = winUnits;
		}
	}

	/**
	 * @return the prizeTemplate
	 */
	public String getPrizeTemplate() {
		return prizeTemplate;
	}

	/**
	 * @param prizeTemplate the prizeTemplate to set
	 */
	public void setPrizeTemplate(String prizeTemplate) {
		this.prizeTemplate = prizeTemplate;
	}

	/**
	 * @return the randomSixWinUnits
	 */
	public Integer getRandomSixWinUnits() {
		return randomSixWinUnits;
	}

	/**
	 * @param randomSixWinUnits the randomSixWinUnits to set
	 */
	public void setRandomSixWinUnits(Integer randomSixWinUnits) {
		this.randomSixWinUnits = randomSixWinUnits;
	}

	public void addRandomSixWinUnits(Integer winUnits) {
		if (this.randomSixWinUnits != null) {
			this.randomSixWinUnits += winUnits;
		} else {
			this.randomSixWinUnits = winUnits;
		}
	}

	/**
	 * @return the randomSevenWinUnits
	 */
	public Integer getRandomSevenWinUnits() {
		return randomSevenWinUnits;
	}

	/**
	 * @param randomSevenWinUnits the randomSevenWinUnits to set
	 */
	public void setRandomSevenWinUnits(Integer randomSevenWinUnits) {
		this.randomSevenWinUnits = randomSevenWinUnits;
	}

	public void addRandomSevenWinUnits(Integer winUnits) {
		if (this.randomSevenWinUnits != null) {
			this.randomSevenWinUnits += winUnits;
		} else {
			this.randomSevenWinUnits = winUnits;
		}
	}

	/**
	 * @return the randomEightWinUnits
	 */
	public Integer getRandomEightWinUnits() {
		return randomEightWinUnits;
	}

	/**
	 * @param randomEightWinUnits the randomEightWinUnits to set
	 */
	public void setRandomEightWinUnits(Integer randomEightWinUnits) {
		this.randomEightWinUnits = randomEightWinUnits;
	}

	public void addRandomEightWinUnits(Integer winUnits) {
		if (this.randomEightWinUnits != null) {
			this.randomEightWinUnits += winUnits;
		} else {
			this.randomEightWinUnits = winUnits;
		}
	}

	public void addEl11to5WinUnits(El11to5WinUnits winUnit) {
		this.addNormalOneWinUnits(winUnit.getNormalOneWinUnits());
		this.addRandomTwoWinUnits(winUnit.getRandomTwoWinUnits());
		this.addForeTwoDirectWinUnits(winUnit.getForeTwoDirectWinUnits());
		this.addForeTwoGroupWinUnits(winUnit.getForeTwoGroupWinUnits());
		this.addForeThreeDirectWinUnits(winUnit.getForeThreeDirectWinUnits());
		this.addForeThreeGroupWinUnits(winUnit.getForeThreeGroupWinUnits());
		this.addRandomThreeWinUnits(winUnit.getRandomThreeWinUnits());
		this.addRandomFourWinUnits(winUnit.getRandomFourWinUnits());
		this.addRandomFiveWinUnits(winUnit.getRandomFiveWinUnits());
		this.addRandomSixWinUnits(winUnit.getRandomSixWinUnits());
		this.addRandomSevenWinUnits(winUnit.getRandomSevenWinUnits());
		this.addRandomEightWinUnits(winUnit.getRandomEightWinUnits());
		
	}

}
