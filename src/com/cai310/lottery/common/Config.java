package com.cai310.lottery.common;

import java.util.Properties;

public class Config {

	public final static Properties config = new Properties();
	static {
		try {
			config.load(Config.class.getClassLoader().getResourceAsStream(
					"config.properties"));
		} catch (Exception e) {
			System.out.println("config.properties read error");
		}
	}
}