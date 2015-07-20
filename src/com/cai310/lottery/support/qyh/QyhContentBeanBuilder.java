package com.cai310.lottery.support.qyh;

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
import com.cai310.lottery.QyhConstant;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.ContentBean;

/**
 * ContentBean构建器
 * 
 */
public class QyhContentBeanBuilder {
	/**
	 * ContentBean复式构建器
	 * 
	 * @param schemeContent
	 *            方案内容
	 * @return {@link com.cai310.lottery.support.ContentBean}
	 * @throws DataException
	 */
	public static ContentBean buildCompoundContentBean(String schemeContent, QyhPlayType playType)
			throws DataException {
		// /复式方案内容注数：
		if (StringUtils.isBlank(schemeContent))
			throw new DataException("方案内容为空.");

		int allUnits = 0;
		List<QyhCompoundContent> all = new ArrayList<QyhCompoundContent>(10);

		String[] arr = schemeContent.split("(\r\n|\n)+");

		/** 除顺二顺三外投注号码 */
		List<String> betList = new ArrayList<String>();

		/** 顺二顺三投注号码 */
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
			QyhCompoundContent lineContent = new QyhCompoundContent();
			if (playType.equals(QyhPlayType.RandomOne)||playType.equals(QyhPlayType.DirectOne)||playType.equals(QyhPlayType.RoundOne)) {
				patt = Pattern.compile(QyhConstant.GENERAL_COMPOUND_REGEX);
				matcher = patt.matcher(str);
				if (!matcher.matches())
					throw new DataException("方案内容格式不正确.");
				String unitsStr = matcher.group(1);
				int units = Integer.parseInt(unitsStr);
				String betStr = matcher.group(2).trim();
				betList = Arrays.asList(betStr.split(QyhConstant.SEPARATOR_FOR_NUMBER));
				for (String num : betList) {
					num = num.trim();
					int number = Integer.parseInt(num);
					if (number < 1 || number > 23)
						throw new DataException("方案内容不正确,号码在[01-23]之间.");
				}
				lineContent.setBetList(betList);
				countUnits = playType.countUnit(lineContent);
				if (units != countUnits)
					throw new DataException("注数与系统计算的不一致.");
				lineContent.setUnits(units);
			} else if (playType.equals(QyhPlayType.DirectTwo)) {
				patt = Pattern.compile(QyhConstant.DIRECT2_COMPOUND_REGEX);
				matcher = patt.matcher(str);
				if (!matcher.matches())
					throw new DataException("方案内容格式不正确.");
				String unitsStr = matcher.group(1);
				int units = Integer.parseInt(unitsStr);
				String betStr = matcher.group(2).trim();
				String[] betArr = betStr.split(QyhConstant.SEPARATOR_FOR_);
				bet1List = Arrays.asList(betArr[0].split(QyhConstant.SEPARATOR_FOR_NUMBER));
				bet2List = Arrays.asList(betArr[1].split(QyhConstant.SEPARATOR_FOR_NUMBER));
				for (String num : bet1List) {
					num = num.trim();
					int number = Integer.parseInt(num);
					if (number < 1 || number > 23)
						throw new DataException("方案内容不正确,号码在[01-23]之间.");
				}
				for (String num : bet2List) {
					num = num.trim();
					int number = Integer.parseInt(num);
					if (number < 1 || number > 23)
						throw new DataException("方案内容不正确,号码在[01-23]之间.");
				}
				lineContent.setBet1List(bet1List);
				lineContent.setBet2List(bet2List);
				countUnits = playType.countUnit(lineContent);
				if (units != countUnits)
					throw new DataException("注数与系统计算的不一致.");
				lineContent.setUnits(units);

			} else if (playType.equals(QyhPlayType.DirectThree)) {
				patt = Pattern.compile(QyhConstant.DIRECT3_COMPOUND_REGEX);
				matcher = patt.matcher(str);
				if (!matcher.matches())
					throw new DataException("方案内容格式不正确.");
				String unitsStr = matcher.group(1);
				int units = Integer.parseInt(unitsStr);
				String betStr = matcher.group(2).trim();
				String[] betArr = betStr.split(QyhConstant.SEPARATOR_FOR_);
				bet1List = Arrays.asList(betArr[0].split(QyhConstant.SEPARATOR_FOR_NUMBER));
				bet2List = Arrays.asList(betArr[1].split(QyhConstant.SEPARATOR_FOR_NUMBER));
				bet3List = Arrays.asList(betArr[2].split(QyhConstant.SEPARATOR_FOR_NUMBER));
				for (String num : bet1List) {
					num = num.trim();
					int number = Integer.parseInt(num);
					if (number < 1 || number > 23)
						throw new DataException("方案内容不正确,号码在[01-23]之间.");
				}
				for (String num : bet2List) {
					num = num.trim();
					int number = Integer.parseInt(num);
					if (number < 1 || number > 23)
						throw new DataException("方案内容不正确,号码在[01-23]之间.");
				}
				for (String num : bet3List) {
					num = num.trim();
					int number = Integer.parseInt(num);
					if (number < 1 || number > 23)
						throw new DataException("方案内容不正确,号码在[01-23]之间.");
				}
				lineContent.setBet1List(bet1List);
				lineContent.setBet2List(bet2List);
				lineContent.setBet3List(bet3List);
				countUnits = playType.countUnit(lineContent);
				if (units != countUnits)
					throw new DataException("注数与系统计算的不一致.");
				lineContent.setUnits(units);
			} else if (playType.equals(QyhPlayType.RandomTwo)
					|| playType.equals(QyhPlayType.RandomThree)
					|| playType.equals(QyhPlayType.RandomFour)
					|| playType.equals(QyhPlayType.RandomFive)
					|| playType.equals(QyhPlayType.RandomSix)
					|| playType.equals(QyhPlayType.RandomSeven)
					|| playType.equals(QyhPlayType.RandomEight)
					|| playType.equals(QyhPlayType.RandomNine)
					|| playType.equals(QyhPlayType.RandomTen)
					||playType.equals(QyhPlayType.RoundTwo)
					|| playType.equals(QyhPlayType.RoundThree)
					|| playType.equals(QyhPlayType.RoundFour)) {
				boolean hasDan = false;// 是否为含胆码格式
				if (str.indexOf(QyhConstant.SEPARATOR_DAN_FOR_NUMBER) >= 0) {
					patt = Pattern.compile(QyhConstant.GENERAL_COMPOUND_DAN_REGEX);
					hasDan = true;
				} else {
					patt = Pattern.compile(QyhConstant.GENERAL_COMPOUND_REGEX);
				}
				matcher = patt.matcher(str);
				if (!matcher.matches())
					throw new DataException("方案内容格式不正确.");
				String unitsStr = matcher.group(1);
				int units = Integer.parseInt(unitsStr);
				String betStr = matcher.group(2).trim();
				if (hasDan) {
					String[] tempArr = betStr.split(QyhConstant.SEPARATOR_DAN_FOR_NUMBER);
					betList = Arrays.asList(tempArr[0].split(QyhConstant.SEPARATOR_FOR_NUMBER));
					for (String num : betList) {
						num = num.trim();
						int number = Integer.parseInt(num);
						if (number < 1 || number > 23)
							throw new DataException("方案内容不正确,号码在[01-23]之间.");
					}
					lineContent.setBetDanList(betList);
					
					betList = Arrays.asList(tempArr[1].split(QyhConstant.SEPARATOR_FOR_NUMBER));
					for (String num : betList) {
						num = num.trim();
						int number = Integer.parseInt(num);
						if (number < 1 || number > 23)
							throw new DataException("方案内容不正确,号码在[01-23]之间.");
					}
					lineContent.setBetList(betList);
					countUnits = playType.countDanUnit(lineContent.getBetDanList(), lineContent.getBetList());
				} else {
					betList = Arrays.asList(betStr.split(QyhConstant.SEPARATOR_FOR_NUMBER));
					for (String num : betList) {
						num = num.trim();
						int number = Integer.parseInt(num);
						if (number < 1 || number > 23)
							throw new DataException("方案内容不正确,号码在[01-23]之间.");
					}
					lineContent.setBetList(betList);
					countUnits = playType.countUnit(lineContent);
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
				if (number < 1 || number > 23)
					throw new DataException("方案内容不正确,号码在[01-23]之间.");
				if (betSet.contains(number))
					throw new DataException("不能出现重复号码.");
				betSet.add(number);
			}
			for (String bet : bet1List) {
				int number = Integer.parseInt(bet);
				if (number < 1 || number > 23)
					throw new DataException("方案内容不正确,第一位号码在[01-23]之间.");
				if (bet1Set.contains(number))
					throw new DataException("第一位不能出现重复号码.");
				bet1Set.add(number);
			}
			for (String bet : bet2List) {
				int number = Integer.parseInt(bet);
				if (number < 1 || number > 23)
					throw new DataException("方案内容不正确,第二位号码在[01-23]之间.");
				if (bet2Set.contains(number))
					throw new DataException("第二位不能出现重复号码.");
				bet2Set.add(number);
			}
			for (String bet : bet3List) {
				int number = Integer.parseInt(bet);
				if (number < 1 || number > 23)
					throw new DataException("方案内容不正确,第三位号码在[01-23]之间.");
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
	public static ContentBean buildSingleContentBean(String schemeContent, QyhPlayType playType)
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
			if (playType.equals(QyhPlayType.RandomOne)||playType.equals(QyhPlayType.DirectOne)||playType.equals(QyhPlayType.RoundOne)) {
				// 一个号码
				single_regex = QyhConstant.CHOOSE1_SINGLE_REGEX;
			} else if (playType.equals(QyhPlayType.RandomTwo)
					|| playType.equals(QyhPlayType.RoundTwo)
					|| playType.equals(QyhPlayType.DirectTwo)) {
				// 两个号码
				single_regex = QyhConstant.CHOOSE2_SINGLE_REGEX;
			} else if (playType.equals(QyhPlayType.RandomThree)
					|| playType.equals(QyhPlayType.RoundThree)
					|| playType.equals(QyhPlayType.DirectThree)) {
				// 三个号码
				single_regex = QyhConstant.CHOOSE3_SINGLE_REGEX;
			} else if (playType.equals(QyhPlayType.RandomFour)|| playType.equals(QyhPlayType.RoundFour)) {
				// 四个号码
				single_regex = QyhConstant.CHOOSE4_SINGLE_REGEX;
			} else if (playType.equals(QyhPlayType.RandomFive)) {
				// 五个号码
				single_regex = QyhConstant.CHOOSE5_SINGLE_REGEX;
			} else if (playType.equals(QyhPlayType.RandomSix)) {
				// 五个号码
				single_regex = QyhConstant.CHOOSE6_SINGLE_REGEX;
			} else if (playType.equals(QyhPlayType.RandomSeven)) {
				// 五个号码
				single_regex = QyhConstant.CHOOSE7_SINGLE_REGEX;
			} else if (playType.equals(QyhPlayType.RandomEight)) {
				// 五个号码
				single_regex = QyhConstant.CHOOSE8_SINGLE_REGEX;
			}  else if (playType.equals(QyhPlayType.RandomNine)) {
				// 五个号码
				single_regex = QyhConstant.CHOOSE9_SINGLE_REGEX;
			}  else if (playType.equals(QyhPlayType.RandomTen)) {
				// 五个号码
				single_regex = QyhConstant.CHOOSE10_SINGLE_REGEX;
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
				if (number < 1 || number > 23)
					throw new DataException("方案内容不正确,号码在[01-23]之间.");
				if (number < 10) {
					numSet.add("0" + number);
				} else {
					numSet.add("" + number);
				}

			}
			if (playType.equals(QyhPlayType.RandomOne)||playType.equals(QyhPlayType.DirectOne)||playType.equals(QyhPlayType.RoundOne)) {
				// 一个号码
				if (numSet.size() != 1)
					throw new DataException("投注数目不对.");
			} else if (playType.equals(QyhPlayType.RandomTwo)
					|| playType.equals(QyhPlayType.RoundTwo)
					|| playType.equals(QyhPlayType.DirectTwo)) {
				// 两个号码
				if (numSet.size() != 2)
					throw new DataException("投注数目不对.");
			} else if (playType.equals(QyhPlayType.RandomThree)
					|| playType.equals(QyhPlayType.RoundThree)
					|| playType.equals(QyhPlayType.DirectThree)) {
				// 三个号码
				if (numSet.size() != 3)
					throw new DataException("投注数目不对.");
			} else if (playType.equals(QyhPlayType.RandomFour)
					|| playType.equals(QyhPlayType.RoundFour)) {
				// 四个号码
				if (numSet.size() != 4)
					throw new DataException("投注数目不对.");
			} else if (playType.equals(QyhPlayType.RandomFive)) {
				// 五个号码
				if (numSet.size() != 5)
					throw new DataException("投注数目不对.");
			} else if (playType.equals(QyhPlayType.RandomSix)) {
				// 六个号码
				if (numSet.size() != 6)
					throw new DataException("投注数目不对.");
			} else if (playType.equals(QyhPlayType.RandomSeven)) {
				// 七个号码
				if (numSet.size() != 7)
					throw new DataException("投注数目不对.");
			} else if (playType.equals(QyhPlayType.RandomEight)) {
				// 八个号码
				if (numSet.size() != 8)
					throw new DataException("投注数目不对.");
			}  else if (playType.equals(QyhPlayType.RandomNine)) {
				// 九个号码
				if (numSet.size() != 9)
					throw new DataException("投注数目不对.");
			}  else if (playType.equals(QyhPlayType.RandomTen)) {
				// 十个号码
				if (numSet.size() != 10)
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
