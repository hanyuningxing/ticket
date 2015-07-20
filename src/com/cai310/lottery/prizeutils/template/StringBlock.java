package com.cai310.lottery.prizeutils.template;

public abstract class StringBlock {
	StringBlock next;

	public abstract void join(TemplateContext ctx, StringBuffer buffer);
}
