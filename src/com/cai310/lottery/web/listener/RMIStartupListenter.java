package com.cai310.lottery.web.listener;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class RMIStartupListenter implements ServletContextListener {
	private static final Logger log = LoggerFactory.getLogger(RMIStartupListenter.class);
	private static final String host_prop = "rmi-host.properties";

	public void contextDestroyed(ServletContextEvent arg0) {
	}

	public void contextInitialized(ServletContextEvent arg0) {
		log.info("正在设置RMI发布的主机名...");
		Resource res = new ClassPathResource(host_prop);
		Properties ps = new Properties();
		String ip = null;
		try {
			ps.load(res.getInputStream());
			ip = ps.getProperty("ip");
		} catch (IOException e) {
			log.warn("加载属性文件[" + host_prop + "]失败,系统将自动获取主机.");
		}
		try {
			if (StringUtils.isBlank(ip)) {
				InetAddress[] inets;
				inets = InetAddress.getAllByName(InetAddress.getLocalHost().getHostName());
				for (InetAddress inet : inets) {
					if (!inet.getHostAddress().startsWith("192.168.")) {
						ip = inet.getHostAddress();
						break;
					}
				}
				if (StringUtils.isBlank(ip)) {
					log.warn("获取不到外网IP，RMI调用可能会失败。");
					ip = InetAddress.getLocalHost().getHostAddress();
				}
			}
			System.setProperty("java.rmi.server.hostname", ip);
			log.info("设置成功，当前RMI发布的主机名为：" + ip);
		} catch (UnknownHostException e) {
			log.error("获取主机IP失败！", e);
		}
	}
}
