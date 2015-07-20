package com.cai310.lottery.support.dczc;

import java.util.ArrayList;
import java.util.List;

import com.cai310.utils.CombCallBack;
import com.cai310.utils.MathUtils;

/**
 * 普通过关更新中奖
 * 
 */
public class DczcNormalPasscountWork extends DczcPasscountWork {

	private static final long serialVersionUID = -4359107574379644427L;

	/** 命中的场次 */
	private final List<DczcMatchItem> correctList;

	public List<DczcMatchItem> getCorrectList() {
		return correctList;
	}

	/** 过关方式 */
	private final PassType passType;

	public DczcNormalPasscountWork(PassType passType,
			List<DczcMatchItem> correctList,List<DczcMatchItem> betList,int multiple) {
		this.passType = passType;
		this.correctList = correctList;
		this.betCount=betList.size();
		this.multiple=multiple;
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
	        /////////运行完之后。中奖列表就有数据了。可以更新中奖注数
		    /////////如果没有中奖。这里默认值是0
			this.wonCount=this.combinationMap.size()*this.multiple;
		}
	}
}
