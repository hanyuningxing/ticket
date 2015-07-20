package com.cai310.lottery.support.ahkuai3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.AhKuai3Constant;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.ContentBean;

/**
 * ContentBean构建器
 * 
 */
public class AhKuai3ContentBeanBuilder {
	/**
	 * ContentBean复式构建器
	 * 
	 * @param schemeContent
	 *            方案内容
	 * @return {@link com.cai310.lottery.support.ContentBean}
	 * @throws DataException
	 */
	public static ContentBean buildCompoundContentBean(String schemeContent, AhKuai3PlayType ahkuai3PlayType) throws DataException {
		if (StringUtils.isBlank(schemeContent))
			throw new DataException("方案内容为空.");

		int allUnits = 0;

		List<AhKuai3CompoundContent> all = new ArrayList<AhKuai3CompoundContent>(10);
		String[] arr = schemeContent.split("(\r\n|\n)+");
		List<String> betList = new ArrayList<String>();
		for (String str : arr) {
			betList.clear();
			Integer countUnits = 0;
			Pattern patt = null;
			Matcher matcher = null;
			AhKuai3CompoundContent lineContent = new AhKuai3CompoundContent();
			if (ahkuai3PlayType.equals(AhKuai3PlayType.HeZhi)) {
				patt = Pattern.compile(AhKuai3Constant.GENERAL_COMPOUND_REGEX);
				matcher = patt.matcher(str);
				if (!matcher.matches())
					throw new DataException("方案内容格式不正确.");
				String unitsStr = matcher.group(1);
				int units = Integer.parseInt(unitsStr);
				String betStr = matcher.group(2).trim();
				betList = Arrays.asList(betStr.split(AhKuai3Constant.SEPARATOR_FOR_NUMBER));
				for (String num : betList) {
					num = num.trim();
					int number = Integer.parseInt(num);
					if (number < 1 || number > 19)
						throw new DataException("方案内容不正确,号码在[01-18]之间.");
				}
				countUnits = ahkuai3PlayType.countUnit(betList);
				if (units != countUnits)
					throw new DataException("注数与系统计算的不一致.");
				lineContent.setBetList(betList);
				lineContent.setUnits(units);
			} else if (ahkuai3PlayType.equals(AhKuai3PlayType.ThreeTX) || ahkuai3PlayType.equals(AhKuai3PlayType.ThreeLX)) {
				patt = Pattern.compile(AhKuai3Constant.GENERAL_COMPOUND_REGEX);
				matcher = patt.matcher(str);
				if (!matcher.matches())
					throw new DataException("方案内容格式不正确.");
				String unitsStr = matcher.group(1);
				int units = Integer.parseInt(unitsStr);
				String betStr = matcher.group(2).trim();
				betList = Arrays.asList(betStr);
				for(String num:betList){
					num=num.trim();
					int number=Integer.parseInt(num);
					if(number!=1){
						throw new DataException("方案内容不正确.");
					}
				}
				countUnits = ahkuai3PlayType.countUnit(betList);
				if (units != countUnits) {
					throw new DataException("注数与系统计算的不一致.");
				}
				lineContent.setBetList(betList);
				lineContent.setUnits(units);
			} else if (ahkuai3PlayType.equals(AhKuai3PlayType.ThreeDX)) {
				patt = Pattern.compile(AhKuai3Constant.GENERAL_COMPOUND_REGEX);
				matcher = patt.matcher(str);
				if (!matcher.matches())
					throw new DataException("方案内容格式不正确.");
				String unitsStr = matcher.group(1);
				int units = Integer.parseInt(unitsStr);
				String betStr = matcher.group(2).trim();
				betList = Arrays.asList(betStr.split(AhKuai3Constant.SEPARATOR_FOR_NUMBER));
				for (String num : betList) {
					num = num.trim();
					int number = Integer.parseInt(num);
					if (number % 111 != 0)
						throw new DataException("方案内容不正确");
				}
				countUnits = ahkuai3PlayType.countUnit(betList);
				if (units != countUnits)
					throw new DataException("注数与系统计算的不一致.");
				lineContent.setBetList(betList);
				lineContent.setUnits(units);
			} else if (ahkuai3PlayType.equals(AhKuai3PlayType.TwoFX)) {
				patt = Pattern.compile(AhKuai3Constant.GENERAL_COMPOUND_REGEX);
				matcher = patt.matcher(str);
				if (!matcher.matches()) {
					throw new DataException("方案内容格式不正确.");
				}
				String unitsStr = matcher.group(1);
				int units = Integer.parseInt(unitsStr);
				String betStr = matcher.group(2).trim();
				betList = Arrays.asList(betStr.split(AhKuai3Constant.SEPARATOR_FOR_NUMBER));
				for (String num : betList) {
					num = num.trim();
					int number = Integer.parseInt(num);
					if (number % 11 != 0) {
						throw new DataException("方案内容不正确");
					}
				}
				countUnits = ahkuai3PlayType.countUnit(betList);
				if (units != countUnits) {
					throw new DataException("注数与系统计算的不一致.");
				}
				lineContent.setBetList(betList);
				lineContent.setUnits(units);
			} else if(ahkuai3PlayType.equals(AhKuai3PlayType.TwoDX)){
				patt=Pattern.compile(AhKuai3Constant.GENERAL_TWODX_REGEX);
				matcher=patt.matcher(str);
				if(!matcher.matches()){
					throw new DataException("方案内容格式不正确.");
				}
				String unitsStr=matcher.group(1);
				int units=Integer.parseInt(unitsStr);
				String betStr=matcher.group(2).trim();
				String[] tempArr=betStr.split(AhKuai3Constant.SEPARATOR_DISTINT_FOR_NUMBER);
				betList=Arrays.asList(tempArr[0].split(AhKuai3Constant.SEPARATOR_FOR_NUMBER));
				for(String num:betList){
					num=num.trim();
					int number=Integer.parseInt(num);
					if(number%11!=0){
						throw new DataException("方案内容不正确");
					}
				}
				lineContent.setBetList(betList);
				betList=Arrays.asList(tempArr[1].split(AhKuai3Constant.SEPARATOR_FOR_NUMBER));
				for(String num:betList){
					num=num.trim();
					int number=Integer.parseInt(num);
					if(number<1||number>6){
						throw new DataException("方案内容不正确");
					}
				}
				lineContent.setDisList(betList);
				countUnits=ahkuai3PlayType.countTwoDXUnit(lineContent.getBetList(), lineContent.getDisList());
				if (units != countUnits)
					throw new DataException("注数与系统计算的不一致.");
				lineContent.setUnits(units);
			}else if (ahkuai3PlayType.equals(AhKuai3PlayType.RandomTwo) || ahkuai3PlayType.equals(AhKuai3PlayType.RandomThree)) {
				boolean hasDan = false;// 是否为含胆码格式
				if (str.indexOf(AhKuai3Constant.SEPARATOR_DAN_FOR_NUMBER) >= 0) {
					patt = Pattern.compile(AhKuai3Constant.GENERAL_COMPOUND_DAN_REGEX);
					hasDan = true;
				} else {
					patt = Pattern.compile(AhKuai3Constant.GENERAL_COMPOUND_REGEX);
				}
				matcher = patt.matcher(str);
				if (!matcher.matches())
					throw new DataException("方案内容格式不正确.");
				String unitsStr = matcher.group(1);
				int units = Integer.parseInt(unitsStr);
				String betStr = matcher.group(2).trim();
				if (hasDan) {
					String[] tempArr = betStr.split(AhKuai3Constant.SEPARATOR_DAN_FOR_NUMBER);
					betList = Arrays.asList(tempArr[0].split(AhKuai3Constant.SEPARATOR_FOR_NUMBER));
					for (String num : betList) {
						num = num.trim();
						int number = Integer.parseInt(num);
						if (number < 1 || number > 6)
							throw new DataException("方案内容不正确,号码在[01-06]之间.");
					}
					lineContent.setBetDanList(betList);

					betList = Arrays.asList(tempArr[1].split(AhKuai3Constant.SEPARATOR_FOR_NUMBER));
					for (String num : betList) {
						num = num.trim();
						int number = Integer.parseInt(num);
						if (number < 1 || number > 6)
							throw new DataException("方案内容不正确,号码在[01-06]之间.");
					}
					lineContent.setBetList(betList);
					countUnits = ahkuai3PlayType.countDanUnit(lineContent.getBetDanList(), lineContent.getBetList());
				} else {
					betList = Arrays.asList(betStr.split(AhKuai3Constant.SEPARATOR_FOR_NUMBER));
					for (String num : betList) {
						num = num.trim();
						int number = Integer.parseInt(num);
						if (number < 1 || number > 6)
							throw new DataException("方案内容不正确,号码在[01-06]之间.");
					}
					countUnits = ahkuai3PlayType.countUnit(betList);
					lineContent.setBetList(betList);
				}
				if (units != countUnits)
					throw new DataException("注数与系统计算的不一致.");
				lineContent.setUnits(units);
			} else {
				throw new DataException("玩法不正确.");
			}

			// ////////////////////////////////////验证号码准确性//////////////////////////////////////////
			// Set<Integer> betSet = new TreeSet<Integer>();
			// for (String bet : betList) {
			// int number = Integer.parseInt(bet);
			// if (number < 1 || number > 19)
			// throw new DataException("方案内容不正确,号码在[01-06]之间.");
			// if (betSet.contains(number))
			// throw new DataException("不能出现重复号码.");
			// betSet.add(number);
			// }

			// ////////////////////////////////////验证号码准确性//////////////////////////////////////////

			allUnits += countUnits;
			all.add(lineContent);
			betList = new ArrayList<String>();

		}
		return new ContentBean(allUnits, JSONArray.fromObject(all).toString());

	}
}
