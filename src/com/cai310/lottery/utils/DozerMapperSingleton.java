package com.cai310.lottery.utils;

import net.sf.dozer.util.mapping.DozerBeanMapper;
import net.sf.dozer.util.mapping.MapperIF;

/**
 * dozer单例的wrapper.
 * 
 * dozer在同一jvm里使用单例即可,无需重复创建.
 * 但Dozer4.0自带的DozerBeanMapperSingletonWrapper必须使用dozerBeanMapping
 * .xml作参数初始化,因此重新实现.
 */
public final class DozerMapperSingleton {

	private static MapperIF instance;

	private DozerMapperSingleton() {
		// shoudn't invoke.
	}

	public static synchronized MapperIF getInstance() {
		if (instance == null) {
			instance = new DozerBeanMapper();
		}
		return instance;
	}

	@SuppressWarnings("unchecked")
	public static <T> T map(T object, Class<T> clazz) {
		return (T) getInstance().map(object, clazz);
	}
}
