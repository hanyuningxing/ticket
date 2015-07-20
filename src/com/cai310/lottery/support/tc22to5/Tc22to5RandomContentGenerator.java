package com.cai310.lottery.support.tc22to5;

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
public class Tc22to5RandomContentGenerator implements RandomContentGenerator {
	private static final long serialVersionUID = -7658679208755867594L;
	public static final String DAN_REGEX = "^\\d{2}(?:\\D\\d{2}){0,3}$";
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

		List<String> betList = new ArrayList<String>();
		for (int i = 1; i <= 22; i++) {
			betList.add(getBallStr(i));
		}

		List<String> danList = new ArrayList<String>();
		if (danMap != null) {
			String dans = danMap.get("dan");
			if (StringUtils.isNotBlank(dans)) {
				dans = dans.trim();
				if (!dans.matches(DAN_REGEX))
					throw new RandomException("胆码必须使用两位数,只能设置1至4个胆码.");

				String[] arr = dans.trim().split("\\D");
				for (String ball : arr) {
					int num = Integer.parseInt(ball);
					if (num < 1 || num > 22)
						throw new RandomException("胆码必须在[01-22]之间.");
					if (danList.contains(ball))
						throw new RandomException("胆码不能重复.");

					danList.add(ball);
				}
				betList.removeAll(danList);
			}
		}

		List<String> contentList = new ArrayList<String>();
		List<String> tempList = new ArrayList<String>();
		for (int i = 0; i < units; i++) {
			int times = 0;
			String content = null;
			while (content == null || (contentList.contains(content) && times < RETRY_TIMES)) {
				tempList.clear();

				tempList.addAll(danList);
				if (danList.size() < 5) {
					Collections.shuffle(betList);
					tempList.addAll(betList.subList(0, 5 - danList.size()));
				}

				Collections.sort(tempList);
				
				content = StringUtils.join(tempList, SsqConstant.SEPARATOR_FOR_NUMBER);
				times++;
			}
			contentList.add(content);
		}

		return contentList;
	}

}
