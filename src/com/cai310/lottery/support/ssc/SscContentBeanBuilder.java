package com.cai310.lottery.support.ssc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.Constant;
import com.cai310.lottery.PlConstant;
import com.cai310.lottery.QyhConstant;
import com.cai310.lottery.SscConstant;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.ContentBean;
import com.cai310.lottery.support.pl.PlPlayType;
import com.cai310.utils.MathUtils;

/**
 * ContentBean构建器
 * 
 */
public class SscContentBeanBuilder {
	/**
	 * ContentBean复式构建器
	 * 
	 * @param schemeContent
	 *            方案内容
	 * @return {@link com.cai310.lottery.support.ContentBean}
	 * @throws DataException
	 */
	public static ContentBean buildCompoundContentBean(String schemeContent, SscPlayType playType) throws DataException {
		// /复式方案内容注数：
		if (StringUtils.isBlank(schemeContent))
			throw new DataException("方案内容为空.");

		int allUnits = 0;
		List<SscCompoundContent> all = new ArrayList<SscCompoundContent>(10);

		String[] arr = schemeContent.split("(\r\n|\n)+");

		List<String> area1List = new ArrayList<String>(); // 万
		List<String> area2List = new ArrayList<String>(); // 千
		List<String> area3List = new ArrayList<String>(); // 百
		List<String> area4List = new ArrayList<String>(); // 十
		List<String> area5List = new ArrayList<String>(); // 个
		/** 三星组三 单复式 号码 */
		List<String> group3List = new ArrayList<String>();
		/** 三星组六 复式 号码 */
		List<String> group6List = new ArrayList<String>();
		/** 两星组选 号码 */
		List<String> groupTwoList = new ArrayList<String>();
		/** 2星直选和值 、3星直选和值 */
		List<String> directSumList = new ArrayList<String>();
		/** 2星组选和值 、3星组选和值 */
		List<String> groupSumList = new ArrayList<String>();
		/** 大小双单 托码 */
		List<String> betList = new ArrayList<String>();
		/** 3星组三胆拖、3星组六胆拖、2星组三胆拖、2星组六胆拖 胆码 **/
		List<String> betDanList = new ArrayList<String>();

		for (String str : arr) {
			area1List.clear();
			area2List.clear();
			area3List.clear();
			area4List.clear();
			area5List.clear();

			group3List.clear();
			group6List.clear();
			
			groupTwoList.clear();

			directSumList.clear();
			groupSumList.clear();
			
			betList.clear();
			betDanList.clear();

			Integer countUnits = 0;
			Pattern patt = null;
			Matcher matcher = null;
			SscCompoundContent lineContent = new SscCompoundContent();
			//五星直选 、五星通选
			if (playType.equals((SscPlayType.DirectFive)) || playType.equals((SscPlayType.AllFive))) {
				//直选
				//复式格式 1,2,3,4-1,2,3,4-1,2,3,4
				patt = Pattern.compile(SscConstant.DirectFive_COMPOUND_REGEX);
				matcher = patt.matcher(str);
				if (!matcher.matches())
					throw new DataException("方案内容格式不正确.");
				String unitsStr = matcher.group(1);
				int units = Integer.parseInt(unitsStr);
				String betStr = matcher.group(2).trim();
				String[] betArr = betStr.split(SscConstant.SEPARATOR_FOR_);

				area1List = Arrays.asList(betArr[0].split(SscConstant.SEPARATOR_FOR_NUMBER));// 万位
				area2List = Arrays.asList(betArr[1].split(SscConstant.SEPARATOR_FOR_NUMBER));// 千位
				area3List = Arrays.asList(betArr[2].split(SscConstant.SEPARATOR_FOR_NUMBER));// 百位
				area4List = Arrays.asList(betArr[3].split(SscConstant.SEPARATOR_FOR_NUMBER));// 十位
				area5List = Arrays.asList(betArr[4].split(SscConstant.SEPARATOR_FOR_NUMBER));// 各位

				Collections.sort(area1List);
				Collections.sort(area2List);
				Collections.sort(area3List);
				Collections.sort(area4List);
				Collections.sort(area5List);
				for (String num : area1List) {
					num = num.trim();
					int number = Integer.parseInt(num);
					if (number < 0 || number > 9)
						throw new DataException("方案内容不正确,号码在[00-09]之间.");
				}
				for (String num : area2List) {
					num = num.trim();
					int number = Integer.parseInt(num);
					if (number < 0 || number > 9)
						throw new DataException("方案内容不正确,号码在[00-09]之间.");
				}
				for (String num : area3List) {
					num = num.trim();
					int number = Integer.parseInt(num);
					if (number < 0 || number > 9)
						throw new DataException("方案内容不正确,号码在[00-09]之间.");
				}

				for (String num : area4List) {
					num = num.trim();
					int number = Integer.parseInt(num);
					if (number < 0 || number > 9)
						throw new DataException("方案内容不正确,号码在[00-09]之间.");
				}
				for (String num : area5List) {
					num = num.trim();
					int number = Integer.parseInt(num);
					if (number < 0 || number > 9)
						throw new DataException("方案内容不正确,号码在[00-09]之间.");
				}

				countUnits = area1List.size() * area2List.size() * area3List.size() * area4List.size()
						* area5List.size();
				if (units != countUnits)
					throw new DataException("注数与系统计算的不一致.");
				lineContent.setUnits(units);

			} 
			//三星直选
			else if (playType.equals(SscPlayType.DirectThree)) {
				patt = Pattern.compile(SscConstant.DirectThree_COMPOUND_REGEX);
				matcher = patt.matcher(str);
				if (!matcher.matches())
					throw new DataException("方案内容格式不正确.");
				String unitsStr = matcher.group(1);
				int units = Integer.parseInt(unitsStr);
				String betStr = matcher.group(2).trim();
				String[] betArr = betStr.split(SscConstant.SEPARATOR_FOR_);

				area3List = Arrays.asList(betArr[0].split(SscConstant.SEPARATOR_FOR_NUMBER));// 百位
				area4List = Arrays.asList(betArr[1].split(SscConstant.SEPARATOR_FOR_NUMBER));// 十位
				area5List = Arrays.asList(betArr[2].split(SscConstant.SEPARATOR_FOR_NUMBER));// 各位

				Collections.sort(area3List);
				Collections.sort(area4List);
				Collections.sort(area5List);
				for (String num : area3List) {
					num = num.trim();
					int number = Integer.parseInt(num);
					if (number < 0 || number > 9)
						throw new DataException("方案内容不正确,号码在[00-09]之间.");
				}
				for (String num : area4List) {
					num = num.trim();
					int number = Integer.parseInt(num);
					if (number < 0 || number > 9)
						throw new DataException("方案内容不正确,号码在[00-09]之间.");
				}
				for (String num : area5List) {
					num = num.trim();
					int number = Integer.parseInt(num);
					if (number < 0 || number > 9)
						throw new DataException("方案内容不正确,号码在[00-09]之间.");
				}

				countUnits = area3List.size() * area4List.size() * area5List.size();
				if (units != countUnits)
					throw new DataException("注数与系统计算的不一致.");
				lineContent.setUnits(units);
			} 
			
			//两星直选
			else if (playType.equals(SscPlayType.DirectTwo)) {
				patt = Pattern.compile(SscConstant.DirectTwo_COMPOUND_REGEX);
				matcher = patt.matcher(str);
				if (!matcher.matches())
					throw new DataException("方案内容格式不正确.");
				String unitsStr = matcher.group(1);
				int units = Integer.parseInt(unitsStr);
				String betStr = matcher.group(2).trim();
				String[] betArr = betStr.split(SscConstant.SEPARATOR_FOR_);

				area4List = Arrays.asList(betArr[0].split(SscConstant.SEPARATOR_FOR_NUMBER));// 十位
				area5List = Arrays.asList(betArr[1].split(SscConstant.SEPARATOR_FOR_NUMBER));// 个位

				Collections.sort(area4List);
				Collections.sort(area5List);
				for (String num : area4List) {
					num = num.trim();
					int number = Integer.parseInt(num);
					if (number < 0 || number > 9)
						throw new DataException("方案内容不正确,号码在[00-09]之间.");
				}
				for (String num : area5List) {
					num = num.trim();
					int number = Integer.parseInt(num);
					if (number < 0 || number > 9)
						throw new DataException("方案内容不正确,号码在[00-09]之间.");
				}

				countUnits = area4List.size() * area5List.size();
				if (units != countUnits)
					throw new DataException("注数与系统计算的不一致.");

				lineContent.setUnits(units);
			} 
			
			//一星直选
			else if (playType.equals(SscPlayType.DirectOne)) {
				patt = Pattern.compile(SscConstant.DirectOne_COMPOUND_REGEX);
				matcher = patt.matcher(str);
				if (!matcher.matches())
					throw new DataException("方案内容格式不正确.");
				String unitsStr = matcher.group(1);
				int units = Integer.parseInt(unitsStr);
				String betStr = matcher.group(2).trim();
				String[] betArr = betStr.split(SscConstant.SEPARATOR_FOR_);

				area5List = Arrays.asList(betArr[0].split(SscConstant.SEPARATOR_FOR_NUMBER));
				Collections.sort(area5List);
				for (String num : area5List) {
					num = num.trim();
					int number = Integer.parseInt(num);
					if (number < 0 || number > 9)
						throw new DataException("方案内容不正确,号码在[00-09]之间.");
				}
				countUnits = area5List.size();
				if (units != countUnits)
					throw new DataException("注数与系统计算的不一致.");

				lineContent.setUnits(units);
			}
			//三星 组三
			else if ((playType.equals(SscPlayType.ThreeGroup3))) {
				// //组选3
				// /复式格式 1,2,3,4
				boolean hasDan = false;// 是否为含胆码格式
				if (str.indexOf(SscConstant.SEPARATOR_DAN_FOR_NUMBER) >= 0) {
					patt = Pattern.compile(SscConstant.GENERAL_COMPOUND_DAN_REGEX);
					hasDan = true;
				} else {
					patt = Pattern.compile(SscConstant.Three_GROUP3_6_COMPOUND_REGEX);
				}
				matcher = patt.matcher(str);
				if (!matcher.matches())
					throw new DataException("方案内容格式不正确.");
				String unitsStr = matcher.group(1);
				int units = Integer.parseInt(unitsStr);
				lineContent.setUnits(units);
				String betStr = matcher.group(2).trim();

				if (hasDan) {
					String[] tempArr = betStr.split(SscConstant.SEPARATOR_DAN_FOR_NUMBER);
					// 胆码
					betDanList = Arrays.asList(tempArr[0].split(SscConstant.SEPARATOR_FOR_NUMBER));
					for (String num : betDanList) {
						num = num.trim();
						int number = Integer.parseInt(num);
						if (number < 0 || number > 9)
							throw new DataException("方案内容不正确,号码在[00-09]之间.");
					}
					// 拖码
					group3List = Arrays.asList(tempArr[1].split(SscConstant.SEPARATOR_FOR_NUMBER));
					for (String num : group3List) {
						num = num.trim();
						int number = Integer.parseInt(num);
						if (number < 0 || number > 9)
							throw new DataException("方案内容不正确,号码在[00-09]之间.");
					}
					countUnits = playType.countDanUnit(betDanList, group3List);
				} else {

					group3List = Arrays.asList(betStr.split(SscConstant.SEPARATOR_FOR_NUMBER));
					for (String num : group3List) {
						num = num.trim();
						int number = Integer.parseInt(num);
						if (number < 0 || number > 9)
							throw new DataException("方案内容不正确,号码在[00-09]之间.");
					}
					Collections.sort(group3List);
					countUnits = group3List.size() * (group3List.size() - 1);
				}
				if (units != countUnits) {
					throw new DataException("注数与系统计算的不一致.");
				}

			} 
			
			//三星组六
			else if (playType.equals(SscPlayType.ThreeGroup6)) {
				boolean hasDan = false;// 是否为含胆码格式
				if (str.indexOf(SscConstant.SEPARATOR_DAN_FOR_NUMBER) >= 0) {
					patt = Pattern.compile(SscConstant.GENERAL_COMPOUND_DAN_REGEX);
					hasDan = true;
				} else {
					patt = Pattern.compile(SscConstant.Three_GROUP3_6_COMPOUND_REGEX);
				}
				matcher = patt.matcher(str);
				if (!matcher.matches())
					throw new DataException("方案内容格式不正确.");
				String unitsStr = matcher.group(1);
				int units = Integer.parseInt(unitsStr);
				lineContent.setUnits(units);
				String betStr = matcher.group(2).trim();
				if (hasDan) {
					String[] tempArr = betStr.split(SscConstant.SEPARATOR_DAN_FOR_NUMBER);
					// 胆码
					betDanList = Arrays.asList(tempArr[0].split(SscConstant.SEPARATOR_FOR_NUMBER));
					for (String num : betDanList) {
						num = num.trim();
						int number = Integer.parseInt(num);
						if (number < 0 || number > 9)
							throw new DataException("方案内容不正确,号码在[00-09]之间.");
					}
					// 拖码
					group6List = Arrays.asList(tempArr[1].split(SscConstant.SEPARATOR_FOR_NUMBER));
					for (String num : group6List) {
						num = num.trim();
						int number = Integer.parseInt(num);
						if (number < 0 || number > 9)
							throw new DataException("方案内容不正确,号码在[00-09]之间.");
					}
					countUnits = playType.countDanUnit(betDanList, group6List);
				} else {
					group6List = Arrays.asList(betStr.split(SscConstant.SEPARATOR_FOR_NUMBER));
					Collections.sort(group6List);
					countUnits = MathUtils.comp(3, group6List.size());
					if (units != countUnits)
						throw new DataException("注数与系统计算的不一致.");
					for (String num : group6List) {
						num = num.trim();
						int number = Integer.parseInt(num);
						if (number < 0 || number > 9)
							throw new DataException("方案内容不正确,号码在[00-09]之间.");
					}
				}
			} 
			//三星直选 和值
			else if (playType.equals(playType.DirectThreeSum)) {
				// 三星直选和值
				patt = Pattern.compile(SscConstant.Three_DIRECT_GROUP_SUM_COMPOUND_REGEX);
				matcher = patt.matcher(str);
				if (!matcher.matches())
					throw new DataException("方案内容格式不正确.");
				String unitsStr = matcher.group(1);
				int units = Integer.parseInt(unitsStr);
				lineContent.setUnits(units);
				String betStr = matcher.group(2).trim();
				directSumList = Arrays.asList(betStr.split(SscConstant.SEPARATOR_FOR_NUMBER));
				Collections.sort(directSumList);
				for (String num : directSumList) {
					num = num.trim();
					int number = Integer.parseInt(num);
					if (number < 0 || number > 27)
						throw new DataException("方案内容不正确,号码在[00-27]之间.");
					countUnits += SscConstant.UNITS_DIRECT_THREE_SUM[number];
				}
				if (units != countUnits)
					throw new DataException("注数与系统计算的不一致.");
			} 
			//三星组选和值
			else if (playType.equals(playType.GroupThreeSum)) {
				// 三星组选和值
				patt = Pattern.compile(SscConstant.Three_DIRECT_GROUP_SUM_COMPOUND_REGEX);
				matcher = patt.matcher(str);
				if (!matcher.matches())
					throw new DataException("方案内容格式不正确.");
				String unitsStr = matcher.group(1);
				int units = Integer.parseInt(unitsStr);
				lineContent.setUnits(units);
				String betStr = matcher.group(2).trim();
				groupSumList = Arrays.asList(betStr.split(SscConstant.SEPARATOR_FOR_NUMBER));
				Collections.sort(groupSumList);
				for (String num : groupSumList) {
					num = num.trim();
					int number = Integer.parseInt(num);
					if (number < 0 || number > 27)
						throw new DataException("方案内容不正确,号码在[00-27]之间.");
					countUnits += SscConstant.UNITS_GROUP_THREE_SUM[number];
				}
				if (units != countUnits)
					throw new DataException("注数与系统计算的不一致.");
			} 
			//两星直选和值
			else if (playType.equals(playType.DirectTwoSum)) {
				// 二星直选和值
				patt = Pattern.compile(SscConstant.Two_DIRECT_GROUP_SUM_COMPOUND_REGEX);
				matcher = patt.matcher(str);
				if (!matcher.matches())
					throw new DataException("方案内容格式不正确.");
				String unitsStr = matcher.group(1);
				int units = Integer.parseInt(unitsStr);
				lineContent.setUnits(units);
				String betStr = matcher.group(2).trim();
				directSumList = Arrays.asList(betStr.split(SscConstant.SEPARATOR_FOR_NUMBER));
				Collections.sort(directSumList);
				for (String num : directSumList) {
					num = num.trim();
					int number = Integer.parseInt(num);
					if (number < 0 || number > 18)
						throw new DataException("方案内容不正确,号码在[00-18]之间.");
					countUnits += SscConstant.UNITS_DIRECT_TWO_SUM[number];
				}
				if (units != countUnits)
					throw new DataException("注数与系统计算的不一致.");
			} 
			//两星组选和值
			else if (playType.equals(playType.GroupTwoSum)) {
				patt = Pattern.compile(SscConstant.Two_DIRECT_GROUP_SUM_COMPOUND_REGEX);
				matcher = patt.matcher(str);
				if (!matcher.matches())
					throw new DataException("方案内容格式不正确.");
				String unitsStr = matcher.group(1);
				int units = Integer.parseInt(unitsStr);
				lineContent.setUnits(units);
				String betStr = matcher.group(2).trim();
				groupSumList = Arrays.asList(betStr.split(PlConstant.SEPARATOR_FOR_NUMBER));
				Collections.sort(groupSumList);
				for (String num : groupSumList) {
					num = num.trim();
					int number = Integer.parseInt(num);
					if (number < 0 || number > 18)
						throw new DataException("方案内容不正确,号码在[01-18]之间.");
					countUnits += SscConstant.UNITS_GROUP_TWO_SUM[number];
				}
				if (units != countUnits)
					throw new DataException("注数与系统计算的不一致.");
			} 
			//大小双单
			else if (playType.equals(playType.BigSmallDoubleSingle)) {
				patt = Pattern.compile(SscConstant.DirectTwo_COMPOUND_REGEX);
				matcher = patt.matcher(str);
				if (!matcher.matches())
					throw new DataException("方案内容格式不正确.");
				String unitsStr = matcher.group(1);
				int units = Integer.parseInt(unitsStr);
				String betStr = matcher.group(2).trim();
				String[] betArr = betStr.split(SscConstant.SEPARATOR_FOR_);

				area4List = Arrays.asList(betArr[0].split(SscConstant.SEPARATOR_FOR_NUMBER));
				area5List = Arrays.asList(betArr[1].split(SscConstant.SEPARATOR_FOR_NUMBER));

				Collections.sort(area4List);
				Collections.sort(area5List);
				for (String num : area4List) {
					num = num.trim();
					int number = Integer.parseInt(num);
					if (number < 0 || number > 9)
						throw new DataException("方案内容不正确,号码在[00-09]之间.");
				}
				for (String num : area5List) {
					num = num.trim();
					int number = Integer.parseInt(num);
					if (number < 0 || number > 9)
						throw new DataException("方案内容不正确,号码在[00-09]之间.");
				}

				countUnits = area4List.size() * area5List.size();
				if (units != countUnits)
					throw new DataException("注数与系统计算的不一致.");
				lineContent.setUnits(units);
			}
			//两星组选
			else if (playType.equals(playType.GroupTwo)) {
				boolean hasDan = false;// 是否为含胆码格式
				if (str.indexOf(SscConstant.SEPARATOR_DAN_FOR_NUMBER) >= 0) {
					patt = Pattern.compile(SscConstant.GENERAL_COMPOUND_DAN_REGEX);
					hasDan = true;
				} else {
					patt = Pattern.compile(SscConstant.Two_DIRECT_GROUP_SUM_COMPOUND_REGEX);
				}
				// 两星组选
				matcher = patt.matcher(str);
				if (!matcher.matches())
					throw new DataException("方案内容格式不正确.");
				String unitsStr = matcher.group(1);
				int units = Integer.parseInt(unitsStr);
				lineContent.setUnits(units);
				String betStr = matcher.group(2).trim();

				if (hasDan) {
					String[] tempArr = betStr.split(SscConstant.SEPARATOR_DAN_FOR_NUMBER);
					// 胆码
					betDanList = Arrays.asList(tempArr[0].split(SscConstant.SEPARATOR_FOR_NUMBER));
					for (String num : betDanList) {
						num = num.trim();
						int number = Integer.parseInt(num);
						if (number < 0 || number > 9)
							throw new DataException("方案内容不正确,号码在[00-09]之间.");
					}
					// 拖码
					groupTwoList = Arrays.asList(tempArr[1].split(SscConstant.SEPARATOR_FOR_NUMBER));
					for (String num : groupTwoList) {
						num = num.trim();
						int number = Integer.parseInt(num);
						if (number < 0 || number > 9)
							throw new DataException("方案内容不正确,号码在[00-09]之间.");
					}
					countUnits = playType.countDanUnit(betDanList, groupTwoList);
				} else {
					groupTwoList = Arrays.asList(betStr.split(SscConstant.SEPARATOR_FOR_NUMBER));
					Collections.sort(groupTwoList);
					for (String num : groupTwoList) {
						num = num.trim();
						int number = Integer.parseInt(num);
						if (number < 0 || number > 9)
							throw new DataException("方案内容不正确,号码在[00-09]之间.");
					}
					countUnits = SscConstant.UNITS_GROUP_TWO[groupTwoList.size()];
				}
				if (units != countUnits)
					throw new DataException("注数与系统计算的不一致.");
			}

			else {
				throw new DataException("玩法不正确.");
			}

			lineContent.setArea1List(area1List);
			lineContent.setArea2List(area2List);
			lineContent.setArea3List(area3List);
			lineContent.setArea4List(area4List);
			lineContent.setArea5List(area5List);
			
			lineContent.setGroup3List(group3List);
			lineContent.setGroup6List(group6List);
			
			lineContent.setGroupSumList(groupSumList);
			lineContent.setDirectSumList(directSumList);
			
			lineContent.setGroupTwoList(groupTwoList);
			lineContent.setBetDanList(betDanList);
			
			lineContent.setUnits(countUnits);
			allUnits += countUnits;
			all.add(lineContent);

			area1List = new ArrayList<String>();
			area2List = new ArrayList<String>();
			area3List = new ArrayList<String>();
			area4List = new ArrayList<String>();
			area5List = new ArrayList<String>();
			
			group3List = new ArrayList<String>();
			group6List = new ArrayList<String>();
			
			directSumList = new ArrayList<String>();
			groupSumList = new ArrayList<String>();
			
			groupTwoList = new ArrayList<String>();
			betDanList = new ArrayList<String>();

		}
		return new ContentBean(allUnits, JSONArray.fromObject(all).toString());
	}

	/**
	 * ContentBean单式构建器   //时时彩全部复试持久化  出票 抽取以复试形式的单式
	 * 
	 * @param schemeContent
	 *            方案内容
	 * @return {@link com.cai310.lottery.support.ContentBean}
	 * @throws DataException
	 */
	public static ContentBean buildSingleContentBean(String schemeContent, SscPlayType playType) throws DataException {
		if (StringUtils.isBlank(schemeContent))
			throw new DataException("方案内容为空.");
		if (playType.equals(SscPlayType.DirectThreeSum) || playType.equals(SscPlayType.DirectTwoSum)
				|| playType.equals(SscPlayType.GroupThreeSum) || playType.equals(SscPlayType.GroupTwoSum)) {
			throw new DataException("该投注方式不支持单式投注");
		}
		int allUnits = 0;
		List<String> all = new ArrayList<String>(10);

		String[] arr = schemeContent.split("(\r\n|\n)+");
		StringBuilder line = new StringBuilder(25);
		List<String> numList = new ArrayList<String>();
		for (String str : arr) {
			Pattern patt = null;
			if (playType.equals(SscPlayType.DirectFive)) {
				patt = Pattern.compile(SscConstant.DirectFive_SINGLE_REGEX);
			} else if (playType.equals(SscPlayType.DirectThree)) {
				patt = Pattern.compile(SscConstant.DirectThree_SINGLE_REGEX);
			} else if (playType.equals(SscPlayType.DirectTwo)) {
				patt = Pattern.compile(SscConstant.DirectTwo_SINGLE_REGEX);
			} else if (playType.equals(SscPlayType.DirectOne)) {
				patt = Pattern.compile(SscConstant.DirectOne_SINGLE_REGEX);
			} else {
				throw new DataException("该投注方式不支持单式投注");
			}

			Matcher matcher = patt.matcher(str);
			if (!matcher.matches())
				throw new DataException("方案内容格式不正确.");

			line.setLength(0);
			numList.clear();
			String[] numArr = matcher.group(1).split("\\D");
			for (String num : numArr) {
				num = num.trim();
				int number = Integer.parseInt(num);
				if (number < 0 || number > 9)
					throw new DataException("方案内容不正确,号码在[00-09]之间.");
				numList.add("" + number);
			}
			Integer num1 = Integer.valueOf(numList.get(0));
			Integer num2 = Integer.valueOf(numList.get(1));
			Integer num3 = Integer.valueOf(numList.get(2));
			if (playType.equals(SscPlayType.DirectFive)) {
				if (numList.size() != 5)
					throw new DataException("投注数目不对.");
			} else if (playType.equals(SscPlayType.DirectThree)) {
				if (numList.size() != 3)
					throw new DataException("投注数目不对.");
			} else if (playType.equals(SscPlayType.DirectTwo)) {
				if (numList.size() != 2)
					throw new DataException("投注数目不对.");
			} else if (playType.equals(SscPlayType.DirectOne)) {
				if (numList.size() != 1)
					throw new DataException("投注数目不对.");
			} else if (playType.equals(SscPlayType.ThreeGroup3)) {
				// //组选3
				if (num1.equals(num2) && num2.equals(num3)) {
					// /三个数相同
					throw new DataException("组选三方案内容错误");
				} else if (!num1.equals(num2) && !num2.equals(num3) && !num1.equals(num3)) {
					// /三个不相同不中奖
					throw new DataException("组选三方案内容错误");
				}
				if (numList.size() != 3)
					throw new DataException("投注数目不对.");
			} else if (playType.equals(SscPlayType.ThreeGroup6)) {
				// //组选6
				if (num1.equals(num2) || num2.equals(num3) || num1.equals(num3)) {
					// /三个有两个是一样的不中奖
					throw new DataException("组选六方案内容错误");
				}
				if (numList.size() != 3)
					throw new DataException("投注数目不对.");
			} else if (playType.equals(SscPlayType.GroupTwo)) {
				// //二星 组选
				if (num1.equals(num2)) {
					throw new DataException("组选六方案内容错误");
				}
				if (numList.size() != 2)
					throw new DataException("投注数目不对.");
			} else {
				throw new DataException("投注方式错误");
			}
			line.append(StringUtils.join(numList, Constant.SINGLE_SEPARATOR_FOR_NUMBER));
			allUnits++;
			all.add(line.toString());
		}

		return new ContentBean(allUnits, StringUtils.join(all, "\r\n"));
	}
}
