package com.cai310.utils;

public interface CombCallBack {

	/**
	 * 组合算法的回调函数
	 * 
	 * @param comb 选中的数组，数组元素的值为真表示原数组相同下标的元素被选中
	 * @param m 选中的元素数目
	 * @return 是否中止组合拆分程序
	 */
	boolean callback(boolean[] comb, int m);

}
