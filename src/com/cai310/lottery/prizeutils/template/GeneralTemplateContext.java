package com.cai310.lottery.prizeutils.template;

import java.util.HashMap;
import java.util.Map;

public class GeneralTemplateContext implements TemplateContext {
	private HashMap varTable = new HashMap();

	public GeneralTemplateContext() {
	}

	public GeneralTemplateContext(Map defaultVals) {
		if (defaultVals != null)
			varTable.putAll(defaultVals);
	}

	public Object getTmplVar(String name) {
		return varTable.get(name);
	}

	public void setVar(String name, Object val) {
		this.varTable.put(name, val);
	}
}
