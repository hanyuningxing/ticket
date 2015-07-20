package com.cai310.lottery.support.klpk;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.cai310.utils.MathUtils;
import com.cai310.utils.NumUtils;
import com.google.common.collect.Lists;

/**
 * 投注方式.<br/>
 * 注：添加类型只能在后面添加，不能插入中间
 * 
 */
public enum KlpkPlayType {
	
	/** 同花包选 */
	/**
	 * 中奖条件：3个开奖号码为任意相同花色
	 */
	SAMEBAO("同花包选", Integer.valueOf("22"),1) {//0
		public KlpkWinUnits calcWinUnit(Integer[] results, KlpkCompoundContent klpkCompoundContent, int multiple) {
			KlpkWinUnits klpkWinUnits = new KlpkWinUnits();
			int hua1 = results[0];
			int hua2 = results[2];
			int hua3 = results[4];
			if (hua1 == hua2 && hua2 == hua3) {
				klpkWinUnits.addSameBaoUnits(multiple);
			}
			return klpkWinUnits;
		}


		public Integer countUnit(List<String> bets) {
			return 1;
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
	},
	/** 同花单选 */
	/**
	 * 中奖条件：所选花色与3个开奖号花色相同
	 */
	SAME("同花单选", Integer.valueOf("90"),1) {//1
		public KlpkWinUnits calcWinUnit(Integer[] results, KlpkCompoundContent klpkCompoundContent, int multiple) {
			KlpkWinUnits klpkWinUnits = new KlpkWinUnits();
			int hua1 = results[0];
			int hua2 = results[2];
			int hua3 = results[4];
			if (hua1 == hua2 && hua2 == hua3) {
				klpkWinUnits.addSameUnits(multiple);
			}
			return klpkWinUnits;
		}

//		public KlpkWinUnits calcWinUnit(Integer[] results, List<Integer> bets1, List<Integer> bets2, int multiple) {
//			throw new RuntimeException("此方法对本玩法不适用！");
//		}
//
//		public KlpkWinUnits calcWinUnit(Integer[] results, List<Integer> bets1, List<Integer> bets2,
//				List<Integer> bets3, int multiple) {
//			throw new RuntimeException("此方法对本玩法不适用！");
//		}

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
			return MathUtils.comp(2-danbets.size(), tuobets.size());
		}
	},
	
	/** 顺子包选 */
	/**
	 * 中奖条件：3个开奖号码为3连号（不分花色）
	 */
	SHUNBAO("顺子包选", Integer.valueOf("33"),1) {//2
		public KlpkWinUnits calcWinUnit(Integer[] results, KlpkCompoundContent klpkCompoundContent, int multiple) {
			KlpkWinUnits klpkWinUnits = new KlpkWinUnits();
			int hao1 = results[1];
			int hao2 = results[3];
			int hao3 = results[5];
			List<Integer> list = Lists.newArrayList();
			list.add(hao1);
			list.add(hao2);
			list.add(hao3);
			Collections.sort(list, MY_LIST_COMPARATOR);
			if(list.get(0)+1==list.get(1)&&list.get(1)+1==list.get(2)){
				//顺子
				klpkWinUnits.addShunBaoUnits(multiple);
			}
			return klpkWinUnits;
		}

		public Integer countUnit(List<String> bets) {
			return 1;
		}

		public Integer countUnit(List<String> bets1, List<String> bets2) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		public Integer countUnit(List<String> bets1, List<String> bets2, List<String> bets3) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		@Override
		public Integer countDanUnit(List<String> danbets, List<String> tuobets) {
			return MathUtils.comp(2-danbets.size(), tuobets.size());
		}
	},
	/** 顺子单选  */
	/**
	 * 中奖条件：所选顺子号与开奖号相同（不分花色）
	 */
	SHUN("顺子单选 ", Integer.valueOf("400"),3) {//3
		
		public KlpkWinUnits calcWinUnit(Integer[] results, KlpkCompoundContent klpkCompoundContent, int multiple) {
			KlpkWinUnits klpkWinUnits = new KlpkWinUnits();
			int hao1 = results[1];
			int hao2 = results[3];
			int hao3 = results[5];
			List<Integer> list = Lists.newArrayList();
			list.add(hao1);
			list.add(hao2);
			list.add(hao3);
			Collections.sort(list, MY_LIST_COMPARATOR);
			if(list.get(0)+1==list.get(1)&&list.get(1)+1==list.get(2)){
				//顺子
				klpkWinUnits.addShunUnits(multiple);
			}
			return klpkWinUnits;
		}

//		public KlpkWinUnits calcWinUnit(Integer[] results, List<Integer> bets1, List<Integer> bets2, int multiple) {
//			KlpkWinUnits klpkWinUnits = new KlpkWinUnits();
//			Integer resultNum1 = results[0];
//			Integer resultNum2 = results[1];
//			if (bets1.contains(resultNum1) && bets2.contains(resultNum2)) {
//				klpkWinUnits.addForeTwoDirectWinUnits(multiple);
//			}
//			return klpkWinUnits;
//		}
//
//		public KlpkWinUnits calcWinUnit(Integer[] results, List<Integer> bets1, List<Integer> bets2,
//				List<Integer> bets3, int multiple) {
//			throw new RuntimeException("此方法对本玩法不适用！");
//		}

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
	},
	
	/** 对子包选 */
	/**
	 * 中奖条件：3个开奖号码为3连号（不分花色）
	 */
	DUIBAO("对子包选", Integer.valueOf("7"),1) {//4
		public KlpkWinUnits calcWinUnit(Integer[] results, KlpkCompoundContent klpkCompoundContent, int multiple) {
			KlpkWinUnits klpkWinUnits = new KlpkWinUnits();
			int hao1 = results[1];
			int hao2 = results[3];
			int hao3 = results[5];
			if(hao2==hao3||hao1==hao2){
				//对子
				klpkWinUnits.addDuiBaoUnits(multiple);
			}
			return klpkWinUnits;
		}

		public Integer countUnit(List<String> bets) {
			return 1;
		}

		public Integer countUnit(List<String> bets1, List<String> bets2) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		public Integer countUnit(List<String> bets1, List<String> bets2, List<String> bets3) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		@Override
		public Integer countDanUnit(List<String> danbets, List<String> tuobets) {
			return MathUtils.comp(2-danbets.size(), tuobets.size());
		}
	},
	/** 对子单选  */
	/**
	 * 中奖条件：所选对子号与开奖号相同（不分花色）
	 */
	DUI("对子单选 ", Integer.valueOf("88"),2) {//5
		
		public KlpkWinUnits calcWinUnit(Integer[] results, KlpkCompoundContent klpkCompoundContent, int multiple) {
			KlpkWinUnits klpkWinUnits = new KlpkWinUnits();
			int hao1 = results[1];
			int hao2 = results[3];
			int hao3 = results[5];
			if(hao2==hao3||hao1==hao2){
				//对子
				klpkWinUnits.addDuiUnits(multiple);
			}
			return klpkWinUnits;
		}

//		public KlpkWinUnits calcWinUnit(Integer[] results, List<Integer> bets1, List<Integer> bets2, int multiple) {
//			KlpkWinUnits klpkWinUnits = new KlpkWinUnits();
//			Integer resultNum1 = results[0];
//			Integer resultNum2 = results[1];
//			if (bets1.contains(resultNum1) && bets2.contains(resultNum2)) {
//				klpkWinUnits.addForeTwoDirectWinUnits(multiple);
//			}
//			return klpkWinUnits;
//		}
//
//		public KlpkWinUnits calcWinUnit(Integer[] results, List<Integer> bets1, List<Integer> bets2,
//				List<Integer> bets3, int multiple) {
//			throw new RuntimeException("此方法对本玩法不适用！");
//		}

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
	},
	
	/** 选一 */
	/**
	 * 中奖条件：所选号码与开奖号码中任意一个相同
	 */
	RandomOne("任选一 ", Integer.valueOf("5"),1) {//6
		public KlpkWinUnits calcWinUnit(Integer[] resultArr, KlpkCompoundContent klpkCompoundContent, int multiple) {
			KlpkWinUnits klpkWinUnits = new KlpkWinUnits();
			int[] results ={resultArr[1],resultArr[3],resultArr[5]};
			int danHits=0;
			int hits=0;
			int danSelected=0;
			int selected=0;
			if(klpkCompoundContent.getBetDanList()!=null&&!klpkCompoundContent.getBetDanList().isEmpty()){
				List<Integer> bets= NumUtils.toIntegerList(klpkCompoundContent.getBetDanList());
				danSelected=bets.size();
				for (Integer result : results) {
					if (bets.contains(result)) {
						danHits++;
					}
				}
			}
			List<Integer> bets= NumUtils.toIntegerList(klpkCompoundContent.getBetList());
			for (Integer result : results) {
				if (bets.contains(result)) {
					hits++;
				}
				selected=bets.size();
			}
			klpkWinUnits.addRandomOneWinUnits(calHit(1,1,danSelected,danHits,selected,hits)*multiple);
			return klpkWinUnits;
		}
		
//		public KlpkWinUnits calcWinUnit(Integer[] results, List<Integer> bets, int multiple) {
//			KlpkWinUnits klpkWinUnits = new KlpkWinUnits();
//			Integer count = 0;
//			for (Integer result : results) {
//				if (bets.contains(result)) {
//					count++;
//				}
//			}
//			if (count >= 3) {
//				klpkWinUnits.addRandomThreeWinUnits(MathUtils.comp(3, count) * multiple);
//			}
//			return klpkWinUnits;
//		}
//
//		public KlpkWinUnits calcWinUnit(Integer[] results, List<Integer> bets1, List<Integer> bets2, int multiple) {
//			throw new RuntimeException("此方法对本玩法不适用！");
//		}
//
//		public KlpkWinUnits calcWinUnit(Integer[] results, List<Integer> bets1, List<Integer> bets2,
//				List<Integer> bets3, int multiple) {
//			throw new RuntimeException("此方法对本玩法不适用！");
//		}

		public Integer countUnit(List<String> bets) {
			return MathUtils.comp(1, bets.size());
		}

		public Integer countUnit(List<String> bets1, List<String> bets2) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		public Integer countUnit(List<String> bets1, List<String> bets2, List<String> bets3) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		@Override
		public Integer countDanUnit(List<String> danbets, List<String> tuobets) {
			return MathUtils.comp(1-danbets.size(), tuobets.size());
		}
	},
	
	/** 选二任选 */
	/**
	 * 中奖条件：选号至少命中开奖号中2个不同数字
	 */
	RandomTwo("任选二 ", Integer.valueOf("33"),2) {
		public KlpkWinUnits calcWinUnit(Integer[] resultArr, KlpkCompoundContent klpkCompoundContent, int multiple) {
			KlpkWinUnits klpkWinUnits = new KlpkWinUnits();
			int[] results ={resultArr[1],resultArr[3],resultArr[5]};
			int danHits=0;
			int hits=0;
			int danSelected=0;
			int selected=0;
			if(klpkCompoundContent.getBetDanList()!=null&&!klpkCompoundContent.getBetDanList().isEmpty()){
				List<Integer> bets= NumUtils.toIntegerList(klpkCompoundContent.getBetDanList());
				danSelected=bets.size();
				for (Integer result : results) {
					if (bets.contains(result)) {
						danHits++;
					}
				}
			}
			List<Integer> bets= NumUtils.toIntegerList(klpkCompoundContent.getBetList());
			for (Integer result : results) {
				if (bets.contains(result)) {
					hits++;
				}
				selected=bets.size();
			}
			klpkWinUnits.addRandomTwoWinUnits(calHit(2,2,danSelected,danHits,selected,hits)*multiple);
			return klpkWinUnits;
		}
		
//		public KlpkWinUnits calcWinUnit(Integer[] results, List<Integer> bets, int multiple) {
//			KlpkWinUnits klpkWinUnits = new KlpkWinUnits();
//			Integer count = 0;
//			for (Integer result : results) {
//				if (bets.contains(result)) {
//					count++;
//				}
//			}
//			if (count >= 3) {
//				klpkWinUnits.addRandomThreeWinUnits(MathUtils.comp(3, count) * multiple);
//			}
//			return klpkWinUnits;
//		}
//
//		public KlpkWinUnits calcWinUnit(Integer[] results, List<Integer> bets1, List<Integer> bets2, int multiple) {
//			throw new RuntimeException("此方法对本玩法不适用！");
//		}
//
//		public KlpkWinUnits calcWinUnit(Integer[] results, List<Integer> bets1, List<Integer> bets2,
//				List<Integer> bets3, int multiple) {
//			throw new RuntimeException("此方法对本玩法不适用！");
//		}

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
			return MathUtils.comp(2-danbets.size(), tuobets.size());
		}
	},
	
	/** 选三任选 */
	/**
	 * 中奖条件：所选号码包括当期全部开奖号码
	 */
	RandomThree("任选三 ", Integer.valueOf("116"),3) {
		public KlpkWinUnits calcWinUnit(Integer[] resultArr, KlpkCompoundContent klpkCompoundContent, int multiple) {
			KlpkWinUnits klpkWinUnits = new KlpkWinUnits();
			int[] results ={resultArr[1],resultArr[3],resultArr[5]};
			int danHits=0;
			int hits=0;
			int danSelected=0;
			int selected=0;
			if(klpkCompoundContent.getBetDanList()!=null&&!klpkCompoundContent.getBetDanList().isEmpty()){
				List<Integer> bets= NumUtils.toIntegerList(klpkCompoundContent.getBetDanList());
				danSelected=bets.size();
				for (Integer result : results) {
					if (bets.contains(result)) {
						danHits++;
					}
				}
			}
			List<Integer> bets= NumUtils.toIntegerList(klpkCompoundContent.getBetList());
			for (Integer result : results) {
				if (bets.contains(result)) {
					hits++;
				}
				selected=bets.size();
			}
			klpkWinUnits.addRandomThreeWinUnits(calHit(3,3,danSelected,danHits,selected,hits)*multiple);
			return klpkWinUnits;
		}
		
//		public KlpkWinUnits calcWinUnit(Integer[] results, List<Integer> bets, int multiple) {
//			KlpkWinUnits klpkWinUnits = new KlpkWinUnits();
//			Integer count = 0;
//			for (Integer result : results) {
//				if (bets.contains(result)) {
//					count++;
//				}
//			}
//			if (count >= 3) {
//				klpkWinUnits.addRandomThreeWinUnits(MathUtils.comp(3, count) * multiple);
//			}
//			return klpkWinUnits;
//		}
//
//		public KlpkWinUnits calcWinUnit(Integer[] results, List<Integer> bets1, List<Integer> bets2, int multiple) {
//			throw new RuntimeException("此方法对本玩法不适用！");
//		}
//
//		public KlpkWinUnits calcWinUnit(Integer[] results, List<Integer> bets1, List<Integer> bets2,
//				List<Integer> bets3, int multiple) {
//			throw new RuntimeException("此方法对本玩法不适用！");
//		}

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
			return MathUtils.comp(3-danbets.size(), tuobets.size());
		}
	},
	
	/** 选四任选 */
	/**
	 * 
	 */
	RandomFour("任选四 ", Integer.valueOf("46"),4) {
		public KlpkWinUnits calcWinUnit(Integer[] resultArr, KlpkCompoundContent klpkCompoundContent, int multiple) {
			KlpkWinUnits klpkWinUnits = new KlpkWinUnits();
			int[] results ={resultArr[1],resultArr[3],resultArr[5]};
			int danHits=0;
			int hits=0;
			int danSelected=0;
			int selected=0;
			if(klpkCompoundContent.getBetDanList()!=null&&!klpkCompoundContent.getBetDanList().isEmpty()){
				List<Integer> bets= NumUtils.toIntegerList(klpkCompoundContent.getBetDanList());
				danSelected=bets.size();
				for (Integer result : results) {
					if (bets.contains(result)) {
						danHits++;
					}
				}
			}
			List<Integer> bets= NumUtils.toIntegerList(klpkCompoundContent.getBetList());
			for (Integer result : results) {
				if (bets.contains(result)) {
					hits++;
				}
				selected=bets.size();
			}
			klpkWinUnits.addRandomFourWinUnits(calHit(4,3,danSelected,danHits,selected,hits)*multiple);
			return klpkWinUnits;
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
			return MathUtils.comp(4-danbets.size(), tuobets.size());
		}
	},
	/** 选五任选 */
	/**
	 */
	RandomFive("任选五", Integer.valueOf("22"),5) {
		public KlpkWinUnits calcWinUnit(Integer[] resultArr, KlpkCompoundContent klpkCompoundContent, int multiple) {
			KlpkWinUnits klpkWinUnits = new KlpkWinUnits();
			int[] results ={resultArr[1],resultArr[3],resultArr[5]};
			int danHits=0;
			int hits=0;
			int danSelected=0;
			int selected=0;
			if(klpkCompoundContent.getBetDanList()!=null&&!klpkCompoundContent.getBetDanList().isEmpty()){
				List<Integer> bets= NumUtils.toIntegerList(klpkCompoundContent.getBetDanList());
				danSelected=bets.size();
				for (Integer result : results) {
					if (bets.contains(result)) {
						danHits++;
					}
				}
			}
			List<Integer> bets= NumUtils.toIntegerList(klpkCompoundContent.getBetList());
			for (Integer result : results) {
				if (bets.contains(result)) {
					hits++;
				}
				selected=bets.size();
			}
			klpkWinUnits.addRandomFiveWinUnits(calHit(5,3,danSelected,danHits,selected,hits)*multiple);
			return klpkWinUnits;
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
			return MathUtils.comp(5-danbets.size(), tuobets.size());
		}
	},
	/** 任选六中三 */
	/**
	 */
	RandomSix("任选六", Integer.valueOf("12"),6) {
		public KlpkWinUnits calcWinUnit(Integer[] resultArr, KlpkCompoundContent klpkCompoundContent, int multiple) {
			KlpkWinUnits klpkWinUnits = new KlpkWinUnits();
			int[] results ={resultArr[1],resultArr[3],resultArr[5]};
			int danHits=0;
			int hits=0;
			int danSelected=0;
			int selected=0;
			if(klpkCompoundContent.getBetDanList()!=null&&!klpkCompoundContent.getBetDanList().isEmpty()){
				List<Integer> bets= NumUtils.toIntegerList(klpkCompoundContent.getBetDanList());
				danSelected=bets.size();
				for (Integer result : results) {
					if (bets.contains(result)) {
						danHits++;
					}
				}
			}
			List<Integer> bets= NumUtils.toIntegerList(klpkCompoundContent.getBetList());
			for (Integer result : results) {
				if (bets.contains(result)) {
					hits++;
				}
				selected=bets.size();
			}
			klpkWinUnits.addRandomSixWinUnits(calHit(6,3,danSelected,danHits,selected,hits)*multiple);
			return klpkWinUnits;
		}

		public Integer countUnit(List<String> bets) {
			return MathUtils.comp(6, bets.size());
		}

		public Integer countUnit(List<String> bets1, List<String> bets2) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		public Integer countUnit(List<String> bets1, List<String> bets2, List<String> bets3) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		@Override
		public Integer countDanUnit(List<String> danbets, List<String> tuobets) {
			return MathUtils.comp(6-danbets.size(), tuobets.size());
		}
	};
	

	private KlpkPlayType(String typeName, Integer prize,Integer lineLimit) {
		this.typeName = typeName;
		this.prize = prize;
		this.lineLimit=lineLimit;
	}

	/** 类型名称 */
	private final String typeName;

	/** 类型名称 */
	private final Integer prize;
	
	private final Integer lineLimit;

//	public abstract KlpkWinUnits calcWinUnit(Integer[] results, List<Integer> bets, int multiple);
//
//	public abstract KlpkWinUnits calcWinUnit(Integer[] results, List<Integer> bets1, List<Integer> bets2,
//			int multiple);
	
	public abstract KlpkWinUnits calcWinUnit(Integer[] results, KlpkCompoundContent klpkCompoundContent,int multiple);

//	public abstract KlpkWinUnits calcWinUnit(Integer[] results, List<Integer> bets1, List<Integer> bets2,
//			List<Integer> bets3, int multiple);

	public abstract Integer countUnit(List<String> bets);
	
	public abstract Integer countDanUnit(List<String> danbets,List<String>tuobets);

	public abstract Integer countUnit(List<String> bets1, List<String> bets2);

	public abstract Integer countUnit(List<String> bets1, List<String> bets2, List<String> bets3);

	public static KlpkPlayType getKlpkPlayType(String typeName) {
		for (KlpkPlayType type : KlpkPlayType.values()) {
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
	/**
	 * 计算命中指定数的组合数
	 * 
	 * @param maxNums 最大命中数
	 * @param needHits 须命中数
	 * @param danSelected 胆码
	 * @param danHits 胆码命中数
	 * @param selected 拖码
	 * @param hits 拖码命中数
	 * @return 符合要求的组合数
	 */
	public static Integer calHit(int maxNums, int needHits, int danSelected, int danHits, int selected, int hits) {
		if (danHits > needHits) {
			return 0;
		}

		int needUnHits = maxNums - needHits;  // 需要不中的数目
		int danUnHits = danSelected - danHits; // 胆码不中的数目
		if(danUnHits > needUnHits)
			return 0;

		int tuomaNeedNums = maxNums - danSelected;// 需要的拖码数
		int unHits = selected - hits;// 不中的拖码数
		int tuomaNeedHits = needHits - danHits;// 拖码需要命中的数目
		int tuomaNeedUnHits = tuomaNeedNums - tuomaNeedHits;// 拖码需要不中的数目
		return MathUtils.comp(tuomaNeedHits, hits) * MathUtils.comp(tuomaNeedUnHits, unHits);
		
	}  
	private static final Comparator<Integer> MY_LIST_COMPARATOR = new Comparator<Integer>() {
		@Override
		public int compare(Integer o1, Integer o2) {
			return o1.compareTo(o2);
		}
	};
	public static void main(String[] args) {
		System.out.println(calHit(5,5,4,3,5,2));
		int hao1 = 2;
		int hao2 = 4;
		int hao3 = 1;
		List<Integer> list = Lists.newArrayList();
		list.add(hao1);
		list.add(hao2);
		list.add(hao3);
		Collections.sort(list, MY_LIST_COMPARATOR);
		System.out.println(list.get(0)+1==list.get(1)&&list.get(1)+1==list.get(2));
	}
	public Integer getLineLimit() {
		return lineLimit;
	}

}
