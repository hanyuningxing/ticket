package com.cai310.lottery.support.sevenstar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import com.cai310.lottery.Constant;
import com.cai310.lottery.SevenstarConstant;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.ContentBean;

/**
 * 福彩3DContentBean构建器
 * 
 */
public class SevenstarContentBeanBuilder {
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
		List<SevenstarCompoundContent> all = new ArrayList<SevenstarCompoundContent>(10);

		String[] arr = schemeContent.split("(\r\n|\n)+");
		List<String> area1List = new ArrayList<String>();
		List<String> area2List = new ArrayList<String>();
		List<String> area3List = new ArrayList<String>();
		List<String> area4List = new ArrayList<String>();
		List<String> area5List = new ArrayList<String>();
		List<String> area6List = new ArrayList<String>();
		List<String> area7List = new ArrayList<String>();
		for (String str : arr) {
			Pattern patt = Pattern.compile(SevenstarConstant.COMPOUND_REGEX);
			Matcher matcher = patt.matcher(str);
			if (!matcher.matches())
				throw new DataException("方案内容格式不正确.");

			// 重置line、redSet、blueSet
			area1List.clear();
			area2List.clear();
			area3List.clear();
			area4List.clear();
			area5List.clear();
			area6List.clear();
			area7List.clear();

			SevenstarCompoundContent lineContent = new SevenstarCompoundContent();
			String unitsStr = matcher.group(1);
			int units = Integer.parseInt(unitsStr);
			lineContent.setUnits(units);
			int countUnits = 0;
			// /复式格式 1,2,3,4-1,2,3,4-1,2,3,4-1,2,3,4-1,2,3,4-1,2,3,4-1,2,3,4
			String betStr = matcher.group(2).trim();
			String[] betArr = betStr.split(SevenstarConstant.SEPARATOR_FOR_);
			area1List = Arrays.asList(betArr[0].split(SevenstarConstant.SEPARATOR_FOR_NUMBER));
			area2List = Arrays.asList(betArr[1].split(SevenstarConstant.SEPARATOR_FOR_NUMBER));
			area3List = Arrays.asList(betArr[2].split(SevenstarConstant.SEPARATOR_FOR_NUMBER));
			area4List = Arrays.asList(betArr[3].split(SevenstarConstant.SEPARATOR_FOR_NUMBER));
			area5List = Arrays.asList(betArr[4].split(SevenstarConstant.SEPARATOR_FOR_NUMBER));
			area6List = Arrays.asList(betArr[5].split(SevenstarConstant.SEPARATOR_FOR_NUMBER));
			area7List = Arrays.asList(betArr[6].split(SevenstarConstant.SEPARATOR_FOR_NUMBER));
			Collections.sort(area1List);
			Collections.sort(area2List);
			Collections.sort(area3List);
			Collections.sort(area4List);
			Collections.sort(area5List);
			Collections.sort(area6List);
			Collections.sort(area7List);
			lineContent.setArea1List(area1List);
			lineContent.setArea2List(area2List);
			lineContent.setArea3List(area3List);
			lineContent.setArea4List(area4List);
			lineContent.setArea5List(area5List);
			lineContent.setArea6List(area6List);
			lineContent.setArea7List(area7List);
			countUnits = area1List.size() * area2List.size() * area3List.size() * area4List.size() * area5List.size()
					* area6List.size() * area7List.size();
			if (units != countUnits)
				throw new DataException("注数与系统计算的不一致.");
			allUnits += units;
			all.add(lineContent);
			area1List = new ArrayList<String>();
			area2List = new ArrayList<String>();
			area3List = new ArrayList<String>();
			area4List = new ArrayList<String>();
			area5List = new ArrayList<String>();
			area6List = new ArrayList<String>();
			area7List = new ArrayList<String>();
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
	public static ContentBean buildSingleContentBean(String schemeContent) throws DataException {
		if (StringUtils.isBlank(schemeContent))
			throw new DataException("方案内容为空.");

		int allUnits = 0;
		List<String> all = new ArrayList<String>(10);

		String[] arr = schemeContent.split("(\r\n|\n)+");
		StringBuilder line = new StringBuilder(25);
		List<String> numList = new ArrayList<String>();
		for (String str : arr) {
			Pattern patt = Pattern.compile(SevenstarConstant.SINGLE_REGEX);
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
					throw new DataException("方案内容不正确,号码在[00-09]之间.");
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
