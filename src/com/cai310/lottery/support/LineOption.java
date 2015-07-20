package com.cai310.lottery.support;

/**
 * 场次选项接口
 * 
 */
public interface LineOption extends SelectedCount {

	/**
	 * @return 场次序号
	 */
	int getLineId();

	/**
	 * @return 是否胆码
	 */
	boolean isDan();
}
