package com.cai310.lottery.utils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;

import com.cai310.lottery.Constant;

public class LocalUtils {
	protected static final Logger log = LoggerFactory.getLogger(LocalUtils.class);

	/**
	 * 获取国际化信息
	 * 
	 * @param msgKey 键值
	 * @param arg 参数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getLocalMessage(String msgKey, Object arg) {
		List args = new ArrayList();
		args.add(arg);
		return getLocalMessage(msgKey, args);
	}

	/**
	 * 获取国际化信息
	 * 
	 * @param msgKey 键值
	 * @return 国际化信息
	 */
	public static String getLocalMessage(String msgKey) {
		return getLocalMessage(msgKey, null);
	}

	/**
	 * 获取国际化信息
	 * 
	 * @param msgKey 键值
	 * @param args 参数
	 * @return 国际化信息
	 */
	@SuppressWarnings("unchecked")
	public static String getLocalMessage(String msgKey, List args) {
		String msg = null;

		if (StringUtils.isNotBlank(msgKey)) {
			Locale locale = LocaleContextHolder.getLocale();

			try {
				msg = ResourceBundle.getBundle(Constant.BUNDLE_KEY, locale).getString(msgKey);
			} catch (MissingResourceException ex) {
				log.error("找不到对应的key", ex);
				return null;
			}

			if (msg != null && args != null && !args.isEmpty()) {
				msg = MessageFormat.format(msg, args.toArray());
			}
		}

		return msg;
	}
}
