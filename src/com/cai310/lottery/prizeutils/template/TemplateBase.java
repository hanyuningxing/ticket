package com.cai310.lottery.prizeutils.template;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

public abstract class TemplateBase {
	public static final int PARSEMODE_DEFAULT = 0;
	public static final int PARSEMODE_STRICT = 1;

	protected void parse(String expr, int parseMode) {
		switch (parseMode) {
		case PARSEMODE_STRICT:
			parse_strict(expr);
			break;
		default:
			parse(expr);
			break;
		}
	}

	protected abstract void addVariable(String varName);

	protected abstract void addConstant(String expr, int start, int end);

	private void parse_strict(String expr) {
		int pos = 0;
		int length = expr.length();
		int state = 0;
		int pre_index = 0;
		int post_index = 0;
		int len_prefix = 2;
		ArrayList vars = new ArrayList();
		while (pos < length && state >= 0) {
			switch (state) {
			case 0:
				pre_index = expr.indexOf("${", pos);
				if (pre_index < 0 || pre_index == length - len_prefix) {
					state = -1;
					break;
				}

				state = 1;
			case 1:
				post_index = expr.indexOf('}', pre_index);
				if (post_index < 0) {
					state = -1;
					break;
				}

				String varName = expr.substring(pre_index + len_prefix, post_index);
				if (StringUtils.isNotBlank(varName)) {
					this.addConstant(expr, pos, pre_index);
					this.addVariable(varName);
					vars.add(varName);
				}
				pos = post_index + 1;
				state = 0;
			}
		}
		if (pos < length)
			this.addConstant(expr, pos, length);

	}

	private void parse(String expr) {
		int pos = 0;
		int length = expr.length();
		int state = 0;
		int pre_index = 0;
		int post_index = 0;
		while (pos < length && state >= 0) {
			switch (state) {
			case 0:

				pre_index = expr.indexOf('{', pos);

				if (pre_index < 0 || pre_index == length - 1) {
					state = -1;
					break;
				}

				state = 1;
			case 1:
				if (expr.charAt(pre_index + 1) == '{') {
					this.addConstant(expr, pos, pre_index + 1);
					pos = pre_index + 2;
					state = 0;
					continue;
				}
				post_index = expr.indexOf('}', pre_index);
				if (post_index < 0) {
					state = -1;
					break;
				}

				String varName = expr.substring(pre_index + 1, post_index);
				if (StringUtils.isNotBlank(varName)) {
					this.addConstant(expr, pos, pre_index);
					this.addVariable(varName);
				}
				pos = post_index + 1;
				state = 0;
			}
		}
		if (pos < length)
			this.addConstant(expr, pos, length);
	}

}
