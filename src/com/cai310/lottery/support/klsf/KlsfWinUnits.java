package com.cai310.lottery.support.klsf;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;

import com.cai310.lottery.Constant;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.prizeutils.template.VariableString;
import com.cai310.lottery.support.WinItem;

public class KlsfWinUnits {

	/** 选一数投中奖注数 **/
	private Integer normalOneWinUnits;

	/** 选一红投中奖注数 **/
	private Integer redOneWinUnits;

	/** 任选二中奖注数 **/
	private Integer randomTwoWinUnits;

	/** 选二连组中奖注数 **/
	private Integer connectTwoGroupWinUnits;

	/** 选二连直中奖注数 **/
	private Integer connectTwoDirectWinUnits;

	/** 任选三中奖注数 **/
	private Integer randomThreeWinUnits;

	/** 选三前组中奖注数 **/
	private Integer foreThreeGroupWinUnits;

	/** 选三前直中奖注数 **/
	private Integer foreThreeDirectWinUnits;

	/** 任选三中奖注数 **/
	private Integer randomFourWinUnits;

	/** 任选三中奖注数 **/
	private Integer randomFiveWinUnits;

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
		this.setNormalOneWinUnits(null);
		this.setRedOneWinUnits(null);
		this.setRandomTwoWinUnits(null);
		this.setConnectTwoGroupWinUnits(null);
		this.setConnectTwoDirectWinUnits(null);
		this.setRandomThreeWinUnits(null);
		this.setForeThreeGroupWinUnits(null);
		this.setForeThreeDirectWinUnits(null);
		this.setRandomFourWinUnits(null);
		this.setRandomFiveWinUnits(null);
	}

	private Integer[] getWinUnits() {
		return new Integer[] { this.getNormalOneWinUnits(), this.getRedOneWinUnits(), this.getRandomTwoWinUnits(),
				this.getConnectTwoGroupWinUnits(), this.getConnectTwoDirectWinUnits(), this.getRandomThreeWinUnits(),
				this.getForeThreeGroupWinUnits(), this.getForeThreeDirectWinUnits(), this.getRandomFourWinUnits(),
				this.getRandomFiveWinUnits() };
	}

	public List<WinItem> getWinItemList() {
		List<WinItem> list = new ArrayList<WinItem>();

		Integer unit = this.getNormalOneWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(KlsfPlayType.NormalOne.getTypeName(), unit));
		}

		unit = this.getRedOneWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(KlsfPlayType.RedOne.getTypeName(), unit));
		}

		unit = this.getRandomTwoWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(KlsfPlayType.RandomTwo.getTypeName(), unit));
		}

		unit = this.getConnectTwoGroupWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(KlsfPlayType.ConnectTwoGroup.getTypeName(), unit));
		}
		unit = this.getConnectTwoDirectWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(KlsfPlayType.ConnectTwoDirect.getTypeName(), unit));
		}
		unit = this.getRandomThreeWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(KlsfPlayType.RandomThree.getTypeName(), unit));
		}

		unit = this.getForeThreeGroupWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(KlsfPlayType.ForeThreeGroup.getTypeName(), unit));
		}

		unit = this.getForeThreeDirectWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(KlsfPlayType.ForeThreeDirect.getTypeName(), unit));
		}

		unit = this.getRandomFourWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(KlsfPlayType.RandomFour.getTypeName(), unit));
		}

		unit = this.getRandomFiveWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(KlsfPlayType.RandomFive.getTypeName(), unit));
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
			prize = KlsfPlayType.NormalOne.getPrize();
			varWonLineText.setVar("PRIZEITEM", KlsfPlayType.NormalOne.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getRedOneWinUnits();
		if (unit != null && unit > 0) {
			prize = KlsfPlayType.RedOne.getPrize();
			varWonLineText.setVar("PRIZEITEM", KlsfPlayType.RedOne.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getRandomTwoWinUnits();
		if (unit != null && unit > 0) {
			prize = KlsfPlayType.RandomTwo.getPrize();
			varWonLineText.setVar("PRIZEITEM", KlsfPlayType.RandomTwo.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getConnectTwoGroupWinUnits();
		if (unit != null && unit > 0) {
			prize = KlsfPlayType.ConnectTwoGroup.getPrize();
			varWonLineText.setVar("PRIZEITEM", KlsfPlayType.ConnectTwoGroup.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getConnectTwoDirectWinUnits();
		if (unit != null && unit > 0) {
			prize = KlsfPlayType.ConnectTwoDirect.getPrize();
			varWonLineText.setVar("PRIZEITEM", KlsfPlayType.ConnectTwoDirect.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getRandomThreeWinUnits();
		if (unit != null && unit > 0) {
			prize = KlsfPlayType.RandomThree.getPrize();
			varWonLineText.setVar("PRIZEITEM", KlsfPlayType.RandomThree.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getForeThreeGroupWinUnits();
		if (unit != null && unit > 0) {
			prize = KlsfPlayType.ForeThreeGroup.getPrize();
			varWonLineText.setVar("PRIZEITEM", KlsfPlayType.ForeThreeGroup.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getForeThreeDirectWinUnits();
		if (unit != null && unit > 0) {
			prize = KlsfPlayType.ForeThreeDirect.getPrize();
			varWonLineText.setVar("PRIZEITEM", KlsfPlayType.ForeThreeDirect.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getRandomFourWinUnits();
		if (unit != null && unit > 0) {
			prize = KlsfPlayType.RandomFour.getPrize();
			varWonLineText.setVar("PRIZEITEM", KlsfPlayType.RandomFour.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getRandomFiveWinUnits();
		if (unit != null && unit > 0) {
			prize = KlsfPlayType.RandomFive.getPrize();
			varWonLineText.setVar("PRIZEITEM", KlsfPlayType.RandomFive.getTypeName());
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
			prize = KlsfPlayType.NormalOne.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getRedOneWinUnits();
		if (unit != null && unit > 0) {
			prize = KlsfPlayType.RedOne.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getRandomTwoWinUnits();
		if (unit != null && unit > 0) {
			prize = KlsfPlayType.RandomTwo.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getConnectTwoGroupWinUnits();
		if (unit != null && unit > 0) {
			prize = KlsfPlayType.ConnectTwoGroup.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getConnectTwoDirectWinUnits();
		if (unit != null && unit > 0) {
			prize = KlsfPlayType.ConnectTwoDirect.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getRandomThreeWinUnits();
		if (unit != null && unit > 0) {
			prize = KlsfPlayType.RandomThree.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getForeThreeGroupWinUnits();
		if (unit != null && unit > 0) {
			prize = KlsfPlayType.ForeThreeGroup.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getForeThreeDirectWinUnits();
		if (unit != null && unit > 0) {
			prize = KlsfPlayType.ForeThreeDirect.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getRandomFourWinUnits();
		if (unit != null && unit > 0) {
			prize = KlsfPlayType.RandomFour.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getRandomFiveWinUnits();
		if (unit != null && unit > 0) {
			prize = KlsfPlayType.RandomFive.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		return totalPrize;
	}

	public double getPrize() {
		double totalPrize = 0;
		int prize = 0;
		Integer unit = this.getNormalOneWinUnits();
		if (unit != null && unit > 0) {
			prize = KlsfPlayType.NormalOne.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getRedOneWinUnits();
		if (unit != null && unit > 0) {
			prize = KlsfPlayType.RedOne.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getRandomTwoWinUnits();
		if (unit != null && unit > 0) {
			prize = KlsfPlayType.RandomTwo.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getConnectTwoGroupWinUnits();
		if (unit != null && unit > 0) {
			prize = KlsfPlayType.ConnectTwoGroup.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getConnectTwoDirectWinUnits();
		if (unit != null && unit > 0) {
			prize = KlsfPlayType.ConnectTwoDirect.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getRandomThreeWinUnits();
		if (unit != null && unit > 0) {
			prize = KlsfPlayType.RandomThree.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getForeThreeGroupWinUnits();
		if (unit != null && unit > 0) {
			prize = KlsfPlayType.ForeThreeGroup.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getForeThreeDirectWinUnits();
		if (unit != null && unit > 0) {
			prize = KlsfPlayType.ForeThreeDirect.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getRandomFourWinUnits();
		if (unit != null && unit > 0) {
			prize = KlsfPlayType.RandomFour.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getRandomFiveWinUnits();
		if (unit != null && unit > 0) {
			prize = KlsfPlayType.RandomFive.getPrize();
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
	 * @return the redOneWinUnits
	 */
	public Integer getRedOneWinUnits() {
		return redOneWinUnits;
	}

	/**
	 * @param redOneWinUnits the redOneWinUnits to set
	 */
	public void setRedOneWinUnits(Integer redOneWinUnits) {
		this.redOneWinUnits = redOneWinUnits;
	}

	public void addRedOneWinUnits(Integer winUnits) {
		if (this.redOneWinUnits != null) {
			this.redOneWinUnits += winUnits;
		} else {
			this.redOneWinUnits = winUnits;
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
	 * @return the connectTwoGroupWinUnits
	 */
	public Integer getConnectTwoGroupWinUnits() {
		return connectTwoGroupWinUnits;
	}

	/**
	 * @param connectTwoGroupWinUnits the connectTwoGroupWinUnits to set
	 */
	public void setConnectTwoGroupWinUnits(Integer connectTwoGroupWinUnits) {
		this.connectTwoGroupWinUnits = connectTwoGroupWinUnits;
	}

	public void addConnectTwoGroupWinUnits(Integer winUnits) {
		if (this.connectTwoGroupWinUnits != null) {
			this.connectTwoGroupWinUnits += winUnits;
		} else {
			this.connectTwoGroupWinUnits = winUnits;
		}
	}

	/**
	 * @return the connectTwoDirectWinUnits
	 */
	public Integer getConnectTwoDirectWinUnits() {
		return connectTwoDirectWinUnits;
	}

	/**
	 * @param connectTwoDirectWinUnits the connectTwoDirectWinUnits to set
	 */
	public void setConnectTwoDirectWinUnits(Integer connectTwoDirectWinUnits) {
		this.connectTwoDirectWinUnits = connectTwoDirectWinUnits;
	}

	public void addConnectTwoDirectWinUnits(Integer winUnits) {
		if (this.connectTwoDirectWinUnits != null) {
			this.connectTwoDirectWinUnits += winUnits;
		} else {
			this.connectTwoDirectWinUnits = winUnits;
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

	public void addKlsfWinUnits(KlsfWinUnits winUnit) {
		this.addNormalOneWinUnits(winUnit.getNormalOneWinUnits());
		this.addRedOneWinUnits(winUnit.getRedOneWinUnits());
		this.addRandomTwoWinUnits(winUnit.getRandomTwoWinUnits());
		this.addConnectTwoDirectWinUnits(winUnit.getConnectTwoDirectWinUnits());
		this.addConnectTwoGroupWinUnits(winUnit.getConnectTwoGroupWinUnits());
		this.addForeThreeDirectWinUnits(winUnit.getForeThreeDirectWinUnits());
		this.addForeThreeGroupWinUnits(winUnit.getForeThreeGroupWinUnits());
		this.addRandomThreeWinUnits(winUnit.getRandomThreeWinUnits());
		this.addRandomFourWinUnits(winUnit.getRandomFourWinUnits());
		this.addRandomFiveWinUnits(winUnit.getRandomFiveWinUnits());
	}

}
