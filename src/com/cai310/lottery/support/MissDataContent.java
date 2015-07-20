package com.cai310.lottery.support;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;
/**
 * 不使用,改用HashMap存放
 * @author Administrator
 *
 */
@Deprecated
public abstract class MissDataContent {

	@Override
	public String toString() {
		return JSONObject.fromObject(this).toString();
	}

	/**
	 * 将bean转化为map
	 * @return
	 */
	public Map<String, Integer> toMap() {
		try {
			Field[] fields = this.getClass().getDeclaredFields();
			if (fields != null) {
				Map<String, Integer> map = new HashMap<String, Integer>();
				for (Field field : fields) {
					field.setAccessible(true);// 设置允许访问
					map.put(field.getName(), (Integer)field.get(this));
				}
				return map;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
