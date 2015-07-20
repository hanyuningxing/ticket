package com.cai310.entity;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 统一定义id的entity基类.
 * 
 * 基类统一定义id的属性名称、数据类型、列名映射及生成策略. 子类可重载getId()函数重定义id的列名映射和生成策略.
 */
// JPA 基类的标识
@MappedSuperclass
public abstract class IdEntity implements Serializable {
	private static final long serialVersionUID = -2625679257568534904L;

	private Long id;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	/**以下配置为一张表对Id进行管理(Oracle)*/
	//@TableGenerator(name = "common_id_generator", allocationSize = 1, table = Constant.GENERATOR_TABLE)
	//@GeneratedValue(strategy = GenerationType.TABLE, generator = "common_id_generator")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
