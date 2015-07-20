package com.cai310.lottery.support.jczq;

import java.util.ArrayList;
import java.util.List;

import com.cai310.lottery.support.JcWonMatchItem;
import com.cai310.utils.CombCallBack;
import com.cai310.utils.MathUtils;

public class JczqSimplePrizeWork extends JczqPrizeWork {
	private static final long serialVersionUID = 1L;

	/** 命中的场次 */
	private final List<JcWonMatchItem> correctList;

	/** 过关方式 */
	private final PassType passType;

	public JczqSimplePrizeWork(PassMode passMode, int multiple, PassType passType, List<JcWonMatchItem> correctList) {
		this(passMode, multiple, passType, correctList, true);
	}

	public JczqSimplePrizeWork(PassMode passMode, int multiple, PassType passType, List<JcWonMatchItem> correctList,
			boolean build) {
		super(passMode, multiple);
		this.passType = passType;
		this.correctList = correctList;

		init(build);
	}

	private void init(boolean build) {
		if (correctList.size() >= passType.getPassMatchs()[0]) {
			CombCallBack call = new CombCallBack() {

				public boolean callback(boolean[] comb, int m) {
					List<JcWonMatchItem> combList = new ArrayList<JcWonMatchItem>();
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
					won = true;
				}
			}
			if (build)
				build();
		}
	}
}
