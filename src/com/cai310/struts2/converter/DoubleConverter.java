package com.cai310.struts2.converter;

import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

public class DoubleConverter extends StrutsTypeConverter {

	@SuppressWarnings("unchecked")
	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		if (Double.class == toClass) {
			String val = values[0];
			if (val != null && !val.trim().equals(""))
				return Double.valueOf(val);
			else
				return null;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String convertToString(Map context, Object o) {
		if (o != null)
			return o.toString();
		return null;
	}

}
