package com.cai310.spring;

import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;

/**
 * 以静态变量保存Spring ApplicationContext,可在任意代码中取出ApplicaitonContext.
 */
public class SpringContextHolder implements ApplicationContextAware ,InitializingBean{

	private static ApplicationContext applicationContext;

	/**
	 * 实现ApplicationContextAware接口的context注入函数, 将其存入静态变量.
	 */
	public void setApplicationContext(ApplicationContext applicationContext) {
		SpringContextHolder.applicationContext = applicationContext;
	}

	/**
	 * 取得存储在静态变量中的ApplicationContext.
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		return (T) applicationContext.getBean(name);
	}

	/**
	 * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> clazz) {
		@SuppressWarnings("rawtypes")
		Map map = applicationContext.getBeansOfType(clazz);
		if (map == null)
			return null;
		return (T) map.values().iterator().next();
	}
	
	private static void checkApplicationContext() {
		if (applicationContext == null) {
			throw new IllegalStateException("applicaitonContext未注入,请在applicationContext.xml中定义SpringContextUtil");
		}
	}

	/**
	 * zhuhui motify by 2011-03-16 将每次获取资源前检查环境 改为属性注入后自动检查
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		checkApplicationContext();
	}
	/**
	 * zhuhui add by 2011-03-16 二次封装系统发布事件接口
	 */
	public static void publishEvent(ApplicationEvent applicationEvent){
		applicationContext.publishEvent(applicationEvent);
	}

}
