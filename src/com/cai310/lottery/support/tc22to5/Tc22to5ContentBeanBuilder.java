package com.cai310.lottery.support.tc22to5;

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
import com.cai310.lottery.Tc22to5Constant;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.ContentBean;
import com.cai310.utils.MathUtils;

/**
 * 体彩22选5ContentBean构建器
 * 
 */
public class Tc22to5ContentBeanBuilder {

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
		List<Tc22to5CompoundContent> all = new ArrayList<Tc22to5CompoundContent>();

		String[] arr = schemeContent.split("(\r\n|\n)+");
		List<String> danList = new ArrayList<String>();
		List<String> betList = new ArrayList<String>();
		for (String str : arr) {
			Pattern patt = Pattern.compile(Tc22to5Constant.COMPOUND_REGEX);
			Matcher matcher = patt.matcher(str);
			if (!matcher.matches())
				throw new DataException("方案内容格式不正确.");

			danList.clear();
			betList.clear();
			Tc22to5CompoundContent lineContent = new Tc22to5CompoundContent();

			String unitsStr = matcher.group(1);
			int units = Integer.parseInt(unitsStr);
			lineContent.setUnits(units);

			String danStr = matcher.group(2);
			if (StringUtils.isNotBlank(danStr)) {
				String[] dans = danStr.trim().split(Tc22to5Constant.SEPARATOR_FOR_NUMBER);
				for (String ball : dans) {
					ball = ball.trim();
					int number = Integer.parseInt(ball);
					if (number < 1 || number > 22)
						throw new DataException("方案内容不正确,胆码必须在[01-22]之间.");
					if (danList.contains(ball) || betList.contains(ball))
						throw new DataException("不能出现重复号码.");

					danList.add(ball);
				}
				if (danList.size() > 4)
					throw new DataException("胆码不能超过4个.");

				Collections.sort(danList);
				lineContent.setDanList(danList);
			}

			String[] bets = matcher.group(3).trim().split(Tc22to5Constant.SEPARATOR_FOR_NUMBER);
			for (String ball : bets) {
				ball = ball.trim();
				int number = Integer.parseInt(ball);
				if (number < 1 || number > 22)
					throw new DataException("方案内容不正确,号码必须在[01-22]之间.");
				if (danList.contains(ball) || betList.contains(ball))
					throw new DataException("不能出现重复号码.");

				betList.add(ball);
			}

			int betSize = danList.size() + betList.size();
			if (betSize < 5 || betSize > 22)
				throw new DataException("红球数目必须大于等于5,小于等于22.");

			Collections.sort(betList);
			lineContent.setBetList(betList);

			

			int countUnits = MathUtils.comp(5 - danList.size(), betList.size());
			if (units != countUnits)
				throw new DataException("注数与系统计算的不一致.");
			
			
			//////////////////////////////////////验证号码准确性//////////////////////////////////////////
			Set<Integer> betSet = new TreeSet<Integer>();
			
			for (String dan : danList) {
				int number = Integer.parseInt(dan);
				if (number < 1 || number > 22)
					throw new DataException("方案内容不正确,号码在[01-22]之间.");
				if (betSet.contains(number))
					throw new DataException("不能出现重复号码.");
				betSet.add(number);
			}
			for (String ball : betList) {
				int number = Integer.parseInt(ball);
				if (number < 1 || number > 22)
					throw new DataException("方案内容不正确,号码在[01-22]之间.");
				if (betSet.contains(number))
					throw new DataException("不能出现重复号码.");
				betSet.add(number);
			}
			if(null!=danList&&!danList.isEmpty()){
				if(danList.size()>4)throw new DataException("胆码最多设置4个");
				if(danList.size()>0&&betSize==5)
					throw new DataException("胆码加拖码必须大于等于6个,请选用普通投注");
			}
			//////////////////////////////////////验证号码准确性//////////////////////////////////////////
			
			
			
			allUnits += units;
			all.add(lineContent);
			danList = new ArrayList<String>();
			betList = new ArrayList<String>();
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
		StringBuilder line = new StringBuilder();
		Set<String> betSet = new TreeSet<String>();
		for (String str : arr) {
			Pattern patt = Pattern.compile(Tc22to5Constant.SINGLE_REGEX);
			Matcher matcher = patt.matcher(str);
			if (!matcher.matches())
				throw new DataException("方案内容格式不正确.");

			// 重置line、redSet、blueSet
			line.setLength(0);
			betSet.clear();

			String[] bets = matcher.group(1).split("\\D");

			for (String ball : bets) {
				ball = ball.trim();
				int number = Integer.parseInt(ball);
				if (number < 1 || number > 22)
					throw new DataException("方案内容不正确,号码在[01-22]之间.");
				if (betSet.contains(ball))
					throw new DataException("不能出现重复号码.");
				if(number<10){
					betSet.add("0"+number);
				}else{
					betSet.add(""+number);
				}
			}
			if (betSet.size() != 5)
				throw new DataException("选择号码数目不对.");
			line.append(StringUtils.join(betSet, Constant.SINGLE_SEPARATOR_FOR_NUMBER));
			
			allUnits++;
			all.add(line.toString());
		}

		return new ContentBean(allUnits, StringUtils.join(all, "\r\n"));
	}
}
