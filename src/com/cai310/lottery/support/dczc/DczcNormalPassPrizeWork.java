package com.cai310.lottery.support.dczc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cai310.utils.CombCallBack;
import com.cai310.utils.MathUtils;

/**
 * 普通过关更新中奖
 * 
 */
public class DczcNormalPassPrizeWork extends DczcPrizeWork {

	private static final long serialVersionUID = -4359107574379644427L;

	/** 命中的场次 */
	private final List<DczcMatchItem> correctList;

	/** 过关方式 */
	private final PassType passType;

	private final CombinationBean combBean;
	private final String key;

	public DczcNormalPassPrizeWork(Map<Integer, DczcResult> resultMap, PassType passType,
			List<DczcMatchItem> correctList) {
		super(resultMap);
		this.passType = passType;
		this.correctList = correctList;

		this.combBean = new CombinationBean();
		combBean.setPassTypeOrdinal(this.passType.ordinal());
		combBean.setItems(this.correctList);
		combBean.setPrize(0.0d);
		combBean.setPrizeAfterTax(0.0d);
		this.key = this.combBean.generateKey();
		
		init();
	}

	private void init() {
		if (correctList.size() >= passType.getPassMatchs()[0]) {
			CombCallBack call = new CombCallBack() {

				public boolean callback(boolean[] comb, int m) {
					List<DczcMatchItem> combList = new ArrayList<DczcMatchItem>();
					for (int i = 0; i < comb.length; i++) {
						if (comb[i]) {
							combList.add(correctList.get(i));
							if (combList.size() == m)
								break;
						}
					}

					handle(passType, combList);
					return false;
				}

			};
			for (final int m : passType.getPassMatchs()) {
				if (m <= correctList.size()) {
					MathUtils.efficientComb(correctList.size(), m, call);
				}
			}
			won = true;
		}
	}

	@Override
	protected void handleCombinationMap(PassType passType, List<DczcMatchItem> combList, DczcPrizeItem prizeItem) {
		combBean.setPrize(combBean.getPrize() + prizeItem.getPrize());
		combBean.setPrizeAfterTax(combBean.getPrizeAfterTax() + prizeItem.getPrizeAfterTax());
		combinationMap.put(this.key, combBean);
	}
}
