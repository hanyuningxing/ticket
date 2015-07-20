package com.cai310.lottery.service;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.transform.ResultTransformer;

import com.cai310.orm.Pagination;
import com.cai310.orm.XDetachedCriteria;

/**
 * 查询接口
 * 
 */
@SuppressWarnings("unchecked")
public interface QueryService {

	/**
	 * 使用DetachedCriteria进行查询
	 * 
	 * @param criteria 查询封装对象
	 * @return 查询结果
	 */
	List findByDetachedCriteria(DetachedCriteria criteria);

	/**
	 * 使用DetachedCriteria进行查询
	 * 
	 * @param criteria 查询封装对象
	 * @param firstResult 起始下标
	 * @param maxResults 查询条数
	 * @return 查询结果
	 */
	List findByDetachedCriteria(DetachedCriteria criteria, int firstResult, int maxResults);

	/**
	 * 使用XDetachedCriteria进行分页查询
	 * 
	 * @param criteria 查询封装对象
	 * @param page 分页对象
	 * @return 包含查询结果的分页对象
	 */
	Pagination findByCriteriaAndPagination(XDetachedCriteria criteria, Pagination page);

	/**
	 * 使用HQL查询
	 * 
	 * @param queryString hql查询语句
	 * @return 查询结果
	 */
	List findByHql(String queryString);

	/**
	 * 使用HQL查询
	 * 
	 * @param queryString hql查询语句
	 * @param paramMap 查询参数
	 * @return 查询结果
	 */
	List findByHql(String queryString, Map<String, Object> paramMap);

	/**
	 * 使用HQL查询
	 * 
	 * @param queryString hql查询语句
	 * @param paramMap 查询参数
	 * @param transformer 查询结果封装类型
	 * @return 查询结果
	 */

	List findByHql(String queryString, Map<String, Object> paramMap, ResultTransformer transformer);

	/**
	 * 使用HQL查询，支持分页
	 * 
	 * @param queryString hql查询语句
	 * @param paramMap 查询参数MAP
	 * @param page 分页对象
	 * @param transformer 结果转换对象
	 * @return 包含查询结果的分页对象
	 */
	Pagination findByHql(String queryString, Map<String, Object> paramMap, Pagination page,
			ResultTransformer transformer);

	/**
	 * 使用SQL查询，支持分页
	 * 
	 * @param sql SQL查询语句
	 * @param page 分页对象
	 * @param entity 查询的实体class
	 * @return 包含查询结果的分页对象
	 */
	Pagination findBySql(String sql, Pagination page, Class entity);
	
	public List findByDetachedCriteria(DetachedCriteria criteria,boolean cacheable);
	public List findByDetachedCriteria(DetachedCriteria criteria, int firstResult, int maxResults,boolean cacheable);

}
