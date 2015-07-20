package com.cai310.lottery.support.welfare36to7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.Constant;
import com.cai310.lottery.Welfare36to7Constant;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.ContentBean;
import com.cai310.utils.MathUtils;

/**
 * 福彩ContentBean构建器
 * 
 */
public class Welfare36to7ContentBeanBuilder {
	/**
	 * ContentBean复式构建器
	 * 
	 * @param schemeContent 方案内容
	 * @return {@link com.cai310.lottery.support.ContentBean}
	 * @throws DataException
	 */
	public static ContentBean buildCompoundContentBean(String schemeContent, Welfare36to7PlayType playType)
			throws DataException {
		// /复式方案内容注数：
		if (StringUtils.isBlank(schemeContent))
			throw new DataException("方案内容为空.");

		int allUnits = 0;
		List<Welfare36to7CompoundContent> all = new ArrayList<Welfare36to7CompoundContent>();
		String[] arr = schemeContent.split("(\r\n|\n)+");
		List<String> welfare36to7List = new ArrayList<String>();
		List<String> haocai1 = new ArrayList<String>();
		List<String> haocai2 = new ArrayList<String>();
		List<String> haocai3 = new ArrayList<String>();
		List<String> zodiacList = new ArrayList<String>();
		List<String> seasonList = new ArrayList<String>();
		List<String> azimuthList = new ArrayList<String>();
		Welfare36to7CompoundContent lineContent = new Welfare36to7CompoundContent();
		for (String str : arr) {
			// 重置
			/*welfare36to7List.clear();
			haocai1.clear();
			haocai2.clear();
			haocai3.clear();
			zodiacList.clear();
			seasonList.clear();
			azimuthList.clear();*/
			int countUnits = 0;
			if (Welfare36to7PlayType.General.equals(playType)) {
				Pattern patt = Pattern.compile(Welfare36to7Constant.GENERAL_COMPOUND_REGEX);
				Matcher matcher = patt.matcher(str);
				if (!matcher.matches())
					throw new DataException("方案内容格式不正确.");
				String unitsStr = matcher.group(1);
				int units = Integer.parseInt(unitsStr);
				String betStr = matcher.group(2).trim();
				welfare36to7List = Arrays.asList(betStr.split(Welfare36to7Constant.SEPARATOR_FOR_NUMBER));
				countUnits = MathUtils.comp(7, welfare36to7List.size());
				if (units != countUnits)
					throw new DataException("注数与系统计算的不一致.");
				for (String num : welfare36to7List) {
					num = num.trim();
					int number = Integer.parseInt(num);
					if (number < 1 || number > 36)
						throw new DataException("方案内容不正确,号码在[01-36]之间.");
				}
				allUnits += units;
				Collections.sort(welfare36to7List);
				lineContent.setUnits(units);
				lineContent.setWelfare36to7List(welfare36to7List);
				all.add(lineContent);

			} else if (Welfare36to7PlayType.Haocai1.equals(playType)) {
				Pattern patt = Pattern.compile(Welfare36to7Constant.HAOCAI1_COMPOUND_REGEX);
				Matcher matcher = patt.matcher(str);
				if (!matcher.matches())
					throw new DataException("方案内容格式不正确.");
				String unitsStr = matcher.group(1);
				int units = Integer.parseInt(unitsStr);
				String betStr = matcher.group(2).trim();
				
				List<String> betList = new ArrayList();
				if (betStr.indexOf(Welfare36to7Constant.SEPARATOR_FOR_NUMBER) != -1) {
					betList = Arrays.asList(betStr.split(Welfare36to7Constant.SEPARATOR_FOR_NUMBER));
					haocai1.addAll(betList);
				} else {
					haocai1.add(betStr);
				}
				countUnits = haocai1.size();
				
				for (String num : haocai1) {
					num = num.trim();
					int number = Integer.parseInt(num);
					if (number < 1 || number > 36)
						throw new DataException("方案内容不正确,号码在[01-36]之间.");
				}
				allUnits += units;
				if (allUnits != countUnits)
					throw new DataException("注数与系统计算的不一致.");
				
				
				Collections.sort(haocai1);
				lineContent.setHaocai1List(haocai1);
				lineContent.setUnits(allUnits);
				
			} else if (Welfare36to7PlayType.Haocai2.equals(playType)) {
				Pattern patt = Pattern.compile(Welfare36to7Constant.HAOCAI2_COMPOUND_REGEX);
				Matcher matcher = patt.matcher(str);
				if (!matcher.matches())
					throw new DataException("方案内容格式不正确.");
				String unitsStr = matcher.group(1);
				int units = Integer.parseInt(unitsStr);
				String betStr = matcher.group(2).trim();
				if (betStr.indexOf(Welfare36to7Constant.SEPARATOR_FOR_NUMBER) != -1) {
					haocai2 = Arrays.asList(betStr.split(Welfare36to7Constant.SEPARATOR_FOR_NUMBER));
				} else {
					haocai2.add(betStr);
				}
				countUnits = MathUtils.comp(2, haocai2.size());
				if (units != countUnits)
					throw new DataException("注数与系统计算的不一致.");
				for (String num : haocai2) {
					num = num.trim();
					int number = Integer.parseInt(num);
					if (number < 1 || number > 36)
						throw new DataException("方案内容不正确,号码在[01-36]之间.");
				}
				allUnits += units;
				Collections.sort(haocai2);
				lineContent.setHaocai2List(haocai2);
				lineContent.setUnits(units);
				all.add(lineContent);
			} else if (Welfare36to7PlayType.Haocai3.equals(playType)) {
				Pattern patt = Pattern.compile(Welfare36to7Constant.HAOCAI3_COMPOUND_REGEX);
				Matcher matcher = patt.matcher(str);
				if (!matcher.matches())
					throw new DataException("方案内容格式不正确.");
				String unitsStr = matcher.group(1);
				int units = Integer.parseInt(unitsStr);
				String betStr = matcher.group(2).trim();
		 		if (betStr.indexOf(Welfare36to7Constant.SEPARATOR_FOR_NUMBER) != -1) {
					haocai3 = Arrays.asList(betStr.split(Welfare36to7Constant.SEPARATOR_FOR_NUMBER));
				} else {
					haocai3.add(betStr);
				}
				countUnits = MathUtils.comp(3, haocai3.size());
				if (units != countUnits)
					throw new DataException("注数与系统计算的不一致.");
				for (String num : haocai3) {
					num = num.trim();
					int number = Integer.parseInt(num);
					if (number < 1 || number > 36)
						throw new DataException("方案内容不正确,号码在[01-36]之间.");
				}
				allUnits += units;
				Collections.sort(haocai3);
				lineContent.setHaocai3List(haocai3);
				lineContent.setUnits(units);
				all.add(lineContent);
			} else if (Welfare36to7PlayType.Zodiac.equals(playType)) {
				Pattern patt = Pattern.compile(Welfare36to7Constant.ZODIAC_COMPOUND_REGEX);
				Matcher matcher = patt.matcher(str);
				if (!matcher.matches())
					throw new DataException("方案内容格式不正确.");
				String unitsStr = matcher.group(1);
				int units = Integer.parseInt(unitsStr);
				String betStr = matcher.group(2).trim();
				
				List<String> betList = new ArrayList();
				if (betStr.indexOf(Welfare36to7Constant.SEPARATOR_FOR_NUMBER) != -1) {
					betList = Arrays.asList(betStr.split(Welfare36to7Constant.SEPARATOR_FOR_NUMBER));
					zodiacList.addAll(betList);
				} else {
					zodiacList.add(betStr);
				}
				countUnits = zodiacList.size();
				
				for (String num : zodiacList) {
					num = num.trim();
					int number = Integer.parseInt(num);
					if (number < 1 || number > 12)
						throw new DataException("方案内容不正确,号码在[01-12]之间.");
				}
				allUnits += units;
				
				if (allUnits != countUnits)
					throw new DataException("注数与系统计算的不一致.");
				
				Collections.sort(zodiacList);
				lineContent.setZodiacList(zodiacList);
				lineContent.setUnits(allUnits);
				
			} else if (Welfare36to7PlayType.Season.equals(playType)) {
				Pattern patt = Pattern.compile(Welfare36to7Constant.SEASON_COMPOUND_REGEX);
				Matcher matcher = patt.matcher(str);
				if (!matcher.matches())
					throw new DataException("方案内容格式不正确.");
				String unitsStr = matcher.group(1);
				int units = Integer.parseInt(unitsStr);
				String betStr = matcher.group(2).trim();
				
				
				List<String> betList = new ArrayList();
				if (betStr.indexOf(Welfare36to7Constant.SEPARATOR_FOR_NUMBER) != -1) {
					betList = Arrays.asList(betStr.split(Welfare36to7Constant.SEPARATOR_FOR_NUMBER));
					seasonList.addAll(betList);
				} else {
					seasonList.add(betStr);
				}
				countUnits = seasonList.size();
	
				for (String num : seasonList) {
					num = num.trim();
					int number = Integer.parseInt(num);
					if (number < 1 || number > 4)
						throw new DataException("方案内容不正确,号码在[01-04]之间.");
				}
				allUnits += units;
				if (allUnits != countUnits)
					throw new DataException("注数与系统计算的不一致.");
				
				Collections.sort(seasonList);
				lineContent.setSeasonList(seasonList);
				lineContent.setUnits(allUnits);

			} else if (Welfare36to7PlayType.Azimuth.equals(playType)) {
				Pattern patt = Pattern.compile(Welfare36to7Constant.AZIMUTH_COMPOUND_REGEX);
				Matcher matcher = patt.matcher(str);
				if (!matcher.matches())
					throw new DataException("方案内容格式不正确.");
				String unitsStr = matcher.group(1);
				int units = Integer.parseInt(unitsStr);
				String betStr = matcher.group(2).trim();
				
				
				List<String> betList = new ArrayList();
				if (betStr.indexOf(Welfare36to7Constant.SEPARATOR_FOR_NUMBER) != -1) {
					betList = Arrays.asList(betStr.split(Welfare36to7Constant.SEPARATOR_FOR_NUMBER));
					azimuthList.addAll(betList);
				} else {
					azimuthList.add(betStr);
				}
				
				countUnits = azimuthList.size();
				for (String num : azimuthList) {
					num = num.trim();
					int number = Integer.parseInt(num);
					if (number < 1 || number > 4)
						throw new DataException("方案内容不正确,号码在[01-04]之间.");
				}
				allUnits += units;
				
				if (allUnits != countUnits)
					throw new DataException("注数与系统计算的不一致.");
				
				Collections.sort(azimuthList);
				lineContent.setAzimuthList(azimuthList);
				lineContent.setUnits(allUnits);
			} else {
				throw new DataException("投注方式不正确.");
			}
			}
		all.add(lineContent);
		return new ContentBean(allUnits, JSONArray.fromObject(all).toString());
	}

	/**
	 * ContentBean单式构建器
	 * 
	 * @param schemeContent 方案内容
	 * @return {@link com.cai310.lottery.support.ContentBean}
	 * @throws DataException
	 */
	public static ContentBean buildSingleContentBean(String schemeContent, Welfare36to7PlayType playType)
			throws DataException {
		if (StringUtils.isBlank(schemeContent))
			throw new DataException("方案内容为空.");

		int allUnits = 0;
		List<String> all = new ArrayList<String>(10);

		String[] arr = schemeContent.split("(\r\n|\n)+");
		StringBuilder line = new StringBuilder(25);
		Set<String> numSet = new TreeSet<String>();
		String[] bets;
		for (String str : arr) {
			String betStr;
			// 重置
			line.setLength(0);
			numSet.clear();
			if (Welfare36to7PlayType.General.equals(playType)) {
				Pattern patt = Pattern.compile(Welfare36to7Constant.GENERAL_SINGLE_REGEX);
				Matcher matcher = patt.matcher(str);
				if (!matcher.matches())
					throw new DataException("方案内容格式不正确.");
				betStr = matcher.group(1).trim();
				bets = betStr.split("\\D");
				for (String bet : bets) {
					bet = bet.trim();
					int number = Integer.parseInt(bet);
					if (number < 1 || number > 36)
						throw new DataException("方案内容不正确,号码在[01-36]之间.");
					if (numSet.contains(bet))
						throw new DataException("不能出现重复号码.");
					if(number<10){
						numSet.add("0"+number);
					}else{
						numSet.add(""+number);
					}
				}
				if (numSet.size() != 7)
					throw new DataException("投注数目不对.");
			} else if (Welfare36to7PlayType.Haocai1.equals(playType)) {
				Pattern patt = Pattern.compile(Welfare36to7Constant.HAOCAI1_SINGLE_REGEX);
				Matcher matcher = patt.matcher(str);
				if (!matcher.matches())
					throw new DataException("方案内容格式不正确.");
				betStr = matcher.group(1).trim();
				bets = betStr.split("\\D");
				for (String bet : bets) {
					bet = bet.trim();
					int number = Integer.parseInt(bet);
					if (number < 1 || number > 36)
						throw new DataException("方案内容不正确,号码在[01-36]之间.");
					if (numSet.contains(bet))
						throw new DataException("不能出现重复号码.");
					if(number<10){
						numSet.add("0"+number);
					}else{
						numSet.add(""+number);
					}
				}
				if (numSet.size() != 1)
					throw new DataException("投注数目不对.");
			} else if (Welfare36to7PlayType.Haocai2.equals(playType)) {
				Pattern patt = Pattern.compile(Welfare36to7Constant.HAOCAI2_SINGLE_REGEX);
				Matcher matcher = patt.matcher(str);
				if (!matcher.matches())
					throw new DataException("方案内容格式不正确.");
				betStr = matcher.group(1).trim();
				bets = betStr.split("\\D");
				for (String bet : bets) {
					bet = bet.trim();
					int number = Integer.parseInt(bet);
					if (number < 1 || number > 36)
						throw new DataException("方案内容不正确,号码在[01-36]之间.");
					if (numSet.contains(bet))
						throw new DataException("不能出现重复号码.");
					if(number<10){
						numSet.add("0"+number);
					}else{
						numSet.add(""+number);
					}
				}
				if (numSet.size() != 2)
					throw new DataException("投注数目不对.");
			} else if (Welfare36to7PlayType.Haocai3.equals(playType)) {
				Pattern patt = Pattern.compile(Welfare36to7Constant.HAOCAI3_SINGLE_REGEX);
				Matcher matcher = patt.matcher(str);
				if (!matcher.matches())
					throw new DataException("方案内容格式不正确.");
				betStr = matcher.group(1).trim();
				bets = betStr.split("\\D");
				for (String bet : bets) {
					bet = bet.trim();
					int number = Integer.parseInt(bet);
					if (number < 1 || number > 36)
						throw new DataException("方案内容不正确,号码在[01-36]之间.");
					if (numSet.contains(bet))
						throw new DataException("不能出现重复号码.");
					if(number<10){
						numSet.add("0"+number);
					}else{
						numSet.add(""+number);
					}
				}
				if (numSet.size() != 3)
					throw new DataException("投注数目不对.");
			} else {
				throw new DataException("投注方式不正确.");
			}
			line.append(StringUtils.join(numSet, Constant.SINGLE_SEPARATOR_FOR_NUMBER));
			allUnits++;
			all.add(line.toString());
		}

		return new ContentBean(allUnits, StringUtils.join(all, "\r\n"));
	}
}
