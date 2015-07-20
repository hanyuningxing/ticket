package com.cai310.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {
	public static void errorLogMethod(String log) {
		Logger logger = LoggerFactory.getLogger(LogUtil.class);
		logger.error(log);
	}

}
