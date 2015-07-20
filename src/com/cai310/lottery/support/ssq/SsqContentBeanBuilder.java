package com.cai310.lottery.support.ssq;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.Constant;
import com.cai310.lottery.SsqConstant;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.ContentBean;
import com.cai310.utils.MathUtils;

/**
 * 双色球ContentBean构建器
 * 
 */
public class SsqContentBeanBuilder {

	/**
	 * ContentBean复式构建器
	 * 
	 * @param schemeContent 方案内容
	 * @return {@link com.cai310.lottery.support.ContentBean}
	 * @throws DataException
	 */
	public static ContentBean buildCompoundContentBean(String schemeContent) throws DataException {
		if (StringUtils.isBlank(schemeContent))
			throw new DataException("方案内容为空.");

		int allUnits = 0;
		List<SsqCompoundContent> all = new ArrayList<SsqCompoundContent>(10);

		String[] arr = schemeContent.split("(\r\n|\n)+");
		List<String> redDanList = new ArrayList<String>();
		List<String> redList = new ArrayList<String>();
		List<String> blueList = new ArrayList<String>();
		for (String str : arr) {
			Pattern patt = Pattern.compile(SsqConstant.COMPOUND_REGEX);
			Matcher matcher = patt.matcher(str);
			if (!matcher.matches())
				throw new DataException("方案内容格式不正确.");

			// 重置line、redSet、blueSet
			redDanList.clear();
			redList.clear();
			blueList.clear();
			SsqCompoundContent lineContent = new SsqCompoundContent();

			String unitsStr = matcher.group(1);
			int units = Integer.parseInt(unitsStr);
			lineContent.setUnits(units);

			String danStr = matcher.group(2);
			if (StringUtils.isNotBlank(danStr)) {
				String[] redDans = danStr.trim().split(SsqConstant.SEPARATOR_FOR_NUMBER);
				for (String ball : redDans) {
					ball = ball.trim();
					int number = Integer.parseInt(ball);
					if (number < 1 || number > 33)
						throw new DataException("方案内容不正确,红球胆码必须在[01-33]之间.");
					if (redDanList.contains(ball) || redList.contains(ball))
						throw new DataException("红球不能出现重复号码.");

					redDanList.add(ball);
				}
				if (redDanList.size() > 5)
					throw new DataException("红球胆码不能超过5个.");

				Collections.sort(redDanList);
				lineContent.setRedDanList(redDanList);
			}

			String[] reds = matcher.group(3).trim().split(SsqConstant.SEPARATOR_FOR_NUMBER);
			for (String ball : reds) {
				ball = ball.trim();
				int number = Integer.parseInt(ball);
				if (number < 1 || number > 33)
					throw new DataException("方案内容不正确,红球号码必须在[01-33]之间.");
				if (redDanList.contains(ball) || redList.contains(ball))
					throw new DataException("红球不能出现重复号码.");

				redList.add(ball);
			}

			int redSize = redDanList.size() + redList.size();
			if (redSize < 6 || redSize > 33)
				throw new DataException("红球数目必须大于等于6,小于等于33.");

			Collections.sort(redList);
			lineContent.setRedList(redList);

			String[] blues = matcher.group(4).trim().split(SsqConstant.SEPARATOR_FOR_NUMBER);
			for (String blue : blues) {
				blue = blue.trim();
				int number = Integer.parseInt(blue);
				if (number < 1 || number > 16)
					throw new DataException("方案内容不正确,蓝球号码在[01-16]之间.");
				if (blueList.contains(blue))
					throw new DataException("蓝球不能出现重复号码.");
				blueList.add(blue);
			}
			Collections.sort(blueList);
			lineContent.setBlueList(blueList);

			int countUnits = MathUtils.comp(6 - redDanList.size(), redList.size()) * MathUtils.comp(1, blueList.size());
			if (units != countUnits)
				throw new DataException("注数与系统计算的不一致.");
			
			
			//////////////////////////////////////验证号码准确性//////////////////////////////////////////
			Set<Integer> redSet = new TreeSet<Integer>();
			Set<Integer> blueSet = new TreeSet<Integer>();
			for (String blue : blueList) {
				int number = Integer.parseInt(blue);
				if (number < 1 || number > 16)
					throw new DataException("方案内容不正确,蓝球号码在[01-16]之间.");
				if (blueSet.contains(number))
					throw new DataException("蓝球不能出现重复号码.");
				blueSet.add(number);
			}
			for (String redDan : redDanList) {
				int number = Integer.parseInt(redDan);
				if (number < 1 || number > 33)
					throw new DataException("方案内容不正确,红球号码在[01-33]之间.");
				if (redSet.contains(number))
					throw new DataException("红球不能出现重复号码.");
				redSet.add(number);
			}
			for (String red : redList) {
				int number = Integer.parseInt(red);
				if (number < 1 || number > 33)
					throw new DataException("方案内容不正确,红球号码在[01-33]之间.");
				if (redSet.contains(number))
					throw new DataException("红球不能出现重复号码.");
				redSet.add(number);
			}
			if(null!=redDanList&&!redDanList.isEmpty()){
				///红球设胆
				if(redDanList.size()>5)throw new DataException("红球胆码最多设置5个");
				if(redDanList.size()>0&&redSize==6)
					throw new DataException("红球胆码加红球拖码必须大于等于7个,请选用普通投注");
			}
			//////////////////////////////////////验证号码准确性//////////////////////////////////////////
			
			
			
			allUnits += units;
			all.add(lineContent);
			redDanList = new ArrayList<String>();
			redList = new ArrayList<String>();
			blueList = new ArrayList<String>();
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
		Set<String> redSet = new TreeSet<String>();
		for (String str : arr) {
			Pattern patt = Pattern.compile(SsqConstant.SINGLE_REGEX);
			Matcher matcher = patt.matcher(str);
			if (!matcher.matches())
				throw new DataException("方案内容格式不正确.");

			// 重置line、redSet、blueSet
			line.setLength(0);
			redSet.clear();

			String[] reds = matcher.group(1).split("\\D");

			for (String red : reds) {
				red = red.trim();
				int number = Integer.parseInt(red);
				if (number < 1 || number > 33)
					throw new DataException("方案内容不正确,红球号码在[01-33]之间.");
				if (redSet.contains(red))
					throw new DataException("红球不能出现重复号码.");
				if(number<10){
					redSet.add("0"+number);
				}else{
					redSet.add(""+number);
				}
			}
			if (redSet.size() != 6)
				throw new DataException("红球数目不对.");
			line.append(StringUtils.join(redSet, Constant.SINGLE_SEPARATOR_FOR_NUMBER));

			String blue = matcher.group(2);
			int number = Integer.parseInt(blue);
			if (number < 1 || number > 16)
				throw new DataException("蓝球号码必须在[01-16]之间.");
			if(number<10){
				line.append(Constant.SINGLE_SEPARATOR_FOR_NUMBER).append("0"+number);
			}else{
				line.append(Constant.SINGLE_SEPARATOR_FOR_NUMBER).append(""+number);
			}

			allUnits++;
			all.add(line.toString());
		}

		return new ContentBean(allUnits, StringUtils.join(all, "\r\n"));
	}
}
