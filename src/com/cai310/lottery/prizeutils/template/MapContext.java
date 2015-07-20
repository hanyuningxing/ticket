package com.cai310.lottery.prizeutils.template;

import java.util.Map;

public class MapContext implements TemplateContext {
	private Map map;

	public MapContext(Map map) {
		this.map = map;
	}

	public void setVar(String name, Object val) {
		map.put(name, val);
	}

	public Object getTmplVar(String name) {
		if (map.containsKey(name))
			return map.get(name);
		return ErrorValue.Error;
	}
}
