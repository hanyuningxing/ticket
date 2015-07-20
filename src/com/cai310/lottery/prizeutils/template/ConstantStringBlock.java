package com.cai310.lottery.prizeutils.template;

public class ConstantStringBlock extends StringBlock {
	private String m_const;

	public ConstantStringBlock() {
	}

	public ConstantStringBlock(String aConst) {
		m_const = aConst;
	}

	public ConstantStringBlock(String str, int start, int end) {
		this.m_const = str.substring(start, end);
	}

	public String getConst() {
		return m_const;
	}

	public void setConst(String aConst) {
		m_const = aConst;
	}

	public void join(TemplateContext ctx, StringBuffer buffer) {
		buffer.append(m_const);
	}

}
