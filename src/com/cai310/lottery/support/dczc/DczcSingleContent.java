package com.cai310.lottery.support.dczc;

import java.io.Serializable;
import java.util.List;

/**
 * 单场足彩单式内容Bean
 * 
 */
public class DczcSingleContent implements Serializable {
	private static final long serialVersionUID = -6747955534455939909L;

	/** 选择的场次 */
	private List<Integer> lineIds;

	/** 投注内容 */
	private String content;

	/**
	 * @return {@link #lineIds}
	 */
	public List<Integer> getLineIds() {
		return lineIds;
	}

	/**
	 * @param lineIds the {@link #lineIds} to set
	 */
	public void setLineIds(List<Integer> lineIds) {
		this.lineIds = lineIds;
	}

	/**
	 * @return {@link #content}
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the {@link #content} to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
}
