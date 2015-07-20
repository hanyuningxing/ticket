package com.cai310.lottery.support.ssq;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.cai310.lottery.SsqConstant;
import com.cai310.lottery.support.RandomContentGenerator;
import com.cai310.lottery.support.RandomException;

@Component
public class SsqRandomContentGenerator implements RandomContentGenerator {
	private static final long serialVersionUID = -7658679208755867594L;
	public static final String RED_DAN_REGEX = "^\\d{2}(?:\\D\\d{2}){0,5}$";
	public static final String BLUE_DAN_REGEX = "^\\d{2}$";
	public static final int RETRY_TIMES = 20;

	private String getBallStr(int ballInt) {
		if (ballInt < 10)
			return "0" + ballInt;
		else
			return String.valueOf(ballInt);
	}

	public List<String> generate(int units, Map<String, String> danMap) throws RandomException {
		if (units <= 0)
			throw new RandomException("机选注数不能小于或等于0.");

		List<String> redList = new ArrayList<String>();
		for (int i = 1; i <= 33; i++) {
			redList.add(getBallStr(i));
		}

		List<String> blueList = new ArrayList<String>();
		for (int j = 1; j <= 16; j++) {
			blueList.add(getBallStr(j));
		}

		List<String> redDanList = new ArrayList<String>();
		String blueDan = null;
		if (danMap != null) {
			String reds = danMap.get("red");
			if (StringUtils.isNotBlank(reds)) {
				reds = reds.trim();
				if (!reds.matches(RED_DAN_REGEX))
					throw new RandomException("红球胆码必须使用两位数,只能设置1至6个胆码.");

				String[] arr = reds.trim().split("\\D");
				for (String red : arr) {
					int num = Integer.parseInt(red);
					if (num < 1 || num > 33)
						throw new RandomException("红球胆码必须在[01-33]之间.");
					if (redDanList.contains(red))
						throw new RandomException("红球胆码不能重复.");

					redDanList.add(red);
				}
				redList.removeAll(redDanList);
			}

			String blue = danMap.get("blue");
			if (StringUtils.isNotBlank(blue)) {
				blue = blue.trim();
				if (!blue.matches(BLUE_DAN_REGEX))
					throw new RandomException("蓝球胆码必须使用两位数,只能设置1个胆码.");

				blueDan = blue;
			}
		}

		List<String> contentList = new ArrayList<String>();
		List<String> tempList = new ArrayList<String>();
		for (int i = 0; i < units; i++) {
			int times = 0;
			String content = null;
			while (content == null || (contentList.contains(content) && times < RETRY_TIMES)) {
				tempList.clear();

				tempList.addAll(redDanList);
				if (redDanList.size() < 6) {
					Collections.shuffle(redList);
					tempList.addAll(redList.subList(0, 6 - redDanList.size()));
				}

				Collections.sort(tempList);

				if (blueDan != null)
					tempList.add(blueDan);
				else {
					Collections.shuffle(blueList);
					tempList.addAll(blueList.subList(0, 1));
				}

				content = StringUtils.join(tempList, SsqConstant.SEPARATOR_FOR_NUMBER);
				times++;
			}
			contentList.add(content);
		}

		return contentList;
	}

}
