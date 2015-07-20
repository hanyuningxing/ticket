package com.cai310.lottery.support.klpk;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;

import com.cai310.lottery.Constant;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.prizeutils.template.VariableString;
import com.cai310.lottery.support.WinItem;

public class KlpkWinUnits {

	/** 同花包选中奖注数 **/
	private Integer sameBaoUnits;

	/** 同花单选中奖注数 **/
	private Integer sameUnits;
	
	/** 顺子包选中奖注数 **/
	private Integer shunBaoUnits;

	/** 顺子单选中奖注数 **/
	private Integer shunUnits;
	
	/** 对子包选中奖注数 **/
	private Integer duiBaoUnits;

	/** 对子单选中奖注数 **/
	private Integer duiUnits;
	
	/** 任选一中奖注数 **/
	private Integer randomOneWinUnits;
	
	/** 任选二中奖注数 **/
	private Integer randomTwoWinUnits;
	
	/** 任选三中奖注数 **/
	private Integer randomThreeWinUnits;
	
	/** 任选四中奖注数 **/
	private Integer randomFourWinUnits;

	/** 任选五中奖注数 **/
	private Integer randomFiveWinUnits;

	/** 任选六中奖注数 **/
	private Integer randomSixWinUnits;

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
		this.setShunBaoUnits(null);
		this.setDuiBaoUnits(null);
		this.setSameBaoUnits(null);
		this.setShunUnits(null);
		this.setDuiUnits(null);
		this.setSameUnits(null);
		this.setRandomOneWinUnits(null);
		this.setRandomTwoWinUnits(null);
		this.setRandomThreeWinUnits(null);
		this.setRandomFourWinUnits(null);
		this.setRandomFiveWinUnits(null);
		this.setRandomSixWinUnits(null);
	}

	private Integer[] getWinUnits() {
		return new Integer[] { this.getSameBaoUnits(), this.getSameUnits(),
				this.getShunBaoUnits(), this.getShunUnits(),this.getDuiBaoUnits(), this.getDuiUnits(), 
				this.getRandomOneWinUnits(),this.getRandomTwoWinUnits(), this.getRandomThreeWinUnits(), this.getRandomFourWinUnits(),
				this.getRandomFiveWinUnits(), this.getRandomSixWinUnits()};
	}

	public List<WinItem> getWinItemList() {
		List<WinItem> list = new ArrayList<WinItem>();

		Integer unit = this.getSameBaoUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(KlpkPlayType.SAMEBAO.getTypeName(), unit));
		}
		unit = this.getSameUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(KlpkPlayType.SAME.getTypeName(), unit));
		}
		unit = this.getShunBaoUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(KlpkPlayType.SHUNBAO.getTypeName(), unit));
		}
		unit = this.getShunUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(KlpkPlayType.SHUN.getTypeName(), unit));
		}
		unit = this.getDuiBaoUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(KlpkPlayType.DUIBAO.getTypeName(), unit));
		}
		unit = this.getDuiUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(KlpkPlayType.DUI.getTypeName(), unit));
		}
		unit = this.getRandomOneWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(KlpkPlayType.RandomOne.getTypeName(), unit));
		}

		unit = this.getRandomTwoWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(KlpkPlayType.RandomTwo.getTypeName(), unit));
		}

		unit = this.getRandomThreeWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(KlpkPlayType.RandomThree.getTypeName(), unit));
		}

		unit = this.getRandomFourWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(KlpkPlayType.RandomFour.getTypeName(), unit));
		}

		unit = this.getRandomFiveWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(KlpkPlayType.RandomFive.getTypeName(), unit));
		}

		unit = this.getRandomSixWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(KlpkPlayType.RandomSix.getTypeName(), unit));
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
		Integer unit = this.getSameBaoUnits();
		if (unit != null && unit > 0) {
			prize = KlpkPlayType.SAMEBAO.getPrize();
			varWonLineText.setVar("PRIZEITEM", KlpkPlayType.SAMEBAO.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getSameUnits();
		if (unit != null && unit > 0) {
			prize = KlpkPlayType.SAME.getPrize();
			varWonLineText.setVar("PRIZEITEM", KlpkPlayType.SAME.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getShunBaoUnits();
		if (unit != null && unit > 0) {
			prize = KlpkPlayType.SHUNBAO.getPrize();
			varWonLineText.setVar("PRIZEITEM", KlpkPlayType.SHUNBAO.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getShunUnits();
		if (unit != null && unit > 0) {
			prize = KlpkPlayType.SHUN.getPrize();
			varWonLineText.setVar("PRIZEITEM", KlpkPlayType.SHUN.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getDuiBaoUnits();
		if (unit != null && unit > 0) {
			prize = KlpkPlayType.DUIBAO.getPrize();
			varWonLineText.setVar("PRIZEITEM", KlpkPlayType.DUIBAO.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getDuiUnits();
		if (unit != null && unit > 0) {
			prize = KlpkPlayType.DUI.getPrize();
			varWonLineText.setVar("PRIZEITEM", KlpkPlayType.DUI.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		
		unit = this.getRandomOneWinUnits();
		if (unit != null && unit > 0) {
			prize = KlpkPlayType.RandomOne.getPrize();
			varWonLineText.setVar("PRIZEITEM", KlpkPlayType.RandomOne.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getRandomTwoWinUnits();
		if (unit != null && unit > 0) {
			prize = KlpkPlayType.RandomTwo.getPrize();
			varWonLineText.setVar("PRIZEITEM", KlpkPlayType.RandomTwo.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		
		unit = this.getRandomThreeWinUnits();
		if (unit != null && unit > 0) {
			prize = KlpkPlayType.RandomThree.getPrize();
			varWonLineText.setVar("PRIZEITEM", KlpkPlayType.RandomThree.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getRandomFourWinUnits();
		if (unit != null && unit > 0) {
			prize = KlpkPlayType.RandomFour.getPrize();
			varWonLineText.setVar("PRIZEITEM", KlpkPlayType.RandomFour.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getRandomFiveWinUnits();
		if (unit != null && unit > 0) {
			prize = KlpkPlayType.RandomFive.getPrize();
			varWonLineText.setVar("PRIZEITEM", KlpkPlayType.RandomFive.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}

		unit = this.getRandomSixWinUnits();
		if (unit != null && unit > 0) {
			prize = KlpkPlayType.RandomSix.getPrize();
			varWonLineText.setVar("PRIZEITEM", KlpkPlayType.RandomSix.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		return prizeDesc;
	}

	public double getPrizeAfterTax() {
		double totalPrize = 0;
		int prize = 0;
		Integer unit = this.getSameBaoUnits();
		if (unit != null && unit > 0) {
			prize = KlpkPlayType.SAMEBAO.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getSameUnits();
		if (unit != null && unit > 0) {
			prize = KlpkPlayType.SAME.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getShunBaoUnits();
		if (unit != null && unit > 0) {
			prize = KlpkPlayType.SHUNBAO.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getShunUnits();
		if (unit != null && unit > 0) {
			prize = KlpkPlayType.SHUN.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getDuiBaoUnits();
		if (unit != null && unit > 0) {
			prize = KlpkPlayType.DUIBAO.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getDuiUnits();
		if (unit != null && unit > 0) {
			prize = KlpkPlayType.DUI.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getRandomOneWinUnits();
		if (unit != null && unit > 0) {
			prize = KlpkPlayType.RandomOne.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getRandomTwoWinUnits();
		if (unit != null && unit > 0) {
			prize = KlpkPlayType.RandomTwo.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getRandomThreeWinUnits();
		if (unit != null && unit > 0) {
			prize = KlpkPlayType.RandomThree.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getRandomFourWinUnits();
		if (unit != null && unit > 0) {
			prize = KlpkPlayType.RandomFour.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getRandomFiveWinUnits();
		if (unit != null && unit > 0) {
			prize = KlpkPlayType.RandomFive.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getRandomSixWinUnits();
		if (unit != null && unit > 0) {
			prize = KlpkPlayType.RandomSix.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		return totalPrize;
	}

	public double getPrize() {
		double totalPrize = 0;
		int prize = 0;
		Integer unit = this.getSameBaoUnits();
		if (unit != null && unit > 0) {
			prize = KlpkPlayType.SAMEBAO.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getSameUnits();
		if (unit != null && unit > 0) {
			prize = KlpkPlayType.SAME.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getShunBaoUnits();
		if (unit != null && unit > 0) {
			prize = KlpkPlayType.SHUNBAO.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getShunUnits();
		if (unit != null && unit > 0) {
			prize = KlpkPlayType.SHUN.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getDuiBaoUnits();
		if (unit != null && unit > 0) {
			prize = KlpkPlayType.DUIBAO.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getDuiUnits();
		if (unit != null && unit > 0) {
			prize = KlpkPlayType.DUI.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getRandomOneWinUnits();
		if (unit != null && unit > 0) {
			prize = KlpkPlayType.RandomOne.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getRandomTwoWinUnits();
		if (unit != null && unit > 0) {
			prize = KlpkPlayType.RandomTwo.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getRandomThreeWinUnits();
		if (unit != null && unit > 0) {
			prize = KlpkPlayType.RandomThree.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getRandomFourWinUnits();
		if (unit != null && unit > 0) {
			prize = KlpkPlayType.RandomFour.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getRandomFiveWinUnits();
		if (unit != null && unit > 0) {
			prize = KlpkPlayType.RandomFive.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getRandomSixWinUnits();
		if (unit != null && unit > 0) {
			prize = KlpkPlayType.RandomSix.getPrize();
			totalPrize += unit * prize;
		}
		return totalPrize;
	}

	// ////////////////////////////////////////////////////////////////////////
	

	/**
	 * 
	 */
	public void addSameBaoUnits(Integer winUnits) {
		if (this.sameBaoUnits != null) {
			this.sameBaoUnits += winUnits;
		} else {
			this.sameBaoUnits = winUnits;
		}
	}
	public void addSameUnits(Integer winUnits) {
		if (this.sameUnits != null) {
			this.sameUnits += winUnits;
		} else {
			this.sameUnits = winUnits;
		}
	}

	public void addShunBaoUnits(Integer winUnits) {
		if (this.shunBaoUnits != null) {
			this.shunBaoUnits += winUnits;
		} else {
			this.shunBaoUnits = winUnits;
		}
	}


	public void addShunUnits(Integer winUnits) {
		if (this.shunUnits != null) {
			this.shunUnits += winUnits;
		} else {
			this.shunUnits = winUnits;
		}
	}
	
	public void addDuiBaoUnits(Integer winUnits) {
		if (this.duiBaoUnits != null) {
			this.duiBaoUnits += winUnits;
		} else {
			this.duiBaoUnits = winUnits;
		}
	}


	public void addDuiUnits(Integer winUnits) {
		if (this.duiUnits != null) {
			this.duiUnits += winUnits;
		} else {
			this.duiUnits = winUnits;
		}
	}
	public void addRandomOneWinUnits(Integer winUnits) {
		if (this.randomOneWinUnits != null) {
			this.randomOneWinUnits += winUnits;
		} else {
			this.randomOneWinUnits = winUnits;
		}
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

	
	public void addKlpkWinUnits(KlpkWinUnits winUnit) {
		this.addSameBaoUnits(winUnit.getSameBaoUnits());
		this.addDuiBaoUnits(winUnit.getDuiBaoUnits());
		this.addShunBaoUnits(winUnit.getShunBaoUnits());
		
		this.addSameBaoUnits(winUnit.getSameBaoUnits());
		this.addDuiBaoUnits(winUnit.getDuiBaoUnits());
		this.addShunBaoUnits(winUnit.getShunBaoUnits());
	
		this.addRandomOneWinUnits(winUnit.getRandomOneWinUnits());
		this.addRandomTwoWinUnits(winUnit.getRandomTwoWinUnits());
		this.addRandomThreeWinUnits(winUnit.getRandomThreeWinUnits());
		this.addRandomFourWinUnits(winUnit.getRandomFourWinUnits());
		this.addRandomFiveWinUnits(winUnit.getRandomFiveWinUnits());
		this.addRandomSixWinUnits(winUnit.getRandomSixWinUnits());
		
	}

	public Integer getSameBaoUnits() {
		return sameBaoUnits;
	}

	public void setSameBaoUnits(Integer sameBaoUnits) {
		this.sameBaoUnits = sameBaoUnits;
	}

	public Integer getSameUnits() {
		return sameUnits;
	}

	public void setSameUnits(Integer sameUnits) {
		this.sameUnits = sameUnits;
	}

	public Integer getShunBaoUnits() {
		return shunBaoUnits;
	}

	public void setShunBaoUnits(Integer shunBaoUnits) {
		this.shunBaoUnits = shunBaoUnits;
	}

	public Integer getShunUnits() {
		return shunUnits;
	}

	public void setShunUnits(Integer shunUnits) {
		this.shunUnits = shunUnits;
	}

	public Integer getDuiBaoUnits() {
		return duiBaoUnits;
	}

	public void setDuiBaoUnits(Integer duiBaoUnits) {
		this.duiBaoUnits = duiBaoUnits;
	}

	public Integer getDuiUnits() {
		return duiUnits;
	}

	public void setDuiUnits(Integer duiUnits) {
		this.duiUnits = duiUnits;
	}

	public Integer getRandomOneWinUnits() {
		return randomOneWinUnits;
	}

	public void setRandomOneWinUnits(Integer randomOneWinUnits) {
		this.randomOneWinUnits = randomOneWinUnits;
	}

	public Integer getRandomTwoWinUnits() {
		return randomTwoWinUnits;
	}

	public void setRandomTwoWinUnits(Integer randomTwoWinUnits) {
		this.randomTwoWinUnits = randomTwoWinUnits;
	}

}
