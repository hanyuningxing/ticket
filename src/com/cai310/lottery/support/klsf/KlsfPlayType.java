package com.cai310.lottery.support.klsf;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.cai310.utils.MathUtils;
import com.cai310.utils.NumUtils;

/**
 * 投注方式.<br/>
 * 注：添加类型只能在后面添加，不能插入中间
 * 
 */
public enum KlsfPlayType {
	/** 选一数投 */
	/**
	 * 指从“01”至“18”中任意选择1个号码，对开奖号码中按开奖顺序出现的第一个位置的投注。
	 * 投注号码与开奖号码中按开奖顺序出现的第一个位置数字号码相符即中奖。中奖概率1/20，单注奖金25元。
	 */
	NormalOne("选一数投", Integer.valueOf("25"),1) {
		public KlsfWinUnits calcWinUnit(Integer[] results, KlsfCompoundContent klsfCompoundContent, int multiple) {
			KlsfWinUnits el11to5WinUnits = new KlsfWinUnits();
			Integer resultsNum1 = results[0];
			List<Integer> bets = NumUtils.toIntegerList(klsfCompoundContent.getBetList());
			if (bets.contains(resultsNum1)) {
				el11to5WinUnits.addNormalOneWinUnits(multiple);
			}
			return el11to5WinUnits;
		}

		public KlsfWinUnits calcWinUnit(Integer[] results, List<Integer> bets1, List<Integer> bets2, int multiple) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		public KlsfWinUnits calcWinUnit(Integer[] results, List<Integer> bets1, List<Integer> bets2,
				List<Integer> bets3, int multiple) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		public Integer countUnit(List<String> bets) {
			return bets.size();
		}

		@Override
		public Integer countDanUnit(List<String> danbets, List<String> tuobets) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}
		public Integer countUnit(List<String> bets1, List<String> bets2) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		public Integer countUnit(List<String> bets1, List<String> bets2, List<String> bets3) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}
	},
	/** 选一红投 */
	/**
	 * 指从“19”和“20”2个红色号码中任意选择1个红色号码，对开奖号码中按开奖顺序出现的第一个位置的投注。
	 * 投注号码与开奖号码中按开奖顺序出现的第一个位置为红色号码即中奖。中奖概率1/10，单注奖金5元。
	 */
	RedOne("选一红投", Integer.valueOf("5"),1) {
		public KlsfWinUnits calcWinUnit(Integer[] results,  KlsfCompoundContent klsfCompoundContent, int multiple) {
			KlsfWinUnits el11to5WinUnits = new KlsfWinUnits();
			Integer resultsNum1 = results[0];
			List<Integer> bets = NumUtils.toIntegerList(klsfCompoundContent.getBetList());
			if (resultsNum1.equals(Integer.valueOf("19")) || resultsNum1.equals(Integer.valueOf("20"))) {
				el11to5WinUnits.addRedOneWinUnits(multiple*bets.size());
			}
			return el11to5WinUnits;
		}

		public KlsfWinUnits calcWinUnit(Integer[] results, List<Integer> bets1, List<Integer> bets2, int multiple) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		public KlsfWinUnits calcWinUnit(Integer[] results, List<Integer> bets1, List<Integer> bets2,
				List<Integer> bets3, int multiple) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}
		@Override
		public Integer countDanUnit(List<String> danbets, List<String> tuobets) {
			throw new RuntimeException("此方法对本玩法不适用！");
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
	},
	/** 选二任选 */
	/**
	 * 指从“01”至“20”中任意选择2个号码对开奖号码中任意2个位置的投注。
	 * 投注号码与开奖号码中任意2个位置的号码相符即中奖。中奖概率1/6.8，单注奖金8元。
	 */
	RandomTwo("选二任选", Integer.valueOf("8"),2) {
		public KlsfWinUnits calcWinUnit(Integer[] results, KlsfCompoundContent klsfCompoundContent, int multiple) {
			KlsfWinUnits klsfWinUnits = new KlsfWinUnits();
			
			int danHits = 0;
			int hits = 0;
			int danSelected = 0;
			int selected = 0;
			if (klsfCompoundContent.getBetDanList() != null && !klsfCompoundContent.getBetDanList().isEmpty()) {
				List<Integer> bets = NumUtils.toIntegerList(klsfCompoundContent.getBetDanList());
				danSelected = bets.size();
				for (Integer result : results) {
					if (bets.contains(result)) {
						danHits++;
					}
				}
			}
			List<Integer> bets = NumUtils.toIntegerList(klsfCompoundContent.getBetList());
			for (Integer result : results) {
				if (bets.contains(result)) {
					hits++;
				}
			}
			selected = bets.size();
			klsfWinUnits.addRandomTwoWinUnits(checkWin(danHits, hits, danSelected, selected, 2, 2) * multiple);
			return klsfWinUnits;
		}

		public KlsfWinUnits calcWinUnit(Integer[] results, List<Integer> bets1, List<Integer> bets2, int multiple) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		public KlsfWinUnits calcWinUnit(Integer[] results, List<Integer> bets1, List<Integer> bets2,
				List<Integer> bets3, int multiple) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		public Integer countUnit(List<String> bets) {
			return MathUtils.comp(2, bets.size());
		}

		public Integer countUnit(List<String> bets1, List<String> bets2) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		public Integer countUnit(List<String> bets1, List<String> bets2, List<String> bets3) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}
		
		@Override
		public Integer countDanUnit(List<String> danbets, List<String> tuobets) {
			return MathUtils.comp(2 - danbets.size(), tuobets.size());
		}
	},
	/** 选二连组 */
	/**
	 * 指从“01”至“20”中任意选择2个号码对开奖号码中按开奖顺序出现的2个连续位置的投注。
	 * 投注号码与开奖号码中按开奖顺序出现的2个连续位置的号码相符（顺序不限）即中奖。中奖概率1/27，单注奖金31元。
	 */
	ConnectTwoGroup("选二连组", Integer.valueOf("31"),2) {
		public KlsfWinUnits calcWinUnit(Integer[] results, KlsfCompoundContent klsfCompoundContent, int multiple) {
			KlsfWinUnits klsfWinUnits = new KlsfWinUnits();
			Boolean[] hit = new Boolean[8];
			List<Integer> bets = NumUtils.toIntegerList(klsfCompoundContent.getBetList());
			hit[0] =bets.contains(results[0]);
			hit[1] = bets.contains(results[1]);
			hit[2] = bets.contains(results[2]);
			hit[3] = bets.contains(results[3]);
			hit[4] = bets.contains(results[4]);
			hit[5] = bets.contains(results[5]);
			hit[6] = bets.contains(results[6]);
			hit[7] = bets.contains(results[7]);
			Integer hitCount = 0;
			List<Integer> hitCountList = new ArrayList<Integer>();
			for (int i = 0; i < hit.length; i++) {
				if (hit[i]) {
					// 该位命中
					hitCount++;
					if (i == hit.length - 1) {
						hitCountList.add(hitCount);
						hitCount = 0;
					}
				} else {
					// 该位没命中
					hitCountList.add(hitCount);
					hitCount = 0;
				}

			}
			hitCount = 0;
			Iterator<Integer> hitContIt = hitCountList.iterator();
			while (hitContIt.hasNext()) {
				hitCount = hitContIt.next();
				if (hitCount >= 2) {
					// 大于2中奖
					klsfWinUnits.addConnectTwoGroupWinUnits((hitCount - 1) * multiple);
				}
			}
			return klsfWinUnits;
		}

		public KlsfWinUnits calcWinUnit(Integer[] results, List<Integer> bets1, List<Integer> bets2, int multiple) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		public KlsfWinUnits calcWinUnit(Integer[] results, List<Integer> bets1, List<Integer> bets2,
				List<Integer> bets3, int multiple) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		public Integer countUnit(List<String> bets) {
			return MathUtils.comp(2, bets.size());
		}

		public Integer countUnit(List<String> bets1, List<String> bets2) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		public Integer countUnit(List<String> bets1, List<String> bets2, List<String> bets3) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}
		@Override
		public Integer countDanUnit(List<String> danbets, List<String> tuobets) {
			return MathUtils.comp(2 - danbets.size(), tuobets.size());
		}
	},
	/** 选二连直 */
	/**
	 * 指从“01”至“20”中任意选择2个号码对开奖号码中按开奖顺序出现的2个连续位置按位相符的投注。
	 * 投注号码与开奖号码中按开奖顺序出现的2个连续位置的号码按位相符即中奖。中奖概率1/54，单注奖金62元。
	 */
	ConnectTwoDirect("选二连直 ", Integer.valueOf("62"),2) {
		public KlsfWinUnits calcWinUnit(Integer[] results, List<Integer> bets1, List<Integer> bets2, int multiple) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		public KlsfWinUnits calcWinUnit(Integer[] results, KlsfCompoundContent klsfCompoundContent, int multiple) {
			KlsfWinUnits klsfWinUnits = new KlsfWinUnits();
			Boolean[] hit = new Boolean[7];
			List<Integer> bet1List = NumUtils.toIntegerList(klsfCompoundContent.getBet1List());
			List<Integer> bet2List = NumUtils.toIntegerList(klsfCompoundContent.getBet2List());
			hit[0] = bet1List.contains(results[0]);
			hit[1] = bet1List.contains(results[1]);
			hit[2] = bet1List.contains(results[2]);
			hit[3] = bet1List.contains(results[3]);
			hit[4] = bet1List.contains(results[4]);
			hit[5] = bet1List.contains(results[5]);
			hit[6] = bet1List.contains(results[6]);

			for (int i = 0; i < hit.length; i++) {
				if (hit[i]) {
					// 第一位该位命中，查看它后一位是否命中
					if (bet2List.contains(results[i + 1])) {
						// 中奖
						klsfWinUnits.addConnectTwoDirectWinUnits(multiple);
					}
				}
			}
			return klsfWinUnits;
		}

		public KlsfWinUnits calcWinUnit(Integer[] results, List<Integer> bets1, List<Integer> bets2,
				List<Integer> bets3, int multiple) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		public Integer countUnit(List<String> bets) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		public Integer countUnit(List<String> bets1, List<String> bets2) {
			Integer repeatCount = 0;
			for (int i = 0; i < bets1.size(); i++) {
				if (bets2.contains(bets1.get(i))) {
					repeatCount++;
				}
			}
			return bets1.size() * bets2.size() - repeatCount;
		}

		public Integer countUnit(List<String> bets1, List<String> bets2, List<String> bets3) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}
		
		@Override
		public Integer countDanUnit(List<String> danbets, List<String> tuobets) {
			throw new RuntimeException("此方法对本玩法不适用！");		
			}
	},
	/** 选三任选 */
	/**
	 *从“01”至“20”中任意选择3个号码对开奖号码中任意3个位置的投注。
	 * 投注号码与开奖号码中任意3个位置的号码相符，即中奖。中奖概率1/20，单注奖金24元。
	 */
	RandomThree("选三任选 ", Integer.valueOf("24"),3) {
		public KlsfWinUnits calcWinUnit(Integer[] results,KlsfCompoundContent klsfCompoundContent, int multiple) {
			KlsfWinUnits klsfWinUnits = new KlsfWinUnits();
			int danHits = 0;
			int hits = 0;
			int danSelected = 0;
			int selected = 0;
			if (klsfCompoundContent.getBetDanList() != null && !klsfCompoundContent.getBetDanList().isEmpty()) {
				List<Integer> bets = NumUtils.toIntegerList(klsfCompoundContent.getBetDanList());
				danSelected = bets.size();
				for (Integer result : results) {
					if (bets.contains(result)) {
						danHits++;
					}
				}
			}
			List<Integer> bets = NumUtils.toIntegerList(klsfCompoundContent.getBetList());
			for (Integer result : results) {
				if (bets.contains(result)) {
					hits++;
				}
			}
			selected = bets.size();
			klsfWinUnits.addRandomThreeWinUnits(checkWin(danHits, hits, danSelected, selected, 3, 3) * multiple);
			return klsfWinUnits;
			
		}

		public KlsfWinUnits calcWinUnit(Integer[] results, List<Integer> bets1, List<Integer> bets2, int multiple) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		public KlsfWinUnits calcWinUnit(Integer[] results, List<Integer> bets1, List<Integer> bets2,
				List<Integer> bets3, int multiple) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		public Integer countUnit(List<String> bets) {
			return MathUtils.comp(3, bets.size());
		}

		public Integer countUnit(List<String> bets1, List<String> bets2) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		public Integer countUnit(List<String> bets1, List<String> bets2, List<String> bets3) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}
		
		@Override
		public Integer countDanUnit(List<String> danbets, List<String> tuobets) {
			return MathUtils.comp(3 - danbets.size(), tuobets.size());
		}
	},
	/** 选三前组 */
	/**
	 *指从“01”至“20”中任意选择3个号码对开奖号码中按开奖顺序出现的前3个连续位置的投注。
	 * 投注号码与开奖号码中按开奖顺序出现的前3个位置的号码相符（顺序不限）即中奖。中奖概率1/1140，单注奖金1300元。
	 */
	ForeThreeGroup("选三前组", Integer.valueOf("1300"),3) {
		public KlsfWinUnits calcWinUnit(Integer[] results, KlsfCompoundContent klsfCompoundContent, int multiple) {
			KlsfWinUnits el11to5WinUnits = new KlsfWinUnits();
			List<Integer> betList = NumUtils.toIntegerList(klsfCompoundContent.getBetList());
			Integer resultNum1 = results[0];
			Integer resultNum2 = results[1];
			Integer resultNum3 = results[2];
			if (betList.contains(resultNum1) && betList.contains(resultNum2) && betList.contains(resultNum3)) {
				el11to5WinUnits.addForeThreeGroupWinUnits(multiple);
			}
			return el11to5WinUnits;
		}

		public KlsfWinUnits calcWinUnit(Integer[] results, List<Integer> bets1, List<Integer> bets2, int multiple) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		public KlsfWinUnits calcWinUnit(Integer[] results, List<Integer> bets1, List<Integer> bets2,
				List<Integer> bets3, int multiple) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		public Integer countUnit(List<String> bets) {
			return MathUtils.comp(3, bets.size());
		}

		public Integer countUnit(List<String> bets1, List<String> bets2) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		public Integer countUnit(List<String> bets1, List<String> bets2, List<String> bets3) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}
		
		@Override
		public Integer countDanUnit(List<String> danbets, List<String> tuobets) {
			return MathUtils.comp(3 - danbets.size(), tuobets.size());
		}
	},
	/** 选三前直 */
	/**
	 * 指从“01”至“20”中任意选择3个号码对开奖号码中按开奖顺序出现的前3个连续位置按位相符的投注。
	 * 投注号码与开奖号码中按开奖顺序出现的前3个位置的号码按位相符即中奖。中奖概率1/6840，单注奖金8000元。
	 */
	ForeThreeDirect("选三前直", Integer.valueOf("8000"),3) {
		public KlsfWinUnits calcWinUnit(Integer[] results, KlsfCompoundContent klsfCompoundContent, int multiple) {
			KlsfWinUnits el11to5WinUnits = new KlsfWinUnits();
			List<Integer> bet1List = NumUtils.toIntegerList(klsfCompoundContent.getBet1List());
			List<Integer> bet2List = NumUtils.toIntegerList(klsfCompoundContent.getBet2List());
			List<Integer> bet3List = NumUtils.toIntegerList(klsfCompoundContent.getBet3List());
			Integer resultNum1 = results[0];
			Integer resultNum2 = results[1];
			Integer resultNum3 = results[2];
			if (bet1List.contains(resultNum1) && bet2List.contains(resultNum2) && bet3List.contains(resultNum3)) {
				el11to5WinUnits.addForeThreeDirectWinUnits(multiple);
			}
			return el11to5WinUnits;
		}

		public KlsfWinUnits calcWinUnit(Integer[] results, List<Integer> bets1, List<Integer> bets2, int multiple) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		public KlsfWinUnits calcWinUnit(Integer[] results, List<Integer> bets1, List<Integer> bets2,
				List<Integer> bets3, int multiple) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		public Integer countUnit(List<String> bets) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		public Integer countUnit(List<String> bets1, List<String> bets2) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		public Integer countUnit(List<String> bets1, List<String> bets2, List<String> bets3) {
			Set<String> betSet = new HashSet<String>();
			Integer betUnits = 0;
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
		
		@Override
		public Integer countDanUnit(List<String> danbets, List<String> tuobets) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}
	},
	/** 选四任选 */
	/**
	 * 指从“01”至“20”中任意选择4个号码，对开奖号码中任意4个位置的投注
	 * 投注号码与开奖号码中任意4个位置的号码相符即中奖。中奖概率1/69，单注奖金80元。
	 */
	RandomFour("选四任选 ", Integer.valueOf("80"),4) {
		public KlsfWinUnits calcWinUnit(Integer[] results, KlsfCompoundContent klsfCompoundContent, int multiple) {
			KlsfWinUnits klsfWinUnits = new KlsfWinUnits();
			int danHits = 0;
			int hits = 0;
			int danSelected = 0;
			int selected = 0;
			if (klsfCompoundContent.getBetDanList() != null && !klsfCompoundContent.getBetDanList().isEmpty()) {
				List<Integer> bets = NumUtils.toIntegerList(klsfCompoundContent.getBetDanList());
				danSelected = bets.size();
				for (Integer result : results) {
					if (bets.contains(result)) {
						danHits++;
					}
				}
			}
			List<Integer> bets = NumUtils.toIntegerList(klsfCompoundContent.getBetList());
			for (Integer result : results) {
				if (bets.contains(result)) {
					hits++;
				}
			}
			selected = bets.size();
			klsfWinUnits.addRandomFourWinUnits(checkWin(danHits, hits, danSelected, selected, 4, 4) * multiple);
			return klsfWinUnits;
		}

		public KlsfWinUnits calcWinUnit(Integer[] results, List<Integer> bets1, List<Integer> bets2, int multiple) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		public KlsfWinUnits calcWinUnit(Integer[] results, List<Integer> bets1, List<Integer> bets2,
				List<Integer> bets3, int multiple) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		public Integer countUnit(List<String> bets) {
			return MathUtils.comp(4, bets.size());
		}

		public Integer countUnit(List<String> bets1, List<String> bets2) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		public Integer countUnit(List<String> bets1, List<String> bets2, List<String> bets3) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}
		
		@Override
		public Integer countDanUnit(List<String> danbets, List<String> tuobets) {
			return MathUtils.comp(4 - danbets.size(), tuobets.size());
		}
	},
	/** 选五任选 */
	/**
	 *指从“01”至“20”中任意选择5个号码，对开奖号码中任意5个位置的投注
	 * 投注号码与开奖号码中任意5个位置的号码相符即中奖。中奖概率1/277，单注奖金320元。
	 */
	RandomFive("选五任选", Integer.valueOf("320"),5) {
		public KlsfWinUnits calcWinUnit(Integer[] results, KlsfCompoundContent klsfCompoundContent, int multiple) {
			KlsfWinUnits klsfWinUnits = new KlsfWinUnits();
			int danHits = 0;
			int hits = 0;
			int danSelected = 0;
			int selected = 0;
			if (klsfCompoundContent.getBetDanList() != null && !klsfCompoundContent.getBetDanList().isEmpty()) {
				List<Integer> bets = NumUtils.toIntegerList(klsfCompoundContent.getBetDanList());
				danSelected = bets.size();
				for (Integer result : results) {
					if (bets.contains(result)) {
						danHits++;
					}
				}
			}
			List<Integer> bets = NumUtils.toIntegerList(klsfCompoundContent.getBetList());
			for (Integer result : results) {
				if (bets.contains(result)) {
					hits++;
				}
			}
			selected = bets.size();
			klsfWinUnits.addRandomFiveWinUnits(checkWin(danHits, hits, danSelected, selected, 5, 5) * multiple);
			return klsfWinUnits;
		}

		public KlsfWinUnits calcWinUnit(Integer[] results, List<Integer> bets1, List<Integer> bets2, int multiple) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		public KlsfWinUnits calcWinUnit(Integer[] results, List<Integer> bets1, List<Integer> bets2,
				List<Integer> bets3, int multiple) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		public Integer countUnit(List<String> bets) {
			return MathUtils.comp(5, bets.size());
		}

		public Integer countUnit(List<String> bets1, List<String> bets2) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		public Integer countUnit(List<String> bets1, List<String> bets2, List<String> bets3) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}
		
		@Override
		public Integer countDanUnit(List<String> danbets, List<String> tuobets) {
			return MathUtils.comp(5 - danbets.size(), tuobets.size());
		}
	};

	private KlsfPlayType(String typeName, Integer prize,Integer lineLimit) {
		this.typeName = typeName;
		this.prize = prize;
		this.lineLimit = lineLimit;
	}

	/** 类型名称 */
	private final String typeName;

	/** 类型名称 */
	private final Integer prize;

	private final Integer lineLimit;
	
	public Integer getLineLimit() {
		return lineLimit;
	}

	public abstract KlsfWinUnits calcWinUnit(Integer[] results, KlsfCompoundContent klsfCompoundContent, int multiple);

	public abstract KlsfWinUnits calcWinUnit(Integer[] results, List<Integer> bets1, List<Integer> bets2, int multiple);

	public abstract KlsfWinUnits calcWinUnit(Integer[] results, List<Integer> bets1, List<Integer> bets2,
			List<Integer> bets3, int multiple);

	public abstract Integer countDanUnit(List<String> danbets, List<String> tuobets);

	public abstract Integer countUnit(List<String> bets);

	public abstract Integer countUnit(List<String> bets1, List<String> bets2);

	public abstract Integer countUnit(List<String> bets1, List<String> bets2, List<String> bets3);

	public static KlsfPlayType getKlsfPlayType(String typeName) {
		for (KlsfPlayType type : KlsfPlayType.values()) {
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
}
