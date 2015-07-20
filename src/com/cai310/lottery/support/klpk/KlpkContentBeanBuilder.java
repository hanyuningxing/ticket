package com.cai310.lottery.support.klpk;

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
import com.cai310.lottery.KlpkConstant;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.ContentBean;

/**
 * ContentBean构建器
 * 
 */
public class KlpkContentBeanBuilder {
	/**
	 * ContentBean复式构建器
	 * 
	 * @param schemeContent
	 *            方案内容
	 * @return {@link com.cai310.lottery.support.ContentBean}
	 * @throws DataException
	 */
	public static ContentBean buildCompoundContentBean(String schemeContent, KlpkPlayType klpkPlayType)
			throws DataException {
		// /复式方案内容注数：
		if (StringUtils.isBlank(schemeContent))
			throw new DataException("方案内容为空.");

		int allUnits = 0;
		List<KlpkCompoundContent> all = new ArrayList<KlpkCompoundContent>(10);

		String[] arr = schemeContent.split("(\r\n|\n)+");

		/** 投注号码 */
		List<String> betList = new ArrayList<String>();
		for (String str : arr) {
			betList.clear();
			Integer countUnits = 0;
			Pattern patt = null;
			Matcher matcher = null;
			KlpkCompoundContent lineContent = new KlpkCompoundContent();
			if (klpkPlayType.equals(KlpkPlayType.SAMEBAO)||klpkPlayType.equals(KlpkPlayType.SHUNBAO)||klpkPlayType.equals(KlpkPlayType.DUIBAO)) {
				patt = Pattern.compile(KlpkConstant.GENERAL_COMPOUND_REGEX);
				matcher = patt.matcher(str);
				if (!matcher.matches())
					throw new DataException("方案内容格式不正确.");
				String unitsStr = matcher.group(1);
				int units = Integer.parseInt(unitsStr);
				String betStr = matcher.group(2).trim();
				betList = Arrays.asList(betStr.split(KlpkConstant.SEPARATOR_FOR_NUMBER));
				for (String num : betList) {
					num = num.trim();
					int number = Integer.parseInt(num);
					if (number!=1)
						throw new DataException("方案内容不正确,号码在[1-1]之间.");
				}
				countUnits = klpkPlayType.countUnit(betList);
				if (units != countUnits)
					throw new DataException("注数与系统计算的不一致.");
				lineContent.setBetList(betList);
				lineContent.setUnits(units);
			} else if (klpkPlayType.equals(KlpkPlayType.SAME)) {
				patt = Pattern.compile(KlpkConstant.GENERAL_COMPOUND_REGEX);
				matcher = patt.matcher(str);
				if (!matcher.matches())
					throw new DataException("方案内容格式不正确.");
				String unitsStr = matcher.group(1);
				int units = Integer.parseInt(unitsStr);
				String betStr = matcher.group(2).trim();
				betList = Arrays.asList(betStr.split(KlpkConstant.SEPARATOR_FOR_NUMBER));
				for (String num : betList) {
					num = num.trim();
					int number = Integer.parseInt(num);
					if (number < 1 || number > 4)
						throw new DataException("方案内容不正确,号码在[1-4]之间.");
				}
				countUnits = klpkPlayType.countUnit(betList);
				if (units != countUnits)
					throw new DataException("注数与系统计算的不一致.");
				lineContent.setBetList(betList);
				lineContent.setUnits(units);

			}else if (klpkPlayType.equals(KlpkPlayType.SHUN)) {
				patt = Pattern.compile(KlpkConstant.GENERAL_COMPOUND_REGEX);
				matcher = patt.matcher(str);
				if (!matcher.matches())
					throw new DataException("方案内容格式不正确.");
				String unitsStr = matcher.group(1);
				int units = Integer.parseInt(unitsStr);
				String betStr = matcher.group(2).trim();
				betList = Arrays.asList(betStr.split(KlpkConstant.SEPARATOR_FOR_NUMBER));
				for (String num : betList) {
					num = num.trim();
					int number = Integer.parseInt(num);
					if (number < 1 || number > 12)
						throw new DataException("方案内容不正确,号码在[1-12]之间.");
				}
				countUnits = klpkPlayType.countUnit(betList);
				if (units != countUnits)
					throw new DataException("注数与系统计算的不一致.");
				lineContent.setBetList(betList);
				lineContent.setUnits(units);

			}else if (klpkPlayType.equals(KlpkPlayType.DUI)) {
				patt = Pattern.compile(KlpkConstant.GENERAL_COMPOUND_REGEX);
				matcher = patt.matcher(str);
				if (!matcher.matches())
					throw new DataException("方案内容格式不正确.");
				String unitsStr = matcher.group(1);
				int units = Integer.parseInt(unitsStr);
				String betStr = matcher.group(2).trim();
				betList = Arrays.asList(betStr.split(KlpkConstant.SEPARATOR_FOR_NUMBER));
				for (String num : betList) {
					num = num.trim();
					int number = Integer.parseInt(num);
					if (number < 1 || number > 13)
						throw new DataException("方案内容不正确,号码在[1-13]之间.");
				}
				countUnits = klpkPlayType.countUnit(betList);
				if (units != countUnits)
					throw new DataException("注数与系统计算的不一致.");
				lineContent.setBetList(betList);
				lineContent.setUnits(units);

			} else if (klpkPlayType.equals(KlpkPlayType.RandomOne)
					|| klpkPlayType.equals(KlpkPlayType.RandomTwo)
					|| klpkPlayType.equals(KlpkPlayType.RandomThree)
					|| klpkPlayType.equals(KlpkPlayType.RandomThree)
					|| klpkPlayType.equals(KlpkPlayType.RandomFour)
					|| klpkPlayType.equals(KlpkPlayType.RandomFive)
					|| klpkPlayType.equals(KlpkPlayType.RandomSix)) {
				boolean hasDan = false;// 是否为含胆码格式
				if (str.indexOf(KlpkConstant.SEPARATOR_DAN_FOR_NUMBER) >= 0) {
					patt = Pattern.compile(KlpkConstant.GENERAL_COMPOUND_DAN_REGEX);
					hasDan = true;
				} else {
					patt = Pattern.compile(KlpkConstant.GENERAL_COMPOUND_REGEX);
				}
				matcher = patt.matcher(str);
				if (!matcher.matches())
					throw new DataException("方案内容格式不正确.");
				String unitsStr = matcher.group(1);
				int units = Integer.parseInt(unitsStr);
				String betStr = matcher.group(2).trim();
				if (hasDan) {
					String[] tempArr = betStr.split(KlpkConstant.SEPARATOR_DAN_FOR_NUMBER);
					betList = Arrays.asList(tempArr[0].split(KlpkConstant.SEPARATOR_FOR_NUMBER));
					for (String num : betList) {
						num = num.trim();
						int number = Integer.parseInt(num);
						if (number < 1 || number > 13)
							throw new DataException("方案内容不正确,号码在[01-13]之间.");
					}
					lineContent.setBetDanList(betList);
					
					betList = Arrays.asList(tempArr[1].split(KlpkConstant.SEPARATOR_FOR_NUMBER));
					for (String num : betList) {
						num = num.trim();
						int number = Integer.parseInt(num);
						if (number < 1 || number > 13)
							throw new DataException("方案内容不正确,号码在[01-13]之间.");
					}
					lineContent.setBetList(betList);
					countUnits = klpkPlayType.countDanUnit(lineContent.getBetDanList(), lineContent.getBetList());
				} else {
					betList = Arrays.asList(betStr.split(KlpkConstant.SEPARATOR_FOR_NUMBER));
					for (String num : betList) {
						num = num.trim();
						int number = Integer.parseInt(num);
						if (number < 1 || number > 13)
							throw new DataException("方案内容不正确,号码在[01-13]之间.");
					}
					countUnits = klpkPlayType.countUnit(betList);
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
				if (number < 1 || number > 13)
					throw new DataException("方案内容不正确,号码在[01-13]之间.");
				if (betSet.contains(number))
					throw new DataException("不能出现重复号码.");
				betSet.add(number);
			}
			// ////////////////////////////////////验证号码准确性//////////////////////////////////////////

			allUnits += countUnits;
			all.add(lineContent);
			betList = new ArrayList<String>();

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
	public static ContentBean buildSingleContentBean(String schemeContent, KlpkPlayType klpkPlayType)
			throws DataException {
			throw new DataException("不支持单式投注.");
	}
}
