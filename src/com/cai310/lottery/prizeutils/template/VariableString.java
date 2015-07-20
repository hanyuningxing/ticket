package com.cai310.lottery.prizeutils.template;

import java.util.Map;

public class VariableString {
	private GeneralTemplateContext tmplContext;
	private Template tmpl;

	public VariableString(String tmplStr, Map defaults) {
		tmplContext = new GeneralTemplateContext(defaults);
		tmpl = new Template(tmplStr);
	}

	public void setVar(String name, Object val) {
		this.tmplContext.setVar(name, val);
	}

	public String toString() {
		return tmpl.eval(this.tmplContext);
	}
}
