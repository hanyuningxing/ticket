package com.cai310.entity;

import java.util.Date;

/**
 * 记录更新时间的接口
 */
public interface UpdateMarkable {

	public Date getLastModifyTime();

	public void setLastModifyTime(Date lastModifyTime);
}
