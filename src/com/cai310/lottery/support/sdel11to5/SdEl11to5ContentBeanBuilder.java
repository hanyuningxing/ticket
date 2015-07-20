package com.cai310.lottery.support.sdel11to5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.Constant;
import com.cai310.lottery.SdEl11to5Constant;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.ContentBean;

/**
 * ContentBean构建器
 * 
 */
public class SdEl11to5ContentBeanBuilder {
	/**
	 * ContentBean复式构建器
	 * 
	 * @param schemeContent
	 *            方案内容
	 * @return {@link com.cai310.lottery.support.ContentBean}
	 * @throws DataException
	 */
	public static ContentBean buildCompoundContentBean(String schemeContent, SdEl11to5PlayType sdel11to5PlayType)
			throws DataException {
		// /复式方案内容注数：
		if (StringUtils.isBlank(schemeContent))
			throw new DataException("方案内容为空.");

		int allUnits = 0;
		List<SdEl11to5CompoundContent> all = new ArrayList<SdEl11to5CompoundContent>(10);

		String[] arr = schemeContent.split("(\r\n|\n)+");

		/** 除选二直选，前三直选外投注号码 */
		List<String> betList = new ArrayList<String>();

		/** 选二直选(前二)，前三直选投注号码 */
		List<String> bet1List = new ArrayList<String>();
		List<String> bet2List = new ArrayList<String>();
		List<String> bet3List = new ArrayList<String>();
		for (String str : arr) {
			betList.clear();
			bet1List.clear();
			bet2List.clear();
			bet3List.clear();
			Integer countUnits = 0;
			Pattern patt = null;
			Matcher matcher = null;
			SdEl11to5CompoundContent lineContent = new SdEl11to5CompoundContent();
			if (sdel11to5PlayType.equals(SdEl11to5PlayType.NormalOne)) {
				patt = Pattern.compile(SdEl11to5Constant.GENERAL_COMPOUND_REGEX);
				matcher = patt.matcher(str);
				if (!matcher.matches())
					throw new DataException("方案内容格式不正确.");
				String unitsStr = matcher.group(1);
				int units = Integer.parseInt(unitsStr);
				String betStr = matcher.group(2).trim();
				betList = Arrays.asList(betStr.split(SdEl11to5Constant.SEPARATOR_FOR_NUMBER));
				for (String num : betList) {
					num = num.trim();
					int number = Integer.parseInt(num);
					if (number < 1 || number > 11)
						throw new DataException("方案内容不正确,号码在[01-11]之间.");
				}
				countUnits = sdel11to5PlayType.countUnit(betList);
				if (units != countUnits)
					throw new DataException("注数与系统计算的不一致.");
				lineContent.setBetList(betList);
				lineContent.setUnits(units);
			} else if (sdel11to5PlayType.equals(SdEl11to5PlayType.ForeTwoDirect)) {
				patt = Pattern.compile(SdEl11to5Constant.DIRECT2_COMPOUND_REGEX);
				matcher = patt.matcher(str);
				if (!matcher.matches())
					throw new DataException("方案内容格式不正确.");
				String unitsStr = matcher.group(1);
				int units = Integer.parseInt(unitsStr);
				String betStr = matcher.group(2).trim();
				String[] betArr = betStr.split(SdEl11to5Constant.SEPARATOR_FOR_);
				bet1List = Arrays.asList(betArr[0].split(SdEl11to5Constant.SEPARATOR_FOR_NUMBER));
				bet2List = Arrays.asList(betArr[1].split(SdEl11to5Constant.SEPARATOR_FOR_NUMBER));
				for (String num : bet1List) {
					num = num.trim();
					int number = Integer.parseInt(num);
					if (number < 1 || number > 11)
						throw new DataException("方案内容不正确,号码在[01-11]之间.");
				}
				for (String num : bet2List) {
					num = num.trim();
					int number = Integer.parseInt(num);
					if (number < 1 || number > 11)
						throw new DataException("方案内容不正确,号码在[01-11]之间.");
				}
				countUnits = sdel11to5PlayType.countUnit(bet1List, bet2List);
				if (units != countUnits)
					throw new DataException("注数与系统计算的不一致.");
				lineContent.setBet1List(bet1List);
				lineContent.setBet2List(bet2List);
				lineContent.setUnits(units);

			} else if (sdel11to5PlayType.equals(SdEl11to5PlayType.ForeThreeDirect)) {
				patt = Pattern.compile(SdEl11to5Constant.DIRECT3_COMPOUND_REGEX);
				matcher = patt.matcher(str);
				if (!matcher.matches())
					throw new DataException("方案内容格式不正确.");
				String unitsStr = matcher.group(1);
				int units = Integer.parseInt(unitsStr);
				String betStr = matcher.group(2).trim();
				String[] betArr = betStr.split(SdEl11to5Constant.SEPARATOR_FOR_);
				bet1List = Arrays.asList(betArr[0].split(SdEl11to5Constant.SEPARATOR_FOR_NUMBER));
				bet2List = Arrays.asList(betArr[1].split(SdEl11to5Constant.SEPARATOR_FOR_NUMBER));
				bet3List = Arrays.asList(betArr[2].split(SdEl11to5Constant.SEPARATOR_FOR_NUMBER));
				for (String num : bet1List) {
					num = num.trim();
					int number = Integer.parseInt(num);
					if (number < 1 || number > 11)
						throw new DataException("方案内容不正确,号码在[01-20]之间.");
				}
				for (String num : bet2List) {
					num = num.trim();
					int number = Integer.parseInt(num);
					if (number < 1 || number > 11)
						throw new DataException("方案内容不正确,号码在[01-20]之间.");
				}
				for (String num : bet3List) {
					num = num.trim();
					int number = Integer.parseInt(num);
					if (number < 1 || number > 11)
						throw new DataException("方案内容不正确,号码在[01-20]之间.");
				}
				countUnits = sdel11to5PlayType.countUnit(bet1List, bet2List, bet3List);
				if (units != countUnits)
					throw new DataException("注数与系统计算的不一致.");
				lineContent.setBet1List(bet1List);
				lineContent.setBet2List(bet2List);
				lineContent.setBet3List(bet3List);
				lineContent.setUnits(units);
			} else if (sdel11to5PlayType.equals(SdEl11to5PlayType.RandomTwo)
					|| sdel11to5PlayType.equals(SdEl11to5PlayType.ForeTwoGroup)
					|| sdel11to5PlayType.equals(SdEl11to5PlayType.RandomThree)
					|| sdel11to5PlayType.equals(SdEl11to5PlayType.ForeThreeGroup)
					|| sdel11to5PlayType.equals(SdEl11to5PlayType.RandomFour)
					|| sdel11to5PlayType.equals(SdEl11to5PlayType.RandomFive)
					|| sdel11to5PlayType.equals(SdEl11to5PlayType.RandomSix)
					|| sdel11to5PlayType.equals(SdEl11to5PlayType.RandomSeven)
					|| sdel11to5PlayType.equals(SdEl11to5PlayType.RandomEight)) {
				boolean hasDan = false;// 是否为含胆码格式
				if (str.indexOf(SdEl11to5Constant.SEPARATOR_DAN_FOR_NUMBER) >= 0) {
					patt = Pattern.compile(SdEl11to5Constant.GENERAL_COMPOUND_DAN_REGEX);
					hasDan = true;
				} else {
					patt = Pattern.compile(SdEl11to5Constant.GENERAL_COMPOUND_REGEX);
				}
				matcher = patt.matcher(str);
				if (!matcher.matches())
					throw new DataException("方案内容格式不正确.");
				String unitsStr = matcher.group(1);
				int units = Integer.parseInt(unitsStr);
				String betStr = matcher.group(2).trim();
				if (hasDan) {
					String[] tempArr = betStr.split(SdEl11to5Constant.SEPARATOR_DAN_FOR_NUMBER);
					betList = Arrays.asList(tempArr[0].split(SdEl11to5Constant.SEPARATOR_FOR_NUMBER));
					for (String num : betList) {
						num = num.trim();
						int number = Integer.parseInt(num);
						if (number < 1 || number > 11)
							throw new DataException("方案内容不正确,号码在[01-20]之间.");
					}
					lineContent.setBetDanList(betList);
					
					betList = Arrays.asList(tempArr[1].split(SdEl11to5Constant.SEPARATOR_FOR_NUMBER));
					for (String num : betList) {
						num = num.trim();
						int number = Integer.parseInt(num);
						if (number < 1 || number > 11)
							throw new DataException("方案内容不正确,号码在[01-20]之间.");
					}
					lineContent.setBetList(betList);
					countUnits = sdel11to5PlayType.countDanUnit(lineContent.getBetDanList(), lineContent.getBetList());
				} else {
					betList = Arrays.asList(betStr.split(SdEl11to5Constant.SEPARATOR_FOR_NUMBER));
					for (String num : betList) {
						num = num.trim();
						int number = Integer.parseInt(num);
						if (number < 1 || number > 11)
							throw new DataException("方案内容不正确,号码在[01-20]之间.");
					}
					countUnits = sdel11to5PlayType.countUnit(betList);
					lineContent.setBetList(betList);
				}
				if (units != countUnits)
					throw new DataException("注数与系统计算的不一致.");
				lineContent.setUnits(units);
			} else {
				throw new DataException("玩法不正确.");
			}

			// ////////////////////////////////////验证号码准确性//////////////////////////////////////////
			Set<Integer> betSet = new TreeSet<Integer>();
			Set<Integer> bet1Set = new TreeSet<Integer>();
			Set<Integer> bet2Set = new TreeSet<Integer>();
			Set<Integer> bet3Set = new TreeSet<Integer>();
			for (String bet : betList) {
				int number = Integer.parseInt(bet);
				if (number < 1 || number > 11)
					throw new DataException("方案内容不正确,号码在[01-11]之间.");
				if (betSet.contains(number))
					throw new DataException("不能出现重复号码.");
				betSet.add(number);
			}
			for (String bet : bet1List) {
				int number = Integer.parseInt(bet);
				if (number < 1 || number > 11)
					throw new DataException("方案内容不正确,第一位号码在[01-11]之间.");
				if (bet1Set.contains(number))
					throw new DataException("第一位不能出现重复号码.");
				bet1Set.add(number);
			}
			for (String bet : bet2List) {
				int number = Integer.parseInt(bet);
				if (number < 1 || number > 11)
					throw new DataException("方案内容不正确,第二位号码在[01-11]之间.");
				if (bet2Set.contains(number))
					throw new DataException("第二位不能出现重复号码.");
				bet2Set.add(number);
			}
			for (String bet : bet3List) {
				int number = Integer.parseInt(bet);
				if (number < 1 || number > 11)
					throw new DataException("方案内容不正确,第三位号码在[01-11]之间.");
				if (bet3Set.contains(number))
					throw new DataException("第三位不能出现重复号码.");
				bet3Set.add(number);
			}

			// ////////////////////////////////////验证号码准确性//////////////////////////////////////////

			allUnits += countUnits;
			all.add(lineContent);
			betList = new ArrayList<String>();
			bet1List = new ArrayList<String>();
			bet2List = new ArrayList<String>();
			bet3List = new ArrayList<String>();

		}
		return new ContentBean(allUnits, JSONArray.fromObject(all).toString());
	}

	/**
	 * ContentBean单式构建器
	 * 
	 * @param schemeContent
	 *            方案内容
	 * @return {@link com.cai310.lottery.support.ContentBean}
	 * @throws DataException
	 */
	public static ContentBean buildSingleContentBean(String schemeContent, SdEl11to5PlayType sdel11to5PlayType)
			throws DataException {
		if (StringUtils.isBlank(schemeContent))
			throw new DataException("方案内容为空.");

		int allUnits = 0;
		List<String> all = new ArrayList<String>(10);

		String[] arr = schemeContent.split("(\r\n|\n)+");
		StringBuilder line = new StringBuilder(25);
		Set<String> numSet = new LinkedHashSet<String>();
		for (String str : arr) {
			String single_regex = null;
			if (sdel11to5PlayType.equals(SdEl11to5PlayType.NormalOne)) {
				// 一个号码
				throw new DataException("任选1不支持单式投注.");
			} else if (sdel11to5PlayType.equals(SdEl11to5PlayType.ForeTwoDirect)
					|| sdel11to5PlayType.equals(SdEl11to5PlayType.RandomTwo)
					|| sdel11to5PlayType.equals(SdEl11to5PlayType.ForeTwoGroup)) {
				// 两个号码
				single_regex = SdEl11to5Constant.CHOOSE2_SINGLE_REGEX;
			} else if (sdel11to5PlayType.equals(SdEl11to5PlayType.RandomThree)
					|| sdel11to5PlayType.equals(SdEl11to5PlayType.ForeThreeGroup)
					|| sdel11to5PlayType.equals(SdEl11to5PlayType.ForeThreeDirect)) {
				// 三个号码
				single_regex = SdEl11to5Constant.CHOOSE3_SINGLE_REGEX;
			} else if (sdel11to5PlayType.equals(SdEl11to5PlayType.RandomFour)) {
				// 四个号码
				single_regex = SdEl11to5Constant.CHOOSE4_SINGLE_REGEX;
			} else if (sdel11to5PlayType.equals(SdEl11to5PlayType.RandomFive)) {
				// 五个号码
				single_regex = SdEl11to5Constant.CHOOSE5_SINGLE_REGEX;
			} else if (sdel11to5PlayType.equals(SdEl11to5PlayType.RandomSix)) {
				// 五个号码
				single_regex = SdEl11to5Constant.CHOOSE6_SINGLE_REGEX;
			} else if (sdel11to5PlayType.equals(SdEl11to5PlayType.RandomSeven)) {
				// 五个号码
				single_regex = SdEl11to5Constant.CHOOSE7_SINGLE_REGEX;
			} else if (sdel11to5PlayType.equals(SdEl11to5PlayType.RandomEight)) {
				// 五个号码
				single_regex = SdEl11to5Constant.CHOOSE8_SINGLE_REGEX;
			} else {
				throw new DataException("玩法不正确.");
			}
			Pattern patt = Pattern.compile(single_regex);
			Matcher matcher = patt.matcher(str);
			if (!matcher.matches())
				throw new DataException("方案内容格式不正确.");

			// 重置line、redSet、blueSet
			line.setLength(0);
			numSet.clear();

			String[] numArr = matcher.group(1).split("\\D");

			for (String num : numArr) {
				num = num.trim();
				int number = Integer.parseInt(num);
				if (number < 1 || number > 11)
					throw new DataException("方案内容不正确,号码在[01-11]之间.");
				if (number < 10) {
					numSet.add("0" + number);
				} else {
					numSet.add("" + number);
				}

			}
			if (sdel11to5PlayType.equals(SdEl11to5PlayType.NormalOne)) {
				// 一个号码
				if (numSet.size() != 1)
					throw new DataException("投注数目不对.");
			} else if (sdel11to5PlayType.equals(SdEl11to5PlayType.ForeTwoDirect)
					|| sdel11to5PlayType.equals(SdEl11to5PlayType.RandomTwo)
					|| sdel11to5PlayType.equals(SdEl11to5PlayType.ForeTwoGroup)) {
				// 两个号码
				if (numSet.size() != 2)
					throw new DataException("投注数目不对.");
			} else if (sdel11to5PlayType.equals(SdEl11to5PlayType.RandomThree)
					|| sdel11to5PlayType.equals(SdEl11to5PlayType.ForeThreeGroup)
					|| sdel11to5PlayType.equals(SdEl11to5PlayType.ForeThreeDirect)) {
				// 三个号码
				if (numSet.size() != 3)
					throw new DataException("投注数目不对.");
			} else if (sdel11to5PlayType.equals(SdEl11to5PlayType.RandomFour)) {
				// 四个号码
				if (numSet.size() != 4)
					throw new DataException("投注数目不对.");
			} else if (sdel11to5PlayType.equals(SdEl11to5PlayType.RandomFive)) {
				// 五个号码
				if (numSet.size() != 5)
					throw new DataException("投注数目不对.");
			} else if (sdel11to5PlayType.equals(SdEl11to5PlayType.RandomSix)) {
				// 六个号码
				if (numSet.size() != 6)
					throw new DataException("投注数目不对.");
			} else if (sdel11to5PlayType.equals(SdEl11to5PlayType.RandomSeven)) {
				// 七个号码
				if (numSet.size() != 7)
					throw new DataException("投注数目不对.");
			} else if (sdel11to5PlayType.equals(SdEl11to5PlayType.RandomEight)) {
				// 八个号码
				if (numSet.size() != 8)
					throw new DataException("投注数目不对.");
			} else {
				throw new DataException("玩法不正确.");
			}
			line.append(StringUtils.join(numSet, Constant.SINGLE_SEPARATOR_FOR_NUMBER));
			allUnits++;
			all.add(line.toString());
			numSet = new LinkedHashSet<String>();
		}

		return new ContentBean(allUnits, StringUtils.join(all, "\r\n"));
	}
}
