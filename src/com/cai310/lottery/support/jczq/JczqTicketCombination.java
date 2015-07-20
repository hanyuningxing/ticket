package com.cai310.lottery.support.jczq;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.cai310.lottery.support.Item;
import com.cai310.lottery.utils.CommonUtil;
import com.cai310.utils.CombCallBack;
import com.cai310.utils.MathUtils;

public class JczqTicketCombination implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 升序 */
	public static final Comparator<Double> ASC = new Comparator<Double>() {

		public int compare(Double o1, Double o2) {
			if (o1 == null)
				return -1;
			else if (o2 == null)
				return 1;
			else if (o1 > o2)
				return 1;
			else if (o1 < o2)
				return -1;
			return 0;
		}
	};

	/** 倒序 */
	public static final Comparator<Double> DESC = new Comparator<Double>() {

		public int compare(Double o1, Double o2) {
			if (o1 == null)
				return 1;
			else if (o2 == null)
				return -1;
			else if (o1 > o2)
				return -1;
			else if (o1 < o2)
				return 1;
			return 0;
		}
	};

	private PlayType playType;

	/** 投注内容 */
	private List<JczqMatchItem> matchItemList;

	/** 过关方式 */
	private PassType passType;

	/** 倍数 */
	private Integer multiple;

	/** 是否中奖，NULL表示未开奖 */
	private Boolean won;

	/** 是否已打印 */
	private boolean printed;

	/** 税后总奖金 */
	protected Double totalPrizeAfterTax;

	/** 预测最小奖金值 */
	private Double forecastMinPrize;

	/** 预测最大奖金值 */
	private Double forecastMaxPrize;

	private Map<String, Map<String, Double>> awardMap;

	/**
	 * @param matchItemList
	 *            选择的场次内容
	 * @param passType
	 *            过关方式
	 * @param multiple
	 *            倍数
	 * @param matchPrintAwardMap
	 *            出票赔率
	 * @param totalPrizeAfterTax
	 *            税后总奖金，NULL表示未开奖，大于0表示中奖，等于0表示不中奖
	 */
	public JczqTicketCombination(PlayType playType, List<JczqMatchItem> matchItemList, PassType passType,
			Integer multiple, Map<String, Map<String, Double>> awardMap, Double totalPrizeAfterTax) {
		super();
		this.playType = playType;
		this.matchItemList = matchItemList;
		this.passType = passType;
		this.multiple = multiple;
		this.awardMap = awardMap;
		this.totalPrizeAfterTax = totalPrizeAfterTax;
		if (this.totalPrizeAfterTax != null) {
			this.won = this.totalPrizeAfterTax > 0;
		}

		if (awardMap != null && !awardMap.isEmpty()){
			this.printed = true;
			forecast();
		}
	}

	private void forecast() {
		final List<Double> minList = new ArrayList<Double>();
		final List<Double> maxList = new ArrayList<Double>();
		for (JczqMatchItem matchItem : this.matchItemList) {
			Double minAward = null;
			Double maxAward = null;
			Object object = null;
			Map<String, Double> map = awardMap.get(matchItem.getMatchKey());
			for (Item item : playType.getAllItems()) {
			    object = map.get(item.getValue());
			    if (object != null) {
					Double award = Double.valueOf(""+map.get(item.getValue()));
					if (award != null) {
						if (minAward == null || award < minAward)
							minAward = award;
						if (maxAward == null || award > maxAward)
							maxAward = award;
					}
			    }
			}
			minList.add(minAward);
			maxList.add(maxAward);
		}
		Collections.sort(minList, ASC);
		Collections.sort(maxList, DESC);

		if (this.passType == PassType.P1) {
			forecastMinPrize = CommonUtil.roundPrize(minList.get(0)) * this.multiple;
			forecastMaxPrize = 0.0;
			for (Double award : maxList) {
				forecastMaxPrize += CommonUtil.roundPrize(award);
			}
			forecastMaxPrize *= this.multiple;
		} else {
			int minPass = this.passType.getPassMatchs()[0];
			forecastMinPrize = 1d;
			for (int i = 0; i < minPass; i++) {
				forecastMinPrize *= minList.get(i);
			}
			forecastMinPrize *= 2d;
			forecastMinPrize = CommonUtil.roundPrize(forecastMinPrize);
			forecastMinPrize *= this.multiple;

			forecastMaxPrize = 0d;
			CombCallBack call = new CombCallBack() {

				public boolean callback(boolean[] comb, int m) {
					double award = 1d;
					int pos = 0;
					for (int i = 0; i < comb.length; i++) {
						if (comb[i] == true) {
							award *= maxList.get(i);
							pos++;
							if (pos == m)
								break;
						}
					}
					award *= 2d;
					award = CommonUtil.roundPrize(award);
					forecastMaxPrize += award;
					return false;
				}
			};
			for (int pass : this.passType.getPassMatchs()) {
				MathUtils.efficientComb(maxList.size(), pass, call);
			}
			forecastMaxPrize *= this.multiple;
		}
	}

	/**
	 * @return the {@link #matchItemList}
	 */
	public List<JczqMatchItem> getMatchItemList() {
		return matchItemList;
	}

	/**
	 * @return the {@link #passType}
	 */
	public PassType getPassType() {
		return passType;
	}

	/**
	 * @return the {@link #multiple}
	 */
	public Integer getMultiple() {
		return multiple;
	}

	/**
	 * @return the {@link #won}
	 */
	public Boolean getWon() {
		return won;
	}

	/**
	 * @return the {@link #printed}
	 */
	public boolean isPrinted() {
		return printed;
	}

	/**
	 * @return the {@link #totalPrizeAfterTax}
	 */
	public Double getTotalPrizeAfterTax() {
		return totalPrizeAfterTax;
	}

	/**
	 * @return the {@link #forecastMinPrize}
	 */
	public Double getForecastMinPrize() {
		return forecastMinPrize;
	}

	/**
	 * @return the {@link #forecastMaxPrize}
	 */
	public Double getForecastMaxPrize() {
		return forecastMaxPrize;
	}

	public PlayType getPlayType() {
		return playType;
	}

	public Map<String, Map<String, Double>> getAwardMap() {
		return awardMap;
	}

	public void setAwardMap(Map<String, Map<String, Double>> awardMap) {
		this.awardMap = awardMap;
	}

	public void setPlayType(PlayType playType) {
		this.playType = playType;
	}

	public void setMatchItemList(List<JczqMatchItem> matchItemList) {
		this.matchItemList = matchItemList;
	}

	public void setPassType(PassType passType) {
		this.passType = passType;
	}

	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}

	public void setWon(Boolean won) {
		this.won = won;
	}

	public void setPrinted(boolean printed) {
		this.printed = printed;
	}

	public void setTotalPrizeAfterTax(Double totalPrizeAfterTax) {
		this.totalPrizeAfterTax = totalPrizeAfterTax;
	}

	public void setForecastMinPrize(Double forecastMinPrize) {
		this.forecastMinPrize = forecastMinPrize;
	}

	public void setForecastMaxPrize(Double forecastMaxPrize) {
		this.forecastMaxPrize = forecastMaxPrize;
	}

}
