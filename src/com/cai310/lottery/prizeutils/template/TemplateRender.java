package com.cai310.lottery.prizeutils.template;

class TemplateRender extends TemplateBase {
	TemplateContext context;
	StringBuffer buf = new StringBuffer();

	public TemplateRender(TemplateContext context) {
		this.context = context;
	}

	protected void addConstant(String expr, int start, int end) {
		buf.append(expr, start, end);
	}

	protected void addVariable(String varName) {
		String val = String.valueOf(this.context.getTmplVar(varName));
		buf.append(val);
	}

	public String toString() {
		return buf.toString();
	}
}
