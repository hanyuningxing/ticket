package com.cai310.lottery.support.shrink;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Component("dltSpinmatrixShrink")
public class DltSpinmatrixShrink extends DltShrink {

	@Override
	public String shrink(final ShrinkBean bean) {
		if (StringUtils.isBlank(bean.getContent()))
			return bean.getContent();

		final List<ShrinkProcessor> shrinkProcessorList = buildShrinkProcessorList(bean);
		final StringBuilder sb = new StringBuilder();
		final String seq = "\r\n";
		String[] contentArr = bean.getContent().trim().split("\r\n");
		boolean pass;
		for (String line : contentArr) {
			if (StringUtils.isBlank(line))
				throw new ShrinkException("号码格式不正确.");
			String[] arr = line.split(",");
			if (arr.length != 5)
				throw new ShrinkException("号码数目不正确.");
			List<Integer> numList = convert(arr);
			pass = true;
			for (ShrinkProcessor processor : shrinkProcessorList) {
				if (!processor.check(numList)) {
					pass = false;
					break;
				}
			}
			if (pass) {
				sb.append(line).append(seq);
			}
		}
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
