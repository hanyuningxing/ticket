package com.cai310.lottery.support.ssc;

import java.util.ArrayList;
import java.util.List;

import com.cai310.lottery.Constant;
import com.cai310.utils.MathUtils;
import com.cai310.utils.NumUtils;

/**
 * 投注方式.<br/>
 * 注：添加类型只能在后面添加，不能插入中间
 * 
 */
public enum SscPlayType {
	/** 一星直选 [0]
	 * 玩法说明：从个位选择1个或多个号码，选号与开奖号码个位一致即中奖10元！
	 * */
	DirectOne("一星直选",Integer.valueOf("10"),1) {
		@Override
		public SscWinUnits calcWinUnit(Integer[] results, SscCompoundContent compoundContent, int multiple) {
			SscWinUnits sscWinUnits = new SscWinUnits();
			int resultNum1 = results[4];// 个位
			List<Integer> area5 = NumUtils.toIntegerList(compoundContent.getArea5List());
			if (area5.contains(resultNum1)) {
				sscWinUnits.addDirectOneWinUnits(multiple);
			}
			return sscWinUnits;
		}

		@Override
		public Integer countUnit(SscCompoundContent compoundContent) {
			return compoundContent.getArea5List().size();
		}

		@Override
		public Integer countDanUnit(List<String> danbets, List<String> tuobets) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}
	},
	
	
	/** 二星直选[1]
	 * 猜开奖号码的最后两位，分别选择十位和个位的1个或多个号码投注，奖金100元
	 **/
	DirectTwo("二星直选",Integer.valueOf("100"),2) {
		@Override
		public SscWinUnits calcWinUnit(Integer[] results, SscCompoundContent compoundContent, int multiple) {
			SscWinUnits sscWinUnits = new SscWinUnits();
			int resultNum5 = results[4];// 个位
			int resultNum4 = results[3];// 十位
			List<Integer> area5 = NumUtils.toIntegerList(compoundContent.getArea5List());
			List<Integer> area4 = NumUtils.toIntegerList(compoundContent.getArea4List());

			if (area5.contains(resultNum5) && area4.contains(resultNum4)) {
				sscWinUnits.addDirectTwoWinUnits(multiple);
			}
			return sscWinUnits;
		}

		@Override
		public Integer countUnit(SscCompoundContent compoundContent) {
			return compoundContent.getArea4List().size() * compoundContent.getArea5List().size();
		}

		@Override
		public Integer countDanUnit(List<String> danbets, List<String> tuobets) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}
	},

	/** 三星直选[2]
	 * 竞猜开奖号码的后面三位，分别选择百位、十位和个位的1个或多个号码投注，奖金1000元
	 **/
	DirectThree("三星直选",Integer.valueOf("1000"),3) {
		@Override
		public SscWinUnits calcWinUnit(Integer[] results, SscCompoundContent compoundContent, int multiple) {
			SscWinUnits sscWinUnits = new SscWinUnits();
			int resultNum5 = results[4];// 个位
			int resultNum4 = results[3];// 十位
			int resultNum3 = results[2];// 百位

			List<Integer> area5 = NumUtils.toIntegerList(compoundContent.getArea5List());
			List<Integer> area4 = NumUtils.toIntegerList(compoundContent.getArea4List());
			List<Integer> area3 = NumUtils.toIntegerList(compoundContent.getArea3List());

			if (area5.contains(resultNum5) && area4.contains(resultNum4) && area3.contains(resultNum3)) {
				sscWinUnits.addDirectThreeWinUnits(multiple);
			}
			return sscWinUnits;
		}

		@Override
		public Integer countUnit(SscCompoundContent compoundContent) {
			return compoundContent.getArea3List().size() * compoundContent.getArea4List().size()
					* compoundContent.getArea5List().size();
		}

		@Override
		public Integer countDanUnit(List<String> danbets, List<String> tuobets) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}
	},

	
	/** 五星直选[3]
	 * 竞猜开奖号码的全部五位，分别选择每位的1个或多个号码投注，奖金100000元
	 **/
	DirectFive("五星直选",Integer.valueOf("100000"),5) {
		@Override
		public SscWinUnits calcWinUnit(Integer[] results, SscCompoundContent compoundContent, int multiple) {
			SscWinUnits sscWinUnits = new SscWinUnits();
			int resultNum5 = results[4];// 个位
			int resultNum4 = results[3];// 十位
			int resultNum3 = results[2];// 百位
			int resultNum2 = results[1];// 百位
			int resultNum1 = results[0];// 百位

			List<Integer> area5 = NumUtils.toIntegerList(compoundContent.getArea5List());
			List<Integer> area4 = NumUtils.toIntegerList(compoundContent.getArea4List());
			List<Integer> area3 = NumUtils.toIntegerList(compoundContent.getArea3List());
			List<Integer> area2 = NumUtils.toIntegerList(compoundContent.getArea2List());
			List<Integer> area1 = NumUtils.toIntegerList(compoundContent.getArea1List());

			if (area5.contains(resultNum5) && area4.contains(resultNum4) && area3.contains(resultNum3)
					&& area2.contains(resultNum2) && area1.contains(resultNum1)) {
				sscWinUnits.addDirectFiveWinUnits(multiple);
			}
			return sscWinUnits;
		}

		@Override
		public Integer countUnit(SscCompoundContent compoundContent) {
			return compoundContent.getArea1List().size() * compoundContent.getArea2List().size()
					* compoundContent.getArea3List().size() * compoundContent.getArea4List().size()
					* compoundContent.getArea5List().size();
		}

		@Override
		public Integer countDanUnit(List<String> danbets, List<String> tuobets) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}
	},

	
	
	/** 组三[4]
	 *  三星组三：竞猜开奖号码后三位，请选择2个号码投注（其中1个号码为重号），奖金320元！
	 **/
	ThreeGroup3("组三",Integer.valueOf("320"),2) {
	/** 组三 */
		@Override
		public SscWinUnits calcWinUnit(Integer[] results, SscCompoundContent compoundContent, int multiple) {
			// //组选3
			int resultNum5 = results[4];// 个位
			int resultNum4 = results[3];// 十位
			int resultNum3 = results[2];// 百位

			SscWinUnits sscWinUnits = new SscWinUnits();

			if (resultNum5 == resultNum4 && resultNum4 == resultNum3) {
				// 三个数相同不中奖
				return sscWinUnits;
			} else if (resultNum5 != resultNum4 && resultNum4 != resultNum3 && resultNum5 != resultNum3) {
				// 三个不相同不中奖
				return sscWinUnits;
			}
			// 计算重号
			int repeatNum = resultNum3;
			for (int i = 2; i < results.length; i++) {
				if (repeatNum == results[i]) {
					break;
				} else {
					repeatNum = results[i];
				}
			}

			List<Integer> group3List = NumUtils.toIntegerList(compoundContent.getGroup3List());

			// 重号、单号
			if (null != compoundContent.getBetDanList() && compoundContent.getBetDanList().size() > 0) {
				List<Integer> betDanList = NumUtils.toIntegerList(compoundContent.getBetDanList());
				if (betDanList.contains(repeatNum)&& (group3List.contains(resultNum5)|| group3List.contains(resultNum4) || group3List.contains(resultNum3))) {
					sscWinUnits.addG3WinUnits(multiple);
				}
			} else {
				// 普通
				if (group3List.contains(resultNum5)
						&& group3List.contains(resultNum4)
						&& group3List.contains(resultNum3)) {
					sscWinUnits.addG3WinUnits(multiple);
				}
			}
			return sscWinUnits;
		}

		@Override
		public Integer countUnit(SscCompoundContent compoundContent) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		@Override
		public Integer countDanUnit(List<String> danbets, List<String> tuobets) {
			return MathUtils.comp(3 - danbets.size(), tuobets.size());
		}
	},

	
	
	/** 组六[5]
	 * 竞猜开奖号码后三位，选择3个或以上号码投注，奖金160元 
	 **/
	ThreeGroup6("组六",Integer.valueOf("160"),3) {
		@Override
		public SscWinUnits calcWinUnit(Integer[] results, SscCompoundContent compoundContent, int multiple) {
			// //组选3
			int resultNum5 = results[4];// 个位
			int resultNum4 = results[3];// 十位
			int resultNum3 = results[2];// 百位
			Integer[] group6Results = { resultNum3, resultNum4, resultNum5 };
			SscWinUnits sscWinUnits = new SscWinUnits();
			// //组选6
			if (resultNum5 == resultNum4 || resultNum4 == resultNum3 || resultNum5 == resultNum3) {
				// /三个有两个是一样的不中奖
				return sscWinUnits;
			}
			int danHits = 0;
			int hits = 0;
			int danSelected = 0;
			int selected = 0;
			if (compoundContent.getBetDanList() != null && !compoundContent.getBetDanList().isEmpty()) {
				List<Integer> bets = NumUtils.toIntegerList(compoundContent.getBetDanList());
				danSelected = bets.size();
				for (Integer result : group6Results) {
					if (bets.contains(result)) {
						danHits++;
					}
				} 
			}
			List<Integer> group6List = NumUtils.toIntegerList(compoundContent.getGroup6List());
			for (Integer result : group6Results) {
				if (group6List.contains(result)) {
					hits++;
				}
			}
			selected = group6List.size();
			sscWinUnits.addG6WinUnits(checkWin(danHits, hits, danSelected, selected, 3, 3) * multiple);
			return sscWinUnits;
		}

		@Override
		public Integer countUnit(SscCompoundContent compoundContent) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		@Override
		public Integer countDanUnit(List<String> danbets, List<String> tuobets) {
			return MathUtils.comp(3 - danbets.size(), tuobets.size());
		}
	},
	
	/** 三星直选和值[6]
	 * 竞猜开奖号码后面三位的数字相加之和，选择1个或多个和值号码投注，奖金1000元 
	 **/
	DirectThreeSum("三星直选和值",Integer.valueOf("1000"),1) {
		@Override
		public SscWinUnits calcWinUnit(Integer[] results, SscCompoundContent compoundContent, int multiple) {
			int resultNum5 = results[4];// 个位
			int resultNum4 = results[3];// 十位
			int resultNum3 = results[2];// 百位
			int sum = resultNum5 + resultNum4 + resultNum3;
			SscWinUnits sscWinUnits = new SscWinUnits();
			List<Integer> directSumList = NumUtils.toIntegerList(compoundContent.getDirectSumList());
			if (directSumList.contains(sum)) {
				sscWinUnits.addDirectThreeSumWinUnits(multiple);
			}
			return sscWinUnits;
		}

		@Override
		public Integer countUnit(SscCompoundContent compoundContent) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		@Override
		public Integer countDanUnit(List<String> danbets, List<String> tuobets) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}
	},
	
	
	/** 三星组选和值[7]
	 * 竞猜开奖号码后面三位的数字相加之和，开奖号码为豹子形态奖金1000元;组3形态奖金320元;组6形态奖金160元
	 **/
	GroupThreeSum("三星组选和值",Integer.valueOf("1000"),Integer.valueOf("320"),Integer.valueOf("160"),1) {
	/** 三星组选和值 */
		@Override
		public SscWinUnits calcWinUnit(Integer[] results, SscCompoundContent compoundContent, int multiple) {
			int resultNum5 = results[4];// 个位
			int resultNum4 = results[3];// 十位
			int resultNum3 = results[2];// 百位
			int sum = resultNum5 + resultNum4 + resultNum3;
			SscWinUnits sscWinUnits = new SscWinUnits();
			// 开奖号码为豹子形态奖金1000元;组3形态奖金320元;组6形态奖金160元
			List<Integer> groupSumList = NumUtils.toIntegerList(compoundContent.getGroupSumList());
			if (groupSumList.contains(sum)) {
					if (resultNum5 != resultNum4 && resultNum4 != resultNum3 && resultNum5 != resultNum3) {
						// /三个不相同组六
						sscWinUnits.addGroupThreeSumH3WinUnits(multiple);
					} else if (resultNum5 == resultNum4 && resultNum4 == resultNum3 && resultNum5 == resultNum3) {
						//豹子形态
						sscWinUnits.addGroupThreeSumH1WinUnits(multiple);
					} else {
						// /三个不相同组3
						sscWinUnits.addGroupThreeSumH2WinUnits(multiple);
					}
			}
			return sscWinUnits;
		}

		@Override
		public Integer countUnit(SscCompoundContent compoundContent) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		@Override
		public Integer countDanUnit(List<String> danbets, List<String> tuobets) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}
	},

	
	/** 二星直选和值[8]
	 * 竞猜开奖号码最后两位的数字相加之和，选择1个或多个和值号码投注，奖金100元
	 **/
	DirectTwoSum("二星直选和值",Integer.valueOf("100"),1) {
		@Override
		public SscWinUnits calcWinUnit(Integer[] results, SscCompoundContent compoundContent, int multiple) {
			int resultNum5 = results[4];// 个位
			int resultNum4 = results[3];// 十位
			int sum = resultNum5 + resultNum4;
			SscWinUnits sscWinUnits = new SscWinUnits();
			List<Integer> directSumList = NumUtils.toIntegerList(compoundContent.getDirectSumList());
			if (directSumList.contains(sum)) {
						// 三星直选
			   sscWinUnits.addDirectTwoSumWinUnits(multiple);
			}
			return sscWinUnits;
		}

		@Override
		public Integer countUnit(SscCompoundContent compoundContent) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		@Override
		public Integer countDanUnit(List<String> danbets, List<String> tuobets) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}
	},

	
	/** 二星组选和值
	 * 竞猜开奖号码后面两位的数字相加之和，开奖号码为对子奖金100元;非对子奖金50元
	 **/
	GroupTwoSum("二星组选和值",Integer.valueOf("100"),Integer.valueOf("50"),1) {
		@Override
		public SscWinUnits calcWinUnit(Integer[] results, SscCompoundContent compoundContent, int multiple) {
			int resultNum5 = results[4];// 个位
			int resultNum4 = results[3];// 十位
			int sum = resultNum5 + resultNum4;
			SscWinUnits sscWinUnits = new SscWinUnits();
			List<Integer> groupSumList = NumUtils.toIntegerList(compoundContent.getGroupSumList());
			if (groupSumList.contains(sum)) {
				if (resultNum5 != resultNum4) {
					// 非对子奖金50元
					sscWinUnits.addGroupSumTwoH2WinUnits(multiple);
				} else {
					// /对子奖金100元
					sscWinUnits.addGroupSumTwoH1WinUnits(multiple);
				}
			}
			return sscWinUnits;
		}

		@Override
		public Integer countUnit(SscCompoundContent compoundContent) {
			throw new RuntimeException("此方法对本玩法不适用！");

		}

		@Override
		public Integer countDanUnit(List<String> danbets, List<String> tuobets) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}
	},

	/** 选五任选 */
	/**
	 * 竞猜开奖号码的全部五位，全部猜中奖金20000元；猜中前三或后三奖金200元；猜中前二或后二奖金20元
	 */
	AllFive("五星通选",Integer.valueOf("20000"), Integer.valueOf("200"), Integer.valueOf("20"),5) {
		public SscWinUnits calcWinUnit(Integer[] results, SscCompoundContent compoundContent, int multiple) {
			SscWinUnits sscWinUnits = new SscWinUnits();
			int resultNum5 = results[4];// 个位
			int resultNum4 = results[3];// 十位
			int resultNum3 = results[2];// 百位
			int resultNum2 = results[1];// 千位
			int resultNum1 = results[0];// 万位

			List<Integer> area5 = NumUtils.toIntegerList(compoundContent.getArea5List());
			List<Integer> area4 = NumUtils.toIntegerList(compoundContent.getArea4List());
			List<Integer> area3 = NumUtils.toIntegerList(compoundContent.getArea3List());
			List<Integer> area2 = NumUtils.toIntegerList(compoundContent.getArea2List());
			List<Integer> area1 = NumUtils.toIntegerList(compoundContent.getArea1List());
			
			//首尾中二
			if(area5.contains(resultNum5)&&area4.contains(resultNum4)){
				sscWinUnits.addAllFiveH3WinUnits(multiple*area3.size()*area2.size()*area1.size());
			}
			
			if(area1.contains(resultNum1)&&area2.contains(resultNum2)){
				sscWinUnits.addAllFiveH3WinUnits(multiple*area3.size()*area4.size()*area5.size());
			}
			//首尾中三
			if(area5.contains(resultNum5)&&area4.contains(resultNum4)&&area3.contains(resultNum3)){
				sscWinUnits.addAllFiveH2WinUnits(multiple*area2.size()*area1.size());
			}
			
			if(area1.contains(resultNum1)&&area2.contains(resultNum2)&&area3.contains(resultNum3)){
				sscWinUnits.addAllFiveH2WinUnits(multiple*area4.size()*area5.size());
			}
			//全中
			if(area1.contains(resultNum1)&&area2.contains(resultNum2)&&area3.contains(resultNum3)&&area4.contains(resultNum4)&&area5.contains(resultNum5)){
				sscWinUnits.addAllFiveH1WinUnits(multiple);
			}
			return sscWinUnits;
		}

		@Override
		public Integer countDanUnit(List<String> danbets, List<String> tuobets) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}

		@Override
		public Integer countUnit(SscCompoundContent compoundContent) {
			return compoundContent.getArea5List().size()*compoundContent.getArea4List().size()*compoundContent.getArea3List().size()
					*compoundContent.getArea2List().size()*compoundContent.getArea1List().size();		
			}
	},

	/** 二星组选
	 * 竞猜开奖号码的最后两位，选择2个或以上号码投注，奖金50元(开出对子不算中奖)
	 **/
	GroupTwo("二星组选",Integer.valueOf("50"),2) {
		@Override
		public SscWinUnits calcWinUnit(Integer[] results, SscCompoundContent compoundContent, int multiple) {
			int resultNum5 = results[4];// 个位
			int resultNum4 = results[3];// 十位
			SscWinUnits sscWinUnits = new SscWinUnits();
			if (resultNum5 == resultNum4) {
				return sscWinUnits;
			}
			int danHits = 0;
			int hits = 0;
			int danSelected = 0;
			int selected = 0;
			if (compoundContent.getBetDanList() != null && !compoundContent.getBetDanList().isEmpty()) {
				List<Integer> bets = NumUtils.toIntegerList(compoundContent.getBetDanList());
				danSelected = bets.size();
				if(bets.contains(resultNum5)) {
					danHits++;
				}
				if(bets.contains(resultNum4)) {
					danHits++;
				}
				
			}
			List<Integer> bets = NumUtils.toIntegerList(compoundContent.getGroupTwoList());
			if(bets.contains(resultNum5)) {
				hits++;
			}
			if(bets.contains(resultNum4)) {
				hits++;
			}
			selected = bets.size();
			sscWinUnits.addGroupTwoH1WinUnits(checkWin(danHits, hits, danSelected, selected, 2, 2) * multiple);
			return sscWinUnits;
		}

		@Override
		public Integer countUnit(SscCompoundContent compoundContent) {
			throw new RuntimeException("此方法对本玩法不适用！");

		}

		@Override
		public Integer countDanUnit(List<String> danbets, List<String> tuobets) {
			return MathUtils.comp(2 - danbets.size(), tuobets.size());
		}
	},
	
	
	
	/** 大小双单 2 1 4 5
	 * 	分别从个、十位中任选一种性质组成一注投注号码，奖金4元
	 * 号码0～9中，0～4为小，5～9为大，1、3、5、7、9为单，0、2、4、6、8为双。
	 **/
	BigSmallDoubleSingle("大小单双 ",Integer.valueOf("4"),2) {
		@Override
		public SscWinUnits calcWinUnit(Integer[] results, SscCompoundContent compoundContent, int multiple) {
			int resultNum5 = results[4];// 个位
			int resultNum4 = results[3];// 十位
			SscWinUnits sscWinUnits = new SscWinUnits();
			
			//个位 
			int num5BigOrSmall = 0;
			int num5DoubleOrSingle = 0;
			
			//十位 
			int num4BigOrSmall = 0;
			int num4DoubleOrSingle = 0;
			if(resultNum5<5) {
				num5BigOrSmall = 1;
			} else {
				num5BigOrSmall = 2;
			}
			
			if(0==resultNum5%2) {
				num5DoubleOrSingle = 4;
			} else {
				num5DoubleOrSingle = 5;
			}
			
			if(resultNum4<5) {
				num4BigOrSmall = 1;
			} else {
				num4BigOrSmall = 2;
			}
			
			if(0==resultNum4%2) {
				num4DoubleOrSingle = 4;
			} else {
				num4DoubleOrSingle = 5;
			}

			int area5hit=0;
			int area4hit=0;
			
			List<Integer> area5List = NumUtils.toIntegerList(compoundContent.getArea5List());
			List<Integer> area4List = NumUtils.toIntegerList(compoundContent.getArea4List());

			
		    if (area5List.contains(num5BigOrSmall)){
		    	area5hit++;
			}
		    if (area5List.contains(num5DoubleOrSingle)){
		    	area5hit++;
			}
		    
		    if (area4List.contains(num4BigOrSmall)){
		    	area4hit++;
			}
		    if (area4List.contains(num4DoubleOrSingle)){
		    	area4hit++;
			}
		    if(area5hit*area4hit>0) {
			    for(int i =1;i<=area5hit*area4hit;i++){
				    sscWinUnits.addBigSmallDoubleSingleWinUnits(multiple);
			    }
		    }
			return sscWinUnits;
		}

		@Override
		public Integer countUnit(SscCompoundContent compoundContent) {
			return compoundContent.getArea5List().size()*compoundContent.getArea4List().size();
		}

		@Override
		public Integer countDanUnit(List<String> danbets, List<String> tuobets) {
			throw new RuntimeException("此方法对本玩法不适用！");
		}
	};
	
	/**
	 * 把号码转换成通俗的文字
	 *  大小双单 2 1 4 5
	 * @param bet 号码
	 * @return 对应的通俗文字
	 */
	public String toText(List<String> bet) {
		StringBuffer betText = new StringBuffer();
		List<String> stringText = new ArrayList<String>();
		stringText.add(0, "小");
		stringText.add(1, "大");
		stringText.add(2, "");
		stringText.add(3, "双");
		stringText.add(4, "单");
		for(String index:bet) {
			betText.append(stringText.get(Integer.valueOf(index).intValue()-1));
		}
		return betText.toString();
	}

	
	
	/** 类型名称 */
	private final String typeName;


	public abstract SscWinUnits calcWinUnit(Integer[] results, SscCompoundContent compoundContent, int multiple);

	public abstract Integer countUnit(SscCompoundContent compoundContent);

	public abstract Integer countDanUnit(List<String> danbets, List<String> tuobets);

	public static final String SQL_TYPE = Constant.ENUM_DEFAULT_SQL_TYPE;

	private SscPlayType(String typeName,  Integer prize,Integer lineLimit) {
		this.typeName = typeName;
		this.prize = prize;
		this.lineLimit = lineLimit;
	}

	private SscPlayType(String typeName, Integer prize1, Integer prize2,Integer lineLimit) {
		this(typeName, prize1, lineLimit);
		this.prize2 = prize2;
	}

	private SscPlayType(String typeName, Integer prize1, Integer prize2, Integer prize3, Integer lineLimit) {
		this(typeName, prize1, prize2,lineLimit);
		this.prize3 = prize3;
	}

	/** 一等奖名称 */
	private final Integer prize;
	public Integer getPrize2() {
		return prize2;
	}

	public void setPrize2(Integer prize2) {
		this.prize2 = prize2;
	}

	public Integer getPrize3() {
		return prize3;
	}

	public void setPrize3(Integer prize3) {
		this.prize3 = prize3;
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

	/** 二等奖 **/
	private Integer prize2;
	/** 三等奖 **/
	private Integer prize3;

	private final Integer lineLimit;

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
	public byte getBetTypeValue() {
		return (byte) this.ordinal();
	}
	public static void main(String[] args) {
		List list = new ArrayList();
		list.add("3");
		list.add("7");
		list.add("9");
		list.add("6");

		//3,7,9
		//7,5,7,6,3 
		Integer[] results = {7,5,7,6,3} ;
		
		int resultNum5 = 3;// 个位
		int resultNum4 = 6;// 十位
		int resultNum3 = 7;// 百位
		Integer[] group6Results = { resultNum3, resultNum4, resultNum5 };
		SscWinUnits sscWinUnits = new SscWinUnits();

		int danHits = 0;
		int hits = 0;
		int danSelected = 0;
		int selected = 0;
 
		List<Integer> group6List = NumUtils.toIntegerList(list);
		for (Integer result : group6Results) {
			if (group6List.contains(result)) {
				hits++;
			}
		}
		selected = group6List.size();
		
		System.out.println(">"+checkWin(danHits, hits, danSelected, selected, 3, 3));
		
		sscWinUnits.addG6WinUnits(checkWin(danHits, hits, danSelected, selected, 3, 3) * 1);
		System.out.println(sscWinUnits.getPrize());
	}
	
	
}
