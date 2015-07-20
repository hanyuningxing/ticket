package org.hibernate.property;

import java.io.Serializable;

import org.hibernate.PropertyNotFoundException;

/**
 * 重写hibernate的PropertyAccessor接口，继承序列化接口
 * 
 */ 
@SuppressWarnings("rawtypes")
public interface PropertyAccessor extends Serializable {
 
	/** 
	 * Create a "getter" for the named attribute
	 */
	public Getter getGetter(Class theClass, String propertyName) throws PropertyNotFoundException;

	/**
	 * Create a "setter" for the named attribute
	 */
	public Setter getSetter(Class theClass, String propertyName) throws PropertyNotFoundException;
}
