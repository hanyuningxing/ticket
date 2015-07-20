package com.cai310.lottery.support;

/**
 * 选项接口
 * 
 */
public interface Item {

	/**
	 * @return 选项值
	 */
	String getValue();

	/**
	 * @return 选项名称
	 */
	String getText();

	/**
	 * @return 选项序号，从0开始
	 */
	int ordinal();
}
