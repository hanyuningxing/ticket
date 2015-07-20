package com.cai310.lottery.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.directwebremoting.proxy.dwr.Util;

public class DwrUtil {
	public final static String ReceiveMessageFn = "receiveMessage";
	public final static String WorkEndFn = "workEnd";
	public final static String UpdateButtonStateFn = "updateButtonState";
	public final static String UpdateStatusText = "updateStatusText";

	public static enum LogType {
		INFO("black"), WARN("blue"), ERROR("red");
		public final String msgColor;

		private LogType(String msgColor) {
			this.msgColor = msgColor;
		}
	};

	private final static String DatePattern = "yyyy-MM-dd HH:mm:ss.SSS";
	private final static Set<LogType> logLevel = new HashSet<LogType>(0);
	static {
		logLevel.add(LogType.INFO);
		logLevel.add(LogType.WARN);
		logLevel.add(LogType.ERROR);
	}

	private Util util;
	private StringBuffer sb = new StringBuffer();
	private StringBuffer allMsg = new StringBuffer();

	public DwrUtil(Util util) {
		super();
		this.util = util;
	}

	/**
	 * 输出普通信息
	 * 
	 * @param msg
	 */
	public synchronized void sendInfoMsg(String msg) {
		sendMsg(msg, LogType.INFO);
	}

	/**
	 * 输出错误信息
	 * 
	 * @param msg
	 */
	public synchronized void sendErrorMsg(String msg) {
		sendMsg(msg, LogType.ERROR);
	}

	/**
	 * 输出警告信息
	 * 
	 * @param msg
	 */
	public synchronized void sendWarnMsg(String msg) {
		sendMsg(msg, LogType.WARN);
	}

	/**
	 * 输出日志信息
	 * 
	 * @param msg
	 * @param logType
	 */
	public synchronized void sendMsg(String msg, LogType logType) {
		if (logLevel.contains(logType)) {
			sb.setLength(0);
			sb.append("<font color='").append(logType.msgColor).append("'>［").append(getNowTimeString()).append("］［")
					.append(logType.toString()).append("］：").append(msg).append("</font>");
			allMsg.append(sb.toString()).append("<br/>");
			util.addFunctionCall(ReceiveMessageFn, sb.toString());
		}
	}

	/**
	 * 获取格式化好的当前时间字符串
	 * 
	 * @return
	 */
	private String getNowTimeString() {
		SimpleDateFormat df = new SimpleDateFormat(DatePattern);
		return df.format(new Date());
	}

	/**
	 * 更新页面按钮的状态
	 * 
	 * @param canRunButtonIds 可用的按钮ID数组
	 */
	public synchronized void updateButtonState(String[] canRunButtonIds) {
		util.addFunctionCall(UpdateButtonStateFn, canRunButtonIds);
	}

	/**
	 * 更新页面销售状态显示
	 * 
	 * @param map 销售状态map,key为相应状态在页面的ID,value为销售状态
	 */
	public synchronized void updateStatusText(Map<String, String> map) {
		util.addFunctionCall(UpdateStatusText, map);
	}

	/**
	 * 通知页面操作已经结束
	 * 
	 * @param time 操作花费的时间，单位毫秒
	 */
	public synchronized void callWorkEnd(long time) {
		sb.setLength(0);
		sb.append("<strong>操作结束，共耗时");
		long ms = time % 1000;
		long s = 0;
		long m = 0;
		time = time / 1000;
		if (time > 0) {
			s = time % 60;
			time = time / 60;
			if (time > 0) {
				m = time / 60;
				sb.append(m).append("分钟");
			}
			if (s > 0) {
				sb.append(s).append("秒");
			}
		}
		sb.append(ms).append("毫秒。</strong>");

		sendInfoMsg(sb.toString());
		util.addFunctionCall(WorkEndFn);
	}

	public String getAllMsg() {
		return allMsg.toString();
	}
}
