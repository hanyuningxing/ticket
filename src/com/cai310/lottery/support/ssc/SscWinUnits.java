package com.cai310.lottery.support.ssc;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;

import com.cai310.lottery.Constant;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.prizeutils.template.VariableString;
import com.cai310.lottery.support.WinItem;

public class SscWinUnits {
	/** 一星直选  中奖注数  玩法说明：从个位选择1个或多个号码，选号与开奖号码个位一致即中奖10元**/
	private Integer directOneWinUnits;
	/** 二星直选  中奖注数 二星直选:从00~99中选一个两位数投注，奖金100元**/
	private Integer directTwoWinUnits;
	/** 三星直选  中奖注数 三星直选:从000~999中选一个三位数投注，奖金1000元！ **/
	private Integer directThreeWinUnits;
	/** 五星直选  中奖注数 从00000~99999中选一个五位数投注，奖金100000元！ **/
	private Integer directFiveWinUnits;
	
	
	/** 通选五  中五 注数  所选号码与开奖号码相同奖金2万元,前三位或后三位相同奖金200元，前二位或者后二位相同奖金20元。**/
	private Integer allFiveH1WinUnits;
	private Integer allFiveH2WinUnits;
	private Integer allFiveH3WinUnits;
	
	
	private Integer groupTwoH1WinUnits;//二星组选
	
	

	private Integer directTwoSumWinUnits;//二星直选和值
	private Integer  groupSumTwoH1WinUnits;//二星组选和值1
	private Integer  groupSumTwoH2WinUnits;//二星组选和值2
	
	
	private Integer groupThreeWinUnits;//三星组选
	private Integer g3WinUnits;//组三
	private Integer g6WinUnits;//组六
	
	private Integer directThreeSumWinUnits;//三星直选和值
	private Integer groupThreeSumH3WinUnits;//豹子
	private Integer groupThreeSumH2WinUnits;//组三
	private Integer groupThreeSumH1WinUnits;//组六

	private Integer  bigSmallDoubleSingleWinUnits;
	
	
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
		
		this.setDirectOneWinUnits(null);
		this.setDirectTwoWinUnits(null);
		this.setDirectThreeWinUnits(null);
		this.setDirectFiveWinUnits(null);
		
		this.setAllFiveH1WinUnits(null);
		this.setAllFiveH2WinUnits(null);
		this.setAllFiveH3WinUnits(null);
		
		this.setGroupTwoH1WinUnits(null);

		this.setDirectTwoSumWinUnits(null);
		this.setGroupSumTwoH1WinUnits(null);
		this.setGroupSumTwoH2WinUnits(null);
		
		
		this.setG3WinUnits(null);
		this.setG6WinUnits(null);
		this.setGroupThreeWinUnits(null);

		this.setDirectThreeSumWinUnits(null);
		
		this.setGroupThreeSumH1WinUnits(null);
		this.setGroupThreeSumH2WinUnits(null);
		this.setGroupThreeSumH3WinUnits(null);
		
		this.setBigSmallDoubleSingleWinUnits(null);
	}

	private Integer[] getWinUnits() {
		return new Integer[] { 
				
				this.getDirectOneWinUnits(),
				this.getDirectTwoWinUnits(),
				this.getDirectThreeWinUnits(),
				this.getDirectFiveWinUnits(),
				
				this.getAllFiveH1WinUnits(),
				this.getAllFiveH2WinUnits(),
				this.getAllFiveH3WinUnits(),
				
				this.getGroupTwoH1WinUnits(),

				this.getDirectTwoSumWinUnits(),
				this.getGroupSumTwoH1WinUnits(),
				this.getGroupSumTwoH2WinUnits(),
				this.getGroupThreeWinUnits(),
				
				this.getG3WinUnits(),
				this.getG6WinUnits(),
				
				this.getDirectThreeSumWinUnits(),
				this.getGroupThreeSumH1WinUnits(),
				this.getGroupThreeSumH2WinUnits(),
				this.getGroupThreeSumH3WinUnits(),
				
				this.getBigSmallDoubleSingleWinUnits()
		};
	}

	public List<WinItem> getWinItemList() {
		List<WinItem> list = new ArrayList<WinItem>();

		Integer unit = this.getDirectOneWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(SscPlayType.DirectOne.getTypeName(), unit));
		}

		unit = this.getDirectTwoWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(SscPlayType.DirectTwo.getTypeName(), unit));
		}
		unit = this.getDirectThreeWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(SscPlayType.DirectThree.getTypeName(), unit));
		}
		unit = this.getDirectFiveWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(SscPlayType.DirectFive.getTypeName(), unit));
		}
		
		
		unit = this.getAllFiveH2WinUnits()+this.getAllFiveH3WinUnits()+this.getAllFiveH1WinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(SscPlayType.AllFive.getTypeName(), unit));
		}
		
		
		unit = this.getG3WinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(SscPlayType.ThreeGroup3.getTypeName(), unit));
		}
		
		unit = this.getG6WinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(SscPlayType.ThreeGroup6.getTypeName(), unit));
		}
		
		
	
		unit = this.getDirectThreeSumWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(SscPlayType.DirectThreeSum.getTypeName(), unit));
		}
		
		
		unit = this.getGroupThreeSumH1WinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(SscPlayType.GroupThreeSum.getTypeName()+"豹子形态", unit));
		}
		unit = this.getGroupThreeSumH2WinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(SscPlayType.GroupThreeSum.getTypeName()+"组3形态", unit));
		}
		unit = this.getGroupThreeSumH3WinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(SscPlayType.GroupThreeSum.getTypeName()+"组6形态", unit));
		}
		
		
		
		unit = this.getDirectTwoSumWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(SscPlayType.DirectTwoSum.getTypeName(), unit));
		}
		
		unit = this.getGroupSumTwoH1WinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(SscPlayType.GroupTwoSum.getTypeName()+"对子", unit));
		}
		unit = this.getGroupSumTwoH2WinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(SscPlayType.GroupTwoSum.getTypeName()+"非对子", unit));
		}
		
		
		unit = this.getGroupTwoH1WinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(SscPlayType.GroupTwo.getTypeName(), unit));
		}
		
		
		unit = this.getBigSmallDoubleSingleWinUnits();
		if (unit != null && unit > 0) {
			list.add(new WinItem(SscPlayType.BigSmallDoubleSingle.getTypeName(), unit));
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
		Integer unit =  this.getDirectOneWinUnits();
		if (unit != null && unit > 0) {
			prize = SscPlayType.DirectOne.getPrize();
			varWonLineText.setVar("PRIZEITEM", SscPlayType.DirectOne.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getDirectTwoWinUnits();
		if (unit != null && unit > 0) {
			prize = SscPlayType.DirectTwo.getPrize();
			varWonLineText.setVar("PRIZEITEM", SscPlayType.DirectTwo.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getDirectThreeWinUnits();
		if (unit != null && unit > 0) {
			prize = SscPlayType.DirectThree.getPrize();
			varWonLineText.setVar("PRIZEITEM", SscPlayType.DirectThree.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		
		unit = this.getDirectFiveWinUnits();
		if (unit != null && unit > 0) {
			prize = SscPlayType.DirectFive.getPrize();
			varWonLineText.setVar("PRIZEITEM", SscPlayType.DirectFive.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}

		
		unit = this.getG3WinUnits();
		if (unit != null && unit > 0) {
			prize = SscPlayType.ThreeGroup3.getPrize();
			varWonLineText.setVar("PRIZEITEM", SscPlayType.ThreeGroup3.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		
		unit = this.getG6WinUnits();
		if (unit != null && unit > 0) {
			prize = SscPlayType.ThreeGroup6.getPrize();
			varWonLineText.setVar("PRIZEITEM", SscPlayType.ThreeGroup6.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		
		unit = this.getDirectThreeSumWinUnits();
		if (unit != null && unit > 0) {
			prize = SscPlayType.DirectThreeSum.getPrize();
			varWonLineText.setVar("PRIZEITEM", SscPlayType.DirectThreeSum.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		
		unit = this.getGroupThreeSumH1WinUnits();
		if (unit != null && unit > 0) {
			prize = SscPlayType.GroupThreeSum.getPrize();
			varWonLineText.setVar("PRIZEITEM", SscPlayType.GroupThreeSum.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getGroupThreeSumH2WinUnits();
		if (unit != null && unit > 0) {
			prize = SscPlayType.GroupThreeSum.getPrize2();
			varWonLineText.setVar("PRIZEITEM", SscPlayType.GroupThreeSum.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getGroupThreeSumH3WinUnits();
		if (unit != null && unit > 0) {
			prize = SscPlayType.GroupThreeSum.getPrize3();
			varWonLineText.setVar("PRIZEITEM", SscPlayType.GroupThreeSum.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		
		
		unit = this.getDirectTwoSumWinUnits();
		if (unit != null && unit > 0) {
			prize = SscPlayType.DirectTwoSum.getPrize();
			varWonLineText.setVar("PRIZEITEM", SscPlayType.DirectTwoSum.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		
		unit = this.getGroupSumTwoH1WinUnits();
		if (unit != null && unit > 0) {
			prize = SscPlayType.GroupTwoSum.getPrize();
			varWonLineText.setVar("PRIZEITEM", SscPlayType.GroupTwoSum.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getGroupSumTwoH2WinUnits();
		if (unit != null && unit > 0) {
			prize = SscPlayType.GroupTwoSum.getPrize2();
			varWonLineText.setVar("PRIZEITEM", SscPlayType.GroupTwoSum.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		
		unit = this.getGroupTwoH1WinUnits();
		if (unit != null && unit > 0) {
			prize = SscPlayType.GroupTwo.getPrize();
			varWonLineText.setVar("PRIZEITEM", SscPlayType.GroupTwo.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		
		
		unit = this.getAllFiveH1WinUnits();
		if (unit != null && unit > 0) {
			prize = SscPlayType.AllFive.getPrize();
			varWonLineText.setVar("PRIZEITEM", SscPlayType.AllFive.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		unit = this.getAllFiveH2WinUnits();
		if (unit != null && unit > 0) {
			prize = SscPlayType.AllFive.getPrize2();
			varWonLineText.setVar("PRIZEITEM", SscPlayType.AllFive.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		
		unit = this.getAllFiveH3WinUnits();
		if (unit != null && unit > 0) {
			prize = SscPlayType.AllFive.getPrize3();
			varWonLineText.setVar("PRIZEITEM", SscPlayType.AllFive.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		
		
		unit = this.getBigSmallDoubleSingleWinUnits();
		if (unit != null && unit > 0) {
			prize = SscPlayType.BigSmallDoubleSingle.getPrize();
			varWonLineText.setVar("PRIZEITEM", SscPlayType.BigSmallDoubleSingle.getTypeName());
			varWonLineText.setVar("WINUNITS", unit);
			varWonLineText.setVar("PRIZE", prize >= 10000.0d ? unit * prize * 0.8d : unit * prize);
			prizeDesc += varWonLineText.toString();
		}
		
		return prizeDesc;
	}

	public double getPrizeAfterTax() {
		double totalPrize = 0;
		int prize = 0;
		Integer unit = this.getDirectOneWinUnits();
		if (unit != null && unit > 0) {
			prize = SscPlayType.DirectOne.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getDirectTwoWinUnits();
		if (unit != null && unit > 0) {
			prize = SscPlayType.DirectTwo.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getDirectThreeWinUnits();
		if (unit != null && unit > 0) {
			prize = SscPlayType.DirectThree.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getDirectFiveWinUnits();
		if (unit != null && unit > 0) {
			prize = SscPlayType.DirectFive.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		
		unit = this.getG3WinUnits();
		if (unit != null && unit > 0) {
			prize = SscPlayType.ThreeGroup3.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		
		unit = this.getG6WinUnits();
		if (unit != null && unit > 0) {
			prize = SscPlayType.ThreeGroup6.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		
		unit = this.getDirectThreeSumWinUnits();
		if (unit != null && unit > 0) {
			prize = SscPlayType.DirectThreeSum.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		
		unit = this.getGroupThreeSumH1WinUnits();
		if (unit != null && unit > 0) {
			prize = SscPlayType.GroupThreeSum.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getGroupThreeSumH2WinUnits();
		if (unit != null && unit > 0) {
			prize = SscPlayType.GroupThreeSum.getPrize2();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getGroupThreeSumH3WinUnits();
		if (unit != null && unit > 0) {
			prize = SscPlayType.GroupThreeSum.getPrize3();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		
		unit = this.getDirectTwoSumWinUnits();
		if (unit != null && unit > 0) {
			prize = SscPlayType.DirectTwoSum.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		
		unit = this.getGroupSumTwoH1WinUnits();
		if (unit != null && unit > 0) {
			prize = SscPlayType.GroupTwoSum.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getGroupSumTwoH2WinUnits();
		if (unit != null && unit > 0) {
			prize = SscPlayType.GroupTwoSum.getPrize2();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		
		
		unit = this.getAllFiveH1WinUnits();
		if (unit != null && unit > 0) {
			prize = SscPlayType.AllFive.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getAllFiveH2WinUnits();
		if (unit != null && unit > 0) {
			prize = SscPlayType.AllFive.getPrize2();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		unit = this.getAllFiveH3WinUnits();
		if (unit != null && unit > 0) {
			prize = SscPlayType.AllFive.getPrize3();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		
		
		unit = this.getGroupTwoH1WinUnits();
		if (unit != null && unit > 0) {
			prize = SscPlayType.GroupTwo.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		
		unit = this.getBigSmallDoubleSingleWinUnits();
		if (unit != null && unit > 0) {
			prize = SscPlayType.BigSmallDoubleSingle.getPrize();
			totalPrize += prize >= 10000.0d ? unit * prize * 0.8d : unit * prize;
		}
		return totalPrize;
	}

	public double getPrize() {
		double totalPrize = 0;
		int prize = 0;
		Integer unit = this.getDirectOneWinUnits();
		if (unit != null && unit > 0) {
			prize = SscPlayType.DirectOne.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getDirectTwoWinUnits();
		if (unit != null && unit > 0) {
			prize = SscPlayType.DirectTwo.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getDirectThreeWinUnits();
		if (unit != null && unit > 0) {
			prize = SscPlayType.DirectThree.getPrize();
			totalPrize += unit * prize;
		}
		unit = this.getDirectFiveWinUnits();
		if (unit != null && unit > 0) {
			prize = SscPlayType.DirectFive.getPrize();
			totalPrize += unit * prize;
		}
		
		
		unit = this.getG3WinUnits();
		if (unit != null && unit > 0) {
			prize = SscPlayType.ThreeGroup3.getPrize();
			totalPrize += unit * prize;
		}
		
		unit = this.getG6WinUnits();
		if (unit != null && unit > 0) {
			prize = SscPlayType.ThreeGroup6.getPrize();
			totalPrize += unit * prize;
		}
		
		
		unit = this.getDirectThreeSumWinUnits();
		if (unit != null && unit > 0) {
			prize = SscPlayType.DirectThreeSum.getPrize();
			totalPrize += unit * prize;
		}
		
		unit = this.getGroupThreeSumH1WinUnits();
		if (unit != null && unit > 0) {
			prize = SscPlayType.GroupThreeSum.getPrize();
			totalPrize +=  unit * prize;
		}
		unit = this.getGroupThreeSumH2WinUnits();
		if (unit != null && unit > 0) {
			prize = SscPlayType.GroupThreeSum.getPrize2();
			totalPrize += unit * prize;
		}
		unit = this.getGroupThreeSumH3WinUnits();
		if (unit != null && unit > 0) {
			prize = SscPlayType.GroupThreeSum.getPrize3();
			totalPrize += unit * prize;
		}
		unit = this.getDirectTwoSumWinUnits();
		if (unit != null && unit > 0) {
			prize = SscPlayType.DirectTwoSum.getPrize();
			totalPrize += unit * prize;
		}
		
		unit = this.getGroupSumTwoH1WinUnits();
		if (unit != null && unit > 0) {
			prize = SscPlayType.GroupTwoSum.getPrize();
			totalPrize +=  unit * prize;
		}
		
		unit = this.getGroupSumTwoH2WinUnits();
		if (unit != null && unit > 0) {
			prize = SscPlayType.GroupTwoSum.getPrize2();
			totalPrize += unit * prize;
		}
		
		unit = this.getAllFiveH1WinUnits();
		if (unit != null && unit > 0) {
			prize = SscPlayType.AllFive.getPrize();
			totalPrize += unit * prize;
		}
		
		unit = this.getAllFiveH2WinUnits();
		if (unit != null && unit > 0) {
			prize = SscPlayType.AllFive.getPrize2();
			totalPrize += unit * prize;
		}
		
		unit = this.getAllFiveH3WinUnits();
		if (unit != null && unit > 0) {
			prize = SscPlayType.AllFive.getPrize3();
			totalPrize += unit * prize;
		}		
		unit = this.getGroupTwoH1WinUnits();
		if (unit != null && unit > 0) {
			prize = SscPlayType.GroupTwo.getPrize();
			totalPrize +=  unit * prize;
		}
		unit = this.getBigSmallDoubleSingleWinUnits();
		if (unit != null && unit > 0) {
			prize = SscPlayType.BigSmallDoubleSingle.getPrize();
			totalPrize += unit * prize;
		}
		
		return totalPrize;
	}

	// ////////////////////////////////////////////////////////////////////////

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

	
	public Integer getGroupSumTwoH2WinUnits() {
		return groupSumTwoH2WinUnits;
	}

	public void setGroupSumTwoH2WinUnits(Integer groupSumTwoH2WinUnits) {
		this.groupSumTwoH2WinUnits = groupSumTwoH2WinUnits;
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


	public void addDirectTwoWinUnits(Integer winUnits) {
		if (this.directTwoWinUnits != null) {
			this.directTwoWinUnits += winUnits;
		} else {
			this.directTwoWinUnits = winUnits;
		}
	}



	public void addDirectThreeWinUnits(Integer winUnits) {
		if (this.directThreeWinUnits != null) {
			this.directThreeWinUnits += winUnits;
		} else {
			this.directThreeWinUnits = winUnits;
		}
	}
	
	public void addDirectFiveWinUnits(Integer winUnits) {
		if (this.directFiveWinUnits != null) {
			this.directFiveWinUnits += winUnits;
		} else {
			this.directFiveWinUnits = winUnits;
		}
	}
	
	public void addAllFiveH1WinUnits(Integer winUnits) {
		if (this.allFiveH1WinUnits != null) {
			this.allFiveH1WinUnits += winUnits;
		} else {
			this.allFiveH1WinUnits = winUnits;
		}
	}
	
	public void addAllFiveH2WinUnits(Integer winUnits) {
		if (this.allFiveH2WinUnits != null) {
			this.allFiveH2WinUnits += winUnits;
		} else {
			this.allFiveH2WinUnits = winUnits;
		}
	}
	public void addAllFiveH3WinUnits(Integer winUnits) {
		if (this.allFiveH3WinUnits != null) {
			this.allFiveH3WinUnits += winUnits;
		} else {
			this.allFiveH3WinUnits = winUnits;
		}
	}
	
	public void addGroupTwoH1WinUnits(Integer winUnits) {
		if (this.groupTwoH1WinUnits != null) {
			this.groupTwoH1WinUnits += winUnits;
		} else {
			this.groupTwoH1WinUnits = winUnits;
		}
	}
	
	public void addDirectTwoSumWinUnits(Integer winUnits) {
		if (this.directTwoSumWinUnits != null) {
			this.directTwoSumWinUnits += winUnits;
		} else {
			this.directTwoSumWinUnits = winUnits;
		}
	}
	
	public void addGroupSumTwoH1WinUnits(Integer winUnits) {
		if (this.groupSumTwoH1WinUnits != null) {
			this.groupSumTwoH1WinUnits += winUnits;
		} else {
			this.groupSumTwoH1WinUnits = winUnits;
		}
	}
	
	public void addGroupSumTwoH2WinUnits(Integer winUnits) {
		if (this.groupSumTwoH2WinUnits != null) {
			this.groupSumTwoH2WinUnits += winUnits;
		} else {
			this.groupSumTwoH2WinUnits = winUnits;
		}
	}
	
	public void addGroupThreeWinUnits(Integer winUnits) {
		if (this.groupThreeWinUnits != null) {
			this.groupThreeWinUnits += winUnits;
		} else {
			this.groupThreeWinUnits = winUnits;
		}
	}
	public void addG3WinUnits(Integer winUnits) {
		if (this.g3WinUnits != null) {
			this.g3WinUnits += winUnits;
		} else {
			this.g3WinUnits = winUnits;
		}
	}
	
	
	public void addG6WinUnits(Integer winUnits) {
		if (this.g6WinUnits != null) {
			this.g6WinUnits += winUnits;
		} else {
			this.g6WinUnits = winUnits;
		}
	}
	
	public void addDirectThreeSumWinUnits(Integer winUnits) {
		if (this.directThreeSumWinUnits != null) {
			this.directThreeSumWinUnits += winUnits;
		} else {
			this.directThreeSumWinUnits = winUnits;
		}
	}
	
	public void addGroupThreeSumH1WinUnits(Integer winUnits) {
		if (this.groupThreeSumH1WinUnits != null) {
			this.groupThreeSumH1WinUnits += winUnits;
		} else {
			this.groupThreeSumH1WinUnits = winUnits;
		}
	}
	public void addGroupThreeSumH2WinUnits(Integer winUnits) {
		if (this.groupThreeSumH2WinUnits != null) {
			this.groupThreeSumH2WinUnits += winUnits;
		} else {
			this.groupThreeSumH2WinUnits = winUnits;
		}
	}
	public void addGroupThreeSumH3WinUnits(Integer winUnits) {
		if (this.groupThreeSumH3WinUnits != null) {
			this.groupThreeSumH3WinUnits += winUnits;
		} else {
			this.groupThreeSumH3WinUnits = winUnits;
		}
	}
	
	public void addBigSmallDoubleSingleWinUnits(Integer winUnits) {
		if (this.bigSmallDoubleSingleWinUnits != null) {
			this.bigSmallDoubleSingleWinUnits += winUnits;
		} else {
			this.bigSmallDoubleSingleWinUnits = winUnits;
		}
	}
	
	

	
	
	
	public void addSscWinUnits(SscWinUnits winUnit) {
		this.addDirectOneWinUnits(winUnit.getDirectOneWinUnits());
		this.addDirectTwoWinUnits(winUnit.getDirectTwoWinUnits());
		this.addDirectThreeWinUnits(winUnit.getDirectThreeWinUnits());
		this.addDirectFiveWinUnits(winUnit.getDirectFiveWinUnits());
		
		this.addAllFiveH1WinUnits(winUnit.getAllFiveH1WinUnits());
		this.addAllFiveH2WinUnits(winUnit.getAllFiveH2WinUnits());
		this.addAllFiveH3WinUnits(winUnit.getAllFiveH3WinUnits());
		
		this.addGroupTwoH1WinUnits(winUnit.getGroupTwoH1WinUnits());

		this.addDirectTwoSumWinUnits(winUnit.getDirectTwoSumWinUnits());
		
		this.addGroupSumTwoH1WinUnits(winUnit.getGroupSumTwoH1WinUnits());
		this.addGroupSumTwoH2WinUnits(winUnit.getGroupSumTwoH2WinUnits());
		this.addGroupThreeWinUnits(winUnit.getGroupThreeWinUnits());
		
		this.addG3WinUnits(winUnit.getG3WinUnits());
		this.addG6WinUnits(winUnit.getG6WinUnits());
		
		this.addDirectThreeSumWinUnits(winUnit.getDirectThreeSumWinUnits());
		
		this.addGroupThreeSumH1WinUnits(winUnit.getGroupThreeSumH1WinUnits());
		this.addGroupThreeSumH2WinUnits(winUnit.getGroupThreeSumH2WinUnits());
		this.addGroupThreeSumH3WinUnits(winUnit.getGroupThreeSumH3WinUnits());
		
		this.addBigSmallDoubleSingleWinUnits(winUnit.getBigSmallDoubleSingleWinUnits());
	}


	public Integer getBigSmallDoubleSingleWinUnits() {
		return bigSmallDoubleSingleWinUnits;
	}

	public void setBigSmallDoubleSingleWinUnits(Integer bigSmallDoubleSingleWinUnits) {
		this.bigSmallDoubleSingleWinUnits = bigSmallDoubleSingleWinUnits;
	}
	
	public Integer getDirectFiveWinUnits() {
		return directFiveWinUnits;
	}

	public void setDirectFiveWinUnits(Integer directFiveWinUnits) {
		this.directFiveWinUnits = directFiveWinUnits;
	}
	


	public Integer getAllFiveH3WinUnits() {
		return allFiveH3WinUnits;
	}

	public void setAllFiveH3WinUnits(Integer allFiveH3WinUnits) {
		this.allFiveH3WinUnits = allFiveH3WinUnits;
	}

	public Integer getAllFiveH2WinUnits() {
		return allFiveH2WinUnits;
	}

	public void setAllFiveH2WinUnits(Integer allFiveH2WinUnits) {
		this.allFiveH2WinUnits = allFiveH2WinUnits;
	}

	public Integer getG3WinUnits() {
		return g3WinUnits;
	}

	public void setG3WinUnits(Integer g3WinUnits) {
		this.g3WinUnits = g3WinUnits;
	}

	public Integer getG6WinUnits() {
		return g6WinUnits;
	}

	public void setG6WinUnits(Integer g6WinUnits) {
		this.g6WinUnits = g6WinUnits;
	}

	public Integer getDirectTwoWinUnits() {
		return directTwoWinUnits;
	}

	public void setDirectTwoWinUnits(Integer directTwoWinUnits) {
		this.directTwoWinUnits = directTwoWinUnits;
	}

	public Integer getDirectThreeWinUnits() {
		return directThreeWinUnits;
	}

	public void setDirectThreeWinUnits(Integer directThreeWinUnits) {
		this.directThreeWinUnits = directThreeWinUnits;
	}

	public Integer getAllFiveH1WinUnits() {
		return allFiveH1WinUnits;
	}

	public void setAllFiveH1WinUnits(Integer allFiveH1WinUnits) {
		this.allFiveH1WinUnits = allFiveH1WinUnits;
	}

	public Integer getGroupSumTwoH1WinUnits() {
		return groupSumTwoH1WinUnits;
	}

	public void setGroupSumTwoH1WinUnits(Integer groupSumTwoH1WinUnits) {
		this.groupSumTwoH1WinUnits = groupSumTwoH1WinUnits;
	}


	public Integer getDirectTwoSumWinUnits() {
		return directTwoSumWinUnits;
	}

	public void setDirectTwoSumWinUnits(Integer directTwoSumWinUnits) {
		this.directTwoSumWinUnits = directTwoSumWinUnits;
	}

	public Integer getGroupThreeWinUnits() {
		return groupThreeWinUnits;
	}

	public void setGroupThreeWinUnits(Integer groupThreeWinUnits) {
		this.groupThreeWinUnits = groupThreeWinUnits;
	}

	public Integer getDirectThreeSumWinUnits() {
		return directThreeSumWinUnits;
	}

	public void setDirectThreeSumWinUnits(Integer directThreeSumWinUnits) {
		this.directThreeSumWinUnits = directThreeSumWinUnits;
	}

	public Integer getGroupThreeSumH3WinUnits() {
		return groupThreeSumH3WinUnits;
	}

	public void setGroupThreeSumH3WinUnits(Integer groupThreeSumH3WinUnits) {
		this.groupThreeSumH3WinUnits = groupThreeSumH3WinUnits;
	}

	public Integer getGroupThreeSumH2WinUnits() {
		return groupThreeSumH2WinUnits;
	}

	public void setGroupThreeSumH2WinUnits(Integer groupThreeSumH2WinUnits) {
		this.groupThreeSumH2WinUnits = groupThreeSumH2WinUnits;
	}

	public Integer getGroupThreeSumH1WinUnits() {
		return groupThreeSumH1WinUnits;
	}

	public void setGroupThreeSumH1WinUnits(Integer groupThreeSumH1WinUnits) {
		this.groupThreeSumH1WinUnits = groupThreeSumH1WinUnits;
	}

	public Integer getGroupTwoH1WinUnits() {
		return groupTwoH1WinUnits;
	}

	public void setGroupTwoH1WinUnits(Integer groupTwoH1WinUnits) {
		this.groupTwoH1WinUnits = groupTwoH1WinUnits;
	}


	

}
