package com.cai310.lottery.support.dczc;

import java.io.Serializable;

import com.cai310.utils.MathUtils;

public class PrizeForecastResult implements Serializable {
	private static final long serialVersionUID = 357907918085306113L;

	private final Integer matchSize;
	private final PrizeForecastItem minForecastItem;
	private final PrizeForecastItem maxForecastItem;

	protected PrizeForecastResult(Integer matchSize, PrizeForecastItem minForecastItem,
			PrizeForecastItem maxForecastItem) {
		super();
		this.matchSize = matchSize;
		this.minForecastItem = minForecastItem;
		this.maxForecastItem = maxForecastItem;
	}

	public Integer getMatchSize() {
		return matchSize;
	}

	public PrizeForecastItem getMinForecastItem() {
		return minForecastItem;
	}

	public PrizeForecastItem getMaxForecastItem() {
		return maxForecastItem;
	}

	public int countUnits(int r) {
		return MathUtils.comp(r, this.matchSize);
	}

}
