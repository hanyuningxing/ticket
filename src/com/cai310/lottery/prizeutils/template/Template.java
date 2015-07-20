package com.cai310.lottery.prizeutils.template;

import java.util.Iterator;
import java.util.LinkedList;

public class Template extends TemplateBase {

	private java.util.LinkedList blockList = new LinkedList();

	public Template(String expr) {
		parse(expr, PARSEMODE_DEFAULT);
	}

	public Template(String expr, int parseMode) {
		parse(expr, parseMode);
	}

	protected void addConstant(String expr, int pos, int pre_index) {
		this.addBlock(new ConstantStringBlock(expr, pos, pre_index));
	}

	protected void addVariable(String varName) {
		VariableBlock block = new VariableBlock(varName);
		this.addBlock(block);
	}

	public void addBlock(StringBlock block) {
		this.blockList.add(block);
	}

	public String eval(TemplateContext context) {
		StringBuffer buffer = new StringBuffer();
		Iterator it = this.blockList.iterator();
		while (it.hasNext()) {
			StringBlock block = (StringBlock) it.next();
			block.join(context, buffer);
		}
		return buffer.toString();
	}

}
