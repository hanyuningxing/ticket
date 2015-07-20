package com.cai310.lottery.support.dlt;

import java.util.List;

import com.cai310.lottery.DltConstant;
import com.cai310.utils.MathUtils;

/**
 * 投注方式.<br/>
 * 注：添加类型只能在后面添加，不能插入中间
 * 
 */
public enum DltPlayType {
	/** 常规 */
	General("超级大乐透") {
		public DltWinUnit calcWinUnit(List<Integer> redResults, List<Integer> blueResults, List<Integer> redDanBets,
				List<Integer> redBets, List<Integer> blueDanBets, List<Integer> blueBets, int multiple) {
			DltWinUnit dltWinUnit = new DltWinUnit();
			Integer redDansHitted = 0;
			Integer redsHitted = 0;
			Integer blueDansHitted = 0;
			Integer bluesHitted = 0;
			for (int i = 0; i < redResults.size(); i++) {
				Integer redResult = redResults.get(i);
				if (redDanBets.contains(redResult)) {
					redDansHitted++;
				}
			}
			for (int i = 0; i < redResults.size(); i++) {
				Integer redResult = redResults.get(i);
				if (redBets.contains(redResult)) {
					redsHitted++;
				}
			}
			for (int i = 0; i < blueResults.size(); i++) {
				Integer blueResult = blueResults.get(i);
				if (blueDanBets.contains(blueResult)) {
					blueDansHitted++;
				}
			}
			for (int i = 0; i < blueResults.size(); i++) {
				Integer blueResult = blueResults.get(i);
				if (blueBets.contains(blueResult)) {
					bluesHitted++;
				}
			}

			int qian = (DltConstant.RED_MAX_HITS - redDanBets.size() >= redsHitted) ? redsHitted + redDansHitted
					: DltConstant.RED_MAX_HITS - redDanBets.size() + redDansHitted; // 前区命中数
			int hou = (DltConstant.BLUE_MAX_HITS - blueDanBets.size() >= bluesHitted) ? bluesHitted + blueDansHitted
					: DltConstant.BLUE_MAX_HITS - blueDanBets.size() + blueDansHitted; // 后区命中数
			if ((qian + hou) >= 3 || hou == 2) {
				// 中奖
				int redGalls = redDanBets.size(); // 前区胆码数
				int redGallHits = redDansHitted; // 前区胆码命中数
				int reds = redBets.size(); // 前区拖码数
				int redHits = redsHitted; // 前区拖码命中数
				int blueGalls = blueDanBets.size(); // 后区胆码数
				int blueGallHits = blueDansHitted; // 后区胆码命中数
				int blues = blueBets.size(); // 后区拖码数
				int blueHits = bluesHitted; // 后区拖码命中数

				dltWinUnit.setFirstWinUnits((calFirst(redGalls, redGallHits, reds, redHits, blueGalls, blueGallHits,
						blues, blueHits) * multiple));
				dltWinUnit.setSecondWinUnits((calSecond(redGalls, redGallHits, reds, redHits, blueGalls, blueGallHits,
						blues, blueHits) * multiple));
				dltWinUnit.setThirdWinUnits((calThird(redGalls, redGallHits, reds, redHits, blueGalls, blueGallHits,
						blues, blueHits) * multiple));
				dltWinUnit.setFourthWinUnits((calFourth(redGalls, redGallHits, reds, redHits, blueGalls, blueGallHits,
						blues, blueHits) * multiple));
				dltWinUnit.setFifthWinUnits((calFifth(redGalls, redGallHits, reds, redHits, blueGalls, blueGallHits,
						blues, blueHits) * multiple));
				dltWinUnit.setSixthWinUnits(calSixth(redGalls, redGallHits, reds, redHits, blueGalls, blueGallHits,
						blues, blueHits)
						* multiple);
				dltWinUnit.setSeventhWinUnits(calSeventh(redGalls, redGallHits, reds, redHits, blueGalls, blueGallHits,
						blues, blueHits)
						* multiple);
				dltWinUnit.setEighthWinUnits(calEighth(redGalls, redGallHits, reds, redHits, blueGalls, blueGallHits,
						blues, blueHits)
						* multiple);
				dltWinUnit.setSelect12to2WinUnits(0);
			}
			return dltWinUnit;
		}

		public DltWinUnit calcWinUnit(List<Integer> blueResults, List<Integer> blueBets, List<Integer> blueDanBets,
				int multiple) {
			throw new RuntimeException("此方法对本玩法不适用！");
		};
	},
	/** 后区 */
	Select12to2("后区12选2生肖乐") {
		public DltWinUnit calcWinUnit(List<Integer> redResults, List<Integer> blueResults, List<Integer> redDanBets,
				List<Integer> redBets, List<Integer> blueDanBets, List<Integer> blueBets, int multiple) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		public DltWinUnit calcWinUnit(List<Integer> blueResults, List<Integer> blueDanBets, List<Integer> blueBets,
				int multiple) {
			DltWinUnit dltWinUnit = new DltWinUnit();
			Integer blueDansHitted = 0;
			Integer bluesHitted = 0;
			for (int i = 0; i < blueResults.size(); i++) {
				Integer blueResult = blueResults.get(i);
				if (blueDanBets.contains(blueResult)) {
					blueDansHitted++;
				}
			}
			for (int i = 0; i < blueResults.size(); i++) {
				Integer blueResult = blueResults.get(i);
				if (blueBets.contains(blueResult)) {
					bluesHitted++;
				}
			}
			int hou = (DltConstant.BLUE_MAX_HITS - blueDanBets.size() >= bluesHitted) ? bluesHitted + blueDansHitted
					: DltConstant.BLUE_MAX_HITS - blueDanBets.size() + blueDansHitted; // 后区命中数
			if (hou == 2) {
				// 中奖
				int blueGalls = blueDanBets.size(); // 后区胆码数
				int blueGallHits = blueDansHitted; // 后区胆码命中数
				int blues = blueBets.size(); // 后区拖码数
				int blueHits = bluesHitted; // 后区拖码命中数
				dltWinUnit.setFirstWinUnits(0);
				dltWinUnit.setSecondWinUnits(0);
				dltWinUnit.setThirdWinUnits(0);
				dltWinUnit.setFourthWinUnits(0);
				dltWinUnit.setFifthWinUnits(0);
				dltWinUnit.setSixthWinUnits(0);
				dltWinUnit.setSeventhWinUnits(0);
				dltWinUnit.setEighthWinUnits(0);
				dltWinUnit.setSelect12to2WinUnits(calSelect12to2(blueGalls, blueGallHits, blues, blueHits) * multiple);
			}
			return dltWinUnit;
		};
	},
	/** 超级大乐透(追加) */
	GeneralAdditional("超级大乐透(追加)") {
		public DltWinUnit calcWinUnit(List<Integer> redResults, List<Integer> blueResults, List<Integer> redDanBets,
				List<Integer> redBets, List<Integer> blueDanBets, List<Integer> blueBets, int multiple) {
			DltWinUnit dltWinUnit = new DltWinUnit();
			Integer redDansHitted = 0;
			Integer redsHitted = 0;
			Integer blueDansHitted = 0;
			Integer bluesHitted = 0;
			for (int i = 0; i < redResults.size(); i++) {
				Integer redResult = redResults.get(i);
				if (redDanBets.contains(redResult)) {
					redDansHitted++;
				}
			}
			for (int i = 0; i < redResults.size(); i++) {
				Integer redResult = redResults.get(i);
				if (redBets.contains(redResult)) {
					redsHitted++;
				}
			}
			for (int i = 0; i < blueResults.size(); i++) {
				Integer blueResult = blueResults.get(i);
				if (blueDanBets.contains(blueResult)) {
					blueDansHitted++;
				}
			}
			for (int i = 0; i < blueResults.size(); i++) {
				Integer blueResult = blueResults.get(i);
				if (blueBets.contains(blueResult)) {
					bluesHitted++;
				}
			}

			int qian = (DltConstant.RED_MAX_HITS - redDanBets.size() >= redsHitted) ? redsHitted + redDansHitted
					: DltConstant.RED_MAX_HITS - redDanBets.size() + redDansHitted; // 前区命中数
			int hou = (DltConstant.BLUE_MAX_HITS - blueDanBets.size() >= bluesHitted) ? bluesHitted + blueDansHitted
					: DltConstant.BLUE_MAX_HITS - blueDanBets.size() + blueDansHitted; // 后区命中数
			if ((qian + hou) >= 3 || hou == 2) {
				// 中奖
				int redGalls = redDanBets.size(); // 前区胆码数
				int redGallHits = redDansHitted; // 前区胆码命中数
				int reds = redBets.size(); // 前区拖码数
				int redHits = redsHitted; // 前区拖码命中数
				int blueGalls = blueDanBets.size(); // 后区胆码数
				int blueGallHits = blueDansHitted; // 后区胆码命中数
				int blues = blueBets.size(); // 后区拖码数
				int blueHits = bluesHitted; // 后区拖码命中数

				dltWinUnit.setFirstWinUnits((calFirst(redGalls, redGallHits, reds, redHits, blueGalls, blueGallHits,
						blues, blueHits) * multiple));
				dltWinUnit.setSecondWinUnits((calSecond(redGalls, redGallHits, reds, redHits, blueGalls, blueGallHits,
						blues, blueHits) * multiple));
				dltWinUnit.setThirdWinUnits((calThird(redGalls, redGallHits, reds, redHits, blueGalls, blueGallHits,
						blues, blueHits) * multiple));
				dltWinUnit.setFourthWinUnits((calFourth(redGalls, redGallHits, reds, redHits, blueGalls, blueGallHits,
						blues, blueHits) * multiple));
				dltWinUnit.setFifthWinUnits((calFifth(redGalls, redGallHits, reds, redHits, blueGalls, blueGallHits,
						blues, blueHits) * multiple));
				dltWinUnit.setSixthWinUnits(calSixth(redGalls, redGallHits, reds, redHits, blueGalls, blueGallHits,
						blues, blueHits)
						* multiple);
				dltWinUnit.setSeventhWinUnits(calSeventh(redGalls, redGallHits, reds, redHits, blueGalls, blueGallHits,
						blues, blueHits)
						* multiple);
				dltWinUnit.setEighthWinUnits(calEighth(redGalls, redGallHits, reds, redHits, blueGalls, blueGallHits,
						blues, blueHits)
						* multiple);
				dltWinUnit.setSelect12to2WinUnits(0);
			}
			return dltWinUnit;
		}

		public DltWinUnit calcWinUnit(List<Integer> blueResults, List<Integer> blueBets, List<Integer> blueDanBets,
				int multiple) {
			throw new RuntimeException("此方法对本玩法不适用！");
		};
	};
	/** 类型名称 */
	private final String typeName;

	/**
	 * @param typeName {@link #typeName}
	 */
	private DltPlayType(String typeName) {
		this.typeName = typeName;
	}

	/**
	 * @return {@link #typeName}
	 */
	public String getTypeName() {
		return typeName;
	}

	public abstract DltWinUnit calcWinUnit(List<Integer> redResults, List<Integer> blueResults,
			List<Integer> redDanBets, List<Integer> redBets, List<Integer> blueDanBetsList, List<Integer> blueBets,
			int multiple);

	public abstract DltWinUnit calcWinUnit(List<Integer> blueResults, List<Integer> blueDanBets,
			List<Integer> blueBets, int multiple);

	/**
	 * 计算一等奖注数,一等奖须命中：5+2
	 * 
	 * @param redGalls 前区胆码
	 * @param redGallHits 前区胆码命中数
	 * @param reds 前区拖码
	 * @param redHits 前区拖码命中数
	 * @param blueGalls 后区胆码
	 * @param blueGallHits 后区胆码命中数
	 * @param blues 后区拖码
	 * @param blueHits 后区拖码命中数
	 * @return 一等奖注数
	 */
	public static int calFirst(int redGalls, int redGallHits, int reds, int redHits, int blueGalls, int blueGallHits,
			int blues, int blueHits) {
		return calHit(DltConstant.RED_MAX_HITS, 5, redGalls, redGallHits, reds, redHits)
				* calHit(DltConstant.BLUE_MAX_HITS, 2, blueGalls, blueGallHits, blues, blueHits);
	}

	/**
	 * 计算二等奖注数,二等奖须命中：5+1
	 * 
	 * @param redGalls 前区胆码
	 * @param redGallHits 前区胆码命中数
	 * @param reds 前区拖码
	 * @param redHits 前区拖码命中数
	 * @param blueGalls 后区胆码
	 * @param blueGallHits 后区胆码命中数
	 * @param blues 后区拖码
	 * @param blueHits 后区拖码命中数
	 * @return 二等奖注数
	 */
	public static int calSecond(int redGalls, int redGallHits, int reds, int redHits, int blueGalls, int blueGallHits,
			int blues, int blueHits) {
		return calHit(DltConstant.RED_MAX_HITS, 5, redGalls, redGallHits, reds, redHits)
				* calHit(DltConstant.BLUE_MAX_HITS, 1, blueGalls, blueGallHits, blues, blueHits);
	}

	/**
	 * 计算三等奖注数,三等奖须命中：5+0
	 * 
	 * @param redGalls 前区胆码
	 * @param redGallHits 前区胆码命中数
	 * @param reds 前区拖码
	 * @param redHits 前区拖码命中数
	 * @param blueGalls 后区胆码
	 * @param blueGallHits 后区胆码命中数
	 * @param blues 后区拖码
	 * @param blueHits 后区拖码命中数
	 * @return 三等奖注数
	 */
	public static int calThird(int redGalls, int redGallHits, int reds, int redHits, int blueGalls, int blueGallHits,
			int blues, int blueHits) {
		return calHit(DltConstant.RED_MAX_HITS, 5, redGalls, redGallHits, reds, redHits)
				* calHit(DltConstant.BLUE_MAX_HITS, 0, blueGalls, blueGallHits, blues, blueHits);
	}

	/**
	 * 计算四等奖注数,四等奖须命中：4+2
	 * 
	 * @param redGalls 前区胆码
	 * @param redGallHits 前区胆码命中数
	 * @param reds 前区拖码
	 * @param redHits 前区拖码命中数
	 * @param blueGalls 后区胆码
	 * @param blueGallHits 后区胆码命中数
	 * @param blues 后区拖码
	 * @param blueHits 后区拖码命中数
	 * @return 四等奖注数
	 */
	public static int calFourth(int redGalls, int redGallHits, int reds, int redHits, int blueGalls, int blueGallHits,
			int blues, int blueHits) {
		return calHit(DltConstant.RED_MAX_HITS, 4, redGalls, redGallHits, reds, redHits)
				* calHit(DltConstant.BLUE_MAX_HITS, 2, blueGalls, blueGallHits, blues, blueHits);
	}

	/**
	 * 计算五等奖注数,五等奖须命中：4+1
	 * 
	 * @param redGalls 前区胆码
	 * @param redGallHits 前区胆码命中数
	 * @param reds 前区拖码
	 * @param redHits 前区拖码命中数
	 * @param blueGalls 后区胆码
	 * @param blueGallHits 后区胆码命中数
	 * @param blues 后区拖码
	 * @param blueHits 后区拖码命中数
	 * @return 五等奖注数
	 */
	public static int calFifth(int redGalls, int redGallHits, int reds, int redHits, int blueGalls, int blueGallHits,
			int blues, int blueHits) {
		return calHit(DltConstant.RED_MAX_HITS, 4, redGalls, redGallHits, reds, redHits)
				* calHit(DltConstant.BLUE_MAX_HITS, 1, blueGalls, blueGallHits, blues, blueHits);
	}

	/**
	 * 计算六等奖注数,六等奖须命中：4+0或3+2
	 * 
	 * @param redGalls 前区胆码
	 * @param redGallHits 前区胆码命中数
	 * @param reds 前区拖码
	 * @param redHits 前区拖码命中数
	 * @param blueGalls 后区胆码
	 * @param blueGallHits 后区胆码命中数
	 * @param blues 后区拖码
	 * @param blueHits 后区拖码命中数
	 * @return 六等奖注数
	 */
	public static int calSixth(int redGalls, int redGallHits, int reds, int redHits, int blueGalls, int blueGallHits,
			int blues, int blueHits) {
		return calHit(DltConstant.RED_MAX_HITS, 4, redGalls, redGallHits, reds, redHits)
				* calHit(DltConstant.BLUE_MAX_HITS, 0, blueGalls, blueGallHits, blues, blueHits)
				+ calHit(DltConstant.RED_MAX_HITS, 3, redGalls, redGallHits, reds, redHits)
				* calHit(DltConstant.BLUE_MAX_HITS, 2, blueGalls, blueGallHits, blues, blueHits);
	}

	/**
	 * 计算七等奖注数,七等奖须命中：3+1或2+2
	 * 
	 * @param redGalls 前区胆码
	 * @param redGallHits 前区胆码命中数
	 * @param reds 前区拖码
	 * @param redHits 前区拖码命中数
	 * @param blueGalls 后区胆码
	 * @param blueGallHits 后区胆码命中数
	 * @param blues 后区拖码
	 * @param blueHits 后区拖码命中数
	 * @return 七等奖注数
	 */
	public static int calSeventh(int redGalls, int redGallHits, int reds, int redHits, int blueGalls, int blueGallHits,
			int blues, int blueHits) {
		return calHit(DltConstant.RED_MAX_HITS, 3, redGalls, redGallHits, reds, redHits)
				* calHit(DltConstant.BLUE_MAX_HITS, 1, blueGalls, blueGallHits, blues, blueHits)
				+ calHit(DltConstant.RED_MAX_HITS, 2, redGalls, redGallHits, reds, redHits)
				* calHit(DltConstant.BLUE_MAX_HITS, 2, blueGalls, blueGallHits, blues, blueHits);
	}

	/**
	 * 计算八等奖注数,八等奖须命中：3+0或2+1或1+2或0+2
	 * 
	 * @param redGalls 前区胆码
	 * @param redGallHits 前区胆码命中数
	 * @param reds 前区拖码
	 * @param redHits 前区拖码命中数
	 * @param blueGalls 后区胆码
	 * @param blueGallHits 后区胆码命中数
	 * @param blues 后区拖码
	 * @param blueHits 后区拖码命中数
	 * @return 八等奖注数
	 */
	public static int calEighth(int redGalls, int redGallHits, int reds, int redHits, int blueGalls, int blueGallHits,
			int blues, int blueHits) {
		return calHit(DltConstant.RED_MAX_HITS, 3, redGalls, redGallHits, reds, redHits)
				* calHit(DltConstant.BLUE_MAX_HITS, 0, blueGalls, blueGallHits, blues, blueHits)
				+ calHit(DltConstant.RED_MAX_HITS, 2, redGalls, redGallHits, reds, redHits)
				* calHit(DltConstant.BLUE_MAX_HITS, 1, blueGalls, blueGallHits, blues, blueHits)
				+ calHit(DltConstant.RED_MAX_HITS, 1, redGalls, redGallHits, reds, redHits)
				* calHit(DltConstant.BLUE_MAX_HITS, 2, blueGalls, blueGallHits, blues, blueHits)
				+ calHit(DltConstant.RED_MAX_HITS, 0, redGalls, redGallHits, reds, redHits)
				* calHit(DltConstant.BLUE_MAX_HITS, 2, blueGalls, blueGallHits, blues, blueHits);
	}

	/**
	 * 计算命中指定数的组合数
	 * 
	 * @param maxHits 最大命中数
	 * @param hits 须命中数
	 * @param gallBalls 胆码
	 * @param gallBallHits 胆码命中数
	 * @param balls 拖码
	 * @param ballHits 拖码命中数
	 * @return 符合要求的组合数
	 */
	public static int calHit(int maxHits, int hits, int gallBalls, int gallBallHits, int balls, int ballHits) {
		if (hits - gallBallHits >= 0 && maxHits - gallBalls >= hits - gallBallHits) {
			return MathUtils.comp(hits - gallBallHits, ballHits)
					* MathUtils.comp(maxHits - gallBalls - (hits - gallBallHits), balls - ballHits);
		} else {
			return 0;
		}
	}

	/**
	 * 计算12选2奖注数
	 * 
	 * @param blueGalls 后区胆码
	 * @param blueGallHits 后区胆码命中数
	 * @param blues 后区拖码
	 * @param blueHits 后区拖码命中数
	 * @return 12选2奖注数
	 */
	public static int calSelect12to2(int blueGalls, int blueGallHits, int blues, int blueHits) {
		return calHit(DltConstant.BLUE_MAX_HITS, 2, blueGalls, blueGallHits, blues, blueHits);
	}
	/**
	 * @param ordinal 
	 * @return SalesMode
	 */
	public static DltPlayType valueOfOrdinal(Integer ordinal) {
		if (null!=ordinal) {
			for (DltPlayType l : DltPlayType.values()) {
				if (ordinal.equals(l.ordinal()))return l;
			}
		}
		return null;
	}
}
