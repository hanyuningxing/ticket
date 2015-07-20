package com.cai310.entity;

import java.util.Date;

/**
 * 记录创建时间的接口
 * 
 */
public interface CreateMarkable {

	/**
	 * 获取创建时间
	 * 
	 * @return 创建时间
	 */
	public Date getCreateTime();

	/**
	 * 设置创建时间
	 * 
	 * @param createTime 创建时间
	 */
	public void setCreateTime(Date createTime);
}
