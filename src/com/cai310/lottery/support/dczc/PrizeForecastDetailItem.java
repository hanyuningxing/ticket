package com.cai310.lottery.support.dczc;

import java.io.Serializable;
import java.util.List;

public class PrizeForecastDetailItem implements Serializable {
	private static final long serialVersionUID = 188438150946603136L;

	private final List<List<DczcExtraMatchItem>> detailList;
	private final String detail;
	private final Double totalPrize;
	private final int passMatch;
	private final int units;
	private final Integer multiple;

	public PrizeForecastDetailItem(Integer multiple, Double totalPrize, List<List<DczcExtraMatchItem>> detailList,
			String detail) {
		this.multiple = multiple;
		this.totalPrize = totalPrize;
		this.detail = detail;
		this.detailList = detailList;
		this.units = detailList.size();
		this.passMatch = detailList.get(0).size();
	}

	public List<List<DczcExtraMatchItem>> getDetailList() {
		return detailList;
	}

	public Double getTotalPrize() {
		return totalPrize;
	}

	public int getPassMatch() {
		return passMatch;
	}

	public int getUnits() {
		return units;
	}

	public Integer getMultiple() {
		return multiple;
	}

	public String getPassText() {
		if (passMatch == 1)
			return "单关";
		else
			return passMatch + "串1";
	}

	public String getDetail() {
		return detail;
	}

}
