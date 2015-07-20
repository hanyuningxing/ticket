package com.cai310.lottery.support;

import org.hibernate.StaleObjectStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cai310.lottery.utils.LocalUtils;

public final class ExecutorUtils {
	protected static final Logger logger = LoggerFactory.getLogger(LocalUtils.class);

	/**
	 * 执行操作,如果执行过程中抛出{@link org.hibernate.StaleObjectStateException}异常,会尝试重新执行.
	 * 如果尝试执行maxTryTimes次都没有成功,将抛出
	 * {@link com.cai310.lottery.support.ExecuteException}
	 * 
	 * @param executable 操作对象
	 * @param maxTryTimes 最大尝试执行次数
	 * @throws ExecuteException 执行异常
	 */
	public static void exec(Executable executable, int maxTryTimes) throws ExecuteException {
		if (maxTryTimes <= 0)
			throw new ExecuteException("最大尝试执行次数不能小于或等于0.");

		int tryTimes = 0;
		boolean run = true;
		while (run) {
			try {
				executable.run();
				run = false;
			} catch (StaleObjectStateException e) {
				tryTimes++;
				run = tryTimes <= maxTryTimes;
				if (!run) {
					logger.error(e.getMessage(), e);
					throw new ExecuteException("系统繁忙，请稍候再试.");
				} else {
					logger.warn(e.getMessage());
					logger.warn("尝试重新执行--第" + tryTimes + "次");
				}
			}
		}
	}
}
