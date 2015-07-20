package com.cai310.struts2.converter;

import java.util.Date;
import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cai310.utils.DateUtil;

public class DateConverter extends StrutsTypeConverter {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	public static String[] formats = { "yyyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM-dd", "yyyy/M/dd HH:mm",
			"yyyy/MM/dd HH:mm" };

	public Object convertFromString(Map ctx, String[] value, Class arg2) {
		if (value[0] == null || value[0].trim().equals("")) {
			return null;
		}
		String val = value[0];
		for (String ft : formats) {
			try {
				Date d = DateUtil.strToDate(val, ft);
				if (d == null)
					continue;
				else
					return d;
			} catch (RuntimeException pe) {
				continue;
			}
		}
		return null;
	}

	public String convertToString(Map ctx, Object data) {
		return DateUtil.dateToStr((Date) data, "yyyyy-MM-dd HH:mm:ss");
	}
}
