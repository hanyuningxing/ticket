package com.cai310.orm.hibernate;

import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.event.SaveOrUpdateEvent;
import org.hibernate.event.SaveOrUpdateEventListener;

import com.cai310.entity.CreateMarkable;
import com.cai310.entity.UpdateMarkable;

/**
 * 自动设置创建和更新时间的监听器
 * 
 */
public class AuditListener implements SaveOrUpdateEventListener {
	private static final long serialVersionUID = 8397647156755392815L;

	public void onSaveOrUpdate(SaveOrUpdateEvent event) throws HibernateException {
		Object object = event.getObject();
		if (object instanceof CreateMarkable) {
			CreateMarkable entity = (CreateMarkable) object;
			if (entity.getCreateTime() == null)
				entity.setCreateTime(new Date());
		}
		if (object instanceof UpdateMarkable) {
			UpdateMarkable entity = (UpdateMarkable) object;
			entity.setLastModifyTime(new Date());
		}
	}
}
