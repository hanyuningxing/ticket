package com.cai310.lottery.support.qyh;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;

import com.cai310.lottery.Constant;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.prizeutils.template.VariableString;
import com.cai310.lottery.support.WinItem;

public class QyhWinUnits {

	/** 选一数投中奖注数 **/
	private Integer randomOneWinUnits;

	/** 任选二中奖注数 **/
	private Integer randomTwoWinUnits;

	/** 任选三中奖注数 **/
	private Integer randomThreeWinUnits;
	/** 任三中二 **/
	private Integer randomThreeH2WinUnits;

	/** 任选四中奖注数 **/
	private Integer randomFourWinUnits;
	/** 任四中三 **/
	private Integer randomFourH3WinUnits;

	/** 任选五中奖注数 **/
	private Integer randomFiveWinUnits;
	/** 任五中四 **/
	private Integer randomFiveH4WinUnits;
	/** 任五中三 **/
	private Integer randomFiveH3WinUnits;

	/** 任选六中奖注数 **/
	private Integer randomSixWinUnits;

	/** 任选七中奖注数 **/
	private Integer randomSevenWinUnits;

	/** 任选八中奖注数 **/
	private Integer randomEightWinUnits;

	/** 任选九中奖注数 **/
	private Integer randomNineWinUnits;

	/** 任选十中奖注数 **/
	private Integer randomTenWinUnits;

	/** 围一中奖注数 **/
	private Integer roundOneWinUnits;

	/** 围二中奖注数 **/
	private Integer roundTwoWinUnits;

	/** 围三中奖注数 **/
	private Integer roundThreeWinUnits;

	/** 围四中奖注数 **/
	private Integer roundFourWinUnits;

	/** 顺一中奖注数 **/
	private Integer directOneWinUnits;

	/** 顺二中奖注数 **/
	private Integer directTwoWinUnits;

	/** 顺三中奖注数 **/
	private Integer directThreeWinUnits;
	/**
	 * 模板：{PRIZEITEM}:{WINUNITS}注,{PRIZE}元;}
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
		this.setRandomOneWinUnits(null);
		this.setRandomTwoWinUnits(null);
		this.setRandomThreeWinUnits(null);
		this.setRandomFourWinUnits(null);
		this.setRandomFiveWinUnits(null);
		this.setRandomSixWinUnits(null);
		this.setRandomSevenWinUnits(null);
		this.setRandomEightWinUnits(null);
		this.setRandomNineWinUnits(null);
		this.setRandomTenWinUnits(null);

		this.setRoundOneWinUnits(null);
		this.setRoundTwoWinUnits(null);
		this.setRoundThreeWinUnits(null);
		this.setRoundFourWinUnits(null);

		this.setDirectOneWinUnits(null);
		this.setDirectTwoWinUnits(null);
		this.setDirectThreeWinUnits(null);
		
		this.setRandomFiveH3WinUnits(null);
		this.setRandomFiveH4WinUnits(null);
		this.setRandomFourH3WinUnits(null);
		this.setRandomThreeH2WinUnits(null);
	}

	private Integer[] getWinUnits() {
		return new Integer[] { this.getRandomOneWinUnits(), this.getRandomTwoWinUnits(), this.getRandomThreeWinUnits(),
				this.getRandomFourWinUnits(), this.getRandomFiveWinUnits(), this.getRandomSixWinUnits(),
				this.getRandomSevenWinUnits(), this.getRandomEightWinUnits(), this.getRandomNineWinUnits(),
				this.getRandomTenWinUnits(), this.getRoundOneWinUnits(), this.getRoundTwoWinUnits(),
				this.getRoundThreeWinUnits(), this.getRoundFourWinUnits(), this.getDirectOneWinUnits(),
				this.getDirectTwoWinUnits(), this.getDirectThreeWinUnits(),this.getRandomFiveH3WinUnits(),this.getRandomFiveH4WinUnits(),
				this.getRandomFourH3WinUnits(),this.getRandomThreeH2WinUnits()};
	}

	public List<WinItem> getWinItemList() {
		List<WinItem> list = new ArrayList<WinItem>();

		Integer unit = this.getRandomOneWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(QyhPlayType.RandomOne.getTypeName(), unit));
		}

		unit = this.getRandomTwoWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(QyhPlayType.RandomTwo.getTypeName(), unit));
		}
		unit = this.getRandomThreeWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(QyhPlayType.RandomThree.getTypeName(), unit));
		}

		unit = this.getRandomFourWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(QyhPlayType.RandomFour.getTypeName(), unit));
		}

		unit = this.getRandomFiveWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(QyhPlayType.RandomFive.getTypeName(), unit));
		}

		unit = this.getRandomSixWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(QyhPlayType.RandomSix.getTypeName(), unit));
		}

		unit = this.getRandomSevenWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(QyhPlayType.RandomSeven.getTypeName(), unit));
		}

		unit = this.getRandomEightWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(QyhPlayType.RandomEight.getTypeName(), unit));
		}

		unit = this.getRandomNineWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(QyhPlayType.RandomNine.getTypeName(), unit));
		}

		unit = this.getRandomTenWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(QyhPlayType.RandomTen.getTypeName(), unit));
		}

		unit = this.getRoundOneWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(QyhPlayType.RoundOne.getTypeName(), unit));
		}

		unit = this.getRoundTwoWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(QyhPlayType.RoundTwo.getTypeName(), unit));
		}
		unit = this.getRoundThreeWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(QyhPlayType.RoundThree.getTypeName(), unit));
		}

		unit = this.getRoundFourWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(QyhPlayType.RoundFour.getTypeName(), unit));
		}

		unit = this.getDirectOneWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(QyhPlayType.DirectOne.getTypeName(), unit));
		}

		unit = this.getDirectTwoWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(QyhPlayType.DirectTwo.getTypeName(), unit));
		}
		unit = this.getDirectThreeWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(QyhPlayType.DirectThree.getTypeName(), unit));
		}
		

		unit = this.getRandomFiveH3WinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(QyhPlayType.RandomFive.getTypeName()+"中三", unit));
		}
		unit = this.getRandomFiveH4WinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(QyhPlayType.RandomFive.getTypeName()+"中四", unit));
		}
		unit = this.getRandomFourH3WinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(QyhPlayType.RandomFour.getTypeName()+"中三", unit));
		}
		unit = this.getRandomThreeH2WinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(QyhPlayType.RandomThree.getTypeName()+"中二", unit));
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
		Integer unit = this.getRandomOneWinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.RandomOne.getPrize();
			varWonLineText.setVar("PRIZEITEM", QyhPlayType.RandomOne.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getRandomTwoWinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.RandomTwo.getPrize();
			varWonLineText.setVar("PRIZEITEM", QyhPlayType.RandomTwo.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getRandomThreeWinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.RandomThree.getPrize();
			varWonLineText.setVar("PRIZEITEM", QyhPlayType.RandomThree.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getRandomFourWinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.RandomFour.getPrize();
			varWonLineText.setVar("PRIZEITEM", QyhPlayType.RandomFour.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getRandomFiveWinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.RandomFive.getPrize();
			varWonLineText.setVar("PRIZEITEM", QyhPlayType.RandomFive.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}

		unit = this.getRandomSixWinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.RandomSix.getPrize();
			varWonLineText.setVar("PRIZEITEM", QyhPlayType.RandomSix.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}

		unit = this.getRandomSevenWinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.RandomSeven.getPrize();
			varWonLineText.setVar("PRIZEITEM", QyhPlayType.RandomSeven.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}

		unit = this.getRandomEightWinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.RandomEight.getPrize();
			varWonLineText.setVar("PRIZEITEM", QyhPlayType.RandomEight.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getRandomNineWinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.RandomNine.getPrize();
			varWonLineText.setVar("PRIZEITEM", QyhPlayType.RandomNine.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getRandomTenWinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.RandomTen.getPrize();
			varWonLineText.setVar("PRIZEITEM", QyhPlayType.RandomTen.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getRoundOneWinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.RoundOne.getPrize();
			varWonLineText.setVar("PRIZEITEM", QyhPlayType.RoundOne.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getRoundTwoWinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.RoundTwo.getPrize();
			varWonLineText.setVar("PRIZEITEM", QyhPlayType.RoundTwo.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getRoundThreeWinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.RoundThree.getPrize();
			varWonLineText.setVar("PRIZEITEM", QyhPlayType.RoundThree.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getRoundFourWinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.RoundFour.getPrize();
			varWonLineText.setVar("PRIZEITEM", QyhPlayType.RoundFour.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getDirectOneWinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.DirectOne.getPrize();
			varWonLineText.setVar("PRIZEITEM", QyhPlayType.DirectOne.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getDirectTwoWinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.DirectTwo.getPrize();
			varWonLineText.setVar("PRIZEITEM", QyhPlayType.DirectTwo.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getDirectThreeWinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.DirectThree.getPrize();
			varWonLineText.setVar("PRIZEITEM", QyhPlayType.DirectThree.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		
		unit = this.getRandomFiveH3WinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.RandomFive.getPrize3();
			varWonLineText.setVar("PRIZEITEM", QyhPlayType.RandomFive.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getRandomFiveH4WinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.RandomFive.getPrize2();
			varWonLineText.setVar("PRIZEITEM", QyhPlayType.RandomFive.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getRandomFourH3WinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.RandomFour.getPrize2();
			varWonLineText.setVar("PRIZEITEM", QyhPlayType.RandomFour.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getRandomThreeH2WinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.RandomThree.getPrize2();
			varWonLineText.setVar("PRIZEITEM", QyhPlayType.RandomThree.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		
		
		return prizeDesc;
	}

	public double getPrizeAfterTax() {
		double totalPrize = 0;
		int prize = 0;
		Integer unit = this.getRandomOneWinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.RandomOne.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getRandomTwoWinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.RandomTwo.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getRandomThreeWinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.RandomThree.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getRandomFourWinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.RandomFour.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getRandomFiveWinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.RandomFive.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getRandomSixWinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.RandomSix.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getRandomSevenWinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.RandomSeven.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getRandomEightWinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.RandomEight.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getRandomNineWinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.RandomNine.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getRandomTenWinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.RandomTen.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getRoundOneWinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.RoundOne.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getRoundTwoWinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.RoundTwo.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getRoundThreeWinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.RoundThree.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getRoundFourWinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.RoundFour.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getDirectOneWinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.DirectOne.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getDirectTwoWinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.DirectTwo.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getDirectThreeWinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.DirectThree.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		
		unit = this.getRandomFiveH3WinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.RandomFive.getPrize3();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getRandomFiveH4WinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.RandomFive.getPrize2();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getRandomFourH3WinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.RandomFour.getPrize2();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getRandomThreeH2WinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.RandomThree.getPrize2();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		
		
		return totalPrize;
	}

	public double getPrize() {
		double totalPrize = 0;
		int prize = 0;
		Integer unit = this.getRandomOneWinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.RandomOne.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getRandomTwoWinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.RandomTwo.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getRandomThreeWinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.RandomThree.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getRandomFourWinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.RandomFour.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getRandomFiveWinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.RandomFive.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getRandomSixWinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.RandomSix.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getRandomSevenWinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.RandomSeven.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getRandomEightWinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.RandomEight.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getRandomNineWinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.RandomNine.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getRandomTenWinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.RandomTen.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getRoundOneWinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.RoundOne.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getRoundTwoWinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.RoundTwo.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getRoundThreeWinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.RoundThree.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getRoundFourWinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.RoundFour.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getDirectOneWinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.DirectOne.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getDirectTwoWinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.DirectTwo.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getDirectThreeWinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.DirectThree.getPrize();
			totalPrize += unit * prize;
		}
		
		unit = this.getRandomFiveH3WinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.RandomFive.getPrize3();
			totalPrize += unit * prize;
		}
		unit = this.getRandomFiveH4WinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.RandomFive.getPrize2();
			totalPrize += unit * prize;
		}
		unit = this.getRandomFourH3WinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.RandomFour.getPrize2();
			totalPrize += unit * prize;
		}
		unit = this.getRandomThreeH2WinUnits();
		if (unit != null && unit > 0) {
			prize = QyhPlayType.RandomThree.getPrize2();
			totalPrize += unit * prize;
		}
		return totalPrize;
	}

	// ////////////////////////////////////////////////////////////////////////
	/**
	 * @return the randomOneWinUnits
	 */
	public Integer getRandomOneWinUnits() {
		return randomOneWinUnits;
	}

	/**
	 * @param randomOneWinUnits
	 *            the randomOneWinUnits to set
	 */
	public void setRandomOneWinUnits(Integer randomOneWinUnits) {
		this.randomOneWinUnits = randomOneWinUnits;
	}

	/**
	 * 
	 */
	public void addRandomOneWinUnits(Integer winUnits) {
		if (this.randomOneWinUnits != null) {
			this.randomOneWinUnits += winUnits;
		} else {
			this.randomOneWinUnits = winUnits;
		}
	}

	/**
	 * @return the randomTwoWinUnits
	 */
	public Integer getRandomTwoWinUnits() {
		return randomTwoWinUnits;
	}

	/**
	 * @param randomTwoWinUnits
	 *            the randomTwoWinUnits to set
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
	 * @return the randomThreeWinUnits
	 */
	public Integer getRandomThreeWinUnits() {
		return randomThreeWinUnits;
	}

	/**
	 * @param randomThreeWinUnits
	 *            the randomThreeWinUnits to set
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
	 * @return the randomFourWinUnits
	 */
	public Integer getRandomFourWinUnits() {
		return randomFourWinUnits;
	}

	/**
	 * @param randomFourWinUnits
	 *            the randomFourWinUnits to set
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
	 * @param randomFiveWinUnits
	 *            the randomFiveWinUnits to set
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
	 * @param prizeTemplate
	 *            the prizeTemplate to set
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
	 * @param randomSixWinUnits
	 *            the randomSixWinUnits to set
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
	 * @param randomSevenWinUnits
	 *            the randomSevenWinUnits to set
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
	 * @param randomEightWinUnits
	 *            the randomEightWinUnits to set
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

	public Integer getRandomNineWinUnits() {
		return randomNineWinUnits;
	}

	public void setRandomNineWinUnits(Integer randomNineWinUnits) {
		this.randomNineWinUnits = randomNineWinUnits;
	}

	public void addRandomNineWinUnits(Integer winUnits) {
		if (this.randomNineWinUnits != null) {
			this.randomNineWinUnits += winUnits;
		} else {
			this.randomNineWinUnits = winUnits;
		}
	}

	public Integer getRandomTenWinUnits() {
		return randomTenWinUnits;
	}

	public void setRandomTenWinUnits(Integer randomTenWinUnits) {
		this.randomTenWinUnits = randomTenWinUnits;
	}

	public void addRandomTenWinUnits(Integer winUnits) {
		if (this.randomTenWinUnits != null) {
			this.randomTenWinUnits += winUnits;
		} else {
			this.randomTenWinUnits = winUnits;
		}
	}

	/**
	 * @return the roundOneWinUnits
	 */
	public Integer getRoundOneWinUnits() {
		return roundOneWinUnits;
	}

	/**
	 * @param roundOneWinUnits
	 *            the roundOneWinUnits to set
	 */
	public void setRoundOneWinUnits(Integer roundOneWinUnits) {
		this.roundOneWinUnits = roundOneWinUnits;
	}

	/**
	 * 
	 */
	public void addRoundOneWinUnits(Integer winUnits) {
		if (this.roundOneWinUnits != null) {
			this.roundOneWinUnits += winUnits;
		} else {
			this.roundOneWinUnits = winUnits;
		}
	}

	/**
	 * @return the roundTwoWinUnits
	 */
	public Integer getRoundTwoWinUnits() {
		return roundTwoWinUnits;
	}

	/**
	 * @param roundTwoWinUnits
	 *            the roundTwoWinUnits to set
	 */
	public void setRoundTwoWinUnits(Integer roundTwoWinUnits) {
		this.roundTwoWinUnits = roundTwoWinUnits;
	}

	public void addRoundTwoWinUnits(Integer winUnits) {
		if (this.roundTwoWinUnits != null) {
			this.roundTwoWinUnits += winUnits;
		} else {
			this.roundTwoWinUnits = winUnits;
		}
	}

	/**
	 * @return the roundThreeWinUnits
	 */
	public Integer getRoundThreeWinUnits() {
		return roundThreeWinUnits;
	}

	/**
	 * @param roundThreeWinUnits
	 *            the roundThreeWinUnits to set
	 */
	public void setRoundThreeWinUnits(Integer roundThreeWinUnits) {
		this.roundThreeWinUnits = roundThreeWinUnits;
	}

	public void addRoundThreeWinUnits(Integer winUnits) {
		if (this.roundThreeWinUnits != null) {
			this.roundThreeWinUnits += winUnits;
		} else {
			this.roundThreeWinUnits = winUnits;
		}
	}

	/**
	 * @return the roundFourWinUnits
	 */
	public Integer getRoundFourWinUnits() {
		return roundFourWinUnits;
	}

	/**
	 * @param roundFourWinUnits
	 *            the roundFourWinUnits to set
	 */
	public void setRoundFourWinUnits(Integer roundFourWinUnits) {
		this.roundFourWinUnits = roundFourWinUnits;
	}

	public void addRoundFourWinUnits(Integer winUnits) {
		if (this.roundFourWinUnits != null) {
			this.roundFourWinUnits += winUnits;
		} else {
			this.roundFourWinUnits = winUnits;
		}
	}

	/**
	 * @return the directOneWinUnits
	 */
	public Integer getDirectOneWinUnits() {
		return directOneWinUnits;
	}

	/**
	 * @param directOneWinUnits
	 *            the directOneWinUnits to set
	 */
	public void setDirectOneWinUnits(Integer directOneWinUnits) {
		this.directOneWinUnits = directOneWinUnits;
	}

	/**
	 * 
	 */
	public void addDirectOneWinUnits(Integer winUnits) {
		if (this.directOneWinUnits != null) {
			this.directOneWinUnits += winUnits;
		} else {
			this.directOneWinUnits = winUnits;
		}
	}

	/**
	 * @return the directTwoWinUnits
	 */
	public Integer getDirectTwoWinUnits() {
		return directTwoWinUnits;
	}

	/**
	 * @param directTwoWinUnits
	 *            the directTwoWinUnits to set
	 */
	public void setDirectTwoWinUnits(Integer directTwoWinUnits) {
		this.directTwoWinUnits = directTwoWinUnits;
	}

	public void addDirectTwoWinUnits(Integer winUnits) {
		if (this.directTwoWinUnits != null) {
			this.directTwoWinUnits += winUnits;
		} else {
			this.directTwoWinUnits = winUnits;
		}
	}

	/**
	 * @return the directThreeWinUnits
	 */
	public Integer getDirectThreeWinUnits() {
		return directThreeWinUnits;
	}

	/**
	 * @param directThreeWinUnits
	 *            the directThreeWinUnits to set
	 */
	public void setDirectThreeWinUnits(Integer directThreeWinUnits) {
		this.directThreeWinUnits = directThreeWinUnits;
	}

	public void addDirectThreeWinUnits(Integer winUnits) {
		if (this.directThreeWinUnits != null) {
			this.directThreeWinUnits += winUnits;
		} else {
			this.directThreeWinUnits = winUnits;
		}
	}

	public Integer getRandomThreeH2WinUnits() {
		return randomThreeH2WinUnits;
	}

	public void setRandomThreeH2WinUnits(Integer randomThreeH2WinUnits) {
		this.randomThreeH2WinUnits = randomThreeH2WinUnits;
	}

	public void addRandomThreeH2WinUnits(Integer winUnits) {
		if (this.randomThreeH2WinUnits != null) {
			this.randomThreeH2WinUnits += winUnits;
		} else {
			this.randomThreeH2WinUnits = winUnits;
		}
	}

	public Integer getRandomFourH3WinUnits() {
		return randomFourH3WinUnits;
	}

	public void setRandomFourH3WinUnits(Integer randomFourH3WinUnits) {
		this.randomFourH3WinUnits = randomFourH3WinUnits;
	}

	public void addRandomFourH3WinUnits(Integer winUnits) {
		if (this.randomFourH3WinUnits != null) {
			this.randomFourH3WinUnits += winUnits;
		} else {
			this.randomFourH3WinUnits = winUnits;
		}
	}

	public Integer getRandomFiveH4WinUnits() {
		return randomFiveH4WinUnits;
	}

	public void setRandomFiveH4WinUnits(Integer randomFiveH4WinUnits) {
		this.randomFiveH4WinUnits = randomFiveH4WinUnits;
	}

	public void addRandomFiveH4WinUnits(Integer winUnits) {
		if (this.randomFiveH4WinUnits != null) {
			this.randomFiveH4WinUnits += winUnits;
		} else {
			this.randomFiveH4WinUnits = winUnits;
		}
	}

	public Integer getRandomFiveH3WinUnits() {
		return randomFiveH3WinUnits;
	}

	public void setRandomFiveH3WinUnits(Integer randomFiveH3WinUnits) {
		this.randomFiveH3WinUnits = randomFiveH3WinUnits;
	}

	public void addRandomFiveH3WinUnits(Integer winUnits) {
		if (this.randomFiveH3WinUnits != null) {
			this.randomFiveH3WinUnits += winUnits;
		} else {
			this.randomFiveH3WinUnits = winUnits;
		}
	}

	public void addQyhWinUnits(QyhWinUnits winUnit) {
		this.addRandomOneWinUnits(winUnit.getRandomOneWinUnits());
		this.addRandomTwoWinUnits(winUnit.getRandomTwoWinUnits());
		this.addRandomThreeWinUnits(winUnit.getRandomThreeWinUnits());
		this.addRandomFourWinUnits(winUnit.getRandomFourWinUnits());
		this.addRandomFiveWinUnits(winUnit.getRandomFiveWinUnits());
		this.addRandomSixWinUnits(winUnit.getRandomSixWinUnits());
		this.addRandomSevenWinUnits(winUnit.getRandomSevenWinUnits());
		this.addRandomEightWinUnits(winUnit.getRandomEightWinUnits());
		this.addRandomNineWinUnits(winUnit.getRandomNineWinUnits());
		this.addRandomTenWinUnits(winUnit.getRandomTenWinUnits());

		this.addRoundOneWinUnits(winUnit.getRoundOneWinUnits());
		this.addRoundTwoWinUnits(winUnit.getRoundTwoWinUnits());
		this.addRoundThreeWinUnits(winUnit.getRoundThreeWinUnits());
		this.addRoundFourWinUnits(winUnit.getRoundFourWinUnits());

		this.addDirectOneWinUnits(winUnit.getDirectOneWinUnits());
		this.addDirectTwoWinUnits(winUnit.getDirectTwoWinUnits());
		this.addDirectThreeWinUnits(winUnit.getDirectThreeWinUnits());
		

		this.addRandomFiveH3WinUnits(winUnit.getRandomFiveH3WinUnits());
		this.addRandomFiveH4WinUnits(winUnit.getRandomFiveH4WinUnits());
		this.addRandomFourH3WinUnits(winUnit.getRandomFourH3WinUnits());
		this.addRandomThreeH2WinUnits(winUnit.getRandomThreeH2WinUnits());

	}

}
