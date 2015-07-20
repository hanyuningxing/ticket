package com.cai310.lottery.support.el11to5;

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
public enum El11to5PlayType {
	
	
	/** 选一数投 */
	/**
	 * 从11个号码中任选1个号码。1/11的中奖机会，奖金13元
	 */
	NormalOne("选一", Integer.valueOf("13"),1) {
		public El11to5WinUnits calcWinUnit(Integer[] results, El11to5CompoundContent el11to5CompoundContent, int multiple) {
			El11to5WinUnits el11to5WinUnits = new El11to5WinUnits();
			int resultNum1 = results[0];
			List<Integer> bets1= NumUtils.toIntegerList(el11to5CompoundContent.getBetList());
			if (bets1.contains(resultNum1)) {
				el11to5WinUnits.addNormalOneWinUnits(multiple);
			}
			return el11to5WinUnits;
		}
//		public El11to5WinUnits calcWinUnit(Integer[] results, List<Integer> bets, int multiple) {
//			El11to5WinUnits el11to5WinUnits = new El11to5WinUnits();
//			Integer resultsNum1 = results[0];
//			if (bets.contains(resultsNum1)) {
//				el11to5WinUnits.addNormalOneWinUnits(multiple);
//			}
//			return el11to5WinUnits;
//		}
//
//		public El11to5WinUnits calcWinUnit(Integer[] results, List<Integer> bets1, List<Integer> bets2, int multiple) {
//			throw new RuntimeException("此方法对本玩法不适用！");
//		}
//
//		public El11to5WinUnits calcWinUnit(Integer[] results, List<Integer> bets1, List<Integer> bets2,
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
	/** 选二任选 */
	/**
	 * 从11个号码中任选2个号码。1/5.5的中奖机会，奖金6元。
	 */
	RandomTwo("选二", Integer.valueOf("6"),2) {
		public El11to5WinUnits calcWinUnit(Integer[] results, El11to5CompoundContent el11to5CompoundContent, int multiple) {
			El11to5WinUnits el11to5WinUnits = new El11to5WinUnits();
			int danHits=0;
			int hits=0;
			int danSelected=0;
			int selected=0;
			if(el11to5CompoundContent.getBetDanList()!=null&&!el11to5CompoundContent.getBetDanList().isEmpty()){
				List<Integer> bets= NumUtils.toIntegerList(el11to5CompoundContent.getBetDanList());
				danSelected=bets.size();
				for (Integer result : results) {
					if (bets.contains(result)) {
						danHits++;
					}
				}
			}
			List<Integer> bets= NumUtils.toIntegerList(el11to5CompoundContent.getBetList());
			for (Integer result : results) {
				if (bets.contains(result)) {
					hits++;
				}
				selected=bets.size();
			}
			el11to5WinUnits.addRandomTwoWinUnits(calHit(2,2,danSelected,danHits,selected,hits)*multiple);
			return el11to5WinUnits;
		}

//		public El11to5WinUnits calcWinUnit(Integer[] results, List<Integer> bets1, List<Integer> bets2, int multiple) {
//			throw new RuntimeException("此方法对本玩法不适用！");
//		}
//
//		public El11to5WinUnits calcWinUnit(Integer[] results, List<Integer> bets1, List<Integer> bets2,
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
	/** 前二组选 */
	/**
	 * 任意2个号码组成1注有效注。1/55的中奖机会，奖金65元。
	 */
	ForeTwoGroup("前二组选", Integer.valueOf("65"),2) {
		public El11to5WinUnits calcWinUnit(Integer[] results, El11to5CompoundContent el11to5CompoundContent, int multiple) {
			El11to5WinUnits el11to5WinUnits = new El11to5WinUnits();
			int danHits=0;
			int hits=0;
			int danSelected=0;
			int selected=0;
			int resultNum1 = results[0];
			int resultNum2 = results[1];
			if(el11to5CompoundContent.getBetDanList()!=null&&!el11to5CompoundContent.getBetDanList().isEmpty()){
				List<Integer> bets= NumUtils.toIntegerList(el11to5CompoundContent.getBetDanList());
				danSelected=bets.size();
				for (Integer result : bets) {
					if (result==resultNum1||result==resultNum2) {
						danHits++;
					}
				}
			}
			List<Integer> bets= NumUtils.toIntegerList(el11to5CompoundContent.getBetList());
			for (Integer result : bets) {
				if (result==resultNum1||result==resultNum2) {
					hits++;
				}
			}
			selected=bets.size();
			el11to5WinUnits.addForeTwoGroupWinUnits(calHit(2,2,danSelected,danHits,selected,hits)*multiple);
			return el11to5WinUnits;
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
			return MathUtils.comp(2-danbets.size(), tuobets.size());
		}
	},
	/** 前二直选 */
	/**
	 * 每行各选1个号码。1/110的中奖机会，奖金130元。
	 */
	ForeTwoDirect("前二直选", Integer.valueOf("130"),1) {
		
		public El11to5WinUnits calcWinUnit(Integer[] results, El11to5CompoundContent el11to5CompoundContent, int multiple) {
			El11to5WinUnits el11to5WinUnits = new El11to5WinUnits();
			int resultNum1 = results[0];
			int resultNum2 = results[1];
			List<Integer> bets1= NumUtils.toIntegerList(el11to5CompoundContent.getBet1List());
			List<Integer> bets2= NumUtils.toIntegerList(el11to5CompoundContent.getBet2List());
			if (bets1.contains(resultNum1) && bets2.contains(resultNum2)) {
				el11to5WinUnits.addForeTwoDirectWinUnits(multiple);
			}
			return el11to5WinUnits;
		}

//		public El11to5WinUnits calcWinUnit(Integer[] results, List<Integer> bets1, List<Integer> bets2, int multiple) {
//			El11to5WinUnits el11to5WinUnits = new El11to5WinUnits();
//			Integer resultNum1 = results[0];
//			Integer resultNum2 = results[1];
//			if (bets1.contains(resultNum1) && bets2.contains(resultNum2)) {
//				el11to5WinUnits.addForeTwoDirectWinUnits(multiple);
//			}
//			return el11to5WinUnits;
//		}
//
//		public El11to5WinUnits calcWinUnit(Integer[] results, List<Integer> bets1, List<Integer> bets2,
//				List<Integer> bets3, int multiple) {
//			throw new RuntimeException("此方法对本玩法不适用！");
//		}

		public Integer countUnit(List<String> bets) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		public Integer countUnit(List<String> bets1, List<String> bets2) {
			Set<String> betSet = new HashSet<String>();
			Integer betUnits = 0;
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
	 * 从11个号码中任选3个号码。1/16.5的中奖机会，奖金19元。
	 */
	RandomThree("任选三 ", Integer.valueOf("19"),3) {
		public El11to5WinUnits calcWinUnit(Integer[] results, El11to5CompoundContent el11to5CompoundContent, int multiple) {
			El11to5WinUnits el11to5WinUnits = new El11to5WinUnits();
			int danHits=0;
			int hits=0;
			int danSelected=0;
			int selected=0;
			if(el11to5CompoundContent.getBetDanList()!=null&&!el11to5CompoundContent.getBetDanList().isEmpty()){
				List<Integer> bets= NumUtils.toIntegerList(el11to5CompoundContent.getBetDanList());
				danSelected=bets.size();
				for (Integer result : results) {
					if (bets.contains(result)) {
						danHits++;
					}
				}
			}
			List<Integer> bets= NumUtils.toIntegerList(el11to5CompoundContent.getBetList());
			for (Integer result : results) {
				if (bets.contains(result)) {
					hits++;
				}
				selected=bets.size();
			}
			el11to5WinUnits.addRandomThreeWinUnits(calHit(3,3,danSelected,danHits,selected,hits)*multiple);
			return el11to5WinUnits;
		}
		
//		public El11to5WinUnits calcWinUnit(Integer[] results, List<Integer> bets, int multiple) {
//			El11to5WinUnits el11to5WinUnits = new El11to5WinUnits();
//			Integer count = 0;
//			for (Integer result : results) {
//				if (bets.contains(result)) {
//					count++;
//				}
//			}
//			if (count >= 3) {
//				el11to5WinUnits.addRandomThreeWinUnits(MathUtils.comp(3, count) * multiple);
//			}
//			return el11to5WinUnits;
//		}
//
//		public El11to5WinUnits calcWinUnit(Integer[] results, List<Integer> bets1, List<Integer> bets2, int multiple) {
//			throw new RuntimeException("此方法对本玩法不适用！");
//		}
//
//		public El11to5WinUnits calcWinUnit(Integer[] results, List<Integer> bets1, List<Integer> bets2,
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
	/** 选三前组 */
	/**
	 * 任选3个号码组成一注有效注。1/165的中奖机会，奖金195元。
	 */
	ForeThreeGroup("选三前组", Integer.valueOf("195"),3) {
		public El11to5WinUnits calcWinUnit(Integer[] results, El11to5CompoundContent el11to5CompoundContent, int multiple) {
			El11to5WinUnits el11to5WinUnits = new El11to5WinUnits();
			int danHits=0;
			int hits=0;
			int danSelected=0;
			int selected=0;
			int resultNum1 = results[0];
			int resultNum2 = results[1];
			int resultNum3 = results[2];
			if(el11to5CompoundContent.getBetDanList()!=null&&!el11to5CompoundContent.getBetDanList().isEmpty()){
				List<Integer> bets= NumUtils.toIntegerList(el11to5CompoundContent.getBetDanList());
				danSelected=bets.size();
				for (Integer result : bets) {
					if (result==resultNum1||result==resultNum2||result==resultNum3) {
						danHits++;
					}
				}
			}
			List<Integer> bets= NumUtils.toIntegerList(el11to5CompoundContent.getBetList());
			for (Integer result : bets) {
				if (result==resultNum1||result==resultNum2||result==resultNum3) {
					hits++;
				}
			}
			selected=bets.size();
			el11to5WinUnits.addForeThreeGroupWinUnits(calHit(3,3,danSelected,danHits,selected,hits)*multiple);
			return el11to5WinUnits;
		}
		
//		public El11to5WinUnits calcWinUnit(Integer[] results, List<Integer> bets, int multiple) {
//			El11to5WinUnits el11to5WinUnits = new El11to5WinUnits();
//			Integer resultNum1 = results[0];
//			Integer resultNum2 = results[1];
//			Integer resultNum3 = results[2];
//			if (bets.contains(resultNum1) && bets.contains(resultNum2) && bets.contains(resultNum3)) {
//				el11to5WinUnits.addForeThreeGroupWinUnits(multiple);
//			}
//			return el11to5WinUnits;
//		}
//
//		public El11to5WinUnits calcWinUnit(Integer[] results, List<Integer> bets1, List<Integer> bets2, int multiple) {
//			throw new RuntimeException("此方法对本玩法不适用！");
//		}
//
//		public El11to5WinUnits calcWinUnit(Integer[] results, List<Integer> bets1, List<Integer> bets2,
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
	/** 选三前直 */
	/**
	 *每行各选1号码。1/990的中奖机会，奖金1170元。
	 */
	ForeThreeDirect("选三前直", Integer.valueOf("1170"),1) {
		public El11to5WinUnits calcWinUnit(Integer[] results, El11to5CompoundContent el11to5CompoundContent, int multiple) {
			El11to5WinUnits el11to5WinUnits = new El11to5WinUnits();
			int resultNum1 = results[0];
			int resultNum2 = results[1];
			int resultNum3 = results[2];
			List<Integer> bets1= NumUtils.toIntegerList(el11to5CompoundContent.getBet1List());
			List<Integer> bets2= NumUtils.toIntegerList(el11to5CompoundContent.getBet2List());
			List<Integer> bets3= NumUtils.toIntegerList(el11to5CompoundContent.getBet3List());
			if (bets1.contains(resultNum1) && bets2.contains(resultNum2) && bets3.contains(resultNum3)) {
				el11to5WinUnits.addForeThreeDirectWinUnits(multiple);
			}
			return el11to5WinUnits;
		}
//		public El11to5WinUnits calcWinUnit(Integer[] results, List<Integer> bets, int multiple) {
//			throw new RuntimeException("此方法对本玩法不适用！");
//		}
//
//		public El11to5WinUnits calcWinUnit(Integer[] results, List<Integer> bets1, List<Integer> bets2, int multiple) {
//			throw new RuntimeException("此方法对本玩法不适用！");
//		}
//
//		public El11to5WinUnits calcWinUnit(Integer[] results, List<Integer> bets1, List<Integer> bets2,
//				List<Integer> bets3, int multiple) {
//			El11to5WinUnits el11to5WinUnits = new El11to5WinUnits();
//			Integer resultNum1 = results[0];
//			Integer resultNum2 = results[1];
//			Integer resultNum3 = results[2];
//			if (bets1.contains(resultNum1) && bets2.contains(resultNum2) && bets3.contains(resultNum3)) {
//				el11to5WinUnits.addForeThreeDirectWinUnits(multiple);
//			}
//			return el11to5WinUnits;
//		}

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
	 * 从11个号码中任选4个号码，1/66的中奖机会，奖金78元
	 */
	RandomFour("任选四 ", Integer.valueOf("78"),4) {
		public El11to5WinUnits calcWinUnit(Integer[] results, El11to5CompoundContent el11to5CompoundContent, int multiple) {
			El11to5WinUnits el11to5WinUnits = new El11to5WinUnits();
			int danHits=0;
			int hits=0;
			int danSelected=0;
			int selected=0;
			if(el11to5CompoundContent.getBetDanList()!=null&&!el11to5CompoundContent.getBetDanList().isEmpty()){
				List<Integer> bets= NumUtils.toIntegerList(el11to5CompoundContent.getBetDanList());
				danSelected=bets.size();
				for (Integer result : results) {
					if (bets.contains(result)) {
						danHits++;
					}
				}
			}
			List<Integer> bets= NumUtils.toIntegerList(el11to5CompoundContent.getBetList());
			for (Integer result : results) {
				if (bets.contains(result)) {
					hits++;
				}
				selected=bets.size();
			}
			el11to5WinUnits.addRandomFourWinUnits(calHit(4,4,danSelected,danHits,selected,hits)*multiple);
			return el11to5WinUnits;
		}
//		public El11to5WinUnits calcWinUnit(Integer[] results, List<Integer> bets, int multiple) {
//			El11to5WinUnits el11to5WinUnits = new El11to5WinUnits();
//			Integer count = 0;
//			for (Integer result : results) {
//				if (bets.contains(result)) {
//					count++;
//				}
//			}
//			if (count >= 4) {
//				el11to5WinUnits.addRandomFourWinUnits(MathUtils.comp(4, count) * multiple);
//			}
//			return el11to5WinUnits;
//		}
//
//		public El11to5WinUnits calcWinUnit(Integer[] results, List<Integer> bets1, List<Integer> bets2, int multiple) {
//			throw new RuntimeException("此方法对本玩法不适用！");
//		}
//
//		public El11to5WinUnits calcWinUnit(Integer[] results, List<Integer> bets1, List<Integer> bets2,
//				List<Integer> bets3, int multiple) {
//			throw new RuntimeException("此方法对本玩法不适用！");
//		}

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
	 *从11个号码中任选5个号码，1/462的中奖机会，奖金540元
	 */
	RandomFive("任选五", Integer.valueOf("540"),5) {
		public El11to5WinUnits calcWinUnit(Integer[] results, El11to5CompoundContent el11to5CompoundContent, int multiple) {
			El11to5WinUnits el11to5WinUnits = new El11to5WinUnits();
			int danHits=0;
			int hits=0;
			int danSelected=0;
			int selected=0;
			if(el11to5CompoundContent.getBetDanList()!=null&&!el11to5CompoundContent.getBetDanList().isEmpty()){
				List<Integer> bets= NumUtils.toIntegerList(el11to5CompoundContent.getBetDanList());
				danSelected=bets.size();
				for (Integer result : results) {
					if (bets.contains(result)) {
						danHits++;
					}
				}
			}
			List<Integer> bets= NumUtils.toIntegerList(el11to5CompoundContent.getBetList());
			for (Integer result : results) {
				if (bets.contains(result)) {
					hits++;
				}
				selected=bets.size();
			}
			el11to5WinUnits.addRandomFiveWinUnits(calHit(5,5,danSelected,danHits,selected,hits)*multiple);
			return el11to5WinUnits;
		}
//		public El11to5WinUnits calcWinUnit(Integer[] results, List<Integer> bets, int multiple) {
//			El11to5WinUnits el11to5WinUnits = new El11to5WinUnits();
//			Integer count = 0;
//			for (Integer result : results) {
//				if (bets.contains(result)) {
//					count++;
//				}
//			}
//			if (count >= 5) {
//				el11to5WinUnits.addRandomFiveWinUnits(MathUtils.comp(5, count) * multiple);
//			}
//			return el11to5WinUnits;
//		}
//
//		public El11to5WinUnits calcWinUnit(Integer[] results, List<Integer> bets1, List<Integer> bets2, int multiple) {
//			throw new RuntimeException("此方法对本玩法不适用！");
//		}
//
//		public El11to5WinUnits calcWinUnit(Integer[] results, List<Integer> bets1, List<Integer> bets2,
//				List<Integer> bets3, int multiple) {
//			throw new RuntimeException("此方法对本玩法不适用！");
//		}

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
	/** 任选六中五 */
	/**
	 *从11个号码中任选6个号码，1/77的中奖机会，奖金90元。
	 */
	RandomSix("任选六", Integer.valueOf("90"),6) {
		public El11to5WinUnits calcWinUnit(Integer[] results, El11to5CompoundContent el11to5CompoundContent, int multiple) {
			El11to5WinUnits el11to5WinUnits = new El11to5WinUnits();
			int danHits=0;
			int hits=0;
			int danSelected=0;
			int selected=0;
			if(el11to5CompoundContent.getBetDanList()!=null&&!el11to5CompoundContent.getBetDanList().isEmpty()){
				List<Integer> bets= NumUtils.toIntegerList(el11to5CompoundContent.getBetDanList());
				danSelected=bets.size();
				for (Integer result : results) {
					if (bets.contains(result)) {
						danHits++;
					}
				}
			}
			List<Integer> bets= NumUtils.toIntegerList(el11to5CompoundContent.getBetList());
			for (Integer result : results) {
				if (bets.contains(result)) {
					hits++;
				}
				selected=bets.size();
			}
			el11to5WinUnits.addRandomSixWinUnits(calHit(6,5,danSelected,danHits,selected,hits)*multiple);
			return el11to5WinUnits;
		}
//		
//		public El11to5WinUnits calcWinUnit(Integer[] results, List<Integer> bets, int multiple) {
//			El11to5WinUnits el11to5WinUnits = new El11to5WinUnits();
//			Integer count = 0;
//			for (Integer result : results) {
//				if (bets.contains(result)) {
//					count++;
//				}
//			}
//			if (count >= 5) {
//				el11to5WinUnits.addRandomSixWinUnits(MathUtils.comp(5, count) * multiple);
//			}
//			return el11to5WinUnits;
//		}
//
//		public El11to5WinUnits calcWinUnit(Integer[] results, List<Integer> bets1, List<Integer> bets2, int multiple) {
//			throw new RuntimeException("此方法对本玩法不适用！");
//		}
//
//		public El11to5WinUnits calcWinUnit(Integer[] results, List<Integer> bets1, List<Integer> bets2,
//				List<Integer> bets3, int multiple) {
//			throw new RuntimeException("此方法对本玩法不适用！");
//		}

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
	},
	/** 任选七中五 */
	/**
	 *从11个号码中任选7个号码，1/22的中奖机会，奖金26元。
	 */
	RandomSeven("任选七", Integer.valueOf("26"),7) {
		public El11to5WinUnits calcWinUnit(Integer[] results, El11to5CompoundContent el11to5CompoundContent, int multiple) {
			El11to5WinUnits el11to5WinUnits = new El11to5WinUnits();
			int danHits=0;
			int hits=0;
			int danSelected=0;
			int selected=0;
			if(el11to5CompoundContent.getBetDanList()!=null&&!el11to5CompoundContent.getBetDanList().isEmpty()){
				List<Integer> bets= NumUtils.toIntegerList(el11to5CompoundContent.getBetDanList());
				danSelected=bets.size();
				for (Integer result : results) {
					if (bets.contains(result)) {
						danHits++;
					}
				}
			}
			List<Integer> bets= NumUtils.toIntegerList(el11to5CompoundContent.getBetList());
			for (Integer result : results) {
				if (bets.contains(result)) {
					hits++;
				}
				selected=bets.size();
			}
			el11to5WinUnits.addRandomSevenWinUnits(calHit(7,5,danSelected,danHits,selected,hits)*multiple);
			return el11to5WinUnits;
		}
//		public El11to5WinUnits calcWinUnit(Integer[] results, List<Integer> bets, int multiple) {
//			El11to5WinUnits el11to5WinUnits = new El11to5WinUnits();
//			Integer count = 0;
//			for (Integer result : results) {
//				if (bets.contains(result)) {
//					count++;
//				}
//			}
//			if (count >= 5) {
//				el11to5WinUnits.addRandomSevenWinUnits(MathUtils.comp(5, count) * multiple);
//			}
//			return el11to5WinUnits;
//		}
//
//		public El11to5WinUnits calcWinUnit(Integer[] results, List<Integer> bets1, List<Integer> bets2, int multiple) {
//			throw new RuntimeException("此方法对本玩法不适用！");
//		}
//
//		public El11to5WinUnits calcWinUnit(Integer[] results, List<Integer> bets1, List<Integer> bets2,
//				List<Integer> bets3, int multiple) {
//			throw new RuntimeException("此方法对本玩法不适用！");
//		}

		public Integer countUnit(List<String> bets) {
			return MathUtils.comp(7, bets.size());
		}

		public Integer countUnit(List<String> bets1, List<String> bets2) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		public Integer countUnit(List<String> bets1, List<String> bets2, List<String> bets3) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		@Override
		public Integer countDanUnit(List<String> danbets, List<String> tuobets) {
			return MathUtils.comp(7-danbets.size(), tuobets.size());
		}
	},
	/** 任选八中五 */
	/**
	 *从11个号码中任选8个号码，1/8.25的中奖机会，奖金9元。
	 */
	RandomEight("任选八", Integer.valueOf("9"),8) {
		public El11to5WinUnits calcWinUnit(Integer[] results, El11to5CompoundContent el11to5CompoundContent, int multiple) {
			El11to5WinUnits el11to5WinUnits = new El11to5WinUnits();
			int danHits=0;
			int hits=0;
			int danSelected=0;
			int selected=0;
			if(el11to5CompoundContent.getBetDanList()!=null&&!el11to5CompoundContent.getBetDanList().isEmpty()){
				List<Integer> bets= NumUtils.toIntegerList(el11to5CompoundContent.getBetDanList());
				danSelected=bets.size();
				for (Integer result : results) {
					if (bets.contains(result)) {
						danHits++;
					}
				}
			}
			List<Integer> bets= NumUtils.toIntegerList(el11to5CompoundContent.getBetList());
			for (Integer result : results) {
				if (bets.contains(result)) {
					hits++;
				}
				selected=bets.size();
			}
			el11to5WinUnits.addRandomEightWinUnits(calHit(8,5,danSelected,danHits,selected,hits)*multiple);
			return el11to5WinUnits;
		}
//		public El11to5WinUnits calcWinUnit(Integer[] results, List<Integer> bets, int multiple) {
//			El11to5WinUnits el11to5WinUnits = new El11to5WinUnits();
//			Integer count = 0;
//			for (Integer result : results) {
//				if (bets.contains(result)) {
//					count++;
//				}
//			}
//			if (count >= 5) {
//				el11to5WinUnits.addRandomEightWinUnits(MathUtils.comp(5, count) * multiple);
//			}
//			return el11to5WinUnits;
//		}
//
//		public El11to5WinUnits calcWinUnit(Integer[] results, List<Integer> bets1, List<Integer> bets2, int multiple) {
//			throw new RuntimeException("此方法对本玩法不适用！");
//		}
//
//		public El11to5WinUnits calcWinUnit(Integer[] results, List<Integer> bets1, List<Integer> bets2,
//				List<Integer> bets3, int multiple) {
//			throw new RuntimeException("此方法对本玩法不适用！");
//		}

		public Integer countUnit(List<String> bets) {
			return MathUtils.comp(8, bets.size());
		}

		public Integer countUnit(List<String> bets1, List<String> bets2) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		public Integer countUnit(List<String> bets1, List<String> bets2, List<String> bets3) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		@Override
		public Integer countDanUnit(List<String> danbets, List<String> tuobets) {
			return MathUtils.comp(8-danbets.size(), tuobets.size());
		}
	};

	private El11to5PlayType(String typeName, Integer prize,Integer lineLimit) {
		this.typeName = typeName;
		this.prize = prize;
		this.lineLimit=lineLimit;
	}

	/** 类型名称 */
	private final String typeName;

	/** 类型名称 */
	private final Integer prize;
	
	private final Integer lineLimit;
  
//	public abstract El11to5WinUnits calcWinUnit(Integer[] results, List<Integer> bets, int multiple);
//
//	public abstract El11to5WinUnits calcWinUnit(Integer[] results, List<Integer> bets1, List<Integer> bets2,
//			int multiple);
	
	public abstract El11to5WinUnits calcWinUnit(Integer[] results, El11to5CompoundContent el11to5CompoundContent,int multiple);

//	public abstract El11to5WinUnits calcWinUnit(Integer[] results, List<Integer> bets1, List<Integer> bets2,
//			List<Integer> bets3, int multiple);

	public abstract Integer countUnit(List<String> bets);
	
	public abstract Integer countDanUnit(List<String> danbets,List<String>tuobets);

	public abstract Integer countUnit(List<String> bets1, List<String> bets2);

	public abstract Integer countUnit(List<String> bets1, List<String> bets2, List<String> bets3);

	public static El11to5PlayType getEl11to5PlayType(String typeName) {
		for (El11to5PlayType type : El11to5PlayType.values()) {
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
	public Integer getLineLimit() {
		return lineLimit;
	}
  
}
