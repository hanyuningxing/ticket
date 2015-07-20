package com.cai310.lottery.utils;

import java.util.HashMap;
import java.util.Map;

public class ManagerUtil {
	public static final String ISSUCCESS = "issuccess";
	public static final String MSG = "msg";
	public static final String OBJ = "obj";

	public static Map<String, Object> failStateMap(String msg) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(ISSUCCESS, Boolean.FALSE);
		map.put(MSG, msg);
		return map;
	}

	public static Map<String, Object> successStateMap(Boolean issuccess, Object o) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(ISSUCCESS, Boolean.TRUE);
		map.put(OBJ, o);
		return map;
	}
}
