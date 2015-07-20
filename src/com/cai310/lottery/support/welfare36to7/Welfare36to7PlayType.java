package com.cai310.lottery.support.welfare36to7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import com.cai310.utils.MathUtils;

/**
 * 投注方式.<br/>
 * 注：添加类型只能在后面添加，不能插入中间
 * 
 */
public enum Welfare36to7PlayType {
	/** 36选7 */
	General(Integer.parseInt("0000001", 2), "36选7", 36, 7) {
		public Welfare36to7WinUnit calcWinUnit(List<Integer> commonResults, Integer specialResult, List<Integer> bets,
				int multiple) {
			int commonHits = 0;// 基本号码命中个数
			for (Integer bet : bets) {
				if (commonResults.contains(bet)) {
					commonHits++;
				}
			}
			int specialHits = bets.contains(specialResult) ? 1 : 0;// 特殊号码命中个数
			int unHits = bets.size() - commonHits - specialHits;// 未命中的号码个数

			Welfare36to7WinUnit winUnit = new Welfare36to7WinUnit();
			winUnit.setMaxHit((byte) (commonHits + specialHits));

			// 一等奖 6+1
			winUnit.setFirstWinUnits(multiple
					* Welfare36to7Utils.calcWinUnits(this.getOneBetNum(), 6, 1, commonHits, specialHits, unHits));

			// 二等奖 6+0
			winUnit.setSecondWinUnits(multiple
					* Welfare36to7Utils.calcWinUnits(this.getOneBetNum(), 6, 0, commonHits, specialHits, unHits));

			// 三等奖 5+1
			winUnit.setThirdWinUnits(multiple
					* Welfare36to7Utils.calcWinUnits(this.getOneBetNum(), 5, 1, commonHits, specialHits, unHits));

			// 四等奖 5+0
			winUnit.setFourthWinUnits(multiple
					* Welfare36to7Utils.calcWinUnits(this.getOneBetNum(), 5, 0, commonHits, specialHits, unHits));

			// 五等奖 4+1
			winUnit.setFifthWinUnits(multiple
					* Welfare36to7Utils.calcWinUnits(this.getOneBetNum(), 4, 1, commonHits, specialHits, unHits));

			// 六等奖 4+0 or 3+1
			winUnit
					.setSixthWinUnits(multiple
							* (Welfare36to7Utils.calcWinUnits(this.getOneBetNum(), 4, 0, commonHits, specialHits,
									unHits) + Welfare36to7Utils.calcWinUnits(this.getOneBetNum(), 3, 1, commonHits,
									specialHits, unHits)));

			return winUnit;
		}

		public List<Integer[]> getBetNumList() {
			throw new RuntimeException("此方法对本玩法不适用！");
		}
	},
	/** 好彩1 */
	Haocai1(Integer.parseInt("0000010", 2), "好彩1", 36, 1) {
		public Welfare36to7WinUnit calcWinUnit(List<Integer> commonResults, Integer specialResult, List<Integer> bets,
				int multiple) {
			Welfare36to7WinUnit winUnit = new Welfare36to7WinUnit();
			if (bets.contains(specialResult)) {
				winUnit.setMaxHit((byte) 1);
				winUnit.setHaocai1WinUnits(1 * multiple);// 所选1个号码与特别号码相同
			}
			return winUnit;
		}

		public List<Integer[]> getBetNumList() {
			throw new RuntimeException("此方法对本玩法不适用！");
		}
	},

	/** 好彩2 */
	Haocai2(Integer.parseInt("0000100", 2), "好彩2", 36, 2) {
		public Welfare36to7WinUnit calcWinUnit(List<Integer> commonResults, Integer specialResult, List<Integer> bets,
				int multiple) {
			int commonHits = 0;// 基本号码命中个数
			for (Integer bet : bets) {
				if (commonResults.contains(bet)) {
					commonHits++;
				}
			}
			Welfare36to7WinUnit winUnit = new Welfare36to7WinUnit();
			winUnit.setMaxHit((byte) commonHits);
			winUnit.setHaocai2WinUnits(multiple * MathUtils.comp(2, commonHits));// 所选2个号码与开奖号码的6个基本号码中任意2个号码相同
			return winUnit;
		}

		public List<Integer[]> getBetNumList() {
			throw new RuntimeException("此方法对本玩法不适用！");
		}
	},

	/** 好彩3 */
	Haocai3(Integer.parseInt("0001000", 2), "好彩3", 36, 3) {
		public Welfare36to7WinUnit calcWinUnit(List<Integer> commonResults, Integer specialResult, List<Integer> bets,
				int multiple) {
			int commonHits = 0;// 基本号码命中个数
			for (Integer bet : bets) {
				if (commonResults.contains(bet)) {
					commonHits++;
				}
			}
			Welfare36to7WinUnit winUnit = new Welfare36to7WinUnit();
			winUnit.setMaxHit((byte) commonHits);
			winUnit.setHaocai3WinUnits(multiple * MathUtils.comp(3, commonHits));// 所选3个号码与开奖号码的6个基本号码中任意3个号码相同
			return winUnit;
		}

		public List<Integer[]> getBetNumList() {
			throw new RuntimeException("此方法对本玩法不适用！");
		}
	},

	/** 生肖 */
	Zodiac(Integer.parseInt("0010000", 2), "好彩1生肖", Arrays.asList("鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡",
			"狗", "猪"), 1) {
		public Welfare36to7WinUnit calcWinUnit(List<Integer> commonResults, Integer specialResult, List<Integer> bets,
				int multiple) {
			Welfare36to7WinUnit winUnit = new Welfare36to7WinUnit();
			Integer result = this.getBetTypeResult(specialResult);
			if (bets.contains(result)) {
				winUnit.setMaxHit((byte) 1);
				winUnit.setZodiacWinUnits(1 * multiple);
			}
			return winUnit;
		}

		public List<Integer[]> getBetNumList() {
			List<Integer[]> betNumList = new ArrayList<Integer[]>();
			betNumList.add(new Integer[] { 1, 13, 25 });
			betNumList.add(new Integer[] { 2, 14, 26 });
			betNumList.add(new Integer[] { 3, 15, 27 });
			betNumList.add(new Integer[] { 4, 16, 28 });
			betNumList.add(new Integer[] { 5, 17, 29 });
			betNumList.add(new Integer[] { 6, 18, 30 });
			betNumList.add(new Integer[] { 7, 19, 31 });
			betNumList.add(new Integer[] { 8, 20, 32 });
			betNumList.add(new Integer[] { 9, 21, 33 });
			betNumList.add(new Integer[] { 10, 22, 34 });
			betNumList.add(new Integer[] { 11, 23, 35 });
			betNumList.add(new Integer[] { 12, 24, 36 });
			return betNumList;
		}
	},
	/** 季节 */
	Season(Integer.parseInt("0100000", 2), "好彩1季节", Arrays.asList("春", "夏", "秋", "冬"), 1) {
		public Welfare36to7WinUnit calcWinUnit(List<Integer> commonResults, Integer specialResult, List<Integer> bets,
				int multiple) {
			Welfare36to7WinUnit winUnit = new Welfare36to7WinUnit();
			Integer result = this.getBetTypeResult(specialResult);// 季节开奖结果
			if (bets.contains(result)) {
				winUnit.setMaxHit((byte) 1);
				winUnit.setSeasonWinUnits(1 * multiple);
			}
			return winUnit;
		}

		public List<Integer[]> getBetNumList() {
			List<Integer[]> betNumList = new ArrayList<Integer[]>();
			betNumList.add(new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 });
			betNumList.add(new Integer[] { 10, 11, 12, 13, 14, 15, 16, 17, 18 });
			betNumList.add(new Integer[] { 19, 20, 21, 22, 23, 24, 25, 26, 27 });
			betNumList.add(new Integer[] { 28, 29, 30, 31, 32, 33, 34, 35, 36 });
			return betNumList;
		}
	},
	/** 方位 */
	Azimuth(Integer.parseInt("1000000", 2), "好彩1方位", Arrays.asList("东", "南", "西", "北"), 1) {
		public Welfare36to7WinUnit calcWinUnit(List<Integer> commonResults, Integer specialResult, List<Integer> bets,
				int multiple) {
			Welfare36to7WinUnit winUnit = new Welfare36to7WinUnit();
			Integer result = this.getBetTypeResult(specialResult);// 方位开奖结果
			if (bets.contains(result)) {
				winUnit.setMaxHit((byte) 1);
				winUnit.setAzimuthWinUnits(1 * multiple);
			}
			return winUnit;
		}

		public List<Integer[]> getBetNumList() {
			List<Integer[]> betNumList = new ArrayList<Integer[]>();
			betNumList.add(new Integer[] { 1, 3, 5, 7, 9, 11, 13, 15, 17 });
			betNumList.add(new Integer[] { 2, 4, 6, 8, 10, 12, 14, 16, 18 });
			betNumList.add(new Integer[] { 19, 21, 23, 25, 27, 29, 31, 33, 35 });
			betNumList.add(new Integer[] { 20, 22, 24, 26, 28, 30, 32, 34, 36 });
			return betNumList;
		}

	};
	private final int playType;

	/** 类型名称 */
	private final String typeName;
	/**
	 * 可选项列表
	 */
	private final List<String> textList;

	/** 多少个号码组成一注 */
	private final int oneBetNum;

	/**
	 * @param typeName {@link #typeName}
	 */
	private Welfare36to7PlayType(int playType, String typeName, List<String> textList, Integer oneBetNum) {
		this.playType = playType;
		this.typeName = typeName;
		this.textList = textList;
		this.oneBetNum = oneBetNum;
	}

	private Welfare36to7PlayType(int playType, String typeName, int ballNums, int oneBetNum) {
		this.playType = playType;
		this.typeName = typeName;
		this.oneBetNum = oneBetNum;
		this.textList = new ArrayList<String>();
		for (int i = 1; i <= ballNums; i++) {
			if (i < 10) {
				this.textList.add("0" + i);
			} else {
				this.textList.add(String.valueOf(i));
			}
		}
	}

	/**
	 * @return {@link #typeName}
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * 获取每个季节选项所对应的号码组，注：只适用于生肖、季节、方位3个玩法
	 * 
	 * @return
	 */
	public abstract List<Integer[]> getBetNumList();

	/**
	 * 获取本玩法开奖结果，注：只适用于生肖、季节、方位3个玩法
	 * 
	 * @param specialResult 开奖号码中的特别号码
	 * @return 本玩法开奖结果
	 */
	public Integer getBetTypeResult(Integer specialResult) {
		if (specialResult != null) {
			List<Integer[]> betNumList = this.getBetNumList();
			for (int i = 0; i < betNumList.size(); i++) {
				if (new HashSet<Integer>(Arrays.asList(betNumList.get(i))).contains(specialResult)) {
					return i + 1;
				}
			}
		}
		return null;
	}

	/**
	 * 把号码转换成通俗的文字
	 * 
	 * @param bet 号码
	 * @return 对应的通俗文字
	 */
	public String toText(Integer bet) {
		if (bet != null && bet >= 1 && bet <= this.getTextList().size()) {
			return this.getTextList().get(bet - 1);
		}
		return null;
	}

	public List<String> getTextList() {
		return textList;
	}

	/**
	 * @return 多少个号码组成一注
	 */
	public int getOneBetNum() {
		return oneBetNum;
	}

	public abstract Welfare36to7WinUnit calcWinUnit(List<Integer> commonResults, Integer specialResult,
			List<Integer> bets, int multiple);

	public static Welfare36to7PlayType valueOf(int playType) {
		for (Welfare36to7PlayType type : Welfare36to7PlayType.values()) {
			if (type.playType == playType)
				return type;
		}
		return null;
	}
}
