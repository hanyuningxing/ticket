package com.cai310.lottery.support.dczc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.cai310.lottery.support.UnitsCountUtils;

public class PrizeForecast implements Serializable {
	private static final long serialVersionUID = -1511486373061304346L;

	private final List<DczcExtraMatchItem> matchItemList;
	private final List<DczcExtraMatchItem> danMatchItemList;
	private final List<DczcExtraMatchItem> undanMatchItemList;

	/** 不包含开奖并且没有命中的设胆场次 */
	private final List<DczcExtraMatchItem> danMatchItemListOfCleanNoHit;
	/** 不包含已开奖并且没有命中的非设胆场次 */
	private final List<DczcExtraMatchItem> undanMatchItemListOfCleanNoHit;

	/** 胆码最小命中数 */
	private final Integer danMinHit;

	/** 胆码最大命中数 */
	private final Integer danMaxHit;

	private final List<PassType> passTypes;

	private final List<Integer> passMatchList;

	private final List<PrizeForecastResult> resultList;

	private final Integer multiple;

	private final int units;

	public PrizeForecast(Integer multiple, List<DczcExtraMatchItem> matchItemList, List<PassType> passTypes,
			Integer danMinHit, Integer danMaxHit) {
		this.multiple = multiple;
		this.matchItemList = matchItemList;
		this.danMatchItemList = new ArrayList<DczcExtraMatchItem>();
		this.undanMatchItemList = new ArrayList<DczcExtraMatchItem>();
		this.danMatchItemListOfCleanNoHit = new ArrayList<DczcExtraMatchItem>();
		this.undanMatchItemListOfCleanNoHit = new ArrayList<DczcExtraMatchItem>();
		for (DczcExtraMatchItem item : matchItemList) {
			if (item.isDan()) {
				this.danMatchItemList.add(item);
				if (!item.isEnd() || item.isWon())
					this.danMatchItemListOfCleanNoHit.add(item);
			} else {
				this.undanMatchItemList.add(item);
				if (!item.isEnd() || item.isWon())
					this.undanMatchItemListOfCleanNoHit.add(item);
			}
		}

		if (danMinHit == null || danMinHit > this.danMatchItemList.size())
			this.danMinHit = this.danMatchItemList.size();
		else
			this.danMinHit = danMinHit;

		if (danMaxHit == null || danMaxHit > this.danMatchItemList.size() || danMaxHit < danMinHit)
			this.danMaxHit = this.danMatchItemList.size();
		else
			this.danMaxHit = danMaxHit;

		this.passTypes = passTypes;
		List<Integer> passMatchList = new ArrayList<Integer>();
		for (PassType passType : this.passTypes) {
			for (Integer passMatch : passType.getPassMatchs()) {
				if (!passMatchList.contains(passMatch))
					passMatchList.add(passMatch);
			}
		}
		Collections.sort(passMatchList);
		this.passMatchList = passMatchList;

		int units = 0;
		for (PassType passType : this.passTypes) {
			for (final int needs : passType.getPassMatchs()) {
				units += UnitsCountUtils.countUnits(needs, this.danMatchItemList, this.danMinHit, this.danMaxHit,
						this.undanMatchItemList);
			}
		}
		this.units = units;

		this.resultList = new ArrayList<PrizeForecastResult>();
		if (this.danMinHit <= this.danMatchItemListOfCleanNoHit.size()) {
			int size = this.danMatchItemListOfCleanNoHit.size() + this.undanMatchItemListOfCleanNoHit.size();
			int start = this.passMatchList.get(0);
			for (int matchSize = start; matchSize <= size; matchSize++) {
				List<DczcExtraMatchItem> minList = getList(matchSize, true);
				if (minList == null)
					continue;
				List<DczcExtraMatchItem> maxList = getList(matchSize, false);

				List<Integer> passMatchs = getPassMatchs(matchSize);
				if (passMatchs == null || passMatchs.isEmpty())
					continue;

				PrizeForecastItem minForecastItem = new PrizeForecastItem(this.multiple, minList, this.danMinHit,
						this.danMaxHit, passMatchs, true);
				PrizeForecastItem maxForecastItem = new PrizeForecastItem(this.multiple, maxList, this.danMinHit,
						this.danMaxHit, passMatchs, false);

				PrizeForecastResult result = new PrizeForecastResult(matchSize, minForecastItem, maxForecastItem);
				this.resultList.add(result);
			}
		}
	}

	private List<Integer> getPassMatchs(int matchSize) {
		List<Integer> list = new ArrayList<Integer>();
		for (Integer passMatch : this.passMatchList) {
			if (passMatch <= matchSize)
				list.add(passMatch);
		}
		return list;
	}

	private List<DczcExtraMatchItem> getList(final int size, final boolean min) {
		if (this.danMinHit > this.danMatchItemListOfCleanNoHit.size()
				|| size > (this.danMatchItemListOfCleanNoHit.size() + this.undanMatchItemListOfCleanNoHit.size()))
			return null;

		Comparator<DczcExtraMatchItem> sort = new Comparator<DczcExtraMatchItem>() {
			public int compare(DczcExtraMatchItem o1, DczcExtraMatchItem o2) {
				if (o1.isCancel() || (o1.isEnd() && o1.getResultItem() != null && o1.getResultSp() != null))
					return -1;
				if (o2.isCancel() || (o2.isEnd() && o2.getResultItem() != null && o2.getResultSp() != null))
					return -1;

				DczcExtraItem item1 = o1.getMinSpOrMaxSpItem(min);
				DczcExtraItem item2 = o2.getMinSpOrMaxSpItem(min);
				Double sp1 = (item1 != null && item1.getSp() != null) ? item1.getSp() : Double.valueOf(0.0);
				Double sp2 = (item2 != null && item2.getSp() != null) ? item2.getSp() : Double.valueOf(0.0);
				int r = sp1.compareTo(sp2);
				if (!min)
					r = -r;
				return r;
			}
		};

		List<DczcExtraMatchItem> danItemListOfCopy = new ArrayList<DczcExtraMatchItem>(
				this.danMatchItemListOfCleanNoHit);
		Collections.sort(danItemListOfCopy, sort);
		List<DczcExtraMatchItem> list = new ArrayList<DczcExtraMatchItem>();
		list.addAll(danItemListOfCopy.subList(0, this.danMinHit));

		if (size > this.danMinHit) {
			List<DczcExtraMatchItem> newItemList = new ArrayList<DczcExtraMatchItem>();
			newItemList.addAll(danItemListOfCopy.subList(this.danMinHit,
					(danItemListOfCopy.size() > this.danMaxHit) ? this.danMaxHit : danItemListOfCopy.size()));
			newItemList.addAll(this.undanMatchItemListOfCleanNoHit);
			Collections.sort(newItemList, sort);
			list.addAll(newItemList.subList(0, size - this.danMinHit));
		}

		return list;
	}

	public List<DczcExtraMatchItem> getMatchItemList() {
		return matchItemList;
	}

	public Integer getDanMinHit() {
		return danMinHit;
	}

	public Integer getDanMaxHit() {
		return danMaxHit;
	}

	public List<PassType> getPassTypes() {
		return passTypes;
	}

	public List<PrizeForecastResult> getResultList() {
		return resultList;
	}

	public List<Integer> getPassMatchList() {
		return passMatchList;
	}

	public Integer getMultiple() {
		return multiple;
	}

	public int getUnits() {
		return units;
	}

	public int getCost() {
		return this.multiple * this.units * 2;
	}
}
