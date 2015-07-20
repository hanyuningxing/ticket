package com.cai310.lottery.prizeutils.template;

public class TemplateUtils {

	public static final String eval(TemplateContext context, String template) {
		return eval(context, template, TemplateBase.PARSEMODE_DEFAULT);
	}

	public static final String eval(TemplateContext context, String template, int parseMode) {
		TemplateRender render = new TemplateRender(context);
		render.parse(template, parseMode);
		return render.toString();

	}
}
