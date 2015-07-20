package com.cai310.lottery.support.welfare3d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.Constant;
import com.cai310.lottery.Welfare3dConstant;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.ContentBean;
import com.cai310.utils.MathUtils;

/**
 * 福彩3DContentBean构建器
 * 
 */
public class Welfare3dContentBeanBuilder {

	/**
	 * ContentBean复式构建器
	 * 
	 * @param schemeContent 方案内容
	 * @return {@link com.cai310.lottery.support.ContentBean}
	 * @throws DataException
	 */
	public static ContentBean buildCompoundContentBean(String schemeContent, Welfare3dPlayType welfare3dPlayType)
			throws DataException {
		if (StringUtils.isBlank(schemeContent))
			throw new DataException("方案内容为空.");
		if (null == welfare3dPlayType)
			throw new DataException("投注方式错误");
		int allUnits = 0;
		List<Welfare3dCompoundContent> all = new ArrayList<Welfare3dCompoundContent>(10);

		String[] arr = schemeContent.split("(\r\n|\n)+");
		List<String> area1List = new ArrayList<String>();
		List<String> area2List = new ArrayList<String>();
		List<String> area3List = new ArrayList<String>();
		List<String> group3List = new ArrayList<String>();
		List<String> group6List = new ArrayList<String>();
		List<String> directSumList = new ArrayList<String>();
		List<String> groupSumList = new ArrayList<String>();
		List<String> baoChuanList = new ArrayList<String>();
		List<String> directKuaduList = new ArrayList<String>();
		List<String> g3KuaduList = new ArrayList<String>();
		List<String> g6KuaduList = new ArrayList<String>();
		for (String str : arr) {
			// 重置
			area1List.clear();
			area2List.clear();
			area3List.clear();
			group3List.clear();
			group6List.clear();
			directSumList.clear();
			groupSumList.clear();
			baoChuanList.clear();
			directKuaduList.clear();
			g3KuaduList.clear();
			g6KuaduList.clear();
			Welfare3dCompoundContent lineContent = new Welfare3dCompoundContent();
			int countUnits = 0;
			if (welfare3dPlayType.equals(Welfare3dPlayType.Direct)) {
				// //直选
				// /复式格式 1,2,3,4-1,2,3,4-1,2,3,4
				Pattern patt = Pattern.compile(Welfare3dConstant.DIRECT_COMPOUND_REGEX);
				Matcher matcher = patt.matcher(str);
				if (!matcher.matches())
					throw new DataException("方案内容格式不正确.");
				String unitsStr = matcher.group(1);
				int units = Integer.parseInt(unitsStr);
				lineContent.setUnits(units);
				String betStr = matcher.group(2).trim();
				String[] betArr = betStr.split(Welfare3dConstant.SEPARATOR_FOR_);
				area1List = Arrays.asList(betArr[0].split(Welfare3dConstant.SEPARATOR_FOR_NUMBER));
				area2List = Arrays.asList(betArr[1].split(Welfare3dConstant.SEPARATOR_FOR_NUMBER));
				area3List = Arrays.asList(betArr[2].split(Welfare3dConstant.SEPARATOR_FOR_NUMBER));
				Collections.sort(area1List);
				Collections.sort(area2List);
				Collections.sort(area3List);
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
				countUnits = area1List.size() * area2List.size() * area3List.size();
				if (units != countUnits)
					throw new DataException("注数与系统计算的不一致.");
			} else if (welfare3dPlayType.equals(Welfare3dPlayType.Group3)) {
				// //组选3
				// /复式格式 1,2,3,4
				Pattern patt = Pattern.compile(Welfare3dConstant.GROUP3_6_COMPOUND_REGEX);
				Matcher matcher = patt.matcher(str);
				if (!matcher.matches())
					throw new DataException("方案内容格式不正确.");
				String unitsStr = matcher.group(1);
				int units = Integer.parseInt(unitsStr);
				lineContent.setUnits(units);
				String betStr = matcher.group(2).trim();
				group3List = Arrays.asList(betStr.split(Welfare3dConstant.SEPARATOR_FOR_NUMBER));
				Collections.sort(group3List);
				countUnits = group3List.size() * (group3List.size() - 1);
				if (units != countUnits)
					throw new DataException("注数与系统计算的不一致.");
				for (String num : group3List) {
					num = num.trim();
					int number = Integer.parseInt(num);
					if (number < 0 || number > 9)
						throw new DataException("方案内容不正确,号码在[00-09]之间.");
				}
			} else if (welfare3dPlayType.equals(Welfare3dPlayType.Group6)) {
				// //组选6
				// /复式格式 1,2,3,4
				Pattern patt = Pattern.compile(Welfare3dConstant.GROUP3_6_COMPOUND_REGEX);
				Matcher matcher = patt.matcher(str);
				if (!matcher.matches())
					throw new DataException("方案内容格式不正确.");
				String unitsStr = matcher.group(1);
				int units = Integer.parseInt(unitsStr);
				lineContent.setUnits(units);
				String betStr = matcher.group(2).trim();
				group6List = Arrays.asList(betStr.split(Welfare3dConstant.SEPARATOR_FOR_NUMBER));
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
			} else if (welfare3dPlayType.equals(Welfare3dPlayType.DirectSum)) {
				Pattern patt = Pattern.compile(Welfare3dConstant.DIRECT_SUM_COMPOUND_REGEX);
				Matcher matcher = patt.matcher(str);
				if (!matcher.matches())
					throw new DataException("方案内容格式不正确.");
				String unitsStr = matcher.group(1);
				int units = Integer.parseInt(unitsStr);
				lineContent.setUnits(units);
				String betStr = matcher.group(2).trim();
				directSumList = Arrays.asList(betStr.split(Welfare3dConstant.SEPARATOR_FOR_NUMBER));
				Collections.sort(directSumList);
				for (String num : directSumList) {
					num = num.trim();
					int number = Integer.parseInt(num);
					if (number < 0 || number > 27)
						throw new DataException("方案内容不正确,号码在[00-27]之间.");
					countUnits += Welfare3dConstant.UNITS_DIRECT_SUM[number];
				}
				if (units != countUnits)
					throw new DataException("注数与系统计算的不一致.");
			} else if (welfare3dPlayType.equals(Welfare3dPlayType.GroupSum)) {
				Pattern patt = Pattern.compile(Welfare3dConstant.GROUP_SUM_COMPOUND_REGEX);
				Matcher matcher = patt.matcher(str);
				if (!matcher.matches())
					throw new DataException("方案内容格式不正确.");
				String unitsStr = matcher.group(1);
				int units = Integer.parseInt(unitsStr);
				lineContent.setUnits(units);
				String betStr = matcher.group(2).trim();
				groupSumList = Arrays.asList(betStr.split(Welfare3dConstant.SEPARATOR_FOR_NUMBER));
				Collections.sort(groupSumList);
				for (String num : groupSumList) {
					num = num.trim();
					int number = Integer.parseInt(num);
					if (number < 0 || number > 26)
						throw new DataException("方案内容不正确,号码在[01-26]之间.");
					countUnits += Welfare3dConstant.UNITS_GROUP_SUM[number];
				}
				if (units != countUnits)
					throw new DataException("注数与系统计算的不一致.");
			} else if (welfare3dPlayType.equals(Welfare3dPlayType.BaoChuan)) {
				Pattern patt = Pattern.compile(Welfare3dConstant.BAOCHUAN_COMPOUND_REGEX);
				Matcher matcher = patt.matcher(str);
				if (!matcher.matches())
					throw new DataException("方案内容格式不正确.");
				String unitsStr = matcher.group(1);
				int units = Integer.parseInt(unitsStr);
				lineContent.setUnits(units);
				String betStr = matcher.group(2).trim();
				baoChuanList = Arrays.asList(betStr.split(Welfare3dConstant.SEPARATOR_FOR_NUMBER));
				Collections.sort(baoChuanList);
				for (String num : baoChuanList) {
					num = num.trim();
					int number = Integer.parseInt(num);
					if (number < 0 || number > 9)
						throw new DataException("方案内容不正确,号码在[01-09]之间.");
					
				}
				countUnits=MathUtils.comp(1,baoChuanList.size())*MathUtils.comp(1,baoChuanList.size())*MathUtils.comp(1,baoChuanList.size());
				if (units != countUnits)
					throw new DataException("注数与系统计算的不一致.");
			}else if (welfare3dPlayType.equals(Welfare3dPlayType.DirectKuadu)) {
				Pattern patt = Pattern.compile(Welfare3dConstant.KUADU_REGEX);
				Matcher matcher = patt.matcher(str);
				if (!matcher.matches())
					throw new DataException("方案内容格式不正确.");
				String unitsStr = matcher.group(1);
				int units = Integer.parseInt(unitsStr);
				lineContent.setUnits(units);
				String betStr = matcher.group(2).trim();
				directKuaduList = Arrays.asList(betStr.split(Welfare3dConstant.SEPARATOR_FOR_NUMBER));
				Collections.sort(directKuaduList);
				for (String num : directKuaduList) {
					num = num.trim();
					int number = Integer.parseInt(num);
					if (number < 0 || number > 9)
						throw new DataException("方案内容不正确,号码在[00-09]之间.");
					countUnits += Welfare3dConstant.UNITS_KUADU[number];
				}
				if (units != countUnits)
					throw new DataException("注数与系统计算的不一致.");
			}else if (welfare3dPlayType.equals(Welfare3dPlayType.Group3Kuadu)) {
				Pattern patt = Pattern.compile(Welfare3dConstant.KUADU_REGEX);
				Matcher matcher = patt.matcher(str);
				if (!matcher.matches())
					throw new DataException("方案内容格式不正确.");
				String unitsStr = matcher.group(1);
				int units = Integer.parseInt(unitsStr);
				lineContent.setUnits(units);
				String betStr = matcher.group(2).trim();
				g3KuaduList = Arrays.asList(betStr.split(Welfare3dConstant.SEPARATOR_FOR_NUMBER));
				Collections.sort(g3KuaduList);
				for (String num : g3KuaduList) {
					num = num.trim();
					int number = Integer.parseInt(num);
					if (number < 0 || number > 9)
						throw new DataException("方案内容不正确,号码在[01-09]之间.");
					countUnits += Welfare3dConstant.UNITS_G3_KUADU[number];
				}
				if (units != countUnits)
					throw new DataException("注数与系统计算的不一致.");
			}else if (welfare3dPlayType.equals(Welfare3dPlayType.Group6Kuadu)) {
				Pattern patt = Pattern.compile(Welfare3dConstant.KUADU_REGEX);
				Matcher matcher = patt.matcher(str);
				if (!matcher.matches())
					throw new DataException("方案内容格式不正确.");
				String unitsStr = matcher.group(1);
				int units = Integer.parseInt(unitsStr);
				lineContent.setUnits(units);
				String betStr = matcher.group(2).trim();
				g6KuaduList = Arrays.asList(betStr.split(Welfare3dConstant.SEPARATOR_FOR_NUMBER));
				Collections.sort(directKuaduList);
				for (String num : g6KuaduList) {
					num = num.trim();
					int number = Integer.parseInt(num);
					if (number < 0 || number > 9)
						throw new DataException("方案内容不正确,号码在[02-09]之间.");
					countUnits += Welfare3dConstant.UNITS_G6_KUADU[number];
				}
				if (units != countUnits)
					throw new DataException("注数与系统计算的不一致.");
			}else {
				throw new DataException("投注方式错误");
			}
			lineContent.setArea1List(area1List);
			lineContent.setArea2List(area2List);
			lineContent.setArea3List(area3List);
			lineContent.setGroup3List(group3List);
			lineContent.setGroup6List(group6List);
			lineContent.setGroupSumList(groupSumList);
			lineContent.setDirectSumList(directSumList);
			lineContent.setBaoChuanList(baoChuanList);
			lineContent.setDirectKuaduList(directKuaduList);
			lineContent.setG3KuaduList(g3KuaduList);
			lineContent.setG6KuaduList(g6KuaduList);
			
			lineContent.setUnits(countUnits);
			allUnits += countUnits;
			all.add(lineContent);

			area1List = new ArrayList<String>();
			area2List = new ArrayList<String>();
			area3List = new ArrayList<String>();
			group3List = new ArrayList<String>();
			group6List = new ArrayList<String>();
			directSumList = new ArrayList<String>();
			groupSumList = new ArrayList<String>();
			baoChuanList = new ArrayList<String>();
			directKuaduList = new ArrayList<String>();
			g3KuaduList = new ArrayList<String>();
			g6KuaduList = new ArrayList<String>();

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
	public static ContentBean buildSingleContentBean(String schemeContent, Welfare3dPlayType welfare3dPlayType)
			throws DataException {
		if (StringUtils.isBlank(schemeContent))
			throw new DataException("方案内容为空.");
		if (welfare3dPlayType.equals(Welfare3dPlayType.DirectSum)
				|| welfare3dPlayType.equals(Welfare3dPlayType.GroupSum)) {
			throw new DataException("该投注方式不支持单式投注");
		}
		int allUnits = 0;
		List<String> all = new ArrayList<String>(10);

		String[] arr = schemeContent.split("(\r\n|\n)+");
		StringBuilder line = new StringBuilder(25);
		List<String> numList = new ArrayList<String>();
		for (String str : arr) {
			Pattern patt = Pattern.compile(Welfare3dConstant.SINGLE_REGEX);
			Matcher matcher = patt.matcher(str);
			if (!matcher.matches())
				throw new DataException("方案内容格式不正确.");

			// 重置line、redSet、blueSet
			line.setLength(0);
			numList.clear();
			String[] numArr = matcher.group(1).split("\\D");
			for (String num : numArr) {
				num = num.trim();
				int number = Integer.parseInt(num);
				if (number < 0 || number > 9)
					throw new DataException("方案内容不正确,号码在[0-9]之间.");
				if(number<10){
					numList.add("0"+number);
				}else{
					numList.add(""+number);
				}
			}
			Integer num1 = Integer.valueOf(numList.get(0));
			Integer num2 = Integer.valueOf(numList.get(1));
			Integer num3 = Integer.valueOf(numList.get(2));
			if (welfare3dPlayType.equals(Welfare3dPlayType.Direct)) {
				// //直选
			} else if (welfare3dPlayType.equals(Welfare3dPlayType.Group3)) {
				// //组选3
				if (num1.equals(num2) && num2.equals(num3)) {
					// /三个数相同
					throw new DataException("组选三方案内容错误");
				} else if (!num1.equals(num2) && !num2.equals(num3) && !num1.equals(num3)) {
					// /三个不相同不中奖
					throw new DataException("组选三方案内容错误");
				}
			} else if (welfare3dPlayType.equals(Welfare3dPlayType.Group6)) {
				// //组选6
				if (num1.equals(num2) || num2.equals(num3) || num1.equals(num3)) {
					// /三个有两个是一样的不中奖
					throw new DataException("组选六方案内容错误");
				}
			} else {
				throw new DataException("投注方式错误");
			}
			if (numList.size() != 3)
				throw new DataException("投注数目不对.");
			line.append(StringUtils.join(numList, Constant.SINGLE_SEPARATOR_FOR_NUMBER));
			allUnits++;
			all.add(line.toString());
		}

		return new ContentBean(allUnits, StringUtils.join(all, "\r\n"));
	}
}
