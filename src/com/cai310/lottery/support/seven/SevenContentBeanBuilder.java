package com.cai310.lottery.support.seven;

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
import com.cai310.lottery.DltConstant;
import com.cai310.lottery.SevenConstant;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.ContentBean;
import com.cai310.utils.MathUtils;

/**
 * ContentBean构建器
 * 
 */
public class SevenContentBeanBuilder {
	/**
	 * ContentBean复式构建器
	 * 
	 * @param schemeContent 方案内容
	 * @return {@link com.cai310.lottery.support.ContentBean}
	 * @throws DataException
	 */
	public static ContentBean buildCompoundContentBean(String schemeContent) throws DataException {
		// /复式方案内容注数：
		if (StringUtils.isBlank(schemeContent))
			throw new DataException("方案内容为空.");

		int allUnits = 0;
		List<SevenCompoundContent> all = new ArrayList<SevenCompoundContent>(10);

		String[] arr = schemeContent.split("(\r\n|\n)+");
		List<String> ballList = new ArrayList<String>();
		List<String> danList = new ArrayList<String>();
		for (String str : arr) {
			Pattern patt = Pattern.compile(SevenConstant.COMPOUND_REGEX);
			Matcher matcher = patt.matcher(str);
			if (!matcher.matches())
				throw new DataException("方案内容格式不正确.");

			// 重置
			ballList.clear();
			danList.clear();

			SevenCompoundContent lineContent = new SevenCompoundContent();
			String unitsStr = matcher.group(1);
			int units = Integer.parseInt(unitsStr);
			lineContent.setUnits(units);
			int countUnits = 0;
			// /复式格式 1;2,3,4 1是胆码
			String danStr = matcher.group(2);
			if(StringUtils.isNotBlank(danStr)){
				if (StringUtils.isNotBlank(danStr)) {
					String[] danArr = danStr.split(SevenConstant.SEPARATOR_FOR_NUMBER);
					danList = Arrays.asList(danArr);
				}
				Collections.sort(danList);
				lineContent.setDanList(danList);
			}
			
			String ballStr = matcher.group(3).trim();
			String[] ballArr = ballStr.split(SevenConstant.SEPARATOR_FOR_NUMBER);
			ballList = Arrays.asList(ballArr);
			Collections.sort(ballList);
			lineContent.setBallList(ballList);
            //////////////////////////////////////验证号码准确性//////////////////////////////////////////
			Set<Integer> danSet = new TreeSet<Integer>();
			Set<Integer> ballSet = new TreeSet<Integer>();
			for (String dan : danList) {
				int number = Integer.parseInt(dan);
				if (number < 1 || number > 30)
					throw new DataException("方案内容不正确,胆码在[01-30]之间.");
				if (danSet.contains(number))
					throw new DataException("胆码不能出现重复号码.");
				danSet.add(number);
			}
			for (String ball : ballList) {
				int number = Integer.parseInt(ball);
				if (number < 1 || number > 30)
					throw new DataException("方案内容不正确,号码在[01-30]之间.");
				if (ballSet.contains(number))
					throw new DataException("号码不能出现重复号码.");
				ballSet.add(number);
			}
			
			countUnits = calTotalBets(danList.size(),ballSet.size());
			if (units != countUnits)
				throw new DataException("注数与系统计算的不一致.");
			allUnits += units;
			all.add(lineContent);
			ballList = new ArrayList<String>();
			danList = new ArrayList<String>();
		}
		return new ContentBean(allUnits, JSONArray.fromObject(all).toString());
	}
	public static void main(String[] args) throws DataException {
		SevenContentBeanBuilder.buildCompoundContentBean("1:03,09,10,11,12,14,15");
		
	}
	/**
	 * 计算注数
	 * 
	 * @param blueDans 胆码
	 * @param blues 拖码
	 * @return 注数
	 */
	public static int calTotalBets(int dans, int balls) {
		return MathUtils.comp(SevenConstant.MAX_HITS - dans, balls);
	}
	/**
	 * ContentBean单式构建器
	 * 
	 * @param schemeContent 方案内容
	 * @return {@link com.cai310.lottery.support.ContentBean}
	 * @throws DataException
	 */
	public static ContentBean buildSingleContentBean(String schemeContent) throws DataException {
		if (StringUtils.isBlank(schemeContent))
			throw new DataException("方案内容为空.");

		int allUnits = 0;
		List<String> all = new ArrayList<String>(10);

		String[] arr = schemeContent.split("(\r\n|\n)+");
		StringBuilder line = new StringBuilder(25);
		List<String> numList = new ArrayList<String>();
		for (String str : arr) {
			Pattern patt = Pattern.compile(SevenConstant.SINGLE_REGEX);
			Matcher matcher = patt.matcher(str);
			if (!matcher.matches())
				throw new DataException("方案内容格式不正确.");

			// 重置line、redSet、blueSet
			line.setLength(0);
			numList.clear();

			String[] numArr = matcher.group(1).split("\\D");
			Set<Integer> ballSet = new TreeSet<Integer>();
			for (String num : numArr) {
				num = num.trim();
				int number = Integer.parseInt(num);
				if (number < 1 || number > 30)
					throw new DataException("方案内容不正确,号码在[1-30]之间.");
				if (ballSet.contains(number))
					throw new DataException("号码不能出现重复号码.");
				ballSet.add(number);
				if(number<10){
					numList.add("0"+number);
				}else{
					numList.add(""+number);
				}
			}
			if (numList.size() != 7)
				throw new DataException("投注数目不对.");
			line.append(StringUtils.join(numList, Constant.SINGLE_SEPARATOR_FOR_NUMBER));

			allUnits++;
			all.add(line.toString());
		}

		return new ContentBean(allUnits, StringUtils.join(all, "\r\n"));
	}
}
