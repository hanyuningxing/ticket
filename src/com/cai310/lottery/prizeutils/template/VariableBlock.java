package com.cai310.lottery.prizeutils.template;

public class VariableBlock extends StringBlock {
	private String varName;

	public VariableBlock(String varName) {
		this.varName = varName;
	}

	public void join(TemplateContext ctx, StringBuffer buffer) {
		Object val = ctx.getTmplVar(varName);
		if (val instanceof ErrorValue) {
			buffer.append('{').append(varName).append('}');
		} else {
			if (val != null)
				buffer.append(String.valueOf(val).trim());
		}
	}
}
