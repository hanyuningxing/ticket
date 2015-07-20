package com.cai310.lottery.support.dlt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.Constant;
import com.cai310.lottery.DltConstant;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.ContentBean;
import com.cai310.utils.MathUtils;

/**
 * ContentBean构建器
 * 
 */
public class DltContentBeanBuilder {
	/**
	 * ContentBean复式构建器
	 * 
	 * @param schemeContent 方案内容
	 * @return {@link com.cai310.lottery.support.ContentBean}
	 * @throws DataException
	 */
	public static ContentBean buildCompoundContentBean(String schemeContent, DltPlayType playType) throws DataException {
		// /复式方案内容注数：
		if (StringUtils.isBlank(schemeContent))
			throw new DataException("方案内容为空.");

		int allUnits = 0;
		List<DltCompoundContent> all = new ArrayList<DltCompoundContent>();
		String[] arr = schemeContent.split("(\r\n|\n)+");

		/** 前区胆码 */
		List<String> redDanList = new ArrayList<String>();

		/** 前区号码 */
		List<String> redList = new ArrayList<String>();

		/** 后区号码 */
		List<String> blueList = new ArrayList<String>();

		/** 后区胆码 */
		List<String> blueDanList = new ArrayList<String>();

		for (String str : arr) {
			// 重置
			redDanList.clear();
			redList.clear();
			blueDanList.clear();
			blueList.clear();
			int countUnits = 0;
			DltCompoundContent lineContent = new DltCompoundContent();
			if (DltPlayType.General.equals(playType)||DltPlayType.GeneralAdditional.equals(playType)) {
				Pattern patt = Pattern.compile(DltConstant.GENERAL_COMPOUND_REGEX);
				Matcher matcher = patt.matcher(str);
				if (!matcher.matches())
					throw new DataException("方案内容格式不正确.");
				String unitsStr = matcher.group(1);
				int units = Integer.parseInt(unitsStr);
				String redDanStr = matcher.group(2);
				if (StringUtils.isNotBlank(redDanStr)) {
					redDanList = Arrays.asList(redDanStr.trim().split(DltConstant.SEPARATOR_FOR_NUMBER));
				}
				String redStr = matcher.group(3).trim();
				redList = Arrays.asList(redStr.split(DltConstant.SEPARATOR_FOR_NUMBER));
				String blueDanStr = matcher.group(4);
				if (StringUtils.isNotBlank(blueDanStr)) {
					blueDanList = Arrays.asList(blueDanStr.trim().split(DltConstant.SEPARATOR_FOR_NUMBER));
				}
				String blueStr = matcher.group(5).trim();
				blueList = Arrays.asList(blueStr.split(DltConstant.SEPARATOR_FOR_NUMBER));
				countUnits = calcTotalBets(redDanList.size(), redList.size(), blueDanList.size(), blueList.size());
				if (units != countUnits)
					throw new DataException("注数与系统计算的不一致.");
				lineContent.setUnits(units);
				lineContent.setBlueDanList(blueDanList);
				lineContent.setBlueList(blueList);
				lineContent.setRedDanList(redDanList);
				lineContent.setRedList(redList);

			} else if (DltPlayType.Select12to2.equals(playType)) {
				Pattern patt = Pattern.compile(DltConstant.SELECT12TO2_COMPOUND_REGEX);
				Matcher matcher = patt.matcher(str);
				if (!matcher.matches())
					throw new DataException("方案内容格式不正确.");
				String unitsStr = matcher.group(1);
				int units = Integer.parseInt(unitsStr);
				String blueDanStr = matcher.group(2);
				if (StringUtils.isNotBlank(blueDanStr)) {
					blueDanList = Arrays.asList(blueDanStr.trim().split(DltConstant.SEPARATOR_FOR_NUMBER));
				}
				String blueStr = matcher.group(3).trim();
				blueList = Arrays.asList(blueStr.split(DltConstant.SEPARATOR_FOR_NUMBER));
				countUnits = calSelect12to2TotalBets(blueDanList.size(), blueList.size());
				if (units != countUnits)
					throw new DataException("注数与系统计算的不一致.");
				lineContent.setUnits(units);
				lineContent.setBlueDanList(blueDanList);
				lineContent.setBlueList(blueList);
			} else {
				throw new DataException("投注方式不正确.");
			}
			//////////////////////////////////////验证号码准确性//////////////////////////////////////////
			Set<Integer> redSet = new TreeSet<Integer>();
			Set<Integer> blueSet = new TreeSet<Integer>();
			for (String blueDan : blueDanList) {
				int number = Integer.parseInt(blueDan);
				if (number < 1 || number > 12)
					throw new DataException("方案内容不正确,后区号码在[01-12]之间.");
				if (blueSet.contains(number))
					throw new DataException("后区不能出现重复号码.");
				blueSet.add(number);
			}
			for (String blue : blueList) {
				int number = Integer.parseInt(blue);
				if (number < 1 || number > 12)
					throw new DataException("方案内容不正确,后区号码在[01-12]之间.");
				if (blueSet.contains(number))
					throw new DataException("后区不能出现重复号码.");
				blueSet.add(number);
			}
			for (String redDan : redDanList) {
				int number = Integer.parseInt(redDan);
				if (number < 1 || number > 35)
					throw new DataException("方案内容不正确,前区号码在[01-35]之间.");
				if (redSet.contains(number))
					throw new DataException("前区不能出现重复号码.");
				redSet.add(number);
			}
			for (String red : redList) {
				int number = Integer.parseInt(red);
				if (number < 1 || number > 35)
					throw new DataException("方案内容不正确,前区号码在[01-35]之间.");
				if (redSet.contains(number))
					throw new DataException("前区不能出现重复号码.");
				redSet.add(number);
			}
			if (DltPlayType.General.equals(playType)||DltPlayType.GeneralAdditional.equals(playType)) {
				int redSize = redDanList.size() + redList.size();
				if (redSize < 5 || redSize > 35)
					throw new DataException("前区数目必须大于等于5,小于等于35.");
				if(null!=redDanList&&!redDanList.isEmpty()){
					///前区设胆
					if(redDanList.size()>4)throw new DataException("前区胆码最多设置4个");
					if(redDanList.size()>0&&redSize==5)
						throw new DataException("前区胆码加前区拖码必须大于等于6个,请选用普通投注");
				}
			}
			int blueSize = blueDanList.size() + blueList.size();
			if (blueSize < 2 || blueSize > 12)
				throw new DataException("后区数目必须大于等于2,小于等于12.");
			if(null!=blueDanList&&!blueDanList.isEmpty()){
				///后区设胆
				if(blueDanList.size()>1)throw new DataException("后区胆码最多设置1个");
				if(blueDanList.size()>0&&blueSize==2)
					throw new DataException("后区胆码加后区拖码必须大于等于3个,请选用普通投注");
			}
			//////////////////////////////////////验证号码准确性//////////////////////////////////////////
			allUnits += countUnits;
			all.add(lineContent);
			redDanList = new ArrayList<String>();
			redList = new ArrayList<String>();
			blueList = new ArrayList<String>();
			blueDanList = new ArrayList<String>();
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
	public static ContentBean buildSingleContentBean(String schemeContent, DltPlayType playType) throws DataException {
		if (StringUtils.isBlank(schemeContent))
			throw new DataException("方案内容为空.");

		int allUnits = 0;
		List<String> all = new ArrayList<String>(10);

		String[] arr = schemeContent.split("(\r\n|\n)+");
		StringBuilder line = new StringBuilder(25);
		Set<String> redSet = new TreeSet<String>();
		Set<String> blueSet = new TreeSet<String>();
		for (String str : arr) {
			// 重置
			line.setLength(0);
			redSet.clear();
			blueSet.clear();
			if (DltPlayType.General.equals(playType)||DltPlayType.GeneralAdditional.equals(playType)) {
				Pattern patt = Pattern.compile(DltConstant.GENERAL_SINGLE_REGEX);
				Matcher matcher = patt.matcher(str);
				if (!matcher.matches())
					throw new DataException("方案内容格式不正确.");
				String[] reds = matcher.group(1).split("\\D");
				for (String red : reds) {
					red = red.trim();
					int number = Integer.parseInt(red);
					if (number < 1 || number > 35)
						throw new DataException("方案内容不正确,前区号码在[01-35]之间.");
					if (redSet.contains(red))
						throw new DataException("前区不能出现重复号码.");
					if(number<10){
						redSet.add("0"+number);
					}else{
						redSet.add(""+number);
					}
				}
				if (redSet.size() != 5)
					throw new DataException("前区数目不对.");
				line.append(StringUtils.join(redSet, Constant.SINGLE_SEPARATOR_FOR_NUMBER));

				String[] blues = matcher.group(2).split("\\D");
				for (String blue : blues) {
					blue = blue.trim();
					int number = Integer.parseInt(blue);
					if (number < 1 || number > 12)
						throw new DataException("方案内容不正确,后区号码在[01-12]之间.");
					if (blueSet.contains(blue))
						throw new DataException("后区不能出现重复号码.");
					if(number<10){
						blueSet.add("0"+number);
					}else{
						blueSet.add(""+number);
					}
				}
				if (blueSet.size() != 2)
					throw new DataException("后区数目不对.");
				line.append(Constant.SINGLE_SEPARATOR_FOR_NUMBER).append(
						StringUtils.join(blueSet, Constant.SINGLE_SEPARATOR_FOR_NUMBER));
			} else if (DltPlayType.Select12to2.equals(playType)) {
				Pattern patt = Pattern.compile(DltConstant.SELECT12TO2_SINGLE_REGEX);
				Matcher matcher = patt.matcher(str);
				if (!matcher.matches())
					throw new DataException("方案内容格式不正确.");
				String[] blues = matcher.group(1).split("\\D");
				for (String blue : blues) {
					blue = blue.trim();
					int number = Integer.parseInt(blue);
					if (number < 1 || number > 12)
						throw new DataException("方案内容不正确,后区号码在[01-12]之间.");
					if (blueSet.contains(blue))
						throw new DataException("后区不能出现重复号码.");
					if(number<10){
						blueSet.add("0"+number);
					}else{
						blueSet.add(""+number);
					}
				}
				if (blueSet.size() != 2)
					throw new DataException("后区数目不对.");
				line.append(StringUtils.join(blueSet, Constant.SINGLE_SEPARATOR_FOR_NUMBER));
			} else {
				throw new DataException("投注方式不正确.");
			}
			allUnits++;
			all.add(line.toString());
		}
		return new ContentBean(allUnits, StringUtils.join(all, "\r\n"));
	}

	/**
	 * 计算注数(超级大乐透)
	 * 
	 * @param redDans 前区胆码
	 * @param reds 前区拖码
	 * @param blueDans 后区胆码
	 * @param blues 后区拖码
	 * @return 注数
	 */
	public static int calcTotalBets(int redDans, int reds, int blueDans, int blues) {
		return MathUtils.comp(DltConstant.RED_MAX_HITS - redDans, reds)
				* MathUtils.comp(DltConstant.BLUE_MAX_HITS - blueDans, blues);
	}

	/**
	 * 计算注数(12选2)
	 * 
	 * @param blueDans 后区胆码
	 * @param blues 后区拖码
	 * @return 注数
	 */
	public static int calSelect12to2TotalBets(int blueDans, int blues) {
		return MathUtils.comp(DltConstant.BLUE_MAX_HITS - blueDans, blues);
	}
}
