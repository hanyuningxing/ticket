package com.cai310.orm;

import java.io.Serializable;
import java.util.List;

public class Pagination implements Serializable {
	private static final long serialVersionUID = -889157967741969292L;

	private int pageNo = 1;

	private int pageSize;

	private boolean autoCount;

	@SuppressWarnings("unchecked")
	private List result = null;

	private long totalCount = -1;

	public Pagination(int pageSize) {
		this(pageSize, true);
	}

	public Pagination(int pageSize, boolean autoCount) {
		this.autoCount = autoCount;
		if (pageSize > 0) {
			this.pageSize = pageSize;
		}
	}

	/**
	 * 每页的记录数量，无默认值.
	 */
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		if (pageSize > 0) {
			this.pageSize = pageSize;
		}
	}

	/**
	 * 当前页的页号,序号从1开始.
	 */
	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	/**
	 * 第一条记录在结果集中的位置,序号从0开始.
	 */
	public int getFirst() {
		if (pageNo < 1 || pageSize < 1)
			return 0;
		else
			return (pageNo - 1) * pageSize;
	}

	/**
	 * 是否自动获取总页数,默认为false.
	 */
	public boolean isAutoCount() {
		return autoCount;
	}

	public void setAutoCount(boolean autoCount) {
		this.autoCount = autoCount;
	}

	/**
	 * 页内的数据列表.
	 */
	@SuppressWarnings("unchecked")
	public List getResult() {
		return result;
	}

	@SuppressWarnings("unchecked")
	public void setResult(List result) {
		this.result = result;
	}

	/**
	 * 总记录数.
	 */
	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * 计算总页数.
	 */
	public long getTotalPages() {
		if (totalCount == -1)
			return -1;

		long count = totalCount / pageSize;
		if (totalCount % pageSize > 0) {
			count++;
		}
		return count;
	}

	/**
	 * 是否还有下一页.
	 */
	public boolean isHasNext() {
		return (pageNo + 1 <= getTotalPages());
	}

	/**
	 * 返回下页的页号,序号从1开始.
	 */
	public int getNextPage() {
		if (isHasNext())
			return pageNo + 1;
		else
			return pageNo;
	}

	/**
	 * 是否还有上一页.
	 */
	public boolean isHasPre() {
		return (pageNo - 1 >= 1);
	}

	/**
	 * 返回上页的页号,序号从1开始.
	 */
	public int getPrePage() {
		if (isHasPre())
			return pageNo - 1;
		else
			return pageNo;
	}
}
