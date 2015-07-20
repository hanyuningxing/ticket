package com.cai310.lottery.support;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 机选内容接口
 * 
 */
public interface RandomContentGenerator extends Serializable {

	/**
	 * 生成机选内容
	 * 
	 * @param units 生成的注数
	 * @param danMap 胆码
	 * @return 生成的机选内容集合
	 */
	List<String> generate(int units, Map<String, String> danMap) throws RandomException;

}
