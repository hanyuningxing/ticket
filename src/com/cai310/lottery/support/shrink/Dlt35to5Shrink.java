package com.cai310.lottery.support.shrink;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.cai310.utils.ExtensionCombCallBack;
import com.cai310.utils.MathUtils;

@Component("dlt35to5Shrink")
public class Dlt35to5Shrink extends DltShrink {

	@Override
	public String shrink(final ShrinkBean bean) {
		if (StringUtils.isBlank(bean.getContent()))
			return bean.getContent();

		String[] arr = bean.getContent().trim().split("_");
		if (arr.length != 2)
			throw new ShrinkException("格式错误.");

		final String[] danArr = (StringUtils.isNotBlank(arr[0])) ? arr[0].split(",") : new String[0];
		final String[] tuoArr = (StringUtils.isNotBlank(arr[1])) ? arr[1].split(",") : new String[0];

		if (tuoArr.length < 6)
			throw new ShrinkException("至少设置6个前区基本号码.");
		else if (danArr.length == 0 && tuoArr.length > 12)
			throw new ShrinkException("不设置前区胆码时，前区基本号码不能超过12个.");
		else if (danArr.length == 1 && tuoArr.length > 13)
			throw new ShrinkException("设置1个前区胆码时，前区基本号码不能超过13个.");
		else if (danArr.length == 2 && tuoArr.length > 14)
			throw new ShrinkException("设置2个前区胆码时，前区基本号码不能超过14个.");
		else if (danArr.length > 2)
			throw new ShrinkException("最多只能设置2个前区胆码.");

		final List<ShrinkProcessor> shrinkProcessorList = buildShrinkProcessorList(bean);
		final StringBuilder sb = new StringBuilder();
		final String seq = "\r\n";
		MathUtils.efficientCombExtension(5, danArr.length, tuoArr.length, new ExtensionCombCallBack() {
			@Override
			public boolean run(boolean[] comb1, int m1, boolean[] comb2, int m2) {
				String[] arr = new String[m1 + m2];
				int index = 0;
				for (int i = 0; i < comb1.length; i++) {
					if (comb1[i]) {
						arr[index] = danArr[i];
						index++;
					}
				}
				for (int i = 0; i < comb2.length; i++) {
					if (comb2[i]) {
						arr[index] = tuoArr[i];
						index++;
					}
				}
				boolean pass = true;
				List<Integer> numList = convert(arr);
				for (ShrinkProcessor processor : shrinkProcessorList) {
					if (!processor.check(numList)) {
						pass = false;
						break;
					}
				}
				if (pass) {
					Collections.sort(numList);
					sb.append(toStr(numList)).append(seq);
				}
				return false;
			}
		});
		if (sb.length() > 0) {
			sb.delete(sb.length() - seq.length(), sb.length());
		}
		return sb.toString();
	}

	@Override
	protected int getBigBeginNum() {
		return 18;
	}
}
