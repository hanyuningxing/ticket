package com.cai310.lottery.support.qyh;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.cai310.utils.MathUtils;
import com.cai310.utils.NumUtils;

/**
 * 投注方式.<br/>
 * 注：添加类型只能在后面添加，不能插入中间
 * 
 */
public enum QyhPlayType {

	/** 任选一数投 */
	/**
	 * 从23个号码中任选1个号码。奖金5元
	 */
	RandomOne("选一", Integer.valueOf("5"), 1) {
		public QyhWinUnits calcWinUnit(Integer[] results, QyhCompoundContent qyhCompoundContent, int multiple) {
			QyhWinUnits qyhWinUnits = new QyhWinUnits();
			int hits = 0;
			List<Integer> bets = NumUtils.toIntegerList(qyhCompoundContent.getBetList());
			for (Integer result : results) {
				if (bets.contains(result)) {
					hits++;
				}
			}
			qyhWinUnits.addRandomOneWinUnits(hits * multiple);
			return qyhWinUnits;
		}

		@Override
		public Integer countDanUnit(List<String> danbets, List<String> tuobets) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		@Override
		public Integer countUnit(QyhCompoundContent compoundContent) {
			return compoundContent.getBetList().size();
		}
	},
	/** 选二任选 */
	/**
	 * 从23个号码中任选2个号码。1/5.5的中奖机会，奖金30元。
	 */
	RandomTwo("选二", Integer.valueOf("30"), 2) {
		public QyhWinUnits calcWinUnit(Integer[] results, QyhCompoundContent qyhCompoundContent, int multiple) {
			QyhWinUnits qyhWinUnits = new QyhWinUnits();
			int danHits = 0;
			int hits = 0;
			int danSelected = 0;
			int selected = 0;
			if (qyhCompoundContent.getBetDanList() != null && !qyhCompoundContent.getBetDanList().isEmpty()) {
				List<Integer> bets = NumUtils.toIntegerList(qyhCompoundContent.getBetDanList());
				danSelected = bets.size();
				for (Integer result : results) {
					if (bets.contains(result)) {
						danHits++;
					}
				}
			}
			List<Integer> bets = NumUtils.toIntegerList(qyhCompoundContent.getBetList());
			for (Integer result : results) {
				if (bets.contains(result)) {
					hits++;
				}
			}
			selected = bets.size();
			qyhWinUnits.addRandomTwoWinUnits(checkWin(danHits, hits, danSelected, selected, 2, 2) * multiple);
			return qyhWinUnits;
		}

		@Override
		public Integer countDanUnit(List<String> danbets, List<String> tuobets) {
			return MathUtils.comp(2 - danbets.size(), tuobets.size());
		}

		@Override
		public Integer countUnit(QyhCompoundContent compoundContent) {
			return MathUtils.comp(2, compoundContent.getBetList().size());
		}
	},
	/** 选三任选 */
	/**
	 * 从23个号码中任选3个号码。1/16.5的中奖机会，奖金100元。
	 */
	RandomThree("任选三 ", Integer.valueOf("100"), 3, Integer.valueOf("5")) {
		public QyhWinUnits calcWinUnit(Integer[] results, QyhCompoundContent qyhCompoundContent, int multiple) {
			QyhWinUnits qyhWinUnits = new QyhWinUnits();
			int danHits = 0;
			int hits = 0;
			int danSelected = 0;
			int selected = 0;
			if (qyhCompoundContent.getBetDanList() != null && !qyhCompoundContent.getBetDanList().isEmpty()) {
				List<Integer> bets = NumUtils.toIntegerList(qyhCompoundContent.getBetDanList());
				danSelected = bets.size();
				for (Integer result : results) {
					if (bets.contains(result)) {
						danHits++;
					}
				}
			}
			List<Integer> bets = NumUtils.toIntegerList(qyhCompoundContent.getBetList());
			for (Integer result : results) {
				if (bets.contains(result)) {
					hits++;
				}
				selected = bets.size();
			}
			qyhWinUnits.addRandomThreeWinUnits(checkWin(danHits, hits, danSelected, selected, 3, 3) * multiple);
			qyhWinUnits.addRandomThreeH2WinUnits(checkWin(danHits, hits, danSelected, selected, 2, 3) * multiple);
			return qyhWinUnits;
		}

		@Override
		public Integer countDanUnit(List<String> danbets, List<String> tuobets) {
			return MathUtils.comp(3 - danbets.size(), tuobets.size());
		}

		@Override
		public Integer countUnit(QyhCompoundContent compoundContent) {
			return MathUtils.comp(3, compoundContent.getBetList().size());
		}
	},
	/** 选四任选 */
	/**
	 * 从23个号码中任选4个号码，1/66的中奖机会，奖金1000元
	 */
	RandomFour("任选四 ", Integer.valueOf("1000"), 4, Integer.valueOf("30")) {
		public QyhWinUnits calcWinUnit(Integer[] results, QyhCompoundContent qyhCompoundContent, int multiple) {
			QyhWinUnits qyhWinUnits = new QyhWinUnits();
			int danHits = 0;
			int hits = 0;
			int danSelected = 0;
			int selected = 0;
			if (qyhCompoundContent.getBetDanList() != null && !qyhCompoundContent.getBetDanList().isEmpty()) {
				List<Integer> bets = NumUtils.toIntegerList(qyhCompoundContent.getBetDanList());
				danSelected = bets.size();
				for (Integer result : results) {
					if (bets.contains(result)) {
						danHits++;
					}
				}
			}
			List<Integer> bets = NumUtils.toIntegerList(qyhCompoundContent.getBetList());
			for (Integer result : results) {
				if (bets.contains(result)) {
					hits++;
				}
				selected = bets.size();
			}
			qyhWinUnits.addRandomFourWinUnits(checkWin(danHits, hits, danSelected, selected, 4, 4) * multiple);
			qyhWinUnits.addRandomFourH3WinUnits(checkWin(danHits, hits, danSelected, selected, 3, 4) * multiple);
			return qyhWinUnits;
		}

		@Override
		public Integer countDanUnit(List<String> danbets, List<String> tuobets) {
			return MathUtils.comp(4 - danbets.size(), tuobets.size());
		}

		@Override
		public Integer countUnit(QyhCompoundContent compoundContent) {
			return MathUtils.comp(4, compoundContent.getBetList().size());
		}
	},
	/** 选五任选 */
	/**
	 * 从23个号码中任选5个号码，1/462的中奖机会，奖金10000元
	 */
	RandomFive("任选五", Integer.valueOf("10000"), 5, Integer.valueOf("150"), Integer.valueOf("10")) {
		public QyhWinUnits calcWinUnit(Integer[] results, QyhCompoundContent qyhCompoundContent, int multiple) {
			QyhWinUnits qyhWinUnits = new QyhWinUnits();
			int danHits = 0;
			int hits = 0;
			int danSelected = 0;
			int selected = 0;
			if (qyhCompoundContent.getBetDanList() != null && !qyhCompoundContent.getBetDanList().isEmpty()) {
				List<Integer> bets = NumUtils.toIntegerList(qyhCompoundContent.getBetDanList());
				danSelected = bets.size();
				for (Integer result : results) {
					if (bets.contains(result)) {
						danHits++;
					}
				}
			}
			List<Integer> bets = NumUtils.toIntegerList(qyhCompoundContent.getBetList());
			for (Integer result : results) {
				if (bets.contains(result)) {
					hits++;
				}
				selected = bets.size();
			}
			qyhWinUnits.addRandomFiveWinUnits(checkWin(danHits, hits, danSelected, selected, 5, 5) * multiple);
			qyhWinUnits.addRandomFiveH4WinUnits(checkWin(danHits, hits, danSelected, selected, 4, 5) * multiple);
			qyhWinUnits.addRandomFiveH3WinUnits(checkWin(danHits, hits, danSelected, selected, 3, 5) * multiple);
			return qyhWinUnits;
		}

		@Override
		public Integer countDanUnit(List<String> danbets, List<String> tuobets) {
			return MathUtils.comp(5 - danbets.size(), tuobets.size());
		}

		@Override
		public Integer countUnit(QyhCompoundContent compoundContent) {
			return MathUtils.comp(5, compoundContent.getBetList().size());
		}
	},
	/** 任选六中五 */
	/**
	 * 从23个号码中任选6个号码，1/77的中奖机会，奖金6500元。
	 */
	RandomSix("任选六", Integer.valueOf("6500"), 6) {
		public QyhWinUnits calcWinUnit(Integer[] results, QyhCompoundContent qyhCompoundContent, int multiple) {
			QyhWinUnits qyhWinUnits = new QyhWinUnits();
			int danHits = 0;
			int hits = 0;
			int danSelected = 0;
			int selected = 0;
			if (qyhCompoundContent.getBetDanList() != null && !qyhCompoundContent.getBetDanList().isEmpty()) {
				List<Integer> bets = NumUtils.toIntegerList(qyhCompoundContent.getBetDanList());
				danSelected = bets.size();
				for (Integer result : results) {
					if (bets.contains(result)) {
						danHits++;
					}
				}
			}
			List<Integer> bets = NumUtils.toIntegerList(qyhCompoundContent.getBetList());
			for (Integer result : results) {
				if (bets.contains(result)) {
					hits++;
				}
				selected = bets.size();
			}
			qyhWinUnits.addRandomSixWinUnits(checkWin(danHits, hits, danSelected, selected, 5, 6) * multiple);
			return qyhWinUnits;
		}

		@Override
		public Integer countDanUnit(List<String> danbets, List<String> tuobets) {
			return MathUtils.comp(6 - danbets.size(), tuobets.size());
		}

		@Override
		public Integer countUnit(QyhCompoundContent compoundContent) {
			return MathUtils.comp(6, compoundContent.getBetList().size());
		}
	},
	/** 任选七中五 */
	/**
	 * 从23个号码中任选7个号码，1/22的中奖机会，奖金1800元。
	 */
	RandomSeven("任选七", Integer.valueOf("1800"), 7) {
		public QyhWinUnits calcWinUnit(Integer[] results, QyhCompoundContent qyhCompoundContent, int multiple) {
			QyhWinUnits qyhWinUnits = new QyhWinUnits();
			int danHits = 0;
			int hits = 0;
			int danSelected = 0;
			int selected = 0;
			if (qyhCompoundContent.getBetDanList() != null && !qyhCompoundContent.getBetDanList().isEmpty()) {
				List<Integer> bets = NumUtils.toIntegerList(qyhCompoundContent.getBetDanList());
				danSelected = bets.size();
				for (Integer result : results) {
					if (bets.contains(result)) {
						danHits++;
					}
				}
			}
			List<Integer> bets = NumUtils.toIntegerList(qyhCompoundContent.getBetList());
			for (Integer result : results) {
				if (bets.contains(result)) {
					hits++;
				}
				selected = bets.size();
			}
			qyhWinUnits.addRandomSevenWinUnits(checkWin(danHits, hits, danSelected, selected, 5, 7) * multiple);
			return qyhWinUnits;
		}

		@Override
		public Integer countDanUnit(List<String> danbets, List<String> tuobets) {
			return MathUtils.comp(7 - danbets.size(), tuobets.size());
		}

		@Override
		public Integer countUnit(QyhCompoundContent compoundContent) {
			return MathUtils.comp(7, compoundContent.getBetList().size());
		}
	},
	/** 任选八中五 */
	/**
	 * 从23个号码中任选8个号码，1/8.25的中奖机会，奖金9元。
	 */
	RandomEight("任选八", Integer.valueOf("680"), 8) {
		public QyhWinUnits calcWinUnit(Integer[] results, QyhCompoundContent qyhCompoundContent, int multiple) {
			QyhWinUnits qyhWinUnits = new QyhWinUnits();
			int danHits = 0;
			int hits = 0;
			int danSelected = 0;
			int selected = 0;
			if (qyhCompoundContent.getBetDanList() != null && !qyhCompoundContent.getBetDanList().isEmpty()) {
				List<Integer> bets = NumUtils.toIntegerList(qyhCompoundContent.getBetDanList());
				danSelected = bets.size();
				for (Integer result : results) {
					if (bets.contains(result)) {
						danHits++;
					}
				}
			}
			List<Integer> bets = NumUtils.toIntegerList(qyhCompoundContent.getBetList());
			for (Integer result : results) {
				if (bets.contains(result)) {
					hits++;
				}
				selected = bets.size();
			}
			qyhWinUnits.addRandomEightWinUnits(checkWin(danHits, hits, danSelected, selected, 5, 8) * multiple);
			return qyhWinUnits;
		}

		@Override
		public Integer countDanUnit(List<String> danbets, List<String> tuobets) {
			return MathUtils.comp(8 - danbets.size(), tuobets.size());
		}

		@Override
		public Integer countUnit(QyhCompoundContent compoundContent) {
			return MathUtils.comp(8, compoundContent.getBetList().size());
		}
	},
	/** 任选九中五 */
	/**
	 * 从23个号码中任选8个号码，1/8.25的中奖机会，奖金300元。
	 */
	RandomNine("任选九", Integer.valueOf("300"), 9) {
		public QyhWinUnits calcWinUnit(Integer[] results, QyhCompoundContent qyhCompoundContent, int multiple) {
			QyhWinUnits qyhWinUnits = new QyhWinUnits();
			int danHits = 0;
			int hits = 0;
			int danSelected = 0;
			int selected = 0;
			if (qyhCompoundContent.getBetDanList() != null && !qyhCompoundContent.getBetDanList().isEmpty()) {
				List<Integer> bets = NumUtils.toIntegerList(qyhCompoundContent.getBetDanList());
				danSelected = bets.size();
				for (Integer result : results) {
					if (bets.contains(result)) {
						danHits++;
					}
				}
			}
			List<Integer> bets = NumUtils.toIntegerList(qyhCompoundContent.getBetList());
			for (Integer result : results) {
				if (bets.contains(result)) {
					hits++;
				}
				selected = bets.size();
			}
			qyhWinUnits.addRandomNineWinUnits(checkWin(danHits, hits, danSelected, selected, 5, 9) * multiple);
			return qyhWinUnits;
		}

		@Override
		public Integer countDanUnit(List<String> danbets, List<String> tuobets) {
			return MathUtils.comp(9 - danbets.size(), tuobets.size());
		}

		@Override
		public Integer countUnit(QyhCompoundContent compoundContent) {
			return MathUtils.comp(9, compoundContent.getBetList().size());
		}
	},
	/** 任选十中五 */
	/**
	 * 从23个号码中任选8个号码，1/8.25的中奖机会，奖金9元。
	 */
	RandomTen("任选十", Integer.valueOf("150"), 10) {
		public QyhWinUnits calcWinUnit(Integer[] results, QyhCompoundContent qyhCompoundContent, int multiple) {
			QyhWinUnits qyhWinUnits = new QyhWinUnits();
			int danHits = 0;
			int hits = 0;
			int danSelected = 0;
			int selected = 0;
			if (qyhCompoundContent.getBetDanList() != null && !qyhCompoundContent.getBetDanList().isEmpty()) {
				List<Integer> bets = NumUtils.toIntegerList(qyhCompoundContent.getBetDanList());
				danSelected = bets.size();
				for (Integer result : results) {
					if (bets.contains(result)) {
						danHits++;
					}
				}
			}
			List<Integer> bets = NumUtils.toIntegerList(qyhCompoundContent.getBetList());
			for (Integer result : results) {
				if (bets.contains(result)) {
					hits++;
				}
				selected = bets.size();
			}
			qyhWinUnits.addRandomTenWinUnits(checkWin(danHits, hits, danSelected, selected, 5, 10) * multiple);
			return qyhWinUnits;
		}

		@Override
		public Integer countDanUnit(List<String> danbets, List<String> tuobets) {
			return MathUtils.comp(10 - danbets.size(), tuobets.size());
		}

		@Override
		public Integer countUnit(QyhCompoundContent compoundContent) {
			return MathUtils.comp(10, compoundContent.getBetList().size());
		}
	},
	/** 围一 **/
	RoundOne("围一", Integer.valueOf("25"), 1) {

		public QyhWinUnits calcWinUnit(Integer[] results, QyhCompoundContent qyhCompoundContent, int multiple) {
			QyhWinUnits qyhWinUnits = new QyhWinUnits();
			int resultNum1 = results[0];
			List<Integer> bets1 = NumUtils.toIntegerList(qyhCompoundContent.getBetList());
			if (bets1.contains(resultNum1)) {
				qyhWinUnits.addRoundOneWinUnits(multiple);
			}
			return qyhWinUnits;
		}

		@Override
		public Integer countDanUnit(List<String> danbets, List<String> tuobets) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		@Override
		public Integer countUnit(QyhCompoundContent compoundContent) {
			return compoundContent.getBetList().size();
		}

	},
	/** 围二 **/
	RoundTwo("围二", Integer.valueOf("300"), 2) {
		public QyhWinUnits calcWinUnit(Integer[] results, QyhCompoundContent qyhCompoundContent, int multiple) {
			QyhWinUnits qyhWinUnits = new QyhWinUnits();
			List<Integer> bets1 = NumUtils.toIntegerList(qyhCompoundContent.getBetList());
			if (bets1.contains(results[0]) && bets1.contains(results[1])) {
				qyhWinUnits.addRoundTwoWinUnits(multiple);
			}
			return qyhWinUnits;
		}

		public Integer countUnit(List<String> bets) {
			return bets.size();
		}

		public Integer countUnit(List<String> bets1, List<String> bets2) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		public Integer countUnit(List<String> bets1, List<String> bets2, List<String> bets3) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		@Override
		public Integer countDanUnit(List<String> danbets, List<String> tuobets) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		@Override
		public Integer countUnit(QyhCompoundContent compoundContent) {
			return MathUtils.comp(2, compoundContent.getBetList().size());
		}
	},
	/** 围三 **/
	RoundThree("围三", Integer.valueOf("2000"), 3) {
		public QyhWinUnits calcWinUnit(Integer[] results, QyhCompoundContent qyhCompoundContent, int multiple) {
			QyhWinUnits qyhWinUnits = new QyhWinUnits();
			List<Integer> bets1 = NumUtils.toIntegerList(qyhCompoundContent.getBetList());
			if (bets1.contains(results[0]) && bets1.contains(results[1]) && bets1.contains(results[2])) {
				qyhWinUnits.addRoundThreeWinUnits(multiple);
			}
			return qyhWinUnits;
		}

		public Integer countUnit(List<String> bets) {
			return bets.size();
		}

		public Integer countUnit(List<String> bets1, List<String> bets2) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		public Integer countUnit(List<String> bets1, List<String> bets2, List<String> bets3) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		@Override
		public Integer countDanUnit(List<String> danbets, List<String> tuobets) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		@Override
		public Integer countUnit(QyhCompoundContent compoundContent) {
			return MathUtils.comp(3, compoundContent.getBetList().size());
		}
	},
	/** 围四 **/
	RoundFour("围四", Integer.valueOf("10000"), 4) {
		public QyhWinUnits calcWinUnit(Integer[] results, QyhCompoundContent qyhCompoundContent, int multiple) {
			QyhWinUnits qyhWinUnits = new QyhWinUnits();
			List<Integer> bets1 = NumUtils.toIntegerList(qyhCompoundContent.getBetList());
			if (bets1.contains(results[0]) && bets1.contains(results[1]) && bets1.contains(results[2])
					&& bets1.contains(results[3])) {
				qyhWinUnits.addRoundFourWinUnits(multiple);
			}
			return qyhWinUnits;
		}

		public Integer countUnit(List<String> bets) {
			return bets.size();
		}

		public Integer countUnit(List<String> bets1, List<String> bets2) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		public Integer countUnit(List<String> bets1, List<String> bets2, List<String> bets3) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		@Override
		public Integer countDanUnit(List<String> danbets, List<String> tuobets) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		@Override
		public Integer countUnit(QyhCompoundContent compoundContent) {
			return MathUtils.comp(4, compoundContent.getBetList().size());
		}
	},
	/** 顺一 **/
	DirectOne("顺一", Integer.valueOf("26"), 1) {
		public QyhWinUnits calcWinUnit(Integer[] results, QyhCompoundContent qyhCompoundContent, int multiple) {
			QyhWinUnits qyhWinUnits = new QyhWinUnits();
			int resultNum1 = results[0];
			List<Integer> bets1 = NumUtils.toIntegerList(qyhCompoundContent.getBetList());
			if (bets1.contains(resultNum1)) {
				qyhWinUnits.addDirectOneWinUnits(multiple);
			}
			return qyhWinUnits;
		}

		public Integer countUnit(List<String> bets) {
			return bets.size();
		}

		public Integer countUnit(List<String> bets1, List<String> bets2) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		public Integer countUnit(List<String> bets1, List<String> bets2, List<String> bets3) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		@Override
		public Integer countDanUnit(List<String> danbets, List<String> tuobets) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		@Override
		public Integer countUnit(QyhCompoundContent compoundContent) {
			return compoundContent.getBetList().size();
		}
	},
	/** 顺二 **/
	DirectTwo("顺二", Integer.valueOf("590"), 1) {
		public QyhWinUnits calcWinUnit(Integer[] results, QyhCompoundContent qyhCompoundContent, int multiple) {
			QyhWinUnits qyhWinUnits = new QyhWinUnits();
			List<Integer> bets1 = NumUtils.toIntegerList(qyhCompoundContent.getBet1List());
			List<Integer> bets2 = NumUtils.toIntegerList(qyhCompoundContent.getBet2List());
			if (bets1.contains(results[0]) && bets2.contains(results[1])) {
				qyhWinUnits.addDirectTwoWinUnits(multiple);
			}
			return qyhWinUnits;
		}

		public Integer countUnit(List<String> bets) {
			return bets.size();
		}

		public Integer countUnit(List<String> bets1, List<String> bets2) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		public Integer countUnit(List<String> bets1, List<String> bets2, List<String> bets3) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		@Override
		public Integer countDanUnit(List<String> danbets, List<String> tuobets) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		@Override
		public Integer countUnit(QyhCompoundContent compoundContent) {
			Set<String> betSet = new HashSet<String>();
			Integer betUnits = 0;
			List<String> bets1 = compoundContent.getBet1List();
			List<String> bets2 = compoundContent.getBet2List();
			for (int i = 0; i < bets1.size(); i++) {
				for (int j = 0; j < bets2.size(); j++) {
					betSet.clear();
					betSet.add(bets1.get(i));
					betSet.add(bets2.get(j));
					if (betSet.size() == 2) {
						betUnits += 1;
					}
				}
			}
			return betUnits;
		}
	},
	/** 顺三 **/
	DirectThree("顺三", Integer.valueOf("12300"), 1) {
		public QyhWinUnits calcWinUnit(Integer[] results, QyhCompoundContent qyhCompoundContent, int multiple) {
			QyhWinUnits qyhWinUnits = new QyhWinUnits();
			List<Integer> bets1 = NumUtils.toIntegerList(qyhCompoundContent.getBet1List());
			List<Integer> bets2 = NumUtils.toIntegerList(qyhCompoundContent.getBet2List());
			List<Integer> bets3 = NumUtils.toIntegerList(qyhCompoundContent.getBet3List());
			if (bets1.contains(results[0]) && bets2.contains(results[1]) && bets3.contains(results[2])) {
				qyhWinUnits.addDirectThreeWinUnits(multiple);
			}
			return qyhWinUnits;
		}

		public Integer countUnit(List<String> bets) {
			return bets.size();
		}

		public Integer countUnit(List<String> bets1, List<String> bets2) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		public Integer countUnit(List<String> bets1, List<String> bets2, List<String> bets3) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		@Override
		public Integer countDanUnit(List<String> danbets, List<String> tuobets) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		@Override
		public Integer countUnit(QyhCompoundContent compoundContent) {
			Set<String> betSet = new HashSet<String>();
			Integer betUnits = 0;
			List<String> bets1 = compoundContent.getBet1List();
			List<String> bets2 = compoundContent.getBet2List();
			List<String> bets3 = compoundContent.getBet3List();
			for (int i = 0; i < bets1.size(); i++) {
				for (int j = 0; j < bets2.size(); j++) {
					for (int k = 0; k < bets3.size(); k++) {
						betSet.clear();
						betSet.add(bets1.get(i));
						betSet.add(bets2.get(j));
						betSet.add(bets3.get(k));
						if (betSet.size() == 3) {
							betUnits += 1;
						}
					}
				}
			}
			return betUnits;
		}
	};
	private QyhPlayType(String typeName, Integer prize, Integer lineLimit) {
		this.typeName = typeName;
		this.prize = prize;
		this.lineLimit = lineLimit;
	}

	private QyhPlayType(String typeName, Integer prize, Integer lineLimit, Integer prize2) {
		this(typeName, prize, lineLimit);
		this.prize2 = prize2;
	}

	private QyhPlayType(String typeName, Integer prize, Integer lineLimit, Integer prize2, Integer prize3) {
		this(typeName, prize, lineLimit, prize2);
		this.prize3 = prize3;
	}

	/** 类型名称 */
	private final String typeName;

	/** 一等奖名称 */
	private final Integer prize;
	/** 二等奖 **/
	private Integer prize2;
	/** 三等奖 **/
	private Integer prize3;

	private final Integer lineLimit;

	public abstract QyhWinUnits calcWinUnit(Integer[] results, QyhCompoundContent compoundContent, int multiple);

	public abstract Integer countUnit(QyhCompoundContent compoundContent);

	public abstract Integer countDanUnit(List<String> danbets, List<String> tuobets);

	public static QyhPlayType getQyhPlayType(String typeName) {
		for (QyhPlayType type : QyhPlayType.values()) {
			if (type.typeName.equals(typeName))
				return type;
		}
		return null;
	}

	/**
	 * @return the typeName
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * @return the prize
	 */
	public Integer getPrize() {
		return prize;
	}

	/**
	 * 获取玩法值
	 * 
	 * @return
	 */
	public byte getBetTypeValue() {
		return (byte) this.ordinal();
	}

	public Integer getPrize2() {
		return prize2;
	}

	public Integer getPrize3() {
		return prize3;
	}

	/**
	 * 
	 * @param danHits
	 *            胆码中的个数
	 * @param hits
	 *            拖码中的个数
	 * @param danSelected
	 *            选择胆码的个数
	 * @param selected
	 *            选择拖码的个数
	 * @param needHits
	 *            总共需要中的个数
	 * @param maxNums
	 *            一注号码的个数
	 * @return
	 */
	private static Integer checkWin(Integer danHits, Integer hits, Integer danSelected, Integer selected,
			Integer needHits, Integer maxNums) {
		if (danHits > needHits) {
			return 0;
		}

		int needUnHits = maxNums - needHits; // 需要不中的数目
		int danUnHits = danSelected - danHits; // 胆码不中的数目
		if (danUnHits > needUnHits)
			return 0;

		int tuomaNeedNums = maxNums - danSelected;// 需要的拖码数
		int unHits = selected - hits;// 不中的拖码数
		int tuomaNeedHits = needHits - danHits;// 拖码需要命中的数目
		int tuomaNeedUnHits = tuomaNeedNums - tuomaNeedHits;// 拖码需要不中的数目
		return MathUtils.comp(tuomaNeedHits, hits) * MathUtils.comp(tuomaNeedUnHits, unHits);
	}

	public Integer getLineLimit() {
		return lineLimit;
	}

	public static void main(String[] args) {
		
	}
}
