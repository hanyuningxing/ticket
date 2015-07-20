package com.cai310.lottery.support.pl;

import java.util.List;

import com.cai310.lottery.Constant;
import com.cai310.lottery.PlConstant;

/**
 * 投注方式.<br/>
 * 注：添加类型只能在后面添加，不能插入中间
 * 
 */
public enum PlPlayType {
	/** 直选 */
	P5Direct("排五直选") {
		public PlWinUnit calcWinUnit(List<Integer> results, List<Integer> area1List, List<Integer> area2List,
				List<Integer> area3List, List<Integer> area4List, List<Integer> area5List, int multiple) {
			PlWinUnit plWinUnit = new PlWinUnit();
			Integer num1 = results.get(0);
			Integer num2 = results.get(1);
			Integer num3 = results.get(2);
			Integer num4 = results.get(3);
			Integer num5 = results.get(4);

			if (area1List.contains(num1) && area2List.contains(num2) && area3List.contains(num3)
					&& area4List.contains(num4) && area5List.contains(num5)) {
				plWinUnit.addP5WinUnits(multiple);
			}
			return plWinUnit;
		}

		public PlWinUnit calcWinUnit(List<Integer> results, List<Integer> area1List, List<Integer> area2List,
				List<Integer> area3List, int multiple) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		public PlWinUnit calcWinUnit(List<Integer> results, List<Integer> group3List, int multiple) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}
	},
	P3Direct("排三直选") {
		public PlWinUnit calcWinUnit(List<Integer> results, List<Integer> area1List, List<Integer> area2List,
				List<Integer> area3List, int multiple) {
			PlWinUnit plWinUnit = new PlWinUnit();
			Integer num1 = results.get(0);
			Integer num2 = results.get(1);
			Integer num3 = results.get(2);
			if (area1List.contains(num1) && area2List.contains(num2) && area3List.contains(num3)) {
				plWinUnit.addP3WinUnits(multiple);
			}
			return plWinUnit;
		}

		public PlWinUnit calcWinUnit(List<Integer> results, List<Integer> area1List, List<Integer> area2List,
				List<Integer> area3List, List<Integer> area4List, List<Integer> area5List, int multiple) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		public PlWinUnit calcWinUnit(List<Integer> results, List<Integer> group3List, int multiple) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}
	},
	/** 组三 */
	Group3("排三组三") {
		public PlWinUnit calcWinUnit(List<Integer> results, List<Integer> group3List, int multiple) {
			// //组选3
			Integer num1 = results.get(0);
			Integer num2 = results.get(1);
			Integer num3 = results.get(2);
			PlWinUnit plWinUnit = new PlWinUnit();
			if (num1.equals(num2) && num2.equals(num3)) {
				// /三个数相同不中奖
				return plWinUnit;
			} else if (!num1.equals(num2) && !num2.equals(num3) && !num1.equals(num3)) {
				// /三个不相同不中奖
				return plWinUnit;
			}
			if (group3List.contains(num1) && group3List.contains(num2) && group3List.contains(num3)) {
				plWinUnit.addP3G3WinUnits(multiple);
			}
			return plWinUnit;
		}

		public PlWinUnit calcWinUnit(List<Integer> results, List<Integer> area1List, List<Integer> area2List,
				List<Integer> area3List, List<Integer> area4List, List<Integer> area5List, int multiple) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		public PlWinUnit calcWinUnit(List<Integer> results, List<Integer> area1List, List<Integer> area2List,
				List<Integer> area3List, int multiple) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}
	},
	/** 组六 */
	Group6("排三组六") {
		public PlWinUnit calcWinUnit(List<Integer> results, List<Integer> group6List, int multiple) {
			// //组选6
			Integer num1 = results.get(0);
			Integer num2 = results.get(1);
			Integer num3 = results.get(2);
			PlWinUnit plWinUnit = new PlWinUnit();
			// //组选6
			if (num1.equals(num2) || num2.equals(num3) || num1.equals(num3)) {
				// /三个有两个是一样的不中奖
				return plWinUnit;
			}
			if (group6List.contains(num1) && group6List.contains(num2) && group6List.contains(num3)) {
				plWinUnit.addP3G6WinUnits(multiple);
			}
			return plWinUnit;
		}

		public PlWinUnit calcWinUnit(List<Integer> results, List<Integer> area1List, List<Integer> area2List,
				List<Integer> area3List, List<Integer> area4List, List<Integer> area5List, int multiple) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		public PlWinUnit calcWinUnit(List<Integer> results, List<Integer> area1List, List<Integer> area2List,
				List<Integer> area3List, int multiple) {
			return null;
		}
	},
	/** 直选和值 */
	DirectSum("排三直选和值") {
		public PlWinUnit calcWinUnit(List<Integer> results, List<Integer> directSumList, int multiple) {
			PlWinUnit plWinUnit = new PlWinUnit();
			Integer num1 = results.get(0);
			Integer num2 = results.get(1);
			Integer num3 = results.get(2);
			Integer sum = num1 + num2 + num3;
			if (directSumList.contains(sum)) {
				for (int i = 0; i < PlConstant.UNITS_DIRECT_SUM.length; i++) {
					if (sum == i) {
						plWinUnit.addP3WinUnits(multiple);
					}
				}
			}
			return plWinUnit;
		}

		public PlWinUnit calcWinUnit(List<Integer> results, List<Integer> area1List, List<Integer> area2List,
				List<Integer> area3List, List<Integer> area4List, List<Integer> area5List, int multiple) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		public PlWinUnit calcWinUnit(List<Integer> results, List<Integer> area1List, List<Integer> area2List,
				List<Integer> area3List, int multiple) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}
	},
	/** 组选和值 */
	GroupSum("排三组选和值") {
		public PlWinUnit calcWinUnit(List<Integer> results, List<Integer> groupSumList, int multiple) {
			PlWinUnit plWinUnit = new PlWinUnit();
			Integer num1 = results.get(0);
			Integer num2 = results.get(1);
			Integer num3 = results.get(2);
			Integer sum = num1 + num2 + num3;
			if (groupSumList.contains(sum)) {
				for (int i = 0; i < PlConstant.UNITS_GROUP_SUM.length; i++) {
					if (sum == i) {
						if (!num1.equals(num2) && !num2.equals(num3) && !num1.equals(num3)) {
							// /三个不相同组六
								plWinUnit.addP3G6WinUnits(multiple);
						}else if(num1.equals(num2) && num2.equals(num3) && num1.equals(num3)){
							/// 豹子不中
						}else {
							// /三个不相同组3
							plWinUnit.addP3G3WinUnits(multiple);
						}
					}
				}
			}
			return plWinUnit;
		}

		public PlWinUnit calcWinUnit(List<Integer> results, List<Integer> area1List, List<Integer> area2List,
				List<Integer> area3List, List<Integer> area4List, List<Integer> area5List, int multiple) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		public PlWinUnit calcWinUnit(List<Integer> results, List<Integer> area1List, List<Integer> area2List,
				List<Integer> area3List, int multiple) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}
	},
	/** 包串 */
	BaoChuan("包串") {
		public PlWinUnit calcWinUnit(List<Integer> results, List<Integer> baoChuanList, int multiple) {
			PlWinUnit plWinUnit = new PlWinUnit();
			Integer num1 = results.get(0);
			Integer num2 = results.get(1);
			Integer num3 = results.get(2);
			if (baoChuanList.contains(num1)&&baoChuanList.contains(num2)&&baoChuanList.contains(num3)) {
			       //中奖，中一注直选，
				   plWinUnit.addP3WinUnits(multiple);
			}
			return plWinUnit;
		}

		public PlWinUnit calcWinUnit(List<Integer> results, List<Integer> area1List, List<Integer> area2List,
				List<Integer> area3List, List<Integer> area4List, List<Integer> area5List, int multiple) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		public PlWinUnit calcWinUnit(List<Integer> results, List<Integer> area1List, List<Integer> area2List,
				List<Integer> area3List, int multiple) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}
	},
	/** 直选跨度  */
	P3DirectKuadu("直选跨度") {
		public PlWinUnit calcWinUnit(List<Integer> results, List<Integer>  kuaduList, int multiple) {
			PlWinUnit plWinUnit = new PlWinUnit();
			Integer num1 = results.get(0);
			Integer num2 = results.get(1);
			Integer num3 = results.get(2);
			Integer bigTemp = Math.max(num1, num2);//算出最大的
			Integer bigNum = Math.max(bigTemp, num3);//算出最大的
			Integer smallTemp = Math.min(num1, num2);//算出最小的
			Integer smallNum = Math.min(smallTemp, num3);//算出最小的
			Integer kuadu = bigNum - smallNum ;
			if (kuaduList.contains(kuadu)) {
			      //中奖，中一注直选
				  plWinUnit.addP3WinUnits(multiple);
			}
			return plWinUnit;
		}

		public PlWinUnit calcWinUnit(List<Integer> results, List<Integer> area1List, List<Integer> area2List,
				List<Integer> area3List, List<Integer> area4List, List<Integer> area5List, int multiple) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		public PlWinUnit calcWinUnit(List<Integer> results, List<Integer> area1List, List<Integer> area2List,
				List<Integer> area3List, int multiple) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}
	},
	/** 组选3跨度  */
	P3Group3Kuadu("组选3跨度") {
		public PlWinUnit calcWinUnit(List<Integer> results, List<Integer>  kuaduList, int multiple) {
			PlWinUnit plWinUnit = new PlWinUnit();
			Integer num1 = results.get(0);
			Integer num2 = results.get(1);
			Integer num3 = results.get(2);
			Integer bigTemp = Math.max(num1, num2);//算出最大的
			Integer bigNum = Math.max(bigTemp, num3);//算出最大的
			Integer smallTemp = Math.min(num1, num2);//算出最小的
			Integer smallNum = Math.min(smallTemp, num3);//算出最小的
			Integer kuadu = bigNum - smallNum ;
			if (kuaduList.contains(kuadu)) {
			      //中奖，中一注组选3
				   if (!num1.equals(num2) && !num2.equals(num3) && !num1.equals(num3)) {
						// /三个不相同组六
				   } else if(num1.equals(num2) && num2.equals(num3) && num1.equals(num3)){
						// /豹子不中组三组六
				   } else {
						// /三个不相同组3
					   plWinUnit.addP3G3WinUnits(multiple);
				   }
			}
			return plWinUnit;
		}

		public PlWinUnit calcWinUnit(List<Integer> results, List<Integer> area1List, List<Integer> area2List,
				List<Integer> area3List, List<Integer> area4List, List<Integer> area5List, int multiple) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		public PlWinUnit calcWinUnit(List<Integer> results, List<Integer> area1List, List<Integer> area2List,
				List<Integer> area3List, int multiple) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}
	},
	/** 组选6跨度  */
	P3Group6Kuadu("组选6跨度") {
		public PlWinUnit calcWinUnit(List<Integer> results, List<Integer>  kuaduList, int multiple) {
			PlWinUnit plWinUnit = new PlWinUnit();
			Integer num1 = results.get(0);
			Integer num2 = results.get(1);
			Integer num3 = results.get(2);
			Integer bigTemp = Math.max(num1, num2);//算出最大的
			Integer bigNum = Math.max(bigTemp, num3);//算出最大的
			Integer smallTemp = Math.min(num1, num2);//算出最小的
			Integer smallNum = Math.min(smallTemp, num3);//算出最小的
			Integer kuadu = bigNum - smallNum ;
			if (kuaduList.contains(kuadu)) {
			      //中奖，中一注组选6
				if (!num1.equals(num2) && !num2.equals(num3) && !num1.equals(num3)) {
					plWinUnit.addP3G6WinUnits(multiple);
					// /三个不相同组六
			   } else if(num1.equals(num2) && num2.equals(num3) && num1.equals(num3)){
					// /豹子不中组三组六
			   } else {
					// /三个不相同组3
			   }
			}
			return plWinUnit;
		}

		public PlWinUnit calcWinUnit(List<Integer> results, List<Integer> area1List, List<Integer> area2List,
				List<Integer> area3List, List<Integer> area4List, List<Integer> area5List, int multiple) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		public PlWinUnit calcWinUnit(List<Integer> results, List<Integer> area1List, List<Integer> area2List,
				List<Integer> area3List, int multiple) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}
	};
	/** 类型名称 */
	private final String typeName;

	/**
	 * @param typeName {@link #typeName}
	 */
	private PlPlayType(String typeName) {
		this.typeName = typeName;
	}

	/**
	 * @return {@link #typeName}
	 */
	public String getTypeName() {
		return typeName;
	}

	public abstract PlWinUnit calcWinUnit(List<Integer> results, List<Integer> bets, int multiple);

	public abstract PlWinUnit calcWinUnit(List<Integer> results, List<Integer> bet1, List<Integer> bet2,
			List<Integer> bet3, int multiple);

	public abstract PlWinUnit calcWinUnit(List<Integer> results, List<Integer> bet1, List<Integer> bet2,
			List<Integer> bet3, List<Integer> bet4, List<Integer> bet5, int multiple);
	
	public static final String SQL_TYPE = Constant.ENUM_DEFAULT_SQL_TYPE;
}
