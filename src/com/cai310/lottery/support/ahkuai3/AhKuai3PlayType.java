package com.cai310.lottery.support.ahkuai3;

import java.util.List;

import com.cai310.utils.MathUtils;
import com.cai310.utils.NumUtils;

/**
 * 投注方式.<br/>
 * 注：添加类型只能在后面添加，不能插入中间
 * 缺二同号单选，任选
 */
public enum AhKuai3PlayType {
	/**
	 * 至少选择1个和值（3个号码之和）进行投注，所选和值与开奖的3个号码的和值相同即中奖，奖金9~240，最高可中240元。
	 */
	HeZhi("和值",Integer.valueOf("240"), 1) {
		@Override
		public AhKuai3WinUnits calcWinUnit(Integer[] results,
				AhKuai3CompoundContent ahKuai3CompoundContent, int multiple) {
			AhKuai3WinUnits ahKuai3WinUnits = new AhKuai3WinUnits();
			Integer num1 = results[0];
			Integer num2 = results[1];
			Integer num3 = results[2];
			Integer sum = num1 + num2 + num3;
			List<Integer> bets = NumUtils.toIntegerList(ahKuai3CompoundContent.getBetList());
			if (bets.contains(sum)) {
				ahKuai3WinUnits.addHeZhiWinUnits(multiple);
			}
			return ahKuai3WinUnits;
		}

		public Integer countUnit(List<String> bets) {
			return bets.size();
		}

		@Override
		public Integer countDanUnit(List<String> danbets, List<String> tuobets) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		@Override
		public Integer countTwoDXUnit(List<String> twosize, List<String> distwosize) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}
	},
	/**
	 * 对所有相同的三个号码（111、222、333、444、555、666）进行投注，任意号码开出即中奖，单注奖金40元。
	 */
	ThreeTX("三同号通选", Integer.valueOf("40"), 1) {
		@Override
		public AhKuai3WinUnits calcWinUnit(Integer[] results, 
				AhKuai3CompoundContent ahKuai3CompoundContent, int multiple) {
			AhKuai3WinUnits ahKuai3WinUnits = new AhKuai3WinUnits();
			if(results[0]==results[1]&&results[1]==results[2]){
				ahKuai3WinUnits.addThreeTXWinUnits(multiple);
			}
			return ahKuai3WinUnits;
		}

		public Integer countUnit(List<String> bets) {
			return bets.size();
		}

		@Override
		public Integer countDanUnit(List<String> danbets, List<String> tuobets) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		@Override
		public Integer countTwoDXUnit(List<String> twosize, List<String> distwosize) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}
	},
	/**
	 * 对相同的三个号码（111、222、333、444、555、666）中的任意一个进行投注，所选号码开出即中奖，单注奖金240元。
	 */
	ThreeDX("三同号单选", Integer.valueOf("240"), 1) {
		@Override
		public AhKuai3WinUnits calcWinUnit(Integer[] results,
				AhKuai3CompoundContent ahKuai3CompoundContent,int multiple){
			AhKuai3WinUnits ahKuai3WinUnits=new AhKuai3WinUnits();
			List<Integer> bets=NumUtils.toIntegerList(ahKuai3CompoundContent.getBetList());
			if(results[0]==results[1]&&results[1]==results[2]){
				Integer num=results[0];
				Integer sum=num*111;
				if(bets.contains(sum)){
				ahKuai3WinUnits.addThreeTXWinUnits(multiple);
				}
			}
			return ahKuai3WinUnits;
		}
		public Integer countUnit(List<String> bets) {
			return bets.size();
		}

		@Override
		public Integer countDanUnit(List<String> danbets, List<String> tuobets) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}
		@Override
		public Integer countTwoDXUnit(List<String> twosize, List<String> distwosize) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}
	},
	/**
	 * 开奖号码的任意2位，与您投注的二同号一致即中奖，单注奖金15元。
	 */
	TwoFX("二同号复选", Integer.valueOf("15"), 1) {
		@Override
		public AhKuai3WinUnits calcWinUnit(Integer[] results,
				AhKuai3CompoundContent ahKuai3CompoundContent,int multiple){
			AhKuai3WinUnits ahKuai3WinUnits=new AhKuai3WinUnits();
			List<Integer> bets=NumUtils.toIntegerList(ahKuai3CompoundContent.getBetList());
			if(results[0]!=results[1]||results[1]!=results[2]){
				if(results[1]==results[2]){
					Integer num=results[1];
					Integer sum=num*11;
					if(bets.contains(sum)){
						ahKuai3WinUnits.addTwoFXWinUnits(multiple);
					}
				}else if(results[0]==results[1]){
					Integer num=results[1];
					Integer sum=num*11;
					if(bets.contains(sum)){
						ahKuai3WinUnits.addTwoFXWinUnits(multiple);
					}
				}
			}
			return ahKuai3WinUnits;
		}
		
		@Override
		public Integer countDanUnit(List<String> danbets, List<String> tuobets) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		@Override
		public Integer countUnit(List<String> bets) {
			return bets.size();
		}

		@Override
		public Integer countTwoDXUnit(List<String> twosize, List<String> distwosize) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}
	},
	/**
	 * 选择1个相同号码和1个不同号码投注，选号与开奖号码一致即中奖，单注奖金80元。
	 */
	TwoDX("二同号单选", Integer.valueOf("80"), 1) {

		@Override
		public AhKuai3WinUnits calcWinUnit(Integer[] results, 
				AhKuai3CompoundContent ahKuai3CompoundContent, int multiple) {
			AhKuai3WinUnits ahKuai3WinUnits=new AhKuai3WinUnits();
			Integer results1=results[0]*11;
			Integer results2=results[2]*11;
			List<Integer> bets1=NumUtils.toIntegerList(ahKuai3CompoundContent.getBetList());
			List<Integer> bets2=NumUtils.toIntegerList(ahKuai3CompoundContent.getDisList());
			if(results[0]!=results[1]||results[1]!=results[2]){
				if(results[0]==results[1]){
					if(bets1.contains(results1)&&bets2.contains(results[2])){
						ahKuai3WinUnits.addTwoDXWinUnits(multiple);
					}
				}else if(results[1]==results[2]){
					if(bets1.contains(results2)&&bets2.contains(results[0])){
						ahKuai3WinUnits.addTwoDXWinUnits(multiple);
					}
				}
			}
			return ahKuai3WinUnits;
		
		}

		@Override
		public Integer countUnit(List<String> bets) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		@Override
		public Integer countDanUnit(List<String> danbets, List<String> tuobets) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		@Override
		public Integer countTwoDXUnit(List<String> twosize, List<String> distwosize) {
			return twosize.size()*distwosize.size();
		}
		
	},
	/**
	 * 至少选择3个不同号码投注，所选号码与开奖号码一致即中奖，单注奖金40元。
	 */
	RandomThree("三不同号", Integer.valueOf("40"), 3) {
		@Override
		public AhKuai3WinUnits calcWinUnit(Integer[] results,
				AhKuai3CompoundContent ahKuai3CompoundContent,int multiple){
		AhKuai3WinUnits ahKuai3WinUnits=new AhKuai3WinUnits();
		if(results[0]!=results[1]&&results[1]!=results[2]){
			int danHits = 0;//胆码命中数
			int hits = 0;//拖码数
			int danSelected = 0;//胆码命中数
			int selected = 0;//拖码数
			if (ahKuai3CompoundContent.getBetDanList() != null
					&& !ahKuai3CompoundContent.getBetDanList().isEmpty()) {
				List<Integer> bets = NumUtils
						.toIntegerList(ahKuai3CompoundContent.getBetDanList());
				danSelected = bets.size();
				for (Integer result : results) {
					if (bets.contains(result)) {
						danHits++;
					}
				}
			}
			List<Integer> bets = NumUtils
					.toIntegerList(ahKuai3CompoundContent.getBetList());
			for (Integer result : results) {
				if (bets.contains(result)) {
					hits++;
				}
				selected = bets.size();
			}
			ahKuai3WinUnits.addRandomThreeWinUnits(calHit(3, 3, danSelected,
					danHits, selected, hits) * multiple);
		}
		return ahKuai3WinUnits;
		
		}

		@Override
		public Integer countUnit(List<String> bets) {
			return MathUtils.comp(3, bets.size());
		}

		@Override
		public Integer countDanUnit(List<String> danbets, List<String> tuobets) {
			return MathUtils.comp(3 - danbets.size(), tuobets.size());
		}

		@Override
		public Integer countTwoDXUnit(List<String> twosize, List<String> distwosize) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}
	},
	/**
	 * 选择2个号码投注，与开奖号码的任意2位一致即中奖8元。
	 */
	RandomTwo("二不同号", Integer.valueOf("8"), 2) {
		@Override
		public AhKuai3WinUnits calcWinUnit(Integer[] results, 
				AhKuai3CompoundContent ahKuai3CompoundContent, int multiple) {
			AhKuai3WinUnits ahKuai3WinUnits=new AhKuai3WinUnits();
			int danHits = 0;
			int hits = 0;
			int danSelected = 0;
			int selected = 0;
			if (ahKuai3CompoundContent.getBetDanList() != null
					&& !ahKuai3CompoundContent.getBetDanList().isEmpty()) {
				List<Integer> bets = NumUtils
						.toIntegerList(ahKuai3CompoundContent.getBetDanList());
				danSelected = bets.size();
				for (Integer result : results) {
					if (bets.contains(result)) {
						danHits++;
					}
				}
			}
			List<Integer> bets = NumUtils
					.toIntegerList(ahKuai3CompoundContent.getBetList());
			for (Integer result : results) {
				if (bets.contains(result)) {
					hits++;
				}
				selected = bets.size();
			}
			ahKuai3WinUnits.addRandomTwoWinUnits(calHit(2, 2, danSelected,
					danHits, selected, hits) * multiple);
			
			return ahKuai3WinUnits;
		}

		@Override
		public Integer countUnit(List<String> bets) {
			return MathUtils.comp(2, bets.size());
		}

		@Override
		public Integer countDanUnit(List<String> danbets, List<String> tuobets) {
			return MathUtils.comp(2 - danbets.size(), tuobets.size());
		}

		@Override
		public Integer countTwoDXUnit(List<String> twosize, List<String> distwosize) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}
		
	},
	/**
	 * 对所有3个相连的号码（123、234、345、456）进行投注，任意号码开出即中奖，单注奖金10元。
	 */
	ThreeLX("三连号通选", Integer.valueOf("10"), 1) {
		public AhKuai3WinUnits calcWinUnit(Integer[] results, 
				AhKuai3CompoundContent ahKuai3CompoundContent, int multiple) {
			AhKuai3WinUnits ahKuai3WinUnits = new AhKuai3WinUnits();
			if(results[1]==results[0]+1&&results[1]==results[2]-1){
				ahKuai3WinUnits.addThreeLXWinUnits(multiple);
			}
			return ahKuai3WinUnits;
		}

		public Integer countUnit(List<String> bets) {
			return bets.size();
		}

		@Override
		public Integer countDanUnit(List<String> danbets, List<String> tuobets) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		@Override
		public Integer countTwoDXUnit(List<String> twosize, List<String> distwosize) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}
	};

	private AhKuai3PlayType(String typeName, Integer prize, Integer lineLimit) {
		this.typeName = typeName;
		this.prize = prize;
		this.lineLimit = lineLimit;
	}

	/** 类型 */
	private final String typeName;
	/** 价格 */
	private final Integer prize;

	private final Integer lineLimit;

	public abstract AhKuai3WinUnits calcWinUnit(Integer[] results,
			AhKuai3CompoundContent ahKuai3CompoundContent, int multiple);

	public abstract Integer countUnit(List<String> bets);

	public abstract Integer countDanUnit(List<String> danbets, List<String> tuobets);
	
	public abstract Integer countTwoDXUnit(List<String> twosize,List<String> distwosize);

	public static AhKuai3PlayType getAhKuai3PlayType(String typeName) {
		for (AhKuai3PlayType type : AhKuai3PlayType.values()) {
			if (type.typeName.equals(typeName))
				return type;
		}
		return null;
	}

	public String getTypeName() {
		return typeName;
	}

	public Integer getPrize() {
		return prize;
	}

	public Integer getLineLimit() {
		return lineLimit;
	}

	/**
	 * 获取玩法值
	 * 
	 * @return
	 */
	public byte getBetTypeValue() {
		return (byte) this.ordinal();
	}

	/**
	 * 计算命中指定数的组合数
	 * 
	 * @param maxNums
	 *            最大命中数
	 * @param needHits
	 *            须命中数
	 * @param danSelected
	 *            胆码
	 * @param danHits
	 *            胆码命中数
	 * @param selected
	 *            拖码
	 * @param hits
	 *            拖码命中数
	 * @return 符合要求的组合数
	 */
	public static Integer calHit(int maxNums, int needHits, int danSelected, int danHits, int selected, int hits) {
		if (danHits > needHits) {
			return 0;
		}

		int needUnHits = maxNums - needHits; // 需要不中的数目
		int danUnHits = danSelected - danHits; // 胆码不中的数目
		if (danUnHits > needUnHits)
			return 0;

		int tuomaNeedNums = maxNums - danSelected;// 需要的拖码数  1
		int unHits = selected - hits;// 不中的拖码数   3
		int tuomaNeedHits = needHits - danHits;// 拖码需要命中的数目  1
		int tuomaNeedUnHits = tuomaNeedNums - tuomaNeedHits;// 拖码需要不中的数目  0
		return MathUtils.comp(tuomaNeedHits, hits) * MathUtils.comp(tuomaNeedUnHits, unHits);

	}


}
