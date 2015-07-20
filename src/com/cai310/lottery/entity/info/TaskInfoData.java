package com.cai310.lottery.entity.info;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.type.EnumType;

import com.cai310.entity.IdEntity;
import com.cai310.entity.UpdateMarkable;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.TaskState;
import com.cai310.lottery.common.TaskType;
@Entity
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "TASK_INFO_DATA")
public class TaskInfoData extends IdEntity implements UpdateMarkable{
	
	private static final long serialVersionUID = -1514049887422135653L;

	private TaskType taskType;
	
	private TaskState taskState;
	
	/** 线程跑的次数*/
	private Long runTime;
	
	/** 线程更新信息*/
	private String runString;
	
	/** 版本号 */
	protected Integer version;

	/** 最后更新时间 */
	private Date lastModifyTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(insertable = false)
	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	/**
	 * @return the taskType
	 */
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.TaskType"),
			@Parameter(name = EnumType.TYPE, value = Lottery.SQL_TYPE) })
	@Column(nullable = false, updatable = false)
	public TaskType getTaskType() {
		return taskType;
	}

	/**
	 * @param taskType the taskType to set
	 */
	public void setTaskType(TaskType taskType) {
		this.taskType = taskType;
	}

	/**
	 * @return the taskState
	 */
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.TaskState"),
			@Parameter(name = EnumType.TYPE, value = Lottery.SQL_TYPE) })
	@Column(nullable = false)
	public TaskState getTaskState() {
		return taskState;
	}

	/**
	 * @param taskState the taskState to set
	 */
	public void setTaskState(TaskState taskState) {
		this.taskState = taskState;
	}

	/**
	 * @return the runTime
	 */
	@Column(nullable = false)
	public Long getRunTime() {
		return runTime;
	}

	/**
	 * @param runTime the runTime to set
	 */
	public void setRunTime(Long runTime) {
		this.runTime = runTime;
	}

	/**
	 * @return the runString
	 */
	@Column(nullable = true,length=500)
	public String getRunString() {
		return runString;
	}

	/**
	 * @param runString the runString to set
	 */
	public void setRunString(String runString) {
		this.runString = runString;
	}
	
	@Version
	@Column(name = "version", nullable = false)
	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	
}
