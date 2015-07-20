package com.cai310.lottery.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cai310.lottery.Constant;

/**
 * 监听ServletContext的创建和销毁
 */
public class StartupListener implements ServletContextListener {
	private static final Logger log = LoggerFactory.getLogger(StartupListener.class);

	public void contextInitialized(ServletContextEvent event) {
		log.info("服务器正在启动...");
		Constant.ROOTPATH=event.getServletContext().getRealPath("/");
		log.info("系统路径...:"+Constant.ROOTPATH);
		Constant.BASEPATH=event.getServletContext().getContextPath();
		log.info("服务器路径...:"+Constant.BASEPATH);
	}

	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		log.info("服务器正在关闭...");
	}
}
