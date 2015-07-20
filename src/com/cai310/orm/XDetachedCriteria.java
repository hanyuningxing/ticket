package com.cai310.orm;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.transform.ResultTransformer;

@SuppressWarnings("unchecked")
public class XDetachedCriteria extends DetachedCriteria {
	private static final long serialVersionUID = -4374366587322363623L;

	private List<Order> orders = new ArrayList<Order>();
	private List<Criterion> criterions = new ArrayList<Criterion>();
	private String primaryKey;

	private Projection projection;

	private ResultTransformer resultTransformer;

	private boolean cacheable;

	private String cacheRegion;

	private int maxResults;

	/**
	 * @param entityClass
	 */
	public XDetachedCriteria(Class entityClass) {
		super(entityClass.getName());
	}

	/**
	 * @param entityClass
	 */
	public XDetachedCriteria(Class entityClass, String alias) {
		super(entityClass.getName(), alias);
	}

	/**
	 * @see org.hibernate.criterion.DetachedCriteria#addOrder(org.hibernate.criterion.O­rder)
	 */
	public XDetachedCriteria addOrder(Order order) {
		orders.add(order);
		return this;
	}

	public XDetachedCriteria add(Criterion criterion) {
		super.add(criterion);
		criterions.add(criterion);
		return this;
	}

	/**
	 * @see org.hibernate.criterion.DetachedCriteria#setProjection(org.hibernate.criter­ion.Projection)
	 */
	public XDetachedCriteria setProjection(Projection projection) {
		this.projection = projection;
		return this;
	}

	/**
	 * @see org.hibernate.criterion.DetachedCriteria#setResultTransformer(org.hibernate.transform.ResultTransformer)
	 */
	public XDetachedCriteria setResultTransformer(ResultTransformer resultTransformer) {
		this.resultTransformer = resultTransformer;
		return this;
	}

	// ---------------------------------------------------------------------------

	/**
	 * 根据Session实例创建一个Criteria实例，并填充入此对象自身保存的条件后返回<br>
	 * 注意：返回的Criteria实例不含排序和投影信息，用户需要自己调用fillOrdersSetting、
	 * fillProjectionSetting填充入排序和投影信息
	 * 
	 * @see org.hibernate.criterion.DetachedCriteria#getExecutableCriteria(org.hibernat­e.Session)
	 */

	/**
	 * 填充入查询设置
	 * 
	 * @param criteria
	 * @return
	 */
	public Criteria fillSetting(Criteria criteria) {
		criteria = fillProjectionSetting(criteria);
		criteria = fillOrdersSetting(criteria);
		criteria = fillResultTransformerSetting(criteria);
		criteria = fillMaxResults(criteria);
		return criteria;
	}

	/**
	 * 填充入读取最大记录数设置
	 * 
	 * @param criteria
	 * @return
	 */
	public Criteria fillMaxResults(Criteria criteria) {
		if (maxResults > 0) {
			criteria.setMaxResults(maxResults);
		}
		return criteria;
	}

	/**
	 * 填充入排序信息设置
	 * 
	 * @param criteria
	 * @return
	 */
	public Criteria fillOrdersSetting(Criteria criteria) {
		for (Order order : orders) {
			criteria.addOrder(order);
		}
		return criteria;
	}

	/**
	 * 填充入投影信息设置
	 * 
	 * @param criteria
	 * @return
	 */
	public Criteria fillProjectionSetting(Criteria criteria) {
		criteria.setProjection(projection);
		return criteria;
	}

	/**
	 * 填充入结果类型转换
	 * 
	 * @param criteria
	 * @return
	 */
	public Criteria fillResultTransformerSetting(Criteria criteria) {
		if (resultTransformer != null) {
			criteria.setResultTransformer(resultTransformer);
		}
		return criteria;
	}

	public String getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	public void setCacheable(boolean cacheable) {
		this.cacheable = cacheable;
	}

	public void setCacheRegion(String cacheRegion) {
		this.cacheRegion = cacheRegion;
	}

	public boolean isCacheable() {
		return cacheable;
	}

	public String getCacheRegion() {
		return cacheRegion;
	}

	public int getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}
}
