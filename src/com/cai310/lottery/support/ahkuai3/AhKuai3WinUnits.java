package com.cai310.lottery.support.ahkuai3;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;

import com.cai310.lottery.Constant;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.prizeutils.template.VariableString;
import com.cai310.lottery.support.WinItem;
/**
 * 快3各玩法的中奖注数
 * <p>Title: AhKuai3WinUnits.java </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: miracle</p>
 * @author leo
 * @date 2014-1-8 上午11:23:46 
 * @version 1.0
 */
public class AhKuai3WinUnits {
	/**和值中奖各注数金额*/
	private Integer heZhiPrize;
	/**和值中奖注数*/
	private Integer heZhiWinUnits;
	/**三同号通选中奖注数*/
	private Integer threeTXWinUnits;
	/**三同号单选中奖注数*/
	private Integer threeDXWinUnits;
	/**二同号复选中奖注数*/
	private Integer twoFXWinUnits;
	/**二同号单选中奖注数*/
	private Integer twoDXWinUnits;
	/**三不同号中奖注数*/
	private Integer randomThreeWinUnits;
	/**二不同号中奖注数*/
	private Integer randomTwoWinUnits;
	/**三连号通选中奖注数*/
	private Integer threeLXWinUnits;
	/**任意选号中奖注数*/
	private Integer OptionalWinUnits;
	
	/**
	 * 模板：{PRIZEITEM}:{WINUNITS}注,{PRIZE}元;}
	 */
	private String prizeTemplate = "{PRIZEITEM}:{WINUNITS}注,{PRIZE}元;";
	
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
	/**
	 * 全部清空
	 */
	public void reset(){
		this.setHeZhiWinUnits(null);
		this.setOptionalWinUnits(null);
		this.setRandomThreeWinUnits(null);
		this.setRandomTwoWinUnits(null);
		this.setThreeDXWinUnits(null);
		this.setThreeLXWinUnits(null);
		this.setThreeTXWinUnits(null);
		this.setTwoDXWinUnits(null);
		this.setTwoFXWinUnits(null);
	}
	/**
	 * 返回中奖注数
	 * @return
	 */
	private Integer[] getWinUnits() {
		return new Integer[] { this.getHeZhiWinUnits(), this.getThreeTXWinUnits(),
				this.getThreeDXWinUnits(), this.getTwoFXWinUnits(), this.getTwoDXWinUnits(),
				this.getRandomThreeWinUnits(), this.getRandomTwoWinUnits(), this.getThreeLXWinUnits(),
				this.getOptionalWinUnits()};
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
	 * 返回各投注的中奖注数
	 * @return
	 */
	public List<WinItem> getWinItemList() {
		List<WinItem> list = new ArrayList<WinItem>();

		Integer unit = this.getHeZhiWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(AhKuai3PlayType.HeZhi.getTypeName(), unit));
		}

		unit = this.getRandomTwoWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(AhKuai3PlayType.RandomTwo.getTypeName(), unit));
		}

		unit = this.getThreeTXWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(AhKuai3PlayType.ThreeTX.getTypeName(), unit));
		}
		unit = this.getThreeDXWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(AhKuai3PlayType.ThreeDX.getTypeName(), unit));
		}
		unit = this.getRandomThreeWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(AhKuai3PlayType.RandomThree.getTypeName(), unit));
		}

		unit = this.getTwoFXWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(AhKuai3PlayType.TwoFX.getTypeName(), unit));
		}

		unit = this.getTwoDXWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(AhKuai3PlayType.TwoDX.getTypeName(), unit));
		}

		unit = this.getThreeLXWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(AhKuai3PlayType.ThreeLX.getTypeName(), unit));
		}


		return list;
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
		Integer unit = this.getHeZhiWinUnits();
		if (unit != null && unit > 0) {
			prize = this.heZhiPrize;
			varWonLineText.setVar("PRIZEITEM", AhKuai3PlayType.HeZhi.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getRandomTwoWinUnits();
		if (unit != null && unit > 0) {
			prize = AhKuai3PlayType.RandomTwo.getPrize();
			varWonLineText.setVar("PRIZEITEM", AhKuai3PlayType.RandomTwo.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getThreeTXWinUnits();
		if (unit != null && unit > 0) {
			prize = AhKuai3PlayType.ThreeTX.getPrize();
			varWonLineText.setVar("PRIZEITEM", AhKuai3PlayType.ThreeTX.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getThreeDXWinUnits();
		if (unit != null && unit > 0) {
			prize = AhKuai3PlayType.ThreeDX.getPrize();
			varWonLineText.setVar("PRIZEITEM", AhKuai3PlayType.ThreeDX.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getRandomThreeWinUnits();
		if (unit != null && unit > 0) {
			prize = AhKuai3PlayType.RandomThree.getPrize();
			varWonLineText.setVar("PRIZEITEM", AhKuai3PlayType.RandomThree.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getTwoFXWinUnits();
		if (unit != null && unit > 0) {
			prize = AhKuai3PlayType.TwoFX.getPrize();
			varWonLineText.setVar("PRIZEITEM", AhKuai3PlayType.TwoFX.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getTwoDXWinUnits();
		if (unit != null && unit > 0) {
			prize = AhKuai3PlayType.TwoDX.getPrize();
			varWonLineText.setVar("PRIZEITEM", AhKuai3PlayType.TwoDX.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getThreeLXWinUnits();
		if (unit != null && unit > 0) {
			prize = AhKuai3PlayType.ThreeLX.getPrize();
			varWonLineText.setVar("PRIZEITEM", AhKuai3PlayType.ThreeLX.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}


		return prizeDesc;
	}
	
	/**
	 * 税后奖金
	 * @return
	 */
	public double getPrizeAfterTax() {
		double totalPrize = 0;
		int prize = 0;
		Integer unit = this.getHeZhiWinUnits();
		if (unit != null && unit > 0) {
			prize = this.heZhiPrize;
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getRandomTwoWinUnits();
		if (unit != null && unit > 0) {
			prize = AhKuai3PlayType.RandomTwo.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getThreeTXWinUnits();
		if (unit != null && unit > 0) {
			prize = AhKuai3PlayType.ThreeTX.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getThreeDXWinUnits();
		if (unit != null && unit > 0) {
			prize = AhKuai3PlayType.ThreeDX.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getRandomThreeWinUnits();
		if (unit != null && unit > 0) {
			prize = AhKuai3PlayType.RandomThree.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getThreeLXWinUnits();
		if (unit != null && unit > 0) {
			prize = AhKuai3PlayType.ThreeLX.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getTwoDXWinUnits();
		if (unit != null && unit > 0) {
			prize = AhKuai3PlayType.TwoDX.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getTwoFXWinUnits();
		if (unit != null && unit > 0) {
			prize = AhKuai3PlayType.TwoFX.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		return totalPrize;
	}
	
	/**
	 * 中奖奖金
	 * @return
	 */
	public double getPrize() {
		double totalPrize = 0;
		int prize = 0;
		Integer unit = this.getHeZhiWinUnits();
		if (unit != null && unit > 0) {
			prize = this.heZhiPrize;
			totalPrize += unit * prize;
		}
		unit = this.getRandomTwoWinUnits();
		if (unit != null && unit > 0) {
			prize = AhKuai3PlayType.RandomTwo.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getThreeDXWinUnits();
		if (unit != null && unit > 0) {
			prize = AhKuai3PlayType.ThreeDX.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getThreeLXWinUnits();
		if (unit != null && unit > 0) {
			prize = AhKuai3PlayType.ThreeLX.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getRandomThreeWinUnits();
		if (unit != null && unit > 0) {
			prize = AhKuai3PlayType.RandomThree.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getThreeTXWinUnits();
		if (unit != null && unit > 0) {
			prize = AhKuai3PlayType.ThreeTX.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getTwoDXWinUnits();
		if (unit != null && unit > 0) {
			prize = AhKuai3PlayType.TwoDX.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getTwoFXWinUnits();
		if (unit != null && unit > 0) {
			prize = AhKuai3PlayType.TwoFX.getPrize();
			totalPrize += unit * prize;
		}
		return totalPrize;
	}
	/**
	 * 嘉奖
	 * @return
	 */
	public double getPrize_add_price() {
		double totalPrize = 0;
		int prize = 0;
		Integer unit = this.getHeZhiWinUnits();
		if (unit != null && unit > 0) {
			prize = this.heZhiPrize;
			totalPrize += unit * prize;
		}
		unit = this.getRandomTwoWinUnits();
		if (unit != null && unit > 0) {
			prize = AhKuai3PlayType.RandomTwo.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getThreeDXWinUnits();
		if (unit != null && unit > 0) {
			prize = AhKuai3PlayType.ThreeDX.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getThreeLXWinUnits();
		if (unit != null && unit > 0) {
			prize = AhKuai3PlayType.ThreeLX.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getRandomThreeWinUnits();
		if (unit != null && unit > 0) {
			prize = AhKuai3PlayType.RandomThree.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getThreeTXWinUnits();
		if (unit != null && unit > 0) {
			prize = AhKuai3PlayType.ThreeTX.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getTwoDXWinUnits();
		if (unit != null && unit > 0) {
			prize = AhKuai3PlayType.TwoDX.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getTwoFXWinUnits();
		if (unit != null && unit > 0) {
			prize = AhKuai3PlayType.TwoFX.getPrize();
			totalPrize += unit * prize;
		}
		return totalPrize;
	}
	
	/**
	 * 奖金描述
	 * 
	 * @return
	 */
	public String getPrizeDetail_add_price() {
		VariableString varWonLineText = new VariableString(prizeTemplate, null);
		String prizeDesc = "";
		int prize = 0;
		Integer unit = this.getHeZhiWinUnits();
		if (unit != null && unit > 0) {
			prize = this.heZhiPrize;
			varWonLineText.setVar("PRIZEITEM", AhKuai3PlayType.HeZhi.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getRandomTwoWinUnits();
		if (unit != null && unit > 0) {
			prize = AhKuai3PlayType.RandomTwo.getPrize();
			varWonLineText.setVar("PRIZEITEM", AhKuai3PlayType.RandomTwo.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getThreeTXWinUnits();
		if (unit != null && unit > 0) {
			prize = AhKuai3PlayType.ThreeTX.getPrize();
			varWonLineText.setVar("PRIZEITEM", AhKuai3PlayType.ThreeTX.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getThreeDXWinUnits();
		if (unit != null && unit > 0) {
			prize = AhKuai3PlayType.ThreeDX.getPrize();
			varWonLineText.setVar("PRIZEITEM", AhKuai3PlayType.ThreeDX.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getRandomThreeWinUnits();
		if (unit != null && unit > 0) {
			prize = AhKuai3PlayType.RandomThree.getPrize();
			varWonLineText.setVar("PRIZEITEM", AhKuai3PlayType.RandomThree.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getTwoFXWinUnits();
		if (unit != null && unit > 0) {
			prize = AhKuai3PlayType.TwoFX.getPrize();
			varWonLineText.setVar("PRIZEITEM", AhKuai3PlayType.TwoFX.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getTwoDXWinUnits();
		if (unit != null && unit > 0) {
			prize = AhKuai3PlayType.TwoDX.getPrize();
			varWonLineText.setVar("PRIZEITEM", AhKuai3PlayType.TwoDX.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getThreeLXWinUnits();
		if (unit != null && unit > 0) {
			prize = AhKuai3PlayType.ThreeLX.getPrize();
			varWonLineText.setVar("PRIZEITEM", AhKuai3PlayType.ThreeLX.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}

		return prizeDesc;
	}
	
	/**
	 * 税后奖金
	 * @return
	 */
	public double getPrizeAfterTax_add_price() {
		double totalPrize = 0;
		int prize = 0;
		Integer unit = this.getHeZhiWinUnits();
		if (unit != null && unit > 0) {
			prize = this.heZhiPrize;
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getRandomTwoWinUnits();
		if (unit != null && unit > 0) {
			prize = AhKuai3PlayType.RandomTwo.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getThreeTXWinUnits();
		if (unit != null && unit > 0) {
			prize = AhKuai3PlayType.ThreeTX.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getThreeDXWinUnits();
		if (unit != null && unit > 0) {
			prize = AhKuai3PlayType.ThreeDX.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getRandomThreeWinUnits();
		if (unit != null && unit > 0) {
			prize = AhKuai3PlayType.RandomThree.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getThreeLXWinUnits();
		if (unit != null && unit > 0) {
			prize = AhKuai3PlayType.ThreeLX.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getTwoDXWinUnits();
		if (unit != null && unit > 0) {
			prize = AhKuai3PlayType.TwoDX.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getTwoFXWinUnits();
		if (unit != null && unit > 0) {
			prize = AhKuai3PlayType.TwoFX.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		return totalPrize;
	}
	
	
	public String getPrizeTemplate() {
		return prizeTemplate;
	}

	public void setPrizeTemplate(String prizeTemplate) {
		this.prizeTemplate = prizeTemplate;
	}

	public Integer getHeZhiWinUnits() {
		return heZhiWinUnits;
	}

	public void setHeZhiWinUnits(Integer heZhiWinUnits) {
		this.heZhiWinUnits = heZhiWinUnits;
	}
	
	public void addHeZhiWinUnits(Integer winUnits) {
		if (this.heZhiWinUnits != null) {
			this.heZhiWinUnits += winUnits;
		} else {
			this.heZhiWinUnits = winUnits;
		}
	}
	
	public Integer getThreeTXWinUnits() {
		return threeTXWinUnits;
	}

	public void setThreeTXWinUnits(Integer threeTXWinUnits) {
		this.threeTXWinUnits = threeTXWinUnits;
	}
	
	public void addThreeTXWinUnits(Integer winUnits){
		if(this.threeTXWinUnits!=null){
			this.threeTXWinUnits+=winUnits;
		}else{
			this.threeTXWinUnits=winUnits;
		}
	}
	
	public Integer getThreeDXWinUnits() {
		return threeDXWinUnits;
	}

	public void setThreeDXWinUnits(Integer threeDXWinUnits) {
		this.threeDXWinUnits = threeDXWinUnits;
	}
	
	public void addThreeDXWinUnits(Integer winUnits){
		if(this.threeDXWinUnits!=null){
			this.threeDXWinUnits+=winUnits;
		}else{
			this.threeDXWinUnits=winUnits;
		}
	}
	
	public Integer getTwoFXWinUnits() {
		return twoFXWinUnits;
	}

	public void setTwoFXWinUnits(Integer twoFXWinUnits) {
		this.twoFXWinUnits = twoFXWinUnits;
	}
	
	public void addTwoFXWinUnits(Integer winUnits){
		if(this.twoFXWinUnits!=null){
			this.twoFXWinUnits+=winUnits;
		}else{
			this.twoFXWinUnits=winUnits;
		}
	}
	
	public Integer getTwoDXWinUnits() {
		return twoDXWinUnits;
	}

	public void setTwoDXWinUnits(Integer twoDXWinUnits) {
		this.twoDXWinUnits = twoDXWinUnits;
	}
	
	public void addTwoDXWinUnits(Integer winUnits){
		if(this.twoDXWinUnits!=null){
			this.twoDXWinUnits+=winUnits;
		}else{
			this.twoDXWinUnits=winUnits;
		}
	}
	
	public Integer getRandomThreeWinUnits() {
		return randomThreeWinUnits;
	}

	public void setRandomThreeWinUnits(Integer randomThreeWinUnits) {
		this.randomThreeWinUnits = randomThreeWinUnits;
	}
	
	public void addRandomThreeWinUnits(Integer winUnits){
		if(this.randomThreeWinUnits!=null){
			this.randomThreeWinUnits+=winUnits;
		}else{
			this.randomThreeWinUnits=winUnits;
		}
	}
	
	public Integer getRandomTwoWinUnits() {
		return randomTwoWinUnits;
	}

	public void setRandomTwoWinUnits(Integer randomTwoWinUnits) {
		this.randomTwoWinUnits = randomTwoWinUnits;
	}
	
	public void addRandomTwoWinUnits(Integer winUnits){
		if(this.randomTwoWinUnits!=null){
			this.randomTwoWinUnits+=winUnits;
		}else{
			this.randomTwoWinUnits=winUnits;
		}
	}
	
	public Integer getThreeLXWinUnits() {
		return threeLXWinUnits;
	}

	public void setThreeLXWinUnits(Integer threeLXWinUnits) {
		this.threeLXWinUnits = threeLXWinUnits;
	}
	
	public void addThreeLXWinUnits(Integer winUnits){
		if(this.threeLXWinUnits!=null){
			this.threeLXWinUnits+=winUnits;
		}else{
			this.threeLXWinUnits=winUnits;
		}
	}
	
	public Integer getOptionalWinUnits() {
		return OptionalWinUnits;
	}

	public void setOptionalWinUnits(Integer optionalWinUnits) {
		OptionalWinUnits = optionalWinUnits;
	}
	public void addOptionalWinUnits(Integer winUnits){
		if(this.OptionalWinUnits!=null){
			this.OptionalWinUnits+=winUnits;
		}else{
			this.OptionalWinUnits=winUnits;
		}
	}
	
	public void addAhKuai3WinUnits(AhKuai3WinUnits winUnit) {
		this.addHeZhiWinUnits(winUnit.getHeZhiWinUnits());
		this.addOptionalWinUnits(winUnit.getOptionalWinUnits());
		this.addRandomThreeWinUnits(winUnit.getRandomThreeWinUnits());
		this.addRandomTwoWinUnits(winUnit.getRandomTwoWinUnits());
		this.addThreeDXWinUnits(winUnit.getThreeDXWinUnits());
		this.addThreeLXWinUnits(winUnit.getThreeLXWinUnits());
		this.addThreeTXWinUnits(winUnit.getThreeTXWinUnits());
		this.addTwoDXWinUnits(winUnit.getTwoDXWinUnits());
		this.addTwoFXWinUnits(winUnit.getTwoFXWinUnits());
	}
	public Integer getHeZhiPrize() {
		return heZhiPrize;
	}
	public void setHeZhiPrize(Integer heZhiPrize) {
		this.heZhiPrize = heZhiPrize;
	}
}
