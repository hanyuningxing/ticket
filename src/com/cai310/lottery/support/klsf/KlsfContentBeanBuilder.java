package com.cai310.lottery.support.klsf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import com.cai310.lottery.Constant;
import com.cai310.lottery.KlsfConstant;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.ContentBean;

/**
 * ContentBean构建器
 * 
 */
public class KlsfContentBeanBuilder {
	/**
	 * ContentBean复式构建器
	 * 
	 * @param schemeContent 方案内容
	 * @return {@link com.cai310.lottery.support.ContentBean}
	 * @throws DataException
	 */
	public static ContentBean buildCompoundContentBean(String schemeContent, KlsfPlayType klsfPlayType)
			throws DataException {
		// /复式方案内容注数：
		if (StringUtils.isBlank(schemeContent))
			throw new DataException("方案内容为空.");

		int allUnits = 0;
		List<KlsfCompoundContent> all = new ArrayList<KlsfCompoundContent>(10);

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
			KlsfCompoundContent lineContent = new KlsfCompoundContent();
			if (klsfPlayType.equals(KlsfPlayType.NormalOne)) {
				patt = Pattern.compile(KlsfConstant.GENERAL_COMPOUND_REGEX);
				matcher = patt.matcher(str);
				if (!matcher.matches())
					throw new DataException("方案内容格式不正确.");
				String unitsStr = matcher.group(1);
				int units = Integer.parseInt(unitsStr);
				String betStr = matcher.group(2).trim();
				betList = Arrays.asList(betStr.split(KlsfConstant.SEPARATOR_FOR_NUMBER));
				for (String num : betList) {
					num = num.trim();
					int number = Integer.parseInt(num);
					if (number < 1 || number > 18)
						throw new DataException("方案内容不正确,号码在[01-18]之间.");
				}
				countUnits = klsfPlayType.countUnit(betList);
				if (units != countUnits)
					throw new DataException("注数与系统计算的不一致.");
				lineContent.setBetList(betList);
				lineContent.setUnits(units);
			} else if (klsfPlayType.equals(KlsfPlayType.RedOne)) {
				patt = Pattern.compile(KlsfConstant.GENERAL_COMPOUND_REGEX);
				matcher = patt.matcher(str);
				if (!matcher.matches())
					throw new DataException("方案内容格式不正确.");
				String unitsStr = matcher.group(1);
				int units = Integer.parseInt(unitsStr);
				String betStr = matcher.group(2).trim();
				betList = Arrays.asList(betStr.split(KlsfConstant.SEPARATOR_FOR_NUMBER));
				for (String num : betList) {
					num = num.trim();
					int number = Integer.parseInt(num);
					if (number < 19 || number > 20)
						throw new DataException("方案内容不正确,号码在[19-20]之间.");
				}
				countUnits = klsfPlayType.countUnit(betList);
				if (units != countUnits)
					throw new DataException("注数与系统计算的不一致.");
				lineContent.setBetList(betList);
				lineContent.setUnits(units);
			} else if (klsfPlayType.equals(KlsfPlayType.ConnectTwoDirect)) {
				patt = Pattern.compile(KlsfConstant.DIRECT2_COMPOUND_REGEX);
				matcher = patt.matcher(str);
				if (!matcher.matches())
					throw new DataException("方案内容格式不正确.");
				String unitsStr = matcher.group(1);
				int units = Integer.parseInt(unitsStr);
				String betStr = matcher.group(2).trim();
				String[] betArr = betStr.split(KlsfConstant.SEPARATOR_FOR_);
				bet1List = Arrays.asList(betArr[0].split(KlsfConstant.SEPARATOR_FOR_NUMBER));
				bet2List = Arrays.asList(betArr[1].split(KlsfConstant.SEPARATOR_FOR_NUMBER));
				for (String num : bet1List) {
					num = num.trim();
					int number = Integer.parseInt(num);
					if (number < 0 || number > 20)
						throw new DataException("方案内容不正确,号码在[01-20]之间.");
				}
				for (String num : bet2List) {
					num = num.trim();
					int number = Integer.parseInt(num);
					if (number < 0 || number > 20)
						throw new DataException("方案内容不正确,号码在[01-20]之间.");
				}
				countUnits = klsfPlayType.countUnit(bet1List, bet2List);
				if (units != countUnits)
					throw new DataException("注数与系统计算的不一致.");
				lineContent.setBet1List(bet1List);
				lineContent.setBet2List(bet2List);
				lineContent.setUnits(units);

			} else if (klsfPlayType.equals(KlsfPlayType.ForeThreeDirect)) {
				patt = Pattern.compile(KlsfConstant.DIRECT3_COMPOUND_REGEX);
				matcher = patt.matcher(str);
				if (!matcher.matches())
					throw new DataException("方案内容格式不正确.");
				String unitsStr = matcher.group(1);
				int units = Integer.parseInt(unitsStr);
				String betStr = matcher.group(2).trim();
				String[] betArr = betStr.split(KlsfConstant.SEPARATOR_FOR_);
				bet1List = Arrays.asList(betArr[0].split(KlsfConstant.SEPARATOR_FOR_NUMBER));
				bet2List = Arrays.asList(betArr[1].split(KlsfConstant.SEPARATOR_FOR_NUMBER));
				bet3List = Arrays.asList(betArr[2].split(KlsfConstant.SEPARATOR_FOR_NUMBER));
				for (String num : bet1List) {
					num = num.trim();
					int number = Integer.parseInt(num);
					if (number < 1 || number > 20)
						throw new DataException("方案内容不正确,号码在[01-20]之间.");
				}
				for (String num : bet2List) {
					num = num.trim();
					int number = Integer.parseInt(num);
					if (number < 1 || number > 20)
						throw new DataException("方案内容不正确,号码在[01-20]之间.");
				}
				for (String num : bet3List) {
					num = num.trim();
					int number = Integer.parseInt(num);
					if (number < 1 || number > 20)
						throw new DataException("方案内容不正确,号码在[01-20]之间.");
				}
				countUnits = klsfPlayType.countUnit(bet1List, bet2List, bet3List);
				if (units != countUnits)
					throw new DataException("注数与系统计算的不一致.");
				lineContent.setBet1List(bet1List);
				lineContent.setBet2List(bet2List);
				lineContent.setBet3List(bet3List);
				lineContent.setUnits(units);
			} else if (klsfPlayType.equals(KlsfPlayType.RandomTwo) || klsfPlayType.equals(KlsfPlayType.ConnectTwoGroup)
					|| klsfPlayType.equals(KlsfPlayType.RandomThree)
					|| klsfPlayType.equals(KlsfPlayType.ForeThreeGroup) || klsfPlayType.equals(KlsfPlayType.RandomFour)
					|| klsfPlayType.equals(KlsfPlayType.RandomFive)) {

				patt = Pattern.compile(KlsfConstant.GENERAL_COMPOUND_REGEX);
				matcher = patt.matcher(str);
				if (!matcher.matches())
					throw new DataException("方案内容格式不正确.");
				String unitsStr = matcher.group(1);
				int units = Integer.parseInt(unitsStr);
				String betStr = matcher.group(2).trim();
				betList = Arrays.asList(betStr.split(KlsfConstant.SEPARATOR_FOR_NUMBER));
				for (String num : betList) {
					num = num.trim();
					int number = Integer.parseInt(num);
					if (number < 1 || number > 20)
						throw new DataException("方案内容不正确,号码在[01-20]之间.");
				}
				countUnits = klsfPlayType.countUnit(betList);
				if (units != countUnits)
					throw new DataException("注数与系统计算的不一致.");
				lineContent.setBetList(betList);
				lineContent.setUnits(units);
			} else {
				throw new DataException("玩法不正确.");
			}
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
	 * @param schemeContent 方案内容
	 * @return {@link com.cai310.lottery.support.ContentBean}
	 * @throws DataException
	 */
	public static ContentBean buildSingleContentBean(String schemeContent, KlsfPlayType klsfPlayType)
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
			if (klsfPlayType.equals(KlsfPlayType.NormalOne)) {
				// 一个号码
				single_regex = KlsfConstant.CHOOSE1_SINGLE_REGEX;
			} else if (klsfPlayType.equals(KlsfPlayType.RedOne) || klsfPlayType.equals(KlsfPlayType.RandomTwo)
					|| klsfPlayType.equals(KlsfPlayType.ConnectTwoGroup)
					|| klsfPlayType.equals(KlsfPlayType.ConnectTwoDirect)) {
				// 两个号码
				single_regex = KlsfConstant.CHOOSE2_SINGLE_REGEX;
			} else if (klsfPlayType.equals(KlsfPlayType.RandomThree)
					|| klsfPlayType.equals(KlsfPlayType.ForeThreeGroup)
					|| klsfPlayType.equals(KlsfPlayType.ForeThreeDirect)) {
				// 三个号码
				single_regex = KlsfConstant.CHOOSE3_SINGLE_REGEX;
			} else if (klsfPlayType.equals(KlsfPlayType.RandomFour)) {
				// 四个号码
				single_regex = KlsfConstant.CHOOSE4_SINGLE_REGEX;
			} else if (klsfPlayType.equals(KlsfPlayType.RandomFive)) {
				// 五个号码
				single_regex = KlsfConstant.CHOOSE5_SINGLE_REGEX;
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
				if (klsfPlayType.equals(KlsfPlayType.NormalOne)) {
					if (number < 1 || number > 18)
						throw new DataException("方案内容不正确,号码在[01-18]之间.");
				} else if (klsfPlayType.equals(KlsfPlayType.RedOne)) {
					if (number < 19 || number > 20)
						throw new DataException("方案内容不正确,号码在[19-20]之间.");
				} else {
					if (number < 1 || number > 20)
						throw new DataException("方案内容不正确,号码在[01-20]之间.");
				}
				if(number<10){
					numSet.add("0"+number);
				}else{
					numSet.add(""+number);
				}
			}
			if (klsfPlayType.equals(KlsfPlayType.NormalOne)) {
				// 一个号码
				if (numSet.size() != 1)
					throw new DataException("投注数目不对.");
			} else if (klsfPlayType.equals(KlsfPlayType.RedOne) || klsfPlayType.equals(KlsfPlayType.RandomTwo)
					|| klsfPlayType.equals(KlsfPlayType.ConnectTwoGroup)
					|| klsfPlayType.equals(KlsfPlayType.ConnectTwoDirect)) {
				// 两个号码
				if (numSet.size() != 2)
					throw new DataException("投注数目不对.");
			} else if (klsfPlayType.equals(KlsfPlayType.RandomThree)
					|| klsfPlayType.equals(KlsfPlayType.ForeThreeGroup)
					|| klsfPlayType.equals(KlsfPlayType.ForeThreeDirect)) {
				// 三个号码
				if (numSet.size() != 3)
					throw new DataException("投注数目不对.");
			} else if (klsfPlayType.equals(KlsfPlayType.RandomFour)) {
				// 四个号码
				if (numSet.size() != 4)
					throw new DataException("投注数目不对.");
			} else if (klsfPlayType.equals(KlsfPlayType.RandomFive)) {
				// 五个号码
				if (numSet.size() != 5)
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
